<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<%@ include file = "COMMON_HEAD.jsp" %>

<title>${TITLE} - <spring:message code="str.common.DICTIONARIES" text="DICTIONARIES"/></title>

<link rel="stylesheet" href="${CSS}/validationEngine.jquery.css" type="text/css"/>

<script src="${JS}/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="${JS}/jquery.validationEngine-ru.js" type="text/javascript" charset="utf-8"></script>

<script>
    $(function() {
	    var menulink =  $('#dictionary-view');
	    $("ul.header li").removeClass("hover");
	    $("ul.header li a").removeClass("hover");
	    menulink.addClass("hover");  
	    menulink.parent().addClass("hover");

	    var tabli, tabid;
		$("#tabs div.tab-content").hide();
		
		$("a.tab-link").click (
			function() { 	
				var id = $(this).attr('href').substring(1);	
				if (tabid != '') {
					$("#" + tabid).hide();
				}
				$("#" + id).show();		
				if (tabli != null) {
					tabli.removeClass("active");	
				}
				tabli = $(this).parent();
				tabli.addClass("active");
				tabid = id;			
				return false;
		});

		$("#tabs ul li:first a.tab-link").click();

		$("#addCountry").click(function(event) {

			$(".popup").remove();

			var url = "${servletURI}/country/update";

			$.get(url, function(data) {
		          var content = $(data);

		          content.css({
    		         'margin-left': "-204px",
    		         'margin-top': "-59px",
    		      });

		          $("body").append(content);
	           
			});
			
			return false;
		});

		$("#addCity").click(function(event) {

			$(".popup").remove();

			var url = "${servletURI}/city/update";

			$.get(url, function(data) {
		          var content = $(data);

		          content.css({
    		         'margin-left': "-204px",
    		         'margin-top': "-78px",
    		      });

		          $("body").append(content);
	           
			});
			
			return false;
		});

		$(".editCountry").click(function(event) {

			$(".popup").remove();

			var id = $(this).attr("id").substring(1);

			var url = "${servletURI}/country/update/" + id;

			$.get(url, function(data) {
		          var content = $(data);

		          content.css({
	  		         'margin-left': "-204px",
	  		         'margin-top': "-59px",
  		          });

		      		$("body").append(content);
	           
			});

			return false;
		});	

		$(".editCity").click(function(event) {

			$(".popup").remove();

			var id = $(this).attr("id").substring(1);

			var url = "${servletURI}/city/update" + id;

			$.get(url, function(data) {
		          var content = $(data);

		          content.css({
    		         'margin-left': "-204px",
    		         'margin-top': "-78px",
    		      });

		          $("body").append(content);
	           
			});
			
			return false;
		});

    });
</script>

</head>
<body>

	<div id="wrapper">

		<%@ include file = "HEADER.jsp" %>
	
		<div id="content" style="width:100%;">	
			<div class="block">
				<div class="card">
					<h3><spring:message code="str.common.dictionaries" text="Dictionaries"/></h3>
					<div id="tabs">
						<ul id="tabs-header" class="tabs">
							<li>
								<a class="tab-link" href="#tab-countries">
									<spring:message code="str.common.countries" text="Countries"/>
								</a>
							</li>
							<li>											
								<a class="tab-link" href="#tab-cities">
									<spring:message code="str.common.cities" text="Cities"/>
								</a>																													
							</li>						
						</ul>	
						<div class="tab-content tab-persistent" id="tab-countries">
							<div style="display:inline; width: 50%;">
								<table id="countries">
									<tr>
										<th>
											ID
										</th>
										<th>
											<spring:message code="str.common.country" text="Country"/>
										</th>
										<th width="34px">
										</th>
									</tr>
									<c:forEach items="${countries}" var="country">
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
									</c:forEach>
								</table>
								<div style="text-align:right;">
									<a id="addCountry" href="#">Add country</a>
								</div>
							</div>
						</div>
						<div class="tab-content tab-persistent" id="tab-cities">
							<table id="cities">
								<tr>
									<th>
										ID
									</th>
									<th>
										<spring:message code="str.common.city" text="City"/>
									</th>
									<th>
										<spring:message code="str.common.country" text="Country"/>
									</th>
									<th width="34px">
									</th>
								</tr>
								<c:forEach items="${countries}" var="country">
									<c:forEach items="${country.cities}" var="city">
										<tr>
											<td>
												<c:out value="${city.id}"/>
											</td>
											<td>
												<c:out value="${city.city}"/>
											</td>
											<td>
												<c:out value="${country.country}"/>
											</td>
											<td>
												<a id="B${city.id}" class="editCity" href="#" style="border:none;">
													<img src="${IMAGES}/edit.png">
												</a>
											</td>
										</tr>										
									</c:forEach>									
								</c:forEach>
							</table>	
							<div style="text-align:right;">
								<a id="addCity" href="#">Add city</a>		
							</div>			
						</div>
					</div>
				</div>
			</div>
		</div>	
	
		<%@ include file = "FOOTER.jsp" %>
		
	</div>
	
</body>
</html>