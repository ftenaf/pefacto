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
package es.mityc.appfacturae.utils;

import es.mityc.appfacturae.facturae.AdministrativeCentreType;
import es.mityc.appfacturae.facturae.ContactDetailsType;
import es.mityc.appfacturae.utils.constants.Constants;

public class AdmCentreUtil {
	public static AdministrativeCentreType makeCentre(String[] result, int spainCode, String version){
		// If result[7] (Address) is empty, the action has been canceled by the user
		AdministrativeCentreType act = null;
		if (Constants.VERSION321.equals(version)) {
			act = new es.mityc.appfacturae.facturae321.AdministrativeCentreType();
		} else if (Constants.VERSION32.equals(version)) {
			act = new es.mityc.appfacturae.facturae32.AdministrativeCentreType();
		}
		if (!"".equals(result[7])) {
			if (!"".equals(result[0]))
				act.setCentreCode(result[0]);
			if (!"".equals(result[1]))
				act.setRoleTypeCode(result[1]);
			if (!"".equals(result[2]))
				act.setName(result[2]);
			if (!"".equals(result[3]))
				act.setFirstSurname(result[3]);
			if (!"".equals(result[4]))
				act.setSecondSurname(result[4]);
			if (!"".equals(result[5]))
				act.setPhysicalGLN(result[5]);
			if (!"".equals(result[6]))
				act.setLogicalOperationalPoint(result[6]);

			if (!"".equals(result[11])) {
				if (result[11].equals(String.valueOf(spainCode))) {
					es.mityc.appfacturae.facturae.AddressType addressSpain = null;
					addressSpain = new es.mityc.appfacturae.facturae32.AddressType();
					((es.mityc.appfacturae.facturae32.AddressType) addressSpain)
						.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
							.parseInt(result[11])]);
					addressSpain.setAddress(result[7]);
					if (!"".equals(result[8]))
						addressSpain.setPostCode(result[8]);
					if (!"".equals(result[9]))
						addressSpain.setTown(result[9]);
					if (!"".equals(result[10]))
						addressSpain.setProvince(result[10]);
					act.setAddressInSpain(addressSpain);
				} else {
					es.mityc.appfacturae.facturae.OverseasAddressType overseas = null;
					overseas = new es.mityc.appfacturae.facturae32.OverseasAddressType();
					((es.mityc.appfacturae.facturae32.OverseasAddressType) overseas)
						.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
							.parseInt(result[11])]);
					overseas.setAddress(result[7]);
					if (!"".equals(result[8]) && !"".equals(result[9])) {
						overseas.setPostCodeAndTown(result[8] + " " + result[9]);
					} else {
						if (!"".equals(result[8])) {
							overseas.setPostCodeAndTown(result[8]);
						} else {
							if (!"".equals(result[9])) {
								overseas.setPostCodeAndTown(result[9]);
							}
						}
					}
					if (!"".equals(result[10]))
						overseas.setProvince(result[10]);
					act.setOverseasAddress(overseas);
				}
			}

			ContactDetailsType cd = new ContactDetailsType();
			boolean cdexist = false;
			if (!"".equals(result[12])) {
				cd.setTelephone(result[12]);
				cdexist = true;
			}
			if (!"".equals(result[13])) {
				cd.setWebAddress(result[13]);
				cdexist = true;
			}
			if (!"".equals(result[14])) {
				cd.setContactPersons(result[14]);
				cdexist = true;
			}
			if (!"".equals(result[15])) {
				cd.setCnoCnae(result[15]);
				cdexist = true;
			}
			if (!"".equals(result[16])) {
				cd.setTeleFax(result[16]);
				cdexist = true;
			}
			if (!"".equals(result[17])) {
				cd.setElectronicMail(result[17]);
				cdexist = true;
			}
			if (!"".equals(result[18])) {
				cd.setIneTownCode(result[18]);
				cdexist = true;
			}
			if (!"".equals(result[19])) {
				cd.setAdditionalContactDetails(result[19]);
				cdexist = true;
			}
			if (cdexist)
				act.setContactDetails(cd);

			if (!"".equals(result[1]))
				act.setRoleTypeCode(result[1]);

			if ("3.2.1".equals(version)) {
				((es.mityc.appfacturae.facturae321.AdministrativeCentreType) act).setCentreDescription(result[20]);
			} else if ("3.2".equals(version)) {
				((es.mityc.appfacturae.facturae32.AdministrativeCentreType) act).setCentreDescription(result[20]);
			}
		}
		return act;
	}
}
