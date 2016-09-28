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
 
import java.util.ArrayList;
import java.util.List;

import es.mityc.appfacturae.facturae.InvoiceHeaderType;
import es.mityc.appfacturae.facturae.ItemsType;
import es.mityc.appfacturae.facturae.LegalLiteralsType;
import es.mityc.appfacturae.facturae.TaxesType;
import es.mityc.appfacturae.facturae32.AdditionalDataType;
import es.mityc.appfacturae.facturae32.InstallmentsType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;


public class InvoiceType implements es.mityc.appfacturae.facturae.InvoiceType {

	protected InvoiceHeaderType invoiceHeader;
    protected InvoiceIssueDataType invoiceIssueData;
    protected InvoiceType.TaxesOutputs taxesOutputs;
    protected TaxesType taxesWithheld;
    protected InvoiceTotalsType invoiceTotals;
    protected ItemsType items;
    protected InstallmentsType paymentDetails;
    protected LegalLiteralsType legalLiterals;
    protected AdditionalDataType additionalData;
   
    // Persistence
    protected InvoiceStatusType state;
    protected int internalId;
    
    // Persistence
    
	public InvoiceStatusType getState() {
		return state;
	}
	
	public int getInternalId(){
	   	return internalId;
	}
	
	// Persistence
	
	public void setState(InvoiceStatusType state) {
		this.state = state;
	}
	
	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}

	public InvoiceHeaderType getInvoiceHeader() {
        return invoiceHeader;
    }
    
	public void setInvoiceHeader(InvoiceHeaderType value) {
        this.invoiceHeader = value;
    }
    
	public InvoiceIssueDataType getInvoiceIssueData() {
        return invoiceIssueData;
    }
    
	public void setInvoiceIssueData(es.mityc.appfacturae.facturae.InvoiceIssueDataType value) {
        this.invoiceIssueData = (InvoiceIssueDataType) value;
    }
    
	public InvoiceType.TaxesOutputs getTaxesOutputs() {
        return taxesOutputs;
    }
    
	public void setTaxesOutputs(InvoiceType.TaxesOutputs value) {
        this.taxesOutputs = value;
    }
    
	public TaxesType getTaxesWithheld() {
        return taxesWithheld;
    }
    
	public void setTaxesWithheld(TaxesType value) {
        this.taxesWithheld = value;
    }
    
	public InvoiceTotalsType getInvoiceTotals() {
        return invoiceTotals;
    }
    
	public void setInvoiceTotals(es.mityc.appfacturae.facturae.InvoiceTotalsType value) {
        this.invoiceTotals = (InvoiceTotalsType) value;
    }
    
	public ItemsType getItems() {
        return items;
    }
    
	public void setItems(ItemsType value) {
        this.items = value;
    }
    
	public InstallmentsType getPaymentDetails() {
        return paymentDetails;
    }
    
	public void setPaymentDetails(InstallmentsType value) {
        this.paymentDetails = value;
    }
    
	public LegalLiteralsType getLegalLiterals() {
        return legalLiterals;
    }
    
	public void setLegalLiterals(LegalLiteralsType value) {
        this.legalLiterals = value;
    }
    
	public AdditionalDataType getAdditionalData() {
        return additionalData;
    }
    
	public void setAdditionalData(es.mityc.appfacturae.facturae.AdditionalDataType value) {
        this.additionalData = (AdditionalDataType) value;
    }
    
	public static class TaxesOutputs {

        protected List<TaxOutputType> tax;
       
        public List<TaxOutputType> getTax() {
            if (tax == null) {
                tax = new ArrayList<TaxOutputType>();
            }
            return this.tax;
        }
    }

	public void setPaymentDetails(es.mityc.appfacturae.facturae.InstallmentsType value) {
		this.paymentDetails = (InstallmentsType) paymentDetails;
		
	}
}