import javax.media.jai.JAI
import java.awt.image.RenderedImage
import de.andreasschmitt.richui.image.ImageCreationException

/*
 * @author Andreas Schmitt
 */
class FontImageController {
	
	def fontImageService
	
	def image = {
		try {
			params.size = new Integer(params?.size)			
			RenderedImage image = fontImageService.createImage(params.text, params.fontName, params.style, params.size, params.color)
			
			//PNG
		    response.contentType = "image/png"
			def outputStream = response.outputStream
			JAI.create("encode", image, outputStream, "PNG", null)	
		}
		catch(ImageCreationException e){
			log.error(e)
		}
		catch(Exception e){
			log.error(e)
		}
	}
}