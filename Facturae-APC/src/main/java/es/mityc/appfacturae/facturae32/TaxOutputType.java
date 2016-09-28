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

import es.mityc.appfacturae.facturae.AmountType;
 
public class TaxOutputType {
    
	protected String taxTypeCode;
    protected double taxRate;
    protected AmountType taxableBase;
    protected AmountType taxAmount;
    protected AmountType specialTaxableBase;
    protected AmountType specialTaxAmount;
    protected Double equivalenceSurcharge;
    protected AmountType equivalenceSurchargeAmount;
    
	public String getTaxTypeCode() {
        return taxTypeCode;
    }
    
	public void setTaxTypeCode(String value) {
        this.taxTypeCode = value;
    }
    
	public double getTaxRate() {
        return taxRate;
    }
    
	public void setTaxRate(double value) {
        this.taxRate = value;
    }
    
	public AmountType getTaxableBase() {
        return taxableBase;
    }
    
	public void setTaxableBase(AmountType value) {
        this.taxableBase = value;
    }
    
	public AmountType getTaxAmount() {
        return taxAmount;
    }
    
	public void setTaxAmount(AmountType value) {
        this.taxAmount = value;
    }
    
	public AmountType getSpecialTaxableBase() {
        return specialTaxableBase;
    }
    
	public void setSpecialTaxableBase(AmountType value) {
        this.specialTaxableBase = value;
    }
    
	public AmountType getSpecialTaxAmount() {
        return specialTaxAmount;
    }
    
	public void setSpecialTaxAmount(AmountType value) {
        this.specialTaxAmount = value;
    }
    
	public Double getEquivalenceSurcharge() {
        return equivalenceSurcharge;
    }
    
	public void setEquivalenceSurcharge(Double value) {
        this.equivalenceSurcharge = value;
    }
    
	public AmountType getEquivalenceSurchargeAmount() {
        return equivalenceSurchargeAmount;
    }
    
	public void setEquivalenceSurchargeAmount(AmountType value) {
        this.equivalenceSurchargeAmount = value;
    }

}