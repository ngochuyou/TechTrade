package com.green.finale.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
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
import com.green.finale.dao.CategoryDAO;
import com.green.finale.dao.CommentDAO;
import com.green.finale.dao.ImageDAO;
import com.green.finale.dao.MessageDAO;
import com.green.finale.dao.PinDAO;
import com.green.finale.dao.PostDAO;
import com.green.finale.dao.PostReportDAO;
import com.green.finale.dao.VoteDAO;
import com.green.finale.entity.Account;
import com.green.finale.entity.Comment;
import com.green.finale.entity.Image;
import com.green.finale.entity.Message;
import com.green.finale.entity.Pin;
import com.green.finale.entity.PinId;
import com.green.finale.entity.Post;
import com.green.finale.entity.PostReport;
import com.green.finale.entity.PostReportId;
import com.green.finale.entity.Vote;
import com.green.finale.entity.VoteId;
import com.green.finale.model.PostModel;
import com.green.finale.model.PostReportModel;
import com.green.finale.utils.AccountRole;
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

	@Autowired
	private PinDAO pinDao;

	@Autowired
	private CategoryDAO cateDao;

	@Autowired
	private PostReportDAO postReportDao;
	
	@Autowired
	private MessageDAO messDao;
	
	@Transactional
	public List<Post> getPostList() {
		return postDao.getList();
	}

	@Transactional
	public List<PostModel> getNewestPostModel(Principal principal, long page) {

		return getPostModelList(postDao.getNewestList(page), principal);
	}

	@Transactional
	public List<PostModel> getPostListByAccount(String username, int page, String sortBy, Principal principal) {
		Pattern p = Pattern.compile("(createAt|nearest|upVote)(:desc|:asc)");
		Matcher m = p.matcher(sortBy);

		if (!m.matches()) {
			return null;
		}

		if (principal != null && principal.getName().equals(username)) {
			return getPostModelList(postDao.getAllListByAccount(username, page, sortBy), principal);
		} else {
			return getPostModelList(postDao.getListByAccount(username, page, sortBy), principal);
		}
	}

	@Transactional
	public Post find(long postId) {

		return postDao.find(postId);
	}

	@Transactional
	public String createPost(PostModel model, String username) {
		if (validatePost(model)) {
			Account acc = accDao.find(username);

			if (acc == null) {
				return Contants.USER_NONEXSIT;
			}

			Post newPost = extractPost(model, new Post());

			newPost.setCategory(cateDao.find(model.getCategoryId()));
			newPost.setCreateAt(new Date());
			newPost.setCreateBy(acc);
			newPost.setStatus(true);
			newPost.setDeleted(false);
			newPost.setUpVote(0);

			postDao.insert(newPost);

			List<String> filenames = uploadImage(model.getFile());
			Image image = null;

			for (String filename : filenames) {
				image = new Image();

				image.setFilename(filename);
				image.setPost(newPost);

				imageDao.insert(image);
			}

			return "";
		} else {
			return Contants.INVALID_FIELDS;
		}

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
	public List<PostModel> search(String categoryId, String keyword, String sortBy, int firstRecord,
			Principal principal) throws NumberFormatException {
		Pattern p = Pattern.compile("(createAt|nearest|upVote)(:desc|:asc)");
		Matcher m = p.matcher(sortBy);

		if (!m.matches()) {
			throw new NumberFormatException(Contants.INVALID_FIELDS);
		}

		try {
			return getPostModelList(postDao.getList(Integer.valueOf(categoryId), keyword, sortBy, firstRecord),
					principal);
		} catch (NumberFormatException ex) {
			throw new NumberFormatException(Contants.INVALID_FIELDS);
		}
	}

	@Transactional
	public String deletePost(long postId, String username) {
		Post targetedPost = postDao.find(postId);

		if (targetedPost == null) {

			return Contants.POST_NONEXSIT;
		}

		Account user = accDao.find(username);

		if (!targetedPost.getCreateBy().getUsername().equals(username) && user.getRole() != AccountRole.Admin) {

			return Contants.NOT_BELONG;
		}

		targetedPost.setDeleted(true);
		postDao.update(targetedPost);

		return "";
	}

	@Transactional
	public String changePostStatus(long postId, String status, Principal principal) {
		Post post = postDao.find(postId);

		if (post != null) {
			String postUsername = post.getCreateBy().getUsername();

			if (postUsername.equals(principal.getName())) {
				if (status.equals("close")) {
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
		model.setDeleted(p.isDeleted());
		model.setTags(p.getTags());
		model.setUpVote(p.getUpVote());
		model.setCreateBy(p.getCreateBy());

		if (principal != null) {
			model.setVote(voteDao.find(new VoteId(principal.getName(), p.getId())));
			model.setPin(pinDao.find(new PinId(principal.getName(), p.getId())));
			model.setReport(postReportDao.find(new PostReportId(accDao.find(principal.getName()), p)));
		} else {
			model.setVote(null);
			model.setPin(null);
			model.setReport(null);
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
				model.setPin(pinDao.find(new PinId(principal.getName(), p.getId())));
			} else {
				model.setVote(null);
				model.setPin(null);
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
			post.setTags(post.getTags());
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

			int postUpvotes = currentPost.getUpVote();

			if (type == true) {
				currentPost.setUpVote(postUpvotes + 1);
			} else {
				currentPost.setUpVote(postUpvotes - 1);
			}

			postDao.update(currentPost);

			return null;
		} else {
			return Contants.POST_ALREADY_VOTED;
		}
	}

	@Transactional
	public PostModel getNewPostModel(String username) {
		PostModel model = new PostModel();

		model.setUsername(username);
		model.setCreateBy(accDao.find(username));
		model.setStatus(true);
		model.setUpVote(0);

		return model;
	}

	@Transactional
	public double rateHashtag(String hashtag) {
		long counted = postDao.countHashtag(hashtag);
		long totalPost = postDao.count("");

		return Math.ceil((counted * 1.0 / totalPost) * 10000) / 100;
	}

	@Transactional
	public String pinPost(String username, long postId) {
		PinId pinId = new PinId(username, postId);
		Pin pin = pinDao.find(pinId);

		if (pin != null) {
			pinDao.delete(pin);

			return "Unpinned";
		} else {
			Account acc = accDao.find(username);

			if (acc == null) {
				return Contants.USER_NONEXSIT;
			}

			pin = new Pin();
			pin.setPinId(pinId);
			pin.setCreateAt(new Date());
			pin.setAccount(acc);

			Post post = postDao.find(postId);

			if (post == null) {
				return Contants.POST_NONEXSIT;
			}

			pin.setPost(post);
			pinDao.insert(pin);

			return "Pinned";
		}
	}

	@Transactional
	public List<Pin> getPinnedPostList(Principal principal) {

		return pinDao.getPinnedList(principal.getName());
	}

	@Transactional
	public String reportPost(PostReportModel model, String username) {
		Account user = accDao.find(username);

		if (user == null) {
			return Contants.USER_NONEXSIT;
		}

		long targetedPostId = model.getTargetedPost();

		if (targetedPostId == 0) {

			return Contants.INVALID_FIELDS;
		}

		Post targetedPost = postDao.find(targetedPostId);

		if (targetedPost == null) {

			return Contants.POST_NONEXSIT;
		}

		PostReportId reportId = new PostReportId(user, targetedPost);
		PostReport report = postReportDao.find(reportId);

		if (report != null) {

			return Contants.POSTREPORT_ALREADYEXSIT;
		}

		report = new PostReport();

		report.setId(reportId);
		report.setContent(model.getContent());
		report.setCreatedAt(new Date());
		report.setDescription(model.getReason());
		postReportDao.insert(report);

		List<Account> admins = accDao.getAdminList();
		Account system = accDao.find("TechTrade");
		Date sentAt = new Date();
		Message reportNoti;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");

		for (Account admin : admins) {
			reportNoti = new Message();

			reportNoti.setContent("A Post has been reported with the reason: " + report.getDescription()
					+ "\nDescription: " + report.getContent() + "\nOn " + sdf.format(report.getCreatedAt()));
			reportNoti.setDeletedByReceiver(false);
			reportNoti.setDeletedBySender(false);
			reportNoti.setRead(false);
			reportNoti.setReceiver(admin);
			reportNoti.setSender(system);
			reportNoti.setSentAt(sentAt);

			messDao.insert(reportNoti);
		}

		return "Post report sent.";
	}

	public boolean validatePost(PostModel post) {
		if (StringUtils.isEmpty(post.getName())) {
			return false;
		}

		if (StringUtils.isEmpty(post.getDescription())) {
			return false;
		}

		if (StringUtils.isEmpty(post.getCategoryId())) {
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
		target.setTags(model.getTags().replaceAll("#", ",#").replaceFirst(",", ""));

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
