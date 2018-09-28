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
	<c:set var="path" value="/TechTrade"></c:set>
	<div class="container-fluid p-3">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-8 main">
				<h1>Category Management</h1>
				<h2>Categories and Brands</h2>
				<table class="table table-bordered">
					<tr>
						<th>Category</th>
						<th>Brands</th>
					</tr>
					<c:if test="${not empty pageModel.cateList }">
						<c:forEach var="cate" items="${pageModel.cateList }">
							<tr>
								<td><div class="m-2">
										<span id="cate-name${cate.id }">${cate.name }</span>
										<div class="dropdown">
											<button
												class="btn btn-outline-light dropdown-toggle text-secondary"
												type="button" id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"></button>
											<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
												<form:form modelAttribute="cateModel"
													action="${path }/category/delete" method="post">
													<form:hidden path="id" value="${cate.id }" />
													<form:button class="dropdown-item">Delete</form:button>
												</form:form>
												<button class="dropdown-item cate-updateBtn" type="button"
													id="cate-id${cate.id }">Update</button>
											</div>
										</div>
									</div></td>
								<td><c:forEach var="brand" items="${pageModel.brandList }">
										<c:if test="${brand.category.id eq cate.id}">
											<div
												class="m-2 ${brand.inUse eq false ? 'text-light bg-secondary' : ''}">
												<span id="brand-id${brand.id }">${brand.name }</span>
												<div class="dropdown">
													<button
														class="btn btn-outline-light dropdown-toggle text-secondary"
														type="button" id="dropdownMenu2" data-toggle="dropdown"
														aria-haspopup="true" aria-expanded="false"></button>
													<c:if test="${brand.inUse eq true}">
														<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
															<form:form modelAttribute="brandModel"
																action="${path }/category/brand/delete" method="post">
																<form:hidden path="id" value="${brand.id }" />
																<form:button class="dropdown-item">Delete</form:button>
															</form:form>
															<button class="dropdown-item brand-updateBtn"
																type="button" id="brand-${brand.id }-cate-${cate.id }">Update</button>
														</div>
													</c:if>
													<c:if test="${brand.inUse eq false }">
														<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
															<button class="dropdown-item"
																type="button"
																onclick="window.location.href='<spring:url value="/category/brand/restore?id=${brand.id }"></spring:url>'">Restore</button>
														</div>
													</c:if>
												</div>
											</div>
										</c:if>
									</c:forEach>
									<button class="btn btn-outline-light brand-form-openBtn m-2"
										id="brand-cate${cate.id }">New brand</button></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty pageModel.cateList }">
						<p>No Category found</p>
					</c:if>
					<c:if test="${not empty pageModel.unUsedCateList }">
						<c:forEach var="uCate" items="${pageModel.unUsedCateList }">
							<tr class="text-light bg-secondary">
								<td><div class="m-2">
										<span>${uCate.name }</span>
										<div class="dropdown">
											<button
												class="btn btn-outline-light dropdown-toggle text-secondary"
												type="button" id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"></button>
											<div class="dropdown-menu" aria-labelledby="dropdownMenu2">
												<button class="dropdown-item" type="button"
													onclick="window.location.href='<spring:url value="/category/restore?id=${uCate.id }"></spring:url>'">Restore</button>
											</div>
										</div>
									</div></td>
								<td></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty pageModel.unUsedCateList }">
						<p>No Unused Category found</p>
					</c:if>
				</table>
			</div>
			<div class="col-2">
				<ul class="list-group">
					<li class="list-group-item">
						<div id="cate-form-openBtn">New Category</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="hidden-absolute-container p-3" id="cate-form-container">
		<form:form modelAttribute="cateModel"
			action="${path }/category/create" method="post" id="cate-form">
			<form:hidden path="id" class="form-control" id="cate-form-id" />
			<div class="form-group">
				<label>Category Name</label>
				<form:input path="name" class="form-control" id="cate-form-name" />
			</div>
			<form:button class="btn btn-primary">Submit</form:button>
			<div class="btn form-closeBtn">Cancel</div>
		</form:form>
		<div class="text-danger" id="cate-form-message"></div>
	</div>
	<div class="hidden-absolute-container p-3" id="brand-form-container">
		<form:form modelAttribute="brandModel"
			action="${path }/category/brand/create" method="post" id="brand-form">
			<form:hidden path="id" class="form-control" id="brand-form-id" />
			<form:hidden path="categoryId" class="form-control"
				id="brand-form-categoryId" />
			<div class="form-group">
				<label>Brand Name</label>
				<form:input path="name" class="form-control" id="brand-form-name" />
			</div>
			<div class="form-group">
				<label>Category Name</label> <input class="form-control"
					id="brand-form-categoryName" readonly="readonly" />
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