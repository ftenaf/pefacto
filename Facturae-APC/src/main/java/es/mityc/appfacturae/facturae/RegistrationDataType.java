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

public class RegistrationDataType {
    
	protected String book;
    protected String registerOfCompaniesLocation;
    protected String sheet;
    protected String folio;
    protected String section;
    protected String volume;
    protected String additionalRegistrationData;
    
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

	public String getBook() {
        return book;
    }
    
	public void setBook(String value) {
        this.book = value;
    }
    
	public String getRegisterOfCompaniesLocation() {
        return registerOfCompaniesLocation;
    }
    
	public void setRegisterOfCompaniesLocation(String value) {
        this.registerOfCompaniesLocation = value;
    }
    
	public String getSheet() {
        return sheet;
    }
    
	public void setSheet(String value) {
        this.sheet = value;
    }
    
	public String getFolio() {
        return folio;
    }
    
	public void setFolio(String value) {
        this.folio = value;
    }
    
	public String getSection() {
        return section;
    }
    
	public void setSection(String value) {
        this.section = value;
    }
    
	public String getVolume() {
        return volume;
    }
    
	public void setVolume(String value) {
        this.volume = value;
    }
    
	public String getAdditionalRegistrationData() {
        return additionalRegistrationData;
    }
    
	public void setAdditionalRegistrationData(String value) {
        this.additionalRegistrationData = value;
    }

}