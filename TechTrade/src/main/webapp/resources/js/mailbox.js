$(document).ready(function() {
	var unread_qty = parseInt($('.unread-qty').first().text());
    var inbox = $('#inbox');
    var currentInboxPage = 0;
    var messagesContainer = $('#messages-container');
    var messagesContainerHTML;
    var stopPagingMessages = false;
    
    $(document).on('click', '.inbox-open', function() {
    	$(inbox).toggleClass('fixed-fullscreen-active');
    	$(document).find('.inbox-noti').remove();
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
    				$('#inbox-showmore').html("<span class='text-danger'>Nothing left.</span>");
    				return ;
    			}
    			currentInboxPage += 1;
    			messagesContainerHTML = "";
    			$.each(result, function() {
    				messagesContainerHTML += "<div class='row message pointer'>"
    								+ "<input type='hidden' value='" + this.id + "' class='message-id'>"
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
    			$(messagesContainer).append(messagesContainerHTML);
    		}
    	});
    });
    
    $(inbox).on('mouseenter', '.message, .outmessage', function() {
    	$(this).find('svg').removeClass('hidden');
    });
    
    $(inbox).on('mouseleave', '.message, .outmessage', function() {
    	$(this).find('svg').addClass('hidden');
    });
    
    var delete_noti = $('#delete-noti');
    var message_id;
    var message_root;
    var inbox_main = $('.inbox-main');
    var inbox_composer = $('.inbox-composer');
    var unRead_qty = $('.unread-qty');
    var message_result = $('.message-result');
    
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
    			$(delete_noti).html('<i class="fas fa-trash mr-3"></i>' + result);
    		}
    	});
		if ($(message_root).hasClass('bg-noti')) {
			unRead_qty.text(--unread_qty);
			$(message_root).remove();
		}
		$(message_root).remove();
		setTimeout(function() {
			  $(delete_noti).remove();
		}, 3000);
	});
	
	$(inbox_main).on('click', '#noti-no', function() {
		$('#delete-noti').remove();
	});
	
    var sender; 
    var reply = $('#reply');
    var inbox_back = $('#inbox-back');
    var composer_avatar = $('#composer-avatar');
    var composer_username = $('#composer-username');
    var composer_sentAt = $('#composer-sentAt');
    var composer_location = $('#composer-location');
    var composer_content = $('#composer-content');
    
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
	    				unRead_qty.text(++unread_qty);
	    				$('.message-id[value="' + message_id + '"]').parent().addClass('bg-noti');
	    			} else {
	    				unRead_qty.text(--unread_qty);
	    				$('.message-id[value="' + message_id + '"]').parent().removeClass('bg-noti');
	    			}
	    		}
	    	});
    	}
    	
    	$(composer_avatar).attr('src', '/TechTrade/account/avatar?username=' + sender);
    	$(composer_username).text(sender);
    	$(composer_sentAt).text($(this).find('.message-sentAt').text());
    	$(composer_location).text($(this).find('.message-location').text());
    	$(composer_content).text($(this).find('.message-content').text());
    	$(inbox_main).fadeOut("fast");
    	$(outbox).fadeOut("fast");
    	$(outbox_newOutbox).fadeOut("fast");
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
    				unRead_qty.text(++unread_qty);	
    				$('.message-id[value="' + message_id + '"]').parent().addClass('bg-noti');
    			} else {
    				$unRead_qty.text(--unread_qty);
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
    			$(delete_noti).html('<i class="fas fa-trash mr-3"></i>' + result);
    		}
    	});
		message_root = $('#message-delete-' + message_id).parents().eq(1);
		
		if ($(message_root).hasClass('bg-noti')) {
			unRead_qty.text(--unread_qty);
		}
		
		$(message_root).remove();
		setTimeout(function() {
			  $(delete_noti).remove();
		}, 3000);
		$(inbox_back).click();
	});
	
	$(inbox_composer).on('click', '#noti-no', function() {
		$('#delete-noti').remove();
	});
	
    $(inbox_back).click(function() {
    	inbox_main.fadeIn("fast");
    	inbox_composer.fadeOut("fast");
    	message_result.addClass('hidden');
    	$(reply).val(null);
    });	
    
    var outmessages_container = $('#outmessages-container');
    var outmessages_containerHTML = $(outmessages_container).html(); 
    var currentOutboxPage = 0;
    
    $('.inbox-main-open').click(function() {
    	$('.absolute:not(.inbox-main)').fadeOut("fast");
    	inbox_main.fadeIn("fast");
    });
    
    var send_result = $('#message-info');
    var send_loader = $('#message-loader');
    var crfs = $('#csrfToken');
    
    $('#message-send').click(function() {
    	if ($(reply).val().length <= 0) {
    		$(reply).attr('placeholder', "Please fill in this field");
    		return ;
    	}
    	message_result.removeClass('hidden');
    	send_loader.attr('src', '/TechTrade/resources/img/loading.gif');
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
    
    var outbox = $('#outbox');
    var outbox_load = $('.outbox-load'); 
    var outbox_load_stop = false;
    var outmessages_container = $('#outmessages-container');
    var	outmessages_container_HTML = $(outmessages_container).html();
    var outbox_open;
    
    $(outbox_load).click(function() {
    	if (outbox_load_stop == false) {
        	console.log("outbox-load-clicked");
    		$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message/outbox',
	    		data : {
	    			page : 0
	     		},
	    		success : function(result) {
	    			outmessages_container_HTML = "";
	    			$.each(result, function() {
	    				outmessages_container_HTML += "<div class='row outmessage' style='height: auto;'>"
													+"	<div class='col-2 border-right'>"
													+"		<img src=\"/TechTrade/account/avatar?username=" + this[1] + "\" class='m-auto avatar-medium'>"
													+"	</div>"
													+"	<div class='col-9'>"
													+"		<h3 class='outmessage-username'>" + this[1] + "</h3>"
													+"		<p class='mb-1'>" + this[3] + "</p>"
													+"		<p class='text-truncate text-small'>" + formatCurrentDateTime(this[2]) + "</p>"
													+"	</div>"
													+"	<div class='col-1 p-0'>"
													+"		<div class='icon icon-small m-auto outmessage-delete' id='outmessage-delete-" + this[0] + "'>"
													+"			<i class='fas fa-trash text-right hidden'></i>"
													+"		</div>"
													+"	</div>"
													+"</div>";
	    			});
	    			$(outmessages_container).append(outmessages_container_HTML);
	    			outbox_load_stop = true;
	    		}
	    	});
    	}
    	
		$(inbox_main).fadeOut("fast");
		$(inbox_composer).fadeOut("fast");
		$(outbox_newOutbox).fadeOut("fast");
		$(outbox).fadeIn("fast");
    });
    
    var outbox_showmore = $('#outbox-showmore');
    var outbox_paging_stop = false;
    var outbox_current_page = 1;
    
    $(outbox_showmore).click(function() {
    	if (outbox_paging_stop == false) {
    		$.ajax({
	    		type : 'GET',
	    		url : '/TechTrade/account/message/outbox',
	    		data : {
	    			page : outbox_current_page
	    		},
	    		success : function(result) {
	    			if (result.length == 0) {
	    				outbox_paging_stop = true;
	    				$(outbox_showmore).html("<span class='text-danger'>Nothing left.</span>");
	    				
	    				return ;
	    			}
	    			outbox_current_page++;
	    			outmessages_container_HTML = "";
	    			$.each(result, function() {
	    				outmessages_container_HTML += "<div class='row outmessage' style='height: auto;'>"
	    											+"	<div class='col-2 border-right'>"
	    											+"		<img src=\"/TechTrade/account/avatar?username=" + this[1] + "\" class='m-auto avatar-medium'>"
	    											+"	</div>"
	    											+"	<div class='col-9'>"
	    											+"		<h3 class='outmessage-username'>" + this[1] + "</h3>"
	    											+"		<p class='mb-1'>" + this[3] + "</p>"
	    											+"		<p class='text-truncate text-small'>" + formatCurrentDateTime(this[2]) + "</p>"
	    											+"	</div>"
	    											+"	<div class='col-1 p-0'>"
	    											+"		<div class='icon icon-small m-auto outmessage-delete' id='outmessage-delete-" + this[0] + "'>"
	    											+"			<i class='fas fa-trash text-right hidden'></i>"
	    											+"		</div>"
	    											+"	</div>"
	    											+"</div>";
	    			});
	    			$(outmessages_container).append(outmessages_container_HTML);
	    		}
	    	});
    	}
    });
    
    var outbox_delete_noti;
    
    $(outbox).on('click', '.outmessage-delete', function() {
    	$('.outbox-delete-noti').remove();
    	message_root = $(this).parents().eq(1);
    	message_id = this.id.match(/\d+/).toString();
    	$(outbox).append("<div class='fixed-noti outbox-delete-noti' id='outbox-delete-noti'>Are you sure you want to delete this message to <span class='font-weight-bold'>"+ $(message_root).find('.outmessage-username').text() +"</span>? Action can not be undo. <button class='btn bg-main mx-4' id='outbox-noti-yes'>Yes!</button><button class='btn btn-outline-main' id='outbox-noti-no'>Don't do it</button></div>");
    	outbox_delete_noti = $('#outbox-delete-noti');
    });
    
    $(outbox).on('click', '#outbox-noti-yes', function() {
		$(delete_noti).html("<img src='/TechTrade/resources/img/loading.gif' class='mr-3' style='height : 50px;'> Please wait...");
		$.ajax({
    		type : 'GET',
    		url : '/TechTrade/account/message/remove',
    		data : {
    			messageId : message_id
    		},
    		success : function(result) {
    			$(outbox_delete_noti).html('<i class="fas fa-trash mr-3"></i>' + result);
    		}
    	});
		$(message_root).remove();
		setTimeout(function() {
			  $(outbox_delete_noti).remove();
		}, 3000);
	});
	
	$(outbox).on('click', '#outbox-noti-no', function() {
		$(outbox_delete_noti).remove();
	});
	
    var inbox_newInbox_container = $('#newmessages-container');
    var inbox_newInbox_container_HTML;
    
    var inbox_newinbox_refreshLoop = setInterval(function() {
    	$.ajax({
    		type: 'GET',
    		url : '/TechTrade/account/message/inbox',
    		success : function(result) {	
    			if (result.length == 0) {
    				return ;
    			}
    			inbox_newInbox_container_HTML = "";
    			$.each(result, function() {
    				inbox_newInbox_container_HTML += "<div class='row bg-noti message newmessage pointer'>"
						+ "<input type='hidden' value='" + this[0] + "' class='message-id'>"
						+ "<div class='col-2 border-right'>"
						+ "<img src='/TechTrade/account/avatar?username="+ this[1] + "' class='m-auto avatar-medium'>"
						+ "</div>"
						+ "<div class='col-9'>"
						+ "<h3 class='text-truncate message-username'>" + this[1] + "</h3>"
						+ "<p class='text-truncate mb-1 message-content'>" + this[3] + "</p>"
						+ "<p class='text-truncate text-small message-sentAt'>"
						+ formatCurrentDateTime(this[2]) + "</p>"
						+ "</div>"
						+ "<div class='col-1 p-0'>"
						+ "<div class='icon icon-small m-auto message-delete' id='message-delete-" + this[0] + "'>"
						+ "<i class='fas fa-trash text-right hidden'></i>"
						+ "</div>"
						+ "<div class='icon icon-small m-auto'>"
						+ "<i class='fas fa-reply text-right hidden'></i>"
						+ "</div></div></div>";
    			});
    			unRead_qty.text(++unread_qty);
    			$(inbox_newInbox_container).append(inbox_newInbox_container_HTML);
    			$('body').append("<div class='fixed-noti inbox-noti'><i class='fas fa-envelope mr-3'></i>You have new Message <span class='mx-3 pointer inbox-open text-primary'>Show me</span></div>");
    		}
    	});
    }, 30000);
    
    var outbox_newOutbox = $('.outbox-composer');
    var outbox_newOutbox_open = $('.newoutbox-open');
    var outbox_newOutbox_id = $('#newoutbox-id');
    var outbox_newOutbox_ids = $('#newoutbox-ids');
    var outbox_newOutbox_content = $('#newoutbox-content-input');
    var outbox_newOutbox_idNumber = 0;
    var outbox_newOutbox_idError = $('#newoutbox-id-error');
    var outbox_newOutbox_idInput = "";
    
    $(outbox_newOutbox_open).click(function() {
    	$(inbox_main).fadeOut("fast");
		$(inbox_composer).fadeOut("fast");
		$(outbox).fadeOut("fast");
		$(outbox_newOutbox).fadeIn("fast");
    });
    
    $(outbox_newOutbox_id).keyup(function(event) {
    	if (event.keyCode ==  13) {
    		$.ajax({
    			type : 'GET',
    			url : '/TechTrade/account/keycheck',
    			data : {
    				key : $(outbox_newOutbox_id).val()
    			},
    			success : function(result) {
    				if (result.length == null) {
    					$(outbox_newOutbox_idError).addClass('hidden');
    					outbox_newOutbox_ids.append("<span class='color-main tags mx-2' id='newoutbox-id" + outbox_newOutbox_idNumber + "'>" + result.username + "<span class='newoutbox-id-del' id='newoutbox-id-del" + outbox_newOutbox_idNumber + "'><i class='fas fa-times-circle mx-2'></i></span>");
    					outbox_newOutbox_idInput += (',' + result.username);
    					$(outbox_newOutbox_id).val(null);
    					console.log(outbox_newOutbox_idInput);
    				} else {
    					$(outbox_newOutbox_idError).removeClass('hidden');
    				}
    			}
    		});	
    	}
    });
    
    var outbox_newOutbox_targetedId;
    
    $(outbox_newOutbox).on('click', '.newoutbox-id-del', function() {
    	outbox_newOutbox_targetedId = $(outbox_newOutbox).find('#newoutbox-id' + this.id.match(/\d+/));
    	
    	outbox_newOutbox_idInput = outbox_newOutbox_idInput.replace((',' + $(outbox_newOutbox_targetedId).text()), '');
    	$(outbox_newOutbox_targetedId).remove();
    	console.log(outbox_newOutbox_idInput);
    });
    
    var outbox_newOutbox_send = $('#newoutbox-send');
    var outbox_newOutbox_noti;
    
    $(outbox_newOutbox_send).click(function() {
    	if (outbox_newOutbox_idInput.length == 0) {
        	outbox_newOutbox_noti = $('.newoutbox-send-noti');
    		$(outbox_newOutbox_noti).remove();
    		$(outbox_newOutbox).append("<div class='fixed-noti newoutbox-send-noti'><i class='fas fa-comment-dots mr-3'></i>Don't forget to enter receiver's username<button class='btn bg-main mx-3 text-primary' id='newoutbox-send-notiOK'>Okay</button></div>");
    		
    		return ;
    	} 
    	
    	$(outbox_newOutbox).append("<div class='fixed-noti newoutbox-send-noti'><img src='/TechTrade/resources/img/loading.gif' class='mr-3' style='height : 50px;'> Please wait...</div>");
    	outbox_newOutbox_noti = $('.newoutbox-send-noti');
    	$.ajax({
    		type : 'POST',
    		url : '/TechTrade/account/message/send',
    		data : {
    			receiver : outbox_newOutbox_idInput,	
    			content : $(outbox_newOutbox_content).val(),
    			[crfs.attr('name')] : crfs.val()
    		},
    		success : function(result) {
    			$(outbox_newOutbox_noti).text(result);
    			setTimeout(function() {
    				$(outbox_newOutbox_noti).remove();
    			}, 3000);
    			outbox_load_stop = false;
    		}
    	});
    });
    
    outbox_newOutbox.on('click', '#newoutbox-send-notiOK', function() {
    	outbox_newOutbox.find('.newoutbox-send-noti').remove();
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
});