package ru.premaservices.astroved.student.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import ru.premaservices.astroved.student.pojo.Course;
import ru.premaservices.astroved.student.pojo.Student;
import ru.premaservices.astroved.student.service.ApplicationMessageSource;

public class StudentListExcelBuilder extends AbstractExcelView {
	
	public static final String SHEET_NAME = "Students";
	public static final String SHEET_SOURCE_LIST_NAME = "studentList";
	
	private final List<String> HEADERS = new ArrayList<String>();
	private final ApplicationMessageSource MESSAGES = new ApplicationMessageSource();
	
	public StudentListExcelBuilder () {
						
		HEADERS.add(MESSAGES.getMessage("str.common.student.family"));
		HEADERS.add(MESSAGES.getMessage("str.common.student.spiritual-name"));
		HEADERS.add(MESSAGES.getMessage("str.common.birthday"));
		HEADERS.add(MESSAGES.getMessage("str.common.city") + "/" + MESSAGES.getMessage("str.common.country"));
		HEADERS.add(MESSAGES.getMessage("str.common.course"));
		HEADERS.add(MESSAGES.getMessage("str.common.group"));
		HEADERS.add(MESSAGES.getMessage("str.common.tel"));
		HEADERS.add(MESSAGES.getMessage("str.common.tel") + " ¹2");
		HEADERS.add(MESSAGES.getMessage("str.common.email"));
		HEADERS.add(MESSAGES.getMessage("str.common.email") + " ¹2");
		HEADERS.add(MESSAGES.getMessage("str.common.skype"));
		HEADERS.add(MESSAGES.getMessage("str.common.skypeChat"));
		HEADERS.add(MESSAGES.getMessage("str.common.mailer"));
		HEADERS.add(MESSAGES.getMessage("str.common.right-to-consult"));
		HEADERS.add(MESSAGES.getMessage("str.common.crisis-contacts"));
		HEADERS.add(MESSAGES.getMessage("str.common.comments"));
		HEADERS.add(MESSAGES.getMessage("str.common.start-date"));
		HEADERS.add(MESSAGES.getMessage("str.common.course-works"));
		HEADERS.add(MESSAGES.getMessage("str.common.exams"));
		HEADERS.add(MESSAGES.getMessage("str.common.diploma"));
		
	}
			
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response) throws Exception {
						
		String sheetName = WorkbookUtil.createSafeSheetName(SHEET_NAME);		
		HSSFSheet sheet = wb.createSheet(sheetName);
				
		@SuppressWarnings("unchecked")
		List<Student> students = (List<Student>)model.get(SHEET_SOURCE_LIST_NAME);
		
		int rowCounter = 0, cellCounter = 0;
		Row head = sheet.createRow(rowCounter++);
		
		CellStyle headerStyle = this.designRowStyle(head, HEADER_ROW);
		
		for (String header : HEADERS) {
			Cell cell = head.createCell(cellCounter++);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
		}
				
		for (Student student : students) {
			Row row = sheet.createRow(rowCounter++); cellCounter = 0;
			
			row.createCell(cellCounter++).setCellValue(student.getFamily() + student.getName() + student.getPatronimic());
			row.createCell(cellCounter++).setCellValue(student.getSpiritualName());		
			row.createCell(cellCounter++).setCellValue(student.getBirthday());
			row.createCell(cellCounter++).setCellValue(student.getCity().getCity() + "/" + student.getCity().getCountry().getCountry());
			
			if (student.getCourse() != Course.NONE)
				row.createCell(cellCounter++).setCellValue(MESSAGES.getMessage("str.enum.Course." + student.getCourse().toString()));
			else {
				row.createCell(cellCounter++).setCellValue("");
			}
			row.createCell(cellCounter++).setCellValue(student.getGroup().getGroup());
			row.createCell(cellCounter++).setCellValue(student.getTel_1());
			row.createCell(cellCounter++).setCellValue(student.getTel_2());
			row.createCell(cellCounter++).setCellValue(student.getEmail_1());
			row.createCell(cellCounter++).setCellValue(student.getEmail_2());
			row.createCell(cellCounter++).setCellValue(student.getSkype());
			row.createCell(cellCounter++).setCellValue(student.getSkypeChat());
			row.createCell(cellCounter++).setCellValue(student.getMailer());
			row.createCell(cellCounter++).setCellValue(student.getConsultant());
			row.createCell(cellCounter++).setCellValue(student.getCrisisContacts());
			row.createCell(cellCounter++).setCellValue(student.getComments());
			row.createCell(cellCounter++).setCellValue(student.getTests().getStartDate());
			row.createCell(cellCounter++).setCellValue(student.getTests().getCourse_1_test() + " " + student.getTests().getCourse_2_test() + student.getTests().getCourse_3_test());
			row.createCell(cellCounter++).setCellValue(student.getTests().getExam_1() + " " + student.getTests().getExam_2() + student.getTests().getExam_3());
			
			if (student.getTests().getDiploma()) {
				row.createCell(cellCounter++).setCellValue(student.getTests().getFinalDiploma().getDiplomaDate());
			}
			else {
				row.createCell(cellCounter++).setCellValue("");
			}										
		}	
		
	    for (int i = 0; i < rowCounter; i++) {	    		    
	    	sheet.autoSizeColumn(i);
	    }		
		
	}
	
	static int getMemoWidth () {		
		return 50*256;									
	}
	

	private static final int HEADER_ROW = 0;
	private static final int ODD_ROW = 1;
	private static final int EVEN_ROW = 2;
	
	private CellStyle designRowStyle (Row row, int t) {
		
		XSSFFont font = (XSSFFont)row.getSheet().getWorkbook().createFont();
		CellStyle style = row.getSheet().getWorkbook().createCellStyle();
		
		switch (t) {
			case HEADER_ROW: {
				font.setColor(IndexedColors.WHITE.index);
				font.setBold(true);
				style.setFillBackgroundColor(IndexedColors.BLACK.index);
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);		
			}
			case ODD_ROW:
			case EVEN_ROW:	
			default:
		}
		style.setFont(font);
		return style;
		
	}

}
