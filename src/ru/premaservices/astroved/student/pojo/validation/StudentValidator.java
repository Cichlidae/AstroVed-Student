package ru.premaservices.astroved.student.pojo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.util.CommonUtil;

public class StudentValidator implements ConstraintValidator<StudentConstraint, Student> {

	@Override
	public void initialize(StudentConstraint arg0) {
	}

	@Override
	public boolean isValid(Student student, ConstraintValidatorContext context) {
		if (!CommonUtil.isNotBlank(student.getTel_1()) && !CommonUtil.isNotBlank(student.getEmail_1()) &&
				!CommonUtil.isNotBlank(student.getTel_2()) && !CommonUtil.isNotBlank(student.getEmail_2())) {
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addNode("email_1").addConstraintViolation();
			return false;
		}	
		return true;
	}

}
