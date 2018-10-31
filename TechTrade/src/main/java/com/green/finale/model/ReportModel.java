package com.green.finale.model;

public class ReportModel {

	private String reason;

	private String content;

	private long targetedPost;

	private String targetedUser;

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

	public String getTargetedUser() {
		return targetedUser;
	}

	public void setTargetedUser(String targetedUser) {
		this.targetedUser = targetedUser;
	}

}
