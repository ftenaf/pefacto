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
package es.mityc.appfacturae.ui;

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.constants.ConstantsLookAndFeel;

public class LookAndFeelLoading {
	
	private static Log logger = LogFactory.getLog(LookAndFeelLoading.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void load(){
		try{
			if (UIManager.getSystemLookAndFeelClassName().toString().equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			else
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			UIManager.getDefaults().put("CheckBox.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("CheckBox.background")).intValue()));
			UIManager.getDefaults().put("Button.darkShadow", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("Button.darkShadow")).intValue()));
			UIManager.getDefaults().put("Button.shadow", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("Button.shadow")).intValue()));
			UIManager.getDefaults().put("Button.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("Button.background")).intValue()));
			UIManager.getDefaults().put("Button.highligth", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("Button.highligth")).intValue()));
			UIManager.getDefaults().put("Button.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("Button.foreground")).intValue()));
			UIManager.getDefaults().put("ComboBox.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.background")).intValue()));
			UIManager.getDefaults().put("ComboBox.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.foreground")).intValue()));
			UIManager.getDefaults().put("ComboBox.buttonBackground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.buttonBackground")).intValue()));
			UIManager.getDefaults().put("ComboBox.buttonDarkShadow", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.buttonDarkShadow")).intValue()));
			UIManager.getDefaults().put("ComboBox.buttonHighlight", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.buttonHighlight")).intValue()));
			UIManager.getDefaults().put("ComboBox.buttonShadow", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.buttonShadow")).intValue()));
			UIManager.getDefaults().put("ComboBox.font", Constants.FONT_PLAIN);
			UIManager.getDefaults().put("ScrollBar.thumb", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ScrollBar.thumb")).intValue()));
			UIManager.getDefaults().put("ScrollBar.thumbShadow", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ScrollBar.thumbShadow")).intValue()));
			UIManager.getDefaults().put("ScrollBar.thumbDarkShadow", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ScrollBar.thumbDarkShadow")).intValue()));
			UIManager.getDefaults().put("ScrollBar.thumbHighlight", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ScrollBar.thumbHighlight")).intValue()));
			UIManager.getDefaults().put("ScrollBar.track", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ScrollBar.track")).intValue()));
			UIManager.getDefaults().put("TextField.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TextField.foreground")).intValue()));
			UIManager.getDefaults().put("TextField.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TextField.background")).intValue()));
			UIManager.getDefaults().put("TextArea.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TextArea.background")).intValue()));
			UIManager.getDefaults().put("PasswordField.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("PasswordField.foreground")).intValue()));
			UIManager.getDefaults().put("PasswordField.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("PasswordField.background")).intValue()));
			UIManager.getDefaults().put("ScrollPane.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ScrollPane.background")).intValue()));
			UIManager.getDefaults().put("List.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("List.foreground")).intValue()));
			UIManager.getDefaults().put("List.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("List.background")).intValue()));
			UIManager.getDefaults().put("Label.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("Label.foreground")).intValue()));
			UIManager.getDefaults().put("TabbedPane.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TabbedPane.background")).intValue()));
			UIManager.getDefaults().put("TabbedPane.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TabbedPane.foreground")).intValue()));
			UIManager.getDefaults().put("Panel.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("Panel.background")).intValue()));
			UIManager.getDefaults().put("ProgressBar.foreground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ProgressBar.foreground")).intValue()));
			UIManager.getDefaults().put("ProgressBar.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ProgressBar.background")).intValue()));
			UIManager.getDefaults().put("ComboBox.disabledBackground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.disabledBackground")).intValue()));
			UIManager.getDefaults().put("ComboBox.disabledForeground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ComboBox.disabledForeground")).intValue()));
			UIManager.getDefaults().put("TextField.disabledBackground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TextField.disabledBackground")).intValue()));
			UIManager.getDefaults().put("TextField.inactiveForeground", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("TextField.inactiveForeground")).intValue()));
			UIManager.getDefaults().put("ToolTip.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ToolTip.background")).intValue()));
			UIManager.getDefaults().put("ToolBar.background", new Color(Integer.valueOf(ConstantsLookAndFeel.LOOKANDFEEL_PROP.getProperty("ToolBar.background")).intValue()));
			
			UIManager.getDefaults().put("TabbedPane.font", Constants.FONT_PLAIN);
			
			// JOptionPane
			UIManager.getDefaults().put("OptionPane.background", Constants.BKG_MAIN_COLOR);
			UIManager.getDefaults().put("OptionPane.messageFont", Constants.FONT_BOLD);
			UIManager.getDefaults().put("OptionPane.messageForeground", Constants.FONT_COLOR);
			
			UIManager.getDefaults().put("OptionPane.errorIcon", Constants.ERROR_ICON);
			UIManager.getDefaults().put("OptionPane.questionIcon", Constants.QUESTION_ICON);
			UIManager.getDefaults().put("OptionPane.informationIcon", Constants.INFO_ICON);
			UIManager.getDefaults().put("OptionPane.warningIcon", Constants.WARNING_ICON);
			UIManager.getDefaults().put("OptionPane.okIcon", Constants.OK_ICON);
			UIManager.getDefaults().put("OptionPane.cancelIcon", Constants.CANCEL_ICON);
								
			Map attributes = Constants.FONT_LINK.getAttributes();
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			Constants.FONT_LINK = Constants.FONT_LINK.deriveFont(attributes);
		}
		catch(ClassNotFoundException cnfe) {
			logger.error("Error loading look and feel properties: " + cnfe.getMessage());
		} catch(InstantiationException ie) {
			logger.error("Error loading look and feel properties: " + ie.getMessage());
		} catch(IllegalAccessException iae) {
			logger.error("Error loading look and feel properties: " + iae.getMessage());
		} catch(UnsupportedLookAndFeelException ulafe) {
			logger.error("Error loading look and feel properties: " + ulafe.getMessage());
		}
	}
}
