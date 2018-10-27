package com.green.finale.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "pin")
public class Pin {

	@EmbeddedId
	private PinId pinId;

	@ManyToOne
	@MapsId("accountId")
	private Account account;

	@ManyToOne
	@MapsId("postId")
	private Post post;

	@Column(name = "create_at", nullable = false)
	private Date createAt;

	public PinId getPinId() {
		return pinId;
	}

	public void setPinId(PinId pinId) {
		this.pinId = pinId;
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
