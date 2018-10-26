package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.Pin;
import com.green.finale.entity.PinId;

@Repository
public class PinDAO {

	@Autowired
	private SessionFactory factory;

	public List<Pin> getList(String username, long postId) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Pin> hql = ss.createQuery("FROM Pin as p where p.account.username like :username And p.post.id = :post ", Pin.class);
		hql.setParameter("post", postId);
		hql.setParameter("username", username);
		return hql.getResultList();
	}
	
	public void insert(Pin pin) {
		Session ss = factory.getCurrentSession();
		ss.save(pin);
	}
	
	public Pin find(PinId id) {
		Session ss = factory.getCurrentSession();
		return ss.get(Pin.class, id);
	}
	
	public boolean isEmptyPost(long post) {
		Session ss = factory.getCurrentSession();
		String queryStr = "From Pin as p Where p.post.id like :post";
		TypedQuery<Pin> query = ss.createQuery(queryStr, Pin.class);
		query.setParameter("post", post);
		if(query.getResultList() == null) {
			return true;
		}
		return false;
	}
	
	public void delete(Pin pin) {
		Session ss = factory.getCurrentSession();
		ss.delete(pin);
	}
	

}
