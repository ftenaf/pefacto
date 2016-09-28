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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.InputStream;

/** Provides a spinning disk of hash marks. */
public class GifAnimatedIcon extends AbstractAnimatedIcon {
    private int w;
    private int h;
    private Image[] frames;
    
    public static GifAnimatedIcon getAnimatedIcon(InputStream is) {
    	GifDecoder gd = new GifDecoder();
    	if (gd.read(is) != 0)
    		return null;
    	Dimension d = gd.getFrameSize();
    	int spokes = gd.getFrameCount();
    	int time = 0;
    	for (int i = 0; i < spokes; i++) {
    		time += gd.getDelay(i);
    	}
    	time = time/spokes;
    	GifAnimatedIcon gai = new GifAnimatedIcon(d.width, d.height, spokes, time);
    	for (int i = 0; i < gai.frames.length; i++) {
    		gai.frames[i] = gd.getFrame(i);
    	}
    	return gai;
    }

    protected GifAnimatedIcon(int w, int h, int spokes, int time) {
        super(spokes, time);
        this.w = w;
        this.h = h;
        frames = new Image[getFrameCount()];
    }
    public int getIconHeight() {
        return h;
    }
    public int getIconWidth() {
        return w;
    }
    /** Set the stroke width according to the size. */
    protected float getStrokeWidth(int size) {
        return size/16f;
    }
    
    protected void paintFrame(Component c, Graphics graphics, int x, int y) {
        int idx = getFrame();
        graphics.drawImage(frames[idx], x, y, null);
    }
    
    public String toString() {
        return "SpinningDial(" + getIconWidth() + "x" + getIconHeight() + ")";
    }
}
