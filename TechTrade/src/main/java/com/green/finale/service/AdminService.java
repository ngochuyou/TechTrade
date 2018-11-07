package com.green.finale.service;

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
import com.green.finale.entity.UserReport;
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
		int userInvolved;
		String targetedUsername;
		
		for (UserReport report : reports) {
			userInvolved = 0;
			model = new UserReportModel();

			model.setId(report.getId());
			model.setReason(report.getDescription());
			model.setCreatedAt(report.getCreatedAt());
			model.setContent(report.getContent());
			targetedUsername = report.getId().getTargetedUser().getUsername();
			userInvolved += postReportDao.countUserInvolve(targetedUsername);
			userInvolved += userReportDao.count(targetedUsername);
			model.setInvolvedIn(userInvolved);

			models.add(model);
		}

		return models;
	}

	public List<PostReportModel> getPostReportModelList(List<PostReport> reports) {
		List<PostReportModel> models = new ArrayList<>();
		PostReportModel model = null;
		int userInvolved;
		String targetedUsername;
		
		for (PostReport report : reports) {
			userInvolved = 0;
			model = new PostReportModel();

			model.setId(report.getId());
			model.setReason(report.getDescription());
			model.setCreatedAt(report.getCreatedAt());
			model.setContent(report.getContent());
			model.setInvolvedIn(postReportDao.count(report.getId().getTargetedPost().getId()));
			targetedUsername = report.getId().getTargetedPost().getCreateBy().getUsername();
			userInvolved += postReportDao.countUserInvolve(targetedUsername);
			userInvolved += userReportDao.count(targetedUsername);
			
			model.setUserInvolvedIn(userInvolved);
			
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
						"One of your posts has been permanently taken down by the Community. Please check your profile page. For further details, please reply to this message.");

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
				"One of your posts has been taken down by the Community. Please check your profile page. For further details, please reply to this message.");
		
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
