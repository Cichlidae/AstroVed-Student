package ru.premaservices.astroved.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.premaservices.astroved.student.pojo.Payment;

@Service
public class NoteTemplateFactory {
	
	public static final String ENROLL_STUDENT_KEY = "management.template.enroll-student";
	public static final String NEED_TO_ENROLL_STUDENT_KEY = "management.template.need-to-enroll-student";
	public static final String EXCLUDE_STUDENT_KEY = "management.template.exclude-student";
	public static final String NEXT_COURSE_STUDENT_KEY = "management.template.next-course-student";
	public static final String GRADUATE_STUDENT_KEY = "management.template.graduate-student";
	public static final String PASSWORD_SENT_KEY = "management.template.password-sent";
	

	@Autowired
	ApplicationMessageSource messageSource;	
	
	public NoteTemplate getSimpleTemplate (String key) {
		SimpleTemplate template = new SimpleTemplate(messageSource.getMessage(key));
		return template;	
	}
	
	public NoteTemplate getPaymentTemplate (Payment payment) {
		PaymentTemplate template = new PaymentTemplate(payment, messageSource.getMessage("management.template.payment"));
		return template;	
	}
	
}
