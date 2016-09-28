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

import es.mityc.appfacturae.facturae.CountryType;
 
public class AddressType implements es.mityc.appfacturae.facturae.AddressType{
    
	protected String address;
    protected String postCode;
    protected String town;
    protected String province;
    protected CountryType countryCode;
    
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

	public String getAddress() {
        return address;
    }
    
	public void setAddress(String value) {
        this.address = value;
    }
    
	public String getPostCode() {
        return postCode;
    }
    
	public void setPostCode(String value) {
        this.postCode = value;
    }
    
	public String getTown() {
        return town;
    }
    
	public void setTown(String value) {
        this.town = value;
    }
    
	public String getProvince() {
        return province;
    }
    
	public void setProvince(String value) {
        this.province = value;
    }
    
	public CountryType getCountryCode() {
        return countryCode;
    }
    
	public void setCountryCode(CountryType value) {
        this.countryCode = value;
    }

}