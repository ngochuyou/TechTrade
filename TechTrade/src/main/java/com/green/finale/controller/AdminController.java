package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.finale.model.CityDetailModel;
import com.green.finale.model.CityModel;
import com.green.finale.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/city", method = RequestMethod.GET)
	public String viewCity(Model model) {
		CityDetailModel cityDetail = new CityDetailModel();
		CityModel city = new CityModel();
		
		city.setCityList(adminService.getCityList());
		model.addAttribute("city", city);
		model.addAttribute("cityDetail", cityDetail);
		
		return "admin_city";
	}
	
	@RequestMapping(value = "/city/create", method = RequestMethod.POST)
	public String handleNewCity(@ModelAttribute(name = "cityDetail") CityDetailModel cityDetail, Model model, BindingResult result) {
		if (result.hasErrors()) {
			
			return "error";
		}
		
		adminService.createCity(cityDetail.extractCity());
		
		return "redirect:/admin/city";
	}
}
