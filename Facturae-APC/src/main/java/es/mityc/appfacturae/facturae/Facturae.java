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

import es.mityc.appfacturae.facturae.signature.SignatureType;
import es.mityc.appfacturae.hibernate.auxClass.xmlData;
public class Facturae {

	protected FileHeaderType fileHeader;
    protected PartiesType parties;
    protected InvoicesType invoices;
    protected ExtensionsType extensions;
    protected SignatureType signature;
    
    // Persistence
    protected xmlData xsig;
    protected int id;
    
    // Persistence
	public xmlData getXsig() {
		return xsig;
	}
	
	// Persistence
	public void setXsig(xmlData xsig) {
		this.xsig = xsig;
	}
	
	// Persistence
	public int getId() {
		return id;
	}

	// Persistence
	public void setId(int id) {
		this.id = id;
	}

	public FileHeaderType getFileHeader() {
        return fileHeader;
    }
    
	public void setFileHeader(FileHeaderType value) {
        this.fileHeader = value;
    }
    
	public PartiesType getParties() {
        return parties;
    }
    
	public void setParties(PartiesType value) {
        this.parties = value;
    }
    
	public InvoicesType getInvoices() {
        return invoices;
    }
    
	public void setInvoices(InvoicesType value) {
		this.invoices = value;
    }
    
	public ExtensionsType getExtensions() {
        return extensions;
    }
    
	public void setExtensions(ExtensionsType value) {
        this.extensions = value;
    }
    
	public SignatureType getSignature() {
        return signature;
    }
    
	public void setSignature(SignatureType value) {
        this.signature = value;
    }

}