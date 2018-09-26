package com.green.finale.model;

import java.util.List;

import com.green.finale.entity.Brand;
import com.green.finale.entity.Category;

public class CategoryModel {

	private List<Category> cateList;
	private List<Brand> brandList;

	public List<Category> getCateList() {
		return cateList;
	}

	public void setCateList(List<Category> cateList) {
		this.cateList = cateList;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

}
