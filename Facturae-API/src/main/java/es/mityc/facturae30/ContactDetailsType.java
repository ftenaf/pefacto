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
 * <p>Java class for ContactDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Telephone" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax15Type" minOccurs="0"/>
 *         &lt;element name="TeleFax" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax15Type" minOccurs="0"/>
 *         &lt;element name="WebAddress" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax60Type" minOccurs="0"/>
 *         &lt;element name="ElectronicMail" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax60Type" minOccurs="0"/>
 *         &lt;element name="ContactPersons" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax40Type" minOccurs="0"/>
 *         &lt;element name="CnoCnae" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}CnoCnaeType" minOccurs="0"/>
 *         &lt;element name="INETownCode" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax9Type" minOccurs="0"/>
 *         &lt;element name="AdditionalContactDetails" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax60Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactDetailsType", propOrder = {
    "telephone",
    "teleFax",
    "webAddress",
    "electronicMail",
    "contactPersons",
    "cnoCnae",
    "ineTownCode",
    "additionalContactDetails"
})
public class ContactDetailsType {

    @XmlElement(name = "Telephone")
    protected String telephone;
    @XmlElement(name = "TeleFax")
    protected String teleFax;
    @XmlElement(name = "WebAddress")
    protected String webAddress;
    @XmlElement(name = "ElectronicMail")
    protected String electronicMail;
    @XmlElement(name = "ContactPersons")
    protected String contactPersons;
    @XmlElement(name = "CnoCnae")
    protected String cnoCnae;
    @XmlElement(name = "INETownCode")
    protected String ineTownCode;
    @XmlElement(name = "AdditionalContactDetails")
    protected String additionalContactDetails;

    /**
     * Gets the value of the telephone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the value of the telephone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelephone(String value) {
        this.telephone = value;
    }

    /**
     * Gets the value of the teleFax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeleFax() {
        return teleFax;
    }

    /**
     * Sets the value of the teleFax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeleFax(String value) {
        this.teleFax = value;
    }

    /**
     * Gets the value of the webAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebAddress() {
        return webAddress;
    }

    /**
     * Sets the value of the webAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebAddress(String value) {
        this.webAddress = value;
    }

    /**
     * Gets the value of the electronicMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElectronicMail() {
        return electronicMail;
    }

    /**
     * Sets the value of the electronicMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElectronicMail(String value) {
        this.electronicMail = value;
    }

    /**
     * Gets the value of the contactPersons property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPersons() {
        return contactPersons;
    }

    /**
     * Sets the value of the contactPersons property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPersons(String value) {
        this.contactPersons = value;
    }

    /**
     * Gets the value of the cnoCnae property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnoCnae() {
        return cnoCnae;
    }

    /**
     * Sets the value of the cnoCnae property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnoCnae(String value) {
        this.cnoCnae = value;
    }

    /**
     * Gets the value of the ineTownCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINETownCode() {
        return ineTownCode;
    }

    /**
     * Sets the value of the ineTownCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINETownCode(String value) {
        this.ineTownCode = value;
    }

    /**
     * Gets the value of the additionalContactDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalContactDetails() {
        return additionalContactDetails;
    }

    /**
     * Sets the value of the additionalContactDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalContactDetails(String value) {
        this.additionalContactDetails = value;
    }

}
