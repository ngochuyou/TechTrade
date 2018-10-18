package com.green.finale.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.green.finale.dao.AccountDAO;
import com.green.finale.dao.CommentDAO;
import com.green.finale.dao.ImageDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.dao.VoteDAO;
import com.green.finale.entity.Comment;
import com.green.finale.entity.Image;
import com.green.finale.entity.Post;
import com.green.finale.entity.Vote;
import com.green.finale.entity.VoteId;
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

	@Autowired
	private AccountDAO accDao;

	@Autowired
	private VoteDAO voteDao;

	@Transactional
	public List<Post> getPostList() {
		return postDao.getList();
	}

	@Transactional
	public List<Post> getNewestList(long page) {

		return postDao.getNewestList(page);
	}

	@Transactional
	public List<PostModel> getPostListByAccount(String username, int page, String sortBy, Principal principal) {
		Pattern p = Pattern.compile("(createAt|nearest|upVote)(:desc|:asc)");
		Matcher m = p.matcher(sortBy);

		if (!m.matches()) {
			return null;
		}

		return getPostModelList(postDao.getListByAccount(username, page, sortBy), principal);
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
	public List<Image> getImageListByPost(long postId) {

		return imageDao.getList(postId);
	}

	@Transactional
	public List<Object[]> search(String keyword) {
		List<Object[]> list = postDao.getListByTitle(keyword);

		return list;
	}

	@Transactional
	public List<Post> search(String categoryId, String keyword, String sortBy, int firstRecord)
			throws NumberFormatException {
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

	@Transactional
	public PostModel getPostModel(long postId, Principal principal) {
		Post p = postDao.find(postId);

		if (p == null) {
			return null;
		}

		PostModel model = new PostModel();

		model.setId(p.getId());
		model.setCategory(p.getCategory());
		model.setCreateAt(p.getCreateAt());
		model.setUsername(p.getCreateBy().getUsername());
		model.setDescription(p.getDescription());
		model.setName(p.getName());
		model.setStatus(p.isStatus());
		model.setTags(p.getTags());
		model.setUpVote(p.getUpVote());
		model.setCreateBy(p.getCreateBy());

		if (principal != null) {
			model.setVote(voteDao.find(new VoteId(principal.getName(), p.getId())));
		} else {
			model.setVote(null);
		}

		return model;
	}

	@Transactional
	public List<PostModel> getPostModelList(List<Post> postList, Principal principal) {
		List<PostModel> modelList = new ArrayList<>();
		PostModel model;
		
		for (Post p : postList) {
			model = new PostModel();

			model.setId(p.getId());
			model.setCategory(p.getCategory());
			model.setCreateAt(p.getCreateAt());
			model.setUsername(p.getCreateBy().getUsername());
			model.setDescription(p.getDescription());
			model.setName(p.getName());
			model.setStatus(p.isStatus());
			model.setTags(p.getTags());
			model.setUpVote(p.getUpVote());
			model.setCreateBy(p.getCreateBy());

			if (principal != null) {
				model.setVote(voteDao.find(new VoteId(principal.getName(), p.getId())));
			} else {
				model.setVote(null);
			}
			
			modelList.add(model);
		}

		return modelList;
	}

	@Transactional
	public String updatePost(PostModel model, Principal principal) {
		if (!principal.getName().equals(model.getUsername())) {
			return Contants.NOT_BELONG;
		}

		Post post = postDao.find(model.getId());

		if (post == null) {
			return Contants.NONEXSIT;
		} else {
			post = extractPost(model, post);
			post.setTags(post.getTags().replaceAll("#", ",#").replaceFirst(",", ""));
			postDao.update(post);
		}

		Image uploadImage = new Image();
		ArrayList<String> filenames = uploadImage(model.getFile());

		for (String name : filenames) {
			uploadImage = new Image();
			uploadImage.setFilename(name);
			uploadImage.setPost(post);
			imageDao.insert(uploadImage);
		}

		String deletedImagesIds[] = model.getDeletedImages().split(",");

		for (String imageId : deletedImagesIds) {
			if (imageId.length() == 0) {
				continue;
			} else {
				imageDao.delete(Long.valueOf(imageId));
			}
		}

		return null;
	}

	@Transactional
	public String addComment(long postId, String comment, Principal principal) {
		Post currentPost = postDao.find(postId);

		if (currentPost == null) {

			return Contants.POST_NONEXSIT;
		}

		Comment currentComment = new Comment();

		currentComment.setAccount(accDao.find(principal.getName()));
		currentComment.setCommentedOn(new Date());
		currentComment.setContent(comment);
		currentComment.setPost(currentPost);
		commentDao.insert(currentComment);

		return null;
	}

	@Transactional
	public String votePost(long postId, boolean type, Principal principal) {
		Post currentPost = postDao.find(postId);

		if (currentPost == null) {
			return Contants.POST_NONEXSIT;
		}

		VoteId voteId = new VoteId(principal.getName(), postId);

		if (voteDao.find(voteId) == null) {
			Vote newVote = new Vote();

			newVote.setVoteId(voteId);
			newVote.setType(type);
			newVote.setAccount(accDao.find(principal.getName()));
			newVote.setPost(currentPost);
			newVote.setVoteDate(new Date());
			voteDao.insert(newVote);

			return null;
		} else {
			return Contants.POST_ALREADY_VOTED;
		}
	}

//	public List<Post>
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

	public Post extractPost(PostModel model, Post target) {
		target.setId(model.getId());
		target.setName(model.getName());
		target.setDescription(model.getDescription());
		target.setTags(model.getTags());

		return target;
	}

	public ArrayList<String> uploadImage(MultipartFile[] files) {
		ArrayList<String> filenames = new ArrayList<>();
		String newestFilename = imageDao.getNewestImage().getFilename();
		long filenameToLong = Long.parseLong(newestFilename.replaceAll("\\D", ""));

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}

			newestFilename = ++filenameToLong + "." + FilenameUtils.getExtension(file.getOriginalFilename());

			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(Contants.UPLOAD_FILE_DESTINATION + newestFilename);
				Files.write(path, bytes);

				filenames.add(newestFilename);

			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		return filenames;
	}
}
