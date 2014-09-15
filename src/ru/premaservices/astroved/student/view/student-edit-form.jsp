<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file = "Servlet-PATTERN.jsp" %>

<spring:message code="str.common.add" text="Add" var="strAdd" htmlEscape="true"/>
<spring:message code="str.common.clear" text="Clear" var="strClear" htmlEscape="true"/>

<div class="card">
	<h3><c:out value="${student.family} ${student.name} - ${student.city.city}"/></h3>
	<form:form modelAttribute="student" action="${servletURI}/student/edit" method="post">
		<form:hidden path="uid"/>
		<form:hidden path="family"/>
		<form:hidden path="name"/>
		<form:hidden path="group.id"/>
		<form:hidden path="course"/>
		<form:hidden path="city.id"/>
		<form:hidden path="type"/>
		<form:hidden path="consultant"/>
		<div class="error">		
			<form:errors path="birthday"/>	
			<form:errors path="city"/>
			<form:errors path="email_1"/>	
		</div>
		<fieldset>
			<div>
				<form:label path="patronimic">
					<spring:message code="str.common.patronimic" text="Patronimic"/>:
				</form:label>
				<br>
				<form:input path="patronimic" size="45" text="${student.patronimic}"/>
			</div>
			<div>
				<form:label path="birthday">
					<spring:message code="str.common.birthday" text="Birthday"/>*:
				</form:label>
				<form:input path="birthday" size="10" class="validate[required,custom[date]]" text="${student.birthday}"/>
			</div>
			<div>
				<form:label path="spiritualName">
					<spring:message code="str.common.spiritualName" text="SpiritualName"/>:
				</form:label>
				<br>
				<form:input path="spiritualName" size="45" text="${student.spiritualName}"/>
			</div>		
		</fieldset>
		<fieldset>
			<div>
				<form:label path="tel_1">
					<spring:message code="str.common.tel" text="Tel"/>:
				</form:label>
				<br>
				<form:input path="tel_1" size="30" class="validate[groupRequired[contacts]]" text="${student.tel_1}"/>
			</div>			
			<div>
				<form:label path="email_1">
					<spring:message code="str.common.email" text="E-mail"/>:
				</form:label>
				<br>
				<form:input path="email_1" size="30" class="validate[groupRequired[contacts], custom[email]]" text="${student.email_1}"/>		
			</div>			
			<div>
				<form:label path="tel_2">
					<spring:message code="str.common.tel" text="Tel"/>:
				</form:label>
				<br>
				<form:input path="tel_2" size="30" text="${student.tel_2}"/>
			</div>			
			<div>
				<form:label path="email_2">
					<spring:message code="str.common.email" text="E-mail"/>:
				</form:label>
				<br>
				<form:input path="email_2" size="30" class="validate[custom[email]]" text="${student.email_2}"/>		
			</div>			
			<div>
				<form:label path="skype">
					<spring:message code="str.common.skype" text="Skype"/>:
				</form:label>
				<br>
				<form:input path="skype" size="30" text="${student.skype}"/>		
			</div>			
			<div>
				<form:input type="checkbox" path="skypeChat" value="1" text="${student.skypeChat}"/>
				<form:label path="skypeChat">
					<spring:message code="str.common.skypeChat" text="In Skype chat"/>
				</form:label>									
			</div>			
			<div>
				<form:input type="checkbox" path="mailer" value="1" text="${student.mailer}"/>
				<form:label path="mailer">
					<spring:message code="str.common.mailer" text="Mailer subscription"/>
				</form:label>									
			</div>
		</fieldset>
		<fieldset>
			<form:label path="crisisContacts">
				<spring:message code="str.common.crisis-contacts" text="Additional contacts (relatives)"/>:
			</form:label>
			<br>
			<form:textarea path="crisisContacts" rows="15" cols="30" text="${student.crisisContacts}"/>	
			<br>
			<form:label path="comments">
				<spring:message code="str.common.comments" text=Comments"/>:
			</form:label>
			<br>
			<form:textarea path="comments" rows="15" cols="30" text="${student.comments}"/>	
		</fieldset>			
		<p>
			<input type="submit" value="  ${strAdd}  "/>
			<input id="reset" type="reset" value="  ${strClear}  "/>
		</p>	
	</form:form>
</div>

<script type="text/javascript">
	jQuery(function($) {	   
		
	    $("input[id='birthday']").mask("99.99.9999", {placeholder:"0"});
	    
	    var formStudent =  $("form#student");  	
	    formStudent.validationEngine('attach', {promptPosition : "centerRight", validationEventTrigger : "submit"});

		$("input#reset").click(function(event) {
			formStudent.validationEngine('hideAll');
			return true;
		});

		formStudent.submit(function(event) {

			if (!formStudent.validationEngine('validate')) {
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
					  formStudent.validationEngine('detach');
					  temporary.empty().append(content);
				  }
				  else {	          
					 temporary.empty().append(content);
				  }
			});

			return false;
		});

		$("form#student div.error span").each(function(i, element) {
			var e = $(this);	
			var id = e.attr("id");
			var value = e.html();
			var point = id.indexOf(".errors");		
			$('#' + id).validationEngine('showPrompt', '* ' + value, 'error');		
		});		 
		
	 });
</script>
