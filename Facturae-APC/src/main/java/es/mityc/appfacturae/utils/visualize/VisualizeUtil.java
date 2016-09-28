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

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.DoubleUtil;
import es.mityc.appfacturae.utils.io.OSUtil;

public class VisualizeUtil {

	private static Log logger = LogFactory.getLog(VisualizeUtil.class);
	private static int decimals = 2;

	public static void showAsHTML(String id, String version) {
		try {
			String xslFileName = "";
			if (version.equals(Constants.VERSION32)) {
				xslFileName = Constants.AEAT_32;
			} else if (version.equals(Constants.VERSION321)) {
				xslFileName = Constants.AEAT_321;
			}

			/** Generating a transformerFactory **/
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			TransformerFactory tfactory = TransformerFactory.newInstance();

			/** Preparation of XSL source **/
			InputStream isXsl = VisualizeUtil.class.getResourceAsStream("/stylesheets/" + xslFileName + ".xsl");

			if (isXsl != null) {
				Document xslDoc = docBuilder.parse(isXsl);
				DOMSource xslSource = new DOMSource(xslDoc);
				xslSource.setSystemId(isXsl.toString());

				/** Preparation of XML source **/
				File xmlFile = null;

				xmlFile = FacturaeManager.getInstance().loadInvoice(id);

				if (xmlFile != null) {
					Document xmlDoc = null;
					xmlDoc = docBuilder.parse(xmlFile);

					DOMSource xmlSource = new DOMSource(xmlDoc);
					xmlSource.setSystemId(isXsl.toString());

					/** Obtaining and inicializating the transfomer **/
					Templates templates = tfactory.newTemplates(xslSource);
					Transformer transformer = templates.newTransformer();

					/** Logotype **/
					String pathLogo = Constants.CONFIG_PROP.getProperty("logoPath");
					File logoFile = new File(pathLogo);

					if (logoFile.exists()) {
						transformer.setParameter(Constants.PARAMETER_INVOICE_LOGOTYPE, logoFile.getAbsolutePath());
					} else {
						transformer.setParameter(Constants.PARAMETER_INVOICE_LOGOTYPE, "");
					}

					/** Visualizing the results **/
					File htmlFile = new File("Visualizing.html");

					StreamResult results = new StreamResult(htmlFile);
					results.setSystemId(htmlFile.getAbsolutePath());
					try {
						transformer.transform(xmlSource, results);
					} catch (NullPointerException e) {
						logger.error(e.getMessage(), e);
					}
					OSUtil.open(htmlFile.getAbsolutePath());
				}
			}
		} catch (ParserConfigurationException e) {
			logger.error("A parsing error took place when visualizing", e);
		} catch (Exception e) {
			logger.error("A visualizing error took place", e);
		}
	}

	public static String newDouble(Double d, int decimals, String version) {
		return DoubleUtil.roundByVersionFormatted(d, decimals, version);
	}

	public static String add(String a, String b, int precision, String version) {
		return VisualizeUtil.newDouble((new Double(a) + new Double(b)), precision, version);
	}

	public static String getValueKey(String value) {
		String result = "";
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(decimals);
		df.setMinimumFractionDigits(decimals);
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols();
		dformater_rules.setDecimalSeparator(',');
		df.setDecimalFormatSymbols(dformater_rules);
		df.setGroupingSize(3);
		df.setGroupingUsed(true);
		if (value != null && !"".equals(value)) {
			result = df.format(new Double(value)).toString();
		}
		return result;
	}

}
