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


/**
 * <p>Java class for PartiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SellerParty" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}EmpresaType"/>
 *         &lt;element name="BuyerParty" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}EmpresaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartiesType", propOrder = {
    "sellerParty",
    "buyerParty"
})
public class PartiesType {

    @XmlElement(name = "SellerParty", required = true)
    protected EmpresaType sellerParty;
    @XmlElement(name = "BuyerParty", required = true)
    protected EmpresaType buyerParty;

    /**
     * Gets the value of the sellerParty property.
     * 
     * @return
     *     possible object is
     *     {@link EmpresaType }
     *     
     */
    public EmpresaType getSellerParty() {
        return sellerParty;
    }

    /**
     * Sets the value of the sellerParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmpresaType }
     *     
     */
    public void setSellerParty(EmpresaType value) {
        this.sellerParty = value;
    }

    /**
     * Gets the value of the buyerParty property.
     * 
     * @return
     *     possible object is
     *     {@link EmpresaType }
     *     
     */
    public EmpresaType getBuyerParty() {
        return buyerParty;
    }

    /**
     * Sets the value of the buyerParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmpresaType }
     *     
     */
    public void setBuyerParty(EmpresaType value) {
        this.buyerParty = value;
    }

}
