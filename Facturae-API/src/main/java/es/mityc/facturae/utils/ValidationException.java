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
package es.mityc.facturae.utils;

/**
 * This exception is thrown when an error happens during XML validation procedure.
 */
public class ValidationException extends Exception {
	
	/**
	 * Constructs a <code>ValidationException</code> with no detail message.
	 */
	public ValidationException() {
	}
	
	/**
	 * Constructs a <code>ValidationException</code> with the specified detail message.
	 * @param s the detail message.
	 */
	public ValidationException(String s) {
		super(s);
	}

	/**
	 * Creates a <code>ValidationException</code> with the specified detail message and cause.
	 * @param throwable the cause of the exception.
	 */
	public ValidationException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Creates a <code>ValidationException</code> with the specified detail message and cause.
	 * @param s the detail message.
	 * @param throwable the cause of the exception.
	 */
	public ValidationException(String s, Throwable throwable) {
		super(s, throwable);
	}

}
