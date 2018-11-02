package com.green.finale.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_report")
public class UserReport {
	@EmbeddedId
	private UserReportId id;

	@Column(name = "content")
	private String content;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	public UserReportId getId() {
		return id;
	}

	public void setId(UserReportId id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
