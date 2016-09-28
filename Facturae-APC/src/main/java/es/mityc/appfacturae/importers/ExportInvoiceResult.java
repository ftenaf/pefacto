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
package es.mityc.appfacturae.importers;

/**
 * This class represents the result of an invoice exportation
 */
public class ExportInvoiceResult {

	public enum ExportInvoiceResultType {

		/**
		 * Correct
		 */
		CORRECT,
		
		/**
		 * Error
		 */
		ERROR
	}
	
	private ExportInvoiceResultType result;
	private String observations;
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ExportInvoiceResultType getResult() {
		return result;
	}

	public void setResult(ExportInvoiceResultType result) {
		this.result = result;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observation) {
		this.observations = observation;
	}
	
	@Override
	public String toString() {
		String value;
		if (result != null && result == ExportInvoiceResultType.CORRECT) {
			value = "Invoice exported succesfully: [" + id + "]";
		} else {
			value = "Error exporting invoice file: [" + id + "]";
			if (observations != null && !observations.equals("")) {
				value += " Observations: "+observations;
			}
		}
		return value;
	}

}
