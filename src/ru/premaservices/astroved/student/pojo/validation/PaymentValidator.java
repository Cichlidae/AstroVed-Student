package ru.premaservices.astroved.student.pojo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Payment;
import ru.premaservices.util.CommonUtil;

public class PaymentValidator implements ConstraintValidator<PaymentConstraint, Payment> {

	@Override
	public void initialize(PaymentConstraint arg0) {		
	}

	@Override
	public boolean isValid(Payment payment, ConstraintValidatorContext arg1) {
		if (payment.getCourse().compareTo(Course.NONE) == 0 && (payment.getSession() == null || payment.getSession().getId() == null || payment.getSession().getId() == 0) && !CommonUtil.isNotBlank(payment.getMasterClass())) {
			return false;
		}
		else return true;		
	}

}
