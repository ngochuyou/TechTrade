package com.green.finale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.finale.dao.CityDAO;
import com.green.finale.dao.DistrictDAO;
import com.green.finale.dao.WardDAO;
import com.green.finale.entity.City;
import com.green.finale.entity.District;
import com.green.finale.entity.Ward;

@Service
public class LocationService {
	@Autowired
	private CityDAO cityDao;
	
	@Autowired
	private DistrictDAO districtDao;
	
	@Autowired
	private WardDAO wardDao;
	
	//About Location
	
	
	
	
	//About City
	public String createCity(City city) {
		
		return cityDao.insert(city);
	}
	
	public void updateCity(City city) {
		cityDao.update(city);
	}
	
	public List<City> getCity(){
		return cityDao.getList();
	}
	
	public void deleteCity(City city) {
		cityDao.delete(city);
	}
	
	//About District
	public List<District> getDistrict(){
		return districtDao.getList();
	}
	
	public District getDistrictById(String id) {
		return districtDao.getListById(id);
	}
	
	public District getDistrictByName(String name) {
		return districtDao.getListByName(name);
	}
	
	public String createDistrict(District district) {
		return districtDao.insert(district);
	}
	
	public void updateDistrict(District district) {
		districtDao.update(district);
	}
	
	public void deleteDistrict(District district) {
		districtDao.delete(district);
	}
	
	public void deleteDistrictById(String id) {
		districtDao.deleteById(id);
	}
	
	//About Ward
	public List<Ward> getWard(){
		return wardDao.getList();
	}
	
	public Ward getWardById(String id) {
		return wardDao.getListById(id);
	}
	
	public Ward getWardByName(String name) {
		return wardDao.getListByName(name);
	}
	
	public String createWard(Ward ward) {
		return wardDao.insert(ward);
	}
	
	public void updateWard(Ward ward) {
		wardDao.update(ward);
	}
	
	public void deleteWard(Ward ward) {
		wardDao.delete(ward);
	}
	
	public void deleteWardById(String id) {
		wardDao.deleteById(id);
	}
	
}
