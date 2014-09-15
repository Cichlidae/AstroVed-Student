package ru.premaservices.astroved.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.premaservices.util.CommonUtil;

@Service
public class CustomExceptionFactory {
	
	@Autowired
	ApplicationMessageSource messageSource;
	
	public AdministrationException getAdministrationException(int msg) {
		
		String message = "";
		String key =  AdministrationException.getKey(msg);
		if (key != null) {
			message = messageSource.getMessage(key);
		}
		if (!CommonUtil.isNotBlank(message)) {
			message = AdministrationException.getMessage(msg);
		}	
		return new AdministrationException(message);
	}
	
	public ManagementException getManagementException(int msg) {
		
		String message = "";
		String key =  ManagementException.getKey(msg);
		if (key != null) {
			message = messageSource.getMessage(key);
		}
		if (!CommonUtil.isNotBlank(message)) {
			message = ManagementException.getMessage(msg);
		}	
		return new ManagementException(message);
	}

}
