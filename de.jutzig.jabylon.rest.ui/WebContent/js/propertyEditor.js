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
		var current = lastExpanded;
		var next = current.next();
		if (next.length == 0) {
			next = $('tr div.collapse').parents('tr').last();
		}
		collapseRow(current);
		expandRow(next);
	});

	shortcut.add("Ctrl+Up", function() {
		var current = lastExpanded;
		var prev = current.prev();
		if (prev.length == 0) {
			prev = $('tr div.collapse').parents('tr').first();
		}
		collapseRow(current);
		expandRow(prev);
	});
	
	
	//this disables the default submit behaviour and instead does a post to the wicket submit URL
	// see https://github.com/jutzig/jabylon/issues/52
	$('#properties-form').submit(function(event) {
		event.preventDefault();
		var table = $('#table').dataTable();
		var sData = $('textarea', table.fnGetNodes()).serialize();
		var form = $('#properties-form');
		var url = form.attr('action');
		$.post(url, sData);
		location.reload();
		return true;
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
	icon.addClass('icon-chevron-down');
	icon.removeClass('icon-chevron-right');
}

function collapseRow(row) {
	row.find('td > div').collapse('hide');
	var icon = row.find('button > i');
	icon.removeClass('icon-chevron-down');
	icon.addClass('icon-chevron-right');

}

function toggleRow(row) {
	lastExpanded = row;
	var divs = row.find('td > div');
	divs.collapse('toggle');
	var icon = row.find('button > i');
	icon.toggleClass('icon-chevron-down');
	icon.toggleClass('icon-chevron-right');
}

// toggle section with expand button
function toggle(row) {
	var parent = $(row).parents('tr');
	toggleRow(parent);
}
