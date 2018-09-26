package com.green.finale.model;

import com.green.finale.entity.Brand;

public class BrandDetail {

	private String id;
	private String name;
	private String categoryId;
	
	public Brand extractBrand() {
		Brand br = new Brand();
		
		br.setId(this.getId());
		br.setName(this.getName());
		
		return br;
	}
	
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
