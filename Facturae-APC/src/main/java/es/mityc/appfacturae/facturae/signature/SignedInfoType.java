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
package es.mityc.appfacturae.facturae.signature;
 
import java.util.ArrayList;
import java.util.List;

public class SignedInfoType {
    
	protected CanonicalizationMethodType canonicalizationMethod;
    protected SignatureMethodType signatureMethod;
    protected List<ReferenceType> reference;
    protected String id;
    
	public CanonicalizationMethodType getCanonicalizationMethod() {
        return canonicalizationMethod;
    }
    
	public void setCanonicalizationMethod(CanonicalizationMethodType value) {
        this.canonicalizationMethod = value;
    }
    
	public SignatureMethodType getSignatureMethod() {
        return signatureMethod;
    }
    
	public void setSignatureMethod(SignatureMethodType value) {
        this.signatureMethod = value;
    }
    
	 public List<ReferenceType> getReference() {
	        if (reference == null) {
	            reference = new ArrayList<ReferenceType>();
	        }
	        return this.reference;
	    }
    
	public String getId() {
        return id;
    }
    
	public void setId(String value) {
        this.id = value;
    }

}