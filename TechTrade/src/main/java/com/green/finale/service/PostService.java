package com.green.finale.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.green.finale.dao.CommentDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.Category;
import com.green.finale.entity.Comment;
import com.green.finale.entity.Post;
import com.green.finale.model.PostModel;

@Service
public class PostService {

	@Autowired
	private PostDAO postDao;

	@Autowired
	private CommentDAO commentDao;
	
	@Transactional
	public List<Post> getPostList() {
		return postDao.getList();
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
	public List<Post> getPostListByHobby(int cate, String ward) {
		return postDao.getListByHobby(cate, ward);
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
	public List<Comment> getCommentListByPost(List<Post> postList) {
		List<Comment> comments = new ArrayList<>();
		List<Comment> temp = new ArrayList<>();
		
		for (Post p: postList) {
			temp = commentDao.getListByPost(p.getId());
			
			for (Comment c: temp) {
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

}
