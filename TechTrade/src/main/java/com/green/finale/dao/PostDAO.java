package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Account;
import com.green.finale.entity.Category;
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

	public List<Post> getListByAccount(Account acc) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post Where createBy.username = :acc", Post.class);
		hql.setParameter("acc", acc.getUsername());
		return hql.getResultList();
	}
	
	public List<Post> getListByCategory(Category cate){
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post Where category.id = :cate", Post.class);
		hql.setParameter("cate", cate.getId());
		return hql.getResultList();
	}
	
	public List<Post> getListByHobby(int cate,String ward){
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post Where category.id = :cate And createBy.ward.id = :ward", Post.class);
		hql.setParameter("cate", cate);
		hql.setParameter("ward", ward);
		return hql.getResultList();
	}
	
	public List<Post> getListByWard(String ward){
		Session ss = factory.getCurrentSession();
		TypedQuery<Post> hql = ss.createQuery("FROM Post Where createBy.ward.id = :ward", Post.class);
		hql.setParameter("ward", ward);
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
