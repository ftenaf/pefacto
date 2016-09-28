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

import es.mityc.facturae.utils.adapters.DoubleAdapterd2;
import es.mityc.facturae.utils.adapters.DoubleAdapterd4;


/**
 * <p>Java class for AmountsWithheldType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AmountsWithheldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WithholdingReason" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax2500Type"/>
 *         &lt;element name="WithholdingRate" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleFourDecimalType" minOccurs="0"/>
 *         &lt;element name="WithholdingAmount" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleTwoDecimalType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountsWithheldType", propOrder = {
    "withholdingReason",
    "withholdingRate",
    "withholdingAmount"
})
public class AmountsWithheldType {

    @XmlElement(name = "WithholdingReason", required = true)
    protected String withholdingReason;
    @XmlElement(name = "WithholdingRate")
    @XmlJavaTypeAdapter(DoubleAdapterd4.class)
    protected Double withholdingRate;
    @XmlElement(name = "WithholdingAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double withholdingAmount;

    /**
     * Gets the value of the withholdingReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWithholdingReason() {
        return withholdingReason;
    }

    /**
     * Sets the value of the withholdingReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWithholdingReason(String value) {
        this.withholdingReason = value;
    }

    /**
     * Gets the value of the withholdingRate property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getWithholdingRate() {
        return withholdingRate;
    }

    /**
     * Sets the value of the withholdingRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setWithholdingRate(Double value) {
        this.withholdingRate = value;
    }

    /**
     * Gets the value of the withholdingAmount property.
     * 
     */
    public double getWithholdingAmount() {
        return withholdingAmount;
    }

    /**
     * Sets the value of the withholdingAmount property.
     * 
     */
    public void setWithholdingAmount(double value) {
        this.withholdingAmount = value;
    }

}
