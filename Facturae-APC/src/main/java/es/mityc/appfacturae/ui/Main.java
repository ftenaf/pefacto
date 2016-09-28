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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.ui.dialogs.InputLanguageDialog;
import es.mityc.appfacturae.ui.windows.InitialWindow;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;

public class Main {
	
	public static void main(String[] args) {
    	
    	PropertiesLoading.load();
        
        LookAndFeelLoading.load();
    	
    	String lang = Constants.CONFIG_PROP.getProperty("language");
    	
    	// In case a language is configured
    	if (lang == null || "".equals(lang)) {
    		InputLanguageDialog ild = new InputLanguageDialog(null, true);
			ild.setVisible(true);
			lang = ild.getValue();
			ild.dispose();
			// Selected language is saved
			Constants.CONFIG_PROP.setProperty("language", lang);
			Constants.SIGN_PROP.setProperty("locale.language", lang);

			
			try {
        		OutputStream f = new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file")));
        		OutputStream f2 = new FileOutputStream(new File(Constants.APP_PROP.getProperty("sign_file")));
        		OutputStream f3 = new FileOutputStream(new File(Constants.APP_PROP.getProperty("legal_literals_file"))); //ctg
        		Constants.CONFIG_PROP.store(f, null);
        		Constants.SIGN_PROP.store(f2, null);
        		Constants.LEGALLITERALS_PROP.store(f3, null);
        	} catch(IOException e) {}
    	}
    	
		//Propiedades necesarias para la internacionalizacion de MITyCLibAPI
		Properties i18nProp = new Properties();
	    i18nProp.setProperty("i18n.factory.class", "");
	    i18nProp.setProperty("i18n.locale.default",lang);
		try {
			File fileI18n = new File(Constants.APP_PROP.getProperty("i18n_file"));
			File dirI18n = fileI18n.getParentFile();
			
			dirI18n.mkdirs();
    		OutputStream f4 = new FileOutputStream(fileI18n);
    		i18nProp.store(f4,null);
    	} catch(IOException e) {
    		//System.out.println(e);
    	}

	    Constants.LANG = ResourceBundle.getBundle("language/lang", new Locale(lang));

    	/** Loading countries lists */
    	Constants.COUNTRYLANG31 = ResourceBundle.getBundle("language/countries"+Constants.FACTURAE31, Constants.LANG.getLocale());
	    String[] countries = new String[]{};
        Constants.COUNTRIES31 = new ArrayList<ComboOption>();
        countries = new String[es.mityc.appfacturae.facturae.CountryType.values().length];
		for (int i = 0; i < countries.length; i++)
			Constants.COUNTRIES31.add(new ComboOption(Constants.COUNTRYLANG31
				.getString(es.mityc.appfacturae.facturae.CountryType.values()[i].toString()), String.valueOf(i)));
        Collections.sort(Constants.COUNTRIES31);
    	
    	Constants.DATE_FORMAT = new SimpleDateFormat(Constants.LANG.getString("Date_Format"));
    	Constants.DATE_FORMAT_HOURS = new SimpleDateFormat(Constants.LANG.getString("Date_Format_Hours"));
    	
    	final InitialWindow iw = InitialWindow.getInstance();
    	
    	// Is the database locked?
    	File f = new File("FacturaeDB.lck");
    	if (f.exists() && !f.delete()){
    		Constants.DIALOG.showErrorDataBaseBusy(null);
    		InitialWindow.getInstance().setVisible(false);
    		InitialWindow.getInstance().dispose();
    		System.exit(0);
    	}
    	
    	JFrame j = MainWindow.getInstance();
    	
    	iw.refreshProgressBar(100);
    	iw.setVisible(false);
		iw.dispose();
		
		j.setState(JFrame.NORMAL);
		j.setVisible(true);      
		j.requestFocusInWindow();
		j.toFront();
    	
    }
}