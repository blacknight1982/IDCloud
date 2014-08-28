function jumpToTime(timecell)
{
	var timeString = timecell.innerHTML;
	var seconds = parseHHMMSStoSeconds(timeString)
	document.getElementById('demo').currentTime = seconds;
}
function parseHHMMSStoSeconds(hhmmssString)
{
	var a = hhmmssString.split(/:|,/);
	var seconds = (+a[0]) * 60 * 60 + (+a[1]) * 60 + (+a[2]);
	return seconds;
}
function loadTextContent(menuitem)
{
	document.getElementById('content').innerHTML = "contentLoaded";
}

function getMP3Path(){
	document.getElementById('demo').src = document.getElementById('mp3path').innerHTML;
}	