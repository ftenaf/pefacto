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
package es.mityc.appfacturae.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.ui.utils.GUIUtils;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.constants.ConstantsLookAndFeel;

public class PropertiesLoading {
	
	private static Log logger = LogFactory.getLog(PropertiesLoading.class);
		
	public static void load(){
		
		
		
		/** Loading look & feel properties */
	    ConstantsLookAndFeel.LOOKANDFEEL_PROP = new Properties();
		try {
	        InputStream is = Main.class.getResourceAsStream(Constants.LOOKANDFEEL_PATH);
	        ConstantsLookAndFeel.LOOKANDFEEL_PROP.load(is);
	        is.close();
	    }
	    catch(Exception e){
	        logger.error("Unable to load look and feel properties. Path not found:" + Constants.LOOKANDFEEL_PATH + ": " + e.getMessage());
	        return;
	    }
		
		/** Loading application properties */
		Constants.APP_PROP = new Properties();
		try {
	        InputStream is = Main.class.getResourceAsStream(Constants.APP_CONFIG_PATH);
	        Constants.APP_PROP.load(is);
	        is.close();
	    }
	    catch(Exception e){
	        logger.error("Unable to load application properties. Path not found:" + Constants.APP_CONFIG_PATH + ": " + e.getMessage());
	        return;
	    }
	    
	    /** Loading configuration properties */
	    if (Constants.CONFIG_PROP == null){
	    	Constants.CONFIG_PROP = new Properties();
	    	try {
	    		File f = new File(Constants.APP_PROP.getProperty("config_file"));
	    		if (f.exists()) {
	    			FileInputStream fis = new FileInputStream(f);
	    			Constants.CONFIG_PROP.load(fis);
	    			fis.close();
	    		}
	    		else {
	    			InputStream is = Main.class.getResourceAsStream(Constants.APP_PROP.getProperty("config_resource"));
	    			Constants.CONFIG_PROP.load(is);
	    			is.close();
	    		}
	    	} catch(Exception e) {
	    		logger.error("Unable to load configuration properties. Path not found:" + Constants.APP_PROP.getProperty("config_resource") + ": " + e.getMessage());
	    		return;
	    	}
	    }
	    
	    if(Constants.WSCONFIG_PROP == null) {
	    	Constants.WSCONFIG_PROP = new Properties();
	    	try {
	    		File f = new File(Constants.APP_PROP.getProperty("ws_file"));
	    		InputStream is = null;
	    		if(f.exists()) {
	    			logger.debug("Uso el fichero de configuración ws_file: " + f.toString());
	    			is = new FileInputStream(f);
	    		} else {
	    			logger.debug("Uso el fichero de configuración ws_resource: " + Constants.APP_PROP.getProperty("ws_resource"));
	    			is = Main.class.getResourceAsStream(Constants.APP_PROP.getProperty("ws_resource"));
	    		}
	    		if(is != null) {
	    			Constants.WSCONFIG_PROP.load(is);
	    			is.close();
	    		} else {
	    			logger.error("Revise la configuración de la aplicación en el fichero Application.properties");
	    		}
    		} catch(Exception e) {
    			logger.error("Error al cargar la configuración de acceso a servicios web.", e);
    			return;
    		}
	    }
	    
	    /** Loading sign (api) properties */
	    if (Constants.SIGN_PROP == null){
	    	Constants.SIGN_PROP = new Properties();
	    	try {
	    		File f = new File(Constants.APP_PROP.getProperty("sign_file"));
	    		if (f.exists()) {
	    			FileInputStream fis = new FileInputStream(f);
	    			Constants.SIGN_PROP.load(fis);
	    			fis.close();
	    		}
	    		else {
	    			InputStream is = Main.class.getResourceAsStream(Constants.APP_PROP.getProperty("sign_resource"));
	    			Constants.SIGN_PROP.load(is);
	    			is.close();
	    		}
	    	} catch(Exception e) {
	    		logger.error("Unable to load sign configuration properties. Path not found:" + Constants.APP_PROP.getProperty("sign_resource") + ": " + e.getMessage());
	    		return;
	    	}
	    }
	    
	    /** Loading tax rates properties */
	    if (Constants.TAXRATE_PROP == null){
	    	Constants.TAXRATE_PROP = new Properties();
	    	try {
	    		File f = new File(Constants.APP_PROP.getProperty("taxRates_file"));
	    		if (f.exists()) {
	    			FileInputStream fis = new FileInputStream(f);
	    			Constants.TAXRATE_PROP.load(fis);
	    			fis.close();
	    		}
	    		else {
	    			InputStream is = Main.class.getResourceAsStream(Constants.APP_PROP.getProperty("taxRates_resource"));
	    			Constants.TAXRATE_PROP.load(is);
	    			is.close();
	    		}
	    	} catch(Exception e) {
	    		logger.error("Unable to load tax rates properties. Path not found:" + Constants.APP_PROP.getProperty("taxRates_resource") + ": " + e.getMessage());
	    		return;
	    	}
	    }
	    /** Loading legal literals properties */
	    loadLegalLiterals();
	    
	    Constants.DIALOG = new GUIUtils();
	    
	}
	
	public static void loadLegalLiterals() {
		File f = null;
		FileInputStream fis = null;
		InputStream is;
		ArrayList<ComboOption> comboLiterals;
		Properties propertiesLiterals;
		try {
			Constants.LEGALLITERALS_PROP = new Properties();
			f = new File(Constants.APP_PROP.getProperty("legal_literals_file"));
			if (f.exists()) {
				fis = new FileInputStream(f);
				Constants.LEGALLITERALS_PROP.load(fis);
				fis.close();
			} else {
				logger.debug("Loading resource from file:"+Constants.APP_PROP.getProperty("legal_literals_resource"));
				is = Main.class.getResourceAsStream(Constants.APP_PROP.getProperty("legal_literals_resource"));
				Constants.LEGALLITERALS_PROP.load(is);
				logger.debug("Resource loaded");
				is.close();
			}
			Constants.LEGAL_LITERALS = new ArrayList<ComboOption>();
			comboLiterals = Constants.LEGAL_LITERALS;
			propertiesLiterals = Constants.LEGALLITERALS_PROP;				
			Set<String> keys = new HashSet<String>();
			for (String key : propertiesLiterals.stringPropertyNames()) {
				keys.add(key);
			}
			String[] legals = keys.toArray(new String[keys.size()]);
			Map<Integer, String> prefixes = new HashMap<Integer, String>();
			Map<Integer, String> values = new HashMap<Integer, String>();
			try {
				//Iterate over legals to split prefixes and values
				for (int i = 0; i < legals.length; i++) {
					String keyLegalPropertie = legals[i];
					if (keyLegalPropertie.startsWith(Constants.OPTION_PREFIX)) {
						String order = keyLegalPropertie.substring(
							keyLegalPropertie.lastIndexOf(Constants.OPTION_PREFIX) + Constants.OPTION_PREFIX.length(),
							keyLegalPropertie.length());
						int keyLegalPropertieOrder = Integer.valueOf(order);
						prefixes.put(keyLegalPropertieOrder, propertiesLiterals.getProperty(legals[i]));
					} else {
						String order = keyLegalPropertie.substring(keyLegalPropertie.lastIndexOf(Constants.TEXT_PREFIX)
							+ Constants.TEXT_PREFIX.length(), keyLegalPropertie.length());
						int keyLegalPropertieOrder = Integer.valueOf(order);
						values.put(keyLegalPropertieOrder, propertiesLiterals.getProperty(legals[i]));
					}
				}
				//Legal literals are stored in ArrayList<ComboOption>
				for (int i = 1; i <= prefixes.size(); i++) {
					comboLiterals.add(new ComboOption(prefixes.get(i), values.get(i)));
				}
			} catch (Exception e) {
				logger.error("Unable to load legal literals properties:"
					+ Constants.APP_PROP.getProperty("legal_literals_resource") + ": " + e.getMessage(),e);
			}

		} catch (Exception e) {
			logger.error("Unable to load legal literals properties."
				+ Constants.APP_PROP.getProperty("legal_literals_resource") + ": " + e.getMessage(),e);
			return;
		}
	}
}
