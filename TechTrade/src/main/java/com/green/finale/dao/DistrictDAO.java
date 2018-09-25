package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.District;

@Repository
public class DistrictDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<District> getList() {
		Session ss = sessionFactory.getCurrentSession();
		TypedQuery<District> query = ss.createQuery("From District", District.class);
		return query.getResultList();
	}

	public District getListById(String id) {
		return sessionFactory.getCurrentSession().find(District.class, id);
	}

	public District getListByName(String name) {
		return sessionFactory.getCurrentSession().find(District.class, name);
	}

	public String insert(District district) {
		Session ss = sessionFactory.getCurrentSession();
		return (String) ss.save(district);
	}

	public void update(District district) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		ss.update(district);
	}

	public void delete(String id) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		ss.delete(getListById(id));
	}

}