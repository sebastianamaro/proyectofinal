$(function() {	
	$('.pagination li').each(function() {
		var cantidad = $(this).find("a").length;
		if(cantidad==0){
			var texto = $(this).text();
			$(this).text("");
			$(this).append($("<a>", { href: '#' }).text(texto));
		}
	});
	var params = window.location.search.split(/\?|\&/);
    params.forEach( function(it) {
        if (it) {
            var param = it.split("=");
            if (param[0] !== 'page'){
				$(".pagination li > a").each(function() {
					var _href = $(this).attr("href"); 
					if (_href !== '#'){
						$(this).attr("href", _href + '&'+param[0]+'='+param[1]);
					}
				});
            }
        }
    });
});	