<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file = "Servlet-PATTERN.jsp" %>

<spring:message code="str.common.add" text="Add" var="strAdd" htmlEscape="true"/>
<spring:message code="str.common.save" text="Save" var="strSave" htmlEscape="true"/>
<spring:message code="str.common.clear" text="Clear" var="strClear" htmlEscape="true"/>

<div id="popupWnd" class="popup block style-green">

	<a href="#" id="close">
		<img src="${IMAGES}/close_green_small.png">
	</a>
	
	<div class="card">
		<c:choose>
			<c:when test="${not empty city.id}">
				<h3><spring:message code="str.common.city.edit" text="Edit city"/></h3>
			</c:when>
			<c:otherwise>
				<h3><spring:message code="str.common.city.add" text="Add city"/></h3>
			</c:otherwise>
		</c:choose>
	
		<form:form modelAttribute="city" action="${servletURI}/city/update" method="post" class="city">
			<form:hidden path="id"/>
			<p class="error">
				<form:errors path="city"/>
				<form:errors path="country" class="id"/>
			</p>
			<p>
				<form:label path="city">
					<spring:message code="str.common.caption" text="Name"/>*:
				</form:label>
				<form:input path="city" size="45" class="validate[required]"/>
			</p>
			<p>
				<form:label path="country.id">
					<spring:message code="str.common.country" text="Country"/>*:
				</form:label>
				<form:select path="country.id">
	   				<form:option value="">
						-- <spring:message code="str.common.nodata" text=" no data "/> --
					</form:option>
					<c:forEach items="${countries}" var="country">
						<form:option value="${country.id}">
							<c:out value="${country.country}"/>
						</form:option>
					</c:forEach>
				</form:select>
			</p>
			<p>
				<c:choose>
					<c:when test="${not empty city.id}">
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

</div>

<script type="text/javascript">
	jQuery(function($) {	

		$("#close").click(function(event) {
			$("#popupWnd").remove();		
			return false;
		});

		var formCity =  $("form.city");  	
		formCity.validationEngine('attach', {promptPosition : "centerRight", validationEventTrigger : "submit"});

		$("input#reset").click(function(event) {
			formCity.validationEngine('hideAll');
			return true;
		});

		formCity.submit(function(event) {

			if (!formCity.validationEngine('validate')) {
				return false;
			}
	
			var form = $(this);
			var url = form.attr("action");
			var terms = form.serialize();

			$.post(url, terms, function(data) {

				$("#popupWnd").remove();
				
		          var content = $(data);
		          var tag = content.get(0).tagName;

				  if (tag == 'TR') {
					  formCity.validationEngine('detach');
					  $("#cities").append(content);
			      }
				  else {
					  content.css({
	    		         'margin-left': "-204px",
	    		         'margin-top': "-78px",
	    		      });

			          $("body").append(content);		  
				  }
			});

			return false;
		});

		$("form.city p.error span").each(function(i, element) {
			var e = $(this);	
			var id = e.attr("id");
			var value = e.html();
			var point = id.indexOf(".errors");		
			id = id.substring(0, point);
			if (e.attr('class') != null) {
				id += "." + e.attr('class');
				$("select[id='" + id + "']").validationEngine('showPrompt', '* ' + value, 'error');
			}
			else {
				$("#" + id).validationEngine('showPrompt', '* ' + value, 'error');
			}
			
		});	
	   
	 });
</script>
