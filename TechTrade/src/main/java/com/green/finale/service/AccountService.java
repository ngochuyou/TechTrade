package com.green.finale.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.finale.dao.AccountDAO;
import com.green.finale.entity.Account;
import com.green.finale.utils.AccountRole;

@Service
public class AccountService {
	
	@Autowired
	private AccountDAO accDao;
	
	@Transactional
	public String createAccount(Account acc) {
		acc.setCreateAt(new Date());
		acc.setRole(AccountRole.User);
		
		return accDao.insert(acc);
	}
	
}
