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

	public List<Ward> getWardByIdDistrict(String idDistrict) {
		Session ss = sessionFactory.getCurrentSession();
		String queryStr = "From Ward  Where district.id like :idDistrict";
		TypedQuery<Ward> query = ss.createQuery(queryStr, Ward.class);
		query.setParameter("idDistrict", idDistrict);
		return query.getResultList();
	}

	public List<Ward> getDistrictId(String idWard) {
		Session ss = sessionFactory.getCurrentSession();
		String queryStr = "Select district From Ward  Where idWard like :idWard";
		TypedQuery<Ward> query = ss.createQuery(queryStr, Ward.class);
		query.setParameter("idWard", idWard);
		return query.getResultList();
	}
	
	public List<String> getWardIdListByWardId (String id){
		System.out.println(id);
		Session ss = sessionFactory.getCurrentSession();
		String queryStr = "Select id From Ward  Where district.id = (Select district.id From Ward where id = :wardId )";
		TypedQuery<String> query = ss.createQuery(queryStr, String.class);
		query.setParameter("wardId", id);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getRandomWardIdList(int limit) {
		Session ss = (Session) sessionFactory.getCurrentSession();
		
		NativeQuery<?> nativeQuery = ss.createSQLQuery("SELECT ward.id FROM ward ORDER BY RAND() LIMIT :limit");

		nativeQuery.setParameter("limit", limit);

		return (List<Integer>) nativeQuery.getResultList();
	}
}
