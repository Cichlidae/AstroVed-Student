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

<title>${TITLE} - <spring:message code="str.common.BOOKMARKS" text="BOOKMARKS"/></title>

<link rel="stylesheet" href="${CSS}/validationEngine.jquery.css" type="text/css"/>

<script src="${JS}/jquery.maskedinput-1.3.min.js" type="text/javascript"></script>
<script src="${JS}/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="${JS}/jquery.validationEngine-ru.js" type="text/javascript" charset="utf-8"></script>

<script>
    $(function() {
      $('#linkBroadcast').addClass("hover");  

      $('#message').hide();	
      $('#temporary').hide();	

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

    });
</script>

</head>
<body>

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
						<a id="add-session" href="${servletURI}/bookmarks/session" class="ajax-form">
							<spring:message code="str.common.session.add" text="Add session"/>
						</a>
					</li>
					<li>
						<a id="search-student" href="${servletURI}/student/search" class="ajax-form">
							<spring:message code="str.common.student.search" text="Search student"/>
						</a>
					</li>	
					<li>
						<a id="form-excel-student-list" href="${servletURI}/student/excel">
							<spring:message code="str.common.student.excel-list.form" text="Form student list in EXCEL"/>
						</a>
					</li>
					<li>
						<a id="form-dstlist" href="${servletURI}/bookmarks/diploma-students">
							<spring:message code="str.common.diploma-list.form" text="Form diploma student list in PDF"/>
						</a>
					</li>
				</ul>
			</div>		
			<div id="diplomas" class="sidecard">
				<h3><spring:message code="str.common.diploma.fresh-numbers" text="Fresh diploma numbers"/></h3>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit. In eu lectus quis purus tincidunt convallis nec eget nibh. In non lobortis urna. Nam facilisis leo at facilisis facilisis. In vehicula nibh in tortor aliquet, ut accumsan sapien egestas. 
			</div>
			<br>
		</div>
		
		<div id="content">	
			<div id="message" class="block">
			</div>
			<div id="temporary" class="block">
			</div>
			<c:if test="${not empty session}">
				<div id="session" class="block">
					<div class="card">
						<h3><spring:message code="str.common.session.student-list" text="Current session student list"/></h3>
					</div>
				</div>
			</c:if>
			<div id="bookmarks" class="block">
				<div class="card">
					<h3><spring:message code="str.common.bookmarks" text="My Bookmarks"/></h3>
					
				</div>
			</div>
			<div id="notes" class="block">
				<div class="card">
					<h3><spring:message code="str.common.note.lastest" text="Lastest notes"/></h3>
					<p>If you're involved in an internet marketing business, then you've probably already come face-to-face with your #1 enemy - "Time Wasting".</p>
					<h4>And this applies <em>even more</em> to the online world</h4>
					<p>This sounds simple. What you need to know is that "Time Is Money". <strong>And this applies <em>even more</em> to the online world</strong>, than in <a href="#">regular business</a>.</p>
				</div>
			</div>		
		</div>	
	
		<%@ include file = "FOOTER.jsp" %>
		
	</div>
	
</body>
</html>