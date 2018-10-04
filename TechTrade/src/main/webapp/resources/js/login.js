$(document).ready(function() {
	$('#sone-btn').click(function() {
		if ($('#username').val().length <= 0) {
			$('#result').text('Couldn\'t find your account');
			return ;
		}
		$.ajax({
			type : 'GET',
			url : '/TechTrade/account/keycheck',
			data : {
				key : $('#username').val(),
			},
			success : function(result) {
				$('#welcome').html("Welcome <span class='text-info'>" + result + "</span>");
				$('.first-stage').hide("fast");
				$('#user-info').html("<div class='avatar m-auto'><img src=\"/TechTrade/account/avatar?key=" + result + "\" /></div>");
				$('.second-stage').show("slow");
			},
			error : function() {
				$('#result').text('Couldn\'t find your account');
			}
		});
	});
	
	$('#notme-btn').click(function() {
		$('#welcome').text("Enter username or email");
		$('.second-stage').hide("fast");
		$('.first-stage').show("slow");
	});
});