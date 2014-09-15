package ru.premaservices.astroved.student.service;

import java.text.SimpleDateFormat;

import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Payment;
import ru.premaservices.util.CommonUtil;

public class PaymentTemplate extends NoteTemplate {
	
	private Payment payment;
	private String msg = DEFMSG;
	
	static final String DEFMSG = "The payment for {course}{mc}{retrit} by {date} on sum {sum}";
	static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
		
	public PaymentTemplate(Payment payment, String msg) {
		super();
		this.payment = payment;
		if (CommonUtil.isNotBlank(msg)) this.msg = msg;
	}

	@Override
	public String getTemplateContent() {
		
		String msg = this.msg;
		
		msg.replaceFirst("{date}", DATE_FORMATTER.format(payment.getPaimentDate()));
		msg.replaceFirst("{sum}", payment.getSum() + "");
		if (payment.getCourse().compareTo(Course.ABITURIENT) > 0) {
			msg.replaceFirst("{course}", payment.getCourse().name());
		}
		else {
			msg.replaceFirst("{course}", "");
		}
		if (CommonUtil.isNotBlank(payment.getMasterClass())) {
			msg.replaceFirst("{mc}", payment.getMasterClass());
		}
		else {
			msg.replaceFirst("{mc}","");
		}
		if (payment.getSession() != null) {
			msg.replaceFirst("{retrit}", payment.getSession().getName());
		}	
		else {
			msg.replaceFirst("{retrit}", "");
		}
		return msg;
	}

}
