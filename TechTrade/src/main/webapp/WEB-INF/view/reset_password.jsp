<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/style.css"></spring:url>"
	type="text/css">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/form-elements.css"></spring:url>"
	type="text/css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script
	src="
http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.
js "></script>
</head>
<body>
	<div class="container-fluid px-0">
		<nav class="navbar navbar-expand-lg color-main">
			<div class="col-1"></div>
			<div class="col-10">
				<h1
					class="text-center text-light font-weight-bold font-italic border-left border-right">Become
					One Of Us And Make Your Deals.</h1>
			</div>
			<div class="1">
			</div>
		</nav>
		<p class="hidden" id="code">${code }</p>
		<div class="top-content">
			<div class="container">
				<div class="row">
					<div class="text-center col-sm-8 col-sm-offset-2 mx-auto my-3">
						<h1 class="font-weight-bold">Tech Trade</h1>
						<h3>Reset Password</h3>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-sm-10 m-auto w-75">
						<div class="form-box">
							<div class="form-top">
								<h3 id="instruct" class="text-center font-weight-bold mt-4">Enter the reset code
									to continue</h3>
								<h4 id="warning" class="text-center text-warning"></h4>
							</div>
							<div class="form-bottom">
								<div class="form-group">
									<input type="password" id="code_input"
										class="w-100 text-center" placeholder="Reset code" />
								</div>
								<form:form modelAttribute="accModel" onsubmit="false" id="form"
									class="hidden" method="POST">
									<div class="form-group">
										<form:input path="newPassword" placeholder="New password"
											class="w-100" type="password"/>
									</div>
									<div class="form-group">
										<input type="password" id="rePassword" name="rePassword"
											placeholder="Re-enter new password" class="w-100" />
									</div>
									<div class="form-group">
										<div id="submit-btn" class="btn btn-primary">Submit</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/reset_password.js"></spring:url>"></script>
</body>
</html>