/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae;

/**
 * This exception is thrown when a generic error happens with the API. 
 *
 */
public class FacturaeException extends Exception {

	private static final long serialVersionUID = -6707603415417704807L;

	/**
	 * Constructs a <code>FacturaeException</code> with no detail message.
	 */
	public FacturaeException() {
		super();
	}

	/**
	 * Constructs a <code>FacturaeException</code> with the specified detail message.
	 * @param message the detail message.
	 */
	public FacturaeException(String message) {
		super(message);
	}

	/**
	 * Creates a <code>FacturaeException</code> with the specified detail message and cause.
	 * @param cause the cause of the exception.
	 */
	public FacturaeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a <code>FacturaeException</code> with the specified detail message and cause.
	 * @param message the detail message.
	 * @param cause the cause of the exception.
	 */
	public FacturaeException(String message, Throwable cause) {
		super(message, cause);
	}
}
