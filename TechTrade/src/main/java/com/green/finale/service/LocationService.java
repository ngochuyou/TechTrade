package com.green.finale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.finale.dao.CityDAO;
import com.green.finale.entity.City;

@Service
public class LocationService {
	@Autowired
	private CityDAO cityDao;
	
	public String createCity(City city) {
		
		return cityDao.insert(city);
	}
	
	public void updateCity(City city) {
		cityDao.update(city);
	}
	
}
