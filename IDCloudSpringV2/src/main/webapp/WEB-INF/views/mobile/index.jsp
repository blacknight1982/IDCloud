<!DOCTYPE html>
<html> 
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
	<title>IDCloud Mobile</title>
	<link rel="stylesheet" media="screen" href="/IDCloudSpringV2/resources/mobile/styles/idcloudm.css" />
	<link rel="stylesheet" media="screen" href="/IDCloudSpringV2/resources/mobile/jquerym/jquery.mobile-1.4.5.min.css" />
	<script src="/IDCloudSpringV2/resources/mobile/jquerym/jquery-1.8.0.js"></script>
	<script src="/IDCloudSpringV2/resources/mobile/jquerym/jquery.mobile-1.4.5.js"></script>
	<script src="/IDCloudSpringV2/resources/mobile/js/idcloudm.js"></script>
</head>
<body>
	<div id="mindex" data-role="page">
		<div id="mpanel" data-theme="b" data-role="panel">
		    <ul data-role="listview">
		    	<li data-icon="delete"><a href="#" data-rel="close">Close menu</a></li>
		    	<li><a href="/IDCloudSpringV2/login" data-ajax="false">Sign In</a></li>
		    	<li><a href="/IDCloudSpringV2/logout" data-ajax="false">Sign Out</a></li>
		    	<li><a href="#mcontact">Contact</a></li>
		    </ul>
		</div><!-- /panel -->
		<header id="mheader" data-role="header" data-position="fixed">
					<a href="#mpanel" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-notext ui-icon-bars"></a>
					<h1 id="mtitle" >IDCloud</h1>
		</header><!-- /header -->
		<div id="mcontent" data-role="main" class="ui-content">
			<ul id="minspirationlist" data-role="listview">
				<li style="margin-bottom: 0.5em">
					<a href="#">
						<h1>Test Header</h1>	
						<p>Author</p>
						<p>2014-11-13 03:22</p>							
						<p><span style="margin-right:0.25em; color:blue; background:yellow">Tag</span><span style="color:red; background:white">Tag2</span></p>
					</a>
				</li>
			</ul>
		</div><!-- /content -->
		<footer data-role="footer" data-position="fixed">
			<nav data-role="navbar">
				<ul>
					<li><a href="#mindex" data-icon="cloud" class="ui-btn-active">Inspiration</a></li>
					<li><a href="#" data-icon="location">Map</a></li>
					<li><a href="/IDCloudSpringV2/login" data-ajax="false" data-icon="user">Sign In</a></li>
				</ul>
			</nav>
		</footer>
	</div><!-- /page -->
	
	<div data-role="page" id="minspirationdetails">
		<header data-role="header" data-position="fixed">
			<a href="#" data-rel="back" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-notext ui-icon-back"></a>
			<h1>Let's see an Inspiration</h1>
		</header><!-- /header -->
		
		<div id="mdetail" data-role="main" class="ui-content">
			
		</div><!-- /content -->
		
		<footer data-role="footer" data-position="fixed" >
			<nav data-role="navbar">
				<ul>
					<li><a href="#mindex" data-icon="cloud" class="ui-btn-active">Inspiration</a></li>
					<li><a href="#" data-icon="location">Map</a></li>
					<li><a href="/IDCloudSpringV2/login" data-icon="user">Sign In</a></li>
				</ul>
			</nav><!-- /navbar -->
		</footer>
	</div>
	
	<div data-role="page" id="mcontact">
		<header data-role="header" data-position="fixed">
			<a href="#" data-rel="back" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-notext ui-icon-back"></a>
			<h1>Contacts</h1>
		</header><!-- /header -->
		
		<div data-role="main" class="ui-content">
			<table data-role="table" class="ui-body-d ui-shadow table-stripe ui-responsive">
				<thead>
					<tr>
						<th>Name</th>
						<th>Title</th>
						<th>Email</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>John Liu</th>
						<th>Founder</th>
						<th>blacknight1982@gmail.com</th>
					</tr>
				</tbody>
			</table>
		</div><!-- /content -->
		
		<footer data-role="footer" data-position="fixed" >
			<h1>Powered By IDCloud</h1>
		</footer>
	</div>
	
	<div id="lockPane" class="LockOff"></div>
</body>
</html>