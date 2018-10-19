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
	src="<spring:url value="/resources/js/wall.js"></spring:url>"></script>
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
				<div class="list-group border-bottom"></div>
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
				id="search-form" action="<spring:url value='/search'></spring:url>"
				method="get">
				<input class="form-control w-100 font-weight-bold" type="search"
					placeholder="Try something like Category's name or Post"
					aria-label="Search" id='search' autocomplete="off" name="k">
				<div class="hidden w-100 my-dropdown-container"
					id="my-dropdown-container"></div>
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
					<sec:authorize access="isAuthenticated()">
						<li>
							<div class="dropdown">
								<button class="btn-nobg dropdown-toggle text-light"
									type="button" id="dropdownMenu2" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">
									<img
										src="<spring:url value="/account/avatar?username=${account.username }"></spring:url>"
										class="mr-5 avatar-small">
								</button>
								<div
									class="dropdown-menu dropdown-menu-right pointer wpx-200 custom-dropdown"
									aria-labelledby="dropdownMenu2">
									<div class="dropdown-item border-bottom font-weight-bold">
										<div class="row">
											<div class="col-5 pr-0">
												<img
													src="<spring:url value="/account/avatar?username=${account.username }"></spring:url>"
													class="mr-5 avatar-small">
											</div>
											<div class="col-7 px-0">
												<p class="text-main text-truncate">${account.username }</p>
											</div>
										</div>
									</div>
									<div
										class="dropdown-item border-bottom text-main font-weight-bold">
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
											</button>
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
					<sec:authorize access="isAnonymous()">
						<li class="nav-item active"><a
							href="<spring:url value="/login"></spring:url>"><button
									class="btn-blank hpx-70  my-0 my-sm-0 font-weight-bold wpx-100"
									type="submit" id="signin">Sign in</button></a></li>
						<li class="nav-item active"><a
							href="<spring:url value="/account/sign-up"></spring:url>"><button
									class="btn-blank hpx-70 my-2 my-sm-0 font-weight-bold wpx-100"
									type="submit" id="signup">Sign up</button></a></li>
					</sec:authorize>
				</ul>
			</div>
		</nav>
		<div
			style="background-image: url('<spring:url value="/resources/img/parallax.jpg"></spring:url>');"
			class="parallax position-relative">
			<div class="wallpaper-cover">
				<p class="background-opacity m-0">
					<img
						src="<spring:url value="/account/avatar?username=${account.username }"></spring:url>"
						class="avatar-large">
				</p>
			</div>
		</div>
		<div class="main mx-0 postion-relative">
			<span id="username" class="hidden">${account.username }</span>
			<div>
				<h1 class="text-center">${account.username }</h1>
			</div>
			<div class="row mx-1 p-5 bg-noti" id="info">
				<div class="col-6">
					<h4 class="text-left my-4">
						<i class="fas fa-phone mr-4"></i>${account.phone }
					</h4>
					<h4 class="text-left my-4">
						<i class="fas fa-map-marker-alt mr-4"></i> ${account.ward.name },
						${account.ward.district.name }, ${account.ward.district.city.name }
					</h4>
				</div>
				<div class="col-6">
					<h4 class="text-left my-4">
						<i class="fas fa-at mr-4"></i>${account.email }
					</h4>
					<h4 class="text-left my-4">
						<i class="fas fa-male mr-4"></i>${account.gender }
					</h4>
					<h4 class="text-left my-4">
						<i class="fas fa-arrows-alt-v mr-4"></i>${account.prestigePoints }
					</h4>
				</div>
			</div>
			<div class="row bg-noti border-bottom" style="margin-right: .03rem;">
				<div
					class="col-2 pt-4 pb-3 border-right box-shadow-hover pointer boxshadow-hover"
					id="profile-posts">
					<h3 class="text-main text-center font-weight-bold">Posts</h3>
				</div>
				<div
					class="col-2 pt-4 pb-3 border-right box-shadow-hover pointer boxshadow-hover"
					id="profile-info">
					<h3 class="text-main text-center font-weight-bold">Informations</h3>
				</div>
				<div
					class="col-2 pt-4 pb-3 border-right box-shadow-hover pointer boxshadow-hover"
					id="profile-messages">
					<h3 class="text-main text-center font-weight-bold">Message</h3>
				</div>
			</div>
			<div class="m-4">
				<div class="row">
					<div class="dropdown">
						<button class="btn-nobg text-main wpx-70 hpx-70" type="button"
							id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false">
							<i class="fas fa-sort fa-lg"></i>
						</button>
						<div class="dropdown-menu dropdown-menu-left bg-main"
							aria-labelledby="dropdownMenu2" style="z-index: 1022;">
							<a class="dropdown-item text-white"
								href="<spring:url value="/account/${account.username }?s=createAt:desc"></spring:url>">Newest</a>
							<a class="dropdown-item text-white"
								href="<spring:url value="/account/${account.username }?s=createAt:asc"></spring:url>">Oldest</a>
							<a class="dropdown-item text-white"
								href="<spring:url value="/account/${account.username }?s=upVote:desc"></spring:url>">Most
								voted</a> <a class="dropdown-item text-white"
								href="<spring:url value="/account/${account.username }?s=upVote:asc"></spring:url>">Least
								Voted</a>
						</div>
					</div>
				</div>
				<div id="post-content" class="bg-noti p-3">
					<span id="sort" class="hidden">${sortBy }</span>
					<p class="panel-header">Posts</p>
					<c:forEach var="post" items="${postList }">
						<div class="py-2 my-2 bg-white border-curve">
							<c:if test="${post.status eq false }">
								<h3 class="text-main">
									<span class="tags text-light bg-main">Closed</span>
								</h3>
							</c:if>
							<div class="row my-4 px-4">
								<div class="col-1 py-3">
									<div class="col-1 m-auto text-center"
										id="vote-holder-${post.id }">
										<c:if test="${not empty post.vote }">
											<c:if test="${post.vote.type eq true }">
												<p class="w-100">You voted this post +1</p>
											</c:if>
											<c:if test="${post.vote.type ne true }">
												<p class="w-100">You voted this post -1</p>
											</c:if>
										</c:if>
										<c:if test="${empty post.vote }">
											<div class="w-100 pointer upvote" id="upvote-${post.id }">
												<i class="fas fa-angle-up fa-3x"></i>
											</div>
											<div class="w-100 pointer downvote" id="downvote-${post.id }">
												<i class="fas fa-angle-down fa-3x"></i>
											</div>
										</c:if>
									</div>
								</div>
								<div class="col-10">
									<h2 class="text-truncate font-weight-bold">${post.name }</h2>
									<p>
										On
										<fmt:formatDate value="${post.createAt }" />
									</p>
									<div class="line-height-large">
										<span>Tags <i class="fas fa-hashtag"></i>
										</span> <span class="tags d-inline-block"
											style="background-color: ${post.category.tagColor}">${post.category.name}</span>
										<span class="color-main tags d-inline-block">
											${fn:replace(post.tags, ",", "</span> <span class='color-main tags d-inline-block'>")}
										</span>
									</div>
								</div>
							</div>
							<div class="row pointer my-2  px-4"
								onclick="window.location.href='<spring:url value='/post/${post.id }'></spring:url>'">
								<p class="col custom-control-description text-size-post">${post.description }</p>
							</div>
							<div class="row px-4">
								<div class="col-6 pointer border half-left-curve">
									<h3 class="mt-3 text-center">
										<i class="fas fa-arrows-alt-v mr-5"></i>${post.upVote } Votes
									</h3>
								</div>
								<div class="col-6 pointer border half-right-curve">
									<h3 class="mt-3 text-center">
										<i class="fas fa-thumbtack mr-5"></i>Pin
									</h3>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="text-center hidden" id="loader">
			<img
				src="<spring:url value='/resources/img/loading.gif'></spring:url>"
				class="avatar-large border">
		</div>
		<sec:authorize access="isAuthenticated()">
			<div class="fixed-container half-top-curve box-shadow">
				<div class="inbox-header">
					<div class="row mx-1 p-1">
						<div class="col-4 pt-1">
							<p class="m-0 text-light">
								<i class="fas fa-envelope fa-2x"></i>
							</p>
						</div>
						<div class="col-8 pt-2">
							<h3 class="m-0 text-light text-right">
								Inbox<span class="ml-4 badge badge-light d-inline-block">${inbox.unreadQty }</span>
							</h3>
						</div>
					</div>
				</div>
				<div class="inbox-content">
					<figure id="inbox">
						<div class="h-100">
							<c:forEach var="mess" items="${inbox.unreadMessages }">
								<div class="row h-25 mx-2 py-2 bg-noti pointer message">
									<div class="col-2 pl-4">
										<img
											src="<spring:url value='/account/avatar?username=${mess.sender.username }'></spring:url>"
											class="avatar-medium m-auto">
									</div>
									<div class="col-9">
										<h5 class="font-weight-bold">${mess.sender.username }</h5>
										<p class="text-truncate my-1">${mess.content }</p>
										<p>
											<fmt:formatDate value="${mess.sentAt }" type="both"></fmt:formatDate>
										</p>
									</div>
									<div class="col-1"></div>
								</div>
							</c:forEach>
							<c:forEach var="mess" items="${inbox.readMessages }">
								<div class="row h-25 mx-2 py-2 bg-white pointer message">
									<div class="col-2 pl-4">
										<img
											src="<spring:url value='/account/avatar?username=${mess.sender.username }'></spring:url>"
											class="avatar-medium m-auto">
									</div>
									<div class="col-9">
										<h5 class="font-weight-bold">${mess.sender.username }</h5>
										<p class="text-truncate my-1">${mess.content }</p>
										<p>
											<fmt:formatDate value="${mess.sentAt }" type="both"></fmt:formatDate>
										</p>
									</div>
									<div class="col-1"></div>
								</div>
							</c:forEach>
						</div>
						<div class="h-100">
							<div class="row h-25 mx-2 py-2 border-bottom">
								<div class="col-2">
									<img src="" class="avatar-medium m-auto" id="sender-ava">
								</div>
								<div class="col-8">
									<h5 class="mb-2" id="sender-username"></h5>
									<p class="mb-2 text-small" id="sent-at"></p>
								</div>
								<div class="col-2">
									<div class="icon-small m-auto">
										<i class="fas fa-trash text-right"></i>
									</div>
									<div class="icon-small m-auto">
										<i class="fas fa-reply text-right"></i>
									</div>
								</div>
							</div>
							<div class="row h-50 mx-2 py-2 flow-auto">
								<p class="custom-control-description" id="message-content"></p>
							</div>
							<div class="row h-25 mx-2 py-2">
								<input class="form-control">
							</div>
						</div>
					</figure>
				</div>
			</div>
		</sec:authorize>
		<div class="overlay"></div>
	</div>
</html>