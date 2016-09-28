/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae.ui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.security.auth.x500.X500Principal;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.layout.GroupLayout;

import es.mityc.facturae.utils.auth.ProxyAuthenticator;
import es.mityc.facturae.utils.constants.Constants;
import es.mityc.facturae.utils.constants.ConstantsLookAndFeel;
import es.mityc.javasign.bridge.ISignFacade;
import es.mityc.javasign.bridge.InvalidCertificateException;

/**
 * This window is the certificates selection dialog.
 */
public class CertificatesWindow extends javax.swing.JDialog {
    
    ResourceBundle lang = null;
    private static Log logger = LogFactory.getLog(CertificatesWindow.class);
    
    /** Creates new form Certificates */
    public CertificatesWindow(List<X509Certificate> certs, String lookAndFeel, ISignFacade signFacade) {
    	super();
    	
    	logger.info("Loading the certificate selection window");
    	
    	lang = ResourceBundle.getBundle("language/lang");
    	this.certs = certs;
    	
    	this.signFacade = signFacade;
    	
    	sdf = new SimpleDateFormat(lang.getString("DateFormat"),Locale.getDefault());
    	
    	this.lookAndFeel = lookAndFeel;
    	
    	logger.info("Initializing the certificate selection window components");
    	
    	ConstantsLookAndFeel.LOOKANDFEEL_PROP = new Properties();
		try {
	        InputStream is = CertificatesWindow.class.getResourceAsStream(Constants.LOOKANDFEEL_PATH);
	        ConstantsLookAndFeel.LOOKANDFEEL_PROP.load(is);
	        is.close();
	    }
	    catch(Exception e){
	        logger.error("Unable to load look and feel properties. Path not found:" + Constants.LOOKANDFEEL_PATH + ": " + e.getMessage());
	        return;
	    }
	    
    	initComponents();
    	
    	logger.info("Loading certificates");
    	
        loadCertificates(certs);
        
        logger.info("Certificates loaded !");
        
        this.setModal(true);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        this.setTitle(lang.getString("CertificateWindowTitle"));
        this.setSize(500, 545);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
    private void initComponents() {

        mainPanel1 = new javax.swing.JPanel();

        if (lookAndFeel.equals("facturae"))
       		topbar = new javax.swing.ImageIcon(getClass().getResource("/images/topbar2.jpg")); 
       	else
       		topbar = new javax.swing.ImageIcon(getClass().getResource("/images/topbar3.jpg"));
        
        if (lookAndFeel.equals("facturae")){
        	fontColor = Constants.FONT_COLOR;
        }
        else{
        	fontColor = Color.black;
        }
        
        jPanelTopbar = new PicturedPanel(topbar.getImage(), 1.00f);
        jLabelTopbar1 = new javax.swing.JLabel();
        jLabelTopbar2 = new javax.swing.JLabel();
        jPanelCertDetail = new javax.swing.JPanel();
        jScrollPaneCertDetail = new javax.swing.JScrollPane();
        jTableCertDetail = new javax.swing.JTable();
        jButtonReturn = new javax.swing.JButton();
        jButtonActionButton = new javax.swing.JButton();
        jButtonValidateButton = new javax.swing.JButton();
        jButtonHelp = new javax.swing.JButton();
        jScrollPaneAttrDetail = new javax.swing.JScrollPane();
        jTextAreaAttrDetail = new javax.swing.JTextArea();
        jPanelCertificates = new javax.swing.JPanel();
        jScrollPaneCertificates = new javax.swing.JScrollPane();
        jTableCertificates = new javax.swing.JTable();
        jLabelAttributeDetail = new javax.swing.JLabel();
        
        if (lookAndFeel.equals("facturae"))
        	mainPanel1.setBackground(Constants.BKG_MAIN_COLOR);
        
        mainPanel1.setMaximumSize(new java.awt.Dimension(500, 545));
        mainPanel1.setMinimumSize(new java.awt.Dimension(500, 545));
        mainPanel1.setPreferredSize(new java.awt.Dimension(500, 545));

        jPanelTopbar.setBackground(Color.white);
        
        if (lookAndFeel.equals("facturae"))
        	jPanelTopbar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

        jLabelTopbar1.setFont(Constants.TITLE_FONT_ITALIC);
        jLabelTopbar1.setForeground(fontColor);
        jLabelTopbar1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        
        if (lookAndFeel.equals("facturae"))
        	jLabelTopbar1.setText(lang.getString("TopBarMessage1")); 

        jLabelTopbar2.setFont(Constants.TITLE_FONT_ITALIC);
        jLabelTopbar2.setForeground(fontColor);
        jLabelTopbar2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        
        if (lookAndFeel.equals("facturae"))
        	jLabelTopbar2.setText(lang.getString("TopBarMessage2")); 

        GroupLayout jPanel5Layout = new GroupLayout(jPanelTopbar);
        jPanelTopbar.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap(145, Short.MAX_VALUE)
                .add(jPanel5Layout.createParallelGroup(GroupLayout.LEADING)
                    .add(GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                        .add(jLabelTopbar2, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
                        .add(8, 8, 8))
                    .add(GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                        .add(jLabelTopbar1, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
                        .add(12, 12, 12))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(4, 4, 4)
                .add(jLabelTopbar1)
                .add(3, 3, 3)
                .add(jLabelTopbar2)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        if (lookAndFeel.equals("facturae")){
        	jPanelCertDetail.setBackground(Constants.BKG_MAIN_COLOR);
        	jPanelCertDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), lang.getString("DataAboutChosenCertificate"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, fontColor)); 
        	jScrollPaneCertDetail.setBackground(Constants.BKG_MAIN_COLOR);
        										
        }
        else{
        	jPanelCertDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(lang.getString("Details"))); 
        }
        
        jScrollPaneCertDetail.setAutoscrolls(true);
        jScrollPaneCertDetail.setOpaque(false);
        
        if (lookAndFeel.equals("facturae"))
        	jScrollPaneCertDetail.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableCertDetail.setFont(Constants.FONT_PLAIN);
        jTableCertDetail.setForeground(fontColor);
        jTableCertDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {lang.getString("Version"), ""},
                {lang.getString("SeriesCode"), ""},
                {lang.getString("SignatureAlgorithm"), ""},
                {lang.getString("Issuer"), ""},
                {lang.getString("ValidFrom"), ""},
                {lang.getString("ValidTo"), ""},
                {lang.getString("Subject"), ""},
                {lang.getString("PublicKey"), ""},
                {lang.getString("KeyUsage"), ""}
            },
            new String [] {
                lang.getString("Field"), lang.getString("Value")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCertDetail.setAutoscrolls(false);
        
        if (lookAndFeel.equals("facturae"))
        	jTableCertDetail.setGridColor(Constants.SELECTION_COLOR);
        
        jTableCertDetail.setRequestFocusEnabled(false);
        jTableCertDetail.getTableHeader().setReorderingAllowed(false);
        jTableCertDetail.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        	public void valueChanged(ListSelectionEvent e) {
        		jTableCertDetailSelection(e);
        	}
        });
        jScrollPaneCertDetail.setViewportView(jTableCertDetail);
        jTableCertDetail.getColumnModel().getColumn(0).setResizable(false);
        jTableCertDetail.getColumnModel().getColumn(0).setPreferredWidth(90);
        jTableCertDetail.getColumnModel().getColumn(0).setHeaderValue(lang.getString("Field")); 
        
        if (lookAndFeel.equals("facturae"))
        	jTableCertDetail.getColumnModel().getColumn(0).setCellRenderer(new LabelImageRenderer(new javax.swing.ImageIcon(getClass().getResource("/images/certificateAttribute.jpg")), lang.getLocale()));
        
        jTableCertDetail.getColumnModel().getColumn(1).setResizable(false);
        jTableCertDetail.getColumnModel().getColumn(1).setPreferredWidth(90);
        jTableCertDetail.getColumnModel().getColumn(1).setHeaderValue(lang.getString("Value")); 
        jTableCertDetail.getColumnModel().getColumn(1).setCellEditor(null);
        
        if (lookAndFeel.equals("facturae")){
            jTableCertDetail.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());
	        for (int i = 0 ; i < jTableCertDetail.getColumnCount() ; i++){
	            jTableCertDetail.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
	        }
        }
        
        GroupLayout jPanel26Layout = new GroupLayout(jPanelCertDetail);
        jPanelCertDetail.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPaneCertDetail, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .add(jScrollPaneCertDetail, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        
        jButtonReturn.setFont(Constants.TITLE_FONT_PLAIN);
        jButtonReturn.setForeground(fontColor);
        jButtonReturn.setToolTipText(lang.getString("Return")); 
        if (lookAndFeel.equals("facturae")){
        	jButtonReturn.setFont(Constants.TITLE_FONT);
        	jButtonReturn.setForeground(Constants.FONT_COLOR);
        	jButtonReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_return.jpg")));  
        	jButtonReturn.setBorderPainted(false);
        	jButtonReturn.setContentAreaFilled(false);
        	jButtonReturn.setSize(32, 32);
    	}
        else{
        	jButtonReturn.setText(lang.getString("Return"));
        }
                
        jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReturnActionPerformed(evt);
            }
        });
        jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CertificatesWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	CertificatesWindow.this.mouseExited(evt);
            }
        });
       
        jButtonActionButton.setFont(Constants.TITLE_FONT);
        jButtonActionButton.setForeground(fontColor);
        jButtonActionButton.setToolTipText(lang.getString("Continue")); 
        jButtonActionButton.setText(lang.getString("Continue"));
        if (lookAndFeel.equals("facturae")){
        	jButtonActionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        	jButtonActionButton.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton_grey.gif")));
        	jButtonActionButton.setBorderPainted(false);
        	jButtonActionButton.setContentAreaFilled(false);
        }
        	
        jButtonActionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonContinueActionPerformed(evt);
            }
        });
        jButtonActionButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	CertificatesWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	CertificatesWindow.this.mouseExited(evt);
            }
        });
        
        jButtonValidateButton.setFont(Constants.TITLE_FONT);
        jButtonValidateButton.setForeground(fontColor);
        jButtonValidateButton.setToolTipText(lang.getString("OCSPValidate")); 
        jButtonValidateButton.setText(lang.getString("OCSPValidate"));
        if (lookAndFeel.equals("facturae")){
        	jButtonValidateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        	jButtonValidateButton.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton_grey.gif")));
        	jButtonValidateButton.setBorderPainted(false);
        	jButtonValidateButton.setContentAreaFilled(false);
        }
        	
        jButtonValidateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValidatePerformed(evt);
            }
        });
        jButtonValidateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	CertificatesWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	CertificatesWindow.this.mouseExited(evt);
            }
        });

        jButtonHelp.setFont(Constants.TITLE_FONT_PLAIN);
        jButtonHelp.setForeground(fontColor);
        jButtonHelp.setToolTipText(lang.getString("Help")); 
        if (lookAndFeel.equals("facturae")){
	        jButtonHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_help.jpg"))); 
	        jButtonHelp.setBorderPainted(false);
	        jButtonHelp.setContentAreaFilled(false);
	        jButtonHelp.setSize(32, 32);
        }
        else{
        	jButtonHelp.setText(lang.getString("Help"));
        }
        jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHelpActionPerformed(evt);
            }
        });
        jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	CertificatesWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	CertificatesWindow.this.mouseExited(evt);
            }
        });

        jTextAreaAttrDetail.setColumns(20);
        jTextAreaAttrDetail.setEditable(false);
        jTextAreaAttrDetail.setRows(5);
        jTextAreaAttrDetail.setFont(Constants.FONT_PLAIN);
        jTextAreaAttrDetail.setForeground(fontColor);
        
        jScrollPaneAttrDetail.setViewportView(jTextAreaAttrDetail);
        
        if (lookAndFeel.equals("facturae")){
        	jPanelCertificates.setBackground(Constants.BKG_MAIN_COLOR);
        	jPanelCertificates.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), lang.getString("AvailableCertificates"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, fontColor)); 
        	jScrollPaneCertificates.setBackground(Constants.BKG_MAIN_COLOR);
    	}
    	else{
    		jPanelCertificates.setBorder(javax.swing.BorderFactory.createTitledBorder(lang.getString("AvailableCertificates"))); 
    	}
        jScrollPaneCertificates.setAutoscrolls(true);
        jScrollPaneCertificates.setOpaque(false);
        
        if (lookAndFeel.equals("facturae"))
        	jScrollPaneCertificates.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableCertificates.setFont(Constants.FONT_PLAIN);
        jTableCertificates.setForeground(fontColor);
        jTableCertificates.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                lang.getString("IssuedFor"), lang.getString("Issuer"), lang.getString("ExpiryDate"), lang.getString("CertificateType")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCertificates.setAutoscrolls(false);
        
        if (lookAndFeel.equals("facturae"))
        	jTableCertificates.setGridColor(Constants.SELECTION_COLOR);
        
        jTableCertificates.setRequestFocusEnabled(false);
        jTableCertificates.getTableHeader().setReorderingAllowed(false);
        jTableCertificates.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        	public void valueChanged(ListSelectionEvent e) {
        		jTableCertificatesSelection(e);
        	}
        });
        jScrollPaneCertificates.setViewportView(jTableCertificates);
        jTableCertificates.getColumnModel().getColumn(0).setResizable(false);
        jTableCertificates.getColumnModel().getColumn(0).setPreferredWidth(90);
        jTableCertificates.getColumnModel().getColumn(0).setHeaderValue(lang.getString("IssuedFor")); 
        
        if (lookAndFeel.equals("facturae"))
        	jTableCertificates.getColumnModel().getColumn(0).setCellRenderer(new LabelImageRenderer(new javax.swing.ImageIcon(getClass().getResource("/images/certificate.jpg")), lang.getLocale()));
        
        jTableCertificates.getColumnModel().getColumn(1).setResizable(false);
        jTableCertificates.getColumnModel().getColumn(1).setPreferredWidth(90);
        jTableCertificates.getColumnModel().getColumn(1).setHeaderValue(lang.getString("Issuer")); 
        
        if (lookAndFeel.equals("facturae"))
        	jTableCertificates.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());
        
        jTableCertificates.getColumnModel().getColumn(2).setResizable(false);
        jTableCertificates.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTableCertificates.getColumnModel().getColumn(2).setHeaderValue(lang.getString("ExpiryDate")); 
        
        if (lookAndFeel.equals("facturae"))
        	jTableCertificates.getColumnModel().getColumn(2).setCellRenderer(new CellRenderer());
        
        jTableCertificates.getColumnModel().getColumn(3).setResizable(false);
        jTableCertificates.getColumnModel().getColumn(3).setPreferredWidth(120);
        jTableCertificates.getColumnModel().getColumn(3).setHeaderValue(lang.getString("CertificateType")); 
        
        if (lookAndFeel.equals("facturae"))
        	jTableCertificates.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer());

        if (lookAndFeel.equals("facturae")){
	        for (int i = 0 ; i < jTableCertificates.getColumnCount() ; i++){
	            jTableCertificates.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
	         }
        }
        
        jTableCertificates.setFont(Constants.FONT_PLAIN);
        jTableCertificates.setForeground(fontColor);
        
        GroupLayout jPanel27Layout = new GroupLayout(jPanelCertificates);
        jPanelCertificates.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPaneCertificates, GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .add(jScrollPaneCertificates, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelAttributeDetail.setFont(Constants.FONT_PLAIN);
        jLabelAttributeDetail.setForeground(fontColor);
        jLabelAttributeDetail.setText(lang.getString("Details")); 
        
        GroupLayout mainPanel1Layout = new GroupLayout(mainPanel1);
        mainPanel1.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createParallelGroup(GroupLayout.LEADING)
            .add(mainPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelTopbar, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(mainPanel1Layout.createSequentialGroup()
            	.addContainerGap()
            	.add(mainPanel1Layout.createParallelGroup(GroupLayout.LEADING)
                    .add(mainPanel1Layout.createSequentialGroup()
                        .add(jLabelAttributeDetail)
                        .addContainerGap())
                    .add(GroupLayout.TRAILING, mainPanel1Layout.createSequentialGroup()
                        .add(mainPanel1Layout.createParallelGroup(GroupLayout.TRAILING)
                            .add(GroupLayout.LEADING, jScrollPaneAttrDetail, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                            .add(GroupLayout.LEADING, jPanelCertDetail, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                            .add(GroupLayout.LEADING, jPanelCertificates, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                            .add(GroupLayout.LEADING, mainPanel1Layout.createSequentialGroup()
                                .add(jButtonActionButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonValidateButton))
                            .add(GroupLayout.TRAILING, mainPanel1Layout.createSequentialGroup()
                                .add(jButtonHelp)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonReturn)))
                        .addContainerGap())))
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(GroupLayout.LEADING)
            .add(mainPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelTopbar, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanelCertificates, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelCertDetail, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelAttributeDetail)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPaneAttrDetail, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(mainPanel1Layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(jButtonReturn)
                    .add(jButtonHelp)
                    .add(jButtonActionButton)
                    .add(jButtonValidateButton))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(mainPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(mainPanel1, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
        );

        pack();
    }

    private void loadCertificates(List<X509Certificate> certs){
        
    	X509Certificate certificate = null;
    	X500Principal subject = null;
    	X500Principal issuer = null; 
    	Date expiryDate = null;
    	
    	SimpleDateFormat sdf = new SimpleDateFormat(lang.getString("DateFormat"));
        String[] strArray = {};
        
        String subjectStr = null;
        String issuerStr = null;
        String dateStr = null;
        String typeStr = null;
        
        int count = certs.size();
        for (int i = 0 ; i < count ; i++ ){
        	subjectStr = "";
            issuerStr = "";
            dateStr = "";
            typeStr = "";
        	certificate = certs.get(i);
        	subject = certificate.getSubjectX500Principal();
        	if (subject != null){
        		strArray = subject.getName("RFC1779").split(", ");
        		if (strArray.length > 0 && (strArray[0].startsWith("CN=") || strArray[0].startsWith("OU=") || strArray[0].startsWith("O=")))
        			subjectStr = strArray[0].split("=")[1];
        	}
        	issuer = certificate.getIssuerX500Principal();
        	if (issuer != null){
        		strArray = issuer.getName("RFC1779").split(", ");
        		if (strArray.length > 0 && (strArray[0].startsWith("CN=") || strArray[0].startsWith("OU=") || strArray[0].startsWith("O=")))
        			issuerStr = strArray[0].split("=")[1];
        	}
        	expiryDate = certificate.getNotAfter();
        	if (expiryDate != null )
        		dateStr = sdf.format(expiryDate);
        	if (certificate.getType() != null)
        		typeStr = certificate.getType();
        	((DefaultTableModel)jTableCertificates.getModel()).addRow(new Object[]{subjectStr,issuerStr,dateStr,typeStr});
        }
    }
    
    
    private void jButtonReturnActionPerformed(java.awt.event.ActionEvent evt) {
    	CertificatesWindow.this.cancelSelection();
    	this.setVisible(false);
    }
 
    private void jButtonContinueActionPerformed(java.awt.event.ActionEvent evt) {
    	if ( cert != null){
    		CertificatesWindow.this.approveSelection();
    		this.setVisible(false);
    	}
    }
    
    private void jButtonValidatePerformed(java.awt.event.ActionEvent evt) {
	    if (cert != null){
	    	jButtonValidateButton.setEnabled(false);
	    	this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));
    		Properties configprops = new Properties(),props = new Properties();
	    	try{
	    		configprops.load(this.getClass().getResourceAsStream(es.mityc.facturae.utils.constants.Constants.CONFIG_PATH));
	    		File f = new File(configprops.getProperty("sign_file"));
	    		if (f.exists())
	    			props.load(new FileInputStream(f));
	    		else
	    			props.load(this.getClass().getResourceAsStream(configprops.getProperty("sign_resource")));
	    		String proxyUsedS = props.getProperty("ocsp.proxyused");
	    		boolean proxyUsedB = proxyUsedS.equals("true");
	    		if (proxyUsedB){
	    			System.setProperty("http.proxyHost", props.getProperty("ocsp.proxyserver"));
	        		System.setProperty("http.proxyPort", props.getProperty("ocsp.proxyport"));
	        		String proxyAuthS = props.getProperty("ocsp.proxyauthenticated");
	        		boolean proxyAuthB = proxyAuthS.equals("true");
	        		if (proxyAuthB) {
	        			Authenticator.setDefault(new ProxyAuthenticator(props.getProperty("ocsp.proxyuser"),props.getProperty("ocsp.proxypassword")));
	        		} 
	        		else {
	        			Authenticator.setDefault(null);
	        		}
	    		}
	    		try{
	    			signFacade.validateCert(cert);
	    			if (lookAndFeel.equals("facturae")){
	    				ResultDialogWindow rdw = new ResultDialogWindow(null,true,lang.getString("Information"),lang.getString("OKValidationOCSP"),new javax.swing.ImageIcon(getClass().getResource("/images/correct_message.gif")));
	    				rdw.setVisible(true);
	    			}
	    			else{
	    				JOptionPane.showMessageDialog(this, lang.getString("OKValidationOCSP"), lang.getString("Information"), JOptionPane.INFORMATION_MESSAGE);
	    			}
	    		}
	    		catch(InvalidCertificateException ice){
	    			logger.error("The Certificate is not valid: "+ice.getMessage());
	    			if (lookAndFeel.equals("facturae")){
	    				ResultDialogWindow rdw = new ResultDialogWindow(null,true,lang.getString("Error"),lang.getString("ErrorValidationOCSP"),new javax.swing.ImageIcon(getClass().getResource("/images/error_message.gif")));
	    				rdw.setVisible(true);
	    			}
	    			else{
	    				JOptionPane.showMessageDialog(this, lang.getString("ErrorValidationOCSP"), lang.getString("Error"), JOptionPane.ERROR_MESSAGE);
	    			}
	    		}
	    		
	    	}
	    	catch(IOException ioe){
	    		logger.error("It is not possible to obtain the configuration properties file");
	    	}
	    	this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
	    	jButtonValidateButton.setEnabled(true);
	    }
	    else{
	    	logger.error("There is not any certificate selected");
	    	if (lookAndFeel.equals("facturae")){
				ResultDialogWindow rdw = new ResultDialogWindow(null,true,lang.getString("Warning"),lang.getString("WarnValidationOCSP"),new javax.swing.ImageIcon(getClass().getResource("/images/warning_message.gif")));
				rdw.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(this, lang.getString("WarnValidationOCSP"), lang.getString("Warning"), JOptionPane.INFORMATION_MESSAGE);
			}
	    }
    }

    private void jButtonHelpActionPerformed(java.awt.event.ActionEvent evt) {
    	HelpWindow chw = new HelpWindow(lang.getLocale());
    	chw.setVisible(true);
    	chw.dispose();
    	chw = null;
    }
    
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    private void jTableCertificatesSelection(ListSelectionEvent evt){
        cert = certs.get(jTableCertificates.getSelectedRow());
        if (cert != null){
	        if (cert.getVersion() > 0)
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("V"+cert.getVersion(), 0, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 0, 1);
	        if (cert.getSerialNumber() != null)
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(cert.getSerialNumber(), 1, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 1, 1);
	        if (cert.getSigAlgName() != null)
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(cert.getSigAlgName(), 2, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 2, 1);
	        String issuer = ((DefaultTableModel)jTableCertificates.getModel()).getValueAt(jTableCertificates.getSelectedRow(), 1).toString();
	        if (issuer != null && !issuer.trim().equals(""))
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(issuer, 3, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 3, 1);
	        if (cert.getNotBefore() != null)
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(sdf.format(cert.getNotBefore()), 4, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 4, 1);
	        if (cert.getNotAfter() != null)
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(sdf.format(cert.getNotAfter()), 5, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 5, 1);
	        String subject = ((DefaultTableModel)jTableCertificates.getModel()).getValueAt(jTableCertificates.getSelectedRow(), 0).toString();
	        if (subject != null && !subject.trim().equals(""))
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(subject, 6, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 6, 1);
	        if (cert.getPublicKey() != null)
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(cert.getPublicKey().toString().split("\n")[0], 7, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 7, 1);
	        String keyUsage = "";
	        boolean[] keyUsages = cert.getKeyUsage();
	        String[] keyUsageNames = {lang.getString("DigitalSignature"),lang.getString("NonRepudiation"), lang.getString("KeyEncipherment"),lang.getString("DataEncipherment"),lang.getString("KeyAgreement"),lang.getString("KeyCertSign"),lang.getString("CRLSign"),lang.getString("EncipherOnly"),lang.getString("DecipherOnly")};
	        for (int i = 0 ; i < keyUsages.length ; i++){
	        	if (keyUsages[i])
	        		if (keyUsage.equals(""))
	        			keyUsage = keyUsageNames[i];
	        		else
	        			keyUsage = keyUsage +","+ keyUsageNames[i];
	        }
	        if (!keyUsage.equals(""))
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt(keyUsage, 8, 1);
	        else
	        	((DefaultTableModel)jTableCertDetail.getModel()).setValueAt("", 8, 1);
        }
    }

    private void jTableCertDetailSelection(ListSelectionEvent evt){
        if (cert != null){
	    	if(jTableCertDetail.getSelectedRow() == 0)
	            jTextAreaAttrDetail.setText(String.valueOf(cert.getVersion()));
	        if(jTableCertDetail.getSelectedRow() == 1)
	            jTextAreaAttrDetail.setText(cert.getSerialNumber().toString());
	        if(jTableCertDetail.getSelectedRow() == 2)
	            jTextAreaAttrDetail.setText(cert.getSigAlgName());
	        if(jTableCertDetail.getSelectedRow() == 3)
	            jTextAreaAttrDetail.setText(cert.getIssuerX500Principal().toString());
	        if(jTableCertDetail.getSelectedRow() == 4)
	        	jTextAreaAttrDetail.setText(cert.getNotBefore().toString());
	        if(jTableCertDetail.getSelectedRow() == 5)
	        	jTextAreaAttrDetail.setText(cert.getNotAfter().toString());
	        if(jTableCertDetail.getSelectedRow() == 6)
	            jTextAreaAttrDetail.setText(cert.getSubjectX500Principal().toString());
	        if(jTableCertDetail.getSelectedRow() == 7)
	            jTextAreaAttrDetail.setText(cert.getPublicKey().toString());
	        if(jTableCertDetail.getSelectedRow() == 8)
	            jTextAreaAttrDetail.setText(jTableCertDetail.getModel().getValueAt(8, 1).toString());
        }
    }
    
    /**
     * Return a certificate
     * @return
     */
    public X509Certificate getCert(){
    	return cert;
    }
    
    public int showOpenDialog(){
    	this.setVisible(true);
    	this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent)
            {
                returnValue = -1;
            }
        });
    	return returnValue;
    }
    
    public void approveSelection()
    {
        returnValue = 0;
    }
    
    public void cancelSelection()
    {
    	returnValue = -1;
    }
    // Variables declaration
    private List<X509Certificate> certs;
    private X509Certificate cert;
    private int returnValue = -1;
    
    private ISignFacade signFacade = null;
    
    private SimpleDateFormat sdf;
    
    	private String lookAndFeel;
    
    private javax.swing.JPanel jPanelCertificates;
    private javax.swing.JScrollPane jScrollPaneCertificates;
    private javax.swing.JTable jTableCertificates;
    
    private javax.swing.JPanel jPanelCertDetail;
    private javax.swing.JScrollPane jScrollPaneCertDetail;
    private javax.swing.JTable jTableCertDetail;
    
    private javax.swing.JLabel jLabelAttributeDetail;
    private javax.swing.JScrollPane jScrollPaneAttrDetail;
    private javax.swing.JTextArea jTextAreaAttrDetail;
    
    private javax.swing.JButton jButtonActionButton;
    private javax.swing.JButton jButtonValidateButton;
    private javax.swing.JButton jButtonReturn;
    private javax.swing.JButton jButtonHelp;
    private javax.swing.JPanel jPanelTopbar;
    private javax.swing.JLabel jLabelTopbar1;
    private javax.swing.JLabel jLabelTopbar2;
    
    private javax.swing.JPanel mainPanel1;

    private java.awt.Color fontColor;
    
    private ImageIcon topbar;
}