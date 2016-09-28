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

import javax.xml.datatype.XMLGregorianCalendar;

public interface InstallmentType {

	// Persistence
    
	public int getId();

	// Persistence
	
	public void setId(int id);

	public XMLGregorianCalendar getInstallmentDueDate();
    
	public void setInstallmentDueDate(XMLGregorianCalendar value);
    
	public double getInstallmentAmount();
    
	public void setInstallmentAmount(double value);
    
	public String getPaymentMeans();
    
	public void setPaymentMeans(String value);
    
	public AccountType getAccountToBeCredited();
    
	public void setAccountToBeCredited(AccountType value);
    
	public String getPaymentReconciliationReference();
    
	public void setPaymentReconciliationReference(String value);
    
	public AccountType getAccountToBeDebited();
    
	public void setAccountToBeDebited(AccountType value);
    
	public String getCollectionAdditionalInformation();
    
	public void setCollectionAdditionalInformation(String value);

//        public void setAccountToBeFactoring(AccountType accountToBeFactoring);
        
//	public AccountType getAccountToBeFactoring();

}