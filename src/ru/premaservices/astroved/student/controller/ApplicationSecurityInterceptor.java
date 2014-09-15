package ru.premaservices.astroved.student.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ru.premaservices.astroved.student.pojo.Role;
import ru.premaservices.astroved.student.pojo.User;
import ru.premaservices.util.ApplicationSecurityManager;
import ru.premaservices.util.Debug;
import ru.premaservices.util.ServletUtil;

public class ApplicationSecurityInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private ApplicationSecurityManager applicationSecurityManager;
	
	private static final String ADMIN_LOGIN = "PREMA";
	private static final String ADMIN_UID = "fefd7d10-686a-11e1-b617-742f68f74300";
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	
		//TODO +checkRights
		
		User user = (User)applicationSecurityManager.getUser(request.getSession(true));
		//if (user == null) {
        //    response.sendRedirect(ServletUtil.getServletURI(request) + SignInController.SIGNIN_HTML);
        //    return false;
        //}
		
		if (user == null) {
			User test = new User();
			test.setId(1);
			test.setLogin("premaeverywhere@gmail.com");
			test.setPassword("admin");
			test.setRole(Role.ADMINISTRATOR);
			applicationSecurityManager.setUser(request.getSession(), test);
		}
	
        return true;
    }

}
