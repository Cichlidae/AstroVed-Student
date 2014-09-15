package ru.premaservices.astroved.student.service;

public class SimpleTemplate extends NoteTemplate {

	private String content;
	
	public SimpleTemplate (String content) {
		super();
		if (content == null) this.content = "";
		else this.content = content;
	}
	
	@Override
	public String getTemplateContent() {
		return content;
	}

}
