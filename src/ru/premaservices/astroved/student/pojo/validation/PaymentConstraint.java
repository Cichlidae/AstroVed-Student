package ru.premaservices.astroved.student.pojo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {PaymentValidator.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentConstraint {
	String message() default "{ru.premaservices.astroved.student.pojo.PaymentConstraint.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
