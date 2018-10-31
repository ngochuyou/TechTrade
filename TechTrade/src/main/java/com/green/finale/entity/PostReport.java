package com.green.finale.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "report")
public class PostReport {

	@EmbeddedId
	private PostReportId id;

	@Column(name = "content")
	private String content;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	public PostReportId getId() {
		return id;
	}

	public void setId(PostReportId id) {
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
