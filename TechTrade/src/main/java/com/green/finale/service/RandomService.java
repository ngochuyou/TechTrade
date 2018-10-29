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
import com.green.finale.dao.CommentDAO;
import com.green.finale.dao.ImageDAO;
import com.green.finale.dao.MessageDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.dao.WardDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.Category;
import com.green.finale.entity.Comment;
import com.green.finale.entity.Image;
import com.green.finale.entity.Message;
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
	
	@Autowired
	private MessageDAO messDao;
	@Autowired
	private ImageDAO imageDao;
	
	@Autowired
	private CommentDAO comDao;
	
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
	public void addRandomComment() {
		int n = 10000;
		
		List<String> des = randomDescription();
		List<Account> accounts = accDao.getList();
		List<Post> posts = postDao.getList();
		List<Post> posts2 = postDao.getList(1000);
		int accCapa = accounts.size()-1;
		int desCapa = des.size()-1;
		Comment c;
		String list[] = new String[20];
		StringBuilder sb = new StringBuilder("image");
		
		for (int i=0; i<20; i++) {
			sb = new StringBuilder("image");
			
			list[i] = sb.append(String.valueOf(i + 1)).append(".jpg").toString();
		}
		
		for (Post p: posts2) {
			posts.add(p);
		}
		
		int postsCapa = posts.size()-1;
		
		for (int i=0; i<n; i++) {
			c = new Comment();
			
			c.setAccount(accounts.get(randomNumber(accCapa, 0)));
			c.setCommentedOn(randomDate());
			c.setContent(des.get(randomNumber(desCapa, 0)));
			c.setImage(list[randomNumber(19, 0)]);
			c.setPost(posts.get(randomNumber(postsCapa, 0)));
			
			comDao.insert(c);
		}
	}
	
	@Transactional
	public void addRandomPost() {
		String status[] = { "true", "false" };
		List<String> hashTags = randomHashTags();
		List<Account> accounts = accDao.getList();
		List<Category> categories = cateDao.getList();
		List<String> desList = randomDescription();
		List<String> titleList = randomNameList();
		Post p;
		StringBuilder sb = null;
		int hashCapa = hashTags.size()-1;
		int accCapa = accounts.size()-1;
		int cateCapa = categories.size()-1;
		int titleCapa = titleList.size()-1;
		int ranNum;
		
		for (String des: desList) {
			p = new Post();

			p.setCreateAt(randomDate());
			p.setStatus(Boolean.valueOf(status[randomNumber(1, 0)]));
			
			ranNum = randomNumber(2, 1);
			sb = new StringBuilder(hashTags.get(randomNumber(hashCapa, 0)));
			
			for (int i=0; i < ranNum; i++) {
				sb.append(',');
				sb.append(hashTags.get(randomNumber(hashCapa, 0)));
			}

			p.setTags(sb.toString());
			p.setCreateBy(accounts.get(randomNumber(accCapa, 0)));
			p.setCategory(categories.get(randomNumber(cateCapa, 0)));
			p.setDescription(des);
			p.setName(titleList.get(randomNumber(titleCapa, 0)));
			p.setUpVote(randomNumber(2000, -100));
			
			postDao.insert(p);
		}
		
		addRandomPostImage(desList.size());
	}

	@Transactional
	public void addRandomPostImage(int count) {
		String list[] = new String[20];
		Random ran = new Random();
		Image image = null;
		StringBuilder sb = new StringBuilder("image");
		
		for (int i=0; i<20; i++) {
			sb = new StringBuilder("image");
			
			list[i] = sb.append(String.valueOf(i + 1)).append(".jpg").toString();
		}
		
		int n = 10000;
		
		for (int i=0; i<n; i++) {
			image = new Image();
			
			image.setFilename(list[ran.nextInt((19 - 0) + 1) + 0]);
			image.setPost(postDao.find(ran.nextInt((count - 1) + 1) + 1));
			
			imageDao.insert(image);
		}
	}
	
	@Transactional
	public void addRandomMessages() {
		int n = 1000;
		
		List<Account> accounts = accDao.getList();
		List<String> contents = randomDescription();
		int accountsMax = accounts.size()-1;
		int contentsMax = contents.size()-1;
		Message mess;
		String s[] = {"true", "false"};
		
		for (int i=0; i<n; i++) {
			mess = new Message();
			
			mess.setSender(accounts.get(randomNumber(accountsMax, 0)));
			mess.setReceiver(accounts.get(randomNumber(accountsMax, 0)));
			mess.setContent(contents.get(randomNumber(contentsMax, 0)));
			mess.setSentAt(new Date());
			mess.setRead(Boolean.valueOf(s[randomNumber(1, 0)]));
			mess.setDeletedByReceiver(false);
			mess.setDeletedBySender(false);
			
			messDao.insert(mess);
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

	public List<String> randomHashTags() {
		List<String> hashtags = new ArrayList<>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\#smartphone.txt"));
			String s;

			while ((s = br.readLine()) != null) {
				hashtags.add(s);
			}
			
			br.close();
			
			br = new BufferedReader(new FileReader("D:\\#TV.txt"));
			
			while ((s = br.readLine()) != null) {
				hashtags.add(s);
			}
			
			br.close();
			
			br = new BufferedReader(new FileReader("D:\\#laptop.txt"));
			
			while ((s = br.readLine()) != null) {
				hashtags.add(s);
			}

			br.close();
			
			br = new BufferedReader(new FileReader("D:\\#speaker.txt"));
			
			while ((s = br.readLine()) != null) {
				hashtags.add(s);
			}

			br.close();
			
			br = new BufferedReader(new FileReader("D:\\#tablet.txt"));
			
			while ((s = br.readLine()) != null) {
				hashtags.add(s);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hashtags;
	}

	public List<String> randomDescription() {
		List<String> des = new ArrayList<>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\description - raw.txt"));
			String s;

			while ((s = br.readLine()) != null) {
				des.add(s);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return des;
	}

	public List<String> randomNameList() {
		List<String> title = new ArrayList<>();
		
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\titles.txt"));
			String s;
			
			while ((s = br.readLine()) != null) {
				title.add(s);
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return title;
	}
	
	public int randomNumber(int max, int min) {
		Random ran = new Random();
		
		return ran.nextInt((max - min) + 1) + min;
	}
}
