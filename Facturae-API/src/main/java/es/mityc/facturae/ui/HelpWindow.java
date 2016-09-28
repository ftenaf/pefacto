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
package es.mityc.facturae.ui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the help window.
 */
public class HelpWindow extends javax.swing.JDialog {
	
	ResourceBundle lang = null;
	
	private static Log logger = LogFactory.getLog(HelpWindow.class);
	
	public HelpWindow(Locale l) {
		super();
		
		lang = ResourceBundle.getBundle("language/lang", l);
		
		initComponents(l);
		this.scrollPaneToTop();
	}
	
    private void initComponents(Locale l) {
	    jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
	    jEditorPane1.setContentType("text/html");
        jEditorPane1.setEditable(false);
        
        try{
        	jEditorPane1.setPage(this.getClass().getResource("/html/help_certificate_"+l.getLanguage()+l.getCountry()+".html").toString());
        }catch(IOException ioe){
        	logger.error("The help page is not available: "+ ioe.getMessage());
        }
        
        jScrollPane1.setViewportView(jEditorPane1);
        
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 352, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle(lang.getString("ContextHelp"));
        setModal(true);
        setBounds(0, 0, 360, 300);
        setLocationRelativeTo(this.getParent());
        setResizable(false);
        
	    pack();
    }
    
    private void scrollPaneToTop() {
    	 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jScrollPane1.getVerticalScrollBar().setValue(
					jScrollPane1.getVerticalScrollBar().getMinimum());
			}
		});
	}

    
    // Variables declaration
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;

    
}
