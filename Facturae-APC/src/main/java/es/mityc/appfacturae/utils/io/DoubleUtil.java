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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.log4j.Logger;

import es.mityc.appfacturae.utils.constants.Constants;

public class DoubleUtil {

	public static BigDecimal roundWithPrecision(Double d, int decimals) {
		BigDecimal bd = new BigDecimal(d.toString()).setScale(decimals, BigDecimal.ROUND_HALF_UP);
		return bd;
	}

	public static Double roundWithPrecisionDouble(Double d, int decimals) {
		//	TODO DOUBLE
		//	Double db = round(d, decimals);
		//	//Comprobamos que la precision es menor que decimals
		//	if (numDecimals(db.toString()) > decimals) {
		//		db = roundWithPrecision(d, decimals).doubleValue();
		//	}
		Double db = roundWithPrecision(d, decimals).doubleValue();
		return db;
	}

	public static String roundWithPrecisionDoubleFormatted(Double d, int decimals) {
		//	TODO DOUBLE
		//	Double db = round(d, decimals);
		//	//Comprobamos que la precision es menor que decimals
		//	if (numDecimals(db.toString()) > decimals) {
		//		db = roundWithPrecision(d, decimals).doubleValue();
		//	}
		Double db = roundWithPrecisionDouble(d, decimals).doubleValue();
		return formatDecimal(db,decimals);
	}
	
	public static Double roundByVersion(Double d, int decimals, String version) {
		if (version.equalsIgnoreCase(Constants.VERSION321)) {
			return roundWithPrecisionDouble(d, decimals);
		} else {
			return round(d, decimals);
		}
	}

	/**
	 * Redondea con una determinada precisión y obtiene un string sin notación científica.
	 * @param d Double a formatear
	 * @param decimals Número de decimales
	 * @param version Versión de facturae
	 * @return Número formateado a String
	 */
	public static String roundByVersionFormatted(Double d, int decimals, String version) {
		Double doble = roundByVersion(d, decimals, version);
		return formatDecimal(doble,decimals);
	}

	
	public static String format(Double d) {
		return formatDecimal(d,-1);
	}
	
	/**
	 * Obtiene un string sin notación científica.
	 * @param d Double a formatear
	 * @return Número formateado a String
	 */
	public static String formatDecimal(Double d,int decimals) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');
		
		if ( decimals == 0 )
			return String.format("%d",d);
		else if ( decimals > 0 )
		{
			String strCadenaDecimales="0.";
			for ( int i = 0; i < decimals; i++)
			{
				strCadenaDecimales += "#";
			}
		
			DecimalFormat format = new DecimalFormat(strCadenaDecimales,otherSymbols);
			return format.format(d);
		}
		else
			return String.format("%s",d);
	}
	
	
	public static Double getDoubleFromPrettyNumber(String prettyNumber)
	{
		return new Double(String.format("%s",prettyNumber));
		
	}
	private static Logger logger = Logger.getLogger(DoubleUtil.class);
	
	public static Double round(Double d, int decimals) {
		logger.debug("d=" + d + " decimals=" + decimals);
		BigDecimal bd = new BigDecimal(d.toString());
		int l = new String(d.intValue() + "").length();
		if (d.intValue() == 0) {
			l = decimals;
		} else {
			l += decimals;
		}
		logger.debug("bd=" + bd + " l=" +l);
		Double result = round(bd, l).doubleValue();
		if(decimals == 2) {
			result = redondeo(result);
		}
		return result;
	}

	public static BigDecimal round(BigDecimal d, int precision) {
		MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
		BigDecimal bd = d.round(mc);
		logger.debug("bd = " + bd);
		return bd;
	}

	private static int numDecimals(String numberWithDecimals) {
		int dotIndex = numberWithDecimals.indexOf('.');
		int decimals = numberWithDecimals.length() - dotIndex - 1;
		return decimals;
	}

	/*
	 * Redondea un importe dado a 2 decimales
	 */
	public static double redondeo(double numero) {
		BigDecimal bd = new BigDecimal(String.valueOf(numero));
		long numeroFinal = Math.round(bd.movePointRight(2).doubleValue());
		return (double) numeroFinal / (100);
	}

}
