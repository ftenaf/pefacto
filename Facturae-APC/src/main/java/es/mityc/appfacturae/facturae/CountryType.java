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
 
public enum CountryType {

    AFG,
    ALB,
    DZA,
    ASM,
    AND,
    AGO,
    AIA,
    ATG,
    ARG,
    ARM,
    ABW,
    AUS,
    AUT,
    AZE,
    BHS,
    BHR,
    BGD,
    BRB,
    BLR,
    BEL,
    BLZ,
    BEN,
    BMU,
    BTN,
    BOL,
    BIH,
    BWA,
    BRA,
    BRN,
    BGR,
    BFA,
    BDI,
    KHM,
    CMR,
    CAN,
    CPV,
    CYM,
    CAF,
    TCD,
    CHL,
    CHN,
    COD,
    COL,
    COM,
    COG,
    COK,
    CRI,
    CIV,
    HRV,
    CUB,
    CYP,
    CZE,
    DNK,
    DJI,
    DMA,
    DOM,
    ECU,
    EGY,
    SLV,
    GNQ,
    ERI,
    EST,
    ETH,
    FLK,
    FRO,
    FJI,
    FIN,
    FRA,
    GUF,
    PYF,
    GAB,
    GMB,
    GEO,
    GGY,
    DEU,
    GHA,
    GIB,
    GRC,
    GRL,
    GRD,
    GLP,
    GUM,
    GTM,
    GIN,
    GNB,
    GUY,
    HTI,
    HND,
    HKG,
    HUN,
    ISL,
    IND,
    IDN,
    IMN,
    IRN,
    IRQ,
    IRL,
    ISR,
    ITA,
    JAM,
    JEY,
    JPN,
    JOR,
    KAZ,
    KEN,
    KIR,
    PRK,
    KOR,
    KWT,
    KGZ,
    LAO,
    LVA,
    LBN,
    LSO,
    LBR,
    LBY,
    LIE,
    LTU,
    LUX,
    MAC,
    MKD,
    MDG,
    MWI,
    MYS,
    MDV,
    MLI,
    MLT,
    MHL,
    MTQ,
    MRT,
    MUS,
    MYT,
    MEX,
    FSM,
    MDA,
    MCO,
    MNE,
    MNG,
    MSR,
    MAR,
    MOZ,
    MMR,
    NAM,
    NRU,
    NPL,
    NLD,
    ANT,
    NCL,
    NZL,
    NIC,
    NER,
    NGA,
    NIU,
    NFK,
    MNP,
    NOR,
    OMN,
    PAK,
    PLW,
    PAN,
    PNG,
    PRY,
    PSE,
    PER,
    PHL,
    PCN,
    POL,
    PRT,
    PRI,
    QAT,
    REU,
    ROU,
    RUS,
    RWA,
    KNA,
    LCA,
    VCT,
    WSM,
    SMR,
    STP,
    SAU,
    SEN,
    SRB,
    SYC,
    SLE,
    SGP,
    SVK,
    SVN,
    SLB,
    SOM,
    ZAF,
    ESP,
    LKA,
    SHN,
    SPM,
    SDN,
    SUR,
    SJM,
    SWZ,
    SWE,
    CHE,
    SYR,
    TWN,
    TJK,
    TZA,
    THA,
    TGO,
    TKL,
    TON,
    TTO,
    TUN,
    TUR,
    TKM,
    TLS,
    TCA,
    TUV,
    UGA,
    UKR,
    ARE,
    GBR,
    USA,
    URY,
    UZB,
    VUT,
    VAT,
    VEN,
    VNM,
    VGB,
    VIR,
    WLF,
    ESH,
    YEM,
    ZAR,
    ZMB,
    ZWE;

    public String value() {
        return name();
    }

    public static CountryType fromValue(String v) {
        return valueOf(v);
    }

}
