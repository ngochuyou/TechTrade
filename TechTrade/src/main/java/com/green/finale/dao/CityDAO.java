package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.City;

@Repository
public class CityDAO {

	@Autowired
	private SessionFactory factory;

	public List<City> getList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<City> hql = ss.createQuery("FROM City", City.class);

		return hql.getResultList();
	}

	public String insert(City city) {
		Session ss = factory.getCurrentSession();

		return (String) ss.save(city);
	}

	public void update(City city) {
		Session ss = factory.getCurrentSession();

		ss.update(city);
	}

	public void delete(City city) {
		Session ss = factory.getCurrentSession();

		ss.delete(city);
	}
	
//	public City getCity() {
//		
//	}
}
