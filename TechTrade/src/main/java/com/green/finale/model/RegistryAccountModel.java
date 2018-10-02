package com.green.finale.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.green.finale.entity.Account;
import com.green.finale.entity.Ward;
import com.green.finale.utils.AccountRole;

public class RegistryAccountModel {
	private String username;
	private String password;
	private String email;
	private AccountRole role;
	private Ward ward;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountRole getRole() {
		return role;
	}

	public void setRole(AccountRole role) {
		this.role = role;
	}

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	public void fromRegistryAccount(Account acc) {
		this.setUsername(acc.getUsername());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		this.setPassword(passwordEncoder.encode(acc.getPassword()));

		this.setEmail(acc.getEmail());
		this.setRole(acc.getRole());
		this.setWard(acc.getWard());
	}

	public Account toRegistryAccount() {
		Account acc = new Account();
		acc.setUsername(this.getUsername());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		acc.setPassword(passwordEncoder.encode(this.getPassword()));

		acc.setEmail(this.getEmail());
		acc.setRole(this.getRole());
		acc.setWard(this.getWard());
		return acc;
	}

}
