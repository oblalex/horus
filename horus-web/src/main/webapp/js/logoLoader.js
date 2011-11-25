var logoImagesCnt;
var logoImageIter;
var logoImageMask;
var logoImage;
var logoPath;
var logoImageNumPrev;
   
$(document).ready(function() {
    logoImagesCnt = 6;
    logoImageIter = logoImagesCnt;
    logoImageMask=new Array();
    
    logoImage = $(".header_logo");  
    logoPath = logoImage.attr('src');
    logoPath = logoPath.substring(0,logoPath.lastIndexOf('/') + 1);
    
    nextLogoIter();
    showLogo();
});

function nextLogoIter(){
    if (logoImageIter < logoImagesCnt-1){
        logoImageIter++;
    } else {
        var i
        for(i=0;i<logoImagesCnt;i++){
            logoImageMask[i] = false;
        }
        logoImageIter = 0;
    }
}
        
function showLogo(){

    var logoImageNum;
            
    while(true){
        logoImageNum = Math.round(Math.random()*(logoImagesCnt-1)); 
        if ((logoImageIter==0) && (logoImageNum==logoImageNumPrev)){
            continue;   
        }
        if (logoImageMask[logoImageNum]==false){
            logoImageMask[logoImageNum]=true;
            break;
        }
    }
    
    logoImageNumPrev = logoImageNum;
    nextLogoIter();
                      
    logoImage.fadeOut('slow', function () {        
        logoImage.attr({
            'src': logoPath+'aircraft'+logoImageNum+'.png'
            }).load(function(){
                logoImage.fadeIn('slow');
            });                     
        
    }); 
            
    setTimeout("showLogo()", 15000);
}