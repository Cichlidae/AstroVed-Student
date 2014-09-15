package ru.premaservices.astroved.student.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="GROUPS")
public class Group {
	
	@Id @NotNull
	@Column(name="ID")
	private Integer id;
	
	@NotBlank
	@Column(name="GROUP", length=10, nullable = false, unique = true)
	private String group;
	
	@NotNull
	@Column(name="MARK")
	private String mark = "com";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	
}
