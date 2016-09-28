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

import es.mityc.appfacturae.facturae.Address;

public class AccountType implements es.mityc.appfacturae.facturae.AccountType{

	protected String iban;
    protected String bankCode;
    protected String branchCode;
    protected es.mityc.appfacturae.facturae.AddressType branchInSpainAddress;
    protected es.mityc.appfacturae.facturae.OverseasAddressType overseasBranchAddress;
    protected String accountNumber;
    protected String bic;
    
    // Persistence
    protected int id;
    protected Address generalAddress;
    protected String addressType;
    
    // Persistence
    
	public String getAddressType() {
		return addressType;
	}

	// Persistence
	
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getIban() {
        return iban;
    }
    
	public void setIban(String value) {
        this.iban = value;
    }
    
	public String getBankCode() {
        return bankCode;
    }
    
	public void setBankCode(String value) {
        this.bankCode = value;
    }
    
	public String getBranchCode() {
        return branchCode;
    }
    
	public void setBranchCode(String value) {
        this.branchCode = value;
    }
    
	public es.mityc.appfacturae.facturae.AddressType getBranchInSpainAddress() {
        return branchInSpainAddress;
    }
    
	public void setBranchInSpainAddress(es.mityc.appfacturae.facturae.AddressType value) {
        
		//Persistence
		setGeneralAddress (value);
		setAddressType ("Spain");
		
		this.branchInSpainAddress = value;
    }
    
	public es.mityc.appfacturae.facturae.OverseasAddressType getOverseasBranchAddress() {
        return overseasBranchAddress;
    }
    
	public void setOverseasBranchAddress(es.mityc.appfacturae.facturae.OverseasAddressType value) {
        
		//Persistence
		setGeneralAddress (value);
		setAddressType ("Overseas");
		
		this.overseasBranchAddress = value;
    }
	
	// Persistence
	
	public Address getGeneralAddress() {
		return generalAddress;
	}

	// Persistence
	
	public void setGeneralAddress(Address generalAddress) {
		this.generalAddress = generalAddress;
	}

	// Persistence
	
	public int getId() {
		return id;
	}

	// Persistence
	
	public void setId(int id) {
		this.id = id;
	}
    
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

}