    //<![CDATA[
    
    function addEvent(object, eventType, handler, useCapture){
    	//IE
    	if(object.attachEvent){
    		object.attachEvent("on" + eventType, handler);	
    	}
    	//Other browsers
    	else if (object.addEventListener) {
    		object.addEventListener(eventType, handler, useCapture);
    	}
    } 
 
    //]]>