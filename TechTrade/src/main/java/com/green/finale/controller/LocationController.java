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

import com.green.finale.entity.District;
import com.green.finale.entity.Ward;
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
			model.addAttribute("districtList", locaService.getDistrictByIdCity(idCity));
		}
		if (idDistrict != null) {
			District district = locaService.getDistrict(idDistrict);
			model.addAttribute("districtList", locaService.getDistrictByIdCity(district.getCity().getId()));
			model.addAttribute("dID", idDistrict);
			model.addAttribute("wardList", locaService.getWardByIdDistrict(idDistrict));
		}

		return "location";
	}
	
	@GetMapping(value = "/district/{cityId}")
	public @ResponseBody List<District> getDistrictList(@PathVariable(name = "cityId") String cityId) {
		
		return locaService.getDistrictByIdCity(cityId);
	}
	
	@GetMapping(value = "/ward/{districtId}")
	public @ResponseBody List<Ward> getWardList(@PathVariable(name = "districtId") String districtId) {
		
		return locaService.getWardByIdDistrict(districtId);
	}
}
