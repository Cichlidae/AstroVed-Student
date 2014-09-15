<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file = "COMMON_HEAD.jsp" %>

<title>${TITLE} - <spring:message code="str.common.STUDENTS" text="STUDENTS"/></title>

<link rel="stylesheet" href="${CSS}/validationEngine.jquery.css" type="text/css"/>

<script src="${JS}/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
<script src="${JS}/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="${JS}/jquery.validationEngine-ru.js" type="text/javascript" charset="utf-8"></script>

<script>
    $(function() {
      $('#linkBroadcast').addClass("hover");  

      $('#message').hide();	
      $('#temporary').hide();	

      $('#country :first').attr("selected", "selected");
      $('#course :first').attr("selected", "selected");
      $('#group :first').attr("selected", "selected");

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

   		// bind change event to select
	    $('#country').bind('change', function () {
	       var val = $(this).val(); 

	       var count = $("select[id='city'] option").size();

	       for (var i = 0; i < count - 1; i++) {
	    	   $("select[id='city'] :last").remove();
	       }    
   
	       if (val != '') {
		       var url = "${servletURI}/bookmarks/cities";
	    	   $.get(url, { id : val }, function(data) {
			          var content = $(data);		  
					  $("select[id='city']").append(content.html());
				});
	       }
	       return false;
	    });

      $('#set-criteria').click(
    	 function() { 	  
    	 }
      );		 	 

    });
</script>

</head>
<body>

	<spring:message code="str.common.apply" text="Apply" var="strApply"/>
	<spring:message code="str.common.search" text="Search" var="strSearch"/>

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
						<a id="search-student" href="${servletURI}/student/search" class="ajax-form">
							<spring:message code="str.common.student.search" text="Search student"/>
						</a>
					</li>	
					<li>
						<a id="form-excel-student-list" href="${servletURI}//student/excel">
							<spring:message code="str.common.student.excel-list.form" text="Form student list in EXCEL"/>
						</a>
					</li>
				</ul>
			</div>
			<div class="sidecard">
				<h3><spring:message code="str.common.quick-search" text="Quick search"/></h3>
				<form method="get" action="${servletURI}/student/search">
					<input name="family" type="text">
					<input type="submit" value=" ${strSearch} ">
				</form>
			</div>
			<div class="sidecard">
				<form:form modelAttribute="studentFilter" action="${servletURI}/student/filter" method="post">
					<h3><spring:message code="str.common.students-filter" text="Filter students"/></h3>
					<p>
						<form:label path="country"><spring:message code="str.common.select.country" text="Select country"/></form:label>:
						<form:select id="country">
							<option value=""></option>
							<c:forEach items="${countries}" var="country">
								<option value="${country.id}">
									<c:out value="${country.country}"/>
								</option>
							</c:forEach>	
						</form:select>
					</p>
					<p>
						<form:label path="city"><spring:message code="str.common.select.city" text="Select city"/></form:label>:
						<form:select id="city">
							<option value=""></option>	
						</form:select>		
					</p>
					<p>
						<form:label path="course"><spring:message code="str.common.select.course" text="Select course"/>:</form:label>
						<form:select id="course">
							<option value=""></option>
							<c:forEach items="${courses}" var="course">
								<option value="${course.key}">
									<c:out value="${course.value}"/>
								</option>
							</c:forEach>	
						</form:select>		
					</p>
					<p>
						<form:label path="group"><spring:message code="str.common.select.group" text="Select group"/>:</form:label>
						<form:select id="group">
							<option value=""></option>
							<c:forEach items="${groups}" var="group">
								<option value="${group.id}">
									<c:out value="${group.group}"/>
								</option>
							</c:forEach>	
						</form:select>				
					</p>
					<p>
						<input type="submit" value="${strApply}">
					</p>
				</form:form>
			</div>
			<div class="sidecard">
				<h3><spring:message code="str.common.markers" text="Markers"/></h3>
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
				<h3><spring:message code="str.common.statictics" text="Statistics"/></h3>
				<table class="stat">
					<tr>
						<th width="60%">
							Total in school:
						</th>
						<th>
							200
						</th>
					</tr>
					<tr>
						<td>
							New: 
						</td>
						<td>
							5
						</td>
					</tr>
					<tr>
						<td>
							Professionals:
						</td>
						<td>
							30
						</td>
					</tr>
					<tr>
						<td>
							Graduated:
						</td>
						<td>
							0
						</td>
					</tr>
					<tr>
						<td>
							Archived:
						</td>
						<td>
							0
						</td>
					</tr>
					<tr>
						<td>
							1 course:
						</td>
						<td>
							120
						</td>
					</tr>
					<tr>
						<td>
							2 course:
						</td>
						<td>
							67
						</td>
					</tr>
					<tr>
						<td>
							3 course:
						</td>
						<td>
							29
						</td>
					</tr>
					<tr>
						<td>
							4 course:
						</td>
						<td>
							11
						</td>
					</tr>
					<tr>
						<td>
							Deleted:
						</td>
						<td>
							2
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<div id="content">	
			<div id="message" class="block">
			</div>
			<div id="temporary" class="block">
			</div>
			<div class="block">
				<div class="card">
					<h3><spring:message code="str.common.student.list" text="Student list"/></h3>
					<table>
						<tr>
							<th width="45%">
								<spring:message code="str.common.student.name" text="Student name"/>
							</th>
							<th>
								<spring:message code="str.common.course" text="Course"/> / <spring:message code="str.common.group" text="Group"/>
							</th>
							<th width="35%">
								<spring:message code="str.common.contacts" text="Contacts"/>
							</th>
						</tr>
						<c:forEach items="${students}" var="student">						
							<tr>
								<td>
									<a href="${servletURI}/student/details?uid=${student.uid}">
										<c:out value="${student.family} ${student.name} ${student.patronimic}"/>	
									</a>
									<br>
									<c:out value="${student.city.city}, ${student.city.country.country}"/>
								</td>
								<c:choose>
									<c:when test="${student.group.id eq 4}">
										<td class="mark-new">
											<spring:message code="str.enum.Course.${student.course}" text="${student.course}"/>
										</td>
									</c:when>
									<c:when test="${student.group.id eq 1}">
										<td class="mark-prof">
											<spring:message code="str.enum.Course.${student.course}" text="${student.course}"/>
										</td>
									</c:when>
									<c:otherwise>
										<td>
											<spring:message code="str.enum.Course.${student.course}" text="${student.course}"/>
										</td>
									</c:otherwise>
								</c:choose>		
								<td>
									<c:out value="${student.tel}"/>
									<a href="mailto:${student.email}"><c:out value="${student.email}"/></a>
								</td>		
							</tr>
						</c:forEach>
					</table>	
					<div class="pager">
						<p>Page 1 of 1</p>
					</div>	
				</div>
			</div>		
		</div>

		<%@ include file = "FOOTER.jsp" %>
	
	</div>
	
</body>
</html>