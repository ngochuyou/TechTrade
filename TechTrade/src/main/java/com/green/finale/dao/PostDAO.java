package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Post;

@Repository
public class PostDAO {
	@Autowired
	private SessionFactory factory;

	public List<Post> getList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post", Post.class);

		return hql.getResultList();
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
