package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.model.AccountModel;
import com.green.finale.service.AccountService;
import com.green.finale.service.EmailService;
import com.green.finale.service.LocationService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService accService;

	@Autowired
	private LocationService locaService;

	@Autowired
	private EmailService mailService;
	
	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	public String createTodoList(Model model) {
		AccountModel regisAcc = new AccountModel();

		model.addAttribute("regisAcc", regisAcc);
		model.addAttribute("cityList", locaService.getCityList());
		model.addAttribute("districtList", locaService.getDistrictByIdCity("01"));
		model.addAttribute("wardList", locaService.getWardByIdDistrict("001"));

		return "registry";
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public String handleCreateTodoList(@ModelAttribute("regisAcc") AccountModel regisAcc, BindingResult result,
			Model model) {
		String resultStr = accService.createAccount(regisAcc);
		if (resultStr == null) {
			return "redirect:/location";
		} else {
			model.addAttribute("error", resultStr);
			return "error";

		}
	}

	@GetMapping(value = "/email")
	public @ResponseBody String emailCheck(@RequestParam(name = "email", defaultValue="") String email) {
		if (accService.findAccountByEmail(email)) {
			return null;
		} else {
			return "Email already exsits";
		}
	}

	@GetMapping(value = "/username")
	public @ResponseBody String usernameCheck(@RequestParam(name = "username",defaultValue="") String username) {
		if (accService.find(username)) {
			return null;
		} else {
			return "Username already exsits";
		}
	}

	@GetMapping(value = "/verify")
	public @ResponseBody int sendEmail(@RequestParam(name = "email") String email) {
		
		return mailService.sendVerifyCode(email);
	}
}
