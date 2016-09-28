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
package es.mityc.appfacturae.importers.transformers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.facturae.InvoiceType;
import es.mityc.appfacturae.utils.constants.Constants;

public class DraftInvoiceTransformer implements InvoiceTransformer {
	
	private static Log logger = LogFactory.getLog(DraftInvoiceTransformer.class);

	public void transform(InvoiceType invoice) {
		String draftPrefix;
		String draftId;
		draftPrefix = Constants.LANG.getString("Draft");
		draftId = Constants.CONFIG_PROP.getProperty("draftId");
		Constants.CONFIG_PROP.setProperty("draftId", String.valueOf(Integer.parseInt(draftId)+1));
		invoice.getInvoiceHeader().setInvoiceSeriesCode(draftPrefix);
		invoice.getInvoiceHeader().setInvoiceNumber(draftId);
	}
	
	public void consolidateLastTranformation() {
    	try{
    		Constants.CONFIG_PROP.store(new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file"))), "draftId property updated");
        }
    	catch(FileNotFoundException fnfe){
    		logger.error("Error saving the properties file");
    		if (logger.isDebugEnabled()) {
    			logger.error("", fnfe);
    		}
    	}
    	catch(IOException ioe){
    		logger.error("Error saving the properties file");
    		if (logger.isDebugEnabled()) {
    			logger.error("", ioe);
    		}
    	}
    	catch(Exception e) {
    		logger.error("Error saving the properties file");
    		if (logger.isDebugEnabled()) {
    			logger.error("", e);
    		}
		}
	}
	
	public void cancelLastTranformation() {
		String draftId = Constants.CONFIG_PROP.getProperty("draftId");
		Constants.CONFIG_PROP.setProperty("draftId", String.valueOf(Integer.parseInt(draftId)-1));
	}
}
