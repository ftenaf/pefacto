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

public class FileHeaderType {

	protected String schemaVersion;
    protected ModalityType modality;
    protected InvoiceIssuerTypeType invoiceIssuerType;
    protected ThirdPartyType thirdParty;
    protected BatchType batch;
    protected FactoringAssignmentDataType factoringAssignmentData;
    
	public String getSchemaVersion() {
        return schemaVersion;
    }
    
	public void setSchemaVersion(String value) {
        this.schemaVersion = value;
    }
    
	public ModalityType getModality() {
        return modality;
    }
    
	public void setModality(ModalityType value) {
        this.modality = value;
    }
    
	public InvoiceIssuerTypeType getInvoiceIssuerType() {
        return invoiceIssuerType;
    }
    
	public void setInvoiceIssuerType(InvoiceIssuerTypeType value) {
        this.invoiceIssuerType = value;
    }
    
	public ThirdPartyType getThirdParty() {
        return thirdParty;
    }
    
	public void setThirdParty(ThirdPartyType value) {
        this.thirdParty = value;
    }
    
	public BatchType getBatch() {
        return batch;
    }
    
	public void setBatch(BatchType value) {
        this.batch = value;
    }
    
	public FactoringAssignmentDataType getFactoringAssignmentData() {
        return factoringAssignmentData;
    }
    
	public void setFactoringAssignmentData(FactoringAssignmentDataType value) {
        this.factoringAssignmentData = value;
    }
}