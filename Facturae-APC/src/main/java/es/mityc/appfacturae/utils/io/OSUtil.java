/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-APC".
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto sean aprobadas por la Comisión Europea– versiones posteriores de la EUPL (la Licencia);
 * Solo podrá usarse esta obra si se respeta la Licencia.
 *
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Salvo cuando lo exija la legislación aplicable o se acuerde por escrito, el programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL»,
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas.
 * Véase la Licencia en el idioma concreto que rige los permisos y limitaciones que establece la Licencia.
 */
package es.mityc.appfacturae.utils.io;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;

public class OSUtil {
	
	private static Log logger = LogFactory.getLog(OSUtil.class);
	
	public static void open(String filePath){
		try{
			logger.info("Operative System: " + Constants.OSNAME);
			if (Constants.OSNAME.contains("Windows")){
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler \"" +filePath+"\"");
			}
			else {
				if (Constants.OSNAME.contains("Linux")){
					logger.info("Linux Desktop Environment: " + Constants.DESKTOP_ENV);
					if (Constants.DESKTOP_ENV.equals("kde"))
						Runtime.getRuntime().exec( new String[]{"kfmclient", "exec", filePath} );
					else if (Constants.DESKTOP_ENV.equals("gnome"))
						Runtime.getRuntime().exec( new String[]{"gnome-open", filePath} );
					else if (Constants.DESKTOP_ENV.equals("xfce")) 
						Runtime.getRuntime().exec( new String[] {"exo-open", filePath} );
					else
						logger.error("The Linux Desktop Environment is not supported");
				}
				else{
					if (Constants.OSNAME.contains("Mac")){
						Runtime.getRuntime().exec (new String[] {"/usr/bin/open",filePath});
					}
				}
			}
		}
		catch(IOException ioe){
			logger.error("A visualizing error took place");
		}
	}
}