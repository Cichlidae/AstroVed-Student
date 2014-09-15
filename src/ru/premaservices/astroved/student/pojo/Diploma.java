package ru.premaservices.astroved.student.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="DIPLOMAS")
public class Diploma {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UID")
	private Student student;
	
	@NotNull
	@Column(name="COURSE")
	@Enumerated(EnumType.ORDINAL)
	private Course course;
	
	@Range(min=1)
	@Column(name="NUMBER")
	private Integer number;
	
	@Column(name="DATE_OF_GETTING")
	@Temporal(value=TemporalType.DATE)
	private Date diplomaDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="AUTHOR")
	private User author;
	
	public Diploma () {
	}
	
	public Diploma (Student student, Course course) {
		setStudent(student);
		if (course.compareTo(Course.FIRST) < 0 && course.compareTo(Course.FORTH) > 0)  
			throw new IllegalArgumentException("course must be only 1-4");
		else 
			setCourse(course);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getDiplomaDate() {
		return diplomaDate;
	}

	public void setDiplomaDate(Date diplomaDate) {
		this.diplomaDate = diplomaDate;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
}
