function addInputsStyles(){
    $('input[type="text"]').addClass("idleField");
    $('input[type="password"]').addClass("idleField");
    $('textarea').addClass("idleField");    
}

$(document).ready(function() {
    addInputsStyles();
    
    $('input[type="text"]').focus(function() {
        doFocus(this);
    });
    
    $('input[type="password"]').focus(function() {
        doFocus(this);
    });
    
    $('textarea').focus(function() {
        doFocus(this);
    });
    
    $('input[type="text"]').blur(function() {
        doUnfocus(this);
    });
    
    $('input[type="password"]').blur(function() {
        doUnfocus(this);
    });
    
    $('textarea').blur(function() {
        doUnfocus(this);
    });
});

function doFocus(target){
    $(target).removeClass("idleField").addClass("focusField");
    if (target.value == target.defaultValue){ 
        target.value = '';
    }
    if(target.value != target.defaultValue){
        target.select();
    }
}

function doUnfocus(target){
    $(target).removeClass("focusField").addClass("idleField").removeClass("idleField_anim");
    if ($.trim(target.value) == ''){
        target.value = (target.defaultValue ? target.defaultValue : '');
    }
}