package com.green.finale.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.finale.dao.AccountDAO;
import com.green.finale.dao.CityDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.City;

@Service
public class AdminService {
	@Autowired
	private AccountDAO accDao;
	@Autowired
	private CityDAO cityDao;
	
	@Transactional
	public List<Account> getAccountList() {
		
		return accDao.getList();
	}
	
	@Transactional
	public List<City> getCityList() {
		
		return cityDao.getList();
	}
	
	@Transactional
	public String createCity(City city) {
		
		return cityDao.insert(city);
	}
}
