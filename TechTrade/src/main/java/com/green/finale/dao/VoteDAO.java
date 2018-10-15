package com.green.finale.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Vote;
import com.green.finale.entity.VoteId;

@Repository
public class VoteDAO {
	
	@Autowired
	private SessionFactory factory;
	
	public Vote find(VoteId id) {
		Session ss = factory.getCurrentSession();
		
		return ss.get(Vote.class, id);
	}
	
	public VoteId insert(Vote vote) {
		Session ss = factory.getCurrentSession();
		
		return (VoteId) ss.save(vote);
	}
}
