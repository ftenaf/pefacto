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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import es.mityc.facturae.utils.adapters.DoubleAdapterd2;

/**
 * <p>Java class for InstallmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InstallmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InstallmentDueDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="InstallmentAmount" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="PaymentMeans" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}PaymentMeansType"/>
 *         &lt;element name="AccountToBeCredited" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AccountType" minOccurs="0"/>
 *         &lt;element name="PaymentReconciliationReference" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax60Type" minOccurs="0"/>
 *         &lt;element name="AccountToBeDebited" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}AccountType" minOccurs="0"/>
 *         &lt;element name="CollectionAdditionalInformation" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax2500Type" minOccurs="0"/>
 *         &lt;element name="RegulatoryReportingData" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}RegulatoryReportingDataType" minOccurs="0"/>
 *         &lt;element name="DebitReconciliationReference" type="{http://www.facturae.es/Facturae/2009/v3.2/Facturae}TextMax60Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstallmentType", propOrder = {
    "installmentDueDate",
    "installmentAmount",
    "paymentMeans",
    "accountToBeCredited",
    "paymentReconciliationReference",
    "accountToBeDebited",
    "collectionAdditionalInformation",
    "regulatoryReportingData",
    "debitReconciliationReference"
})
public class InstallmentType {

    @XmlElement(name = "InstallmentDueDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar installmentDueDate;
    @XmlElement(name = "InstallmentAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double installmentAmount;
    @XmlElement(name = "PaymentMeans", required = true)
    protected String paymentMeans;
    @XmlElement(name = "AccountToBeCredited")
    protected AccountType accountToBeCredited;
    @XmlElement(name = "PaymentReconciliationReference")
    protected String paymentReconciliationReference;
    @XmlElement(name = "AccountToBeDebited")
    protected AccountType accountToBeDebited;
    @XmlElement(name = "CollectionAdditionalInformation")
    protected String collectionAdditionalInformation;
    @XmlElement(name = "RegulatoryReportingData")
    protected String regulatoryReportingData;
    @XmlElement(name = "DebitReconciliationReference")
    protected String debitReconciliationReference;

    /**
     * Gets the value of the installmentDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInstallmentDueDate() {
        return installmentDueDate;
    }

    /**
     * Sets the value of the installmentDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInstallmentDueDate(XMLGregorianCalendar value) {
        this.installmentDueDate = value;
    }

    /**
     * Gets the value of the installmentAmount property.
     * 
     */
    public double getInstallmentAmount() {
        return installmentAmount;
    }

    /**
     * Sets the value of the installmentAmount property.
     * 
     */
    public void setInstallmentAmount(double value) {
        this.installmentAmount = value;
    }

    /**
     * Gets the value of the paymentMeans property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentMeans() {
        return paymentMeans;
    }

    /**
     * Sets the value of the paymentMeans property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentMeans(String value) {
        this.paymentMeans = value;
    }

    /**
     * Gets the value of the accountToBeCredited property.
     * 
     * @return
     *     possible object is
     *     {@link AccountType }
     *     
     */
    public AccountType getAccountToBeCredited() {
        return accountToBeCredited;
    }

    /**
     * Sets the value of the accountToBeCredited property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountType }
     *     
     */
    public void setAccountToBeCredited(AccountType value) {
        this.accountToBeCredited = value;
    }

    /**
     * Gets the value of the paymentReconciliationReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentReconciliationReference() {
        return paymentReconciliationReference;
    }

    /**
     * Sets the value of the paymentReconciliationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentReconciliationReference(String value) {
        this.paymentReconciliationReference = value;
    }

    /**
     * Gets the value of the accountToBeDebited property.
     * 
     * @return
     *     possible object is
     *     {@link AccountType }
     *     
     */
    public AccountType getAccountToBeDebited() {
        return accountToBeDebited;
    }

    /**
     * Sets the value of the accountToBeDebited property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountType }
     *     
     */
    public void setAccountToBeDebited(AccountType value) {
        this.accountToBeDebited = value;
    }

    /**
     * Gets the value of the collectionAdditionalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectionAdditionalInformation() {
        return collectionAdditionalInformation;
    }

    /**
     * Sets the value of the collectionAdditionalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectionAdditionalInformation(String value) {
        this.collectionAdditionalInformation = value;
    }

    /**
     * Gets the value of the regulatoryReportingData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegulatoryReportingData() {
        return regulatoryReportingData;
    }

    /**
     * Sets the value of the regulatoryReportingData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegulatoryReportingData(String value) {
        this.regulatoryReportingData = value;
    }

    /**
     * Gets the value of the debitReconciliationReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDebitReconciliationReference() {
        return debitReconciliationReference;
    }

    /**
     * Sets the value of the debitReconciliationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDebitReconciliationReference(String value) {
        this.debitReconciliationReference = value;
    }

}
