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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.swing.CellRendererPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.ui.dialogs.panels.InvoiceDetailGeneralPanel;

import sun.awt.image.GifImageDecoder;
import sun.awt.image.ImageDecoder;
import sun.awt.image.InputStreamImageSource;

/** Ensures animated icons are properly handled within objects that use
 * renderers within a {@link CellRendererPane} to render the icon.  Keeps
 * a list of repaint rectangles to be used to queue repaint requests when
 * the animated icon indicates an update.  The set of repaint rectangles
 * is cleared after the repaint requests are queued.
 * @author twall
 */
public class AnimatedIcon implements Icon {

    /** Cache results to reduce decoding overhead. */
    private static Map<Image, Boolean> decoded = new WeakHashMap<Image, Boolean>();
    private static Log logger = LogFactory.getLog(AnimatedIcon.class);
    
    /** Returns whether the given icon is an animated GIF. */
    public static boolean isAnimated(Icon icon) {
        if (icon instanceof ImageIcon) {
            Image image = ((ImageIcon)icon).getImage();
            if (image != null) {
                // Quick check for commonly-occurring animated GIF comment
                Object comment = image.getProperty("comment", null);
                if (String.valueOf(comment).startsWith("GifBuilder"))
                    return true;

                // Check cache of already-decoded images
                if (decoded.containsKey(image)) {
                    return Boolean.TRUE.equals(decoded.get(image));
                }

                InputStream is = null;
                try {
                    URL url = new URL(icon.toString());
                    is = url.openConnection().getInputStream();
                }
                catch(MalformedURLException e) {
                    logger.error("Error during animated icon visualization: " + e.getMessage());
                } catch (IOException e) {
                	logger.error("Error during animated icon visualization: " + e.getMessage());
                }
                if (is == null) {
                    try {
                        // Beware: lots of hackery to obtain the image input stream
                        // Be sure to catch security exceptions
                        ImageProducer p = image.getSource();
                        if (p instanceof InputStreamImageSource) {
                            Method m = InputStreamImageSource.class.getDeclaredMethod("getDecoder", (Class<?>)null);
                            m.setAccessible(true);
                            ImageDecoder d = (ImageDecoder)m.invoke(p, (Class<?>)null);
                            if (d instanceof GifImageDecoder) {
                                GifImageDecoder gd = (GifImageDecoder)d;
                                Field input = ImageDecoder.class.getDeclaredField("input");
                                input.setAccessible(true);
                                is = (InputStream)input.get(gd);
                            }
                        }
                    }
                    catch(Exception e) {
                    	logger.error("Error during animated icon visualization: " + e.getMessage());
                    }
                }
                if (is != null) {
                    GifDecoder decoder = new GifDecoder();
                    decoder.read(is);
                    boolean animated = decoder.getFrameCount() > 1;
                    decoded.put(image, Boolean.valueOf(animated));
                    return animated;
                }
            }
            return false;
        }
        return icon instanceof AnimatedIcon;
    }
    
    private ImageIcon original;
    private Set<RepaintArea> repaints = new HashSet<RepaintArea>();

    /** For use by derived classes that don't have an original. */
    protected AnimatedIcon() { }
    
    /** Create an icon that takes care of animating itself on components
     * which use a CellRendererPane.
     */
    public AnimatedIcon(ImageIcon original) {
        this.original = original;
        new AnimationObserver(this, original);
    }
    
    /** Trigger a repaint on all components on which we've previously been 
     * painted.
     */
    protected synchronized void repaint() {
        for (Iterator<RepaintArea> i = repaints.iterator();i.hasNext();) {
            i.next().repaint();
        }
        repaints.clear();
    }
    public int getIconHeight() {
        return original.getIconHeight();
    }
    public int getIconWidth() {
        return original.getIconWidth();
    }
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        paintFrame(c, g, x, y);
        if (c != null) {
            int w = getIconWidth();
            int h = getIconHeight();
            AffineTransform tx = ((Graphics2D)g).getTransform();
            w = (int)(w * tx.getScaleX());
            h = (int)(h * tx.getScaleY());
            registerRepaintArea(c, x, y, w, h);
        }
    }
    protected void paintFrame(Component c, Graphics g, int x, int y) {
        original.paintIcon(c, g, x, y);
    }
    /** Register repaint areas, which get get cleared once the repaint request
     * has been queued.
     */
    protected void registerRepaintArea(Component c, int x, int y, int w, int h) {
        repaints.add(new RepaintArea(c, x, y, w, h));
    }
    
    /** Object to encapsulate an area on a component to be repainted. */
    private class RepaintArea {
        public int x, y, w, h;
        public Component component;
        private int hashCode;
        public RepaintArea(Component c, int x, int y, int w, int h) {
            Component ancestor = findNonRendererAncestor(c);
            if (ancestor != c) {
                Point pt = SwingUtilities.convertPoint(c, x, y, ancestor);
                c = ancestor;
                x = pt.x;
                y = pt.y;
            }
            this.component = c;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            String hash = String.valueOf(x) + "," + y + ":" + c.hashCode();
            this.hashCode = hash.hashCode();
        }
        /** Find the first ancestor <em>not</em> descending from a 
         * {@link CellRendererPane}.
         */
        private Component findNonRendererAncestor(Component c) {
            Component ancestor = SwingUtilities.getAncestorOfClass(CellRendererPane.class, c);
            if (ancestor != null && ancestor != c && ancestor.getParent() != null) {
                c = findNonRendererAncestor(ancestor.getParent());
            }
            return c;
        }
        /** Queue a repaint request for this area. */
        public void repaint() {
            component.repaint(x, y, w, h);
        }
        public boolean equals(Object o) {
            if (o instanceof RepaintArea) {
                RepaintArea area = (RepaintArea)o;
                return area.component == component
                    && area.x == x && area.y == y
                    && area.w == w && area.h == h;
            }
            return false;
        }
        /** Since we're using a HashSet. */
        public int hashCode() {
            return hashCode;
        }
        public String toString() {
            return "Repaint(" + component.getClass().getName() + "@" + x + "," + y + " " + w + "x" + h + ")";
        }
    }

    /** Detect changes in the original animated image, and remove self
     * if the target icon is GC'd.
     * @author twall
     */
    private static class AnimationObserver implements ImageObserver {
        private WeakReference<AnimatedIcon> ref;
        private ImageIcon original;
        public AnimationObserver(AnimatedIcon animIcon, ImageIcon original) {
            this.original = original;
            this.original.setImageObserver(this);
            ref = new WeakReference<AnimatedIcon>(animIcon);
        }
        /** Queue repaint requests for all known painted areas. */
        public boolean imageUpdate(Image img, int flags, int x, int y, int width, int height) {
            if ((flags & (FRAMEBITS|ALLBITS)) != 0) {
                AnimatedIcon animIcon = ref.get();
                if (animIcon != null) {
                    animIcon.repaint();
                }
                else
                    original.setImageObserver(null);
            }
            // Return true if we want to keep painting
            return (flags & (ALLBITS|ABORT)) == 0;
        }
    }
}
