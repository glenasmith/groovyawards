import de.andreasschmitt.richui.taglib.renderer.TagCloudRenderer

class TagCloudTagLibTests extends GroovyTestCase {

	void testTagCloud() {
		def attrs = [values: [Grails: 20, Java: 15], style: "", styleClass: "", action: "", controller: "", linkStyle: "", linkClass: ""]
			     		
		def tl = new TagCloudTagLib()
		tl.tagCloudRenderer = new TagCloudRenderer()
		     		
		String expectedResult = """
		<div class='$attrs.styleClass' style='$attrs.style'>
  			<form id='1' action='..//' method='post'>
    			<input id='2' name='selectedTag' type='hidden' />
    			<a href='#' class='$attrs.linkClass' style='font-size: 20; ' onclick='javascript: document.getElementById("2").value = "Grails"; document.getElementById("1").submit();'>Grails</a>
    			<a href='#' class='$attrs.linkClass' style='font-size: 15; ' onclick='javascript: document.getElementById("2").value = "Java"; document.getElementById("1").submit();'>Java</a>
  				</form>
		</div>
		"""
		     		
		def modify = { value ->
			value = value.replaceAll("form id=\'.*\'", "id='1'")
			value = value.replaceAll("input id=\'.*\'", "id='2'")
			value = value.replaceAll("javascript: document\\.getElementById\\(\".*\"\\)", "javascript: document.getElementById(\"2\")")
			value = value.replaceAll("document\\.getElementById\\(\".*\"\\)\\.submit", "document.getElementById(\"1\").submit")
			value = value.replaceAll("\\s", "")	
		    return value	
		}
		     		
		expectedResult = modify(expectedResult)
		String result = modify(tl.tagCloud(attrs))
		
		assertEquals expectedResult, result		
	}
}
