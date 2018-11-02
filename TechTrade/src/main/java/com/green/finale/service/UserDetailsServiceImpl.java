package com.green.finale.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.green.finale.dao.AccountDAO;
import com.green.finale.entity.Account;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AccountDAO accountDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String key) throws UsernameNotFoundException {
		Account account = accountDao.find(key);

		if (account == null) {
			account = accountDao.findByEmail(key);

			if (account == null) {
				throw new UsernameNotFoundException(key + " not found!");
			}
		}

		// TODO: get user permission here
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(account.getRole().toString()));

		UserDetails user = new User(account.getUsername(), account.getPassword(), authorities);

		return user;
	}

}
