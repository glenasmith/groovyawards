package de.andreasschmitt.richui.taglib.renderer

import java.rmi.server.UID
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Formatter
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.plugins.PluginManagerHolder
import org.apache.commons.codec.digest.DigestUtils

/*
*
* @author Andreas Schmitt
*/
class RenderUtils {

	public static String getUniqueId() {
		return DigestUtils.md5Hex(new UID().toString())
    }
	
	public static String getResourcePath(String pluginName, String contextPath){
		//TODO find a more efficient way to retrieve plugin version getPlugin(name).version did not work 
		//def plugin = PluginManagerHolder?.pluginManager?.allPlugins.find { it.name == pluginName.toLowerCase() }
		//String pluginVersion = plugin?.version
				
		//The above version doesn't work on Jetty so for now an ugly approach will be used
		String pluginVersion = "0.4"
		
		"${contextPath}/plugins/${pluginName.toLowerCase()}-$pluginVersion"
	}

}