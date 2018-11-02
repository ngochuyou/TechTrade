package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.UserReport;
import com.green.finale.entity.UserReportId;

@Repository
public class UserReportDAO {
	@Autowired
	private SessionFactory factory;

	private static final int MAX_RESULTS = 10;

	public List<UserReport> getList(int page) {
		Session ss = factory.getCurrentSession();
		TypedQuery<UserReport> hql = ss.createQuery("FROM UserReport", UserReport.class);

		hql.setFirstResult(page * MAX_RESULTS);
		hql.setMaxResults(MAX_RESULTS);

		return hql.getResultList();
	}

	public long count(String targetedUsername) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Long> hql = ss.createQuery("SELECT COUNT(*) FROM UserReport WHERE id.targetedUser.username = :username", Long.class);
		
		hql.setParameter("username", targetedUsername);
		
		return hql.getSingleResult();
	}
		
	public void insert(UserReport report) {
		Session ss = factory.getCurrentSession();

		ss.save(report);
	}

	public UserReport find(UserReportId id) {
		Session ss = factory.getCurrentSession();

		return ss.get(UserReport.class, id);
	}
}
