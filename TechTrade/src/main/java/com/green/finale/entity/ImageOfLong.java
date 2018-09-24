package com.green.finale.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image_long")
// chua hinh anh thuoc kieu long
public class ImageOfLong {
	@Id
	@GeneratedValue
	@Column(name="id",nullable=false)
	private long id;
	
	@Column(name="location",nullable=false)
	private String location;
	
	@Column(name="key_id",nullable=false)
	private long keyId;

}
