package com.green.finale.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.green.finale.dao.BrandDAO;
import com.green.finale.dao.CategoryDAO;
import com.green.finale.entity.Brand;
import com.green.finale.entity.Category;
import com.green.finale.model.BrandModel;
import com.green.finale.model.CategoryModel;
import com.green.finale.utils.Messages;

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
	public List<Category> getInUseCategoryList() {

		return cateDao.getInUseList();
	}
	
	@Transactional
	public List<Category> getUnUsedCategoryList() {

		return cateDao.getUnUsedList();
	}

	@Transactional
	public String createCategory(CategoryModel cateModel) {
		Category newCate = cateModel.extractCategory();

		if (validateCategory(newCate)) {
			int newCateId = newCate.getId();

			if (searchCategory(newCateId) != null) {
				return Messages.DUPLICATED_ID + newCateId;
			} else {
				cateDao.insert(newCate);

				return null;
			}
		}

		return Messages.EMPTY_FIELDS;
	}

	@Transactional
	public Category searchCategory(int categoryId) {

		return cateDao.find(categoryId);
	}

	@Transactional
	public String deleteCategory(CategoryModel cateModel) {
		Category cate = searchCategory(cateModel.getId());

		if (cate == null) {
			return Messages.NONEXSIT;
		} else {
			cate.setInUse(false);
			cateDao.update(cate);
		}

		return null;
	}

	@Transactional
	public String restoreCategory(String cateId) {
		try {
			int parsedId = categoryIdToint(cateId);
			Category cate = searchCategory(parsedId);
			
			if (cate == null) {
				return Messages.NONEXSIT;
			} else {
				cate.setInUse(true);
				cateDao.update(cate);
			}
		} catch (NullPointerException ex) {
			return Messages.NONEXSIT;
		}
		
		return null;
	}
	
	@Transactional
	public String updateCategory(CategoryModel cateModel) {
		Category newCate = cateModel.extractCategory();

		if (validateCategory(newCate) && !StringUtils.isEmpty(newCate.getId())) {
			if (searchCategory(newCate.getId()) == null) {
				return Messages.NONEXSIT;
			}

			cateDao.update(newCate);

			return null;
		}

		return Messages.EMPTY_FIELDS;
	}

	public boolean validateCategory(Category cate) {
		if (StringUtils.isEmpty(cate.getName())) {
			return false;
		} else {
			return true;
		}

	}
	
	public Integer categoryIdToint(String cateId) {
		int parsedId;
		
		try {
			parsedId = Integer.parseInt(cateId);
		} catch (NumberFormatException ex) {
			return null;
		}
		
		return parsedId;
	}
	
	// About Brand
	@Transactional
	public List<Brand> getBrandList() {

		return brandDao.getList();
	}

	@Transactional
	public String createBrand(BrandModel brandModel) {
		Brand newBrand = brandModel.extractBrand();

		newBrand.setCategory(searchCategory(brandModel.getCategoryId()));

		return brandDao.insert(newBrand);
	}
}
