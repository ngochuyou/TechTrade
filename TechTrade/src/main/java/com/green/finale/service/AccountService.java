package com.green.finale.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.green.finale.dao.AccountDAO;
import com.green.finale.dao.WardDAO;
import com.green.finale.entity.Account;
import com.green.finale.model.AccountModel;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Contants;

@Service
public class AccountService {

	@Autowired
	private AccountDAO accDao;

	@Autowired
	private WardDAO wardDao;

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
	public AccountModel findModel(String username) {
		Account acc = accDao.find(username);

		if (acc == null) {
			
			return null;
		}
		
		return injectAccount(acc);
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
		if (validateRegistryAccount(acc)) {
			if ((accDao.find(acc.getUsername()) == null) && (accDao.findByEmail(acc.getEmail()) == null)) {
				Account account = new Account();
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				account.setEmail(acc.getEmail());
				account.setUsername(acc.getUsername());
				account.setPassword(passwordEncoder.encode(acc.getPassword()));
				account.setGender(acc.getGender());
				account.setAvatar("default.jpg");
				account.setPhone(acc.getPhone());
				account.setCreateAt(new Date());
				account.setSpentMoney(0);
				account.setPrestigePoints(0);
				account.setRole(AccountRole.User);
				account.setWard(wardDao.find(acc.getWardId()));

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
			return getImageBytes(filename + ".jpg");
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

	public String uploadFile(MultipartFile file) {
		if (file.isEmpty()) {
			return null;
		}

		try {
			// Get the file and save it to UPLOAD_FILE_DESTINATION
			byte[] bytes = file.getBytes();
			Path path = Paths.get(Contants.UPLOAD_FILE_DESTINATION + file.getOriginalFilename());

			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return file.getOriginalFilename();
	}

	public byte[] getImageBytes(String filename) throws IOException {
		File file = new File(Contants.UPLOAD_FILE_DESTINATION + filename);

		if (!file.exists()) {
			return null;
		}

		byte[] data = Files.readAllBytes(file.toPath());

		return data;
	}

	public boolean validateRegistryAccount(AccountModel acc) {
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
	
	public AccountModel injectAccount(Account acc) {
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
		
		return model;
	}

}
