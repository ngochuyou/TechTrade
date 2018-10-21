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

		$(window).click(function(target) {
	    	if (target.target.id != 'my-dropdown-container') {
	    		$('#my-dropdown-container').hide();
	    	}
	    });
		
		var string = "";
		
	    $('#search').keyup(function() {
	        $.ajax({
	        	type : 'GET',
	        	url : '/TechTrade/post/search',
	        	data : {
	        		keyword : $('#search').val(),
	        	},
	        	success : function(list) {
	        		string = "";
	        		$.each(list, function() {
	        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/"+this[1]+"'>"+this[0]+"</a>";
	        		});
	        		
	        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k="+$('#search').val()+"'>See more</a>";
	        		$('#my-dropdown-container').html(string);
	        		$('#my-dropdown-container').show();
	        	},
	        	error : function() {
	        		$('#my-dropdown-container').hide();
	        	}
	        });
	    });

		$('#profile-posts').click(function() {
			$('html, body').animate({
				scrollTop: $(this).parent().offset().top -100
			}, 200);
		});
		
		var postId;
		
		$('.upvote').click(function() {
			postId = this.id.match(/\d+/);
			
	    	$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/post/vote/' + postId,
	    		data : {
	    			type : true
	    		},
	    		success : function(result) {
	    			$('#vote-holder-' + postId).html("<p class='w-100'>You voted this post +1</p>");
	    		}
	    	});
	    });
	    
	    $('.downvote').click(function() {
	    	postId = this.id.match(/\d+/);
	    	
	    	$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/post/vote/' + postId,
	    		data : {
	    			type : false
	    		},
	    		success : function(result) {
	    			$('#vote-holder-' + postId).html("<p class='w-100'>You voted this post -1</p>");
	    		}
	    	});
	    });
	    
	    var currentPage = 0;
	    var stopPaging = false;
	    
	    $(window).scroll(function() {
	  	   if((($(window).scrollTop() + $(window).height())) == ($(document).height())) {
	 		   if (stopPaging == true) {
	 			   return ;
	 		   }
	  		   $('#loader').fadeIn("fast");
	  	       $.ajax({
	  	    	   type: 'GET',
	  	    	   url: '/TechTrade/account/api/' + $('#username').text(),
	  	    	   data:{
	  	    		   p: currentPage + 1,
	  	    		   s: $('#sort').text()
	  	    	   },
	  	    	   contentType: "application/json; charset=utf-8",
	  	    	   success: function(result){
	  	    		   currentPage++;
  	    			   string = "";
	  	    		   $.each(result, function() {
	  	    			   spanTags = "";
	  	    			   $.each(this.tags.split(','), function(){
	  	    				   spanTags += "<span class='color-main tags d-inline-block'>"+this+"</span> ";
	  	    			   });
	  	    			   date = new Date(this.createAt);	
	  	    			   string += "<div class='post'>"
	 		+"			<div class='row my-2'>"
	 		+"				<div class='col-10'>"
	 		+"					<h3>"
	 		+"						<i class='fas fa-map-marker mr-2'></i>"+this.createBy.ward.name+","
	 		+"						"+this.createBy.ward.district.name+','
	 		+"						"+this.createBy.ward.district.city.name
	 		+"					</h3>"
	 		+"					<h2 class='text-truncate font-weight-bold'>"+this.name+"</h2>"
	 		+"					<p>"
	 		+"						By <span class='font-italic text-main'>"+this.createBy.username+"</span>"
	 		+"						on " + formatCurrentDate(date)
	 		+"					</p>"
	 		+"					<div class='line-height-large'>"
	 		+"						<span>Tags <i class='fas fa-hashtag'></i>"
	 		+"						</span> <span class='tags d-inline-block'"
	 		+"							style='background-color : "+this.category.tagColor+"'>"+this.category.name+"</span> "
	 		+ spanTags
	 		+"						</span>"					
	 		+"					</div>"
	 		+"				</div>"
	 		+"				<div class='col-2'>"
	 		+"					<img src='/TechTrade/account/avatar/"+this.createBy.avatar +"'"
	 		+"						class='avatar position-right mx-3'>"
	 		+"				</div>"
	 		+"			</div>"
	 		+"			<div class='row pointer' onclick='window.location.href=\"/TechTrade/post/"+ this.id +"\"'>"
	 		+"				<div class='col custom-control-description text-size-post'>"+ this.description +"</div>"
	 		+"			</div>"
	 		+"			<div class='row post-footer'>"
	 		+"				<div class='col'>"
	 		+"					<div class='col-6 float-left border text-center h-100'>"
	 		+"						<h3 class='mt-3'>"
	 		+"							<i class='fas fa-arrows-alt-v mr-5'></i>"+this.upVote+" Votes"
	 		+"						</h3>"
	 		+"					</div>"
	 		+"					<div class='col-6 float-left border text-center h-100 pointer'>"
	 		+"						<h3 class='mt-3'>"
	 		+"							<i class='fas fa-thumbtack mr-5'></i>Pin"
	 		+"						</h3>"
	 		+"					</div>"
	 		+"				</div>"
	 		+"			</div>"
	 		+"		</div>";
	  	        		});
	  	    		   $('#post-content').html($('#post-content').html()+string);
	  	    		   $('#loader').fadeOut("fast");
	 	    		   if (string.length == 0) {
	 	    			   stopPaging = true;
	 	    		   }
	  	    	   }
	  	       });
	  	   };
	  	});
	    
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
	    var dateObject;
	    
	    function formatCurrentDateTime(date) {
	    	dateObject = new Date(date);
	    	overTime = "AM";
	    	hour = dateObject.getHours();
	    	
	    	if (hour >= 13) {
	    		hour -= 12;
	    		overTime = "PM";
	    	}
	    	
			return monthNames[dateObject.getMonth()] + ' ' + dateObject.getDate() + ', ' + dateObject.getFullYear() + ':' + hour + ':' + dateObject.getMinutes() + ':' + dateObject.getSeconds() + ' ' + overTime;
	    }
	    
	    var inbox = $('#inbox');
	    var currentInboxPage = 0;
	    var messagesContainer = $('#messages-container');
	    var messagesContainerHTML = $(messagesContainer).html();
	    var stopPagingMessages = false;
	    
	    $('.inbox-open').click(function() {
	    	$(inbox).toggleClass('fixed-fullscreen-active');
	    });
	    
	    $('#inbox-showmore').click(function() {
	    	if (stopPagingMessages == true) {
	    		return ;
	    	}
	    	
	    	$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message',
	    		contentType: "application/json; charset=utf-8",
	    		data : {
	    			page : currentInboxPage + 1
	    		},
	    		success : function(result) {
	    			if (result.length <= 0) {
	    				stopPagingMessages = true; 
	    				$('#inbox-showmore').text("Nothing left.");
	    				return ;
	    			}
	    			currentInboxPage += 1;
	    			$.each(result, function() {
	    				messagesContainerHTML += "<div class='row message pointer'>"
	    								+ "<input type='hidden' value='" + this.id + "' class='message-id'>"
	    								+ "<span class='message-location hidden'>" + this.sender.ward.name + ","
	    								+ this.sender.ward.district.name + ","
	    								+ this.sender.ward.district.city.name + "</span>"
	    								+ "<div class='col-2 border-right'>"
	    								+ "<img src='/TechTrade/account/avatar?username="+ this.sender.username + "' class='m-auto avatar-medium'>"
	    								+ "</div>"
	    								+ "<div class='col-9'>"
	    								+ "<h3 class='text-truncate message-username'>" + this.sender.username + "</h3>"
	    								+ "<p class='text-truncate mb-1 message-content'>" + this.content + "</p>"
	    								+ "<p class='text-truncate text-small message-sentAt'>"
	    								+ formatCurrentDateTime(this.sentAt) + "</p>"
	    								+ "</div>"
	    								+ "<div class='col-1 p-0'>"
	    								+ "<div class='icon icon-small m-auto message-delete' id='message-delete-" + this.id + "'>"
	    								+ "<i class='fas fa-trash text-right hidden'></i>"
	    								+ "</div>"
	    								+ "<div class='icon icon-small m-auto'>"
	    								+ "<i class='fas fa-reply text-right hidden'></i>"
	    								+ "</div></div></div>";
	    			});
	    			$(messagesContainer).html(messagesContainerHTML);
	    		}
	    	});
	    });
	    
	    $('.inbox-main').on('mouseenter', '.message', function() {
	    	$(this).find('svg').removeClass('hidden');
	    });
	    
	    $('.inbox-main').on('mouseleave', '.message', function() {
	    	$(this).find('svg').addClass('hidden');
	    });
	    
	    var delete_noti = $('#delete-noti');
	    var message_id;
	    var message_root;
	    var inbox_main = $('.inbox-main');
	    var inbox_composer = $('.inbox-composer');
	    
	    $(inbox_main).on('click', '.message-delete', function() {
	    	$('.delete-noti').remove();
	    	message_root = $(this).parents().eq(1);
	    	message_id = this.id.match(/\d+/).toString();
	    	$(inbox_main).append("<div class='fixed-noti delete-noti' id='delete-noti'>Are you sure you want to delete this message from <span class='font-weight-bold'>"+ $(message_root).find('.message-username').text() +"</span>? Action can not be undo. <button class='btn bg-main mx-4' id='noti-yes'>Yes!</button><button class='btn btn-outline-main' id='noti-no'>Don't do it</button></div>");
	    	delete_noti = $('#delete-noti');
	    });
	    
    	$(inbox_main).on('click', '#noti-yes', function() {
    		$(delete_noti).html("<img src='/TechTrade/resources/img/loading.gif' class='mr-3' style='height : 50px;'> Please wait...");
    		$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message/remove',
	    		data : {
	    			messageId : message_id
	    		},
	    		success : function(result) {
	    			$(delete_noti).text(result);
	    		}
	    	});
    		$(message_root).remove();
    		setTimeout(function() {
    			  $(delete_noti).remove();
    		}, 3000);
    	});
    	
    	$(inbox_main).on('click', '#noti-no', function() {
    		$('#delete-noti').remove();
	    	return ;
    	});
    	
	    var sender; 
	    var reply = $('#reply');
	    var inbox_back = $('#inbox-back');
	    
	    $(inbox).on('click', '.message .col-9', function() {
	    	sender = $(this).find('.message-username').text();
	    	$('#composer-avatar').attr('src', '/TechTrade/account/avatar?username=' + sender);
	    	$('#composer-username').text(sender);
	    	$('#composer-sentAt').text($(this).find('.message-sentAt').text());
	    	$('#composer-location').text($(this).find('.message-location').text());
	    	$('#composer-content').text($(this).find('.message-content').text());
	    	$('.composer-delete').attr('id', 'composer-delete-' + $(this).parent().find('.message-delete').attr('id').match(/\d+/));
	    	$(inbox_main).fadeOut("fast");
	    	$(inbox_composer).fadeIn("fast");
	    });
	    
	    $('.composer-delete').click(function() {
	    	$('.delete-noti').remove();
	    	message_id = this.id.match(/\d+/).toString();
	    	$(inbox_composer).append("<div class='fixed-noti delete-noti' id='delete-noti'>Are you sure you want to delete this message from <span class='font-weight-bold'>"+ sender +"</span>? Action can not be undo. <button class='btn bg-main mx-4' id='noti-yes'>Yes!</button><button class='btn btn-outline-main' id='noti-no'>Don't do it</button></div>");
	    	delete_noti = $('#delete-noti');
	    });
	    
	    $(inbox_composer).on('click', '#noti-yes', function() {
    		$(delete_noti).html("<img src='/TechTrade/resources/img/loading.gif' class='mr-3' style='height : 50px;'> Please wait...");
    		$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message/remove',
	    		data : {
	    			messageId : message_id
	    		},
	    		success : function(result) {
	    			$(delete_noti).text(result);
	    		}
	    	});
    		$('#message-delete-' + message_id).parents().eq(1).remove();
    		$('.composer-delete').attr('id', null);
    		setTimeout(function() {
    			  $(delete_noti).remove();
    		}, 3000);
    		$(inbox_back).click();
    	});
    	
    	$(inbox_composer).on('click', '#noti-no', function() {
    		$('#delete-noti').remove();
	    	return ;
    	});
    	
	    $(inbox_back).click(function() {
	    	$('.inbox-main').fadeIn("fast");
	    	$('.inbox-composer').fadeOut("fast");
	    	$('.message-result').addClass('hidden');
	    	$(reply).val(null);
	    });
	    	
	    var send_result = $('#message-info');
	    var send_loader = $('#message-loader');
	    var crfs = $('#csrfToken');
	    
	    $('#message-send').click(function() {
	    	if ($(reply).val().length <= 0) {
	    		$(reply).attr('placeholder', "Please fill in this field");
	    		return ;
	    	}
	    	$('.message-result').removeClass('hidden');
	    	send_loader.attr('src', '/TechTrade/resources/img/checked.gif');
	    	send_result.text("Please wait!");
	    	$.ajax({
	    		type : 'POST',
	    		url : '/TechTrade/account/message/send',
	    		data : {
	    			receiver : sender,
	    			content : $(reply).val(),
	    			[crfs.attr('name')] : crfs.val()
	    		},
	    		success : function(result) {
	    			$(send_result).html(result);
	    			if (result == "Sent!") {
	    				send_loader.attr('src', '/TechTrade/resources/img/checked.gif');
	    			} else {
	    				send_loader.attr('src', '/TechTrade/resources/img/error.gif');
	    			}
	    		}
	    	});
	    });
});