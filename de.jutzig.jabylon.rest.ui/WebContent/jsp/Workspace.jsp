<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../static/css/main.css" rel="stylesheet">
<title>Jabylon</title>
</head>
<body>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script src="../static/bootstrap/js/bootstrap.min.js"></script>
	<script src="../static/js/main.js"></script>
	<script type="text/javascript">
		// Retrieve wine list when application starts
		findAll(rootURL + '?depth=2');
	</script>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<a class="brand" href="#">Jabylon</a>
			<ul class="nav">
				<li class="active"><a href="#">Home</a></li>
				<li class="divider-vertical"></li>
				<li><a href="#">Settings</a></li>
				<li class="divider-vertical"></li>
				<li><a href="#">Login</a></li>
				<li class="divider-vertical"></li>
				<li><a href="#">Help</a></li>
				<li class="divider-vertical"></li>
				<li>
					<form class="navbar-search pull-right">
						<input type="text" class="search-query" placeholder="Search">
					</form>
				</li>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="span12">
			<div class="row">
				<div class="span3">
					<div class="well well-large">
						<h4>Activity Stream</h4>
						<span class="label label-info">Info</span>
						<p>Nicole updated /x/y/props</p>
						<span class="label label-info">Info</span>
						<p>Joe reviewed /x/y/props</p>
					</div>
				</div>
				<div class="span9">
					<table class="table table-striped table-hover">
						<!-- <caption>Projects</caption> -->
						<thead>
							<tr>
								<th>Project Name</th>
								<th>Progress</th>
							</tr>
						</thead>
						<tbody id="projects">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>