package com.green.finale.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.PostReport;
import com.green.finale.entity.PostReportId;

@Repository
public class PostReportDAO {
	@Autowired
	private SessionFactory factory;

	public void insert(PostReport report) {
		Session ss = factory.getCurrentSession();

		ss.save(report);
	}
	
	public PostReport find(PostReportId id) {
		Session ss = factory.getCurrentSession();
		
		return ss.get(PostReport.class, id);
	}
}
