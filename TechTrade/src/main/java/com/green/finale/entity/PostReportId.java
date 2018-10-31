package com.green.finale.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PostReportId implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "reporter")
	private Account accuser;

	@ManyToOne
	@JoinColumn(name = "post")
	private Post targetedPost;

	public PostReportId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PostReportId(Account accuser, Post targetedPost) {
		super();
		this.accuser = accuser;
		this.targetedPost = targetedPost;
	}

	public Account getAccuser() {
		return accuser;
	}

	public void setAccuser(Account accuser) {
		this.accuser = accuser;
	}

	public Post getTargetedPost() {
		return targetedPost;
	}

	public void setTargetedPost(Post targetedPost) {
		this.targetedPost = targetedPost;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof VoteId))
			return false;
		
		PostReportId that = (PostReportId) o;
		
		return Objects.equals(getAccuser(), that.getAccuser()) && Objects.equals(getTargetedPost(), that.getTargetedPost());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAccuser(), getTargetedPost());
	}
}
