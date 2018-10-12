package com.green.finale.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pin")
public class Pin {

	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="account_id")
	private Account account;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="post_id")
	private Post post;
	
	@Column(name="create_at", nullable=false)
	private Date createAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
}
