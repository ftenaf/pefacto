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
package es.mityc.appfacturae.facturae;

public class AttachmentType {

	protected AttachmentCompressionAlgorithmType attachmentCompressionAlgorithm;
    protected AttachmentFormatType attachmentFormat;
    protected AttachmentEncodingType attachmentEncoding;
    protected String attachmentDescription;
    protected String attachmentData;
    
	public AttachmentCompressionAlgorithmType getAttachmentCompressionAlgorithm() {
        return attachmentCompressionAlgorithm;
    }
    
	public void setAttachmentCompressionAlgorithm(AttachmentCompressionAlgorithmType value) {
        this.attachmentCompressionAlgorithm = value;
    }
    
	public AttachmentFormatType getAttachmentFormat() {
        return attachmentFormat;
    }
    
	public void setAttachmentFormat(AttachmentFormatType value) {
        this.attachmentFormat = value;
    }
    
	public AttachmentEncodingType getAttachmentEncoding() {
        return attachmentEncoding;
    }
    
	public void setAttachmentEncoding(AttachmentEncodingType value) {
        this.attachmentEncoding = value;
    }
    
	public String getAttachmentDescription() {
        return attachmentDescription;
    }
    
	public void setAttachmentDescription(String value) {
        this.attachmentDescription = value;
    }
    
	public String getAttachmentData() {
        return attachmentData;
    }
    
	public void setAttachmentData(String value) {
        this.attachmentData = value;
    }

}