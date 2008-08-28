/*
* @author Andreas Schmitt
*/

    //<![CDATA[
        
    function GoogleMapIntegration() {
    	var map;
    	var gdir;
    	var geocoder = null;
    	var addressMarker;
    	
    	var zoomLevel;
    	var latitudeId;
    	var longitudeId;
    	var mapId;
    	var directionsId;
    	
    	var showStartMarker = true;
    	var smallMap = false;
    	
    	var isDraggable = true;
    	var markers = new Array();
    	
    	this.load = function(pos){
	   		if (GBrowserIsCompatible()) {
	        	map = new GMap2(document.getElementById(mapId));
	
				//Initialize map
	        	initializeMap(map, pos);
	        
	        	//Directions
	        	gdir = new GDirections(map, document.getElementById(directionsId));
	        	GEvent.addListener(gdir, "load", onGDirectionsLoad);
	        	GEvent.addListener(gdir, "error", handleErrors);
	      	}
    	}
    	
    	this.setZoomLevel = function(zoom){
    		zoomLevel = zoom;
    	}
    	
    	this.setLatitudeId = function(latId){
    		latitudeId = latId;
    	}
    	
    	this.setLongitudeId = function(lngId){
    		longitudeId = lngId;
    	}
    	
    	this.setMapId = function(mId){
    		mapId = mId;
    	}
    	
    	this.setDirectionsId = function(dirId){
    		directionsId = dirId;
    	}
    	
    	this.setDraggable = function(enabled){
    		isDraggable = enabled;
    	}
    	
    	this.setShowStartMarker = function(show){
    		showStartMarker = show;
    	}
    	
    	this.setSmallMap = function(small){
    		smallMap = small;
    	}
    	
    	this.addMarker = function(pos, isDraggable, desc){
    		var marker = new GMarker(pos, {draggable: isDraggable});
    		
    		GEvent.addListener(marker, "dragstart", function() {
  				map.closeInfoWindow();
  			});

			GEvent.addListener(marker, "click", function() {
  				marker.openInfoWindowHtml(desc);
  			});
  			
    		markers.push(marker);
    	}
      	
      	function initializeMap(map, pos){
	    	map.setCenter(pos, zoomLevel);
	    	
	    	latElem = document.getElementById(latitudeId);
      		if(latElem != null){
	    		latElem.value = pos.y;	
	    	}
	    	
	    	lngElem = document.getElementById(longitudeId);
	    	if(lngElem != null){
	  			lngElem.value = pos.x;
	    	}
	    
	        map.addControl(new GMapTypeControl());
	    	
	    	if(smallMap){
	    		map.addControl(new GSmallMapControl());
	    	}
	    	else {
	    		map.addControl(new GLargeMapControl());	
	    	}
	    	
	    	if(showStartMarker){
	    		var startMarker = new GMarker(pos, {draggable: isDraggable});
	        	GEvent.addListener(startMarker, "dragend", function() {
	  				if(latElem != null){
	  					latElem.value = startMarker.getPoint().y;
	  				}
	  			
	  				if(lngElem != null){
	  					lngElem.value = startMarker.getPoint().x;
	  				}
	  			});
	        	map.addOverlay(startMarker);
	    	}
	        
	        for (var i = 0; i < markers.length; i++){	        	
	        	map.addOverlay(markers[i]);
	        }
    	}
    	
	   this.showAddress = function(address) {
	   		if(address != ""){
		    	var map = new GMap2(document.getElementById(mapId));
		
		    	var geocoder = new GClientGeocoder();
		  		geocoder.getLatLng(address,
		    		function(point) {
		      		if (!point) {
		        		alert(address + " not found");
		      		} 
		      		else {
		        		initializeMap(map, point);
		      		}
		    	}
		  		);
	   		}
	 	} 
	 
	    this.setDirections = function(fromAddress, toAddress, locale) {
	    	if(fromAddress != "" && toAddress != ""){
	    		gdir.load("from: " + fromAddress + " to: " + toAddress, { "locale": locale });	
	    	}
	    }
	    
	    this.clearDirections = function(){
	    	gdir.clear();
	    }
	 
	    function handleErrors(){
		   if (gdir.getStatus().code == G_GEO_UNKNOWN_ADDRESS)
		     alert("No corresponding geographic location could be found for one of the specified addresses. This may be due to the fact that the address is relatively new, or it may be incorrect.\nError code: " + gdir.getStatus().code);
		   else if (gdir.getStatus().code == G_GEO_SERVER_ERROR)
		     alert("A geocoding or directions request could not be successfully processed, yet the exact reason for the failure is not known.\n Error code: " + gdir.getStatus().code);
		   
		   else if (gdir.getStatus().code == G_GEO_MISSING_QUERY)
		     alert("The HTTP q parameter was either missing or had no value. For geocoder requests, this means that an empty address was specified as input. For directions requests, this means that no query was specified in the input.\n Error code: " + gdir.getStatus().code);
	
		//   else if (gdir.getStatus().code == G_UNAVAILABLE_ADDRESS)  <--- Doc bug... this is either not defined, or Doc is wrong
		//     alert("The geocode for the given address or the route for the given directions query cannot be returned due to legal or contractual reasons.\n Error code: " + gdir.getStatus().code);
		     
		   else if (gdir.getStatus().code == G_GEO_BAD_KEY)
		     alert("The given key is either invalid or does not match the domain for which it was given. \n Error code: " + gdir.getStatus().code);
	
		   else if (gdir.getStatus().code == G_GEO_BAD_REQUEST)
		     alert("A directions request could not be successfully parsed.\n Error code: " + gdir.getStatus().code);
		    
		   else alert("An unknown error occurred.");
		   
		} 
	 
	 	function onGDirectionsLoad(){ 

		}
    		
    }

    //]]>