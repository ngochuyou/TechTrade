package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
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

	public String insert(Account acc) {
		Session ss = factory.getCurrentSession();

		return (String) ss.save(acc);
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

	public Account findByEmail(String email) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Account> query = ss.createQuery("From Account Where email = :email", Account.class);
		query.setParameter("email", email);
		Account account = null;
		try {
			account = query.getSingleResult();

		} catch (Exception e) {
			return null;
		}
		return account;
	}

	@SuppressWarnings("unchecked")
	public List<String> getRandomAccountIdList(int limit) {
		Session ss = factory.getCurrentSession();
		NativeQuery<?> nQuery = ss.createSQLQuery("SELECT account.username FROM account ORDER BY RAND() LIMIT :limit");

		nQuery.setParameter("limit", limit);

		return (List<String>) nQuery.getResultList();
	}
}
