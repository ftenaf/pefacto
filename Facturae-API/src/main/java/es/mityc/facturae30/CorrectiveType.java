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


/**
 * <p>Java class for CorrectiveType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorrectiveType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InvoiceNumber" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="InvoiceSeriesCode" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="ReasonCode" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ReasonCodeType"/>
 *         &lt;element name="ReasonDescription" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ReasonDescriptionType"/>
 *         &lt;element name="TaxPeriod" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}PeriodDates"/>
 *         &lt;element name="CorrectionMethod" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}CorrectionMethodType"/>
 *         &lt;element name="CorrectionMethodDescription" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}CorrectionMethodDescriptionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorrectiveType", propOrder = {
    "invoiceNumber",
    "invoiceSeriesCode",
    "reasonCode",
    "reasonDescription",
    "taxPeriod",
    "correctionMethod",
    "correctionMethodDescription"
})
public class CorrectiveType {

    @XmlElement(name = "InvoiceNumber")
    protected String invoiceNumber;
    @XmlElement(name = "InvoiceSeriesCode")
    protected String invoiceSeriesCode;
    @XmlElement(name = "ReasonCode", required = true)
    protected String reasonCode;
    @XmlElement(name = "ReasonDescription", required = true)
    protected ReasonDescriptionType reasonDescription;
    @XmlElement(name = "TaxPeriod", required = true)
    protected PeriodDates taxPeriod;
    @XmlElement(name = "CorrectionMethod", required = true)
    protected String correctionMethod;
    @XmlElement(name = "CorrectionMethodDescription", required = true)
    protected CorrectionMethodDescriptionType correctionMethodDescription;

    /**
     * Gets the value of the invoiceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * Sets the value of the invoiceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    /**
     * Gets the value of the invoiceSeriesCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceSeriesCode() {
        return invoiceSeriesCode;
    }

    /**
     * Sets the value of the invoiceSeriesCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceSeriesCode(String value) {
        this.invoiceSeriesCode = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCode(String value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the reasonDescription property.
     * 
     * @return
     *     possible object is
     *     {@link ReasonDescriptionType }
     *     
     */
    public ReasonDescriptionType getReasonDescription() {
        return reasonDescription;
    }

    /**
     * Sets the value of the reasonDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReasonDescriptionType }
     *     
     */
    public void setReasonDescription(ReasonDescriptionType value) {
        this.reasonDescription = value;
    }

    /**
     * Gets the value of the taxPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link PeriodDates }
     *     
     */
    public PeriodDates getTaxPeriod() {
        return taxPeriod;
    }

    /**
     * Sets the value of the taxPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodDates }
     *     
     */
    public void setTaxPeriod(PeriodDates value) {
        this.taxPeriod = value;
    }

    /**
     * Gets the value of the correctionMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrectionMethod() {
        return correctionMethod;
    }

    /**
     * Sets the value of the correctionMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrectionMethod(String value) {
        this.correctionMethod = value;
    }

    /**
     * Gets the value of the correctionMethodDescription property.
     * 
     * @return
     *     possible object is
     *     {@link CorrectionMethodDescriptionType }
     *     
     */
    public CorrectionMethodDescriptionType getCorrectionMethodDescription() {
        return correctionMethodDescription;
    }

    /**
     * Sets the value of the correctionMethodDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectionMethodDescriptionType }
     *     
     */
    public void setCorrectionMethodDescription(CorrectionMethodDescriptionType value) {
        this.correctionMethodDescription = value;
    }

}
