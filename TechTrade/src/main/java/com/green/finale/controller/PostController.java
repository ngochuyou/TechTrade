package com.green.finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.entity.Post;
import com.green.finale.service.PostService;

@Controller
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping(value = "/search")
	public @ResponseBody List<Object[]> search(@RequestParam(name = "keyword") String keyword) {

		return postService.search(keyword);
	}

	@GetMapping(value = "/{postId}")
	public String viewPost(@PathVariable(name = "postId") long id, Model model) {
		model.addAttribute("post", postService.find(id));
		model.addAttribute("comments", postService.getCommentsByPost(id));
		model.addAttribute("images", postService.getImageListByPost(id));

		return "viewPost";
	}

	@GetMapping
	public @ResponseBody List<Post> getPostList(@RequestParam(name = "page") long page) {
		return postService.getNewestList(page);
	}

	@GetMapping(value = "/images/{filename}")
	public @ResponseBody byte[] getPostImages(@PathVariable(name = "filename") String filename) {

		return postService.getImageBytes(filename + ".jpg");
	}
}
