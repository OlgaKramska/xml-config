


$( document ).ready(function() {
    $("#btn-group2").hide();
});
$(function(){
    $("#button_edit").on('click',function() {
        $("#btn-group1").hide();
        $("#btn-group2").toggle();
    });
});
$(function(){
    $("#button_close").on('click',function() {

    });
});
$(function(){
    $("#button_cancel").on('click',function() {
        $("#btn-group2").hide();
        $("#btn-group1").toggle();
    });
});
$(function(){
    $("#button_save").on('click',function() {
        $("#btn-group2").hide();
        $("#btn-group1").toggle();
    });
});
