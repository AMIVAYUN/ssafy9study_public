$(function(){
  $('.tab-content > div').hide();
  $('.tab-nav a').click(function () {
    $('.tab-content > div').hide().filter(this.hash).fadeIn();
    $('.tab-nav a').removeClass('active');
    $(this).addClass('active');
    return false;
  }).filter(':eq(0)').click();
  });