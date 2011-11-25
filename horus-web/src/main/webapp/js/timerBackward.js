var oneSecondMillis = 1000;
var secondsDefault = 5;
var timeUnitsNamesDefault = {
    h:"h", 
    m:"min", 
    s:"sec"
};

function getSecondsFromArgOrDefault(seconds){
    if (seconds!=null){
        return seconds;
    } else {
        return secondsDefault;
    }
}

function getTimeUnitsNamesFromArgOrDefault(timeUnitsNames){
    if (timeUnitsNames!=null){
        return timeUnitsNames;
    } else {
        return timeUnitsNamesDefault;
    }
}

function getHmsFromArgOrDefault(arg){
    var seconds = getSecondsFromArgOrDefault(arg);
    
    var hours = Math.floor(seconds / 3600);    
    var t1 = seconds - hours * 3600;
    var mins = Math.floor(t1 / 60);
    var secs = Math.floor(t1 - mins * 60);        
    
    return {
        h:hours, 
        m:mins, 
        s:secs
    };
}

function formatDoubleBitNumber(num) {
    return (num<10?'0'+num:num);
}

jQuery.fn.addAndRunBackwardTimer = function() {
    var target = $(this[0]);
    var args = arguments[0] || {};
    
    var hms = getHmsFromArgOrDefault(args.seconds);
    var postStopText = args.postStopText;
    var timeUnitsNames = getTimeUnitsNamesFromArgOrDefault(args.timeUnitsNames);
    var onTimerStop = args.onTimerStop;        
    
    setTimeout(function() {
        goNext(false);
    }, 0);
    
    function decreaseHms() {
        hms.s--;
        if (hms.s<0) {
            hms.s=59;
            hms.m--;
            if (hms.m<0) {
                hms.m=59;
                if (hms.h>0){
                    hms.h--;
                } else {
                    hms.h=hms.m=hms.s=0;
                }
            }
        }
    }
    
    function timeHasLeft(){
        return (hms.h+hms.m+hms.s) > 0;
    }
    
    function hmsToString(){
        var result;
        
        if (hms.h > 0) {
            result = formatDoubleBitNumber(hms.h)+" "
            +timeUnitsNames.h+" "
            +formatDoubleBitNumber(hms.m)+" "
            +timeUnitsNames.m;
        } else if (hms.m > 0) {
            result = formatDoubleBitNumber(hms.m)+" "
            +timeUnitsNames.m;
        } 
        
        if ((hms.h > 0) || (hms.m > 0)){
            result += " "+formatDoubleBitNumber(hms.s)+" "
            +timeUnitsNames.s;
        } else {
            result = formatDoubleBitNumber(hms.s)+" "
            +timeUnitsNames.s;
        }        
        
        return result;
    }
    
    function goNext(wasRunning) { 
        if (timeHasLeft()) {                
            target.text(hmsToString());
            decreaseHms();
            setTimeout(function() {
                goNext(true);
            }, oneSecondMillis);
        }
        else {
            if (postStopText!=null){
                target.text(postStopText);
            } else {
                target.text("00 "+timeUnitsNames.s);
            }
            if (onTimerStop){
                onTimerStop();
            }
        }
    }
};