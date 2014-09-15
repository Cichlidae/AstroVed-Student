package ru.premaservices.astroved.student.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.premaservices.astroved.student.pojo.Country;
import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Session;
import ru.premaservices.astroved.student.pojo.User;
import ru.premaservices.astroved.student.service.ApplicationMessageSource;
import ru.premaservices.astroved.student.service.ManagementService;
import ru.premaservices.astroved.student.service.PresentationService;
import ru.premaservices.astroved.student.view.DiplomaListPDFBuilder;
import ru.premaservices.extention.spring.annotation.SessionAttribute;
import ru.premaservices.util.ApplicationSecurityManager;

@Controller
public class BookmarkController {
	
	public static final String BOOKMARK_VIEW_REQUEST = "/bookmarks/view";
	public static final String STUDENT_FORM_REQUEST = "/bookmarks/student";
	public static final String SESSION_FORM_REQUEST = "/bookmarks/session";
	public static final String DIPLOMA_STUDENTS_REQUEST = "/bookmarks/diploma-students";
	public static final String CITY_LIST_REQUEST = "/bookmarks/cities";
	
	public static final String BOOKMARK_VIEW_JSP = "main";
	public static final String SESSION_FORM_JSP = "session-form";
	public static final String SUCCESS_MSG_JSP = "success-msg";
	public static final String CITY_LIST_JSP = "city-list";
	
	public static final String NOTE_LIST_MAP_KEY = "noteList";
	public static final String DIPLOMAS_MAP_KEY = "diplomaMap";
	public static final String MESSAGE_MAP_KEY = "message";
	public static final String COUNTRIES_MAP_KEY = "countries";
	public static final String CITY_LIST_KEY = "cities";
	
	@Autowired
	private PresentationService presentationService;

	@Autowired
	private ManagementService managementService;
	
	@Autowired
	ApplicationMessageSource messageSource;

	public ApplicationMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(ApplicationMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public PresentationService getPresentationService() {
		return presentationService;
	}

	public void setPresentationService(PresentationService presentationService) {
		this.presentationService = presentationService;
	}

	public ManagementService getManagementService() {
		return managementService;
	}

	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}
	
	@InitBinder("session")
	public void initBinderForSession(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DatePropertyEditor(DatePropertyEditor.DATE_FORMATTER));
	}
	
	@RequestMapping(value = BOOKMARK_VIEW_REQUEST)
	public String getBookmarkView (Model model) {
		model.addAttribute(NOTE_LIST_MAP_KEY, presentationService.getLastMounthNotes());
		model.addAttribute(DIPLOMAS_MAP_KEY, presentationService.getLastDiplomas());
		Session s = presentationService.getOpenSession();
		if (s != null) {
			model.addAttribute(s);
		}
		//TODO +getBookmarks		
		return BOOKMARK_VIEW_JSP;
	}
	
	@RequestMapping(value = SESSION_FORM_REQUEST)
	public String getNewSessionForm (Model model) { //AJAX
		model.addAttribute(new Session());
		return SESSION_FORM_JSP;
	}
	
	@RequestMapping(value = SESSION_FORM_REQUEST, method = RequestMethod.POST)
	public ModelAndView addSession (@SessionAttribute(ApplicationSecurityManager.USER) User user, @ModelAttribute("session") @Valid Session s, BindingResult result) { //AJAX
		
		ModelAndView model;
		
		if (result.hasErrors()) {
			model = new ModelAndView(SESSION_FORM_JSP);
			model.addAllObjects(result.getModel());
		}
		else {
			model = new ModelAndView(SUCCESS_MSG_JSP);
			model.addObject(MESSAGE_MAP_KEY, messageSource.getMessage("str.common.session.added"));
			s.setAuthor(user);
			managementService.addSession(s, user);
		}	
		return model;
	}
	
	@RequestMapping(value = CITY_LIST_REQUEST)
	public String getCities (@RequestParam("id") Integer countryId, Model model) { //AJAX
		Country country = presentationService.getCountry(countryId);
		model.addAttribute(CITY_LIST_KEY, country.getCities());	
		return CITY_LIST_JSP;
	}
	
	@RequestMapping(value = DIPLOMA_STUDENTS_REQUEST)
	public ModelAndView getDiplomaStudentList (Model model) {
		
		Map<String, Object> map = new TreeMap<String, Object>();	
		map.put(DiplomaListPDFBuilder.PDF_1ST_COURSE_SOURCE_NAME, presentationService.getDiplomas(Course.FIRST));
		map.put(DiplomaListPDFBuilder.PDF_2ND_COURSE_SOURCE_NAME, presentationService.getDiplomas(Course.SECOND));
		map.put(DiplomaListPDFBuilder.PDF_3RD_COURSE_SOURCE_NAME, presentationService.getDiplomas(Course.THIRD));
		map.put(DiplomaListPDFBuilder.PDF_GR_COURSE_SOURCE_NAME, presentationService.getDiplomas(Course.FORTH));			
		return new ModelAndView("pdf-diplomas", map);
	}
	
}
