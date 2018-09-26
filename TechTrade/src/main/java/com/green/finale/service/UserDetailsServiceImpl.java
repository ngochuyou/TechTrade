package com.green.finale.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountDao.find(username);
		if (account == null) {
			throw new UsernameNotFoundException(username + " not found!");
		}

		// TODO: get user permission here
		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ADMIN"));
//		authorities.add(new SimpleGrantedAuthority("CONTACT-MANAGER"));

		UserDetails user = new User(account.getUsername(), account.getPassword(), authorities);
		return user;
	}

}
