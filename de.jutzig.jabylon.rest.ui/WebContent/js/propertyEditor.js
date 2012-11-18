//to remember which row was last expanded (for next/previous)
var lastExpanded;

$(document).ready(function() {
	
	
	$('#table').dataTable({
		"iDisplayLength" : 50
	});

	// this is to make an AJAX request whenever a textarea is selected
	$('textarea').focus(function() {
		var textArea = $(this);
		var tr = textArea.parents('tr');
		var keyField = tr.find('td span').first();
		requestAid(keyField.text());
	});

	// toggle section with expand button
	$('.toggle-button').click(function() {

		var parent = $(this).parents('tr');
		toggleRow(parent);
	});

	$('.next-button').click(function() {

		var parent = $(this).parents('tr');
		expandRow(parent.next());
		collapseRow(parent);
	});

	$('.previous-button').click(function() {

		var parent = $(this).parents('tr');
		expandRow(parent.prev());
		collapseRow(parent);
	});

	selectFuzzyRow();

	// initialize keyboard shortcuts
	shortcut.add("Ctrl+Down", function() {
	var next = lastExpanded.next();
	if(next.length==0)
	{
		next = $('tr div.collapse').parents('tr').last(); 
	}
	collapseRow(lastExpanded);
	expandRow(next);
	});
	
	shortcut.add("Ctrl+Up", function() {
	var prev = lastExpanded.prev();
	if (prev.length == 0) {
		prev = $('tr div.collapse').parents('tr').first();
	}
	collapseRow(lastExpanded);
	expandRow(prev);
	});
});

// automatically expand the first row that has an error
function selectFuzzyRow() {
	var tableRow = $('tr.error').first();
	expandRow(tableRow);
}

function expandRow(row) {
	lastExpanded = row;
	var divs = row.find('td > div');
	var icon = row.find('button > i');
	divs.collapse('show');
	icon.toggleClass('icon-chevron-down');
	icon.toggleClass('icon-chevron-right');
}

function collapseRow(row) {
	row.find('td > div').collapse('hide');
	var icon = row.find('button > i');
	icon.toggleClass('icon-chevron-down');
	icon.toggleClass('icon-chevron-right');

}

function toggleRow(row) {
	lastExpanded = row;
	var divs = row.find('td > div');
	divs.collapse('toggle');
	var icon = row.find('button > i');
	icon.toggleClass('icon-chevron-down');
	icon.toggleClass('icon-chevron-right');
}