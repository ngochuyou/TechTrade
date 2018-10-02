<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<!-- Library Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<title>Manager Location</title>
<style>
.red {
	color: red;
}

.blue {
	color: blue;
}

#standard {
	width: 30%;
	float: left;
	margin: 18px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#standard").customselect();
	});
</script>
</head>

<body>
	<div class="container">
		<%-- <div>
			<select id='standard' name='standard' class='custom-select'>
				<option value=''>Please Select City</option>
				<c:if test="${not empty cityList }">
					<c:forEach var="citySLB" items="${cityList}">
						<option value="${citySLB.id}">${citySLB.name}</option>
					</c:forEach>
				</c:if>
			</select> <select id='standard' name='standard' class='custom-select'>
				<option value=''>Please Select District</option>
				<c:if test="${not empty districtList }">
					<c:forEach var="districtSLB" items="${districtList}">
						<option value="${districtSLB.id}">${districtSLB.name}</option>
					</c:forEach>
				</c:if>
			</select> <select id='standard' name='standard' class='custom-select'>
				<option value=''>Please Select Ward</option>
				<c:if test="${not empty wardList }">
					<c:forEach var="wardSLB" items="${wardList}">
						<option value="${wardSLB.id}">${wardSLB.name}</option>

					</c:forEach>
				</c:if>

			</select>
		</div> --%>
		<table class="table">
			<tr>
				<th>City</th>
				<th>District</th>
			</tr>

			<!-- check list of city before show on web -->
			<c:if test="${empty cityList }">
				<tr>
					<td colspan="3">Empty!!!</td>
				</tr>
			</c:if>

			<c:if test="${ not empty cityList }">
				<c:forEach var="city" items="${cityList}">
					<tr>
						<td
							onclick="window.location.href='<spring:url value="/location?idCity=${city.id}"></spring:url>'">${city.name}</td>
						<td><c:forEach var="district" items="${districtList}">
								<c:if test="${district.city.id eq city.id}">
									<p class="${dID eq district.id?'red':'blue'}"
										onclick="window.location.href='<spring:url value="/location?idDistrict=${district.id}"></spring:url>'">${district.name}</p>
								</c:if>

								<div style="float: right">
									<c:forEach var="ward" items="${wardList}">
										<c:if
											test="${(ward.district.id  eq district.id) and (district.city.id eq city.id)}">
											<p style="color: red">${ward.name}</p>
										</c:if>
									</c:forEach>
								</div>
							</c:forEach></td>

					</tr>
				</c:forEach>
			</c:if>
		</table>

	</div>
</body>
</html>