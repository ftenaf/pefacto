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
package es.mityc.appfacturae.facturae;

import javax.xml.datatype.XMLGregorianCalendar;

public class PaymentDetailsType {

	protected XMLGregorianCalendar assignmentDuePaymentDate;
    protected String assignmentPaymentMeans;
    protected String iban;
    protected String paymentReference;
    
	public XMLGregorianCalendar getAssignmentDuePaymentDate() {
        return assignmentDuePaymentDate;
    }
    
	public void setAssignmentDuePaymentDate(XMLGregorianCalendar value) {
        this.assignmentDuePaymentDate = value;
    }
    
	public String getAssignmentPaymentMeans() {
        return assignmentPaymentMeans;
    }
    
	public void setAssignmentPaymentMeans(String value) {
        this.assignmentPaymentMeans = value;
    }
    
	public String getIBAN() {
        return iban;
    }
    
	public void setIBAN(String value) {
        this.iban = value;
    }
    
	public String getPaymentReference() {
        return paymentReference;
    }
    
	public void setPaymentReference(String value) {
        this.paymentReference = value;
    }

}