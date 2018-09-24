package com.green.finale.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.green.finale.utils.PostStatus;
import com.green.finale.utils.ProductCondition;

@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private ProductCondition state;

	@Column(name = "description", columnDefinition = "TEXT", nullable = false)
	private String description;

	@Column(name = "price", nullable = false)
	private long price;

	@Column(name = "qty", nullable = false)
	private int qty;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@Column(name = "create_at", columnDefinition = "DATE", nullable = false)
	private Date createAt;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", nullable = false)
	private Account createBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private PostStatus status;

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

	public ProductCondition getState() {
		return state;
	}

	public void setState(ProductCondition state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
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

	public PostStatus getStatus() {
		return status;
	}

	public void setStatus(PostStatus status) {
		this.status = status;
	}

}
