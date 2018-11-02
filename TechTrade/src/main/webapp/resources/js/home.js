$(document).ready(function() {
	var currentPage = 0;
	
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

    var search_box = $('#search');
    var search_dropdown = $('#my-dropdown-container');
    
    $(window).click(function(event) {
    	if (event.target.id != 'my-dropdown-container') {
    		search_dropdown.hide();
    	}
    });
  
    search_box.keyup(function() {
        $.ajax({
        	type : 'GET',
        	url : '/TechTrade/post/search',
        	data : {
        		keyword : search_box.val(),
        	},
        	success : function(list) {
        		var string = "";
        		
        		$.each(list, function() {
        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/view/" + this[1] + "'>" + this[0] + "</a>";
        		});
        		
        		string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/search?k=" + search_box.val() + "'>See more</a>";
        		search_dropdown.html(string);
        		search_dropdown.show();
        	},
        	error : function() {
        		search_dropdown.hide();
        	}
        });
    });
    
    var spanTags;
    var d;
    var string = "";
    var stopPaging = false;
    var flag = $('#flag').val();
    var page_loader = $('#loader');
    var post_content = $('#post-content');
    
    $(window).scroll(function() {
    	if((($(window).scrollTop() + $(window).height())) == ($(document).height())) {
    		if (stopPaging == true) {
	 			   return ;
	 		   }
    		   page_loader.fadeIn("fast");
    	       $.ajax({
    	    	   type: 'GET',
    	    	   url: '/TechTrade/post',
    	    	   data:{
    	    		   page: currentPage++,
    	    	   },
    	    	   contentType: "application/json; charset=utf-8",
    	    	   success: function(result){
    	    		   console.log(currentPage);
	    			   string = "";
    	    		   $.each(result, function() {
    	    			   spanTags = "";
    	    			   $.each(this.tags.split(','),function(){
    	    				   spanTags += "<span class='color-main tags d-inline-block'>"+this+"</span> ";
    	    			   })
    	    			   date = new Date(this.createAt);	
    	    			   string += "<div class='post'>"
		+"			<div class='row my-2'>"
		+"				<div class='col-10'>"
		+"					<h3>"
		+"						<i class='fas fa-map-marker mr-2'></i>" + this.createBy.ward.name + ","
		+"						" + this.createBy.ward.district.name + ','
		+"						" + this.createBy.ward.district.city.name
		+"					</h3>"
		+"					<h2 class='text-truncate font-weight-bold'>" + this.name + "</h2>"
		+"					<p>"
		+"						By <span class='font-italic text-main pointer' onclick='window.location.href=\"/TechTrade/account/wall/" + this.createBy.username + "\"'>" + this.createBy.username + "</span>"
		+"						on " + formatCurrentDate(date)
		+"					</p>"
		+"					<div class='line-height-large'>"
		+"						<span>Tags <i class='fas fa-hashtag'></i>"
		+"						</span> <span class='tags d-inline-block'"
		+"							style='background-color : " + this.category.tagColor + "'>" + this.category.name + "</span> "
		+ spanTags
		+"						</span>"				
		+"					</div>"
		+"				</div>"
		+"				<div class='col-2'>"
		+"					<img src='/TechTrade/account/avatar/" + this.createBy.avatar + "'"
		+"						class='avatar position-right mx-3'>"
		+"				</div>"
		+"			</div>"
		+"			<div class='row pointer' onclick='window.location.href=\"/TechTrade/post/view/"+ this.id +"\"'>"
		+"				<div class='col custom-control-description text-size-post'>"+ this.description +"</div>"
		+"			</div>"
		+"			<div class='row post-footer'>"
		+"				<div class='col'>";
    	    			   
    	    			   if (flag == 'true') {
								string += "<div class='col-6 float-left border text-center h-100'>	"					
								+"			<h3 class='mt-3'>"
								+"				<i class='fas fa-arrows-alt-v mr-5'></i>" + this.upVote + " Votes"
								+"			</h3>"
								+"		</div>"
								+"		<div class='col-6 float-left border text-center h-100 pointer' >";
								if (this.pin == null) {
									string +="<h3 class='mt-3 pin' id='" + this.id + "' >"
										+"				<i class='fas fa-thumbtack mr-5'></i>Pin"
										+"			</h3>"
										+"		</div>";
								} else {
									string +="<h3 class='mt-3 pin' id='" + this.id + "' style='color: var(--primary)' >"
										+"				<i class='fas fa-thumbtack mr-5'></i>Unpin"
										+"			</h3>"
										+"		</div>";
								}
								
						} else {
								string +="<div class='col-12 float-left border text-center h-100'>"
								+"			<h3 class='mt-3'>"
								+"				<i class='fas fa-arrows-alt-v mr-5'></i>" + this.upVote + " Votes"
								+"			</h3>"
								+"		</div>";
						}
						string += "</div> </div> </div>";		
    	        		});
    	    		   
    	    		   post_content.append(string);
    	    		   page_loader.fadeOut("fast");
    	    		   
	 	    		   if (string.length == 0) {
	 	    			   stopPaging = true;
	 	    		   }
    	    	   },    	    	   
    	       });
    	   };
    	});
    
    var postId = "";
    var pin_noti;
    
    $(document).on('click','.pin', function(){
    	id = this.id;
    	$.ajax({
	    	   type: 'GET',
	    	   url: '/TechTrade/post/pin',
	    	   data : {
	    		   postId : id
	    	   },
	    	   contentType: "application/json; charset=utf-8",
	    	   success: function(result) {
	    		   if(result == "Pinned") {
	    			   $(document).find('#' + id).html("<i class='fas fa-thumbtack mr-5'></i>Unpin");
	    			   $(document).find('#' + id).css({
	    				   'color': 'var(--primary)',
	    			   });
	    		   }
	    		   else {
	    			   if(result == "Unpinned") {
		    			   $(document).find('#' + id).html("<i class='fas fa-thumbtack mr-5'></i>Pin");
		    			   $(document).find('#' + id).css({
		    				   'color': '#555',
		    			   });
	    			   }
	    		   }
	    		   $('.pin-noti').remove();
	    		   $('body').append("<div class='fixed-noti pin-noti' id='pin-noti'><i class='fas fa-thumbtack mr-3'></i>" + result + "</div>");
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
    
    function formatCurrentDate(date) {
    	
    	return monthNames[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear();
    }
});
