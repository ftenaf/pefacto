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
package es.mityc.facturae321;

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
 *         &lt;element name="TotalGrossAmount" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType"/>
 *         &lt;element name="GeneralDiscounts" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DiscountsAndRebatesType" minOccurs="0"/>
 *         &lt;element name="GeneralSurcharges" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}ChargesType" minOccurs="0"/>
 *         &lt;element name="TotalGeneralDiscounts" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType" minOccurs="0"/>
 *         &lt;element name="TotalGeneralSurcharges" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType" minOccurs="0"/>
 *         &lt;element name="TotalGrossAmountBeforeTaxes" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType"/>
 *         &lt;element name="TotalTaxOutputs" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType"/>
 *         &lt;element name="TotalTaxesWithheld" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType"/>
 *         &lt;element name="InvoiceTotal" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType"/>
 *         &lt;element name="Subsidies" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}SubsidiesType" minOccurs="0"/>
 *         &lt;element name="PaymentsOnAccount" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}PaymentsOnAccountType" minOccurs="0"/>
 *         &lt;element name="ReimbursableExpenses" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}ReimbursableExpenses" minOccurs="0"/>
 *         &lt;element name="TotalFinancialExpenses" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleTwoDecimalType" minOccurs="0"/>
 *         &lt;element name="TotalOutstandingAmount" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType"/>
 *         &lt;element name="TotalPaymentsOnAccount" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType" minOccurs="0"/>
 *         &lt;element name="AmountsWithheld" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}AmountsWithheldType" minOccurs="0"/>
 *         &lt;element name="TotalExecutableAmount" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType"/>
 *         &lt;element name="TotalReimbursableExpenses" type="{http://www.facturae.es/Facturae/2014/v3.2.1/Facturae}DoubleUpToEightDecimalType" minOccurs="0"/>
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
    "reimbursableExpenses",
    "totalFinancialExpenses",
    "totalOutstandingAmount",
    "totalPaymentsOnAccount",
    "amountsWithheld",
    "totalExecutableAmount",
    "totalReimbursableExpenses"
})
public class InvoiceTotalsType {

    @XmlElement(name = "TotalGrossAmount")
    protected double totalGrossAmount;
    @XmlElement(name = "GeneralDiscounts")
    protected DiscountsAndRebatesType generalDiscounts;
    @XmlElement(name = "GeneralSurcharges")
    protected ChargesType generalSurcharges;
    @XmlElement(name = "TotalGeneralDiscounts")
    protected Double totalGeneralDiscounts;
    @XmlElement(name = "TotalGeneralSurcharges")
    protected Double totalGeneralSurcharges;
    @XmlElement(name = "TotalGrossAmountBeforeTaxes")
    protected double totalGrossAmountBeforeTaxes;
    @XmlElement(name = "TotalTaxOutputs")
    protected double totalTaxOutputs;
    @XmlElement(name = "TotalTaxesWithheld")
    protected double totalTaxesWithheld;
    @XmlElement(name = "InvoiceTotal")
    protected double invoiceTotal;
    @XmlElement(name = "Subsidies")
    protected SubsidiesType subsidies;
    @XmlElement(name = "PaymentsOnAccount")
    protected PaymentsOnAccountType paymentsOnAccount;
    @XmlElement(name = "ReimbursableExpenses")
    protected ReimbursableExpenses reimbursableExpenses;
    @XmlElement(name = "TotalFinancialExpenses")
    @XmlJavaTypeAdapter(type=double.class, value=DoubleAdapterd2.class)
    protected Double totalFinancialExpenses;
    @XmlElement(name = "TotalOutstandingAmount")
    protected double totalOutstandingAmount;
    @XmlElement(name = "TotalPaymentsOnAccount")
    protected Double totalPaymentsOnAccount;
    @XmlElement(name = "AmountsWithheld")
    protected AmountsWithheldType amountsWithheld;
    @XmlElement(name = "TotalExecutableAmount")
    protected double totalExecutableAmount;
    @XmlElement(name = "TotalReimbursableExpenses")
    protected Double totalReimbursableExpenses;

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
     * Gets the value of the reimbursableExpenses property.
     * 
     * @return
     *     possible object is
     *     {@link ReimbursableExpenses }
     *     
     */
    public ReimbursableExpenses getReimbursableExpenses() {
        return reimbursableExpenses;
    }

    /**
     * Sets the value of the reimbursableExpenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReimbursableExpenses }
     *     
     */
    public void setReimbursableExpenses(ReimbursableExpenses value) {
        this.reimbursableExpenses = value;
    }

    /**
     * Gets the value of the totalFinancialExpenses property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalFinancialExpenses() {
        return totalFinancialExpenses;
    }

    /**
     * Sets the value of the totalFinancialExpenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalFinancialExpenses(Double value) {
        this.totalFinancialExpenses = value;
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

    /**
     * Gets the value of the totalReimbursableExpenses property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTotalReimbursableExpenses() {
        return totalReimbursableExpenses;
    }

    /**
     * Sets the value of the totalReimbursableExpenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTotalReimbursableExpenses(Double value) {
        this.totalReimbursableExpenses = value;
    }

}
