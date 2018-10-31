package com.green.finale.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.model.ReportModel;
import com.green.finale.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping(value = "/report/post")
	public @ResponseBody String reportPost(@RequestBody ReportModel model, Principal principal) {

		return adminService.reportPost(model, principal.getName());
	}
	
	@PostMapping(value = "/report/user")
	public @ResponseBody String reportUser(@RequestBody ReportModel model, Principal principal) {

		return adminService.reportUser(model, principal.getName());
	}
}
