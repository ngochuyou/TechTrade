package com.green.finale.model;

import java.util.List;

public class InboxModel {
	private List<MessageModel> unreadMessages;
	private List<MessageModel> readMessages;
	private long unreadQty;

	public List<MessageModel> getUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(List<MessageModel> unreadMessages) {
		this.unreadMessages = unreadMessages;
	}

	public List<MessageModel> getReadMessages() {
		return readMessages;
	}

	public void setReadMessages(List<MessageModel> readMessages) {
		this.readMessages = readMessages;
	}

	public long getUnreadQty() {
		return unreadQty;
	}

	public void setUnreadQty(long unreadQty) {
		this.unreadQty = unreadQty;
	}

}
