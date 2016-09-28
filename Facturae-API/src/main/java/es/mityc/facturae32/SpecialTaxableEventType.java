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
 * <p>Java class for SpecialTaxableEventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpecialTaxableEventType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SpecialTaxableEventCode" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}SpecialTaxableEventCodeType"/>
 *         &lt;element name="SpecialTaxableEventReason" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax2500Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialTaxableEventType", propOrder = {
    "specialTaxableEventCode",
    "specialTaxableEventReason"
})
public class SpecialTaxableEventType {

    @XmlElement(name = "SpecialTaxableEventCode", required = true)
    protected String specialTaxableEventCode;
    @XmlElement(name = "SpecialTaxableEventReason", required = true)
    protected String specialTaxableEventReason;

    /**
     * Gets the value of the specialTaxableEventCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialTaxableEventCode() {
        return specialTaxableEventCode;
    }

    /**
     * Sets the value of the specialTaxableEventCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialTaxableEventCode(String value) {
        this.specialTaxableEventCode = value;
    }

    /**
     * Gets the value of the specialTaxableEventReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialTaxableEventReason() {
        return specialTaxableEventReason;
    }

    /**
     * Sets the value of the specialTaxableEventReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialTaxableEventReason(String value) {
        this.specialTaxableEventReason = value;
    }

}
