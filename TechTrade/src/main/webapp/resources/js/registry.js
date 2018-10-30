$(document)
		.ready(
				function() {
					$('#ward-select option').first().attr('selected',
							'selected');
					
					var selected;
					var districtSb;
					var districtSbHTML;
					var wardSb;
					var wardSbHTML;
					
					$('#city-select')
							.change(
									function() {
										selected = $('#city-select option:selected');

										$
												.ajax({
													type : 'GET',
													url : '/TechTrade/location/district/'
															+ selected
																	.attr("value"),
													contentType : 'application/json; charset=utf-8',
													success : function(list) {
														districtSb = $('#district-select');

														districtSb.empty();

														districtSbHTML = districtSb
																.html();

														$
																.each(
																		list,
																		function() {
																			districtSbHTML += '<option value="'
																					+ this.id
																					+ '">'
																					+ this.name
																					+ '</option>';
																		});

														districtSb
																.html(districtSbHTML);

														$(
																'#district-select option')
																.first()
																.attr(
																		'selected',
																		'selected');

														selected = $('#district-select option:selected');

														$
																.ajax({
																	type : 'GET',
																	url : '/TechTrade/location/ward/'
																			+ selected
																					.attr("value"),
																	contentType : 'application/json; charset=utf-8',
																	success : function(
																			list) {
																		wardSb = $('#ward-select');

																		wardSb
																				.empty();

																		wardSbHTML = wardSb;

																		$
																				.each(
																						list,
																						function() {
																							wardSbHTML += '<option value="'
																									+ this.id
																									+ '">'
																									+ this.name
																									+ '</option>';
																						});

																		wardSb
																				.html(wardSbHTML);
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

					$('#district-select')
							.change(
									function() {
										var selected = $('#district-select option:selected');

										$
												.ajax({
													type : 'GET',
													url : '/TechTrade/location/ward/'
															+ selected
																	.attr("value"),
													contentType : 'application/json; charset=utf-8',
													success : function(list) {
														wardSb = $('#ward-select');

														wardSb.empty();

														wardSbHTML = wardSb
																.html();

														$
																.each(
																		list,
																		function() {
																			wardSbHTML += '<option value="'
																					+ this.id
																					+ '">'
																					+ this.name
																					+ '</option>';
																		});

														wardSb.html(wardSbHTML);
													},
													error : function() {
														alert('error');
													}
												});
									});

					$('#email').keyup(function() {
						var emailVar = $('#email').val();

						$.ajax({

							type : 'GET',
							url : '/TechTrade/account/email',
							data : {
								email : emailVar
							},
							success : function(result) {
								$('#email-check-result').text(result);
							},

						});
					});
					
					var username = $('#username');
					var username_result = $('#username-check-result');
					var username_key;
					
					username.keyup(function() {
						username_key = username.val();
						
						$.ajax({
							type : 'GET',
							url : '/TechTrade/account/username',
							data : {
								username : username_key
							},
							success : function(result) {
								username_result.text(result);
							},
						});
					});

					var phone = $('#phone');
					var phone_key;
					var phone_result = $('#phone-check-result');
					
					phone.keyup(function() {
						phone_key = phone.val();

						$.ajax({
							type : 'GET',
							url : '/TechTrade/account/phone',
							data : {
								phone : phoneVar
							},
							success : function(result) {
								phone_result.text(result);
							},
						});
					});

					$('#reg-form')
							.validate(
									{
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
											gender : {
												required : true,
											}
										},
										messages : {
											phone : {
												digits : "Please enter a valid phonenumber"
											},
											confirmPassword : {
												equalTo : "Please enter the same password that you entered above"
											}
										}
									});
					var code;
					var email = $('#email');
					
					$('#submit-btn').click(function() {
						if ($('#reg-form').valid() == true) {
							$.ajax({
								type : 'GET',
								url : '/TechTrade/account/verify',
								data : {
									email : email.val()
								},
								success : function(verifyCode) {
									code = verifyCode;
								}
							});
							$('#code-verify').show("fast");
						}
					});

					$('#verify-btn')
							.click(
									function() {
										if ($('#code').val() == code) {
											$('#reg-form').submit();
										} else {
											$('#code-result')
													.text(
															"Invalid verify code, please try again.");
										}
									});
				});