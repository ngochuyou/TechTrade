<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
				id="search-form" action="<spring:url value="/search"></spring:url>"
				method="get">
				<input class="form-control w-100 font-weight-bold" type="search"
					placeholder="Try something like Category's name or Post"
					aria-label="Search" id='search' name="k" autocomplete="off">
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
									class="btn-blank hpx-70  my-0 my-sm-0 font-weight-bold wpx-100"
									type="submit" id="signin">Sign in</button></a></li>
						<li class="nav-item active"><a
							href="<spring:url value="/account/sign-up"></spring:url>"><button
									class="btn-blank hpx-70 my-2 my-sm-0 font-weight-bold wpx-100"
									type="submit" id="signup">Sign up</button></a></li>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<sec:authentication property="principal" var="user" />
						<c:set var="flag" value="${user.username == post.username }"></c:set>
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
										<div class="row">
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
				</ul>
			</div>
		</nav>
		<div
			style="background-image: url('<spring:url value="/resources/img/parallax.jpg"></spring:url>');"
			class="parallax"></div>
		<div class="p-5">
			<div class="row">
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal" var="user" />
					<c:if test="${user.username == post.username}">
						<div class="col-6">
							<h1 class="panel-header">${post.username }'s<span
									class="mx-2">Post</span>
							</h1>
						</div>
						<div class="col-5">
							<c:if test="${flag eq true}">
								<div class="text-right editable hidden">
									<button class="btn bg-main mx-2 my-1 d-inline-block"
										id="submit-all">Apply all changes</button>
									<button
										class="btn btn-outline-main mx-2 my-1 d-inline-block border-main"
										onclick="window.location.reload()">Cancel all changes</button>
								</div>
							</c:if>
						</div>
						<div class="col-1 text-right">
							<div class="dropdown">
								<button class="btn-nobg text-light" type="button"
									id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									<i class="fas fa-ellipsis-h text-dark fa-2x mr-4"></i>
								</button>
								<div class="dropdown-menu dropdown-menu-right box-shadow"
									aria-labelledby="dropdownMenu2">
									<p class="dropdown-item" id="post-edit">Edit</p>
									<a class="dropdown-item"
										href="<spring:url value="/post/delete/${post.id }"></spring:url>">Delete</a>
									<c:if test="${post.status == true }">
										<a class="dropdown-item"
											href="<spring:url value="/post/${post.id }/status?s=close"></spring:url>">Close</a>
									</c:if>
									<c:if test="${post.status == false }">
										<a class="dropdown-item"
											href="<spring:url value="/post/${post.id }/status?s=restore"></spring:url>">Restore</a>
									</c:if>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${flag ne true}">
						<div class="col-12">
							<h1 class="panel-header">${post.username }'s<span
									class="mx-2">Post</span>
							</h1>
						</div>
					</c:if>
				</sec:authorize>
			</div>
			<div class="post">
				<form:form modelAttribute="post" method="post" id="form"
					enctype="multipart/form-data">
					<form:hidden path="id" id="post-id" />
					<form:hidden path="username" />
					<div class="row m-2">
						<div class="col-1 m-auto text-center" id="vote-holder">
							<c:if test="${not empty post.vote }">
								<c:if test="${post.vote.type eq true }">
									<p class="w-100">You voted this post +1</p>
								</c:if>
								<c:if test="${post.vote.type ne true }">
									<p class="w-100">You voted this post -1</p>
								</c:if>
							</c:if>
							<c:if test="${empty post.vote }">
								<div class="w-100 pointer" id="upvote">
									<i class="fas fa-angle-up fa-3x"></i>
								</div>
								<div class="w-100 pointer" id="downvote">
									<i class="fas fa-angle-down fa-3x"></i>
								</div>
							</c:if>
						</div>
						<div class="col-10 px-4">
							<h3>
								<i class="fas fa-map-marker mr-2"></i>${post.createBy.ward.name },
								${post.createBy.ward.district.name },
								${post.createBy.ward.district.city.name }
							</h3>
							<div class="row">
								<div class="col-11">
									<h2 class="font-weight-bold" id="post-name">${post.name }</h2>
									<form:input path="name"
										class='mr-3 w-100 form-control font-weight-bold text-medium pr-5 hidden'
										id='title-input' />
									<button class="btn bg-main hidden top-right" id="title-submit">Edit</button>
								</div>
								<div class="col-1">
									<c:if test="${flag eq true}">
										<span id="title-edit" class="hidden editable"><i
											class="fas fa-edit fa-2x text-muted my-4 pointer"></i></span>
										<button class="btn bg-main hidden py-1" id="title-cancel">Cancel</button>
									</c:if>
								</div>
							</div>
							<p>
								By <span class="font-italic text-main">${post.username }</span>
								on
								<fmt:formatDate value="${post.createAt }" />
							</p>
							<div class="my-2 row">
								<div class="col-11">
									<span>Tags <i class="fas fa-hashtag"></i>
									</span> <span class="tags stage-1 d-inline-block"
										style="background-color : ${post.category.tagColor}">${post.category.name }</span>
									<span id="hashtags-container" class="line-height-large"><span
										class="color-main tags hashtags m-2 d-inline-block">${fn:replace(post.tags, ",", "<span class='hidden hashtags-stage2 hashtags-del'><i class='fas fa-times-circle'></i></span></span><span class='color-main tags hashtags mx-2 d-inline-block'>")}
											<span class="hidden hashtags-stage2 hashtags-del"><i
												class="fas fa-times-circle"></i></span>
									</span> </span><span class="hidden hashtags-stage2"><input type="text"
										id="hashtags-add-input" value="#" class="p-1 m-2"><span
										id="hashtags-add"><i class="fas fa-plus mx-2"></i></span></span>
									<form:hidden path="tags" id="hashtags-input" />
								</div>
								<div class="col-1">
									<c:if test="${flag eq true}">
										<span id="hashtags-edit" class="hidden editable"><i
											class="fas fa-edit fa-2x text-muted pointer"></i></span>
										<button class="btn bg-main hidden hashtags-stage2 my-2"
											id="hashtags-submit">Edit</button>
										<button class="btn bg-main hidden py-1 hashtags-stage2"
											id="hashtags-cancel">Cancel</button>
									</c:if>
								</div>
							</div>
						</div>
						<div class="col-1">
							<img
								src="<spring:url value="/account/avatar/${post.createBy.avatar }"></spring:url>"
								class="avatar position-right mx-3">
						</div>
					</div>
					<div class="row">
						<div class="col custom-control-description text-size-post p-3"
							id="post-description">${post.description }</div>
						<form:textarea path="description"
							class='hidden my-3 custom-control-description text-size-post p-3 w-100 text-main'
							rows='15' id='content-input' />
					</div>
					<c:if test="${flag eq true}">
						<div class="row px-2 text-right border-bottom">
							<button class="btn bg-main hidden mx-2 my-1" id="content-submit">Edit</button>
							<button class="btn bg-main hidden mx-2 my-1" id="content-cancel">Cancel</button>
							<span id="content-edit" class="hidden editable"><i
								class="fas fa-edit fa-2x text-muted pointer mx-2 my-1"></i></span>
						</div>
					</c:if>
					<div class="row m-3">
						<form:hidden path="deletedImages" id="deleted-image-input" />
						<c:forEach var="image" items="${images }" varStatus="level">
							<c:if test="${(level.index + 1) % 3 eq 0}">
								<div
									class="col-4 hpx-350 p-2 text-center position-relative image"
									id="image-${image.id }">
									<div
										class="position-absolute wpx-70 hpx-70 hidden image-del pointer"
										id="image-del-btn${image.id }">
										<i class="fas fa-times-circle fa-lg text-main"></i>
									</div>
									<img src="/TechTrade/post/images/${image.filename }">
								</div>
								<c:out value="</div><div class='row m-2'>" escapeXml="false"></c:out>
							</c:if>
							<c:if test="${(level.index + 1) % 3 ne 0}">
								<div
									class="col-4 hpx-350 p-2 text-center position-relative image"
									id="image-${image.id }">
									<div
										class="position-absolute wpx-70 hpx-70 hidden image-del pointer"
										id="image-del-btn${image.id }">
										<i class="fas fa-times-circle fa-lg text-main"></i>
									</div>
									<img src="/TechTrade/post/images/${image.filename }">
								</div>
							</c:if>
						</c:forEach>
					</div>
					<c:if test="${flag eq true}">
						<div class="row post-footer">
							<div class="col-1 float-left">
								<span id="image-edit" class="hidden editable"><i
									class="fas fa-edit fa-2x text-muted pointer m-2"></i></span>
							</div>
							<div class="col-6 float-left border text-center h-100 hidden"
								id="image-add">
								<label for="upload-photo" class="w-100 h-100 text-main mt-3"><h3>
										<i class="fas fa-plus mr-5"></i>Add more images
									</h3></label>
								<form:input path="file" type="file" id="upload-photo"
									class="uploadFile" multiple="multiple" />
							</div>
							<div id="image-cancel"
								class="col-5 float-left border text-center h-100 pointer hidden">
								<div class="m-auto text-main">
									<h3 class="mt-3">
										<i class="fas fa-trash mr-5"></i>Clear
									</h3>
								</div>
							</div>
						</div>
						<div class="row preview border-dashed mx-3 p-3 hidden"></div>
					</c:if>

					<div class="row post-footer">
						<div class="col">
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
										<h3 class="mt-3 pin" style="color: blue;" id="${post.id }">
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
				</form:form>
				<div class="hidden stage-2" id="cancel">Cancel</div>
				<div class="panel-header">Comments</div>
				<div class="row m-2" id="comments">
					<c:forEach var="comment" items="${comments }">
						<div class="m-2 w-100">
							<p class="text-primary">
								<fmt:formatDate value="${comment.commentedOn }" />
							</p>
							<p>
								<span class="font-italic text-primary">${comment.account.username }</span>
								: ${comment.content }
							</p>
						</div>
					</c:forEach>
				</div>
				<sec:authorize access="isAuthenticated()">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" id="csrfToken" />
					<div class="row m-2">
						<div class="m-2 w-100">
							<input class="mycustom-input w-100 form-control"
								placeholder="Write a comment..." id="comment" />
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
		<div class="overlay"></div>
	</div>
</body>
</html>