package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Message;

@Repository
public class MessageDAO {
	@Autowired
	private SessionFactory factory;
	private static final int MAX_RESULT = 4;

	public List<Message> getList(String username, boolean isRead) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Message> hql = ss.createQuery("FROM Message WHERE receiver.id = :username AND read = :read", Message.class);

		hql.setParameter("username", username);
		hql.setParameter("read", isRead);
		hql.setMaxResults(MAX_RESULT);

		return hql.getResultList();
	}

	public long insert(Message mess) {
		Session ss = factory.getCurrentSession();

		return (long) ss.save(mess);
	}

	public void delete(Message mess) {
		Session ss = factory.getCurrentSession();

		ss.delete(mess);
	}

	public void update(Message mess) {
		Session ss = factory.getCurrentSession();

		ss.update(mess);
	}
}
