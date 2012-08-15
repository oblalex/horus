$.fn.addPopup = function() {
	var moveLeft = 20;
	var moveDown = 10;

	var target = $(this[0]);
	var popup = arguments[0] || {};

	target.hover(function(e) {
		popup.show();
	}, function() {
		popup.hide();
	});

	target.mousemove(function(e) {
		popup.css('top', e.pageY + moveDown).css('left', e.pageX + moveLeft);
	});
};
