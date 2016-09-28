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
package es.mityc.appfacturae.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.storemanager.PluginAlmCertUCCD;
import es.mityc.facturae.ui.CertificatesWindow;
import es.mityc.facturae.utils.SignatureUtil;
import es.mityc.facturae.utils.SigningException;
import es.mityc.facturaeface.FACeConfig.FACeCertificateProvider;
import es.mityc.javasign.bridge.ConfigurationException;
import es.mityc.javasign.bridge.ISignFacade;
import es.mityc.javasign.bridge.SignFactory;
import es.mityc.javasign.pkstore.mitycstore.PKHandlers.PlainPassHandler;
import es.mityc.javasign.pkstore.mitycstore.mantainer.CertsFilter;

public class FACeConfigDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6472700177872274821L;
	private static final String CFG_ALMACEN_EXPLORER = "Explorer";

	private static Log logger = LogFactory.getLog(FACeConfigDialog.class);
	
	private X509Certificate faceCert = null;
	private PrivateKey facePK = null;
	private FACeCertificateProvider certificateProvider = FACeCertificateProvider.JAVA;

	public FACeConfigDialog(Frame owner, boolean modal) {
		super(owner, modal);
		setResizable(false);
		initComponents();
		init();
	}
	
	public JTextField getjTextFieldFACeEmail() {
		return jTextFieldFACeEmail;
	}

	public JTextField getjTextFieldFACeCert() {
		return jTextFieldFACeCert;
	}

	public X509Certificate getFaceCert() {
		return faceCert;
	}

	public PrivateKey getFacePK() {
		return facePK;
	}
	
	public FACeCertificateProvider getCertificateProvider() {
		return certificateProvider;
	}

	public void init(){
		String[] store = new PluginAlmCertUCCD().getStore();
		jRadioWindows.setEnabled(store[0].equalsIgnoreCase(CFG_ALMACEN_EXPLORER));
		if(jRadioWindows.isEnabled() && certificateProvider.equals(FACeCertificateProvider.WINDOWS)) {
			jRadioWindows.setSelected(true);
			jRadioDNIe.setSelected(false);
			jRadioJavaStore.setSelected(false);
			jButtonSelectCert.setVisible(true);
			jLabelSelectCert.setVisible(true);
		} else if(certificateProvider.equals(FACeCertificateProvider.DNIE)) {
			jRadioWindows.setSelected(false);
			jRadioDNIe.setSelected(true);
			jRadioJavaStore.setSelected(false);
			jButtonSelectCert.setVisible(false);
			jLabelSelectCert.setVisible(false);
		} else {			
			jRadioWindows.setSelected(false);
			jRadioDNIe.setSelected(false);
			jRadioJavaStore.setSelected(true);
			jButtonSelectCert.setVisible(true);
			jLabelSelectCert.setVisible(true);
		}

	}

	private void initComponents() {
		panelPrin = new JPanel();
        panelButton = new JPanel();
        jButtonCancel = new JButton();
        jButtonAccept = new JButton();
        jButtonSelectCert = new JButton();

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_cancel.jpg"))); 
        jButtonCancel.setToolTipText(Constants.LANG.getString("Cancel")); 
        jButtonCancel.setBorderPainted(false);
        jButtonCancel.setContentAreaFilled(false);
        jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	FACeConfigDialog.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	FACeConfigDialog.this.mouseExited(evt);
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
            	FACeConfigDialog.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	FACeConfigDialog.this.mouseExited(evt);
            }
        });
        jButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonAcceptActionPerformed();
			}
		});

        panelPrin.setBackground(Constants.BKG_MAIN_COLOR);
		panelPrin.setLayout(new GridBagLayout());

		jLabelFACeEmail = new JLabel();
	    
        jLabelFACeEmail.setFont(Constants.FONT_PLAIN);
        jLabelFACeEmail.setForeground(Constants.FONT_COLOR);
        jLabelFACeEmail.setText(Constants.LANG.getString("FACeEmail")); 
	            
	    GridBagConstraints jLabelFACeEmailConstraints = new GridBagConstraints();
	    jLabelFACeEmailConstraints.gridx = 0;
	    jLabelFACeEmailConstraints.gridy = 0;
	    jLabelFACeEmailConstraints.anchor = GridBagConstraints.EAST;
	    jLabelFACeEmailConstraints.insets = new Insets(0, 10, 0, 5);
	    panelPrin.add(jLabelFACeEmail, jLabelFACeEmailConstraints);		

        jTextFieldFACeEmail = new JTextField();
        
        jTextFieldFACeEmail.setFont(Constants.FONT_PLAIN);
        jTextFieldFACeEmail.setHorizontalAlignment(SwingConstants.LEFT);
        jTextFieldFACeEmail.setText(Constants.CONFIG_PROP.getProperty("FACeEmail"));
        GridBagConstraints jTextFieldFACeEmailConstraints = new GridBagConstraints();
        jTextFieldFACeEmailConstraints.gridwidth = 3;
        jTextFieldFACeEmailConstraints.weightx = 0.1;
        jTextFieldFACeEmailConstraints.fill = GridBagConstraints.HORIZONTAL;
        jTextFieldFACeEmailConstraints.gridx = 1;
        jTextFieldFACeEmailConstraints.gridy = 0;
        //jTextFieldFACeEmailConstraints.anchor = GridBagConstraints.EAST;
        jTextFieldFACeEmailConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jTextFieldFACeEmail, jTextFieldFACeEmailConstraints);		

        jLabelFACeCert = new JLabel();
	    
        jLabelFACeCert.setFont(Constants.FONT_PLAIN);
        jLabelFACeCert.setForeground(Constants.FONT_COLOR);
        jLabelFACeCert.setText(Constants.LANG.getString("FACeCert")); 
	            
	    GridBagConstraints jLabelFACeCertConstraints = new GridBagConstraints();
	    jLabelFACeCertConstraints.gridx = 0;
	    jLabelFACeCertConstraints.gridy = 1;
	    jLabelFACeCertConstraints.anchor = GridBagConstraints.EAST;
	    jLabelFACeCertConstraints.insets = new Insets(0, 10, 0, 5);
	    panelPrin.add(jLabelFACeCert, jLabelFACeCertConstraints);		

        jTextFieldFACeCert = new JTextField();
        
        jTextFieldFACeCert.setFont(Constants.FONT_PLAIN);
        jTextFieldFACeCert.setHorizontalAlignment(SwingConstants.LEFT);
        jTextFieldFACeCert.setText(Constants.CONFIG_PROP.getProperty("FACeCert"));
        jTextFieldFACeCert.setEditable(false);
        GridBagConstraints jTextFieldFACeCertConstraints = new GridBagConstraints();
        jTextFieldFACeCertConstraints.gridwidth = 3;
        jTextFieldFACeCertConstraints.weightx = 0.1;
        jTextFieldFACeCertConstraints.fill = GridBagConstraints.HORIZONTAL;
        jTextFieldFACeCertConstraints.gridx = 1;
        jTextFieldFACeCertConstraints.gridy = 1;
        jTextFieldFACeCertConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jTextFieldFACeCert, jTextFieldFACeCertConstraints);		

        jRadioWindows = new JRadioButton();
        jRadioJavaStore = new JRadioButton();
        jRadioDNIe = new JRadioButton();
        jRadioWindows.setBackground(Constants.BKG_MAIN_COLOR);
		jRadioWindows.setToolTipText(Constants.LANG.getString("Store.IexplorerList"));
		jRadioWindows.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
		        ButtonModel aModel = aButton.getModel();
		        boolean selected = aModel.isSelected();
				if(selected && !certificateProvider.equals(FACeCertificateProvider.WINDOWS)) {
					certificateProvider = FACeCertificateProvider.WINDOWS;
					jTextFieldFACeCert.setText("");
					faceCert=null;
					facePK=null;
					jButtonSelectCert.setVisible(true);
					jLabelSelectCert.setVisible(true);
				}
			}
		});		
        jRadioJavaStore.setBackground(Constants.BKG_MAIN_COLOR);
		jRadioJavaStore.setToolTipText(Constants.LANG.getString("FACeDiskCert"));
		jRadioJavaStore.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
		        ButtonModel aModel = aButton.getModel();
		        boolean selected = aModel.isSelected();
				if(selected && !certificateProvider.equals(FACeCertificateProvider.JAVA)) {
					certificateProvider = FACeCertificateProvider.JAVA;
					jTextFieldFACeCert.setText("");
					faceCert=null;
					facePK=null;
					jButtonSelectCert.setVisible(true);
					jLabelSelectCert.setVisible(true);
				}
			}
		});		
		jRadioDNIe.setBackground(Constants.BKG_MAIN_COLOR);
		jRadioDNIe.setToolTipText(Constants.LANG.getString("Store.DNIeList"));
		jRadioDNIe.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				AbstractButton aButton = (AbstractButton)e.getSource();
		        ButtonModel aModel = aButton.getModel();
		        boolean selected = aModel.isSelected();
				if(selected && !certificateProvider.equals(FACeCertificateProvider.DNIE)) {
					certificateProvider = FACeCertificateProvider.DNIE;
					jTextFieldFACeCert.setText("DNIe");
					faceCert=null;
					facePK=null;
					jButtonSelectCert.setVisible(false);
					jLabelSelectCert.setVisible(false);
				}
			}
		});		

		certificateProvider = FACeCertificateProvider.valueOf(Constants.CONFIG_PROP.getProperty("FACeProvider")!=null&&Constants.CONFIG_PROP.getProperty("FACeProvider").length()>0?Constants.CONFIG_PROP.getProperty("FACeProvider"):"JAVA");

        radioGrpExpJa = new ButtonGroup();
        radioGrpExpJa.add(jRadioWindows);
        radioGrpExpJa.add(jRadioJavaStore);
        radioGrpExpJa.add(jRadioDNIe);

        jLabelIExplorer = new JLabel(Constants.LANG.getString("Store.Iexplorer"),new ImageIcon(this.getClass().getResource("/images/icon_iexplorer.jpg")),JLabel.LEFT);
        jLabelJava = new JLabel(Constants.LANG.getString("FACeDiskCert"),new ImageIcon(this.getClass().getResource("/images/icon_java.jpg")),JLabel.LEFT);
        jLabelDNIe = new JLabel(Constants.LANG.getString("Store.DNIe"),new ImageIcon(this.getClass().getResource("/images/icon_dnie.jpg")),JLabel.LEFT);

        GridBagConstraints jRadioWindowsConstraints = new GridBagConstraints();
        jRadioWindowsConstraints.anchor = GridBagConstraints.EAST;
        jRadioWindowsConstraints.gridx = 0;
        jRadioWindowsConstraints.gridy = 2;
        jRadioWindowsConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jRadioWindows, jRadioWindowsConstraints);		
        GridBagConstraints jLabelIExplorerConstraints = new GridBagConstraints();
        jLabelIExplorerConstraints.weightx = 0.1;
        jLabelIExplorerConstraints.anchor = GridBagConstraints.WEST;
        jLabelIExplorerConstraints.fill = GridBagConstraints.HORIZONTAL;
        jLabelIExplorerConstraints.gridwidth = 3;
        jLabelIExplorerConstraints.gridx = 1;
        jLabelIExplorerConstraints.gridy = 2;
        jLabelIExplorerConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jLabelIExplorer, jLabelIExplorerConstraints);		

        GridBagConstraints jRadioDNIeConstraints = new GridBagConstraints();
        jRadioDNIeConstraints.anchor = GridBagConstraints.EAST;
        jRadioDNIeConstraints.gridx = 0;
        jRadioDNIeConstraints.gridy = 3;
        jRadioDNIeConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jRadioDNIe, jRadioDNIeConstraints);		
        GridBagConstraints jLabelDNIeConstraints = new GridBagConstraints();
        jLabelDNIeConstraints.weightx = 0.1;
        jLabelDNIeConstraints.anchor = GridBagConstraints.WEST;
        jLabelDNIeConstraints.fill = GridBagConstraints.HORIZONTAL;
        jLabelDNIeConstraints.gridwidth = 3;
        jLabelDNIeConstraints.gridx = 1;
        jLabelDNIeConstraints.gridy = 3;
        jLabelDNIeConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jLabelDNIe, jLabelDNIeConstraints);		
        
        GridBagConstraints jRadioJavaStoreConstraints = new GridBagConstraints();
        jRadioJavaStoreConstraints.anchor = GridBagConstraints.EAST;
        jRadioJavaStoreConstraints.gridx = 0;
        jRadioJavaStoreConstraints.gridy = 4;
        jRadioJavaStoreConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jRadioJavaStore, jRadioJavaStoreConstraints);		
        GridBagConstraints jLabelJavaConstraints = new GridBagConstraints();
        jLabelJavaConstraints.weightx = 0.1;
        jLabelJavaConstraints.anchor = GridBagConstraints.WEST;
        jLabelJavaConstraints.fill = GridBagConstraints.HORIZONTAL;
        jLabelJavaConstraints.gridwidth = 3;
        jLabelJavaConstraints.gridx = 1;
        jLabelJavaConstraints.gridy = 4;
        jLabelJavaConstraints.insets = new Insets(0, 5, 0, 10);
        panelPrin.add(jLabelJava, jLabelJavaConstraints);		

        jButtonSelectCert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonSelectCert.setToolTipText(Constants.LANG.getString("Store.SelectCertificate")); 
        jButtonSelectCert.setBorderPainted(false);
        jButtonSelectCert.setContentAreaFilled(false);
        jButtonSelectCert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	FACeConfigDialog.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	FACeConfigDialog.this.mouseExited(evt);
            }
        });
        jButtonSelectCert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonFACeCertActionPerformed();
			}
		});
        GridBagConstraints jButtonSelectCertConstraints = new GridBagConstraints();
        jButtonSelectCertConstraints.ipadx = -20;
        jButtonSelectCertConstraints.weightx = 0.1;
        jButtonSelectCertConstraints.gridwidth = 2;
        jButtonSelectCertConstraints.anchor = GridBagConstraints.EAST;
        jButtonSelectCertConstraints.gridx = 0;
        jButtonSelectCertConstraints.gridy = 5;
        jButtonSelectCertConstraints.insets = new Insets(0, 5, 0, 0);
        panelPrin.add(jButtonSelectCert, jButtonSelectCertConstraints);		

        jLabelSelectCert = new JLabel();
	    
        jLabelSelectCert.setFont(Constants.FONT_PLAIN);
        jLabelSelectCert.setForeground(Constants.FONT_COLOR);
        jLabelSelectCert.setText(Constants.LANG.getString("Store.SelectCertificate")); 
        GridBagConstraints jLabelSelectCertConstraints = new GridBagConstraints();
        jLabelSelectCertConstraints.gridwidth = 2;
        jLabelSelectCertConstraints.weightx = 0.2;
        jLabelSelectCertConstraints.anchor = GridBagConstraints.WEST;
        jLabelSelectCertConstraints.gridx = 2;
        jLabelSelectCertConstraints.gridy = 5;
        jLabelSelectCertConstraints.insets = new Insets(0, 0, 0, 10);
        panelPrin.add(jLabelSelectCert, jLabelSelectCertConstraints);		

        panelButton.setBackground(Constants.BKG_MAIN_COLOR);
		panelButton.setLayout(new GridBagLayout());

	    GridBagConstraints jButtonAcceptConstraints = new GridBagConstraints();
	    jButtonAcceptConstraints.gridx = 0;
	    jButtonAcceptConstraints.gridy = 0;
	    jButtonAcceptConstraints.anchor = GridBagConstraints.EAST;
	    jButtonAcceptConstraints.insets = new Insets(1, 3, 3, 10);
	    panelButton.add(jButtonAccept, jButtonAcceptConstraints);		
	    GridBagConstraints jButtonCancelConstraints = new GridBagConstraints();
	    jButtonCancelConstraints.gridx = 1;
	    jButtonCancelConstraints.gridy = 0;
	    jButtonCancelConstraints.anchor = GridBagConstraints.WEST;
	    jButtonCancelConstraints.insets = new Insets(1, 3, 3, 10);
	    panelButton.add(jButtonCancel, jButtonCancelConstraints);		

		getContentPane().add(panelPrin, BorderLayout.CENTER);
		getContentPane().add(panelButton, BorderLayout.SOUTH);
		
        this.setSize(400, 300);
        this.setTitle(Constants.LANG.getString("FACeConfig"));
        this.setLocationRelativeTo(null);
        this.setResizable(false);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
	}
	
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

	private void jButtonCancelActionPerformed() {
        jTextFieldFACeEmail.setText(Constants.CONFIG_PROP.getProperty("FACeEmail"));
        jTextFieldFACeCert.setText(Constants.CONFIG_PROP.getProperty("FACeCert"));
        certificateProvider = FACeCertificateProvider.valueOf(Constants.CONFIG_PROP.getProperty("FACeProvider")!=null&&Constants.CONFIG_PROP.getProperty("FACeProvider").length()>0?Constants.CONFIG_PROP.getProperty("FACeProvider"):"JAVA");
		this.setVisible(false);
		this.dispose();
	}
	
	private void jButtonAcceptActionPerformed() {
		this.setVisible(false);
		this.dispose();
	}

	private void jButtonFACeCertActionPerformed() {
    	Thread th = new Thread(new Runnable() {
    		public void run() {
    			switch(certificateProvider){
    				case WINDOWS:
	    				try{
	        				ISignFacade signFacade = SignFactory.getInstance().getSignFacade();
	        				String[] store = new PluginAlmCertUCCD().getStore();
	        				signFacade.setStoreManager(store[0], store[1]);
	        				Properties configprops = new Properties(), props = new Properties();
	
	        				logger.info("Loading configuration properties");
	    					configprops.load(SignatureUtil.class.getResourceAsStream(es.mityc.facturae.utils.constants.Constants.CONFIG_PATH));
	    					File f = new File(configprops.getProperty("sign_file"));
	    		    		if (f.exists()){
	    		    			logger.info("A configuration file created by user exists, so it is loaded");
	    		    			props.load(new FileInputStream(f));
	    		    		}
	    		    		else{
	    		    			logger.info("There is not a custom configuration file created by user, so the default resource included in the API jar is loaded");
	    		    			props.load(SignatureUtil.class.getResourceAsStream(configprops.getProperty("sign_resource")));
	    		    		}
	    		    		
	    		    		logger.info("Initializing facade and setting the locale language and country");
	    		    		signFacade.init(props);
	    					Locale l = new Locale(props.getProperty("locale.language"),props.getProperty("locale.country"));
	    					Locale.setDefault(l);
	    					
	    					logger.info("Getting the certificates from the store");
	    					List<X509Certificate> certs = signFacade.getSignCertificates();
	    					if (certs == null){
	    						throw new SigningException("Error loading certificates");
	    					}
	    					
	    					logger.info("Establishing the look & feel");
	    					// Look & Feel selection
	    					try{
	    						if (props.getProperty("lookAndFeel").equals("facturae") || props.getProperty("lookAndFeel").equals("so"))
	    							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    						else if (props.getProperty("lookAndFeel").equals("metal")){
	    							if (props.getProperty("lookAndFeelTheme").equals("0")) 
	    								MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
	    							else if (props.getProperty("lookAndFeelTheme").equals("1"))
	    								MetalLookAndFeel.setCurrentTheme(new OceanTheme());
	    							UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    						}
	    						else if (props.getProperty("lookAndFeel").equals("windows"))
	    							UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    						else if (props.getProperty("lookAndFeel").equals("motif"))
	    							UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
	    					}
	    			        catch(Exception e){
	    			        	logger.error("An error has occurred when the look and feel was being set :"+e.getMessage());
	    						if (logger.isDebugEnabled()) {
	    							logger.error("", e);
	    						}
	    						throw new SigningException("Error establishing the look and feel for the certificates window.", e);
	    			        }
	
							CertificatesWindow cw = new CertificatesWindow(certs ,props.getProperty("lookAndFeel"),signFacade);
		    				int returnValue = cw.showOpenDialog();
		    				if (returnValue == 0){
		    					logger.info("Getting the selected certificate...");
		    					X509Certificate cert = cw.getCert();
		    				
		    					jTextFieldFACeCert.setText(cert.getSubjectDN().getName());
			    				faceCert = null;
			    				facePK = null;
		    				}
	    				}
	    				catch(IOException ioe){
	    					logger.error("An error has ocurred during the properties loading :"+ioe.getMessage());
	    					if (logger.isDebugEnabled()) {
	    						logger.error("", ioe);
	    					}
	    					//throw new SigningException("Error loading the properties file \""+es.mityc.facturae.utils.constants.Constants.CONFIG_PATH+"\"", ioe);
	    				}
	    				catch(ConfigurationException ce){
	    					logger.error("An error has occurred trying to configure the facade :"+ce.getMessage());
	    					if (logger.isDebugEnabled()) {
	    						logger.error("", ce);
	    					}
	    					//throw new SigningException("Error configuring the facade to sign.", ce);
	    				}
	    				catch(Exception e){
	    					//throw new SigningException("Generic error during signing process: " + e.getMessage(), e);
	    				}
	    				break;
    				case JAVA:
		    			JFileChooser chooser = new JFileChooser();
		    			// Añadir certificado
		    			chooser.setDialogTitle(Constants.LANG.getString("Store.AddCert"));
		    	        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		    	        chooser.setFileFilter(new CertsFilter(true));
		    		    int returnVal = chooser.showOpenDialog(null);
		    		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		    		    	addCertFromPath(chooser.getSelectedFile().getAbsolutePath());
		    		    } else {
		    		    	return;
		    		    }
    					break;
    				default:
		    		    break;
    			}
    		}
    	});

    	th.run();
	}

	/**
	 * <p>Recupera un certificado de una ruta. Dicho fichero ha de ser un .p12.</p>
	 * @param path Ruta al fichero que contiene el ceritficado
	 */
	private void addCertFromPath(final String path) {
		if (!new File(path).exists()) {
			// El fichero indicado no existe o no se encuentra
			logger.error(Constants.LANG.getString("Store.FileNotFound"));
			return;
		}
		// Variables de proceso de uso común
		FileInputStream fis = null;
		// Resultados esperados
		X509Certificate cert = null;
		PrivateKey pk12 = null;
		char[] passwordP12 = null;

		if (path.endsWith("p12")) {
			// Se carga el P12
			try {				
				KeyStore ks12 = KeyStore.getInstance("PKCS12");
				passwordP12 = null;
				try {
					fis = new FileInputStream(path);
					PlainPassHandler passHandler = new PlainPassHandler();
					passwordP12 = passHandler.getPassword(null, path.substring(path.lastIndexOf(File.separator) + 1));
					try {
						ks12.load(fis, passwordP12);
					} catch (IOException e) {
						// La contraseña no es válida. No se pudo acceder al contenedor P12
						logger.error(Constants.LANG.getString("Store.InvalidPwd"));
						Constants.DIALOG.showWarnInvalidPass(this);
						return;
					}

					// Se accede al contenido
					Enumeration<String> contenidoP12 = ks12.aliases();
					String alias = null;
					if (ks12.size() == 1) {
						alias = contenidoP12.nextElement();
						logger.debug("P12.- Alias del certificado: " + alias);
					} else {
						// El contenedor P12 está vacío
						Constants.DIALOG.showWarnEmptyContainer(this);
						return;
					}

					// Carga el certificado
					try {
						cert = (X509Certificate) ks12.getCertificate(alias);
					} catch (KeyStoreException e1) {
						e1.printStackTrace();
						return;
					} 

					// Carga la private Key
					if (ks12.isKeyEntry(alias)) {
						// obtener la clave privada
						passwordP12 = passHandler.getPassword(null, Constants.LANG.getString("Store.PrivateKeyObtain"));
						KeyStore.PasswordProtection kpp12 = new KeyStore.PasswordProtection(passwordP12);
						KeyStore.PrivateKeyEntry pkEntry12 = null;
						try {
							pkEntry12 = (KeyStore.PrivateKeyEntry) ks12.getEntry(alias, kpp12);
						} catch (NoSuchAlgorithmException e1) {
							e1.printStackTrace();
						} catch (UnrecoverableEntryException e) {
							// La contraseña no es válida. No se pudo acceder a la clave privada
							Constants.DIALOG.showWarnInvalidPass2(this);
							return;
						}
						if (pkEntry12 != null) {
							pk12 = pkEntry12.getPrivateKey();
						} else {
							logger.debug("P12.- No se encontró la clave privada");
						}
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					return;
				} catch (CertificateException e) {
					e.printStackTrace();
					return;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return;
				} finally {
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) { /* No se hace nada */ }
					}
				}
			} catch (KeyStoreException e1) {
				e1.printStackTrace();
				return;
			}
		}
		
		faceCert = cert;
		facePK = pk12;
		
		jTextFieldFACeCert.setText(cert.getSubjectDN().getName());
	}
	
	private JPanel panelPrin;
	private JPanel panelButton;
    private JLabel jLabelFACeEmail = null;
    private JTextField jTextFieldFACeEmail = null;
    private JLabel jLabelFACeCert = null;
    private JTextField jTextFieldFACeCert = null;
    private ButtonGroup radioGrpExpJa = null;
    private JRadioButton jRadioWindows = null;
    private JRadioButton jRadioJavaStore = null;
    private JRadioButton jRadioDNIe = null;
    private JLabel jLabelIExplorer = null;
    private JLabel jLabelJava = null;
    private JLabel jLabelDNIe = null;
    private JButton jButtonSelectCert = null;
    private JLabel jLabelSelectCert = null;
    private JButton jButtonCancel = null;
    private JButton jButtonAccept = null;

}
