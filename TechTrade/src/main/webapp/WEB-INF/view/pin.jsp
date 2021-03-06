<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
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
<title>Pinned</title>
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
	src="<spring:url value="/resources/js/pin.js"></spring:url>"></script>
<sec:authorize access="isAuthenticated()">
	<script type="text/javascript"
		src="<spring:url value="/resources/js/mailbox.js"></spring:url>"></script>
</sec:authorize>
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
									<span>${cate.name }</span>
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
										onclick="window.location.href='<spring:url value='/'></spring:url>'">
										<div>
											<i class="fas fa-home mr-4"></i>
										</div>
										<div>
											<span class="text-right">Home</span>
										</div>
									</div>
									<div
										class="dropdown-item text-main font-weight-bold border-bottom inbox-open">
										<div>
											<i class="fas fa-envelope"></i>
										</div>
										<div>
											<span>Inboxs</span><span
												class="badge bg-main ml-3 unread-qty">${inbox.unreadQty }</span>
										</div>
									</div>
									<div
										class="dropdown-item text-main font-weight-bold border-bottom"
										onclick="window.location.href='<spring:url value='/post/upload'></spring:url>'">
										<div>
											<i class="fas fa-plus"></i>
										</div>
										<div>
											<span>Upload</span>
										</div>
									</div>
									<div class="dropdown-item text-main font-weight-bold"
										onclick="window.location.href='<spring:url value='/logout'></spring:url>'">
										<div>
											<i class="fas fa-sign-out-alt mr-4"></i>
										</div>
										<div>
											<span class="font-weight-bold text-right">Logout</span>
										</div>
									</div>
									<c:if test="${account.role eq 'Admin' }">
										<div class="dropdown-item text-main font-weight-bold"
											onclick="window.location.href='<spring:url value='/admin/report/view'></spring:url>'">
											<div>
												<i class="fas fa-flag mr-4"></i>
											</div>
											<div>
												<span class="font-weight-bold text-right">Reports</span>
											</div>
										</div>
									</c:if>
								</div>
							</div>
						</li>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
						<li class="nav-item active"><a
							href="<spring:url value="/login"></spring:url>"><button
									class="btn-blank hpx-70  my-0 my-sm-0 font-weight-bold wpx-100"
									id="signin">Sign in</button></a></li>
						<li class="nav-item active"><a
							href="<spring:url value="/account/sign-up"></spring:url>"><button
									class="btn-blank hpx-70 my-2 my-sm-0 font-weight-bold wpx-100"
									id="signup">Sign up</button></a></li>
					</sec:authorize>
				</ul>
			</div>
		</nav>
		<div
			style="background-image: url('<spring:url value="/account/avatar/${account.wallpaper }"></spring:url>');"
			class="parallax position-relative">
			<div class="wallpaper-cover">
				<p class="background-opacity m-0">
					<img
						src="<spring:url value="/account/avatar?username=${user.username }"></spring:url>"
						class="avatar-large box-shadow">
				</p>
			</div>
		</div>
		<div class="main p-3">
			<c:if test="${empty pinnedList }">
				<div class="hpx-600">
					<h2 class="text-center text-main">
						<i class="fas fa-clipboard-list mr-3"></i>You haven't pinned any
						post.
					</h2>
				</div>
			</c:if>
			<c:if test="${not empty pinnedList }">
				<h2 class="text-main">
					<i class="fas fa-clipboard-list mr-3"></i>Pinned Posts
				</h2>
			</c:if>
			<c:forEach var="pin" items="${pinnedList }">
				<div id="${pin.post.id }" class="px-2 my-3 post">
					<p class="panel-header">
						Pinned On
						<fmt:formatDate value="${pin.createAt }" type="date"></fmt:formatDate>
					</p>
					<div class="row my-0 mx-2">
						<div class="col-8">
							<h3 class="text-truncate text-main pointer">${pin.post.name }</h3>
							<p class="text-truncate">${pin.post.description }</p>
							<div class="row">
								<div class="col-4 border text-center pointer unpin"
									id="unpin-${pin.post.id }">
									<p class="my-2" style="color: #007bff;">
										<i class="fas fa-thumbtack mr-5"></i>Unpin
									</p>
								</div>
								<div class="col-4 border text-center h-100 pointer"
									onclick="window.location.href='<spring:url value="/post/view/${pin.post.id }"></spring:url>'">
									<p class="my-2">
										<i class="fas fa-external-link-square-alt mr-5"></i>See Post
									</p>
								</div>
								<div class="col-4 border text-center h-100 pointer"
									onclick="window.location.href='<spring:url value="/account/wall/${pin.post.createBy.username }"></spring:url>'">
									<p class="my-2">
										<i class="fas fa-user mr-5"></i>Visit user's page
									</p>
								</div>
							</div>
						</div>
						<div class="col-4">
							<div class="row">
								<div class="col-2">
									<img class="avatar-small"
										src="<spring:url value='/account/avatar?username=${pin.post.createBy.username }'></spring:url>">
								</div>
								<div class="col-10">
									<h3 class="text-truncate pointer text-main">${pin.post.createBy.username  }</h3>
									<p>
										<i class="fas fa-arrows-alt-v fa-lg mr-3"></i>${pin.post.createBy.prestigePoints }</p>
									<p>
										<i class="fas fa-map-marker mr-2"></i>${pin.post.createBy.ward.name },
										${pin.post.createBy.ward.district.name },
										${pin.post.createBy.ward.district.city.name }
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<sec:authorize access="isAuthenticated()">
			<div class="fixed-fullscreen" id="inbox">
				<div class="absolute inbox-composer">
					<div class="row py-3 pl-4 border-bottom header bg-white">
						<div class="col-1 pointer" id="inbox-back">
							<span><i class="fas fa-angle-left fa-2x mt-3"></i></span>
						</div>
						<div class="col-2">
							<img src="" id="composer-avatar" class="m-auto avatar-medium">
						</div>
						<div class="col-7">
							<h3 class="text-truncate" id="composer-username"></h3>
							<p class="m-0 text-small text-truncate" id="composer-sentAt"></p>
							<p class="m-0 text-small text-truncate" id="composer-location"></p>
						</div>
						<div class="col-2 p-0">
							<div class="icon icon-small composer-delete">
								<i class="fas fa-trash text-right"></i>
							</div>
							<div class="icon icon-small composer-mark">
								<i class="fas fa-dot-circle"></i>
							</div>
						</div>
					</div>
					<div class="row p-3 border-bottom">
						<div class="col custom-control-description" id="composer-content"></div>
					</div>
					<div class="row py-3 px-4">
						<textarea
							class="form-control custom-control-description text-size-post text-main"
							placeholder="Reply" rows="5" id="reply"></textarea>
						<div class="my-3">
							<button class="btn btn-outline-main float-left mr-4"
								id="message-send">Send</button>
						</div>
					</div>
					<div class="row h-25 mb-5 message-result hidden">
						<div class="text-center m-auto">
							<img
								src="<spring:url value="/resources/img/loading.gif"></spring:url>"
								class="avatar-large border fit-cover" id="message-loader">
							<span id="message-info" class="font-weight-bold"></span>
						</div>
					</div>
				</div>
				<div class="absolute outbox" id="outbox">
					<div class="row border-bottom">
						<div class="col-2 pt-1 border-right inbox-open pointer">
							<i class="fas fa-bars fa-2x mt-2 ml-2"></i>
						</div>
						<div class="col-10">
							<div class="w-25 h-100 py-3 pointer inbox-main-open float-left">
								<p class="text-center">Inbox</p>
							</div>
							<div class="w-25 h-100 py-3 pointer outbox-load float-left">
								<p class="text-center">Outbox</p>
							</div>
							<div class="w-25 h-100 py-3 pointer newoutbox-open float-left">
								<p class="text-center">
									<i class="fas fa-plus"></i>
								</p>
							</div>
						</div>
					</div>
					<div class="flow-auto" style="height: 85%;">
						<div id="outmessages-container"></div>
						<div class="row">
							<div class="col">
								<p class="pointer text-center font-italic m-0"
									id="outbox-showmore">Show more</p>
							</div>
						</div>
					</div>
				</div>
				<div class="absolute outbox-composer">
					<div class="row border-bottom">
						<div class="col-2 pt-1 border-right inbox-open pointer">
							<i class="fas fa-bars fa-2x mt-2 ml-2"></i>
						</div>
						<div class="col-10">
							<div class="w-25 h-100 py-3 pointer inbox-main-open float-left">
								<p class="text-center">Inbox</p>
							</div>
							<div class="w-25 h-100 py-3 pointer outbox-load float-left">
								<p class="text-center">Outbox</p>
							</div>
							<div class="w-25 h-100 py-3 pointer newoutbox-open float-left">
								<p class="text-center">
									<i class="fas fa-plus"></i>
								</p>
							</div>
						</div>
					</div>
					<div class="flow-auto" style="height: 85%;">
						<div class="row">
							<div class="col">
								<input id="newoutbox-id" class="form-control border-curve"
									placeholder="To"> <label class="text-main font-italic">Type
									in Receiver's username or email and press enter</label> <label
									id="newoutbox-id-error" class="text-danger hidden">This
									user doesn't exsit</label> <input id="newoutbox-id-input"
									class="hidden">
								<div class="col my-3 p-3" id="newoutbox-ids"></div>
								<textarea id="newoutbox-content-input" rows="7"
									class="form-control custom-control-description text-size-post text-main"
									placeholder="Compose here"></textarea>
								<div class="my-3">
									<button class="btn btn-outline-main float-left mr-4"
										id="newoutbox-send">Send</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="absolute inbox-main">
					<div class="row border-bottom">
						<div class="col-2 pt-1 border-right inbox-open pointer">
							<i class="fas fa-bars fa-2x mt-2 ml-2"></i>
						</div>
						<div class="col-10">
							<div class="w-25 h-100 py-3 pointer inbox-main-open float-left">
								<p class="text-center">Inbox</p>
							</div>
							<div class="w-25 h-100 py-3 pointer outbox-load float-left">
								<p class="text-center">Outbox</p>
							</div>
							<div class="w-25 h-100 py-3 pointer newoutbox-open float-left">
								<p class="text-center">
									<i class="fas fa-plus"></i>
								</p>
							</div>
						</div>
					</div>
					<div class="flow-auto" style="height: 85%;">
						<div id="messages-container">
							<div id="newmessages-container"></div>
							<c:forEach var="message" items="${inbox.unreadMessages }">
								<div class="row bg-noti message pointer">
									<input type="hidden" value="${message.id }" class="message-id">
									<span class="message-location hidden">${message.sender.ward.name },
										${message.sender.ward.district.name },
										${message.sender.ward.district.city.name }</span>
									<div class="col-2 border-right">
										<img
											src="<spring:url value='/account/avatar?username=${message.sender.username }'></spring:url>"
											class="m-auto avatar-medium">
									</div>
									<div class="col-9">
										<h3 class="text-truncate message-username">${message.sender.username }</h3>
										<p class="text-truncate mb-1 message-content">${message.content }</p>
										<p class="text-truncate text-small message-sentAt">
											<fmt:formatDate value="${message.sentAt }" type="both"
												pattern="MMM dd, yyyy hh:mm a"></fmt:formatDate>
										</p>
									</div>
									<div class="col-1 p-0">
										<div class="icon icon-small m-auto message-delete"
											id="message-delete-${message.id }">
											<i class="fas fa-trash text-right hidden"></i>
										</div>
										<div class="icon icon-small m-auto">
											<i class="fas fa-reply text-right hidden"></i>
										</div>
									</div>
								</div>
							</c:forEach>
							<c:forEach var="message" items="${inbox.readMessages }">
								<div class="row message pointer">
									<input type="hidden" value="${message.id }" class="message-id">
									<div class="col-2 border-right">
										<img
											src="<spring:url value='/account/avatar?username=${message.sender.username }'></spring:url>"
											class="m-auto avatar-medium">
									</div>
									<div class="col-9">
										<h3 class="text-truncate message-username">${message.sender.username }</h3>
										<p class="text-truncate mb-1 message-content">${message.content }</p>
										<p class="text-truncate text-small message-sentAt">
											<fmt:formatDate value="${message.sentAt }" type="both"
												pattern="MMM dd, yyyy hh:mm a"></fmt:formatDate>
										</p>
									</div>
									<div class="col-1 p-0">
										<div class="icon icon-small m-auto message-delete"
											id="message-delete-${message.id }">
											<i class="fas fa-trash text-right hidden"></i>
										</div>
										<div class="icon icon-small m-auto">
											<i class="fas fa-reply text-right hidden"></i>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="row">
							<div class="col">
								<p class="pointer text-center font-italic m-0"
									id="inbox-showmore">Show more</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" id="csrfToken" />
		<div class="overlay"></div>
	</div>
</body>
</html>