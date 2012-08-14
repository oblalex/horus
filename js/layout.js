$(document).ready(function() {
	$('head').append("<link rel='stylesheet' type='text/css' href='style/layout.css'/>");
	
	$('#forLayout').load("layout/layout.html", function() {
		var forContent = $('#forContent');
		$('#content').html(forContent.html());
		forContent.remove();		
	});
});
