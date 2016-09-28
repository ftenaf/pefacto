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
import es.mityc.appfacturae.facturae.CurrencyCodeType;

public class BatchType implements es.mityc.appfacturae.facturae.BatchType{
    
	protected String batchIdentifier;
    protected long invoicesCount;
    protected AmountType totalInvoicesAmount;
    protected AmountType totalOutstandingAmount;
    protected AmountType totalExecutableAmount;
    protected CurrencyCodeType invoiceCurrencyCode;
    
    // Persistence
    protected int id;
    
	public String getBatchIdentifier() {
        return batchIdentifier;
    }
    
	public void setBatchIdentifier(String value) {
        this.batchIdentifier = value;
    }
    
	public long getInvoicesCount() {
        return invoicesCount;
    }
    
	public void setInvoicesCount(long value) {
        this.invoicesCount = value;
    }
    
	public AmountType getTotalInvoicesAmount() {
        return totalInvoicesAmount;
    }
    
	public void setTotalInvoicesAmount(AmountType value) {
        this.totalInvoicesAmount = value;
    }
    
	public AmountType getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }
    
	public void setTotalOutstandingAmount(AmountType value) {
        this.totalOutstandingAmount = value;
    }
    
	public AmountType getTotalExecutableAmount() {
        return totalExecutableAmount;
    }
    
	public void setTotalExecutableAmount(AmountType value) {
        this.totalExecutableAmount = value;
    }
    
	public CurrencyCodeType getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }
    
	public void setInvoiceCurrencyCode(CurrencyCodeType value) {
        this.invoiceCurrencyCode = value;
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}