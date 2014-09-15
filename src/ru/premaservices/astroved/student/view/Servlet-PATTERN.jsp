<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set value="/astroved-st" var="PATTERN" scope="page"/>
<c:url value="${PATTERN}" var="servletURI"/>

<c:set var="IMAGES" value="${servletURI}/includes/images"/>
<c:set var="CSS" value="${servletURI}/includes"/>
<c:set var="JS" value="${servletURI}/js"/>