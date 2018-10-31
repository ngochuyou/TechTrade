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

import com.green.finale.service.AccountService;
import com.green.finale.service.CategoryService;
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

	@Autowired
	private AccountService accService;
	
	@GetMapping(value = "/search")
	public @ResponseBody List<Object[]> search(@RequestParam(name = "keyword", defaultValue = "") String keyword) {

		return postService.search(keyword);
	}

	@GetMapping(value = "/view/{postId}")
	public String viewPost(@PathVariable(name = "postId") long id, Model model, Principal principal) {
		PostModel postModel = postService.getPostModel(id, principal);

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

		if (principal != null) {
			model.addAttribute("inbox", accService.getInboxModel(principal.getName(), 0));
		}
		
		return "viewPost";
	}

	@GetMapping(value = "/upload")
	public String uploadPost(Model model, Principal principal) {
		model.addAttribute("model", postService.getNewPostModel(principal.getName()));
		model.addAttribute("inbox", accService.getInboxModel(principal.getName(), 0));
		model.addAttribute("account", accService.find(principal.getName()));
		model.addAttribute("cateList", cateService.getCategoryList());

		return "uploadPost";
	}

	@PostMapping(value = "/upload")
	public String uploadPost(@ModelAttribute(name = "model") PostModel postModel, BindingResult result, Model model,
			Principal principal) {
		
		String error = postService.createPost(postModel, principal.getName());
		
		if (error.length() != 0) {
			model.addAttribute("error", error);
			
			return "error";
		}
		
		return "redirect:/account/wall/" + principal.getName();
	}

	@PostMapping(value = "/update/{postId}")
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

		return "redirect:/post/view/" + postId;
	}
	
	@GetMapping
	public @ResponseBody List<PostModel> getPostList(@RequestParam(name = "page") long page, Principal principal) {
		
		return postService.getNewestPostModel(principal, page);
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

		return "redirect:/account/wall/" + principal.getName();
	}

	@GetMapping(value = "/status/{postId}")
	public String restoreOrclose(@PathVariable(name = "postId", required = false) long postId,
			@RequestParam(name = "s", defaultValue = "") String status, Principal principal, Model model) {
		String result = postService.changePostStatus(postId, status, principal);

		if (result != null) {
			model.addAttribute("error", result);

			return "error";
		}

		return "redirect:/post/view/" + postId;
	}

	@PostMapping(value = "/comment")
	public @ResponseBody String addComment(@RequestParam(name = "comment") String comment,
			@RequestParam(name = "postId") long postId, Principal principal) {
		String error = postService.addComment(postId, comment, principal);

		if (error != null) {

			return "error";
		}

		return principal.getName();
	}

	@GetMapping(value = "/vote/{postId}")
	public @ResponseBody String vote(@PathVariable(name = "postId") long postId,
			@RequestParam(name = "type", defaultValue = "true") boolean type, Principal principal) {
		String error = postService.votePost(postId, type, principal);

		if (error == null) {

			return Contants.POST_VOTED;
		}

		return error;
	}

	@GetMapping(value = "/hashtags/rate")
	public @ResponseBody double rateHashtags(@RequestParam(name = "keyword") String hashtag) {

		return postService.rateHashtag(hashtag);
	}
	
	@GetMapping(value = "/pin")
	public @ResponseBody String pinPost(@RequestParam(name = "postId") long postId, Principal principal) {
		
		return postService.pinPost(principal.getName(), postId);
	}
	
	@GetMapping(value = "/pinned")
	public String getPinnedPosts(Principal principal, Model model) {
		model.addAttribute("pinnedList", postService.getPinnedPostList(principal));
		model.addAttribute("cateList", cateService.getCategoryList());
		model.addAttribute("inbox", accService.getInboxModel(principal.getName(), 0));
		model.addAttribute("account", accService.find(principal.getName()));
		
		return "pin";
	}
}
