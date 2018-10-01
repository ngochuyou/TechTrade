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
import com.green.finale.model.CategoryModel;
import com.green.finale.model.CategoryPage;
import com.green.finale.service.CategoryService;
import com.green.finale.service.RandomService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService cateService;

	@Autowired
	private RandomService ranService;
	
	// category control
	@GetMapping
	public String view(Model model) {
		CategoryPage catePage = new CategoryPage();
		CategoryModel cateModel = new CategoryModel();

		catePage.setCateList(cateService.getInUseCategoryList());
		catePage.setUnUsedCateList(cateService.getUnUsedCategoryList());
		
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

	@GetMapping(value = "/delete")
	public String handleDeleteCategory(@RequestParam(name = "id") String cateId, Model model) {
		String resultMessage = cateService.deleteCategory(cateId);

		if (resultMessage != null) {
			model.addAttribute("error", resultMessage);

			return "error";
		}

		return "redirect:/category";
	}

	@GetMapping(value = "/restore")
	public String handleRestoreCategory(@RequestParam(name = "id") String cateId, Model model) {
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
	
	@GetMapping(value = "/test")
	public String random() {
		ranService.addRandomPost();
		
		return "redirect:/category";
	}
}
