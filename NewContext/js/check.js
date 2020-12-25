function trim(str){
	while(str && str.indexOf(" ") == 0)
		str = str.substring(1); 
	while(str && str.lastIndexOf(" ") == str.length-1)
		str = str.substring(0, str.length-1); 
	return str;
}
function checkByteLen(str, size){
	var byteLen = getByteLen(str);
	if(byteLen <= size){
		return true;
	}else{
		return false;
	}
}
function getByteLen(str){
	return str.replace(/[\0-\x7f]|([0-\u07ff]|(.))/g,"$&$1$2").length;
}