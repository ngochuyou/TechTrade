package com.green.finale.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.green.finale.entity.Account;
import com.green.finale.entity.Category;
import com.green.finale.entity.Pin;
import com.green.finale.entity.PostReport;
import com.green.finale.entity.Vote;

public class PostModel {
	private long id;
	private String name;
	private String description;
	private Date createAt;
	private Account createBy;
	private Category category;
	private boolean status;
	private boolean deleted;
	private String tags;
	private int categoryId;
	private MultipartFile[] file;
	private int upVote;
	private String deletedImages;
	private String username;
	private Vote vote;
	private Pin pin;
	private PostReport report;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Account getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Account createBy) {
		this.createBy = createBy;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public MultipartFile[] getFile() {
		return file;
	}

	public void setFile(MultipartFile[] file) {
		this.file = file;
	}

	public int getUpVote() {
		return upVote;
	}

	public void setUpVote(int upVote) {
		this.upVote = upVote;
	}

	public String getDeletedImages() {
		return deletedImages;
	}

	public void setDeletedImages(String deletedImages) {
		this.deletedImages = deletedImages;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public Pin getPin() {
		return pin;
	}

	public void setPin(Pin pin) {
		this.pin = pin;
	}

	public PostReport getReport() {
		return report;
	}

	public void setReport(PostReport report) {
		this.report = report;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
