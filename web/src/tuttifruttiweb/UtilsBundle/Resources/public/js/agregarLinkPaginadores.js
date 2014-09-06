$(function() {	
	$('.pagination li').each(function() {
		var cantidad = $(this).find("a").length;
		if(cantidad==0){
			var texto = $(this).text();
			$(this).text("");
			$(this).append($("<a>", { href: '#' }).text(texto));
		}
	});
});	