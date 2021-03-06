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
package es.mityc.facturae321;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InvoiceHeader" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}InvoiceHeaderType"/>
 *         &lt;element name="InvoiceIssueData" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}InvoiceIssueDataType"/>
 *         &lt;element name="TaxesOutputs">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Tax" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TaxOutputType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TaxesWithheld" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TaxesType" minOccurs="0"/>
 *         &lt;element name="InvoiceTotals" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}InvoiceTotalsType"/>
 *         &lt;element name="Items" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}ItemsType"/>
 *         &lt;element name="PaymentDetails" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}InstallmentsType" minOccurs="0"/>
 *         &lt;element name="LegalLiterals" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}LegalLiteralsType" minOccurs="0"/>
 *         &lt;element name="AdditionalData" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}AdditionalDataType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceType", propOrder = {
    "invoiceHeader",
    "invoiceIssueData",
    "taxesOutputs",
    "taxesWithheld",
    "invoiceTotals",
    "items",
    "paymentDetails",
    "legalLiterals",
    "additionalData"
})
public class InvoiceType {

    @XmlElement(name = "InvoiceHeader", required = true)
    protected InvoiceHeaderType invoiceHeader;
    @XmlElement(name = "InvoiceIssueData", required = true)
    protected InvoiceIssueDataType invoiceIssueData;
    @XmlElement(name = "TaxesOutputs", required = true)
    protected InvoiceType.TaxesOutputs taxesOutputs;
    @XmlElement(name = "TaxesWithheld")
    protected TaxesType taxesWithheld;
    @XmlElement(name = "InvoiceTotals", required = true)
    protected InvoiceTotalsType invoiceTotals;
    @XmlElement(name = "Items", required = true)
    protected ItemsType items;
    @XmlElement(name = "PaymentDetails")
    protected InstallmentsType paymentDetails;
    @XmlElement(name = "LegalLiterals")
    protected LegalLiteralsType legalLiterals;
    @XmlElement(name = "AdditionalData")
    protected AdditionalDataType additionalData;

    /**
     * Gets the value of the invoiceHeader property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceHeaderType }
     *     
     */
    public InvoiceHeaderType getInvoiceHeader() {
        return invoiceHeader;
    }

    /**
     * Sets the value of the invoiceHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceHeaderType }
     *     
     */
    public void setInvoiceHeader(InvoiceHeaderType value) {
        this.invoiceHeader = value;
    }

    /**
     * Gets the value of the invoiceIssueData property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceIssueDataType }
     *     
     */
    public InvoiceIssueDataType getInvoiceIssueData() {
        return invoiceIssueData;
    }

    /**
     * Sets the value of the invoiceIssueData property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceIssueDataType }
     *     
     */
    public void setInvoiceIssueData(InvoiceIssueDataType value) {
        this.invoiceIssueData = value;
    }

    /**
     * Gets the value of the taxesOutputs property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceType.TaxesOutputs }
     *     
     */
    public InvoiceType.TaxesOutputs getTaxesOutputs() {
        return taxesOutputs;
    }

    /**
     * Sets the value of the taxesOutputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceType.TaxesOutputs }
     *     
     */
    public void setTaxesOutputs(InvoiceType.TaxesOutputs value) {
        this.taxesOutputs = value;
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
     * Gets the value of the invoiceTotals property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceTotalsType }
     *     
     */
    public InvoiceTotalsType getInvoiceTotals() {
        return invoiceTotals;
    }

    /**
     * Sets the value of the invoiceTotals property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceTotalsType }
     *     
     */
    public void setInvoiceTotals(InvoiceTotalsType value) {
        this.invoiceTotals = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link ItemsType }
     *     
     */
    public ItemsType getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemsType }
     *     
     */
    public void setItems(ItemsType value) {
        this.items = value;
    }

    /**
     * Gets the value of the paymentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link InstallmentsType }
     *     
     */
    public InstallmentsType getPaymentDetails() {
        return paymentDetails;
    }

    /**
     * Sets the value of the paymentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstallmentsType }
     *     
     */
    public void setPaymentDetails(InstallmentsType value) {
        this.paymentDetails = value;
    }

    /**
     * Gets the value of the legalLiterals property.
     * 
     * @return
     *     possible object is
     *     {@link LegalLiteralsType }
     *     
     */
    public LegalLiteralsType getLegalLiterals() {
        return legalLiterals;
    }

    /**
     * Sets the value of the legalLiterals property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegalLiteralsType }
     *     
     */
    public void setLegalLiterals(LegalLiteralsType value) {
        this.legalLiterals = value;
    }

    /**
     * Gets the value of the additionalData property.
     * 
     * @return
     *     possible object is
     *     {@link AdditionalDataType }
     *     
     */
    public AdditionalDataType getAdditionalData() {
        return additionalData;
    }

    /**
     * Sets the value of the additionalData property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalDataType }
     *     
     */
    public void setAdditionalData(AdditionalDataType value) {
        this.additionalData = value;
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
     *         &lt;element name="Tax" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TaxOutputType" maxOccurs="unbounded"/>
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
        protected List<TaxOutputType> tax;

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
         * {@link TaxOutputType }
         * 
         * 
         */
        public List<TaxOutputType> getTax() {
            if (tax == null) {
                tax = new ArrayList<TaxOutputType>();
            }
            return this.tax;
        }

    }

}
