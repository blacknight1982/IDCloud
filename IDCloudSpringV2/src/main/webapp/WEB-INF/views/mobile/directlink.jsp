<!DOCTYPE html>
<html> 
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">
	<title>IDCloud Mobile</title>
	<link rel="stylesheet" media="screen" href="/IDCloudSpringV2/resources/mobile/styles/idcloudm.css" />
	<link rel="stylesheet" media="screen" href="/IDCloudSpringV2/resources/mobile/jquerym/jquery.mobile-1.4.5.min.css" />
	<script src="/IDCloudSpringV2/resources/mobile/jquerym/jquery-2.0.3.js"></script>
	<script src="/IDCloudSpringV2/resources/mobile/jquerym/jquery.mobile-1.4.5.js"></script>
	<script src="/IDCloudSpringV2/resources/mobile/js/idcloudmdirectlink.js"></script>
</head>

<body>
	<div data-role="page" id="minspirationdetails">
		<header data-role="header" data-position="fixed">
			<a href="/IDCloudSpringV2/inspiration/index" data-ajax="false" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-notext ui-icon-back"></a>
			<h1 id="minspirationtitle"></h1>
		</header><!-- /header -->
		
		<div id="mdetail" data-role="main" class="ui-content">
			
		</div><!-- /content -->
		
		<footer data-role="footer" data-position="fixed" >
			<nav data-role="navbar">
				<ul>
					<li><a href="/IDCloudSpringV2/inspiration/index" data-ajax="false" data-icon="cloud" class="ui-btn-active">Inspiration</a></li>
					<li><a id="linktommap" href="/IDCloudSpringV2/inspiration/map" data-ajax="false" data-icon="location">Map</a></li>
					<li><a href="/IDCloudSpringV2/login" data-ajax="false" data-icon="user">Sign In</a></li>
				</ul>
			</nav><!-- /navbar -->
		</footer>
	</div>
	
	<div id="lockPane" class="LockOff"></div>
</body>
</html>