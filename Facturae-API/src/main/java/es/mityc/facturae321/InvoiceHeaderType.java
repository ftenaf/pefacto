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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InvoiceNumber" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax20Type"/>
 *         &lt;element name="InvoiceSeriesCode" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="InvoiceDocumentType" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}InvoiceDocumentTypeType"/>
 *         &lt;element name="InvoiceClass" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}InvoiceClassType"/>
 *         &lt;element name="Corrective" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}CorrectiveType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceHeaderType", propOrder = {
    "invoiceNumber",
    "invoiceSeriesCode",
    "invoiceDocumentType",
    "invoiceClass",
    "corrective"
})
public class InvoiceHeaderType {

    @XmlElement(name = "InvoiceNumber", required = true)
    protected String invoiceNumber;
    @XmlElement(name = "InvoiceSeriesCode")
    protected String invoiceSeriesCode;
    @XmlElement(name = "InvoiceDocumentType", required = true)
    protected InvoiceDocumentTypeType invoiceDocumentType;
    @XmlElement(name = "InvoiceClass", required = true)
    protected InvoiceClassType invoiceClass;
    @XmlElement(name = "Corrective")
    protected CorrectiveType corrective;

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
     * Gets the value of the invoiceDocumentType property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDocumentTypeType }
     *     
     */
    public InvoiceDocumentTypeType getInvoiceDocumentType() {
        return invoiceDocumentType;
    }

    /**
     * Sets the value of the invoiceDocumentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDocumentTypeType }
     *     
     */
    public void setInvoiceDocumentType(InvoiceDocumentTypeType value) {
        this.invoiceDocumentType = value;
    }

    /**
     * Gets the value of the invoiceClass property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceClassType }
     *     
     */
    public InvoiceClassType getInvoiceClass() {
        return invoiceClass;
    }

    /**
     * Sets the value of the invoiceClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceClassType }
     *     
     */
    public void setInvoiceClass(InvoiceClassType value) {
        this.invoiceClass = value;
    }

    /**
     * Gets the value of the corrective property.
     * 
     * @return
     *     possible object is
     *     {@link CorrectiveType }
     *     
     */
    public CorrectiveType getCorrective() {
        return corrective;
    }

    /**
     * Sets the value of the corrective property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrectiveType }
     *     
     */
    public void setCorrective(CorrectiveType value) {
        this.corrective = value;
    }

}
