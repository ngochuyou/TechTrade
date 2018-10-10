package com.green.finale.dao;

import java.util.List;

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

	public List<Category> getInUseList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Category> hql = ss.createQuery("FROM Category WHERE inUse = true", Category.class);

		return hql.getResultList();
	}

	public List<Category> getUnUsedList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Category> hql = ss.createQuery("FROM Category WHERE inUse = false", Category.class);

		return hql.getResultList();
	}

	public int insert(Category category) {
		Session ss = factory.getCurrentSession();

		return (int) ss.save(category);
	}

	public void update(Category category) {
		Session ss = factory.getCurrentSession();

		ss.update(category);
	}

	public Category find(int id) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Category> hql = ss.createQuery("FROM Category WHERE id = :id", Category.class);

		hql.setParameter("id", id);

		Category resultCate = null;

		try {
			resultCate = hql.getSingleResult();
		} catch (Exception ex) {
			return null;
		}

		ss.evict(resultCate);

		return resultCate;
	}
}
