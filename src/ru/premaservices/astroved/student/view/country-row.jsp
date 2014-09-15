<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file = "Servlet-PATTERN.jsp" %>

<tr>
	<td>
		<c:out value="${country.id}"/>
	</td>
	<td>
		<c:out value="${country.country}"/>
	</td>
	<td>
		<a id="A${country.id}" class="editCountry" href="#" style="border:none;">
			<img src="${IMAGES}/edit.png">
		</a>
	</td>
</tr>		