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
 
public class ReferenceType {
    
	protected TransformsType transforms;
    protected DigestMethodType digestMethod;
    protected byte[] digestValue;
    protected String id;
    protected String uri;
    protected String type;
    
	public TransformsType getTransforms() {
        return transforms;
    }
    
	public void setTransforms(TransformsType value) {
        this.transforms = value;
    }
    
	public DigestMethodType getDigestMethod() {
        return digestMethod;
    }
    
	public void setDigestMethod(DigestMethodType value) {
        this.digestMethod = value;
    }
    
	public byte[] getDigestValue() {
        return digestValue;
    }
    
	public void setDigestValue(byte[] value) {
        this.digestValue = ((byte[]) value);
    }
    
	public String getId() {
        return id;
    }
    
	public void setId(String value) {
        this.id = value;
    }
    
	public String getURI() {
        return uri;
    }
    
	public void setURI(String value) {
        this.uri = value;
    }
    
	public String getType() {
        return type;
    }
    
	public void setType(String value) {
        this.type = value;
    }

}