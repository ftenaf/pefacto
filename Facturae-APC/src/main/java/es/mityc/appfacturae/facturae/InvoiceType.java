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

import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;

public interface InvoiceType {
    
 	public InvoiceHeaderType getInvoiceHeader();
    
	public void setInvoiceHeader(InvoiceHeaderType value);
    
	public InvoiceIssueDataType getInvoiceIssueData();
    
	public void setInvoiceIssueData(InvoiceIssueDataType value);
    
	public TaxesType getTaxesWithheld();
    
	public void setTaxesWithheld(TaxesType value);
    
	public InvoiceTotalsType getInvoiceTotals();
    
	public void setInvoiceTotals(InvoiceTotalsType value);
    
	public ItemsType getItems();
    
	public void setItems(ItemsType value);
    
	public LegalLiteralsType getLegalLiterals();
    
	public void setLegalLiterals(LegalLiteralsType value);
    
	public AdditionalDataType getAdditionalData();
    
	public void setAdditionalData(AdditionalDataType value);
	
	public InstallmentsType getPaymentDetails();
	
	public void setPaymentDetails(InstallmentsType value);
	
	// Persistence
	
	public InvoiceStatusType getState();
	public int getInternalId();
	
	// Persistence
	
	public void setState(InvoiceStatusType state);
	public void setInternalId(int internalId);
  
}