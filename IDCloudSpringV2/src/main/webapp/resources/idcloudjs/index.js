$(initTagFilter);
$(initSlidingPanel);

function initTagFilter(){
	$("#tagfilter").keyup(function() {
		filterInspirationByTitleAndTag();
	});
	
	$("#titlefilter").keyup(function() {
		filterInspirationByTitleAndTag();
	});
}

function filterInspirationByTitleAndTag(){
	var titlefiltertext = $("#titlefilter").val();
	var tagfiltertext = $("#tagfilter").val();
	var inspirationMatchSpanTags = $("#inspirationtable").find("span:contains('"+tagfiltertext+"')");
	var inspirationMatchTitles = $("#inspirationtable").find("a:contains('"+titlefiltertext+"')");
	$("#inspirationtable").find("tr.dynamicrows").data("display",0);
	$("#inspirationtable").find("tr.dynamicrows").data("display",0);
	$(inspirationMatchSpanTags).each(function(){
		$(this).parent().parent().data("display",$(this).parent().parent().data("display")|1);
	});
	$(inspirationMatchTitles).each(function(){
		$(this).parent().parent().data("display",$(this).parent().parent().data("display")|2);
	});
	$("#inspirationtable").find("tr.dynamicrows").each(function(index){
		if($(this).data("display")==3){
			$(this).css("display","table-row");
		}
		else{
			$(this).css("display","none");
		}
	});
}

function initSlidingPanel(){
	
	var inspirationid;
	var inspirationURL;
	$("#inspirationpanel").data("reading",true);
	
	$("#readingback").click(function(){
		var readingmode = $("#inspirationpanel").data("reading");
		if(!readingmode){
			switchModeForInspirationpanel(true,false);
		}
		$('#wrapper').scrollTo("#panel1", 1000);
	});
	
	$(".inspirationlink").click(function(){
		inspirationURL = $(this).attr("id");
		$("#readingframe").attr("src",inspirationURL);
		inspirationid = $(this).parent().parent().children(":first-child").text();
		$("#inspirationpanel").data("inspirationid",inspirationid);
		$('#wrapper').scrollTo("#panel2", 1000);
	});
	
	$("#readingedit").click(function(){
		var readingmode = $("#inspirationpanel").data("reading");
		if(readingmode){
			switchModeForInspirationpanel(false,false);
		}
		else{
			switchModeForInspirationpanel(true,true);
		}
	});
	 
	function switchModeForInspirationpanel(readingMode,changed){
		if(readingMode){
			/*if(changed){
				var confirmchange = window.confirm("Do you want to save your editing?");
				if(confirmchange){
					$.post( "./management/edit/"+inspirationid, 
				    		{"inspiration_title-edit": $("#inspiration_title-edit").val(),"inspiration_editor-edit": $("#inspiration_editor-edit").val() },
				    			function(response) {
				    				if (response) {
				    					window.location.reload();
				    				}
				    			});
				}
			}*/
			$("#inspirationpanel").empty();
			$("#inspirationpanel").html('<iframe id="readingframe"></iframe>');
			$("#readingframe").attr("src",inspirationURL);
			$("#inspirationpanel").data("reading",true);
			$("#readingedit").text("Switch to Editing Mode");
			$("#readingback").text("Back");
		}
		else{
			$("#inspirationpanel").load("./management/edit/"+inspirationid,function(){
				$("#readingedit").text("Back to Reading Mode");
				$("#readingback").text("Back without Save");
				$("#inspirationpanel").data("reading",false);
			});
		}
	}
}