<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file = "Servlet-PATTERN.jsp" %>

<tr>
	<td>
		<c:out value="${city.id}"/>
	</td>
	<td>
		<c:out value="${city.city}"/>
	</td>
	<td>
		<c:out value="${city.country.country}"/>
	</td>
	<td>
		<a id="B${city.id}" class="editCity" href="#" style="border:none;">
			<img src="${IMAGES}/edit.png">
		</a>
	</td>
</tr>			