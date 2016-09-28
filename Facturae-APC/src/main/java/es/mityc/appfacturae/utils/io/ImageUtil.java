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
package es.mityc.appfacturae.utils.io;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ImageUtil {
	
	private static Log logger = LogFactory.getLog(ImageUtil.class);
	
    /**
     * It calculates the minimum factor of scale for resize the image
     * @param nMaxWidth.- minimum width
     * @param nMaxHeight.- minimum high
     * @param imgSrc.- The image stored in memory
     */
	public static BufferedImage scaleToSize(int nMaxWidth, int nMaxHeight, BufferedImage imgSrc) {
		int nHeight = imgSrc.getHeight();
		int nWidth = imgSrc.getWidth();
		double scaleX = (double)nMaxWidth / (double)nWidth;
		double scaleY = (double)nMaxHeight / (double)nHeight;
		double fScale = Math.min(scaleX, scaleY);
		return scale(fScale, imgSrc);
	}
	 
	/**
	* It scales the provided image acording to percentage value
	* @param scale f.e: scale=0.6 (it scales image a 60%)
	* @param imgSrc.- The image stored in memory
	* @return scaled BufferedImage
	*/
	public static BufferedImage scale(double scale, BufferedImage srcImg) {
		if (scale == 1 )
			return srcImg;
		AffineTransformOp op = new AffineTransformOp (AffineTransform.getScaleInstance(scale, scale), null);
		return op.filter(srcImg, null);
	}
        
    public static BufferedImage createBufferedImage(Image image, JLabel lab){
        if(image instanceof BufferedImage)
            return (BufferedImage)image;
        BufferedImage bi = new BufferedImage(image.getWidth(lab), image.getHeight(lab),BufferedImage.TYPE_INT_ARGB); // ARGB to support transparency if in original image
        Graphics2D g = bi.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bi;
    }
    
    public static ImageIcon createScaledImageIcon (File f, JLabel j, int maxWidth, int maxHeight){
    	String extension = FileUtil.getExtension(f);
        if (extension.equalsIgnoreCase("tiff") ||
            extension.equalsIgnoreCase("tif") ||
            extension.equalsIgnoreCase("gif") ||
            extension.equalsIgnoreCase("jpeg") ||
            extension.equalsIgnoreCase("jpg") ||
            extension.equalsIgnoreCase("png") ||
            extension.equalsIgnoreCase("bmp")) {
            
            BufferedImage bufImg = null;
            
            try {
                if (extension.equals("bmp"))
                    bufImg = ImageIO.read(f);
                else
                    bufImg = ImageUtil.createBufferedImage(new ImageIcon(f.getAbsolutePath()).getImage(), j);
                ImageIcon imgIcon = new ImageIcon(ImageUtil.scaleToSize(maxWidth, maxHeight, bufImg));
                
                return imgIcon;                
            }
            catch (Exception e) {
                logger.error("Incorrect file extension during scaled image creation: " + e.getMessage());
            }
        } else {
        	if (extension != null && !"".equals(extension))
        		logger.error("Creating a scaled image an error happens becauase the extension is not supported by the application");
        }
        
        return null;
    }
}