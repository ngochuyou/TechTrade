package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Post;

@Repository
public class PostDAO {
	@Autowired
	private SessionFactory factory;

	private static final int MAX_RESULT = 10;

	public Post find(long id) {
		Session ss = factory.getCurrentSession();

		return ss.get(Post.class, id);
	}

	public List<Post> getList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post WHERE deleted = :deleted AND status = :status", Post.class);

		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.getResultList();
	}

	public List<Post> getList(int firstRow) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post WHERE deleted = :deleted AND status = :status", Post.class);

		hql.setFirstResult(firstRow);
		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.getResultList();
	}

	public List<Post> getList(int categoryId, String keyword, String sortBy, int firstRecord) {
		Session ss = factory.getCurrentSession();
		String query = null;
		Query<Post> hql = null;

		if (categoryId == 0) {
			query = "FROM Post WHERE category.id IS NOT NULL AND (name LIKE :keyword OR tags LIKE :keyword) AND deleted = :deleted AND status = :status ORDER BY "
					+ sortBy.replace(":", " ");
			hql = ss.createQuery(query, Post.class);
		} else {
			query = "FROM Post WHERE category.id = :cateId AND (name LIKE :keyword OR tags LIKE :keyword) AND deleted = :deleted AND status = :status ORDER BY "
					+ sortBy.replace(":", " ");
			hql = ss.createQuery(query, Post.class);
			hql.setParameter("cateId", categoryId);
		}

		hql.setParameter("keyword", "%" + keyword + "%");
		hql.setFirstResult(firstRecord * MAX_RESULT);
		hql.setMaxResults(MAX_RESULT);
		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.getResultList();
	}

	public List<Post> getNewestList(long page) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery(
				"FROM Post WHERE deleted = :deleted AND status = :status ORDER BY createAt desc ", Post.class);

		hql.setMaxResults(MAX_RESULT);
		hql.setFirstResult((int) page * MAX_RESULT);
		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.getResultList();
	}

	public List<Post> getAllListByAccount(String username, int firstRecord, String sortBy) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss
				.createQuery("FROM Post Where createBy.username = :username AND deleted = :deleted ORDER BY "
						+ sortBy.replace(":", " "), Post.class);

		hql.setParameter("username", username);
		hql.setFirstResult(firstRecord * MAX_RESULT);
		hql.setMaxResults(MAX_RESULT);
		hql.setParameter("deleted", false);

		return hql.getResultList();
	}

	public List<Post> getListByAccount(String username, int firstRecord, String sortBy) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery(
				"FROM Post Where createBy.username = :username AND deleted = :deleted AND status = :status ORDER BY "
						+ sortBy.replace(":", " "),
				Post.class);

		hql.setParameter("username", username);
		hql.setFirstResult(firstRecord * MAX_RESULT);
		hql.setMaxResults(MAX_RESULT);
		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.getResultList();
	}

	public List<Post> getListByCategory(int cateId, String sortBy, int firstRecord) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery(
				"FROM Post Where category.id = :cateId AND deleted = :deleted AND status = :status ORDER BY "
						+ sortBy.replace(":", " "),
				Post.class);

		hql.setParameter("cateId", cateId);
		hql.setFirstResult(firstRecord * MAX_RESULT);
		hql.setMaxResults(MAX_RESULT);
		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.getResultList();
	}

	public List<Object[]> getListByTitle(String keyword) {
		Session ss = factory.getCurrentSession();
		Query<Object[]> hql = ss.createQuery(
				"SELECT name, id FROM Post WHERE (name LIKE :keyword OR tags LIKE :keyword) AND deleted = :deleted AND status = :status ORDER BY upVote desc",
				Object[].class);

		hql.setParameter("keyword", "%" + keyword + "%");
		hql.setMaxResults(MAX_RESULT);
		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.list();
	}

	public List<Post> getPinnedList(String username) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery(
				"SELECT p FROM Post as p, Pin as pn WHERE pn.account.username = p.createBy.username AND pn.account.username = :username AND p.deleted = :deleted AND p.status = :status ORDER BY pn.createAt desc",
				Post.class);

		hql.setParameter("username", username);
		hql.setParameter("deleted", false);
		hql.setParameter("status", true);

		return hql.getResultList();
	}

	public long count(String conditionQuery) {
		Session ss = factory.getCurrentSession();
		Query<Long> hql = ss.createQuery("SELECT COUNT(*) FROM Post " + conditionQuery, Long.class);

		return hql.getSingleResult();
	}

	public long countHashtag(String hashtag) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Long> hql = ss.createQuery("SELECT COUNT(*) FROM Post WHERE tags LIKE :keyword", Long.class);

		hql.setParameter("keyword", "%" + hashtag + "%");

		return hql.getSingleResult();
	}

	public long insert(Post post) {
		Session ss = factory.getCurrentSession();

		return (long) ss.save(post);
	}

	public void update(Post post) {
		Session ss = factory.getCurrentSession();

		ss.update(post);
	}

	public void delete(Post post) {
		Session ss = factory.getCurrentSession();

		ss.delete(post);
	}
}