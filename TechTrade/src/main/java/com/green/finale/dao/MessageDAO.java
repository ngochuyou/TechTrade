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

	public List<Message> getReceivedList(String username, boolean isRead, int page) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Message> hql = ss.createQuery("FROM Message WHERE receiver.id = :username AND read = :read AND deletedByReceiver = :deleted", Message.class);

		hql.setParameter("username", username);
		hql.setParameter("read", isRead);
		hql.setParameter("deleted", false);
		
		if (isRead == true) {
			hql.setFirstResult(page * MAX_RESULT);
			hql.setMaxResults(MAX_RESULT);
		}

		return hql.getResultList();
	}
	
	public int deleteBySender(long id) {
		Session ss = factory.getCurrentSession();
		TypedQuery<?> hql = ss.createQuery("UPDATE Message SET deletedBySender = :deleted WHERE id = :id");
		
		hql.setParameter("deleted", true);
		hql.setParameter("id", id);
		
		return hql.executeUpdate();
	}
	
	public int deleteByReceiver(long id) {
		Session ss = factory.getCurrentSession();
		TypedQuery<?> hql = ss.createQuery("UPDATE Message SET deletedByReceiver = :deleted WHERE id = :id");
		
		hql.setParameter("deleted", true);
		hql.setParameter("id", id);
		
		return hql.executeUpdate();
	}
	
	public Message find(long id) {
		Session ss = factory.getCurrentSession();
		
		return ss.get(Message.class, id);
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
