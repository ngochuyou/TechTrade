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
	
    $(window).click(function(target) {
    	if (target.target.id != 'my-dropdown-container') {
    		$('#my-dropdown-container').hide();
    	}
    });
  
    $('#search').keyup(function() {
        $.ajax({
        	type : 'GET',
        	url : '/TechTrade/post/search',
        	data : {
        		keyword : $('#search').val(),
        	},
        	success : function(list) {
        		var string = "";
        		
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
});