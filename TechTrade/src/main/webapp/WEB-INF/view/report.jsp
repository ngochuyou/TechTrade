<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Reports</title>
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
<sec:authorize access="isAuthenticated()">
	<script type="text/javascript"
		src="<spring:url value="/resources/js/mailbox.js"></spring:url>"></script>
</sec:authorize>
<script type="text/javascript"
	src="<spring:url value="/resources/js/report.js"></spring:url>"></script>
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
		<div class="main">
			<h2 class="text-center py-4 text-light border-bottom bg-main">
				<i class="fas fa-flag mr-3"></i>Post Reports
			</h2>
			<div class="row m-0 hpx-500 border-bottom" id="postReport-container">
				<div class="col-4 flowY-auto h-100 border-right">
					<c:forEach var="report" items="${postReportList }">
						<c:set var="postReportId"
							value="${report.id.accuser.username }${report.id.targetedPost.id }"></c:set>
						<div
							class="row h-25 border-bottom postReport pointer bg-noti-hover"
							id="${postReportId }">
							<div class="col-4">
								<p>
									<fmt:formatDate value="${report.createdAt }"></fmt:formatDate>
								</p>
							</div>
							<div class="col-8">
								<p class="text-truncate"
									data-postReport-content="${postReportId }">${report.content }</p>
							</div>
							<input type="hidden" data-postReport-post-id="${postReportId }"
								value="${report.id.targetedPost.id }"> <input
								type="hidden"
								data-postReport-reporter-username="${postReportId }"
								value="${report.id.accuser.username }"> <input
								type="hidden" data-postReport-post-upvote="${postReportId }"
								value="${report.id.targetedPost.upVote }"> <input
								type="hidden" data-postReport-post-involved="${postReportId }"
								value="${report.involvedIn }"> <input type="hidden"
								data-postReport-owner-username="${postReportId }"
								value="${report.id.targetedPost.createBy.username }"> <input
								type="hidden" data-postReport-owner-involved="${postReportId }"
								value="${report.userInvolvedIn }">
						</div>
					</c:forEach>
				</div>
				<div class="col-8">
					<div class="row h-100">
						<div class="col-6">
							<div class="row h-50">
								<div class="col">
									<h3 class="text-main">Post peek</h3>
									<div class="pl-3">
										<p>
											<i class="fas fa-user-times mr-3"></i>Reported by <span
												id="postReport-details-accuserUsername"></span>.
										</p>
										<p>
											<i class="fas fa-arrows-alt-v mr-3"></i><span
												id="postReport-details-post-vote"></span> Vote(s) in this
											post.
										</p>
										<p>
											<i class="fas fa-flag mr-3"></i><span
												id="postReport-details-post-involved"></span> Report(s)
											involved.
										</p>
										<p>
											<a href="" id="postReport-details-post"><i
												class="fas fa-external-link-square-alt mr-3"></i>See post
												details.</a>
										</p>
									</div>
								</div>
							</div>
							<div class="row h-25">
								<div class="col">
									<h3 class="text-main">Post's owner peek</h3>
									<div class="pl-3">
										<div class="row">
											<div class="col-6">
												<p class="text-truncate">
													<i class="fas fa-user mr-3"></i><span
														id="postReport-details-owner-username"></span>
												</p>
											</div>
											<div class="col-6">
												<p>
													<i class="fas fa-flag mr-3"></i><span
														id="postReport-details-owner-involved"></span> involved
												</p>
											</div>
										</div>
										<p>
											<a id="postReport-details-owner"><i
												class="fas fa-external-link-square-alt mr-3"></i>See user
												details.</a>
										</p>
									</div>
								</div>
							</div>
							<div class="row h-25">
								<div class="col">
									<h3 class="text-main">Action</h3>
									<div class="dropdown">
										<button class="btn-nobg text-main wpx-70 hpx-70" type="button"
											id="dropdownMenu2" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">
											<i class="fas fa-cog"></i>
										</button>
										<div class="dropdown-menu dropdown-menu-left bg-main"
											aria-labelledby="dropdownMenu2">
											<p
												class="dropdown-item text-white pointer postReport-action-postId"
												id="postReport-action-closePost" data-id="">Delete this
												post.</p>
											<p
												class="dropdown-item text-white pointer postReport-action-postId"
												id="postReport-action-delPost" data-id="">Permanently
												delete this post.</p>
											<p
												class="dropdown-item text-white pointer postReport-action-ownerId"
												id="postReport-action-messOwner" data-id="">Send a
												message to post's owner.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-6">
							<div class="row h-100">
								<div class="col">
									<h3>
										<i class="fas fa-comment-dots mr-3"></i>Accusation
									</h3>
									<div class="pl-3 flow-auto" style="max-height: 90%;">
										<p class="custom-control-description"
											id="postReport-details-content"></p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<h2 class="text-center py-4 text-light border-bottom bg-main">
				<i class="fas fa-flag mr-3"></i>User Reports
			</h2>
			<div class="row m-0 hpx-500" id="userReport-container">
				<div class="col-4 flowY-auto h-100 border-right">
					<c:forEach var="report" items="${userReportList }">
						<c:set var="userReportId"
							value="${report.id.accuser.username }${report.id.targetedUser.username }"></c:set>
						<div
							class="row h-25 border-bottom userReport pointer bg-noti-hover"
							id="${userReportId }">
							<div class="col-4">
								<p>
									<fmt:formatDate value="${report.createdAt }"></fmt:formatDate>
								</p>
							</div>
							<div class="col-8">
								<p class="text-truncate"
									data-userReport-content="${userReportId }">${report.content }</p>
							</div>
							<input type="hidden"
								data-userReport-target-username="${userReportId }"
								value="${report.id.targetedUser.username }"> <input
								type="hidden"
								data-userReport-reporter-username="${userReportId }"
								value="${report.id.accuser.username }"> <input
								type="hidden" data-postReport-involved="${userReportId }"
								value="${report.involvedIn }">
						</div>
					</c:forEach>
				</div>
				<div class="col-8">
					<div class="row h-100">
						<div class="col-6">
							<div class="row h-50">
								<div class="col">
									<h3 class="text-main">User peek</h3>
									<div class="pl-3">
										<div class="row">
											<div class="col">
												<p>
													<i class="fas fa-user-times mr-3"></i>Reported by <span
														id="userReport-details-accuserUsername"></span>.
												</p>
												<p class="text-truncate">
													<i class="fas fa-user mr-3"></i><span
														id="userReport-details-target-username"></span>
												</p>
												<p>
													<i class="fas fa-flag mr-3"></i><span
														id="userReport-details-target-involved"></span> involved
												</p>
											</div>
										</div>
										<p>
											<a id="userReport-details-owner"><i
												class="fas fa-external-link-square-alt mr-3"></i>See user
												details.</a>
										</p>
									</div>
								</div>
							</div>
							<div class="row h-25">
								<div class="col">
									<h3 class="text-main">Action</h3>
									<div class="dropdown">
										<button class="btn-nobg text-main wpx-70 hpx-70" type="button"
											id="dropdownMenu2" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false">
											<i class="fas fa-cog"></i>
										</button>
										<div class="dropdown-menu dropdown-menu-left bg-main"
											aria-labelledby="dropdownMenu2">
											<p class="dropdown-item text-white pointer"
												id="userReport-action-messOwner" data-id="">Send a
												message to this User.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-6">
							<div class="row h-100">
								<div class="col">
									<h3>
										<i class="fas fa-comment-dots mr-3"></i>Accusation
									</h3>
									<div class="pl-3 flow-auto" style="max-height: 90%;">
										<p class="custom-control-description"
											id="userReport-details-content"></p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
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
		</sec:authorize>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" id="csrfToken" />
		<div class="overlay"></div>
	</div>
</html>
