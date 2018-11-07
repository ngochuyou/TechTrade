package com.green.finale.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.green.finale.dao.AccountDAO;
import com.green.finale.dao.AccountImageDAO;
import com.green.finale.dao.ImageDAO;
import com.green.finale.dao.MessageDAO;
import com.green.finale.dao.UserReportDAO;
import com.green.finale.dao.WardDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.AccountImage;
import com.green.finale.entity.Image;
import com.green.finale.entity.Message;
import com.green.finale.entity.UserReport;
import com.green.finale.entity.UserReportId;
import com.green.finale.model.AccountModel;
import com.green.finale.model.InboxModel;
import com.green.finale.model.MessageModel;
import com.green.finale.model.UserReportModel;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Contants;
import com.green.finale.utils.Gender;

@Service
public class AccountService {

	@Autowired
	private AccountDAO accDao;

	@Autowired
	private WardDAO wardDao;

	@Autowired
	private MessageDAO messDao;

	@Autowired
	private UserReportDAO userReportDao;

	@Autowired
	private AccountImageDAO accountImageDao;
	
	@Autowired
	private ImageDAO imageDao;
	
	@Transactional
	public Account find(String username) {
		Account acc = accDao.find(username);

		if (acc == null) {
			return null;
		} else {
			return acc;
		}
	}

	@Transactional
	public String updateAccount(AccountModel acc, String username) {

		Account oldAccount = accDao.find(username);

		if (oldAccount != null) {
			oldAccount = extractAccount(acc, oldAccount);
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String stringHolder = acc.getNewPassword();
			Date currentDate = new Date();

			if (!StringUtils.isEmpty(acc.getPassword()) && !StringUtils.isEmpty(stringHolder)) {
				if (stringHolder.length() < 8) {
					return Contants.INVALID_FIELDS;
				}

				if (!passwordEncoder.matches(acc.getPassword(), oldAccount.getPassword())) {
					return Contants.INVALID_FIELDS;
				}

				oldAccount.setPassword(passwordEncoder.encode(stringHolder));
			}

			stringHolder = acc.getPhone();

			if (!oldAccount.getPhone().equals(stringHolder) && !StringUtils.isEmpty(stringHolder)) {
				if (currentDate.getTime() - oldAccount.getLastUpdatePhone().getTime() < 2592000000L) {
					return Contants.PHONE_RECENTLY_UPDATED;
				}

				oldAccount.setPhone(stringHolder);
				oldAccount.setLastUpdatePhone(new Date());
			}

			stringHolder = acc.getWardId();
			
			if (!StringUtils.isEmpty(stringHolder) && !acc.getWardId().equals(oldAccount.getWard().getId())) {
				if (currentDate.getTime() - oldAccount.getLastUpdateLocation().getTime() < 2592000000L) {
					return Contants.LOCATION_RECENTLY_UPDATED;
				}

				oldAccount.setWard(wardDao.find(acc.getWardId()));
				oldAccount.setLastUpdateLocation(new Date());
			}

			String filename = "";
			AccountImage image = new AccountImage();

			if (acc.getAvatarFile().isEmpty()) {
				oldAccount.setAvatar(oldAccount.getAvatar());
			} else {
				filename = uploadImage(acc.getAvatarFile());
				image.setFilename(filename);
				image.setAccount(oldAccount);
				accountImageDao.insert(image);
				oldAccount.setAvatar(filename);
			}

			if (acc.getWallpaperFile().isEmpty()) {
				oldAccount.setWallpaper(oldAccount.getWallpaper());
			} else {
				image = new AccountImage();
				filename = uploadImage(acc.getWallpaperFile());
				image.setFilename(filename);
				image.setAccount(oldAccount);
				accountImageDao.insert(image);
				oldAccount.setWallpaper(filename);
			}

			accDao.update(oldAccount);

			return "";
		}

		return Contants.USER_NONEXSIT;
	}

	@Transactional
	public AccountModel findModel(String username, Principal principal) {
		Account acc = accDao.find(username);

		if (acc == null) {

			return null;
		}

		AccountModel accountModel = injectAccount(acc, principal);

		accountModel.setPassword("");
		accountModel.setNewPassword("");

		return accountModel;
	}

	@Transactional
	public Account findAccountByEmail(String email) {
		Account acc = accDao.findByEmail(email);

		if (acc == null) {
			return null;
		} else {
			return acc;
		}
	}

	@Transactional
	public Account findAccountByPhone(String phone) {
		Account acc = accDao.findByPhone(phone);

		if (acc == null) {
			return null;
		} else {
			return acc;
		}
	}

	@Transactional
	public String createAccount(AccountModel acc) {
		if (accDao.count() == 0) {
			bootSystem();
		}
		
		if (validateAccount(acc)) {
			if ((accDao.find(acc.getUsername()) == null) && (accDao.findByEmail(acc.getEmail()) == null)) {
				Account account = new Account();
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				account = extractAccount(acc, account);
				account.setPassword(passwordEncoder.encode(acc.getPassword()));
				account.setPhone(acc.getPhone());
				account.setAvatar("default.JPG");
				account.setWallpaper("wall_default.JPG");
				account.setCreateAt(new Date());
				account.setSpentMoney(0);
				account.setPrestigePoints(0);
				account.setWard(wardDao.find(acc.getWardId()));
				account.setRole(AccountRole.User);
				account.setLastUpdateLocation(new Date());
				account.setLastUpdatePhone(new Date());

				accDao.insert(account);
			} else {
				return Contants.ALREADYEXSIT;
			}
		} else {
			return Contants.INVALID_FIELDS;
		}

		return null;
	}

	@Transactional
	public byte[] getUserAva(String username) {
		Account acc = accDao.find(username);

		try {
			return getImageBytes(acc.getAvatar());
		} catch (Exception ex) {
			return null;
		}
	}

	@Transactional
	public byte[] getUserAvaByFilename(String filename) {
		try {
			return getImageBytes(filename + ".JPG");
		} catch (Exception ex) {
			return null;
		}
	}

	@Transactional
	public byte[] getUserAvatar(String username) {
		Account user = accDao.find(username);

		if (user != null) {
			try {
				return getImageBytes(user.getAvatar());
			} catch (Exception ex) {
				return null;
			}
		}

		return null;
	}

	@Transactional
	public Account keycheck(String key) {
		Account acc = accDao.find(key);

		if (acc == null) {
			acc = accDao.findByEmail(key);
		}

		return acc;
	}

	@Transactional
	public String resetPassword(AccountModel accModel) {
		Account acc = accDao.find(accModel.getUsername());

		if (acc != null) {
			String newPass = accModel.getNewPassword();

			if (StringUtils.isEmpty(newPass) || newPass.length() < 8) {

				return Contants.INVALID_FIELDS;
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			acc.setPassword(encoder.encode(newPass));
			accDao.update(acc);

			return null;
		}

		return Contants.NONEXSIT;
	}

	@Transactional
	public InboxModel getInboxModel(String username, int page) {
		InboxModel model = new InboxModel();
		List<Message> unreadMessages = messDao.getReceivedList(username, false, page);

		model.setUnreadMessages(getMessageModelList(unreadMessages));
		model.setReadMessages(getMessageModelList(messDao.getReceivedList(username, true, page)));
		model.setUnreadQty(unreadMessages.size());

		return model;
	}

	@Transactional
	public List<Object[]> getNewMessages(String username) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		date.setTime(date.getTime() - 30000);

		return messDao.getJustRecentlyReceivedList(username, sdf.format(date));
	}

	@Transactional
	public List<MessageModel> getReceivedMessage(String username, int page) {

		return getMessageModelList(messDao.getReceivedList(username, true, page));
	}

	@Transactional
	public List<Object[]> getSentMessage(String username, int page) {

		return messDao.getSentList(username, page);
	}

	@Transactional
	public String createMessage(String username, String receiverId, String content) {
		String[] receiverIds = receiverId.replaceFirst(",", "").split(",");
		Account receiver = null;
		Message mess = null;
		Account sender = accDao.find(username);
		Date sentAt = new Date();

		for (String id : receiverIds) {
			receiver = accDao.find(id);

			if (receiver == null) {
				return Contants.USER_NONEXSIT + ":" + id;
			}

			mess = new Message();

			mess.setSender(sender);
			mess.setReceiver(receiver);
			mess.setContent(content);
			mess.setRead(false);
			mess.setSentAt(sentAt);
			mess.setDeletedByReceiver(false);
			mess.setDeletedBySender(false);

			messDao.insert(mess);
		}

		return "Sent!";
	}

	@Transactional
	public String deleteMessage(String username, long messId) {
		Message mess = messDao.find(messId);

		if (mess == null) {
			return Contants.NONEXSIT;
		}

		String receiver = mess.getReceiver().getUsername();
		String sender = mess.getSender().getUsername();

		if (!receiver.equals(username) && !sender.equals(username)) {
			return Contants.NOT_BELONG;
		}

		if (mess.isDeletedByReceiver() == true || mess.isDeletedBySender() == true) {
			messDao.delete(mess);

			return "Message deleted.";
		} else {
			if (username.equals(receiver)) {
				messDao.deleteByReceiver(messId);
			} else {
				messDao.deleteBySender(messId);
			}
		}

		return "Message deleted.";
	}

	@Transactional
	public String markMessage(String username, long messId) {
		Message mess = messDao.find(messId);

		if (mess == null) {
			return Contants.NONEXSIT;
		}

		String receiver = mess.getReceiver().getUsername();
		String sender = mess.getSender().getUsername();

		if (!receiver.equals(username) && !sender.equals(username)) {
			return Contants.NOT_BELONG;
		}

		if (mess.isRead() == true) {
			messDao.markMessage(messId, false);

			return "Marked as unread.";
		} else {
			messDao.markMessage(messId, true);

			return "Marked as read.";
		}
	}

	@Transactional
	public String reportUser(UserReportModel model, String username) {
		Account user = accDao.find(username);

		if (user == null) {
			return Contants.USER_NONEXSIT;
		}

		String targetdUsername = null;

		targetdUsername = model.getTargetedUser();

		if (targetdUsername == null) {

			return Contants.INVALID_FIELDS;
		}

		Account targetedUser = accDao.find(targetdUsername);

		if (targetedUser == null) {

			return Contants.USER_NONEXSIT;
		}

		UserReportId reportId = new UserReportId(user, targetedUser);
		UserReport report = userReportDao.find(reportId);

		if (report != null) {

			return Contants.USERREPORT_ALREADYEXSIT;
		}

		report = new UserReport();

		report.setId(reportId);
		report.setContent(model.getContent());
		report.setCreatedAt(new Date());
		report.setDescription(model.getReason());
		userReportDao.insert(report);

		List<Account> admins = accDao.getAdminList();
		Account system = accDao.find("TechTrade");
		Date sentAt = new Date();
		Message reportNoti;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");

		for (Account admin : admins) {
			reportNoti = new Message();

			reportNoti.setContent("A User has been reported with the reason: " + report.getDescription()
					+ "\nDescription: " + report.getContent() + "\nOn " + sdf.format(report.getCreatedAt()));
			reportNoti.setDeletedByReceiver(false);
			reportNoti.setDeletedBySender(false);
			reportNoti.setRead(false);
			reportNoti.setReceiver(admin);
			reportNoti.setSender(system);
			reportNoti.setSentAt(sentAt);

			messDao.insert(reportNoti);
		}

		return "User report sent.";
	}	

	@Transactional
	public boolean passwordCheck(String username, String rawPassword) {
		String encodedPassword = accDao.find(username).getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (encoder.matches(rawPassword, encodedPassword)) {
			
			return true;
		}
		
		return false;
	}
	
	public String uploadImage(MultipartFile file) {
		String newestFilename = accountImageDao.getNewestImage().getFilename();
		long filenameToLong = Long.parseLong(newestFilename.replaceAll("\\D", ""));

		if (file.isEmpty()) {
			return null;
		}

		newestFilename = ++filenameToLong + "." + FilenameUtils.getExtension(file.getOriginalFilename());

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(Contants.USER_IMAGE_DESTINATION + newestFilename);
			Files.write(path, bytes);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return newestFilename;
	}

	public byte[] getImageBytes(String filename) throws IOException {
		File file = new File(Contants.USER_IMAGE_DESTINATION + filename);

		if (!file.exists()) {
			return null;
		}

		byte[] data = Files.readAllBytes(file.toPath());

		return data;
	}

	public boolean validateAccount(AccountModel acc) {
		Pattern p = null;
		Matcher matcher = null;

		if (StringUtils.isEmpty(acc.getEmail())) {
			return false;
		} else {
			p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
			matcher = p.matcher(acc.getEmail());

			if (!matcher.find()) {
				return false;
			}
		}

		if (StringUtils.isEmpty(acc.getUsername()) && acc.getUsername().length() < 8) {
			return false;
		}

		if (StringUtils.isEmpty(acc.getPassword()) && acc.getPassword().length() < 8) {
			return false;
		}

		if (StringUtils.isEmpty(acc.getPhone())) {
			return false;
		} else {
			p = Pattern.compile("\\D+");
			matcher = p.matcher(acc.getPhone());

			if (matcher.find()) {
				return false;
			}
		}

		if (StringUtils.isEmpty(acc.getGender())) {
			return false;
		}

		if (StringUtils.isEmpty(acc.getWardId())) {
			return false;
		}

		return true;
	}

	public AccountModel injectAccount(Account acc, Principal principal) {
		AccountModel model = new AccountModel();

		model.setUsername(acc.getUsername());
		model.setPassword(acc.getPassword());
		model.setEmail(acc.getEmail());
		model.setPhone(acc.getPhone());
		model.setAvatar(acc.getAvatar());
		model.setGender(acc.getGender());
		model.setRole(acc.getRole());
		model.setWardId(acc.getWard().getId());
		model.setSpentMoney(acc.getSpentMoney());
		model.setPrestigePoints(acc.getPrestigePoints());
		model.setWallpaper(acc.getWallpaper());
		model.setWard(acc.getWard());
		
		if (new Date().getTime() - acc.getLastUpdateLocation().getTime() < 2592000000L) {
			model.setUpdatableLocation(false);
		}
		else {
			model.setUpdatableLocation(true);
		}
		
		if (principal != null) {
			UserReportId reportId = new UserReportId(accDao.find(principal.getName()), acc);
			UserReport report = userReportDao.find(reportId);

			model.setReport(report);
		}

		return model;
	}

	public Account extractAccount(AccountModel acc, Account account) {
		account.setEmail(acc.getEmail());
		account.setUsername(acc.getUsername());
		account.setGender(acc.getGender());
		account.setRole(acc.getRole());

		return account;
	}

	public List<MessageModel> getMessageModelList(List<Message> messages) {
		List<MessageModel> models = new ArrayList<>();
		MessageModel model;

		for (Message mess : messages) {
			model = new MessageModel();

			model.setId(mess.getId());
			model.setSender(mess.getSender());
			model.setReceiver(mess.getReceiver());
			model.setSentAt(mess.getSentAt());
			model.setRead(mess.isRead());
			model.setContent(mess.getContent());

			models.add(model);
		}

		return models;
	}
	
	public void bootSystem() {
		Account system = new Account();
		
		system.setUsername("TechTrade");
		system.setAvatar("default.JPG");
		system.setBanned(false);
		system.setCreateAt(new Date());
		system.setDeleted(false);
		system.setEmail("techtrade@gmail.com");
		system.setGender(Gender.Other);
		system.setLastUpdateLocation(new Date());
		system.setLastUpdatePhone(new Date());
		system.setPassword(new BCryptPasswordEncoder().encode("techtrade"));
		system.setPhone("0974032706");
		system.setPrestigePoints(0);
		system.setRole(AccountRole.Admin);
		system.setSpentMoney(0);
		system.setWallpaper("wall_default.JPG");
		system.setWard(wardDao.find("00001"));
		
		accDao.insert(system);
		
		Image firstImage = new Image();
		
		firstImage.setFilename("0.JPG");
		imageDao.insert(firstImage);
		
		AccountImage firstPostImage = new AccountImage();
		
		firstPostImage.setFilename("0.JPG");
		accountImageDao.insert(firstPostImage);
	}	
}
