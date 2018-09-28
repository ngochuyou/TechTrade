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

	$('.brand-form-openBtn').click(function() {
		var categoryId = this.id.match(/\d+/);
		
		$('#brand-form').attr('action', '/TechTrade/category/brand/create');
		$('#brand-form-categoryId').val(categoryId);
		$('#brand-form-categoryName').val($('#cate-name' + categoryId).html());
		$('#brand-form-container').show("fast");
	});
	
	$('.brand-updateBtn').click(function() {
		$('#brand-form').attr('action', '/TechTrade/category/brand/update');
		
		var id = this.id;
		var group = id.split(/cate-\d+/);
		
		$('#brand-form-id').val(group[0].match(/\d+/));
		
		group = id.split(/brand-\d+/);
		
		var cateId = group[1].match(/\d+/);
		
		$('#brand-form-categoryId').val(cateId);
		$('#brand-form-categoryName').val($('#cate-name' + cateId).html());
		$('#brand-form-container').show("fast");
	});
});