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
package es.mityc.facturae321;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttachmentFormatType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AttachmentFormatType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="xml"/>
 *     &lt;enumeration value="doc"/>
 *     &lt;enumeration value="gif"/>
 *     &lt;enumeration value="rtf"/>
 *     &lt;enumeration value="pdf"/>
 *     &lt;enumeration value="xls"/>
 *     &lt;enumeration value="jpg"/>
 *     &lt;enumeration value="bmp"/>
 *     &lt;enumeration value="tiff"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AttachmentFormatType")
@XmlEnum
public enum AttachmentFormatType {

    @XmlEnumValue("xml")
    XML("xml"),
    @XmlEnumValue("doc")
    DOC("doc"),
    @XmlEnumValue("gif")
    GIF("gif"),
    @XmlEnumValue("rtf")
    RTF("rtf"),
    @XmlEnumValue("pdf")
    PDF("pdf"),
    @XmlEnumValue("xls")
    XLS("xls"),
    @XmlEnumValue("jpg")
    JPG("jpg"),
    @XmlEnumValue("bmp")
    BMP("bmp"),
    @XmlEnumValue("tiff")
    TIFF("tiff");
    private final String value;

    AttachmentFormatType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttachmentFormatType fromValue(String v) {
        for (AttachmentFormatType c: AttachmentFormatType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
