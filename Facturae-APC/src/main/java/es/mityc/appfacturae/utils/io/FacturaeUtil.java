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
package es.mityc.appfacturae.utils.io;

import java.util.ArrayList;

import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.facturae.FacturaeVersion;

public class FacturaeUtil {

	public static ArrayList<ComboOption> getCountries(String s) {
		return Constants.COUNTRIES31;
	}

	public static int getSpainCode() {
		return Constants.SPAINCODE31;
	}

	public static FacturaeVersion getVersionOb(String s) {
		if (Constants.VERSION32.equals(s))
			return FacturaeVersion.FACTURAE_32;
		else if (Constants.VERSION321.equals(s))
			return FacturaeVersion.FACTURAE_321;
		else
			return null;
	}

	public static String getVersionStr(FacturaeVersion v) {
		if (v.equals(FacturaeVersion.FACTURAE_32))
			return Constants.VERSION32;
		else if (v.equals(FacturaeVersion.FACTURAE_321))
			return Constants.VERSION321;
		else
			return null;
	}

	public static String getVersionConst(String s) {
		if (Constants.VERSION32.equals(s))
			return Constants.FACTURAE32;
		else if (Constants.VERSION321.equals(s))
			return Constants.FACTURAE321;
		else
			return null;
	}

}
