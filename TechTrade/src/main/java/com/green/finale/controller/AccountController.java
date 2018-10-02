package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.finale.entity.Account;
import com.green.finale.model.AccountModel;
import com.green.finale.service.AccountService;
import com.green.finale.service.LocationService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService accService;
	
	@Autowired
	private LocationService locaService;
	
	@RequestMapping(value = "/sign-up", method = RequestMethod.GET)
	public String createTodoList(Model model) {
		AccountModel regisAcc = new AccountModel();
		
		model.addAttribute("regisAcc", regisAcc);
		model.addAttribute("cityList", locaService.getCityList());
		model.addAttribute("districtList", locaService.getDistrictByIdCity("01"));
		model.addAttribute("wardList", locaService.getWardByIdDistrict("001"));
		
		return "registry";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String handleCreateTodoList(@ModelAttribute("regisAcc") AccountModel regisAcc,
			BindingResult result, Model model) {
		
		Account acc = null;
		
		accService.createAccount(acc);
		

		// back to contact list page
		return "redirect:/location";
	}

}
