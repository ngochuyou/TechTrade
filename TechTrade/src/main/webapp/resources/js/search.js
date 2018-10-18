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
    
    var spanTags;
    var d;
    var string = "";
    var currentPage = 0;
    var stopPaging = false;
    
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
 	    		   p: currentPage + 1,
 	    	   },
 	    	   contentType: "application/json; charset=utf-8",
 	    	   success: function(result){
 	    		   currentPage++;
 	    		   $.each(result, function() {
 	    			   spanTags = "";
 	    			   string = "";
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
		+"				<div class='col'>"
		+"					<div class='col-6 float-left border text-center h-100'>"
		+"						<h3 class='mt-3'>"
		+"							<i class='fas fa-arrows-alt-v mr-5'></i>"+this.upVote+" Votes"
		+"						</h3>"
		+"					</div>"
		+"					<div class='col-6 float-left border text-center h-100 pointer'>"
		+"						<h3 class='mt-3'>"
		+"							<i class='fas fa-thumbtack mr-5'></i>Pin"
		+"						</h3>"
		+"					</div>"
		+"				</div>"
		+"			</div>"
		+"		</div>";    	        			
 	        		});
 	    		   $('#post-content').html($('#post-content').html()+string);
 	    		   $('#loader').fadeOut("fast");
 	    		   if (string.length == 0) {
 	    			   stopPaging = true;
 	    		   }
 	    	   }
 	       });
 	   };
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
