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
 * <p>Java class for AdministrativeCentreType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdministrativeCentreType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CentreCode" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax10Type" minOccurs="0"/>
 *         &lt;element name="RoleTypeCode" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}RoleTypeCodeType" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax40Type" minOccurs="0"/>
 *         &lt;element name="FirstSurname" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax40Type" minOccurs="0"/>
 *         &lt;element name="SecondSurname" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax40Type" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="AddressInSpain" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}AddressType"/>
 *           &lt;element name="OverseasAddress" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}OverseasAddressType"/>
 *         &lt;/choice>
 *         &lt;element name="ContactDetails" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}ContactDetailsType" minOccurs="0"/>
 *         &lt;element name="PhysicalGLN" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax14Type" minOccurs="0"/>
 *         &lt;element name="LogicalOperationalPoint" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax14Type" minOccurs="0"/>
 *         &lt;element name="CentreDescription" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}TextMax2500Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdministrativeCentreType", propOrder = {
    "centreCode",
    "roleTypeCode",
    "name",
    "firstSurname",
    "secondSurname",
    "addressInSpain",
    "overseasAddress",
    "contactDetails",
    "physicalGLN",
    "logicalOperationalPoint",
    "centreDescription"
})
public class AdministrativeCentreType {

    @XmlElement(name = "CentreCode")
    protected String centreCode;
    @XmlElement(name = "RoleTypeCode")
    protected String roleTypeCode;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "FirstSurname")
    protected String firstSurname;
    @XmlElement(name = "SecondSurname")
    protected String secondSurname;
    @XmlElement(name = "AddressInSpain")
    protected AddressType addressInSpain;
    @XmlElement(name = "OverseasAddress")
    protected OverseasAddressType overseasAddress;
    @XmlElement(name = "ContactDetails")
    protected ContactDetailsType contactDetails;
    @XmlElement(name = "PhysicalGLN")
    protected String physicalGLN;
    @XmlElement(name = "LogicalOperationalPoint")
    protected String logicalOperationalPoint;
    @XmlElement(name = "CentreDescription")
    protected String centreDescription;

    /**
     * Gets the value of the centreCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentreCode() {
        return centreCode;
    }

    /**
     * Sets the value of the centreCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentreCode(String value) {
        this.centreCode = value;
    }

    /**
     * Gets the value of the roleTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleTypeCode() {
        return roleTypeCode;
    }

    /**
     * Sets the value of the roleTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleTypeCode(String value) {
        this.roleTypeCode = value;
    }

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

    /**
     * Gets the value of the physicalGLN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhysicalGLN() {
        return physicalGLN;
    }

    /**
     * Sets the value of the physicalGLN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhysicalGLN(String value) {
        this.physicalGLN = value;
    }

    /**
     * Gets the value of the logicalOperationalPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogicalOperationalPoint() {
        return logicalOperationalPoint;
    }

    /**
     * Sets the value of the logicalOperationalPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogicalOperationalPoint(String value) {
        this.logicalOperationalPoint = value;
    }

    /**
     * Gets the value of the centreDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentreDescription() {
        return centreDescription;
    }

    /**
     * Sets the value of the centreDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentreDescription(String value) {
        this.centreDescription = value;
    }

}
