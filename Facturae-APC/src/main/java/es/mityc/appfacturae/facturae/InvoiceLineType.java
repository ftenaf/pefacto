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

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import es.mityc.appfacturae.hibernate.auxClass.Tax;

public interface InvoiceLineType {
    
	// Persistence
	
	public int getId();
	
	// Persistence
	
	public void setId(int value);

	// Persistence
	
	public List<Tax> getTaxesPersistence();
	
	// Persistence
	
	public void setTaxesPersistence(List<Tax> l);
	
	public String getIssuerContractReference();
    
	public void setIssuerContractReference(String value);
    
	public String getIssuerTransactionReference();
    
	public void setIssuerTransactionReference(String value);
    
	public String getReceiverContractReference();
    
	public void setReceiverContractReference(String value);
    
	public String getReceiverTransactionReference();
    
	public void setReceiverTransactionReference(String value);
    
	public DeliveryNotesReferencesType getDeliveryNotesReferences();
    
	public void setDeliveryNotesReferences(DeliveryNotesReferencesType value);
    
	public String getItemDescription();
    
	public void setItemDescription(String value);
    
	public double getQuantity();
    
	public void setQuantity(double value);
    
	public String getUnitOfMeasure();
    
	public void setUnitOfMeasure(String value);
    
	public double getUnitPriceWithoutTax();
    
	public void setUnitPriceWithoutTax(double value);
    
	public double getTotalCost();
    
	public void setTotalCost(double value);
    
	public DiscountsAndRebatesType getDiscountsAndRebates();
    
	public void setDiscountsAndRebates(DiscountsAndRebatesType value);
    
	public ChargesType getCharges();
    
	public void setCharges(ChargesType value);
    
	public double getGrossAmount();
    
	public void setGrossAmount(double value);
    
	public TaxesType getTaxesWithheld();
    
	public void setTaxesWithheld(TaxesType value);
     
	public PeriodDates getLineItemPeriod();
    
	public void setLineItemPeriod(PeriodDates value);
    
	public XMLGregorianCalendar getTransactionDate();
    
	public void setTransactionDate(XMLGregorianCalendar value);
    
	public String getAdditionalLineItemInformation();
    
	public void setAdditionalLineItemInformation(String value);
	
	public ExtensionsType getExtensions();
    
	public void setExtensions(ExtensionsType value);

}