package com.green.finale.service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.green.finale.dao.AccountDAO;
import com.green.finale.entity.Account;
import com.green.finale.model.AccountModel;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Messages;

@Service
public class AccountService {

	@Autowired
	private AccountDAO accDao;

	@Transactional
	public boolean findAccountByEmail(String email) {
		if (accDao.findByEmail(email) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean find(String username) {
		if (accDao.find(username) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public String createAccount(AccountModel acc) {
		if (validateRegistryAccount(acc)) {
			Account account = new Account();
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			account.setEmail(acc.getEmail());
			account.setUsername(acc.getUsername());
			account.setPassword(passwordEncoder.encode(acc.getPassword()));
			account.setGender(acc.getGender());
			account.setAvatar("default.JPG");
			account.setPhone(acc.getPhone());
			account.setCreateAt(new Date());
			account.setSpentMoney(0);
			account.setPrestigePoints(0);
			account.setRole(AccountRole.User);

			accDao.insert(account);

		} else {
			return Messages.INVALID_FIELDS;
		}

		return null;
	}

	public boolean validateRegistryAccount(AccountModel acc) {
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;

		if (StringUtils.isEmpty(acc.getEmail())) {
			return false;
		} else {
			matcher = p.matcher(acc.getEmail());
			if (!matcher.find()) {
				return false;
			}
		}
		if (StringUtils.isEmpty(acc.getUsername()) && acc.getUsername().length() < 8) {
			return false;
		}
		if (StringUtils.isEmpty(acc.getPassword()) && acc.getPassword().length() < 8) {
			return false;
		}
		if (StringUtils.isEmpty(acc.getPhone())) {
			return false;
		} else {
			p = Pattern.compile("\\D+");
			matcher = p.matcher(acc.getPhone());
			if (matcher.find()) {
				return false;
			}
		}
		if (StringUtils.isEmpty(acc.getGender())) {
			return false;
		}
		if (StringUtils.isEmpty(acc.getWardId())) {
			return false;
		}

		return true;

	}

}
