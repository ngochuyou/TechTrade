package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Comment;

@Repository
public class CommentDAO {
	
	@Autowired
	private SessionFactory factory;
	
	public List<Comment> getListByPost(long postId) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Comment> hql = ss.createQuery("FROM Comment WHERE post.id = :postId", Comment.class);
		
		hql.setParameter("postId", postId);
		
		return hql.getResultList();
	}
}
