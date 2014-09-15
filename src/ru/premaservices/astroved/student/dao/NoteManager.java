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

import ru.premaservices.astroved.student.pojo.Note;
import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.astroved.student.pojo.User;

@Repository
public class NoteManager {

	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Note updateNote (Note note) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(note);
		return note;	
	}
	
	public Note removeNote (Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Note note = (Note)session.get(Note.class, id);
		session.delete(note);
		return note;	
	}
	
	@SuppressWarnings("unchecked")
	public List<Note> getNotes (int monthCount) {
		Session session = sessionFactory.getCurrentSession();	
		Date point = new Date(System.currentTimeMillis() - SessionManager.INTERVAL_MOUNTH*monthCount);	
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.ge("timestamp", point));
		criteria.addOrder(Order.desc("timestamp"));
		return (List<Note>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Note> getNotes (Date from, Date upto) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.and(Restrictions.ge("timestamp", from), Restrictions.le("timestamp", upto)));
		criteria.addOrder(Order.desc("timestamp"));
		return (List<Note>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Note> getNotes (Date from, Date upto, User author) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("author", author));
		criteria.add(Restrictions.and(Restrictions.ge("timestamp", from), Restrictions.le("timestamp", upto)));
		criteria.addOrder(Order.desc("timestamp"));
		return (List<Note>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Note> getNotes (Student student, Date from, Date upto) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("student", student));
		criteria.add(Restrictions.and(Restrictions.ge("timestamp", from), Restrictions.le("timestamp", upto)));
		criteria.addOrder(Order.desc("timestamp"));
		return (List<Note>)criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Note> getNotes (Student student, Date from, Date upto, User author) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Note.class);
		criteria.add(Restrictions.eq("student", student));
		criteria.add(Restrictions.eq("author", author));
		criteria.add(Restrictions.and(Restrictions.ge("timestamp", from), Restrictions.le("timestamp", upto)));
		criteria.addOrder(Order.desc("timestamp"));
		return (List<Note>)criteria.list();
	}
	
	public String getComment (Integer noteId) {
		Session session = sessionFactory.getCurrentSession();
		Note note = (Note)session.get(Note.class, noteId);
		return note.getComments();
	}
	
}
