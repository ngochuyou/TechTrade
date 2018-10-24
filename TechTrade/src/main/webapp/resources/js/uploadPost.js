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

		$(window).click(function(target) {
	    	if (target.target.id != 'my-dropdown-container') {
	    		$('#my-dropdown-container').hide();
	    	}
	    });
		
		var string = "";
		
	    $('#search').keyup(function() {
	        $.ajax({
	        	type : 'GET',
	        	url : '/TechTrade/post/search',
	        	data : {
	        		keyword : $('#search').val(),
	        	},
	        	success : function(list) {
	        		string = "";
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
	    
	    function formatCurrentDate(date) {
	    	
	    	return monthNames[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear();
	    }
	    
	    var overTime = "AM";
	    var hour;
	    var dateObject;
	    
	    function formatCurrentDateTime(date) {
	    	dateObject = new Date(date);
	    	overTime = "AM";
	    	hour = dateObject.getHours();
	    	
	    	if (hour >= 13) {
	    		hour -= 12;
	    		overTime = "PM";
	    	}
	    	
			return monthNames[dateObject.getMonth()] + ' ' + dateObject.getDate() + ', ' + dateObject.getFullYear() + ':' + hour + ':' + dateObject.getMinutes() + ':' + dateObject.getSeconds() + ' ' + overTime;
	    }
	    
	    var unread_qty = parseInt($('.unread-qty').first().text());
	    var inbox = $('#inbox');
	    var currentInboxPage = 0;
	    var messagesContainer = $('#messages-container');
	    var messagesContainerHTML = $(messagesContainer).html();
	    var stopPagingMessages = false;
	    
	    $('.inbox-open').click(function() {
	    	$(inbox).toggleClass('fixed-fullscreen-active');
	    });
	    
	    $('#inbox-showmore').click(function() {
	    	if (stopPagingMessages == true) {
	    		return ;
	    	}
	    	
	    	$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message',
	    		contentType: "application/json; charset=utf-8",
	    		data : {
	    			page : currentInboxPage + 1
	    		},
	    		success : function(result) {
	    			if (result.length <= 0) {
	    				stopPagingMessages = true; 
	    				$('#inbox-showmore').text("Nothing left.");
	    				return ;
	    			}
	    			currentInboxPage += 1;
	    			$.each(result, function() {
	    				messagesContainerHTML += "<div class='row message pointer'>"
	    								+ "<input type='hidden' value='" + this.id + "' class='message-id'>"
	    								+ "<span class='message-location hidden'>" + this.sender.ward.name + ","
	    								+ this.sender.ward.district.name + ","
	    								+ this.sender.ward.district.city.name + "</span>"
	    								+ "<div class='col-2 border-right'>"
	    								+ "<img src='/TechTrade/account/avatar?username="+ this.sender.username + "' class='m-auto avatar-medium'>"
	    								+ "</div>"
	    								+ "<div class='col-9'>"
	    								+ "<h3 class='text-truncate message-username'>" + this.sender.username + "</h3>"
	    								+ "<p class='text-truncate mb-1 message-content'>" + this.content + "</p>"
	    								+ "<p class='text-truncate text-small message-sentAt'>"
	    								+ formatCurrentDateTime(this.sentAt) + "</p>"
	    								+ "</div>"
	    								+ "<div class='col-1 p-0'>"
	    								+ "<div class='icon icon-small m-auto message-delete' id='message-delete-" + this.id + "'>"
	    								+ "<i class='fas fa-trash text-right hidden'></i>"
	    								+ "</div>"
	    								+ "<div class='icon icon-small m-auto'>"
	    								+ "<i class='fas fa-reply text-right hidden'></i>"
	    								+ "</div></div></div>";
	    			});
	    			$(messagesContainer).html(messagesContainerHTML);
	    		}
	    	});
	    });
	    
	    $('.inbox-main').on('mouseenter', '.message', function() {
	    	$(this).find('svg').removeClass('hidden');
	    });
	    
	    $('.inbox-main').on('mouseleave', '.message', function() {
	    	$(this).find('svg').addClass('hidden');
	    });
	    
	    var delete_noti = $('#delete-noti');
	    var message_id;
	    var message_root;
	    var inbox_main = $('.inbox-main');
	    var inbox_composer = $('.inbox-composer');
	    
	    $(inbox_main).on('click', '.message-delete', function() {
	    	$('.delete-noti').remove();
	    	message_root = $(this).parents().eq(1);
	    	message_id = this.id.match(/\d+/).toString();
	    	$(inbox_main).append("<div class='fixed-noti delete-noti' id='delete-noti'>Are you sure you want to delete this message from <span class='font-weight-bold'>"+ $(message_root).find('.message-username').text() +"</span>? Action can not be undo. <button class='btn bg-main mx-4' id='noti-yes'>Yes!</button><button class='btn btn-outline-main' id='noti-no'>Don't do it</button></div>");
	    	delete_noti = $('#delete-noti');
	    });
	    
    	$(inbox_main).on('click', '#noti-yes', function() {
    		$(delete_noti).html("<img src='/TechTrade/resources/img/loading.gif' class='mr-3' style='height : 50px;'> Please wait...");
    		$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message/remove',
	    		data : {
	    			messageId : message_id
	    		},
	    		success : function(result) {
	    			$(delete_noti).text(result);
	    		}
	    	});
    		if ($(message_root).hasClass('bg-noti')) {
    			$('.unread-qty').text(--unread_qty);
    	
    			$(message_root).remove();
    		}
    		$(message_root).remove();
    		setTimeout(function() {
    			  $(delete_noti).remove();
    		}, 3000);
    	});
    	
    	$(inbox_main).on('click', '#noti-no', function() {
    		$('#delete-noti').remove();
	    	return ;
    	});
    	
	    var sender; 
	    var reply = $('#reply');
	    var inbox_back = $('#inbox-back');
	    
	    $(inbox).on('click', '.message .col-9', function() {
	    	sender = $(this).find('.message-username').text();
	    	message_root = $(this).parent();
	    	message_id = $(message_root).find('.message-id').val();
	    	if ($(message_root).hasClass('bg-noti')) {
	    		console.log('marking');
	    		$.ajax({
		    		type : 'GET',
		    		url : '/TechTrade/account/message/mark',
		    		data : {
		    			messageId : message_id
		    		},
		    		success : function(result) {
		    			if (result == "Marked as unread.") {
		    				$('.unread-qty').text(++unread_qty);
		    				$('.message-id[value="' + message_id + '"]').parent().addClass('bg-noti');
		    			} else {
		    				$('.unread-qty').text(--unread_qty);
		    				$('.message-id[value="' + message_id + '"]').parent().removeClass('bg-noti');
		    			}
		    		}
		    	});
	    	}
	    	
	    	$('#composer-avatar').attr('src', '/TechTrade/account/avatar?username=' + sender);
	    	$('#composer-username').text(sender);
	    	$('#composer-sentAt').text($(this).find('.message-sentAt').text());
	    	$('#composer-location').text($(this).find('.message-location').text());
	    	$('#composer-content').text($(this).find('.message-content').text());
	    	$(inbox_main).fadeOut("fast");
	    	$(inbox_composer).fadeIn("fast");
	    });
	    
	    $('.composer-delete').click(function() {
	    	$('.delete-noti').remove();
	    	$(inbox_composer).append("<div class='fixed-noti delete-noti' id='delete-noti'>Are you sure you want to delete this message? Action can not be undo. <button class='btn bg-main mx-4' id='noti-yes'>Yes!</button><button class='btn btn-outline-main' id='noti-no'>Don't do it</button></div>");
	    	delete_noti = $('#delete-noti');
	    });
	    
	    $('.composer-mark').click(function() {
	    	$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message/mark',
	    		data : {
	    			messageId : message_id
	    		},
	    		success : function(result) {
	    			$(inbox_composer).append("<div class='fixed-noti mark-noti'>" + result + "</div>");
	    			setTimeout(function() {
	    				$('.mark-noti').remove();
	    			}, 3000);
	    			
	    			if (result == "Marked as unread.") {
	    				$('.unread-qty').text(++unread_qty);	
	    				$('.message-id[value="' + message_id + '"]').parent().addClass('bg-noti');
	    			} else {
	    				$('.unread-qty').text(--unread_qty);
	    				$('.message-id[value="' + message_id + '"]').parent().removeClass('bg-noti');
	    			}
	    		}
	    	});
	    });
	    
	    $(inbox_composer).on('click', '#noti-yes', function() {
    		$(delete_noti).html("<img src='/TechTrade/resources/img/loading.gif' class='mr-3' style='height : 50px;'> Please wait...");
    		$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message/remove',
	    		data : {
	    			messageId : message_id
	    		},
	    		success : function(result) {
	    			$(delete_noti).text(result);
	    		}
	    	});
    		message_root = $('#message-delete-' + message_id).parents().eq(1);
    		
    		if ($(message_root).hasClass('bg-noti')) {
    			$('.unread-qty').text(--unread_qty);
    		}
    		
			$(message_root).remove();
    		setTimeout(function() {
    			  $(delete_noti).remove();
    		}, 3000);
    		$(inbox_back).click();
    	});
    	
    	$(inbox_composer).on('click', '#noti-no', function() {
    		$('#delete-noti').remove();
	    	return ;
    	});
    	
	    $(inbox_back).click(function() {
	    	$('.inbox-main').fadeIn("fast");
	    	$('.inbox-composer').fadeOut("fast");
	    	$('.message-result').addClass('hidden');
	    	$(reply).val(null);
	    });	
	    
	    var outmessages_container = $('#outmessages-container');
	    var outmessages_containerHTML = $(outmessages_container).html(); 
	    var currentOutboxPage = 0;
	    
	    $('.inbox-main-open').click(function() {
	    	$('.absolute:not(.inbox-main)').fadeOut("fast");
	    	$('.inbox-main').fadeIn("fast");
	    });
	    
	    var send_result = $('#message-info');
	    var send_loader = $('#message-loader');
	    var crfs = $('#csrfToken');
	    
	    $('#message-send').click(function() {
	    	if ($(reply).val().length <= 0) {
	    		$(reply).attr('placeholder', "Please fill in this field");
	    		return ;
	    	}
	    	$('.message-result').removeClass('hidden');
	    	send_loader.attr('src', '/TechTrade/resources/img/checked.gif');
	    	send_result.text("Please wait!");
	    	$.ajax({
	    		type : 'POST',
	    		url : '/TechTrade/account/message/send',
	    		data : {
	    			receiver : sender,
	    			content : $(reply).val(),
	    			[crfs.attr('name')] : crfs.val()
	    		},
	    		success : function(result) {
	    			$(send_result).html(result);
	    			if (result == "Sent!") {
	    				send_loader.attr('src', '/TechTrade/resources/img/checked.gif');
	    			} else {
	    				send_loader.attr('src', '/TechTrade/resources/img/error.gif');
	    			}
	    		}
	    	});
	    });
	    
	    var slider = $('#form-container');
	    var slider_left = 0; 
	    var slider_left_btn = $('#slider-left');
	    var slider_right_btn = $('#slider-right');
	    var slider_progress = $('#post-progress');
	    var slider_progress_width = 25;
	    
	    $('.slider-right').click(function() {
	    	$(slider).css({
	    		'left' : slider_left - 100 + '%'
	    	});
	    	slider_progress_width += 25;
	    	$(slider_progress).css({
	    		'width' : slider_progress_width + "%"
	    	});
	    	slider_left -= 100;
	    });
	    
	    $('.slider-left').click(function() {
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
	    
	    $('#hashtags-add').click(function() {
	    	tagInput = $(hashtagsInput).val();
	    	$($.parseHTML("<span class='color-main tags m-2 d-inline-block' id='hashtags"+tagNumber+"'>")).html(tagInput + "<span class='hashtags-del' id='hashtags-del"+tagNumber+"'><i class='fas fa-times-circle mx-2'></i></span>").appendTo("#hashtags-container");
	    	tagNumber++;
	    	$(hashtagsInput).val('#');
	    	finalTags += tagInput;  
	    	$(form_hashtags).val(finalTags);
	    });
	    
	    var targetHashtag;
	    
	    $('.stage-hashtags').on('click', '.hashtags-del', function() {
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
		
	    $('#files-cancel').click(function() {
	    	$('.preview').empty();
	    	$("#files").val(null);
	    });
	    
	    var validated = true;
	    var name_message = $('#name-forgot');
	    var cate_message = $('#categoy-forgot');
	    var des_message = $('#description-forgot');
	    
	    $('#post-form').submit(function(event) {
	    	if ($('#name').val().length == 0) {
	    		validated = false;
	    		handleInvalid(0, 25, name_message);
	    		
	    		return false;
	    	}
	    	
	    	if ($('#categoryId').val() == null) {
	    		validated = false;
	    		handleInvalid(-100, 50, cate_message);
	    		
	    		return false;
	    	}
	    	
	    	if ($('#form-description').val().length == 0) {
	    		validated = false;
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
	    
	    $("#dropzone").on("dragover", function(event) {
    		event.preventDefault();  
    		event.stopPropagation();
    		$(this).addClass('dragging');
		});

		$("#dropzone").on("dragleave", function(event) {
    		event.preventDefault();  
    		event.stopPropagation();
		    $(this).removeClass('dragging');
		});

		$('#dropzone').on("drop", function(event) {
			event.preventDefault();  
    		event.stopPropagation();
    		$('#files').prop('files', event.originalEvent.dataTransfer.files);
		});
		
		function readURL(input, place) {
			$('.preview').empty();
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
    
    $("#files").change(function() {
    	readURL(this, "div.preview");
    });
});