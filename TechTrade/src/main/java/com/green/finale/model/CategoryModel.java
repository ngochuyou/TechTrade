package com.green.finale.model;

import com.green.finale.entity.Category;

public class CategoryModel {
	private int id;
	private String name;

	public void injectCategory(Category cate) {
		this.setId(cate.getId());
		this.setName(cate.getName());
	}

	public Category extractCategory() {
		Category cate = new Category();

		cate.setId(this.getId());
		cate.setName(this.getName());

		return cate;
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

}
