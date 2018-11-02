package com.green.finale.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.finale.dao.AccountDAO;
import com.green.finale.dao.CommentDAO;
import com.green.finale.dao.ImageDAO;
import com.green.finale.dao.MessageDAO;
import com.green.finale.dao.PinDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.dao.PostReportDAO;
import com.green.finale.dao.UserReportDAO;
import com.green.finale.dao.VoteDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.Message;
import com.green.finale.entity.Post;
import com.green.finale.entity.PostReport;
import com.green.finale.entity.PostReportId;
import com.green.finale.entity.UserReport;
import com.green.finale.entity.UserReportId;
import com.green.finale.model.PostReportModel;
import com.green.finale.model.UserReportModel;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Contants;

@Service
public class AdminService {

	@Autowired
	private PostReportDAO postReportDao;

	@Autowired
	private UserReportDAO userReportDao;

	@Autowired
	private AccountDAO accDao;

	@Autowired
	private PostDAO postDao;

	@Autowired
	private MessageDAO messDao;

	@Autowired
	private CommentDAO commentDao;

	@Autowired
	private ImageDAO imageDao;

	@Autowired
	private VoteDAO voteDao;

	@Autowired
	private PinDAO pinDao;

	@Transactional
	public String reportPost(PostReportModel model, String username) {
		Account user = accDao.find(username);

		if (user == null) {
			return Contants.USER_NONEXSIT;
		}

		long targetedPostId = model.getTargetedPost();

		if (targetedPostId == 0) {

			return Contants.INVALID_FIELDS;
		}

		Post targetedPost = postDao.find(targetedPostId);

		if (targetedPost == null) {

			return Contants.POST_NONEXSIT;
		}

		PostReportId reportId = new PostReportId(user, targetedPost);
		PostReport report = postReportDao.find(reportId);

		if (report != null) {

			return Contants.POSTREPORT_ALREADYEXSIT;
		}

		report = new PostReport();

		report.setId(reportId);
		report.setContent(model.getContent());
		report.setCreatedAt(new Date());
		report.setDescription(model.getReason());
		postReportDao.insert(report);

		List<Account> admins = accDao.getAdminList();
		Account system = accDao.find("TechTrade");
		Date sentAt = new Date();
		Message reportNoti;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");

		for (Account admin : admins) {
			reportNoti = new Message();

			reportNoti.setContent("A Post has been reported with the reason: " + report.getDescription()
					+ "\nDescription: " + report.getContent() + "\nOn " + sdf.format(report.getCreatedAt()));
			reportNoti.setDeletedByReceiver(false);
			reportNoti.setDeletedBySender(false);
			reportNoti.setRead(false);
			reportNoti.setReceiver(admin);
			reportNoti.setSender(system);
			reportNoti.setSentAt(sentAt);

			messDao.insert(reportNoti);
		}

		return "Post report sent.";
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
	public List<PostReportModel> getPostReportList(int page) {

		return getPostReportModelList(postReportDao.getList(page));
	}

	@Transactional
	public List<UserReportModel> getUserReportList(int page) {

		return getUserReportModelList(userReportDao.getList(page));
	}

	public List<UserReportModel> getUserReportModelList(List<UserReport> reports) {
		List<UserReportModel> models = new ArrayList<>();
		UserReportModel model = null;

		for (UserReport report : reports) {
			model = new UserReportModel();

			model.setId(report.getId());
			model.setReason(report.getDescription());
			model.setCreatedAt(report.getCreatedAt());
			model.setContent(report.getContent());
			model.setInvolvedIn(userReportDao.count(report.getId().getTargetedUser().getUsername()));

			models.add(model);
		}

		return models;
	}

	public List<PostReportModel> getPostReportModelList(List<PostReport> reports) {
		List<PostReportModel> models = new ArrayList<>();
		PostReportModel model = null;

		for (PostReport report : reports) {
			model = new PostReportModel();

			model.setId(report.getId());
			model.setReason(report.getDescription());
			model.setCreatedAt(report.getCreatedAt());
			model.setContent(report.getContent());
			model.setInvolvedIn(postReportDao.count(report.getId().getTargetedPost().getId()));
			model.setUserInvolvedIn(
					postReportDao.countUserInvolve(report.getId().getTargetedPost().getCreateBy().getUsername()));

			models.add(model);
		}

		return models;
	}

	@Transactional
	public String permaDeletePost(long postId, String username) {
		Account user = accDao.find(username);

		if (user == null) {
			return Contants.USER_NONEXSIT;
		}

		Post post = postDao.find(postId);

		if (post != null) {
			if (user.getRole() == AccountRole.Admin) {
				commentDao.deleteByPost(postId);
				imageDao.deleteByPost(postId);
				voteDao.deleteByPost(postId);
				pinDao.deleteByPost(postId);
				postReportDao.deleteByPost(postId);
				postDao.delete(post);
				sendMessage(user, post.getCreateBy(),
						"One of your posts has been taken down by the Community. Please check your profile page. If you want to have further details, please reply to this message.");

				return "Post permanently deleted";
			}

			return Contants.NOT_BELONG;
		}

		return Contants.NONEXSIT;
	}

	@Transactional
	public String deletePost(long postId, String username) {
		Post targetedPost = postDao.find(postId);

		if (targetedPost == null) {

			return Contants.POST_NONEXSIT;
		}

		Account user = accDao.find(username);

		if (user.getRole() != AccountRole.Admin) {

			return Contants.NOT_BELONG;
		}

		targetedPost.setDeleted(true);
		postDao.update(targetedPost);
		sendMessage(user, targetedPost.getCreateBy(),
				"One of your posts has been taken down by the Community. Please check your profile page. If you want to have further details, please reply to this message.");
		
		return "Post deleted";
	}

	@Transactional
	public void sendMessage(Account sender, Account receiver, String content) {
		Message mess = new Message();

		mess.setContent(content);
		mess.setSender(sender);
		mess.setReceiver(receiver);
		mess.setDeletedByReceiver(false);
		mess.setDeletedBySender(false);
		mess.setRead(false);
		mess.setSentAt(new Date());

		messDao.insert(mess);
	}
}
