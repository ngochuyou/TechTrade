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
@Table(name = "history")
public class History {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "buyer_id", nullable = false)
	private Account buyer;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(name = "qty", nullable = false)
	private int qty;

	@Column(name = "total", nullable = false)
	private long total;

	@Column(name = "comment", nullable = true)
	private String comment;

	@Column(name = "votes", nullable = true)
	private int votes;

	@Column(name = "complete_at", columnDefinition = "DATETIME", nullable = false)
	private Date completeAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getBuyer() {
		return buyer;
	}

	public void setBuyer(Account buyer) {
		this.buyer = buyer;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public Date getCompleteAt() {
		return completeAt;
	}

	public void setCompleteAt(Date completeAt) {
		this.completeAt = completeAt;
	}

}
