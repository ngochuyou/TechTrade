package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Ward;

@Repository
public class WardDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Ward> getList() {
		Session ss = sessionFactory.getCurrentSession();
		TypedQuery<Ward> query = ss.createQuery("From Ward", Ward.class);
		return query.getResultList();
	}

	public Ward find(String id) {
		return sessionFactory.getCurrentSession().find(Ward.class, id);
	}

	public Ward getWardByName(String name) {
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

	public void delete(Ward ward) {
		Session ss = (Session) sessionFactory.getCurrentSession();

		ss.delete(ward);
	}

	public List<Ward> getWardByIdCity(String idDistrict) {
		Session ss = sessionFactory.getCurrentSession();
		String queryStr = "From Ward  Where district.id like :idDistrict";
		TypedQuery<Ward> query = ss.createQuery(queryStr, Ward.class);
		query.setParameter("idDistrict", idDistrict);
		return query.getResultList();
	}

	public List<Integer> getRandomWardIdList(int limit) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		NativeQuery<?> nativeQuery = ss.createSQLQuery("SELECT ward.id FROM ward ORDER BY RAND() LIMIT :limit");

		nativeQuery.setParameter("limit", limit);

		return (List<Integer>) nativeQuery.getResultList();
// branch 'master' of https://github.com/ngochuyou/TechTrade.git
	}
}
