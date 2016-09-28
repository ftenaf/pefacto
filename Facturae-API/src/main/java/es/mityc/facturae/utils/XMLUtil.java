/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import adsi.org.apache.xml.security.Init;
import adsi.org.apache.xml.security.utils.XMLUtils;

/**
 * This class offers XML utilities
 *
 */
public class XMLUtil {

	private static Log logger = LogFactory.getLog(SignatureUtil.class);

	/**
	 * This method writes a docuemnt into a file.
	 * 
	 * @param doc - Document to store.
	 * @param filename - File to write the document.
	 * @return File signed and stored.
	 * @throws FileNotFoundException If the file cannot be opened for writing 
	 */
	public static File writeXml2File(Document doc, String filename) throws FileNotFoundException {
		logger.info("Writing the signed document to a file in binary way");
		FileOutputStream fo = null;
		File f = null; 
		try {
			f = new File(filename);
			fo = new FileOutputStream(f);
			Init.init();
			XMLUtils.outputDOM(doc, fo, true);
		} catch (FileNotFoundException e) {
			logger.error("A file not found exception has occurred: :"+e.getMessage());
			throw e;
		} 
		finally{
			try {
				fo.close();
			} catch (IOException e) {}
		}
		return f;
	}

}
