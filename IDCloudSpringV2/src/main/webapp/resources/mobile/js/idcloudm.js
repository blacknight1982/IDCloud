$( document ).ready(function() {
	
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