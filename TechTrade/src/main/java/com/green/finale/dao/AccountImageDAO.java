package com.green.finale.dao;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.AccountImage;

@Repository
public class AccountImageDAO {
	@Autowired
	private SessionFactory factory;

	public long insert(AccountImage image) {
		Session ss = factory.getCurrentSession();

		return (long) ss.save(image);
	}

	public int delete(long id) {
		Session ss = factory.getCurrentSession();
		TypedQuery<?> hql = ss.createQuery("DELETE FROM AccountImage WHERE id = :id");

		hql.setParameter("id", id);

		return hql.executeUpdate();
	}


	public AccountImage getNewestImage() {
		Session ss = factory.getCurrentSession();
		TypedQuery<AccountImage> hql = ss.createQuery("FROM AccountImage ORDER BY id desc", AccountImage.class);

		hql.setMaxResults(1);

		return hql.getSingleResult();
	}
}
