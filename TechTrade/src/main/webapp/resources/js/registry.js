$(document).ready(function() {
	$('#ward-select option').first().attr('selected','selected');
	
	$('#city-select').change(function() {
		var selected = $('#city-select option:selected');
		
		$.ajax({
			type : 'GET',
			url : '/TechTrade/location/district/'+selected.attr("value"),
			contentType : 'application/json; charset=utf-8',
			success : function(list) {
				var districtSb = $('#district-select');
				
				districtSb.empty();
				
				var districtSbHTML = districtSb.html();
				
				$.each(list, function() {
					districtSbHTML += '<option value="' + this.id + '">' + this.name + '</option>';
				});
				
				districtSb.html(districtSbHTML);
				
				$('#district-select option').first().attr('selected','selected');
				
				selected = $('#district-select option:selected');
				
				$.ajax({
					type : 'GET',
					url : '/TechTrade/location/ward/'+selected.attr("value"),
					contentType : 'application/json; charset=utf-8',
					success : function(list) {
						var wardSb = $('#ward-select');
						
						wardSb.empty();
						
						var wardSbHTML = wardSb;
						
						$.each(list, function() {
							wardSbHTML += '<option value="' + this.id + '">' + this.name + '</option>';
						});
						
						wardSb.html(wardSbHTML);
					},
					error : function() {
						alert('error');
					}
				});
			},
			error : function() {
				alert('error');
			}
		});
	});
	
	$('#district-select').change(function() {
		var selected = $('#district-select option:selected');
		
		$.ajax({
			type : 'GET',
			url : '/TechTrade/location/ward/'+selected.attr("value"),
			contentType : 'application/json; charset=utf-8',
			success : function(list) {
				var wardSb = $('#ward-select');
				
				wardSb.empty();
				
				var wardSbHTML = wardSb.html();
				
				$.each(list, function() {
					wardSbHTML += '<option value="' + this.id + '">' + this.name + '</option>';
				});
				
				wardSb.html(wardSbHTML);
			},
			error : function() {
				alert('error');
			}
		});
	});
	
	$('#reg-form').validate({
		rules : {
			email : {
				required : true,
				email : true,
			},
			username : {
				required : true,
				minlength : 8
			},
			password : {
				required : true,
				minlength : 8
			},
			confirmPassword : {
				required : true,
				equalTo : password
			},
			phone : {
				required : true,
				digits : true
			},
		},
		messages : {
			phone : {
				digits : "Please enter a valid phonenumber"
			}
		}
	});
});