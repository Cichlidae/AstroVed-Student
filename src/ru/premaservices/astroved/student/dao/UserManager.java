package ru.premaservices.astroved.student.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.premaservices.astroved.student.pojo.User;

@Repository
public class UserManager {

	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public User updateUser (User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		return user;
	}
	
	public User removeUser (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User)session.get(User.class, id);
		session.delete(user);
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUserList () {
		Session session = sessionFactory.getCurrentSession();
		return (List<User>)session.createCriteria(User.class).list();
	}
	
	public User getUser (String login) {
		Session session = sessionFactory.getCurrentSession();
		return (User)session.createCriteria(User.class).add(Restrictions.eq("login", login)).uniqueResult();
	}
	
	public User getUser (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (User)session.get(User.class, id);
	}
	
}
