package com.green.finale.model;

import java.util.Date;

import com.green.finale.entity.UserReportId;

public class UserReportModel {

	private UserReportId id;

	private String reason;

	private String content;

	private String targetedUser;

	private long involvedIn;

	private Date createdAt;

	public UserReportId getId() {
		return id;
	}

	public void setId(UserReportId id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTargetedUser() {
		return targetedUser;
	}

	public void setTargetedUser(String targetedUser) {
		this.targetedUser = targetedUser;
	}

	public long getInvolvedIn() {
		return involvedIn;
	}

	public void setInvolvedIn(long involvedIn) {
		this.involvedIn = involvedIn;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
