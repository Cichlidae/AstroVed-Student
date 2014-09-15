package ru.premaservices.astroved.student.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.premaservices.astroved.student.pojo.Session;

@Repository
public class SessionManager {
	
	public final static long INTERVAL_DAY = 1000*60*60*24L;
	public final static long INTERVAL_WEEK = INTERVAL_DAY*7;
	public final static long INTERVAL_MOUNTH = INTERVAL_DAY*30;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session addSession (Session s) {
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		session.save(s);
		return s;
	}
	
	public Session updateSession (Session s) {
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		session.update(s);
		return s;
	}
	
	public Session removeSession (Integer id) {
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		Session s = (Session)session.get(Session.class, id);
		session.delete(s);
		return s;
	}

	@SuppressWarnings("unchecked")
	public List<Session> getSessions () {
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		return (List<Session>)session.createCriteria(Session.class).addOrder(Order.desc("startDate")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Session> getLastRecords (long interval) {
		if (interval > 0) {	
			org.hibernate.Session session = sessionFactory.getCurrentSession();	
			Date point = new Date(System.currentTimeMillis() - interval);			
			List<Session> list = (List<Session>)session.createCriteria(Session.class).add(Restrictions.ge("startDate", point)).addOrder(Order.desc("startDate")).list();
			for (Session s : list) {
				if (!Hibernate.isInitialized(s.getAuthor())) {
					Hibernate.initialize(s.getAuthor());
				}
			}	
			return list;
		}
		else {
			return new ArrayList<Session>(0);
		}
	}
	
	public Session getSession (Integer id) {
		org.hibernate.Session session = sessionFactory.getCurrentSession();	
		Session s = (Session)session.get(Session.class, id);
		s.getStudents();
		return s;
	}
	
	public Session getOpenSession () {
		org.hibernate.Session session = sessionFactory.getCurrentSession();	
		Criteria criteria = session.createCriteria(Session.class);
		criteria.add(Restrictions.ge("startDate", new Date(System.currentTimeMillis())));
		return (Session)criteria.uniqueResult();	
	}
	
}
