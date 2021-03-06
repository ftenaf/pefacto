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
 * <p>Java class for BusinessType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaxIdentification" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TaxIdentificationType"/>
 *         &lt;element name="PartyIdentification" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}PartyIdentificationType" minOccurs="0"/>
 *         &lt;element name="AdministrativeCentres" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AdministrativeCentresType" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="LegalEntity" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}LegalEntityType"/>
 *           &lt;element name="Individual" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}IndividualType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessType", propOrder = {
    "taxIdentification",
    "partyIdentification",
    "administrativeCentres",
    "legalEntity",
    "individual"
})
public class BusinessType {

    @XmlElement(name = "TaxIdentification", required = true)
    protected TaxIdentificationType taxIdentification;
    @XmlElement(name = "PartyIdentification")
    protected String partyIdentification;
    @XmlElement(name = "AdministrativeCentres")
    protected AdministrativeCentresType administrativeCentres;
    @XmlElement(name = "LegalEntity")
    protected LegalEntityType legalEntity;
    @XmlElement(name = "Individual")
    protected IndividualType individual;

    /**
     * Gets the value of the taxIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link TaxIdentificationType }
     *     
     */
    public TaxIdentificationType getTaxIdentification() {
        return taxIdentification;
    }

    /**
     * Sets the value of the taxIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxIdentificationType }
     *     
     */
    public void setTaxIdentification(TaxIdentificationType value) {
        this.taxIdentification = value;
    }

    /**
     * Gets the value of the partyIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartyIdentification() {
        return partyIdentification;
    }

    /**
     * Sets the value of the partyIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartyIdentification(String value) {
        this.partyIdentification = value;
    }

    /**
     * Gets the value of the administrativeCentres property.
     * 
     * @return
     *     possible object is
     *     {@link AdministrativeCentresType }
     *     
     */
    public AdministrativeCentresType getAdministrativeCentres() {
        return administrativeCentres;
    }

    /**
     * Sets the value of the administrativeCentres property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdministrativeCentresType }
     *     
     */
    public void setAdministrativeCentres(AdministrativeCentresType value) {
        this.administrativeCentres = value;
    }

    /**
     * Gets the value of the legalEntity property.
     * 
     * @return
     *     possible object is
     *     {@link LegalEntityType }
     *     
     */
    public LegalEntityType getLegalEntity() {
        return legalEntity;
    }

    /**
     * Sets the value of the legalEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link LegalEntityType }
     *     
     */
    public void setLegalEntity(LegalEntityType value) {
        this.legalEntity = value;
    }

    /**
     * Gets the value of the individual property.
     * 
     * @return
     *     possible object is
     *     {@link IndividualType }
     *     
     */
    public IndividualType getIndividual() {
        return individual;
    }

    /**
     * Sets the value of the individual property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndividualType }
     *     
     */
    public void setIndividual(IndividualType value) {
        this.individual = value;
    }

}
