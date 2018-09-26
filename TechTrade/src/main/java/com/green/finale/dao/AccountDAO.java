package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Account;

@Repository
public class AccountDAO {
	@Autowired
	private SessionFactory factory;
	
	public List<Account> getList() {
		Session ss = factory.getCurrentSession();
		TypedQuery<Account> hql = ss.createQuery("FROM Account", Account.class);
		
		return hql.getResultList();
	}
	
	public long insert(Account acc) {
		Session ss = factory.getCurrentSession();
		
		return (long) ss.save(acc);
	}
	
	public void update(Account acc) {
		Session ss = factory.getCurrentSession();
		
		ss.update(acc);
	}
	
	public void delete(Account acc) {
		Session ss = factory.getCurrentSession();
		
		ss.delete(acc);
	}
	
	public Account find(String username) {
		Session ss = factory.getCurrentSession();
		
		return ss.get(Account.class, username);
	}
}
