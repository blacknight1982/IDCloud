$(initTagFilter);

function initTagFilter(){
	$("#tagfilter").keyup(function() {
		/*var tagfiltertext = $(this).val();
		var inspirationMatchSpanTags = $("#inspirationtable").find("span:contains('"+tagfiltertext+"')");
		$("#inspirationtable").find("tr.dynamicrows").css("display","none");
		$(inspirationMatchSpanTags).parent().parent().css("display","table-row");*/
		filterInspirationByTitleAndTag();
	});
	
	$("#titlefilter").keyup(function() {
		/*var titlefiltertext = $(this).val();
		var inspirationMatchTitles = $("#inspirationtable").find("a:contains('"+titlefiltertext+"')");
		$("#inspirationtable").find("tr.dynamicrows").css("display","none");
		$(inspirationMatchTitles).parent().parent().css("display","table-row");*/
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
		//console.log($(this).parent().parent().data("display")+"\n");
	});
	$(inspirationMatchTitles).each(function(){
		$(this).parent().parent().data("display",$(this).parent().parent().data("display")|2);
		//console.log($(this).parent().parent().data("display")+"\n");
	});
	$("#inspirationtable").find("tr.dynamicrows").each(function(index){
		console.log(index+" --- "+$(this).data("display")+"\n");
		if($(this).data("display")==3){
			$(this).css("display","table-row");
		}
		else{
			$(this).css("display","none");
		}
	});
	//$(inspirationMatchSpanTags).parent().parent().data("display",1);
	//$(inspirationMatchTitles).parent().parent().css("display","table-row");
}