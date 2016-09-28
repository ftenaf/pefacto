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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BatchIdentifier" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax70Type"/>
 *         &lt;element name="InvoicesCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="TotalInvoicesAmount" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AmountType"/>
 *         &lt;element name="TotalOutstandingAmount" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AmountType"/>
 *         &lt;element name="TotalExecutableAmount" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AmountType"/>
 *         &lt;element name="InvoiceCurrencyCode" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}CurrencyCodeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchType", propOrder = {
    "batchIdentifier",
    "invoicesCount",
    "totalInvoicesAmount",
    "totalOutstandingAmount",
    "totalExecutableAmount",
    "invoiceCurrencyCode"
})
public class BatchType {

    @XmlElement(name = "BatchIdentifier", required = true)
    protected String batchIdentifier;
    @XmlElement(name = "InvoicesCount")
    protected long invoicesCount;
    @XmlElement(name = "TotalInvoicesAmount", required = true)
    protected AmountType totalInvoicesAmount;
    @XmlElement(name = "TotalOutstandingAmount", required = true)
    protected AmountType totalOutstandingAmount;
    @XmlElement(name = "TotalExecutableAmount", required = true)
    protected AmountType totalExecutableAmount;
    @XmlElement(name = "InvoiceCurrencyCode", required = true)
    protected CurrencyCodeType invoiceCurrencyCode;

    /**
     * Gets the value of the batchIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchIdentifier() {
        return batchIdentifier;
    }

    /**
     * Sets the value of the batchIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchIdentifier(String value) {
        this.batchIdentifier = value;
    }

    /**
     * Gets the value of the invoicesCount property.
     * 
     */
    public long getInvoicesCount() {
        return invoicesCount;
    }

    /**
     * Sets the value of the invoicesCount property.
     * 
     */
    public void setInvoicesCount(long value) {
        this.invoicesCount = value;
    }

    /**
     * Gets the value of the totalInvoicesAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTotalInvoicesAmount() {
        return totalInvoicesAmount;
    }

    /**
     * Sets the value of the totalInvoicesAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTotalInvoicesAmount(AmountType value) {
        this.totalInvoicesAmount = value;
    }

    /**
     * Gets the value of the totalOutstandingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    /**
     * Sets the value of the totalOutstandingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTotalOutstandingAmount(AmountType value) {
        this.totalOutstandingAmount = value;
    }

    /**
     * Gets the value of the totalExecutableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTotalExecutableAmount() {
        return totalExecutableAmount;
    }

    /**
     * Sets the value of the totalExecutableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTotalExecutableAmount(AmountType value) {
        this.totalExecutableAmount = value;
    }

    /**
     * Gets the value of the invoiceCurrencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyCodeType }
     *     
     */
    public CurrencyCodeType getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }

    /**
     * Sets the value of the invoiceCurrencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyCodeType }
     *     
     */
    public void setInvoiceCurrencyCode(CurrencyCodeType value) {
        this.invoiceCurrencyCode = value;
    }

}
