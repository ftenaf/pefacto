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

public class LegalEntityRow {

	public Object[] attributes;
	
	public LegalEntityRow(Object[] attributes) {
		super();
		this.attributes = attributes;
	}

	public LegalEntityRow(BusinessType b, String version) {
		attributes = new Object[14];
		attributes[0] = b.getId();
		attributes[1] = b.getLegalEntity().getCorporateName();
		attributes[2] = b.getLegalEntity().getTradeName();
		if(b.getLegalEntity().getAddressInSpain() != null) {
			attributes[3] = "Spain-" + version;
			attributes[4] = b.getLegalEntity().getAddressInSpain().getAddress();
			attributes[5] = b.getLegalEntity().getAddressInSpain().getPostCode();
			attributes[6] = b.getLegalEntity().getAddressInSpain().getTown();
			attributes[7] = b.getLegalEntity().getAddressInSpain().getProvince();
			attributes[8] = ((es.mityc.appfacturae.facturae32.AddressType)b.getLegalEntity().getAddressInSpain()).getCountryCode().ordinal();
		} else {
			int p = b.getLegalEntity().getOverseasAddress().getPostCodeAndTown().indexOf(" ");
			attributes[3] = "Overseas-" + version;
			attributes[4] = b.getLegalEntity().getOverseasAddress().getAddress();
			attributes[5] = b.getLegalEntity().getOverseasAddress().getPostCodeAndTown().substring(0, p);
			attributes[6] = b.getLegalEntity().getOverseasAddress().getPostCodeAndTown().substring(p + 1);
			attributes[7] = b.getLegalEntity().getOverseasAddress().getProvince();
			attributes[8] = ((es.mityc.appfacturae.facturae32.AddressType)b.getLegalEntity().getOverseasAddress()).getCountryCode().ordinal();
		}
		attributes[9] = b.getTaxIdentification().getTaxIdentificationNumber();
		attributes[10] = b.getTaxIdentification().getResidenceTypeCode();
		attributes[11] = b.getLegalEntity().getContactDetails();
		attributes[12] = b.getLegalEntity().getRegistrationData();
		attributes[13] = new Boolean(b.getLegalEntity().isFace());
	}

	public String toString(){
		return attributes[1].toString() + " ("+attributes[0].toString()+")";
	}
	
}