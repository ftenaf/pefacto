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
package es.mityc.appfacturae.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.mityc.appfacturae.exceptions.ParseException;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.appfacturae.utils.io.FileUtil;
import es.mityc.appfacturae.utils.io.OSUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.FacturaeVersion;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.XMLUtil;

public class XMLFacturaeUtil {
	
	private static Log logger = LogFactory.getLog(XMLFacturaeUtil.class);
	
	public static void seeXML(String facturaeId) {
		// Get Invoice from data base
		File fO = FacturaeManager.getInstance().loadInvoice(facturaeId);
		
		// Rename it to XML
		File fD = null;
		try {
			fD = File.createTempFile("Invoice", ".xml");
		} catch (IOException e2) {
			logger.error("Error creating a temporal file to show the invoice XML in a web browser");
			Constants.DIALOG.showErrorSeeXML();
		}
		fD.delete();
		fD.deleteOnExit();
		fO.renameTo(fD);
		
		// Launch browser
		String url = null;
		try {
			url = fD.getCanonicalPath();
		} catch (IOException e1) {}
		
		logger.info("Launching browser with XML information");
		OSUtil.open(url);
		logger.info("Launching browser completed");
	} 
	

	public static File insertarExtension(InputStream invoice, InputStream extension, String[] nodeRoute) throws ValidationException, FileNotFoundException {
		Document d = StreamUtil.createDoc(invoice);
		Document d2 = StreamUtil.createDoc(extension);
		Node tempNode = d.importNode(d2.getChildNodes().item(0),true); // true = deep copy
		
		// Facturae Node
		Node node = d.getChildNodes().item(0);
		
		// Scanning the node to write (defined by the nodeRoute variable)
		for (int i = 0 ; i < nodeRoute.length ; i++){
			NodeList nl = node.getChildNodes();
			for (int j = 0 ; j < nl.getLength() ; j++){
				if (nodeRoute[i].equals(nl.item(j).getNodeName()))
					node = nl.item(j);
			}
		}
		
		node.appendChild(tempNode);
		
		File f = new File("extValid.tmp");
		f.deleteOnExit();
		
		try {
			return XMLUtil.writeXml2File(d, f.getName());
		} catch (FileNotFoundException e) {
			throw e;
		}
	}
	
	public static String getVersionStr(File f) throws ParseException{
		org.w3c.dom.Document doc = null;
		String version = "";
		/** Parsing from File to Document in order to obtain the schema version **/
		try {
			doc = FileUtil.createDoc(f);
		} catch(ParseException pe) {
			throw pe;
		}
		version = getVersionStr(doc);
		if (FacturaeUtil.getVersionConst(version) == null)
			throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeVersion"));

		return version;
	}
	
	public static String getVersionStr(Document doc) throws ParseException{
		String version = "";
		version = doc.getElementsByTagName("SchemaVersion").item(0).getTextContent();
		if (FacturaeUtil.getVersionConst(version) == null)
			throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeVersion"));
		return version;
	}
	
	public static FacturaeVersion getVersionOb(File f) throws ParseException{
		String version = getVersionStr(f);
		return FacturaeUtil.getVersionOb(version);
	}
}
