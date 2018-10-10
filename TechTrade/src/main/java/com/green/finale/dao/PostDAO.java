package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Account;
import com.green.finale.entity.Category;
import com.green.finale.entity.Post;

@Repository
public class PostDAO {
	@Autowired
	private SessionFactory factory;

	private static final int maxResult = 10;

	public Post find(long id) {
		Session ss = factory.getCurrentSession();

		return ss.get(Post.class, id);
	}

	public List<Post> getList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post", Post.class);

		return hql.getResultList();
	}
	
	public List<Post> getList(int firstRow) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post", Post.class);

		hql.setFirstResult(firstRow);
		
		return hql.getResultList();
	}
	
	public List<Post> getNewestList(long page) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post ORDER BY createAt desc", Post.class);

		hql.setMaxResults(maxResult);
		hql.setFirstResult((int) page * maxResult);

		return hql.getResultList();
	}

	public List<Post> getListByAccount(Account acc) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post Where createBy.username = :acc", Post.class);

		hql.setParameter("acc", acc.getUsername());

		return hql.getResultList();
	}

	public List<Post> getListByCategory(Category cate) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post Where category.id = :cate", Post.class);

		hql.setParameter("cate", cate.getId());

		return hql.getResultList();
	}

	public List<Post> getListByWard(String ward) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post Where createBy.ward.id = :ward", Post.class);

		hql.setParameter("ward", ward);

		return hql.getResultList();
	}

	public List<Object[]> searchByTitle(String keyword) {
		Session ss = factory.getCurrentSession();
		Query<Object[]> hql = ss.createQuery(
				"SELECT name, id FROM Post WHERE name LIKE :keyword OR tags LIKE :keyword ORDER BY upVote desc",
				Object[].class);

		hql.setParameter("keyword", "%" + keyword + "%");
		hql.setMaxResults(maxResult);

		return hql.list();
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