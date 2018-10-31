package com.green.finale.dao;

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
	
	public void insert(UserReport report) {
		Session ss = factory.getCurrentSession(); 
		
		ss.save(report);
	}
	
	public UserReport find(UserReportId id) {
		Session ss = factory.getCurrentSession();
		
		return ss.get(UserReport.class, id);
	}
}
