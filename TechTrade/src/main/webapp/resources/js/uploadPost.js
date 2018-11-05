$(document).ready(function() {
	ClassicEditor
	.create( document.querySelector( '#form-description' ) )
	.then( editor => {
		console.log( editor );
	} )
	.catch( error => {
		console.error( error );
	} );
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
		
		var string = "";
		var search = $('#search');
	    var search_dropdown = $('#my-dropdown-container');
	    
	    $(search).keyup(function() {
	        $.ajax({
	        	type : 'GET',
	        	url : '/TechTrade/post/search',
	        	data : {
	        		keyword :$(search).val(),
	        	},
	        	success : function(list) {
	        		var string = "";
	        		
	        		$.each(list, function() {
	        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/view/" + this[1] + "'>" + this[0] + "</a>";
	        		});
	        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k="+$(search).val()+"'>See more</a>";
	        		$(search_dropdown).html(string);
	        		$(search_dropdown).show();
	        	},
	        	error : function() {
	        		$(search_dropdown).hide();
	        	}
	        });
	    });
	    
	    var monthNames = [
			"Jan", "Feb", "Mar",
			"Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct",
			"Nov", "Dec"
	    ];
	    
	    function formatCurrentDate(date) {
	    	
	    	return monthNames[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear();
	    }
	    
	    var overTime = "AM";
	    var hour;
	    var minute; 
	    var dateObject;
	    
	    function formatCurrentDateTime(date) {
	    	dateObject = new Date(date);
	    	overTime = "AM";
	    	hour = dateObject.getHours();
	    	
	    	if (hour >= 13) {
	    		hour -= 12;
	    		overTime = "PM";
	    	}
	    	
	    	if (hour < 10) {
				hour = "0" + hour;
			}
	    	
	    	minute = dateObject.getMinutes();
	    	
	    	if (minute < 10) {
	    		minute = "0" + minute;
	    	}
	    	
			return monthNames[dateObject.getMonth()] + ' ' + dateObject.getDate() + ', ' + dateObject.getFullYear() + ' ' + hour + ':' + minute + " " + overTime;
	    }
	    
	    var slider = $('#form-container');
	    var slider_left = 0; 
	    var slider_left_btn = $('.slider-left');
	    var slider_right_btn = $('.slider-right');
	    var slider_progress = $('#post-progress');
	    var slider_progress_width = 25;
	    
	    $(slider_right_btn).click(function() {
	    	$(slider).css({
	    		'left' : slider_left - 100 + '%'
	    	});
	    	slider_progress_width += 25;
	    	$(slider_progress).css({
	    		'width' : slider_progress_width + "%"
	    	});
	    	slider_left -= 100;
	    });
	    
	    $(slider_left_btn).click(function() {
	    	$(slider).css({
	    		'left' : slider_left + 100  + '%'
	    	});
	    	slider_progress_width -= 25;
	    	$(slider_progress).css({
	    		'width' : slider_progress_width + "%"
	    	});
	    	slider_left += 100;
	    });
	    
	    var tagInput;
	    var finalTags = "";
	    var hashtagsInput = $('#hashtags-input');
	    var hashtagId;
	    var form_hashtags = $('#form-hashtags');
	    var tagNumber = 0;
	    var hashtags_add = $('#hashtags-add'); 
	    var stage_hashtasg = $('.stage-hashtags');
	    
    	console.log(finalTags);
	    $(hashtags_add).click(function() {
	    	tagInput = $(hashtagsInput).val();
	    	$($.parseHTML("<span class='color-main tags m-2 d-inline-block' id='hashtags"+tagNumber+"'>")).html(tagInput + "<span class='hashtags-del' id='hashtags-del"+tagNumber+"'><i class='fas fa-times-circle mx-2'></i></span>").appendTo("#hashtags-container");
	    	tagNumber++;
	    	$(hashtagsInput).val('#');
	    	finalTags += tagInput; 
	    	console.log(finalTags);
	    	$(form_hashtags).val(finalTags);
	    });
	    
	    var targetHashtag;
	    
	    $(stage_hashtasg).on('click', '.hashtags-del', function() {
	    	hashtagId = this.id.match(/\d+/);
	    	targetHashtag = $('#hashtags'+hashtagId);
	    	finalTags = finalTags.replace(targetHashtag.text().trim(), "");
	    	$(targetHashtag).remove();
	    	$(form_hashtags).val(finalTags);
	    	console.log(finalTags);
	    });
	    
	    var hashtags_rate_result = $('#hashtags-rate-result');
	    var hashtags_rate_message = $('#hashtags-rate-message');
	    var hashtags_input_value;
	    
	    $(hashtagsInput).keyup(function() {
	    	hashtags_input_value = $(hashtagsInput).val();
	    	if (hashtags_input_value.length <= 1) {
	    		return ;
	    	}
	    	$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/post/hashtags/rate',
	    		data : {
	    			keyword : hashtags_input_value
	    		},
	    		success : function(result) {
	    			$(hashtags_rate_result).css({
	    				'width' : result + '%'
	    			});
	    			$(hashtags_rate_message).html(result + "% of the posts in community has <span class='font-weight-bold font-italic'>" + hashtags_input_value + "</span> Tag.");
	    		}
	    	});
	    });
	    
	    var count = 0;
		var temp = "";
		var reader = null;
		var filesAmount;
		var files_cancel = $('#files-cancel');
		
	    $(files_cancel).click(function() {
	    	$('.preview').empty();
	    	$("#files").val(null);
	    });
	    
	    var name_message = $('#name-forgot');
	    var cate_message = $('#categoy-forgot');
	    var des_message = $('#description-forgot');
	    var post_form = $('#post-form');
	    var post_name = $('#name');
	    var post_cateId = $('#categoryId');
	    var post_des = $('#form-description');
	    
	    $(post_form).submit(function(event) {
	    	if (post_name.val().length == 0) {
	    		handleInvalid(0, 25, name_message);
	    		
	    		return false;
	    	}
	    	
	    	if (post_cateId.val() == null) {
	    		handleInvalid(-100, 50, cate_message);
	    		
	    		return false;
	    	}
	    	
	    	if (post_des.val().length == 0) {
	    		handleInvalid(-200, 75, des_message);
	    		
	    		return false;
	    	}
	    	
	    	return true;
	    });
	    
	    function handleInvalid(position, progress, target_message) {
	    	$(slider).css({
	    		'left' : position + '%'
	    	});
	    	$(slider_progress).css({
	    		'width' : progress + "%"
	    	});
	    	slider_left = position;
	    	slider_progress_width = progress; 
	    	$(target_message.show());
	    }
	    
	    var drop_zone = $("#dropzone");
	    $(drop_zone).on("dragover", function(event) {
    		event.preventDefault();  
    		event.stopPropagation();
		});
	    
 		$(drop_zone).on("dragleave", function(event) {
    		event.preventDefault();  
    		event.stopPropagation();
		});

 		var files = $("#files");
 		
 		$(drop_zone).on("drop", function(event) {
			event.preventDefault();  
    		event.stopPropagation();
    		files.prop('files', event.originalEvent.dataTransfer.files);
		});
		
 		var preview = $('.preview');
 		
		function readURL(input, place) {
			$(preview).empty();
            if (input.files) {
            	filesAmount = input.files.length;
            	console.log(filesAmount);
                for (i = 0; i < filesAmount; i++) {
                	reader = new FileReader();
                     reader.onload = function(event) {
                        $($.parseHTML("<img class='float-left w-25 hpx-350 mx-5 my-3'>")).attr('src', event.target.result).appendTo(place);
                    }
                     reader.readAsDataURL(input.files[i]);
                	console.log(input.files[i]);
                }
            }
		};
		
    $(files).change(function() {
    	readURL(this, "div.preview");
    });    
});