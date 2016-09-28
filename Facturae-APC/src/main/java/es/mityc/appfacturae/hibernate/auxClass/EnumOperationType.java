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
package es.mityc.appfacturae.hibernate.auxClass;


public enum EnumOperationType {

	AppStart,
	AppEnd,
	SaveDraft,
	SignIn,
	RectIn,
	EditIn,
	SeeIn,
	NewParty,
	ReceiveIn,
	GenIVA,
	ImpBBDD,
	ExpBBDD,
	ConfChange,
	SendIn,
	ResendIn,
	SeeRect,
	SendFace;

    public String value() {
        return name();
    }

    public static Operation getOperation(EnumOperationType op) {

    	Operation o = new Operation();

    	switch (op.ordinal()) {

    	case 0: /** 01 - Aplication start */
    		o.setCode("01");
    		o.setShortName("AppStart");
    		o.setDescription("HistDesc_AppStart"); // This is the multilanguage key. For to get real description, use: lang.getProperty(o.getDescription());
    		o.setInfo("HistInfo_AppStart"); // This is the multilanguage key

    		return o;

    	case 1: /** 02 - Aplication end */
    		o.setCode("02");
    		o.setShortName("AppEnd");
    		o.setDescription("HistDesc_AppEnd");
    		o.setInfo("HistInfo_AppEnd");

    		return o;

    	case 2: /** 03 - Save draft */
    		o.setCode("03");
    		o.setShortName("SaveDraft");
    		o.setDescription("HistDesc_SaveDraft");
    		o.setInfo("HistInfo_SaveDraft");

    		return o;

    	case 3: /** 04 - Sign invoice */
    		o.setCode("04");
    		o.setShortName("SignIn");
    		o.setDescription("HistDesc_SignIn");
    		o.setInfo("HistInfo_SignIn");

    		return o;

    	case 4: /** 05 - Rectify invoice */
    		o.setCode("05");
    		o.setShortName("RectIn");
    		o.setDescription("HistDesc_RectIn");
    		o.setInfo("HistInfo_RectIn");

    		return o;

    	case 5: /** 06 - Edit invoice */
    		o.setCode("06");
    		o.setShortName("EditIn");
    		o.setDescription("HistDesc_EditIn");
    		o.setInfo("HistInfo_EditIn");

    		return o;

    	case 6: /** 07 - See invoice */
    		o.setCode("07");
    		o.setShortName("SeeIn");
    		o.setDescription("HistDesc_SeeIn");
    		o.setInfo("HistInfo_SeeIn");

    		return o;

    	case 7: /** 08 - New party */
    		o.setCode("08");
    		o.setShortName("NewParty");
    		o.setDescription("HistDesc_NewParty");
    		o.setInfo("HistInfo_NewParty");

    		return o;

    	case 8: /** 09 - Receive invoice */
    		o.setCode("09");
    		o.setShortName("ReceiveIn");
    		o.setDescription("HistDesc_ReceiveIn");
    		o.setInfo("HistInfo_ReceiveIn");

    		return o;

    	case 9: /** 10 - Generate IVA book */
    		o.setCode("10");
    		o.setShortName("GenIVA");
    		o.setDescription("HistDesc_GenIVA");
    		o.setInfo("HistInfo_GenIVA");

    		return o;

    	case 10: /** 11 - Import BBDD */
    		o.setCode("11");
    		o.setShortName("ImpBBDD");
    		o.setDescription("HistDesc_ImpBBDD");
    		o.setInfo("HistInfo_ImpBBDD");

    		return o;

    	case 11: /** 12 - Export BBDD*/
    		o.setCode("12");
    		o.setShortName("ExpBBDD");
    		o.setDescription("HistDesc_ExpBBDD");
    		o.setInfo("HistInfo_ExpBBDD");

    		return o;

    	case 12: /** 13 - Configuration change */
    		o.setCode("13");
    		o.setShortName("ConfChange");
    		o.setDescription("HistDesc_ConfChange");
    		o.setInfo("HistInfo_ConfChange");

    		return o;

    	case 13: /** 14 - Send invoice */
    		o.setCode("14");
    		o.setShortName("SendIn");
    		o.setDescription("HistDesc_SendIn");
    		o.setInfo("HistInfo_SendIn");

    		return o;

    	case 14: /** 15 - Resend invoice*/
    		o.setCode("15");
    		o.setShortName("ResendIn");
    		o.setDescription("HistDesc_ResendIn");
    		o.setInfo("HistInfo_ResendIn");

    		return o;

    	case 15: /** 16 - See rectifications */
    		o.setCode("16");
    		o.setShortName("SeeRect");
    		o.setDescription("HistDesc_SeeRect");
    		o.setInfo("HistInfo_SeeRect");

    		return o;
    		
    	case 16: /** 17 - Send invoice to FACe */
    		o.setCode("17");
    		o.setShortName("SendFACe");
    		o.setDescription("HistDesc_SendFACe");
    		o.setInfo("HistInfo_SendFACe");

    		return o;
    		
    	default:
    		return null;
    	}
    }
}
