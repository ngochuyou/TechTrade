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
    
    $('.comments-btn').click(function() {
        var index = $('.comments-btn').index(this);
        var target = $('.comments-container:eq(' + index +')');

        $(target).show("fast", function() {
            window.scrollTo(0, target.offset().top - 60);
        });
    });

    $('#search').keydown(function() {
        $('#my-dropdown-container').show();
    });
});