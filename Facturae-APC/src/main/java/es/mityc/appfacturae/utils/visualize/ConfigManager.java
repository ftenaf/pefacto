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
package es.mityc.appfacturae.utils.visualize;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigManager {
	
	private static final Log log = LogFactory.getLog(ConfigManager.class);
	
	private static final String FILE_PROPERTIES = "/config/Application.properties";
	
	private Properties app_properties;
	
	/*
	 * Constructor de ConfigManager: Inicializa el properties de la aplicacion
	 */
	public ConfigManager() {
		try {
			log.info("Cargando fichero de propiedades de aplicacion");
			app_properties = new Properties();
			InputStream is = this.getClass().getResourceAsStream(FILE_PROPERTIES);
			if (is != null){
				app_properties.load(this.getClass().getResourceAsStream(FILE_PROPERTIES));
				log.info("Fichero de propiedades cargado correctamente");
			}
			else
				log.error("No se ha podido acceder al fichero de configuración: " + FILE_PROPERTIES);
		}
		catch (IOException ex) {
			log.error("Error en la lectura del fichero de configuración", ex);
		}
	}
		
	/*
	 * Devuelve el tamano maximo de fichero permitido
	 */
	public int getMaxSize() {
		if (app_properties != null) {
			try {
				log.info("Consultando el tamano maximo de fichero permitido");
				return Integer.parseInt(app_properties.getProperty("fileupload.maxsize", "0"));
			} catch (NumberFormatException e) {
				log.error("Excepcion de formato de numero al cargar el tamano maximo de fichero permitido", e);
				return 0;
			}
		}
		else{ 
			log.error("Error al cargar el tamano maximo de fichero permitido: fichero de propiedades nulo");
			return 0;
		}
	}
	
	public String getConfigProperty(String key) {
		return app_properties.getProperty(key);
	}

}
