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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.ImportExportException;
import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.importers.ExportInvoiceResult;
import es.mityc.appfacturae.importers.InvoiceExporter;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.ImportExportResult;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.transitions.GifAnimatedIcon;
import es.mityc.appfacturae.ui.transitions.GifWaitIndicator;
import es.mityc.appfacturae.ui.transitions.WaitIndicator;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FileUtil;


public class ExportInvoicesDialog  extends JDialog {

	private static Log logger = LogFactory.getLog(ExportInvoicesDialog.class);
	
	private WaitIndicator waiter = null;
	private Thread th = null;
    private Thread waiterTh = null;
	
	public ExportInvoicesDialog (Frame parent, boolean modal) {
		super(parent, modal);
	    
	    this.setResizable(false);
	    this.setSize(390, 300);
	    this.setTitle(Constants.LANG.getString("ExportInvoices"));
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
		
        jPanelInvoicesExport = new JPanel();
		jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
        jLabelTopBar1 = new javax.swing.JLabel();
        jLabelTopBar2 = new javax.swing.JLabel();
	    jButtonReturn = new JButton();
		jButtonHelp = new JButton();
		jLabelTransition = new JLabel();
		
    	jLabelExportInvoiceState = new javax.swing.JLabel();
    	jTextFieldExportInvoice = new javax.swing.JTextField();
    	jLabelExportInvoice = new javax.swing.JLabel();
    	jLabelExportInvoiceDir = new javax.swing.JLabel();
    	jButtonExportInvoiceDir = new javax.swing.JButton();
        jButtonExportInvoice = new javax.swing.JButton();
        jComboBoxExport = new JComboBox();
    	
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
		
    	jComboBoxExport.setModel(new DefaultComboBoxModel(new String[]{Constants.LANG.getString("ALL"),Constants.LANG.getString("DraftStr"),Constants.LANG.getString("Issued"),Constants.LANG.getString("Sent"),Constants.LANG.getString("ReceivedStr"),Constants.LANG.getString("CorrectiveDraft"),Constants.LANG.getString("CorrectiveIssued"),Constants.LANG.getString("CorrectiveSent") }));
    	jComboBoxExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
    	
        fcExpInv = new JFileChooser();
		fcExpInv.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		fd.setForeground(Constants.FONT_COLOR);       

		jLabelExportInvoiceState.setFont(Constants.FONT_PLAIN);
    	jLabelExportInvoiceState.setForeground(Constants.FONT_COLOR);
    	jLabelExportInvoiceState.setText(Constants.LANG.getString("StateExportMessage") + ":"); 
		
		jButtonExportInvoiceDir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_folder.jpg"))); 
    	jButtonExportInvoiceDir.setToolTipText(Constants.LANG.getString("Examine")); 
    	jButtonExportInvoiceDir.setBorderPainted(false);
    	jButtonExportInvoiceDir.setContentAreaFilled(false);
    	jButtonExportInvoiceDir.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			getDestinyFolderToExportInvoices();
    		}
    	});
    	jButtonExportInvoiceDir.addMouseListener(new ButtonCursor());
		
		jButtonExportInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif")));
    	jButtonExportInvoice.setFont(Constants.TITLE_FONT);
    	jButtonExportInvoice.setForeground(Constants.FONT_COLOR);
    	jButtonExportInvoice.setText(Constants.LANG.getString("Export")); 
    	jButtonExportInvoice.setToolTipText(Constants.LANG.getString("ExportInvoices")); 
    	jButtonExportInvoice.setBorderPainted(false);
    	jButtonExportInvoice.setContentAreaFilled(false);
    	jButtonExportInvoice.setMargin(new Insets(1, 1, 1, 1));
    	jButtonExportInvoice.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			
    			if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				MainWindow mw = MainWindow.getInstance();
        				try {
        					mw.getTransition().putTransitionPanel(Constants.LANG.getString("ExportInvoicesProcess"));
        					putTransitionPanel(Constants.LANG.getString("ExportInvoicesProcess"));
        					Collection<ExportInvoiceResult> results = new ArrayList<ExportInvoiceResult>();
							InvoiceExporter invoiceExporter = new InvoiceExporter();
							File destinationDirectory = FileUtil.createNewDirectory(new File(jTextFieldExportInvoice.getText()), Constants.APP_PROP.getProperty("invoices_export_dir"), "yyyyMMdd");
        					try {
        						switch (jComboBoxExport.getSelectedIndex()){
        						case 0:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.D,Constants.LANG.getString("Drafts"));
        							results.addAll(invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.I,Constants.LANG.getString("Issueds")));
        							results.addAll(invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.S,Constants.LANG.getString("Sents")));
        							results.addAll(invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.R,Constants.LANG.getString("Receiveds")));
        							results.addAll(invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OR, InvoiceStatusType.D, Constants.LANG.getString("CorrectiveDrafts")));
        							results.addAll(invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OR, InvoiceStatusType.I, Constants.LANG.getString("CorrectiveIssueds")));
        							results.addAll(invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OR, InvoiceStatusType.S, Constants.LANG.getString("CorrectiveSents")));
        							break;
        						case 1:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.D,Constants.LANG.getString("Drafts"));
        							break;
        						case 2:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.I,Constants.LANG.getString("Issueds"));
        							break;
        						case 3:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.S,Constants.LANG.getString("Sents"));
        							break;
        						case 4:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OO, InvoiceStatusType.R,Constants.LANG.getString("Receiveds"));
        							break;
        						case 5:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OR, InvoiceStatusType.D, Constants.LANG.getString("CorrectiveDrafts"));
        							break;
        						case 6:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OR, InvoiceStatusType.I, Constants.LANG.getString("CorrectiveIssueds"));
        							break;
        						case 7:
        							results = invoiceExporter.exportInvoices(destinationDirectory, InvoiceClassType.OR, InvoiceStatusType.S, Constants.LANG.getString("CorrectiveSents"));
        							break;
        						default:
        							break;
        						}
        						
        						ArrayList<ImportExportResult> errors = new ArrayList<ImportExportResult>();
    							int ok = 0;
    							for (Iterator<ExportInvoiceResult> it = results.iterator(); it.hasNext(); ) {
    								ExportInvoiceResult result = (ExportInvoiceResult)it.next();
    								if (result.getResult() == ExportInvoiceResult.ExportInvoiceResultType.CORRECT){
    									errors.add(new ImportExportResult(ImportExportResult.ImportErrorLevel.OK, result.getId(), result.getObservations()));
    									ok++;
    								}
    								else{
    									errors.add(new ImportExportResult(ImportExportResult.ImportErrorLevel.ERROR, result.getId(), result.getObservations()));
    								}
    							}

    							if (ok == results.size())
    								Constants.DIALOG.showOKExport(Constants.LANG.getString("ExportedInvoices") + ": " + ok + "/" + ok);
    							else{
    								ImportExportErrorsDialog ied = new ImportExportErrorsDialog(mw, true, errors, ok);
    								ied.setVisible(true);
    							}
        					
        					} catch (ImportExportException e) {
        						logger.error("Error exporting invoices: " + e.getMessage());
        						Constants.DIALOG.showErrorExport("");
        					}
        					
        					mw.refreshProgressBar(100);
        				} catch (FileNotFoundException e) {
        					Constants.DIALOG.showErrorExport("");
						} finally {
        					removeTransitionPanel();
        					mw.getTransition().removeTransitionPanel();
        				}
        			}});
        			th.start();
        		}
    		}
    	});
    	jButtonExportInvoice.addMouseListener(new ButtonCursor());
		
		jLabelExportInvoice.setFont(Constants.FONT_BOLD);
    	jLabelExportInvoice.setForeground(Constants.FONT_COLOR);
    	jLabelExportInvoice.setText(Constants.LANG.getString("ExportInvoices")); 
		
		jLabelExportInvoiceDir.setFont(Constants.FONT_PLAIN);
    	jLabelExportInvoiceDir.setForeground(Constants.FONT_COLOR);
    	jLabelExportInvoiceDir.setText(Constants.LANG.getString("ExportInvoiceDir")+":"); 
    	
		org.jdesktop.layout.GroupLayout jPanelInvoicesExportLayout = new org.jdesktop.layout.GroupLayout(jPanelInvoicesExport);
		jPanelInvoicesExport.setLayout(jPanelInvoicesExportLayout);
    	jPanelInvoicesExportLayout.setHorizontalGroup(
    			jPanelInvoicesExportLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jPanelInvoicesExportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    					.add(jLabelExportInvoice)
    					.add(jLabelExportInvoiceDir)
    					.add(jPanelInvoicesExportLayout.createSequentialGroup()
    							.add(jTextFieldExportInvoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonExportInvoiceDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonExportInvoice))
    					.add(jLabelExportInvoiceState)
    					.add(jComboBoxExport,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,222,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				)
    				.addContainerGap()
    	);
    	jPanelInvoicesExportLayout.setVerticalGroup(
    			jPanelInvoicesExportLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jLabelExportInvoice)
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(jLabelExportInvoiceDir)
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(jPanelInvoicesExportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    						.add(jTextFieldExportInvoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    						.add(jButtonExportInvoiceDir)
    						.add(jButtonExportInvoice))
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(jLabelExportInvoiceState)
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(jComboBoxExport,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
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
                .add(jPanelInvoicesExport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 370, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
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
            	.add(jPanelInvoicesExport)
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
    		ExportInvoicesDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
        	ExportInvoicesDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
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
    		waiter = new GifWaitIndicator(jPanelInvoicesExport, jLabelTransition);
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
    
    private void getDestinyFolderToExportInvoices() {
    	int returnVal = fcExpInv.showOpenDialog(this);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		jTextFieldExportInvoice.setText(fcExpInv.getSelectedFile().getAbsolutePath());
    		jTextFieldExportInvoice.setForeground(Constants.FONT_COLOR);
    	}
    }

    
	/** Declaration of variables **/
	
    private JFileChooser fcExpInv = new JFileChooser();
    private JComboBox jComboBoxExport;
    private javax.swing.JTextField jTextFieldExportInvoice;
    private javax.swing.JLabel jLabelExportInvoiceState;
    private javax.swing.JLabel jLabelExportInvoice;
    private javax.swing.JLabel jLabelExportInvoiceDir;
    private javax.swing.JButton jButtonExportInvoiceDir;
    private javax.swing.JButton jButtonExportInvoice;
    
    private JPanel jPanelInvoicesExport; 
	private javax.swing.JPanel jPanelTopBar;
	
    private javax.swing.JLabel jLabelTopBar1;
    private javax.swing.JLabel jLabelTopBar2;
    private javax.swing.JLabel jLabelTransition = null;
  
    private JButton jButtonReturn = null;
    private JButton jButtonHelp = null;
    
    private FadingCanvas fd = null;
}
