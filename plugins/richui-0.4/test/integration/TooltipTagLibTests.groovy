import de.andreasschmitt.richui.taglib.renderer.TooltipRenderer

class TooltipTagLibTests extends GroovyTestCase {

	void testTooltip() {
		def attrs = ["id": "test"]
		     		
		def tl = new TooltipTagLib()
		tl.tooltipRenderer = new TooltipRenderer()
		
		String expectedResult = """
		<script type='text/javascript'>
			var myTooltip = new YAHOO.widget.Tooltip("myTooltip", { context:"$attrs.id" } );
		</script>
		"""
		
		def modify = { value ->
			value = value.replaceAll("\\s", "")	
			return value	
		}
		
		expectedResult = modify(expectedResult)
		String result = modify(tl.tooltip(attrs))
		
		assertEquals expectedResult, result
	}
}
