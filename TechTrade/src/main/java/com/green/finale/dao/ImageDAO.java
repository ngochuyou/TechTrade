package com.green.finale.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Image;

@Repository
public class ImageDAO {
	@Autowired
	private SessionFactory factory;
	
	public long insert(Image image) {
		Session ss = factory.getCurrentSession();
		
		return (long) ss.save(image);
	}
	
}
