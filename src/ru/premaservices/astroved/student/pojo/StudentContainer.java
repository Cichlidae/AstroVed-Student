package ru.premaservices.astroved.student.pojo;


public class StudentContainer {
	
	private String uid;
	private String family;
	private String name;
	private String patronimic;
	private String spiritualName;	
	private City city;	
	private Course course;	
	private Group group;	
	private String tel;	
	private String email;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPatronimic() {
		return patronimic;
	}
	public void setPatronimic(String patronimic) {
		this.patronimic = patronimic;
	}
	public String getSpiritualName() {
		return spiritualName;
	}
	public void setSpiritualName(String spiritualName) {
		this.spiritualName = spiritualName;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
