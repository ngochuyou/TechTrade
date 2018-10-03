<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Login &amp; Register</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet"
	href="<spring:url value="/resources/font-awesome/css/font-awesome.min.css"></spring:url>">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/form-elements.css"></spring:url>"
	type="text/css">
<link rel="shortcut icon"
	href="<spring:url value="/resources/ico/favicon.png"></spring:url>">
<script
	src="
http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.
js "></script>
<link rel="stylesheet"
	href="<spring:url value="/resources/css/style.css"></spring:url>"
	type="text/css">
<style>
</style>
</head>
<body>
	<div class="container-fluid px-0">
		<nav class="navbar navbar-expand-lg color-main">
			<div class="col-1"></div>
			<div class="col-10">
				<h1
					class="text-light font-weight-bold font-italic border-left border-right">Become
					One Of Us And Make Your Deals.</h1>
			</div>
			<div class="1">
				<button class="btn btn-nobg-light font-weight-bold">Sign in</button>
			</div>
		</nav>
	</div>
	<div class="top-content">
		<div class="inner-bg">
			<div class="container">
				<div class="row">
					<div class="col-sm-8 col-sm-offset-2 m-auto">
						<h1 class="font-weight-bold">Tech Trade</h1>
						<h3>Login &amp; Register</h3>
						<div class="description"></div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-7 col-sm-10 m-auto w-75">
						<div class="form-box">
							<div class="form-top">
								<div class="form-top-left">
									<h3 class="font-weight-bold">Sign up now</h3>
									<p class="text-medium">Fill in the form below to get
										instant access:</p>
								</div>
								<div class="form-top-right">
									<i class="fa fa-pencil"></i>
								</div>
							</div>
							<div class="form-bottom">
								<form:form modelAttribute="regisAcc" onsubmit="false"
									id="reg-form">
									<div class="form-group">
										<label class="sr-only" for="form-email">Email</label>
										<p id="email-check-result" class="text-danger"></p>
										<form:input path="email" id="email" placeholder="Email... "
											class="w-100" />
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-username">Username</label>
										<p id="username-check-result" class="text-danger"></p>
										<form:input path="username" id="username"
											placeholder="Username..." class="w-100" />
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password">Password</label>
										<form:input type="password" path="password" id="password"
											placeholder="Password..." class="w-100" />
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password">Confirm
											Password</label> <input type="password" name="confirmPassword"
											id="confirmPassword" class="w-100" placeholder="Confirm Password">
									</div>
									<div class="form-group">
										<form:input type="text" path="phone" id="phone"
											placeholder="Phonenumber" class="w-100" />
									</div>
									<div class="form-group">
										<form:select path="gender" class="custom-select hpx-50">
											<form:option value="Male">Male</form:option>
											<form:option value="Female">Female</form:option>
											<form:option value="Other">Other</form:option>
										</form:select>
									</div>
									<div class="border-bottom my-3"></div>
									<p class="text-medium">Let us know your location so that
										you will have the best experience browsing the posts.</p>
									<div class="form-group">
										<select id="city-select" class="custom-select hpx-50">
											<c:forEach var="city" items="${cityList }">
												<option value="${city.id }">${city.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<select id="district-select" class="custom-select hpx-50">
											<c:forEach var="district" items="${districtList }">
												<option value="${district.id }">${district.name }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<form:select path="wardId" id="ward-select"
											class="custom-select hpx-50">
											<c:forEach var="ward" items="${wardList }">
												<option value="${ward.id }">${ward.name }</option>
											</c:forEach>
										</form:select>
									</div>
									<div class="form-group"></div>
									<form:button type="submit" class="btn">Sign me up!</form:button>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<spring:url value="/resources/js/registry.js"></spring:url>"></script>
</html>