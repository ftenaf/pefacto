/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-APC".
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto sean aprobadas por
 * la Comisión Europea– versiones posteriores de la EUPL (la Licencia); Solo
 * podrá usarse esta obra si se respeta la Licencia.
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

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.SQLQuery;
import org.w3c.dom.Element;

import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.utils.constants.Constants;

public class IntermediaryUtil {

	/**
	 * Converts an es.mityc.facturae.GenericElectronicInvoice object into an es.mityc.appfacturae.facturae.Facturae object  ***
	 * @param invoice The es.mityc.facturae.GenericElectronicInvoice object to convert
	 * @return The es.mityc.appfacturae.facturae.Facturae object
	 */
	public static es.mityc.appfacturae.facturae.Facturae getApplicationFacturae(
		es.mityc.facturae.GenericFacturae invoice) {
		if (invoice instanceof es.mityc.facturae32.Facturae) {
			return IntermediaryUtil32.getApplicationFacturae((es.mityc.facturae32.Facturae) invoice);
		} else if (invoice instanceof es.mityc.facturae321.Facturae) {
			return IntermediaryUtil321.getApplicationFacturae((es.mityc.facturae321.Facturae) invoice);
		} else {
			throw new IllegalArgumentException("The invoice is not supported.");
		}
	}

	/**
	 * Converts an es.mityc.appfacturae.facturae.Facturae object into an es.mityc.facturae.GenericFacturae object  ***
	 * @param facturae The es.mityc.appfacturae.facturae.Facturae object to convert
	 * @return The es.mityc.facturae.GenericFacturae object
	 */
	public static es.mityc.facturae.GenericFacturae getfacturae(es.mityc.appfacturae.facturae.Facturae facturae) {
		String version = facturae.getFileHeader().getSchemaVersion();
		if (version.equals(Constants.VERSION32)) {
			return IntermediaryUtil32.getfacturae32(facturae);
		} else if (version.equals(Constants.VERSION321)) {
			return IntermediaryUtil321.getfacturae321(facturae);
		} else {
			throw new IllegalArgumentException("The invoice is not supported.");
		}
	}

}
