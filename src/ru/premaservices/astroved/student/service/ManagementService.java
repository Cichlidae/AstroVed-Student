package ru.premaservices.astroved.student.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.premaservices.astroved.student.dao.DiplomaManager;
import ru.premaservices.astroved.student.dao.HistoryManager;
import ru.premaservices.astroved.student.dao.NoteManager;
import ru.premaservices.astroved.student.dao.RegionManager;
import ru.premaservices.astroved.student.dao.SessionManager;
import ru.premaservices.astroved.student.dao.StudentManager;
import ru.premaservices.astroved.student.pojo.City;
import ru.premaservices.astroved.student.pojo.Country;
import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Diploma;
import ru.premaservices.astroved.student.pojo.History;
import ru.premaservices.astroved.student.pojo.Note;
import ru.premaservices.astroved.student.pojo.Payment;
import ru.premaservices.astroved.student.pojo.Session;
import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.astroved.student.pojo.Table;
import ru.premaservices.astroved.student.pojo.User;
import ru.premaservices.astroved.student.var.Constants;

@Service
public class ManagementService {
	
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
	
	@Autowired
	private HistoryManager historyManager;
	
	@Autowired
	private CustomExceptionFactory customExceptionFactory;
	
	@Autowired
	private NoteTemplateFactory noteTemplateFactory;
	
	@Autowired
	private Constants constants;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student updateStudent (Student student, User author) {	
		Student dbStudent = studentManager.updateStudent(student);
		historyManager.addHistory(new History(Table.STUDENTS, "add/update student: " + dbStudent.getUid() + " " + dbStudent.getFamily() + " " + dbStudent.getName(), author));		
		return dbStudent;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student enroll (String uid, User author) {
		Student student = studentManager.enroll(uid);
		Note note = noteTemplateFactory.getSimpleTemplate(NoteTemplateFactory.ENROLL_STUDENT_KEY).createNote(student, author);
		noteManager.updateNote(note);
		historyManager.addHistory(new History(Table.STUDENTS, "enroll student: " + student.getUid() + " " + student.getFamily() + " " + student.getName(), author));
		return student;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student exclude (String uid, User author) {		
		Student student = studentManager.exclude(uid);
		if (this.canExclude(student)) {
			Note note = noteTemplateFactory.getSimpleTemplate(NoteTemplateFactory.EXCLUDE_STUDENT_KEY).createNote(student, author);
			noteManager.updateNote(note);
			historyManager.addHistory(new History(Table.STUDENTS, "exclude student: " + student.getUid() + " " + student.getFamily() + " " + student.getName(), author));
		}
		return student;
	}
	
	public Student setAdvise (String uid, boolean permission, User author) { //TODO
		Student student = studentManager.setAdvise(uid, permission);	
		historyManager.addHistory(new History(Table.STUDENTS, "change advise flag of student: " + student.getUid() + " " + student.getFamily() + " " + student.getName(), author));
		return student;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student setAvatar (String uid, byte[] avatar, User author) {
		Student student = studentManager.setAvatar(uid, avatar);
		historyManager.addHistory(new History(Table.STUDENTS, "change avatar of student: " + student.getUid() + " " + student.getFamily() + " " + student.getName(), author));
		return student;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Session addSession (Session s, User author) {
		Session dbSession = sessionManager.addSession(s);
		historyManager.addHistory(new History(Table.SESSIONS, "add session: " + dbSession.getId() + " " + dbSession.getName(), author));
		return dbSession;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Session removeSession (Integer id, User author) {
		Session s = sessionManager.removeSession(id);
		historyManager.addHistory(new History(Table.SESSIONS, "remove session: " + s.getId() + " " + s.getName(), author));
		return s;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Country updateCountry (Country country) {
		return regionManager.updateCountry(country);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public City updateCity (City city) {
		return regionManager.updateCity(city);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Note updateNote (Note note) {
		return noteManager.updateNote(note);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Note removeNote (Integer id) {
		return noteManager.removeNote(id);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student passTest (String uid, User author) {	
		Student student = studentManager.getStudentWithTests(uid);
		Course currentCourse = student.getCourse();	
		switch (currentCourse) {
			case FIRST: student.getTests().setCourse_1_test(true); break;
			case SECOND:  student.getTests().setCourse_2_test(true); break;
			case THIRD:	 student.getTests().setCourse_3_test(true); break;
		}
		studentManager.updateStudent(student);
		return student;		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student passExam (String uid, User author) {	
		Student student = studentManager.getStudentWithTests(uid);
		Course currentCourse = student.getCourse();	
		switch (currentCourse) {
			case FIRST: student.getTests().setExam_1(true); break;
			case SECOND:  student.getTests().setExam_2(true); break;
			case THIRD:	 student.getTests().setExam_3(true); break;
		}
		studentManager.updateStudent(student);
		return student;		
	}
	
	public Student passExam (Student student, Integer diplomaNumber, Date diplomaDate, User author) throws ManagementException {
		Course currentCourse = student.getCourse();	
		Diploma dublicate = diplomaManager.findDiploma(currentCourse, diplomaNumber);
		if (dublicate != null) {
			throw customExceptionFactory.getManagementException(ManagementException.DIPLOMA_NUMBER_HAS_DIBLICATE);
		}
		switch (currentCourse) {
			case FIRST: {
				if (!student.getTests().getCourse_1_test()) {
					throw customExceptionFactory.getManagementException(ManagementException.COURSE_TEST_NOT_PASSED);
				}
				student.getTests().setExam_1(true);
				Diploma diploma = student.getTests().getDiploma_1();
				diploma.setNumber(diplomaNumber);
				diploma.setDiplomaDate(diplomaDate);
				diploma.setAuthor(author);		
				student.setCourse(Course.SECOND);
				break;
			}
			case SECOND: {
				if (!student.getTests().getCourse_2_test()) {
					throw customExceptionFactory.getManagementException(ManagementException.COURSE_TEST_NOT_PASSED);
				}
				student.getTests().setExam_2(true);
				Diploma diploma = student.getTests().getDiploma_2();
				diploma.setNumber(diplomaNumber);
				diploma.setDiplomaDate(diplomaDate);
				diploma.setAuthor(author);	
				student.setConsultant(true);			
				student.setCourse(Course.THIRD);
				break;
			}
			case THIRD: {
				if (!student.getTests().getCourse_3_test()) {
					throw customExceptionFactory.getManagementException(ManagementException.COURSE_TEST_NOT_PASSED);
				}
				student.getTests().setExam_3(true);
				Diploma diploma = student.getTests().getDiploma_3();
				diploma.setNumber(diplomaNumber);
				diploma.setDiplomaDate(diplomaDate);
				diploma.setAuthor(author);				
				student.setCourse(Course.FORTH);
				break;
			}
			case FORTH:	{
				student.getTests().setDiploma(true);
				Diploma diploma = student.getTests().getFinalDiploma();
				diploma.setNumber(diplomaNumber);
				diploma.setDiplomaDate(diplomaDate);
				diploma.setAuthor(author);			
				student.setCourse(Course.GRADUATE);
				break;
			}
			case ABITURIENT:
			case GRADUATE:
			default: {
				return student;
			}
		}
		Student dbStudent = studentManager.updateStudent(student);
		String noteKey = NoteTemplateFactory.NEXT_COURSE_STUDENT_KEY;
		
		Note note = noteTemplateFactory.getSimpleTemplate(dbStudent.getCourse() == Course.GRADUATE ? NoteTemplateFactory.GRADUATE_STUDENT_KEY : noteKey).createNote(dbStudent, author);
		noteManager.updateNote(note);
			
		historyManager.addHistory(new History(Table.STUDENTS, "change course of student: " + student.getUid() + " " + student.getFamily() + " " + student.getName() + " " + student.getCourse().name(), author));
		return dbStudent;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Session register (Student student, Session session, User author) {
		Session s = sessionManager.getSession(session.getId());
		s.getStudents().add(student);
		sessionManager.updateSession(s);
		historyManager.addHistory(new History(Table.SESSIONS, "register student: " + student.getUid() + " " + student.getFamily() + " " + student.getName() + " on " + session.getName(), author));
		return s; 
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Student pay (String uid, Payment payment, User author) throws ManagementException {
		Student student = studentManager.getStudentLazily(uid);
		if (payment.getSession() != null) {
			if (!student.getSessions().contains(payment.getSession())) {
				throw customExceptionFactory.getManagementException(ManagementException.NEEDS_TO_REGISTER_STUDENT_ON_SESSION_FIRST);
			}
		}
		student.getPaiments().add(payment);
		studentManager.updateStudent(student);		
		historyManager.addHistory(new History(Table.PAIMENTS, "get payment: " + student.getUid() + " " + student.getFamily() + " " + student.getName() + " " + payment.getSum(), author));
		
		this.updateNote(noteTemplateFactory.getPaymentTemplate(payment).createNote(student, author));		
		
		if (needEnroll(student)) {
			this.updateNote(noteTemplateFactory.getSimpleTemplate(NoteTemplateFactory.NEED_TO_ENROLL_STUDENT_KEY).createNote(student, author));	
		}		
		return student; 
	}
	
	@Transactional(readOnly = true)
	public Student createStudent () {
		Student student = new Student();
		student.setGroup(studentManager.getNewGroup());
		return student;
	}
	
	@Transactional(readOnly = true)
	public boolean needEnroll (Student student) {		
		if (student.getCourse().compareTo(Course.FIRST) < 0) {
			//check payment sum if student bought the first course and ready to study
			Integer sum = studentManager.getPaymentSum(student);
			if (sum >= constants.getMinSumToEnroll()) {
				return true;
			}
		}	
		return false;
	}

	public boolean canExclude (Student student) {
		if (student.getCourse().compareTo(Course.ABITURIENT) > 0) {
			if (student.getGroup().getId() != 6)
				return true;
		}
		return false;
	}
	
	public boolean canTest(Student student) {		
		switch (student.getCourse()) {
			case FIRST: return !student.getTests().getCourse_1_test();
			case SECOND: return !student.getTests().getCourse_2_test();
			case THIRD:	return !student.getTests().getCourse_3_test();
		}		
		return false;
	}
	
	public boolean canExam(Student student) {
		switch (student.getCourse()) {
			case FIRST: return !student.getTests().getExam_1();
			case SECOND: return !student.getTests().getExam_2();
			case THIRD:	return !student.getTests().getExam_3();
		}		
		return false;
	}

	public boolean canProceed(Student student) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean canGraduate(Student student) {
		if (student.getCourse().compareTo(Course.FORTH) == 0) return true;
		return false;
	}

}
