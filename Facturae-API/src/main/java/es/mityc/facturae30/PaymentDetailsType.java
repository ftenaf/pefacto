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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PaymentDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AssignmentDuePaymentDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="AssignmentPaymentMeans" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax15Type"/>
 *         &lt;element name="IBAN" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMin5Max34Type"/>
 *         &lt;element name="PaymentReference" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}TextMax60Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentDetailsType", propOrder = {
    "assignmentDuePaymentDate",
    "assignmentPaymentMeans",
    "iban",
    "paymentReference"
})
public class PaymentDetailsType {

    @XmlElement(name = "AssignmentDuePaymentDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar assignmentDuePaymentDate;
    @XmlElement(name = "AssignmentPaymentMeans", required = true)
    protected String assignmentPaymentMeans;
    @XmlElement(name = "IBAN", required = true)
    protected String iban;
    @XmlElement(name = "PaymentReference", required = true)
    protected String paymentReference;

    /**
     * Gets the value of the assignmentDuePaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAssignmentDuePaymentDate() {
        return assignmentDuePaymentDate;
    }

    /**
     * Sets the value of the assignmentDuePaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAssignmentDuePaymentDate(XMLGregorianCalendar value) {
        this.assignmentDuePaymentDate = value;
    }

    /**
     * Gets the value of the assignmentPaymentMeans property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignmentPaymentMeans() {
        return assignmentPaymentMeans;
    }

    /**
     * Sets the value of the assignmentPaymentMeans property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignmentPaymentMeans(String value) {
        this.assignmentPaymentMeans = value;
    }

    /**
     * Gets the value of the iban property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBAN() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIBAN(String value) {
        this.iban = value;
    }

    /**
     * Gets the value of the paymentReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentReference() {
        return paymentReference;
    }

    /**
     * Sets the value of the paymentReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentReference(String value) {
        this.paymentReference = value;
    }

}
