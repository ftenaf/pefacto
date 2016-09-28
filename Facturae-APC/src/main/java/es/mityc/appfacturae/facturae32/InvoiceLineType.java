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
package es.mityc.appfacturae.facturae32;
 
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import es.mityc.appfacturae.facturae.ChargesType;
import es.mityc.appfacturae.facturae.DeliveryNotesReferencesType;
import es.mityc.appfacturae.facturae.DiscountsAndRebatesType;
import es.mityc.appfacturae.facturae.ExtensionsType;
import es.mityc.appfacturae.facturae.PeriodDates;
import es.mityc.appfacturae.facturae.TaxesType;
import es.mityc.appfacturae.facturae32.TaxOutputType;
import es.mityc.appfacturae.hibernate.auxClass.Tax;

public class InvoiceLineType implements es.mityc.appfacturae.facturae.InvoiceLineType{

	protected String issuerContractReference;
    protected String issuerTransactionReference;
    protected String receiverContractReference;
    protected String receiverTransactionReference;
    protected DeliveryNotesReferencesType deliveryNotesReferences;
    protected String itemDescription;
    protected double quantity;
    protected String unitOfMeasure;
    protected double unitPriceWithoutTax;
    protected double totalCost;
    protected DiscountsAndRebatesType discountsAndRebates;
    protected ChargesType charges;
    protected double grossAmount;
    protected TaxesType taxesWithheld;
    protected InvoiceLineType.TaxesOutputs taxesOutputs;
    protected PeriodDates lineItemPeriod;
    protected XMLGregorianCalendar transactionDate;
    protected String additionalLineItemInformation;
    protected ExtensionsType extensions;
    protected XMLGregorianCalendar issuerContractDate;
    protected XMLGregorianCalendar issuerTransactionDate;
    protected XMLGregorianCalendar receiverContractDate;
    protected XMLGregorianCalendar receiverTransactionDate;
    protected String fileReference;
    protected XMLGregorianCalendar fileDate;
    protected Double sequenceNumber;
    protected SpecialTaxableEventType specialTaxableEvent;
    protected String articleCode;
    
    // Persistence
    protected int id;
    protected List<Tax> taxesPersistence;
    
    // Persistence
    
	public int getId() {
		return id;
	}

	// Persistence
	
	public void setId(int id) {
		this.id = id;
	}
	
	// Persistence
	
	public List<Tax> getTaxesPersistence() {
        if (taxesPersistence == null) {
        	taxesPersistence = new ArrayList<Tax>();
        }
        return this.taxesPersistence;
    }
	
	// Persistence
	
	public void setTaxesPersistence(List<Tax> l) {
        taxesPersistence = l;
    }
	
	public String getIssuerContractReference() {
        return issuerContractReference;
    }
    
	public void setIssuerContractReference(String value) {
        this.issuerContractReference = value;
    }
    
	public String getIssuerTransactionReference() {
        return issuerTransactionReference;
    }
    
	public void setIssuerTransactionReference(String value) {
        this.issuerTransactionReference = value;
    }
    
	public String getReceiverContractReference() {
        return receiverContractReference;
    }
    
	public void setReceiverContractReference(String value) {
        this.receiverContractReference = value;
    }
    
	public String getReceiverTransactionReference() {
        return receiverTransactionReference;
    }
    
	public void setReceiverTransactionReference(String value) {
        this.receiverTransactionReference = value;
    }
    
	public DeliveryNotesReferencesType getDeliveryNotesReferences() {
        return deliveryNotesReferences;
    }
    
	public void setDeliveryNotesReferences(DeliveryNotesReferencesType value) {
        this.deliveryNotesReferences = value;
    }
    
	public String getItemDescription() {
        return itemDescription;
    }
    
	public void setItemDescription(String value) {
        this.itemDescription = value;
    }
    
	public double getQuantity() {
        return quantity;
    }
    
	public void setQuantity(double value) {
        this.quantity = value;
    }
    
	public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
    
	public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }
    
	public double getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }
    
	public void setUnitPriceWithoutTax(double value) {
        this.unitPriceWithoutTax = value;
    }
    
	public double getTotalCost() {
        return totalCost;
    }
    
	public void setTotalCost(double value) {
        this.totalCost = value;
    }
    
	public DiscountsAndRebatesType getDiscountsAndRebates() {
        return discountsAndRebates;
    }
    
	public void setDiscountsAndRebates(DiscountsAndRebatesType value) {
        this.discountsAndRebates = value;
    }
    
	public ChargesType getCharges() {
		return charges;
    }
    
	public void setCharges(ChargesType value) {
        this.charges = value;
    }
    
	public double getGrossAmount() {
        return grossAmount;
    }
    
	public void setGrossAmount(double value) {
        this.grossAmount = value;
    }
    
	public TaxesType getTaxesWithheld() {
        return taxesWithheld;
    }
    
	public void setTaxesWithheld(TaxesType value) {
        this.taxesWithheld = value;
    }
    
	public InvoiceLineType.TaxesOutputs getTaxesOutputs() {
        return taxesOutputs;
    }
    
	public void setTaxesOutputs(InvoiceLineType.TaxesOutputs value) {
        this.taxesOutputs = value;
        if (taxesOutputs.getTax() != null){
	        int size = taxesOutputs.getTax().size();
	        Tax t = null;
	        for(int i = 0 ; i < size ; i++){
	        	t = new Tax();
	        	t.setTaxRate(taxesOutputs.getTax().get(i).getTaxRate());
	        	t.setTaxTypeCode(taxesOutputs.getTax().get(i).getTaxTypeCode());
	        	t.setEquivalenceSurcharge(taxesOutputs.getTax().get(i).getEquivalenceSurcharge());
	        	getTaxesPersistence().add(t);
	        }
        }
    }
    
	public void reloadTaxesPersistence(){
		setTaxesOutputs(taxesOutputs);
	}
	
	public PeriodDates getLineItemPeriod() {
        return lineItemPeriod;
    }
    
	public void setLineItemPeriod(PeriodDates value) {
        this.lineItemPeriod = value;
    }
    
	public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }
    
	public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }
    
	public String getAdditionalLineItemInformation() {
        return additionalLineItemInformation;
    }
    
	public void setAdditionalLineItemInformation(String value) {
        this.additionalLineItemInformation = value;
    }
    
	public ExtensionsType getExtensions() {
        return extensions;
    }
    
	public void setExtensions(es.mityc.appfacturae.facturae.ExtensionsType value) {
        this.extensions = (ExtensionsType) value;
    }
	
	public static class TaxesOutputs {

        protected List<InvoiceLineType.TaxesOutputs.Tax> tax;

        public List<InvoiceLineType.TaxesOutputs.Tax> getTax() {
            if (tax == null) {
                tax = new ArrayList<InvoiceLineType.TaxesOutputs.Tax>();
            }
            return this.tax;
        }
        
        public static class Tax
            extends TaxOutputType
        {

        }

    }

	public XMLGregorianCalendar getIssuerContractDate() {
		return issuerContractDate;
	}

	public void setIssuerContractDate(XMLGregorianCalendar issuerContractDate) {
		this.issuerContractDate = issuerContractDate;
	}

	public XMLGregorianCalendar getIssuerTransactionDate() {
		return issuerTransactionDate;
	}

	public void setIssuerTransactionDate(XMLGregorianCalendar issuerTransactionDate) {
		this.issuerTransactionDate = issuerTransactionDate;
	}

	public XMLGregorianCalendar getReceiverContractDate() {
		return receiverContractDate;
	}

	public void setReceiverContractDate(XMLGregorianCalendar receiverContractDate) {
		this.receiverContractDate = receiverContractDate;
	}

	public XMLGregorianCalendar getReceiverTransactionDate() {
		return receiverTransactionDate;
	}

	public void setReceiverTransactionDate(
			XMLGregorianCalendar receiverTransactionDate) {
		this.receiverTransactionDate = receiverTransactionDate;
	}

	public String getFileReference() {
		return fileReference;
	}

	public void setFileReference(String fileReference) {
		this.fileReference = fileReference;
	}

	public XMLGregorianCalendar getFileDate() {
		return fileDate;
	}

	public void setFileDate(XMLGregorianCalendar fileDate) {
		this.fileDate = fileDate;
	}

	public Double getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Double sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public SpecialTaxableEventType getSpecialTaxableEvent() {
		return specialTaxableEvent;
	}

	public void setSpecialTaxableEvent(SpecialTaxableEventType specialTaxableEvent) {
		this.specialTaxableEvent = specialTaxableEvent;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	
}