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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/category.css"></spring:url>"
	type="text/css">
<script src="https://code.jquery.com/jquery-3.3.1.js"
	integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60="
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
</head>
<body>
	<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
	<div class="container-fluid p-3">
		<div class="row">
			<div class="col-3"></div>
			<div class="col-6">
				<h1>Category Management</h1>
				<c:if test="${empty cateModel.cateList }">
					<p>No Category found</p>
				</c:if>
				<c:if test="${not empty cateModel.cateList }">
					<table class="table table-bordered">
						<tr>
							<th>Category</th>
							<th>Brands</th>
						</tr>
						<c:forEach var="cate" items="${cateModel.cateList }">
							<tr id="${cate.id }">
								<td>${cate.name }
									<span class="dropdown">
										<button class="btn btn-secondary dropdown-toggle"
											type="button" id="dropdownMenu2" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"></button>
										<span class="dropdown-menu" aria-labelledby="dropdownMenu2">
											<button class="dropdown-item" type="button">Delete</button>
											<button class="dropdown-item" type="button">Update</button>
										</span>
									</span>
								</td>
								<td><c:forEach var="brand" items="${cateModel.brandList }">
										<c:if test="${brand.category.id eq cate.id}">
											<p>${brand.name }</p>
										</c:if>
									</c:forEach></td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<div class="col-3">
				<ul class="list-group">
					<li class="list-group-item">
						<div id="cate-form-openBtn">New Category</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="hidden-absolute-container p-3" id="cate-form">
		<form:form modelAttribute="cateDetail"
			action="${path }/category/create" method="post">
			<div class="form-group">
				<label>Category Id</label>
				<form:input path="id" class="form-control" />
			</div>
			<div class="form-group">
				<label>Category Name</label>
				<form:input path="name" class="form-control" />
			</div>
			<form:button class="btn btn-primary">Create</form:button>
			<div class="btn" id="cate-form-closeBtn">Cancel</div>
		</form:form>
	</div>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/category.js"></spring:url>"></script>
</body>
</html>