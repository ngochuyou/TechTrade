package com.green.finale.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "sender", referencedColumnName = "username", nullable = false)
	private Account sender;

	@ManyToOne
	@JoinColumn(name = "receiver", nullable = false)
	private Account receiver;

	@Column(name = "sent_at", nullable = false)
	private Date sentAt;

	@Column(name = "is_read")
	private boolean read;

	@Column(name = "content", columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(name = "deleted_by_sender", nullable = false)
	private boolean deletedBySender;

	@Column(name = "deleted_by_receiver", nullable = false)
	private boolean deletedByReceiver;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	public Account getReceiver() {
		return receiver;
	}

	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isDeletedBySender() {
		return deletedBySender;
	}

	public void setDeletedBySender(boolean deletedBySender) {
		this.deletedBySender = deletedBySender;
	}

	public boolean isDeletedByReceiver() {
		return deletedByReceiver;
	}

	public void setDeletedByReceiver(boolean deletedByReceiver) {
		this.deletedByReceiver = deletedByReceiver;
	}
}
