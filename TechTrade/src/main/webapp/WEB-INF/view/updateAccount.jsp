<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<spring:url value="/resources/css/style.css"></spring:url>"
	type="text/css">
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"></script>
<script type="text/javascript"
	src="<spring:url value="/resources/js/updateAccount.js"></spring:url>"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="wrapper">
		<nav id="sidebar" class="sidebar-active">
			<div class="sidebar-header p-3 border-bottom">
				<h2 class="text-light pointer"
					onclick="window.location.href='<spring:url value="/"></spring:url>'">TechTrade</h2>
			</div>
			<div class="sidebar-body hpx-500">
				<div class="list-group border-bottom">
					<c:forEach var="cate" items="${cateList }">
						<a
							href="<spring:url value="/search?category=${cate.id }"></spring:url>"
							class="list-group-item list-group-item-action flex-column align-items-start py-3 noborder thumb-medium">
							<div class="row">
								<div class="col-4 h-100">
									<h1>
										<i class="${cate.icon }"></i>
									</h1>
								</div>
								<div class="col-8 my-auto border-left">
									<span>${cate.name }<span
										class="badge badge-pill bg-light text-main position-right">1</span></span>
								</div>
							</div>
						</a>
					</c:forEach>
				</div>
			</div>
			<div class="row sidebar-footer pr-3">
				<div class="col-4 text-center">
					<a href="#"><h1>
							<i class="fas fa-info"></i>
						</h1></a>
				</div>
				<div class="col-4 text-center">
					<a href="#"><h1>
							<i class="fas fa-file-alt"></i>
						</h1></a>
				</div>
				<div class="col-4 text-center">
					<a href="#"><h1>
							<i class="fas fa-flag"></i>
						</h1></a>
				</div>
			</div>
		</nav>
	</div>
	<div id="content" class="p-0 container-fluid">
		<nav class="navbar navbar-expand-lg color-main p-0 sticky-top">
			<button type="button" id="sidebarCollapse" class="btn color-main m-3">
				<i class="fas fa-list fa-lg text-light"></i>
			</button>
			<form class="form-inline m-3 my-lg-0 w-50 position-relative"
				id="search-form" action="<spring:url value="/search"></spring:url>"
				method="get">
				<input class="form-control w-100 font-weight-bold" type="search"
					placeholder="Try something like Category's name or Post"
					aria-label="Search" id='search' autocomplete="off" name="k">
				<div class="hidden w-100 my-dropdown-container"
					id="my-dropdown-container">
					<a class="dropdown-item text-main" href="#">Action</a>
				</div>
				<button
					class="btn bg-white position-right border-left font-weight-bold"
					type="submit">
					<i class="fas fa-search"></i>
				</button>
			</form>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarTogglerDemo03"
				aria-controls="navbarTogglerDemo03" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon text-light"><i
					class="fas fa-align-justify"></i></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarTogglerDemo03">
				<ul class="navbar-nav mt-lg-0 position-right">
					<sec:authorize access="isAnonymous()">
						<li class="nav-item active"><a
							href="<spring:url value="/login"></spring:url>"><button
									class="btn-blank hpx-70 my-0 my-sm-0 font-weight-bold wpx-100"
									type="submit">Sign in</button></a></li>
						<li class="nav-item active"><a
							href="<spring:url value="/account/sign-up"></spring:url>"><button
									class="btn-blank hpx-70 my-2 my-sm-0 font-weight-bold wpx-100"
									type="submit">Sign up</button></a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="principal" var="user" />
						<li>
							<div class="dropdown">
								<button class="btn-nobg dropdown-toggle text-light"
									type="button" id="dropdownMenu2" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">
									<img
										src="<spring:url value="/account/avatar?username=${user.username }"></spring:url>"
										class="mr-5 avatar-small">
								</button>
								<div
									class="dropdown-menu dropdown-menu-right pointer wpx-200 custom-dropdown"
									aria-labelledby="dropdownMenu2">
									<div class="dropdown-item border-bottom font-weight-bold">
										<div class="row"
											onclick="window.location.href='<spring:url value='/account/wall/${user.username }'></spring:url>'">
											<div class="col-5 pr-0">
												<img
													src="<spring:url value="/account/avatar?username=${user.username }"></spring:url>"
													class="mr-5 avatar-small">
											</div>
											<div class="col-7 px-0">
												<p class="text-main text-truncate">${user.username }</p>
											</div>
										</div>
									</div>
									<div
										class="dropdown-item border-bottom text-main font-weight-bold"
										onclick="window.location.href='<spring:url value='/account/wall/${user.username }'></spring:url>'">
										<div>
											<i class="fas fa-home mr-4"></i>
										</div>
										<div>
											<span class="text-right">Home</span>
										</div>
									</div>
									<div
										class="dropdown-item text-main font-weight-bold border-bottom">
										<div>
											<i class="fas fa-envelope"></i>
										</div>
										<div>
											<span>Inboxs</span><span class="badge bg-main ml-3">4</span>
										</div>
									</div>
									<div class="dropdown-item text-main font-weight-bold">
										<div>
											<i class="fas fa-sign-out-alt mr-4"></i>
										</div>
										<div>
											<a class="font-weight-bold text-right" href="">Logout</a>
										</div>
									</div>
								</div>
							</div>
						</li>
					</sec:authorize>
				</ul>
			</div>
		</nav>
		<form:form modelAttribute="account" method="post"
			enctype="multipart/form-data" id="form">
			<div class="position-relative wallpaper">
				<img
					src="<spring:url value="/account/avatar/${account.wallpaper }"></spring:url>"
					class="w-100 wallpaperPreview"> <label for="wallpaperFile"
					class="w-100 h-100 position-absolute mx-0" style="top: 0;"></label>
				<form:input type="file" path="wallpaperFile" class="uploadFile"
					id="wallpaperFile" />
			</div>
			<div>
				<p class="background-opacity mx-2 my-3 position-relative"">
					<label for="avatarFile" class="h-100 w-100 position-absolute"
						style="top: 0; left: 0"></label>
					<form:input type="file" path="avatarFile" class="uploadFile"
						id="avatarFile" />
					<img
						src="<spring:url value="/account/avatar?username=${account.username }"></spring:url>"
						class="avatar-large avatarPreview">
				</p>
			</div>
			<div class="mx-0 postion-relative">
				<span id="username" class="hidden">${account.username }</span>
				<div>
					<h1 class="text-center">${account.username }</h1>
				</div>
			</div>
			<div class="mx-0 postion-relative">
				<div class="row mx-1 p-5 bg-noti" id="info">
					<div class="col-1"></div>
					<div class="col-10">
						<div class="row my-3">
							<div class="col-1">
								<i class="fas fa-at fa-lg"></i>
							</div>
							<div class="col-11">
								<h4>${account.email }</h4>
							</div>
						</div>
						<div class="row my-3">
							<div class="col-1">
								<i class="fas fa-male fa-lg"></i>
							</div>
							<div class="col-11">
								<h4>
									<form:select id="gender" class="w-100 form-control hpx-50"
										style="width: auto; font-size: 15px;" path="gender">
										<form:option value="Male">Male</form:option>
										<form:option value="Female">Female</form:option>
										<form:option value="Other">Other</form:option>
									</form:select>
								</h4>
							</div>
						</div>
						<div class="row my-3">
							<div class="col-1">
								<i class="fas fa-key fa-lg"></i>
							</div>
							<div class="col-11">
								<h4 class="text-left">
									<form:input type="password" id="password"
										placeholder="Old Password" path="password"
										class="form-control" />
								</h4>
								<p class="hidden text-danger" id="oldpassword-notmatch">Incorrect
									old password</p>
							</div>
						</div>
						<div class="row my-3">
							<div class="col-1">
								<i class="fas fa-key fa-lg"></i>
							</div>
							<div class="col-11">
								<h4 class="text-left">
									<form:input type="password" id="newPassword"
										placeholder="New Password" path="newPassword"
										class="form-control" />
								</h4>
								<p class="hidden invalid text-danger">Invalid password.</p>
							</div>
						</div>
						<div class="row my-3">
							<div class="col-1">
								<i class="fas fa-key fa-lg"></i>
							</div>
							<div class="col-11">
								<h4 class="text-left">
									<input type="password" id="repassword"
										placeholder="Confirm New Password" class="form-control" />
								</h4>
								<p class="hidden invalid text-danger">Invalid password.</p>
								<p class="hidden not-match text-danger">Incorrect
									Re-password.</p>
							</div>
						</div>
						<div class="row my-3">
							<div class="col-1">
								<i class="fas fa-phone fa-lg"></i>
							</div>
							<div class="col-11">
								<h4 class="text-left">
									<form:input id="phone" placeholder="Phone Number"
										class="w-100 d-inline-block w-75 form-control" path="phone" />
								</h4>
								<p class="hidden text-danger" id="invalid-phone">Invalid
									phone number.</p>
							</div>
						</div>
						<c:if test="${account.updatableLocation eq true }">
							<div class="row my-3">
								<div class="col-1">
									<i class="fas fa-map-marker-alt fa-lg"></i>
								</div>
								<div class="col-11">
									<h4 class="text-left">
										<select id="city-select" class="custom-select hpx-50"
											style="width: auto; font-size: 15px;">
											<c:forEach var="city" items="${cityList }">
												<option value="${city.id }"
													${city.id eq account.ward.district.city.id ? "selected" : ""}>${city.name }</option>
											</c:forEach>
										</select> <select id="district-select" class="custom-select hpx-50"
											style="width: auto; font-size: 15px;">
											<c:forEach var="district" items="${districtList }">
												<option value="${district.id }"
													${district.id eq account.ward.district.id ? "selected" : "" }>${district.name }</option>
											</c:forEach>
										</select>
										<form:select id="ward-select" class="custom-select hpx-50"
											style="width: auto; font-size: 15px;" path="wardId">
											<form:options items="${wardList }" itemValue="id"
												itemLabel="name"></form:options>
										</form:select>
									</h4>
								</div>
							</div>
						</c:if>
						<c:if test="${account.updatableLocation eq false }">
							<p>You cannot update your location since it has been less
								than 30 days since the last time you did.</p>
						</c:if>
						<form:hidden path="email" />
						<form:hidden path="username" />
						<form:hidden path="role" />
						<div class="row">
							<div class="col">
								<div class="btn btn-main" id="form-submit">Save</div>
							</div>
						</div>
					</div>
					<div class="col-1"></div>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>