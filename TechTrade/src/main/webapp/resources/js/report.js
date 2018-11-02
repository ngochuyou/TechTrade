$(document).ready(function() {
	$('.overlay').click(function() {
		$('#sidebar').addClass('sidebar-active');
		$('.overlay').removeClass('active-overlay');
	});

	$('#sidebarCollapse').click(function() {
		$('#sidebar').removeClass('sidebar-active');
		$('.overlay').addClass('active-overlay');
		$('.collapse.in').toggleClass('in');
		$('a[aria-expanded=true]').attr('aria-expanded', 'false');
	});

    var search_box = $('#search');
    var search_dropdown = $('#my-dropdown-container');
    
    $(window).click(function(event) {
    	if (event.target.id != 'my-dropdown-container') {
    		search_dropdown.hide();
    	}
    });
  
    search_box.keyup(function() {
        $.ajax({
        	type : 'GET',
        	url : '/TechTrade/post/search',
        	data : {
        		keyword : search_box.val(),
        	},
        	success : function(list) {
        		var string = "";
        		
        		$.each(list, function() {
        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/view/" + this[1] + "'>" + this[0] + "</a>";
        		});
        		
        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k=" + search_box.val() + "'>See more</a>";
        		search_dropdown.html(string);
        		search_dropdown.show();
        	},
        	error : function() {
        		search_dropdown.hide();
        	}
        });
    });
    
    var crfs = $('#csrfToken');
    var postReport_container = $('#postReport-container');
    var postReport = $('.postReport');
    var postReport_id;
    var postReport_details_post = $('#postReport-details-post');
    var postReport_details_owner = $('#postReport-details-owner');
    var postReport_details_accuserUsername = $('#postReport-details-accuserUsername');
    var postReport_details_post_vote = $('#postReport-details-post-vote');
    var postReport_details_post_involved = $('#postReport-details-post-involved');
    var postReport_details_owner_username = $('#postReport-details-owner-username');
    var postReport_details_owner_involved = $('#postReport-details-owner-involved');
    var postReport_details_content = $('#postReport-details-content');
    var postReport_action_postId = $('.postReport-action-postId');
    var postReport_action_ownerId = $('.postReport-action-ownerId');
    var postReport_postId;
    var postReport_ownerId;

    postReport.click(function() {
    	postReport.removeClass('bg-noti');
    	$(this).addClass('bg-noti');
    	postReport_id = this.id;
    	postReport_postId = $("[data-postReport-post-id='" + postReport_id + "']").val();
    	postReport_ownerId = $("[data-postReport-owner-username='" + postReport_id + "']").val();
    	
    	postReport_details_post.attr('href', '/TechTrade/post/view/' + postReport_postId);
    	postReport_details_owner.attr('href', '/TechTrade/account/wall/' + postReport_ownerId);
    	postReport_details_accuserUsername.text($("[data-postReport-reporter-username='" + postReport_id + "']").val());
    	postReport_details_post_vote.text($("[data-postReport-post-upvote='" + postReport_id + "']").val());
    	postReport_details_post_involved.text($("[data-postReport-post-involved='" + postReport_id + "']").val());
    	postReport_details_owner_username.text(postReport_ownerId);
    	postReport_details_owner_involved.text($("[data-postReport-owner-involved='" + postReport_id + "']").val());
    	postReport_details_content.text($("[data-postReport-content='" + postReport_id + "']").text());
    	postReport_action_postId.attr('data-id', postReport_postId);
    	postReport_action_ownerId.attr('data-id', postReport_ownerId);
    });
    
    var postReport_action_closePost = $('#postReport-action-closePost');
    var postReport_action_delPost = $('#postReport-action-delPost');
    var postReport_action_messOwner = $('#postReport-action-messOwner');
    var postReport_action_targetedId;
    
    postReport_action_closePost.click(function() {
    	postReport_action_targetedId = $(this).attr('data-id');
    	appendNoti(postReport_container, "<i class='fas fa-trash mr-3'></i>Are you sure you want to delete this post? ", "post-del");
    });
    
    postReport_container.on('click', '.noti-no', function(){
    	noti.remove();
    });
    
    postReport_container.on('click', '#post-del-yes', function() {
    	$.ajax({
    		type : 'GET',
    		url : '/TechTrade/admin/post/delete/' + postReport_action_targetedId,
    		success : function(result) {
    			noti.html("<i class='fas fa-trash mr-3'></i>" + result);
    			setTimeout(function() {
    				noti.remove();
    			}, 3000);
    		}
    	});
    });
    
    postReport_action_delPost.click(function() {
    	postReport_action_targetedId = $(this).attr('data-id');
    	appendNoti(postReport_container, "<span class='text-small'><i class='fas fa-trash mr-3'></i>Are you sure you want to <span class='font-weight-bold'>PERMANENTLY DELETE</span> this post? Everthing related (include Reports and Report Actions) will be completely deleted.</span>", "post-permadel");
    });
    
    postReport_container.on('click', '#post-permadel-yes', function() {
    	$.ajax({
    		type : 'POST',
    		url : '/TechTrade/admin/post/permadelete/' + postReport_action_targetedId,
    		data : {
    			[crfs.attr('name')] : crfs.val()
    		},
    		success : function(result) {
    			if (result == 'Post permanently deleted') {
    				noti.html("<i class='fas fa-trash mr-3'></i>" + result);
        			setTimeout(function() {
        				noti.remove();
        			}, 3000);
        			$('#' + postReport_id).remove();
    			}
    		}
    	});
    });
    
    var inbox_main = $('.inbox-main');
    var inbox_composer = $('.inbox-composer');
    var outbox = $('#outbox');
    var outbox_newOutbox = $('.outbox-composer');
    
    postReport_action_messOwner.click(function() {
    	$(inbox).toggleClass('fixed-fullscreen-active');	
    	$(inbox_main).fadeOut("fast");
		$(inbox_composer).fadeOut("fast");
		$(outbox).fadeOut("fast");
		$(outbox_newOutbox).fadeIn("fast");
    });
    
    var userReport = $('.userReport');
    var userReport_id;
    var userReport_targetId;
    var userReport_details_accuserUsername = $('#userReport-details-accuserUsername');
    var userReport_details_content = $('#userReport-details-content');
    var userReport_details_targetUsername = $('#userReport-details-target-username');
    var userReport_details_targetInvolved = $('#userReport-details-target-involved');
    var userReport_details_target = $('#userReport-details-owner');
    
    userReport.click(function() {
    	userReport_id = this.id;
    	userReport_targetId = $("[data-userReport-target-username='" + userReport_id + "']").val();
    	
    	userReport_details_content.text($("[data-userReport-content='" + userReport_id + "']").text());
    	userReport_details_accuserUsername.text($("[data-userReport-reporter-username='" + userReport_id + "']").val());
    	userReport_details_targetUsername.text(userReport_targetId);
    	userReport_details_targetInvolved.text($("[data-postReport-involved='" + userReport_id + "']").val());
    	userReport_details_target.attr('href', '/TechTrade/account/wall/' + userReport_targetId);
    });
    
    var userReport_action_messOwner = $('#userReport-action-messOwner');
    
    userReport_action_messOwner.click(function() {
    	$(inbox).toggleClass('fixed-fullscreen-active');	
    	$(inbox_main).fadeOut("fast");
		$(inbox_composer).fadeOut("fast");
		$(outbox).fadeOut("fast");
		$(outbox_newOutbox).fadeIn("fast");
    });
    
    var noti;
    
    function appendNoti(noti_container, message, idprefix) {
    	noti_container.append("<div class='fixed-noti noti'>" + message + "<button class='btn bg-main mx-4' id='" + idprefix + "-yes'>Yes!</button><button class='btn btn-outline-main noti-no'>Don't do it</button></div>");
    	noti = $('.noti');
    }
    
    var monthNames = [
		"Jan", "Feb", "Mar",
		"Apr", "May", "Jun", "Jul",
		"Aug", "Sep", "Oct",
		"Nov", "Dec"
    ];
    
    function formatCurrentDate(date) {
    	
    	return monthNames[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear();
    }
    
    var overTime = "AM";
    var hour;
    var minute; 
    var dateObject;
    
    function formatCurrentDateTime(date) {
    	dateObject = new Date(date);
    	overTime = "AM";
    	hour = dateObject.getHours();
    	
    	if (hour >= 13) {
    		hour -= 12;
    		overTime = "PM";
    	}
    	
    	if (hour < 10) {
			hour = "0" + hour;
		}
    	
    	minute = dateObject.getMinutes();
    	
    	if (minute < 10) {
    		minute = "0" + minute;
    	}
    	
		return monthNames[dateObject.getMonth()] + ' ' + dateObject.getDate() + ', ' + dateObject.getFullYear() + ' ' + hour + ':' + minute + " " + overTime;
    }
});