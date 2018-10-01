package com.green.finale.service;

import java.util.List;

import javax.transaction.Transactional;

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

	// About Location

	// About City
	@Transactional
	public String createCity(City city) {

		return cityDao.insert(city);
	}

	@Transactional
	public void updateCity(City city) {
		cityDao.update(city);
	}

	@Transactional
	public List<City> getCityList() {
		return cityDao.getList();
	}

	@Transactional
	public void deleteCity(City city) {
		cityDao.delete(city);
	}

	// About District
	@Transactional
	public List<District> getDistrictList() {
		return districtDao.getList();
	}

	@Transactional
	public District getDistrict(String id) {
		return districtDao.getDistrict(id);
	}

	@Transactional
	public District getDistrictByName(String name) {
		return districtDao.getDistrictByName(name);
	}

	@Transactional
	public List<District> getDistrictByIdCity(String city) {
		return districtDao.getDistrictByIdCity(city);
	}

	@Transactional
	public String createDistrict(District district) {
		return districtDao.insert(district);
	}

	@Transactional
	public void updateDistrict(District district) {
		districtDao.update(district);
	}

	@Transactional
	public void deleteDistrict(District district) {
		districtDao.delete(district);
	}

	@Transactional
	public void deleteDistrictById(String id) {
		districtDao.deleteById(id);
	}

	// About Ward
	@Transactional
	public List<Ward> getWardList() {
		return wardDao.getList();
	}

	@Transactional
	public Ward getWard(String id) {
		return wardDao.find(id);
	}

	@Transactional
	public Ward getWardByName(String name) {
		return wardDao.getWardByName(name);
	}

	@Transactional
	public String createWard(Ward ward) {
		return wardDao.insert(ward);
	}

	@Transactional
	public void updateWard(Ward ward) {
		wardDao.update(ward);
	}

	@Transactional
	public void deleteWard(Ward ward) {
		wardDao.delete(ward);
	}

	@Transactional
	public void deleteWardById(String id) {
		wardDao.find(id);
	}

	@Transactional
	public List<Ward> getWardByIdDistrict(String district) {
		return wardDao.getWardByIdCity(district);
	}

}
