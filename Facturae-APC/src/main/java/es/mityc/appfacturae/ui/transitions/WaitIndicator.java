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
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/** Prevents mouse and key input to a {@link JComponent} or {@link JFrame},
 * while dimming the component and displaying a wait cursor.
 */
public class WaitIndicator extends AbstractComponentDecorator implements KeyEventDispatcher {

    /** Place the wait indicator over the entire frame. */
    public WaitIndicator(JFrame frame) {
        this(frame.getLayeredPane());
    }
    
    /** Place the wait indicator over the given component. */
    public WaitIndicator(JComponent target) {
        super(target);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
        getPainter().addMouseListener(new MouseAdapter() { });
        getPainter().addMouseMotionListener(new MouseMotionAdapter() { });
        getPainter().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    /** Remove the wait indicator. */
    public void dispose() {
        super.dispose();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
    }

    /** Consume events targeted at our target component.  Return true to
     * consume the event.
     */
    public boolean dispatchKeyEvent(KeyEvent e) {
        return SwingUtilities.isDescendingFrom(e.getComponent(), getComponent());
    }
    
    /** The default dims the blocked component. */
    public void paint(Graphics g) {
        Color bg = super.fadeColor;
        Color c = new Color(bg.getRed(), bg.getGreen(), bg.getBlue(), 128);
        Rectangle r = getDecorationBounds();
        g = g.create();
        g.setColor(c);
        g.fillRect(r.x, r.y, r.width, r.height);
        g.dispose();
    }
}
