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

import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.importers.ImportInvoiceResult;
import es.mityc.appfacturae.importers.InvoiceImporter;
import es.mityc.appfacturae.importers.transformers.DraftInvoiceTransformer;
import es.mityc.appfacturae.importers.transformers.DraftRectifiedInvoiceTransformer;
import es.mityc.appfacturae.ui.components.CustomFileFilter;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.ImportExportResult;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.transitions.GifAnimatedIcon;
import es.mityc.appfacturae.ui.transitions.GifWaitIndicator;
import es.mityc.appfacturae.ui.transitions.WaitIndicator;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;


public class ImportInvoicesDialog  extends JDialog {

	private static Log logger = LogFactory.getLog(ImportInvoicesDialog.class);
	
	private WaitIndicator waiter = null;
	private Thread th = null;
    private Thread waiterTh = null;
    
    private File[] invoicesFiles = null;
	
	public ImportInvoicesDialog (Frame parent, boolean modal) {
		super(parent, modal);
	    
	    this.setResizable(false);
	    this.setSize(390, 300);
	    this.setTitle(Constants.LANG.getString("ImportInvoices"));
	    this.setDefaultCloseOperation(HIDE_ON_CLOSE);		
	    
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
		
		jPanelInvoicesImport = new javax.swing.JPanel();
		jLabelImportInvoiceState = new javax.swing.JLabel();
		jLabelImportInvoiceDir = new javax.swing.JLabel();
		jTextFieldImportInvoice = new javax.swing.JTextField();
		jLabelImportInvoice = new javax.swing.JLabel();
		jButtonImportInvoiceDir = new javax.swing.JButton();
		jButtonImportInvoice = new javax.swing.JButton();
		jComboBoxImport = new JComboBox();
		jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
        jLabelTopBar1 = new javax.swing.JLabel();
        jLabelTopBar2 = new javax.swing.JLabel();
	    jButtonReturn = new JButton();
		jButtonHelp = new JButton();
		jLabelTransition = new JLabel();
    	
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
		
		fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		fd.setForeground(Constants.FONT_COLOR);       

		jComboBoxImport.setModel(new DefaultComboBoxModel(new String[]{Constants.LANG.getString("DraftStr"),Constants.LANG.getString("Issued"),Constants.LANG.getString("Sent"),Constants.LANG.getString("ReceivedStr"),Constants.LANG.getString("CorrectiveDraft"),Constants.LANG.getString("CorrectiveIssued"),Constants.LANG.getString("CorrectiveSent") }));
		
		fcImpInv = new JFileChooser();
		CustomFileFilter filter2 = new CustomFileFilter();
		filter2.addExtension("xml");
		filter2.addExtension("xsig");
		fcImpInv.setFileFilter(filter2);
		fcImpInv.setMultiSelectionEnabled(true);

		jLabelImportInvoiceState.setFont(Constants.FONT_PLAIN);
		jLabelImportInvoiceState.setForeground(Constants.FONT_COLOR);
		jLabelImportInvoiceState.setText(Constants.LANG.getString("StateImportMessage") + ":"); 

		jLabelImportInvoiceDir.setFont(Constants.FONT_PLAIN);
		jLabelImportInvoiceDir.setForeground(Constants.FONT_COLOR);
		jLabelImportInvoiceDir.setText(Constants.LANG.getString("ImportInvoiceFile") + ":");

		jTextFieldImportInvoice.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldImportInvoice.setEditable(false);
		
		jButtonImportInvoiceDir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_folder.jpg"))); 
		jButtonImportInvoiceDir.setToolTipText(Constants.LANG.getString("Examine")); 
		jButtonImportInvoiceDir.setBorderPainted(false);
		jButtonImportInvoiceDir.setContentAreaFilled(false);
		jButtonImportInvoiceDir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getInvoiceFilesToImport();
			}
		});
		jButtonImportInvoiceDir.addMouseListener(new ButtonCursor());

		jButtonImportInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif")));
		jButtonImportInvoice.setFont(Constants.TITLE_FONT);
		jButtonImportInvoice.setForeground(Constants.FONT_COLOR);
		jButtonImportInvoice.setText(Constants.LANG.getString("Import")); 
		jButtonImportInvoice.setToolTipText(Constants.LANG.getString("ImportInvoices")); 
		jButtonImportInvoice.setBorderPainted(false);
		jButtonImportInvoice.setContentAreaFilled(false);
		jButtonImportInvoice.setMargin(new Insets(1, 1, 1, 1));
		jButtonImportInvoice.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String validError = validateForm();
				if ("".equals(validError)){
					dispose();
					if (th == null || !th.isAlive()) {
						th = new Thread(new Runnable() { public void run() {
							MainWindow mw = MainWindow.getInstance();
							try {
								mw.getTransition().putTransitionPanel(Constants.LANG.getString("ImportInvoicesProcess"));
								putTransitionPanel(Constants.LANG.getString("ImportInvoicesProcess"));

								Collection<ImportInvoiceResult> results = new ArrayList<ImportInvoiceResult>();
								InvoiceImporter invoiceImporter = new InvoiceImporter();
								switch (jComboBoxImport.getSelectedIndex()){
								case 0:
									results = invoiceImporter.importInvoices(invoicesFiles, InvoiceClassType.OO, InvoiceStatusType.D, new DraftInvoiceTransformer());
									break;
								case 1:
									results = invoiceImporter.importInvoices(invoicesFiles, InvoiceClassType.OO, InvoiceStatusType.I, null);
									break;
								case 2:
									results = invoiceImporter.importInvoices(invoicesFiles, InvoiceClassType.OO, InvoiceStatusType.S, null);
									break;
								case 3:
									results = invoiceImporter.importInvoices(invoicesFiles, InvoiceClassType.OO, InvoiceStatusType.R, null);
									break;
								case 4:
									results = invoiceImporter.importInvoices(invoicesFiles, InvoiceClassType.OR, InvoiceStatusType.D, new DraftRectifiedInvoiceTransformer());
									break;
								case 5:
									results = invoiceImporter.importInvoices(invoicesFiles, InvoiceClassType.OR, InvoiceStatusType.I, null);
									break;
								case 6:
									results = invoiceImporter.importInvoices(invoicesFiles, InvoiceClassType.OR, InvoiceStatusType.S, null);
									break;
								default:
									Constants.DIALOG.showErrorImport("");
								}

								ArrayList<ImportExportResult> errors = new ArrayList<ImportExportResult>();
								int ok = 0;
								for (Iterator<ImportInvoiceResult> it = results.iterator(); it.hasNext(); ) {
									ImportInvoiceResult result = (ImportInvoiceResult)it.next();
									if (result.getResult() == ImportInvoiceResult.ImportInvoiceResultType.CORRECT){
										errors.add(new ImportExportResult(ImportExportResult.ImportErrorLevel.OK, result.getOldId(), Constants.LANG.getString("OKImportResultMessage")+ ": " + result.getNewId()));
										ok++;
									}
									else{
										errors.add(new ImportExportResult(ImportExportResult.ImportErrorLevel.ERROR, result.getOldId(), result.getObservations()));
									}
								}

								if (ok == results.size())
									Constants.DIALOG.showOKImport(Constants.LANG.getString("ImportedInvoices") + ": " + ok + "/" + ok);
								else{
									ImportExportErrorsDialog ied = new ImportExportErrorsDialog(mw, true, errors, ok);
									ied.setVisible(true);
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
				else{
					fd.showMessage(validError, Constants.ERROR_MSG_COLOR);
				}
			}
		});
		jButtonImportInvoice.addMouseListener(new ButtonCursor());

		jLabelImportInvoice.setFont(Constants.FONT_BOLD);
		jLabelImportInvoice.setForeground(Constants.FONT_COLOR);
		jLabelImportInvoice.setText(Constants.LANG.getString("ImportInvoices")); 

		org.jdesktop.layout.GroupLayout jPanelInvoicesImportLayout = new org.jdesktop.layout.GroupLayout(jPanelInvoicesImport);
    	jPanelInvoicesImport.setLayout(jPanelInvoicesImportLayout);
    	jPanelInvoicesImportLayout.setHorizontalGroup(
    			jPanelInvoicesImportLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jPanelInvoicesImportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    					.add(jLabelImportInvoice)
    					.add(jLabelImportInvoiceDir)
    					.add(jPanelInvoicesImportLayout.createSequentialGroup()
    							.add(jTextFieldImportInvoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonImportInvoiceDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    							.add(jButtonImportInvoice))
    					.add(jLabelImportInvoiceState)
    					.add(jComboBoxImport,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,222,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    	    			
    				)
    				.addContainerGap()
    	);
    	jPanelInvoicesImportLayout.setVerticalGroup(
    			jPanelInvoicesImportLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jLabelImportInvoice)
			    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			    	.add(jLabelImportInvoiceDir)
			    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			    	.add(jPanelInvoicesImportLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
			    			.add(jTextFieldImportInvoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
			    			.add(jButtonImportInvoiceDir)
			    			.add(jButtonImportInvoice))
			    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			    	.add(jLabelImportInvoiceState)
			    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			    	.add(jComboBoxImport,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
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
                .add(jPanelInvoicesImport, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 370, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
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
            	.add(jPanelInvoicesImport)
            	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            	.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            		.add(jButtonReturn)
            		.add(jButtonHelp)
            		.add(fd))
            	.addContainerGap()
        );
        
        pack();
		
	}
	
	private String validateForm(){
		if (jTextFieldImportInvoice.getText() == null || "".equals(jTextFieldImportInvoice.getText().trim())){
			jTextFieldImportInvoice.setBackground(Constants.BKG_ERROR_COLOR);
			return Constants.LANG.getString("ParameterRequiredMessage");
		}
		return "";
	}
	
	private class ButtonCursor extends MouseAdapter {
    	public void mouseEntered(java.awt.event.MouseEvent evt) {
            ImportInvoicesDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
        	ImportInvoicesDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
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
    		waiter = new GifWaitIndicator(jPanelInvoicesImport, jLabelTransition);
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
    
    private void getInvoiceFilesToImport() {
    	int returnVal = fcImpInv.showOpenDialog(this);
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		jTextFieldImportInvoice.setBackground(Constants.BKG_MAIN_COLOR);
    		invoicesFiles = fcImpInv.getSelectedFiles();
    		String value = "";
    		for (int i = 0; i < invoicesFiles.length ; i++){
    			if ("".equals(value))
    				value = invoicesFiles[i].getName();
    			else
    				value += ";" + invoicesFiles[i].getName();
    		}
    		jTextFieldImportInvoice.setText(value);
    		jTextFieldImportInvoice.setForeground(Constants.FONT_COLOR);
    	}
    }
    
	/** Declaration of variables **/
	private JPanel jPanelInvoicesImport; 
	private javax.swing.JPanel jPanelTopBar;
	
    private javax.swing.JLabel jLabelImportInvoice;
    private javax.swing.JLabel jLabelImportInvoiceDir;
    private javax.swing.JLabel jLabelImportInvoiceState;
    private javax.swing.JLabel jLabelTopBar1;
    private javax.swing.JLabel jLabelTopBar2;
    private javax.swing.JLabel jLabelTransition = null;
    
    private javax.swing.JTextField jTextFieldImportInvoice;
    
    private JComboBox jComboBoxImport;
    
    private javax.swing.JButton jButtonImportInvoiceDir;
    private javax.swing.JButton jButtonImportInvoice;
    private JButton jButtonReturn = null;
    private JButton jButtonHelp = null;
    
    private JFileChooser fcImpInv = new JFileChooser();
    
    private FadingCanvas fd = null;
}
