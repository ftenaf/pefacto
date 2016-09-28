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

import es.mityc.appfacturae.facturae.AssigneeType;
import es.mityc.appfacturae.facturae.InstallmentsType;

public class FactoringAssignmentDataType implements es.mityc.appfacturae.facturae.FactoringAssignmentDataType {

	protected AssigneeType assignee;
    protected InstallmentsType paymentDetails;
    protected String factoringAssignmentClauses;
    protected es.mityc.appfacturae.facturae.InvoiceType invoice;
    protected int id;
    
	public AssigneeType getAssignee() {
        return assignee;
    }
    
	public void setAssignee(AssigneeType value) {
        this.assignee = value;
    }
    
	public InstallmentsType getPaymentDetails() {
        return paymentDetails;
    }
    
	public void setPaymentDetails(InstallmentsType value) {
        this.paymentDetails = value;
    }
    
	public String getFactoringAssignmentClauses() {
        return factoringAssignmentClauses;
    }
    
	public void setFactoringAssignmentClauses(String value) {
        this.factoringAssignmentClauses = value;
    }

	public es.mityc.appfacturae.facturae.InvoiceType getInvoice() {
		return invoice;
	}

	@Override
	public void setInvoice(es.mityc.appfacturae.facturae.InvoiceType invoice) {
		this.invoice = invoice;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int value) {
		this.id = value;
	}
}