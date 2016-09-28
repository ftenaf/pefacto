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

public class ConstantsOldApp {

	public static final String INSTALLATION_PATH_KEY_NAME = "Software\\Facturación Electrónica";
	public static final String INSTALLATION_PATH_VALUE_NAME = "instPath";

	/** Directory of the invoices files */
	public static final String INVOICES_DIRECTORY = "xml";
	
	/** Directory of the invoices in draft status */
	public static final String DRAFT_INVOICES_DIRECTORY = "borrador";
	/** Directory of the invoices in issued status */
	public static final String ISSUED_INVOICES_DIRECTORY = "emitidas";
	/** Directory of the invoices in sent status */
	public static final String SENT_INVOICES_DIRECTORY = "enviadas";
	/** Directory of the invoices in received status */
	public static final String RECEIVED_INVOICES_DIRECTORY = "recibidas";
	/** Directory of the rectified invoices */
	public static final String RECTIFIED_INVOICES_DIRECTORY = "rectificadas";

	
	/** Old application installation directory */
	public static File INSTALL_PATH = null;
	
	/** Directory of the attachments files */
	public static final String ATTACHMENTS_DIRECTORY = "adjuntos";
	
	/** Directory of the attachments whose category is offer */
	public static final String OFFERS_ATTACHMENTS_DIRECTORY = "Oferta";
	/** Directory of the attachments whose category is order */
	public static final String ORDERS_ATTACHMENTS_DIRECTORY = "Pedido";
	/** Directory of the attachments whose category is contract */
	public static final String CONTRACTS_ATTACHMENTS_DIRECTORY = "Contrato";
	/** Directory of the attachments whose category is delivery note */
	public static final String DELIVERY_NOTES_ATTACHMENTS_DIRECTORY = "Albarán";
	/** Directory of the attachments whose category is communication */
	public static final String COMMUNICATIONS_ATTACHMENTS_DIRECTORY = "Comunicaciones";
	
}
