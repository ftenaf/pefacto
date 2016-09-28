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

public class LegalEntityType implements Entity {

    protected String corporateName;
    protected String tradeName;
    protected RegistrationDataType registrationData;
    protected AddressType addressInSpain;
    protected OverseasAddressType overseasAddress;
    protected ContactDetailsType contactDetails;

    // Persistence
    protected int id;
    protected Address generalAddress;
    protected String addressType;
    protected boolean face;

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String value) {
        this.corporateName = value;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String value) {
        this.tradeName = value;
    }

    public RegistrationDataType getRegistrationData() {
        return registrationData;
    }

    public void setRegistrationData(RegistrationDataType value) {
        this.registrationData = value;
    }

    public AddressType getAddressInSpain() {
        return addressInSpain;
    }

    public void setAddressInSpain(AddressType value) {

        // Persistence
        setGeneralAddress(value);
        setAddressType("Spain");

        this.addressInSpain = value;
    }

    public OverseasAddressType getOverseasAddress() {
        return overseasAddress;
    }

    public void setOverseasAddress(OverseasAddressType value) {

        // Persistence
        setGeneralAddress(value);
        setAddressType("Overseas");

        this.overseasAddress = value;
    }

    public ContactDetailsType getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetailsType value) {
        this.contactDetails = value;
    }

	// Persistence
    public Address getGeneralAddress() {
        return generalAddress;
    }

	// Persistence
    public void setGeneralAddress(Address generalAddress) {
        this.generalAddress = generalAddress;
    }

	// Persistence
    public int getId() {
        return id;
    }

	// Persistence
    public void setId(int id) {
        this.id = id;
    }

	// Persistence
    public String getAddressType() {
        return addressType;
    }

	// Persistence
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public boolean isFace() {
        return face;
    }

    public void setFace(boolean face) {
        this.face = face;
    }

}
