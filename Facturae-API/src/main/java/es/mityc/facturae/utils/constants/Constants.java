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
package es.mityc.facturae.utils.constants;

import java.awt.Color;
import java.awt.Font;

/**
 * Constants used by the API. It contains paths, look and feel properties (colors and fonts). 
 */
public class Constants {
		
		// Paths
		public static final String CONFIG_PATH = "/config/config.properties";
		public static final String LOOKANDFEEL_PATH = "/config/Certlookandfeel.properties";
		
		// Colors
		public static final Color BKG_MAIN_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BKG_MAIN_COLOR")).intValue());
		public static final Color SELECTION_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("SELECTION_COLOR")).intValue());
		public static final Color FONT_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_COLOR")).intValue());
		public static final Color BORDER_COLOR = new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("BORDER_COLOR")).intValue());

		//Fonts
		public static final Font TITLE_FONT = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_SIZE")).intValue());
		public static final Font TITLE_FONT_PLAIN = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_PLAIN"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_PLAIN_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_PLAIN_SIZE")).intValue());
		public static final Font TITLE_FONT_ITALIC = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TITLE_FONT_ITALIC_SIZE")).intValue());
		public static final Font FONT_PLAIN = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_PLAIN"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_PLAIN_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_PLAIN_SIZE")).intValue());
		public static final Font FONT_BOLD = new java.awt.Font(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_BOLD"), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_BOLD_STYLE")).intValue(), Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("FONT_BOLD_SIZE")).intValue());
		
}
