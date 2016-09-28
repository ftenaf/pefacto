/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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
package es.mityc.appfacturae.facturae32;

import es.mityc.appfacturae.facturae.AttachedDocumentsType;
 
public class AdditionalDataType implements es.mityc.appfacturae.facturae.AdditionalDataType {

    protected String relatedInvoice;
    protected AttachedDocumentsType relatedDocuments;
    protected String invoiceAdditionalInformation;
    protected ExtensionsType extensions;
    
	public String getRelatedInvoice() {
        return relatedInvoice;
    }
    
	public void setRelatedInvoice(String value) {
        this.relatedInvoice = value;
    }
    
	public AttachedDocumentsType getRelatedDocuments() {
        return relatedDocuments;
    }
    
	public void setRelatedDocuments(AttachedDocumentsType value) {
        this.relatedDocuments = value;
    }
    
	public String getInvoiceAdditionalInformation() {
        return invoiceAdditionalInformation;
    }
    
	public void setInvoiceAdditionalInformation(String value) {
        this.invoiceAdditionalInformation = value;
    }
    
	public ExtensionsType getExtensions() {
        return extensions;
    }
	
	public void setExtensions(es.mityc.appfacturae.facturae.ExtensionsType value) {
        this.extensions = (ExtensionsType) value;
    }
}