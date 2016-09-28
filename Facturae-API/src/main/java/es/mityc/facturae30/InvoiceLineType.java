/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

import es.mityc.facturae.utils.adapters.DoubleAdapterd2;
import es.mityc.facturae.utils.adapters.DoubleAdapterd6;


/**
 * <p>Java class for InvoiceLineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceLineType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IssuerContractReference" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="IssuerTransactionReference" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="ReceiverContractReference" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="ReceiverTransactionReference" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="PurchaseOrderReference" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="DeliveryNotesReferences" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DeliveryNotesReferencesType" minOccurs="0"/>
 *         &lt;element name="ItemDescription" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax2500Type"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UnitOfMeasure" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}UnitOfMeasureType" minOccurs="0"/>
 *         &lt;element name="UnitPriceWithoutTax" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleSixDecimalType"/>
 *         &lt;element name="TotalCost" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="DiscountsAndRebates" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DiscountsAndRebatesType" minOccurs="0"/>
 *         &lt;element name="Charges" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ChargesType" minOccurs="0"/>
 *         &lt;element name="GrossAmount" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="TaxesWithheld" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TaxesType" minOccurs="0"/>
 *         &lt;element name="TaxesOutputs" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TaxesType"/>
 *         &lt;element name="LineItemPeriod" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}PeriodDates" minOccurs="0"/>
 *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="AdditionalLineItemInformation" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax2500Type" minOccurs="0"/>
 *         &lt;element name="Extensions" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ExtensionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceLineType", propOrder = {
    "issuerContractReference",
    "issuerTransactionReference",
    "receiverContractReference",
    "receiverTransactionReference",
    "purchaseOrderReference",
    "deliveryNotesReferences",
    "itemDescription",
    "quantity",
    "unitOfMeasure",
    "unitPriceWithoutTax",
    "totalCost",
    "discountsAndRebates",
    "charges",
    "grossAmount",
    "taxesWithheld",
    "taxesOutputs",
    "lineItemPeriod",
    "transactionDate",
    "additionalLineItemInformation",
    "extensions"
})
public class InvoiceLineType {

    @XmlElement(name = "IssuerContractReference")
    protected String issuerContractReference;
    @XmlElement(name = "IssuerTransactionReference")
    protected String issuerTransactionReference;
    @XmlElement(name = "ReceiverContractReference")
    protected String receiverContractReference;
    @XmlElement(name = "ReceiverTransactionReference")
    protected String receiverTransactionReference;
    @XmlElement(name = "PurchaseOrderReference")
    @XmlJavaTypeAdapter(DoubleAdapterd2.class)
    protected Double purchaseOrderReference;
    @XmlElement(name = "DeliveryNotesReferences")
    protected DeliveryNotesReferencesType deliveryNotesReferences;
    @XmlElement(name = "ItemDescription", required = true)
    protected String itemDescription;
    @XmlElement(name = "Quantity")
    protected double quantity;
    @XmlElement(name = "UnitOfMeasure")
    protected String unitOfMeasure;
    @XmlElement(name = "UnitPriceWithoutTax")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd6.class)
    protected double unitPriceWithoutTax;
    @XmlElement(name = "TotalCost")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double totalCost;
    @XmlElement(name = "DiscountsAndRebates")
    protected DiscountsAndRebatesType discountsAndRebates;
    @XmlElement(name = "Charges")
    protected ChargesType charges;
    @XmlElement(name = "GrossAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double grossAmount;
    @XmlElement(name = "TaxesWithheld")
    protected TaxesType taxesWithheld;
    @XmlElement(name = "TaxesOutputs", required = true)
    protected TaxesType taxesOutputs;
    @XmlElement(name = "LineItemPeriod")
    protected PeriodDates lineItemPeriod;
    @XmlElement(name = "TransactionDate")
    protected XMLGregorianCalendar transactionDate;
    @XmlElement(name = "AdditionalLineItemInformation")
    protected String additionalLineItemInformation;
    @XmlElement(name = "Extensions")
    protected ExtensionsType extensions;

    /**
     * Gets the value of the issuerContractReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuerContractReference() {
        return issuerContractReference;
    }

    /**
     * Sets the value of the issuerContractReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuerContractReference(String value) {
        this.issuerContractReference = value;
    }

    /**
     * Gets the value of the issuerTransactionReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuerTransactionReference() {
        return issuerTransactionReference;
    }

    /**
     * Sets the value of the issuerTransactionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuerTransactionReference(String value) {
        this.issuerTransactionReference = value;
    }

    /**
     * Gets the value of the receiverContractReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverContractReference() {
        return receiverContractReference;
    }

    /**
     * Sets the value of the receiverContractReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverContractReference(String value) {
        this.receiverContractReference = value;
    }

    /**
     * Gets the value of the receiverTransactionReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverTransactionReference() {
        return receiverTransactionReference;
    }

    /**
     * Sets the value of the receiverTransactionReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverTransactionReference(String value) {
        this.receiverTransactionReference = value;
    }

    /**
     * Gets the value of the purchaseOrderReference property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPurchaseOrderReference() {
        return purchaseOrderReference;
    }

    /**
     * Sets the value of the purchaseOrderReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPurchaseOrderReference(Double value) {
        this.purchaseOrderReference = value;
    }

    /**
     * Gets the value of the deliveryNotesReferences property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryNotesReferencesType }
     *     
     */
    public DeliveryNotesReferencesType getDeliveryNotesReferences() {
        return deliveryNotesReferences;
    }

    /**
     * Sets the value of the deliveryNotesReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryNotesReferencesType }
     *     
     */
    public void setDeliveryNotesReferences(DeliveryNotesReferencesType value) {
        this.deliveryNotesReferences = value;
    }

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     */
    public void setQuantity(double value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the unitPriceWithoutTax property.
     * 
     */
    public double getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }

    /**
     * Sets the value of the unitPriceWithoutTax property.
     * 
     */
    public void setUnitPriceWithoutTax(double value) {
        this.unitPriceWithoutTax = value;
    }

    /**
     * Gets the value of the totalCost property.
     * 
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     */
    public void setTotalCost(double value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the discountsAndRebates property.
     * 
     * @return
     *     possible object is
     *     {@link DiscountsAndRebatesType }
     *     
     */
    public DiscountsAndRebatesType getDiscountsAndRebates() {
        return discountsAndRebates;
    }

    /**
     * Sets the value of the discountsAndRebates property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscountsAndRebatesType }
     *     
     */
    public void setDiscountsAndRebates(DiscountsAndRebatesType value) {
        this.discountsAndRebates = value;
    }

    /**
     * Gets the value of the charges property.
     * 
     * @return
     *     possible object is
     *     {@link ChargesType }
     *     
     */
    public ChargesType getCharges() {
        return charges;
    }

    /**
     * Sets the value of the charges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargesType }
     *     
     */
    public void setCharges(ChargesType value) {
        this.charges = value;
    }

    /**
     * Gets the value of the grossAmount property.
     * 
     */
    public double getGrossAmount() {
        return grossAmount;
    }

    /**
     * Sets the value of the grossAmount property.
     * 
     */
    public void setGrossAmount(double value) {
        this.grossAmount = value;
    }

    /**
     * Gets the value of the taxesWithheld property.
     * 
     * @return
     *     possible object is
     *     {@link TaxesType }
     *     
     */
    public TaxesType getTaxesWithheld() {
        return taxesWithheld;
    }

    /**
     * Sets the value of the taxesWithheld property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxesType }
     *     
     */
    public void setTaxesWithheld(TaxesType value) {
        this.taxesWithheld = value;
    }

    /**
     * Gets the value of the taxesOutputs property.
     * 
     * @return
     *     possible object is
     *     {@link TaxesType }
     *     
     */
    public TaxesType getTaxesOutputs() {
        return taxesOutputs;
    }

    /**
     * Sets the value of the taxesOutputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxesType }
     *     
     */
    public void setTaxesOutputs(TaxesType value) {
        this.taxesOutputs = value;
    }

    /**
     * Gets the value of the lineItemPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link PeriodDates }
     *     
     */
    public PeriodDates getLineItemPeriod() {
        return lineItemPeriod;
    }

    /**
     * Sets the value of the lineItemPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodDates }
     *     
     */
    public void setLineItemPeriod(PeriodDates value) {
        this.lineItemPeriod = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the additionalLineItemInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalLineItemInformation() {
        return additionalLineItemInformation;
    }

    /**
     * Sets the value of the additionalLineItemInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalLineItemInformation(String value) {
        this.additionalLineItemInformation = value;
    }

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionsType }
     *     
     */
    public ExtensionsType getExtensions() {
        return extensions;
    }

    /**
     * Sets the value of the extensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionsType }
     *     
     */
    public void setExtensions(ExtensionsType value) {
        this.extensions = value;
    }

}
