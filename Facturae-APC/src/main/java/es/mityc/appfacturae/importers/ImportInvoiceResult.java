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

import java.util.Collection;

/**
 * This class represents the result of an invoice importation (without attachments)
 */
public class ImportInvoiceResult {

	public enum ImportInvoiceResultType {

		/**
		 * Correct
		 */
		CORRECT,
		
		/**
		 * Error
		 */
		ERROR
	}
	
	private String oldId;
	private String newId;
	private ImportInvoiceResultType result;
	private Collection<ImportAttachmentResult> attachmentsResult;
	private String observations;
	
	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	public ImportInvoiceResultType getResult() {
		return result;
	}

	public void setResult(ImportInvoiceResultType result) {
		this.result = result;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observation) {
		this.observations = observation;
	}
	
	public Collection<ImportAttachmentResult> getAttachmentsResult() {
		return attachmentsResult;
	}

	public void setAttachmentsResult(
			Collection<ImportAttachmentResult> attachmentsResult) {
		this.attachmentsResult = attachmentsResult;
	}
	
	@Override
	public String toString() {
		String value;
		if (result != null && result == ImportInvoiceResultType.CORRECT) {
			value = "Invoice ["+oldId+"] imported succesfully as ["+newId+"].";
		} else {
			value = " Error importing invoice file ["+oldId+"].";
			if (observations != null && !observations.equals("")) {
				value += " Observations: "+observations;
			}
		}
		return value;
	}

}
