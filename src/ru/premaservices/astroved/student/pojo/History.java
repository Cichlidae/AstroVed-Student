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
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="HISTORY")
public class History {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="AUTHOR", nullable = true)
	private User author;
	
	@NotNull
	@Column(name="TABLEID")
	@Enumerated(EnumType.ORDINAL)
	private ru.premaservices.astroved.student.pojo.Table table;
	
	@NotNull
	@Column(name="DATE_OF_NOTE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date timestamp = new Date(System.currentTimeMillis());
	
	@NotBlank
	@Lob @Basic(fetch = FetchType.EAGER)
	@Column(name="DESCRIPTION")
	private String description;
	
	public History () {
	}
	
	public History (ru.premaservices.astroved.student.pojo.Table table, String description, User author) {
		this.table = table;
		this.description = description;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public ru.premaservices.astroved.student.pojo.Table getTable() {
		return table;
	}

	public void setTable(ru.premaservices.astroved.student.pojo.Table table) {
		this.table = table;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
