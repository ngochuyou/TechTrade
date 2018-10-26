package com.green.finale.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.entity.Account;
import com.green.finale.model.AccountModel;
import com.green.finale.model.PostModel;
import com.green.finale.service.AccountService;
import com.green.finale.service.EmailService;
import com.green.finale.service.LocationService;
import com.green.finale.service.PostService;
import com.green.finale.utils.Contants;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService accService;

	@Autowired
	private LocationService locaService;

	@Autowired
	private EmailService mailService;

	@Autowired
	private PostService postService;
	
	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;

	@GetMapping(value = "/{username}")
	public String wall(@PathVariable(name = "username") String username,
			@RequestParam(name = "s", defaultValue = "createAt:desc") String sortBy,
			@RequestParam(name = "p", defaultValue = "0") int page, Model model, Principal principal) {
		AccountModel acc = accService.findModel(username);

		if (acc == null) {
			model.addAttribute("error", Contants.NONEXSIT);

			return "error";
		}

		model.addAttribute("account", acc);
		model.addAttribute("postList", postService.getPostListByAccount(username, page, sortBy, principal));
		model.addAttribute("sortBy", sortBy);
		
		if (principal != null) {
			model.addAttribute("inbox", accService.getInboxModel(principal.getName()));
		}

		return "wall";
	}
	@GetMapping(value="/update/{username}")
	public String update(@PathVariable(name = "username") String username,Model model, Principal principal) {
		model.addAttribute("cityList", locaService.getCityList());
		model.addAttribute("districtList", locaService.getDistrictByIdCity("01"));
		model.addAttribute("wardList", locaService.getWardByIdDistrict("001"));
		return "updateAccount";
	}

	@GetMapping(value = "/api/{username}")
	public @ResponseBody List<PostModel> wallAPI(@PathVariable(name = "username") String username,
			@RequestParam(name = "s", defaultValue = "createAt:desc") String sortBy,
			@RequestParam(name = "p", defaultValue = "0") int page, Principal principal) {
		
		return postService.getPostListByAccount(username, page, sortBy, principal);
	}

	@GetMapping(value = "/sign-up")
	public String createTodoList(Model model) {
		if (!authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {

			return "redirect:/";
		}

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
	public @ResponseBody String emailCheck(@RequestParam(name = "email", defaultValue = "") String email) {
		if (accService.findAccountByEmail(email) == null) {
			return null;
		} else {
			return "Email already exists";
		}
	}

	@GetMapping(value = "/username")
	public @ResponseBody String usernameCheck(@RequestParam(name = "username", defaultValue = "") String username) {
		if (accService.find(username) == null) {
			return null;
		} else {
			return "Username already exists";
		}
	}

	@GetMapping(value = "/phone")
	public @ResponseBody String phoneCheck(@RequestParam(name = "phone", defaultValue = "") String phone) {
		if (accService.findAccountByPhone(phone) == null) {
			return null;
		}
		return "Phone already exists";
	}

	@GetMapping(value = "/verify")
	public @ResponseBody int sendEmail(@RequestParam(name = "email", defaultValue = "") String email) {

		return mailService.sendVerifyCode(email, "Hi! Your account's verify code is ",
				"TechTrade - Verify your account");
	}

	@GetMapping(value = "/avatar/{filename}")
	public @ResponseBody byte[] getUserAvatarByFilename(@PathVariable(name = "filename") String filename) {

		return accService.getUserAvaByFilename(filename);
	}

	@GetMapping(value = "/avatar")
	public @ResponseBody byte[] getUserAvatar(@RequestParam(name = "username", defaultValue = "") String username) {

		return accService.getUserAva(username);
	}

	@GetMapping(value = "/keycheck")
	public @ResponseBody Account keyCheck(@RequestParam(name = "key") String key) {

		try {
			return accService.keycheck(key);
		} catch (Exception ex) {
			return null;
		}
	}

	@GetMapping(value = "/password/forgot")
	public String resetPassword(@RequestParam(name = "username", defaultValue = "") String username, Model model) {
		Account acc = accService.find(username);

		if (acc != null) {
			AccountModel accModel = new AccountModel();

			accModel.setUsername(username);
			accModel.setEmail(acc.getEmail());
			model.addAttribute("accModel", accModel);
			model.addAttribute("code", mailService.sendVerifyCode(accModel.getEmail(), "Hi! Here is your reset code ",
					"TechTrade - Reset your password"));

			return "reset_password";
		}

		model.addAttribute("error", Contants.NONEXSIT);

		return "error";
	}

	@PostMapping(value = "/password/forgot")
	public String handleResetPassword(@ModelAttribute(name = "accModel") AccountModel accModel, BindingResult result,
			Model model) {
		String message = accService.resetPassword(accModel);

		if (message != null) {
			model.addAttribute("error", message);

			return "error";
		}

		return "/login";
	}
	
}
