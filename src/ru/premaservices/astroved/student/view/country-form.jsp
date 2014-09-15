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
			<c:when test="${not empty country.id}">
				<h3><spring:message code="str.common.country.edit" text="Edit country"/></h3>
			</c:when>
			<c:otherwise>
				<h3><spring:message code="str.common.country.add" text="Add country"/></h3>
			</c:otherwise>
		</c:choose>
	
		<form:form modelAttribute="country" action="${servletURI}/country/update" method="post" class="country">
			<form:hidden path="id"/>
			<p class="error">
				<form:errors path="country"/>
			</p>
			<p>
				<form:label path="country">
					<spring:message code="str.common.caption" text="Name"/>*:
				</form:label>
				<form:input path="country" size="45" class="validate[required]"/>
			</p>
			<p>
				<c:choose>
					<c:when test="${not empty country.id}">
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

		var formCountry =  $("form.country");  	
		formCountry.validationEngine('attach', {promptPosition : "centerRight", validationEventTrigger : "submit"});

		$("input#reset").click(function(event) {
			formCountry.validationEngine('hideAll');
			return true;
		});

		formCountry.submit(function(event) {

			if (!formCountry.validationEngine('validate')) {
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
					  formCountry.validationEngine('detach');

					  <c:choose>
						<c:when test="${not empty country.id}">

                            var id = 'a#A${country.id}';					
							var el = $(id).parent().parent();
                            el.replaceWith(content); 
									
						</c:when>
						<c:otherwise>
						    $("#countries").append(content);
						</c:otherwise>
					  </c:choose>
	 
			      }
				  else {
					  content.css({
	    		         'margin-left': "-204px",
	    		         'margin-top': "-59px",
	    		      });

			          $("body").append(content);		  
				  }
			});

			return false;
		});

		$("form.country p.error span").each(function(i, element) {
			var e = $(this);	
			var id = e.attr("id");
			var value = e.html();
			var point = id.indexOf(".errors");		
			id = id.substring(0, point);
			$('#' + id).validationEngine('showPrompt', '* ' + value, 'error');
		});	
	   
	 });
</script>
