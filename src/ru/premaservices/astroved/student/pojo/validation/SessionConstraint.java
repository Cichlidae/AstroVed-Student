package ru.premaservices.astroved.student.pojo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {SessionValidator.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionConstraint {
	String message() default "{ru.premaservices.astroved.student.pojo.validation.SessionConstraint.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
