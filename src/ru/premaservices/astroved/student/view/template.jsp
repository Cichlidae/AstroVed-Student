<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file = "COMMON_HEAD.jsp" %>

<title>${TITLE} - <tiles:insertAttribute name="title" ignore="true" /></title>

<tiles:insertAttribute name="scripts"/>

</head>
<body>

	<div id="wrapper">

		<%@ include file = "HEADER.jsp" %>
	
		<div id="sidebar-b">	
			<tiles:insertAttribute name="sidebar-b" ignore="true" />							
		</div>
		
		<div id="content">	
			<tiles:insertAttribute name="content" ignore="true" />	
		</div>	
	
		<%@ include file = "FOOTER.jsp" %>
		
	</div>
	
</body>
</html>