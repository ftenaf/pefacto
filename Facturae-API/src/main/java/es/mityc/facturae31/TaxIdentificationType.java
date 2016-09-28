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
 * <p>Java class for TaxIdentificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxIdentificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PersonTypeCode" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}PersonTypeCodeType"/>
 *         &lt;element name="ResidenceTypeCode" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}ResidenceTypeCodeType"/>
 *         &lt;element name="TaxIdentificationNumber" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TextMin3Max30Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxIdentificationType", propOrder = {
    "personTypeCode",
    "residenceTypeCode",
    "taxIdentificationNumber"
})
public class TaxIdentificationType {

    @XmlElement(name = "PersonTypeCode", required = true)
    protected PersonTypeCodeType personTypeCode;
    @XmlElement(name = "ResidenceTypeCode", required = true)
    protected ResidenceTypeCodeType residenceTypeCode;
    @XmlElement(name = "TaxIdentificationNumber", required = true)
    protected String taxIdentificationNumber;

    /**
     * Gets the value of the personTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link PersonTypeCodeType }
     *     
     */
    public PersonTypeCodeType getPersonTypeCode() {
        return personTypeCode;
    }

    /**
     * Sets the value of the personTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonTypeCodeType }
     *     
     */
    public void setPersonTypeCode(PersonTypeCodeType value) {
        this.personTypeCode = value;
    }

    /**
     * Gets the value of the residenceTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link ResidenceTypeCodeType }
     *     
     */
    public ResidenceTypeCodeType getResidenceTypeCode() {
        return residenceTypeCode;
    }

    /**
     * Sets the value of the residenceTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResidenceTypeCodeType }
     *     
     */
    public void setResidenceTypeCode(ResidenceTypeCodeType value) {
        this.residenceTypeCode = value;
    }

    /**
     * Gets the value of the taxIdentificationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    /**
     * Sets the value of the taxIdentificationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdentificationNumber(String value) {
        this.taxIdentificationNumber = value;
    }

}
