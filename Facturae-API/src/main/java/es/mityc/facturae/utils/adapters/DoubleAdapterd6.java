/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae.utils.adapters;

import java.text.DecimalFormat;
import java.lang.Double;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adapts a Java type for custom marshaling. 
 * 
 * <p>Some Java types do not map naturally to a XML representation.</p>
 * <p>In this case, the double must have 6 decimals.</p> 
 */
public class DoubleAdapterd6 extends XmlAdapter<String,Double> {

	DecimalFormat df6 = new DecimalFormat("0.000000");
	
	/**
	 * Convert a bound type to a value type.
	 */
	@Override
	public String marshal(Double dbl) throws Exception {
		return df6.format(dbl).replace(',', '.');
	}
	/** 
	 * Convert a value type to a bound type
	 */
	@Override
	public Double unmarshal(String stg) throws Exception {
		return Double.parseDouble(stg);
	}
	

}
