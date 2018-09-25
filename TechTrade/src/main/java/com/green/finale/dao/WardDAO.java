package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Ward;


@Repository
public class WardDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List<Ward> getList(){
		Session ss = sessionFactory.getCurrentSession();
		TypedQuery<Ward> query = ss.createQuery("From Ward", Ward.class);
		return query.getResultList();
	}
	
	public Ward getListById(String id) {
		return sessionFactory.getCurrentSession().find(Ward.class, id);
	}

	public Ward getListByName(String name) {
		return sessionFactory.getCurrentSession().find(Ward.class, name);
	}

	public String insert(Ward ward) {
		Session ss = sessionFactory.getCurrentSession();
		return (String) ss.save(ward);
	}

	public void update(Ward ward) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		ss.update(ward);
	}

	public void deleteById(String id) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		ss.delete(getListById(id));
	}
	
	public void delete(Ward ward) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		ss.delete(ward);
	}
}
