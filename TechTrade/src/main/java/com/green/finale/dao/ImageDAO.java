package com.green.finale.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Image;

@Repository
public class ImageDAO {
	@Autowired
	private SessionFactory factory;

	public long insert(Image image) {
		Session ss = factory.getCurrentSession();

		return (long) ss.save(image);
	}

	public int delete(long id) {
		Session ss = factory.getCurrentSession();
		TypedQuery<?> hql = ss.createQuery("DELETE FROM Image WHERE id = :id");
		
		hql.setParameter("id", id);
		
		return hql.executeUpdate();
	}
	
	public String find(long postId, int number) {
		Session ss = factory.getCurrentSession();
		TypedQuery<String> hql = ss.createQuery("SELECT filename FROM Image WHERE post.id = :postId", String.class);

		hql.setParameter("postId", postId);
		hql.setFirstResult(number);
		hql.setMaxResults(1);

		try {
			return hql.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	public List<Image> getList(long postId) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Image> hql = ss.createQuery("FROM Image WHERE post.id = :postId", Image.class);
		
		hql.setParameter("postId", postId);
		
		return hql.getResultList();
	}
	
	public int deleteByPost(long postId) {
		Session ss = factory.getCurrentSession();
		TypedQuery<?> hql = ss.createQuery("DELETE FROM Image WHERE post.id = :postId");
		
		hql.setParameter("postId", postId);
		
		return hql.executeUpdate();
	}
	
	public Image getNewestImage() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Image> hql = ss.createQuery("FROM Image ORDER BY id desc", Image.class);
		
		hql.setMaxResults(1);
		
		return hql.getSingleResult();
	}
}
