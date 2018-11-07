package com.green.finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.finale.entity.District;
import com.green.finale.entity.Ward;
import com.green.finale.service.LocationService;

@Controller
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private LocationService locaService;
	
	@GetMapping(value = "/district/{cityId}")
	public @ResponseBody List<District> getDistrictList(@PathVariable(name = "cityId") String cityId) {
		
		return locaService.getDistrictByIdCity(cityId);
	}
	
	@GetMapping(value = "/ward/{districtId}")
	public @ResponseBody List<Ward> getWardList(@PathVariable(name = "districtId") String districtId) {
		
		return locaService.getWardByIdDistrict(districtId);
	}
}
