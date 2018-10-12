package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.finale.model.SearchPage;
import com.green.finale.service.CategoryService;
import com.green.finale.service.PostService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	private PostService postService;

	@Autowired
	private CategoryService cateService;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("cateList", cateService.getCategoryList());
		model.addAttribute("postList", postService.getNewestList(0));

		return "home";
	}

	@GetMapping(value = "/search")
	public String search(@RequestParam(name = "category", defaultValue = "0") String categoryId,
			@RequestParam(name = "k", defaultValue = "") String keyword,
			@RequestParam(name = "s", defaultValue = "createAt:desc") String sortBy,
			@RequestParam(name = "p", defaultValue = "0") int page, Model model) throws Exception {
		model.addAttribute("cateList", cateService.getCategoryList());
		
		try {
			model.addAttribute("postList", postService.search(categoryId, keyword, sortBy, page));
		} catch (NumberFormatException ex) {
			model.addAttribute("error", ex.getMessage());
			
			return "error";
		}
		
		SearchPage pageModel = new SearchPage();
		
		try {
			pageModel.setCategoryId(Integer.parseInt(categoryId));
		} catch (NumberFormatException ex) {
			pageModel.setCategoryId(0);
		}
		
		pageModel.setKeyword(keyword);
		pageModel.setPageNumber(page);
		pageModel.setSortBy(sortBy);
		model.addAttribute("page", pageModel);
		
		return "search";
	}
	
	@GetMapping(value = "/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authenticationTrustResolver.isAnonymous(authentication)) {

			return "login";
		}

		return "redirect:/";
	}

	@GetMapping(value = "/logout")
	public String logoutPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}

		return "redirect:/";
	}

	@GetMapping(value = "/denied")
	public String accessDeniedPage() {
		return "denied";
	}
}
