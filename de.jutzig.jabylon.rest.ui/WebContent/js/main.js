// The root URL for the RESTful services
var rootURL = "http://localhost:8080/jabylon/api/";

var currentWine;



// Nothing to delete in initial application state
$('#btnDelete').hide();

// Register listeners
$('#btnSearch').click(function() {
	search($('#searchKey').val());
	return false;
});

// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e) {
	if (e.which == 13) {
		search($('#searchKey').val());
		e.preventDefault();
		return false;
	}
});

$('#btnAdd').click(function() {
	newWine();
	return false;
});

$('#btnSave').click(function() {
	if ($('#wineId').val() == '')
		addWine();
	else
		updateWine();
	return false;
});

$('#btnDelete').click(function() {
	deleteWine();
	return false;
});

$('#wineList a').live('click', function() {
	findById($(this).data('identity'));
});

// Replace broken images with generic wine bottle
$("img").error(function() {
	$(this).attr("src", "pics/generic.jpg");

});

function search(searchKey) {
	if (searchKey == '')
		findAll();
	else
		findByName(searchKey);
}

function newWine() {
	$('#btnDelete').hide();
	currentWine = {};
	renderDetails(currentWine); // Display empty form
}

function findAll(url) {
	console.log('findAll');
	$.ajax({
		type : 'GET',
		url : url,
		dataType : "json", // data type of response
		success : renderList
	});
}



function findByName(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type : 'GET',
		url : rootURL + '/search/' + searchKey,
		dataType : "json",
		success : renderList
	});
}

function findById(id) {
	console.log('findById: ' + id);
	$.ajax({
		type : 'GET',
		url : rootURL + '/' + id,
		dataType : "json",
		success : function(data) {
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			currentWine = data;
			renderDetails(currentWine);
		}
	});
}

function addWine() {
	console.log('addWine');
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : rootURL,
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Wine created successfully');
			$('#btnDelete').show();
			$('#wineId').val(data.id);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('addWine error: ' + textStatus);
		}
	});
}

function updateWine() {
	console.log('updateWine');
	$.ajax({
		type : 'PUT',
		contentType : 'application/json',
		url : rootURL + '/' + $('#wineId').val(),
		dataType : "json",
		data : formToJSON(),
		success : function(data, textStatus, jqXHR) {
			alert('Wine updated successfully');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('updateWine error: ' + textStatus);
		}
	});
}

function deleteWine() {
	console.log('deleteWine');
	$.ajax({
		type : 'DELETE',
		url : rootURL + '/' + $('#wineId').val(),
		success : function(data, textStatus, jqXHR) {
			alert('Wine deleted successfully');
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('deleteWine error');
		}
	});
}

function renderList(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an
	// object (not an 'array of one')
//	var list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	var list = data.children;
	$('#projects tr').remove();
	$.each(list, function(index, project) {
		$('#projects').append(
				'<tr><td><a href="./'+project.name+'/index.html">'+ project.name + '</a><p>Summary stuff...</p></td>'
				+ '<td><div class="progress"><div class="bar bar-success" style="width: '+project.percentComplete+'%;"></div></div></td></tr>');
//				'<li><a href="#" data-identity="' + project.name + '">' + project.name
//						+ '</a></li>');
	});
}

function renderDetails(wine) {
	$('#wineId').val(wine.id);
	$('#name').val(wine.name);
	$('#grapes').val(wine.grapes);
	$('#country').val(wine.country);
	$('#region').val(wine.region);
	$('#year').val(wine.year);
	$('#pic').attr('src', 'pics/' + wine.picture);
	$('#description').val(wine.description);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	var wineId = $('#wineId').val();
	return JSON.stringify({
		"id" : wineId == "" ? null : wineId,
		"name" : $('#name').val(),
		"grapes" : $('#grapes').val(),
		"country" : $('#country').val(),
		"region" : $('#region').val(),
		"year" : $('#year').val(),
		"picture" : currentWine.picture,
		"description" : $('#description').val()
	});
}



function lookup() {
//	var uri = rootURL+path;
//	console.log('lookup '+uri);
	$.ajax({
		type : 'GET',
		url : 'http://localhost:8080/jabylon/api/jabylon/master?depth=2',
		dataType : "json", // data type of response
		success : renderProject
	});
}

function renderProject(data) {
	var list = data.children;
	$('#locales tr').remove();
	$.each(list, function(index, locale) {
		$('#locales').append(
				//TODO: need server side solution to get the right name
				'<tr><td><a href="./'+locale.locale+'/index.html">'+ locale.locale + '</a><p>Summary stuff...</p></td>'
				+ '<td><div class="progress"><div class="bar bar-success" style="width: '+locale.percentComplete+'%;"></div></div></td></tr>');
//				'<li><a href="#" data-identity="' + project.name + '">' + project.name
//						+ '</a></li>');
	});
}