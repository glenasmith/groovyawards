import de.andreasschmitt.richui.taglib.renderer.AutoCompleteRenderer

class AutoCompleteTagLibTests extends GroovyTestCase {
	
	def modify = { value ->
		value = value.replaceAll("id=\'.*\'", "id='1'")
		value = value.replaceAll(",'.*', autoCompleteDataS", ",'1', autoCompleteDataS")
		value = value.replaceAll("\\s", "")
	
		return value
	}

	void testAutoComplete() {
		def attrs = [name: "person", id: "person", action: "http://localhost:8080/testapp/person/searchAjax", value: ""]
		     		
		def tl = new AutoCompleteTagLib()
		tl.autoCompleteRenderer = new AutoCompleteRenderer()
		
		String expectedResult = """
		<input type='text' id='$attrs.name' name='$attrs.name' value='$attrs.value'></input>
		<div class='searchcontainer' style='' id='1'></div>
		<script type='text/javascript'>	autoCompleteDataSource = new YAHOO.widget.DS_XHR("$attrs.action", ["result", "name"]);
			autoCompleteDataSource.responseType = YAHOO.widget.DS_XHR.TYPE_XML;
			autoComplete = new YAHOO.widget.AutoComplete('$attrs.id','1', autoCompleteDataSource);
			autoComplete.queryDelay = 0;
			autoComplete.prehighlightClassName = "yui-ac-prehighlight";
			autoComplete.useShadow = false;
			autoComplete.minQueryLength = 0;
		</script>
		"""
		
		String result = tl.autoComplete(attrs)
		
		expectedResult = modify(expectedResult)
		result = modify(result)
		
		assertEquals expectedResult, result
	}
	
	void testAutoCompleteWithMoreAttrs() {
		def attrs = [name: "person", id: "person", queryDelay: "5", minQueryLength: "2", delimChar: ",", style: "height: 250px;", styleClass: "testClass", shadow: "true", action: "http://localhost:8080/testapp/person/searchAjax", value: "Hallo"]
			     		
		def tl = new AutoCompleteTagLib()
		tl.autoCompleteRenderer = new AutoCompleteRenderer()
		     		
		String expectedResult = """
		<input type='text' id='$attrs.name' name='$attrs.name' value='$attrs.value'></input>
		<div class='$attrs.styleClass' style='$attrs.style' id='1'></div>
		<script type='text/javascript'>	autoCompleteDataSource = new YAHOO.widget.DS_XHR("$attrs.action", ["result", "name"]);
			autoCompleteDataSource.responseType = YAHOO.widget.DS_XHR.TYPE_XML;
		    autoComplete = new YAHOO.widget.AutoComplete('$attrs.id','1', autoCompleteDataSource);
		    autoComplete.queryDelay = $attrs.queryDelay;
		    autoComplete.prehighlightClassName = "yui-ac-prehighlight";
		    autoComplete.useShadow = true;
		    autoComplete.minQueryLength = $attrs.minQueryLength;
			autoComplete.delimChar = "$attrs.delimChar";
		</script>
		"""
		     		
		String result = tl.autoComplete(attrs)
		     		
		expectedResult = modify(expectedResult)
		result = modify(result)
		     		
		assertEquals expectedResult, result
	}
}
