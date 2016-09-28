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

public class ReceiveException extends Exception {
	
	private String multiKey = "";
	
	public ReceiveException() {
	}

	public ReceiveException(String arg0) {
		super(arg0);
	}

	public ReceiveException(Throwable arg0) {
		super(arg0);
	}

	public ReceiveException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ReceiveException(String multiKey, String message) {
		super(message);
		this.multiKey = multiKey;
	}
	
	public ReceiveException(String multiKey, String message, Throwable cause) {
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
