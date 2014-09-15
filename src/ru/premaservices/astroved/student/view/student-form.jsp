<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file = "Servlet-PATTERN.jsp" %>

<spring:message code="str.common.add" text="Add" var="strAdd" htmlEscape="true"/>
<spring:message code="str.common.clear" text="Clear" var="strClear" htmlEscape="true"/>

<div class="card">
	<h3><spring:message code="str.common.student.add" text="Add student"/></h3>
	<form:form modelAttribute="student" action="${servletURI}/student/add" method="post">
		<form:hidden path="group.id"/>
		<form:hidden path="course"/>
		<div class="error">
			<form:errors path="family"/>
			<form:errors path="name"/>
			<form:errors path="birthday"/>	
			<form:errors path="city"/>
			<form:errors path="email_1"/>	
		</div>
		<table>
			<tr>
				<td>
					<form:label path="family">
						<spring:message code="str.common.family" text="Family"/>*:
					</form:label>
					<br>
					<form:input path="family" size="45" class="validate[required]"/>
				</td>
				<td>
					<form:label path="type">
						<spring:message code="str.common.type" text="Type"/>*:
					</form:label>
					<form:select path="type" class="validate[required]"> 
			         	<form:options items="${type}"/>
			    	</form:select>
				</td>		
			</tr>
			<tr>
				<td>
					<form:label path="name">
						<spring:message code="str.common.name" text="Name"/>*:
					</form:label>
					<br>
					<form:input path="name" size="45" class="validate[required]"/>
				</td>	
				<td>
					<label for="country">
						<spring:message code="str.common.country" text="Country"/>:
					</label>
					<select id="country" name="country">
		   				<option value="">
							-- <spring:message code="str.common.nodata" text=" no data "/> --
						</option>
						<c:forEach items="${countries}" var="country">
							<option value="${country.id}">
								<c:out value="${country.country}"/>
							</option>
						</c:forEach>
					</select>		
				</td>	
			</tr>
			<tr>
				<td>
					<form:label path="patronimic">
						<spring:message code="str.common.patronimic" text="Patronimic"/>:
					</form:label>
					<br>
					<form:input path="patronimic" size="45"/>
				</td>
				<td>
					<form:label path="city.id">
						<spring:message code="str.common.city" text="City"/>*:
					</form:label>
					<form:select path="city.id" class="validate[required]">
		   				<form:option value="">
							-- <spring:message code="str.common.nodata" text=" no data "/> --
						</form:option>
					</form:select>		
				</td>
			</tr>
			<tr>
				<td>
					<form:label path="spiritualName">
						<spring:message code="str.common.spiritualName" text="SpiritualName"/>:
					</form:label>
					<br>
					<form:input path="spiritualName" size="45"/>
				</td>
				<td>
					<form:label path="tel_1">
						<spring:message code="str.common.tel" text="Tel"/>:
					</form:label>
					<br>
					<form:input path="tel_1" size="30" class="validate[groupRequired[contacts]]"/>
				</td>		
			</tr>
			<tr>
				<td>
					<form:label path="birthday">
						<spring:message code="str.common.birthday" text="Birthday"/>*:
					</form:label>
					<form:input path="birthday" size="10" class="validate[required,custom[date]]"/>
				</td>		
				<td>
					<form:label path="email_1">
						<spring:message code="str.common.email" text="E-mail"/>:
					</form:label>
					<br>
					<form:input path="email_1" size="30" class="validate[groupRequired[contacts], custom[email]]"/>		
				</td>
			</tr>
		</table>
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

			var count = $("select[id='city.id'] option").size();

	        for (var i = 0; i < count - 1; i++) {
	    	   $("select[id='city.id'] :last").remove();
	        }  
			
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
			id = id.substring(0, point);
			if (id == 'city') {
				$("select[id='city.id']").validationEngine('showPrompt', '* ' + value, 'error');
			}
			else {
				$('#' + id).validationEngine('showPrompt', '* ' + value, 'error');
			}
		});	

	    // bind change event to select
	    $('#country').bind('change', function () {
	       var val = $(this).val(); 

	       var count = $("select[id='city.id'] option").size();

	       for (var i = 0; i < count - 1; i++) {
	    	   $("select[id='city.id'] :last").remove();
	       }    
     
	       if (val != '') {
		       var url = "${servletURI}/bookmarks/cities";
	    	   $.get(url, { id : val }, function(data) {
			          var content = $(data);		  
					  $("select[id='city.id']").append(content.html());
				});
	       }
	       return false;
	    });
		
	 });
</script>
