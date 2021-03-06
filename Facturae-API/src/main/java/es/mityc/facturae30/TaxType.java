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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.mityc.facturae.utils.adapters.DoubleAdapterd2;


/**
 * <p>Java class for TaxType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaxTypeCode" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TaxTypeCodeType"/>
 *         &lt;element name="TaxRate" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="TaxableBase" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ImporteType"/>
 *         &lt;element name="TaxAmount" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ImporteType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxType", propOrder = {
    "taxTypeCode",
    "taxRate",
    "taxableBase",
    "taxAmount"
})
public class TaxType {

    @XmlElement(name = "TaxTypeCode", required = true)
    protected String taxTypeCode;
    @XmlElement(name = "TaxRate")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double taxRate;
    @XmlElement(name = "TaxableBase", required = true)
    protected ImporteType taxableBase;
    @XmlElement(name = "TaxAmount")
    protected ImporteType taxAmount;

    /**
     * Gets the value of the taxTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxTypeCode() {
        return taxTypeCode;
    }

    /**
     * Sets the value of the taxTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxTypeCode(String value) {
        this.taxTypeCode = value;
    }

    /**
     * Gets the value of the taxRate property.
     * 
     */
    public double getTaxRate() {
        return taxRate;
    }

    /**
     * Sets the value of the taxRate property.
     * 
     */
    public void setTaxRate(double value) {
        this.taxRate = value;
    }

    /**
     * Gets the value of the taxableBase property.
     * 
     * @return
     *     possible object is
     *     {@link ImporteType }
     *     
     */
    public ImporteType getTaxableBase() {
        return taxableBase;
    }

    /**
     * Sets the value of the taxableBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImporteType }
     *     
     */
    public void setTaxableBase(ImporteType value) {
        this.taxableBase = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link ImporteType }
     *     
     */
    public ImporteType getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImporteType }
     *     
     */
    public void setTaxAmount(ImporteType value) {
        this.taxAmount = value;
    }

}
