package ru.premaservices.astroved.student.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import ru.premaservices.astroved.student.pojo.validation.TestDataConstraint;

@Entity
@Table(name="TESTS") @TestDataConstraint
public class TestData {

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "student"))
	@Id @GeneratedValue(generator = "generator")
	@Column(name="UID", length=36, nullable = false, unique = true)
	private String uid;
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Student student;
	
	@Column(name="COURSE_TEST_1")
	private Boolean course_1_test;
	
	@Column(name="COURSE_TEST_2")
	private Boolean course_2_test;
	
	@Column(name="COURSE_TEST_3")
	private Boolean course_3_test;
	
	@Column(name="EXAM_1")
	private Boolean exam_1;
	
	@Column(name="EXAM_2")
	private Boolean exam_2;
	
	@Column(name="EXAM_3")
	private Boolean exam_3;
	
	@Column(name="DIPLOMA")
	private Boolean diploma;
	
	@NotNull
	@Column(name="START_DATE")
	@Temporal(value=TemporalType.DATE)
	private Date startDate;
	
	@Column(name="PWD_SENT")
	private Boolean passwordSend;
	
	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DIPLOMA_1", nullable = false, unique = true)
	private Diploma diploma_1;
	
	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DIPLOMA_2", nullable = false, unique = true)
	private Diploma diploma_2;
	
	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DIPLOMA_3", nullable = false, unique = true)
	private Diploma diploma_3;
	
	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="FINAL_DIPLOMA", nullable = false, unique = true)
	private Diploma finalDiploma;
	
	public TestData () {
	}
	
	public TestData (Student student) {
		setStudent(student);
		setStartDate(new Date(System.currentTimeMillis()));
		setDiploma_1(new Diploma(student, Course.FIRST));
		setDiploma_2(new Diploma(student, Course.SECOND));
		setDiploma_3(new Diploma(student, Course.THIRD));
		setFinalDiploma(new Diploma(student, Course.FORTH));
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Boolean getCourse_1_test() {
		return course_1_test;
	}

	public void setCourse_1_test(Boolean course_1_test) {
		this.course_1_test = course_1_test;
	}

	public Boolean getCourse_2_test() {
		return course_2_test;
	}

	public void setCourse_2_test(Boolean course_2_test) {
		this.course_2_test = course_2_test;
	}

	public Boolean getCourse_3_test() {
		return course_3_test;
	}

	public void setCourse_3_test(Boolean course_3_test) {
		this.course_3_test = course_3_test;
	}

	public Boolean getExam_1() {
		return exam_1;
	}

	public void setExam_1(Boolean exam_1) {
		this.exam_1 = exam_1;
	}

	public Boolean getExam_2() {
		return exam_2;
	}

	public void setExam_2(Boolean exam_2) {
		this.exam_2 = exam_2;
	}

	public Boolean getExam_3() {
		return exam_3;
	}

	public void setExam_3(Boolean exam_3) {
		this.exam_3 = exam_3;
	}

	public Boolean getDiploma() {
		return diploma;
	}

	public void setDiploma(Boolean diploma) {
		this.diploma = diploma;
	}

	public Boolean getPasswordSend() {
		return passwordSend;
	}

	public void setPasswordSend(Boolean passwordSend) {
		this.passwordSend = passwordSend;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Diploma getDiploma_1() {
		return diploma_1;
	}

	public void setDiploma_1(Diploma diploma_1) {
		this.diploma_1 = diploma_1;
	}

	public Diploma getDiploma_2() {
		return diploma_2;
	}

	public void setDiploma_2(Diploma diploma_2) {
		this.diploma_2 = diploma_2;
	}

	public Diploma getDiploma_3() {
		return diploma_3;
	}

	public void setDiploma_3(Diploma diploma_3) {
		this.diploma_3 = diploma_3;
	}

	public Diploma getFinalDiploma() {
		return finalDiploma;
	}

	public void setFinalDiploma(Diploma finalDiploma) {
		this.finalDiploma = finalDiploma;
	}
	
}
