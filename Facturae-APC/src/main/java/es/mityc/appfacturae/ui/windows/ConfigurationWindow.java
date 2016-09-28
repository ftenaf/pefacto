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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.facturae.LanguageCodeType;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.EnumOperationType;
import es.mityc.appfacturae.ui.Main;
import es.mityc.appfacturae.ui.components.CustomFileFilter;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.dialogs.FACeConfigDialog;
import es.mityc.appfacturae.ui.dialogs.InputLanguageDialog;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.Base64;
import es.mityc.appfacturae.utils.io.FileUtil;
import es.mityc.appfacturae.utils.io.ImageUtil;
import es.mityc.appfacturae.utils.io.StringUtil;
import es.mityc.appfacturae.utils.storemanager.PluginAlmCertUCCD;
import es.mityc.facturae.utils.ValidatorUtil;
import es.mityc.javasign.pkstore.DefaultPassStoreKS;


public class ConfigurationWindow extends javax.swing.JFrame {
    
    Properties extProps = null;
    private static Log logger = LogFactory.getLog(ConfigurationWindow.class);
    private FadingCanvas fd = null;
    private PluginAlmCertUCCD pacu = null;
    private FACeConfigDialog fcd = null;
	
    private static ConfigurationWindow confWindow = null;
    
    /**
     * Singleton pattern
     */
    static public ConfigurationWindow getInstance() {
    	if (confWindow == null)
    		confWindow = new ConfigurationWindow();
    	
    	return confWindow;
    }
    
    /** Creates new Configuracion form */
    private ConfigurationWindow() {
        
        /** Loading images */
        try {
        	imgLogoApp = ImageIO.read(this.getClass().getResourceAsStream("/images/logoapp.jpg"));
        } catch(IOException ioe) {
        	logger.error("The Facturae logo could not be loaded: " + ioe.getMessage());
        }
        
        /** Loading extensions */
        extProps = new Properties();
        try {
        	File f = new File(Constants.APP_PROP.getProperty("extensions_file"));
        	if (f.exists()) {
        		FileInputStream fis = new FileInputStream(f);
        		extProps.load(fis);
        		fis.close();
        	}
        	else {
        		InputStream is = Main.class.getResourceAsStream(Constants.APP_PROP.getProperty("extensions_resource"));
        		extProps.load(is);
        		is.close();
        	}
        } catch(Exception e) {
        	logger.error("Unable to load extensions properties. Path not found:" + Constants.APP_PROP.getProperty("extensions_resource") + ": " + e.getMessage());
        	return;
        }
        
        fcd = new FACeConfigDialog(this, true);
	            
        initComponents();
        
        this.pack();
        this.setSize(715,750);
        this.setResizable(false);
        if (imgLogoApp != null)
        	this.setIconImage(imgLogoApp);
        this.setLocationRelativeTo(null);      
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanelGeneral = new javax.swing.JPanel();
        jLabelDraft = new javax.swing.JLabel();
        jLabelRecDraft = new javax.swing.JLabel();
        jLabelIssued = new javax.swing.JLabel();
        jLabelRecIssued = new javax.swing.JLabel();
        jLabelReceived = new javax.swing.JLabel();
        jLabelCorrectedFac = new javax.swing.JLabel();
        jLabelNextId = new javax.swing.JLabel();
        jTextFieldDraftId = new javax.swing.JTextField();
        jTextFieldIssuedId = new javax.swing.JTextField();
        jTextFieldRecievedId = new javax.swing.JTextField();
        jTextFieldRecDraftId = new javax.swing.JTextField();
        jTextFieldRecIssuedId = new javax.swing.JTextField();
        jTextFieldSeries = new javax.swing.JTextField();
        jLabelSeriesIssued = new javax.swing.JLabel();
        jTextFieldCorrecSeries = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelLogo = new javax.swing.JPanel();
        jButtonLogoDir = new javax.swing.JButton();
        jButtonLogoDel = new javax.swing.JButton();
        jButtonLanguage = new javax.swing.JButton();
        jButtonStoreConfig = new javax.swing.JButton();
        jLabelLogoPath = new javax.swing.JLabel();
        jTextFieldLogoPath = new javax.swing.JTextField();
        jLabelLogoIcon = new javax.swing.JLabel();
        jPanelMailAndConx = new javax.swing.JPanel();
        jLabelSMTP = new javax.swing.JLabel();
        jLabelPort = new javax.swing.JLabel();
        jLabelMailUser = new javax.swing.JLabel();
        jLabelMailPass = new javax.swing.JLabel();
        jLabelMail = new javax.swing.JLabel();
        jLabelDefaultSubject = new javax.swing.JLabel();
        jLabelStoreConfig = new javax.swing.JLabel();
        jTextFieldSMTPServer = new javax.swing.JTextField();
        jTextFieldXSDPath = new javax.swing.JTextField();
        jTextFieldNamespace = new javax.swing.JTextField();
        jTextFieldMailPort = new javax.swing.JTextField();
        jTextFieldMailUser = new javax.swing.JTextField();
        jTextFieldMail = new javax.swing.JTextField();
        jTextFieldDefaultSubject = new javax.swing.JTextField();
        jCheckBoxSSL = new javax.swing.JCheckBox();
        jCheckBoxMailAuth = new javax.swing.JCheckBox();
        jMailPasswordField = new javax.swing.JPasswordField();
        jLabelProxy = new javax.swing.JLabel();
        jTextFieldProxy = new javax.swing.JTextField();
        jLabelProxyPort = new javax.swing.JLabel();
        jTextFieldProxyPort = new javax.swing.JTextField();
        jCheckBoxProxyAuth = new javax.swing.JCheckBox();
        jLabelProxyUser = new javax.swing.JLabel();
        jTextFieldProxyUser = new javax.swing.JTextField();
        jLabelProxyPass = new javax.swing.JLabel();
        jProxyPasswordField = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        jPanelOCSP = new javax.swing.JPanel();
        jPanelExtensions = new javax.swing.JPanel();
        jCheckBoxOCSPValReceiv = new javax.swing.JCheckBox();
        jCheckBoxOCSPValIssued = new javax.swing.JCheckBox();
        jLabelOCSPServer = new javax.swing.JLabel();
        jTextFieldOCSPServer = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jButtonAccept = new javax.swing.JButton();
        jButtonHelp = new JButton();
        jButtonXSDPath = new JButton();
        jButtonXSDAdd = new JButton();
        jButtonXSDDel = new JButton();
        jPanelTopImage = new javax.swing.JPanel();
        jPanelLanguage = new javax.swing.JPanel();
        jPanelStoreConfig = new javax.swing.JPanel();
        jLabelLanguage = new javax.swing.JLabel();
        jLabelBanner = new javax.swing.JLabel();
        jLabelBannerMsg1 = new javax.swing.JLabel();
        jLabelBannerMsg2 = new javax.swing.JLabel();
        jLabelXSDPath = new javax.swing.JLabel();
        jLabelNamespace = new javax.swing.JLabel();
        jScrollPaneXSD = new javax.swing.JScrollPane();
        jTableXSD = new javax.swing.JTable();
        jPanelFACe = new javax.swing.JPanel();
        jButtonFACeConfig = new javax.swing.JButton();
        jLabelFACeConfig = new javax.swing.JLabel();
        jLabelFACeLink = new javax.swing.JLabel();

        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonCancelActionPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
        setTitle(Constants.LANG.getString("ConfigurationWindowTitle")); 
        
        fd = new FadingCanvas();
        fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);

        mainPanel.setBackground(Constants.BKG_MAIN_COLOR);

        jPanelGeneral.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 240, 255), 1, true), Constants.LANG.getString("General"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

        jLabelDraft.setFont(Constants.FONT_PLAIN);
        jLabelDraft.setForeground(Constants.FONT_COLOR);
        jLabelDraft.setText(Constants.LANG.getString("DraftStr")); 
        jLabelDraft.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        jLabelDraft.setHorizontalAlignment(SwingConstants.LEFT);

        jLabelRecDraft.setFont(Constants.FONT_PLAIN);
        jLabelRecDraft.setForeground(Constants.FONT_COLOR);
        jLabelRecDraft.setText(Constants.LANG.getString("CorrectiveDraft")); 
        jLabelRecDraft.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        jLabelRecDraft.setHorizontalAlignment(SwingConstants.LEFT);

        jLabelIssued.setFont(Constants.FONT_PLAIN);
        jLabelIssued.setForeground(Constants.FONT_COLOR);
        jLabelIssued.setText(Constants.LANG.getString("Issued")); 
        jLabelIssued.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        jLabelIssued.setHorizontalAlignment(SwingConstants.LEFT);

        jLabelRecIssued.setFont(Constants.FONT_PLAIN);
        jLabelRecIssued.setForeground(Constants.FONT_COLOR);
        jLabelRecIssued.setText(Constants.LANG.getString("CorrectiveIssued")); 
        jLabelRecIssued.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        jLabelRecIssued.setHorizontalAlignment(SwingConstants.LEFT);

        jLabelReceived.setFont(Constants.FONT_PLAIN);
        jLabelReceived.setForeground(Constants.FONT_COLOR);
        jLabelReceived.setText(Constants.LANG.getString("ReceivedStr")); 
        jLabelReceived.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        jLabelReceived.setHorizontalAlignment(SwingConstants.LEFT);

        jLabelCorrectedFac.setFont(Constants.FONT_PLAIN);
        jLabelCorrectedFac.setForeground(Constants.FONT_COLOR);
        jLabelCorrectedFac.setText(Constants.LANG.getString("CorrectedSeries")); 

        jLabelNextId.setFont(Constants.FONT_PLAIN);
        jLabelNextId.setForeground(Constants.FONT_COLOR);
        jLabelNextId.setText(Constants.LANG.getString("NextIdentifier")); 

        jTextFieldDraftId.setFont(Constants.FONT_PLAIN);
        jTextFieldDraftId.setText(Constants.CONFIG_PROP.getProperty("draftId"));
        jTextFieldDraftId.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldDraftId.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt){
    			idValidator(evt, 0);
    		}
    	});
        jTextFieldDraftId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.white);
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
			}				
		});

        jTextFieldIssuedId.setFont(Constants.FONT_PLAIN);
        jTextFieldIssuedId.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldIssuedId.setText(Constants.CONFIG_PROP.getProperty("issuedId"));
        jTextFieldIssuedId.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt){
    			idValidator(evt, 1);
    		}
    	});
        jTextFieldIssuedId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.white);
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
			}				
		});

        jTextFieldRecievedId.setFont(Constants.FONT_PLAIN);
        jTextFieldRecievedId.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldRecievedId.setText(Constants.CONFIG_PROP.getProperty("receivedId"));
        jTextFieldRecievedId.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt){
    			idValidator(evt, 2);
    		}
    	});
        jTextFieldRecievedId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.white);		
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
			}				
		});

        jTextFieldRecDraftId.setFont(Constants.FONT_PLAIN);
        jTextFieldRecDraftId.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldRecDraftId.setText(Constants.CONFIG_PROP.getProperty("c-draftId"));
        jTextFieldRecDraftId.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt){
    			idValidator(evt, 3);
    		}
    	});
        jTextFieldRecDraftId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.white);	
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
			}				
		});

        jTextFieldRecIssuedId.setFont(Constants.FONT_PLAIN);
        jTextFieldRecIssuedId.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldRecIssuedId.setText(Constants.CONFIG_PROP.getProperty("c-issuedId"));
        jTextFieldRecIssuedId.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt){
    			idValidator(evt, 4);
    		}
    	});
        jTextFieldRecIssuedId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.white);
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
			}				
		});

        jTextFieldSeries.setFont(Constants.FONT_PLAIN);
        jTextFieldSeries.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldSeries.setText(Constants.CONFIG_PROP.getProperty("series"));
        jTextFieldSeries.addFocusListener(new FocusAdapter() {
        	public void focusLost(FocusEvent evt){
        		if (jTextFieldSeries.getText() != null){
        			String value = jTextFieldSeries.getText();
        			if (jTextFieldCorrecSeries.getText() != null && value.trim().equals(jTextFieldCorrecSeries.getText().trim())) {
        				((JTextField)evt.getSource()).setText("");
        				((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
        				fd.showMessage(Constants.LANG.getString("NOOKMessageConfigurationSeries"), Constants.ERROR_MSG_COLOR);
        			}
        			else{
        				if (value.length() > 20){
            				((JTextField)evt.getSource()).setText("");
            				((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
            				fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
            				return;
            			}
        			}
        		}
    		}
    	});
        jTextFieldSeries.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				if (!StringUtil.isSpecialCharWindows(arg0.getKeyChar()))
					((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
				else{
					fd.showMessage(Constants.LANG.getString("NOOKMessageSpecialChar") + " " + StringUtil.getSpecialCharsWindows(), Constants.ERROR_MSG_COLOR);
					arg0.consume();
				}
			}				
		});

        jLabelSeriesIssued.setFont(Constants.FONT_PLAIN);
        jLabelSeriesIssued.setForeground(Constants.FONT_COLOR);
        jLabelSeriesIssued.setText(Constants.LANG.getString("SeriesIssuedInvoice")); 

        jTextFieldCorrecSeries.setFont(Constants.FONT_PLAIN);
        jTextFieldCorrecSeries.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldCorrecSeries.setText(Constants.CONFIG_PROP.getProperty("CorrectedSeries"));
        jTextFieldCorrecSeries.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt){
    			if (jTextFieldCorrecSeries.getText() != null){
        			String value = jTextFieldCorrecSeries.getText();
        			if (jTextFieldSeries.getText() != null && value.trim().equals(jTextFieldSeries.getText().trim())) {
        				((JTextField)evt.getSource()).setText("");
        				((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
        				fd.showMessage(Constants.LANG.getString("NOOKMessageConfigurationSeries"), Constants.ERROR_MSG_COLOR);
        			}
        			else{
        				if (value.length() > 20){
            				((JTextField)evt.getSource()).setText("");
            				((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
            				fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
            				return;
            			}
        			}
        		}
    		}
    	});
        jTextFieldCorrecSeries.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				if (!StringUtil.isSpecialCharWindows(arg0.getKeyChar()))
					((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
				else{
					fd.showMessage(Constants.LANG.getString("NOOKMessageSpecialChar") + " " + StringUtil.getSpecialCharsWindows(), Constants.ERROR_MSG_COLOR);
					arg0.consume();
				}
			}				
		});

        jSeparator1.setForeground(Constants.SEPARATOR_COLOR);
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanelGeneral);
        jPanelGeneral.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabelRecDraft)
                            .add(jLabelDraft)
                            .add(jLabelRecIssued)
                            .add(jLabelIssued)
                            .add(jLabelReceived))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jTextFieldRecIssuedId, 0, 0, Short.MAX_VALUE)
                            .add(jTextFieldRecDraftId, 0, 0, Short.MAX_VALUE)
                            .add(jTextFieldRecievedId, 0, 0, Short.MAX_VALUE)
                            .add(jTextFieldIssuedId, 0, 0, Short.MAX_VALUE)
                            .add(jTextFieldDraftId, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)))
                    .add(jLabelNextId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelSeriesIssued)
                    .add(jTextFieldSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelCorrectedFac)
                    .add(jTextFieldCorrecSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jLabelNextId)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelDraft)
                    .add(jTextFieldDraftId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelIssued)
                    .add(jTextFieldIssuedId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelReceived)
                    .add(jTextFieldRecievedId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(11, 11, 11)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelRecDraft)
                    .add(jTextFieldRecDraftId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelRecIssued)
                    .add(jTextFieldRecIssuedId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(17, 17, 17))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(jPanel2Layout.createSequentialGroup()
                .add(21, 21, 21)
                .add(jLabelSeriesIssued)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextFieldSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(35, 35, 35)
                .add(jLabelCorrectedFac)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextFieldCorrecSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanelLogo.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelLogo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Logo"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelLogo.setForeground(Constants.BKG_MAIN_COLOR);
        jPanelLogo.setFont(Constants.FONT_PLAIN);

        jButtonLogoDir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_folder.jpg"))); 
        jButtonLogoDir.setToolTipText(Constants.LANG.getString("Examine")); 
        jButtonLogoDir.setBorderPainted(false);
        jButtonLogoDir.setContentAreaFilled(false);
        jButtonLogoDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoActionPerformed();
            }
        });
        jButtonLogoDir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseExited(evt);
            }
        });
        
        jButtonLogoDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_middle_cancel.jpg"))); 
        jButtonLogoDel.setToolTipText(Constants.LANG.getString("Cancel")); 
        jButtonLogoDel.setBorderPainted(false);
        jButtonLogoDel.setContentAreaFilled(false);
        jButtonLogoDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDelLogoActionPerformed();
            }
        });
        jButtonLogoDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseExited(evt);
            }
        });

        jLabelLogoPath.setFont(Constants.FONT_PLAIN);
        jLabelLogoPath.setForeground(Constants.FONT_COLOR);
        jLabelLogoPath.setText(Constants.LANG.getString("Path")); 

        jTextFieldLogoPath.setFont(Constants.FONT_PLAIN);
        jTextFieldLogoPath.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldLogoPath.setText(Constants.CONFIG_PROP.getProperty("logoPath"));

        jLabelLogoIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        
        File f = new File(Constants.CONFIG_PROP.getProperty("logoPath"));
        ImageIcon imgIcon = ImageUtil.createScaledImageIcon(f, jLabelLogoIcon,290,125);
        jLabelLogoIcon.setIcon(imgIcon);
        jLabelLogoIcon.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        jLabelLogoIcon.setVerticalAlignment(javax.swing.JLabel.CENTER);
        
        GroupLayout jPanel3Layout = new GroupLayout(jPanelLogo);
        jPanel3Layout.setVerticalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.CENTER)
        				.addComponent(jLabelLogoPath, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jButtonLogoDir)
        				.addComponent(jButtonLogoDel)
        				.addComponent(jTextFieldLogoPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jLabelLogoIcon, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(jLabelLogoIcon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
        				.addGroup(jPanel3Layout.createSequentialGroup()
        					.addComponent(jLabelLogoPath, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        					.addGap(8)
        					.addComponent(jTextFieldLogoPath, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
        					.addGap(8)
        					.addComponent(jButtonLogoDir, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        					.addGap(8)
        					.addComponent(jButtonLogoDel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        jPanelLogo.setLayout(jPanel3Layout);

        jPanelFACe.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelFACe.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("FACe"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelFACe.setForeground(Constants.BKG_MAIN_COLOR);
        jPanelFACe.setFont(Constants.FONT_PLAIN);
        jPanelFACe.setLayout(new GridBagLayout());

        jButtonFACeConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonFACeConfig.setToolTipText(Constants.LANG.getString("FACeConfig")); 
        jButtonFACeConfig.setBorderPainted(false);
        jButtonFACeConfig.setContentAreaFilled(false);
        jButtonFACeConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButtonFACeConfigActionPerformed();
            }
        });
        jButtonFACeConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseExited(evt);
            }
        });

        jLabelFACeConfig.setFont(Constants.FONT_PLAIN);
        jLabelFACeConfig.setForeground(Constants.FONT_COLOR);
        jLabelFACeConfig.setText(Constants.LANG.getString("FACeConfig")); 

        jLabelFACeLink.setText(Constants.LANG.getString("FACeLink")); 
        jLabelFACeLink.setForeground(Constants.LINK_COLOR);
        jLabelFACeLink.setFont(Constants.FONT_LINK);
        jLabelFACeLink.setHorizontalAlignment(SwingConstants.LEFT);
        jLabelFACeLink.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent evt) {
        		setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
        	public void mouseExited(MouseEvent evt) {
        		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        	}
        	public void mouseReleased(MouseEvent e) {
        		jLabelFACeLinkActionPerformed();
        	}
        	
        });
        
        GridBagConstraints jButtonFACeConfigConstraints = new GridBagConstraints();
        jButtonFACeConfigConstraints.ipadx = -20;
        jButtonFACeConfigConstraints.weightx = 0.1;
        jButtonFACeConfigConstraints.anchor = GridBagConstraints.EAST;
        jButtonFACeConfigConstraints.gridwidth = 2;
        jButtonFACeConfigConstraints.insets = new Insets(0, 0, 5, 0);
        jButtonFACeConfigConstraints.gridx = 0;
        jButtonFACeConfigConstraints.gridy = 1;
        jPanelFACe.add(jButtonFACeConfig, jButtonFACeConfigConstraints);
        GridBagConstraints jLabelFACeConfigConstraints = new GridBagConstraints();
        jLabelFACeConfigConstraints.weightx = 0.1;
        jLabelFACeConfigConstraints.anchor = GridBagConstraints.WEST;
        jLabelFACeConfigConstraints.fill = GridBagConstraints.HORIZONTAL;
        jLabelFACeConfigConstraints.gridwidth = 2;
        jLabelFACeConfigConstraints.insets = new Insets(0, 0, 5, 5);
        jLabelFACeConfigConstraints.gridx = 2;
        jLabelFACeConfigConstraints.gridy = 1;
        jPanelFACe.add(jLabelFACeConfig, jLabelFACeConfigConstraints);
	            
        GridBagConstraints jLabelFACeLinkConstraints = new GridBagConstraints();
        jLabelFACeLinkConstraints.gridwidth = 4;
        jLabelFACeLinkConstraints.insets = new Insets(1, 3, 3, 10);
        jLabelFACeLinkConstraints.gridx = 0;
        jLabelFACeLinkConstraints.gridy = 2;
        jPanelFACe.add(jLabelFACeLink, jLabelFACeLinkConstraints);


        jPanelMailAndConx.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelMailAndConx.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("MailAndConnection"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelMailAndConx.setForeground(Constants.BKG_MAIN_COLOR);
        jPanelMailAndConx.setFont(Constants.FONT_PLAIN);

        jLabelSMTP.setFont(Constants.FONT_PLAIN);
        jLabelSMTP.setForeground(Constants.FONT_COLOR);
        jLabelSMTP.setText(Constants.LANG.getString("SMTPServer")); 

        jLabelPort.setFont(Constants.FONT_PLAIN);
        jLabelPort.setForeground(Constants.FONT_COLOR);
        jLabelPort.setText(Constants.LANG.getString("Port")); 
        
        jCheckBoxSSL.setBackground(Constants.BKG_MAIN_COLOR);
        jCheckBoxSSL.setFont(Constants.FONT_PLAIN);
        jCheckBoxSSL.setForeground(Constants.FONT_COLOR);
        jCheckBoxSSL.setSelected(Constants.CONFIG_PROP.getProperty("SSL").equals("true"));
        jCheckBoxSSL.setText(Constants.LANG.getString("SSL")); 
        jCheckBoxSSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jCheckBoxSSLActionPerformed(evt);
            }
        });

        jCheckBoxMailAuth.setBackground(Constants.BKG_MAIN_COLOR);
        jCheckBoxMailAuth.setFont(Constants.FONT_PLAIN);
        jCheckBoxMailAuth.setForeground(Constants.FONT_COLOR);
        jCheckBoxMailAuth.setEnabled(jCheckBoxSSL.isSelected());
        if (Constants.CONFIG_PROP.getProperty("Authentication") != null)
        	jCheckBoxMailAuth.setSelected(Constants.CONFIG_PROP.getProperty("Authentication").equals("true"));
        
        jCheckBoxMailAuth.setText(Constants.LANG.getString("Authenticated")); 
        jCheckBoxMailAuth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jCheckBoxMailAuthActionPerformed(evt);
            }
        });

        jLabelMailUser.setFont(Constants.FONT_PLAIN);
        jLabelMailUser.setForeground(Constants.FONT_COLOR);
        jLabelMailUser.setText(Constants.LANG.getString("User")); 
        jLabelMailUser.setEnabled(jCheckBoxSSL.isSelected() && jCheckBoxMailAuth.isSelected());
        if (!jLabelMailUser.isEnabled())
        	jLabelMailUser.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelMailPass.setFont(Constants.FONT_PLAIN);
        jLabelMailPass.setForeground(Constants.FONT_COLOR);
        jLabelMailPass.setText(Constants.LANG.getString("Password")); 
        jLabelMailPass.setEnabled(jCheckBoxSSL.isSelected() && jCheckBoxMailAuth.isSelected());
        if (!jLabelMailPass.isEnabled())
        	jLabelMailPass.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelMail.setFont(Constants.FONT_PLAIN);
        jLabelMail.setForeground(Constants.FONT_COLOR);
        jLabelMail.setText(Constants.LANG.getString("EMail")); 
        
        jLabelDefaultSubject.setFont(Constants.FONT_PLAIN);
        jLabelDefaultSubject.setForeground(Constants.FONT_COLOR);
        jLabelDefaultSubject.setText(Constants.LANG.getString("DefaultSubject")); 
        
        jLabelStoreConfig.setFont(Constants.FONT_PLAIN);
        jLabelStoreConfig.setForeground(Constants.FONT_COLOR);
        
        updateLabelStore();
        
        jTextFieldSMTPServer.setFont(Constants.FONT_PLAIN);
        jTextFieldSMTPServer.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldSMTPServer.setText(Constants.CONFIG_PROP.getProperty("SMTPServer"));

        jTextFieldMailPort.setFont(Constants.FONT_PLAIN);
        jTextFieldMailPort.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldMailPort.setText(Constants.CONFIG_PROP.getProperty("mailPort"));
        jTextFieldMailPort.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt){
    			String value = ((JTextField)evt.getSource()).getText();   			
    			try {
    				int intVal = Integer.parseInt(value);
    				if (intVal < 0 || intVal > 65535) {
    					((JTextField)evt.getSource()).setText("");
    					((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
    					fd.showMessage(Constants.LANG.getString("NOOKParamPortNotValid"), Constants.ERROR_MSG_COLOR);
    				}
    			} catch (NumberFormatException e) {
    				((JTextField)evt.getSource()).setText("");
    				((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
    				fd.showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
    			}   
    		}
    	});
        jTextFieldMailPort.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);				
			}				
		});

        jTextFieldMailUser.setFont(Constants.FONT_PLAIN);
        jTextFieldMailUser.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldMailUser.setText(Constants.CONFIG_PROP.getProperty("mailUser"));
        jTextFieldMailUser.setEnabled(jCheckBoxSSL.isSelected() && jCheckBoxMailAuth.isSelected());
        if (!jTextFieldMailUser.isEnabled())
        	jTextFieldMailUser.setBackground(Constants.BKG_MAIN_COLOR);

        jTextFieldMail.setFont(Constants.FONT_PLAIN);
        jTextFieldMail.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldMail.setText(Constants.CONFIG_PROP.getProperty("EMail"));

        jTextFieldDefaultSubject.setFont(Constants.FONT_PLAIN);
        jTextFieldDefaultSubject.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldDefaultSubject.setText(Constants.CONFIG_PROP.getProperty("DefaultSubject"));

        jMailPasswordField.setFont(Constants.FONT_PLAIN);
        jMailPasswordField.setHorizontalAlignment(SwingConstants.RIGHT);
        jMailPasswordField.setText(new String(Base64.decode(Constants.CONFIG_PROP.getProperty("mailPwd"))));
        jMailPasswordField.setEnabled(jCheckBoxSSL.isSelected() && jCheckBoxMailAuth.isSelected());
        if (!jMailPasswordField.isEnabled())
        	jMailPasswordField.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelProxy.setFont(Constants.FONT_PLAIN);
        jLabelProxy.setForeground(Constants.FONT_COLOR);
        jLabelProxy.setText(Constants.LANG.getString("Proxy")); 

        jTextFieldProxy.setFont(Constants.FONT_PLAIN);
        jTextFieldProxy.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldProxy.setText(Constants.CONFIG_PROP.getProperty("proxy"));

        jLabelProxyPort.setText(Constants.LANG.getString("Port")); 

        jTextFieldProxyPort.setFont(Constants.FONT_PLAIN);
        jTextFieldProxyPort.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldProxyPort.setText(Constants.CONFIG_PROP.getProperty("proxyPort"));
        jTextFieldProxyPort.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent evt) {
    			String value = ((JTextField)evt.getSource()).getText();   			
    			try {
    				int intVal = Integer.parseInt(value);
    				if (intVal < 0 || intVal > 65535) {
    					((JTextField)evt.getSource()).setText("");
    					((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
    					fd.showMessage(Constants.LANG.getString("NOOKParamPortNotValid"), Constants.ERROR_MSG_COLOR);
    				}
    			} catch (NumberFormatException e) {
    				((JTextField)evt.getSource()).setText("");
    				((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
    				fd.showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
    			}    			
    		}
    	});
        jTextFieldProxyPort.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setForeground(Constants.FONT_COLOR);
			}				
		});

        jCheckBoxProxyAuth.setBackground(Constants.BKG_MAIN_COLOR);
        jCheckBoxProxyAuth.setFont(Constants.FONT_PLAIN);
        jCheckBoxProxyAuth.setForeground(Constants.FONT_COLOR);
        jCheckBoxProxyAuth.setSelected(Constants.CONFIG_PROP.getProperty("proxyAuth").equals("true"));
        jCheckBoxProxyAuth.setText(Constants.LANG.getString("Authenticated")); 
        jCheckBoxProxyAuth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jCheckBoxProxyAuthActionPerformed(evt);
            }
        });

        jLabelProxyUser.setFont(Constants.FONT_PLAIN);
        jLabelProxyUser.setForeground(Constants.FONT_COLOR);
        jLabelProxyUser.setText(Constants.LANG.getString("User")); 
        jLabelProxyUser.setEnabled(jCheckBoxProxyAuth.isSelected());
        if (!jLabelProxyUser.isEnabled())
        	jLabelProxyUser.setBackground(Constants.BKG_MAIN_COLOR);

        jTextFieldProxyUser.setFont(Constants.FONT_PLAIN);
        jTextFieldProxyUser.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldProxyUser.setText(Constants.CONFIG_PROP.getProperty("proxyUser"));
        jTextFieldProxyUser.setEnabled(jCheckBoxProxyAuth.isSelected());
        if (!jTextFieldProxyUser.isEnabled())
        	jTextFieldProxyUser.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelProxyPass.setFont(Constants.FONT_PLAIN);
        jLabelProxyPass.setForeground(Constants.FONT_COLOR);
        jLabelProxyPass.setText(Constants.LANG.getString("Password")); 
        jLabelProxyPass.setEnabled(jCheckBoxProxyAuth.isSelected());
        if (!jLabelProxyPass.isEnabled())
        	jLabelProxyPass.setBackground(Constants.BKG_MAIN_COLOR);

        jProxyPasswordField.setFont(Constants.FONT_PLAIN);
        jProxyPasswordField.setHorizontalAlignment(SwingConstants.RIGHT);
        jProxyPasswordField.setText(new String(Base64.decode(Constants.CONFIG_PROP.getProperty("proxyPwd"))));
        jProxyPasswordField.setEnabled(jCheckBoxProxyAuth.isSelected());
        if (!jProxyPasswordField.isEnabled())
        	jProxyPasswordField.setBackground(Constants.BKG_MAIN_COLOR);

        jSeparator2.setForeground(Constants.SEPARATOR_COLOR);
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        org.jdesktop.layout.GroupLayout jPanelMailAndConxLayout = new org.jdesktop.layout.GroupLayout(jPanelMailAndConx);
        jPanelMailAndConx.setLayout(jPanelMailAndConxLayout);
        jPanelMailAndConxLayout.setHorizontalGroup(
            jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMailAndConxLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelMailAndConxLayout.createSequentialGroup()
                    	.add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    			.add(jLabelMail)
                    			.add(jLabelDefaultSubject))
                    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    	.add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    			.add(jTextFieldMail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    			.add(jTextFieldDefaultSubject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanelMailAndConxLayout.createSequentialGroup()
                        .add(jCheckBoxMailAuth,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabelMailUser)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldMailUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(15,15,15)
                        .add(jLabelMailPass)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jMailPasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelMailAndConxLayout.createSequentialGroup()
                        .add(jLabelSMTP)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldSMTPServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(15,15,15)
                        .add(jLabelPort)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldMailPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(10, 10, 10)
                        .add(jCheckBoxSSL,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(12, 12, 12)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(20, 20, 20)
                .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelMailAndConxLayout.createSequentialGroup()
                        .add(jLabelProxy)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldProxy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(15,15,15)
                        .add(jLabelProxyPort)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelMailAndConxLayout.createSequentialGroup()
                    	.add(15,15,15)
                    	.add(jCheckBoxProxyAuth,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(15,15,15)
                        .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        	.add(jPanelMailAndConxLayout.createSequentialGroup()
                        		.add(jLabelProxyUser)
                        		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        		.add(jTextFieldProxyUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanelMailAndConxLayout.createSequentialGroup()
                        		.add(jLabelProxyPass)
                        		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        		.add(jProxyPasswordField,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanelMailAndConxLayout.setVerticalGroup(
            jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMailAndConxLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelSMTP)
                    .add(jTextFieldSMTPServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelPort)
                    .add(jTextFieldMailPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jCheckBoxSSL,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelProxy)
                    .add(jTextFieldProxy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelProxyPort)
                    .add(jTextFieldProxyPort, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jCheckBoxMailAuth,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelMailUser)
                    .add(jLabelMailPass)
                    .add(jMailPasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jCheckBoxProxyAuth,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldMailUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelProxyUser)
                    .add(jTextFieldProxyUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                        .add(jLabelMail)
                        .add(jTextFieldMail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                        .add(jLabelProxyPass)
                        .add(jProxyPasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMailAndConxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                        .add(jLabelDefaultSubject)
                        .add(jTextFieldDefaultSubject, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
            .add(jPanelMailAndConxLayout.createSequentialGroup()
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelOCSP.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelOCSP.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("OCSP"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelOCSP.setForeground(Constants.BKG_MAIN_COLOR);
        jPanelOCSP.setFont(Constants.FONT_PLAIN);

        jPanelExtensions.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelExtensions.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Extensions"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelExtensions.setForeground(Constants.BKG_MAIN_COLOR);
        jPanelExtensions.setFont(Constants.FONT_PLAIN);
        
        jCheckBoxOCSPValReceiv.setBackground(Constants.BKG_MAIN_COLOR);
        jCheckBoxOCSPValReceiv.setFont(Constants.FONT_PLAIN);
        jCheckBoxOCSPValReceiv.setForeground(Constants.FONT_COLOR);
        if (Constants.CONFIG_PROP.getProperty("ValidateOnReception") != null)
        	jCheckBoxOCSPValReceiv.setSelected(Constants.CONFIG_PROP.getProperty("ValidateOnReception").equals("true"));

        jCheckBoxOCSPValReceiv.setText(Constants.LANG.getString("ValidateOnReception")); 

        jCheckBoxOCSPValIssued.setBackground(Constants.BKG_MAIN_COLOR);
        jCheckBoxOCSPValIssued.setFont(Constants.FONT_PLAIN);
        jCheckBoxOCSPValIssued.setForeground(Constants.FONT_COLOR);
        if (Constants.CONFIG_PROP.getProperty("ValidateOnIssue") != null)
        	jCheckBoxOCSPValIssued.setSelected(Constants.CONFIG_PROP.getProperty("ValidateOnIssue").equals("true"));

        jCheckBoxOCSPValIssued.setText(Constants.LANG.getString("ValidateOnIssue")); 

        jLabelOCSPServer.setFont(Constants.FONT_PLAIN);
        jLabelOCSPServer.setForeground(Constants.FONT_COLOR);
        jLabelOCSPServer.setText(Constants.LANG.getString("OCSPserver")); 

        jTextFieldOCSPServer.setFont(Constants.FONT_PLAIN);
        jTextFieldOCSPServer.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldOCSPServer.setText(Constants.CONFIG_PROP.getProperty("urlOCSP"));

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanelOCSP);
        jPanelOCSP.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            	.add(jPanel5Layout.createSequentialGroup()
            		.addContainerGap()
            		.add(jCheckBoxOCSPValIssued)
            		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
            	.add(jPanel5Layout.createSequentialGroup()
            		.addContainerGap()
            		.add(jCheckBoxOCSPValReceiv)
            		.addContainerGap())
                .add(jPanel5Layout.createSequentialGroup()
                	.addContainerGap()
                	.add(jLabelOCSPServer)
                	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                	.add(jTextFieldOCSPServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 185, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                	.addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
            	.addContainerGap()
            	.add(jCheckBoxOCSPValIssued)
            	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCheckBoxOCSPValReceiv)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelOCSPServer)
                    .add(jTextFieldOCSPServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
        );
        
        jLabelXSDPath.setFont(Constants.FONT_PLAIN);
        jLabelXSDPath.setForeground(Constants.FONT_COLOR);
        jLabelXSDPath.setText(Constants.LANG.getString("Schema")); 
        
        jTextFieldXSDPath.setFont(Constants.FONT_PLAIN);
        jTextFieldXSDPath.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldXSDPath.setForeground(Constants.FONT_COLOR);
        
        jLabelNamespace.setFont(Constants.FONT_PLAIN);
        jLabelNamespace.setForeground(Constants.FONT_COLOR);
        jLabelNamespace.setText(Constants.LANG.getString("Namespace")); 
        
        jTextFieldNamespace.setFont(Constants.FONT_PLAIN);
        jTextFieldNamespace.setForeground(Constants.FONT_COLOR);
    	
        jButtonXSDPath.setIcon(new ImageIcon(getClass().getResource("/images/button_middle_folder.jpg"))); 
        jButtonXSDPath.setToolTipText(Constants.LANG.getString("Examine")); 
        jButtonXSDPath.setBorderPainted(false);
        jButtonXSDPath.setContentAreaFilled(false);
        jButtonXSDPath.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseExited(evt);
            }
        });
        jButtonXSDPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fc = new JFileChooser();
				CustomFileFilter filter = new CustomFileFilter();
		        filter.addExtension("xsd");
		        fc.setFileFilter(filter);

		        int returnVal = fc.showOpenDialog(confWindow);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	File file = fc.getSelectedFile();
		        	jTextFieldXSDPath.setText(file.getAbsolutePath());
		        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
		        	jTextFieldXSDPath.setText("");
		        	jTextFieldNamespace.setText("");
				} else if (returnVal == JFileChooser.ERROR_OPTION) {
					jTextFieldXSDPath.setText("");
					jTextFieldNamespace.setText("");
				} 
				
		        jButtonXSDPath.getTopLevelAncestor().setVisible(true);
			}
		});
        
        jButtonXSDAdd.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus.jpg"))); 
        jButtonXSDAdd.setToolTipText(Constants.LANG.getString("Add")); 
        jButtonXSDAdd.setBorderPainted(false);
        jButtonXSDAdd.setContentAreaFilled(false);
        jButtonXSDAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseExited(evt);
            }
        });
        jButtonXSDAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jTextFieldXSDPath != null && !"".equals(jTextFieldXSDPath.getText().trim())){
					if (jTextFieldNamespace != null && !"".equals(jTextFieldNamespace.getText().trim())){
						// If the XSD and the XMLNS are specified, the operation go on
						String fileName = jTextFieldXSDPath.getText();
						String[] schemaSplit = fileName.split("\\.");
						String schemaExtension = schemaSplit[schemaSplit.length-1];
						if(schemaExtension.equalsIgnoreCase("xsd")){
							// If the extension is correct (is a XML Schema), the operation go on
							File f = new File(fileName);
							String schemaId = f.getName().split("\\.")[0].toUpperCase().replaceAll(Pattern.compile("[ \t\n\f\r]").pattern(),"_");
							int count = jTableXSD.getModel().getRowCount();
							boolean exist = false;
							for (int i = 0 ; i < count ; i++){
								if (((DefaultTableModel)jTableXSD.getModel()).getValueAt(i, 0).toString().equals(schemaId)){
									exist = true;
									i = count;
								}
							}
							if (!exist){
								// File-Copy  
								File f2 = new File(Constants.APP_PROP.getProperty("config_scheme_folder")+f.getName());
								if (!f2.exists()) {
									// The extension and file associated do not exist, so the extension can be added
									try{
										f2.createNewFile();
										FileUtil.copy(f,f2);
										// Pattern p = Pattern.compile("\\",Pattern.LITERAL);
										// extProperties.setProperty("EXTENSION_SCHEMA_"+schemaId, f2.getPath().replaceAll(p.pattern(), "/"));
										extProps.setProperty("EXTENSION_SCHEMA_"+schemaId, f2.getPath());
										extProps.setProperty(schemaId+"_EXTENSION_NAMESPACE",jTextFieldNamespace.getText());
										if (jTableXSD.getRowCount() > 0)
											extProps.setProperty("EXTENSION_SCHEMAS",extProps.getProperty("EXTENSION_SCHEMAS")+";"+schemaId);
										else
											extProps.setProperty("EXTENSION_SCHEMAS",extProps.getProperty("EXTENSION_SCHEMAS")+schemaId);
										extProps.store(new FileOutputStream(Constants.APP_PROP.getProperty("extensions_file")), new String("New extension added : " + schemaId));
										((DefaultTableModel)jTableXSD.getModel()).addRow(new String[]{schemaId});
										ValidatorUtil.reload();
										fd.showMessage(Constants.LANG.getString("OKMessageExtensionAdded"), Constants.OK_MSG_COLOR);
									}
									catch(IOException ioe){
										logger.error("Error while copying file: " + ioe.getMessage());
										fd.showMessage(Constants.LANG.getString("NOOKMessageCopyingFile"), Constants.ERROR_MSG_COLOR);
									}
								}
								else{
									fd.showMessage(Constants.LANG.getString("NOOKMessageFileExist"), Constants.ERROR_MSG_COLOR);
								}
							}
							else{
								fd.showMessage(Constants.LANG.getString("NOOKMessageExtensionExist"), Constants.ERROR_MSG_COLOR);
							}
						}
						else{
							fd.showMessage(Constants.LANG.getString("NOOKMessageFileExtension"), Constants.ERROR_MSG_COLOR);
						}
					}
					else{
						fd.showMessage(Constants.LANG.getString("NOOKMessageNamespaceEmpty"), Constants.ERROR_MSG_COLOR);
					}
				}
				else{
					fd.showMessage(Constants.LANG.getString("FileXSDRequiredMessage"), Constants.ERROR_MSG_COLOR);
				}
			}
		});
        
        jButtonXSDDel.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus.jpg"))); 
        jButtonXSDDel.setToolTipText(Constants.LANG.getString("Remove")); 
        jButtonXSDDel.setBorderPainted(false);
        jButtonXSDDel.setContentAreaFilled(false);
        jButtonXSDDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseExited(evt);
            }
        });
        jButtonXSDDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jTableXSD.getSelectedRow() > -1){
					String schemaId = (((DefaultTableModel)jTableXSD.getModel()).getValueAt(jTableXSD.getSelectedRow(), 0)).toString();
					File f = new File(extProps.getProperty("EXTENSION_SCHEMA_" + schemaId));
					if (f.delete()){
						extProps.remove("EXTENSION_SCHEMA_" + schemaId);
						extProps.remove(schemaId+"_EXTENSION_NAMESPACE");
						String[] extensions = extProps.getProperty("EXTENSION_SCHEMAS").split(";");
						String extensionsNew = "";
						for (int i = 0 ; i < extensions.length ; i++){
							if (!schemaId.equals(extensions[i])){
								if ("".equals(extensionsNew))
									extensionsNew = extensionsNew + extensions[i];
								else
									extensionsNew = extensionsNew + ";" + extensions[i];
							}
						}
						extProps.setProperty("EXTENSION_SCHEMAS", extensionsNew);
						try {
							extProps.store(new FileOutputStream(Constants.APP_PROP.getProperty("extensions_file")), new String("Extension deleted : " + schemaId));
							((DefaultTableModel)jTableXSD.getModel()).removeRow(jTableXSD.getSelectedRow());
							ValidatorUtil.reload();
							fd.showMessage(Constants.LANG.getString("OKMessageExtensionDeleted"), Constants.OK_MSG_COLOR);
						} catch (FileNotFoundException e) {
							fd.showMessage(Constants.LANG.getString("NOOKMessageProcessDeleteExt"), Constants.ERROR_MSG_COLOR);
						} catch (IOException e) {
							fd.showMessage(Constants.LANG.getString("NOOKMessageProcessDeleteExt"), Constants.ERROR_MSG_COLOR);
						}
					}
					else{
						fd.showMessage(Constants.LANG.getString("NOOKMessageExtensionUsed"), Constants.ERROR_MSG_COLOR);
					}
				}
				else
					fd.showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
			}
		});
        
        jScrollPaneXSD.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneXSD.setAutoscrolls(true);
        jScrollPaneXSD.setOpaque(false);
        jScrollPaneXSD.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableXSD.setFont(Constants.FONT_PLAIN);
        jTableXSD.setForeground(Constants.FONT_COLOR);
        jTableXSD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                Constants.LANG.getString("Extensions")
            }
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        jTableXSD.setGridColor(Constants.SELECTION_COLOR);
        jTableXSD.getTableHeader().setReorderingAllowed(false);
        jScrollPaneXSD.setViewportView(jTableXSD);
        jTableXSD.getColumnModel().getColumn(0).setResizable(false);
        jTableXSD.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableXSD.getColumnModel().getColumn(0).setHeaderRenderer(new HeaderRenderer());
        
        String[] extensions = extProps.getProperty("EXTENSION_SCHEMAS").split(";");
        for (int i = 0 ; i < extensions.length && (extensions.length > 1 || (extensions.length == 1 && !"".equals(extensions[0].trim()))); i++)
        	((DefaultTableModel)jTableXSD.getModel()).addRow(new String[]{extensions[i]});
        
        org.jdesktop.layout.GroupLayout jPanelExtensionsLayout = new org.jdesktop.layout.GroupLayout(jPanelExtensions);
        jPanelExtensions.setLayout(jPanelExtensionsLayout);
        jPanelExtensionsLayout.setHorizontalGroup(
        		jPanelExtensionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            		.add(jPanelExtensionsLayout.createSequentialGroup()
            			.addContainerGap()
            			.add(jPanelExtensionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			        		.add(jPanelExtensionsLayout.createSequentialGroup()
			            		.add(jLabelXSDPath,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
			            		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			            		.add(jTextFieldXSDPath,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,200,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
			            		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			            		.add(jButtonXSDPath,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
			            	.add(jPanelExtensionsLayout.createSequentialGroup()
			            		.add(jLabelNamespace,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
				            	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
				            	.add(jTextFieldNamespace,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,220,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
			            	.add(jPanelExtensionsLayout.createSequentialGroup()
			            		.add(jScrollPaneXSD,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,290,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
			            		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
			            		.add(jPanelExtensionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			                		.add(jButtonXSDAdd,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
			                		.add(jButtonXSDDel,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanelExtensionsLayout.setVerticalGroup(
        	jPanelExtensionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelExtensionsLayout.createSequentialGroup()
            	.add(jPanelExtensionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
            		.add(jLabelXSDPath)
                	.add(jTextFieldXSDPath,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,20,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                	.add(jButtonXSDPath))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelExtensionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
            		.add(jLabelNamespace)
                	.add(jTextFieldNamespace,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,20,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(13,13,13)
                .add(jPanelExtensionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                	.add(jScrollPaneXSD,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,130,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                	.add(jPanelExtensionsLayout.createSequentialGroup()
                		.add(jButtonXSDAdd)
                		.add(jButtonXSDDel)))
                .add(2, 2, 2)
                .addContainerGap())
        );

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_cancel.jpg"))); 
        jButtonCancel.setToolTipText(Constants.LANG.getString("Cancel")); 
        jButtonCancel.setBorderPainted(false);
        jButtonCancel.setContentAreaFilled(false);
        jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseExited(evt);
            }
        });
        jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelActionPerformed();
			}
		});

        jButtonAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_accept.jpg"))); 
        jButtonAccept.setToolTipText(Constants.LANG.getString("Accept")); 
        jButtonAccept.setBorderPainted(false);
        jButtonAccept.setContentAreaFilled(false);
        jButtonAccept.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseExited(evt);
            }
        });
        jButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean flag = false;
				
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldDraftId.getForeground()) ) {
					jTextFieldDraftId.setForeground(Constants.FONT_COLOR);
        			return;
				} else if (jTextFieldDraftId.getText() == null || "".equals(jTextFieldDraftId.getText().trim())) {
					jTextFieldDraftId.setBackground(Constants.BKG_ERROR_COLOR);
					flag = true;
				}
				
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldIssuedId.getForeground()) ) {
					jTextFieldIssuedId.setForeground(Constants.FONT_COLOR);
        			return;
				} else if (jTextFieldIssuedId.getText() == null || "".equals(jTextFieldIssuedId.getText().trim())) {
					jTextFieldIssuedId.setBackground(Constants.BKG_ERROR_COLOR);
					flag = true;
				}
				
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldRecievedId.getForeground()) ) {
					jTextFieldRecievedId.setForeground(Constants.FONT_COLOR);
        			return;
				} else if (jTextFieldRecievedId.getText() == null || "".equals(jTextFieldRecievedId.getText().trim())) {
					jTextFieldRecievedId.setBackground(Constants.BKG_ERROR_COLOR);
					flag = true;
				}
				
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldRecDraftId.getForeground()) ) {
					jTextFieldRecDraftId.setForeground(Constants.FONT_COLOR);
        			return;
				} else if (jTextFieldRecDraftId.getText() == null || "".equals(jTextFieldRecDraftId.getText().trim())) {
					jTextFieldRecDraftId.setBackground(Constants.BKG_ERROR_COLOR);
					flag = true;
				}
				
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldRecIssuedId.getForeground()) ) {
					jTextFieldRecIssuedId.setForeground(Constants.FONT_COLOR);
        			return;
				} else if (jTextFieldRecIssuedId.getText() == null || "".equals(jTextFieldRecIssuedId.getText().trim())) {
					jTextFieldRecIssuedId.setBackground(Constants.BKG_ERROR_COLOR);
					flag = true;
				}
				
				if (flag) {
					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
					return;
				}
					
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldMailPort.getForeground()) ) {
					jTextFieldMailPort.setForeground(Constants.FONT_COLOR);
					return;
				}
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldProxyPort.getForeground()) ) {
					jTextFieldProxyPort.setForeground(Constants.FONT_COLOR);
					return;
				}
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldSeries.getForeground()) ) {
					jTextFieldSeries.setForeground(Constants.FONT_COLOR);
					return;
				}
				if (Constants.ERROR_MSG_COLOR.equals(jTextFieldCorrecSeries.getForeground()) ) {
					jTextFieldCorrecSeries.setForeground(Constants.FONT_COLOR);
					return;
				}
				
				if ((jTextFieldSeries.getText() == null && jTextFieldCorrecSeries.getText() == null) || 
						jTextFieldSeries.getText() != null && jTextFieldCorrecSeries.getText() != null && "".equals(jTextFieldSeries.getText().trim()) && "".equals(jTextFieldCorrecSeries.getText().trim())) {
					jTextFieldCorrecSeries.setText(Constants.LANG.getString("CorrectiveSerie"));
        			fd.showMessage(Constants.LANG.getString("NOOKMessageConfigurationSeries"), Constants.ERROR_MSG_COLOR);
        			return;
        		}
				
				/** The next lines remove the logo imagen from ConfigurationWindow when an empty string is introduced in jTextFieldLogoPath **/
		    	if(jTextFieldLogoPath.getText() == null || "".equals(jTextFieldLogoPath.getText().trim())){
		    		jLabelLogoIcon.setIcon(null);
		    	}
		    	
		    	/** The next lines remove the logo imagen from MainWindow when an empty string is introduced in jTextFieldLogoPath **/
		    	String oldValue = Constants.CONFIG_PROP.getProperty("logoPath");
		    	Constants.CONFIG_PROP.setProperty("logoPath", jTextFieldLogoPath.getText());	
		    	firePropertyChange("logoPath", oldValue, null);
		    	
				Constants.CONFIG_PROP.setProperty("draftId", jTextFieldDraftId.getText());				
				Constants.CONFIG_PROP.setProperty("issuedId", jTextFieldIssuedId.getText());
		        Constants.CONFIG_PROP.setProperty("receivedId", jTextFieldRecievedId.getText());
		        Constants.CONFIG_PROP.setProperty("c-draftId", jTextFieldRecDraftId.getText());
		        Constants.CONFIG_PROP.setProperty("c-issuedId", jTextFieldRecIssuedId.getText());
		        Constants.CONFIG_PROP.setProperty("series", jTextFieldSeries.getText());
		        Constants.CONFIG_PROP.setProperty("CorrectedSeries", jTextFieldCorrecSeries.getText());
		        Constants.CONFIG_PROP.setProperty("SSL", jCheckBoxSSL.isSelected()?"true":"false");
		        Constants.CONFIG_PROP.setProperty("Authentication", jCheckBoxMailAuth.isSelected()?"true":"false");
		        Constants.CONFIG_PROP.setProperty("SMTPServer", jTextFieldSMTPServer.getText());
		        Constants.CONFIG_PROP.setProperty("mailPort", jTextFieldMailPort.getText());
		        Constants.CONFIG_PROP.setProperty("mailUser", jTextFieldMailUser.getText());   
		        Constants.CONFIG_PROP.setProperty("EMail", jTextFieldMail.getText());
		        Constants.CONFIG_PROP.setProperty("mailPwd", Base64.encode(String.valueOf(jMailPasswordField.getPassword())));
		        Constants.CONFIG_PROP.setProperty("proxy", jTextFieldProxy.getText());
		        Constants.CONFIG_PROP.setProperty("proxyPort", jTextFieldProxyPort.getText());
		        Constants.CONFIG_PROP.setProperty("proxyAuth", jCheckBoxProxyAuth.isSelected()?"true":"false");
		        Constants.CONFIG_PROP.setProperty("proxyUser", jTextFieldProxyUser.getText());
		        Constants.CONFIG_PROP.setProperty("proxyPwd", Base64.encode(String.valueOf(jProxyPasswordField.getPassword())));
		        Constants.CONFIG_PROP.setProperty("ValidateOnReception", jCheckBoxOCSPValReceiv.isSelected()?"true":"false");
	        	Constants.CONFIG_PROP.setProperty("ValidateOnIssue", jCheckBoxOCSPValIssued.isSelected()?"true":"false");
	        	Constants.CONFIG_PROP.setProperty("urlOCSP", jTextFieldOCSPServer.getText());
	        	Constants.CONFIG_PROP.setProperty("DefaultSubject", jTextFieldDefaultSubject.getText());
	        	Constants.CONFIG_PROP.setProperty("FACeEmail", fcd.getjTextFieldFACeEmail().getText());
	        	Constants.CONFIG_PROP.setProperty("FACeCert", fcd.getjTextFieldFACeCert().getText());	        	
	        	Constants.CONFIG_PROP.setProperty("FACeProvider", ""+fcd.getCertificateProvider());

	        	switch(fcd.getCertificateProvider()) {
		        	case WINDOWS:
		        	case DNIE:
	    				File f = new File(Constants.FACE_KEYSTORE_FILE);
	    				if(f.exists()) {
	    					f.delete();
	    				}
	    				try {f.createNewFile();} catch (IOException e) {
	    					logger.error("Error: " + e.getMessage(), e);
	    				}
	    				break;
		        	default:
			        	X509Certificate faceCert = fcd.getFaceCert();
				        	PrivateKey facePK = fcd.getFacePK();
				    		try{
				    			if(faceCert != null && facePK != null) {
				    				KeyStore ks = KeyStore.getInstance("JCEKS");
				    				
				    				ks.load(null,"".toCharArray());
				    				KeyStore.PrivateKeyEntry ksEntry = new KeyStore.PrivateKeyEntry(facePK, new Certificate[]{faceCert});
				    				
				    				//Preguntar contraseña
				    				DefaultPassStoreKS handler = new DefaultPassStoreKS();
				    				handler.setPINMessage(Constants.LANG.getString("FACePIN"));
				    				handler.setTitle(Constants.LANG.getString("FACePIN"));
				    				KeyStore.PasswordProtection pp = new KeyStore.PasswordProtection(handler.getPassword(null, null));
				    				//Importar certificado al JKS
				    				ks.setEntry(fcd.getjTextFieldFACeCert().getText(), ksEntry, pp);
				    	
				    				//Crear un almacen JKS (borrar si ya existia)
				    				File fKstore = new File(Constants.FACE_KEYSTORE_FILE);
				    				fKstore.createNewFile();
				    				FileOutputStream fos = new FileOutputStream(fKstore);
				    				ks.store(fos, Constants.FACE_KEYSTORE_PASSWORD);
				    				fos.close();
				    			}
				    		} catch (KeyStoreException e) {
				    			logger.error("Error: " + e.getMessage(), e);
				    		} catch (NoSuchAlgorithmException e) {
				    			logger.error("Error: " + e.getMessage(), e);
				    		} catch (CertificateException e) {
				    			logger.error("Error: " + e.getMessage(), e);
				    		} catch (IOException e) {
				    			logger.error("Error: " + e.getMessage(), e);
				    		}
				    		break;
	        	}
	        	
	        	try {
	        		OutputStream f = new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file")));
	        		Constants.CONFIG_PROP.store(f, null);
	        	} catch(IOException e) {
	        		logger.error("The configuration could not be saved: " + e.getMessage()); 
	        		return;
	        	}
		        
	        	confWindow = null;
	        	setVisible(false);
				dispose();
		        
				try {
					FacturaeManager.getInstance().saveAction(EnumOperationType.ConfChange, null);
				} catch (DatabaseOperationException e) {
					// The user is not informed about action not saved event. An error log has been created previously.
				}
			}
		});
        
        jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/images/button_help.jpg"))); 
        jButtonHelp.setToolTipText(Constants.LANG.getString("Help")); 
        jButtonHelp.setBorderPainted(false);
        jButtonHelp.setContentAreaFilled(false);
        jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	ConfigurationWindow.this.mouseExited(evt);
            }
        });
        jButtonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonHelpActionPerformed();
			}
		});
        
        jPanelTopImage.setBackground(java.awt.Color.white);
        jPanelTopImage.setBorder(new javax.swing.border.LineBorder(Constants.BORDER_COLOR, 1, true));

        jLabelBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/topbar2.jpg")));
        
        jLabelBannerMsg1.setFont(Constants.TITLE_FONT_ITALIC);
        jLabelBannerMsg1.setForeground(Constants.FONT_COLOR);
        jLabelBannerMsg1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelBannerMsg1.setText(Constants.LANG.getString("TopBarMessage1")); 

        jLabelBannerMsg2.setFont(Constants.TITLE_FONT_ITALIC);
        jLabelBannerMsg2.setForeground(Constants.FONT_COLOR);
        jLabelBannerMsg2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelBannerMsg2.setText(Constants.LANG.getString("TopBarMessage2")); 

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanelTopImage);
        jPanelTopImage.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jLabelBanner, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jLabelBannerMsg2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabelBannerMsg1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                .add(13, 13, 13))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel8Layout.createSequentialGroup()
                    .add(jLabelBannerMsg1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jLabelBannerMsg2))
                .add(jLabelBanner))
        );
        
        jPanelLanguage.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelLanguage.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Language"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelLanguage.setForeground(Constants.BKG_MAIN_COLOR);
        jPanelLanguage.setFont(Constants.FONT_PLAIN);
        
        jPanelStoreConfig.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelStoreConfig.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Store.Certificates"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelStoreConfig.setForeground(Constants.BKG_MAIN_COLOR);
        jPanelStoreConfig.setFont(Constants.FONT_PLAIN);
        
        jLabelLanguage.setFont(Constants.FONT_PLAIN);
        jLabelLanguage.setForeground(Constants.FONT_COLOR);
     // Set language in JTextField
        String language = Constants.CONFIG_PROP.getProperty("language");
    	if (language == null)
    		language = Constants.LANG.getString("Spanish");
    	else if (language.equals(LanguageCodeType.ES.value()))
    		language = Constants.LANG.getString("Spanish");
    	else if(language.equals(LanguageCodeType.CA.value()))
    		language = Constants.LANG.getString("Catalan");
    	else if(language.equals(LanguageCodeType.EU.value()))
    		language = Constants.LANG.getString("Basque");
    	else if(language.equals(LanguageCodeType.GL.value()))
    		language = Constants.LANG.getString("Galician");
		else if(language.equals(LanguageCodeType.EN.value()))
			language = Constants.LANG.getString("English");
		else
			language = Constants.LANG.getString("Spanish");
    	jLabelLanguage.setText(Constants.LANG.getString("PresentationLanguage") + ": " + language); 
        
        jButtonLanguage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonLanguage.setToolTipText(Constants.LANG.getString("PresentationLanguage")); 
        jButtonLanguage.setBorderPainted(false);
        jButtonLanguage.setContentAreaFilled(false);
        jButtonLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLanguageActionPerformed();
            }
        });
        jButtonLanguage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseExited(evt);
            }
        });
        
        jButtonStoreConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonStoreConfig.setToolTipText(Constants.LANG.getString("Store.StoreConfigChangeMessage")); 
        jButtonStoreConfig.setBorderPainted(false);
        jButtonStoreConfig.setContentAreaFilled(false);
        jButtonStoreConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStoreActionPerformed();
            }
        });
        jButtonStoreConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ConfigurationWindow.this.mouseExited(evt);
            }
        });
        
        org.jdesktop.layout.GroupLayout jPanelLanguageLayout = new org.jdesktop.layout.GroupLayout(jPanelLanguage);
        jPanelLanguage.setLayout(jPanelLanguageLayout);
        jPanelLanguageLayout.setHorizontalGroup(
        	jPanelLanguageLayout.createSequentialGroup()
            	.addContainerGap()
                .add(jLabelLanguage)
                .add(70,70,70)
                .add(jButtonLanguage)
                .add(org.jdesktop.layout.LayoutStyle.RELATED)
                .addContainerGap())
        ;
        jPanelLanguageLayout.setVerticalGroup(
        		jPanelLanguageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelLanguageLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                 .add(jLabelLanguage)
                 .add(jButtonLanguage))
        );
        
        org.jdesktop.layout.GroupLayout jPanelStoreLayout = new org.jdesktop.layout.GroupLayout(jPanelStoreConfig);
        jPanelStoreConfig.setLayout(jPanelStoreLayout);
        jPanelStoreLayout.setHorizontalGroup(
        		jPanelStoreLayout.createSequentialGroup()
            	.addContainerGap()
                .add(jLabelStoreConfig)
                .add(50,50,50)
                .add(jButtonStoreConfig)
                .add(org.jdesktop.layout.LayoutStyle.RELATED)
                .addContainerGap())
        ;
        jPanelStoreLayout.setVerticalGroup(
        		jPanelStoreLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelStoreLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                 .add(jLabelStoreConfig)
                 .add(jButtonStoreConfig))
        );

        GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
        mainPanelLayout.setHorizontalGroup(
        	mainPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(mainPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jPanelTopImage, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
        					.addComponent(jPanelGeneral, GroupLayout.PREFERRED_SIZE, 362, GroupLayout.PREFERRED_SIZE)
        					.addGap(7)
        					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(mainPanelLayout.createSequentialGroup()
        							.addComponent(jPanelLogo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE))
        						.addComponent(jPanelFACe, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)))
        				.addGroup(Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
        					.addComponent(jButtonAccept, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
        					.addGap(30)
        					.addComponent(jButtonCancel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
        					.addGap(0)
        					.addComponent(fd, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
        					.addGap(0))
        				.addGroup(Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
        					.addComponent(jButtonHelp, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
        					.addGap(10))
        				.addGroup(Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
        					.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jPanelOCSP, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jPanelLanguage, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jPanelStoreConfig, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jPanelExtensions, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(jPanelMailAndConx, 690, 690, 690))
        			.addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
        	mainPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(mainPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanelTopImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(mainPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jPanelGeneral, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        				.addGroup(mainPanelLayout.createSequentialGroup()
        					.addComponent(jPanelLogo, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jPanelFACe, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jPanelMailAndConx, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(mainPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jPanelExtensions, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
        				.addGroup(mainPanelLayout.createSequentialGroup()
        					.addComponent(jPanelOCSP, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jPanelLanguage, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jPanelStoreConfig, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(mainPanelLayout.createParallelGroup(Alignment.CENTER)
        				.addComponent(jButtonCancel)
        				.addComponent(jButtonAccept)
        				.addComponent(fd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jButtonHelp, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        
        mainPanel.setLayout(mainPanelLayout);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 708, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void updateLabelStore() {
    	File fichero = new File(System.getProperty(Constants.APP_PROP.getProperty("USER_HOME")) + File.separator + Constants.APP_PROP.getProperty("CONFIG_EXT_DIR"), Constants.APP_PROP.getProperty("CONFIG_EXT_FILE"));
        if (!fichero.exists()){
        	if (pacu == null)
        		pacu = new PluginAlmCertUCCD();
        	pacu.guardarConfiguracion();
        }
        /** Loading store properties */
	    Properties storeProp = new Properties();
		try {
	        InputStream is = new FileInputStream(fichero);
	        storeProp.load(is);
	        is.close();
	    }
		catch(Exception e){
	        logger.error("Unable to load store properties. Path not found:" + fichero.getPath() + ": " + e.getMessage());
	        return;
	    }
        jLabelStoreConfig.setText(Constants.LANG.getString("Store.StoreConfig")+": "+ storeProp.getProperty("almacenCerts"));	
	}

	private void jCheckBoxSSLActionPerformed(java.awt.event.ActionEvent evt) {
    	boolean sel = ((JCheckBox)evt.getSource()).isSelected();
        jCheckBoxMailAuth.setEnabled(sel);        	
        sel = sel && jCheckBoxMailAuth.isSelected();
        jLabelMailUser.setEnabled(sel);
        jTextFieldMailUser.setEnabled(sel);
        jLabelMailPass.setEnabled(sel);
        jMailPasswordField.setEnabled(sel);
        if (sel) {
        	jLabelMailUser.setBackground(java.awt.Color.white);
            jTextFieldMailUser.setBackground(java.awt.Color.white);
            jLabelMailPass.setBackground(java.awt.Color.white);
            jMailPasswordField.setBackground(java.awt.Color.white);
        } else {
        	jLabelMailUser.setBackground(Constants.BKG_MAIN_COLOR);
            jTextFieldMailUser.setBackground(Constants.BKG_MAIN_COLOR);
            jLabelMailPass.setBackground(Constants.BKG_MAIN_COLOR);
            jMailPasswordField.setBackground(Constants.BKG_MAIN_COLOR);
        }
    }
    
    private void jButtonCancelActionPerformed() {
		setVisible(false);
    	if (confWindow != null) {
    		confWindow.dispose();
        	confWindow = null;
    	}
    }
    
    private void jCheckBoxMailAuthActionPerformed(java.awt.event.ActionEvent evt) {
    	boolean sel = ((JCheckBox)evt.getSource()).isSelected();
        jLabelMailUser.setEnabled(sel);
        jTextFieldMailUser.setEnabled(sel);
        jLabelMailPass.setEnabled(sel);
        jMailPasswordField.setEnabled(sel);
        if (sel) {
        	jLabelMailUser.setBackground(java.awt.Color.white);
            jTextFieldMailUser.setBackground(java.awt.Color.white);
            jLabelMailPass.setBackground(java.awt.Color.white);
            jMailPasswordField.setBackground(java.awt.Color.white);
        } else {
        	jLabelMailUser.setBackground(Constants.BKG_MAIN_COLOR);
            jTextFieldMailUser.setBackground(Constants.BKG_MAIN_COLOR);
            jLabelMailPass.setBackground(Constants.BKG_MAIN_COLOR);
            jMailPasswordField.setBackground(Constants.BKG_MAIN_COLOR);
        }
    }
    
    private void jCheckBoxProxyAuthActionPerformed(java.awt.event.ActionEvent evt) {
    	boolean sel = ((JCheckBox)evt.getSource()).isSelected();
        jLabelProxyUser.setEnabled(sel);
        jTextFieldProxyUser.setEnabled(sel);
        jLabelProxyPass.setEnabled(sel);
        jProxyPasswordField.setEnabled(sel);
        if (sel) {
        	jLabelProxyUser.setBackground(java.awt.Color.white);
            jTextFieldProxyUser.setBackground(java.awt.Color.white);
            jLabelProxyPass.setBackground(java.awt.Color.white);
            jProxyPasswordField.setBackground(java.awt.Color.white);
        } else {       	
            jLabelProxyUser.setBackground(Constants.BKG_MAIN_COLOR);
            jTextFieldProxyUser.setBackground(Constants.BKG_MAIN_COLOR);
            jLabelProxyPass.setBackground(Constants.BKG_MAIN_COLOR);
            jProxyPasswordField.setBackground(Constants.BKG_MAIN_COLOR);
        }
    }

    private void jButtonLogoActionPerformed() {
    	JFileChooser fc = new JFileChooser();
    	logger.debug(fc.getLocation().x + "--" + fc.getLocation().y);
    	CustomFileFilter filter = new CustomFileFilter();
        filter.addExtension("tiff");
        filter.addExtension("tif");
        filter.addExtension("gif");
        filter.addExtension("jpeg");
        filter.addExtension("jpg");
        filter.addExtension("png");
        filter.addExtension("bmp");
        filter.setDescription(Constants.LANG.getString("ImageFiles"));
        fc.setFileFilter(filter);
        
    	int returnVal = fc.showOpenDialog(confWindow);
    	ImageIcon imgIcon = null;
    	
    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    		
    		String oldValue = Constants.CONFIG_PROP.getProperty("logoPath");
    			
    		File file = fc.getSelectedFile();
    		
    		logger.debug(jLabelLogoIcon.getWidth() + "-" + jLabelLogoIcon.getHeight());
    		imgIcon = ImageUtil.createScaledImageIcon(file, jLabelLogoIcon, 305, 121);

    		jTextFieldLogoPath.setText(file.getAbsolutePath());
    		
    		jLabelLogoIcon.setIcon(imgIcon);
    		jLabelLogoIcon.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    		jLabelLogoIcon.setVerticalAlignment(javax.swing.JLabel.CENTER);

    		Constants.CONFIG_PROP.setProperty("logoPath", file.getAbsolutePath());
    		
    		String newValue = file.getAbsolutePath();
    		
    		try{
    			Constants.CONFIG_PROP.store(new FileOutputStream(Constants.APP_PROP.getProperty("config_file")), "new logo path");
    		}
    		catch (IOException ioe){
    			logger.error("The logo indicated could not be stored: " + ioe.getMessage()); 
    		}     
    		
    		firePropertyChange("logoPath", oldValue, newValue);   		
    	}
    }
    
    private void jButtonDelLogoActionPerformed(){
    	String oldValue = Constants.CONFIG_PROP.getProperty("logoPath");
    	jTextFieldLogoPath.setText("");
    	jLabelLogoIcon.setIcon(null);
    	Constants.CONFIG_PROP.setProperty("logoPath", "");
		try{
			Constants.CONFIG_PROP.store(new FileOutputStream(Constants.APP_PROP.getProperty("config_file")), "new logo path");
		}
		catch (IOException ioe){
			logger.error("The logo indicated could not be deleted: " + ioe.getMessage()); 
		}     
		
		firePropertyChange("logoPath", oldValue, "");   	
    }
    
    private void jButtonLanguageActionPerformed() {
    	String language = Constants.CONFIG_PROP.getProperty("language");
    	InputLanguageDialog ild = new InputLanguageDialog(this, true);
    	ild.setValue(language);
    	ild.setVisible(true);
    	language = ild.getValue();
    	ild.dispose();
    	// Selected language is saved in configuration file
    	if (!Constants.CONFIG_PROP.getProperty("language").equals(language)) {
    		Constants.DIALOG.showWarnLanguage();
    	}
    	Constants.CONFIG_PROP.setProperty("language", language);
    	try {
    		OutputStream f = new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file")));
    		Constants.CONFIG_PROP.store(f, null);
    	} catch(IOException e) {}
    	// Selected language is saved in sign file
    	Constants.SIGN_PROP.setProperty("locale.language", language);
    	try {
    		OutputStream f = new FileOutputStream(new File(Constants.APP_PROP.getProperty("sign_file")));
    		Constants.SIGN_PROP.store(f, null);
    	} catch(IOException e) {}
    	// Set language in JTextField
    	if (language == null)
    		language = Constants.LANG.getString("Spanish");
    	else if (language.equals(LanguageCodeType.ES.value()))
    		language = Constants.LANG.getString("Spanish");
    	else if(language.equals(LanguageCodeType.CA.value()))
    		language = Constants.LANG.getString("Catalan");
    	else if(language.equals(LanguageCodeType.EU.value()))
    		language = Constants.LANG.getString("Basque");
    	else if(language.equals(LanguageCodeType.GL.value()))
    		language = Constants.LANG.getString("Galician");
		else if(language.equals(LanguageCodeType.EN.value()))
			language = Constants.LANG.getString("English");
		else
			language = Constants.LANG.getString("Spanish");
    	jLabelLanguage.setText(Constants.LANG.getString("PresentationLanguage") + ": " + language); 
    }
    
    private void jButtonStoreActionPerformed() {
    	pacu = new PluginAlmCertUCCD();
    	pacu.configItemCertActionPerformed();
    	updateLabelStore();
    }
    
    private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_config_" +Constants.LANG.getLocale().getLanguage()+ ".html");
    	if (helpFile == null) {
    		Constants.DIALOG.showErrorHelp();
    		return;
    	}
    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
    }
    
    private void idValidator(FocusEvent evt, int facCase) {
		String value = ((JTextField)evt.getSource()).getText();   			
		try {
			int intVal = Integer.parseInt(value);
			if (intVal <= 0) {
				((JTextField)evt.getSource()).setText("");
				((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
				fd.showMessage(Constants.LANG.getString("NOOKParamNegative"), Constants.ERROR_MSG_COLOR);
			} else {
				String idSerie = null;
				switch (facCase) {
					case 0: // Draft
						idSerie = Constants.LANG.getString("Draft");
						break;
					case 1: // Issued
						idSerie = jTextFieldSeries.getText();
						break;
					case 2: // Received
						idSerie = Constants.LANG.getString("Received");
						break;
					case 3:	// Corrective draft
						idSerie = "Rec" + Constants.LANG.getString("Draft");
						break;
					case 4:	// Corrective issued
						idSerie = jTextFieldCorrecSeries.getText();
						break;
					default:
						idSerie = jTextFieldSeries.getText();
				}
				String idNumber = ((JTextField)evt.getSource()).getText();
				SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT * FROM INVOICE WHERE NVL(SERIES_CODE + NUMBER, NUMBER)= '" + idSerie + idNumber + "'");
				List<?> ls = s.list();
				if (ls.size() > 0) {
					// If the element is contained in the Data Base it already exists
					logger.debug(s.toString());
					((JTextField)evt.getSource()).setText("");
					((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
					fd.showMessage(Constants.LANG.getString("NOOKParamAlreadyExists"), Constants.ERROR_MSG_COLOR);
				}
			}
		} catch (NumberFormatException e) {
			((JTextField)evt.getSource()).setText("");
			((JTextField)evt.getSource()).setForeground(Constants.ERROR_MSG_COLOR);
			fd.showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
		}   
    }
    
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void jButtonFACeConfigActionPerformed() {
    	fcd.init();
    	fcd.setVisible(true);	
    }
    
	private void jLabelFACeLinkActionPerformed() {
		try {
			Desktop.getDesktop().browse(new URI(Constants.FACE_URL));
        } catch (Exception ex) {
		}
	}
    // Variables declaration
    private javax.swing.JPanel mainPanel = null;
    private javax.swing.JPanel jPanelGeneral = null;
    private javax.swing.JPanel jPanelLogo = null;
    private javax.swing.JPanel jPanelMailAndConx = null;
    private javax.swing.JPanel jPanelOCSP = null;
    private javax.swing.JPanel jPanelExtensions = null;
    private javax.swing.JPanel jPanelTopImage = null;
    private javax.swing.JPanel jPanelLanguage = null;
    private javax.swing.JPanel jPanelStoreConfig = null;
    private javax.swing.JPanel jPanelFACe = null;
    
    private javax.swing.JButton jButtonLogoDir = null;
    private javax.swing.JButton jButtonLogoDel = null;
    private javax.swing.JButton jButtonLanguage = null;
    private javax.swing.JButton jButtonCancel = null;
    private javax.swing.JButton jButtonAccept = null;
    private javax.swing.JButton jButtonHelp = null;
    
    private javax.swing.JButton jButtonStoreConfig = null;
    
    private javax.swing.JButton jButtonXSDPath = null;
    private javax.swing.JButton jButtonXSDAdd = null;
    private javax.swing.JButton jButtonXSDDel = null;
    
    private javax.swing.JCheckBox jCheckBoxSSL = null;
    private javax.swing.JCheckBox jCheckBoxMailAuth = null;
    private javax.swing.JCheckBox jCheckBoxProxyAuth = null;
    private javax.swing.JCheckBox jCheckBoxOCSPValReceiv = null;
    private javax.swing.JCheckBox jCheckBoxOCSPValIssued = null;
    
    private javax.swing.JLabel jLabelNextId = null;
    private javax.swing.JLabel jLabelDraft = null;
    private javax.swing.JLabel jLabelIssued = null;
    private javax.swing.JLabel jLabelReceived = null;
    private javax.swing.JLabel jLabelRecDraft = null;
    private javax.swing.JLabel jLabelRecIssued = null;
    private javax.swing.JLabel jLabelSeriesIssued = null;
    private javax.swing.JLabel jLabelCorrectedFac = null;
    
    private javax.swing.JLabel jLabelLogoPath = null;
    private javax.swing.JLabel jLabelLogoIcon = null;
    private javax.swing.JLabel jLabelSMTP = null;
    private javax.swing.JLabel jLabelPort = null;
    private javax.swing.JLabel jLabelMailUser = null;
    private javax.swing.JLabel jLabelMailPass = null;
    private javax.swing.JLabel jLabelMail = null;
    private javax.swing.JLabel jLabelOCSPServer = null;
    private javax.swing.JLabel jLabelBanner = null;
    private javax.swing.JLabel jLabelBannerMsg1 = null;
    private javax.swing.JLabel jLabelLanguage = null;
    
    private javax.swing.JLabel jLabelProxy = null;
    private javax.swing.JLabel jLabelProxyPort = null;
    private javax.swing.JLabel jLabelProxyUser = null;
    private javax.swing.JLabel jLabelProxyPass = null;
    private javax.swing.JLabel jLabelBannerMsg2 = null;
    
    private javax.swing.JLabel jLabelDefaultSubject = null;
    private javax.swing.JLabel jLabelStoreConfig = null;
    
    private javax.swing.JPasswordField jMailPasswordField = null;
    private javax.swing.JPasswordField jProxyPasswordField = null;
    private javax.swing.JSeparator jSeparator1 = null;
    private javax.swing.JSeparator jSeparator2 = null;

    private javax.swing.JTextField jTextFieldDraftId = null;
    private javax.swing.JTextField jTextFieldMailPort = null;
    private javax.swing.JTextField jTextFieldMailUser = null;
    private javax.swing.JTextField jTextFieldMail = null;
    private javax.swing.JTextField jTextFieldOCSPServer = null;
    private javax.swing.JTextField jTextFieldProxy = null;
    private javax.swing.JTextField jTextFieldProxyPort = null;
    private javax.swing.JTextField jTextFieldProxyUser = null;
    private javax.swing.JTextField jTextFieldIssuedId = null;
    private javax.swing.JTextField jTextFieldRecievedId = null;
    private javax.swing.JTextField jTextFieldRecDraftId = null;
    private javax.swing.JTextField jTextFieldRecIssuedId = null;
    private javax.swing.JTextField jTextFieldSeries = null;
    private javax.swing.JTextField jTextFieldCorrecSeries = null;
    private javax.swing.JTextField jTextFieldLogoPath = null;
    private javax.swing.JTextField jTextFieldSMTPServer = null;
    
    private javax.swing.JTextField jTextFieldDefaultSubject = null;
    
    private javax.swing.JLabel jLabelXSDPath = null;
    private javax.swing.JTextField jTextFieldXSDPath = null;
    
    private javax.swing.JLabel jLabelNamespace = null;
    private javax.swing.JTextField jTextFieldNamespace = null;
    
    private javax.swing.JButton jButtonFACeConfig = null;
    private javax.swing.JLabel jLabelFACeConfig = null;
    private javax.swing.JLabel jLabelFACeLink = null;
    
    private javax.swing.JTable jTableXSD = null;
    private javax.swing.JScrollPane jScrollPaneXSD = null;
    
    private Image imgLogoApp = null;
}