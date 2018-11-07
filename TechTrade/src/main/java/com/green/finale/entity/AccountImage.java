package com.green.finale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="account_image")
public class AccountImage {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="filename", nullable = false)
	private String filename;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name="account_id", nullable = true)
	private Account account;

	public long getId() {
		return id;
	}

	public String getFilename() {
		return filename;
	}

	public Account getAccount() {
		return account;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
