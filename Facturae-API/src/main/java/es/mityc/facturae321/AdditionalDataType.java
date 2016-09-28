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
 * <p>Java class for AdditionalDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdditionalDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RelatedInvoice" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax40Type" minOccurs="0"/>
 *         &lt;element name="RelatedDocuments" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}AttachedDocumentsType" minOccurs="0"/>
 *         &lt;element name="InvoiceAdditionalInformation" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax2500Type" minOccurs="0"/>
 *         &lt;element name="Extensions" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}ExtensionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdditionalDataType", propOrder = {
    "relatedInvoice",
    "relatedDocuments",
    "invoiceAdditionalInformation",
    "extensions"
})
public class AdditionalDataType {

    @XmlElement(name = "RelatedInvoice")
    protected String relatedInvoice;
    @XmlElement(name = "RelatedDocuments")
    protected AttachedDocumentsType relatedDocuments;
    @XmlElement(name = "InvoiceAdditionalInformation")
    protected String invoiceAdditionalInformation;
    @XmlElement(name = "Extensions")
    protected ExtensionsType extensions;

    /**
     * Gets the value of the relatedInvoice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelatedInvoice() {
        return relatedInvoice;
    }

    /**
     * Sets the value of the relatedInvoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelatedInvoice(String value) {
        this.relatedInvoice = value;
    }

    /**
     * Gets the value of the relatedDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link AttachedDocumentsType }
     *     
     */
    public AttachedDocumentsType getRelatedDocuments() {
        return relatedDocuments;
    }

    /**
     * Sets the value of the relatedDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachedDocumentsType }
     *     
     */
    public void setRelatedDocuments(AttachedDocumentsType value) {
        this.relatedDocuments = value;
    }

    /**
     * Gets the value of the invoiceAdditionalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvoiceAdditionalInformation() {
        return invoiceAdditionalInformation;
    }

    /**
     * Sets the value of the invoiceAdditionalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvoiceAdditionalInformation(String value) {
        this.invoiceAdditionalInformation = value;
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
