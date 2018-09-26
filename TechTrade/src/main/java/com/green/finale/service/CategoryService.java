package com.green.finale.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.green.finale.dao.BrandDAO;
import com.green.finale.dao.CategoryDAO;
import com.green.finale.entity.Brand;
import com.green.finale.entity.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryDAO cateDao;

	@Autowired
	private BrandDAO brandDao;

	// About Category
	@Transactional
	public List<Category> getCategoryList() {

		return cateDao.getList();
	}

	@Transactional
	public String createCategory(Category newCate) {
		if (validateCategory(newCate)) {
			String newCateId = newCate.getId();
			
			if (search(newCateId) != null) {
				return "Duplicated id: " + newCateId;
			} else {
				return cateDao.insert(newCate);
			}
		}
		
		return "Category Id and Name field are both required";
	}

	@Transactional
	public Category search(String categoryId) {
		Category result = null;

		try {
			result = cateDao.find(categoryId);
		} catch (NoResultException ex) {
			return null;
		}

		return result;
	}

	public boolean validateCategory(Category cate) {
		if (StringUtils.isEmpty(cate.getId()) && StringUtils.isEmpty(cate.getName())) {
			return false;
		} else {
			return true;
		}
		
	}

	// About Brand
	@Transactional
	public List<Brand> getBrandList() {

		return brandDao.getList();
	}

	@Transactional
	public String createBrand(Brand brand) {

		return brandDao.insert(brand);
	}
}
