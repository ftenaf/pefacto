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
 
public enum ReasonDescriptionType {

    NUMERO_DE_LA_FACTURA("N\u00famero de la factura"),
    SERIE_DE_LA_FACTURA("Serie de la factura"),
    FECHA_EXPEDICION("Fecha expedici\u00f3n"),
    NOMBRE_Y_APELLIDOS_RAZON_SOCIAL_EMISOR("Nombre y apellidos/Raz\u00f3n Social-Emisor"),
    NOMBRE_Y_APELLIDOS_RAZON_SOCIAL_RECEPTOR("Nombre y apellidos/Raz\u00f3n Social-Receptor"),
    IDENTIFICACION_FISCAL_EMISOR_OBLIGADO("Identificaci\u00f3n fiscal Emisor/obligado"),
    IDENTIFICACION_FISCAL_RECEPTOR("Identificaci\u00f3n fiscal Receptor"),
    DOMICILIO_EMISOR_OBLIGADO("Domicilio Emisor/Obligado"),
    DOMICILIO_RECEPTOR("Domicilio Receptor"),
    DETALLE_OPERACION("Detalle Operaci\u00f3n"),
    PORCENTAJE_IMPOSITIVO_A_APLICAR("Porcentaje impositivo a aplicar"),
    CUOTA_TRIBUTARIA_A_APLICAR("Cuota tributaria a aplicar"),
    FECHA_PERIODO_A_APLICAR("Fecha/Periodo a aplicar"),
    CLASE_DE_FACTURA("Clase de factura"),
    LITERALES_LEGALES("Literales legales"),
    BASE_IMPONIBLE("Base imponible"),
    CALCULO_DE_CUOTAS_REPERCUTIDAS("C\u00e1lculo de cuotas repercutidas"),
    CALCULO_DE_CUOTAS_RETENIDAS("C\u00e1lculo de cuotas retenidas"),
    BASE_IMPONIBLE_MODIFICADA_POR_DEVOLUCION_DE_ENVASES_EMBALAJES("Base imponible modificada por devoluci\u00f3n de envases / embalajes"),
    BASE_IMPONIBLE_MODIFICADA_POR_DESCUENTOS_Y_BONIFICACIONES("Base imponible modificada por descuentos y bonificaciones"),
    BASE_IMPONIBLE_MODIFICADA_POR_RESOLUCION_FIRME_JUDICIAL_O_ADMINISTRATIVA("Base imponible modificada por resoluci\u00f3n firme, judicial o administrativa"),
    BASE_IMPONIBLE_MODIFICADA_CUOTAS_REPERCUTIDAS_NO_SATISFECHAS_AUTO_DE_DECLARACION_DE_CONCURSO("Base imponible modificada cuotas repercutidas no satisfechas. Auto de declaraci\u00f3n de concurso");
    
	private final String value;

    ReasonDescriptionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReasonDescriptionType fromValue(String v) {
        for (ReasonDescriptionType c: ReasonDescriptionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
