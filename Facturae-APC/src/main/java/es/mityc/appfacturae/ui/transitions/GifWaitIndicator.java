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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

/* Gradually fade the display, then show a spinning dial indicator.
 */
public class GifWaitIndicator extends WaitIndicator implements ActionListener {

    private static final int FADE_INTERVAL = 500/24;
    private static final int FADE_THRESHOLD = 192;
    
    private static Rectangle paintIconR = new Rectangle();
    private static Rectangle paintTextR = new Rectangle();
    
    private Timer timer;
    private int fade;
    private JLabel lbl;

    public GifWaitIndicator(JComponent target, JLabel lbl) {
        super(target);
        this.lbl = lbl;
    }

    /** Fade the affected component to background, then apply a spinning
     * wait indicator.
     */
    public void paint(Graphics g) {
        if (timer == null) {
            timer = new Timer(FADE_INTERVAL, this);
            timer.start();
        }
    
        Rectangle r = getComponent().getVisibleRect();
        Color bg = super.fadeColor;
        g.setColor(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), fade));
        g.fillRect(r.x, r.y, r.width, r.height);
        if (fade < FADE_THRESHOLD)
            return;
        
        paintLabel(g, lbl, r);
    
        g.dispose();
    }
    
    private static FontMetrics getFontMetrics(JComponent c, Font font) {
		return c.getFontMetrics(font);
    }

    protected void paintLabel(Graphics g, JLabel label, Rectangle r) {
        if (label == null)
        	return;
    	String text = label.getText();
        Icon icon = label.getIcon();

        if ((icon == null) && (text == null)) {
            return;
        }
        
        paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
        paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

        g.setFont(label.getFont());
        FontMetrics fm = getFontMetrics(label, g.getFont());
    	String clippedText = SwingUtilities.layoutCompoundLabel(
    			(JComponent) label,
                fm,
                text,
                icon,
                label.getVerticalAlignment(),
                label.getHorizontalAlignment(),
                label.getVerticalTextPosition(),
                label.getHorizontalTextPosition(),
                r,
                paintIconR,
                paintTextR,
                label.getIconTextGap());

        if (icon != null)
		    icon.paintIcon(getPainter(), g, paintIconR.x, paintIconR.y);
        
        if (text != null) {
     	    View v = (View) label.getClientProperty(BasicHTML.propertyKey);
     	    if (v == null) {  		
         		g.setColor(label.getForeground());
                g.drawString(clippedText, paintTextR.x, paintTextR.y + fm.getAscent());
     	    }
        }
        
    }
    
    /** Remove the wait decoration. */
    public void dispose() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        super.dispose();
    }
    
    /** First fade the background, then spin the dial. */
    public void actionPerformed(ActionEvent e) {
        if (fade < FADE_THRESHOLD) {
            fade += 32;
        	getPainter().repaint();
    	}
    }
}
