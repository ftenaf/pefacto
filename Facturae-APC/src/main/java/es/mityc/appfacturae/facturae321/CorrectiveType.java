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
package es.mityc.appfacturae.facturae321;

import es.mityc.appfacturae.facturae.CorrectionMethodDescriptionType;
import es.mityc.appfacturae.facturae.PeriodDates;
import es.mityc.appfacturae.facturae32.ReasonDescriptionType;

public class CorrectiveType implements es.mityc.appfacturae.facturae.CorrectiveType{
    
	protected String invoiceNumber;
    protected String invoiceSeriesCode;
    protected String reasonCode;
    protected ReasonDescriptionType reasonDescription;
    protected PeriodDates taxPeriod;
    protected String correctionMethod;
    protected CorrectionMethodDescriptionType correctionMethodDescription;
    protected String additionalReasonDescription;
    
	public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
	public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }
    
	public String getInvoiceSeriesCode() {
        return invoiceSeriesCode;
    }
    
	public void setInvoiceSeriesCode(String value) {
        this.invoiceSeriesCode = value;
    }
    
	public String getReasonCode() {
        return reasonCode;
    }
    
	public void setReasonCode(String value) {
        this.reasonCode = value;
    }
    
	public ReasonDescriptionType getReasonDescription() {
        return reasonDescription;
    }
    
	public void setReasonDescription(ReasonDescriptionType value) {
        this.reasonDescription = value;
    }
    
	public PeriodDates getTaxPeriod() {
        return taxPeriod;
    }
    
	public void setTaxPeriod(PeriodDates value) {
        this.taxPeriod = value;
    }
    
	public String getCorrectionMethod() {
        return correctionMethod;
    }
    
	public void setCorrectionMethod(String value) {
        this.correctionMethod = value;
    }
    
	public CorrectionMethodDescriptionType getCorrectionMethodDescription() {
        return correctionMethodDescription;
    }
    
	public void setCorrectionMethodDescription(CorrectionMethodDescriptionType value) {
        this.correctionMethodDescription = value;
    }
	
	public String getReasonDescriptionValue() {
		return reasonDescription.value();
	}

	public String getAdditionalReasonDescription() {
		return additionalReasonDescription;
	}

	public void setAdditionalReasonDescription(String additionalReasonDescription) {
		this.additionalReasonDescription = additionalReasonDescription;
	}
}