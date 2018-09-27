<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.3.1.js"
	integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
	crossorigin="anonymous"></script>
</head>
<body>
	<c:set var="path" value="/TechTrade"></c:set>
	<h1>Admin Only: Cities Management</h1>
	<c:if test="${empty city.cityList }">
		<p>No city found</p>
	</c:if>
	<c:if test="${not empty city.cityList }">
		<table>
			<tr>
				<th>City Id</th>
				<th>City Name</th>
			</tr>
			<c:forEach var="cl" items="${city.cityList }">
				<tr>
					<td>${cl.id }</td>
					<td>${cl.name }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<h2>Create new City</h2>
	<form:form modelAttribute="cityDetail"
		action="${path }/admin/city/create " method="POST">
		<label>City Id</label>
		<form:input path="id" />
		<label>City Name</label>
		<form:input path="name" />
		<form:button type="submit">Create</form:button>
	</form:form>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/admin_city.js"></spring:url>"></script>
</body>
</html>