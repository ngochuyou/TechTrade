/**
 * 
 */
package com.green.finale.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.green.finale.entity.Account;
import com.green.finale.entity.Ward;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Gender;

/**
 * @author duypham
 *
 */
public class AccountModel {
	private String username;
	private String password;
	private String fullname;
	private Gender gender;
	private String avatar;
	private String phone;
	private String email;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;
	private long spentMoney;
	private int prestigePoints;
	private String id;
	private AccountRole role;
	private Ward ward;
	private String address;

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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public long getSpentMoney() {
		return spentMoney;
	}

	public void setSpentMoney(long spentMoney) {
		this.spentMoney = spentMoney;
	}

	public int getPrestigePoints() {
		return prestigePoints;
	}

	public void setPrestigePoints(int prestigePoints) {
		this.prestigePoints = prestigePoints;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void fromAccount(Account account) {
		this.setUsername(account.getUsername());
		this.setPassword(account.getPassword());
		this.setFullname(account.getFullname());
		this.setGender(account.getGender());
		this.setAvatar(account.getAvatar());
		this.setPhone(account.getPhone());
		this.setEmail(account.getEmail());
		this.setCreateAt(account.getCreateAt());
		this.setSpentMoney(account.getSpentMoney());
		this.setPrestigePoints(account.getPrestigePoints());
		this.setId(account.getId());
		this.setRole(account.getRole());
		this.setWard(account.getWard());
		this.setAddress(account.getAddress());
	}

	public Account toAccount() {
		Account account = new Account();
		account.setUsername(this.getUsername());
		account.setPassword(this.getPassword());
		account.setFullname(this.getFullname());
		account.setGender(this.getGender());
		account.setAvatar(this.getAvatar());
		account.setPhone(this.getPhone());
		account.setEmail(this.getEmail());
		account.setCreateAt(this.getCreateAt());
		account.setSpentMoney(this.getSpentMoney());
		account.setPrestigePoints(this.getPrestigePoints());
		account.setId(this.getId());
		account.setRole(this.getRole());
		account.setWard(this.getWard());
		account.setAddress(this.getAddress());
		return account;
	}

}
