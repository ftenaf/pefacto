/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
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
package es.mityc.appfacturae.facturae;

public interface InvoiceTotalsType {

	public double getTotalGrossAmount();
    
	public void setTotalGrossAmount(double value);
    
	public DiscountsAndRebatesType getGeneralDiscounts();
    
	public void setGeneralDiscounts(DiscountsAndRebatesType value);
    
	public ChargesType getGeneralSurcharges();
    
	public void setGeneralSurcharges(ChargesType value);
    
	public Double getTotalGeneralDiscounts();
    
	public void setTotalGeneralDiscounts(Double value);
    
	public Double getTotalGeneralSurcharges();
    
	public void setTotalGeneralSurcharges(Double value);
    
	public double getTotalGrossAmountBeforeTaxes();
    
	public void setTotalGrossAmountBeforeTaxes(double value);
    
	public double getTotalTaxOutputs();
    
	public void setTotalTaxOutputs(double value);
    
	public double getTotalTaxesWithheld();
    
	public void setTotalTaxesWithheld(double value);
    
	public double getInvoiceTotal();
    
	public void setInvoiceTotal(double value);
    
	public SubsidiesType getSubsidies();
    
	public void setSubsidies(SubsidiesType value);
    
	public PaymentsOnAccountType getPaymentsOnAccount();
    
	public void setPaymentsOnAccount(PaymentsOnAccountType value);
    
	public double getTotalOutstandingAmount();
    
	public void setTotalOutstandingAmount(double value);
    
	public Double getTotalPaymentsOnAccount();
    
	public void setTotalPaymentsOnAccount(Double value);
    
	public AmountsWithheldType getAmountsWithheld();
    
	public void setAmountsWithheld(AmountsWithheldType value);
    
	public double getTotalExecutableAmount();
    
	public void setTotalExecutableAmount(double value);

}