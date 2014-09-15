package ru.premaservices.astroved.student.pojo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.premaservices.astroved.student.pojo.Session;

public class SessionValidator implements ConstraintValidator<SessionConstraint, Session> {

	@Override
	public void initialize(SessionConstraint arg0) {	
	}

	@Override
	public boolean isValid(Session s, ConstraintValidatorContext context) {
	
		if (s.getFinalDate().compareTo(s.getStartDate()) <= 0) {	
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("finalDate").addConstraintViolation();
			return false;
		}
		else return true;
	}

}
