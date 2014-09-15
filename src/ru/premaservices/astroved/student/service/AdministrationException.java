package ru.premaservices.astroved.student.service;

public class AdministrationException extends Exception {

	private static final long serialVersionUID = 355927052143271468L;
	
	public static final int LOGIN_FAILED_NOT_FOUND = 0;
	public static final int LOGIN_FAILED_INCORRECT_PASSWORD = 1;
	public static final int LOGIN_FAILED_LOCKING = 2;
	
	static final String[] messages = new String[] {
		"This user not found. Ask admin to register.",
		"This password is incorrect.",
		"This login locked by admin. Ask support for reasons."
	};
	
	static final String[] keys = new String[] {
		"administration.exception.user-not-found",
		"administration.exception.password-incorrect",
		"administration.exception.login-locked"
	};

	public AdministrationException (String msg) {
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
