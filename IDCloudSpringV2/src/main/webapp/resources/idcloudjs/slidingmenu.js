$(function(){
  var menuwidth  = 240; // pixel value for sliding menu width
  var menuspeed  = 200; // milliseconds for sliding menu animation time
  
  var $bdy       = $('body');
  var $container = $('#pgcontainer');
  var $idcloud    = $('#idcloudmenu');
  var negwidth   = "-"+menuwidth+"px";
  var poswidth   = menuwidth+"px";
  
  $('#menubtn').on('click',function(e){
    if($bdy.hasClass('openmenu')) {
      jsAnimateMenu('close');
    } else {
      jsAnimateMenu('open');
    }
  });
  
  $('.overlay').on('click', function(e){
    if($bdy.hasClass('openmenu')) {
      jsAnimateMenu('close');
    }
  });
  
  $('a[href$="#"]').on('click', function(e){
    e.preventDefault();
  });
  
  function jsAnimateMenu(action) {
    if(action == 'open') {
      $bdy.addClass('openmenu');
      
      $container.animate({marginRight: negwidth, marginLeft: poswidth}, menuspeed);
      $idcloud.animate({width: poswidth}, menuspeed);
      $('.overlay').animate({left: poswidth}, menuspeed);
    }
    
    if(action == 'close') {
      $bdy.removeClass('openmenu');
      
      $container.animate({marginRight: "0", marginLeft: "0"}, menuspeed);
      $idcloud.animate({width: "0"}, menuspeed);
      $('.overlay').animate({left: "0"}, menuspeed);
    }
  }
});