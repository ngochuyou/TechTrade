$(document).ready(function() {
	var username = $('#username');
	
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
		
		var string = "";
		var search = $('#search');
	    var search_dropdown = $('#my-dropdown-container');
	    
		$(window).click(function(target) {
	    	if (target.target.id != 'my-dropdown-container') {
	    		search_dropdown.hide();
	    	}
	    });
		
	    $(search).keyup(function() {
	        $.ajax({
	        	type : 'GET',
	        	url : '/TechTrade/post/search',
	        	data : {
	        		keyword :$(search).val(),
	        	},
	        	success : function(list) {
	        		var string = "";
	        		
	        		$.each(list, function() {
	        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/view/" + this[1] + "'>" + this[0] + "</a>";
	        		});
	        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k=" + $(search).val() + "'>See more</a>";
	        		$(search_dropdown).html(string);
	        		$(search_dropdown).show();
	        	},
	        	error : function() {
	        		$(search_dropdown).hide();
	        	}
	        });
	    });
	    
	    var profile_posts = $('#profile-posts');
	    
	    profile_posts.click(function() {
			$('html, body').animate({
				scrollTop: $(this).parent().offset().top -100
			}, 200);
		});
		
		var postId;
		var main = $('.main').first();
		
		$(main).on('click', '.upvote', function() {
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
	    
		$(main).on('click', '.downvote', function() {
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
	    var voteHTML = "";
	    var post_content = $('#post-content');
	    var flag = $('#flag').val();
	    var page_loader = $('#loader');
	    
	    $(window).scroll(function() {
	  	   if((($(window).scrollTop() + $(window).height())) == ($(document).height())) {
	 		   if (stopPaging == true) {
	 			   return ;
	 		   }
	 		  page_loader.fadeIn("fast");
	  	       $.ajax({
	  	    	   type: 'GET',
	  	    	   url: '/TechTrade/account/api/wall/' + $(username).text(),
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
	  	    			   if (this.vote == null) {
	  	    				   voteHTML = " <div class='w-100 pointer upvote' id='upvote-" + this.id + "'>"
										+"		<i class='fas fa-angle-up fa-3x'></i>"
										+"	</div> "
										+"	<div class='w-100 pointer downvote' id='downvote-" + this.id + "'>"
										+"		<i class='fas fa-angle-down fa-3x'></i>"
										+"	</div>";
	  	    			   } else {
	  	    				   if (this.vote.type == true) {
	  	    					   voteHTML = "<p class='w-100'>You voted this post +1</p>";
	  	    				   } else {
	  	    					   voteHTML = "<p class='w-100'>You voted this post -1</p>";
	  	    				   }
	  	    			   }
	  	    			   string += "<div class='my-2 py-2 bg-white'>";
	  	    			   
	  	    			   if (this.status == false) {
	  	    				   string += "<h3 class='text-main'><span class='tags text-light bg-main'>Closed</span></h3>"
	  	    			   }
	  	    			   
	  	    			 string += "<div class='row my-4 px-4'>"
			+"					<div class='col-1 py-3'>"
			+"						<div class='col-1 m-auto text-center'"
			+"							id='vote-holder-" + this.id + "'>" 
			+ voteHTML
			+"						</div></div>"
	 		+"				<div class='col-10'>"
	 		+"					<h3>"
	 		+"						<i class='fas fa-map-marker mr-2'></i>" + this.createBy.ward.name + ","
	 		+"						" + this.createBy.ward.district.name + ','
	 		+"						" + this.createBy.ward.district.city.name
	 		+"					</h3>"
	 		+"					<h2 class='text-truncate font-weight-bold'>" + this.name + "</h2>"
	 		+"					<p>"
	 		+"						By <span class='font-italic text-main'>" + this.createBy.username + "</span>"
	 		+"						on " + formatCurrentDate(date)
	 		+"					</p>"
	 		+"					<div class='line-height-large'>"
	 		+"						<span>Tags <i class='fas fa-hashtag'></i>"
	 		+"						</span> <span class='tags d-inline-block'"
	 		+"							style='background-color : " + this.category.tagColor + "'>" + this.category.name + "</span> "
	 		+ spanTags
	 		+"						</span>"					
	 		+"					</div>"
	 		+"				</div>"
	 		+"				<div class='col-1'></div>"
	 		+"			</div>"
	 		+"			<div class='row pointer my-2 px-4' onclick='window.location.href=\"/TechTrade/post/view/"+ this.id +"\"'>"
	 		+"				<div class='col custom-control-description text-size-post'>"+ this.description +"</div>"
	 		+"			</div>"
	 		+"			<div class='row px-4'>"
	 		+"				<div class='col'>"
							   if (flag == 'true') {
									string += "<div class='col-6 float-left border text-center h-100'>	"					
									+"			<h3 class='mt-3'>"
									+"				<i class='fas fa-arrows-alt-v mr-5'></i>"+this.upVote+" Votes"
									+"			</h3>"
									+"		</div>"
									+"		<div class='col-6 float-left border text-center h-100 pointer' >";
									
									if (this.pin == null) {
										string +="<h3 class='mt-3 pin' id='"+this.id+"' >"
											+"				<i class='fas fa-thumbtack mr-5'></i>Pin"
											+"			</h3>"
											+"		</div>";
									} else {
										string +="<h3 class='mt-3 pin' id='"+this.id+"' style='color: var(--primary)' >"
											+"				<i class='fas fa-thumbtack mr-5'></i>Unpin"
											+"			</h3>"
											+"		</div>";
									}
									
							} else {
									string +="<div class='col-12 float-left border text-center h-100'>"
									+"			<h3 class='mt-3'>"
									+"				<i class='fas fa-arrows-alt-v mr-5'></i>"+this.upVote+" Votes"
									+"			</h3>"
									+"		</div>";
							}
							string += "</div> </div> </div>";	
	  	        		});
	  	    		   $(post_content).append(string);
	  	    		   page_loader.fadeOut("fast");
	 	    		   if (string.length == 0) {
	 	    			   stopPaging = true;
	 	    		   }
	  	    	   }
	  	       });
	  	   };
	  	});
	    
	    var postId = "";
	    var flag;
	    var pin_noti;
	    
	    $(document).on('click','.pin', function() {
	    	id = this.id;
	    	$.ajax({
		    	   type: 'GET',
		    	   url: '/TechTrade/post/pin',
		    	   data : {
		    		   postId : id
		    	   },
		    	   contentType: "application/json; charset=utf-8",
		    	   success: function(result) {
		    		   if(result == "Pinned") {
		    			   $(document).find('#'+id).html("<i class='fas fa-thumbtack mr-5'></i>Unpin");
		    			   $(document).find('#'+id).css({
		    				   'color': 'var(--primary)',
		    			   });
		    		   }
		    		   else {
		    			   if(result == "Unpinned") {
			    			   $(document).find('#'+id).html("<i class='fas fa-thumbtack mr-5'></i>Pin");
			    			   $(document).find('#'+id).css({
			    				   'color': '#555',
			    			   });
		    			   }
		    		   }
		    		   $('.pin-noti').remove();
		    		   $('body').append("<div class='fixed-noti pin-noti' id='pin-noti'><i class='fas fa-thumbtack mr-5'></i>" + result + "</div>");
		    		   pin_noti = $('#pin-noti');
		    		   setTimeout(function() {
		    			   $(pin_noti).remove();
		    		   }, 3000);
		    	   }
	    	});
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
	    
	    var post_del_link = $('a.post-del');
	    var post_noti;
	    var post_id;
	    
	    post_del_link.click(function(event) {
	    	event.preventDefault();
	    	post_id = this.id.match(/\d+/);
	    	post_content.append("<div class='fixed-noti post-noti'><i class='fas fa-trash mr-3'></i>Are you sure you want to delete this post? Action can not be undo. <button class='btn bg-main mx-4' id='post-noti-yes'>Yes!</button><button class='btn btn-outline-main' id='post-noti-no'>Don't do it</button></div>");
	    	post_noti = $('.post-noti');
	    });
	    
	    post_content.on('click', '#post-noti-yes', function(){
	    	window.location.href = '/TechTrade/post/delete/' + post_id; 
	    });
	    
	    post_content.on('click', '#post-noti-no', function(){
	    	post_noti.remove();
	    });
	    
	    $(function () {
	    	$('[data-toggle="tooltip"]').tooltip();
	    });
	    
	    var report_open = $('#report-open');
	    var report_cancel = $('#report_cancel');
	    var report_container = $('#report-container');
	    var report = $('#report');
	    var report_send = $('#report-send');
	    var report_cancel = $('#report-cancel');
	    var report_content = $('#report-content');
	    var report_model = {
	    	reason : "",
	    	content : "",
	    	targetedUser : username.text()
	    }
	    
	    report_open.click(function() {
	    	report_container.show();
	    	setTimeout(function() {
	    		report.addClass("absolute-center-active");
	    	}, 100);
	    });
	    
	    report_cancel.click(function() {
	    	report.removeClass("absolute-center-active");
	    	setTimeout(function() {
	    		report_container.hide();
	    	}, 200);
	    });
	    
	    report_send.click(function() {
	    	report_model.content = report_content.val();
	    	report_model.reason = $('input[name=report-radio]:checked').val();
	    	$.ajax({
	    		type : 'POST',
	    		url : '/TechTrade/admin/report/user',
	    		contentType: "application/json; charset=utf-8",
	    		data : JSON.stringify(report_model),
	    		success : function(result) {
	    			report_cancel.trigger('click');
	    			report_open.html("<h3 class='text-right'>"
	    								+ "<i class='fas fa-flag mr-3'></i>You reported this user on " + formatCurrentDate(new Date()) + ".</h3>");
	    			$('#report-open').off('click');
	    			$('body').append("<div class='fixed-noti report-noti'><i class='fas fa-flag mr-3'></i>" + result + "</div>");
	    			setTimeout(function() {
	    				$('.report-noti').remove();
	    			}, 3000);
	    		}
	    	});
	    });
});