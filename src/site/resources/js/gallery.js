/**
 * activates fancybox
 */

$(document).ready(function() {
	
	$("ul.thumbnails a:has(img)").attr("rel", function() {
		return "gallery";
	});
	
	$("ul.thumbnails a:has(img)").attr("href", function() {
		return $(this).find("img").attr("src");
	});
	
	$("ul.thumbnails a:has(img)").attr("title", function() {
		return $(this).siblings("p.gallery-text").text();
	});	

	$("ul.thumbnails a:has(img)").fancybox({
		'transitionIn' : 'elastic',
		'transitionOut' : 'elastic',
		'speedIn' : 600,
		'speedOut' : 200,
		'overlayShow' : false,
		'titlePosition' : 'over'

	});
});