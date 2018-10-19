package com.green.finale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message_attach")
public class MessageAttach {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message messageId;

	@Column(name = "filename", nullable = false)
	private String filename;

	public Message getMessageId() {
		return messageId;
	}

	public void setMessageId(Message messageId) {
		this.messageId = messageId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
