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
	    
	    var main = $('.main');
	    var pin_unpin = $('.unpin');
	    var pin_noti;
	    var post_id;
	    
	    pin_unpin.click(function() {
	    	post_id = this.id.match(/\d+/).toString();
	    	console.log(post_id);
	    	$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/post/pin',
	    		data : {
	    			postId : post_id
	    		},
	    		success : function(result) {
	    			if(result == "Unpinned") {
	    				$('#' + post_id).remove();
	    			}
	    			$('.pin-noti').remove();
	    			main.append("<div class='fixed-noti pin-noti' id='pin-noti'><i class='fas fa-thumbtack mr-3'></i>" + result + "</div>");
	    			pin_noti = $('#pin-noti');
	    			setTimeout(function() {
	    				$(pin_noti).remove();
	    			}, 3000);
	    		}
	    	})
	    });
	    
	    var pin_post = $('.post');
	    
	    pin_post.on('mouseenter', function() {
	    	$(this).addClass('bg-noti');
	    });
	    
	    pin_post.on('mouseleave', function() {
	    	$(this).removeClass('bg-noti');
	    });
});