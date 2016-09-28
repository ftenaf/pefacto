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

public class SignatureType {
    
	protected SignedInfoType signedInfo;
    protected SignatureValueType signatureValue;
    protected KeyInfoType keyInfo;
    protected List<ObjectType> object;
    protected String id;
    
	public SignedInfoType getSignedInfo() {
        return signedInfo;
    }
    
	public void setSignedInfo(SignedInfoType value) {
        this.signedInfo = value;
    }
    
	public SignatureValueType getSignatureValue() {
        return signatureValue;
    }
    
	public void setSignatureValue(SignatureValueType value) {
        this.signatureValue = value;
    }
    
	public KeyInfoType getKeyInfo() {
        return keyInfo;
    }
    
	public void setKeyInfo(KeyInfoType value) {
        this.keyInfo = value;
    }
    
	public List<ObjectType> getObject() {
        if (object == null) {
            object = new ArrayList<ObjectType>();
        }
        return this.object;
    }
    
	public String getId() {
        return id;
    }
    
	public void setId(String value) {
        this.id = value;
    }

}
