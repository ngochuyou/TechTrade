package com.green.finale.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.green.finale.dao.AccountDAO;
import com.green.finale.entity.Account;
import com.green.finale.model.AccountModel;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Contants;

@Service
public class AccountService {

	@Autowired
	private AccountDAO accDao;

	@Transactional
	public Account findAccountByEmail(String email) {
		Account acc = accDao.findByEmail(email);

		if (acc == null) {
			return null;
		} else {
			return acc;
		}
	}

	@Transactional
	public Account find(String username) {
		Account acc = accDao.findByEmail(username);

		if (acc == null) {
			return null;
		} else {
			return acc;
		}
	}

	@Transactional
	public Account findAccountByPhone(String phone) {
		Account acc = accDao.findByEmail(phone);

		if (acc == null) {
			return null;
		} else {
			return acc;
		}
	}

	@Transactional
	public String createAccount(AccountModel acc) {
		if (validateRegistryAccount(acc)) {
			if ((accDao.find(acc.getUsername()) == null) && (accDao.findByEmail(acc.getEmail()) == null)) {
				Account account = new Account();
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				account.setEmail(acc.getEmail());
				account.setUsername(acc.getUsername());
				account.setPassword(passwordEncoder.encode(acc.getPassword()));
				account.setGender(acc.getGender());
				account.setAvatar("default.jpg");
				account.setPhone(acc.getPhone());
				account.setCreateAt(new Date());
				account.setSpentMoney(0);
				account.setPrestigePoints(0);
				account.setRole(AccountRole.User);

				accDao.insert(account);

			} else {
				return Contants.ALREADYEXSIT;
			}

		} else {
			return Contants.INVALID_FIELDS;
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

	@Transactional
	public byte[] getUserAva(String username) {
		Account acc = accDao.find(username);
		
		try {
			return getImageBytes(acc.getAvatar());
		} catch (Exception ex) {
			return null;
		}
	}
	
	@Transactional
	public Account keycheck(String key) {
		Account acc = accDao.find(key);
		
		if (acc == null) {
			acc = accDao.findByEmail(key);
			
			if (acc == null) {
				acc = accDao.findByPhone(key);
				
				if (acc == null) {
					return null;
				}
			}
		}
		
		return acc;
	}
	
	public String uploadFile(MultipartFile file) {

		if (file.isEmpty()) {
			return null;
		}

		try {

			// Get the file and save it to UPLOADED_FOLDER
			byte[] bytes = file.getBytes();
			Path path = Paths.get(Contants.UPLOAD_FILE_DESTINATION + file.getOriginalFilename());
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return file.getOriginalFilename();
	}

	public byte[] getImageBytes(String filename) throws IOException {
		File file = new File(Contants.UPLOAD_FILE_DESTINATION + filename);

		if (!file.exists()) {
			return null;
		}

		byte[] data = Files.readAllBytes(file.toPath());

		return data;
	}
}
