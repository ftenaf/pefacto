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
package es.mityc.appfacturae.ui.transitions;

import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.mityc.appfacturae.utils.constants.Constants;

public class Transition {

	private WaitIndicator waiter = null;
	private Thread waiterTh = null;
	private JLabel jLabelTransition = null;
	private JPanel jPanelTransition = null;
	
	public Transition (JPanel panel){
		jLabelTransition = new JLabel();
		jLabelTransition.setVerticalAlignment(SwingConstants.CENTER);
    	jLabelTransition.setVerticalTextPosition(SwingConstants.TOP);
    	jLabelTransition.setHorizontalAlignment(SwingConstants.CENTER);
    	jLabelTransition.setHorizontalTextPosition(SwingConstants.CENTER);
    	jLabelTransition.setFont(Constants.FONT_PLAIN);
    	jLabelTransition.setForeground(Constants.FONT_COLOR);
		jPanelTransition = panel;
		reInstWaiterThread();
	}
	
	/**
     * Puts a semi-translucent panel in MainWindow showing a parametric message with a animated icon
     * @param message.- Message to show in the translucent panel
     */
    public void putTransitionPanel(String message) {
    	removeTransitionPanel();
    	reInstWaiterThread();
    	jLabelTransition.setText(message);
    	if (jLabelTransition.getIcon() == null) {
    		InputStream is = getClass().getResourceAsStream("/images/FactWaiter.gif");
    		if (is != null) {
    			GifAnimatedIcon animatedIcon = GifAnimatedIcon.getAnimatedIcon(is);
    			if (animatedIcon != null)
    				jLabelTransition.setIcon(animatedIcon);
    		}
    	}
    	waiterTh.start();
    }
    
    /**
     * Removes the semi-translucent panel
     */
    public void removeTransitionPanel() {
    	if (waiterTh != null) {
			try {
				waiterTh.join(500);
			} catch (InterruptedException e) {}
			waiterTh.interrupt();
			if (waiter != null) {
	    		waiter.setVisible(false);
	    		waiter.dispose();
	    	}
		}	
    }
    
    private void reInstWaiterThread(){
    	waiterTh = new Thread(new Runnable() { public void run() {
    		waiter = new GifWaitIndicator(jPanelTransition, jLabelTransition);
    	}});
    }
    
}
