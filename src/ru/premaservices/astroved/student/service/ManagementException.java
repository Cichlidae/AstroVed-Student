package ru.premaservices.astroved.student.service;

public class ManagementException extends Exception {

	private static final long serialVersionUID = 3344228248006795801L;
	
	public static final int COURSE_TEST_NOT_PASSED = 0;
	public static final int DIPLOMA_NUMBER_HAS_DIBLICATE = 1;
	public static final int NEEDS_TO_REGISTER_STUDENT_ON_SESSION_FIRST = 2;
	
	static final String[] messages = new String[] {
		"Course test not passed.",
		"The diploma number has dublicate.",
		"Needs to register student on session first."
	};
	
	static final String[] keys = new String[] {
		"management.exception.course-test-not-passed",
		"management.exception.diploma-dublicate",
		"management.exception.not-register-on-session"
	};

	public ManagementException (String msg) {
		super(msg);
	}

	static String getKey (int msg) {
		if (msg < 0 && msg >= keys.length) return "";
		return AdministrationException.keys[msg];
	}
	
	static String getMessage (int msg) {
		if (msg < 0 && msg >= messages.length) return "";
		return AdministrationException.messages[msg];
	}

}
