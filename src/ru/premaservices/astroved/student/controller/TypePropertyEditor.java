package ru.premaservices.astroved.student.controller;

import java.beans.PropertyEditorSupport;

import ru.premaservices.astroved.student.pojo.Type;

public class TypePropertyEditor extends PropertyEditorSupport {
	
	@Override
	public String getAsText() {
		Type value = (Type)this.getValue();
		return value.toString();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		this.setValue(Type.valueOf(text.toUpperCase()));
	}

}
