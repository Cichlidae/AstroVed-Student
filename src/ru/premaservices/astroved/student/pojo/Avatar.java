package ru.premaservices.astroved.student.pojo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Avatar {
	
	private CommonsMultipartFile avatarFile;
	private String uid;
	
	public Avatar () {		
	}
	
	public Avatar (String uid) {
		this.uid = uid;
	}

	public CommonsMultipartFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(CommonsMultipartFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
