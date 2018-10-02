<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login &amp; Register</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Popper JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

<!-- CSS -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet"
	href="<spring:url value="/resources/bootstrap/css/bootstrap.min.css"></spring:url>">

<link rel="stylesheet"
	href="<spring:url value="/resources/font-awesome/css/font-awesome.min.css"></spring:url>">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/form-elements.css"></spring:url>"
	type="text/css">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/style.css"></spring:url>"
	type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

<!-- Favicon and touch icons -->
<link rel="shortcut icon"
	href="<spring:url value="/resources/ico/favicon.png"></spring:url>">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="<spring:url value="/resources/ico/apple-touch-icon-144-precomposed.png"></spring:url>">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="<spring:url value="/resources/ico/apple-touch-icon-114-precomposed.png"></spring:url>">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="<spring:url value="/resources/ico/apple-touch-icon-72-precomposed.png"></spring:url>">

<link rel="apple-touch-icon-precomposed"
	href="<spring:url value="/resources/ico/apple-touch-icon-57-precomposed.png"></spring:url>"
	type="text/css">
<style>
.my-form {
	margin: auto;
}
</style>

</head>

<body>

	<!-- Top content -->
	<div class="top-content">

		<div class="inner-bg">
			<div class="container">

				<div class="row">
					<div class="col-sm-8 col-sm-offset-2 text">
						<h1>Tech Trade</h1>
						<h3>Login &amp; Register</h3>
						<div class="description">
							<!-- <p>
								This is a free responsive <strong>"login and register
									forms"</strong> template made with Bootstrap. Download it on <a
									href="http://azmind.com" target="_blank"><strong>AZMIND</strong></a>,
								customize and use it as you like!
							</p> -->
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6 col-sm-10" style="margin: auto">
						<div class="form-box ">
							<div class="form-top">
								<div class="form-top-left">
									<h3>Sign up now</h3>
									<p>Fill in the form below to get instant access:</p>
								</div>
								<div class="form-top-right">
									<i class="fa fa-pencil"></i>
								</div>
							</div>
							<div class="form-bottom">
								<form:form modelAttribute="regisAcc" onsubmit="false">

									<div class="form-group">
										<label class="sr-only" for="form-email">Email</label>
										<form:input path="email" id="email" placeholder="Email... "
											style="width:100%;" />
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-username">Username</label>
										<form:input path="username" id="user-name"
											placeholder="Username..." style="width:100%;" />
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password">Password</label>
										<form:input type="password" path="password" id="password"
											placeholder="Password..." style="width:100%;" />
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password">Confirm
											Password</label>
										<form:input type="password" path="password"
											id="confirm_password" placeholder="Password..."
											style="width:100%;" />
										<p id="message"></p>
									</div>

									<div class="form-group"></div>
									<form:button type="submit" class="btn">Sign me up!</form:button>
									<!-- <button type="submit" class="btn">Sign me up!</button> -->
								</form:form>
							</div>
						</div>

					</div>


				</div>

			</div>
		</div>

	</div>

	<!-- Footer -->
	<footer>
		<div class="container">
			<div class="row">

				<div class="col-sm-8 col-sm-offset-2">
					<div class="footer-border"></div>
					<!-- <p>
						Made by Anli Zaimi at <a href="http://azmind.com" target="_blank"><strong>AZMIND</strong></a>
						having a lot of fun. <i class="fa fa-smile-o"></i>
					</p> -->
				</div>

			</div>
		</div>
	</footer>

	<!-- Javascript -->
	<script type="text/javascript"
		src="<spring:url value="/resources/js/scripts.js"></spring:url>"></script>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/jquery-1.11.1.min.js"></spring:url>"></script>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/bootstrap.min.js"></spring:url>"></script>



</body>
<script type="text/javascript">
	$('document').ready(function() {
		$('#confirm_password').keyup(function() {
			if ($('#password').val() == $('#confirm_password').val()) {
				$('#message').html('matching');
				$('#message').style('color', 'green');
			} else {
				$('#message').html('not matching');
				$('#message').style('color', 'red');
			}
		})

		function validateInput() {
			alert('as');
			if ($('#email').val() == '') {
				return false;
			} else if ($('#user-name').val() == '') {
				return false;
			} else if ($('#password').val() == '') {
				return false;
			} else if ($('#confirm-password').val() == '') {
				return false;
			}

			return true;
		}
	});
</script>


</html>