/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-APC".
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto sean aprobadas por
 * la Comisión Europea– versiones posteriores de la EUPL (la Licencia); Solo
 * podrá usarse esta obra si se respeta la Licencia.
 *
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Salvo cuando lo exija la legislación aplicable o se acuerde por escrito, el
 * programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL», SIN
 * GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas. Véase la
 * Licencia en el idioma concreto que rige los permisos y limitaciones que
 * establece la Licencia.
 */
package es.mityc.appfacturae.facturae;

public class BusinessType {

    protected TaxIdentificationType taxIdentification;
    protected String partyIdentification;
    protected AdministrativeCentresType administrativeCentres;
    protected LegalEntityType legalEntity;
    protected IndividualType individual;

    // Persistence
    protected int id;
    protected Entity generalEntity;
    protected boolean individualType;

    public TaxIdentificationType getTaxIdentification() {
        return taxIdentification;
    }

    public void setTaxIdentification(TaxIdentificationType value) {
        this.taxIdentification = value;
    }

    public String getPartyIdentification() {
        return partyIdentification;
    }

    public void setPartyIdentification(String value) {
        this.partyIdentification = value;
    }

    public AdministrativeCentresType getAdministrativeCentres() {
        return administrativeCentres;
    }

    public void setAdministrativeCentres(AdministrativeCentresType value) {
        this.administrativeCentres = value;
    }

	// Persistence
    public Entity getGeneralEntity() {
        return generalEntity;
    }

	// Persistence
    public void setGeneralEntity(Entity generalEntity) {
        this.generalEntity = generalEntity;
    }

    public LegalEntityType getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(LegalEntityType value) {

        //Persistence
        setGeneralEntity(value);
        setIndividualType(false);

        this.legalEntity = value;
    }

    public IndividualType getIndividual() {
        return individual;
    }

    public void setIndividual(IndividualType value) {

        //Persistence
        setGeneralEntity(value);
        setIndividualType(true);

        this.individual = value;
    }

    public boolean isIndividualType() {
        return individualType;
    }

    public void setIndividualType(boolean individualType) {
        this.individualType = individualType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
