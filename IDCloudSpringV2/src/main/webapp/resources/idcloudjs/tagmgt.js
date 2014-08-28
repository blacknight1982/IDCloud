/**
 * Tag Drap and Drop
 */ 

$(initPageTabs);

$(initInspirationSel);

$(initDragAndDropTags);

$(initTagRemovable);

$(initTagPool);

$(initTagCreationDialog);

$(initInspirationPool);

$(initInspirationRemovable);


function initPageTabs(){
	$( "#tagmgt-tabs" ).tabs({
			beforeLoad: function( event, ui ) {
				$("#tagcontainer").load("./management/"+$("#inspiration-sel").val(),function(){
					initDragAndDropTags();
				});
			}
	});
}
	 
function initDragAndDropTags() {
	
	/**
	 * init draggable tag status
	 */
	
	$("#tagcontainer").data("statusChanged",false);
	
	$("#tagcontainer").data("inspirationTagOperation","");
	
	$("#tagFromBox span").data("box","fromBox");
	
	$("#tagToBox span").data("box","toBox");
	
	$(".draggableItem").draggable({
		containment: '#tagcontainer',
		cursor: 'move',
		helper: 'clone',
		stop: handleDragStop,
		//revert: true
		//stack: '#draggableBox div'
	});

	/**
	 * init droppable tag status
	 */
	$(".droppableFromBox").droppable( {
	    drop: handleDropFromBoxEvent
	});
	
	$(".droppableToBox").droppable( {
	    drop: handleDropToBoxEvent
	});
	
}

function draggableItemHelper (event) {
	
	  return '<span class="draggableItemHelper">'+event.target.id+'</span>';
}

function handleDragStop( event, ui ) {
	  var offsetXPos = parseInt( ui.offset.left );
	  var offsetYPos = parseInt( ui.offset.top );
	  //alert( "Drag stopped!\n\nOffset: (" + offsetXPos + ", " + offsetYPos + ")\n");
}

function handleDropFromBoxEvent( event, ui ) {
	var draggable = ui.draggable;
	var box = draggable.data("box");
	if(box == "toBox"){
		draggable.position( { of: event, my: 'center' } );
		draggable.data("box","fromBox");
		var inspirationTagOperation=$("#tagcontainer").data("inspirationTagOperation");
		inspirationTagOperation = inspirationTagOperation+"-"+draggable.attr("id")+",";
		$("#tagcontainer").data("inspirationTagOperation",inspirationTagOperation);
		$("#tagcontainer").data("statusChanged",true);
	}
	
}

function handleDropToBoxEvent( event, ui ) {
	var draggable = ui.draggable;
	var box = draggable.data("box");
	if(box == "fromBox"){
		draggable.position( { of: event, my: 'center' } );
		draggable.data("box","toBox");
		var inspirationTagOperation=$("#tagcontainer").data("inspirationTagOperation");
		inspirationTagOperation = inspirationTagOperation+"+"+draggable.attr("id")+",";
		$("#tagcontainer").data("inspirationTagOperation",inspirationTagOperation);
		$("#tagcontainer").data("statusChanged",true);
	}
}

function initTagRemovable(){
	/**
	 * init removable tag status
	 */
	$("#tagpool").data("removeTagIDs","");
	
	$("#tagpool span").hover(
		function(){
			$(this).text("Single click to remove tag");
		},
		function(){
			$(this).text($(this).attr("name"));
		}
	);
	
	$("#tagpool span").click(
		function(){
			var tagID = $(this).attr("id");
			$(this).remove();
			if(tagID != "no"){
				var removeTagIDs = $("#tagpool").data("removeTagIDs");
				if (0 == removeTagIDs.length){
					$("#tagpool").data("removeTagIDs",tagID);
				}
				else{
					$("#tagpool").data("removeTagIDs",removeTagIDs+","+tagID);
				}
			}
		}
	);
}

function initTagPool(){
	$("#tagpool").load("./management/tagpool",function(){
		initTagRemovable();
	});
	
	$("#save-tag").button().on( "click", function() {
		var tagCount = $("#tagpool div").children("[id='no']").length;
		var tagArray = $("#tagpool div").children("[id='no']");
		var tagNameArray = new Array(tagCount);
		var tagColorArray = new Array(tagCount);
		var removeTagIDs = $("#tagpool").data("removeTagIDs");
		for (i = 0; i < tagCount; i++) {
			tagNameArray[i]=$(tagArray[i]).attr("name");
			tagColorArray[i]=rgb2hex($(tagArray[i]).css("background-color"));
		}
	    alert(tagCount);
	    $.post( "./management/create_delete", 
	    		{ "tagNameArray": tagNameArray, "tagColorArray": tagColorArray, "removeTagIDs": removeTagIDs},
	    			function(response) {
	    				if (response) {
	    					$("#tagpool").load("./management/tagpool",function(){
	    						initTagRemovable();
	    					});
	    					
	    					var inspirationID = $("#inspiration-sel").val();
	    					$("#tagcontainer").load("./management/"+inspirationID,function(){
	    						initDragAndDropTags();
	    					});
	    				}
	    			});
	});	
}

function initInspirationSel(){
	
	$("#tagcontainer").load("./management/"+$("#inspiration-sel").val(),function(){
		initDragAndDropTags();
	});
	
	$("#inspiration-sel").data("lastInspirationID",$("#inspiration-sel").val());
	
	$("#inspiration-sel").change(function () {
        /**
         * Get Confirmation before send inspiration tag update post to server
         */
		var confirmChange = false;
		var lastInspirationID = $("#inspiration-sel").data("lastInspirationID");
		$("#inspiration-sel").data("lastInspirationID",this.value);
		
		if($("#tagcontainer").data("statusChanged")){
			confirmChange = window.confirm("Changes for inspiration with ID: "+ lastInspirationID +" will be applied");
		}
		if(confirmChange){
			var inspirationTagOperation = $("#tagcontainer").data("inspirationTagOperation");
			$.post( "./management/inspirationtags/"+lastInspirationID, 
		    		{ "inspirationTagOperation": inspirationTagOperation},
		    			function(response) {
		    				if (response) {
		    					//window.location.reload();
		    				}
		    			});
		}
		
		var inspirationid = this.value;
        if(inspirationid!=""){
        	$("#tagcontainer").load("./management/"+inspirationid,function(){
        		initDragAndDropTags();
        	});
        }
    });
	
	$("#save-tag2").button().on( "click", function() {
		var confirmChange = false;
		var inspirationID = $("#inspiration-sel").val();
		if($("#tagcontainer").data("statusChanged")){
			confirmChange = window.confirm("Changes for inspiration with ID: "+ inspirationID +" will be applied");
		}
		if(confirmChange){
			var inspirationTagOperation = $("#tagcontainer").data("inspirationTagOperation");
			$.post( "./management/inspirationtags/"+ inspirationID, 
		    		{ "inspirationTagOperation": inspirationTagOperation},
		    			function(response) {
		    				if (response) {
		    					$("#tagcontainer").load("./management/"+inspirationID,function(){
		    						initDragAndDropTags();
		    					});
		    				}
		    			});
		}
		else{
			window.alert("Nothing was changed for inspiration with ID: "+ inspirationID);
		}
	});
}

function initInspirationPool(){
	$("#inspirationcontainer").load("./management/inspirationpool",function(){
		$(initInspirationRemovable);
	});
}

function initInspirationRemovable(){
	$(".removeinspiration").hover(
			function(){
				$(this).text("Single click to remove inspiration");
			},
			function(){
				$(this).text($(this).attr("name"));
			}
	);
	
	$(".removeinspiration").click(function(){
			var inspirationID = $(this).attr("name");
			var confirmRemove = window.confirm("Remove inspiration with ID "+ inspirationID);
			if(confirmRemove){
				$("#inspirationcontainer").load("./management/inspirationremove/"+inspirationID,function(){
					$(initInspirationRemovable);
				});
			}
		}
	);
}


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
			"Create a Tag": addTag,
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
		var newTag = $('<span>'+tagName.val()+'</span>');
		var valid = true;
		valid = valid && checkLength( tagName, "Tag Name", 1, 255 );
		valid = valid && checkLength( tagColor, "Tag Color", 7, 7);
		
		if (valid){
			newTag.css("background-color",tagColor.val());
			newTag.css("color",invertColor(tagColor.val()));
			newTag.css("font-size","18px");
			newTag.attr("name",tagName.val());
			newTag.attr("class","tag");
			newTag.attr("id","no");
			$("#tagpool div").append(newTag);
			dialog.dialog("close");
			$(initTagRemovable);
		}
	}
	
	$("#create-tag").button().on( "click", function() {
	      dialog.dialog( "open" );
	});	
}

function invertColor(hexTripletColor) {
    var color = hexTripletColor;
    color = color.substring(1);           // remove #
    color = parseInt(color, 16);          // convert to integer
    color = 0xFFFFFF ^ color;             // invert three bytes
    color = color.toString(16);           // convert to hex
    color = ("000000" + color).slice(-6); // pad with leading zeros
    color = "#" + color;                  // prepend #
    return color;
}

function checkLength( o, n, min, max ) {
    if ( (o.val().length <= max) && (o.val().length >= min) ) {
    	
      return true;
    } else {
      alert("Validation Not Passed");
      return false;
    }
}

function rgb2hex(rgb){
	 rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	 return "#" +
	  ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[3],10).toString(16)).slice(-2);
}