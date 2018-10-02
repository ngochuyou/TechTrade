package com.green.finale.service;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.finale.dao.AccountDAO;
import com.green.finale.entity.Account;
import com.green.finale.utils.AccountRole;

@Service
public class AccountService {
	
	@Autowired
	private AccountDAO accDao;
	
	@Transactional
	public String createAccount(Account acc) {
		Calendar cal = Calendar.getInstance();
		Date dateAT = cal.getTime();
		
		acc.setCreateAt(dateAT);
		acc.setRole(AccountRole.User);
		
		return accDao.insert(acc);
	}
	


}
