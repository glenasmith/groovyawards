import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class GoogleMapsTagLib {
	
	static namespace = "richui"
	
	Renderer mapRenderer
	
	def googlemaps = { attrs -> 				
		if(!attrs?.lat){
			attrs.lat = ""
		}
		
		if(!attrs?.lng){
			attrs.lng = ""
		}
		
		if(!attrs?.zoomLevel){
			attrs.zoomLevel = "13"
		}
		
		String mapIntegrationVar = "gi"
		
		if(!attrs?.mapIntegrationVar){
			attrs.mapIntegrationVar = mapIntegrationVar
		} else {
			attrs.mapIntegrationVar = attrs.mapIntegrationVar
		}

		String mapId = mapIntegrationVar + "map"
		String dirId = mapIntegrationVar + "directions"
		String latId = "latitude"
		String lngId = "longitude"
		boolean draggable = true
		
		if(!attrs?.mapId){
			attrs?.mapId = mapId
		}
		if(!attrs?.dirId){
			attrs?.dirId = dirId
		}
		if(!attrs?.latId){
			attrs.latId = latId
		}
		if(!attrs?.lngId){
			attrs.lngId = lngId
		}
		
		if(attrs?.draggable){
			attrs.draggable = attrs.draggable == "true" ? true : false
		} else {
			attrs.draggable == "true"
		}
		
		boolean showStartMarker = true
		
		if(attrs?.showStartMarker){
			attrs.showStartMarker = attrs.showStartMarker == "true" ? true : false
		} else {
			attrs.showStartMarker = true
		}
		
		attrs.routeStart = message("code": "map.route.start")
		attrs.routeDestination = message("code": "map.route.destination")
		attrs.routeOk = message("code": "map.route.ok")
		attrs.routeClear = message("code": "map.route.clear")
		
		attrs.searchSearch = message("code": "map.search.search")
		attrs.searchOk = message("code": "map.search.ok")
		
		if(!attrs.directionsLocale){
			attrs.directionsLocale = "${request?.locale?.language}_${request?.locale?.country}"
		}
	    
		if(!attrs?.style){
			attrs.style =  "width: 500px; background: #F1F1F1; border: 1px solid #F1F1F1;\""
		}
		if(!attrs?.'class'){
			attrs.'class' = ""
		}
	    
		if(!attrs?.mapStyle){
			attrs.mapStyle = "width: 500px; height: 300px;"
		}
		if(!attrs?.mapStyleClass){
			attrs.mapStyleClass = ""
		}
		
		//Render output
		try {
			out << mapRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
	
}