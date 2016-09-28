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
package es.mityc.appfacturae.utils.constants;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.ui.utils.GUIUtils;

public class Constants { 
	
		// Language resource
		public static ResourceBundle LANG = null;
		public static ResourceBundle COUNTRYLANG30 = null;
		public static ResourceBundle COUNTRYLANG31 = null;
		
		// SpainCode
		public static final int SPAINCODE30 = 194;
		public static final int SPAINCODE31 = 191;
		
		// OS
		public static final String OSNAME = System.getProperty("os.name");
		public static String DESKTOP_ENV = System.getenv("DESKTOP_SESSION");
		
		// Versions
		public static final String FACTURAE321 = "321";
		public static final String FACTURAE32 = "32";
		public static final String FACTURAE31 = "31";
		
		// Versions Str
		public static final String VERSION321 = "3.2.1";
		public static final String VERSION32 = "3.2";
		
		// Presentations
		public static final String AEAT_30 = "aeat30";
		public static final String AEAT_31 = "aeat31";
		public static final String AEAT_32 = "aeat32";
		public static final String AEAT_321 = "aeat321";
		
		// Properties
		public static Properties APP_PROP = null;
		public static Properties CONFIG_PROP = null;
		public static Properties SIGN_PROP = null;
		public static Properties TAXRATE_PROP = null;
		public static Properties LEGALLITERALS_PROP = null;
		public static Properties WSCONFIG_PROP = null;

		
		// Paths
		public static final String APP_CONFIG_PATH = "/config/Application.properties";
		public static final String LOOKANDFEEL_PATH = "/config/Lookandfeel.properties";

	    public static final String I30_EMPTY_PATH = "/config/Invoice30.xml";
		public static final String I31_EMPTY_PATH = "/config/Invoice31.xsig";
	    public static final String I32_EMPTY_PATH = "/config/Invoice32.xsig";
	    public static final String I321_EMPTY_PATH = "/config/Invoice321.xsig";
	    
	    // Colors
		public static final Color ERROR_MSG_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ERROR_MSG_COLOR")).intValue());
		public static final Color WARN_MSG_COLOR =  new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("WARN_MSG_COLOR")).intValue());
		public static final Color OK_MSG_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("OK_MSG_COLOR")).intValue());
		public static final Color BKG_MAIN_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BKG_MAIN_COLOR")).intValue());
		public static final Color SELECTION_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("SELECTION_COLOR")).intValue());
		public static final Color FONT_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_COLOR")).intValue());
		public static final Color INDIVIDUAL_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("INDIVIDUAL_COLOR")).intValue());
		public static final Color LEGENTITY_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("LEGENTITY_COLOR")).intValue());
		public static final Color BKG_ERROR_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BKG_ERROR_COLOR")).intValue());
		public static final Color BORDER_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BORDER_COLOR")).intValue());
		public static final Color SECOND_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("SECOND_COLOR")).intValue());
		public static final Color TRANSLUCENT_COLOR = new Color(0,0,0,0);
		public static final Color INITIAL_CANVAS_BKG = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("INITIAL_CANVAS_BKG")).intValue());
		public static final Color INITIAL_BORDER_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("INITIAL_BORDER_COLOR")).intValue());
		public static final Color DEFAULT_GRAY_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("DEFAULT_GRAY_COLOR")).intValue());
		public static final Color PROGRESS_BAR_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("PROGRESS_BAR_COLOR")).intValue());
		public static final Color SEPARATOR_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("SEPARATOR_COLOR")).intValue());
		public static final Color LINK_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("LINK_COLOR")).intValue());
		//Fonts
		public static final Font BUTTON_FONT = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BUTTON_FONT"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BUTTON_FONT_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BUTTON_FONT_SIZE")).intValue());
		public static final Font TITLE_FONT = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_SIZE")).intValue());
		public static final Font TITLE_FONT_PLAIN = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_PLAIN"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_PLAIN_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_PLAIN_SIZE")).intValue());
		public static final Font TITLE_FONT_ITALIC = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC_SIZE")).intValue());
		public static final Font TITLE_FONT_ITALIC_LITTLE = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC_LITTLE"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC_LITTLE_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC_LITTLE_SIZE")).intValue());
		public static final Font FONT_PLAIN = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_PLAIN"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_PLAIN_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_PLAIN_SIZE")).intValue());
		public static final Font FONT_BOLD = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_BOLD"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_BOLD_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_BOLD_SIZE")).intValue());
		public static final Font FONT_ITALIC = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_ITALIC"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_ITALIC_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_ITALIC_SIZE")).intValue());
		public static Font FONT_LINK = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_LINK"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_LINK_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_LINK_SIZE")).intValue());
		
		// VAT Book Fonts
		public static com.lowagie.text.Font FONT_TEXT_PLAIN = new com.lowagie.text.Font(com.lowagie.text.Font.NORMAL, 9, com.lowagie.text.Font.NORMAL, Constants.FONT_COLOR);
		public static com.lowagie.text.Font FONT_TEXT_BOLD = new com.lowagie.text.Font(com.lowagie.text.Font.BOLD, 10, com.lowagie.text.Font.NORMAL, Constants.FONT_COLOR);
		public static com.lowagie.text.Font FONT_TITLE_BOLD = new com.lowagie.text.Font(com.lowagie.text.Font.BOLD, 14, com.lowagie.text.Font.NORMAL, Constants.FONT_COLOR);
		
		//Extensions
		public static final String XSIG = ".xsig";
		public static final String XML = ".xml";
		
		// Attachments
		public static final int AttIncludedLimit = 5 * 1024 * 1024; 
		
		//Dates
		public static SimpleDateFormat DATE_FORMAT = null;	
		public static SimpleDateFormat DATE_FORMAT_HOURS = null;		
		
		//Taxes
		public static final String[] TAX_TYPE = {"IVA","IGIC","OTHER"};
		public static final String[] TAX_TYPE_CODE = {"01","03","05"};
		public static final Double[][] TAX_TYPE_VAL = {{16.00,7.00,4.00,0.00},{5.00,0.00,2.00,9.00,13.00,20.00,35.00},{}};
		public static final String[] PAYMENT_TYPE = {"CASH","DEBIT","TRANSFER"};
		
		// DataBase constants
		public static final String parametersInd = "PARTY_ID,NAME,FIRST_SURNAME,SECOND_SURNAME,TYPE_VERSION,ADDRESS.ADDRESS,POST_CODE,TOWN,PROVINCE,COUNTRY,TAX_IDENTIFICATION,RESIDENCE_TYPE,CONTACT";
		public static final String parametersLEn = "PARTY_ID,CORPORATE_NAME,TRADE_NAME,TYPE_VERSION,ADDRESS.ADDRESS,POST_CODE,TOWN,PROVINCE,COUNTRY,TAX_IDENTIFICATION,RESIDENCE_TYPE,CONTACT,REGISTRATION_DATA,FACE";
		
		//Visualize Parameters
		public static final String PARAMETER_INVOICE_CONTENT = "invoiceType";
		public static final String PARAMETER_INVOICE_LOGOTYPE = "logoPath";
		
		//Language files
		public static final String[] LANGUAGES = {"lang","lang_en","lang_ca","lang_eu","lang_gl"};
		
		//Queries
		public static final String QUERY1 = "SELECT PARTY_ID_BUYER,PARTY_ID_SELLER FROM FACTURAE WHERE FACTURAE_ID IN (SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER)='$1'))";
		public static final String QUERY2 = "SELECT FACTURAE.XML FROM FACTURAE WHERE FACTURAE_ID IN (SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE NVL(INVOICE.SERIES_CODE + INVOICE.NUMBER,INVOICE.NUMBER) = '$1'))";
		public static final String QUERY3 = "SELECT FACTURAE_ID,PARTY_ID_SELLER,PARTY_ID_BUYER,XML FROM FACTURAE WHERE FACTURAE_ID IN (SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = '$1'))";
		public static final String QUERY4 = "SELECT FACTURAE_ID,INVOICE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE STATE = '$1' AND CLASS = '$2')";
		public static final String QUERY4_BIS = "SELECT FACTURAE_ID,INVOICE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE STATE = '$1')";
		public static final String QUERY5 = "SELECT INVOICE_ID,NVL(SERIES_CODE+NUMBER,NUMBER),VERSION FROM INVOICE WHERE STATE='$1' AND CLASS='$2'";
		public static final String QUERY6 = "SELECT XML FROM FACTURAE WHERE FACTURAE_ID IN (SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID = '$1')";
		
		//Countries
		public static ArrayList<ComboOption> COUNTRIES31 = null;
		
		//Icons
		public static final Icon OK_ICON = new ImageIcon(GUIUtils.class.getResource("/images/button_mini_accept_trans.gif"));
		public static final Icon CANCEL_ICON = new ImageIcon(GUIUtils.class.getResource("/images/button_mini_cancel_trans.gif"));
		public static final Icon INFO_ICON = new ImageIcon(GUIUtils.class.getResource("/images/correct_message.gif"));
		public static final Icon ERROR_ICON = new ImageIcon(GUIUtils.class.getResource("/images/error_message.gif"));
		public static final Icon QUESTION_ICON = new ImageIcon(GUIUtils.class.getResource("/images/question_message.gif"));
		public static final Icon WARNING_ICON = new ImageIcon(GUIUtils.class.getResource("/images/warning_message.gif"));
		
		//Namespaces
		public static final String FACTURAE321NS = "http://www.facturae.es/Facturae/2014/v3.2.1/Facturae";
		public static final String FACTURAE32NS = "http://www.facturae.es/Facturae/2009/v3.2/Facturae";
		public static final String FACTURAE31NS = "http://www.facturae.es/Facturae/2007/v3.1/Facturae";
		public static final String FACTURAE30NS = "http://www.facturae.es/Facturae/2007/v3.0/Facturae";

		public static final String FACE_URL = "https://sede.gobcan.es/hacienda/pefac/";
		
		//GUIUtils
		public static GUIUtils DIALOG = null;
		
		//FACe roles
		public static final String FACE_ROLE_ACCOUNTINGOFFICE = "01";
		public static final String FACE_ROLE_MANAGEMENTAGENCY = "02";
		public static final String FACE_ROLE_PROCESSINGAUTHORITYUNIT = "03";
		public static final String FACE_ROLE_PROPOSINGAGENCY = "04";
		
		public static final String FACE_ERROR_CODE_FACE = "0001";
		
		public static final String FACE_KEYSTORE_FILE = "firmaface.jks";
		public static final char[] FACE_KEYSTORE_PASSWORD = "9Q23B34M.w13agJ41".toCharArray();
		
		public static final int MAX_LENGTH_ID = 20;

		//Legal Literals
		public static final String OPTION_PREFIX="OPTION_";
		public static final String TEXT_PREFIX="TEXT_";
		public static ArrayList<ComboOption> LEGAL_LITERALS = null;
		public static String COUNTRY_CODE_SPAIN ="ESP";

		//Table Invoices (MainWindow)
		public static final int TABLE_INVOICES_ID_0 = 0;
	    public static final int TABLE_INVOICES_DATE_1 = 1;
	    public static final int TABLE_INVOICES_ISSUER_2 = 2;
	    public static final int TABLE_INVOICES_RECEIVER_3 = 3;
	    public static final int TABLE_INVOICES_AMOUNT_4 = 4;
	    public static final int TABLE_INVOICES_VERSION_5 = 5;
	    public static final int TABLE_INVOICES_ACTION_FACE_6 = 6;
	    public static final int TABLE_INVOICES_ACTION_SEE_7 = 7;
	    public static final int TABLE_INVOICES_ACTION_DELETE_8 = 8;
	    public static final int TABLE_INVOICES_ACTION_SAVE_9 = 9;
	    public static final int TABLE_INVOICES_STATUS_10 = 10;
		
	    //Status (TreeInvoices selection in MainWindow)
	    public static final int DRAFT_STATUS_TREE=1;
	    public static final int ISSUED_STATUS_TREE=2;
	    public static final int SENT_STATUS_TREE=3;
	    public static final int RECEIVED_STATUS_TREE=4;
	    public static final int CORRECTIVE_DRAFT_STATUS_TREE=5;
	    public static final int CORRECTIVE_ISSUED_STATUS_TREE=6;
	    public static final int CORRECTIVE_SENT_STATUS_TREE=7;
	    
	    //Received invoice data
		public static final int SCHEMA_VALIDATION = 0;
		public static final int SIGN_VALIDATION = 1;
		public static final int VERSION = 2;
		public static final int SIGN_POLICY = 3;
		public static final int SELLER = 4;
		public static final int SELLER_TAX_IDENTIFICATION = 5;
		public static final int BUYER = 6;
		public static final int BUYER_TAX_IDENTIFICATION = 7;
		public static final int ITEM = 8;
		public static final int AMOUNT = 9;
		public static final int ISSUE_DATE = 10;
		public static final int RECEIVEDID = 11;

		
		// Grid con líneas de pedido 
		public static final int POS_ITEM_DESCRIPTION_GRID=0;
		public static final int POS_QUANTITY_GRID=1;
		public static final int POS_UNIT_OF_MEASURE_GRID=2;
		public static final int POS_TOTAL_COST_GRID=3;
		public static final int POS_TAX_OUT_GRID=4;
		public static final int POS_TAX_WITH_GRID=5;
		public static final int POS_ITEM_TOTAL_COST_GRID=6;
		
		//Grid de impuestos repercutidos
		public static final int POS_GRID_REPERCUTIDOS_ID_IMPUESTO = 0 ;     //Impuesto (ej IVA - 01)
		public static final int POS_GRID_REPERCUTIDOS_TIPO_IMPOSITIVO = 1 ; //Tipo impositivo (ej 21)
		public static final int POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE = 2 ;  //Base imponible
		public static final int POS_GRID_REPERCUTIDOS_CUOTA = 3  ;          //Cuota
		public static final int POS_GRID_REPERCUTIDOS_BASE_IMPONIBLE_ESPECIAL = 4 ;  //Base imponible especial
		public static final int POS_GRID_REPERCUTIDOS_CUOTA_ESPECIAL = 5 ;           //Cuota especial
		public static final int POS_GRID_REPERCUTIDOS_RECARGO_EQUIVALENCIA = 6 ;     //Recargo equivalencia
		public static final int POS_GRID_REPERCUTIDOS_IMPORTE_RECARGO_EQUIVALENCIA = 7 ;  //Importe recargo equivalencia
		
		// 10-10-2014 Servidor de eFactura-ws
		
		// public static final String SERVIDOR_eFactura_ws = "https://localhost:8543/eFactura-ws/FacturaService";
		// public static final String SERVIDOR_eFactura_ws = "https://ddes1vll.ses.hacienda.gobiernodecanarias.org:18443/eFactura-ws/FacturaService";
}
