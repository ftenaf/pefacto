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
package es.mityc.appfacturae.importers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import es.mityc.appfacturae.exceptions.ImportExportException;
import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.FacturaeStatics;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.importers.ExportInvoiceResult.ExportInvoiceResultType;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.XMLUtil;

/**
 * This class exports invoices.
 *
 */
public class InvoiceExporter {

	private static Log logger = LogFactory.getLog(InvoiceExporter.class);
	
	/**
	 * Export invoices
	 * @param destinationDirectory The destination directory to place the exported invoices
	 * @param invoiceClass The class of the invoices to export
	 * @param invoiceStatus The state of the invoices to export
	 * @param subdirectoryName The subdirectory where exported invoices are going to be stored (depending on the class and state)
	 * @return The results of the exportation
	 * @throws ImportExportException 
	 */
	public Collection<ExportInvoiceResult> exportInvoices(File destinationDirectory, InvoiceClassType invoiceClass,
		InvoiceStatusType invoiceStatus, String subdirectoryName) throws ImportExportException {
		Collection<ExportInvoiceResult> results = new ArrayList<ExportInvoiceResult>();
		ExportInvoiceResult result = new ExportInvoiceResult();
		
		File finalDirectory = new File(destinationDirectory.getAbsolutePath()+File.separator+subdirectoryName);
		if (!finalDirectory.mkdirs())
			throw new ImportExportException("Error creating invoices subdirectory: "+ destinationDirectory.getAbsolutePath()+File.separator+subdirectoryName);
		
		SQLQuery s, s2 = null;
		s = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_INVOICE_BY_STATE_CLASS.
				replace("$1", String.valueOf(invoiceStatus.ordinal())).
				replace("$2",String.valueOf(invoiceClass.ordinal())));
		List<?> ls = s.list();
		if (ls != null && ls.size() > 0) {
			int numResults = ls.size();
			java.sql.Blob b = null;
			String ext = Constants.XML;
			for (int i = 0; i < numResults; i++) {
				Object[] oArray = (Object[])ls.get(i);
				s2 = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_FACTURAE_XML_BY_ID.replace("$1", oArray[0].toString()));
				List<?> ls2 = s2.list();
				if (ls2 != null && ls2.size() > 0) {
					b = (java.sql.Blob)ls2.get(0);	
					result = new ExportInvoiceResult();
					try {
						XMLUtil.writeXml2File(StreamUtil.createDoc(b.getBinaryStream()),finalDirectory+File.separator+oArray[1].toString()+ext);
						result.setResult(ExportInvoiceResultType.CORRECT);
						result.setId(oArray[1].toString());
						result.setObservations(subdirectoryName+": "+Constants.LANG.getString("OKExportResultMessage"));
						results.add(result);
					} catch (FileNotFoundException e) {
						logger.error("File not found exception ["+finalDirectory+File.separator+"]: " + e.getMessage());
						result.setResult(ExportInvoiceResultType.ERROR);
						result.setId(oArray[1].toString());
						result.setObservations(subdirectoryName+": "+Constants.LANG.getString("NOOKFileNotFoundExport"));
						results.add(result);
					} catch (ValidationException e) {
						logger.error("Error validating the file to write: " + e.getMessage());
						result.setResult(ExportInvoiceResultType.ERROR);
						result.setId(oArray[1].toString());
						result.setObservations(subdirectoryName+": "+Constants.LANG.getString("NOOKMessageFacturaeFormat"));
						results.add(result);
					} catch (SQLException e) {
						logger.error("Error getting the binary stream: " + e.getMessage());
						result.setResult(ExportInvoiceResultType.ERROR);
						result.setId(oArray[1].toString());
						result.setObservations(subdirectoryName+": "+Constants.LANG.getString("NOOKMessageFacturaeFormat"));
						results.add(result);
					}
				}
			}
		}
		return results;
	}
	
	/**
	 * Export invoice
	 * @param destinationDirectory The destination directory to place the exported invoice
	 * @param invoiceClass The class of the invoice to export
	 * @param invoiceStatus The state of the invoice to export
	 * @param invoiceName Name of the invoice (Series code + Number)
	 * @return The result of the exportation
	 * @throws ImportExportException 
	 */
	public ExportInvoiceResult exportInvoice(File destinationDirectory, InvoiceClassType invoiceClass,
		InvoiceStatusType invoiceStatus, String invoiceName) throws ImportExportException {
		ExportInvoiceResult result = new ExportInvoiceResult();
		File finalDirectory = new File(destinationDirectory.getAbsolutePath());
		if (!finalDirectory.exists())
			throw new ImportExportException("Error creating invoice directory: "
				+ destinationDirectory.getAbsolutePath());
		SQLQuery s, s2 = null;
		s = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_INVOICE_BY_STATE_CLASS_NAME.
				replace("$1", String.valueOf(invoiceStatus.ordinal())).
				replace("$2", String.valueOf(invoiceClass.ordinal())).
				replace("$3", invoiceName));
		List<?> ls = s.list();
		if (ls != null && ls.size() > 0) {
			java.sql.Blob b = null;
			String ext = Constants.XML;
			Object[] oArray = (Object[]) ls.get(0);
			s2 = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_FACTURAE_XML_BY_ID.replace("$1", oArray[0].toString()));
			List<?> ls2 = s2.list();
			if (ls2 != null && ls2.size() > 0) {
				b = (java.sql.Blob) ls2.get(0);
				result = new ExportInvoiceResult();
				try {
					XMLUtil.writeXml2File(StreamUtil.createDoc(b.getBinaryStream()), finalDirectory + File.separator
						+ oArray[1].toString() + ext);
					result.setResult(ExportInvoiceResultType.CORRECT);
					result.setId(oArray[1].toString());
					result.setObservations(destinationDirectory + ": "
						+ Constants.LANG.getString("OKExportResultMessage"));
				} catch (FileNotFoundException e) {
					logger.error("File not found exception [" + finalDirectory + File.separator + "]: "
						+ e.getMessage());
					result.setResult(ExportInvoiceResultType.ERROR);
					result.setId(oArray[1].toString());
					result.setObservations(destinationDirectory + ": "
						+ Constants.LANG.getString("NOOKFileNotFoundExport"));
				} catch (ValidationException e) {
					logger.error("Error validating the file to write: " + e.getMessage());
					result.setResult(ExportInvoiceResultType.ERROR);
					result.setId(oArray[1].toString());
					result.setObservations(destinationDirectory + ": "
						+ Constants.LANG.getString("NOOKMessageFacturaeFormat"));
				} catch (SQLException e) {
					logger.error("Error getting the binary stream: " + e.getMessage());
					result.setResult(ExportInvoiceResultType.ERROR);
					result.setId(oArray[1].toString());
					result.setObservations(destinationDirectory + ": "
						+ Constants.LANG.getString("NOOKMessageFacturaeFormat"));
				}
			}
		}
		return result;
	}
	
	/**
	 * Export invoice
	 * @param destinationName The destination file name for the exported invoice
	 * @param invoiceClass The class of the invoice to export
	 * @param invoiceStatus The state of the invoice to export
	 * @param invoiceName Name of the invoice (Series code + Number)
	 * @return The result of the exportation
	 * @throws ImportExportException 
	 */
	public ExportInvoiceResult exportInvoiceWithName(File destinationName, InvoiceClassType invoiceClass,
		InvoiceStatusType invoiceStatus, String invoiceName) throws ImportExportException {
		ExportInvoiceResult result = new ExportInvoiceResult();
		SQLQuery s, s2 = null;
		s = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_INVOICE_BY_STATE_CLASS_NAME.
				replace("$1", String.valueOf(invoiceStatus.ordinal())).
				replace("$2", String.valueOf(invoiceClass.ordinal())).
				replace("$3", invoiceName));
		List<?> ls = s.list();
		if (ls != null && ls.size() > 0) {
			java.sql.Blob b = null;
			Object[] oArray = (Object[]) ls.get(0);
			s2 = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_FACTURAE_XML_BY_ID.replace("$1", oArray[0].toString()));
			List<?> ls2 = s2.list();
			if (ls2 != null && ls2.size() > 0) {
				b = (java.sql.Blob) ls2.get(0);
				result = new ExportInvoiceResult();
				try {
					XMLUtil.writeXml2File(StreamUtil.createDoc(b.getBinaryStream()), destinationName.getAbsolutePath());
					result.setResult(ExportInvoiceResultType.CORRECT);
					result.setId(oArray[1].toString());
					result.setObservations(destinationName.getAbsolutePath() + ": "
						+ Constants.LANG.getString("OKExportResultMessage"));
				} catch (FileNotFoundException e) {
					logger.error("File not found exception [" + destinationName.getAbsolutePath()+ "]: " + e.getMessage());
					result.setResult(ExportInvoiceResultType.ERROR);
					result.setId(oArray[1].toString());
					result.setObservations(destinationName.getAbsolutePath() + ": "
						+ Constants.LANG.getString("NOOKFileNotFoundExport"));
				} catch (ValidationException e) {
					logger.error("Error validating the file to write: " + e.getMessage());
					result.setResult(ExportInvoiceResultType.ERROR);
					result.setId(oArray[1].toString());
					result.setObservations(invoiceName + ": " + Constants.LANG.getString("NOOKMessageFacturaeFormat"));
				} catch (SQLException e) {
					logger.error("Error getting the binary stream: " + e.getMessage());
					result.setResult(ExportInvoiceResultType.ERROR);
					result.setId(oArray[1].toString());
					result.setObservations(invoiceName + ": " + Constants.LANG.getString("NOOKMessageFacturaeFormat"));
				}
			}
		}
		return result;
	}	
	
	
}
