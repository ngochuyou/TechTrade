$(document).ready(function() {
	var code = $('#code').text();
	
	$('#code').remove();
	
	var verified = false;

	$('#code_input').keyup(function() {
		if ($('#code_input').val() == code) {
			verified = true;
			$('#code_input').hide("fast");
			$('#form').show("slow");
		}
	});
	
	$('#form').validate({
		rules : {
			newPassword : {
				required : true,
				minlength : 8
			},
			rePassword : {
				equalTo : newPassword,
			},
		},
		messages : {
			rePassword : "Please enter the password above"
		}
	});
	
	$('#submit-btn').click(function(){
		if (verified == true) {
			$('#form').submit();
		} else {
			$('#warning').text("Please enter your reset code before changing the password");
		}
	});
});