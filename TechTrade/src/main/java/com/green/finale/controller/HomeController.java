package com.green.finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.finale.entity.Post;
import com.green.finale.service.LocationService;
import com.green.finale.service.PostService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;

	@Autowired
	private PostService postService;
	
	@Autowired
	private LocationService locaService;
	
	

	@GetMapping
	public String index(Model model, @RequestParam(name = "category", defaultValue = "") int category,
			@RequestParam(name = "ward", defaultValue = "") String ward) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authenticationTrustResolver.isAnonymous(authentication)) {
			List<Post> post = postService.getPostList();
			model.addAttribute("post", post);
		} 
		else {
			List<Post> postWard = postService.getPostListByWard(ward);
			
		}

		return "home";
	}

	@GetMapping(value = "/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authenticationTrustResolver.isAnonymous(authentication)) {

			return "login";
		}

		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}

		return "redirect:/login?logout=true";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String accessDeniedPage() {
		return "denied";
	}
}
