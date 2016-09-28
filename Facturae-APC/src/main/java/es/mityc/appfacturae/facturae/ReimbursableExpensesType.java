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
package es.mityc.appfacturae.facturae;
 
import javax.xml.datatype.XMLGregorianCalendar;

import es.mityc.appfacturae.facturae.TaxIdentificationType;


public class ReimbursableExpensesType {

	protected TaxIdentificationType reimbursableExpensesSellerParty;
    protected TaxIdentificationType reimbursableExpensesBuyerParty;
    protected XMLGregorianCalendar issueDate;
    protected String invoiceNumber;
    protected String invoiceSeriesCode;
    protected double reimbursableExpensesAmount;
    
	public TaxIdentificationType getReimbursableExpensesSellerParty() {
        return reimbursableExpensesSellerParty;
    }
    
	public void setReimbursableExpensesSellerParty(TaxIdentificationType value) {
        this.reimbursableExpensesSellerParty = value;
    }
    
	public TaxIdentificationType getReimbursableExpensesBuyerParty() {
        return reimbursableExpensesBuyerParty;
    }
    
	public void setReimbursableExpensesBuyerParty(TaxIdentificationType value) {
        this.reimbursableExpensesBuyerParty = value;
    }
    
	public XMLGregorianCalendar getIssueDate() {
        return issueDate;
    }
    
	public void setIssueDate(XMLGregorianCalendar value) {
        this.issueDate = value;
    }
    
	public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
	public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }
    
	public String getInvoiceSeriesCode() {
        return invoiceSeriesCode;
    }
    
	public void setInvoiceSeriesCode(String value) {
        this.invoiceSeriesCode = value;
    }
    
	public double getReimbursableExpensesAmount() {
        return reimbursableExpensesAmount;
    }
    
	public void setReimbursableExpensesAmount(double value) {
        this.reimbursableExpensesAmount = value;
    }

}