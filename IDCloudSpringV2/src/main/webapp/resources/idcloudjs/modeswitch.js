function modeswitch(inspirationid){
	
	lockScreenAndWait("Switching to edit mode...");
	$("#directlinkpanel").load("./management/edit/"+inspirationid,function(){
		unlockScreen();
	});
	$("#editswitch").css("visibility", "hidden");
	$("#directlinkhome").attr("href", "./"+inspirationid);
	$("#directlinkhome").text("<-Back without Saving");
}