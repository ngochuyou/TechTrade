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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>404 HTML Tempalte by Colorlib</title>

<!-- Google font -->
<link
	href="https://fonts.googleapis.com/css?family=Montserrat:400,700,900"
	rel="stylesheet">

<!-- Custom stlylesheet -->
<link rel="stylesheet"
	href="<spring:url value="/resources/css/error.css"></spring:url>"
	type="text/css">
<!-- <link type="text/css" rel="stylesheet" href="css/style.css" /> -->
</head>
<body>
	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>Oops!</h1>
			</div>
			<h2>404 - Page not found</h2>
			<form:errors path=""></form:errors>
			<c:if test="${not empty error }">
				<p>${error }</p>
			</c:if>
			<a href="#" onclick="history.back()">Back</a>
		</div>
	</div>



</body>
</html>