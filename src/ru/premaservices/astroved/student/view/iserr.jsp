<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file = "COMMON_HEAD.jsp" %>

<title>${TITLE} - <spring:message code="str.common.ERROR" text="ERROR"/></title>

</head>
<body>

	<div class="fatal">
		<spring:message code="str.http-error.500.phrase" text="Internal server error occured."/> 
		<spring:message code="str.http-error.trylater.phrase" text="Plase try later."/>
	</div>
	<div>
		<a href="${servletURI}/boormarks/view"><spring:message code="str.common.back-to-main" text="Back to main page"/></a>
	</div>
	
	<br>
	
	<%@ include file = "FOOTER.jsp" %>

</body>
</html>