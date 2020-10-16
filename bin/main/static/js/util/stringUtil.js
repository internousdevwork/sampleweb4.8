function bytes(s){
	s = (s === null || typeof s === 'undefined') ? '' : s;
	return encodeURI(s).replace(/%../g, "*").length;
}

function isEmpty(s) {
	return s === null || s === '' || typeof s === 'undefined';
}