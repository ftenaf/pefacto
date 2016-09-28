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
package es.mityc.appfacturae.ui.windows;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.components.TranslucentPanel;
import es.mityc.appfacturae.utils.constants.Constants;

public class FinalWindow extends JWindow {
	
	private static final String BkgImgPath = "/images/FinalFacturae.jpg";
	private static final String CanvasBkgImgPath = "/images/CanvasFinalBkg.jpg";
	
	private static FinalWindow fw = null;
	
	public static FinalWindow getInstance() {
		if (fw == null)
			fw = new FinalWindow();
		
		return fw;
	}
	
	private FinalWindow() {
		super();
		loadImages();
		init();
	}
	
	private void loadImages() {
		/** Loading images */
        try {
        	imgLogoApp = ImageIO.read(FinalWindow.class.getResourceAsStream(BkgImgPath));
        	canvasBkg = ImageIO.read(FinalWindow.class.getResourceAsStream(CanvasBkgImgPath));
        } catch(IOException ioe) {}
	}

	private void init() {		
		mainPanel = new PicturedPanel(imgLogoApp, 1.0f);
		msgPanel = new TranslucentPanel();
		infPanel = new TranslucentPanel();
		jLabelMsg = new JLabel();
		canvas = new Canvas();

		jProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
		jProgressBar.setMaximum(100);
		jProgressBar.setIndeterminate(false);
		jProgressBar.setValue(0);

		jLabelMsg.setVerticalAlignment(SwingConstants.CENTER);
		jLabelMsg.setHorizontalAlignment(SwingConstants.LEFT);
		jLabelMsg.setFont(new java.awt.Font("Arial", Font.BOLD, 11));
		jLabelMsg.setForeground(Constants.FONT_COLOR);
		jLabelMsg.setBorder(new LineBorder(Constants.TRANSLUCENT_COLOR, 6));
		jLabelMsg.setText(Constants.LANG.getString("MsgClosingApp"));

		canvas.setFont(new java.awt.Font("Arial", Font.PLAIN, 10));
		canvas.setForeground(Constants.FONT_COLOR);
		canvas.setBackground(Constants.INITIAL_CANVAS_BKG);

		// Message panel
		msgPanel.setLayout(new GridBagLayout());

		GridBagConstraints stuff1Layout = new GridBagConstraints();
		stuff1Layout.gridx = 0;
		stuff1Layout.gridy = 0;
		stuff1Layout.fill = GridBagConstraints.VERTICAL;
		stuff1Layout.weighty = 1.0;

		msgPanel.add(new TranslucentPanel(), stuff1Layout);

		GridBagConstraints stuff2Layout = new GridBagConstraints();
		stuff2Layout.gridx = 5;
		stuff2Layout.gridy = 2;
		stuff2Layout.fill = GridBagConstraints.HORIZONTAL;
		stuff2Layout.weightx = 1.0;

		msgPanel.add(new TranslucentPanel(), stuff2Layout);

		GridBagConstraints msgLayout = new GridBagConstraints();
		msgLayout.gridx = 0;
		msgLayout.gridy = 2;
		msgLayout.fill = GridBagConstraints.HORIZONTAL;
		msgLayout.weightx = 0.2;
		msgLayout.ipady = 2;

		msgPanel.add(jLabelMsg, msgLayout);

		GridBagConstraints canvasLayout = new GridBagConstraints();
		canvasLayout.gridx = 0;
		canvasLayout.gridy = 3;
		canvasLayout.fill = GridBagConstraints.HORIZONTAL;
		canvasLayout.weightx = 0.2;
		canvasLayout.ipady = 20;

		msgPanel.add(canvas, canvasLayout);

		// ContentPane layout
		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints mainPanelLayout = new GridBagConstraints();
		mainPanelLayout.gridx = 0;
		mainPanelLayout.gridy = 0;
		mainPanelLayout.fill = GridBagConstraints.BOTH;
		mainPanelLayout.weightx = 1.0;
		mainPanelLayout.weighty = 0.9;

		mainPanel.add(msgPanel, mainPanelLayout);

		GridBagConstraints progressLayout = new GridBagConstraints();
		progressLayout.gridx = 0;
		progressLayout.gridy = 1;
		progressLayout.fill = GridBagConstraints.HORIZONTAL;
		progressLayout.weightx = 1.0;
		progressLayout.ipady = 1;

		mainPanel.add(jProgressBar, progressLayout);

		GridBagConstraints infoLayout = new GridBagConstraints();
		infoLayout.gridx = 0;
		infoLayout.gridy = 2;
		infoLayout.fill = GridBagConstraints.HORIZONTAL;
		infoLayout.anchor = GridBagConstraints.EAST;
		infoLayout.weightx = 1.0;
		infoLayout.ipady = 8;

		mainPanel.add(infPanel, infoLayout);

		add(mainPanel);

		setSize(452, 202);
		jProgressBar.setSize(452, 1);
		setLocationRelativeTo(null);
		setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
		getRootPane().setBorder(new LineBorder(Constants.INITIAL_BORDER_COLOR, 2, true));
		drawMsg(Constants.LANG.getString("MsgFinal"));
		Toolkit.getDefaultToolkit().sync();
		setVisible(true);
		Thread thPipe = new Thread(new Runnable() { public void run() {
			for (int i = 100; i > 0; ) {
				if (!isShowing()) continue;
				refreshProgressBar(100 - i);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
				i--;
			}
			
			fw.setVisible(false);
    		fw.dispose();
    		fw = null;
    		Runtime.getRuntime().exit(0); // Force JVM to exit
		}});
		thPipe.start();
	}
	
    /**
     * Updates the bar state for progress representation
     * @param val.- Rate for completed progress ([0, 100])
     */
    public void refreshProgressBar(int val) {
    	if (val <= 0)
    		val = 0;
    	else if (val > 100)
    		val = 100;

    	refreshPipe(val);;
    	jProgressBar.setValue(val);	
    }
    
    private void refreshPipe(int val) {
    	if (val == 5)
    		drawMsg(Constants.LANG.getString("MsgFinal"));
    	else if (val == 30)
    		drawMsg(Constants.LANG.getString("MsgCloseComp"));
    	else if (val == 50)
    		drawMsg(Constants.LANG.getString("MsgFreeMem"));
    	else if (val == 60)
    		drawMsg(Constants.LANG.getString("MsgSaveDB"));
    	else if (val == 95)
    		drawMsg(Constants.LANG.getString("MsgExit"));
    }
    
    private void drawMsg(String msg) {
    	Graphics2D g2 = (Graphics2D)canvas.getGraphics();
    	if (g2 != null){
    		canvas.update(g2);
    		g2.drawImage(canvasBkg, 0, 0, null);
    		g2.drawString(msg, 20, 15);
    	}
    }
	
	// Elements
	private JPanel mainPanel = null;
	private JPanel msgPanel = null;
	private JPanel infPanel = null;
	private JLabel jLabelMsg = null;
	private Canvas canvas = null;
	
	private JProgressBar jProgressBar = null;
	private static Image imgLogoApp = null;
	private static Image canvasBkg = null;
}