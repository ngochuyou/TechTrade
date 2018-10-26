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
        			string += "<a class='dropdown-item text-main text-truncate' href='/TechTrade/post/"+this[1]+"'>"+this[0]+"</a>";
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
    
    var spanTags;
    var d;
    var string = "";
    var currentPage = 0;
    var stopPaging = false;
    var post_content = $('#post-content');
    
    $(window).scroll(function() {
 	   if((($(window).scrollTop() + $(window).height())) == ($(document).height())) {
 		   if (stopPaging == true) {
 			   return ;
 		   }
 		   $('#loader').fadeIn("fast");
 	       $.ajax({
 	    	   type: 'GET',
 	    	   url: '/TechTrade/s?' + parameters,
 	    	   data:{
 	    		   p: currentPage ++,
 	    	   },
 	    	   contentType: "application/json; charset=utf-8",
 	    	   success: function(result){
 	    		   currentPage++;
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
		+"						<i class='fas fa-map-marker mr-2'></i>"+this.createBy.ward.name+","
		+"						"+this.createBy.ward.district.name+','
		+"						"+this.createBy.ward.district.city.name
		+"					</h3>"
		+"					<h2 class='text-truncate font-weight-bold'>"+this.name+"</h2>"
		+"					<p>"
		+"						By <span class='font-italic text-main'>"+this.createBy.username+"</span>"
		+"						on " + formatCurrentDate(date)
		+"					</p>"
		+"					<div class='line-height-large'>"
		+"						<span>Tags <i class='fas fa-hashtag'></i>"
		+"						</span> <span class='tags d-inline-block'"
		+"							style='background-color : "+this.category.tagColor+"'>"+this.category.name+"</span> "
		+ spanTags
		+"						</span>"
							
		+"					</div>"
		+"				</div>"
		+"				<div class='col-2'>"
		+"					<img src='/TechTrade/account/avatar/"+this.createBy.avatar +"'"
		+"						class='avatar position-right mx-3'>"
		+"				</div>"
		+"			</div>"
		+"			<div class='row pointer' onclick='window.location.href=\"/TechTrade/post/"+ this.id +"\"'>"
		+"				<div class='col custom-control-description text-size-post'>"+ this.description +"</div>"
		+"			</div>"
		+"			<div class='row post-footer'>"
		+"				<div class='col'>";
 	    			  if($('#flag').val() == 'true'){
							string += "<div class='col-6 float-left border text-center h-100'>	"					
							+"			<h3 class='mt-3'>"
							+"				<i class='fas fa-arrows-alt-v mr-5'></i>"+this.upVote+" Votes"
							+"			</h3>"
							+"		</div>"
							+"		<div class='col-6 float-left border text-center h-100 pointer' >";
							if(this.pin == null){
								string +="<h3 class='mt-3 pin' id='"+this.id+"' >"
									+"				<i class='fas fa-thumbtack mr-5'></i>Pin"
									+"			</h3>"
									+"		</div>";
							}
							else{
								string +="<h3 class='mt-3 pin' id='"+this.id+"' style='color:blue' >"
									+"				<i class='fas fa-thumbtack mr-5'></i>Unpin"
									+"			</h3>"
									+"		</div>";
							}
							
					}
				    	        			
					if($('#flag').val() == 'false'){
							string +="<div class='col-12 float-left border text-center h-100'>"
							+"			<h3 class='mt-3'>"
							+"				<i class='fas fa-arrows-alt-v mr-5'></i>"+this.upVote+" Votes"
							+"			</h3>"
							+"		</div>";
					}
					string += "</div> </div> </div>";	
 	        		});
 	    		   $(post_content).append(string);
 	    		   $('#loader').fadeOut("fast");
 	    		   if (string.length == 0) {
 	    			   stopPaging = true;
 	    		   }
 	    	   }
 	       });
 	   };
 	});
    
    var postId = "";
    var flag;
    $(document).on('click','.pin', function(){
    	postId = this.id;
    	$.ajax({
	    	   type: 'GET',
	    	   url: '/TechTrade/post/pin?post='+postId,
	    	   contentType: "application/json; charset=utf-8",
	    	   success: function(result){
	    		   if(result == true){
	    			   $(document).find('#'+postId).html("<i class='fas fa-thumbtack mr-5'></i>Unpin");
	    			   $(document).find('#'+postId).css({
	    				   'color': 'blue',
	    			   });
	    		   }
	    		   else{
	    			   $(document).find('#'+postId).html("<i class='fas fa-thumbtack mr-5'></i>Pin");
	    			   $(document).find('#'+postId).css({
	    				   'color': '#555',
	    			   });
	    		   }
	    	   },
	    	   error:function(){
	    		   alert('error duy');
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
    
    function formatCurrentDate(date) {
    	
    	return monthNames[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear();
    }
});
