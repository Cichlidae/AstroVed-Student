package ru.premaservices.astroved.student.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ru.premaservices.astroved.student.pojo.Avatar;
import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Payment;
import ru.premaservices.astroved.student.pojo.Session;
import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.astroved.student.pojo.StudentFilter;
import ru.premaservices.astroved.student.pojo.Type;
import ru.premaservices.astroved.student.pojo.User;
import ru.premaservices.astroved.student.service.ApplicationMessageSource;
import ru.premaservices.astroved.student.service.ManagementException;
import ru.premaservices.astroved.student.service.ManagementService;
import ru.premaservices.astroved.student.service.PresentationService;
import ru.premaservices.astroved.student.service.ResourceLoaderService;
import ru.premaservices.astroved.student.view.StudentListExcelBuilder;
import ru.premaservices.extention.spring.annotation.SessionAttribute;
import ru.premaservices.util.ApplicationSecurityManager;
import ru.premaservices.util.CommonUtil;
import ru.premaservices.util.FileUtil;
import ru.premaservices.util.ServletUtil;

@Controller
public class StudentController {
	
	public static final String STUDENT_FORM_REQUEST = "/student/add";
	public static final String STUDENT_EDIT_REQUEST = "/student/edit";
	public static final String STUDENT_VIEW_REQUEST = "/student/view";
	public static final String STUDENT_AVATAR_REQUEST = "/student/avatar";
	public static final String STUDENT_DETAILS_REQUEST = "/student/details";
	public static final String STUDENT_PAYMENT_REQUEST = "/student/payment";
	public static final String STUDENT_NOTES_REQUEST = "/student/notes";
	public static final String PAYMENT_ADD_REQUEST = "/payment/add";
	public static final String STUDENT_REGISTER_REQUEST = "/student/register";
	public static final String STUDENT_ENROLL_REQUEST = "/student/enroll";
	public static final String STUDENT_EXCLUDE_REQUEST = "/student/exclude";
	public static final String STUDENT_AVATAR_FORM_REQUEST = "/student/upload";
	public static final String STUDENT_SEARCH_REQUEST = "/student/search";
	public static final String STUDENT_FILTER_REQUEST = "/student/filter";
	public static final String STUDENT_TEST_REQUEST = "/student/test";
	public static final String STUDENT_EXAM_REQUEST = "/student/exam";
	public static final String STUDENT_PROCEED_REQUEST = "/student/proceed";
	public static final String EXCEL_STUDENT_LIST_REQUEST = "/student/excel";
	
	public static final String STUDENT_FORM_JSP = "student-form";
	public static final String STUDENT_EDIT_JSP = "student-edit-form";
	public static final String STUDENT_VIEW_JSP = "student-list";
	public static final String STUDENT_DETAILS_JSP = "student-view";
	public static final String STUDENT_PAYMENT_JSP = "student-payment";
	public static final String STUDENT_NOTES_JSP = "student-notes";
	public static final String PAYMENT_FORM_JSP = "payment-form";
	public static final String PAYMENT_INFO_JSP = "payment-info";
	public static final String EXCEPTION_MSG_JSP = "exception-msg";
	public static final String STUDENT_AVATAR_FORM_JSP = "avatar-form";
	
	public static final String TYPE_MAP_KEY = "type";
	public static final String STUDENT_LIST_KEY = "students";
	public static final String GROUP_LIST_KEY = "groups";
	public static final String COURSE_MAP_KEY = "courses";
	public static final String PAYMENT_MAP_KEY = "payments";
	public static final String PAYMENTINFO_MAP_KEY = "paymentinfo";
	public static final String NOTES_MAP_KEY = "notes";
	public static final String ERROR_KEY = "error";
	public static final String FLAGS_KEY = "flags";
	
	public static final String FLAG_ENROLL = "!ENROLL~";
	public static final String FLAG_EXCLUDE = "!EXCLUDE~";
	public static final String FLAG_TEST = "!TEST~";
	public static final String FLAG_EXAM = "!EXAM~";
	public static final String FLAG_PROCEED = "!PROCEED~";
	public static final String FLAG_GRADUATE = "!GRADUATE~";
	
	@Autowired
	private PresentationService presentationService;

	@Autowired
	private ManagementService managementService;
	
	@Autowired
	ApplicationMessageSource messageSource;
	
	@Autowired
	private ResourceLoaderService resourceLoader;
	
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
	
	@InitBinder("student")
	public void initBinderForStudent(WebDataBinder binder) {
		 binder.registerCustomEditor(Date.class, new DatePropertyEditor(DatePropertyEditor.DATE_FORMATTER));
		 binder.registerCustomEditor(Type.class, new TypePropertyEditor());
	}
	
	@RequestMapping(value = STUDENT_FORM_REQUEST)
	public String getNewStudentForm (Model model) { //AJAX
		model.addAttribute(managementService.createStudent());
		model.addAttribute(BookmarkController.COUNTRIES_MAP_KEY, presentationService.getCountries());
		model.addAttribute(TYPE_MAP_KEY, getTypeMap());
		return STUDENT_FORM_JSP;
	}
	
	@RequestMapping(value = STUDENT_EDIT_REQUEST)
	public String getStudentDetailsForm (@RequestParam("uid") String uid, Model model) { //AJAX
		Student student = presentationService.getStudent(uid);
		model.addAttribute(student);				
		return STUDENT_EDIT_JSP;
	}
	
	private HashMap<String, String> getTypeMap () {
		Type[] types = Type.values();
		HashMap<String, String> typesMap = new HashMap<String, String>(types.length);
		for (Type t : types) {
			String key = t.toString();
			String value = messageSource.getMessage("str.enum.Type." + key);
			typesMap.put(key, value);
		}
		return typesMap;
	}
	
	private HashMap<String, String> getCourseMap () {
		Course[] courses = Course.values();
		HashMap<String, String> cMap = new HashMap<String, String>(courses.length);
		for (Course t : courses) {
			if (t != Course.NONE) {
				String key = t.toString();
				String value = messageSource.getMessage("str.enum.Course." + key);
				cMap.put(key, value);
			}
		}
		return cMap;
	}

	@RequestMapping(value = STUDENT_FORM_REQUEST, method = RequestMethod.POST)
	public ModelAndView addStudent (@SessionAttribute(ApplicationSecurityManager.USER) User user, @ModelAttribute("student") @Valid Student student, BindingResult result) { //AJAX
		
		ModelAndView model;
		
		if (result.hasErrors()) {
			model = new ModelAndView(STUDENT_FORM_JSP);
			model.addAllObjects(result.getModel());
			model.addObject(BookmarkController.COUNTRIES_MAP_KEY, presentationService.getCountries());
			model.addObject(TYPE_MAP_KEY, getTypeMap());
		}
		else {
			model = new ModelAndView(BookmarkController.SUCCESS_MSG_JSP);
			model.addObject(BookmarkController.MESSAGE_MAP_KEY, messageSource.getMessage("str.common.student.added"));
			managementService.updateStudent(student, user);
		}
		
		return model;
	}
	
	@RequestMapping(value = STUDENT_EDIT_REQUEST, method = RequestMethod.POST)
	public ModelAndView editStudent (@SessionAttribute(ApplicationSecurityManager.USER) User user, @ModelAttribute("student") @Valid Student student, BindingResult result) { //AJAX
		
		ModelAndView model;
		
		if (result.hasErrors()) {
			model = new ModelAndView(STUDENT_EDIT_JSP);
			model.addAllObjects(result.getModel());
		}
		else {
			model = new ModelAndView(BookmarkController.SUCCESS_MSG_JSP);
			model.addObject(BookmarkController.MESSAGE_MAP_KEY, messageSource.getMessage("str.common.student.updated"));
			managementService.updateStudent(student, user);
		}
		
		return model;		
	}
	
	@RequestMapping(value = STUDENT_VIEW_REQUEST)
	public String getStudentView (Model model) {
		model.addAttribute(STUDENT_LIST_KEY, presentationService.getStudents());
		model.addAttribute(BookmarkController.COUNTRIES_MAP_KEY, presentationService.getCountries());
		model.addAttribute(GROUP_LIST_KEY, presentationService.getGroups());
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		model.addAttribute(new StudentFilter());
		return STUDENT_VIEW_JSP;
	}
	
	@RequestMapping(value = STUDENT_DETAILS_REQUEST)
	public String getStudentDetails (@RequestParam("uid") String uid, Model model) {
		Student student = presentationService.getStudent(uid);
		model.addAttribute(student);
		Session s = presentationService.getOpenSession();
		if (s != null) {
			model.addAttribute(s);
		}
		String flags = "";
		if (managementService.needEnroll(student)) {
			flags += FLAG_ENROLL;
		}
		if (managementService.canExclude(student)) {
			flags += FLAG_EXCLUDE;
		}
		if (managementService.canTest(student)) {
			flags += FLAG_TEST;				
		}		
		if (managementService.canExam(student)) {		
			flags += FLAG_EXAM;			
		}
		if (managementService.canProceed(student)) {
			flags += FLAG_PROCEED;
		}
		if (managementService.canGraduate(student)) {
			flags += FLAG_GRADUATE;
		}				
		if (CommonUtil.isNotBlank(flags)) {
			model.addAttribute(FLAGS_KEY, flags);
		}
		return STUDENT_DETAILS_JSP;
	}
	
	@RequestMapping(value = STUDENT_AVATAR_REQUEST)
	public ResponseEntity<byte[]> getImage (@RequestParam("uid") String uid) {
	   byte[] content = presentationService.getStudentAvatar(uid);
	   
	   if (content == null) {
		   try {
			   //load standard pictures
			   String path = "classpath:ru/premaservices/astroved/student/view/lotos_preview.png";
			   Resource resource = resourceLoader.getResource(path);
			   content = FileUtil.getImage(resource.getInputStream());
		   }
		   catch (IOException e) {
			   e.printStackTrace();
			   content = new byte[0];
		   }
	   }
	   
	   HttpHeaders headers = new HttpHeaders();
	   headers.setContentType(MediaType.IMAGE_PNG);
	   return new ResponseEntity<byte[]>(content, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = STUDENT_AVATAR_FORM_REQUEST, method = RequestMethod.GET)
	public String getUploadAvatar (@RequestParam("uid") String uid, Model model) { //AJAX
		model.addAttribute(new Avatar(uid));
		return STUDENT_AVATAR_FORM_JSP;
	}
	
	@RequestMapping(value = STUDENT_AVATAR_FORM_REQUEST, method = RequestMethod.POST) 
	public ModelAndView uploadAvatar (@SessionAttribute(ApplicationSecurityManager.USER) User user, @ModelAttribute("avatar") Avatar file) {
		
		ModelAndView model = new ModelAndView();	
		managementService.setAvatar(file.getUid(), file.getAvatarFile().getBytes(), user);
		
		model.setView(
				new RedirectView(
						ServletUtil.getRedirectUrl(STUDENT_AVATAR_FORM_REQUEST, STUDENT_DETAILS_REQUEST + "?uid=" + file.getUid())
				)
		);
		
		return model;				
	}
	
	@RequestMapping(value = STUDENT_PAYMENT_REQUEST)
	public String getStudentPayments (@RequestParam("uid") String uid, Model model) { //AJAX		
		model.addAttribute(PAYMENT_MAP_KEY, presentationService.getPayments(uid));
		model.addAttribute(PAYMENTINFO_MAP_KEY, presentationService.getPaymentInfo(new Student(uid)));		
		return STUDENT_PAYMENT_JSP;
	}

	@RequestMapping(value = STUDENT_NOTES_REQUEST)
	public String getStudentNotes (@RequestParam("uid") String uid, Model model) { //AJAX
		model.addAttribute(NOTES_MAP_KEY, presentationService.getLastMounthNotes());
		return STUDENT_NOTES_JSP;
	}
	
	@RequestMapping(value = PAYMENT_ADD_REQUEST)
	public String getPaymentForm (Model model) {
		model.addAttribute(new Payment());
		return PAYMENT_FORM_JSP;
	}

	@RequestMapping(value = PAYMENT_ADD_REQUEST, method = RequestMethod.POST)
	public ModelAndView addPayment (@SessionAttribute(ApplicationSecurityManager.USER) User user, @ModelAttribute("payment") @Valid Payment payment, BindingResult result) throws ManagementException { //AJAX
		
		ModelAndView model;
		
		if (result.hasErrors()) {
			model = new ModelAndView(PAYMENT_FORM_JSP);
			model.addAllObjects(result.getModel());	
		}
		else {
			managementService.pay(payment.getStudent().getUid(), payment, user);
			model = new ModelAndView(PAYMENT_INFO_JSP);
			model.addObject(payment);			
		}
		
		return model;		
	}
	
	@ExceptionHandler(ManagementException.class)
	public String handleManagementException (ManagementException exception, Model model) {
		model.addAttribute(ERROR_KEY, exception.getMessage());		
		return EXCEPTION_MSG_JSP;
	}
	
	@RequestMapping(value = STUDENT_REGISTER_REQUEST) @ResponseBody
	public ResponseEntity<String> registerStudent (@SessionAttribute(ApplicationSecurityManager.USER) User user, @RequestParam("uid") String uid) { //AJAX
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json; charset=utf-8");
	    String msg;
		
		Session s = presentationService.getOpenSession();
		if (s != null) {
			msg = messageSource.getMessage("str.common.student.registered");
			managementService.register(presentationService.getStudent(uid), s, user);
			return new ResponseEntity<String>("{'status':1, 'msg':'" + msg + "'}", headers, HttpStatus.OK);	
		}
		else {
			return new ResponseEntity<String>("{'status':0}", headers, HttpStatus.OK);	
		}					    	
	}		
	
	@RequestMapping(value = STUDENT_ENROLL_REQUEST) @ResponseBody
	public ResponseEntity<String> enrollStudent (@SessionAttribute(ApplicationSecurityManager.USER) User user, @RequestParam("uid") String uid) { //AJAX
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json; charset=utf-8");
	    String msg;
	    
	    managementService.enroll(uid, user);
	    msg = messageSource.getMessage("str.common.student.enrolled");
	    return new ResponseEntity<String>("{'status':1, 'msg':'" + msg + "'}", headers, HttpStatus.OK);			
	}
	
	@RequestMapping(value = STUDENT_EXCLUDE_REQUEST) @ResponseBody
	public ResponseEntity<String> excludeStudent (@SessionAttribute(ApplicationSecurityManager.USER) User user, @RequestParam("uid") String uid) { //AJAX
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json; charset=utf-8");
	    String msg;
	    
	    managementService.exclude(uid, user);
	    msg = messageSource.getMessage("str.common.student.excluded");
	    return new ResponseEntity<String>("{'status':1, 'msg':'" + msg + "'}", headers, HttpStatus.OK);					
	}
	
	@RequestMapping(value = STUDENT_SEARCH_REQUEST, method = RequestMethod.GET)
	public String searchStudents (@RequestParam("family") String family, Model model) {
		model.addAttribute(STUDENT_LIST_KEY, presentationService.getStudents(family));
		model.addAttribute(BookmarkController.COUNTRIES_MAP_KEY, presentationService.getCountries());
		model.addAttribute(GROUP_LIST_KEY, presentationService.getGroups());
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		model.addAttribute(new StudentFilter());
		return STUDENT_VIEW_JSP;		
	}
	
	@RequestMapping(value = STUDENT_FILTER_REQUEST, method = RequestMethod.POST)
	public String filterStudents (@ModelAttribute("studentFilter") StudentFilter filter, Model model) {	
		model.addAttribute(STUDENT_LIST_KEY, presentationService.getStudents(filter));
		model.addAttribute(BookmarkController.COUNTRIES_MAP_KEY, presentationService.getCountries());
		model.addAttribute(GROUP_LIST_KEY, presentationService.getGroups());
		model.addAttribute(COURSE_MAP_KEY, getCourseMap());	
		model.addAttribute(new StudentFilter());		
		return STUDENT_VIEW_JSP;
	}
	
	@RequestMapping(value = STUDENT_TEST_REQUEST, method = RequestMethod.GET)
	public ModelAndView passTest (@SessionAttribute(ApplicationSecurityManager.USER) User user, @RequestParam("uid") String uid) {
		
		ModelAndView model = new ModelAndView();	
		managementService.passTest(uid, user);
		
		model.setView(
				new RedirectView(
						ServletUtil.getRedirectUrl(STUDENT_TEST_REQUEST, STUDENT_DETAILS_REQUEST + "?uid=" + uid)
				)
		);
		
		return model;
	}
	
	@RequestMapping(value = STUDENT_EXAM_REQUEST, method = RequestMethod.GET)
	public ModelAndView passExam (@SessionAttribute(ApplicationSecurityManager.USER) User user, @RequestParam("uid") String uid) {
		
		ModelAndView model = new ModelAndView();	
		managementService.passTest(uid, user);
		
		model.setView(
				new RedirectView(
						ServletUtil.getRedirectUrl(STUDENT_EXAM_REQUEST, STUDENT_DETAILS_REQUEST + "?uid=" + uid)
				)
		);
		
		return model;
	}
	
	@RequestMapping(value = EXCEL_STUDENT_LIST_REQUEST, method = RequestMethod.GET)
	public ModelAndView getExcelStudentList () {
		
		HashMap<String, Object> map = new HashMap<String, Object>(1);		
		map.put(StudentListExcelBuilder.SHEET_SOURCE_LIST_NAME, presentationService.getStudentsWithTests(true));				
		return new ModelAndView("excel-students", map);
	}
	
}
