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
package es.mityc.appfacturae.ui.dialogs.importers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.ImportExportException;
import es.mityc.appfacturae.ui.components.CustomFileFilter;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.transitions.GifAnimatedIcon;
import es.mityc.appfacturae.ui.transitions.GifWaitIndicator;
import es.mityc.appfacturae.ui.transitions.WaitIndicator;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.ImportExportUtil;
import es.mityc.appfacturae.utils.constants.Constants;


public class ExportDataBaseDialog  extends JDialog {

	private static Log logger = LogFactory.getLog(ExportDataBaseDialog.class);
	
	private WaitIndicator waiter = null;
	private Thread th = null;
    private Thread waiterTh = null;
	
	public ExportDataBaseDialog (Frame parent, boolean modal) {
		super(parent, modal);
	    
	    this.setResizable(false);
	    this.setSize(390, 300);
	    this.setTitle(Constants.LANG.getString("ExportDB"));
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
	    
        init();
        
		setLocationRelativeTo(parent);
	}

	private void init() {
		
		BufferedImage imgTopBar2 = null;
        try {
        	imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
        } catch (IOException ioe) {
        	logger.error("Error during image charge: " + ioe.getMessage());
        }
		
		jPanelDataBaseExport = new JPanel();
		jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
        jLabelTopBar1 = new javax.swing.JLabel();
        jLabelTopBar2 = new javax.swing.JLabel();
	    jButtonReturn = new JButton();
		jButtonHelp = new JButton();
		jLabelTransition = new JLabel();
		jLabelExportDataBase = new javax.swing.JLabel();
    	jLabelExportDataBaseDir = new javax.swing.JLabel();
    	jTextFieldExportDataBase = new javax.swing.JTextField();
    	jButtonExportDataBaseDir = new javax.swing.JButton();
    	jButtonExportDataBase = new javax.swing.JButton();
    			
    	jLabelTransition.setVerticalAlignment(SwingConstants.CENTER);
    	jLabelTransition.setVerticalTextPosition(SwingConstants.TOP);
    	jLabelTransition.setHorizontalAlignment(SwingConstants.CENTER);
    	jLabelTransition.setHorizontalTextPosition(SwingConstants.CENTER);
    	jLabelTransition.setFont(Constants.FONT_PLAIN);
    	jLabelTransition.setForeground(Constants.FONT_COLOR);
    	
		jButtonHelp.setBorderPainted(false);
		jButtonHelp.setContentAreaFilled(false);
		jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/images/button_help.jpg")));
		jButtonHelp.setToolTipText(Constants.LANG.getString("Help"));
		jButtonHelp.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonHelp.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
		    }
			public void mouseExited(MouseEvent evt) {
				jButtonHelp.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonHelpActionPerformed();
			}
		});
		
		jButtonReturn.setBorderPainted(false);
		jButtonReturn.setContentAreaFilled(false);
		jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg")));
		jButtonReturn.setToolTipText(Constants.LANG.getString("Return"));
		jButtonReturn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonReturn.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
		    }
			public void mouseExited(MouseEvent evt) {
				jButtonReturn.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
		    	dispose();
			}
		});
		
		jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));
		
		jPanelTopBar.setBackground(Color.white);
        jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

        jLabelTopBar1.setFont(Constants.TITLE_FONT_ITALIC_LITTLE);
        jLabelTopBar1.setForeground(Constants.FONT_COLOR);
        jLabelTopBar1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTopBar1.setText(Constants.LANG.getString("TopBarMessage1")); 

        jLabelTopBar2.setFont(Constants.TITLE_FONT_ITALIC_LITTLE);
        jLabelTopBar2.setForeground(Constants.FONT_COLOR);
        jLabelTopBar2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTopBar2.setText(Constants.LANG.getString("TopBarMessage2")); 

        org.jdesktop.layout.GroupLayout jPanelTopBarLayout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
        jPanelTopBar.setLayout(jPanelTopBarLayout);
        jPanelTopBarLayout.setHorizontalGroup(
        		jPanelTopBarLayout.createSequentialGroup()
        			.addContainerGap()
        			.add(jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
        				.add(jLabelTopBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.add(jLabelTopBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			.addContainerGap()
        );
        jPanelTopBarLayout.setVerticalGroup(
        		jPanelTopBarLayout.createSequentialGroup()
        				.add(5,5,5)
        				.add(jLabelTopBar1)
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jLabelTopBar2)
        				.add(5,5,5)
        );
		
		/**********************************
         *          Cont. Panel 
         **********************************/
		
    	fcExp = new JFileChooser();
		CustomFileFilter filter = new CustomFileFilter();
        filter.addExtension("feDB");
        fcExp.setFileFilter(filter);
		
        fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		fd.setForeground(Constants.FONT_COLOR);       

		jLabelExportDataBase.setFont(Constants.FONT_BOLD);
    	jLabelExportDataBase.setForeground(Constants.FONT_COLOR);
    	jLabelExportDataBase.setText(Constants.LANG.getString("ExportDB"));
		
		jLabelExportDataBaseDir.setFont(Constants.FONT_PLAIN);
    	jLabelExportDataBaseDir.setForeground(Constants.FONT_COLOR);
    	jLabelExportDataBaseDir.setText(Constants.LANG.getString("ExportDBFile")+":"); 
		
    	jButtonExportDataBaseDir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_folder.jpg"))); 
    	jButtonExportDataBaseDir.setToolTipText(Constants.LANG.getString("Examine")); 
    	jButtonExportDataBaseDir.setBorderPainted(false);
    	jButtonExportDataBaseDir.setContentAreaFilled(false);
    	jButtonExportDataBaseDir.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			getFileToExport();
    		}
    	});
    	jButtonExportDataBaseDir.addMouseListener(new ButtonCursor());

    	jButtonExportDataBase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif")));
    	jButtonExportDataBase.setFont(Constants.TITLE_FONT);
    	jButtonExportDataBase.setForeground(Constants.FONT_COLOR);
    	jButtonExportDataBase.setText(Constants.LANG.getString("Export")); 
    	jButtonExportDataBase.setToolTipText(Constants.LANG.getString("Export")); 
    	jButtonExportDataBase.setBorderPainted(false);
    	jButtonExportDataBase.setContentAreaFilled(false);
    	jButtonExportDataBase.setMargin(new Insets(1, 1, 1, 1));
    	jButtonExportDataBase.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			dispose();
    			if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				MainWindow mw = MainWindow.getInstance();
        				try {
        					mw.getTransition().putTransitionPanel(Constants.LANG.getString("ExportingDataBase"));
        					putTransitionPanel(Constants.LANG.getString("ExportingDataBase"));

        	    			if (jTextFieldExportDataBase.getText() != null && !"".equals(jTextFieldExportDataBase.getText().trim())) {
        	    				
        	    	    		String fileName = new File(jTextFieldExportDataBase.getText()).getName();
        	    	    		if (!jTextFieldExportDataBase.getText().endsWith(".fedb")) {
        	    	    			
        	    	    			if (fileName.contains(".")) {
        	    	    				int lastIndex = jTextFieldExportDataBase.getText().lastIndexOf(".");
        	    	    				jTextFieldExportDataBase.setText(jTextFieldExportDataBase.getText().substring(0,lastIndex) + ".fedb");
        	    	    			} else
        	    	    				jTextFieldExportDataBase.setText(jTextFieldExportDataBase.getText() + ".fedb");
        	    	    		}
        	    	    		try{
	            	    			ImportExportUtil.exportDB(jTextFieldExportDataBase.getText());
	            	    			Constants.DIALOG.showOKExport(Constants.LANG.getString("OKMessageDataBaseExport"));
	            	    		}
	            	    		catch(ImportExportException e){
	            	    			if ("".equals(e.getMessage()))
	            	    				Constants.DIALOG.showOKExport(Constants.LANG.getString("OKMessageCanceledByUser"));
	            	    			else
	            	    				Constants.DIALOG.showErrorExport(e.getMessage());
	            	    		}
        	    			}
        	    	    	else
        	    	    		Constants.DIALOG.showErrorExport(Constants.LANG.getString("NOOKMessageExportEmptyFile"));
        	    	        mw.refreshProgressBar(100);
        				} finally {
        					removeTransitionPanel();
        					mw.getTransition().removeTransitionPanel();
        				}	
        			}});
        			th.start();
        		}
    		}
    	
  
    	});
    	jButtonExportDataBase.addMouseListener(new ButtonCursor());
    	
		org.jdesktop.layout.GroupLayout jPanelDataBaseExportLayout = new org.jdesktop.layout.GroupLayout(jPanelDataBaseExport);
		jPanelDataBaseExport.setLayout(jPanelDataBaseExportLayout);
    	jPanelDataBaseExportLayout.setHorizontalGroup(
    			jPanelDataBaseExportLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jPanelDataBaseExportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    					.add(jLabelExportDataBase)
    					.add(jLabelExportDataBaseDir)
    					.add(jPanelDataBaseExportLayout.createSequentialGroup()
    							.add(jTextFieldExportDataBase, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonExportDataBaseDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonExportDataBase)))
    				.addContainerGap()
    	);
    	jPanelDataBaseExportLayout.setVerticalGroup(
    			jPanelDataBaseExportLayout.createSequentialGroup()
    			.addContainerGap()
    			.add(jLabelExportDataBase)
    			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    			.add(jLabelExportDataBaseDir)
    			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    			.add(jPanelDataBaseExportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    					.add(jTextFieldExportDataBase, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    					.add(jButtonExportDataBaseDir)
    					.add(jButtonExportDataBase))
				.addContainerGap()
    	);
    	
		/**********************************
         *          Main Panel 
         **********************************/
                
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
        	layout.createSequentialGroup()
            .addContainerGap()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            	.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 370, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jPanelDataBaseExport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 370, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                	  .add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 280, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                      .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                	  .add(jButtonHelp,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE )
                      .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                      .add(jButtonReturn,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
             .addContainerGap()
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            	.addContainerGap()
            	.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            	.add(jPanelDataBaseExport)
            	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            	.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            		.add(jButtonReturn)
            		.add(jButtonHelp)
            		.add(fd))
            	.addContainerGap()
        );
        
        pack();

	}
	
	private class ButtonCursor extends MouseAdapter {
    	public void mouseEntered(java.awt.event.MouseEvent evt) {
    		ExportDataBaseDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
        	ExportDataBaseDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
    }
	
    private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_ImportExportWindow_"+Constants.LANG.getLocale().getLanguage()+".html");
    	if (helpFile == null) {
    		Constants.DIALOG.showErrorHelp();
	    	return;
    	}
    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
    }
	
    /**
     * Puts a semi-translucent panel in MainWindow showing a parametric message with a animated icon
     * @param message.- Message to show in the translucent panel
     */
    public void putTransitionPanel(String message) {
    	jLabelTransition.setText(message);
    	if (jLabelTransition.getIcon() == null) {
    		InputStream is = getClass().getResourceAsStream("/images/FactWaiter.gif");
    		if (is != null) {
    			GifAnimatedIcon animatedIcon = GifAnimatedIcon.getAnimatedIcon(is);
    			if (animatedIcon != null)
    				jLabelTransition.setIcon(animatedIcon);
    		}
    	}
    	waiterTh = new Thread(new Runnable() { public void run() {
    		waiter = new GifWaitIndicator(jPanelDataBaseExport, jLabelTransition);
    	}});
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
    
    private void getFileToExport() {
    	int returnVal = fcExp.showOpenDialog(this);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		jTextFieldExportDataBase.setText(fcExp.getSelectedFile().getAbsolutePath());
    		jTextFieldExportDataBase.setForeground(Constants.FONT_COLOR);
    	}
    }
    
	/** Declaration of variables **/
	
    private JFileChooser fcExp = new JFileChooser();
    private javax.swing.JTextField jTextFieldExportDataBase;
    private javax.swing.JLabel jLabelExportDataBase;
    private javax.swing.JLabel jLabelExportDataBaseDir;
    private javax.swing.JButton jButtonExportDataBaseDir;
    private javax.swing.JButton jButtonExportDataBase;
    
    private JPanel jPanelDataBaseExport; 
	private javax.swing.JPanel jPanelTopBar;
	
    private javax.swing.JLabel jLabelTopBar1;
    private javax.swing.JLabel jLabelTopBar2;
    private javax.swing.JLabel jLabelTransition = null;
  
    private JButton jButtonReturn = null;
    private JButton jButtonHelp = null;
    
    private FadingCanvas fd = null;
}
