package ru.premaservices.astroved.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignInController {
	
	public static final String SIGNIN_HTML = "/signin.html";
	public static final String SIGNIN_JSP = "signin";
	
	@RequestMapping("/")
    public String home() {
        return "redirect:/index.jsp";
    }

}
