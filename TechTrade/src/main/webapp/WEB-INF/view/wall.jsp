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
										src="<spring:url value="/account/avatar?username=${account.username }"></spring:url>"
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
													src="<spring:url value="/account/avatar?username=${account.username }"></spring:url>"
													class="mr-5 avatar-small">
											</div>
											<div class="col-7 px-0">
												<p class="text-main text-truncate">${account.username }</p>
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
			style="background-image: url('<spring:url value="/account/avatar/${account.wallpaper }"></spring:url>');"
			class="parallax position-relative">
			<div class="wallpaper-cover">
				<p class="background-opacity m-0">
					<img
						src="<spring:url value="/account/avatar?username=${account.username }"></spring:url>"
						class="avatar-large box-shadow">
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
				<c:if test="${user.username eq account.username }">
					<div
						class="col-2 pt-4 pb-3 border-right box-shadow-hover pointer boxshadow-hover"
						onclick="window.location.href='<spring:url value='/post/upload'></spring:url>'">
						<h3 class="text-main text-center font-weight-bold">Upload</h3>
					</div>
					<div
						class="col-2 pt-4 pb-3 border-right box-shadow-hover pointer boxshadow-hover"
						onclick="window.location.href='<spring:url value='/post/pinned'></spring:url>'">
						<h3 class="text-main text-center font-weight-bold">Pinned</h3>
					</div>
				</c:if>
			</div>
			<div class="m-4">
				<div class="row">
					<div class="col-6">
						<div class="dropdown">
							<button class="btn-nobg text-main wpx-70 hpx-70" type="button"
								id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								<i class="fas fa-sort fa-lg"></i>
							</button>
							<div class="dropdown-menu dropdown-menu-left bg-main"
								aria-labelledby="dropdownMenu2" style="z-index: 1022;">
								<a class="dropdown-item text-white"
									href="<spring:url value="/account/wall/${account.username }?s=createAt:desc"></spring:url>">Newest</a>
								<a class="dropdown-item text-white"
									href="<spring:url value="/account/wall/${account.username }?s=createAt:asc"></spring:url>">Oldest</a>
								<a class="dropdown-item text-white"
									href="<spring:url value="/account/wall/${account.username }?s=upVote:desc"></spring:url>">Most
									voted</a> <a class="dropdown-item text-white"
									href="<spring:url value="/account/wall/${account.username }?s=upVote:asc"></spring:url>">Least
									Voted</a>
							</div>
						</div>
					</div>
					<sec:authorize access="isAuthenticated()">
						<c:if test="${user.username ne account.username }">
							<c:if test="${account.report eq null }">
								<div class="col-6 pointer" id="report-open">
									<h3 class="text-right">
										<i class="fas fa-flag mr-3"></i>Report
									</h3>
								</div>
							</c:if>
							<c:if test="${account.report ne null }">
								<div class="col-6 pointer" data-toggle="tooltip"
									data-placement="bottom"
									title="With accusation: ${account.report.description } and Description: ${account.report.content }">
									<h3 class="text-right">
										<i class="fas fa-flag mr-3"></i>You reported this user on
										<fmt:formatDate value="${account.report.createdAt }"
											type="date"></fmt:formatDate>
									</h3>
								</div>
							</c:if>
						</c:if>
					</sec:authorize>
				</div>
				<div id="post-content" class="bg-noti p-3">
					<span id="sort" class="hidden">${sortBy }</span>
					<p class="panel-header">Posts</p>
					<c:forEach var="post" items="${postList }">
						<div class="py-2 my-2 bg-white">
							<c:if test="${post.status eq false }">
								<h3 class="text-main">
									<span class="half-right-curve tags text-light bg-main mr-1">Closed</span>
								</h3>
							</c:if>
							<div class="row my-4 px-4">
								<sec:authorize access="isAuthenticated()">
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
												<div class="w-100 pointer downvote"
													id="downvote-${post.id }">
													<i class="fas fa-angle-down fa-3x"></i>
												</div>
											</c:if>
										</div>
									</div>
								</sec:authorize>
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
								<sec:authorize access="isAuthenticated()">
									<c:if test="${user.username eq account.username }">
										<div class="dropdown">
											<button class="btn-nobg text-light" type="button"
												id="dropdownMenu2" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false">
												<i class="fas fa-ellipsis-h text-dark fa-2x mr-4"></i>
											</button>
											<div class="dropdown-menu dropdown-menu-right box-shadow"
												aria-labelledby="dropdownMenu2">
												<a class="dropdown-item post-del" id="post-del-${post.id }"
													href="<spring:url value="/post/delete/${post.id }"></spring:url>">Delete</a>
												<c:if test="${post.status == true }">
													<a class="dropdown-item"
														href="<spring:url value="/post/status/${post.id }?s=close"></spring:url>">Close</a>
												</c:if>
												<c:if test="${post.status == false }">
													<a class="dropdown-item"
														href="<spring:url value="/post/status/${post.id }?s=restore"></spring:url>">Restore</a>
												</c:if>
											</div>
										</div>
									</c:if>
								</sec:authorize>
							</div>
							<div class="row pointer my-2  px-4"
								onclick="window.location.href='<spring:url value='/post/view/${post.id }'></spring:url>'">
								<div class="col custom-control-description text-size-post">${post.description }</div>
							</div>
							<div class="row px-4">
								<sec:authorize access="isAuthenticated()">
									<input id="flag" value='true' type="hidden" />
									<div class="col-6 float-left border text-center h-100">
										<h3 class="mt-3">
											<i class="fas fa-arrows-alt-v mr-5"></i>${post.upVote } Votes
										</h3>
									</div>
									<c:if test="${post.pin ne null }">
										<input id="isPin" type="hidden" value="false" />
										<div class="col-6 float-left border text-center h-100 pointer">
											<h3 class="mt-3 pin" style="color: #007bff;" id="${post.id }">
												<i class="fas fa-thumbtack mr-5"></i>Unpin
											</h3>
										</div>
									</c:if>
									<c:if test="${post.pin eq null }">
										<input id="isPin" type="hidden" value="true" />
										<div class="col-6 float-left border text-center h-100 pointer">
											<h3 class="mt-3 pin" id="${post.id }">
												<i class="fas fa-thumbtack mr-5"></i>Pin
											</h3>
										</div>
									</c:if>
								</sec:authorize>
								<sec:authorize access="isAnonymous()">
									<input id="flag" value='false' type="hidden" />
									<div class="col-12 float-left border text-center h-100">
										<h3 class="mt-3">
											<i class="fas fa-arrows-alt-v mr-5"></i>${post.upVote } Votes
										</h3>
									</div>
								</sec:authorize>
							</div>
						</div>
					</c:forEach>
				</div>
				<c:if test="${empty postList }">
					<div class="hpx-600"></div>
				</c:if>
			</div>
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
			<c:if test="${user.username ne account.username }">
				<div class="hidden-center-container" id="report-container">
					<div class="absolute-center flow-hidden p-3" id="report">
						<div class="row m-0" style="height: 30%;">
							<div class="col">
								<h5 class="text-main">
									<i class="fas fa-question mr-3"></i>You want to make a report
									about:
								</h5>
								<label class="text-dark custom-radio-container">This
									user is providing fake profile.<input type="radio"
									checked="checked" name="report-radio"
									value="The informations provided in this post is not real.">
									<span class="custom-radio-label"></span>
								</label> <label class="text-dark custom-radio-container">Other.<input
									type="radio" name="report-radio" value="Other."> <span
									class="custom-radio-label"></span>
								</label>
							</div>
						</div>
						<div class="row m-0" style="height: 70%;">
							<div class="col">
								<h5 class="text-main">
									<i class="fas fa-comment-dots mr-3"></i>Write a description:
								</h5>
								<textarea class="form-control" placeholder="Description"
									style="resize: none;" rows="9" id="report-content"></textarea>
								<button class="btn btn-main mt-3 float-left mr-3"
									id="report-send">Send report</button>
								<button class="btn btn-outline-main mt-3 float-left"
									id="report-cancel">Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</sec:authorize>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" id="csrfToken" />
		<div class="overlay"></div>
	</div>
</html>