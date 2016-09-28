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
 * <p>Java class for IndividualType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndividualType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax40Type"/>
 *         &lt;element name="FirstSurname" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax40Type"/>
 *         &lt;element name="SecondSurname" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax40Type" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="AddressInSpain" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AddressType"/>
 *           &lt;element name="OverseasAddress" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}OverseasAddressType"/>
 *         &lt;/choice>
 *         &lt;element name="ContactDetails" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}ContactDetailsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndividualType", propOrder = {
    "name",
    "firstSurname",
    "secondSurname",
    "addressInSpain",
    "overseasAddress",
    "contactDetails"
})
public class IndividualType {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "FirstSurname", required = true)
    protected String firstSurname;
    @XmlElement(name = "SecondSurname")
    protected String secondSurname;
    @XmlElement(name = "AddressInSpain")
    protected AddressType addressInSpain;
    @XmlElement(name = "OverseasAddress")
    protected OverseasAddressType overseasAddress;
    @XmlElement(name = "ContactDetails")
    protected ContactDetailsType contactDetails;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the firstSurname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstSurname() {
        return firstSurname;
    }

    /**
     * Sets the value of the firstSurname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstSurname(String value) {
        this.firstSurname = value;
    }

    /**
     * Gets the value of the secondSurname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondSurname() {
        return secondSurname;
    }

    /**
     * Sets the value of the secondSurname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondSurname(String value) {
        this.secondSurname = value;
    }

    /**
     * Gets the value of the addressInSpain property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddressInSpain() {
        return addressInSpain;
    }

    /**
     * Sets the value of the addressInSpain property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddressInSpain(AddressType value) {
        this.addressInSpain = value;
    }

    /**
     * Gets the value of the overseasAddress property.
     * 
     * @return
     *     possible object is
     *     {@link OverseasAddressType }
     *     
     */
    public OverseasAddressType getOverseasAddress() {
        return overseasAddress;
    }

    /**
     * Sets the value of the overseasAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link OverseasAddressType }
     *     
     */
    public void setOverseasAddress(OverseasAddressType value) {
        this.overseasAddress = value;
    }

    /**
     * Gets the value of the contactDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ContactDetailsType }
     *     
     */
    public ContactDetailsType getContactDetails() {
        return contactDetails;
    }

    /**
     * Sets the value of the contactDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactDetailsType }
     *     
     */
    public void setContactDetails(ContactDetailsType value) {
        this.contactDetails = value;
    }

}
