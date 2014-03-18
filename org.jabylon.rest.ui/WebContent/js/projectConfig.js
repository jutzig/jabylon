$(document).ready(function() {
	$("#inputType").change(function() {
		var selection = $(this).find("option:selected").text(); 
		var includes = $("#default-includes span[type='"+selection+"']").attr("include");
		$("#inputIncludes").text(includes);
		var excludes = $("#default-excludes span[type='"+selection+"']").attr("exclude");
		$("#inputExcludes").text(excludes);
	});
});