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

public class ContactDetailsType {

	protected String telephone;
    protected String teleFax;
    protected String webAddress;
    protected String electronicMail;
    protected String contactPersons;
    protected String cnoCnae;
    protected String ineTownCode;
    protected String additionalContactDetails;
    
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

	public String getTelephone() {
        return telephone;
    }
    
	public void setTelephone(String value) {
        this.telephone = value;
    }
    
	public String getTeleFax() {
        return teleFax;
    }
    
	public void setTeleFax(String value) {
        this.teleFax = value;
    }
    
	public String getWebAddress() {
        return webAddress;
    }
    
	public void setWebAddress(String value) {
        this.webAddress = value;
    }
    
	public String getElectronicMail() {
        return electronicMail;
    }
    
	public void setElectronicMail(String value) {
        this.electronicMail = value;
    }
    
	public String getContactPersons() {
        return contactPersons;
    }
    
	public void setContactPersons(String value) {
        this.contactPersons = value;
    }
    
	public String getCnoCnae() {
        return cnoCnae;
    }
    
	public void setCnoCnae(String value) {
        this.cnoCnae = value;
    }
    
	public String getIneTownCode() {
        return ineTownCode;
    }
    
	public void setIneTownCode(String value) {
        this.ineTownCode = value;
    }
    
	public String getAdditionalContactDetails() {
        return additionalContactDetails;
    }
    
	public void setAdditionalContactDetails(String value) {
        this.additionalContactDetails = value;
    }

}