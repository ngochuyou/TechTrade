package com.green.finale.model;

import com.green.finale.entity.Brand;

public class BrandModel {

	private int id;
	private String name;
	private int categoryId;

	public Brand extractBrand() {
		Brand br = new Brand();

		br.setId(this.getId());
		br.setName(this.getName());

		return br;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
