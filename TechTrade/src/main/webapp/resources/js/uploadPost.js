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