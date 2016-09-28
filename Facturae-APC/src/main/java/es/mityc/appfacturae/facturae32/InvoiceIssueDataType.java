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
 
import javax.xml.datatype.XMLGregorianCalendar;

import es.mityc.appfacturae.facturae.CurrencyCodeType;
import es.mityc.appfacturae.facturae.ExchangeRateDetailsType;
import es.mityc.appfacturae.facturae.LanguageCodeType;
import es.mityc.appfacturae.facturae.PeriodDates;
import es.mityc.appfacturae.facturae.PlaceOfIssueType;


public class InvoiceIssueDataType implements es.mityc.appfacturae.facturae.InvoiceIssueDataType {

	protected XMLGregorianCalendar issueDate;
    protected XMLGregorianCalendar operationDate;
    protected PlaceOfIssueType placeOfIssue;
    protected PeriodDates invoicingPeriod;
    protected CurrencyCodeType invoiceCurrencyCode;
    protected ExchangeRateDetailsType exchangeRateDetails;
    protected CurrencyCodeType taxCurrencyCode;
    protected LanguageCodeType languageName;
    
	public XMLGregorianCalendar getIssueDate() {
        return issueDate;
    }
    
	public void setIssueDate(XMLGregorianCalendar value) {
        this.issueDate = value;
    }
    
	public XMLGregorianCalendar getOperationDate() {
        return operationDate;
    }
    
	public void setOperationDate(XMLGregorianCalendar value) {
        this.operationDate = value;
    }
    
	public PlaceOfIssueType getPlaceOfIssue() {
        return placeOfIssue;
    }
    
	public void setPlaceOfIssue(PlaceOfIssueType value) {
        this.placeOfIssue = value;
    }
    
	public PeriodDates getInvoicingPeriod() {
        return invoicingPeriod;
    }
    
	public void setInvoicingPeriod(PeriodDates value) {
        this.invoicingPeriod = value;
    }
    
	public CurrencyCodeType getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }
    
	public void setInvoiceCurrencyCode(CurrencyCodeType value) {
        this.invoiceCurrencyCode = value;
    }
    
	public ExchangeRateDetailsType getExchangeRateDetails() {
        return exchangeRateDetails;
    }
    
	public void setExchangeRateDetails(ExchangeRateDetailsType value) {
        this.exchangeRateDetails = value;
    }
    
	public CurrencyCodeType getTaxCurrencyCode() {
        return taxCurrencyCode;
    }
    
	public void setTaxCurrencyCode(CurrencyCodeType value) {
        this.taxCurrencyCode = value;
    }
    
	public LanguageCodeType getLanguageName() {
        return languageName;
    }
    
	public void setLanguageName(LanguageCodeType value) {
        this.languageName = value;
    }

}