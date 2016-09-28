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
import es.mityc.appfacturae.facturae.ContactDetailsType;

public class AdministrativeCentreType implements es.mityc.appfacturae.facturae.AdministrativeCentreType {
    
	protected String centreCode;
    protected String roleTypeCode;
    protected String name;
    protected String firstSurname;
    protected String secondSurname;
    protected es.mityc.appfacturae.facturae.AddressType addressInSpain;
    protected es.mityc.appfacturae.facturae.OverseasAddressType overseasAddress;
    protected ContactDetailsType contactDetails;
    protected String physicalGLN;
    protected String logicalOperationalPoint;
    protected String centreDescription;
    
	// Persistence
    protected int id;
    protected Address generalAddress;
    protected String addressType;
    
    // Persistence
	
	public int getId() {
		return id;
	}

	// Persistence
	
	public void setId(int id) {
		this.id = id;
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
    
	public String getAddressType() {
		return addressType;
	}

	// Persistence
	
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	
	public String getCentreCode() {
        return centreCode;
    }
    
	public void setCentreCode(String value) {
        this.centreCode = value;
    }
    
	public String getRoleTypeCode() {
        return roleTypeCode;
    }
    
	public void setRoleTypeCode(String value) {
        this.roleTypeCode = value;
    }
    
	public String getName() {
        return name;
    }
    
	public void setName(String value) {
        this.name = value;
    }
    
	public String getFirstSurname() {
        return firstSurname;
    }
    
	public void setFirstSurname(String value) {
        this.firstSurname = value;
    }
    
	public String getSecondSurname() {
        return secondSurname;
    }
    
	public void setSecondSurname(String value) {
        this.secondSurname = value;
    }
    
	public es.mityc.appfacturae.facturae.AddressType getAddressInSpain() {
		return addressInSpain;
    }
    
	public void setAddressInSpain(es.mityc.appfacturae.facturae.AddressType value) {
        
		// Persistence
		setGeneralAddress(value);
		setAddressType("Spain");
		
		this.addressInSpain = value;
    }
    
	public es.mityc.appfacturae.facturae.OverseasAddressType getOverseasAddress() {
        return overseasAddress;
    }
    
	public void setOverseasAddress(es.mityc.appfacturae.facturae.OverseasAddressType value) {
        
		// Persistence
		setGeneralAddress(value);
		setAddressType("Overseas");
		
		this.overseasAddress = value;
    }
    
	public ContactDetailsType getContactDetails() {
        return contactDetails;
    }
    
	public void setContactDetails(ContactDetailsType value) {
        this.contactDetails = value;
    }
    
	public String getPhysicalGLN() {
        return physicalGLN;
    }
    
	public void setPhysicalGLN(String value) {
        this.physicalGLN = value;
    }
    
	public String getLogicalOperationalPoint() {
        return logicalOperationalPoint;
    }
    
	public void setLogicalOperationalPoint(String value) {
        this.logicalOperationalPoint = value;
    }
	
	public String getCentreDescription() {
		return centreDescription;
	}

	public void setCentreDescription(String centreDescription) {
		this.centreDescription = centreDescription;
	}
}