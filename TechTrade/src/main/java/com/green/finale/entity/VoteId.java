package com.green.finale.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VoteId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "post_id", nullable = false)
	private long postId;

	public VoteId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoteId(String username, long postId) {
		super();
		this.username = username;
		this.postId = postId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof VoteId))
			return false;
		VoteId that = (VoteId) o;
		return Objects.equals(getPostId(), that.getPostId()) && Objects.equals(getUsername(), that.getUsername());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername(), getPostId());
	}
}
