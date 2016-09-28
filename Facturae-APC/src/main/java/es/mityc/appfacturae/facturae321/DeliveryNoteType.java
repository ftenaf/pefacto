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
package es.mityc.appfacturae.facturae321;

import javax.xml.datatype.XMLGregorianCalendar;

public class DeliveryNoteType implements es.mityc.appfacturae.facturae.DeliveryNoteType{

	protected String deliveryNoteNumber;
	protected XMLGregorianCalendar deliveryNoteDate;
	
	public String getDeliveryNoteNumber() {
        return deliveryNoteNumber;
    }
    
	public void setDeliveryNoteNumber(String value) {
        this.deliveryNoteNumber = value;
    }

	public XMLGregorianCalendar getDeliveryNoteDate() {
		return deliveryNoteDate;
	}

	public void setDeliveryNoteDate(XMLGregorianCalendar deliveryNoteDate) {
		this.deliveryNoteDate = deliveryNoteDate;
	}

}