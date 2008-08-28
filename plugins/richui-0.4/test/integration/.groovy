import de.andreasschmitt.richui.taglib.renderer.FeedRenderer
import javax.servlet.http.HttpServletResponse

class RichUIUtilTagLibTests extends GroovyTestCase {

	void testFeed() {
		def attrs = ["title": "News feed", "type": "rss", "url": "http://www.feed.org/rss"]
		
		def tl = new RichUIUtilTagLib()
		tl.feedRenderer = new FeedRenderer()
		
		assertEquals "<link rel='alternate' type='application/$attrs.type+xml' title='$attrs.title' href='$attrs.url' />", tl.feed(attrs)
	
		attrs = ["type": "rss", "url": "http://www.feed.org/rss"]
		assertEquals "<link rel='alternate' type='application/$attrs.type+xml' title='${attrs.type.toUpperCase()}' href='$attrs.url' />", tl.feed(attrs)
	
		attrs = ["url": "http://www.feed.org/rss"]
		assertEquals "", tl.feed(attrs)
		
		attrs = [:]
		assertEquals "", tl.feed(attrs)         
	}
	
	void testRedirect() {
		//No real test possible, because response property is read only
		def attrs = [url: "http://www.google.de"]
		
		def tl = new RichUIUtilTagLib()
		
		assertEquals "", tl.redirect(attrs)
	}

}
