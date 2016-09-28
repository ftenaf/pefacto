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

public interface AccountType{

	// Persistence
    
	public String getAddressType();

	// Persistence
	
	public void setAddressType(String addressType);

	public String getIban();
    
	public void setIban(String value);
    
	public String getBankCode();
    
	public void setBankCode(String value);
    
	public String getBranchCode();
    
	public void setBranchCode(String value);
    
	public AddressType getBranchInSpainAddress();
    
	public void setBranchInSpainAddress(AddressType value);
    
	public OverseasAddressType getOverseasBranchAddress();
    
	public void setOverseasBranchAddress(OverseasAddressType value);
	
	// Persistence
	
	public Address getGeneralAddress();

	// Persistence
	
	public void setGeneralAddress(Address generalAddress);

	// Persistence
	
	public int getId();

	// Persistence
	
	public void setId(int id);
	
}