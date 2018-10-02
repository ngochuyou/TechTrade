package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.finale.entity.Account;
import com.green.finale.model.RegistryAccountModel;
import com.green.finale.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createTodoList(Model model) {
		RegistryAccountModel regisAcc = new RegistryAccountModel();
		model.addAttribute("regisAcc", regisAcc);
		return "registry";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String handleCreateTodoList(@ModelAttribute("regisAcc") RegistryAccountModel regisAcc,
			BindingResult result, Model model) {
		
		Account acc = regisAcc.toRegistryAccount();
		
		accService.createAccount(acc);
		

		// back to contact list page
		return "redirect:/location";
	}

}
