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
package es.mityc.appfacturae.facturae;

public enum LanguageCodeType {

    /**
     * Arabe
     * 
     */
    AR("ar"),

    /**
     * Bielorruso
     * 
     */
    BE("be"),

    /**
     * Bulgaro
     * 
     */
    BG("bg"),

    /**
     * Catalan
     * 
     */
    CA("ca"),

    /**
     * Checo
     * 
     */
    CS("cs"),

    /**
     * Danes
     * 
     */
    DA("da"),

    /**
     * Aleman
     * 
     */
    DE("de"),

    /**
     * Griego moderno
     * 
     */
    EL("el"),

    /**
     * Ingles
     * 
     */
    EN("en"),

    /**
     * Espanol
     * 
     */
    ES("es"),

    /**
     * Estonio
     * 
     */
    ET("et"),

    /**
     * Vascuence
     * 
     */
    EU("eu"),

    /**
     * Finlandes
     * 
     */
    FI("fi"),

    /**
     * Frances
     * 
     */
    FR("fr"),

    /**
     * Gaelico de Irlanda
     * 
     */
    GA("ga"),

    /**
     * Gallego
     * 
     */
    GL("gl"),

    /**
     * Croata
     * 
     */
    HR("hr"),

    /**
     * Hungaro
     * 
     */
    HU("hu"),

    /**
     * Islandes
     * 
     */
    IS("is"),

    /**
     * Italiano
     * 
     */
    IT("it"),

    /**
     * Leton
     * 
     */
    LV("lv"),

    /**
     * Lituano
     * 
     */
    LT("lt"),

    /**
     * Macedonio
     * 
     */
    MK("mk"),

    /**
     * Maltes
     * 
     */
    MT("mt"),

    /**
     * Neerlandes
     * 
     */
    NL("nl"),

    /**
     * Noruego
     * 
     */
    NO("no"),

    /**
     * Polaco
     * 
     */
    PL("pl"),

    /**
     * Portugues
     * 
     */
    PT("pt"),

    /**
     * Rumano
     * 
     */
    RO("ro"),

    /**
     * Ruso
     * 
     */
    RU("ru"),

    /**
     * Eslovaco
     * 
     */
    SK("sk"),

    /**
     * Esloveno
     * 
     */
    SL("sl"),

    /**
     * Albanes
     * 
     */
    SQ("sq"),

    /**
     * Serbio
     * 
     */
    SR("sr"),

    /**
     * Sueco
     * 
     */
    SV("sv"),

    /**
     * Turco
     * 
     */
    TR("tr"),

    /**
     * Ucraniano
     * 
     */
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
