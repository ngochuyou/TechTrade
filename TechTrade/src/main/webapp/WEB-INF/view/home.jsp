<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"></script>
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
						<a href="#"
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
			<form class="form-inline m-3 my-lg-0 w-50 position-relative">
				<input class="form-control w-100 font-weight-bold" type="search"
					placeholder="Try something like Category's name or Post"
					aria-label="Search" id='search'>
				<div class="hidden w-100 my-dropdown-container"
					id="my-dropdown-container">
					<a class="dropdown-item text-main" href="#">Action</a> <a
						class="dropdown-item text-main" href="#">Another action</a> <a
						class="dropdown-item text-main" href="#">Something else here</a> <a
						class="dropdown-item text-main" href="#">Something else here</a> <a
						class="dropdown-item text-main" href="#">Something else here</a> <a
						class="dropdown-item text-main" href="#">Something else here</a> <a
						class="dropdown-item text-main" href="#">Something else here</a> <a
						class="dropdown-item text-main" href="#">Something else here</a>
				</div>
				<button
					class="btn btn-blank-main bg-light position-right border-left font-weight-bold"
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
					<li class="nav-item active">
						<button
							class="btn-blank hpx-70  my-0 my-sm-0 font-weight-bold wpx-100"
							type="submit">Sign in</button>
					</li>
					<li class="nav-item active">
						<button
							class="btn-blank hpx-70 my-2 my-sm-0 font-weight-bold wpx-100"
							type="submit">Sign up</button>
					</li>
				</ul>
			</div>
		</nav>
		<div style="background-image: url('../img/parallax.jpg');"
			class="parallax"></div>
		<div class="p-5">
			<h1 class="panel-header">Closest to you</h1>
			<c:forEach var="post" items="${postList }">
				<div class="post">
					<div class="row my-2">
						<div class="col-10">
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
								<span class="color-main tags">#iphoneX</span> <span
									class="color-main tags">#nicecamera</span>
							</div>
						</div>
						<div class="col-2">
							<img src="../img/parallax.jpg" class="avatar position-right">
						</div>
					</div>
					<div class="row">
						<div class="col custom-control-description text-size-post">${post.description }</div>
					</div>
					<div class="row my-5">
						<div id="carouselExampleControls" class="carousel slide px-3"
							data-ride="carousel">
							<ol class="carousel-indicators">
								<li data-target="#carouselExampleIndicators" data-slide-to="0"
									class="active"></li>
								<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
								<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
							</ol>
							<div class="carousel-inner">
								<div class="carousel-item active">
									<div class="row">
										<div class="col-4 pr-0 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
										<div class="col-4 px-1 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
										<div class="col-4 pl-0 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
									</div>
								</div>
								<div class="carousel-item">
									<div class="row">
										<div class="col-4 pr-0 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
										<div id="demo"></div>
										<div class="col-4 px-1 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
										<div class="col-4 pl-0 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
									</div>
								</div>
								<div class="carousel-item">
									<div class="row">
										<div class="col-4 pr-0 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
										<div class="col-4 px-1 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
										<div class="col-4 pl-0 hpx-350">
											<div class="post-img w-100">
												<img src="../img/parallax.jpg">
											</div>
										</div>
									</div>
								</div>
							</div>
							<a class="carousel-control-prev" href="#carouselExampleControls"
								role="button" data-slide="prev"> <span
								class="carousel-control-prev-icon" aria-hidden="true"></span> <span
								class="sr-only">Previous</span>
							</a> <a class="carousel-control-next" href="#carouselExampleControls"
								role="button" data-slide="next"> <span
								class="carousel-control-next-icon" aria-hidden="true"></span> <span
								class="sr-only">Next</span>
							</a>
						</div>
					</div>
					<div class="row post-footer">
						<div class="col-10 row pointer">
							<div
								class="col-6 float-left border text-center h-100 comments-btn">
								<h3 class="mt-3">
									<i class="fas fa-ellipsis-h mr-5"></i>Comments
								</h3>
							</div>
							<div class="col-6 float-left border text-center h-100">
								<h3 class="mt-3">
									<i class="fas fa-thumbtack mr-5"></i>Pin
								</h3>
							</div>
						</div>
						<div class="col-2">
							<p class="text-right mt-3">
								TechTrade <i class="fa fa-check"></i>
							</p>
						</div>
					</div>
					<div class="row comments-container">
						<div class="col">
							<div class="panel-header">Comments</div>
							<c:forEach var="comment" items="${commentList }">
								<c:if test="${comment.post.id eq post.id }">
									<div class="row px-5 my-3">
										<div class="col-1">
											<img src="../img/parallax.jpg" class="avatar-small">
										</div>
										<div class="col-11">
											<div class="comments">${comment.content }</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="overlay"></div>
	</div>
	<script type="text/javascript"
		src="<spring:url value="/resources/js/home.js"></spring:url>"></script>
</body>
</html>