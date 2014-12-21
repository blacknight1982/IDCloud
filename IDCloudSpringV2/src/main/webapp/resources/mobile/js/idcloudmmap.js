$( document ).ready(function() {
	
	var map;
	var markerBounds;
	
	/*
	 * Google Maps documentation: http://code.google.com/apis/maps/documentation/javascript/basics.html
	 * Geolocation documentation: http://dev.w3.org/geo/api/spec-source.html
	 */
	var defaultLatLng = new google.maps.LatLng(39.764339, -104.8551114);
	if (navigator.geolocation) {
		function success(pos) {
			// Location found, show map with these coordinates
			drawMap(new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude));
		}
		function fail(error) {
			drawMap(defaultLatLng);
			  // Failed to find location, show default map
		}
		// Find the users current position.  Cache the location for 5 minutes, timeout after 6 seconds
		navigator.geolocation.getCurrentPosition(success, fail, {
			maximumAge : 500000,
			enableHighAccuracy : true,
			timeout : 6000
		});
	} else {
		drawMap(defaultLatLng);
		  // No geolocation support, show default map
	}
	
	function drawMap(latlng) {
		var myOptions = {
			zoom : 3,
			center : latlng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("map-canvas"), myOptions);
		// Add an overlay to the map of current lat/lng
		
		var marker, i, point;
		markerBounds = new google.maps.LatLngBounds();
				
		$.ajax({
			  dataType: "json",
			  url: "/IDCloudSpringV2/inspiration/rest/accesslocations",
			  success: function(data){	
				  
				  for (i=0;i<data.length;i++){
					 point = new google.maps.LatLng(data[i].latitude ,data[i].longitude);
					 new google.maps.Marker({
						 position: point,
						 map: map
					 });
					 markerBounds.extend(point);
				  }
			  }
		});
	}
	
	google.maps.event.addListenerOnce(map, 'idle', function(){
		map.fitBounds(markerBounds);
		google.maps.event.trigger(map, 'resize');
	});
});

/**
 * Function to lock screen when performing ajex calls
 * @param str display the information when screen being locked
 */
function lockScreenAndWait(str) 
{ 
   var lock = document.getElementById('lockPane'); 
   if (lock) 
      lock.className = 'LockOn'; 

   lock.innerHTML = str; 

}

/**
 * Function to unlock screen when finishing ajex calls
 */
function unlockScreen() 
{ 
   var lock = document.getElementById('lockPane'); 
   if (lock) 
      lock.className = 'LockOff'; 

}