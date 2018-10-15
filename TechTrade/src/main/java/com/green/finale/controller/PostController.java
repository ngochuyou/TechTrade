package com.green.finale.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.service.CategoryService;
import com.green.finale.entity.Post;
import com.green.finale.model.PostModel;
import com.green.finale.service.PostService;
import com.green.finale.utils.Contants;

@ControllerAdvice
@RequestMapping("/post")
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
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
	public String viewPost(@PathVariable(name = "postId") long id, Model model, Principal principal) {
		PostModel postModel = postService.getPostModel(id);

		if (postModel == null) {
			model.addAttribute("error", Contants.POST_NONEXSIT);

			return "error";
		}

		if (postModel.isStatus() == false) {
			if (!principal.getName().equals(postModel.getUsername())) {
				model.addAttribute("error", Contants.POST_CLOSED);

				return "error";
			}
		}

		model.addAttribute("post", postModel);
		model.addAttribute("comments", postService.getCommentsByPost(id));
		model.addAttribute("images", postService.getImageListByPost(id));
		model.addAttribute("cateList", cateService.getCategoryList());

		return "viewPost";
	}

	@PostMapping(value = "/{postId}")
	public String update(@ModelAttribute("post") PostModel postModel, BindingResult result,
			@PathVariable(name = "postId") long postId, Principal principal, Model model) {

		if (result.hasErrors()) {
			return "error";
		}

		String error = postService.updatePost(postModel, principal);

		if (error != null) {
			model.addAttribute("error", error);

			return "error";
		}

		return "redirect:/post/" + postId;
	}

	@GetMapping
	public @ResponseBody List<Post> getPostList(@RequestParam(name = "page") long page) {
		return postService.getNewestList(page);
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
			@RequestParam(name = "s", defaultValue = "") String status, Principal principal, Model model) {
		String result = postService.changePostStatus(postId, status, principal);

		if (result != null) {
			model.addAttribute("error", result);

			return "error";
		}

		return "redirect:/post/" + postId;
	}

	@PostMapping("/comment")
	public @ResponseBody String addComment(@RequestParam(name = "comment") String comment,
			@RequestParam(name = "postId") long postId, Principal principal) {
		String error = postService.addComment(postId, comment, principal);
		
		if (error != null) {
			
			return "error";
		}
		
		return principal.getName();
	}
}
