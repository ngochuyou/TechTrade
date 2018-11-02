package com.green.finale.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.finale.entity.PostReport;
import com.green.finale.entity.PostReportId;

@Repository
public class PostReportDAO {
	@Autowired
	private SessionFactory factory;

	private static final int MAX_RESULTS = 10;

	public List<PostReport> getList(int page) {
		Session ss = factory.getCurrentSession();
		TypedQuery<PostReport> hql = ss.createQuery("FROM PostReport", PostReport.class);

		hql.setFirstResult(page * MAX_RESULTS);
		hql.setMaxResults(MAX_RESULTS);

		return hql.getResultList();
	}

	public int deleteByPost(long postId) {
		Session ss = factory.getCurrentSession();
		Query<?> hql = ss.createQuery("DELETE FROM PostReport WHERE id.targetedPost.id = :postId");

		hql.setParameter("postId", postId);

		return hql.executeUpdate();
	}

	public long count(long postId) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Long> hql = ss.createQuery("SELECT COUNT(*) FROM PostReport WHERE id.targetedPost.id = :id",
				Long.class);

		hql.setParameter("id", postId);

		return hql.getSingleResult();
	}

	public long countUserInvolve(String targetedUsername) {
		Session ss = factory.getCurrentSession();
		TypedQuery<Long> hql = ss.createQuery(
				"SELECT COUNT(*) FROM PostReport WHERE id.targetedPost.createBy.username = :username", Long.class);

		hql.setParameter("username", targetedUsername);

		return hql.getSingleResult();
	}

	public void insert(PostReport report) {
		Session ss = factory.getCurrentSession();

		ss.save(report);
	}

	public PostReport find(PostReportId id) {
		Session ss = factory.getCurrentSession();

		return ss.get(PostReport.class, id);
	}
}
