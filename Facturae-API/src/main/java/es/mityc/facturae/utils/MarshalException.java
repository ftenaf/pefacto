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

import es.mityc.facturae.FacturaeException;

/**
 * This exception is thrown when an error in the marshal process happens. 
 *
 */
public class MarshalException extends FacturaeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>MarshallerException</code> with no detail message.
	 */
	public MarshalException() {
		super();
	}

	/**
	 * Constructs a <code>MarshallerException</code> with the specified detail message.
	 * @param message the detail message.
	 */
	public MarshalException(String message) {
		super(message);
	}

	/**
	 * Creates a <code>MarshallerException</code> with the specified detail message and cause.
	 * @param cause the cause of the exception.
	 */
	public MarshalException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a <code>MarshallerException</code> with the specified detail message and cause.
	 * @param message the detail message.
	 * @param cause the cause of the exception.
	 */
	public MarshalException(String message, Throwable cause) {
		super(message, cause);
	}
}
