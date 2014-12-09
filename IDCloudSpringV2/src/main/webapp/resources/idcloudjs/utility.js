/**
 * Get the complementary 
 * @param hexTripletColor
 * @returns {String} Complementary color returned by HEX format
 */
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

/**
 * Check the value length of input object o. If check falied, alert n
 * @param o - input object
 * @param n - warning message
 * @param min - minimum length
 * @param max - maximun length
 * @returns {Boolean} return true if length meet requirement, else return false
 */
function checkLength( o, n, min, max ) {
    if ( (o.val().length <= max) && (o.val().length >= min) ) {
    	
      return true;
    } else {
      alert(n);
      return false;
    }
}

/**
 * Transform rgs format color to HEX format color. Eg. from rgb(255,255,255) to	#FFFFFF
 * @param rgb - Transform rgs format color
 * @returns {String} - HEX format color
 */
function rgb2hex(rgb){
	 rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	 return "#" +
	  ("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
	  ("0" + parseInt(rgb[3],10).toString(16)).slice(-2);
}