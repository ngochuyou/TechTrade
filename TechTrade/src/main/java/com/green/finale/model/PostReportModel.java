package com.green.finale.model;

import java.util.Date;

import com.green.finale.entity.PostReportId;

public class PostReportModel {

	private PostReportId id;

	private String reason;

	private String content;

	private long targetedPost;

	private long userInvolvedIn;

	private long involvedIn;

	private Date createdAt;

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

	public long getTargetedPost() {
		return targetedPost;
	}

	public void setTargetedPost(long targetedPost) {
		this.targetedPost = targetedPost;
	}

	public long getInvolvedIn() {
		return involvedIn;
	}

	public void setInvolvedIn(long involved) {
		this.involvedIn = involved;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public PostReportId getId() {
		return id;
	}

	public void setId(PostReportId id) {
		this.id = id;
	}

	public long getUserInvolvedIn() {
		return userInvolvedIn;
	}

	public void setUserInvolvedIn(long userInvolvedIn) {
		this.userInvolvedIn = userInvolvedIn;
	}

}
