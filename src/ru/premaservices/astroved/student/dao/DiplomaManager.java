package ru.premaservices.astroved.student.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Diploma;

@Repository
public class DiplomaManager {

	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Diploma findDiploma (Course course, Integer number) {
		Session session = sessionFactory.getCurrentSession();
		return (Diploma)session.createCriteria(Diploma.class).add(
				Restrictions.and(Restrictions.eq("course", course), Restrictions.eq("number", number)
		)).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Diploma> getDiplomas () {
		Session session = sessionFactory.getCurrentSession();	
		Criteria criteria = session.createCriteria(Diploma.class);
		criteria.addOrder(Order.asc("course"));
		criteria.addOrder(Order.asc("number"));
		
		List<Diploma> list = (List<Diploma>)criteria.list();
		for (Diploma diploma : list) {
			diploma.getStudent();
		}	
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Diploma> getDiplomas (Course course) {
		Session session = sessionFactory.getCurrentSession();	
		Criteria criteria = session.createCriteria(Diploma.class);
		criteria.add(Restrictions.eq("course",  course));				
		criteria.addOrder(Order.asc("course"));
		criteria.addOrder(Order.asc("number"));
		
		List<Diploma> list = (List<Diploma>)criteria.list();
		for (Diploma diploma : list) {
			diploma.getStudent();
		}	
		return list;
	}
	
	public Diploma getLastDiploma (Course course) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Diploma.class);
		criteria.add(Restrictions.eq("course", course));
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.max("number"));
		return (Diploma)criteria.uniqueResult();
	}
	
}
