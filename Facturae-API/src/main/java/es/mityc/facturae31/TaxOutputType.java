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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.mityc.facturae.utils.adapters.DoubleAdapterd2;


/**
 * <p>Java class for TaxOutputType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxOutputType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaxTypeCode" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}TaxTypeCodeType"/>
 *         &lt;element name="TaxRate" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="TaxableBase" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AmountType"/>
 *         &lt;element name="TaxAmount" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AmountType" minOccurs="0"/>
 *         &lt;element name="SpecialTaxableBase" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AmountType" minOccurs="0"/>
 *         &lt;element name="SpecialTaxAmount" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AmountType" minOccurs="0"/>
 *         &lt;element name="EquivalenceSurcharge" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}DoubleTwoDecimalType" minOccurs="0"/>
 *         &lt;element name="EquivalenceSurchargeAmount" type="{http://www.facturae.es/Facturae/2007/v3.1/Facturae}AmountType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxOutputType", propOrder = {
    "taxTypeCode",
    "taxRate",
    "taxableBase",
    "taxAmount",
    "specialTaxableBase",
    "specialTaxAmount",
    "equivalenceSurcharge",
    "equivalenceSurchargeAmount"
})
@XmlSeeAlso({
    es.mityc.facturae31.InvoiceLineType.TaxesOutputs.Tax.class
})
public class TaxOutputType {

    @XmlElement(name = "TaxTypeCode", required = true)
    protected String taxTypeCode;
    @XmlElement(name = "TaxRate")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double taxRate;
    @XmlElement(name = "TaxableBase", required = true)
    protected AmountType taxableBase;
    @XmlElement(name = "TaxAmount")
    protected AmountType taxAmount;
    @XmlElement(name = "SpecialTaxableBase")
    protected AmountType specialTaxableBase;
    @XmlElement(name = "SpecialTaxAmount")
    protected AmountType specialTaxAmount;
    @XmlElement(name = "EquivalenceSurcharge")
    @XmlJavaTypeAdapter(DoubleAdapterd2.class)
    protected Double equivalenceSurcharge;
    @XmlElement(name = "EquivalenceSurchargeAmount")
    protected AmountType equivalenceSurchargeAmount;

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
     *     {@link AmountType }
     *     
     */
    public AmountType getTaxableBase() {
        return taxableBase;
    }

    /**
     * Sets the value of the taxableBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTaxableBase(AmountType value) {
        this.taxableBase = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setTaxAmount(AmountType value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the specialTaxableBase property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getSpecialTaxableBase() {
        return specialTaxableBase;
    }

    /**
     * Sets the value of the specialTaxableBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setSpecialTaxableBase(AmountType value) {
        this.specialTaxableBase = value;
    }

    /**
     * Gets the value of the specialTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getSpecialTaxAmount() {
        return specialTaxAmount;
    }

    /**
     * Sets the value of the specialTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setSpecialTaxAmount(AmountType value) {
        this.specialTaxAmount = value;
    }

    /**
     * Gets the value of the equivalenceSurcharge property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEquivalenceSurcharge() {
        return equivalenceSurcharge;
    }

    /**
     * Sets the value of the equivalenceSurcharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEquivalenceSurcharge(Double value) {
        this.equivalenceSurcharge = value;
    }

    /**
     * Gets the value of the equivalenceSurchargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link AmountType }
     *     
     */
    public AmountType getEquivalenceSurchargeAmount() {
        return equivalenceSurchargeAmount;
    }

    /**
     * Sets the value of the equivalenceSurchargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountType }
     *     
     */
    public void setEquivalenceSurchargeAmount(AmountType value) {
        this.equivalenceSurchargeAmount = value;
    }

}
