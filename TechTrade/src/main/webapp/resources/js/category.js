$(document).ready(function() {
	$('#cate-form-openBtn').click(function() {
		$('#cate-form').attr('action', '/TechTrade/category/create');
		$('#cate-form-container').show("fast");
	});

	$('#cate-form-closeBtn').click(function() {
		$('#cate-form-container').hide("fast");
	});

	$('.cate-updateBtn').click(function() {
		$.ajax({
			url : '/TechTrade/category/get',
			type : "GET",
			data : {
				cateId : this.id
			},
			dataType : 'json',
			success : function(data) {
				$('#cate-form').attr('action', '/TechTrade/category/update');
				$('#cate-form-id').val(data.id);
				$('#cate-form-name').val(data.name);
			},
			error : function() {
				$('#cate-form-message').html("You are trying to update a non-exsit Category");
			}
		});
		$('#cate-form-container').show("fast");
	});

	$('#testBtn').click(function() {
		$.ajax({
			url : '/TechTrade/category/test',
			type : "GET",
			dataType : 'json',
			success : function(data) {
				alert(data.name);
				alert(data.id)
			}
		})
	});
});