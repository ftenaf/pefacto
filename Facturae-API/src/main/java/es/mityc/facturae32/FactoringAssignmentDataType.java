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
 * <p>Java class for FactoringAssignmentDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FactoringAssignmentDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Assignee" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AssigneeType"/>
 *         &lt;element name="PaymentDetails" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}InstallmentsType"/>
 *         &lt;element name="FactoringAssignmentClauses" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax2500Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FactoringAssignmentDataType", propOrder = {
    "assignee",
    "paymentDetails",
    "factoringAssignmentClauses"
})
public class FactoringAssignmentDataType {

    @XmlElement(name = "Assignee", required = true)
    protected AssigneeType assignee;
    @XmlElement(name = "PaymentDetails", required = true)
    protected InstallmentsType paymentDetails;
    @XmlElement(name = "FactoringAssignmentClauses", required = true)
    protected String factoringAssignmentClauses;

    /**
     * Gets the value of the assignee property.
     * 
     * @return
     *     possible object is
     *     {@link AssigneeType }
     *     
     */
    public AssigneeType getAssignee() {
        return assignee;
    }

    /**
     * Sets the value of the assignee property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssigneeType }
     *     
     */
    public void setAssignee(AssigneeType value) {
        this.assignee = value;
    }

    /**
     * Gets the value of the paymentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link InstallmentsType }
     *     
     */
    public InstallmentsType getPaymentDetails() {
        return paymentDetails;
    }

    /**
     * Sets the value of the paymentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstallmentsType }
     *     
     */
    public void setPaymentDetails(InstallmentsType value) {
        this.paymentDetails = value;
    }

    /**
     * Gets the value of the factoringAssignmentClauses property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFactoringAssignmentClauses() {
        return factoringAssignmentClauses;
    }

    /**
     * Sets the value of the factoringAssignmentClauses property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFactoringAssignmentClauses(String value) {
        this.factoringAssignmentClauses = value;
    }

}
