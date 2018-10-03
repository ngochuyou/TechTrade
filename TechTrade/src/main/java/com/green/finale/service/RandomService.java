package com.green.finale.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.green.finale.dao.AccountDAO;
import com.green.finale.dao.CategoryDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.dao.WardDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.Post;
import com.green.finale.utils.AccountRole;
import com.green.finale.utils.Gender;

@Service
public class RandomService {

	@Autowired
	private WardDAO wardDao;

	@Autowired
	private AccountDAO accDao;

	@Autowired
	private CategoryDAO cateDao;

	@Autowired
	private PostDAO postDao;

	@Transactional
	public void addRandomAccount() {
		int n = 1000;
		Account[] acc = new Account[n];
		Random ran = new Random();
		int UNlength = ran.nextInt((20 - 8) + 1) + 8;
		char username[] = new char[UNlength];
		String[] gender = { "Male", "Female" };
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		List<Integer> randomWardId = wardDao.getRandomWardIdList(n);
		String usernameString;

		for (int i = 0; i < n; i++) {
			acc[i] = new Account();

			for (int j = 0; j < UNlength; j++) {
				username[j] = (char) (ran.nextInt((122 - 97) + 1) + 97);
			}

			usernameString = String.valueOf(username);
			acc[i].setUsername(usernameString);
			acc[i].setAddress(usernameString + "Address");
			acc[i].setAvatar("default.jpg");
			acc[i].setCreateAt(randomDate());
			acc[i].setEmail(usernameString + ".bot@gmail.com");
			acc[i].setGender(Gender.valueOf(gender[ran.nextInt((1 - 0) + 1) + 0]));
			acc[i].setPassword(encoder.encode(usernameString));
			acc[i].setPhone("0974032706");
			acc[i].setPrestigePoints(ran.nextInt((1000 - 10) + 1) + 10);
			acc[i].setRole(AccountRole.User);
			acc[i].setSpentMoney(ran.nextInt((1000000 - 10000) + 1) + 10000);
			acc[i].setWard(wardDao.find(String.valueOf(randomWardId.get(i))));

			accDao.insert(acc[i]);
		}

	}

	@Transactional
	public void addRandomPost() {
		int n = 10000;
		Post post[] = new Post[n];
		String status[] = { "true", "false" };
		Random ran = new Random();
		List<String> hashTags = randomHashTags(n);
		List<String> accountIds = accDao.getRandomAccountIdList(n);
		List<Integer> categoryIds = cateDao.getRandomCategoryIdList();
		List<String> desList = randomDescription(n);
		List<String> titleList = randomNameList(n);

		for (int i = 0; i < n; i++) {
			post[i] = new Post();

			post[i].setCreateAt(randomDate());
			post[i].setStatus(Boolean.valueOf(status[ran.nextInt((1 - 0) + 1)]));
			post[i].setTags(hashTags.get(i));
			post[i].setCreateBy(accDao.find(accountIds.get(ran.nextInt((999 - 0) + 1) + 0)));
			post[i].setCategory(cateDao.find(categoryIds.get(ran.nextInt((4 - 0) + 1) + 0)));
			post[i].setDescription(desList.get(i));
			post[i].setName(titleList.get(i));

			postDao.insert(post[i]);
		}

	}

	public Date randomDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Random ran = new Random();
		StringBuilder sb = new StringBuilder();

		sb.append(String.valueOf(ran.nextInt((2018 - 2000) + 1) + 2000));
		sb.append("-");
		sb.append(String.valueOf(ran.nextInt((12 - 1) + 1) + 1));
		sb.append("-");
		sb.append(String.valueOf(ran.nextInt((28 - 1) + 1) + 1));

		try {
			return sdf.parse(sb.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public List<String> randomHashTags(int n) {
		String[] tagArray = new String[64];

		try {
			BufferedReader bf = new BufferedReader(new FileReader("D:\\randomHashTag.txt"));
			String s;

			for (int i = 0; (s = bf.readLine()) != null; i++) {
				tagArray[i] = s;
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Random ran = new Random();
		int noOfTags;
		List<String> hashTags = new ArrayList<String>();
		StringBuffer tag = new StringBuffer();

		for (int i = 0; i < n; i++) {
			noOfTags = ran.nextInt((5 - 1) + 1) + 1;
			tag = new StringBuffer();

			for (int j = 0; j < noOfTags; j++) {
				tag.append(tagArray[ran.nextInt((63 - 0) + 1) + 0]);
				tag.append(',');
			}
			hashTags.add(tag.toString());
		}

		return hashTags;
	}

	public List<String> randomDescription(int n) {
		String[] desArray = new String[41];

		try {
			BufferedReader bf = new BufferedReader(new FileReader("D:\\description.txt"));
			String s;

			for (int i = 0; (s = bf.readLine()) != null; i++) {
				desArray[i] = s;
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Random ran = new Random();
		int noOfRows;
		List<String> desList = new ArrayList<String>();
		StringBuffer des = new StringBuffer();

		for (int i = 0; i < n; i++) {
			noOfRows = ran.nextInt((10 - 1) + 1) + 1;
			des = new StringBuffer();

			for (int o = ran.nextInt((30 - 0) + 1) + 0, j = 0; j < noOfRows; j++, o++) {
				des.append(desArray[o]);
			}
			desList.add(des.toString());
		}

		return desList;
	}

	public List<String> randomNameList(int n) {
		String[] titleArray = new String[74];

		try {
			BufferedReader bf = new BufferedReader(new FileReader("D:\\titles.txt"));
			String s;

			for (int i = 0; (s = bf.readLine()) != null; i++) {
				titleArray[i] = s;
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Random ran = new Random();
		List<String> titleList = new ArrayList<String>();

		for (int i = 0; i < n; i++) {
			titleList.add(titleArray[ran.nextInt((73 - 0) + 1) + 0]);
		}

		return titleList;
	}
}
