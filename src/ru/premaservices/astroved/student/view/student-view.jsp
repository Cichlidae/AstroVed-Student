<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.Date" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file = "COMMON_HEAD.jsp" %>

<title>${TITLE} - <spring:message code="str.common.STUDENTS" text="STUDENTS"/></title>

<link rel="stylesheet" href="${CSS}/validationEngine.jquery.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${CSS}/smoothness/jquery-ui-1.8.22.custom.css" />

<script src="${JS}/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
<script src="${JS}/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="${JS}/jquery.validationEngine-ru.js" type="text/javascript" charset="utf-8"></script>
<script src="${JS}/jquery-ui-1.8.22.custom.min.js" type="text/javascript" charset="utf-8"></script>

<script>
    $(function() {
      $('#linkBroadcast').addClass("hover");  

      $('#message').hide();	
      $('#temporary').hide();	

      $('#popup-text').hide();	
    
      $('.ajax-form').click(
      	function() {          	
      		var url = $(this).attr("href");
      		$.get(url, function(data) {
      			 var content = $(data);  		 
      			 var target = $("div#temporary");
      			 target.empty().append(content);
          	});

      		$('#temporary').append($('<div>Please, wait ...</div>')).show();
            return false;
        }
	  );

      $('.ajax-json').click(
      	function() {          	
      		var url = $(this).attr("href");
      		$.get(url, function(data) {
      			 var content = $(data);      			   		 
      			 var target = $("div#temporary");
      			 if (content.status == 1) {
      			 	target.empty().append(content.msg);
      			 }
          	}, "json");

      		$('#temporary').append($('<div>Please, wait ...</div>')).show();
            return false;
        }
	  );

	  function edit_text (var owner, var pname) { 	
		 var url = $(owner).attr("href");	
		 var coord =  owner.parent().offset();
		 var form = $('#popup-text');
		 $('#text-value').attr('value', $('#' + pname).html());
		 $('#text-name').attr('value', pname);
		 form.offset({top:coord.top, left: coord:left});
		 form.show();		 
	  }	 

	 $("a.close").click(function(event) {
		$(".edit-property").hide();		
		return false;
	 });

	 $('.edit-form').submit(
		function (event) {
			var form = $(this);
			var url = form.attr("action");
			var terms = form.serialize();

			$.post(url, terms, function(data) {
				$(".popup-property").hide();
				 var property = $(data);
				 $('#' + property.name).attr('value', property.value);				
			}, "json");				
		}
	 );	 	 	   		
	   
   });
</script>

</head>

<%!
	private Date timestamp = new Date();
%>

<body>

	<spring:message code="str.common.update" text="Update" var="strUpdate" htmlEscape="true"/>

	<div id="popup-text" class="edit-property popup-field block style-yellow">

		<a href="#" class="close">
			<img src="${IMAGES}/close_yellow_small.png">
		</a>
		
		<div class="card">
			<form method="post" action="${servletURI}/student/update-property" class="edit-form">			
				<input type="text" id="text-value" value="">
				<input type="submit" value=" ${strUpdate} ">
			</form>
		</div>
	
	</div>

	<div id="wrapper">

		<%@ include file = "HEADER.jsp" %>
		
		<div id="sidebar-b">
			<div class="sidecard">
				<h3>Menu</h3>
				<ul class="menu">
					<li>
						<a id="add-student" href="${servletURI}/student/add" class="ajax-form">
							<spring:message code="str.common.student.add" text="Add student"/>
						</a>
					</li>
					<li>
						<a id="edit-student" href="${servletURI}/student/edit" class="ajax-form">
							<spring:message code="str.common.student.edit" text="Edit student"/>
						</a>
					</li>					
					<li>
						<a id="add-payment" href="${servletURI}/payment/add" class="ajax-form">
							<spring:message code="str.common.payment.add" text="Add payment"/>
						</a>
					</li>	
					<c:if test="${not empty session}">	
						<li>
							<a id="register" href="${servletURI}/student/register?uid=${student.uid}" class="ajax-json">
								<spring:message code="str.common.student.register" text="Register to retrit"/>														
							</a>
							<span><br><c:out value="${session.name}"/></span>
						</li>
					</c:if>	
					<c:if test="${fn:contains(flags, '!ENROLL~')}">	
						<li>
							<a id="enroll" href="${servletURI}/student/enroll?uid=${student.uid}" class="ajax-json">
								<spring:message code="str.common.student.enroll" text="Enroll student"/>														
							</a>						
						</li>
					</c:if>
					<c:if test="${fn:contains(flags, '!TEST~')}">	
						<li>
							<a id="test" href="${servletURI}/student/test?uid=${student.uid}" class="ajax-json">
								<spring:message code="str.common.student.take-test" text="Take course work"/>														
							</a>						
						</li>
					</c:if>
					<c:if test="${fn:contains(flags, '!EXAM~')}">	
						<li>
							<a id="test" href="${servletURI}/student/exam?uid=${student.uid}" class="ajax-json">
								<spring:message code="str.common.student.take-exam" text="Take exam"/>														
							</a>						
						</li>
					</c:if>
					<c:if test="${fn:contains(flags, '!PROCEED~')}">
						<li>
							<a id="test" href="${servletURI}/student/proceed?uid=${student.uid}" class="ajax-json">
								<spring:message code="str.common.student.proceed-course" text="Proceed to next course"/>														
							</a>						
						</li>
					</c:if>
					<c:if test="${fn:contains(flags, '!GRADUATE~')}">
						<li>
							<a id="test" href="${servletURI}/student/graduate?uid=${student.uid}" class="ajax-json">
								<spring:message code="str.common.student.graduate" text="Take diploma & graguate"/>														
							</a>						
						</li>
					</c:if>
					<c:if test="${fn:contains(flags, '!EXCLUDE~')}">	
						<li>
							<a id="exclude" href="${servletURI}/student/exclude?uid=${student.uid}" class="ajax-json">
								<spring:message code="str.common.student.exclude" text="Exclude student"/>														
							</a>							
						</li>
					</c:if>	
					<li>
						<a id="form-pdf-student-info" href="${servletURI}/student/pdf">
							<spring:message code="str.common.student.pdf.form" text="Form student card in PDF"/>
						</a>
					</li>
				</ul>
			</div>		
			<div class="sidecard">
				<h3>Mark signing</h3>
				<table>
					<tr>
						<td width="40px">
							<img src="${IMAGES}/label_new_small.png">
						</td>
						<td>
							for new students
						</td>
					</tr>
					<tr>
						<td width="40px">
							<img src="${IMAGES}/label_dollar_small.png">
						</td>
						<td>
							for professionals
						</td>
					</tr>
				</table>
			</div>
			<div class="sidecard">
				<h3>Tests</h3>
			</div>
			<div class="sidecard">
				<h3>Sessions visited</h3>
			</div>
			
		<!--  	<div class="sidecard">
				<c:out value="student.importantNotes"/>
			</div>	-->	
					
			<c:if test="${not empty student.startDate}">
				<div class="sidecard">
					<table class="tests">
						<tr>
							<th colspan="2">
								<spring:message code="str.enum.Course.FIRST" text="Course 1st"/>	
							</th>
						</tr>
						<c:if test="${student.tests.course_1_test}">
							<tr>
								<td>
									<spring:message code="str.common.course-work" text="Course work"/>
								</td>
								<td class="ok"/>							
							</tr>
						</c:if>
						<c:if test="${student.tests.exam_1}">
							<tr>
								<td>
									<spring:message code="str.common.exam" text="Exam"/>
								</td>
								<td class="ok"/>							
							</tr>
						</c:if>
						<c:if test="${student.tests.diploma_1.number > 0}">
							<tr>
								<td>
									<spring:message code="str.common.diploma" text="Diploma"/>№
								</td>
								<td>
									<c:out value="${student.tests.diploma_1.number}"/>
									<c:out value="${student.tests.diploma_1.diplomaDate}"/>
								</td>							
							</tr>
						</c:if>	
						<tr>
							<th colspan="2">
								<spring:message code="str.enum.Course.SECOND" text="Course 2nd"/>	
							</th>
						</tr>
						<c:if test="${student.tests.course_2_test}">
							<tr>
								<td>
									<spring:message code="str.common.course-work" text="Course work"/>
								</td>
								<td class="ok"/>							
							</tr>
						</c:if>
						<c:if test="${student.tests.exam_2}">
							<tr>
								<td>
									<spring:message code="str.common.exam" text="Exam"/>
								</td>
								<td class="ok"/>							
							</tr>
						</c:if>
						<c:if test="${student.tests.diploma_2.number > 0}">
							<tr>
								<td>
									<spring:message code="str.common.diploma" text="Diploma"/>№
								</td>
								<td>
									<c:out value="${student.tests.diploma_2.number}"/>
									<c:out value="${student.tests.diploma_2.diplomaDate}"/>
								</td>							
							</tr>
						</c:if>		
						<tr>
							<th colspan="2">
								<spring:message code="str.enum.Course.THIRD" text="Course 3rd"/>	
							</th>
						</tr>
						<c:if test="${student.tests.course_3_test}">
							<tr>
								<td>
									<spring:message code="str.common.course-work" text="Course work"/>
								</td>
								<td class="ok"/>							
							</tr>
						</c:if>
						<c:if test="${student.tests.exam_3}">
							<tr>
								<td>
									<spring:message code="str.common.exam" text="Exam"/>
								</td>
								<td class="ok"/>							
							</tr>
						</c:if>
						<c:if test="${student.tests.diploma_3.number > 0}">
							<tr>
								<td>
									<spring:message code="str.common.diploma" text="Diploma"/>№
								</td>
								<td>
									<c:out value="${student.tests.diploma_3.number}"/>
									<c:out value="${student.tests.diploma_3.diplomaDate}"/>
								</td>							
							</tr>
						</c:if>		
						<tr>
							<th colspan="2">
								<spring:message code="str.enum.Course.FORTH" text="Course 4th"/>	
							</th>
						</tr>					
						<c:if test="${student.tests.exam_3}">
							<tr>
								<td>
									<spring:message code="str.common.protection-degree" text="Protection degree"/>
								</td>
								<td class="ok"/>							
							</tr>
						</c:if>
						<c:if test="${student.tests.finalDiploma.number > 0}">
							<tr>
								<td>
									<spring:message code="str.common.diploma" text="Diploma"/>№
								</td>
								<td>
									<c:out value="${student.tests.finalDiploma.number}"/>
									<c:out value="${student.tests.finalDiploma.diplomaDate}"/>
								</td>							
							</tr>
						</c:if>																
					</table>
				</div>	
			</c:if>						
			<!-- <div class="sidecard">
				<c:out value="student.sessions"/>
			</div>-->
		</div>
	
		<div id="content">	
			<div id="message" class="block">
			</div>
			<div id="temporary" class="block">
			</div>
			<div class="block">
				<div class="card">
					<div class="face">
						<a href="${servletURI}/student/upload?uid=${student.uid}">
							<img src="${servletURI}/student/avatar?uid=${student.uid}&ts=<%=timestamp.getTime()%>">
						</a>	
						<h2>
							<c:out value="${student.family} ${student.name} ${student.patronimic}"/>
						</h2>
						<h5><c:out value="${student.spiritualName}"/></h5>
					</div>
				</div>
				<div class="card">
					<table>
						<tr>
							<th>
			    				<spring:message code="str.common.birthday" text="Birthday"/><br>
			    			</th>
			    			<th>
			    				<spring:message code="str.common.country" text="Country"/> / <spring:message code="str.common.city" text="City"/><br>
			    			</th>
			    			<th>
			    				<spring:message code="str.common.course" text="Course"/><br>		    				
			    			</th>
			    			<th>
			    				<spring:message code="str.common.group" text="Group"/><br>		    			
			    			</th>
						</tr>
						<tr>	    		
			    			<td>
			    				<c:out value="${student.birthday}"/>
			    			</td>
			    			<td>
			    				<c:out value="${student.city.city}"/>,<br>
			    				<c:out value="${student.city.country.country}"/>	    			
			    			</td>
			    			<td>			    				
			    				<strong><spring:message code="str.enum.Course.${student.course}" text="${student.course}"/></strong>,<br>
			    				<spring:message code="str.enum.Type.${student.type}" text="${student.type}"/>
			    			</td>
			    			<c:choose>
								<c:when test="${student.group.id eq 4}">
									<td class="mark-new">
										<c:out value="${student.group.group}"/>
									</td>
								</c:when>
								<c:when test="${student.group.id eq 1}">
									<td class="mark-prof">
										<c:out value="${student.group.group}"/>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<c:out value="${student.group.group}"/>
									</td>
								</c:otherwise>
							</c:choose>				
			    		</tr>	    				
					</table>				
    				<spring:message code="str.common.right-to-consult" text="Has schools rights to consult"/>:
    				<c:choose>
    					<c:when test="${student.consultant eq true}">
    						<img src="${IMAGES}/check.png">
    					</c:when>
    					<c:otherwise>
    						<strong>No</strong>
    					</c:otherwise>
    				</c:choose>		    			
				</div>	
				<div class="card">
					<table>
						<tr>
							<th width="25%">
			    				<spring:message code="str.common.tel" text="Tel"/>
			    			</th>
			    			<th>
			    				<spring:message code="str.common.email" text="Email"/>
			    			</th>
			    			<th width="30%">
			    				<spring:message code="str.common.skype" text="Skype"/>		    				
			    			</th>
						</tr>
						<tr>
							<td>
			    				<span id="tel_1" class="edit-value"><c:out value="${student.tel_1}"/></span>
			    				<a href="${servletURI}/student/update-property" onclick="edit_text(this, 'tel_1')"><img src="${IMAGES}/edit.png"></a>
			    			</td>
			    			<td>
			    				<c:out value="${student.email_1}"/>
			    				<a href="#"><img src="${IMAGES}/edit.png"></a>
			    			</td>
			    			<td>
			    				<c:out value="${student.skype}"/>
			    				<a href="#"><img src="${IMAGES}/edit.png"></a>
			    			</td>
						</tr>
						<tr>
							<th>
			    				<spring:message code="str.common.tel" text="Tel"/>&nbsp;2
			    			</th>
			    			<th>
			    				<spring:message code="str.common.email" text="Email"/>&nbsp;2
			    			</th>
			    			<th>
			    				<spring:message code="str.common.skypeChat" text="In Skype chat"/> / <spring:message code="str.common.mailer" text="Mailer subscription"/>	    				
			    			</th>
						</tr>
						<tr>
							<td>
			    				<c:out value="${student.tel_2}"/>
			    				<a href="#"><img src="${IMAGES}/edit.png"></a>
			    			</td>
			    			<td>
			    				<c:out value="${student.email_2}"/>
			    				<a href="#"><img src="${IMAGES}/edit.png"></a>
			    			</td>
			    			<td>
			    				<c:choose>
			    					<c:when test="${student.skypeChat eq true}">
			    						<img src="${IMAGES}/check.png">
			    					</c:when>
			    					<c:otherwise>
			    						<strong>No</strong>
			    					</c:otherwise>			    					
			    				</c:choose>
			    				<a href="#"><img src="${IMAGES}/edit.png"></a>
			    				<c:out value=" / "/>
			    				<c:choose>
			    					<c:when test="${student.mailer eq true}">
			    						<img src="${IMAGES}/check.png">
			    					</c:when>
			    					<c:otherwise>
			    						<strong>No</strong>
			    					</c:otherwise>
			    				</c:choose>
			    				<a href="#"><img src="${IMAGES}/edit.png"></a>
			    			</td>
						</tr>
					</table>	
			    	<spring:message code="str.common.crisis-contacts" text="Additional contacts (relatives)"/>: 
			    	<c:if test="${empty student.crisisContacts}">
			    		<strong>No</strong>
			    	</c:if>
			    	<br>
			    	<c:out value="${student.crisisContacts}"/>	
			    	<a href="#"><img src="${IMAGES}/edit.png"></a>
					<br>
					<div id="tabs">
						<ul id="tabs-header" class="tabs">
							<li class="active">
								<a class="tab-link" href="#tab-comments">
									<spring:message code="str.common.comments" text="Comments"/>
								</a>
							</li>
							<li>											
								<a class="tab-link" href="${servletURI}/student/notes?uid=${student.uid}">
									<spring:message code="str.common.note-section" text="Contact history"/>
								</a>																													
							</li>
							<li>											
								<a class="tab-link" href="${servletURI}/student/payment?uid=${student.uid}">
									<spring:message code="str.common.payment-section" text="Payment"/>
								</a>																													
							</li>							
						</ul>	
						<div class="tab-content tab-persistent" id="tab-comments">
							<c:out value="${student.comments}"/>
							If you're involved in an internet marketing business, then you've probably already come face-to-face with your #1 enemy - "Time Wasting".
							<a href="#"><img src="${IMAGES}/edit.png"></a>
						</div>
					</div>
				</div>	
			</div>	
			
		</div>
		
		<%@ include file = "FOOTER.jsp" %>
	</div>	
	
</body>
</html>