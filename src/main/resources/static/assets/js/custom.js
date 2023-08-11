(function ($) {
   
   "use strict";

   $(window).scroll(function() {
     var scroll = $(window).scrollTop();
     var box = $('.header-text').height();
     var header = $('header').height();

     if (scroll >= box - header) {
       $("header").addClass("background-header");
     } else {
       $("header").removeClass("background-header");
     }
   });
   
   $('.filters ul li').click(function(){
        $('.filters ul li').removeClass('active');
        $(this).addClass('active');
          
          var data = $(this).attr('data-filter');
          $grid.isotope({
            filter: data
          })
        });

        var $grid = $(".grid").isotope({
          itemSelector: ".all",
          percentPosition: true,
          masonry: {
            columnWidth: ".all"
          }
        })


   const Accordion = {
     settings: {
       // Expand the first item by default
       first_expanded: false,
       // Allow items to be toggled independently
       toggle: false
     },

     openAccordion: function(toggle, content) {
       if (content.children.length) {
         toggle.classList.add("is-open");
         let final_height = Math.floor(content.children[0].offsetHeight);
         content.style.height = final_height + "px";
       }
     },

     closeAccordion: function(toggle, content) {
       toggle.classList.remove("is-open");
       content.style.height = 0;
     },

     init: function(el) {
       const _this = this;

       // Override default settings with classes
       let is_first_expanded = _this.settings.first_expanded;
       if (el.classList.contains("is-first-expanded")) is_first_expanded = true;
       let is_toggle = _this.settings.toggle;
       if (el.classList.contains("is-toggle")) is_toggle = true;

       // Loop through the accordion's sections and set up the click behavior
       const sections = el.getElementsByClassName("accordion");
       const all_toggles = el.getElementsByClassName("accordion-head");
       const all_contents = el.getElementsByClassName("accordion-body");
       for (let i = 0; i < sections.length; i++) {
         const section = sections[i];
         const toggle = all_toggles[i];
         const content = all_contents[i];

         // Click behavior
         toggle.addEventListener("click", function(e) {
           if (!is_toggle) {
             // Hide all content areas first
             for (let a = 0; a < all_contents.length; a++) {
               _this.closeAccordion(all_toggles[a], all_contents[a]);
             }

             // Expand the clicked item
             _this.openAccordion(toggle, content);
           } else {
             // Toggle the clicked item
             if (toggle.classList.contains("is-open")) {
               _this.closeAccordion(toggle, content);
             } else {
               _this.openAccordion(toggle, content);
             }
           }
         });

         // Expand the first item
         if (i === 0 && is_first_expanded) {
           _this.openAccordion(toggle, content);
         }
       }
     }
   };

  /* (function() {
     // Initiate all instances on the page
     const accordions = document.getElementsByClassName("accordions");
     for (let i = 0; i < accordions.length; i++) {
       Accordion.init(accordions[i]);
     }
   })();
   

   (function init() {
      function getTimeRemaining(endtime) {
        var t = Date.parse(endtime) - Date.parse(new Date());
        var seconds = Math.floor((t / 1000) % 60);
        var minutes = Math.floor((t / 1000 / 60) % 60);
        var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
        var days = Math.floor(t / (1000 * 60 * 60 * 24));
        return {
         'total': t,
         'days': days,
         'hours': hours,
         'minutes': minutes,
         'seconds': seconds
        };
      }
      
      function initializeClock(endtime){
      var timeinterval = setInterval(function(){
        var t = getTimeRemaining(endtime);
        document.querySelector(".days > .value").innerText=t.days;
        document.querySelector(".hours > .value").innerText=t.hours;
        document.querySelector(".minutes > .value").innerText=t.minutes;
        document.querySelector(".seconds > .value").innerText=t.seconds;
        if(t.total<=0){
         clearInterval(timeinterval);
        }
      },1000);
     }
     initializeClock(((new Date()).getFullYear()+1) + "/1/1")
   })()*/

   var context;
   var $window = $(window);

   // run this right away to set context
   if ($window.width() <= 768) {
      context = 'small';
   } else if (768 < $window.width() < 970) {
      context = 'medium';
   } else {
      context = 'large';
   }

   // refresh the page only if you're crossing into a context
   // that isn't already set
   $(window).resize(function() {
      if(($window.width() <= 768) && (context != 'small')) {
         //refresh the page
         location.reload();
      } else if ((768 < $window.width()  < 970) && (context != 'medium')) {
         location.reload();
      } else if (context != 'large') {
         location.reload();
      }
   });

   $("#modal_trigger").leanModal({
      top: 100,
      overlay: 0.6,
      closeButton: ".modal_close"
   });

   $(function() {
      // Calling Login Form
      $("#user_login_form").click(function() {
         $(".social_login").hide();
         $(".user_login").show();
         return false;
      });
      $("#cp_login_form").click(function() {
         $(".social_login").hide();
         $(".cp_login").show();
         return false;
      });


      // Calling userRegister Form
      $("#userRegister_form").click(function() {
         $(".social_login").hide();
         $(".userNew_register").show();
         $(".header_title").text('Register');
         return false;
      });
      // Calling cpRegister Form
      $("#cpRegister_form").click(function() {
         $(".social_login").hide();
         $(".cp_register").show();
         $(".header_title").text('Register');
         return false;
      });

      // Going back to Social Forms
      $(".back_btn").click(function() {
         $(".user_login").hide();
         $(".cp_login").hide();
         $(".user_register").hide();
         $(".cp_register").hide();
         $(".social_login").show();
         $(".header_title").text('Login');
         return false;
      });
   });

   $(document).on("click", ".naccs .menu div", function() {
      var numberIndex = $(this).index();
   
      if (!$(this).is("active")) {
         $(".naccs .menu div").removeClass("active");
         $(".naccs ul li").removeClass("active");
   
         $(this).addClass("active");
         $(".naccs ul").find("li:eq(" + numberIndex + ")").addClass("active");
   
         var listItemHeight = $(".naccs ul")
            .find("li:eq(" + numberIndex + ")")
            .innerHeight();
         $(".naccs ul").height(listItemHeight + "px");
      }
   });

   $('.owl-testimonials').owlCarousel({
      items:1,
      loop:true,
      dots: true,
      nav: false,
      autoplay: true,
      margin:30,
        responsive:{
           0:{
              items:1
           },
           600:{
              items:1
           },
           1000:{
              items:1
           }
        }
     })

   $('.owl-features').owlCarousel({
      items:3,
      loop:true,
      dots: true,
      nav: true,
      autoplay: true,
      margin:30,
        responsive:{
           0:{
              items:1
           },
           800:{
              items:2
           },
           1000:{
              items:3
           }
        }
     })
   
   

   // Menu Dropdown Toggle
   if($('.menu-trigger').length){
      $(".menu-trigger").on('click', function() {   
         $(this).toggleClass('active');
         $('.header-area .nav').slideToggle(200);
      });
   }


   // Menu elevator animation
   $('.scroll-to-section a[href*=\\#]:not([href=\\#])').on('click', function() {
      if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
         var target = $(this.hash);
         target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
         if (target.length) {
            var width = $(window).width();
            if(width < 991) {
               $('.menu-trigger').removeClass('active');
               $('.header-area .nav').slideUp(200);   
            }            
            $('html,body').animate({
               scrollTop: (target.offset().top) - 80
            }, 700);
            return false;
         }
      }
   });

   $(document).ready(function () {
       $(document).on("scroll", onScroll);
       
       //smoothscroll
       $('.scroll-to-section a[href^="#"]').on('click', function (e) {
           e.preventDefault();
           $(document).off("scroll");
           
           $('.scroll-to-section a').each(function () {
               $(this).removeClass('active');
           })
           $(this).addClass('active');
         
           var target = this.hash,
           menu = target;
             var target = $(this.hash);
           $('html, body').stop().animate({
               scrollTop: (target.offset().top) - 79
           }, 500, 'swing', function () {
               window.location.hash = target;
               $(document).on("scroll", onScroll);
           });
       });
   });

   function onScroll(event){
       var scrollPos = $(document).scrollTop();
       $('.nav a').each(function () {
           var currLink = $(this);
           var refElement = $(currLink.attr("href"));
           if (refElement.position().top <= scrollPos && refElement.position().top + refElement.height() > scrollPos) {
               $('.nav ul li a').removeClass("active");
               currLink.addClass("active");
           }
           else{
               currLink.removeClass("active");
           }
       });
   }


   // Page loading animation
   $(window).on('load', function() {
      if($('.cover').length){
         $('.cover').parallax({
            imageSrc: $('.cover').data('image'),
            zIndex: '1'
         });
      }

      $("#preloader").animate({
         'opacity': '0'
      }, 600, function(){
         setTimeout(function(){
            $("#preloader").css("visibility", "hidden").fadeOut();
         }, 300);
      });
   });

   

   const dropdownOpener = $('.main-nav ul.nav .has-sub > a');

    // Open/Close Submenus
    if (dropdownOpener.length) {
        dropdownOpener.each(function () {
            var _this = $(this);

            _this.on('tap click', function (e) {
                var thisItemParent = _this.parent('li'),
                    thisItemParentSiblingsWithDrop = thisItemParent.siblings('.has-sub');

                if (thisItemParent.hasClass('has-sub')) {
                    var submenu = thisItemParent.find('> ul.sub-menu');

                    if (submenu.is(':visible')) {
                        submenu.slideUp(450, 'easeInOutQuad');
                        thisItemParent.removeClass('is-open-sub');
                    } else {
                        thisItemParent.addClass('is-open-sub');

                        if (thisItemParentSiblingsWithDrop.length === 0) {
                            thisItemParent.find('.sub-menu').slideUp(400, 'easeInOutQuad', function () {
                                submenu.slideDown(250, 'easeInOutQuad');
                            });
                        } else {
                            thisItemParent.siblings().removeClass('is-open-sub').find('.sub-menu').slideUp(250, 'easeInOutQuad', function () {
                                submenu.slideDown(250, 'easeInOutQuad');
                            });
                        }
                    }
                }

                e.preventDefault();
            });
        });
    }


   function visible(partial) {
        var $t = partial,
            $w = jQuery(window),
            viewTop = $w.scrollTop(),
            viewBottom = viewTop + $w.height(),
            _top = $t.offset().top,
            _bottom = _top + $t.height(),
            compareTop = partial === true ? _bottom : _top,
            compareBottom = partial === true ? _top : _bottom;

        return ((compareBottom <= viewBottom) && (compareTop >= viewTop) && $t.is(':visible'));

    }

    $(window).scroll(function() {

        if (visible($('.count-digit'))) {
            if ($('.count-digit').hasClass('counter-loaded')) return;
            $('.count-digit').addClass('counter-loaded');

            $('.count-digit').each(function() {
                var $this = $(this);
                jQuery({
                    Counter: 0
                }).animate({
                    Counter: $this.text()
                }, {
                    duration: 3000,
                    easing: 'swing',
                    step: function() {
                        $this.text(Math.ceil(this.Counter));
                    }
                });
            });
        }
    })
    
  /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    /*지유가 작성한 jquery*/
  /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    
    $(".filter_li").mouseenter(function(event) {
  	  $(event.currentTarget).find(".filter_choice").show();
		
	})
	
	$(".filter_li").mouseleave(function(event) {
		$(event.currentTarget).find(".filter_choice").hide();
	})
	

	
	$(".filter_choice > li").click(function() {
		let house_type = $("#house_type").val();
		let house_size = $("#house_size").val();
		let house_adr = $("#house_adr").val();
		let house_fam = $("#house_fam").val();
		let house_remo = $("#house_remo").val();

		if($(event.currentTarget).parent().prev().html() == "주거형태") {
			house_type = $(event.currentTarget).children().html();
		} else if($(event.currentTarget).parent().prev().html() == "평수") {
			house_size = $(event.currentTarget).children().html();
		} else if($(event.currentTarget).parent().prev().html() == "지역") {
			house_adr = $(event.currentTarget).children().html();
		} else if($(event.currentTarget).parent().prev().html() == "가족형태") {
			house_fam = $(event.currentTarget).children().html();
		} else {
			house_remo = $(event.currentTarget).children().html();
		}
		
		
		let currentUrl = "../houseopen/houseopenList" + "?" + "house_type" + "=" + house_type + "&" + "house_size" + "=" + house_size + "&" + "house_adr" + "=" + house_adr + "&" + "house_fam" + "=" + house_fam + "&" + "house_remo" + "=" + house_remo;
		console.log(currentUrl);
		$(event.target).closest("li").children().attr("href", currentUrl);
		
	})
	
	$(".select_btn > li").click(function() {
		console.log($(event.currentTarget).closest("li").hasClass("select_type"));
		console.log($(event.currentTarget));
		console.log($(event.target));
		let house_type = $("#house_type").val();
		let house_size = $("#house_size").val();
		let house_adr = $("#house_adr").val();
		let house_fam = $("#house_fam").val();
		let house_remo = $("#house_remo").val();
		
		if($(event.currentTarget).closest("li").hasClass("select_type")) {
			house_type = "";
		} else if($(event.currentTarget).closest("li").hasClass("select_adr")) {
			house_adr = "";
		} else if($(event.currentTarget).closest("li").hasClass("select_size")) {
			house_size = "";
		} else if($(event.currentTarget).closest("li").hasClass("select_fam")) {
			house_fam = "";
		} else if($(event.currentTarget).closest("li").hasClass("select_remo")) {
			house_remo = "";
		}
		
		let currentUrl = "../houseopen/houseopenList" + "?" + "house_type" + "=" + house_type + "&" + "house_size" + "=" + house_size + "&" + "house_adr" + "=" + house_adr + "&" + "house_fam" + "=" + house_fam + "&" + "house_remo" + "=" + house_remo;
		console.log(currentUrl);
		$(event.target).closest("li").children().attr("href", currentUrl);
		
	})
	
	//파일업로드
$(document).ready(function() {
	var fileTarget = $('.title_content .upload_main_file'); //jquery는 다중 태그이벤트도 한번에 처리
	
	fileTarget.on('change', function(){ //change이벤트
	if(window.FileReader){ // modern browser 
		var filename = $(this)[0].files[0].name; 
		} 
	else { // old IE 
		var filename = $(this).val().split('/').pop().split('\\').pop(); // 파일명만 추출 
	} // 추출한 파일명 삽입 
	$(this).siblings('.upload_name').val(filename); });

	var imgTarget = $('.preview_image .upload_main_file'); 
	imgTarget.on('change', function() { 
		var parent = $(this).parent(); 
		
		if(window.FileReader){ //image 파일만 
			if (!$(this)[0].files[0].type.match(/image\//)) return; 
			var reader = new FileReader(); 
			reader.onload = function(e){ 
				var src = e.target.result; 
				//parent.find(".file_midBox").children().attr("src", src);
				//if(parent.find(".photo").attr("src") != undefined) {
					
				parent.find(".file_midBox").find("img").attr("src", src);
			/*	} else {
					
				parent.find(".photos").append('<img style="width: 60%;" src="'+src+'" class="upload-thumb">');
				}*/
				
			} 
			reader.readAsDataURL($(this)[0].files[0]); 
		} else { 
			$(this)[0].select(); 
			$(this)[0].blur(); 
			var imgSrc = document.selection.createRange().text; 
			parent.prepend('<div class="upload_display"><div class="file_midBox"><img class="upload_img"></div></div>'); 

			var img = $(this).siblings('.upload_display').find('img'); 
			img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")"; 
		} 
	});

});


})(window.jQuery);