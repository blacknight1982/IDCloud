audiojs.events.ready(function(){
	audiojs.createAll();
});

$(document).ready(function(){
  $(".menuitem").click(function(){
  	$("#content").load("./contentHTML/"+$(this).text()+".html");
  	$("#demo").attr("src","./audios/"+$(this).text()+".mp3");
  });
});