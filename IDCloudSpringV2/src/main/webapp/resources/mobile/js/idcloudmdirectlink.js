$( document ).ready(function() {
	
	var urlString = document.URL;
	var inspirationID = urlString.substr(urlString.lastIndexOf('/') + 1);
	var webpagebaseURL = 'http://idcloud.info/webcontents/en';
	console.log(inspirationID);
	
	$.ajax({
		  dataType: "json",
		  url: "/IDCloudSpringV2/inspiration/rest/"+inspirationID,
		  success: function(data){
			  $("#minspirationtitle").text(data.title);
			  $("#mdetail").load(encodeURI(webpagebaseURL+data.mainPageLocation),function(){});
		  }
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