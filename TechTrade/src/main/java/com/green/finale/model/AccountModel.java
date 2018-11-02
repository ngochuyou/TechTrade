package com.green.finale.model;

import com.green.finale.entity.UserReport;
import com.green.finale.entity.Ward;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Gender;

public class AccountModel {
	private String username;
	private String password;
	private String email;
	private String phone;
	private String avatar;
	private Gender gender;
	private AccountRole role;
	private String wardId;
	private String newPassword;
	private long spentMoney;
	private int prestigePoints;
	private String wallpaper;
	private Ward ward;
	private UserReport report;

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

	public String getWardId() {
		return wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	public String getWallpaper() {
		return wallpaper;
	}

	public void setWallpaper(String wallpaper) {
		this.wallpaper = wallpaper;
	}

	public Ward getWard() {
		return ward;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	public UserReport getReport() {
		return report;
	}

	public void setReport(UserReport report) {
		this.report = report;
	}

}
