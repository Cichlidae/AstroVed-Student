package ru.premaservices.astroved.student.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.premaservices.astroved.student.dao.HistoryManager;
import ru.premaservices.astroved.student.dao.UserManager;
import ru.premaservices.astroved.student.pojo.History;
import ru.premaservices.astroved.student.pojo.Rights;
import ru.premaservices.astroved.student.pojo.Role;
import ru.premaservices.astroved.student.pojo.Table;
import ru.premaservices.astroved.student.pojo.User;

@Service
public class AdministrationService {
	
	@Autowired
	private UserManager manager;
	
	@Autowired
	private HistoryManager historyManager;
	
	@Autowired
	private CustomExceptionFactory customExceptionFactory;

	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser (User user) {
		return manager.updateUser(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public User removeUser (Integer id) {
		return manager.removeUser(id);
	}
	
	@Transactional(readOnly = true)
	public List<User> getUserList () {
		return manager.getUserList();
	}
	
	@Transactional(readOnly = true)
	public User getUser (String login, String password) throws AdministrationException {
		User user = manager.getUser(login);
		if (user == null) throw customExceptionFactory.getAdministrationException(AdministrationException.LOGIN_FAILED_NOT_FOUND);
		if (user.getLocked()) throw customExceptionFactory.getAdministrationException(AdministrationException.LOGIN_FAILED_LOCKING);
		if (user.getPassword().compareTo(password) != 0) throw customExceptionFactory.getAdministrationException(AdministrationException.LOGIN_FAILED_INCORRECT_PASSWORD);	
		return user;
	}
	
	@Transactional(readOnly = true)
	public User restore (Integer id) throws AdministrationException {
		User user = manager.getUser(id);
		if (user == null) {
			throw customExceptionFactory.getAdministrationException(AdministrationException.LOGIN_FAILED_NOT_FOUND);
		}
		return user;
	}
	
	public boolean canWrite (User user) {
		return user.getRole() == Role.ADMINISTRATOR || user.getRole() == Role.MANAGER;
	}
	
	public boolean canRead (User user) {
		return true;
	}
	
	public boolean isAdmin (User user) {
		return user.getRole() == Role.ADMINISTRATOR;
	}
	
	public boolean canWrite (User user, Table table) {
		if (canWrite(user) && user.getRights() > 0) {
			return checkRights(user, table, Rights.READ_WRITE);	
		}
		return false;
	}
	
	public boolean canRead (User user, Table table) {
		if (canRead(user) && user.getRights() > 0) {
			return checkRights(user, table, Rights.READ);	
		}
		return false;
	}
	
	protected static boolean checkRights (User user, Table table, Rights rights) {
		int count = table.ordinal() + 1;
		int r = user.getRights();
		do {
			if (count == 1)
				r = r%10;
			else			
				r = r/10;
		}
		while (--count > 0);
		if (r == rights.ordinal()) return true;	
		return false;
	}
	
	@Transactional(readOnly = true)
	public List<History> getHistory (Date from, Date upto) {
		return historyManager.getHistory(from, upto);
	}

}
