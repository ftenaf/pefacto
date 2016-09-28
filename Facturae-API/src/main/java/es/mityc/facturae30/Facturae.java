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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import es.mityc.facturae.GenericFacturae;


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
 *         &lt;element name="FileHeader" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}FileHeaderType"/>
 *         &lt;element name="Parties" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}PartiesType"/>
 *         &lt;element name="Invoices" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}InvoicesType"/>
 *         &lt;element name="Extensions" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ExtensionsType" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
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
    "fileHeader",
    "parties",
    "invoices",
    "extensions",
    "signature"
})
@XmlRootElement(name = "Facturae")
public class Facturae extends GenericFacturae {

    @XmlElement(name = "FileHeader", required = true)
    protected FileHeaderType fileHeader;
    @XmlElement(name = "Parties", required = true)
    protected PartiesType parties;
    @XmlElement(name = "Invoices", required = true)
    protected InvoicesType invoices;
    @XmlElement(name = "Extensions")
    protected ExtensionsType extensions;
    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;

    /**
     * Gets the value of the fileHeader property.
     * 
     * @return
     *     possible object is
     *     {@link FileHeaderType }
     *     
     */
    public FileHeaderType getFileHeader() {
        return fileHeader;
    }

    /**
     * Sets the value of the fileHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileHeaderType }
     *     
     */
    public void setFileHeader(FileHeaderType value) {
        this.fileHeader = value;
    }

    /**
     * Gets the value of the parties property.
     * 
     * @return
     *     possible object is
     *     {@link PartiesType }
     *     
     */
    public PartiesType getParties() {
        return parties;
    }

    /**
     * Sets the value of the parties property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartiesType }
     *     
     */
    public void setParties(PartiesType value) {
        this.parties = value;
    }

    /**
     * Gets the value of the invoices property.
     * 
     * @return
     *     possible object is
     *     {@link InvoicesType }
     *     
     */
    public InvoicesType getInvoices() {
        return invoices;
    }

    /**
     * Sets the value of the invoices property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoicesType }
     *     
     */
    public void setInvoices(InvoicesType value) {
        this.invoices = value;
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
     * Conjunto de datos asociados a la factura que garantizarán la autoría y la integridad del mensaje. Se define como opcional para facilitar la verificación y el tránsito del fichero. No obstante, debe cumplimentarse este bloque de firma electrónica para que se considere una factura electrónica válida legalmente frente a terceros.
     * 						
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

}
