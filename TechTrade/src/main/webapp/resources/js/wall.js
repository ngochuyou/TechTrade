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

		$(window).click(function(target) {
	    	if (target.target.id != 'my-dropdown-container') {
	    		$('#my-dropdown-container').hide();
	    	}
	    });
		
		var string = "";
		var search = $('#search');
	    var search_dropdown = $('#my-dropdown-container');
	    
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
	        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/"+this[1]+"'>"+this[0]+"</a>";
	        		});
	        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k="+$(search).val()+"'>See more</a>";
	        		$(search_dropdown).html(string);
	        		$(search_dropdown).show();
	        	},
	        	error : function() {
	        		$(search_dropdown).hide();
	        	}
	        });
	    });

		$('#profile-posts').click(function() {
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
	    
	    $(window).scroll(function() {
	  	   if((($(window).scrollTop() + $(window).height())) == ($(document).height())) {
	 		   if (stopPaging == true) {
	 			   return ;
	 		   }
	  		   $('#loader').fadeIn("fast");
	  	       $.ajax({
	  	    	   type: 'GET',
	  	    	   url: '/TechTrade/account/api/' + $(username).text(),
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
	  	    			   string += "<div class='my-2 py-2 bg-white'>"
	 		+"				<div class='row my-4 px-4'>"
			+"					<div class='col-1 py-3'>"
			+"						<div class='col-1 m-auto text-center'"
			+"							id='vote-holder-" + this.id + "'>" 
			+ voteHTML
			+"						</div></div>"
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
	 		+"				<div class='col-1'></div>"
	 		+"			</div>"
	 		+"			<div class='row pointer my-2 px-4' onclick='window.location.href=\"/TechTrade/post/"+ this.id +"\"'>"
	 		+"				<div class='col custom-control-description text-size-post'>"+ this.description +"</div>"
	 		+"			</div>"
	 		+"			<div class='row px-4'>"
	 		+"				<div class='col'>"
	 		+"					<div class='col-6 float-left border half-left-curve pointer'>"
	 		+"						<h3 class='mt-3 text-center'>"
	 		+"							<i class='fas fa-arrows-alt-v mr-5'></i>"+this.upVote+" Votes"
	 		+"						</h3>"
	 		+"					</div>"
	 		+"					<div class='col-6 float-left border half-right-curve pointer'>"
	 		+"						<h3 class='mt-3 text-center'>"
	 		+"							<i class='fas fa-thumbtack mr-5'></i>Pin"
	 		+"						</h3>"
	 		+"					</div>"
	 		+"				</div>"
	 		+"			</div></div>";
	  	        		});
	  	    		   $(post_content).append(string);
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