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
	
	$("#readingback").click(function(){
		$('#wrapper').scrollTo("#panel1", 1000);	
	});
	
	$(".inspirationlink").click(function(){
		$("#readingframe").attr("src",$(this).attr("id"));
		$('#wrapper').scrollTo("#panel2", 1000);
	});
}