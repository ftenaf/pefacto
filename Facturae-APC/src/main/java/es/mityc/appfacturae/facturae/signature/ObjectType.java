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

public class ObjectType {

	protected List<Object> content;
    protected String id;
    protected String mimeType;
    protected String encoding;
    
	public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }
    
	public String getId() {
        return id;
    }
    
	public void setId(String value) {
        this.id = value;
    }
    
	public String getMimeType() {
        return mimeType;
    }
    
	public void setMimeType(String value) {
        this.mimeType = value;
    }
    
	public String getEncoding() {
        return encoding;
    }
    
	public void setEncoding(String value) {
        this.encoding = value;
    }

}