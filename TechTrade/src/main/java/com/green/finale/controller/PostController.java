package com.green.finale.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.service.CategoryService;
import com.green.finale.service.PostService;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CategoryService cateService;
	
	@GetMapping(value = "/search")
	public @ResponseBody List<Object[]> search(@RequestParam(name = "keyword", defaultValue = "") String keyword) {

		return postService.search(keyword);
	}
	
	@GetMapping(value = "/{postId}")
	public String viewPost(@PathVariable(name = "postId") long id, Model model) {
		model.addAttribute("post", postService.find(id));
		model.addAttribute("comments", postService.getCommentsByPost(id));
		model.addAttribute("images", postService.getImageListByPost(id));
		model.addAttribute("cateList", cateService.getCategoryList());
		
		return "viewPost";
	}

	@GetMapping(value = "/images/{filename}")
	public @ResponseBody byte[] getPostImages(@PathVariable(name = "filename") String filename) {

		return postService.getImageBytes(filename + ".jpg");
	}

	@GetMapping(value = "/delete/{postId}")
	public String delete(@PathVariable(name = "postId") long postId, Principal principal, Model model) {
		String result = postService.deletePost(postId, principal);

		if (result != null) {
			model.addAttribute("error", result);

			return "error";
		}

		return "redirect:/";
	}

	@GetMapping(value = "/{postId}/status")
	public String restoreOrclose(@PathVariable(name = "postId", required = false) long postId,
			@RequestParam(name = "s", defaultValue = "") String status,
			Principal principal, Model model) {
		String result = postService.changePostStatus(postId, status, principal);

		if (result != null) {
			model.addAttribute("error", result);
			
			return "error";
		}
		
		return "redirect:/post/" + postId;
	}
}