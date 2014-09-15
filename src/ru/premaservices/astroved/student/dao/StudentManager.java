package ru.premaservices.astroved.student.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Group;
import ru.premaservices.astroved.student.pojo.Payment;
import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.astroved.student.pojo.StudentFilter;
import ru.premaservices.astroved.student.pojo.TestData;
import ru.premaservices.util.FileUtil;

@Repository
public class StudentManager {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Student updateStudent (Student student) {
		Session session = sessionFactory.getCurrentSession();
		if (student.getGroup() == null) {
			Group group = (Group)session.get(Group.class, 4); //new
			student.setGroup(group);
		}	
		session.saveOrUpdate(student);
		return student;
	}
	
	public Student enroll (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Group group = (Group)session.get(Group.class, 5); //common
		Student student = (Student)session.get(Student.class, uid);
		student.setCourse(Course.FIRST);
		student.setGroup(group);	
		TestData tests = new TestData(student);
		student.setTests(tests);	
		session.saveOrUpdate(student);
		return student;
	}
	
	public Student exclude (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Group group = (Group)session.get(Group.class, 6); //excluded
		Student student = (Student)session.get(Student.class, uid);
		student.setGroup(group);
		session.update(student);
		return student;
	}
	
	public Student getStudent (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		if (!Hibernate.isInitialized(student.getComments())) {
			Hibernate.initialize(student.getComments());
		}
		if (!Hibernate.isInitialized(student.getTests())) {
			Hibernate.initialize(student.getTests());
		}
		student.getSessions();
		student.getImportantNotes();
		return student;
	}
	
	public SortedSet<Payment> getPayments (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		return student.getPaiments();
	}
	
	public Student getStudentWithTests (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		if (!Hibernate.isInitialized(student.getTests())) {
			Hibernate.initialize(student.getTests());
		}
		return student;
	}
	
	public Student getStudentLazily (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		return student;
	}
	
	public byte[] getAvatar (String uid) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		return student.getAvatar();
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> getStudentsWithTests (boolean all) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		if (!all) {
			criteria.add(
					Restrictions.and(Restrictions.not(Restrictions.eq("group.id", 3)), Restrictions.not(Restrictions.eq("group.id", 6)))
			); //not excluded & not archived
		}
		else {
			criteria.add(Restrictions.not(Restrictions.eq("group.id", 3))); //not excluded
		}
		List<Student> list = (List<Student>)criteria.list();				
		return list;												
	}
	
	public List<Object[]> getStudents () {
		return getStudents(null, null);
	}
	
	public List<Object[]> getStudents (StudentFilter filter) {
		HashMap<String, Object> map = new HashMap<String, Object>(4);
		map.put("city.country", filter.getCountry());
		map.put("cit", filter.getCity());
		map.put("course", filter.getCourse());	
		map.put("group", filter.getGroup());
		return getStudents(map, false);
	}
	
	public List<Object[]> getStudents (String selectionProperty, Object selectionValue) {
		return this.getStudents(selectionProperty, selectionValue, false);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getStudents (String selectionProperty, Object selectionValue, boolean all) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		if (!all) {
			criteria.add(
					Restrictions.and(Restrictions.not(Restrictions.eq("group.id", 3)), Restrictions.not(Restrictions.eq("group.id", 6)))
			); //not excluded & not archived
		}
		if (selectionProperty != null && selectionValue != null) {
			criteria.add(Restrictions.eq(selectionProperty, selectionValue));
		}
		
		criteria = prepareStudentProjectionList(criteria);
		return (List<Object[]>)criteria.list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getStudents (Group group) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class).add(Restrictions.eq("group", group));
		criteria = prepareStudentProjectionList(criteria);
		return (List<Object[]>)criteria.list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getStudents (HashMap<String, Object> map, boolean all) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Student.class);
		if (!map.containsKey("group") && !all) {
			criteria.add(
					Restrictions.and(Restrictions.not(Restrictions.eq("group.id", 3)), Restrictions.not(Restrictions.eq("group.id", 6)))
			); //not excluded & not archived
		}
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {		
			String key = keys.next();
			criteria.add(Restrictions.eq(key, map.get(key)));			
		}
			
		criteria = prepareStudentProjectionList(criteria);
		return (List<Object[]>)criteria.list();	
	}
	
	protected Criteria prepareStudentProjectionList (Criteria criteria) {
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.id());
		projections.add(Projections.property("family"));
		projections.add(Projections.property("name"));
		projections.add(Projections.property("patronimic"));
		projections.add(Projections.property("spiritualName"));
		projections.add(Projections.property("city"));
		projections.add(Projections.property("course"));
		projections.add(Projections.property("group"));
		projections.add(Projections.property("tel_1"));
		projections.add(Projections.property("email_1"));
		criteria.setProjection(Projections.distinct(projections));
		criteria.addOrder(Order.asc("family"));
		criteria.addOrder(Order.asc("name"));
		return criteria;
	}
	
	public Student setAdvise (String uid, boolean permission) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		if (student.getCourse().compareTo(Course.FIRST) > 0) {
			student.setConsultant(permission);
			session.update(student);
		}
		return student;
	}
	
	public Student setAvatar (String uid, byte[] avatar) {
		Session session = sessionFactory.getCurrentSession();
		Student student = (Student)session.get(Student.class, uid);
		
		try {
			student.setAvatar(FileUtil.resizeImage(avatar, 300, FileUtil.RESIZE_ONLY_MINIMIZE));
		}
		catch (IOException e) {
			throw new HibernateException(e);
		}
		return student;	
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<Course, Integer> getPaymentInfo (Student student) {
		Session session = sessionFactory.getCurrentSession();
		HashMap<Course, Integer> map = new HashMap<Course, Integer>(4);
		
		Criteria criteria = session.createCriteria(Payment.class);
		criteria.add(Restrictions.eq("student", student));
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.property("course"));
		criteria.setProjection(Projections.sum("sum"));
		criteria.setProjection(Projections.groupProperty("course"));
		criteria.addOrder(Order.asc("course"));
	
		List<Object[]> list = (List<Object[]>)criteria.list();
		for (Object[] o : list) {
			map.put((Course)o[0], (Integer)o[1]);
		}
		return map;
	}
	
	public Integer getPaymentSum (Student student) {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(Payment.class);
		criteria.add(Restrictions.eq("student", student));
		criteria.setProjection(Projections.sum("sum"));
		
		Integer sum = (Integer)criteria.uniqueResult();
		return sum;
	}
	
	public Group getNewGroup () {
		Session session = sessionFactory.getCurrentSession();
		return (Group)session.get(Group.class, 4); //new
	}
	
	@SuppressWarnings("unchecked")
	public List<Group> getGroups () {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Group.class);
		return (List<Group>)criteria.list();
	}
	
}
