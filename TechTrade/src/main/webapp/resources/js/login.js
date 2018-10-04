$(document).ready(function() {
	var username;
	
	$('#sone-btn').click(function() {
		$.ajax({
			type : 'GET',
			url : '/TechTrade/account/keycheck',
			data : {
				key : $('#username').val(),
			},
			success : function(result) {
				if (result.length <= 0) {
					$('#result').text('Couldn\'t find your account');
				} else {
					username = result;
					$('#welcome').html("Welcome <span class='text-info'>" + result + "</span>");
					$('.first-stage').hide("fast");
					$('#user-info').html("<div class='avatar m-auto'><img src=\"/TechTrade/account/avatar?username=" + result + "\" /></div>");
					$('.second-stage').show("slow");
				}
			}
		});
	});
	
	$('#notme-btn').click(function() {
		$('#welcome').text("Enter username or email");
		$('.second-stage').hide("fast");
		$('.first-stage').show("slow");
		$('#result').hide("fast");
	});
	
	$('#forgot').click(function() {
		window.location.replace("/TechTrade/account/password/forgot?username=" + username);
	});
});