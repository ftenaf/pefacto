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
 * <p>Java class for RegistrationDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegistrationDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Book" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="RegisterOfCompaniesLocation" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="Sheet" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="Folio" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="Section" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="Volume" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax20Type" minOccurs="0"/>
 *         &lt;element name="AdditionalRegistrationData" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMax20Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationDataType", propOrder = {
    "book",
    "registerOfCompaniesLocation",
    "sheet",
    "folio",
    "section",
    "volume",
    "additionalRegistrationData"
})
public class RegistrationDataType {

    @XmlElement(name = "Book")
    protected String book;
    @XmlElement(name = "RegisterOfCompaniesLocation")
    protected String registerOfCompaniesLocation;
    @XmlElement(name = "Sheet")
    protected String sheet;
    @XmlElement(name = "Folio")
    protected String folio;
    @XmlElement(name = "Section")
    protected String section;
    @XmlElement(name = "Volume")
    protected String volume;
    @XmlElement(name = "AdditionalRegistrationData")
    protected String additionalRegistrationData;

    /**
     * Gets the value of the book property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBook() {
        return book;
    }

    /**
     * Sets the value of the book property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBook(String value) {
        this.book = value;
    }

    /**
     * Gets the value of the registerOfCompaniesLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisterOfCompaniesLocation() {
        return registerOfCompaniesLocation;
    }

    /**
     * Sets the value of the registerOfCompaniesLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisterOfCompaniesLocation(String value) {
        this.registerOfCompaniesLocation = value;
    }

    /**
     * Gets the value of the sheet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSheet() {
        return sheet;
    }

    /**
     * Sets the value of the sheet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSheet(String value) {
        this.sheet = value;
    }

    /**
     * Gets the value of the folio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Sets the value of the folio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolio(String value) {
        this.folio = value;
    }

    /**
     * Gets the value of the section property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSection() {
        return section;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSection(String value) {
        this.section = value;
    }

    /**
     * Gets the value of the volume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVolume(String value) {
        this.volume = value;
    }

    /**
     * Gets the value of the additionalRegistrationData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalRegistrationData() {
        return additionalRegistrationData;
    }

    /**
     * Sets the value of the additionalRegistrationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalRegistrationData(String value) {
        this.additionalRegistrationData = value;
    }

}
