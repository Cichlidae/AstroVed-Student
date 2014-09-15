package ru.premaservices.astroved.student.service;

import java.util.Date;

import ru.premaservices.astroved.student.pojo.Note;
import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.astroved.student.pojo.User;

public abstract class NoteTemplate {
	
	public abstract String getTemplateContent ();
	
	public Note createNote (Student student, User author) {
		Note note = new Note();
		note.setCaption(getTemplateContent());
		note.setTimestamp(new Date(System.currentTimeMillis()));
		note.setStudent(student);
		note.setAuthor(author);
		return note;
	}

}
