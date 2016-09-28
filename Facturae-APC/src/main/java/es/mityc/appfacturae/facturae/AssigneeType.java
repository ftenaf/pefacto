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

public class AssigneeType {
    
	protected TaxIdentificationType taxIdentification;
    protected LegalEntityType legalEntity;
    protected IndividualType individual;
    protected int assigneeNumber;
    
	public TaxIdentificationType getTaxIdentification() {
        return taxIdentification;
    }
    
	public void setTaxIdentification(TaxIdentificationType value) {
        this.taxIdentification = value;
    }
    
	public LegalEntityType getLegalEntity() {
        return legalEntity;
    }
    
	public void setLegalEntity(LegalEntityType value) {
        this.legalEntity = value;
    }
    
	public IndividualType getIndividual() {
        return individual;
    }
    
	public void setIndividual(IndividualType value) {
        this.individual = value;
    }

    public int getAssigneeNumber() {
        return assigneeNumber;
    }
    
    public void setAssigneeNumber(int assigneeNumber) {
        this.assigneeNumber = assigneeNumber;
    }
}