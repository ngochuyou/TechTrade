$(document).ready(function() {
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
	
	for (var i=0; i<tagNumber; i++) {
		$($('.hashtags')[i]).attr('id', 'hashtags'+i);
		$($('.hashtags-del')[i]).attr('id', 'hashtags-del'+i);
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
	
    $(window).click(function(target) {
    	if (target.target.id != 'my-dropdown-container') {
    		$('#my-dropdown-container').hide();
    	}
    });
  
    $('#search').keyup(function() {
        $.ajax({
        	type : 'GET',
        	url : '/TechTrade/post/search',
        	data : {
        		keyword :$('#search').val(),
        	},
        	success : function(list) {
        		var string = "";
        		
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
    
    $('#post-edit').click(function() {
    	$('.editable').toggleClass('hidden');
    });
    
    var title = $('#post-name').text();
    var description = $('#post-description').text();

    $('#title-edit').click(function(event) {
    	$('#post-name').toggleClass('hidden');
    	$('#title-input').toggleClass('hidden');
    	$('#title-submit').show();
    	$('#title-cancel').show();
    });
    
    $('#title-cancel').click(function() {
    	$('#post-name').toggleClass('hidden');
    	$('#title-input').toggleClass('hidden');
    	$('#title-input').val($('#post-name').text());
    	$('#title-submit').hide();
    	$('#title-cancel').hide();
    	console.log($('#title-input').val());
    });

    $('#hashtags-edit').click(function() {
    	$('.hashtags-stage2').show();
    });
    
    var hashtags = $('#hashtags-container').text().trim();
    var hashtagId;
    var originalHashtag = hashtags;
    console.log(hashtags);
    var targetHashtag = "";
    
    $('#hashtags-container').on('click', '.hashtags-del', function() {
    	hashtagId = this.id.match(/\d+/);
    	targetHashtag = $('#hashtags'+hashtagId);
    	targetHashtag.remove();
    	originalHashtag = originalHashtag.replace(targetHashtag.text().trim(), '');
    });
    
    var tagContainer = $('#hashtags-container').html();
    var tagInput;
    
    $('#hashtags-add').click(function() {
    	tagInput = $('#hashtags-add-input').val();
    	$($.parseHTML("<span class='color-main tags temptags mx-2 d-inline-block' id='hashtags"+tagNumber+"'>")).html(tagInput + "<span class='hashtags-del' id='hashtags-del"+tagNumber+"'><i class='fas fa-times-circle mx-2'></i></span>").appendTo("#hashtags-container");
    	originalHashtag += tagInput;
    	tagNumber++;
    	$('#hashtags-add-input').val('#');
    });
    
    $('#hashtags-cancel').click(function() {
    	
    	$('.hashtags').show();
    	$('.temptags').remove();
    	$('.hashtags-stage2').hide();
    	originalHashtag = hashtags;
    });
    
    $('#content-edit').click(function() {
    	$('#post-description').toggleClass('hidden');
    	$('#content-input').toggleClass('hidden');
    	$('#content-submit').show();
    	$('#content-cancel').show();
    });
    
    $('#content-cancel').click(function() {
    	$('#post-description').toggleClass('hidden');
    	$('#content-input').val($('#post-description'));
    	$('#content-input').toggleClass('hidden');
    	$('#content-submit').hide();
    	$('#content-cancel').hide();
    });
    
    $('#image-edit').click(function() {
    	$('.image-del').show();
    	$('#image-add').show();
    	$('#image-cancel').show();
    	$('.preview').toggleClass('hidden');
    });
    
    var deletedImage = [];
    var imageId;
    
    $('.image-del').click(function() {
    	imageId = this.id.match(/\d+/);
    	$('#image-'+imageId).hide();
    	deletedImage.push("image-"+imageId);
    });
    
    $('#image-cancel').click(function() {
    	$('.image-del').hide();
    	$('#image-add').hide();
    	$('#image-cancel').hide();
    	$('.preview').toggleClass('hidden');
    	$('.preview').empty();
    	$("#upload-photo").val(null);
    	$('.image').show();
    	deletedImage.length = 0;
    });
    
	var count = 0;
	var temp = "";
	var reader = null;
	var filesAmount;
	
    function readURL(input, place) {
    	$('.preview').empty();
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
    
    $("#upload-photo").change(function() {
    	readURL(this, "div.preview");
    	console.log($("#upload-photo").val());
    });
    
    $('#title-submit').click(function() {
    	$('#post-name').text($('#title-input').val());
    	$('#post-name').toggleClass('hidden');
    	$('#title-input').toggleClass('hidden');
    	$('#title-submit').hide();
    	$('#title-cancel').hide();
    });
    
    $('#hashtags-submit').click(function() {
    	$('.temptags').addClass('hashtags');
    	$('.temptags').removeClass('temptags');
    	$('.hashtags').show();
    	$('.hashtags-stage2').hide();
    	$('.hashtags-del').toggleClass('hidden');
    	$('#hashtags-input').val(originalHashtag);
    });
    
    $('#content-submit').click(function() {
    	$('#post-description').text($('#content-input').val());
    	$('#post-description').toggleClass('hidden');
    	$('#content-input').toggleClass('hidden');
    	$('#content-submit').hide();
    	$('#content-cancel').hide();
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
    
    $('#comment').focus(function() {
    	$(document).keypress(function(event) {
    		if (event.which == 13 && $('#comment').val().length > 0) {
    			newComment = "";
    			$.ajax({
    				type : 'POST',
    				url : '/TechTrade/post/comment',
    				data : {
    					postId : $('#post-id').val(),
    					comment : $('#comment').val(),
    					[crfs.attr('name')] : crfs.val()
    				},
    				success : function(username) {
    					newComment = "<div class='m-2 w-100'>"
    								+ "<p class='text-primary'>" + formatCurrentDate() + "</p>"
    								+ "<p> <span class='font-italic text-primary'>" + username + "</span>: " + $('#comment').val() + "</p>"
    								+ "</div>";
    					
    					$('#comments').html($('#comments').html() + newComment);
    					$('#comment').val(null);
    				},
    			});
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
});
