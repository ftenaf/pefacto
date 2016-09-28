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
package es.mityc.facturae31;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttachmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AttachmentCompressionAlgorithm" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AttachmentCompressionAlgorithmType" minOccurs="0"/>
 *         &lt;element name="AttachmentFormat" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AttachmentFormatType"/>
 *         &lt;element name="AttachmentEncoding" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AttachmentEncodingType" minOccurs="0"/>
 *         &lt;element name="AttachmentDescription" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax2500Type" minOccurs="0"/>
 *         &lt;element name="AttachmentData" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttachmentType", propOrder = {
    "attachmentCompressionAlgorithm",
    "attachmentFormat",
    "attachmentEncoding",
    "attachmentDescription",
    "attachmentData"
})
public class AttachmentType {

    @XmlElement(name = "AttachmentCompressionAlgorithm")
    protected AttachmentCompressionAlgorithmType attachmentCompressionAlgorithm;
    @XmlElement(name = "AttachmentFormat", required = true)
    protected AttachmentFormatType attachmentFormat;
    @XmlElement(name = "AttachmentEncoding")
    protected AttachmentEncodingType attachmentEncoding;
    @XmlElement(name = "AttachmentDescription")
    protected String attachmentDescription;
    @XmlElement(name = "AttachmentData", required = true)
    protected String attachmentData;

    /**
     * Gets the value of the attachmentCompressionAlgorithm property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentCompressionAlgorithmType }
     *     
     */
    public AttachmentCompressionAlgorithmType getAttachmentCompressionAlgorithm() {
        return attachmentCompressionAlgorithm;
    }

    /**
     * Sets the value of the attachmentCompressionAlgorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentCompressionAlgorithmType }
     *     
     */
    public void setAttachmentCompressionAlgorithm(AttachmentCompressionAlgorithmType value) {
        this.attachmentCompressionAlgorithm = value;
    }

    /**
     * Gets the value of the attachmentFormat property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentFormatType }
     *     
     */
    public AttachmentFormatType getAttachmentFormat() {
        return attachmentFormat;
    }

    /**
     * Sets the value of the attachmentFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentFormatType }
     *     
     */
    public void setAttachmentFormat(AttachmentFormatType value) {
        this.attachmentFormat = value;
    }

    /**
     * Gets the value of the attachmentEncoding property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentEncodingType }
     *     
     */
    public AttachmentEncodingType getAttachmentEncoding() {
        return attachmentEncoding;
    }

    /**
     * Sets the value of the attachmentEncoding property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentEncodingType }
     *     
     */
    public void setAttachmentEncoding(AttachmentEncodingType value) {
        this.attachmentEncoding = value;
    }

    /**
     * Gets the value of the attachmentDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachmentDescription() {
        return attachmentDescription;
    }

    /**
     * Sets the value of the attachmentDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachmentDescription(String value) {
        this.attachmentDescription = value;
    }

    /**
     * Gets the value of the attachmentData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachmentData() {
        return attachmentData;
    }

    /**
     * Sets the value of the attachmentData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachmentData(String value) {
        this.attachmentData = value;
    }

}
