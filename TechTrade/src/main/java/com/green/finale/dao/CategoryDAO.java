package com.green.finale.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Category;

@Repository
public class CategoryDAO {
	
	@Autowired
	private SessionFactory factory;
	
	public List<Category> getList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Category> hql = ss.createQuery("FROM Category", Category.class);
		
		return hql.getResultList();
	}
	
	public String insert(Category category) {
		Session ss = factory.getCurrentSession();
		
		try {
			String result = (String) ss.save(category);
			
			return result; 
		} catch (Exception ex) {
			System.out.println("exceptioned");
			return null;
		}
	}
	
	public void update(Category category) {
		Session ss = factory.getCurrentSession();
		
		ss.update(category);
	}
	
	public void delete(Category category) {
		Session ss = factory.getCurrentSession();
		
		ss.delete(category);
	}
	
	public Category find(String id) throws NoResultException {
		Session ss = factory.getCurrentSession();
		TypedQuery<Category> hql = ss.createQuery("FROM Category WHERE id = :id", Category.class);
		
		hql.setParameter("id", id);
		
		return hql.getSingleResult();
	}
}
