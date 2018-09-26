/**
 * 
 */
package com.green.finale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.finale.entity.City;
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

	public String getAllList(Model model) {

		List<City> cityList = locaService.getCityList();
		List<District> districtList = locaService.getDistrictList();
		List<Ward> wardList = locaService.getWardList();

		model.addAttribute("cityList", cityList);
		model.addAttribute("districtList", districtList);
		model.addAttribute("wardList", wardList);

		return "location";
	}
}
