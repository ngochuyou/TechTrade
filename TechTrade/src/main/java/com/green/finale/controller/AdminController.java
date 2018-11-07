package com.green.finale.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.service.AccountService;
import com.green.finale.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AccountService accService;

	@GetMapping(value = "/report/view")
	public String reportView(Model model, Principal principal) {
		model.addAttribute("inbox", accService.getInboxModel(principal.getName(), 0));
		model.addAttribute("account", accService.find(principal.getName()));
		model.addAttribute("postReportList", adminService.getPostReportList(0));
		model.addAttribute("userReportList", adminService.getUserReportList(0));
		
		return "report";
	}

	@GetMapping(value = "/post/delete/{postId}")
	public @ResponseBody String deletePost(@PathVariable(name = "postId") long postId, Principal principal) {

		return adminService.deletePost(postId, principal.getName());
	}

	@PostMapping(value = "/post/permadelete/{postId}")
	public @ResponseBody String permadeletePost(@PathVariable(name = "postId") long postId, Principal principal) {

		return adminService.permaDeletePost(postId, principal.getName());
	}
}
