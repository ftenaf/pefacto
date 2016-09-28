/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NodeList;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.DuplicatedInvoiceException;
import es.mityc.appfacturae.exceptions.ImportExportException;
import es.mityc.appfacturae.exceptions.ParseException;
import es.mityc.appfacturae.facturae.Facturae;
import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.facturae.InvoiceHeaderType;
import es.mityc.appfacturae.facturae.InvoiceType;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.hibernate.auxClass.xmlData;
import es.mityc.appfacturae.importers.ImportInvoiceResult.ImportInvoiceResultType;
import es.mityc.appfacturae.importers.transformers.InvoiceTransformer;
import es.mityc.appfacturae.utils.IntermediaryUtil;
import es.mityc.appfacturae.utils.XMLFacturaeUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FileUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.FacturaeVersion;
import es.mityc.facturae.GenericFacturae;
import es.mityc.facturae.utils.UnmarshalException;
import es.mityc.facturae.utils.UnmarshallerUtil;
import es.mityc.facturae.utils.ValidationException;

/**
 * This class imports invoices to the internal database.
 *
 */
public class InvoiceImporter {

	private static Log logger = LogFactory.getLog(InvoiceImporter.class);
	private String idTemp = "";
	
	/**
	 * This Private class implements the filter for invoice files 
	 *
	 */
	private class InvoicesFileFilter implements FileFilter {
		public boolean accept(File pathname) {
			String filename = pathname.getName().toLowerCase();
			if (pathname.isFile() && (filename.endsWith(".xml") || filename.endsWith(".xsig"))) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Import an invoice from a file.
	 * @param file The invoice file
	 * @param invoiceType The type of the invoice to import
	 * @param invoiceClass The class of the invoice to import
	 * @param invoiceStatus The status of the invoice to import
	 * @param invoiceTransformer A transformer for the invoice or <code>null</code> to not transform the invoice number
	 * @return The result of the importation
	 */
	public ImportInvoiceResult importInvoice(File file, FacturaeVersion invoiceType, InvoiceClassType invoiceClass, InvoiceStatusType invoiceStatus, InvoiceTransformer invoiceTransformer, boolean attachments) {
		ImportInvoiceResult result = new ImportInvoiceResult();
		String receiveId = null;
		String invoiceId = null;
		String oldInvoiceId = null;
		boolean transformationDone = false;
		boolean error = false;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Validating parameters of file ["+file.getAbsolutePath()+"]");
			}
			
			validateParameters(file, invoiceClass, invoiceStatus);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Loading version if it is null ["+file.getAbsolutePath()+"]");
			}
			
			if (invoiceType == null){
				invoiceType = XMLFacturaeUtil.getVersionOb(file);
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("Unmarshalling the file ["+file.getAbsolutePath()+"]");
			}
			//ctg attach
			UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(invoiceType);
			GenericFacturae invoice = unmarshallerUtil.unmarshal(file);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Unmarshalling finished successfully.");
				logger.debug("Converting the generic invoice from ["+invoice.getClass().getName()+"] to ["+Facturae.class.getName()+"]");
			}
			FacturaeManager facturaeManager = FacturaeManager.getInstance();
			Facturae facturaeToSave = IntermediaryUtil.getApplicationFacturae(invoice);
			if (logger.isDebugEnabled()) {
				logger.debug("Generic invoice converted");
			}

			//The application only admit an invoice per file although the format admits batches
			InvoiceType invoiceTypeObj = facturaeToSave.getInvoices().getInvoice().get(0);
			if (logger.isDebugEnabled()) {
				logger.debug("Obtaining the invoice id");
			}
			
			if (invoiceTypeObj.getInvoiceHeader().getInvoiceSeriesCode() != null && !"".equals(invoiceTypeObj.getInvoiceHeader().getInvoiceSeriesCode()))
				invoiceId = invoiceTypeObj.getInvoiceHeader().getInvoiceSeriesCode() + invoiceTypeObj.getInvoiceHeader().getInvoiceNumber();
			else
				invoiceId = invoiceTypeObj.getInvoiceHeader().getInvoiceNumber();
			
			oldInvoiceId = invoiceId;
			
			if (invoiceTransformer != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Transforming the invoice with the transformer ["+invoiceTransformer.getClass()+"]");
				}
				invoiceTransformer.transform(invoiceTypeObj);
				transformationDone = true;
				oldInvoiceId = invoiceId;
				if (invoiceTypeObj.getInvoiceHeader().getInvoiceSeriesCode() != null && !"".equals(invoiceTypeObj.getInvoiceHeader().getInvoiceSeriesCode()))
					invoiceId = invoiceTypeObj.getInvoiceHeader().getInvoiceSeriesCode() + invoiceTypeObj.getInvoiceHeader().getInvoiceNumber();
				else
					invoiceId = invoiceTypeObj.getInvoiceHeader().getInvoiceNumber();
				if (logger.isDebugEnabled()) {
					logger.debug("Old invoice id ["+oldInvoiceId+"]. New invoice id ["+invoiceId+"]");
				}
			}
			
			result.setNewId(invoiceId);
			result.setOldId(oldInvoiceId);
			
			logger.debug("Establishing the status of the invoice");
			invoiceTypeObj.setState(invoiceStatus);

			logger.debug("Establishing the class of the invoice");
			InvoiceHeaderType invoiceHeader = invoiceTypeObj.getInvoiceHeader();
			invoiceHeader.setInvoiceClass(invoiceClass);
			
			// The file (XML) is saved and included in the facturae object
			byte[] data = new byte[(int)file.length()];
			try {
				StreamUtil.readStream(new FileInputStream(file), data);
			} catch (IOException e) {
				logger.error ("I/O error during invoice sign" + e.getMessage());
				throw new ImportExportException("I/O error during invoice sign", e);
			}
			xmlData xsig = new xmlData();
			xsig.createBlob(data);
			facturaeToSave.setXsig(xsig);
			
			if (logger.isInfoEnabled()) {
				logger.info("Saving the invoice with id ["+invoiceId+"], type ["+invoiceType+"] status ["+invoiceStatus+"] and class ["+invoiceClass+"] to the database");
			}
			if (InvoiceStatusType.R.compareTo(invoiceStatus) == 0){
				receiveId = Constants.CONFIG_PROP.getProperty("receivedId");
				result.setNewId(Constants.LANG.getString("Received")+receiveId);
				facturaeManager.saveFacturae(facturaeToSave, receiveId);
				Constants.CONFIG_PROP.setProperty("receivedId", String.valueOf(Integer.parseInt(receiveId)+1));
				try {
					Constants.CONFIG_PROP.store(new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file"))), new String("receivedId property updated"));
				} catch(Exception e) {
					logger.error("The configuration could not be saved: " + e.getMessage());
				}
			}
			else{
				facturaeManager.saveFacturae(facturaeToSave, null);
			}
			
			logger.info("Invoice saved successfully");
			result.setResult(ImportInvoiceResultType.CORRECT);
			if (invoiceTransformer != null) {
				logger.debug("Canceling the last invoice transformation");
				invoiceTransformer.consolidateLastTranformation();
			}
			
			if (attachments){
				AttachmentImporter attachmentImporter = new AttachmentImporter();
				// Importing the associated attachments
				Collection<ImportAttachmentResult> resultAttachments = attachmentImporter.importInvoiceAttachments(new File("C:/Temp/importacionFacturae"), oldInvoiceId, invoiceId);
				result.setAttachmentsResult(resultAttachments);
			}

		} catch (UnmarshalException e) {
			logger.error("Error unmarshalling the file ["+file.getAbsolutePath()+"]: " + e.getMessage());
			result.setNewId("");
			result.setOldId(idTemp);
			result.setResult(ImportInvoiceResult.ImportInvoiceResultType.ERROR);
			result.setObservations(Constants.LANG.getString("NOOKMessageFileTransform"));
			error = true;
		} catch (DuplicatedInvoiceException e) {
			logger.error("The invoice with id ["+invoiceId+"] already exists in the database: " + e.getMessage());
			result.setNewId("");
			result.setOldId(idTemp);
			result.setResult(ImportInvoiceResult.ImportInvoiceResultType.ERROR);
			result.setObservations(Constants.LANG.getString("NOOKMessageDuplicatedFacturae"));
			error = true;
		} catch (FileNotFoundException e) {
			logger.error("Error importing ["+invoiceId+"] due to directory operation: " + e.getMessage());
			result.setNewId("");
			result.setOldId(idTemp);
			result.setResult(ImportInvoiceResult.ImportInvoiceResultType.ERROR);
			result.setObservations(Constants.LANG.getString("NOOKMessageImportProcess"));
			error = true;
		}
		catch (ValidationException e) {
			logger.error("Error validation xml ["+file.getAbsolutePath()+"]: " + e.getMessage());
			result.setNewId("");
			result.setOldId(idTemp);
			result.setResult(ImportInvoiceResult.ImportInvoiceResultType.ERROR);
			result.setObservations(Constants.LANG.getString("NOOKSchemeValidation"));
			error = true;
		}
		catch (DatabaseOperationException e) {
			logger.error("Error in data base operation importing ["+invoiceId+"]: " + e.getMessage());
			result.setNewId("");
			result.setOldId(idTemp);
			result.setResult(ImportInvoiceResult.ImportInvoiceResultType.ERROR);
			result.setObservations(Constants.LANG.getString("NOOKMessageImportProcess"));
			error = true;
		} catch (ParseException e) {
			logger.error("Error validating parameters ["+invoiceId+"]: " + e.getMessage());
			result.setNewId("");
			result.setOldId(idTemp);
			result.setResult(ImportInvoiceResult.ImportInvoiceResultType.ERROR);
			result.setObservations(e.getMessage());
			error = true;
		} catch (Exception e) {
			logger.error("Internal error importing the file ["+file.getAbsolutePath()+"]: " + e.getMessage());
			result.setNewId("");
			result.setOldId(idTemp);
			result.setResult(ImportInvoiceResult.ImportInvoiceResultType.ERROR);
			result.setObservations(Constants.LANG.getString("NOOKErrorDuringImport"));
			error = true;
		} finally {
			if (error && invoiceTransformer != null && transformationDone) {
				logger.debug("Canceling the last invoice transformation");
				invoiceTransformer.cancelLastTranformation();
			}
			
		}
		return result;
	}
	
	private void validateParameters(File file, InvoiceClassType invoiceClass, InvoiceStatusType invoiceStatus) throws ParseException {
		org.w3c.dom.Document doc = null;
		String version = "";
		idTemp = "";
		/** Parsing from File to Document in order to obtain the schema version **/
		try {
			doc = FileUtil.createDoc(file);
		} catch(ParseException pe) {
			throw pe;
		}
		
		// Ids loading
		NodeList nl = doc.getElementsByTagName("InvoiceSeriesCode");
		if (nl.getLength() > 0){
			idTemp = nl.item(0).getTextContent();
		}
		nl = doc.getElementsByTagName("InvoiceNumber");
		if (nl.getLength() > 0){
			idTemp += nl.item(0).getTextContent();
		}

		// Version
		nl = doc.getElementsByTagName("SchemaVersion");
		if (nl.getLength() > 0){
			version = nl.item(0).getTextContent();
		}
		else{
			throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeFormat"));
		}
		if (!Constants.VERSION32.equals(version) && !Constants.VERSION321.equals(version))
			throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeVersionNotCorrespond"));
		
		// Class
		nl = doc.getElementsByTagName("Corrective");
		if (nl.getLength() > 0){
			if (invoiceClass.compareTo(InvoiceClassType.OR) != 0 && invoiceStatus.compareTo(InvoiceStatusType.R) != 0){
				throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeClassOO"));
			}
		}
		else{
			if (invoiceClass.compareTo(InvoiceClassType.OR) == 0)
				throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeClassOR"));
		}
		
		// Status
		nl = doc.getElementsByTagNameNS("*","Signature");
		if (invoiceStatus.compareTo(InvoiceStatusType.D) == 0 && nl.getLength() > 0){
			throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeStatusD"));
		}
		else{
			if ((invoiceStatus.compareTo(InvoiceStatusType.I) == 0 || invoiceStatus.compareTo(InvoiceStatusType.S) == 0 || invoiceStatus.compareTo(InvoiceStatusType.R) == 0) && nl.getLength() < 1)
				throw new ParseException(Constants.LANG.getString("NOOKMessageFacturaeNoSign"));
		}
	}

	/**
	 * Import all invoice from a directory
	 * @param directory The directory containing the invoices
	 * @param invoiceType The type of the invoices to import
	 * @param invoiceClass The class of the invoices to import
	 * @param invoiceStatus The status of the invoices to import
	 * @param invoiceTransformer A transformer for the invoice number or <code>null</code> to not transform the invoice number
	 * @param attDestinationDirectory The directory containing the attachments 
	 * @return A collection of importation results
	 */
	public Collection<ImportInvoiceResult> importInvoicesCategory(File directory, FacturaeVersion invoiceType, InvoiceStatusType invoiceStatus, InvoiceClassType invoiceClass, InvoiceTransformer invoiceTransformer, File attDestinationDirectory) {
		Collection<ImportInvoiceResult> results = new ArrayList<ImportInvoiceResult>();
		if (!directory.exists() || !directory.isDirectory() || !directory.canRead()) {
			logger.error("The directory ["+directory.getAbsolutePath()+"] does not exist or cannot be read");
			return results;
		}
		
		File[] invoices = directory.listFiles(new InvoicesFileFilter());
		
		for (int i = 0; i < invoices.length; i++) {
			File invoice = invoices[i];
			ImportInvoiceResult importResult = importInvoice(invoice, invoiceType, invoiceClass, invoiceStatus, invoiceTransformer, true);
			results.add(importResult);
		}
		return results;
	}
	
	/**
	 * Import specified (selected by user) invoices 
	 * @param invoices The Facturae invoices to import
	 * @param invoiceClass The class of the invoices to import
	 * @param invoiceStatus The status of the invoices to import
	 * @param invoiceTransformer A transformer for the invoice number or <code>null</code> to not transform the invoice number 
	 * @return A collection of importation results
	 */
	public Collection<ImportInvoiceResult> importInvoices(File[] invoices, InvoiceClassType invoiceClass, InvoiceStatusType invoiceStatus, InvoiceTransformer invoiceTransformer){
		Collection<ImportInvoiceResult> results = new ArrayList<ImportInvoiceResult>();
		for (int i = 0; i < invoices.length; i++){
			ImportInvoiceResult importResult = new ImportInvoiceResult();
			importResult = importInvoice(invoices[i], null, invoiceClass, invoiceStatus, invoiceTransformer, false);
			results.add(importResult);
		}
		return results;
	}
	

}
