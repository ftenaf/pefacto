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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.WeakReference;

import javax.swing.Timer;

/** Provide animation of auto-generated animations.  Makes use of the repaint
 * tracking structure established by {@link AnimatedIcon}. 
 */
public abstract class AbstractAnimatedIcon extends AnimatedIcon {
    private static final int DEFAULT_INTERVAL = 1000/24;

    private Timer timer;
    private int repaintInterval;
    private int frame;
    private int frameCount;
    
    protected AbstractAnimatedIcon() {
        this(0);
    }
    
    protected AbstractAnimatedIcon(int frameCount) {
        this(frameCount, DEFAULT_INTERVAL);
    }
    
    protected AbstractAnimatedIcon(int frameCount, int interval) {
        this.frameCount = frameCount;
        setFrameInterval(interval);
    }
    
    /** Ensure the timer stops running, so it, too can be GC'd. */
    protected void finalize() {
        timer.stop();
    }
    
    /** Setting a frame interval of zero stops automatic animation. */
    public void setFrameInterval(int interval) {
        repaintInterval = interval;
        if (interval != 0) {
            if (timer == null) {
                timer = new Timer(interval, new AnimationUpdater(this));
                timer.setRepeats(true);
            }
            else {
                timer.setDelay(interval);
            }
        }
        else if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    public int getFrameInterval() {
        return repaintInterval;
    }
    
    /** Returns the total number of frames. */
    public int getFrameCount() {
        return frameCount;
    }
    
    /** Advance to the next animation frame. */
    public void nextFrame() {
        setFrame(getFrame() + 1);
    }
    
    /** Set the current animation frame number. */
    public void setFrame(int f) {
        this.frame = f;
        if (frameCount != 0)
            frame = frame % frameCount;
        repaint();
    }
    
    /** Returns the current animation frame number. */
    public int getFrame() {
        return frame;
    }

    /** Implement this method to paint the icon. */
    protected abstract void paintFrame(Component c, Graphics g, int x, int y);

    public abstract int getIconWidth();
    public abstract int getIconHeight();
    
    protected synchronized void registerRepaintArea(Component c, int x, int y, int w, int h) {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
        super.registerRepaintArea(c, x, y, w, h);
    }
    
    private static class AnimationUpdater implements ActionListener {
        private WeakReference<AbstractAnimatedIcon> ref = null;
        public AnimationUpdater(AbstractAnimatedIcon icon) {
            this.ref = new WeakReference<AbstractAnimatedIcon>(icon);
        }
        public void actionPerformed(ActionEvent e) {
            AbstractAnimatedIcon icon = (AbstractAnimatedIcon)ref.get();
            if (icon != null) {
                icon.nextFrame();
            }
        }
    }
}
