package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Comment;

@Repository
public class CommentDAO {
	
	@Autowired
	private SessionFactory factory;
	private static final int MAX_RESULT = 10;
	
	public List<Comment> getListByPost(long postId) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Comment> hql = ss.createQuery("FROM Comment WHERE post.id = :postId", Comment.class);
		
		hql.setParameter("postId", postId);
		hql.setMaxResults(MAX_RESULT);
		
		return hql.getResultList();
	}
	
	public long insert(Comment c) {
		Session ss = factory.getCurrentSession();
		
		return (long) ss.save(c);
	}
	
	public int deleteByPost(long postId) {
		Session ss = factory.getCurrentSession();
		Query<?> hql = ss.createQuery("DELETE FROM Comment WHERE post.id = :postId");
		
		hql.setParameter("postId", postId);
		
		return hql.executeUpdate();
	}
}
