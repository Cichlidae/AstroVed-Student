<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file = "Servlet-PATTERN.jsp" %>

<spring:message code="str.common.add" text="Add" var="strAdd" htmlEscape="true"/>
<spring:message code="str.common.save" text="Save" var="strSave" htmlEscape="true"/>
<spring:message code="str.common.clear" text="Clear" var="strClear" htmlEscape="true"/>

<div id="popupWnd" class="popup block style-yellow">

	<a href="#" id="close">
		<img src="${IMAGES}/close_yellow_small.png">
	</a>
	
	<div class="card">
		
		<h3><spring:message code="str.common.avatar.upload" text="Upload avatar"/></h3>
		
		<form:form modelAttribute="avatar" action="${servletURI}/student/upload" method="post" enctype="multipart/form-data">
			<form:hidden path="uid"/>
			<form:input path="avatarFile" type="file"/>
			<p>			
				<input type="submit" value="  ${strAdd}  "/>			
			</p>	
		</form:form>
	</div>

</div>

<script type="text/javascript">
	jQuery(function($) {	
		$("#close").click(function(event) {
			$("#popupWnd").remove();		
			return false;
		});	   
	 });
</script>
	