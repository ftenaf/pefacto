/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-APC".
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
package es.mityc.appfacturae.facturae32;

import es.mityc.appfacturae.facturae.AmountsWithheldType;
import es.mityc.appfacturae.facturae.ChargesType;
import es.mityc.appfacturae.facturae.DiscountsAndRebatesType;
import es.mityc.appfacturae.facturae.PaymentsOnAccountType;
import es.mityc.appfacturae.facturae.ReimbursableExpenses;
import es.mityc.appfacturae.facturae.SubsidiesType;
 
public class InvoiceTotalsType implements es.mityc.appfacturae.facturae.InvoiceTotalsType{
    
	protected double totalGrossAmount;
    protected DiscountsAndRebatesType generalDiscounts;
    protected ChargesType generalSurcharges;
    protected Double totalGeneralDiscounts;
    protected Double totalGeneralSurcharges;
    protected double totalGrossAmountBeforeTaxes;
    protected double totalTaxOutputs;
    protected double totalTaxesWithheld;
    protected double invoiceTotal;
    protected SubsidiesType subsidies;
    protected PaymentsOnAccountType paymentsOnAccount;
    protected ReimbursableExpenses reimbursableExpenses;
    protected Double totalFinancialExpenses;
    protected double totalOutstandingAmount;
    protected Double totalPaymentsOnAccount;
    protected AmountsWithheldType amountsWithheld;
    protected double totalExecutableAmount;
    protected Double totalReimbursableExpenses;
    
	public double getTotalGrossAmount() {
        return totalGrossAmount;
    }
    
	public void setTotalGrossAmount(double value) {
        this.totalGrossAmount = value;
    }
    
	public DiscountsAndRebatesType getGeneralDiscounts() {
        return generalDiscounts;
    }
    
	public void setGeneralDiscounts(DiscountsAndRebatesType value) {
        this.generalDiscounts = value;
    }
    
	public ChargesType getGeneralSurcharges() {
        return generalSurcharges;
    }
    
	public void setGeneralSurcharges(ChargesType value) {
        this.generalSurcharges = value;
    }
    
	public Double getTotalGeneralDiscounts() {
        return totalGeneralDiscounts;
    }
    
	public void setTotalGeneralDiscounts(Double value) {
        this.totalGeneralDiscounts = value;
    }
    
	public Double getTotalGeneralSurcharges() {
        return totalGeneralSurcharges;
    }
    
	public void setTotalGeneralSurcharges(Double value) {
        this.totalGeneralSurcharges = value;
    }
    
	public double getTotalGrossAmountBeforeTaxes() {
        return totalGrossAmountBeforeTaxes;
    }
    
	public void setTotalGrossAmountBeforeTaxes(double value) {
        this.totalGrossAmountBeforeTaxes = value;
    }
    
	public double getTotalTaxOutputs() {
        return totalTaxOutputs;
    }
    
	public void setTotalTaxOutputs(double value) {
        this.totalTaxOutputs = value;
    }
    
	public double getTotalTaxesWithheld() {
        return totalTaxesWithheld;
    }
    
	public void setTotalTaxesWithheld(double value) {
        this.totalTaxesWithheld = value;
    }
    
	public double getInvoiceTotal() {
        return invoiceTotal;
    }
    
	public void setInvoiceTotal(double value) {
        this.invoiceTotal = value;
    }
    
	public SubsidiesType getSubsidies() {
        return subsidies;
    }
    
	public void setSubsidies(SubsidiesType value) {
        this.subsidies = value;
    }
    
	public PaymentsOnAccountType getPaymentsOnAccount() {
        return paymentsOnAccount;
    }
    
	public void setPaymentsOnAccount(PaymentsOnAccountType value) {
        this.paymentsOnAccount = value;
    }
    
	public ReimbursableExpenses getReimbursableExpenses() {
        return reimbursableExpenses;
    }
    
	public void setReimbursableExpenses(ReimbursableExpenses value) {
        this.reimbursableExpenses = value;
    }
    
	public Double getTotalFinancialExpenses() {
        return totalFinancialExpenses;
    }
    
	public void setTotalFinancialExpenses(Double value) {
        this.totalFinancialExpenses = value;
    }
    
	public double getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }
    
	public void setTotalOutstandingAmount(double value) {
        this.totalOutstandingAmount = value;
    }
    
	public Double getTotalPaymentsOnAccount() {
        return totalPaymentsOnAccount;
    }
    
	public void setTotalPaymentsOnAccount(Double value) {
        this.totalPaymentsOnAccount = value;
    }
    
	public AmountsWithheldType getAmountsWithheld() {
        return amountsWithheld;
    }
    
	public void setAmountsWithheld(AmountsWithheldType value) {
        this.amountsWithheld = value;
    }
    
	public double getTotalExecutableAmount() {
        return totalExecutableAmount;
    }
    
	public void setTotalExecutableAmount(double value) {
        this.totalExecutableAmount = value;
    }
    
	public Double getTotalReimbursableExpenses() {
        return totalReimbursableExpenses;
    }
    
	public void setTotalReimbursableExpenses(Double value) {
        this.totalReimbursableExpenses = value;
    }

}