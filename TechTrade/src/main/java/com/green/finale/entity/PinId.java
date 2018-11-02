package com.green.finale.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PinId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "account_id")
	private String accountId;

	@Column(name = "post_id")
	private long postId;

	public PinId(String accountId, long postId) {
		this.accountId = accountId;
		this.postId = postId;
	}

	public PinId() {
	}

	public String getAccountId() {
		return accountId;
	}

	public long getPostId() {
		return postId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAccountId(), getPostId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PinId))
			return false;
		PinId that = (PinId) obj;
		return Objects.equals(getAccountId(), that.getAccountId()) && Objects.equals(getPostId(), that.getPostId());

	}

}
