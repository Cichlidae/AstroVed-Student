package ru.premaservices.astroved.student.controller;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.premaservices.util.CommonUtil;

public class DatePropertyEditor extends PropertyEditorSupport {
	
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	
	private DateFormat formatter;

	public DatePropertyEditor (DateFormat df) {
		formatter = df;
	}
	
	@Override
	public String getAsText() {
		Date date = (Date)getValue();
		if (date != null) {
			String text = formatter.format(date);
			return text;
		}
		else
			return null;
	}

	@Override
	public void setAsText(String text) {	
		if (CommonUtil.isNotBlank(text)) {
			try {
				Date date = formatter.parse(text);
				setValue(date);
			}
			catch (ParseException e) {
				setValue(null);
			}
		}
	}

}
