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
package es.mityc.appfacturae.utils.storemanager;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.ui.components.CustomFileFilter;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.OSUtil;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.ConstantsCert;
import es.mityc.javasign.pkstore.IPKStoreMaintainer;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.mitycstore.MITyCStore;
import es.mityc.javasign.pkstore.mozilla.MozillaTokenLoginModeEnum;
import es.mityc.javasign.utils.OSTool;
import es.mityc.javasign.utils.WinRegistryUtils;


public class PluginAlmCertUCCD {
	
	static Log logger = LogFactory.getLog(PluginAlmCertUCCD.class);
	
	// Título de opción de menú y de ventana de diálogo
	private static final String TITULO_ALMACEN = Constants.LANG.getString("Store.Store");
	
	// Atajo de teclado
	private static final int ATAJO_DE_TECLADO = java.awt.event.KeyEvent.VK_O;
	
	private static final String ACEPTAR = Constants.LANG.getString("Store.Accept");
	private static final String CANCELAR = Constants.LANG.getString("Store.Cancel");

	private static final String fichAyudaPerfil = "mozilla.html";
	
	private static final String CFG_ALMACEN = "almacenCerts";
	private static final String CFG_ALMACEN_EXPLORER = "IEXPLORER";
	private static final String CFG_ALMACEN_MOZILLA = "MOZILLA";
	private static final String CFG_ALMACEN_MAC = "MACOS";
	private static final String CFG_ALMACEN_JAVA = "JAVASTORE";
	private static final String CFG_RUTA_CONF_JAVA = "confJavaStore";
	private static final String CFG_RUTA_PROFILE_MOZILLA = "rutaProfileMozilla";
	private static final String CFG_MOZILLA_TOKEN_TIPO_LOGIN = "MozillaTokenLoginType";
	private static final String CFG_MOZILLA_TOKEN_LOGINTIMEOUT = "MozillaTokenLoginInterval";
	
	private static final Color ROJO = new Color(250, 220, 230); 
	private static final Color VERDE = new Color(200, 240, 200);
	private static final String PATH_ICO_CARPETA = "/images/button_middle_folder.jpg";
	
	private String almacenSeleccionado = CFG_ALMACEN_EXPLORER;
	private String javaStoreConf = null;
	private String rutaPerfil = "";
	private MozillaTokenLoginModeEnum tipoTokenLogin = MozillaTokenLoginModeEnum.ONE_TIME;
	private int tiempoLog = 0;
	
	private JFrame owner = null;
	
	public PluginAlmCertUCCD() {
		if (jConfigDialog == null)
			init();
	}
	
	public void setOwner(JFrame owner) {
		this.owner = owner;
	}

	protected void init() {
    	jConfigDialog = new JDialog(owner);
    	
    	jButtonAceptarConfig = new JButton();
    	jButtonCancelarConfig = new JButton();
		
		// Panel de almacén de certificados
		jAlmacenPanel = new JPanel();
		mainPanel = new JPanel();
		
        jRadioWindows = new JRadioButton();
        jRadioMozilla = new JRadioButton();
        jRadioMac = new JRadioButton();
        jRadioJavaStore = new JRadioButton();
        
        
        jLabelIExplorer = new JLabel(Constants.LANG.getString("Store.Iexplorer"),new ImageIcon(this.getClass().getResource("/images/icon_iexplorer.jpg")),JLabel.LEFT);
        jLabelFirefox = new JLabel(Constants.LANG.getString("Store.Mozilla"),new ImageIcon(this.getClass().getResource("/images/icon_firefox.jpg")),JLabel.LEFT);
        jLabelMacos = new JLabel(Constants.LANG.getString("Store.Mac"),new ImageIcon(this.getClass().getResource("/images/icon_macos.jpg")),JLabel.LEFT);
        jLabelJava = new JLabel(Constants.LANG.getString("Store.Java"),new ImageIcon(this.getClass().getResource("/images/icon_java.jpg")),JLabel.LEFT);
        
        jRadioWindows.setBackground(Constants.BKG_MAIN_COLOR);
        jRadioMozilla.setBackground(Constants.BKG_MAIN_COLOR);
        jRadioMac.setBackground(Constants.BKG_MAIN_COLOR);
        jRadioJavaStore.setBackground(Constants.BKG_MAIN_COLOR);
        radioGrpExpMozJa = new ButtonGroup();
        radioGrpExpMozJa.add(jRadioWindows);
        radioGrpExpMozJa.add(jRadioMozilla);
        radioGrpExpMozJa.add(jRadioMac);
        radioGrpExpMozJa.add(jRadioJavaStore);
        jLabelPerfil = new JLabel(Constants.LANG.getString("Store.Path")+":");
        jButtonAyudaPerfil = new JButton();
        jPerfilTextField = new JTextField();
        
        //Panel de IExplorer Store
        jExplorerStorePanel = new JPanel();
        jExplorerStoreLbl = new JLabel(Constants.LANG.getString("Store.JavaMsg1"));
        
        // Panel de Token de mozilla
    	jMozillaPanel = new JPanel();
		jMozillaLabel = new JLabel();
		
		// Panel del KeyStore de Mac
		jMacPanel = new JPanel();
		jMacLabel = new JLabel();

		// Panel de Java Store
		jJavaStorePanel = new JPanel();
		jLabelJavaStore = new JLabel(Constants.LANG.getString("Store.JavaMsg2"));
		jJavaConfLbl = new JLabel(Constants.LANG.getString("Store.JavaConfigPath")+":");
		jJavaConfTextFld = new JTextField();
		jJavaConfTextFld.setEditable(false);
		jJavaConfTextFld.setBackground(ROJO);
		jJavaConfTextFld.setText("");
		jPathConfBtn = new JButton();
		jJavaStoreManagerButton = new JButton();
		
		jJavaStoreManagerButton.setToolTipText(Constants.LANG.getString("Store.JavaAdmin"));
		jJavaStoreManagerButton.setIcon(new ImageIcon(this.getClass().getResource("/images/button_admcert.jpg")));
		jJavaStoreManagerButton.setDisabledIcon(new ImageIcon(this.getClass().getResource("/images/button_admcert_disabled.jpg")));
		jJavaStoreManagerButton.setBorderPainted(false);
		jJavaStoreManagerButton.setContentAreaFilled(false);
		jJavaStoreManagerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonAceptarConfigActionPerformed();
			}
		});		
		jJavaStoreManagerButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jJavaStoreManagerButton.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jJavaStoreManagerButton.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
		
		ImageIcon icon = null;
		URL urlIcon = getClass().getResource(PATH_ICO_CARPETA);
		if (urlIcon != null)
			icon = new ImageIcon(urlIcon);

		if (icon != null) {
			jPathConfBtn.setIcon(icon);
			jPathConfBtn.setIconTextGap(3);
			jPathConfBtn.setHorizontalTextPosition(SwingConstants.LEFT);
			jPathConfBtn.setMargin(new Insets(0,0,0,0));
			jPathConfBtn.setBorderPainted(false);
			jPathConfBtn.setContentAreaFilled(false);
		}
		jPathConfBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jPathConfBtn.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jPathConfBtn.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
		jPathConfBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				File conf = null;
				if (javaStoreConf != null && !"".equals(javaStoreConf) && !"null".equals(javaStoreConf)) {
					conf = jButtonPathActionPerformed(javaStoreConf);
				} else {
					conf = jButtonPathActionPerformed(null);
				}
				if (conf != null && !"".equals(conf)) { 
					jJavaConfTextFld.setText(conf.getPath());
					jJavaConfTextFld.setToolTipText(conf.getPath());
					jJavaConfTextFld.setBackground(obtenerColor(conf));
					javaStoreConf = conf.getAbsolutePath();
				}
			}
		});
        
		// Se estructura el cuadro de diálogo
		
        // Cuadro de diálogo
        jButtonAceptarConfig.setToolTipText(ACEPTAR);
		jButtonAceptarConfig.setIcon(new ImageIcon(this.getClass().getResource("/images/button_accept.jpg")));
		jButtonAceptarConfig.setBorderPainted(false);
		jButtonAceptarConfig.setContentAreaFilled(false);
		jButtonAceptarConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonAceptarConfigActionPerformed();
			}
		});		
		jButtonAceptarConfig.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonAceptarConfig.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jButtonAceptarConfig.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	

		jButtonCancelarConfig.setToolTipText(CANCELAR);
		jButtonCancelarConfig.setIcon(new ImageIcon(this.getClass().getResource("/images/button_cancel.jpg")));
		jButtonCancelarConfig.setBorderPainted(false);
		jButtonCancelarConfig.setContentAreaFilled(false);
		jButtonCancelarConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelarConfigActionPerformed();
			}
		});
		jButtonCancelarConfig.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonCancelarConfig.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jButtonCancelarConfig.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
		
		// Se estructura el panel del almacén de certificados de IExplorer
		jExplorerStorePanel.setBackground(Constants.BKG_MAIN_COLOR);
		jExplorerStorePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Store.JavaProperties"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
		jExplorerStorePanel.setForeground(Constants.BKG_MAIN_COLOR);
		jExplorerStorePanel.setFont(Constants.FONT_PLAIN);
		
		// Se estructura el panel con los JRadioButtons de almacenes
		jButtonAyudaPerfil.setIcon(new ImageIcon(this.getClass().getResource("/images/button_firefox.jpg")));
        jButtonAyudaPerfil.setContentAreaFilled(false);
        jButtonAyudaPerfil.setBorderPainted(false);
        jButtonAyudaPerfil.setToolTipText(Constants.LANG.getString("Store.PathHelp"));
        jButtonAyudaPerfil.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonAyudaPerfil.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jButtonAyudaPerfil.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
        
		jButtonAyudaPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (jButtonAyudaPerfil.isEnabled()) {
					try {
			    		// Se busca el original empleado por el usuario
			    		String rutaFirefox = null;
			    		try {
			    			rutaFirefox = WinRegistryUtils.readString(WinRegistryUtils.HKEY_LOCAL_MACHINE, "Software\\Classes\\FirefoxURL\\shell\\open\\command", "");
			    		} catch (Exception e) {
			    			logger.debug("No se pudo encontrar la ruta de Firefox en LocalMachine", e);
			    			try {
			    				rutaFirefox = WinRegistryUtils.readString(WinRegistryUtils.HKEY_CURRENT_USER, "Software\\Classes\\FirefoxURL\\shell\\open\\command", "");
			    			} catch (Exception ex) {
			    				logger.debug("No se pudo encontrar la ruta de Firefox en CurrentUser", ex);
			    			}
			    		}			    		
			    		if (rutaFirefox != null) {
			    			int i = rutaFirefox.indexOf("firefox.exe");
							if (i != -1) {
								if ((rutaFirefox = rutaFirefox.substring(0, i + 11)).startsWith("\""))
									rutaFirefox = rutaFirefox.substring(1);
								if (new File(rutaFirefox).exists()) {
									try {
										Runtime.getRuntime().exec(new String[] { rutaFirefox, new File("mozilla.html").getCanonicalPath()});
										return;
									} catch(Exception e) {
										logger.debug("No se pudo ejecutar directamente Firefox para mostrar la ayuda.", e);
									}
								}
							}
			    		}
			    		OSUtil.open(new File("mozilla.html").getCanonicalPath());
					} catch (Throwable e) {
						logger.error(Constants.LANG.getString("Store.MozillaMsg1") + " " + e.getMessage(), e);
						Constants.DIALOG.showErrorOpeningFile(fichAyudaPerfil,e.getMessage());
					}
				}
			}
		});
		jButtonAyudaPerfil.addKeyListener(new KeyListener()  {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					jButtonAyudaPerfil.doClick();		
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}				
		});
		
		jRadioWindows.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jRadioExplorerActionPerformed();
			}        	
		});
		
		jRadioMozilla.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jRadioMozillaActionPerformed();
			}        	
		});
		jRadioMac.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				jRadioMacActionPerformed();
			}        	
		});
		
		jRadioJavaStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jRadioJavaStoreActionPerformed();
			}
		});
		
		jButtonAyudaPerfil.setToolTipText(Constants.LANG.getString("Store.MozillaHelp"));
		
		jRadioWindows.setToolTipText(Constants.LANG.getString("Store.IexplorerList"));
		jRadioMozilla.setToolTipText(Constants.LANG.getString("Store.MozillaList"));
		jRadioMac.setToolTipText(Constants.LANG.getString("Store.MacList"));
		jRadioJavaStore.setToolTipText(Constants.LANG.getString("Store.JavaList"));
		
		jRadioWindows.setEnabled(false);  
    	jRadioMac.setEnabled(false);  
    	jRadioMozilla.setEnabled(false);

		jAlmacenPanel.setBorder(BorderFactory.createTitledBorder(""));
		jAlmacenPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true))); 
				
		// Se estructura el cuadro de Token login de Mozilla
		jMozillaPanel.setBackground(Constants.BKG_MAIN_COLOR);
		jMozillaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Store.JavaProperties"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
		jMozillaPanel.setForeground(Constants.BKG_MAIN_COLOR);
		jMozillaPanel.setFont(Constants.FONT_PLAIN);

		jMozillaLabel.setText(Constants.LANG.getString("Store.MozillaMsg5"));
		
		
		// Se estructura el panel de Mac
		jMacPanel.setBackground(Constants.BKG_MAIN_COLOR);
		jMacPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Store.Mac"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
		jMacPanel.setForeground(Constants.BKG_MAIN_COLOR);
		jMacPanel.setFont(Constants.FONT_PLAIN);
		
		jMacLabel.setText(Constants.LANG.getString("Store.MacMsg1"));
		jMacLabel.setToolTipText(Constants.LANG.getString("Store.MacList"));
		
		// Se estructura el panel de JavaStore
		jJavaStorePanel.setBackground(Constants.BKG_MAIN_COLOR);
		jJavaStorePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Store.JavaProperties"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
		jJavaStorePanel.setForeground(Constants.BKG_MAIN_COLOR);
		jJavaStorePanel.setFont(Constants.FONT_PLAIN);
		
		jJavaStoreManagerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonJavaStoreManagerActionPerformed();
			}
		});
		
		jJavaStoreManagerButton.addKeyListener(new KeyListener()  {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					jJavaStoreManagerButton.doClick();		
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}				
		});
		
		org.jdesktop.layout.GroupLayout jExplorerLayout = new org.jdesktop.layout.GroupLayout(jExplorerStorePanel);
		jExplorerStorePanel.setLayout(jExplorerLayout);
		jExplorerLayout.setHorizontalGroup(
				jExplorerLayout.createSequentialGroup()
					.addContainerGap()
					.add(jExplorerStoreLbl)
					.addContainerGap()
		);
		jExplorerLayout.setVerticalGroup(
				jExplorerLayout.createSequentialGroup()
						.addContainerGap()
						.add(jExplorerStoreLbl)
						.addContainerGap()		
		);
		
		org.jdesktop.layout.GroupLayout jMacLayout = new org.jdesktop.layout.GroupLayout(jMacPanel);
		jMacPanel.setLayout(jMacLayout);
		jMacLayout.setHorizontalGroup(
				jMacLayout.createSequentialGroup()
					.addContainerGap()
					.add(jMacLabel)
					.addContainerGap()
		);
		jMacLayout.setVerticalGroup(
				jMacLayout.createSequentialGroup()
						.addContainerGap()
						.add(jMacLabel)
						.addContainerGap()		
		);
		
		org.jdesktop.layout.GroupLayout jMozillaLayout = new org.jdesktop.layout.GroupLayout(jMozillaPanel);
		jMozillaPanel.setLayout(jMozillaLayout);
		jMozillaLayout.setHorizontalGroup(
				jMozillaLayout.createSequentialGroup()
					.addContainerGap()
					.add(jMozillaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							.add(jMozillaLabel)
							.add(jMozillaLayout.createSequentialGroup()
									.add(jLabelPerfil)
									.add(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jPerfilTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 340, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
							.add(org.jdesktop.layout.GroupLayout.TRAILING, jButtonAyudaPerfil, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
					.addContainerGap()
		);
		jMozillaLayout.setVerticalGroup(
				jMozillaLayout.createSequentialGroup()
						.addContainerGap()
						.add(jMozillaLabel)
						.add(10,10,10)
						.add(jMozillaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jLabelPerfil)
								.add(jPerfilTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.add(10,10,10)
						.add(jButtonAyudaPerfil, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()		
		);
		
		org.jdesktop.layout.GroupLayout jJavaLayout = new org.jdesktop.layout.GroupLayout(jJavaStorePanel);
		jJavaStorePanel.setLayout(jJavaLayout);
		jJavaLayout.setHorizontalGroup(
				jJavaLayout.createSequentialGroup()
					.addContainerGap()
					.add(jJavaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							.add(jLabelJavaStore)
							.add(jJavaLayout.createSequentialGroup()
									.add(jJavaConfLbl)
									.add(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jJavaConfTextFld, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 280, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
									.add(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jPathConfBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
							.add(org.jdesktop.layout.GroupLayout.CENTER, jJavaStoreManagerButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
					.addContainerGap()
		);
		jJavaLayout.setVerticalGroup(
				jJavaLayout.createSequentialGroup()
						.addContainerGap()
						.add(jLabelJavaStore)
						.add(10,10,10)
						.add(jJavaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jJavaConfLbl)
								.add(jJavaConfTextFld, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jPathConfBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.add(10,10,10)
						.add(jJavaStoreManagerButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()		
		);
		
		org.jdesktop.layout.GroupLayout jAlmacenPanelLayout = new org.jdesktop.layout.GroupLayout(jAlmacenPanel);
		jAlmacenPanel.setLayout(jAlmacenPanelLayout);
		jAlmacenPanelLayout.setHorizontalGroup(
				jAlmacenPanelLayout.createSequentialGroup()
					.addContainerGap()
					.add(jAlmacenPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							.add(jAlmacenPanelLayout.createSequentialGroup()
									.add(jRadioWindows)
									.add(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jLabelIExplorer))
							.add(jAlmacenPanelLayout.createSequentialGroup()
									.add(jRadioMozilla)
									.add(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jLabelFirefox))
							.add(jAlmacenPanelLayout.createSequentialGroup()
									.add(jRadioMac)
									.add(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jLabelMacos))
							.add(jAlmacenPanelLayout.createSequentialGroup()
									.add(jRadioJavaStore)
									.add(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jLabelJava))
							.add(jExplorerStorePanel)
							.add(jMozillaPanel)
							.add(jMacPanel)
							.add(jJavaStorePanel))
					.addContainerGap()
		);
		jAlmacenPanelLayout.setVerticalGroup(
				jAlmacenPanelLayout.createSequentialGroup()
						.addContainerGap()
						.add(jAlmacenPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jRadioWindows)
								.add(jLabelIExplorer))
						.add(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jAlmacenPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jRadioMozilla)
								.add(jLabelFirefox))
						.add(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jAlmacenPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jRadioMac)
								.add(jLabelMacos))
						.add(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jAlmacenPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jRadioJavaStore)
								.add(jLabelJava))
						.add(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jExplorerStorePanel)
						.add(jMozillaPanel)
						.add(jMacPanel)
						.add(jJavaStorePanel)
						.addContainerGap()		
		);

		
		org.jdesktop.layout.GroupLayout jConfigDialogLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
		mainPanel.setLayout(jConfigDialogLayout);
		jConfigDialogLayout.setHorizontalGroup(
				jConfigDialogLayout.createSequentialGroup()
					.addContainerGap()
					.add(jConfigDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
							.add(jAlmacenPanel)
							.add(jConfigDialogLayout.createSequentialGroup()
									.add(jButtonAceptarConfig)
									.add(jButtonCancelarConfig)))
					.addContainerGap()
		);
		jConfigDialogLayout.setVerticalGroup(
				jConfigDialogLayout.createSequentialGroup()
						.addContainerGap()
						.add(jAlmacenPanel)
						.add(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jConfigDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
								.add(jButtonAceptarConfig)
								.add(jButtonCancelarConfig))
						.addContainerGap()
		);
		
		jConfigDialog.add(mainPanel);
		// Se estructura el cuadro de diálogo
		
		jConfigDialog.setModal(true);
		jConfigDialog.setResizable(false);
		jConfigDialog.setSize(510,440);
		
		jConfigDialog.setLocationRelativeTo(jButtonAceptarConfig);
		jConfigDialog.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent ce) {
				jButtonCancelarConfig.requestFocusInWindow();
			}
		});
		
		cargarConfiguracion();
	}

	public String[] getStore() {
		String[] retorno = new String[2];
		if (CFG_ALMACEN_EXPLORER.equalsIgnoreCase(almacenSeleccionado)) {
			retorno[0] = "Explorer";
		} else if (CFG_ALMACEN_MOZILLA.equalsIgnoreCase(almacenSeleccionado)) {
			retorno[0] = "Mozilla";
			retorno[1] = rutaPerfil;
		} else if (CFG_ALMACEN_MAC.equalsIgnoreCase(almacenSeleccionado)) {
			retorno[0]="MacOsX";
		} else if (CFG_ALMACEN_JAVA.equalsIgnoreCase(almacenSeleccionado)) {
			retorno[0] = "MITyC";
			retorno[1] = javaStoreConf;
		}
	
		if (retorno[0] == null || "".equals(retorno[0].trim())) {
			Constants.DIALOG.showErrorStoreNotSupported(jConfigDialog);
		}
		
    	return retorno;
	}

	public ArrayList<JMenuItem> getMenuItems() {
		ArrayList<JMenuItem> lista = new ArrayList<JMenuItem> ();
        
        JMenuItem menuItemCert = new JMenuItem();
		
        menuItemCert.setText(TITULO_ALMACEN);
        menuItemCert.setAccelerator(KeyStroke.getKeyStroke(ATAJO_DE_TECLADO,
                java.awt.Event.ALT_MASK));
        menuItemCert.setVisible(true);
        menuItemCert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	configItemCertActionPerformed();
            }
        });		
        
        lista.add(menuItemCert);
		
		return lista;
	}
	
	/**
     * Se escoge la opción 'Almacén de certificados': Se muestra el cuadro de diálogo
     */
    public void configItemCertActionPerformed() {    	
    	if (CFG_ALMACEN_MOZILLA.equals(almacenSeleccionado)) {
			jRadioMozilla.setSelected(true);
    		jRadioMozillaActionPerformed();
    	} else if (CFG_ALMACEN_EXPLORER.equals(almacenSeleccionado)) {
			jRadioWindows.setSelected(true);
    		jRadioExplorerActionPerformed();
		} else if (CFG_ALMACEN_MAC.equals(almacenSeleccionado)) {
			jRadioMac.setSelected(true);
    		jRadioMacActionPerformed();
		} else {
			jRadioJavaStore.setSelected(true);
    		jRadioJavaStoreActionPerformed();
		}

    	// Se escriben en pantalla las propiedades cargadas para Firefox
    	jPerfilTextField.setText(rutaPerfil);
		
		// Se escriben en pantalla las propiedades cargadas para JavaStore
		if (javaStoreConf != null) {
			jJavaConfTextFld.setText(javaStoreConf);
			jJavaConfTextFld.setToolTipText(javaStoreConf);
			jJavaConfTextFld.setBackground(obtenerColor(new File(javaStoreConf)));
		}
		
    	jConfigDialog.setTitle(TITULO_ALMACEN);
    	jConfigDialog.setVisible(true);
    }
    
    /**
     * <p>Abre el administrador del almacén Java</p>
     */
    private void jButtonJavaStoreManagerActionPerformed() {
    	File conf = new File(javaStoreConf);
    	if (!conf.exists()) {
    		Constants.DIALOG.showErrorConfJavaStore(jConfigDialog);
    		return;
    	}
    	try {
    		MITyCStore mks = MITyCStore.getInstance(conf, true);
    		JavaStoreManager manager = JavaStoreManager.getInstance(null, true, (IPKStoreManager) mks, (IPKStoreMaintainer) mks);
    		manager.setVisible(true);
    		logger.debug("Se ha puesto visitle la ventana administración almacén");
    		// Posiciona la ventana en el centro de la pantalla
    		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    		manager.setBounds((screenSize.width-manager.getWidth())/2, (screenSize.height-manager.getHeight())/2, manager.getWidth(), manager.getHeight());  
    		logger.debug("Accedido almacén");
    	} catch (CertStoreException e) {
    		logger.error(e.getMessage(), e);
    		Constants.DIALOG.showErrorConfJavaStore(jConfigDialog);
    	}
    }
    
    /**
	 * Se guarda la configuración de la aplicación cuando se pulsa sobre el
	 * botón 'Aceptar' en la ventana de configuración
	 */
	private void jButtonAceptarConfigActionPerformed() {
		
		almacenSeleccionado = jRadioMozilla.isSelected()? CFG_ALMACEN_MOZILLA :
														  jRadioWindows.isSelected()? CFG_ALMACEN_EXPLORER:
														  jRadioMac.isSelected()? CFG_ALMACEN_MAC : CFG_ALMACEN_JAVA;
		
		javaStoreConf = jJavaConfTextFld.getText();

		if (CFG_ALMACEN_JAVA.equals(almacenSeleccionado)) {
			if (javaStoreConf != null && !"".equals(javaStoreConf)) {
				// Se parsea la configuración
				InputStream is = null;
				try {
					is = new FileInputStream(javaStoreConf);
				} catch(FileNotFoundException e) {
					logger.error(e.getMessage(), e);
					Constants.DIALOG.showErrorConfJavaStore(jConfigDialog);
					return;
				}

				Properties prop = new Properties();
				try {
					prop.load(is);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					Constants.DIALOG.showErrorConfJavaStore(jConfigDialog);
					return;
				}

				String storeURL = prop.getProperty("KeyStoreName");
				
				if (storeURL == null || "".equals(storeURL)) {
					Constants.DIALOG.showErrorConfJavaStore(jConfigDialog);
					return;
				}

				int res = Constants.DIALOG.showConfirmJavaStoreUsed(jConfigDialog, storeURL);
				if (res == JOptionPane.NO_OPTION) {
					return;
				}
			} else {
				Constants.DIALOG.showWarnConfigFileRequired(jConfigDialog);
				return;
			}
		}
		
		rutaPerfil = cambiaSeparadorDir(jPerfilTextField.getText());
		
		guardarConfiguracion();
		
		jButtonCancelarConfigActionPerformed(); // Sale del diálogo
	}
	
	/**
	 * Sale del cuadro de diálogo de configuración de roles
	 */
	private void jButtonCancelarConfigActionPerformed() {		
		jConfigDialog.setVisible(false);
		jConfigDialog.dispose();
	}
	
	/**
     * Este método guarda la configuración en el fichero "nombreFichero"
     */
    public void guardarConfiguracion() {	
    	// Flags para indicar si se escribió la clave
    	boolean almacenOK = false;
    	boolean javaStoreOk = false;
    	boolean perfilOK = false;
    	boolean loginOK = false;
    	boolean tiempoOK = false;
    	
        StringBuffer paraGrabar = new StringBuffer();
	    String nombreDir = Constants.APP_PROP.getProperty("CONFIG_EXT_DIR");

    	// La configuración siempre se guarda en un fichero externo
	    File dir = new File(System.getProperty(Constants.APP_PROP.getProperty("USER_HOME")) + File.separator + nombreDir);
	    if ((!dir.exists()) || (!dir.isDirectory())) {
	    	if (!dir.mkdir())
	    		return;
	    }
	    
	    File fichero = new File(dir, Constants.APP_PROP.getProperty("CONFIG_EXT_FILE"));
	    
	    logger.trace("Salva fichero de configuraci\u00f3n en: " + fichero.getAbsolutePath());

	    BufferedWriter bw = null;
	    try {
	    	if (fichero.exists()) {
	    		BufferedReader propiedades = new BufferedReader(new FileReader(fichero));

	    		String linea = propiedades.readLine();
	    		while(linea != null) {
	    			StringTokenizer token = new StringTokenizer(linea, "=");
	    			if (token.hasMoreTokens())
	    			{
	    				String clave =token.nextToken().trim();

	    				if (CFG_ALMACEN.equals(clave))
	    				{
	    					almacenOK = true;
	    					paraGrabar.append(CFG_ALMACEN);
	    					paraGrabar.append(" = ");
	    					paraGrabar.append(almacenSeleccionado);
	    					paraGrabar.append("\n");
	    				}
	    				else if (CFG_RUTA_PROFILE_MOZILLA.equals(clave))
	    				{
	    					perfilOK = true;
	    					paraGrabar.append(CFG_RUTA_PROFILE_MOZILLA);
	    					paraGrabar.append(" = ");
	    					paraGrabar.append(rutaPerfil);
	    					paraGrabar.append("\n");
	    				} 
	    				else if (CFG_MOZILLA_TOKEN_TIPO_LOGIN.equals(clave))
	    				{
	    					loginOK = true;
	    					paraGrabar.append(CFG_MOZILLA_TOKEN_TIPO_LOGIN);
	    					paraGrabar.append(" = ");
	    					paraGrabar.append(String.valueOf(tipoTokenLogin));
	    					paraGrabar.append("\n");
	    				}
	    				else if (CFG_MOZILLA_TOKEN_LOGINTIMEOUT.equals(clave))
	    				{
	    					tiempoOK = true;
	    					paraGrabar.append(CFG_MOZILLA_TOKEN_LOGINTIMEOUT);
	    					paraGrabar.append(" = ");
	    					paraGrabar.append(tiempoLog);
	    					paraGrabar.append("\n");
	    				}
	    				else if (CFG_RUTA_CONF_JAVA.equals(clave))
	    				{
	    					javaStoreOk = true;
	    					paraGrabar.append(CFG_RUTA_CONF_JAVA);
	    					paraGrabar.append(" = ");
	    					if ( javaStoreConf != null )
	    						paraGrabar.append(javaStoreConf);
	    					paraGrabar.append("\n");
	    				}
	    				else
	    				{
	    					paraGrabar.append(linea);
	    					paraGrabar.append("\n");

	    				}
	    			}
	    			else
	    				paraGrabar.append("\n");
	    			linea = propiedades.readLine();
	    		}
	    		propiedades.close();
	    	}
	    	if (!almacenOK) {
	    		paraGrabar.append(CFG_ALMACEN);
				paraGrabar.append(" = ");
				paraGrabar.append(almacenSeleccionado);
				paraGrabar.append("\n");
	    	}
	    	if (!perfilOK) {
	    		paraGrabar.append(CFG_RUTA_PROFILE_MOZILLA);
				paraGrabar.append(" = ");
				paraGrabar.append(rutaPerfil);
				paraGrabar.append("\n");
	    	}
	    	if (!loginOK) {
	    		loginOK = true;
				paraGrabar.append(CFG_MOZILLA_TOKEN_TIPO_LOGIN);
				paraGrabar.append(" = ");
				paraGrabar.append(String.valueOf(tipoTokenLogin));
				paraGrabar.append("\n");
	    	}
	    	if (!tiempoOK) {
	    		paraGrabar.append(CFG_MOZILLA_TOKEN_LOGINTIMEOUT);
				paraGrabar.append(" = ");
				paraGrabar.append(tiempoLog);
				paraGrabar.append("\n");
	    	}
	    	if (!javaStoreOk) {
	    		paraGrabar.append(CFG_RUTA_CONF_JAVA);
				paraGrabar.append(" = ");
				if ( javaStoreConf != null )
					paraGrabar.append(javaStoreConf);
				paraGrabar.append("\n");
	    	}

	    	//AppPerfect: Falso positivo
	    	bw = new BufferedWriter(new FileWriter(fichero));
	    	bw.write(String.valueOf(paraGrabar));
	    	bw.flush();
	    } catch (IOException e) {
	    	logger.error("No se puede salvar la configuraci\u00f3n de UCCDPlugin: " + e.getMessage(), e);
	    } finally {
	    	try {
	    		bw.close();
	    	} catch (Exception e) {
	    		// No hace nada
	    	}
	    }	    	    
    }
    
    /**
     * Carga la configuración contenida en el archivo de propiedades.
     */
    private void cargarConfiguracion() {
    	
    	String nombreDir = Constants.APP_PROP.getProperty("CONFIG_EXT_DIR");

    	try {
    		// La configuración siempre se guarda en un fichero externo
    		File dir = new File(System.getProperty(Constants.APP_PROP.getProperty("USER_HOME")) + File.separator + nombreDir);
    		if ((!dir.exists()) || (!dir.isDirectory())) {
    			logger.trace("No existe el directorio contenedor de la configuraci\u00f3n llamado: " + dir.getAbsolutePath());
    			return;
    		}
    		File fichero = new File(dir, Constants.APP_PROP.getProperty("CONFIG_EXT_FILE"));
    		logger.trace("Carga el fichero de configuraci\u00f3n de: " + fichero.getAbsolutePath());

 			BufferedReader propiedades = new BufferedReader(new FileReader(fichero));

 			String linea = propiedades.readLine();
 			while (linea != null) {
 				StringTokenizer token = new StringTokenizer(linea, "=");
 				if (token.hasMoreTokens()) {
 					String clave = token.nextToken().trim();
 					String valor = "";
 					if (token.hasMoreTokens())
 						valor = token.nextToken();
 					
 					if (CFG_ALMACEN.equals(clave) && valor != null)
 						almacenSeleccionado = valor.trim();
 					else if (CFG_RUTA_PROFILE_MOZILLA.equals(clave) && valor != null)
 						rutaPerfil = valor.trim();	
 					else if (CFG_MOZILLA_TOKEN_TIPO_LOGIN.equals(clave) && valor != null)
 						tipoTokenLogin = getLoginMode(valor.trim());	
 					else if (CFG_MOZILLA_TOKEN_LOGINTIMEOUT.equals(clave) && valor != null)
 						tiempoLog = Integer.valueOf(valor.trim());
 					else if (CFG_RUTA_CONF_JAVA.equals(clave) && valor != null) {
 						javaStoreConf = valor.trim();
 					}
 				}
 				linea = propiedades.readLine();
 			}
 		} catch (FileNotFoundException e) {
 			logger.trace("No existe el fichero de configuraci\u00f3n, se carga la configuraci\u00f3n por defecto");
 			confPorDefecto();
 		} catch (IOException e) {
 			logger.trace("No se puede leer el fichero de configuraci\u00f3n, se carga la configuraci\u00f3n por defecto");
 			confPorDefecto();
 		} catch (NoSuchElementException e) {
 			logger.trace("El fichero de configuraci\u00f3n no esta bien formado, se carga la configuraci\u00f3n por defecto");
 			confPorDefecto();
 		} finally {
 			// Estado inicial: Todo deshabilitado
 			jRadioWindows.setEnabled(false);  
 	    	jRadioMac.setEnabled(false);  
 	    	jRadioMozilla.setEnabled(false);
 	    	
 			// Se habilita la posibilidad de utilizar almacenes en función de su ámbito de uso
 			if (OSTool.getSO().isWindows()) {  
 				jRadioWindows.setEnabled(true);
 				jRadioMozilla.setEnabled(true);  
 				if (CFG_ALMACEN_MAC.equals(almacenSeleccionado)) {
 					almacenSeleccionado = CFG_ALMACEN_EXPLORER;
 				}
 			} else if (OSTool.getSO().isMacOsX()) {
 				jRadioMac.setEnabled(true);
 				jRadioMozilla.setEnabled(true);
 				if (CFG_ALMACEN_EXPLORER.equals(almacenSeleccionado) ||
 						CFG_ALMACEN_MOZILLA.equals(almacenSeleccionado)) {
 					almacenSeleccionado = CFG_ALMACEN_MAC;
 				}
 			} else if (OSTool.getSO().isLinux()) {
 				jRadioMozilla.setEnabled(true);
 				if (CFG_ALMACEN_EXPLORER.equals(almacenSeleccionado) ||
 						CFG_ALMACEN_MAC.equals(almacenSeleccionado)) {
 					almacenSeleccionado = CFG_ALMACEN_JAVA;
 				}
 			}
 		}
    }
    
    private void jRadioExplorerActionPerformed() {
    	jExplorerStorePanel.setVisible(true);
    	jMozillaPanel.setVisible(false);
    	jMacPanel.setVisible(false);
    	jLabelPerfil.setVisible(false);
		jPerfilTextField.setVisible(false);
		jButtonAyudaPerfil.setVisible(false);
    	jJavaStorePanel.setVisible(false);
    }
    
    /**
     * Este evento se lanza cuando se selecciona el almacén de Firefox
     */
    private void jRadioMozillaActionPerformed() {
    	jExplorerStorePanel.setVisible(false);
    	jMacPanel.setVisible(false);
    	jLabelPerfil.setVisible(true);
		jPerfilTextField.setVisible(true);
		jButtonAyudaPerfil.setVisible(true);
    	jMozillaPanel.setVisible(true);
		jJavaStorePanel.setVisible(false);
    }
    
    /**
     * Este evento se lanza cuando se selecciona el almacén de Mac OS
     */
    private void jRadioMacActionPerformed() {
    	jExplorerStorePanel.setVisible(false);
    	jMozillaPanel.setVisible(false);
    	jMacPanel.setVisible(true);
    	jLabelPerfil.setVisible(false);
		jPerfilTextField.setVisible(false);
		jButtonAyudaPerfil.setVisible(false);
		jJavaStorePanel.setVisible(false);
    }
    
    /**
     * 
     */
    private void jRadioJavaStoreActionPerformed() {
    	jExplorerStorePanel.setVisible(false);
    	jMozillaPanel.setVisible(false);
    	jMacPanel.setVisible(false);
    	jLabelPerfil.setVisible(false);
		jPerfilTextField.setVisible(false);
		jButtonAyudaPerfil.setVisible(false);
		jJavaStorePanel.setVisible(true);
    }
    
    private void confPorDefecto() {
    	almacenSeleccionado = CFG_ALMACEN_EXPLORER;
    	rutaPerfil = "";
		javaStoreConf = null;
    }
    
    private MozillaTokenLoginModeEnum getLoginMode (String mode) {
		MozillaTokenLoginModeEnum emod = MozillaTokenLoginModeEnum.getDefault();
		if (String.valueOf(MozillaTokenLoginModeEnum.ONE_TIME).equals(mode)) 
			emod = MozillaTokenLoginModeEnum.ONE_TIME;
		if (String.valueOf(MozillaTokenLoginModeEnum.TIMEOUT).equals(mode)) 
			emod = MozillaTokenLoginModeEnum.TIMEOUT;
		if (String.valueOf(MozillaTokenLoginModeEnum.EVERY_TIME).equals(mode)) 
			emod = MozillaTokenLoginModeEnum.EVERY_TIME;
		
		return emod;
	}
    
    /**
     * <p>Permite validar si el fichero indicado existe.
     * Se devuelve un color en función del resultado, 
     * y si el fichero no existe se muestra un aviso.</p>
     * @param file fichero a validar
     * @return VERDE si el fichero existe, AMARILLO si no existe.
     */
    private Color obtenerColor(File file) {
		if (!file.isDirectory() && file.exists()) {
			jJavaStoreManagerButton.setEnabled(true);
			return VERDE;
		} else {
			jJavaStoreManagerButton.setEnabled(false);
			return ROJO;
		}
	}
    
    private static String cambiaSeparadorDir(String original) {
    	return original.replace("\\", "/");
    }
    
    /**
	 * <p>Muestra un JFileChooser para seleccionar</p>
	 * 
	 * @param confFile Path del fichero de configuración
	 */
	private File jButtonPathActionPerformed(String confFile) {
		
		logger.debug("confFile:" +confFile);
		
		if (confFile == null) { // Se indica una ruta por defecto
			confFile = System.getProperty(Constants.APP_PROP.getProperty("USER_HOME")) + File.separator +
			Constants.APP_PROP.getProperty("CONFIG_EXT_DIR")+ 
			File.separator + Constants.APP_PROP.getProperty("PROPOSED_CONF");
			logger.debug("confFile:" +confFile);
		} 
		
		JFileChooser fc = new JFileChooser(confFile);
		fc.setDialogTitle(Constants.LANG.getString("Store.JavaMsg7"));
		fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
		fc.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else if (f.getName().endsWith(".properties")) {
					return true;
				} else
					return false;
			}
			@Override
			public String getDescription() {
				return "Ficheros .properties de configuración";
			}
		});
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setSelectedFile(new File(confFile));

		int returnVal = fc.showDialog(jConfigDialog, "Seleccionar");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String path = file.getPath();

			if (file != null && file.exists()) {
				return file;
			} else { // Si no existe, pregunta si se crea nuevo a partir de un recurso interno.
				if (path != null && !path.endsWith(".properties")) {
					path = path.concat(".properties");
				}
				
				file = new File(path);

				int res = Constants.DIALOG.showConfirmCreateConfigFile(jConfigDialog); 
				if (res == JOptionPane.YES_OPTION) {
					// Se copia el recurso interno en la ruta indicada
					BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream("/MITyC_KS.properties"));
					BufferedOutputStream bos = null;
					byte[] buffer = new byte[4096];
					int i = 0;
					int length;
					try {
						bos = new BufferedOutputStream(new FileOutputStream(file));
						length = bis.available();
						while (i < length) {
							int j = bis.read(buffer);
							bos.write(buffer, 0, j);
							bos.flush();
							i += j;
						}						
					} catch (IOException e) {
					} finally {
						if (bos != null) {
							try {bos.close();} catch (IOException e) {}
						}
						if (bis != null) { 
							try {bis.close();} catch (IOException e) {}
						}
					}

					// Se sustituye la ruta por defecto con el valor propio del usuario
					String nombreDir = Constants.APP_PROP.getProperty("CONFIG_EXT_DIR");

					BufferedReader propiedades = null;
					BufferedWriter bw = null;
					StringTokenizer token = null;
					try {
						propiedades = new BufferedReader(new FileReader(file));
						StringBuffer sb = new StringBuffer();
						String linea = propiedades.readLine();
						while(linea != null) {

							token = new StringTokenizer(linea, "=");
							if (token.hasMoreTokens()) {
								String clave = token.nextToken().trim();
								
								if (ConstantsCert.KS_NAME_KEY.equals(clave)) {
									String keyStorePath = System.getProperty(Constants.APP_PROP.getProperty("USER_HOME")) + File.separator + nombreDir + File.separator + Constants.APP_PROP.getProperty("JAVASTORE_EXT");
									sb.append(ConstantsCert.KS_NAME_KEY);
									sb.append(" = ");
									sb.append(keyStorePath.replace("\\", "/"));
									sb.append("\n");
								} else {
									sb.append(linea + "\n");
								}
							}
							linea = propiedades.readLine();
						}
						bw = new BufferedWriter(new FileWriter(file));
						bw.write(String.valueOf(sb));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try { propiedades.close(); } catch (IOException e) { /* No se hace nada */ }
						try { bw.flush(); } catch (IOException e) { /* No se hace nada */ }
					}
				}
				return file;
			}
		} else
			return null;
	}

	// Panel almacén de certificados
	private JDialog jConfigDialog = null;
	private JPanel mainPanel = null;
	private JPanel jAlmacenPanel = null;
	
	private JButton jButtonAceptarConfig = null;
	private JButton jButtonCancelarConfig = null;
	
    private ButtonGroup radioGrpExpMozJa = null;
    private JRadioButton jRadioWindows = null;
    private JRadioButton jRadioMozilla = null;
    private JRadioButton jRadioMac = null;
    private JRadioButton jRadioJavaStore = null;
    private JLabel jLabelPerfil = null;
    private JButton jButtonAyudaPerfil = null;
    private JTextField jPerfilTextField = null;
    
    private JPanel jExplorerStorePanel = null;
    private JLabel jExplorerStoreLbl = null;
    
    private JLabel jLabelIExplorer = null;
    private JLabel jLabelFirefox = null;
    private JLabel jLabelMacos = null;
    private JLabel jLabelJava = null;
    
    private JPanel jMozillaPanel = null;
	private JLabel jMozillaLabel = null;
	
	private JPanel jMacPanel = null;
	private JLabel jMacLabel = null;
   
    private JPanel jJavaStorePanel = null;
    private JLabel jLabelJavaStore = null;
    private JLabel jJavaConfLbl = null;
    private JTextField jJavaConfTextFld = null;
    private JButton jPathConfBtn = null;
	private JButton jJavaStoreManagerButton = null;
}
