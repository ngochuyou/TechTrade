package com.green.finale.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.green.finale.dao.CommentDAO;
import com.green.finale.dao.ImageDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.Category;
import com.green.finale.entity.Comment;
import com.green.finale.entity.Post;
import com.green.finale.model.PostModel;
import com.green.finale.utils.Contants;

@Service
public class PostService {

	@Autowired
	private PostDAO postDao;

	@Autowired
	private CommentDAO commentDao;
	
	@Autowired
	private ImageDAO imageDao;
	
	@Transactional
	public List<Post> getPostList() {
		return postDao.getList();
	}

	@Transactional
	public List<Post> getNewestPost() {
		List<Post> post = postDao.getList();
		Collections.reverse(post);
		return post;
	}

	@Transactional
	public List<Post> getNewestList(long page) {

		return postDao.getNewestList(page);
	}

	@Transactional
	public List<Post> getPostListByAccount(Account acc) {
		return postDao.getListByAccount(acc);
	}

	@Transactional
	public List<Post> getPostListByCategory(Category cate) {
		return postDao.getListByCategory(cate);
	}

	@Transactional
	public List<Post> getPostListByWard(String ward) {
		return postDao.getListByWard(ward);
	}

	@Transactional
	public Post find(long postId) {
		
		return postDao.find(postId);
	}
	
	@Transactional
	public long createPost(PostModel postMD) {
		boolean status = true;
		if (validatePost(postMD)) {
			Post post = new Post();

			post.setName(postMD.getName());
			post.setDescription(postMD.getDescription());
			post.setStatus(status);
			post.setCreateAt(new Date());
			post.setTags("");

			postDao.insert(post);
		} else {
		}
		return 0;
	}

	@Transactional
	public List<Comment> getCommentsByPost(long postId) {
		List<Comment> comments = commentDao.getListByPost(postId);
	
		return comments;
	}
	
	public List<Comment> getCommentListByPost(List<Post> postList) {
		List<Comment> comments = new ArrayList<>();
		List<Comment> temp = new ArrayList<>();

		for (Post p : postList) {
			temp = commentDao.getListByPost(p.getId());

			for (Comment c : temp) {
				comments.add(c);
			}
		}

		return comments;
	}

	public boolean validatePost(PostModel post) {
		if (StringUtils.isEmpty(post.getName())) {
			return false;
		}
		if (StringUtils.isEmpty(post.getDescription())) {
			return false;
		}
		if (StringUtils.isEmpty(post.getCategory())) {
			return false;
		}

		return true;
	}
	
	@Transactional
	public List<String> getImageListByPost(long postId) {
		
		return imageDao.getList(postId);
	}
	
	@Transactional
	public List<Object[]> search(String keyword) {
		List<Object[]> list = postDao.searchByTitle(keyword);
		
		return list;
	}
	
	public byte[] getImageBytes(String filename) {
		File file = new File(Contants.UPLOAD_FILE_DESTINATION + filename);
		
		if (!file.exists()) {
			System.out.println("file doesn't exsit");
			return null;
		}

		byte[] data;
		try {
			data = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}

		return data;
	}

	public void test() {

	}
	
}
