package ru.premaservices.astroved.student.pojo;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.validator.constraints.NotBlank;

import ru.premaservices.astroved.student.pojo.validation.SessionConstraint;
import ru.premaservices.extention.spring.hibernate.validation.constraints.Unique;

@Entity
@Table(name="SESSIONS") @Unique(names = {"name", "startDate"}) @SessionConstraint
public class Session implements Comparable<Session> {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@NotBlank 
	@Column(name="NAME", length=70, nullable = false, unique = true)
	private String name;
	
	@NotNull @Future
	@Column(name="START_DATE", nullable = false, unique = true)
	@Temporal(value=TemporalType.DATE)
	private Date startDate;
	
	@NotNull @Future
	@Column(name="FINAL_DATE", nullable = false)
	@Temporal(value=TemporalType.DATE)
	private Date finalDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="AUTHOR", nullable = false)
	private User author;
	
	@ManyToMany(mappedBy="sessions", fetch=FetchType.LAZY)
	@Cascade(CascadeType.SAVE_UPDATE)
	@Sort(type = SortType.COMPARATOR, comparator = StudentComparator.class)
	private SortedSet<Student> students = new TreeSet<Student>();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public SortedSet<Student> getStudents() {
		return students;
	}

	public void setStudents(SortedSet<Student> students) {
		this.students = students;
	}

	@Override
	public int compareTo(Session that) {
		if (that == null) {
	       return -1;
	    }
		return that.getStartDate().compareTo(this.getStartDate());
	}

}
