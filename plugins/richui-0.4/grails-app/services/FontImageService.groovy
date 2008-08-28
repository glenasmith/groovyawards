import java.awt.*
import java.awt.image.BufferedImage
import java.awt.image.renderable.ParameterBlock
import java.io.OutputStream
import java.awt.geom.Rectangle2D
import javax.media.jai.JAI
import javax.media.jai.PlanarImage
import java.awt.image.RenderedImage
import de.andreasschmitt.richui.image.ImageCreationException

/*
 * @author Andreas Schmitt
 */
class FontImageService {
	
    boolean transactional = false

    //The following code is based on a blog post by Rene Gosh http://rghosh.free.fr/groovyimages/index.html
    public RenderedImage createImage(String text, String fontName, String style, int size, String color) throws ImageCreationException {
		try {
	    	//Font
	    	int fontStyle = getFontStyle(style) 
	    	Font font = new Font(fontName, fontStyle, size)
	    	
			//Color
	    	color = "0x${color.replace('#', '')}"
	    	Color fontColor = Color.decode(color)
	    	
	    	//Determine bounds
	    	Map bounds = determineBounds(font, text)
	    	
	    	//Create image
	    	BufferedImage image = new BufferedImage((int) Math.ceil(bounds.rectangle.width) + 5, (int) Math.ceil(bounds.fontMetrics.ascent) + 10, BufferedImage.TYPE_INT_ARGB)
	    	Graphics2D graphics = image.graphics
	    		
	    	//Anti aliasing
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
	    		
	        //Transparent background
	        graphics.setColor(new Color(0, 0, 0, 0))
	        graphics.fillRect(0, 0, image.width, image.height)
	    		
	    	//Font
	    	graphics.setColor(fontColor)
	    	graphics.setFont(font)
	    	graphics.drawString(text, 0, graphics.fontMetrics.ascent)
	    		
	    	//Read AWT image
	    	ParameterBlock pb = new ParameterBlock()
	    	pb.add(image)
	    	PlanarImage renderedImage = (PlanarImage) JAI.create("awtImage", pb)
	    	
	    	return renderedImage
		}
		catch(Exception e){
			log.error("Error creating image")
			throw new ImageCreationException("Error creating image", e)
		}
    }
    
    private int getFontStyle(String style){
    	if(!style){
    		return Font.PLAIN
    	}
    	
    	return Font."${style.toUpperCase()}"
    }
    
    
    //Determine bounds for given font and text
    private Map determineBounds(Font font, String text){
  		int width = 1000
  		int height = 1000
  		
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    	Graphics2D graphics = image.getGraphics()    	
    	graphics.setFont(font)
    	
    	Map bounds = [:]
    	
  		bounds["rectangle"] = graphics.fontMetrics.getStringBounds(text, graphics)
  		bounds["fontMetrics"] = graphics.fontMetrics
    	
  		return bounds
    }
}