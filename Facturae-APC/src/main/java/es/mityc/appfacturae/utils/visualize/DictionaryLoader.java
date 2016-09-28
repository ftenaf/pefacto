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


public class DictionaryLoader {

	private static Log logger = LogFactory.getLog(DictionaryLoader.class);
	private static String[] languages = null;
	private static ConfigManager cm = null;
	private static DictionaryLoader ourInstance = null;
	private static Properties defaultLang = null;
	private static Properties currentLang = null;
	private static String currentLangStr = "";
	
	public static DictionaryLoader getInstance() {
		if(ourInstance == null)
			ourInstance = new DictionaryLoader();
		return ourInstance;
	}
	
	private DictionaryLoader() {
		cm = new ConfigManager();
		languages = cm.getConfigProperty("langs").split(";");
		defaultLang = new Properties();
		try {
			InputStream inputStream = DictionaryLoader.class.getResourceAsStream(cm.getConfigProperty(languages[0]));
			defaultLang.load(inputStream);
		} catch (IOException e) {
			logger.error("An Error occurred when trying to load the configuration " + e, e);
		}
	}
	
	public static void reinit() {
		logger.debug("reinit()");
		ourInstance = getInstance();
	}

	public static String getTranslation(String tagName, String tagValue, String lang) {
		logger.debug("getTranslation(" + tagName + "," + tagValue + "," + lang + ")");
		reinit();
		if (getCurrentLang() == null || !getCurrentLangStr().equals(lang))
			setCurrentLang(lang);
		String result = null;
		String key = tagName + tagValue;
		result = currentLang.getProperty(key);
		if (result == null) {
			if (tagValue.equals("")){
				result = defaultLang.getProperty(key);
				if (result == null){
					logger.warn("getTranslation <-- " + tagName + tagValue + " in language: " + lang + " has not been found. Returns default tagName:" + tagName + "\n");
					return tagName;
				}
				else{
					logger.warn("getTranslation <-- " + tagName + tagValue + " in language: " + lang + " has not been found. Returns default tag (first language defined):" + result + "\n");
					return result;
				}
			}
			logger.warn("getTranslation <-- " + tagName + tagValue + " in language: " + lang + " has not been found. Returns:" + tagValue + "\n");
			return tagValue;
		}
		return result;
	}

	private static Object getCurrentLang() {
		return currentLang;
	}
	
	private static String getCurrentLangStr() {
		return currentLangStr;
	}

	private static void setCurrentLang(String lang){
		logger.debug("Setting current lang: " + lang);
		for (int i = 0; i < languages.length; i++){
			if (lang.equalsIgnoreCase(languages[i])) {
				currentLang = new Properties();
				currentLangStr = "";
				try {
					logger.debug("Setting current lang (matched): " + lang + " with file: " + cm.getConfigProperty(languages[i]));
					InputStream inputStream = DictionaryLoader.class.getResourceAsStream(cm.getConfigProperty(languages[i]));
					logger.debug("Setting current lang resource inputStream: " + inputStream);
					currentLang.load(inputStream);
					currentLangStr = lang;
				} catch (IOException e) {
					logger.error("An Error occurred when trying to load the configuration " + e, e);
				}
			}
		}
		if (currentLang == null)
			currentLang = new Properties();
	}

}