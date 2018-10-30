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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.entity.Account;
import com.green.finale.model.AccountModel;
import com.green.finale.model.MessageModel;
import com.green.finale.model.PostModel;
import com.green.finale.service.AccountService;
import com.green.finale.service.CategoryService;
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
	private CategoryService cateService;

	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;

	@GetMapping(value = "/wall/{username}")
	public String wall(@PathVariable(name = "username") String username,
			@RequestParam(name = "s", defaultValue = "createAt:desc") String sortBy,
			@RequestParam(name = "p", defaultValue = "0") int page, Model model, Principal principal) {
		AccountModel acc = accService.findModel(username);

		if (acc == null) {
			model.addAttribute("error", Contants.NONEXSIT);

			return "error";
		}
		
		model.addAttribute("cateList", cateService.getCategoryList());
		model.addAttribute("account", acc);
		model.addAttribute("postList", postService.getPostListByAccount(username, page, sortBy, principal));
		model.addAttribute("sortBy", sortBy);

		if (principal != null) {
			model.addAttribute("inbox", accService.getInboxModel(principal.getName(), page));
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

	@GetMapping(value = "/api/wall/{username}")
	public @ResponseBody List<PostModel> wallAPI(@PathVariable(name = "username") String username,
			@RequestParam(name = "s", defaultValue = "createAt:desc") String sortBy,
			@RequestParam(name = "p", defaultValue = "0") int page, Principal principal) {

		return postService.getPostListByAccount(username, page, sortBy, principal);
	}

	@GetMapping(value = "/sign-up")
	public String createTodoList(Model model) {
		if (!authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {

			return "redirect:/login";
		}

		AccountModel regisAcc = new AccountModel();

		model.addAttribute("regisAcc", regisAcc);
		model.addAttribute("cityList", locaService.getCityList());
		model.addAttribute("districtList", locaService.getDistrictByIdCity("01"));
		model.addAttribute("wardList", locaService.getWardByIdDistrict("001"));

		return "registry";
	}

	@PostMapping(value = "/sign-up")
	public String handleCreateTodoList(@ModelAttribute("regisAcc") AccountModel regisAcc, BindingResult result,
			Model model) {
		String resultStr = accService.createAccount(regisAcc);

		if (resultStr == null) {
			return "redirect:/";
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

		return accService.keycheck(key);
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

	@GetMapping(value = "/message")
	public @ResponseBody List<MessageModel> getMessages(@RequestParam(name = "page", defaultValue = "0") int page,
			Principal principal) {

		return accService.getReceivedMessage(principal.getName(), page);
	}

	@GetMapping(value = "/message/inbox")
	public @ResponseBody List<Object[]> getNewInbox(Principal principal) {

		return accService.getNewMessages(principal.getName());
	}

	@GetMapping(value = "/message/outbox")
	public @ResponseBody List<Object[]> getOutbox(@RequestParam(name = "page", defaultValue = "0") int page,
			Principal principal) {

		return accService.getSentMessage(principal.getName(), page);
	}

	@PostMapping(value = "/message/send")
	public @ResponseBody String sendMessage(@RequestParam(name = "receiver", defaultValue = "") String receiverId,
			@RequestParam(name = "content") String content, Principal principal) {

		return accService.createMessage(principal.getName(), receiverId, content);
	}

	@GetMapping(value = "/message/remove")
	public @ResponseBody String deleteMessage(@RequestParam(name = "messageId", defaultValue = "") long messId,
			Principal principal) {

		return accService.deleteMessage(principal.getName(), messId);
	}

	@GetMapping(value = "/message/mark")
	public @ResponseBody String markMessage(@RequestParam(name = "messageId", defaultValue = "") long messId,
			Principal principal) {

		return accService.markMessage(principal.getName(), messId);
	}
}
