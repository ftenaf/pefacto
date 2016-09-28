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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.ImportExportException;
import es.mityc.appfacturae.exceptions.ParseException;
import es.mityc.appfacturae.facturae.Facturae;
import es.mityc.appfacturae.utils.IntermediaryUtil;
import es.mityc.appfacturae.utils.XMLFacturaeUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.facturae.GenericFacturae;
import es.mityc.facturae.utils.UnmarshalException;
import es.mityc.facturae.utils.UnmarshallerUtil;

public class InvoiceTypeValidator {

	private static Log logger = LogFactory.getLog(InvoiceTypeValidator.class);
	
	public static void validateType(File invoice, int option) throws ImportExportException{
		try{
			UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(XMLFacturaeUtil.getVersionOb(invoice));
			GenericFacturae generic = unmarshallerUtil.unmarshal(invoice);
			if (logger.isDebugEnabled()) {
				logger.debug("Unmarshalling finished successfully.");
				logger.debug("Converting the generic invoice from ["+invoice.getClass().getName()+"] to ["+Facturae.class.getName()+"]");
			}
			Facturae facturae = IntermediaryUtil.getApplicationFacturae(generic);
			if (logger.isDebugEnabled()) {
				logger.debug("Generic invoice converted");
			}
			boolean signature = facturae.getSignature() != null;
			boolean corrective = facturae.getInvoices().getInvoice().get(0).getInvoiceHeader().getCorrective() != null;
			switch (option){
			case 0:
				if (signature){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageSignatureExist"));
				} else if (corrective){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageCorrectiveExist"));
				}
				break;
			case 1:
			case 2:
				if (!signature){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageSignatureNoExist"));
				} else if (corrective){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageCorrectiveExist"));
				}
				break;
			case 3:
				if (!signature){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageSignatureNoExist"));
				}
				break;
			case 4:
				if (signature){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageSignatureExist"));
				} else if (!corrective){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageCorrectiveNoExist"));
				}
				break;
			case 5:
			case 6:
				if (!signature){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageSignatureNoExist"));
				} else if (!corrective){
					throw new ImportExportException(Constants.LANG.getString("NOOKMessageCorrectiveNoExist"));
				}
				break;
			}
		} catch (NullPointerException e){
			throw new ImportExportException(Constants.LANG.getString("NOOKMessageFacturaeFormat"),e);
		} catch (UnmarshalException e) {
			throw new ImportExportException("Unmarshalling: " + Constants.LANG.getString("NOOKMessageFacturaeFormat"),e);
		} catch (ParseException e) {
			throw new ImportExportException("Parsing: " + Constants.LANG.getString("NOOKMessageFacturaeFormat"),e);
		}
	}
}
