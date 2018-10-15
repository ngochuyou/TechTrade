package com.green.finale.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "vote")
public class Vote {

	@EmbeddedId
	private VoteId voteId;

	@MapsId("postId")
	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@MapsId("username")
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	private Account account;

	@Column(name = "vote_date", nullable = false)
	private Date voteDate;

	@Column(name = "type", nullable = false)
	private boolean type;

	public Date getVoteDate() {
		return voteDate;
	}

	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public VoteId getVoteId() {
		return voteId;
	}

	public void setVoteId(VoteId voteId) {
		this.voteId = voteId;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	
}
