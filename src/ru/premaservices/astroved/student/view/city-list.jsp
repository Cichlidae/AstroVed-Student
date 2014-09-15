<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div>
		<c:forEach items="${cities}" var="city">
			<option value="${city.id}">
				<c:out value="${city.city}"/>
			</option>
		</c:forEach>
	</div>
