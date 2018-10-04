<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Login</title>
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
</head>
<body class="flow-hidden">
	<div class="container-fluid px-0">
		<nav class="navbar navbar-expand-lg color-main">
			<div class="col-1"></div>
			<div class="col-10">
				<h1
					class="text-light font-weight-bold font-italic border-left border-right">Become
					One Of Us And Make Your Deals.</h1>
			</div>
			<div class="1">
				<a href="<spring:url value="/account/sign-up"></spring:url>"><button
						class="collapse navbar-collapse btn btn-nobg-light font-weight-bold">Sign
						up</button></a>
			</div>
		</nav>
	</div>
	<div class="top-content h-100">
		<div class="container-fluid h-100">
			<div class="row h-100">
				<div class="col-8 bg-info h-100">
					<h1>Banner here</h1>
				</div>
				<div class="col-4 h-100 bg-eee">
					<div class="row">
						<div class="w-100">
							<div class="form-box my-0">
								<div class="form-top py-2">
									<h2 class="text-center font-weight-bold">TechTrade</h2>
								</div>
								<div class="form-bottom">
									<div class="form-group my-3">
										<p class="text-center" id="welcome">Enter Username/
											Email/Phonenumber</p>
									</div>
									<div id="user-info" class="second-stage"></div>
									<p
										class="second-stage text-primary font-weight-bold text-center hidden pointer"
										id="notme-btn">Not you? Try again here.</p>
									<c:url value="/handleLogin" var="loginUrl" />
									<form action="${loginUrl}" method="post" id="form">
										<c:if test="${param.error != null}">
											<p class="text-danger text-center first-stage">Invalid
												password.</p>
										</c:if>
										<c:if test="${param.logout != null}">
											<p class="text-danger">You have been logged out.</p>
										</c:if>
										<p id="result" class="first-stage text-center text-danger"></p>
										<div class="form-group my-3">
											<label for="username" class="sr-only">Username</label> <input
												type="text" name="username" id="username"
												class="w-100 first-stage" placeholder="Username" />
										</div>
										<div class="form-group my-3">
											<label for="password" class="sr-only">Password</label> <input
												type="password" name="password"
												class="w-100 hidden second-stage" placeholder="Password" />
										</div>
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<div class="form-group my-3">
											<div class="btn btn-outline-primary first-stage"
												id="sone-btn">Continue</div>
										</div>
										<div class="form-group my-3">
											<button type="submit" class="btn second-stage hidden">Sign
												in</button>
										</div>
										<div class="form-group my-3 second-stage hidden">
											<p id="forgot" class="pointer font-weight-bold text-primary">I
												forgot my password</p>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/login.js"></spring:url>"></script>
</body>
</html>