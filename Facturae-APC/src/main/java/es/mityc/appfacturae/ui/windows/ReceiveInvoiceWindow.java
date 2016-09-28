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

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.ReceiveException;
import es.mityc.appfacturae.ui.components.CustomFileFilter;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.ui.renderers.ReceiveImageRenderer;
import es.mityc.appfacturae.ui.transitions.GifAnimatedIcon;
import es.mityc.appfacturae.ui.transitions.GifWaitIndicator;
import es.mityc.appfacturae.ui.transitions.WaitIndicator;
import es.mityc.appfacturae.utils.ReceiveUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FileUtil;

public class ReceiveInvoiceWindow extends javax.swing.JFrame {
    
    private static Log logger = LogFactory.getLog(ReceiveInvoiceWindow.class);
    
    // Transition panel
    private WaitIndicator waiter = null;
    private JLabel jLabelTransition = null;
    private Thread waiterTh = null;
    private Thread th = null;
    
    private static ReceiveInvoiceWindow receiveWindow = null;
    
    /**
     * Singleton pattern
     */
    static public ReceiveInvoiceWindow getInstance() {
    	if (receiveWindow == null)
    		receiveWindow = new ReceiveInvoiceWindow();
    	
    	return receiveWindow;
    }
    
    /** Creates new form JFramePrincipal */
    private ReceiveInvoiceWindow() {
    	
    	/** Loading logo image */ 
    	try{
            imgLogoApp = ImageIO.read(getClass().getResource("/images/logoapp.jpg"));
        }
        catch(IOException ioe){
            logger.error("An exception occurred when loading the images: " + ioe.getMessage());
        }
        
        initComponents();
        
        filter = new CustomFileFilter();
        filter.addExtension("xml");
        filter.addExtension("xsig");
        filter.setDescription(Constants.LANG.getString("InvoiceExtension"));
        fc.setFileFilter(filter);
        
        this.setSize(462, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setIconImage(imgLogoApp);
        
        try {
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/logoapp.jpg")));
        } catch (IOException ex) {
            Logger.getLogger(ReceiveInvoiceWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonReturnActionPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
        
    }
    
    private void initComponents() {

    	mainPanel = new javax.swing.JPanel();
    	try {
			imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
		} catch (IOException ioe) {
            Logger.getLogger(ReceiveInvoiceWindow.class.getName()).log(Level.SEVERE, null, ioe);
		}
    	jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
    	jPanelTableData = new javax.swing.JPanel();
    	
    	jLabelTopBarMess1 = new javax.swing.JLabel();
    	jLabel1TopBarMess2 = new javax.swing.JLabel();
    	jLabelFadingCanvas = new javax.swing.JLabel();
    	jLabelPath = new javax.swing.JLabel();
    	
    	jButtonReturn = new javax.swing.JButton();
    	jButtonReceive = new javax.swing.JButton();
    	jButtonConventional = new javax.swing.JButton();
    	jButtonExamine = new javax.swing.JButton();
    	jButtonHelp = new javax.swing.JButton();
    	
    	jScrollPaneTableData = new javax.swing.JScrollPane();
    	jTableData = new javax.swing.JTable();
    	jTextFieldPath = new javax.swing.JTextField();
    	jTextAreaConventionalInvoice = new javax.swing.JTextArea();
    	fc = new JFileChooser();
    	
    	this.setTitle(Constants.LANG.getString("ReceiveInvoice")); 
    	
    	// Transition label
        jLabelTransition = new JLabel();
    	
    	jLabelTransition.setVerticalAlignment(SwingConstants.CENTER);
    	jLabelTransition.setVerticalTextPosition(SwingConstants.TOP);
    	jLabelTransition.setHorizontalAlignment(SwingConstants.CENTER);
    	jLabelTransition.setHorizontalTextPosition(SwingConstants.CENTER);
    	jLabelTransition.setFont(Constants.FONT_PLAIN);
    	jLabelTransition.setForeground(Constants.FONT_COLOR);

    	mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
    	mainPanel.setMaximumSize(new java.awt.Dimension(500, 480));
    	mainPanel.setMinimumSize(new java.awt.Dimension(500, 480));
    	mainPanel.setPreferredSize(new java.awt.Dimension(500, 480));

    	jPanelTopBar.setBackground(new java.awt.Color(255, 255, 255));
    	jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

    	jLabelTopBarMess1.setFont(Constants.TITLE_FONT_ITALIC);
    	jLabelTopBarMess1.setForeground(Constants.FONT_COLOR);
    	jLabelTopBarMess1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    	jLabelTopBarMess1.setText(Constants.LANG.getString("TopBarMessage1")); 

    	jLabel1TopBarMess2.setFont(Constants.TITLE_FONT_ITALIC);
    	jLabel1TopBarMess2.setForeground(Constants.FONT_COLOR);
    	jLabel1TopBarMess2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    	jLabel1TopBarMess2.setText(Constants.LANG.getString("TopBarMessage2")); 

    	org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
    	jPanelTopBar.setLayout(jPanel5Layout);
    	jPanel5Layout.setHorizontalGroup(
    			jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    			.add(jPanel5Layout.createSequentialGroup()
    					.addContainerGap(152, Short.MAX_VALUE)
    					.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    							.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
    									.add(jLabel1TopBarMess2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 316, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    									.add(8, 8, 8))
    									.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
    											.add(jLabelTopBarMess1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 307, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    											.add(12, 12, 12))))
    	);
    	jPanel5Layout.setVerticalGroup(
    			jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    			.add(jPanel5Layout.createSequentialGroup()
    					.add(4, 4, 4)
    					.add(jLabelTopBarMess1)
    					.add(3, 3, 3)
    					.add(jLabel1TopBarMess2)
    					.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    	);

    	jPanelTableData.setBackground(Constants.BKG_MAIN_COLOR);
    	jPanelTableData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("InformationAboutTheInvoiceReceived"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

    	jScrollPaneTableData.setBackground(Constants.BKG_MAIN_COLOR);
    	jScrollPaneTableData.setAutoscrolls(true);
    	jScrollPaneTableData.setOpaque(false);

    	jTableData.setFont(Constants.FONT_PLAIN);
    	jTableData.setForeground(Constants.FONT_COLOR);
    	jTableData.setModel(new javax.swing.table.DefaultTableModel(
    			new Object [][] {
    					{Constants.LANG.getString("SchemaValidation"), ""},
    					{Constants.LANG.getString("SignValidation"), ""},
    					{Constants.LANG.getString("AccountantValidation"), ""},
    					{Constants.LANG.getString("FacturaeFormat"), ""},
    					{Constants.LANG.getString("SignPolicy"), ""},
    					{Constants.LANG.getString("Issuer"), ""},
    					{Constants.LANG.getString("TaxIdentificationNumberIssuer"), ""},
    					{Constants.LANG.getString("Receiver"), ""},
    					{Constants.LANG.getString("TaxIdentificationNumberReceiver"), ""},
    					{Constants.LANG.getString("Item"), ""},
    					{Constants.LANG.getString("Amount"), ""},
    					{Constants.LANG.getString("IssueDate"), null}

    			},
    			new String [] {
    					Constants.LANG.getString("Data"), Constants.LANG.getString("Value")
    			}
    	) {
    		boolean[] canEdit = new boolean [] {
    				false, false
    		};

    		public boolean isCellEditable(int rowIndex, int columnIndex) {
    			return canEdit [columnIndex];
    		}
    	});
    	jTableData.setAutoscrolls(false);
    	jTableData.setGridColor(Constants.SELECTION_COLOR);
    	jTableData.setRequestFocusEnabled(false);
    	jTableData.setRowSelectionAllowed(false);
    	jTableData.getTableHeader().setReorderingAllowed(false);
    	jScrollPaneTableData.setViewportView(jTableData);
    	jTableData.getColumnModel().getColumn(0).setResizable(false);
    	jTableData.getColumnModel().getColumn(0).setHeaderValue(Constants.LANG.getString("Data")); 
    	jTableData.getColumnModel().getColumn(1).setResizable(false);
    	jTableData.getColumnModel().getColumn(1).setHeaderValue(Constants.LANG.getString("Value")); 
    	for (int i = 0 ; i < jTableData.getColumnCount() ; i++){
    		jTableData.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
    	}
    	ReceiveImageRenderer lir= new ReceiveImageRenderer();
        jTableData.getColumnModel().getColumn(1).setCellRenderer(lir);

    	org.jdesktop.layout.GroupLayout jPanel26Layout = new org.jdesktop.layout.GroupLayout(jPanelTableData);
    	jPanelTableData.setLayout(jPanel26Layout);
    	jPanel26Layout.setHorizontalGroup(
    			jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    			.add(jPanel26Layout.createSequentialGroup()
    					.addContainerGap()
    					.add(jScrollPaneTableData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 405, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    					.addContainerGap())
    	);
    	jPanel26Layout.setVerticalGroup(
    			jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    			.add(jPanel26Layout.createSequentialGroup()
    					.addContainerGap()
    					.add(jScrollPaneTableData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,  212, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    					.addContainerGap(21, Short.MAX_VALUE))
    	);

    	jLabelPath.setFont(Constants.FONT_PLAIN);
    	jLabelPath.setForeground(Constants.FONT_COLOR);
    	jLabelPath.setText(Constants.LANG.getString("Path")); 

    	jTextFieldPath.setFont(Constants.FONT_PLAIN);
        jTextFieldPath.setForeground(Constants.FONT_COLOR);   
    	
    	jTextAreaConventionalInvoice.setFont(Constants.FONT_ITALIC);
    	jTextAreaConventionalInvoice.setForeground(Constants.FONT_COLOR);
    	jTextAreaConventionalInvoice.setBackground(Constants.BKG_MAIN_COLOR);
    	jTextAreaConventionalInvoice.setText(Constants.LANG.getString("MessageConventionalInvoice")); 

    	jButtonConventional.setFont(Constants.FONT_BOLD);
    	jButtonConventional.setText(Constants.LANG.getString("ConventionalInvoice")); 
    	jButtonConventional.setForeground(Constants.FONT_COLOR);
    	jButtonConventional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
    	jButtonConventional.setToolTipText(Constants.LANG.getString("ConventionalInvoice")); 
    	jButtonConventional.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
    	jButtonConventional.setContentAreaFilled(false);
    	jButtonConventional.setBorderPainted(false);
    	jButtonConventional.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			jButtonConventionalActionPerformed(evt);
    		}
    	});
    	jButtonConventional.addMouseListener(new java.awt.event.MouseAdapter() {
    		public void mouseEntered(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseEntered(evt);
    		}
    		public void mouseExited(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseExited(evt);
    		}
    	});
    	
    	jLabelFadingCanvas.setFont(new java.awt.Font("Arial", 1, 12));
    	jLabelFadingCanvas.setForeground(Constants.FONT_COLOR);

    	jButtonReturn.setFont(Constants.TITLE_FONT);
    	jButtonReturn.setForeground(Constants.FONT_COLOR);
    	jButtonReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_return.jpg"))); 
    	jButtonReturn.setToolTipText(Constants.LANG.getString("Return")); 
    	jButtonReturn.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
    	jButtonReturn.setContentAreaFilled(false);
    	jButtonReturn.setBorderPainted(false);
    	jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			jButtonReturnActionPerformed();
    		}
    	});
    	jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
    		public void mouseEntered(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseEntered(evt);
    		}
    		public void mouseExited(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseExited(evt);
    		}
    	});

    	jButtonReceive.setFont(Constants.FONT_BOLD);
    	jButtonReceive.setText(Constants.LANG.getString("Receive")); 
    	jButtonReceive.setForeground(Constants.FONT_COLOR);
    	jButtonReceive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif")));
    	jButtonReceive.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton2.gif")));
        jButtonReceive.setToolTipText(Constants.LANG.getString("Receive")); 
    	jButtonReceive.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
    	jButtonReceive.setContentAreaFilled(false);
    	jButtonReceive.setBorderPainted(false);
    	jButtonReceive.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				String id = null;
        				try {
        					putTransitionPanel(Constants.LANG.getString("ReceivingInvoice"));
        					id = jButtonActionReceivePerformed();
        					jTextFieldPath.setText("");
        				} catch(ReceiveException e) {
        					fd.showMessage(e.getMessage(), es.mityc.appfacturae.utils.constants.Constants.ERROR_MSG_COLOR);
        		    		jButtonReceive.setEnabled(true);
        				} finally {
        					removeTransitionPanel();
        					MainWindow mw = MainWindow.getInstance();
        					// Invoices table refresh
            				mw.getJTreeInvoices().setSelectionRow(-1);
            				mw.getJTreeInvoices().setSelectionRow(4);
            				// Wait for invoices refresh
        					try {
        						mw.getActionThread().join();
        					} catch (Exception e) {}
        					// Set the new invoice selected
        					mw.getJTableInvoices().clearSelection();
        					if (mw.getJTableInvoices().getRowCount() != 0 && id != null && !id.equals("")) {
        						for (int i = 0; i < mw.getJTableInvoices().getRowCount(); ++i) {
        							if (id.equals(mw.getJTableInvoices().getModel().getValueAt(i, 0))) {
        								mw.getJTableInvoices().setRowSelectionInterval(i, i);
        								break;
        							}
        						}
        					}
        					
            				// Filter combos refresh
        					mw.loadData();
        				}
        			}});
        			th.start();
        		}
    		}
    	});
    	jButtonReceive.addMouseListener(new java.awt.event.MouseAdapter() {
    		public void mouseEntered(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseEntered(evt);
    		}
    		public void mouseExited(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseExited(evt);
    		}
    	});

    	jButtonExamine.setFont(Constants.TITLE_FONT);
    	jButtonExamine.setForeground(Constants.FONT_COLOR);
    	jButtonExamine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_middle_folder.jpg"))); 
    	jButtonExamine.setToolTipText(Constants.LANG.getString("Examine")); 
    	jButtonExamine.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
    	jButtonExamine.setContentAreaFilled(false);
    	jButtonExamine.setBorderPainted(false);
    	jButtonExamine.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			jButtonExamineActionPerformed(evt);
    		}
    	});
    	jButtonExamine.addMouseListener(new java.awt.event.MouseAdapter() {
    		public void mouseEntered(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseEntered(evt);
    		}
    		public void mouseExited(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseExited(evt);
    		}
    	});

    	jButtonHelp.setFont(Constants.TITLE_FONT);
    	jButtonHelp.setForeground(Constants.FONT_COLOR);
    	jButtonHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_help.jpg"))); 
    	jButtonHelp.setToolTipText(Constants.LANG.getString("Help")); 
    	jButtonHelp.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
    	jButtonHelp.setContentAreaFilled(false);
    	jButtonHelp.setBorderPainted(false);
    	jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
    		public void actionPerformed(java.awt.event.ActionEvent evt) {
    			jButtonHelpActionPerformed();
    		}
    	});
    	jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
    		public void mouseEntered(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseEntered(evt);
    		}
    		public void mouseExited(java.awt.event.MouseEvent evt) {
    			ReceiveInvoiceWindow.this.mouseExited(evt);
    		}
    	});

    	org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
    	mainPanel.setLayout(mainPanelLayout);
    	mainPanelLayout.setHorizontalGroup(
    			mainPanelLayout.createSequentialGroup()
    					.addContainerGap()
    					.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    						.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 435, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	    					.add(mainPanelLayout.createSequentialGroup()
	    						.add(jLabelPath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	    						.add(jTextFieldPath,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,365,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	    						.add(jButtonExamine, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    						.add(jPanelTableData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	    					.add(mainPanelLayout.createSequentialGroup()
	    						.add(jButtonReceive, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jLabelFadingCanvas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jButtonHelp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jButtonReturn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
	    					.add(mainPanelLayout.createSequentialGroup()
	    						.add(jButtonConventional, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	    						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	    						.add(jTextAreaConventionalInvoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 400, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
	    				.addContainerGap()
    	);
    	mainPanelLayout.setVerticalGroup(
    			mainPanelLayout.createSequentialGroup()
    				.addContainerGap()
    				.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    					.add(jButtonExamine)
    					.add(jLabelPath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    					.add(jTextFieldPath, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(jPanelTableData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.HORIZONTAL)
    					.add(jLabelFadingCanvas, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    					.add(jButtonHelp)
    					.add(jButtonReturn)
    					.add(jButtonReceive))
    				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    				.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.HORIZONTAL)
    					.add(jButtonConventional)
    				    .add(jTextAreaConventionalInvoice))
    	);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        
    	fd = new FadingCanvas();
    	fd.setFont(Constants.FONT_BOLD);
        fd.setBackground(Constants.BKG_MAIN_COLOR);
        jLabelFadingCanvas.add(fd);
        fd.setSize(250, 27);
        
    }
    
    private void jButtonReturnActionPerformed() {
    	receiveWindow = null;
    	setVisible(false);
    	dispose();
    }

    private void jButtonConventionalActionPerformed(ActionEvent evt) {
		String version = "";
		if (MainWindow.getInstance().getJComboBoxGenVersion().getSelectedItem() != null)
			version = MainWindow.getInstance().getJComboBoxGenVersion().getSelectedItem().toString();
		if (version.equals(Constants.VERSION32)){
			GenerateInvoice32Window in32WinInstance = GenerateInvoice32Window.getInstance(MainWindow.getInstance());
			in32WinInstance.getJPanelInvoiceGeneral().setJCheckBoxIsReceivedSelected(true);
			in32WinInstance.jCheckBoxIsReceivedChangePerformed(true);
			in32WinInstance.setVisible(true);
		}
	}
    
    private String jButtonActionReceivePerformed() throws ReceiveException{
        
    	/** OptionButton2 is deactivated **/ 
        jButtonReceive.setEnabled(false);
        
        /** Introducing received invoice data into jTableData**/
        jTableDataModel = (DefaultTableModel)jTableData.getModel();
        
        for (int j=0;j<jTableData.getRowCount();j++)
            jTableDataModel.setValueAt("", j, 1);     
        
        Object[] receivedInvoiceData = ReceiveUtil.receive(jTextFieldPath.getText());
		
        for (int j=0;j<jTableData.getRowCount();j++)
            jTableDataModel.setValueAt(receivedInvoiceData[j], j, 1);     
        
        if (receivedInvoiceData.length > 0 && !receivedInvoiceData[0].toString().equals(Constants.LANG.getString("NOOK")) && !receivedInvoiceData[1].toString().equals(Constants.LANG.getString("NOOK")) && !receivedInvoiceData[2].toString().equals(Constants.LANG.getString("NOOK"))){
	        MainWindow.getInstance().loadData();
	        jTextFieldPath.setText("");
			fd.showMessage(Constants.LANG.getString("OKMessageReceive"), es.mityc.appfacturae.utils.constants.Constants.OK_MSG_COLOR);
	        jButtonReceive.setEnabled(true);
			
			return receivedInvoiceData[12].toString();
        }
        else{
        	jButtonReceive.setEnabled(true);
        	if (receivedInvoiceData[jTableData.getRowCount()] != null)
        		throw (ReceiveException)receivedInvoiceData[jTableData.getRowCount()];
        	else
        		throw new ReceiveException(Constants.LANG.getString("NOOKMessageReceive"));
        }
        
    }
    
    private void jButtonExamineActionPerformed(java.awt.event.ActionEvent evt) {
        int returnVal = fc.showOpenDialog(receiveWindow);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	String extension = FileUtil.getExtension(fc.getSelectedFile());
            if (extension.equalsIgnoreCase("xsig") || extension.equalsIgnoreCase("xml")) {
                jTextFieldPath.setText(fc.getSelectedFile().getAbsolutePath());
            }   
        }
    }
    
    private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_receiveInvoice_"+Constants.LANG.getLocale().getLanguage()+".html");
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
    		waiter = new GifWaitIndicator(mainPanel, jLabelTransition);
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
    
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7mouseExited
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    /** Variables declaration */
    
    private javax.swing.JPanel jPanelTableData = null;
    private javax.swing.JPanel jPanelTopBar = null;
    private javax.swing.JPanel mainPanel = null;
    
    private javax.swing.JLabel jLabelPath = null;
    private javax.swing.JLabel jLabel1TopBarMess2 = null;
    private javax.swing.JLabel jLabelFadingCanvas = null;
    private javax.swing.JLabel jLabelTopBarMess1 = null;
    
    private javax.swing.JButton jButtonReturn = null;
    private javax.swing.JButton jButtonReceive = null;
    private javax.swing.JButton jButtonConventional = null;
    private javax.swing.JButton jButtonExamine = null;
    private javax.swing.JButton jButtonHelp = null;
    
    private javax.swing.JScrollPane jScrollPaneTableData = null;
    private javax.swing.JTable jTableData = null;
    private javax.swing.JTextField jTextFieldPath = null;
    private javax.swing.JTextArea jTextAreaConventionalInvoice = null;

    private BufferedImage imgTopBar2 = null;
    private Image imgLogoApp = null;
    private JFileChooser fc = null;
    private CustomFileFilter filter = null;
    private DefaultTableModel jTableDataModel = null;
    private FadingCanvas fd = null;
}