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
	<script src="/IDCloudSpringV2/resources/mobile/js/idcloudmmap.js"></script>
	
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB55kMR7IuDEHQHqYkUDOQA1e-AmzX5yek"></script>
    
</head>
<body>

	<div data-role="page" id="mmap" data-url="mmap">
		<header data-role="header" data-position="fixed">
			<a href="/IDCloudSpringV2/inspiration/index" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-notext ui-icon-back"></a>
			<h1>Locations</h1>
		</header><!-- /header -->
		
		<div role="main" class="ui-content" id="map-canvas">
		</div>
		
		<footer data-role="footer" data-position="fixed" >
			<h1>Powered By IDCloud</h1>
		</footer>
	</div>
	
	<div id="lockPane" class="LockOff"></div>
</body>
</html>