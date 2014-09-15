package ru.premaservices.astroved.student.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.premaservices.astroved.student.dao.DiplomaManager;
import ru.premaservices.astroved.student.dao.NoteManager;
import ru.premaservices.astroved.student.dao.RegionManager;
import ru.premaservices.astroved.student.dao.SessionManager;
import ru.premaservices.astroved.student.dao.StudentManager;
import ru.premaservices.astroved.student.pojo.City;
import ru.premaservices.astroved.student.pojo.Country;
import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Diploma;
import ru.premaservices.astroved.student.pojo.Group;
import ru.premaservices.astroved.student.pojo.Note;
import ru.premaservices.astroved.student.pojo.Payment;
import ru.premaservices.astroved.student.pojo.Session;
import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.astroved.student.pojo.StudentContainer;
import ru.premaservices.astroved.student.pojo.StudentFilter;
import ru.premaservices.astroved.student.pojo.User;

@Service
public class PresentationService {

	@Autowired
	private StudentManager studentManager;
	
	@Autowired
	private NoteManager noteManager;
	
	@Autowired
	private DiplomaManager diplomaManager;
	
	@Autowired
	private RegionManager regionManager;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Transactional(readOnly = true)
	public List<StudentContainer> getStudents () {		
		return asStudents(studentManager.getStudents());
	}
	
	protected List<StudentContainer> asStudents (List<Object[]> list) {
		List<StudentContainer> students = new ArrayList<StudentContainer>(list.size());
		for (Object[] o : list) {
			StudentContainer st = new StudentContainer();
			st.setUid((String)o[0]);
			st.setFamily((String)o[1]);
			st.setName((String)o[2]);
			st.setPatronimic((String)o[3]);
			st.setSpiritualName((String)o[4]);
			st.setCity((City)o[5]);
			st.setCourse((Course)o[6]);
			st.setGroup((Group)o[7]);
			st.setTel((String)o[8]);
			st.setEmail((String)o[9]);
			students.add(st);
		}
		return students;
	}
	
	@Transactional(readOnly = true)
	public  List<StudentContainer> getStudents (String family) {
		return asStudents(studentManager.getStudents("family", family, true));
	}
	
	@Transactional(readOnly = true)
	public  List<StudentContainer> getStudents (StudentFilter filter) {
		return asStudents(studentManager.getStudents(filter));
	}
	
	@Transactional(readOnly = true)
	public  List<StudentContainer> getStudents (Country country) {
		return asStudents(studentManager.getStudents("city.country", country));
	}
	
	@Transactional(readOnly = true)
	public  List<StudentContainer> getStudents (City city) {
		return asStudents(studentManager.getStudents("city", city));
	}
	
	@Transactional(readOnly = true)
	public  List<StudentContainer> getStudents (Course course) {
		return asStudents(studentManager.getStudents("course", course));
	}
	
	@Transactional(readOnly = true)
	public List<StudentContainer> getStudents (Group group) {
		return asStudents(studentManager.getStudents(group));
	}
	
	@Transactional(readOnly = true)
	public List<Student> getStudentsWithTests (boolean all) {
		return studentManager.getStudentsWithTests(all);
	}
	
	@Transactional(readOnly = true)
	public Student getStudent (String uid) { 
		return studentManager.getStudent(uid);
	}
	
	@Transactional(readOnly = true)
	public HashMap<Course, Integer> getPaymentInfo (Student student) {
		return studentManager.getPaymentInfo(student);
	}
	
	@Transactional(readOnly = true)
	public SortedSet<Payment> getPayments (String uid) {
		return studentManager.getPayments(uid);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getLastDayNotes () {
		GregorianCalendar calendar = new GregorianCalendar();
		Date today = calendar.getTime();
		calendar.add(Calendar.DATE, -1);
		return noteManager.getNotes(calendar.getTime(), today);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getLastWeekNotes () {
		GregorianCalendar calendar = new GregorianCalendar();
		Date today = calendar.getTime();
		calendar.add(Calendar.DATE, -7);
		return noteManager.getNotes(calendar.getTime(), today);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getLastMounthNotes () {
		return noteManager.getNotes(1);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getNotes (Student student, Date from, Date upto) {
		return noteManager.getNotes(student, from, upto);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getNotes (Student student, Date from, Date upto, User author) {
		return noteManager.getNotes(student, from, upto, author);
	}
	
	@Transactional(readOnly = true)
	public String getComment (Integer noteId) {
		return noteManager.getComment(noteId);
	}
	
	@Transactional(readOnly = true)
	public List<Diploma> getDiplomas () {
		return diplomaManager.getDiplomas();
	}
	
	@Transactional(readOnly = true)
	public List<Diploma> getDiplomas (Course course) {
		return diplomaManager.getDiplomas(course);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getNotes (Date from, Date upto, User author) {
		return noteManager.getNotes(from, upto, author);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getNotes (Date from, Date upto) {
		return noteManager.getNotes(from, upto);
	}
	
	@Transactional(readOnly = true)
	public List<Note> getNotes (int monthCount) {
		return noteManager.getNotes(monthCount);
	}
	
	@Transactional(readOnly = true)
	public List<Country> getCountries () {
		return regionManager.getCountries();
	}
	
	@Transactional(readOnly = true)
	public List<Country> getCountriesWithCities () {
		return regionManager.getCountriesWithCities();
	}
	
	@Transactional(readOnly = true)
	public Country getCountry (Integer id) {
		Country country = regionManager.getCountry(id);
		country.getCities();
		return country;
	}
	
	@Transactional(readOnly = true)
	public Country getCountryLazily (Integer id) {
		Country country = regionManager.getCountry(id);
		return country;
	}
	
	@Transactional(readOnly = true)
	public City getCity (Integer id) {
		City city = regionManager.getCity(id);
		return city;
	}
	
	@Transactional(readOnly = true)
	public List<Session> getSessions () {
		return sessionManager.getSessions();
	}
	
	@Transactional(readOnly = true)
	public List<Session> getLastRecords (long interval) {
		return sessionManager.getLastRecords(interval);
	}
	
	@Transactional(readOnly = true)
	public Session getSession (Integer id) {
		return sessionManager.getSession(id);
	}
	
	@Transactional(readOnly = true)
	public HashMap<Course, Diploma> getLastDiplomas () {
		HashMap<Course, Diploma> map = new HashMap<Course, Diploma>(3);
		map.put(Course.FIRST, diplomaManager.getLastDiploma(Course.FIRST));
		map.put(Course.SECOND, diplomaManager.getLastDiploma(Course.SECOND));
		map.put(Course.THIRD, diplomaManager.getLastDiploma(Course.THIRD));
		return map;
	}
	
	@Transactional(readOnly = true)
	public Session getOpenSession () {
		Session s = sessionManager.getOpenSession();
		if (s != null) s.getStudents();
		return s;
	}
	
	@Transactional(readOnly = true)
	public List<Group> getGroups () {
		return studentManager.getGroups();
	}
	
	@Transactional(readOnly = true)
	public byte[] getStudentAvatar (String uid) {
		return studentManager.getAvatar(uid);
	}
	
}
