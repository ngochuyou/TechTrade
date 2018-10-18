$(document).ready(function() {
	var username;
	
	$('#sone-btn').click(function() {
		$.ajax({
			type : 'GET',
			url : '/TechTrade/account/keycheck',
			data : {
				key : "" + $('#username').val(),
			},
			success : function(result) {
				if (!jQuery.isEmptyObject(result)) {
					username = result.username;
					
					$('#welcome').html("Welcome <span class='text-info'>" + username + "</span>");
					$('.first-stage').hide("fast");
					$('#user-info').html("<div class='avatar m-auto'><img src=\"/TechTrade/account/avatar/" + result.avatar + "\" /></div>");
					$('.second-stage').show("slow");
				} else {
					$('#result').text('Couldn\'t find your account');
				}
			}
		});
	});
	
	$('#notme-btn').click(function() {
		$('#welcome').text("Enter username or email");
		$('.second-stage').hide("fast");
		$('.first-stage').show("slow");
		$('#result').text(null);
	});
	
	$('#forgot').click(function() {
		window.location.replace("/TechTrade/account/password/forgot?username=" + username);
	});
});