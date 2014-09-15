<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="header">
	<div id="logo"></div>
	<div id="date">Today: 10 June 2013, Tuesday</div>
	<div id="logout">You authorized as MANAGER. <a href="#">logout</a><a href="#">help</a></div>
	<ul class="header">
		<li class="hover">
			<a class="hover" id="bookmark-view" href="${servletURI}/bookmarks/view">
				<img src="${IMAGES}/tv.png"/>
				<spring:message code="str.common.header.bookmarks" text="Bookmarks"/>
			</a>
		</li>
		<li>
			<a id="table-view" href="${servletURI}/student/view">
				<img src="${IMAGES}/playlist.png"/>
				<spring:message code="str.common.header.table" text="Student table"/>
			</a>
		</li>
		<li>
			<a id="dictionary-view" href="${servletURI}/dictionary/view">
				<img src="${IMAGES}/statistics.png"/>
				<spring:message code="str.common.header.dictionaries" text="Dictionaries"/>
			</a>
		</li>
		<li>
			<a id="manager-view" href="${servletURI}/manager/view">
				<img src="${IMAGES}/statistics.png"/>
				<spring:message code="str.common.header.management" text="Management"/>
			</a>
		</li>
	</ul>
</div>