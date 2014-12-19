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
	<div data-role="page" id="msignin">
		<header data-role="header" data-position="fixed">
			<a href="/IDCloudSpringV2/inspiration/index" data-ajax="false" class="ui-btn ui-shadow ui-corner-all ui-btn-inline ui-btn-icon-notext ui-icon-back"></a>
			<h1>Please Sign In</h1>
		</header><!-- /header -->
		
		<div data-role="main" class="ui-content">
			<form action="/IDCloudSpringV2/login" data-ajax="false" method="POST">
				<div class="ui-field-contain">
			  		<label for="username">User Name:</label>
			  		<input type="email" name="idcloud_username" required>
			  		<label for="password">Password:</label>
			  		<input type="password" name="idcloud_password" required>
			  	</div>	
			  	<input id="submit" name="submit" type="submit" value="Submit"/>
			</form>
		</div><!-- /content -->
		
		<footer data-role="footer" data-position="fixed" >
			<h1>Powered By IDCloud</h1>
		</footer>
	</div>
</body>
</html>