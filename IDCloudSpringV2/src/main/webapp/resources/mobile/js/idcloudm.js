

$( document ).ready(function() {
	
	var map;
	var markerBounds;
	
	lockScreenAndWait("Loading...");
	
	var dataid;
	var webpagebaseURL = 'http://idcloud.info/webcontents/en';
	
	$.ajax({
		  dataType: "json",
		  url: "/IDCloudSpringV2/inspiration/rest/inspiration",
		  success: function(data){		  
			  for (i=0;i<data.length;i++){
				  var appendInspiration = '<li style="margin-bottom: 0.5em"><a href="#minspirationdetails" data-id="'+data[i].mainPageLocation+'"><h1>'+data[i].title+'</h1><p>'+data[i].authorNickname+'</p><p>';
				  for(j=0 ; j<data[i].tags.length;j++){
					  appendInspiration += '<span style="margin-right:0.25em; color:'+data[i].tags[j].complementaryColorInHex+'; background:'+data[i].tags[j].color+'">'+data[i].tags[j].name+'</span>';
				  }
				  appendInspiration += '</p></a></li>';
				  $("#minspirationlist").append(appendInspiration);
				  $("#minspirationlist").listview('refresh');
				  $("#minspirationlist li a").on( "click", function( event ) {
						$("#mdetail").empty();
						lockScreenAndWait("Loading...");
					    $("#mdetail").load(encodeURI(webpagebaseURL+$(this).attr('data-id')),function(){
					    	unlockScreen();
					    });
				  });
			  }
			  
			  unlockScreen();
		  }
	});

	
	
	/*
	 * Google Maps documentation: http://code.google.com/apis/maps/documentation/javascript/basics.html
	 * Geolocation documentation: http://dev.w3.org/geo/api/spec-source.html
	 */
	$(document).on("pagecreate", "#mmap", function () {
		var defaultLatLng = new google.maps.LatLng(34.0983425, -118.3267434);
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
				zoom : 10,
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
	});
	
	jQuery( "#mmap" ).on( "pageshow", function( event ) {
		google.maps.event.trigger(map, 'resize');
		map.fitBounds(markerBounds);
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