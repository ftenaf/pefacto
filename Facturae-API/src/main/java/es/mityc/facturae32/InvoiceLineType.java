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
package es.mityc.facturae32;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
 *         &lt;element name="IssuerContractReference" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="IssuerContractDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="IssuerTransactionReference" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="IssuerTransactionDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ReceiverContractReference" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="ReceiverContractDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ReceiverTransactionReference" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="ReceiverTransactionDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="FileReference" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="FileDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="DeliveryNotesReferences" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DeliveryNotesReferencesType" minOccurs="0"/>
 *         &lt;element name="ItemDescription" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax2500Type"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="UnitOfMeasure" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}UnitOfMeasureType" minOccurs="0"/>
 *         &lt;element name="UnitPriceWithoutTax" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleSixDecimalType"/>
 *         &lt;element name="TotalCost" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleSixDecimalType"/>
 *         &lt;element name="DiscountsAndRebates" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DiscountsAndRebatesType" minOccurs="0"/>
 *         &lt;element name="Charges" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}ChargesType" minOccurs="0"/>
 *         &lt;element name="GrossAmount" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleSixDecimalType"/>
 *         &lt;element name="TaxesWithheld" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TaxesType" minOccurs="0"/>
 *         &lt;element name="TaxesOutputs">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Tax" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TaxOutputType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="LineItemPeriod" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}PeriodDates" minOccurs="0"/>
 *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="AdditionalLineItemInformation" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax2500Type" minOccurs="0"/>
 *         &lt;element name="SpecialTaxableEvent" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}SpecialTaxableEventType" minOccurs="0"/>
 *         &lt;element name="ArticleCode" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="Extensions" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}ExtensionsType" minOccurs="0"/>
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
    "issuerContractDate",
    "issuerTransactionReference",
    "issuerTransactionDate",
    "receiverContractReference",
    "receiverContractDate",
    "receiverTransactionReference",
    "receiverTransactionDate",
    "fileReference",
    "fileDate",
    "sequenceNumber",
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
    "specialTaxableEvent",
    "articleCode",
    "extensions"
})
public class InvoiceLineType {

    @XmlElement(name = "IssuerContractReference")
    protected String issuerContractReference;
    @XmlElement(name = "IssuerContractDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar issuerContractDate;
    @XmlElement(name = "IssuerTransactionReference")
    protected String issuerTransactionReference;
    @XmlElement(name = "IssuerTransactionDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar issuerTransactionDate;
    @XmlElement(name = "ReceiverContractReference")
    protected String receiverContractReference;
    @XmlElement(name = "ReceiverContractDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar receiverContractDate;
    @XmlElement(name = "ReceiverTransactionReference")
    protected String receiverTransactionReference;
    @XmlElement(name = "ReceiverTransactionDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar receiverTransactionDate;
    @XmlElement(name = "FileReference")
    protected String fileReference;
    @XmlElement(name = "FileDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fileDate;
    @XmlElement(name = "SequenceNumber")
    protected Double sequenceNumber;
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
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd6.class)
    protected double totalCost;
    @XmlElement(name = "DiscountsAndRebates")
    protected DiscountsAndRebatesType discountsAndRebates;
    @XmlElement(name = "Charges")
    protected ChargesType charges;
    @XmlElement(name = "GrossAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd6.class)
    protected double grossAmount;
    @XmlElement(name = "TaxesWithheld")
    protected TaxesType taxesWithheld;
    @XmlElement(name = "TaxesOutputs", required = true)
    protected InvoiceLineType.TaxesOutputs taxesOutputs;
    @XmlElement(name = "LineItemPeriod")
    protected PeriodDates lineItemPeriod;
    @XmlElement(name = "TransactionDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar transactionDate;
    @XmlElement(name = "AdditionalLineItemInformation")
    protected String additionalLineItemInformation;
    @XmlElement(name = "SpecialTaxableEvent")
    protected SpecialTaxableEventType specialTaxableEvent;
    @XmlElement(name = "ArticleCode")
    protected String articleCode;
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
     * Gets the value of the issuerContractDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssuerContractDate() {
        return issuerContractDate;
    }

    /**
     * Sets the value of the issuerContractDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssuerContractDate(XMLGregorianCalendar value) {
        this.issuerContractDate = value;
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
     * Gets the value of the issuerTransactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIssuerTransactionDate() {
        return issuerTransactionDate;
    }

    /**
     * Sets the value of the issuerTransactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssuerTransactionDate(XMLGregorianCalendar value) {
        this.issuerTransactionDate = value;
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
     * Gets the value of the receiverContractDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceiverContractDate() {
        return receiverContractDate;
    }

    /**
     * Sets the value of the receiverContractDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceiverContractDate(XMLGregorianCalendar value) {
        this.receiverContractDate = value;
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
     * Gets the value of the receiverTransactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReceiverTransactionDate() {
        return receiverTransactionDate;
    }

    /**
     * Sets the value of the receiverTransactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReceiverTransactionDate(XMLGregorianCalendar value) {
        this.receiverTransactionDate = value;
    }

    /**
     * Gets the value of the fileReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileReference() {
        return fileReference;
    }

    /**
     * Sets the value of the fileReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileReference(String value) {
        this.fileReference = value;
    }

    /**
     * Gets the value of the fileDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileDate() {
        return fileDate;
    }

    /**
     * Sets the value of the fileDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileDate(XMLGregorianCalendar value) {
        this.fileDate = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSequenceNumber(Double value) {
        this.sequenceNumber = value;
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
     *     {@link InvoiceLineType.TaxesOutputs }
     *     
     */
    public InvoiceLineType.TaxesOutputs getTaxesOutputs() {
        return taxesOutputs;
    }

    /**
     * Sets the value of the taxesOutputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceLineType.TaxesOutputs }
     *     
     */
    public void setTaxesOutputs(InvoiceLineType.TaxesOutputs value) {
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
     * Gets the value of the specialTaxableEvent property.
     * 
     * @return
     *     possible object is
     *     {@link SpecialTaxableEventType }
     *     
     */
    public SpecialTaxableEventType getSpecialTaxableEvent() {
        return specialTaxableEvent;
    }

    /**
     * Sets the value of the specialTaxableEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecialTaxableEventType }
     *     
     */
    public void setSpecialTaxableEvent(SpecialTaxableEventType value) {
        this.specialTaxableEvent = value;
    }

    /**
     * Gets the value of the articleCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticleCode() {
        return articleCode;
    }

    /**
     * Sets the value of the articleCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticleCode(String value) {
        this.articleCode = value;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Tax" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TaxOutputType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "tax"
    })
    public static class TaxesOutputs {

        @XmlElement(name = "Tax", required = true)
        protected List<InvoiceLineType.TaxesOutputs.Tax> tax;

        /**
         * Gets the value of the tax property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the tax property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTax().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InvoiceLineType.TaxesOutputs.Tax }
         * 
         * 
         */
        public List<InvoiceLineType.TaxesOutputs.Tax> getTax() {
            if (tax == null) {
                tax = new ArrayList<InvoiceLineType.TaxesOutputs.Tax>();
            }
            return this.tax;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TaxOutputType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Tax
            extends TaxOutputType
        {


        }

    }

}
