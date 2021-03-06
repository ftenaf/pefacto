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
 * <p>Java class for InvoiceTotalsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceTotalsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TotalGrossAmount" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="GeneralDiscounts" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DiscountsAndRebatesType" minOccurs="0"/>
 *         &lt;element name="GeneralSurcharges" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}ChargesType" minOccurs="0"/>
 *         &lt;element name="TotalGeneralDiscounts" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType" minOccurs="0"/>
 *         &lt;element name="TotalGeneralSurcharges" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType" minOccurs="0"/>
 *         &lt;element name="TotalGrossAmountBeforeTaxes" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="TotalTaxOutputs" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="TotalTaxesWithheld" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="InvoiceTotal" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="Subsidies" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}SubsidiesType" minOccurs="0"/>
 *         &lt;element name="PaymentsOnAccount" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}PaymentsOnAccountType" minOccurs="0"/>
 *         &lt;element name="TotalPaymentsOnAccount" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType" minOccurs="0"/>
 *         &lt;element name="TotalOutstandingAmount" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *         &lt;element name="AmountsWithheld" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}AmountsWithheldType" minOccurs="0"/>
 *         &lt;element name="TotalExecutableAmount" type="{http://www.facturae.es/Facturae/2007/v3.0/Facturae}DoubleTwoDecimalType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceTotalsType", propOrder = {
    "totalGrossAmount",
    "generalDiscounts",
    "generalSurcharges",
    "totalGeneralDiscounts",
    "totalGeneralSurcharges",
    "totalGrossAmountBeforeTaxes",
    "totalTaxOutputs",
    "totalTaxesWithheld",
    "invoiceTotal",
    "subsidies",
    "paymentsOnAccount",
    "totalPaymentsOnAccount",
    "totalOutstandingAmount",
    "amountsWithheld",
    "totalExecutableAmount"
})
public class InvoiceTotalsType {

    @XmlElement(name = "TotalGrossAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double totalGrossAmount;
    @XmlElement(name = "GeneralDiscounts")
    protected DiscountsAndRebatesType generalDiscounts;
    @XmlElement(name = "GeneralSurcharges")
    protected ChargesType generalSurcharges;
    @XmlElement(name = "TotalGeneralDiscounts")
    @XmlJavaTypeAdapter(DoubleAdapterd2.class)
    protected Double totalGeneralDiscounts;
    @XmlElement(name = "TotalGeneralSurcharges")
    @XmlJavaTypeAdapter(DoubleAdapterd2.class)
    protected Double totalGeneralSurcharges;
    @XmlElement(name = "TotalGrossAmountBeforeTaxes")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double totalGrossAmountBeforeTaxes;
    @XmlElement(name = "TotalTaxOutputs")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double totalTaxOutputs;
    @XmlElement(name = "TotalTaxesWithheld")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double totalTaxesWithheld;
    @XmlElement(name = "InvoiceTotal")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double invoiceTotal;
    @XmlElement(name = "Subsidies")
    protected SubsidiesType subsidies;
    @XmlElement(name = "PaymentsOnAccount")
    protected PaymentsOnAccountType paymentsOnAccount;
    @XmlElement(name = "TotalPaymentsOnAccount")
    @XmlJavaTypeAdapter(DoubleAdapterd2.class)
    protected Double totalPaymentsOnAccount;
    @XmlElement(name = "TotalOutstandingAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double totalOutstandingAmount;
    @XmlElement(name = "AmountsWithheld")
    protected AmountsWithheldType amountsWithheld;
    @XmlElement(name = "TotalExecutableAmount")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected double totalExecutableAmount;

    /**
     * Gets the value of the totalGrossAmount property.
     * 
     */
    public double getTotalGrossAmount() {
        return totalGrossAmount;
    }

    /**
     * Sets the value of the totalGrossAmount property.
     * 
     */
    public void setTotalGrossAmount(double value) {
        this.totalGrossAmount = value;
    }

    /**
     * Gets the value of the generalDiscounts property.
     * 
     * @return
     *     possible object is
     *     {@link DiscountsAndRebatesType }
     *     
     */
    public DiscountsAndRebatesType getGeneralDiscounts() {
        return generalDiscounts;
    }

    /**
     * Sets the value of the generalDiscounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiscountsAndRebatesType }
     *     
     */
    public void setGeneralDiscounts(DiscountsAndRebatesType value) {
        this.generalDiscounts = value;
    }

    /**
     * Gets the value of the generalSurcharges property.
     * 
     * @return
     *     possible object is
     *     {@link ChargesType }
     *     
     */
    public ChargesType getGeneralSurcharges() {
        return generalSurcharges;
    }

    /**
     * Sets the value of the generalSurcharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChargesType }
     *     
     */
    public void setGeneralSurcharges(ChargesType value) {
        this.generalSurcharges = value;
    }

    /**
     * Gets the value of the totalGeneralDiscounts property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalGeneralDiscounts() {
        return totalGeneralDiscounts;
    }

    /**
     * Sets the value of the totalGeneralDiscounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalGeneralDiscounts(Double value) {
        this.totalGeneralDiscounts = value;
    }

    /**
     * Gets the value of the totalGeneralSurcharges property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalGeneralSurcharges() {
        return totalGeneralSurcharges;
    }

    /**
     * Sets the value of the totalGeneralSurcharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalGeneralSurcharges(Double value) {
        this.totalGeneralSurcharges = value;
    }

    /**
     * Gets the value of the totalGrossAmountBeforeTaxes property.
     * 
     */
    public double getTotalGrossAmountBeforeTaxes() {
        return totalGrossAmountBeforeTaxes;
    }

    /**
     * Sets the value of the totalGrossAmountBeforeTaxes property.
     * 
     */
    public void setTotalGrossAmountBeforeTaxes(double value) {
        this.totalGrossAmountBeforeTaxes = value;
    }

    /**
     * Gets the value of the totalTaxOutputs property.
     * 
     */
    public double getTotalTaxOutputs() {
        return totalTaxOutputs;
    }

    /**
     * Sets the value of the totalTaxOutputs property.
     * 
     */
    public void setTotalTaxOutputs(double value) {
        this.totalTaxOutputs = value;
    }

    /**
     * Gets the value of the totalTaxesWithheld property.
     * 
     */
    public double getTotalTaxesWithheld() {
        return totalTaxesWithheld;
    }

    /**
     * Sets the value of the totalTaxesWithheld property.
     * 
     */
    public void setTotalTaxesWithheld(double value) {
        this.totalTaxesWithheld = value;
    }

    /**
     * Gets the value of the invoiceTotal property.
     * 
     */
    public double getInvoiceTotal() {
        return invoiceTotal;
    }

    /**
     * Sets the value of the invoiceTotal property.
     * 
     */
    public void setInvoiceTotal(double value) {
        this.invoiceTotal = value;
    }

    /**
     * Gets the value of the subsidies property.
     * 
     * @return
     *     possible object is
     *     {@link SubsidiesType }
     *     
     */
    public SubsidiesType getSubsidies() {
        return subsidies;
    }

    /**
     * Sets the value of the subsidies property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubsidiesType }
     *     
     */
    public void setSubsidies(SubsidiesType value) {
        this.subsidies = value;
    }

    /**
     * Gets the value of the paymentsOnAccount property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentsOnAccountType }
     *     
     */
    public PaymentsOnAccountType getPaymentsOnAccount() {
        return paymentsOnAccount;
    }

    /**
     * Sets the value of the paymentsOnAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentsOnAccountType }
     *     
     */
    public void setPaymentsOnAccount(PaymentsOnAccountType value) {
        this.paymentsOnAccount = value;
    }

    /**
     * Gets the value of the totalPaymentsOnAccount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalPaymentsOnAccount() {
        return totalPaymentsOnAccount;
    }

    /**
     * Sets the value of the totalPaymentsOnAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalPaymentsOnAccount(Double value) {
        this.totalPaymentsOnAccount = value;
    }

    /**
     * Gets the value of the totalOutstandingAmount property.
     * 
     */
    public double getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    /**
     * Sets the value of the totalOutstandingAmount property.
     * 
     */
    public void setTotalOutstandingAmount(double value) {
        this.totalOutstandingAmount = value;
    }

    /**
     * Gets the value of the amountsWithheld property.
     * 
     * @return
     *     possible object is
     *     {@link AmountsWithheldType }
     *     
     */
    public AmountsWithheldType getAmountsWithheld() {
        return amountsWithheld;
    }

    /**
     * Sets the value of the amountsWithheld property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountsWithheldType }
     *     
     */
    public void setAmountsWithheld(AmountsWithheldType value) {
        this.amountsWithheld = value;
    }

    /**
     * Gets the value of the totalExecutableAmount property.
     * 
     */
    public double getTotalExecutableAmount() {
        return totalExecutableAmount;
    }

    /**
     * Sets the value of the totalExecutableAmount property.
     * 
     */
    public void setTotalExecutableAmount(double value) {
        this.totalExecutableAmount = value;
    }

}
