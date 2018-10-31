package com.green.finale.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.finale.dao.AccountDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.dao.PostReportDAO;
import com.green.finale.dao.UserReportDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.Post;
import com.green.finale.entity.PostReport;
import com.green.finale.entity.PostReportId;
import com.green.finale.entity.UserReport;
import com.green.finale.entity.UserReportId;
import com.green.finale.model.ReportModel;
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

	@Transactional
	public String reportPost(ReportModel model, String username) {
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

		return "Post report sent.";
	}

	@Transactional
	public String reportUser(ReportModel model, String username) {
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

		return "User report sent.";
	}
}
