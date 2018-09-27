package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.entity.Category;
import com.green.finale.model.BrandModel;
import com.green.finale.model.CategoryModel;
import com.green.finale.model.CategoryPage;
import com.green.finale.service.CategoryService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService cateService;

	// category control
	@GetMapping
	public String view(Model model) {
		CategoryPage catePage = new CategoryPage();
		CategoryModel cateModel = new CategoryModel();

		catePage.setCateList(cateService.getInUseCategoryList());
		catePage.setUnUsedCateList(cateService.getUnUsedCategoryList());
		catePage.setBrandList(cateService.getBrandList());

		model.addAttribute("cateModel", cateModel);
		model.addAttribute("pageModel", catePage);

		return "category";
	}

	@PostMapping(value = "/create")
	public String handleCreateCategory(@ModelAttribute(name = "cateDetail") CategoryModel cateModel,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "error";
		}

		String resultMessage = cateService.createCategory(cateModel);

		if (resultMessage == null) {
			return "redirect:/category";
		} else {
			model.addAttribute("error", resultMessage);
		}

		return "error";
	}

	@PostMapping(value = "/delete")
	public String handleDeleteCategory(@ModelAttribute(name = "cateModel") CategoryModel cateModel,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "error";
		}
		
		String resultMessage = cateService.deleteCategory(cateModel);
		
		if (resultMessage != null) {
			model.addAttribute("error", resultMessage);
			
			return "error";
		}

		return "redirect:/category";
	}

	@GetMapping(value = "/restore")
	public String restoreCategory(@RequestParam(name = "id") String cateId, Model model) {
		String result = cateService.restoreCategory(cateId);
		
		if (result != null) {
			model.addAttribute("error", result);
			
			return "error";
		}
		
		return "redirect:/category";
	}
	
	@PostMapping(value = "/update")
	public String handleUpdateCategory(@ModelAttribute(name = "cateModel") CategoryModel cateModel,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "error";
		}

		String resultMessage = cateService.updateCategory(cateModel);

		if (resultMessage == null) {
			return "redirect:/category";
		} else {
			model.addAttribute("error", resultMessage);
		}

		return "error";
	}

	@GetMapping(value = "/get")
	public @ResponseBody Category searchCategory(@RequestParam(name = "cateId") String cateId) {
		int parsedId;

		try {
			parsedId = Integer.parseInt(cateId);
		} catch (NumberFormatException ex) {
			return null;
		}

		return cateService.searchCategory(parsedId);
	}

	// brand control
	@PostMapping(value = "/brand/create")
	public String handleCreateBrand(@ModelAttribute(name = "brandModel") BrandModel brandModel, BindingResult result) {
		if (result.hasErrors()) {

			return "error";
		}

		cateService.createBrand(brandModel);

		return "redirect:/category";
	}
//
//	@ExceptionHandler(Exception.class)
//	public String exceptionHandler() {
//
//		return "error";
//	}

}
