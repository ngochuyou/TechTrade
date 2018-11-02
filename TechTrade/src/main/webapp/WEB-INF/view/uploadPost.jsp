<%@ page language="java" contentType="text/html; charset=utf-8"
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
	src="<spring:url value="/resources/js/uploadPost.js"></spring:url>"></script>
<sec:authorize access="isAuthenticated()">
	<script type="text/javascript"
		src="<spring:url value="/resources/js/mailbox.js"></spring:url>"></script>
</sec:authorize>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<style type="text/css">
.slider {
	overflow-x: hidden;
}

.slider figure {
	width: 400%;
	height: auto;
	min-height: 80%;
	overflow: hidden;
	position: relative;
	left: 0;
	transition: all .3s;
}

.slider figure>div {
	width: 25%;
	height: 100%;
	float: left;
	padding: 2rem 1rem;
}
</style>
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
					<sec:authentication property="principal" var="user" />
					<li>
						<div class="dropdown">
							<button class="btn-nobg dropdown-toggle text-light" type="button"
								id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
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
										<span>Inboxs</span><span class="badge bg-main ml-3 unread-qty">${inbox.unreadQty }</span>
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
							</div>
						</div>
					</li>
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
		<div class="row mt-5 mx-1">
			<div class="col mt-5">
				<h2 class="text-medium font-weight-bold text-center panel-header">Upload
					your post</h2>
			</div>
		</div>
		<form:form modelAttribute="model" method="POST" id="post-form"
			enctype="multipart/form-data" accept-charset="UTF-8">
			<div class="row hpx-1000 m-0 bg-noti">
				<div class="slider position-relative w-100">
					<div class="progress">
						<div class="progress-bar progress-bar-striped bg-main"
							role="progressbar" style="width: 25%" aria-valuenow="25"
							aria-valuemin="0" aria-valuemax="100" id="post-progress"></div>
					</div>
					<figure id="form-container">
						<div>
							<div class="row m-0">
								<div class="col-10">
									<h2 class="text-main border-bottom">
										Step 1: Give it a good <span
											class="font-weight-bold font-italic">Title</span>.<i
											class="fas fa-bullhorn ml-3"></i>
									</h2>
									<span class="text-danger hidden" id="name-forgot">Don't
										forget to enter the Post's title.</span>
								</div>
								<div class="col-2">
									<div class="btn btn-main mx-2 slider-right">Continue</div>
								</div>
							</div>
							<form:input path="name"
								class="form-control h-25 font-weight-bold text-center text-large text-main py-5"
								autocomplete="off" style="background: none; border:none" />
							<h3 class="my-3">
								<i class="fas fa-comment-dots mr-3"></i>Search box searches
								results by posts's titles so it's important to have a brief and
								concise title and this is a <span
									class="font-weight-bold font-italic">must-have</span> step.
							</h3>
						</div>
						<div class="stage-hashtags">
							<div class="row m-0">
								<div class="col-9">
									<h2 class="text-main border-bottom">
										Step 2: <span class="font-weight-bold font-italic">Category</span>
										and <span class="font-weight-bold font-italic">Hashtags</span>
										<i class="fas fa-hashtag ml-3"></i>
									</h2>
								</div>
								<div class="col-3">
									<div class="btn btn-outline-main mx-2 slider-left float-left">Back</div>
									<div class="btn btn-main mx-2 slider-right float-left mr-3">Continue</div>
								</div>
							</div>
							<h3 class="ml-5 mt-4">
								<i class="fas fa-comment-dots mr-3"></i><span
									class="font-weight-bold font-italic">First</span>: Category.
								Your post will be marked as a post in whichever kind of Category
								you chose below.
							</h3>
							<form:select path="categoryId"
								class="form-control text-medium hpx-50">
								<c:forEach var="cate" items="${cateList }">
									<form:option value="${cate.id }">${cate.name }</form:option>
								</c:forEach>
							</form:select>
							<span class="text-danger hidden" id="category-forgot">Don't
								forget to select the post's Category.</span>
							<h3 class="ml-5 mt-4">
								<i class="fas fa-comment-dots mr-3"></i><span
									class="font-weight-bold font-italic">Second</span>: Hashtags.
								Hashtags are important too, they will also be considered in
								search box so this is a +1 chance for your post to approach
								viewers.
							</h3>
							<p>
								<input type="text" id="hashtags-input"
									class="w-25 hpx-50 p-1 m-2" value="#"><span
									id="hashtags-add"><i class="fas fa-plus mx-2"></i></span>
							</p>
							<p id="hashtags-container"></p>
							<form:hidden path="tags" id="form-hashtags" />
							<h5 class="ml-3 mt-4">
								<i class="fas fa-hand-peace mr-3"></i>We will help you pick your
								hashtags by rating them base on the ones that have already been
								used in the community.
							</h5>
							<div class="ml-3 mt-3 progress">
								<div class="progress-bar bg-main" role="progressbar"
									style="width: 0%" aria-valuenow="0" aria-valuemin="0"
									aria-valuemax="100" id="hashtags-rate-result"></div>
							</div>
							<p class="ml-3 mt-3" id="hashtags-rate-message"></p>
						</div>
						<div>
							<div class="row m-0">
								<div class="col-9">
									<h2 class="text-main">
										Step 3: <span class="font-weight-bold font-italic">Description.</span><i
											class="fas fa-clipboard-list ml-3"></i>
									</h2>
								</div>
								<div class="col-3">
									<div class="btn btn-outline-main mx-2 slider-left float-left">Back</div>
									<div class="btn btn-main mx-2 slider-right float-left mr-3">Continue</div>
								</div>
							</div>
							<h3 class="ml-5 mt-4">
								<i class="fas fa-comment-dots mr-3"></i>Everything about your
								post should be in this area, try to describe it in the most
								detailed way so that people can understand what you're trying to
								say.
							</h3>
							<span class="text-danger hidden" id="description-forgot">Don't
								forget to enter the Description.</span>
							<form:textarea path="description"
								class="form-control text-medium border-main" rows="10"
								id="form-description" />
						</div>
						<div>
							<div class="row m-0">
								<div class="col-9">
									<h2 class="text-main">
										Final Step: <span class="font-weight-bold font-italic">Images.</span><i
											class="fas fa-images ml-3"></i>
									</h2>
								</div>
								<div class="col-3">
									<div
										class="btn btn-outline-main mx-2 slider-left float-left mr-3">Back</div>
									<form:button type="submit"
										class="btn btn-main mx-2 float-left mr-3">Post!</form:button>
								</div>
								<h3 class="ml-5 mt-4">
									<i class="fas fa-comment-dots mr-3"></i>Let's add some images
									in order to make the description even easier to understand.
								</h3>
								<h3 class="ml-5 mt-4">
									<i class="fas fa-comment-dots mr-3"></i>You can choose one or
									multiple images but remember, you can not upload more than 12
									images.
								</h3>
							</div>
							<div class="row mx-0 mt-3" style="margin-bottom: 90px;">
								<div class="col-4"></div>
								<div class="col-4">
									<label class="dropzone border-dashed" id='dropzone' for="files">
										Drop images on me OR click on me. </label>
									<form:input type="file" path="file" multiple="multiple"
										id="files"></form:input>
								</div>
								<div class="col-4"></div>
							</div>
							<div class="row preview border-dashed m-3 p-3"></div>
							<div class="row m-0">
								<div class="col-12 border pointer" id="files-cancel">
									<h3 class="mt-3 text-center">
										<i class="fas fa-trash mr-5"></i>Clear
									</h3>
								</div>
							</div>
						</div>
					</figure>
				</div>
			</div>
		</form:form>
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