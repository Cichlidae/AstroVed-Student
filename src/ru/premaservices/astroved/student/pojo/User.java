package ru.premaservices.astroved.student.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="USERS")
public class User implements Serializable {
	
	private static final long serialVersionUID = 357668609542213439L;

	@Id
	@Column(name="ID")
	private Integer id;
	
	@Email
	@Column(name="LOGIN", length=30)
	private transient String login;
	
	@NotBlank @Length(min=6, max=20)
	@Column(name="PASSWORD", length=20)
	private transient String password;
	
	@NotBlank
	@Column(name="NAME", length=50, nullable = false)
	private String name;
	
	@NotNull
	@Column(name="ROLE")
	@Enumerated(EnumType.ORDINAL)
	private transient Role role = Role.GUEST;
	
	@Column(name="POST", length=40)
	private String post;
	
	@Column(name="LOCKED")
	private transient Boolean locked = false;
	
	@Column(name="RIGHTS")
	private transient Integer rights = Rights.NONE.ordinal();

	public Integer getId() {
		return id;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Integer getRights() {
		return rights;
	}

	public void setRights(Integer rights) {
		this.rights = rights;
	}
	
}
