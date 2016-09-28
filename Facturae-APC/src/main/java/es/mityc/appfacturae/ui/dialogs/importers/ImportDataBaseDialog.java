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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
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


public class ImportDataBaseDialog  extends JDialog {

	private static Log logger = LogFactory.getLog(ImportDataBaseDialog.class);
	
	private WaitIndicator waiter = null;
	private Thread th = null;
    private Thread waiterTh = null;
	
	public ImportDataBaseDialog (Frame parent, boolean modal) {
		super(parent, modal);
	    
	    this.setResizable(false);
	    this.setSize(390, 300);
	    this.setTitle(Constants.LANG.getString("ImportDB"));
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
		
		jPanelDataBaseImport = new JPanel();
		jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
        jLabelTopBar1 = new javax.swing.JLabel();
        jLabelTopBar2 = new javax.swing.JLabel();
	    jButtonReturn = new JButton();
		jButtonHelp = new JButton();
		jLabelTransition = new JLabel();
    	
    	jLabelImportDataBaseDir = new javax.swing.JLabel();
    	jTextFieldImportDataBase = new javax.swing.JTextField();
    	jButtonImportDataBaseDir = new javax.swing.JButton();
    	jButtonImportDataBase = new javax.swing.JButton();
    	checkboxSaveDrafts = new JCheckBox();
    	jLabelImportDataBase = new javax.swing.JLabel();
    	
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
		
        fcImp = new JFileChooser();
    	CustomFileFilter filter = new CustomFileFilter();
        filter.addExtension("feDB");
        fcImp.setFileFilter(filter);
        
        checkboxSaveDrafts.setText(Constants.LANG.getString("ImportSaveDrafts"));
    	checkboxSaveDrafts.setBackground(Constants.BKG_MAIN_COLOR);
    	checkboxSaveDrafts.setForeground(Constants.FONT_COLOR);
    	
		fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		fd.setForeground(Constants.FONT_COLOR);       

		jLabelImportDataBaseDir.setFont(Constants.FONT_PLAIN);
    	jLabelImportDataBaseDir.setForeground(Constants.FONT_COLOR);
    	jLabelImportDataBaseDir.setText(Constants.LANG.getString("ImportDBFile") + ":"); 
		
		jButtonImportDataBaseDir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_folder.jpg"))); 
    	jButtonImportDataBaseDir.setToolTipText(Constants.LANG.getString("Examine")); 
    	jButtonImportDataBaseDir.setBorderPainted(false);
    	jButtonImportDataBaseDir.setContentAreaFilled(false);
    	jButtonImportDataBaseDir.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			getFileToImport();
    		}
    	});
    	jButtonImportDataBaseDir.addMouseListener(new ButtonCursor());
    	
    	jButtonImportDataBase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif")));
    	jButtonImportDataBase.setFont(Constants.TITLE_FONT);
    	jButtonImportDataBase.setForeground(Constants.FONT_COLOR);
    	jButtonImportDataBase.setText(Constants.LANG.getString("Import")); 
    	jButtonImportDataBase.setToolTipText(Constants.LANG.getString("Import")); 
    	jButtonImportDataBase.setBorderPainted(false);
    	jButtonImportDataBase.setContentAreaFilled(false);
    	jButtonImportDataBase.setMargin(new Insets(1, 1, 1, 1));
    	jButtonImportDataBase.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			dispose();
    			if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				MainWindow mw = MainWindow.getInstance();
        				try {
        					mw.getTransition().putTransitionPanel(Constants.LANG.getString("ImportingDataBase"));
        					putTransitionPanel(Constants.LANG.getString("ImportingDataBase"));
        					try {
								ImportExportUtil.importDB(mw,jTextFieldImportDataBase.getText(),checkboxSaveDrafts.isSelected());
								Constants.DIALOG.showOKImport(Constants.LANG.getString("OKMessageDataBaseImport"));
							} catch (ImportExportException e) {
								if (!"".equals(e.getMessage()))
									Constants.DIALOG.showErrorImport(e.getMessage());
							} catch (DatabaseOperationException e) {
								Constants.DIALOG.showErrorImport(e.getMessage());
							}
        					mw.refreshProgressBar(100);
        				} finally {
        					removeTransitionPanel();
        					mw.getTransition().removeTransitionPanel();
							mw.refreshInvoices();
        				}
        			}});
        			th.start();
        		}
    		}
    	});
    	jButtonImportDataBase.addMouseListener(new ButtonCursor());
		
		jLabelImportDataBase.setFont(Constants.FONT_BOLD);
    	jLabelImportDataBase.setForeground(Constants.FONT_COLOR);
    	jLabelImportDataBase.setText(Constants.LANG.getString("ImportDB")); 
		
		org.jdesktop.layout.GroupLayout jPanelDataBaseImportLayout = new org.jdesktop.layout.GroupLayout(jPanelDataBaseImport);
    	jPanelDataBaseImport.setLayout(jPanelDataBaseImportLayout);
    	jPanelDataBaseImportLayout.setHorizontalGroup(
    			jPanelDataBaseImportLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jPanelDataBaseImportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    					.add(jLabelImportDataBase)
    					.add(jLabelImportDataBaseDir)
    					.add(jPanelDataBaseImportLayout.createSequentialGroup()
    							.add(jTextFieldImportDataBase, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonImportDataBaseDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonImportDataBase))
    					.add(checkboxSaveDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    				.addContainerGap()
    	);
    	jPanelDataBaseImportLayout.setVerticalGroup(
    			jPanelDataBaseImportLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jLabelImportDataBase)
			    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			    	.add(jLabelImportDataBaseDir)
			    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			    	.add(jPanelDataBaseImportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
			    		.add(jTextFieldImportDataBase, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
			    		.add(jButtonImportDataBaseDir)
			    		.add(jButtonImportDataBase))
			    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			    	.add(checkboxSaveDrafts)
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
                .add(jPanelDataBaseImport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 370, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
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
            	.add(jPanelDataBaseImport)
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
    		ImportDataBaseDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
        	ImportDataBaseDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
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
    		waiter = new GifWaitIndicator(jPanelDataBaseImport, jLabelTransition);
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
    
    private void getFileToImport() {
    	int returnVal = fcImp.showOpenDialog(this);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		jTextFieldImportDataBase.setText(fcImp.getSelectedFile().getAbsolutePath());
    		jTextFieldImportDataBase.setForeground(Constants.FONT_COLOR);
    	}
    }
    
	/** Declaration of variables **/
	
    private JFileChooser fcImp = new JFileChooser();
    private javax.swing.JTextField jTextFieldImportDataBase;
    private javax.swing.JLabel jLabelImportDataBase;
    private javax.swing.JLabel jLabelImportDataBaseDir;
    private javax.swing.JButton jButtonImportDataBaseDir;
    private javax.swing.JButton jButtonImportDataBase;
    private JCheckBox checkboxSaveDrafts;
    
    private JPanel jPanelDataBaseImport; 
	private javax.swing.JPanel jPanelTopBar;
	
    private javax.swing.JLabel jLabelTopBar1;
    private javax.swing.JLabel jLabelTopBar2;
    private javax.swing.JLabel jLabelTransition = null;
  
    private JButton jButtonReturn = null;
    private JButton jButtonHelp = null;
    
    private FadingCanvas fd = null;
}
