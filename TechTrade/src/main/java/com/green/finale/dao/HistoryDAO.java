package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.History;

@Repository
public class HistoryDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<History> getList() {
		Session ss = sessionFactory.getCurrentSession();
		TypedQuery<History> query = ss.createQuery("From History", History.class);
		return query.getResultList();
	}

	public History getListById(String id) {
		return sessionFactory.getCurrentSession().find(History.class, id);
	}

	public Long insert(History history) {
		Session ss = sessionFactory.getCurrentSession();
		return (Long) ss.save(history);
	}

	public void update(History history) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		ss.update(history);
	}

	public void delete(String id) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		ss.delete(getListById(id));
	}
}
