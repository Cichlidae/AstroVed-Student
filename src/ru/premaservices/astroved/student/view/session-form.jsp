<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file = "Servlet-PATTERN.jsp" %>

<spring:message code="str.common.add" text="Add" var="strAdd" htmlEscape="true"/>
<spring:message code="str.common.save" text="Save" var="strSave" htmlEscape="true"/>
<spring:message code="str.common.clear" text="Clear" var="strClear" htmlEscape="true"/>

<div class="card">
	<h3><spring:message code="str.common.session.add" text="Add session"/></h3>
	<form:form modelAttribute="session" action="${servletURI}/bookmarks/session" method="post">
		<form:hidden path="id"/>
		<p class="error">
			<form:errors path="name"/>
			<form:errors path="startDate"/>
			<form:errors path="finalDate"/>
		</p>
		<p>
			<form:label path="name">
				<spring:message code="str.common.caption" text="Name"/>*:
			</form:label>
			<form:input path="name" size="60" class="validate[required]"/>
		</p>
		<p>
			<form:label path="startDate">
				<spring:message code="str.common.start-date" text="Start date"/>*:
			</form:label>
			<form:input path="startDate" size="10" class="validate[required,custom[date],future[NOW]]"/>
			<span>&nbsp;</span>
			<form:label path="finalDate">
				<spring:message code="str.common.final-date" text="Final date"/>*:
			</form:label>
			<form:input path="finalDate" size="10" class="validate[required,custom[date],future[NOW]]"/>
		</p>	
		<p>
			<c:choose>
				<c:when test="${not empty session.id}">
					<input type="submit" value="  ${strSave}  "/>
				</c:when>
				<c:otherwise>
					<input type="submit" value="  ${strAdd}  "/>
				</c:otherwise>
			</c:choose>
			<input id="reset" type="reset" value="  ${strClear}  "/>
		</p>	
	</form:form>
</div>

<script type="text/javascript">
	jQuery(function($) {	
		
	    $("input[id='startDate']").mask("99.99.2099", {placeholder:"0"});	   
	  	$("input[id='finalDate']").mask("99.99.2099", {placeholder:"0"}); 

		var formSession =  $("form#session");  	
		formSession.validationEngine('attach', {promptPosition : "centerRight", validationEventTrigger : "submit"});

		$("input#reset").click(function(event) {
			formSession.validationEngine('hideAll');
			return true;
		});

		formSession.submit(function(event) {

			if (!formSession.validationEngine('validate')) {
				return false;
			}
	
			var form = $(this);
			var url = form.attr("action");
			var terms = form.serialize();

			var temporary = $('#temporary');

			$.post(url, terms, function(data) {
		          var content = $(data);
		          var clazz = content.attr('class');
				  if (clazz == 'success') {
					  formSession.validationEngine('detach');
					  temporary.empty().append(content);
				  }
				  else {	          
					 temporary.empty().append(content);
				  }
			});

			return false;
		});

		$("form#session p.error span").each(function(i, element) {
			var e = $(this);	
			var id = e.attr("id");
			alert(id);
			var value = e.html();
			var point = id.indexOf(".errors");		
			id = id.substring(0, point);
			$('#' + id).validationEngine('showPrompt', '* ' + value, 'error');
		});	
	   
	 });
</script>
