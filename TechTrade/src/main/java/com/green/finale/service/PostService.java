package com.green.finale.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.green.finale.dao.CommentDAO;
import com.green.finale.dao.ImageDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.entity.Account;
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
	public List<Post> getNewestList(long page) {

		return postDao.getNewestList(page);
	}

	@Transactional
	public List<Post> getPostListByAccount(Account acc) {
		return postDao.getListByAccount(acc.getUsername());
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

	@Transactional
	public List<String> getImageListByPost(long postId) {

		return imageDao.getList(postId);
	}

	@Transactional
	public List<Object[]> search(String keyword) {
		List<Object[]> list = postDao.getListByTitle(keyword);

		return list;
	}

	@Transactional
	public List<Post> search(String categoryId, String keyword, String sortBy, int firstRecord) throws NumberFormatException {
		Pattern p = Pattern.compile("(createAt|nearest|upVote)(:desc|:asc)");
		Matcher m = p.matcher(sortBy);

		if (!m.matches()) {
			throw new NumberFormatException(Contants.INVALID_FIELDS);
		}

		try {
			return postDao.getList(Integer.valueOf(categoryId), keyword, sortBy, firstRecord);
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(Contants.INVALID_FIELDS);
		}
	}

	@Transactional
	public String deletePost(long postId, Principal principal) {
		Post post = postDao.find(postId);

		if (post != null) {
			String postUsername = post.getCreateBy().getUsername();

			if (postUsername.equals(principal.getName())) {
				commentDao.deleteByPost(postId);
				imageDao.deleteByPost(postId);
				postDao.delete(post);

				return null;
			}

			return Contants.NOT_BELONG;
		}

		return Contants.NONEXSIT;
	}

	@Transactional
	public String changePostStatus(long postId, String status, Principal principal) {
		Post post = postDao.find(postId);

		if (post != null) {
			String postUsername = post.getCreateBy().getUsername();

			if (postUsername.equals(principal.getName())) {
				if (status.equals("close")) {
					System.out.println(status);
					post.setStatus(false);
					postDao.update(post);

					return null;
				}

				if (status.equals("restore")) {
					post.setStatus(true);
					postDao.update(post);

					return null;
				}

				return Contants.INVALID_FIELDS;
			}

			return Contants.NOT_BELONG;
		}

		return Contants.NONEXSIT;
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

}
