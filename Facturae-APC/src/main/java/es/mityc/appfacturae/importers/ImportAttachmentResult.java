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

import java.io.File;

/**
 * This class represents the importation result of an attachment
 */
public class ImportAttachmentResult {

	public enum ImportAttachmentResultType {

		/**
		 * Correct
		 */
		CORRECT,

		/**
		 * Error
		 */
		ERROR
	}

	private File file;
	private String type;
	private ImportAttachmentResultType result;
	private String observations;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ImportAttachmentResultType getResult() {
		return result;
	}

	public void setResult(ImportAttachmentResultType result) {
		this.result = result;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
	
	public String toString() {
		String value;
		if (result != null && result == ImportAttachmentResultType.CORRECT) {
			value = "Attachment of type ["+type+"]: ["+file.getAbsolutePath()+"] imported succesfully.";
		} else {
			value = "Error importing attachment of type ["+type+"]: ["+file.getAbsolutePath()+"]. ";
			if (observations != null && !observations.equals("")) {
				value += "Observations: " + observations;
			}
		}
		return value;
	}

}
