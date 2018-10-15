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
<meta charset="utf-8">
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
	src="<spring:url value="/resources/js/viewPost.js"></spring:url>"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="wrapper">
		<nav id="sidebar" class="sidebar-active">
			<div class="sidebar-header p-3 border-bottom">
				<h2 class="text-light">TechTrade</h2>
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
				id="search-form"
				action="<spring:url value="/search?category=${page.categoryId }"></spring:url>"
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
										src="<spring:url value="/account/avatar/?username=${user.username }"></spring:url>"
										class="mr-5 avatar-small">
								</button>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="dropdownMenu2">
									<a class="dropdown-item font-weight-bold"
										href="<spring:url value="/logout"></spring:url>">Logout</a>
								</div>
							</div>
						</li>
					</sec:authorize>
				</ul>
			</div>
		</nav>
		<div class="px-5">
			<form action="<spring:url value="/search"></spring:url>">
				<div class="row py-2">
					<div class="col-8">
						<input class="form-control font-weight-bold" name="k"
							placeholder="Search">
					</div>
					<div class="col-3">
						<select name="category" class="form-control">
							<c:forEach var="cate" items="${cateList }">
								<option value="${cate.id }">${cate.name }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-1">	
						<button type="submit" class="btn bg-main">Search</button>
					</div>
				</div>
			</form>
			<div class="row py-2 panel-header">
				<div class="col-8" id="pageNumber">Page ${page.pageNumber + 1}
				</div>
				<div class="col-4 text-right">
					<div class="dropdown">
						<button class="btn-nobg text-main wpx-70 hpx-70" type="button"
							id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false">
							<i class="fas fa-sort fa-lg"></i>
						</button>
						<div class="dropdown-menu dropdown-menu-right bg-main"
							aria-labelledby="dropdownMenu2">
							<a class="dropdown-item text-white"
								href="<spring:url value="/search?${page.toParameters() }&s=createAt:desc"></spring:url>">Newest</a>
							<a class="dropdown-item text-white"
								href="<spring:url value="/search?${page.toParameters() }&s=createAt:asc"></spring:url>">Oldest</a>
							<a class="dropdown-item text-white"
								href="<spring:url value="/search?${page.toParameters() }&s=upVote:desc"></spring:url>">Most
								voted</a> <a class="dropdown-item text-white"
								href="<spring:url value="/search?${page.toParameters() }&s=upVote:asc"></spring:url>">Least
								Voted</a> <a class="dropdown-item text-white"
								href="<spring:url value="/logout"></spring:url>">Closest to
								you</a>
						</div>
					</div>
				</div>
			</div>
			<c:forEach var="post" items="${postList }">
				<div class="post">
					<div class="row my-2">
						<div class="col-10">
							<h3>
								<i class="fas fa-map-marker mr-2"></i>${post.createBy.ward.name },
								${post.createBy.ward.district.name },
								${post.createBy.ward.district.city.name }
							</h3>
							<h2 class="text-truncate font-weight-bold">${post.name }</h2>
							<p>
								By <span class="font-italic text-main">${post.createBy.username }</span>
								on
								<fmt:formatDate value="${post.createAt }" />
							</p>
							<div>
								<span>Tags <i class="fas fa-hashtag"></i>
								</span> <span class="tags"
									style="background-color : ${post.category.tagColor}">${post.category.name }</span>
								<span class="color-main tags"> ${fn:replace(post.tags, ",", "</span> <span class='color-main tags'>")}
								</span>
							</div>
						</div>
						<div class="col-2">
							<img src="/TechTrade/account/avatar/${post.createBy.avatar }"
								class="avatar position-right mx-3">
						</div>
					</div>
					<div class="row">
						<div class="col custom-control-description text-size-post">${post.description }</div>
					</div>
					<div class="row post-footer">
						<div class="col">
							<div class="col-6 float-left border text-center h-100">
								<h3 class="mt-3">
									<i class="fas fa-arrows-alt-v mr-5"></i>${post.upVote } Votes
								</h3>
							</div>
							<div class="col-6 float-left border text-center h-100 pointer">
								<h3 class="mt-3">
									<i class="fas fa-thumbtack mr-5"></i>Pin
								</h3>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="overlay"></div>
	</div>
</body>
</html>