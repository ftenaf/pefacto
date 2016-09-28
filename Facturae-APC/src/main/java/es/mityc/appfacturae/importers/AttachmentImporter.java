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
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.AttachedDocument;
import es.mityc.appfacturae.importers.ImportAttachmentResult.ImportAttachmentResultType;
import es.mityc.appfacturae.utils.io.FileUtil;



/**
 * This class imports attachments to the internal database.
 *
 */
public class AttachmentImporter {

	private static Log logger = LogFactory.getLog(AttachmentImporter.class);
	
	/**
	 * Import all attachments for a Invoice
	 * @param destinationDirectory Destination directory for all imported attachments. This directory MUST exist and MUST be writable
	 * @param oldInvoiceId The invoice's id in the old application
	 * @param newInvoiceId The invoice's id in the new application 
	 * @return The result of the importation
	 * @throws FileNotFoundException  If the destination directory does not exist or cannot be openend for writing
	 */
	public Collection<ImportAttachmentResult> importInvoiceAttachments(File destinationDirectory, String oldInvoiceId, String newInvoiceId) throws FileNotFoundException {
		if (!destinationDirectory.isDirectory() || !destinationDirectory.canWrite()) {
			logger.error("The destination directory ["+destinationDirectory+"] cannot be written");
			throw new FileNotFoundException("The destination directory ["+destinationDirectory+"] cannot be written");
		}
		Collection<ImportAttachmentResult> attachmentsResult = new ArrayList<ImportAttachmentResult>();
		File attachmentsDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.ATTACHMENTS_DIRECTORY);
		if (!attachmentsDirectory.exists() || !attachmentsDirectory.canRead()) {
			logger.error("The attachment directory ["+attachmentsDirectory+"] does not exist or cannot be read");
			return attachmentsResult;
		}
		File attachmentsInvoiceDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.ATTACHMENTS_DIRECTORY+File.separator+oldInvoiceId);
		if (!attachmentsInvoiceDirectory.exists()){
			logger.info("The invoice attachment directory ["+attachmentsInvoiceDirectory+"] does not exist. There is not attachments for this invoice");
		} else if (!attachmentsInvoiceDirectory.canRead()) {
			logger.error("The invoice attachment directory ["+attachmentsInvoiceDirectory+"] cannot be read.");
			throw new FileNotFoundException("The invoice attachment directory ["+attachmentsInvoiceDirectory+"] cannot be read.");
		} else {
			logger.info("Importing files contained in invoice attachment directory ["+attachmentsInvoiceDirectory+"].");
			File destinationAttachmentsInvoiceDirectory = new File(destinationDirectory.getAbsoluteFile()+File.separator+newInvoiceId);
			if (destinationAttachmentsInvoiceDirectory.exists() || destinationAttachmentsInvoiceDirectory.mkdir()) {
				//Offer attachments
				logger.info("Importing offer attachments for invoice id ["+newInvoiceId+"]...");
				Collection<ImportAttachmentResult> partialResults = importInvoiceAttachmentsCategory(oldInvoiceId, attachmentsInvoiceDirectory, newInvoiceId, destinationAttachmentsInvoiceDirectory, ConstantsOldApp.OFFERS_ATTACHMENTS_DIRECTORY);
				attachmentsResult.addAll(partialResults);
				logger.info("["+partialResults.size()+"] offers attachments for invoice id ["+newInvoiceId+"] were imported");
				//Orders attachments
				logger.info("Importing orders attachments for invoice id ["+newInvoiceId+"]...");
				partialResults = importInvoiceAttachmentsCategory(oldInvoiceId, attachmentsInvoiceDirectory, newInvoiceId, destinationAttachmentsInvoiceDirectory, ConstantsOldApp.ORDERS_ATTACHMENTS_DIRECTORY);
				attachmentsResult.addAll(partialResults);
				logger.info("["+partialResults.size()+"] orders attachments for invoice id ["+newInvoiceId+"] were imported");
				//Contracts attachments
				logger.info("Importing contracts attachments for invoice id ["+newInvoiceId+"]...");
				partialResults = importInvoiceAttachmentsCategory(oldInvoiceId, attachmentsInvoiceDirectory, newInvoiceId, destinationAttachmentsInvoiceDirectory, ConstantsOldApp.CONTRACTS_ATTACHMENTS_DIRECTORY);
				attachmentsResult.addAll(partialResults);
				logger.info("["+partialResults.size()+"] contracts attachments for invoice id ["+newInvoiceId+"] were imported");
				//Delivery notes attachments
				logger.info("Importing delivery notes attachments for invoice id ["+newInvoiceId+"]...");
				partialResults = importInvoiceAttachmentsCategory(oldInvoiceId, attachmentsInvoiceDirectory, newInvoiceId, destinationAttachmentsInvoiceDirectory, ConstantsOldApp.DELIVERY_NOTES_ATTACHMENTS_DIRECTORY);
				attachmentsResult.addAll(partialResults);
				logger.info("["+partialResults.size()+"] delivery notes attachments for invoice id ["+newInvoiceId+"] were imported");
				//Communications attachments
				logger.info("Importing communications attachments for invoice id ["+newInvoiceId+"]...");
				partialResults = importInvoiceAttachmentsCategory(oldInvoiceId, attachmentsInvoiceDirectory, newInvoiceId, destinationAttachmentsInvoiceDirectory, ConstantsOldApp.COMMUNICATIONS_ATTACHMENTS_DIRECTORY);
				attachmentsResult.addAll(partialResults);
				logger.info("["+partialResults.size()+"] communications attachments for invoice id ["+newInvoiceId+"] were imported");

				logger.info("Attachments for invoice id ["+newInvoiceId+"] were imported");
			} else {
				throw new FileNotFoundException("The destination directory ["+destinationAttachmentsInvoiceDirectory.getAbsolutePath()+"] for the attachments of the invoice ["+newInvoiceId+"] cannot be created");
			}
		}
		return attachmentsResult;
	}
	
	/**
	 * Import all attachments of an especific category of an invoice
	 * @param oldInvoiceId The invoice id
	 * @param attachmentsInvoiceDirectory Source directory containing the attachments. This directory MUST exist and MUST be readable
	 * @param newInvoiceId The invoice id in the new application (1.0)
	 * @param destinationAttachmentsInvoiceDirectory Destination directory where attachments will be copied. This directory MUST exist and MUST be writable
	 * @param attachmentsCategory Attachments category
	 * @return The result of the importation
	 * @throws FileNotFoundException  If the source or destination directory does not exist or the permissions are not suitable.
	 */
	private Collection<ImportAttachmentResult> importInvoiceAttachmentsCategory(String oldInvoiceId, File attachmentsInvoiceDirectory, String newInvoiceId, File destinationAttachmentsInvoiceDirectory, String attachmentsCategory) throws FileNotFoundException {
		File sourceDirectoryCategory = new File(attachmentsInvoiceDirectory.getAbsoluteFile()+File.separator+attachmentsCategory);
		Collection<ImportAttachmentResult> results = new ArrayList<ImportAttachmentResult>();
		// If the directory for this type does not exists we return an empty collection, but it is not an error
		if (!sourceDirectoryCategory.exists()) {
			return results;
		}
		if (!sourceDirectoryCategory.isDirectory() || !sourceDirectoryCategory.canRead()) {
			logger.error("The source directory ["+sourceDirectoryCategory+"] cannot be read");
			throw new FileNotFoundException("The source directory ["+sourceDirectoryCategory+"] cannot be read");
		}
		if (!destinationAttachmentsInvoiceDirectory.isDirectory() || !destinationAttachmentsInvoiceDirectory.canWrite()) {
			logger.error("The destination directory ["+destinationAttachmentsInvoiceDirectory+"] cannot be written");
			throw new FileNotFoundException("The destination directory ["+destinationAttachmentsInvoiceDirectory+"] cannot be written");
		}
		File destinationDirectoryCategory = new File(destinationAttachmentsInvoiceDirectory.getAbsoluteFile()+File.separator+attachmentsCategory);
		if (!destinationDirectoryCategory.exists() && !destinationDirectoryCategory.mkdir()) {
			throw new FileNotFoundException("The destination directory for the type ["+attachmentsCategory+"] in the directory ["+destinationDirectoryCategory+"] cannot be created");
		}
		File[] attachmentsToImport = sourceDirectoryCategory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
		for (int i = 0; i < attachmentsToImport.length; i++) {
			ImportAttachmentResult importAttachmentResult = new ImportAttachmentResult();
			File attachment = attachmentsToImport[i];
			if (logger.isDebugEnabled()) {
				logger.debug("Importing the attachment ["+attachment.getAbsolutePath()+"]...");
			}
			importAttachmentResult.setFile(attachment);
			importAttachmentResult.setType(attachmentsCategory);
			AttachedDocument attachedDocument = new AttachedDocument();
			attachedDocument.setInvoiceId(newInvoiceId);
			attachedDocument.setDescription(attachmentsCategory+" for invoice "+newInvoiceId);
			attachedDocument.setIsIncluded(0);
			File attachmedCopied = new File(destinationDirectoryCategory, attachment.getName());
			try {
				FileUtil.copy(attachment, attachmedCopied, true);
				attachedDocument.setPath(attachmedCopied.getAbsolutePath());
				attachedDocument.setExtension(FileUtil.getExtension(attachmedCopied).toUpperCase());
				FacturaeManager.getInstance().saveAttachment(attachedDocument);
				importAttachmentResult.setResult(ImportAttachmentResultType.CORRECT);
				if (logger.isDebugEnabled()) {
					logger.debug("Attachment ["+attachment.getAbsolutePath()+"] imported successfully");
				}
			} catch (IOException e) {
				logger.error("Error copying the attachment from file ["+attachment.getAbsolutePath()+"] to file ["+attachmedCopied.getAbsolutePath()+"]");
				if (logger.isDebugEnabled()) {
					logger.error("", e);
				}
				importAttachmentResult.setResult(ImportAttachmentResultType.ERROR);
				importAttachmentResult.setObservations("Error copying the file");
			} catch (DatabaseOperationException e) {
				logger.error("Error during data base operation with the attachment ["+attachment.getAbsolutePath()+"]");
				if (logger.isDebugEnabled()) {
					logger.error("", e);
				}
				importAttachmentResult.setResult(ImportAttachmentResultType.ERROR);
				importAttachmentResult.setObservations("Data base operation error");
			}
			results.add(importAttachmentResult);
		}
		return results;
	}	
	
}
