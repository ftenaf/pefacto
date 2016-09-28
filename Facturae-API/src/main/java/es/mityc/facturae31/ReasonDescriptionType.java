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
package es.mityc.facturae31;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReasonDescriptionType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReasonDescriptionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="N�mero de la factura"/>
 *     &lt;enumeration value="Serie de la factura"/>
 *     &lt;enumeration value="Fecha expedici�n"/>
 *     &lt;enumeration value="Nombre y apellidos/Raz�n Social-Emisor"/>
 *     &lt;enumeration value="Nombre y apellidos/Raz�n Social-Receptor"/>
 *     &lt;enumeration value="Identificaci�n fiscal Emisor/obligado"/>
 *     &lt;enumeration value="Identificaci�n fiscal Receptor"/>
 *     &lt;enumeration value="Domicilio Emisor/Obligado"/>
 *     &lt;enumeration value="Domicilio Receptor"/>
 *     &lt;enumeration value="Detalle Operaci�n"/>
 *     &lt;enumeration value="Porcentaje impositivo a aplicar"/>
 *     &lt;enumeration value="Cuota tributaria a aplicar"/>
 *     &lt;enumeration value="Fecha/Periodo a aplicar"/>
 *     &lt;enumeration value="Clase de factura"/>
 *     &lt;enumeration value="Literales legales"/>
 *     &lt;enumeration value="Base imponible"/>
 *     &lt;enumeration value="C�lculo de cuotas repercutidas"/>
 *     &lt;enumeration value="C�lculo de cuotas retenidas"/>
 *     &lt;enumeration value="Base imponible modificada por devoluci�n de envases / embalajes"/>
 *     &lt;enumeration value="Base imponible modificada por descuentos y bonificaciones"/>
 *     &lt;enumeration value="Base imponible modificada por resoluci�n firme, judicial o administrativa"/>
 *     &lt;enumeration value="Base imponible modificada cuotas repercutidas no satisfechas. Auto de declaraci�n de concurso"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReasonDescriptionType")
@XmlEnum
public enum ReasonDescriptionType {

    @XmlEnumValue("N\u00famero de la factura")
    NUMERO_DE_LA_FACTURA("N\u00famero de la factura"),
    @XmlEnumValue("Serie de la factura")
    SERIE_DE_LA_FACTURA("Serie de la factura"),
    @XmlEnumValue("Fecha expedici\u00f3n")
    FECHA_EXPEDICION("Fecha expedici\u00f3n"),
    @XmlEnumValue("Nombre y apellidos/Raz\u00f3n Social-Emisor")
    NOMBRE_Y_APELLIDOS_RAZON_SOCIAL_EMISOR("Nombre y apellidos/Raz\u00f3n Social-Emisor"),
    @XmlEnumValue("Nombre y apellidos/Raz\u00f3n Social-Receptor")
    NOMBRE_Y_APELLIDOS_RAZON_SOCIAL_RECEPTOR("Nombre y apellidos/Raz\u00f3n Social-Receptor"),
    @XmlEnumValue("Identificaci\u00f3n fiscal Emisor/obligado")
    IDENTIFICACION_FISCAL_EMISOR_OBLIGADO("Identificaci\u00f3n fiscal Emisor/obligado"),
    @XmlEnumValue("Identificaci\u00f3n fiscal Receptor")
    IDENTIFICACION_FISCAL_RECEPTOR("Identificaci\u00f3n fiscal Receptor"),
    @XmlEnumValue("Domicilio Emisor/Obligado")
    DOMICILIO_EMISOR_OBLIGADO("Domicilio Emisor/Obligado"),
    @XmlEnumValue("Domicilio Receptor")
    DOMICILIO_RECEPTOR("Domicilio Receptor"),
    @XmlEnumValue("Detalle Operaci\u00f3n")
    DETALLE_OPERACION("Detalle Operaci\u00f3n"),
    @XmlEnumValue("Porcentaje impositivo a aplicar")
    PORCENTAJE_IMPOSITIVO_A_APLICAR("Porcentaje impositivo a aplicar"),
    @XmlEnumValue("Cuota tributaria a aplicar")
    CUOTA_TRIBUTARIA_A_APLICAR("Cuota tributaria a aplicar"),
    @XmlEnumValue("Fecha/Periodo a aplicar")
    FECHA_PERIODO_A_APLICAR("Fecha/Periodo a aplicar"),
    @XmlEnumValue("Clase de factura")
    CLASE_DE_FACTURA("Clase de factura"),
    @XmlEnumValue("Literales legales")
    LITERALES_LEGALES("Literales legales"),
    @XmlEnumValue("Base imponible")
    BASE_IMPONIBLE("Base imponible"),
    @XmlEnumValue("C\u00e1lculo de cuotas repercutidas")
    CALCULO_DE_CUOTAS_REPERCUTIDAS("C\u00e1lculo de cuotas repercutidas"),
    @XmlEnumValue("C\u00e1lculo de cuotas retenidas")
    CALCULO_DE_CUOTAS_RETENIDAS("C\u00e1lculo de cuotas retenidas"),
    @XmlEnumValue("Base imponible modificada por devoluci\u00f3n de envases / embalajes")
    BASE_IMPONIBLE_MODIFICADA_POR_DEVOLUCION_DE_ENVASES_EMBALAJES("Base imponible modificada por devoluci\u00f3n de envases / embalajes"),
    @XmlEnumValue("Base imponible modificada por descuentos y bonificaciones")
    BASE_IMPONIBLE_MODIFICADA_POR_DESCUENTOS_Y_BONIFICACIONES("Base imponible modificada por descuentos y bonificaciones"),
    @XmlEnumValue("Base imponible modificada por resoluci\u00f3n firme, judicial o administrativa")
    BASE_IMPONIBLE_MODIFICADA_POR_RESOLUCION_FIRME_JUDICIAL_O_ADMINISTRATIVA("Base imponible modificada por resoluci\u00f3n firme, judicial o administrativa"),
    @XmlEnumValue("Base imponible modificada cuotas repercutidas no satisfechas. Auto de declaraci\u00f3n de concurso")
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
