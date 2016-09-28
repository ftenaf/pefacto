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
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import es.mityc.facturae.utils.ValidationException;

public class StreamUtil {

	private final static int BUFFER_SIZE = 16384; 	// 16 KB
	
	public static void readStream(InputStream is, byte[] data) throws IOException {
		int pos = 0;
		int block = 0;
		int total = data.length;
		while (pos < total) {
			block = ((total - pos) > BUFFER_SIZE) ? BUFFER_SIZE : (total - pos);
			block = is.read(data, pos, block);
			if (block == -1)
				break;
			pos += block;
		}
	}
	
	public static void writeStream(OutputStream os, byte[] data) throws IOException {
		int pos = 0;
		int total = data.length;
		while (pos < total) {
			int block = ((total - pos) > BUFFER_SIZE) ? BUFFER_SIZE : (total - pos);
			os.write(data, pos, block);
			pos += block;
		}
	}
	
	public static Document createDoc(InputStream is) throws ValidationException{
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			doc = dbf.newDocumentBuilder().parse(is);
		} catch (ParserConfigurationException ex) {
			throw new ValidationException(ex);
		} catch (SAXException ex) {
			throw new ValidationException(ex);
		} catch (IOException ex) {
			throw new ValidationException(ex);
		} catch (IllegalArgumentException ex) {
			throw new ValidationException(ex);
		}
		return doc;
	}

}