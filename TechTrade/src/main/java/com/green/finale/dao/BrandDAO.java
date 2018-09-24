package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Brand;

@Repository
public class BrandDAO {
	@Autowired
	private SessionFactory factory;
	
	public List<Brand> getList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Brand> hql = ss.createQuery("FROM Brand", Brand.class);
		
		return hql.getResultList();
	}
	
	public String insert(Brand brand) {
		Session ss = factory.getCurrentSession();
		
		return (String) ss.save(brand);
	}
	
	public void update(Brand brand) {
		Session ss = factory.getCurrentSession();
		
		ss.update(brand);
	}
	
	public void delete(Brand brand) {
		Session ss = factory.getCurrentSession();
		
		ss.delete(brand);
	}
}
