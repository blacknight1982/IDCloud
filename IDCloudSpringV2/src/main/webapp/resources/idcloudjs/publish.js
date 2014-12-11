$( document ).ready(function() {
	
	bkLib.onDomLoaded(function() {
		new nicEditor({fullPanel: true, maxHeight : 450}).panelInstance('inspiration_editor');
	});
  	
	$(initTagCreationDialog);
});

/**
 * Tag Creating Dialog
 */ 
function initTagCreationDialog() {
	var dialog, form;
	tagName = $("#tagName"),
	tagColor = $("#tagColor");

	dialog = $("#dialog-form").dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
		buttons: {
			"Create": addTag,
	        Cancel: function() {
	        dialog.dialog( "close" );
	       }
	    },
	    close: function() {
	        form[0].reset();
	    }
	});
	
	form = dialog.find("#tag-creating-form");
	
	function addTag() {
		var newCheckbox = $('<input type="checkbox" />');
		var newTag = $('<span>'+tagName.val()+'</span>');
		var valid = true;
		valid = valid && checkLength( tagName, "Tag Name doesn't meet required length", 1, 255 );
		valid = valid && checkLength( tagColor, "Tag Color doesn't meet requirement", 7, 7);
		
		if (valid){
			
			var xmlhttp = new XMLHttpRequest();
			var url = "/IDCloudSpringV2/inspiration/rest/tag";
			var createSuccessful = true;
			var newtag = {
				    name: tagName.val(),
				    color: tagColor.val()
			};
			xmlhttp.open("POST", url, false);
			xmlhttp.setRequestHeader("Content-Type","application/json");
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status != 202) {
					alert("Creating of new tag:" + tagName.val() +" failed!");
					createSuccessful = false;
				}
			}
			xmlhttp.send(JSON.stringify(newtag));
			
			if(createSuccessful){
				newCheckbox.attr("name",tagName.val());
				newCheckbox.attr("checked","true");
				newTag.css("background-color",tagColor.val());
				newTag.css("color",invertColor(tagColor.val()));
				newTag.css("font-size","18px");
				newTag.attr("name",tagName.val());
				newTag.attr("class","tag");
				newTag.attr("id","no");
				$("#publish_newtagpool").append(newCheckbox);
				$("#publish_newtagpool").append(newTag);
			}
			dialog.dialog("close");
		}
	}
	
	$("#publish_newtag").on( "click", function() {
	      dialog.dialog( "open" );
	});	
}

function validateForm(){
	var newInspirationTitle = $("#inspiration_title-edit").val();
	return checkLength($("#inspiration_title-publish"),"Title length not meet requirement",1,255);
}