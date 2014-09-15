package ru.premaservices.astroved.student.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.premaservices.astroved.student.pojo.History;

@Repository
public class HistoryManager {

	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public History addHistory (History history) {
		Session session = sessionFactory.getCurrentSession();
		session.save(history);
		return history;
	}
	
	@SuppressWarnings("unchecked")
	public List<History> getHistory (Date from, Date upto) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(History.class);
		criteria.add(Restrictions.and(Restrictions.ge("timestamp", from), Restrictions.le("timestamp", upto)));
		criteria.addOrder(Order.desc("timestamp"));
		return (List<History>)criteria.list();
	}
	
}
