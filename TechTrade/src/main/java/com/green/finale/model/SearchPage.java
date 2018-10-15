package com.green.finale.model;

import org.springframework.util.StringUtils;

public class SearchPage {
	private int pageNumber;
	private String sortBy;
	private String keyword;
	private int categoryId;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String toParameters() {
		StringBuilder sb = new StringBuilder("");

		if (this.categoryId != 0) {
			sb.append("category=" + this.categoryId);
		}

		if (!StringUtils.isEmpty(this.keyword)) {
			sb.append("&k=" + this.keyword);
		}

		return sb.toString();
	}

}
