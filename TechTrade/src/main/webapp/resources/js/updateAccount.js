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
	
	var search = $('#search');
    var search_dropdown = $('#my-dropdown-container');
    
    $(window).click(function(target) {
    	if (target.target.id != 'my-dropdown-container') {
    		search_dropdown.hide();
    	}
    });
    
    $('.wallpaper').click(function(){
    	
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
    
    var selected;
    var districtSb;
    var districtSbHTML;
    
	$('#city-select').change(function() {
        selected = $('#city-select option:selected');
		$.ajax({
            type: 'GET',
            url: '/TechTrade/location/district/' + selected.attr("value"),
            contentType: 'application/json; charset=utf-8',
            success: function(list) {
	            districtSb = $('#district-select');
	            districtSb.empty();
	            districtSbHTML = districtSb.html();
	            $.each(list,function() {
	                districtSbHTML += '<option value="' + this.id + '">' + this.name +'</option>';
	            });
	            districtSb.html(districtSbHTML);
	            $('#district-select option').first().attr('selected','selected');
	            selected = $('#district-select option:selected');
	            $.ajax({
	                    type: 'GET',
	                    url: '/TechTrade/location/ward/' +selected.attr("value"),
	                    contentType: 'application/json; charset=utf-8',
	                    success: function(list) {
	                        var wardSb = $('#ward-select');
	                        wardSb.empty();
	                        var wardSbHTML = wardSb;
	                        $.each(list,function() {
	                            wardSbHTML += '<option value="' +this.id +'">' +this.name +'</option>';
	                        });
	                        wardSb.html(wardSbHTML);
	                    },
	                    error: function() {
	                        alert('error');
	                    }
	            });
            },
            error: function() {
                alert('error');
            }
        });
	});
	
	var selected;
	var wardSb;
	var wardSbHTML;
	
	$('#district-select').change(function() {
        selected = $('#district-select option:selected');
        $.ajax({
            type: 'GET',
            url: '/TechTrade/location/ward/' +selected.attr("value"),
            contentType: 'application/json; charset=utf-8',
            success: function(list) {
                wardSb = $('#ward-select');
                wardSb.empty();
                wardSbHTML = wardSb.html();
                $.each(list,function() {
                    wardSbHTML += '<option value="' + this.id + '">' + this.name + '</option>';
                });
                wardSb.html(wardSbHTML);
            },
            error: function() {
                alert('error');
            }
        });
    });
	
	var avatarFile = $('#avatarFile'); 
	var avatarFilePreview = $('.avatarPreview');
	
	var wallpaperFile = $('#wallpaperFile');
	var wallpaperFilePreview = $('.wallpaperPreview');
	var reader = null;
	
	function readURLAvatar(input){
		avatarFilePreview.empty();
		if(input.files){
			reader = new FileReader;
			reader.onload = function(event){
				avatarFilePreview.attr('src', event.target.result);
			};
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	function readURLWallpaper(input){
		wallpaperFilePreview.empty();
		if(input.files){
			reader = new FileReader;
			reader.onload = function(event){
				wallpaperFilePreview.attr('src', event.target.result);
			};
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	avatarFile.change(function(){
		readURLAvatar(this);
	});
	
	wallpaperFile.change(function(){
		readURLWallpaper(this);
	});
	
	var form = $('#form');
	var form_submit = $('#form-submit');
	var old_password = $('[name="password"]');
	var new_password = $('[name="newPassword"]');
	var new_password_confirm = $('#repassword');
	var phone = $('#phone');
	var old_password_value;
	var new_password_value;
	var new_password_confirm_value;
	var invalid_eles = $('.invalid');
	var notmatch = $('.not-match');
	var user_password;
	var incorrect_oldpass = $('#oldpassword-notmatch');
	var invalid_phone = $('#invalid-phone');
	
	form_submit.click(function() {
		old_password_value = old_password.val();
		if (old_password_value.length > 0) {
			$.ajax({
				type : 'GET',
				url : '/TechTrade/account/password',
				data : {
					raw : old_password_value 
				},
				success : function(result) {
					if (result == true) {
						incorrect_oldpass.addClass('hidden');
						new_password_value = new_password.val();
						new_password_confirm_value = new_password_confirm.val();
						
						if (new_password_value.length < 8 || new_password_confirm_value.length < 8) {
							invalid_eles.removeClass('hidden');
							
							return false;
						}
						
						if (new_password_value !== new_password_confirm_value) {
							notmatch.removeClass('hidden');
						} 
						
						if(phone.val().match(/^\d+$/)) {
							form.submit();
						} else {
							invalid_phone.removeClass('hidden');
						}
					} else {
						incorrect_oldpass.removeClass('hidden');
					}
				}	
			});
		} else {
			if(phone.val().match(/^\d+$/)) {
				form.submit();
			} else {
				invalid_phone.removeClass('hidden');
			}
		}
	});
});