$(document).ready(function() {
    ClassicEditor
	.create( document.querySelector( '#content-input' ) )
	.then( newEditor => {
		
	})
	.catch( error => {
		console.error( error );
	});
    
	$('button').click(function(event) {
		event.preventDefault();
	});
	
    $('#search').focus(function() {
    	$(document).keypress(function(event) {
    		if (event.which == 13) {
    			$('#search-form').submit()
    		}
    	});
    });
    	
	$('#signin').click(function() {
		window.location.href = "/TechTrade/login";
	});
	
	$('#signup').click(function() {
		window.location.href = "/TechTrade/account/sign-up";
	});
	
	var tagNumber = $('.hashtags').length;
	var hashtags_array = $('.hashtags');
	var hashtags_del_array = $('.hashtags-del');
	
	for (var i=0; i<tagNumber; i++) {
		$(hashtags_array[i]).attr('id', 'hashtags' + i);
		$(hashtags_del_array[i]).attr('id', 'hashtags-del' + i);
	}
		
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
        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k=" + $(search).val() + "'>See more</a>";
        		$(search_dropdown).html(string);
        		$(search_dropdown).show();
        	},
        	error : function() {
        		$(search_dropdown).hide();
        	}
        });
    });
    
    var editable = $('.editable');
    	
    $('#post-edit').click(function() {
    	editable.toggleClass('hidden');
    });
    
    var title = $('#post-name').text();
    var description = $('#post-description').text();
    var title_edit = $('#title-edit');
    var title_input = $('#title-input');
    var title_submit = $('#title-submit');
    var title_cancel = $('#title-cancel');
    
    title_edit.click(function(event) {
    	$('#post-name').toggleClass('hidden');
    	title_input.toggleClass('hidden');
    	title_submit.toggleClass('hidden');
    	title_cancel.toggleClass('hidden');
    });
    
    title_cancel.click(function() {
    	$('#post-name').toggleClass('hidden');
    	title_input.toggleClass('hidden');
    	title_input.val($('#post-name').text());
    	title_submit.toggleClass('hidden');
    	title_cancel.toggleClass('hidden');
    });

    var hashtags_edit =  $('#hashtags-edit');
    var hashtags_stage2 = $('.hashtags-stage2');
    
    hashtags_edit.click(function() {
    	$(document).find('.hashtags-stage2').toggleClass('hidden');
    });
    
    var hashtags = $('#hashtags-container').text().trim();
    var hashtagId;
    var originalHashtag = hashtags;
    var targetHashtag;
    var hashtags_input = $('#hashtags-input');
    var hashtags_container = $('#hashtags-container');
    
    hashtags_input.val(originalHashtag.replace(',', ""));
    
    hashtags_container.on('click', '.hashtags-del', function() {
    	hashtagId = this.id.match(/\d+/);
    	targetHashtag = $('#hashtags'+hashtagId);
    	targetHashtag.addClass('deltags');
    	hashtags_del_array.add(targetHashtag);
    	$(targetHashtag).hide();
    	originalHashtag = originalHashtag.replace(targetHashtag.text().trim(), "");
    	console.log(originalHashtag);
    });
    
    var tagContainer = $('#hashtags-container').html();
    var tagInput;	
    var hashtags_add = $('#hashtags-add');
    var hashtags_add_input = $('#hashtags-add-input');
    
    hashtags_add.click(function() {
    	tagInput = hashtags_add_input.val();
    	$($.parseHTML("<span class='color-main tags temptags mx-2' id='hashtags"+tagNumber+"'>")).html(tagInput + "<span class='hashtags-stage2 hashtags-del' id='hashtags-del"+tagNumber+"'><i class='fas fa-times-circle mx-2'></i></span>").appendTo("#hashtags-container");
    	originalHashtag += tagInput;
    	tagNumber++;
    	hashtags_add_input.val('#');
    });
    
    var hashtags_cancel = $('#hashtags-cancel');
    
    hashtags_cancel.click(function() {
    	hashtags_array.show();
    	$('.temptags').remove();
    	$(document).find('.deltags').removeClass('deltags');
    	$(document).find('.hashtags-stage2').toggleClass('hidden');
    	originalHashtag = hashtags;
    });
    
    var content_edit = $('#content-edit');
    var post_description = $('#post-description');
    var content_input = $('#content-input');
    var content_submit = $('#content-submit');
    var content_cancel = $('#content-cancel');
    var content_container = $('#content-container');
    
    content_edit.click(function() {
    	post_description.toggleClass('hidden');
    	content_container.toggleClass('hidden');
    	content_input.toggleClass('hidden');
    	content_submit.toggleClass('hidden');
    	content_cancel.toggleClass('hidden');
    });
     
    content_cancel.click(function() {
    	post_description.toggleClass('hidden');
    	content_container.toggleClass('hidden');
    	content_input.val(post_description.text());
    	content_input.toggleClass('hidden');
    	content_submit.toggleClass('hidden');
    	content_cancel.toggleClass('hidden');
    });
    
    var image_edit = $('#image-edit');
    var image_del = $('.image-del');
    var image_add = $('#image-add');
    var image_cancel = $('#image-cancel');
    var preview = $('.preview');
    
    image_edit.click(function() {
    	image_del.toggleClass('hidden');
    	image_add.toggleClass('hidden');
    	image_cancel.toggleClass('hidden');
    	preview.toggleClass('hidden');
    });
    
    var deletedImage = [];
    var imageId;
    
    image_del.click(function() {
    	imageId = this.id.match(/\d+/);
    	$('#image-'+imageId).hide();
    	deletedImage.push("image-"+imageId);
    });
    
    var upload_photo = $("#upload-photo");
    var image =  $('.image');
    
    image_cancel.click(function() {
    	image_del.toggleClass('hidden');
    	image_add.toggleClass('hidden');
    	image_cancel.toggleClass('hidden');
    	preview.toggleClass('hidden').empty();
    	upload_photo.val(null);
    	image.show();
    	deletedImage.length = 0;
    });
    
	var count = 0;
	var temp = "";
	var reader = null;
	var filesAmount;
	
    function readURL(input, place) {
    	preview.empty();
            if (input.files) {
            	filesAmount = input.files.length;

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
    
    upload_photo.change(function() {
    	readURL(this, "div.preview");
    });
    
    var post_name = $('#post-name');
    
    title_submit.click(function() {
    	post_name.text($('#title-input').val());
    	post_name.toggleClass('hidden');
    	title_input.toggleClass('hidden');
    	title_submit.toggleClass('hidden');
    	title_cancel.toggleClass('hidden');
    });
    
    var hashtags_submit = $('#hashtags-submit');
    var hashtags_del_array = $('.deltags');
    
    hashtags_submit.click(function() {
    	console.log(originalHashtag);
    	$('.temptags').addClass('hashtags').removeClass('temptags');
    	hashtags_array.show();
    	$(document).find('.deltags').remove();
    	hashtags_input.val(originalHashtag);
    	$(document).find('.hashtags-stage2').toggleClass('hidden');
    });
    
    content_submit.click(function() {
    	$('#form').submit();
    });
    
    $('#submit-all').click(function() {
    	var stringify = "";
    	
    	$.each(deletedImage, function() {
    		stringify += this.match(/\d+/) + ",";
    	});
    	
    	$('#deleted-image-input').val(stringify);
    	$('#form').submit();
    });
    
    var newComment = "";
    var crfs = $('#csrfToken');
    var comment = $('#comment');
    var post_id = $('#post-id').val();
    
    comment.focus(function() {
    	$(document).keypress(function(event) {
    		if (event.which == 13 && comment.val().length > 0) {
    			newComment = "";
    			$.ajax({
    				type : 'POST',
    				url : '/TechTrade/post/comment',
    				data : {
    					postId : post_id,
    					comment : comment.val(),
    					[crfs.attr('name')] : crfs.val()
    				},
    				success : function(username) {
    					newComment = "<div class='m-2 w-100'>"
    								+ "<p class='text-primary'>" + formatCurrentDate() + "</p>"
    								+ "<p> <span class='font-italic text-primary'>" + username + "</span> : " + comment.val() + "</p>"
    								+ "</div>";
    					
    					$('#comments').append(newComment);
    					comment.val(null);
    				},
    			});
    		}
    	});
    });
    
    var flag;
    var pin_noti;
    
    $(document).on('click','.pin', function(){
    	$.ajax({
	    	   type: 'GET',
	    	   url: '/TechTrade/post/pin',
	    	   data : {
	    		   postId : post_id
	    	   },
	    	   contentType: "application/json; charset=utf-8",
	    	   success: function(result) {
	    		   if(result == "Pinned") {
	    			   $(document).find('#' + post_id).html("<i class='fas fa-thumbtack mr-5'></i>Unpin").css({
	    				   'color': 'blue',
	    			   }); 
	    		   }
	    		   else {
	    			   if(result == "Unpinned") {
		    			   $(document).find('#' + post_id).html("<i class='fas fa-thumbtack mr-5'></i>Pin").css({
		    				   'color': '#555',
		    			   });
	    			   }
	    		   }
	    		   $('.pin-noti').remove();
	    		   $('body').append("<div class='fixed-noti pin-noti' id='pin-noti'><i class='fas fa-thumbtack mr-5'></i>" + result + "</div>");
	    		   pin_noti = $('#pin-noti');
	    		   setTimeout(function() {
	    			   $(pin_noti).remove();
	    		   }, 3000);
	    	   }
    	});
    });
    
    var monthNames = [
		"Jan", "Feb", "Mar",
		"Apr", "May", "Jun", "Jul",
		"Aug", "Sep", "Oct",
		"Nov", "Dec"
    ];
    
    var currentDate = new Date();
    var day = currentDate.getDate();
	var monthIndex = currentDate.getMonth();
	var year = currentDate.getFullYear();
	
    function formatCurrentDate() {
    	
    	return monthNames[monthIndex] + ' ' + day + ', ' + year;
    }
    
    var vote = $('#vote');
    
    $('#upvote').click(function() {
    	vote.text((parseInt(vote.text()) + 1) + " Voted");
    	$.ajax({
    		type : 'GET',
    		url : '/TechTrade/post/vote/' + post_id,
    		data : {
    			type : true
    		},
    		success : function(result) {
    			$('#vote-holder').html("<p class='w-100'>You voted this post +1</p>");
    		}
    	});
    });
    
    $('#downvote').click(function() {
    	vote.text((parseInt(vote.text()) - 1) + " Voted");
    	$.ajax({
    		type : 'GET',
    		url : '/TechTrade/post/vote/' + post_id,
    		data : {
    			type : false
    		},
    		success : function(result) {
    			$('#vote-holder').html("<p class='w-100'>You voted this post -1</p>");
    		}
    	});
    });
    
    var post_del_link = $('a.post-del');
    var post_noti;
    var post_main = $('.post');
    
    post_del_link.click(function(event) {
    	event.preventDefault();
    	post_id = this.id.match(/\d+/);
    	post_main.append("<div class='fixed-noti post-noti'><i class='fas fa-trash mr-3'></i>Are you sure you want to delete this post? Action can not be undo. <button class='btn bg-main mx-4' id='post-noti-yes'>Yes!</button><button class='btn btn-outline-main' id='post-noti-no'>Don't do it</button></div>");
    	post_noti = $('.post-noti');
    });
    
    post_main.on('click', '#post-noti-yes', function(){
    	window.location.href = '/TechTrade/post/delete/' + post_id; 
    });
    
    post_main.on('click', '#post-noti-no', function(){
    	post_noti.remove();
    });
    
    $(function () {
    	$('[data-toggle="tooltip"]').tooltip();
    });
    
    var report_open = $('#report-open');
    var report_cancel = $('#report_cancel');
    var report_container = $('#report-container');
    var report = $('#report');
    var report_send = $('#report-send');
    var report_cancel = $('#report-cancel');
    var report_content = $('#report-content');
    var report_model = {
    	reason : "",
    	content : "",
    	targetedPost : 0
    }
    
    report_open.click(function() {
    	report_container.show();
    	setTimeout(function() {
    		report.addClass("absolute-center-active");
    	}, 100);
    });
    
    report_cancel.click(function() {
    	report.removeClass("absolute-center-active");
    	setTimeout(function() {
    		report_container.hide();
    	}, 200);
    });
    
    report_send.click(function() {
    	report_model.content = report_content.val();
    	report_model.reason = $('input[name=report-radio]:checked').val();
    	report_model.targetedPost = post_id;
    	$.ajax({
    		type : 'POST',
    		url : '/TechTrade/post/report',
    		contentType: "application/json; charset=utf-8",
    		data : JSON.stringify(report_model),
    		success : function(result) {
    			report_cancel.trigger('click');
    			report_open.removeAttr('class');
    			report_open.attr('class', 'col-4 float-left border text-center h-100');
    			report_open.html("<p class='mt-3 text-small text-main'>"
    								+ "<i class='fas fa-flag mr-3'></i>You reported this post on " + formatCurrentDate() + ".</p>");
    			$('#report-open').off('click');
    			$('body').append("<div class='fixed-noti report-noti'><i class='fas fa-flag mr-3'></i>" + result + "</div>");
    			setTimeout(function() {
    				$('.report-noti').remove();
    			}, 3000);
    		}
    	});
    });
});
