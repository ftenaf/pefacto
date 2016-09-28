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
package es.mityc.appfacturae.ui.components;

import es.mityc.appfacturae.facturae.BusinessType;

public class IndividualRow {
	
	public Object[] attributes;
	
	public IndividualRow(Object[] attributes) {
		super();
		this.attributes = attributes;
	}
	// PARTY_ID,NAME,FIRST_SURNAME,SECOND_SURNAME,TYPE_VERSION,ADDRESS.ADDRESS,POST_CODE,TOWN,PROVINCE,COUNTRY,TAX_IDENTIFICATION,RESIDENCE_TYPE,CONTACT
	public IndividualRow(BusinessType b, String version) {
		attributes = new Object[13];
		attributes[0] = b.getId();
		attributes[1] = b.getIndividual().getName();
		attributes[2] = b.getIndividual().getFirstSurname();
		attributes[3] = b.getIndividual().getSecondSurname();
		if(b.getIndividual().getAddressInSpain() != null) {
			attributes[4] = "Spain-" + version;
			attributes[5] = b.getIndividual().getAddressInSpain().getAddress();
			attributes[6] = b.getIndividual().getAddressInSpain().getPostCode();
			attributes[7] = b.getIndividual().getAddressInSpain().getTown();
			attributes[8] = b.getIndividual().getAddressInSpain().getProvince();
			attributes[9] = ((es.mityc.appfacturae.facturae32.AddressType)b.getIndividual().getAddressInSpain()).getCountryCode().ordinal();
		} else {
			attributes[4] = "Overseas-" + version;
			int p = b.getIndividual().getOverseasAddress().getPostCodeAndTown().indexOf(" ");
			attributes[5] = b.getIndividual().getOverseasAddress().getAddress();
			attributes[6] = b.getIndividual().getOverseasAddress().getPostCodeAndTown().substring(0, p);
			attributes[7] = b.getIndividual().getOverseasAddress().getPostCodeAndTown().substring(p + 1);
			attributes[8] = b.getIndividual().getOverseasAddress().getProvince();
			attributes[9] = ((es.mityc.appfacturae.facturae32.AddressType)b.getIndividual().getOverseasAddress()).getCountryCode().ordinal();
		}
		attributes[10] = b.getTaxIdentification().getTaxIdentificationNumber();
		attributes[11] = b.getTaxIdentification().getResidenceTypeCode();
		attributes[12] = b.getIndividual().getContactDetails();
	}

	public String toString(){
		String itemName = attributes[2].toString();
		if (attributes[3] != null)
			itemName = itemName + " " + attributes[3].toString();
		itemName = itemName + ", " + attributes[1].toString() + " ("+attributes[0].toString()+")";
		return itemName;
	}
}
