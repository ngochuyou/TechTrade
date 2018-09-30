/**
 * 
 */
package com.green.finale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.finale.entity.District;
import com.green.finale.service.LocationService;

/**
 * @author duypham
 *
 */
@Controller
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private LocationService locaService;

	@GetMapping
	public String getAllList(@RequestParam(name = "idCity", required = false) String idCity,
			@RequestParam(name = "idDistrict", required = false) String idDistrict, Model model) {
		model.addAttribute("cityList", locaService.getCityList());

		if (idCity != null) {
			System.out.println("anhduy");
			model.addAttribute("districtList", locaService.getDistrictByIdCity(idCity));
		}
		if (idDistrict != null) {
			District district = locaService.getDistrict(idDistrict);
			model.addAttribute("districtList", locaService.getDistrictByIdCity(district.getCity().getId()));
			model.addAttribute("dID",idDistrict);
			model.addAttribute("wardList", locaService.getWardByIdDistrict(idDistrict));
		}

		return "location";
	}
}
