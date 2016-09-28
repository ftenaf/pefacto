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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.mityc.facturae.utils.adapters.DoubleAdapterd4;
import es.mityc.facturae.utils.adapters.DoubleAdapterd6;


/**
 * <p>Java class for ChargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChargeReason" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax2500Type"/>
 *         &lt;element name="ChargeRate" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleFourDecimalType" minOccurs="0"/>
 *         &lt;element name="ChargeAmount" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleSixDecimalType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeType", propOrder = {
    "chargeReason",
    "chargeRate",
    "chargeAmount"
})
public class ChargeType {

    @XmlElement(name = "ChargeReason", required = true)
    protected String chargeReason;
    @XmlElement(name = "ChargeRate")
    @XmlJavaTypeAdapter(DoubleAdapterd4.class)
    protected Double chargeRate;
    @XmlElement(name = "ChargeAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd6.class)
    protected double chargeAmount;

    /**
     * Gets the value of the chargeReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeReason() {
        return chargeReason;
    }

    /**
     * Sets the value of the chargeReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeReason(String value) {
        this.chargeReason = value;
    }

    /**
     * Gets the value of the chargeRate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getChargeRate() {
        return chargeRate;
    }

    /**
     * Sets the value of the chargeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setChargeRate(Double value) {
        this.chargeRate = value;
    }

    /**
     * Gets the value of the chargeAmount property.
     * 
     */
    public double getChargeAmount() {
        return chargeAmount;
    }

    /**
     * Sets the value of the chargeAmount property.
     * 
     */
    public void setChargeAmount(double value) {
        this.chargeAmount = value;
    }

}
