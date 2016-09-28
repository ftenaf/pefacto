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
package es.mityc.appfacturae.facturae32;

import javax.xml.datatype.XMLGregorianCalendar;

public class InstallmentType implements es.mityc.appfacturae.facturae.InstallmentType{

	protected XMLGregorianCalendar installmentDueDate;
    protected double installmentAmount;
    protected String paymentMeans;
    protected es.mityc.appfacturae.facturae.AccountType accountToBeCredited;
    protected String paymentReconciliationReference;
    protected es.mityc.appfacturae.facturae.AccountType accountToBeDebited;
    protected String collectionAdditionalInformation;
    protected String regulatoryReportingData;
    protected String debitReconciliationReference;
//    protected es.mityc.appfacturae.facturae.AccountType accountToBeFactoring;

    // Persistence
    protected int id;
    
    // Persistence
    
	public int getId() {
		return id;
	}

	// Persistence
	
	public void setId(int id) {
		this.id = id;
	}

	public XMLGregorianCalendar getInstallmentDueDate() {
        return installmentDueDate;
    }
    
	public void setInstallmentDueDate(XMLGregorianCalendar value) {
        this.installmentDueDate = value;
    }
    
	public double getInstallmentAmount() {
        return installmentAmount;
    }
    
	public void setInstallmentAmount(double value) {
        this.installmentAmount = value;
    }
    
	public String getPaymentMeans() {
        return paymentMeans;
    }
    
	public void setPaymentMeans(String value) {
        this.paymentMeans = value;
    }
    
	public es.mityc.appfacturae.facturae.AccountType getAccountToBeCredited() {
        return accountToBeCredited;
    }
    
	public void setAccountToBeCredited(es.mityc.appfacturae.facturae.AccountType value) {
        this.accountToBeCredited = value;
    }
    
	public String getPaymentReconciliationReference() {
        return paymentReconciliationReference;
    }
    
	public void setPaymentReconciliationReference(String value) {
        this.paymentReconciliationReference = value;
    }
    
	public es.mityc.appfacturae.facturae.AccountType getAccountToBeDebited() {
        return accountToBeDebited;
    }
    
	public void setAccountToBeDebited(es.mityc.appfacturae.facturae.AccountType value) {
        this.accountToBeDebited = value;
    }
    
	public String getCollectionAdditionalInformation() {
        return collectionAdditionalInformation;
    }
    
	public void setCollectionAdditionalInformation(String value) {
        this.collectionAdditionalInformation = value;
    }

	public String getRegulatoryReportingData() {
		return regulatoryReportingData;
	}

	public void setRegulatoryReportingData(String regulatoryReportingData) {
		this.regulatoryReportingData = regulatoryReportingData;
	}

	public String getDebitReconciliationReference() {
		return debitReconciliationReference;
	}

	public void setDebitReconciliationReference(String debitReconciliationReference) {
		this.debitReconciliationReference = debitReconciliationReference;
	}

//    @Override
//    public void setAccountToBeFactoring(es.mityc.appfacturae.facturae.AccountType accountToBeFactoring) {
//		this.accountToBeFactoring = accountToBeFactoring;
//    }
//	
//        
//	public es.mityc.appfacturae.facturae.AccountType getAccountToBeFactoring() {
//		return accountToBeFactoring;
//	}

}