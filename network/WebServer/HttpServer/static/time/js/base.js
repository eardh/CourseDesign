//返回顶部
jQuery(document).ready(function($){
	// browser window scroll (in pixels) after which the "back to top" link is shown
	var offset = 300,
		//browser window scroll (in pixels) after which the "back to top" link opacity is reduced
		offset_opacity = 1200,
		//duration of the top scrolling animation (in ms)
		scroll_top_duration = 700,
		//grab the "back to top" link
		$back_to_top = $('.cd-top');

	//hide or show the "back to top" link
	$(window).scroll(function(){
		( $(this).scrollTop() > offset ) ? $back_to_top.addClass('cd-is-visible') : $back_to_top.removeClass('cd-is-visible cd-fade-out');
		if( $(this).scrollTop() > offset_opacity ) { 
			$back_to_top.addClass('cd-fade-out');
		}
	});

	//smooth scroll to top
	$back_to_top.on('click', function(event){
		event.preventDefault();
		$('body,html').animate({
			scrollTop: 0 ,
		 	}, scroll_top_duration
		);
	});

});

//顶部右上角相册
$(function(){
	
	var li = $('.mess').find('li');
		
		
	var mess = {
		random: function(low, up){
			return Math.floor((Math.random()*(low-up+1))+up);
		},
		css: function(){
			var rotate = this.random(-20, 20),
				zindex = this.random(0, li.length),
				degrees = 'rotate('+rotate+'deg)';
			return {'degrees': degrees, 'zindex': zindex }
		},
		degrees: function(element){
			var random = this.css();
			$(element).css({
				'-webkit-transform': random.degrees,
				'-moz-transform': random.degrees,
				'-o-transform': random.degrees,
				'z-index': random.zindex
			});
		},
		animate: function(element, x, y){
			$(element).animate({
				top: y,
				left: x
			}, 500);
		}
	}
	
	li.each(function(){
		mess.degrees($(this));
	});
			
	$('.make').toggle(function(){
		li.each(function(){
			var t = $(this), x = mess.random(-400, 270), y = mess.random(-80, 150);
			mess.animate(t, x, y);
			mess.degrees(t);
		});
		$(this).text('合回');
	}, function(){
		li.each(function(){
			mess.animate($(this), 0, 0);
		});
		$(this).text('咔嚓');
		return false;
	});
	
			
});