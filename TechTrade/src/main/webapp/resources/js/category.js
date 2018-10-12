$(document).ready(function() {
	$('#cate-form-openBtn').click(function() {
		$('#cate-form').attr('action', '/TechTrade/category/create');
		$('#cate-form-container').show("fast");
	});

	$('.form-closeBtn').click(function() {
		$('.hidden-absolute-container').hide("fast");
	});

	$('.cate-updateBtn').click(function() {
		var id = this.id.match(/\d+/);
		
		$('#cate-form-id').val(id);
		$('#cate-form-name').val($('#cate-name' + id).html());
		$('#cate-form').attr('action', '/TechTrade/category/update');
		$('#cate-form-container').show("fast");
	});
});