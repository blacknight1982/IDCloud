function modeswitch(inspirationid){
	
	$("#directlinkpanel").load("./management/edit/"+inspirationid,function(){
		$("#editswitch").css("visibility", "hidden");
		$("#directlinkhome").attr("href", "./"+inspirationid);
		$("#directlinkhome").text("Back without Saving");
	});
	
}