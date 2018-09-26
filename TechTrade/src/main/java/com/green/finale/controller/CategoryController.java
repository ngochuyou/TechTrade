package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.finale.entity.Brand;
import com.green.finale.entity.Category;
import com.green.finale.model.BrandDetail;
import com.green.finale.model.CategoryModel;
import com.green.finale.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService cateService;

	// category control
	@GetMapping
	public String view(Model model) {
		CategoryModel cateModel = new CategoryModel();

		cateModel.setCateList(cateService.getCategoryList());
		cateModel.setBrandList(cateService.getBrandList());

		model.addAttribute("cateDetail", new Category());
		model.addAttribute("cateModel", cateModel);

		return "category";
	}
	
	@PostMapping(value = "/create")
	public String handleCreateCategory(@ModelAttribute(name = "cateDetail") Category newCate, Model model) {		
		String result = cateService.createCategory(newCate);
		
		if (result.equals(newCate.getId())) {
			return "redirect:/category";
		} else {
			model.addAttribute("error", result);
		}
		
		return "error";
	}

	@PostMapping(value = "/delete")
	public String handleDeleteCategory(@RequestParam(name = "cateId") String cateId) {
		System.out.println("in");
		return "redirect:/category";
	}
	
	// brand control
	@PostMapping(value = "/brand/create")
	public String handleCreateBrand(@ModelAttribute(name = "newBrand") BrandDetail newBrand, BindingResult result) {
		if (result.hasErrors()) {

			return "error";
		}
		
		Brand newBr = newBrand.extractBrand();
		
		newBr.setCategory(cateService.search(newBrand.getCategoryId()));
		cateService.createBrand(newBr);
		
		return "redirect:/category";
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public String exceptionHandler() {
		
		return "error";
	}
}
