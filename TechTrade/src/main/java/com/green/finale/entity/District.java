package com.green.finale.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "district")
public class District {
	@Id
	@Column(name="id", nullable = false)
	private String id;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="city_id")
	private City city;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
}
