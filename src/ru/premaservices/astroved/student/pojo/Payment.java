package ru.premaservices.astroved.student.pojo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Range;

import ru.premaservices.astroved.student.pojo.validation.PaymentConstraint;

@Entity
@Table(name="PAIMENT") @PaymentConstraint
public class Payment implements Comparable<Payment> {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UID")
	private Student student;
	
	@NotNull
	@Column(name="DATE_PMNT")
	@Temporal(value=TemporalType.DATE)
	private Date paimentDate;
	
	@Range(min=1)
	@Column(name="SUM")
	private Integer sum;
	
	@Column(name="COURSE")
	@Enumerated(EnumType.ORDINAL)
	private Course course;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="SESSION")
	private Session session;
	
	@Column(name="MASTER_CLASS", length=50)
	private String masterClass;
	
	@Lob @Basic(fetch=FetchType.EAGER)
	@Column(name="COMMENT")
	private String comment;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="AUTHOR")
	private User author;

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
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

	public Date getPaimentDate() {
		return paimentDate;
	}

	public void setPaimentDate(Date paimentDate) {
		this.paimentDate = paimentDate;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getMasterClass() {
		return masterClass;
	}

	public void setMasterClass(String masterClass) {
		this.masterClass = masterClass;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int compareTo(Payment that) {
		if (that == null) {
	       return -1;
	    }
		return that.getPaimentDate().compareTo(this.getPaimentDate());
	}
	
}
