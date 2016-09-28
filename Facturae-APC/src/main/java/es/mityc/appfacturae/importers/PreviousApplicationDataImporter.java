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
import java.util.ArrayList;
import java.util.Collection;
import java.util.prefs.BackingStoreException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.PreviousApplicationNotFoundException;
import es.mityc.appfacturae.exceptions.UnsupportedOperatingSystemException;
import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.importers.transformers.DraftInvoiceTransformer;
import es.mityc.appfacturae.importers.transformers.DraftRectifiedInvoiceTransformer;
import es.mityc.facturae.FacturaeVersion;

/**
 * This class imports all data from previous versions like 0.9. This class only works in Windows
 *
 */
public class PreviousApplicationDataImporter {

	private static Log logger = LogFactory.getLog(PreviousApplicationDataImporter.class);
	
	// Singleton
	private static PreviousApplicationDataImporter padi = null;
	
	/**
	 * Constructor
	 * @throws UnsupportedOperatingSystemException If the operating system is not supported
	 * @throws PreviousApplicationNotFoundException If the previous application is not found
	 */
	private PreviousApplicationDataImporter() throws UnsupportedOperatingSystemException, PreviousApplicationNotFoundException {
		String osName = System.getProperty("os.name");
		if (logger.isInfoEnabled()) {
			logger.info("Executing the importer in the ["+osName+"] operating system.");
		}
		if (!osName.contains("Windows")) {
			logger.error("This importer cannot be executed in the ["+osName+"] operating system.");
			throw new UnsupportedOperatingSystemException("This importer only works in Windows systems.");
		}
		
		ConstantsOldApp.INSTALL_PATH = getInstallPath();
	}
	
	/**
	 * Get Instance
	 * @throws UnsupportedOperatingSystemException If the operating system is not supported
	 * @throws PreviousApplicationNotFoundException If the previous application is not found
	 */
	public static PreviousApplicationDataImporter getInstance() throws UnsupportedOperatingSystemException, PreviousApplicationNotFoundException {
    	if (padi == null){
			try {
				padi = new PreviousApplicationDataImporter();
			} catch (UnsupportedOperatingSystemException e) {
				throw e;
			} catch (PreviousApplicationNotFoundException e) {
				throw e;
			}
    	}
    	
    	return padi;
    }

	/**
	 * Get the install path directory
	 * @return The install path directory
	 * @throws PreviousApplicationNotFoundException If the install path of the previous application does not exists or cannot be read
	 */
	private File getInstallPath() throws PreviousApplicationNotFoundException {
		logger.info("Obtaining the install path of the old application...");
		File installPath = null;
		try {
			String installPathStr = WindowsRegistry.getKeySz(WindowsRegistry.HKEY_CURRENT_USER, ConstantsOldApp.INSTALLATION_PATH_KEY_NAME, ConstantsOldApp.INSTALLATION_PATH_VALUE_NAME);
			if (logger.isInfoEnabled()) {
				logger.info("The install path is ["+installPathStr+"].");
			}
			installPath = new File(installPathStr);
			if (!installPath.exists() || !installPath.canRead() || !installPath.isDirectory()) {
				logger.error("The install path directory does not exist or cannot be read");
				throw new PreviousApplicationNotFoundException("The install path directory does not exist or cannot be read");
			}
			return installPath;
		} catch (BackingStoreException e) {
			logger.error("Error retrieving the application path from the registry.");
			if (logger.isDebugEnabled()) {
				logger.error("", e);
			}
			throw new PreviousApplicationNotFoundException("The install path of the previous application cannot be retrieved.");
		}
	}
	
	/**
	 * Import all invoices
	 * @return The result of the importation
	 */
	public Collection<ImportInvoiceResult> importInvoices(File attDestinationDirectory) {
		
		Collection<ImportInvoiceResult> results = new ArrayList<ImportInvoiceResult>();
		InvoiceImporter invoiceImporter = new InvoiceImporter();
		
		logger.info("Importing draft invoices...");
		File draftInvoicesDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.INVOICES_DIRECTORY+File.separator+ConstantsOldApp.DRAFT_INVOICES_DIRECTORY);
		Collection<ImportInvoiceResult> partialResults = invoiceImporter.importInvoicesCategory(draftInvoicesDirectory, FacturaeVersion.FACTURAE_30, InvoiceStatusType.D, InvoiceClassType.OO, new DraftInvoiceTransformer(), attDestinationDirectory);
		results.addAll(partialResults);
		if (logger.isInfoEnabled()) {
			logger.info("["+partialResults.size()+"] Draft invoices were imported");
		}
		
		logger.info("Importing issued invoices...");
		File issuedInvoicesDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.INVOICES_DIRECTORY+File.separator+ConstantsOldApp.ISSUED_INVOICES_DIRECTORY);
		partialResults = invoiceImporter.importInvoicesCategory(issuedInvoicesDirectory, FacturaeVersion.FACTURAE_30, InvoiceStatusType.I, InvoiceClassType.OO, null, attDestinationDirectory);
		results.addAll(partialResults);
		if (logger.isInfoEnabled()) {
			logger.info("["+partialResults.size()+"] Issued invoices were imported");
		}
		
		logger.info("Importing sent invoices...");
		File sentInvoicesDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.INVOICES_DIRECTORY+File.separator+ConstantsOldApp.SENT_INVOICES_DIRECTORY);
		partialResults = invoiceImporter.importInvoicesCategory(sentInvoicesDirectory, FacturaeVersion.FACTURAE_30, InvoiceStatusType.S, InvoiceClassType.OO, null, attDestinationDirectory);
		results.addAll(partialResults);
		if (logger.isInfoEnabled()) {
			logger.info("["+partialResults.size()+"] Sent invoices were imported");
		}
		
		logger.info("Importing received invoices...");
		File receivedInvoicesDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.INVOICES_DIRECTORY+File.separator+ConstantsOldApp.RECEIVED_INVOICES_DIRECTORY);
		partialResults = invoiceImporter.importInvoicesCategory(receivedInvoicesDirectory, FacturaeVersion.FACTURAE_30, InvoiceStatusType.R, InvoiceClassType.OO, null, attDestinationDirectory);
		results.addAll(partialResults);
		if (logger.isInfoEnabled()) {
			logger.info("["+partialResults.size()+"] received invoices were imported");
		}
		
		logger.info("Importing rectified and draft invoices...");
		File rectifiedAndDraftInvoicesDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.INVOICES_DIRECTORY+File.separator+ConstantsOldApp.RECTIFIED_INVOICES_DIRECTORY+File.separator+ConstantsOldApp.DRAFT_INVOICES_DIRECTORY);
		partialResults = invoiceImporter.importInvoicesCategory(rectifiedAndDraftInvoicesDirectory, FacturaeVersion.FACTURAE_30, InvoiceStatusType.D, InvoiceClassType.OR, new DraftRectifiedInvoiceTransformer(),attDestinationDirectory);
		results.addAll(partialResults);
		if (logger.isInfoEnabled()) {
			logger.info("["+partialResults.size()+"] rectified and draft invoices were imported");
		}

		logger.info("Importing rectified and issued invoices...");
		File rectifiedAndIssuedInvoicesDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.INVOICES_DIRECTORY+File.separator+ConstantsOldApp.RECTIFIED_INVOICES_DIRECTORY+File.separator+ConstantsOldApp.ISSUED_INVOICES_DIRECTORY);
		partialResults = invoiceImporter.importInvoicesCategory(rectifiedAndIssuedInvoicesDirectory, FacturaeVersion.FACTURAE_30, InvoiceStatusType.I, InvoiceClassType.OR, null, attDestinationDirectory);
		results.addAll(partialResults);
		if (logger.isInfoEnabled()) {
			logger.info("["+partialResults.size()+"] rectified and issued invoices were imported");
		}

		logger.info("Importing rectified and sent invoices...");
		File rectifiedAndSentInvoicesDirectory = new File(ConstantsOldApp.INSTALL_PATH.getAbsolutePath()+File.separator+ConstantsOldApp.INVOICES_DIRECTORY+File.separator+ConstantsOldApp.RECTIFIED_INVOICES_DIRECTORY+File.separator+ConstantsOldApp.SENT_INVOICES_DIRECTORY);
		partialResults = invoiceImporter.importInvoicesCategory(rectifiedAndSentInvoicesDirectory, FacturaeVersion.FACTURAE_30, InvoiceStatusType.S, InvoiceClassType.OR, null, attDestinationDirectory);
		results.addAll(partialResults);
		if (logger.isInfoEnabled()) {
			logger.info("["+partialResults.size()+"] rectified and sent invoices were imported");
		}

		return results;
	}
}
