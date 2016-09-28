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
package es.mityc.appfacturae.ui.components;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;

public class FadingCanvas extends Canvas {

	private static Log logger = LogFactory.getLog(FadingCanvas.class);
	private Canvas canvas = null;
	private Thread th = null;
	private String msg = null;
	private Color color = null;
	
	private Image myFlatImage;

	public FadingCanvas () {
		canvas = this;
		
		try {
			myFlatImage = ImageIO.read(this.getClass().getResourceAsStream("/images/background_image_canvas.jpg"));
		}
		catch(IOException ioe) {
			logger.error("Unable to load fading canvas background image from resources: " + ioe.getMessage());
		}
	}

	public void showMessage(String msg, Color color) {
		if (color != null)
			this.color = color;
		else
			this.color = Constants.ERROR_MSG_COLOR;
		
		if (msg != null && !"".equals(msg.trim()))
			this.msg = msg;
		else
			this.msg = "";

		if (th != null && th.isAlive()) {
			th.interrupt();		
		}

		th = new Thread(new Runnable() {
			public void run() {
				fadeOut(canvas.getGraphics(), 0.10f, 2000, 50, 40);
			}						
		});
		th.start();
	}

	/** Fade out effect for displayed messages*/
	private void fadeOut(Graphics g, float alpha, long fixedTime, long fadeTime, long fadeIterations){
		if (g == null)
			return;
		
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(color);
		g2.drawString(msg, 20, 22);

		// The message result is shown during firtsTime milliseconds
		try {
			Thread.sleep(fixedTime);
		} catch (Exception e1) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
			g2.drawImage(myFlatImage, 0, 0, null);
			return;
		}

		// The message result is fading out during firtsTime milliseconds
		for (int i = 0 ; i < fadeIterations ; i++){
			try{
				Thread.sleep(fadeTime);
			}catch(Exception e){
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
				g2.drawImage(myFlatImage, 0, 0, null);
				return;
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2.drawImage(myFlatImage, 0, 0, null);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.00f));
		g2.drawImage(myFlatImage, 0, 0, null);
	}
}
