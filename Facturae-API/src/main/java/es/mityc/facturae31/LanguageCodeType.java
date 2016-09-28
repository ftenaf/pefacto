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
 * <p>Java class for LanguageCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LanguageCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ar"/>
 *     &lt;enumeration value="be"/>
 *     &lt;enumeration value="bg"/>
 *     &lt;enumeration value="ca"/>
 *     &lt;enumeration value="cs"/>
 *     &lt;enumeration value="da"/>
 *     &lt;enumeration value="de"/>
 *     &lt;enumeration value="el"/>
 *     &lt;enumeration value="en"/>
 *     &lt;enumeration value="es"/>
 *     &lt;enumeration value="et"/>
 *     &lt;enumeration value="eu"/>
 *     &lt;enumeration value="fi"/>
 *     &lt;enumeration value="fr"/>
 *     &lt;enumeration value="ga"/>
 *     &lt;enumeration value="gl"/>
 *     &lt;enumeration value="hr"/>
 *     &lt;enumeration value="hu"/>
 *     &lt;enumeration value="is"/>
 *     &lt;enumeration value="it"/>
 *     &lt;enumeration value="lv"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="mk"/>
 *     &lt;enumeration value="mt"/>
 *     &lt;enumeration value="nl"/>
 *     &lt;enumeration value="no"/>
 *     &lt;enumeration value="pl"/>
 *     &lt;enumeration value="pt"/>
 *     &lt;enumeration value="ro"/>
 *     &lt;enumeration value="ru"/>
 *     &lt;enumeration value="sk"/>
 *     &lt;enumeration value="sl"/>
 *     &lt;enumeration value="sq"/>
 *     &lt;enumeration value="sr"/>
 *     &lt;enumeration value="sv"/>
 *     &lt;enumeration value="tr"/>
 *     &lt;enumeration value="uk"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LanguageCodeType")
@XmlEnum
public enum LanguageCodeType {


    /**
     * Arabe
     * 
     */
    @XmlEnumValue("ar")
    AR("ar"),

    /**
     * Bielorruso
     * 
     */
    @XmlEnumValue("be")
    BE("be"),

    /**
     * Búlgaro
     * 
     */
    @XmlEnumValue("bg")
    BG("bg"),

    /**
     * Catalán
     * 
     */
    @XmlEnumValue("ca")
    CA("ca"),

    /**
     * Checo
     * 
     */
    @XmlEnumValue("cs")
    CS("cs"),

    /**
     * Danés
     * 
     */
    @XmlEnumValue("da")
    DA("da"),

    /**
     * Alemán
     * 
     */
    @XmlEnumValue("de")
    DE("de"),

    /**
     * Griego moderno
     * 
     */
    @XmlEnumValue("el")
    EL("el"),

    /**
     * Inglés
     * 
     */
    @XmlEnumValue("en")
    EN("en"),

    /**
     * Español
     * 
     */
    @XmlEnumValue("es")
    ES("es"),

    /**
     * Estonio
     * 
     */
    @XmlEnumValue("et")
    ET("et"),

    /**
     * Vascuence
     * 
     */
    @XmlEnumValue("eu")
    EU("eu"),

    /**
     * Finlandés
     * 
     */
    @XmlEnumValue("fi")
    FI("fi"),

    /**
     * Francés
     * 
     */
    @XmlEnumValue("fr")
    FR("fr"),

    /**
     * Gálico de Irlanda
     * 
     */
    @XmlEnumValue("ga")
    GA("ga"),

    /**
     * Gallego
     * 
     */
    @XmlEnumValue("gl")
    GL("gl"),

    /**
     * Croata
     * 
     */
    @XmlEnumValue("hr")
    HR("hr"),

    /**
     * Húngaro
     * 
     */
    @XmlEnumValue("hu")
    HU("hu"),

    /**
     * Islandés
     * 
     */
    @XmlEnumValue("is")
    IS("is"),

    /**
     * Italiano
     * 
     */
    @XmlEnumValue("it")
    IT("it"),

    /**
     * Letón
     * 
     */
    @XmlEnumValue("lv")
    LV("lv"),

    /**
     * Lituano
     * 
     */
    @XmlEnumValue("lt")
    LT("lt"),

    /**
     * Macedonio
     * 
     */
    @XmlEnumValue("mk")
    MK("mk"),

    /**
     * Maltés
     * 
     */
    @XmlEnumValue("mt")
    MT("mt"),

    /**
     * Neerlandés
     * 
     */
    @XmlEnumValue("nl")
    NL("nl"),

    /**
     * Noruego
     * 
     */
    @XmlEnumValue("no")
    NO("no"),

    /**
     * Polaco
     * 
     */
    @XmlEnumValue("pl")
    PL("pl"),

    /**
     * Portugués
     * 
     */
    @XmlEnumValue("pt")
    PT("pt"),

    /**
     * Rumano
     * 
     */
    @XmlEnumValue("ro")
    RO("ro"),

    /**
     * Ruso
     * 
     */
    @XmlEnumValue("ru")
    RU("ru"),

    /**
     * Eslovaco
     * 
     */
    @XmlEnumValue("sk")
    SK("sk"),

    /**
     * Esloveno
     * 
     */
    @XmlEnumValue("sl")
    SL("sl"),

    /**
     * Albanés
     * 
     */
    @XmlEnumValue("sq")
    SQ("sq"),

    /**
     * Serbio
     * 
     */
    @XmlEnumValue("sr")
    SR("sr"),

    /**
     * Sueco
     * 
     */
    @XmlEnumValue("sv")
    SV("sv"),

    /**
     * Turco
     * 
     */
    @XmlEnumValue("tr")
    TR("tr"),

    /**
     * Ucraniano
     * 
     */
    @XmlEnumValue("uk")
    UK("uk");
    private final String value;

    LanguageCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LanguageCodeType fromValue(String v) {
        for (LanguageCodeType c: LanguageCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
