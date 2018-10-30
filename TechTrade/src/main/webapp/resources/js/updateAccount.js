$(document).ready(function() {
	var currentPage = 0;
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
	
	var search = $('#search');
    var search_dropdown = $('#my-dropdown-container');
    
    $(window).click(function(target) {
    	if (target.target.id != 'my-dropdown-container') {
    		search_dropdown.hide();
    	}
    });
  
    search.keyup(function() {
        $.ajax({
        	type : 'GET',
        	url : '/TechTrade/post/search',
        	data : {
        		keyword : search.val(),
        	},
        	success : function(list) {
        		var string = "";
        		
        		$.each(list, function() {
        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/view/" + this[1] + "'>" + this[0] + "</a>";
        		});
        		
        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k=" + search.val() + "'>See more</a>";
        		search_dropdown.html(string);
        		search_dropdown.show();
        	},
        	error : function() {
        		search_dropdown.hide();
        	}
        });
    });
});