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
package es.mityc.appfacturae.exceptions;

public class DatabaseOperationException extends Exception {

	private static final long serialVersionUID = 1L;
	private String multiKey = "";
	
	public DatabaseOperationException() {
	}

	public DatabaseOperationException(String message) {
		super(message);
	}

	public DatabaseOperationException(Throwable cause) {
		super(cause);
	}

	public DatabaseOperationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DatabaseOperationException(String multiKey, String message) {
		super(message);
		this.multiKey = multiKey;
	}
	
	public DatabaseOperationException(String multiKey, String message, Throwable cause) {
		super(message, cause);
		this.multiKey = multiKey;
	}

	public String getMultiKey() {
		return multiKey;
	}

	public void setMultiKey(String multiKey) {
		this.multiKey = multiKey;
	}

}
