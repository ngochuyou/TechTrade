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
	href="<spring:url value="/resources/css/style.css"></spring:url>"
	type="text/css">
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
	<c:set var="path" value="/TechTrade"></c:set>
	<div class="container-fluid p-3">
		<div class="row">
			<div class="col-2 border-right"></div>
			<div class="col-7 main">
				<h1 class="mt-2 mb-4">Category Management</h1>
				<c:if test="${empty pageModel.cateList }">
					<p>No Category found</p>
				</c:if>
				<c:if test="${not empty pageModel.cateList }">
					<h2>Category list</h2>
					<div>
						<table class="table table-bordered">
							<tr>
								<th class="text-title">ID</th>
								<th class="text-title">Name</th>
							</tr>
							<c:forEach var="cate" items="${pageModel.cateList }">
								<tr>
									<td><span>${cate.id }</span></td>
									<td><span id="cate-name${cate.id }">${cate.name }</span>
										<div class="dropdown">
											<button class="btn btn-secondary dropdown-toggle"
												type="button" id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"></button>
											<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
												<button class="dropdown-item" type="button"
													onclick="window.location.href='<spring:url value="/category/delete?id=${cate.id }"></spring:url>'">Mark as unused</button>
												<button class="dropdown-item cate-updateBtn"
													id="cate-${cate.id }" type="button">Update</button>
											</div>
										</div></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</c:if>
				<h2>Marked as unused</h2>
				<table class="table table-bordered">
					<tr>
						<th class="text-title">ID</th>
						<th class="text-title">Name</th>
					</tr>
					<c:forEach var="uCate" items="${pageModel.unUsedCateList }">
						<tr class="bg-secondary">
							<td><span>${uCate.id }</span></td>
							<td><span>${uCate.name }</span>
								<div class="dropdown">
									<button class="btn btn-secondary dropdown-toggle" type="button"
										id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false"></button>
									<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
										<button class="dropdown-item" type="button"
											onclick="window.location.href='<spring:url value="/category/restore?id=${uCate.id }"></spring:url>'">Restore</button>
									</div>
								</div></td>
					</c:forEach>
				</table>
			</div>
			<div class="col-3 border-left">
				<ul class="list-group text-center">
					<li class="list-group-item list-group-item-action"
						id="cate-form-openBtn"><span class="text-medium">New
							Category</span></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="hidden-center-container p-3" id="cate-form-container">
		<h2 class="border-bottom">Category Form</h2>
		<form:form modelAttribute="cateModel" class="my-4 mx-2"
			action="${path }/category/create" method="post" id="cate-form">
			<form:hidden path="id" class="form-control" id="cate-form-id" />
			<div class="form-group">
				<label class="mb-3 font-weight-bold">Category Name</label>
				<form:input path="name" class="form-control mb-3"
					id="cate-form-name" />
			</div>
			<form:button class="btn btn-primary">Submit</form:button>
			<div class="btn form-closeBtn">Cancel</div>
		</form:form>
		<div class="text-danger" id="cate-form-message"></div>
	</div>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/category.js"></spring:url>"></script>
</body>
</html>