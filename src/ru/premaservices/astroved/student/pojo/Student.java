package ru.premaservices.astroved.student.pojo;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import ru.premaservices.astroved.student.pojo.validation.StudentConstraint;
import ru.premaservices.extention.spring.hibernate.validation.constraints.NotNullObjectId;

@Entity
@Table(name="STUDENTS") @StudentConstraint
public class Student {
	
	@Id @GenericGenerator(name = "guid", strategy = "guid") @GeneratedValue(generator="guid")
	@Column(name="UID", length=36, nullable = false, unique = true)
	private String uid;
	
	@NotBlank
	@Column(name="FAMILY", length=45, nullable = false)
	private String family;
	
	@NotBlank
	@Column(name="NAME", length=20, nullable = false)
	private String name;
	
	@Column(name="PATRONIMIC", length=30)
	private String patronimic;
	
	@Column(name="SPIRITUAL_NAME", length=45)
	private String spiritualName;

	@NotNull
	@Column(name="BIRTHDAY", nullable = false)
	@Temporal(value=TemporalType.DATE)
	private Date birthday;
	
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(name="AVATAR")
	private byte[] avatar;
	
	@NotNullObjectId(value = "id")
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CITY", nullable = false)
	private City city;
	
	@NotNull
	@Column(name="COURSE")
	@Enumerated(EnumType.ORDINAL)
	private Course course = Course.ABITURIENT;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PROFGROUP", nullable = false)
	private Group group;
	
	@NotNull
	@Column(name="TYPE")
	@Enumerated(EnumType.ORDINAL)
	private Type type = Type.IN_ABSENTIA;
	
	@Column(name="TEL_1", length=20)
	private String tel_1;
	
	@Column(name="TEL_2", length=20)
	private String tel_2;
	
	@Email
	@Column(name="EMAIL_1", length=30)
	private String email_1;
	
	@Email
	@Column(name="EMAIL_2", length=30)
	private String email_2;
	
	@Column(name="SKYPE", length=30)
	private String skype;
	
	@Column(name="CRISIS_CONTACTS", length=100)
	private String crisisContacts;
	
	@Column(name="SKYPECHAT")
	private Boolean skypeChat = false;
	
	@Column(name="MAILER")
	private Boolean mailer = false;
	
	@Column(name="CONSULTANT")
	private Boolean consultant = false;
	
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(name="COMMENTS")
	private String comments;
	
	@OneToOne(mappedBy="student", fetch=FetchType.LAZY)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private TestData tests;
	
	@OneToMany(mappedBy="student", fetch=FetchType.LAZY)
	@Sort(type = SortType.NATURAL)
	@Cascade(value = CascadeType.SAVE_UPDATE)
	private SortedSet<Payment> paiments = new TreeSet<Payment>();
	
	@OneToMany(mappedBy="student", fetch=FetchType.LAZY)
	@Sort(type = SortType.NATURAL)
	@Where(clause="important = true and timestamp > now() - 1000*60*60*24*30")
	private SortedSet<Note> importantNotes = new TreeSet<Note>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "STUDENT_SESSIONS",
			joinColumns = { 
				@JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false) 
			}, 
			inverseJoinColumns = { 
				@JoinColumn(name = "SESSION_ID", nullable = false, updatable = false) 
			}		
	)
	@Sort(type = SortType.NATURAL)
	private SortedSet<Session> sessions = new TreeSet<Session>();
	
	public Student () {
	}
	
	public Student (String uid) {
		this.uid = uid;
	}
	
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getTel_1() {
		return tel_1;
	}

	public void setTel_1(String tel_1) {
		this.tel_1 = tel_1;
	}

	public String getTel_2() {
		return tel_2;
	}

	public void setTel_2(String tel_2) {
		this.tel_2 = tel_2;
	}

	public String getEmail_1() {
		return email_1;
	}

	public void setEmail_1(String email_1) {
		this.email_1 = email_1;
	}

	public String getEmail_2() {
		return email_2;
	}

	public void setEmail_2(String email_2) {
		this.email_2 = email_2;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getCrisisContacts() {
		return crisisContacts;
	}

	public void setCrisisContacts(String crisisContacts) {
		this.crisisContacts = crisisContacts;
	}

	public Boolean getSkypeChat() {
		return skypeChat;
	}

	public void setSkypeChat(Boolean skypeChat) {
		this.skypeChat = skypeChat;
	}

	public Boolean getMailer() {
		return mailer;
	}

	public void setMailer(Boolean mailer) {
		this.mailer = mailer;
	}

	public Boolean getConsultant() {
		return consultant;
	}

	public void setConsultant(Boolean consultant) {
		this.consultant = consultant;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public TestData getTests() {
		return tests;
	}

	public void setTests(TestData tests) {
		this.tests = tests;
	}

	public SortedSet<Payment> getPaiments() {
		return paiments;
	}

	public void setPaiments(SortedSet<Payment> paiments) {
		this.paiments = paiments;
	}

	public SortedSet<Note> getImportantNotes() {
		return importantNotes;
	}

	public void setImportantNotes(SortedSet<Note> importantNotes) {
		this.importantNotes = importantNotes;
	}

	public SortedSet<Session> getSessions() {
		return sessions;
	}

	public void setSessions(SortedSet<Session> sessions) {
		this.sessions = sessions;
	}
	
}
