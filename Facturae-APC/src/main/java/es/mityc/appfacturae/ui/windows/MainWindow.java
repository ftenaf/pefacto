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
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.exception.JDBCConnectionException;

import de.wannawork.jcalendar.JCalendarComboBox;
import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.SignInvoiceException;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.EnumOperationType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.ui.components.ButtonDeleteEditor;
import es.mityc.appfacturae.ui.components.ButtonSeeFACeSentResultEditor;
import es.mityc.appfacturae.ui.components.ButtonSeeHTMLEditor;
import es.mityc.appfacturae.ui.components.IndividualRow;
import es.mityc.appfacturae.ui.components.InvoicesTree;
import es.mityc.appfacturae.ui.components.LegalEntityRow;
import es.mityc.appfacturae.ui.components.NoEdiTableModel;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.components.PicturedViewport;
import es.mityc.appfacturae.ui.components.TranslucentPanel;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.dialogs.importers.ExportDataBaseDialog;
import es.mityc.appfacturae.ui.dialogs.importers.ExportInvoicesDialog;
import es.mityc.appfacturae.ui.dialogs.importers.ImportDataBaseDialog;
import es.mityc.appfacturae.ui.dialogs.importers.ImportInvoicesDialog;
import es.mityc.appfacturae.ui.dialogs.importers.InputSelectImportExportOption;
import es.mityc.appfacturae.ui.renderers.ButtonDeleteRenderer;
import es.mityc.appfacturae.ui.renderers.ButtonSeeFACeSentResultRenderer;
import es.mityc.appfacturae.ui.renderers.ButtonSeeHTMLRenderer;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.ui.renderers.InvoicesTreeRenderer;
import es.mityc.appfacturae.ui.transitions.Transition;
import es.mityc.appfacturae.ui.utils.GUIUtils;
import es.mityc.appfacturae.ui.windows.panels.InvoiceActionsPane;
import es.mityc.appfacturae.utils.InvoiceUtil;
import es.mityc.appfacturae.utils.XMLFacturaeUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.appfacturae.utils.io.ImageUtil;
import es.mityc.facturae.utils.UnmarshalException;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 8092147181012546000L;

	private static Log logger = LogFactory.getLog(MainWindow.class);
    
    // Transition panel
    private Thread th = null;
    private boolean uncontrolledExit = true;
    
    // It stores the selected invoices' info
    int invoiceState = -1;
    int invoiceClass = -1;
    
    private static MainWindow mw = null;
	
    private Transition t = null;
    
	/**
	 * Singleton pattern
	 */
	static public MainWindow getInstance() {
		if (mw == null)
			mw = new MainWindow();
		
		return mw;
	}
    /**
     * Creates new form MainWindow 
     */
    private MainWindow() {
        super();
        InitialWindow.getInstance().refreshProgressBar(10);
        /** Loading images */
        try {
        	imgBackground = ImageIO.read(this.getClass().getResourceAsStream("/images/background.jpg"));
            imgWhite = ImageIO.read(this.getClass().getResourceAsStream("/images/white.jpg"));
            imgLogoApp = ImageIO.read(this.getClass().getResourceAsStream("/images/logoapp.jpg"));
        } catch (Exception e) {
            logger.error("An exception occurred when loading the images: " + e.getMessage());
            return;
        }
        
        InitialWindow.getInstance().refreshProgressBar(30);
        /** Init components */
        initComponents();
        
        InitialWindow.getInstance().refreshProgressBar(60);
        loadData();
        
        t = new Transition(mainPanel);
        
        /** Standard window properties */
        this.pack();
        this.setSize(Integer.valueOf(Constants.APP_PROP.getProperty("Width")),Integer.valueOf(Constants.APP_PROP.getProperty("Height")));
        this.setTitle(Constants.LANG.getString("MainWindowTitle"));
        this.setIconImage(imgLogoApp);
        this.setLocationRelativeTo(null);
        // If screen size is less or equals to 1024x768, initial window position must be at 0,0 
        // instead of centered on the screen, due to visibility problem
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        if (dim.getHeight() <= 768)
        	this.setLocation(0,0);
        
        try {
			FacturaeManager.getInstance().saveAction(EnumOperationType.AppStart, null);
        } catch (DatabaseOperationException e) {
			// The user is not informed about action not saved event. An error log has been created previously.
		}
    }

    /**
     * Initializes all window components
     */
    private void initComponents() {
        mainPanel = new PicturedPanel(imgBackground,1.00f);
        jLabelTopBar = new JLabel();
        jScrollPaneTree = new JScrollPane();
        jTreeInvoices = new JTree();
        jComboBoxGenVersion = new JComboBox();
        jTabbedPaneInvoiceActions = new InvoiceActionsPane();
        jPanelInvoiceFilter = new TranslucentPanel();
        jLabelFiltIssuer = new JLabel();
        jComboBoxFiltIssuer = new JComboBox();
        jLabelFiltReceiver = new JLabel();
        jComboBoxFiltReceiver = new JComboBox();
        jRadioButtonFiltDate = new JRadioButton();
        jLabelFiltItem = new JLabel();
        jComboBoxFiltItem = new JComboBox();
        jCalendarComboBoxFiltFrom = new JCalendarComboBox();
        jLabelFiltTo = new JLabel();
        jCalendarComboBoxFiltTo = new JCalendarComboBox();
        jButtonFilter = new JButton();
        jButtonGenerate = new JButton();
        jButtonReceive = new JButton();
        jButtonConfig = new JButton();
        jButtonSearch = new JButton();
        jButtonHistorical = new JButton();
        jButtonImpExp = new JButton();
        jScrollPaneTable = new JScrollPane();
        jTableInvoices = new JTable();
        jPanelProgress = new TranslucentPanel();
        jSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        jSeparator2 = new JSeparator(SwingConstants.HORIZONTAL);
        jProgressBar = new JProgressBar();
        jLabelProgress = new JLabel();
        jLabelLogo = new JLabel();
        jButtonHelp = new JButton();
        jButtonExit = new JButton();
        
    	// Progress Bar
    	jProgressBar.setMaximum(100);
    	jProgressBar.setIndeterminate(false);
    	jProgressBar.setValue(0);
    	
    	// Window properties
    	setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowClosing());
        setResizable(false);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() { public void run() {
        	appHook();
        }}));
        
        int width = Integer.valueOf(Constants.APP_PROP.getProperty("Width"));
        int height = Integer.valueOf(Constants.APP_PROP.getProperty("Height"));
        mainPanel.setMaximumSize(new java.awt.Dimension(width, height));
        mainPanel.setMinimumSize(new java.awt.Dimension(width, height));
        
        // Invoices Folder
        File dir = new File(Constants.APP_PROP.getProperty("invoices_root"));
        if (!dir.exists())
        	dir.mkdir();
        File stlDir = new File(System.getProperty("user.home"), Constants.APP_PROP.getProperty("invoices_stealth_dir"));
        if (!stlDir.exists())
        	stlDir.mkdir();

        // Component properties
        jSeparator.setForeground(Constants.SEPARATOR_COLOR);
        jSeparator2.setForeground(Constants.SEPARATOR_COLOR);
        
        jLabelTopBar.setIcon(new ImageIcon(getClass().getResource("/images/topbar.jpg"))); 
        jLabelTopBar.setBorder(new LineBorder(Constants.BORDER_COLOR, 1, true));

        jScrollPaneTree.setBackground(Constants.BORDER_COLOR);
        jScrollPaneTree.setBorder(BorderFactory.createTitledBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("InvoicesTree"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Constants.TITLE_FONT, Constants.FONT_COLOR)); 
        jScrollPaneTree.setForeground(Constants.BORDER_COLOR);
        jScrollPaneTree.setOpaque(false);

        jTreeInvoices.setBackground(Constants.BKG_MAIN_COLOR);
        jTreeInvoices.setCellRenderer(new InvoicesTreeRenderer());
        jTreeInvoices.setModel(new InvoicesTree().getModel());
        jTreeInvoices.addTreeSelectionListener(new TreeSelectionListener() {
        	public void valueChanged(TreeSelectionEvent evt) {
        		if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				try {
        					t.putTransitionPanel(Constants.LANG.getString("LoadingInvoices"));
        					try {
        						Thread.sleep(500);
        					} catch (InterruptedException e) {}
        					MainWindow.this.valueChanged();
        				} finally {
        					t.removeTransitionPanel();
        				}
        			}});
        			th.start();
        		}
        	}
        });
        
        jScrollPaneTree.setViewportView(jTreeInvoices);

        jComboBoxGenVersion.setModel(new DefaultComboBoxModel(Constants.APP_PROP.getProperty("versions").split(";")));

        jPanelInvoiceFilter.setBorder(BorderFactory.createTitledBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Filter"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Constants.TITLE_FONT, Constants.FONT_COLOR)); 

        jLabelFiltIssuer.setFont(Constants.TITLE_FONT_PLAIN);
        jLabelFiltIssuer.setForeground(Constants.FONT_COLOR);
        jLabelFiltIssuer.setText(Constants.LANG.getString("Issuer")); 

        jComboBoxFiltIssuer.setFont(Constants.FONT_PLAIN);
        jComboBoxFiltIssuer.setModel(new DefaultComboBoxModel(new String[] {""}));
        jComboBoxFiltIssuer.setRenderer(new ComboBoxRenderer(false));

        jLabelFiltReceiver.setFont(Constants.TITLE_FONT_PLAIN);
        jLabelFiltReceiver.setForeground(Constants.FONT_COLOR);
        jLabelFiltReceiver.setText(Constants.LANG.getString("Receiver")); 

        jComboBoxFiltReceiver.setFont(Constants.FONT_PLAIN);
        jComboBoxFiltReceiver.setModel(new DefaultComboBoxModel(new String[] {""}));
        jComboBoxFiltReceiver.setRenderer(new ComboBoxRenderer(false));

        jRadioButtonFiltDate.setFont(Constants.TITLE_FONT_PLAIN);
        jRadioButtonFiltDate.setForeground(Constants.FONT_COLOR);
        jRadioButtonFiltDate.setText(Constants.LANG.getString("Date")); 
        jRadioButtonFiltDate.setBackground(Color.white);
        jRadioButtonFiltDate.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		jPanelDate.setVisible(jRadioButtonFiltDate.isSelected());
        	}
        });

        jLabelFiltItem.setFont(Constants.TITLE_FONT_PLAIN);
        jLabelFiltItem.setForeground(Constants.FONT_COLOR);
        jLabelFiltItem.setText(Constants.LANG.getString("Item")); 

        jComboBoxFiltItem.setFont(Constants.FONT_PLAIN);
        jComboBoxFiltItem.setModel(new DefaultComboBoxModel(new String[] {""}));
        jComboBoxFiltItem.setRenderer(new ComboBoxRenderer(true));
        
        jPanelDate = new JPanel();
        jPanelDate.setBackground(es.mityc.appfacturae.utils.constants.Constants.BKG_MAIN_COLOR);
        jPanelDate.setVisible(false);

        org.jdesktop.layout.GroupLayout jPanelDateLayout = new org.jdesktop.layout.GroupLayout(jPanelDate);
        jPanelDate.setLayout(jPanelDateLayout);
        jPanelDateLayout.setHorizontalGroup(jPanelDateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(jPanelDateLayout.createSequentialGroup()
        				.add(jCalendarComboBoxFiltFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jLabelFiltTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        				.add(jCalendarComboBoxFiltTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        		)
        );
        jPanelDateLayout.setVerticalGroup(  jPanelDateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(jPanelDateLayout.createSequentialGroup()
        				.add(jPanelDateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
        						.add(jCalendarComboBoxFiltFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jLabelFiltTo)
        						.add(jCalendarComboBoxFiltTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				)                          
        		)
        );
        jCalendarComboBoxFiltFrom.setFont(Constants.FONT_PLAIN);
        
        jLabelFiltTo.setFont(Constants.TITLE_FONT_PLAIN);
        jLabelFiltTo.setForeground(Constants.FONT_COLOR);
        jLabelFiltTo.setText(Constants.LANG.getString("To")); 

        jCalendarComboBoxFiltTo.setFont(Constants.FONT_PLAIN);

        jButtonFilter.setBackground(Constants.BORDER_COLOR);
        jButtonFilter.setFont(Constants.TITLE_FONT);
        jButtonFilter.setForeground(Constants.FONT_COLOR);
        jButtonFilter.setText(Constants.LANG.getString("Search"));
        jButtonFilter.setMargin(new Insets(1, 1, 1, 1));
        jButtonFilter.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonFilter.setContentAreaFilled(false);
        jButtonFilter.setBorderPainted(false);
        jButtonFilter.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				try {
        					t.putTransitionPanel(Constants.LANG.getString("LoadingInvoices"));
        					try {
        						Thread.sleep(500);
        					} catch (InterruptedException e) {}
        					filterInvoices();
        				} finally {
        					jButtonFilter.setEnabled(true);
        					t.removeTransitionPanel();
        				}
        			}});
        			th.start();
        		}
        	}
        });
        jButtonFilter.addMouseListener(new ButtonCursor());

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanelInvoiceFilter);
        jPanelInvoiceFilter.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBoxFiltIssuer, 0, 200, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelFiltIssuer))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jComboBoxFiltReceiver, 0, 200, Short.MAX_VALUE)
                            .add(jLabelFiltReceiver))
                        .add(10, 10, 10)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabelFiltItem)
                            .add(jComboBoxFiltItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(40, 40, 40))
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jRadioButtonFiltDate)
                        .add(10, 10, 10)
                        .add(jPanelDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        ))
                .add(jButtonFilter)
                .add(17, 17, 17))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jButtonFilter, 32, 32, 32)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jLabelFiltIssuer)
                            .add(jLabelFiltReceiver)
                            .add(jLabelFiltItem))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jComboBoxFiltIssuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jComboBoxFiltReceiver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jComboBoxFiltItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jRadioButtonFiltDate)
                            .add(jPanelDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jButtonGenerate.setBackground(Constants.BORDER_COLOR);
        jButtonGenerate.setFont(Constants.BUTTON_FONT);
        jButtonGenerate.setForeground(Constants.FONT_COLOR);
        jButtonGenerate.setText(Constants.LANG.getString("GenerateInvoice"));
        jButtonGenerate.setMargin(new Insets(1, 1, 1, 1));
        jButtonGenerate.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonGenerate.setBorderPainted(false);
        jButtonGenerate.setContentAreaFilled(false);
        jButtonGenerate.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				try {
        					MainWindow.getInstance().getJTableInvoices().clearSelection();
        					t.putTransitionPanel(Constants.LANG.getString("GeneratingInvoice"));
        					generateAction();
        				} finally {
        					t.removeTransitionPanel();
        				}
        			}});
        			th.start();
        		}
        	}
        });
        jButtonGenerate.addMouseListener(new ButtonCursor());

        jButtonReceive.setFont(Constants.BUTTON_FONT);
        jButtonReceive.setForeground(Constants.FONT_COLOR);
        jButtonReceive.setText(Constants.LANG.getString("ReceiveInvoice")); 
        jButtonReceive.setMargin(new Insets(1, 1, 1, 1));
        jButtonReceive.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonReceive.setBorderPainted(false);
        jButtonReceive.setContentAreaFilled(false);
        jButtonReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	receiveAction();
            }
        });
        jButtonReceive.addMouseListener(new ButtonCursor());
        
        jButtonConfig.setFont(Constants.BUTTON_FONT);
        jButtonConfig.setForeground(Constants.FONT_COLOR);
        jButtonConfig.setText(Constants.LANG.getString("Configuration"));
        jButtonConfig.setMargin(new Insets(1, 1, 1, 1));
        jButtonConfig.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonConfig.setBorderPainted(false);
        jButtonConfig.setContentAreaFilled(false);
        jButtonConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	configurationAction();
            }
        });
        jButtonConfig.addMouseListener(new ButtonCursor());

        jButtonSearch.setFont(Constants.BUTTON_FONT);
        jButtonSearch.setForeground(Constants.FONT_COLOR);
        jButtonSearch.setText(Constants.LANG.getString("AdvancedSearch"));
        jButtonSearch.setMargin(new Insets(1, 1, 1, 1));
        jButtonSearch.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonSearch.setBorderPainted(false);
        jButtonSearch.setContentAreaFilled(false);
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	advancedSearchAction();
            }
        });
        jButtonSearch.addMouseListener(new ButtonCursor());

        jButtonHistorical.setFont(Constants.BUTTON_FONT);
        jButtonHistorical.setForeground(Constants.FONT_COLOR);
        jButtonHistorical.setText(Constants.LANG.getString("Historical"));
        jButtonHistorical.setMargin(new Insets(1, 1, 1, 1));
        jButtonHistorical.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonHistorical.setBorderPainted(false);
        jButtonHistorical.setContentAreaFilled(false);
        jButtonHistorical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	historicalAction();
            }
        });
        jButtonHistorical.addMouseListener(new ButtonCursor());

        jButtonImpExp.setFont(Constants.BUTTON_FONT);
        jButtonImpExp.setForeground(Constants.FONT_COLOR);
        jButtonImpExp.setText(Constants.LANG.getString("ImportExport")); 
        jButtonImpExp.setMargin(new Insets(1, 1, 1, 1));
        jButtonImpExp.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
        jButtonImpExp.setBorderPainted(false);
        jButtonImpExp.setContentAreaFilled(false);
        jButtonImpExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	impExpAction();
            }
        });
        jButtonImpExp.addMouseListener(new ButtonCursor());
        
        jScrollPaneTable.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneTable.setAutoscrolls(true);
        jScrollPaneTable.setOpaque(false);

        jTableInvoices.setFont(Constants.FONT_PLAIN);
        jTableInvoices.setForeground(Constants.FONT_COLOR);
        jTableInvoices.setModel(new NoEdiTableModel (
            new Object [][] { },
            new String [] {Constants.LANG.getString("Identifier"),Constants.LANG.getString("Date"),Constants.LANG.getString("Issuer")+" ("+Constants.LANG.getString("Id")+")",Constants.LANG.getString("Receiver")+" ("+Constants.LANG.getString("Id")+")",Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")",Constants.LANG.getString("Version"),"", "", ""},
            new int [] {6,7,8}
        ));
        jTableInvoices.setGridColor(Constants.SELECTION_COLOR);
        jTableInvoices.getTableHeader().setReorderingAllowed(false);
        jTableInvoices.setColumnSelectionAllowed(false);
        jTableInvoices.setRowSelectionAllowed(true);
        
        /** Scroll Pane containing the Table is made transparent (override scroll's viewport) */
        PicturedViewport vp = new PicturedViewport(imgWhite,0.80f);
        vp.setView(jTableInvoices);
        jScrollPaneTable.setViewport(vp);
        
        jTableInvoices.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableInvoices.getColumnModel().getColumn(0).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(0).setPreferredWidth(81);
        jTableInvoices.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableInvoices.getColumnModel().getColumn(1).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(1).setPreferredWidth(81);
        jTableInvoices.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableInvoices.getColumnModel().getColumn(2).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTableInvoices.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableInvoices.getColumnModel().getColumn(3).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTableInvoices.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableInvoices.getColumnModel().getColumn(4).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableInvoices.getColumnModel().getColumn(5).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(5).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableInvoices.getColumnModel().getColumn(6).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(6).setPreferredWidth(20);
        jTableInvoices.getColumnModel().getColumn(6).setCellEditor(new ButtonSeeFACeSentResultEditor());
        jTableInvoices.getColumnModel().getColumn(6).setCellRenderer(new ButtonSeeFACeSentResultRenderer());
        jTableInvoices.getColumnModel().getColumn(7).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(7).setPreferredWidth(20);
        jTableInvoices.getColumnModel().getColumn(7).setCellEditor(new ButtonSeeHTMLEditor());
        jTableInvoices.getColumnModel().getColumn(7).setCellRenderer(new ButtonSeeHTMLRenderer());
        jTableInvoices.getColumnModel().getColumn(8).setResizable(false);
        jTableInvoices.getColumnModel().getColumn(8).setPreferredWidth(20);
        jTableInvoices.getColumnModel().getColumn(8).setCellEditor(new ButtonDeleteEditor());
        jTableInvoices.getColumnModel().getColumn(8).setCellRenderer(new ButtonDeleteRenderer());

        for (int i = 0; i < jTableInvoices.getColumnCount(); i++)
            jTableInvoices.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());

        jLabelProgress.setText(Constants.LANG.getString("Progress")); 

        org.jdesktop.layout.GroupLayout jPanel14Layout = new org.jdesktop.layout.GroupLayout(jPanelProgress);
        jPanelProgress.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                	.add(jSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 985, 985)
                    .add(jPanel14Layout.createSequentialGroup()
                        .add(jProgressBar,100,100,100)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabelProgress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 795, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel14Layout.createSequentialGroup()
                        	.add(jButtonHelp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        	.add(jButtonExit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(12, 12, 12)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel14Layout.createSequentialGroup()
            	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 2, 2)
                .add(jSeparator, 5, 5, 5)
                .add(1, 1, 1)
                .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelProgress)
                    .add(jProgressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButtonHelp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButtonExit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );
        
        File f = new File(Constants.CONFIG_PROP.getProperty("logoPath"));
        
        ImageIcon imgIcon = ImageUtil.createScaledImageIcon(f, jLabelLogo, 215, 95);
        jLabelLogo.setIcon(imgIcon);
        jLabelLogo.setHorizontalAlignment(JLabel.CENTER);
        jLabelLogo.setVerticalAlignment(JLabel.CENTER);
        
        jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/images/button_help2.jpg"))); 
        jButtonHelp.setBorderPainted(false);
        jButtonHelp.setContentAreaFilled(false);
        jButtonHelp.setToolTipText(Constants.LANG.getString("Help"));
        jButtonHelp.addMouseListener(new ButtonCursor());
        jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButtonHelpActionPerformed();
            }
        });
        
        jButtonExit.setIcon(new ImageIcon(getClass().getResource("/images/button_exit.jpg"))); 
        jButtonExit.setBorderPainted(false);
        jButtonExit.setContentAreaFilled(false);
        jButtonExit.setToolTipText(Constants.LANG.getString("Exit"));
        jButtonExit.addMouseListener(new ButtonCursor());
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	appClose();
            }
        });
        
        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanelProgress, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(18, 18, 18)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jScrollPaneTree, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 215, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jPanel1Layout.createSequentialGroup()
                                    	.add(jButtonGenerate)
                                    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 3, 3)
                                        .add(jComboBoxGenVersion))
                                    .add(jButtonReceive)
                                    .add(jButtonSearch)
                                    .add(jButtonConfig)
                                    .add(jButtonHistorical)
                                    .add(jButtonImpExp)
                                    .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, 220)
                                    .add(org.jdesktop.layout.GroupLayout.CENTER, jLabelLogo))
                                .add(25, 25, 25)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jTabbedPaneInvoiceActions, 0, 0, Short.MAX_VALUE)
                            .add(jScrollPaneTable)
                            .add(jPanelInvoiceFilter, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 1, Short.MAX_VALUE))
                    .add(jLabelTopBar))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelTopBar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(13, 13, 13)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jPanelInvoiceFilter, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jScrollPaneTable, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 315, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTabbedPaneInvoiceActions, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jScrollPaneTree, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 185, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(5, 5, 5)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                    .add(jComboBoxGenVersion, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jButtonGenerate))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonReceive)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonSearch)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jSeparator2, 5, 5, 5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonImpExp)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonHistorical)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButtonConfig)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 15, 15)
                                .add(jLabelLogo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(5,5,5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelProgress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 735, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }
    
    private void generateAction() {
    	JFrame j = null;
    	if (jComboBoxGenVersion.getSelectedItem().toString().equals(Constants.VERSION321))
    		j = GenerateInvoice321Window.getInstance(mw);
    	if (jComboBoxGenVersion.getSelectedItem().toString().equals(Constants.VERSION32))
    		j = GenerateInvoice32Window.getInstance(mw);
    	if (j != null) {
    		j.setVisible(true);       
    		if (j.getState() == JFrame.ICONIFIED)
    			j.setState(JFrame.NORMAL);
        }
    }

    private void receiveAction() {
    	JFrame j = ReceiveInvoiceWindow.getInstance();
    	j.setVisible(true);
    	if (j.getState() == JFrame.ICONIFIED)
    		j.setState(JFrame.NORMAL);
    }
    
    private void historicalAction() {
    	JFrame j = HistoricalWindow.getInstance();
    	j.setVisible(true);
    	if (j.getState() == JFrame.ICONIFIED)
    		j.setState(JFrame.NORMAL);
    }
    
    private void advancedSearchAction() {
    	JFrame j = new AdvancedSearchWindow();
    	j.setVisible(true);
    	if (j.getState() == JFrame.ICONIFIED)
    		j.setState(JFrame.NORMAL);
    }
    
    private void configurationAction() {
        JFrame j = ConfigurationWindow.getInstance();
        
        if (j == null)
        	return;
        
        j.addPropertyChangeListener("logoPath", new PropertyChangeListener() {
        	public void propertyChange(PropertyChangeEvent evt) {
        		reloadImage();
        	}
        });
        j.setVisible(true);
        if (j.getState() == JFrame.ICONIFIED)
        	j.setState(JFrame.NORMAL);
    }

    private void impExpAction() {
    	InputSelectImportExportOption isieo = new InputSelectImportExportOption(this, true);
    	isieo.setVisible(true);
    	isieo.setVisible(false);
    	switch(isieo.getSelectedOption()){
    		case 0:
    			ImportInvoicesDialog iid = new ImportInvoicesDialog((JFrame)this.getOwner(), true);
    			iid.setVisible(true);
    			break;
    		case 1:
    			ImportDataBaseDialog idbd = new ImportDataBaseDialog((JFrame)this.getOwner(), true);
    			idbd.setVisible(true);
    			break;
    		case 2:
    			ExportInvoicesDialog eid = new ExportInvoicesDialog((JFrame)this.getOwner(), true);
    			eid.setVisible(true);
    			break;
    		case 3:
    			ExportDataBaseDialog edbd = new ExportDataBaseDialog((JFrame)this.getOwner(), true);
				edbd.setVisible(true);
				break;
    		case -1:
    			break;
			
		}
	}
    
    private void appHook() {
    	if (uncontrolledExit) {
    		Thread finalTh = new Thread(new Runnable() {public void run() {
    			FacturaeManager.getInstance().commitAndClose();
    		}});
    		finalTh.start();
    		try { 
    			finalTh.join(5000); // JVM waits for DB commit or 5000 ms max. before exit 
    		} catch (InterruptedException e) {}
    		finally {
    			logger.error("Error by uncontrolled exit");
    			System.exit(0);
    		}
    	}
    }
    
    public void loadData() {
    	String query = null;
		SQLQuery s = null;
		Object[] comboArray = null;
		Object[] comboArray2 = null;
    	try {
    		// Loading items from data base
    		query = "SELECT DISTINCT DESCRIPTION FROM ITEM";
    		s = FacturaeManager.getInstance().executeQuery(query);
    		InitialWindow.getInstance().refreshProgressBar(75);
    		List<?> ls = s.list();
    		if (ls != null && ls.size() > 0) {
    			int numRes = ls.size();
    			comboArray = new String[numRes+1];
    			comboArray[0] = Constants.LANG.getString("ALL"); 
    			for (int i = 0; i < numRes; i++)
    				comboArray[i+1] = ls.get(i).toString();
    			jComboBoxFiltItem.setModel(new DefaultComboBoxModel(comboArray));
    		} else {
    			comboArray = new String[1];
    			comboArray[0] = Constants.LANG.getString("ALL"); 
    			jComboBoxFiltItem.setModel(new DefaultComboBoxModel(comboArray));
    		}
    	} catch (JDBCConnectionException e) { 
    		// Data base is in use by another process
    		new GUIUtils().showErrorDataBaseBusy(null);
    		InitialWindow.getInstance().setVisible(false);
    		InitialWindow.getInstance().dispose();
    		uncontrolledExit = false;
    		System.exit(0);
    	}
        
        // ONLY one per Tax Identification
        query = "SELECT * FROM INDIVIDUAL,PARTY,TAX_IDENTIFICATION WHERE PARTY.PARTY_ID = INDIVIDUAL.PARTY_ID AND TAX_IDENTIFICATION.TAX_ID_NUMBER = PARTY.TAX_IDENTIFICATION AND INDIVIDUAL.PARTY_ID IN (SELECT MAX(PARTY_ID) FROM PARTY,INDIVIDUAL WHERE PARTY.PARTY_ID = INDIVIDUAL.PARTY_ID GROUP BY TAX_IDENTIFICATION) ORDER BY FIRST_SURNAME,SECOND_SURNAME,NAME";
        
        s = FacturaeManager.getInstance().executeQuery(query);
        List<?> ls = s.list();
        
        // ONLY one per Tax Identification
        query = "SELECT * FROM LEGAL_ENTITY,PARTY,TAX_IDENTIFICATION WHERE PARTY.PARTY_ID = LEGAL_ENTITY.PARTY_ID AND TAX_IDENTIFICATION.TAX_ID_NUMBER = PARTY.TAX_IDENTIFICATION AND LEGAL_ENTITY.PARTY_ID IN (SELECT MAX(PARTY_ID) FROM PARTY,LEGAL_ENTITY WHERE PARTY.PARTY_ID = LEGAL_ENTITY.PARTY_ID GROUP BY TAX_IDENTIFICATION) ORDER BY CORPORATE_NAME";
        
        InitialWindow.getInstance().refreshProgressBar(80);
        
        SQLQuery s2 = FacturaeManager.getInstance().executeQuery(query);
        List<?> ls2 = s2.list();
        int count = 0, count2 = 0; 
        if (ls != null && ls.size() > 0)
        	count = ls.size();
        if (ls2 != null && ls2.size() > 0)
        	count2 = ls2.size();
        if (count + count2 > 0) {
        	comboArray = new Object[count+count2+3];
        	comboArray[0] = Constants.LANG.getString("ALL");
        	comboArray[1] = Constants.LANG.getString("INDIVIDUAL");
        	for (int i = 0; i < count; i++)
        		comboArray[i+2] = new IndividualRow((Object[])ls.get(i));
        	comboArray[count+2] = Constants.LANG.getString("LEGALENTITY");
        	for (int i = 0; i < count2; i++)
        		comboArray[count+3+i] = new LegalEntityRow((Object[])ls2.get(i));	
        	jComboBoxFiltIssuer.setModel(new DefaultComboBoxModel(comboArray));
        	jComboBoxFiltReceiver.setModel(new DefaultComboBoxModel(comboArray));
        } else {
        	comboArray2 = new Object[3];
        	comboArray2[0] = Constants.LANG.getString("ALL");
        	comboArray2[1] = Constants.LANG.getString("INDIVIDUAL");
        	comboArray2[2] = Constants.LANG.getString("LEGALENTITY");
        	jComboBoxFiltIssuer.setModel(new DefaultComboBoxModel(comboArray2));
        	jComboBoxFiltReceiver.setModel(new DefaultComboBoxModel(comboArray2));
        }
        
        InitialWindow.getInstance().refreshProgressBar(90);
        
        // If signed series are equals to corrected series, it changes
        String series = Constants.CONFIG_PROP.getProperty("CorrectedSeries");
        String coSeries = Constants.CONFIG_PROP.getProperty("series");
        if (series == null &&  coSeries == null || series != null && series.equals(coSeries))
        	Constants.CONFIG_PROP.setProperty("coSeries", Constants.LANG.getString("CorrectiveSerie"));

        // If operation types are not defined yet (because DB data is empty)
        try {
			FacturaeManager.getInstance().setOperationTypes();
        } catch (DatabaseOperationException e) {
			// The user is not informed about action not saved event. An error log has been created previously.
		}
    }
    
    private void filterInvoices() {

    	Object issuer = jComboBoxFiltIssuer.getSelectedItem();
    	Object receiver = jComboBoxFiltReceiver.getSelectedItem();
    	Object item = jComboBoxFiltItem.getSelectedItem();
	
    	String queryFacturae = "";
    	String queryFacturae1 = ", INDIVIDUAL";
    	String queryFacturae2 = ", LEGAL_ENTITY";
    	
    	String issuerCondition = "",receiverCondition = "",itemCondition = "",datesCondition = "";
    	int lestart = -1;
    	int selectedIndex = -1;
    	boolean individualUsed = false;
    	boolean legalentityUsed = false;
    	
    	// ISSUER
    	lestart = ((ComboBoxRenderer)jComboBoxFiltIssuer.getRenderer()).getLeStart();
    	selectedIndex = jComboBoxFiltIssuer.getSelectedIndex();
    	// If the comboBox is not set to "ALL" the search is restricted in this field.
    	if (issuer != null && !issuer.toString().equals(Constants.LANG.getString("ALL"))) {
    		if (issuer.toString().equals(Constants.LANG.getString("LEGALENTITY"))){
    			issuerCondition = " AND FACTURAE.PARTY_ID_SELLER = LEGAL_ENTITY.PARTY_ID";
    			legalentityUsed = true;
    		}
    		else if (issuer.toString().equals(Constants.LANG.getString("INDIVIDUAL"))) {
    			issuerCondition = " AND FACTURAE.PARTY_ID_SELLER = INDIVIDUAL.PARTY_ID";
    			individualUsed = true;
    		}
    		else if (selectedIndex > lestart) {
    			issuerCondition = " AND FACTURAE.PARTY_ID_SELLER IN (SELECT PARTY_ID FROM PARTY WHERE TAX_IDENTIFICATION IN (SELECT TAX_IDENTIFICATION FROM PARTY WHERE PARTY_ID = '" + ((LegalEntityRow)issuer).attributes[0].toString() + "'))";
    		}
    		else{
    			issuerCondition = " AND FACTURAE.PARTY_ID_SELLER IN (SELECT PARTY_ID FROM PARTY WHERE TAX_IDENTIFICATION IN (SELECT TAX_IDENTIFICATION FROM PARTY WHERE PARTY_ID = '" + ((IndividualRow)issuer).attributes[0].toString() + "'))";
    		}
    	}
    	
    	// RECEIVER
    	lestart = ((ComboBoxRenderer)jComboBoxFiltReceiver.getRenderer()).getLeStart();
    	selectedIndex = jComboBoxFiltReceiver.getSelectedIndex();
    	// If the comboBox is not set to "ALL" the search is restricted in this field.
    	if (receiver != null && !receiver.toString().equals(Constants.LANG.getString("ALL"))) {
    		if (receiver.toString().equals(Constants.LANG.getString("LEGALENTITY"))) {
    			receiverCondition = " AND FACTURAE.PARTY_ID_BUYER = LEGAL_ENTITY.PARTY_ID";
    			legalentityUsed = true;
    		}
    		else if (receiver.toString().equals(Constants.LANG.getString("INDIVIDUAL"))) {
    			receiverCondition = " AND FACTURAE.PARTY_ID_BUYER = INDIVIDUAL.PARTY_ID";
    			individualUsed = true;
    		}
    		else if (selectedIndex > lestart) {
    			receiverCondition = " AND FACTURAE.PARTY_ID_BUYER IN (SELECT PARTY_ID FROM PARTY WHERE TAX_IDENTIFICATION IN (SELECT TAX_IDENTIFICATION FROM PARTY WHERE PARTY_ID = '" + ((LegalEntityRow)receiver).attributes[0].toString() + "'))";
    		}
    		else
    			receiverCondition = " AND FACTURAE.PARTY_ID_BUYER IN (SELECT PARTY_ID FROM PARTY WHERE TAX_IDENTIFICATION IN (SELECT TAX_IDENTIFICATION FROM PARTY WHERE PARTY_ID = '" + ((IndividualRow)receiver).attributes[0].toString() + "'))";
    	}
    	
    	if (!individualUsed)
    		queryFacturae1 = "";
    	if (!legalentityUsed)
    		queryFacturae2 = "";
    	
    	// ITEM
    	// If the comboBox is not set to "ALL" the search is restricted in this field.
    	if (item != null && !item.toString().equals(Constants.LANG.getString("ALL")))
    		itemCondition = " ITEM_ID IN (SELECT ITEM_ID FROM ITEM WHERE DESCRIPTION =  '" + item + "') AND";

    	// DATES
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	if (jRadioButtonFiltDate.isSelected())
    		datesCondition = " AND ISSUE_DATE >= '"+ sdf.format(jCalendarComboBoxFiltFrom.getCalendar().getTime()) +"' AND ISSUE_DATE <= '"+ sdf.format(jCalendarComboBoxFiltTo.getCalendar().getTime())+"'";
    	
    	String queryInvoice = "SELECT INVOICE_ID FROM INVOICE_ITEM WHERE"+itemCondition+" INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE STATE = '" + invoiceState + "' AND CLASS = '"+ invoiceClass +"'"+datesCondition+")";
    	
    	queryFacturae = "SELECT FACTURAE_ID FROM FACTURAE"+queryFacturae1+queryFacturae2+" WHERE FACTURAE_ID IS NOT NULL";
    	
    	SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT FACTURAE_ID, INVOICE_ID FROM FACTURAE_INVOICES WHERE FACTURAE_ID IN ("+queryFacturae+issuerCondition+receiverCondition+") AND INVOICE_ID IN ("+queryInvoice+")");
        List<?> ls = s.list();
        
    	int numRows = jTableInvoices.getModel().getRowCount();
    	for (int i = numRows-1; i > -1; i--){
    		((NoEdiTableModel)jTableInvoices.getModel()).removeRow(i);
    	}
    	
    	refreshProgressBar(20);
    	
    	if (ls != null && ls.size() > 0) {
    		numRows = ls.size();
    		for (int i = 0; i < numRows; i++) {
				Object[] oArray = new Object[9];
				SQLQuery sFacturae = FacturaeManager.getInstance().executeQuery("SELECT PARTY_ID_SELLER, PARTY_ID_BUYER FROM FACTURAE WHERE FACTURAE_ID = '"+((Object[])ls.get(i))[0]+"'");
				List<?> lsFacturae = sFacturae.list();
				SQLQuery sInvoice = FacturaeManager.getInstance().executeQuery("SELECT SERIES_CODE, NUMBER, ISSUE_DATE, TOTAL_EXECUTABLE, VERSION FROM INVOICE WHERE INVOICE_ID = '"+((Object[])ls.get(i))[1]+"'");
				List<?> lsInvoice = sInvoice.list();
				
    			String registerCode = "";
    			if(invoiceState == 2) {
    				String sQuery = "SELECT REGISTER_CODE FROM FACE_SENT_RESULT WHERE NUMBER = '"+((Object[])lsInvoice.get(0))[1];
    				if( ((Object[])lsInvoice.get(0))[0] != null ) {
						sQuery += "' AND SERIES_CODE = '"+((Object[])lsInvoice.get(0))[0]+"'";
    				} else {
						sQuery += "' AND (SERIES_CODE is null OR SERIES_CODE = '')";
    				}
    				sQuery += " ORDER BY DATE DESC";
    				SQLQuery sFACeSent = FacturaeManager.getInstance().executeQuery(sQuery);
        			List<?> lsFACeSent = sFACeSent.list();
        			if(lsFACeSent!=null && lsFACeSent.size()>0) {
        				if(lsFACeSent.get(0)!=null && lsFACeSent.get(0).toString().length()>0)
        					registerCode = lsFACeSent.get(0).toString();            				
        			}
    			}

				oArray[0] = ((Object[])lsInvoice.get(0))[0];
				oArray[1] = ((Object[])lsInvoice.get(0))[1];
				oArray[2] = ((Object[])lsInvoice.get(0))[2];
				oArray[3] = ((Object[])lsFacturae.get(0))[0];
				oArray[4] = ((Object[])lsFacturae.get(0))[1];
				oArray[5] = ((Object[])lsInvoice.get(0))[3];
				oArray[6] = ((Object[])lsInvoice.get(0))[4];
				oArray[7] = ((Object[])ls.get(i))[0];
				oArray[8] = registerCode;
        		addTableRow(oArray);
        		mw.refreshProgressBar( (int)(i / (numRows * 1.) * 80) + 20 );
    		}
        }	
    	mw.refreshProgressBar(100);
    }
    
    /**
     * Refresh jTable data and combos depending on invoice type selection in jTree
     */
    public void valueChanged() {
        jComboBoxFiltIssuer.setSelectedIndex(0);
        jComboBoxFiltItem.setSelectedIndex(0);
        jComboBoxFiltReceiver.setSelectedIndex(0);
        jRadioButtonFiltDate.setSelected(false);
        jPanelDate.setVisible(false);
    	
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTreeInvoices.getLastSelectedPathComponent();
        if (node == null)
            return;
        int count = jTabbedPaneInvoiceActions.getTabCount();
        String nodeName = node.getUserObject().toString();
        for (int i = 0; i < count; i++) {
        	if (nodeName.equals(jTabbedPaneInvoiceActions.getTitleAt(i))) {
        		jTabbedPaneInvoiceActions.setSelectedIndex(i);
        		break;
        	}
        }
        
        refreshProgressBar(5);
        
        if (node.getParent()!= null)
        	invoiceState = node.getParent().getIndex(node);
        invoiceClass = node.getLevel()-1;
        
        jTableInvoices.setVisible(false); // May not update graphics until load process ends
        try {
        	// The table content is removed
        	int numRows = ((NoEdiTableModel)jTableInvoices.getModel()).getRowCount();
        	for (int i = numRows; i > 0; i--)
        		((NoEdiTableModel)jTableInvoices.getModel()).removeRow(i - 1);

        	refreshProgressBar(10);

        	// Loading Invoices Table Data
        	SQLQuery s = null;
        	if (String.valueOf(invoiceState) != null && String.valueOf(invoiceState).equals(String.valueOf((InvoiceStatusType.R.ordinal()))))
        		s = FacturaeManager.getInstance().executeQuery(Constants.QUERY4_BIS.replace("$1", String.valueOf(invoiceState)));
        	else
        		s = FacturaeManager.getInstance().executeQuery(Constants.QUERY4.replace("$1", String.valueOf(invoiceState)).replace("$2", String.valueOf(invoiceClass)));
        	List<?> ls = s.list();
        	if (ls != null && ls.size() > 0) {
        		int numResults = ls.size();
        		for (int i = 0; i < numResults; i++) {
        			refreshProgressBar( (int)( (i / (numResults * 1.) ) * 90 + 10) );
        			
        			SQLQuery sFacturae = FacturaeManager.getInstance().executeQuery("SELECT PARTY_ID_SELLER, PARTY_ID_BUYER FROM FACTURAE WHERE FACTURAE_ID = '"+((Object[])ls.get(i))[0]+"'");
        			List<?> lsFacturae = sFacturae.list();
        			SQLQuery sInvoice = FacturaeManager.getInstance().executeQuery("SELECT SERIES_CODE, NUMBER, ISSUE_DATE, TOTAL_EXECUTABLE, VERSION FROM INVOICE WHERE INVOICE_ID = '"+((Object[])ls.get(i))[1]+"'");
        			List<?> lsInvoice = sInvoice.list();

        			String registerCode = "";
        			if(invoiceState == 2) {
        				String sQuery = "SELECT REGISTER_CODE FROM FACE_SENT_RESULT WHERE NUMBER = '"+((Object[])lsInvoice.get(0))[1];
        				if( ((Object[])lsInvoice.get(0))[0] != null ) {
    						sQuery += "' AND SERIES_CODE = '"+((Object[])lsInvoice.get(0))[0]+"'";
	    				} else {
    						sQuery += "' AND (SERIES_CODE is null OR SERIES_CODE = '')";
	    				}
        				sQuery += " ORDER BY DATE DESC";
        				SQLQuery sFACeSent = FacturaeManager.getInstance().executeQuery(sQuery);
            			List<?> lsFACeSent = sFACeSent.list();
            			if(lsFACeSent!=null && lsFACeSent.size()>0) {
            				if(lsFACeSent.get(0)!=null && lsFACeSent.get(0).toString().length()>0)
            					registerCode = lsFACeSent.get(0).toString();            				
            			}
        			}
        			
        			Object[] oArray = new Object[9];
        			oArray[0] = ((Object[])lsInvoice.get(0))[0];
        			oArray[1] = ((Object[])lsInvoice.get(0))[1];
        			oArray[2] = ((Object[])lsInvoice.get(0))[2];
        			oArray[3] = ((Object[])lsFacturae.get(0))[0];
        			oArray[4] = ((Object[])lsFacturae.get(0))[1];
        			oArray[5] = ((Object[])lsInvoice.get(0))[3];
        			oArray[6] = ((Object[])lsInvoice.get(0))[4];
        			oArray[7] = ((Object[])ls.get(i))[0];
        			oArray[8] = registerCode;
        			addTableRow(oArray);
        		}
        	}
        } finally {
        	jTableInvoices.setVisible(true);
        	refreshProgressBar(100);
        }
    }
    
    private void addTableRow(Object[] oArray) {
    	if (oArray == null)
    		return;
    	
    	String[] sArray = new String[9];
    	if (oArray[0] != null)
    		sArray[0] = oArray[0].toString();
    	else
    		sArray[0] = "";
    	
    	if (oArray[1] != null)
    		sArray[0] = sArray[0] + oArray[1];
    	
    	sArray[7] = sArray[0];
    	sArray[8] = sArray[0];
    	
		if (oArray[2] != null)
			sArray[1] = Constants.DATE_FORMAT.format(oArray[2]);
		
		String query2 = null;
		SQLQuery s2 = null;
		if (oArray[3] != null) {
			//Seller
			query2 = "SELECT NAME, FIRST_SURNAME, SECOND_SURNAME FROM INDIVIDUAL WHERE PARTY_ID = '"+ oArray[3].toString() +"'";
			s2 = FacturaeManager.getInstance().executeQuery(query2);
			List<?> ls2 = s2.list();
			if (ls2 != null && ls2.size() > 0){
				Object[] oArray2 = (Object[])ls2.get(0);
				if (oArray2[2] != null && !oArray2[2].toString().trim().equals(""))
					sArray[2] =  oArray2[1].toString() + " " + oArray2[2].toString() + ", " + oArray2[0].toString();
				else
					sArray[2] =  oArray2[1].toString() + ", " + oArray2[0].toString();
				sArray[2] = sArray[2] + " (" + oArray[3].toString() + ")"; 
			}
			else {
				query2 = "SELECT CORPORATE_NAME FROM LEGAL_ENTITY WHERE PARTY_ID = '"+ oArray[3].toString() +"'";
				s2 = FacturaeManager.getInstance().executeQuery(query2);
				ls2 = s2.list();
				if (ls2 != null && ls2.size() > 0)
					sArray[2] = ls2.get(0).toString() + " (" + oArray[3].toString() + ")";
			}
		}
		if (oArray[4] != null) {
			//Buyer
			query2 = "SELECT NAME, FIRST_SURNAME, SECOND_SURNAME FROM INDIVIDUAL WHERE PARTY_ID = '"+ oArray[4].toString() +"'";
			s2 = FacturaeManager.getInstance().executeQuery(query2);
			List<?> ls2 = s2.list();
			if (ls2 != null && ls2.size() > 0) {
				Object[] oArray2 = (Object[])ls2.get(0);
				if (oArray2[2] != null && !oArray2[2].toString().trim().equals(""))
					sArray[3] =  oArray2[1].toString() + " " + oArray2[2].toString() + ", " + oArray2[0].toString();
				else
					sArray[3] =  oArray2[1].toString() + ", " + oArray2[0].toString();
				sArray[3] = sArray[3] + " (" + oArray[4].toString() + ")"; 
			}
			else {
				query2 = "SELECT CORPORATE_NAME FROM LEGAL_ENTITY WHERE PARTY_ID = '"+ oArray[4].toString() +"'";
				s2 = FacturaeManager.getInstance().executeQuery(query2);
				ls2 = s2.list();
				if (ls2 != null && ls2.size() > 0)
					sArray[3] = ls2.get(0).toString();
				sArray[3] = sArray[3] + " (" + oArray[4].toString() + ")"; 
			}
		}
		if (oArray[5] != null)
			sArray[4] = oArray[5].toString();
		if (oArray[6] != null)
			sArray[5] = oArray[6].toString();
		if (oArray[8] != null && oArray[8].toString().length()>0)
			sArray[6] = oArray[8].toString();
		
		((NoEdiTableModel)jTableInvoices.getModel()).addRow(sArray);
    }
    
    private void reloadImage() {
    	//Reloading configuration properties
    	if (Constants.CONFIG_PROP == null){
    		try {
    			FileInputStream f = new FileInputStream(Constants.APP_PROP.getProperty("config_file"));
    			Constants.CONFIG_PROP = new Properties();
    			Constants.CONFIG_PROP.load(f);
    			f.close();
    		}
    		catch(Exception e) {
    			logger.error("An exception occurred when loading the properties of the configuration file: " + e.getMessage());
    		}
    	}
        
        File f = new File(Constants.CONFIG_PROP.getProperty("logoPath"));
        
        ImageIcon imgIcon = ImageUtil.createScaledImageIcon(f, jLabelLogo, 215, 95);
        jLabelLogo.setIcon(imgIcon);
        jLabelLogo.setHorizontalAlignment(JLabel.CENTER);
        jLabelLogo.setVerticalAlignment(JLabel.CENTER);
	}
    
    /**
     *  Sign the selected invoice
     *  @argument rectIn .- If true, the invoice is a Rectified invoice
     */
    public void signInvoice(final boolean rectIn) {
    	final int selRow = jTableInvoices.getSelectedRow();
		if (selRow == -1) { // No row selected
			logger.debug("No row selected");
			return;
		}
    	if (jTableInvoices.getModel().getValueAt(selRow, 0) == null) {
    		logger.debug("No invoice exist. Id may not be an empty field");
    		return;
    	}
    	
    	if (th == null || !th.isAlive()) {
    		Thread th2 = new Thread(new Runnable() { public void run() {
    			String id = jTableInvoices.getModel().getValueAt(selRow, 0).toString();
		    	String version = jTableInvoices.getModel().getValueAt(selRow, 5).toString().trim();
    			try {
    				t.putTransitionPanel(Constants.LANG.getString("SigningInvoice"));
    				try {
    					if (version != null && !"".equals(version)){
    						try{
    							id = ((InvoiceUtil) Class.forName(Constants.APP_PROP.getProperty("appUtilsPath")+"Invoice"+FacturaeUtil.getVersionConst(version)+"Util").newInstance()).signInvoice(id, rectIn, mw);
    						} catch (InstantiationException e) {
    							new GUIUtils().showErrorSignProccess("InstantiationException:" + e.getMessage());
    						} catch (IllegalAccessException e) {
    							new GUIUtils().showErrorSignProccess("IllegalAccessException:" + e.getMessage());
    						} catch (ClassNotFoundException e) {
    							new GUIUtils().showErrorSignProccess("ClassNotFoundException:" + e.getMessage());
    						}
    					} else {
    						logger.error("Invoice version has not been caught");
    						return;
    					}
    				} catch (SignInvoiceException e) {
    					new GUIUtils().showErrorSignProccess(e.getMessage()+ (e.getCause() != null? " "+e.getCause():""));
    				}
    			} catch (DatabaseOperationException e) {
    				mw.refreshProgressBar(0);
    				new GUIUtils().showErrorSignProccess(e.getMessage());
				} catch (Exception e) {
         			mw.refreshProgressBar(0);
         			Constants.DIALOG.showErrorGeneratingInvoice(e.getMessage());
         		} finally {
    				t.removeTransitionPanel();
    				if (id == null || "".equals(id)) // Sign was canceled
    					return;

    				// Invoices table refresh
    				jTreeInvoices.setSelectionRow(-1);
    				if (rectIn) {
    					mw.getJTreeInvoices().expandRow(5);
    					jTreeInvoices.setSelectionRow(7);
    				} else
    					jTreeInvoices.setSelectionRow(2);
    				// Wait for invoices refresh
					try {
						th.join();
					} catch (Exception e) {}
					// Set the new invoice selected
					jTableInvoices.clearSelection();
					if (jTableInvoices.getRowCount() != 0 && id != null)
						for (int i = 0; i < jTableInvoices.getRowCount(); ++i) {
							if (id.equals(jTableInvoices.getModel().getValueAt(i, 0))) {
								jTableInvoices.setRowSelectionInterval(i, i);
								break;
							}
						}
					
    				// Filter combos refresh
    				loadData();
    			}
    		}});
    		th2.start();
    	}
    }
    
  /**
   * Charges an invoice stored in a File to screen
   * 
   * @argument openType.- If it is:
   * 					true  .- See invoice  (all disabled, no dialog)
   * 					false .- Corrective Invoice (nothing disabled, a dialog is shown)
   * 					null  .- Edit invoice (nothing disabled, no dialog)
   */
    public void chargeInvoice(final Boolean openType) {

    	final int selRow = jTableInvoices.getSelectedRow();
		if (selRow == -1) { // No row selected
			logger.debug("No row selected");
			return;
		}
    	if (jTableInvoices.getModel().getValueAt(selRow, 0) == null) {
    		logger.debug("No invoice exist. Id may not be an empty field");
    		return;
    	}
    	
    	if (th == null || !th.isAlive()) {
    		th = new Thread(new Runnable() { public void run() {
    			try {
    				t.putTransitionPanel(Constants.LANG.getString("LoadingInvoice"));
    				
    		    	String id = jTableInvoices.getModel().getValueAt(selRow, 0).toString();
    		    	String version = jTableInvoices.getModel().getValueAt(selRow, 5).toString().trim();
    		    	try {
    		    		if (version != null && !"".equals(version)){
    		    			try{
    							((InvoiceUtil) Class.forName(Constants.APP_PROP.getProperty("appUtilsPath")+"Invoice"+FacturaeUtil.getVersionConst(version)+"Util").newInstance()).chargeInvoice(openType, id);
    						} catch (InstantiationException e) {
    							new GUIUtils().showErrorChargeInvoice("InstantiationException:" + e.getMessage());
    						} catch (IllegalAccessException e) {
    							new GUIUtils().showErrorChargeInvoice("IllegalAccessException:" + e.getMessage());
    						} catch (ClassNotFoundException e) {
    							new GUIUtils().showErrorChargeInvoice("ClassNotFoundException:" + e.getMessage());
    						}
    		    		}
    		    		else{
    		    			logger.error("Invoice version has not been caught");
    		    			return;
    		    		}
    		    	} catch (UnmarshalException e) {
    		    		new GUIUtils().showErrorChargeInvoice(e.getMessage());
    		    	}
    			} catch (Exception e){
    				logger.error("General exception charging invoice data:" + e.getMessage(), e);
    			} finally {
    				t.removeTransitionPanel();
    			}
    		}});
    		th.start();
    	}
    }
    
    /**
     * Shows a dialog containing Rectified invoice corrections
     */
    public void seeCorrections() {
    	final int selRow = jTableInvoices.getSelectedRow();
		if (selRow == -1) { // No row selected
			logger.debug("No row selected");
			return;
		}
    	if (jTableInvoices.getModel().getValueAt(selRow, 0) == null) {
    		logger.debug("No invoice exist. Id may not be an empty field");
    		return;
    	}
    	
    	if (th == null || !th.isAlive()) {
    		th = new Thread(new Runnable() { public void run() {
    			try {
    				t.putTransitionPanel(Constants.LANG.getString("LoadingInvoice"));
    				
    		    	String id = jTableInvoices.getModel().getValueAt(selRow, 0).toString();
    		    	String version = jTableInvoices.getModel().getValueAt(selRow, 5).toString().trim();
    		    	if (version != null && !"".equals(version)){
    		    		try{
							((InvoiceUtil) Class.forName(Constants.APP_PROP.getProperty("appUtilsPath")+"Invoice"+FacturaeUtil.getVersionConst(version)+"Util").newInstance()).seeCorrections(id);
						} catch (InstantiationException e) {
							new GUIUtils().showErrorSeeCorrections("InstantiationException:" + e.getMessage());
						} catch (IllegalAccessException e) {
							new GUIUtils().showErrorSeeCorrections("IllegalAccessException:" + e.getMessage());
						} catch (ClassNotFoundException e) {
							new GUIUtils().showErrorSeeCorrections("ClassNotFoundException:" + e.getMessage());
						}
    		    	} else {
    		    		logger.error("Invoice version has not been caught");
					}
    			} finally {
    				t.removeTransitionPanel();
    			}
    		}});
    		th.start();
    	}
    }
    
    /**
     * Shows a dialog containing invoice s attachments
     */
    public void seeAttachment() {
    	int selRow = jTableInvoices.getSelectedRow();
		if (selRow == -1) { // No row selected
			logger.debug("No row selected");
			return;
		}
    	if (jTableInvoices.getModel().getValueAt(selRow, 0) == null) {
    		logger.debug("No invoice exist. Id may not be an empty field");
    		return;
    	}

    	String invoiceNumber = jTableInvoices.getModel().getValueAt(selRow, 0).toString();
    	String invoiceId = null;
		SQLQuery sqlQuery = FacturaeManager.getInstance().executeQuery("SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE + NUMBER,NUMBER) ='" + invoiceNumber + "'");
		List<?> lsInvoiceId = sqlQuery.list();
		if(lsInvoiceId != null && !lsInvoiceId.isEmpty()) {
			invoiceId = lsInvoiceId.get(0).toString();
		}
    	
    	ArrayList<Object[]> att = new ArrayList<Object[]>(); 
    	SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT EXTENSION,PATH,DESCRIPTION,INCLUDED FROM ATTACHMENT WHERE INVOICE = '" + invoiceNumber + "'" );
    	List<?> ls = s.list();
    	if (ls != null && ls.size() > 0)
    		for (int i = 0; i < ls.size(); ++i)
    			att.add((Object[])ls.get(i));
    	
    	JFrame j = AttachmentsWindow.getInstance(att, invoiceId);
		j.setVisible(true);    
    }
    
    /**
     * Open a platform s default browser with an invoice in XML format
     */
    public void seeXML () {
    	int selRow = jTableInvoices.getSelectedRow();
		if (selRow == -1) { // No row selected
			logger.debug("No row selected");
			return;
		}
    	if (jTableInvoices.getModel().getValueAt(selRow, 0) == null) {
    		logger.debug("No invoice exist. Id may not be an empty field");
    		return;
    	}

    	String id = jTableInvoices.getModel().getValueAt(selRow, 0).toString();
    	XMLFacturaeUtil.seeXML(id);
    }
    
    /**
     * Sends an invoice using SMTP configuration to an required e-mail
     */
    public void sendInvoice() {
    	int selRow = jTableInvoices.getSelectedRow();
		if (selRow == -1) { // No row selected
			logger.debug("No row selected");
			return;
		}
    	if (jTableInvoices.getModel().getValueAt(selRow, 0) == null) {
    		logger.debug("No invoice exist. Id may not be an empty field");
    		return;
    	}

    	final String id = jTableInvoices.getModel().getValueAt(selRow, 0).toString();
    	if (th == null || !th.isAlive()) {
    		Thread th2 = new Thread(new Runnable() { public void run() {
    			boolean result = false;
    			try {
    				t.putTransitionPanel(Constants.LANG.getString("SendingInvoice"));
    				try {
						Thread.sleep(500);
					} catch (InterruptedException e) {}
					String version = jTableInvoices.getModel().getValueAt(jTableInvoices.getSelectedRow(), 5).toString();
					try{
						result = ((InvoiceUtil) Class.forName(Constants.APP_PROP.getProperty("appUtilsPath")+"Invoice"+FacturaeUtil.getVersionConst(version)+"Util").newInstance()).sendInvoice(id, mw);
					} catch (InstantiationException e) {
						new GUIUtils().showErrorSend("InstantiationException:" + e.getMessage());
					} catch (IllegalAccessException e) {
						new GUIUtils().showErrorSend("IllegalAccessException:" + e.getMessage());
					} catch (ClassNotFoundException e) {
						new GUIUtils().showErrorSend("ClassNotFoundException:" + e.getMessage());
					}
    			} catch (Exception e) {
    				logger.error ("Unhandled exception during send process", e);
    				// The user is informed through internal InvoiceUtil mechanism
    			} finally {
    				t.removeTransitionPanel();
    				if (result) {
    					// Invoices table refresh
    					int[] selectedRows = jTreeInvoices.getSelectionRows();
    					boolean rectIn = false;
    					for (int i = 0; i < selectedRows.length; ++i) {
    						if (selectedRows[i] == 2)
    							break;
    						else if (selectedRows[i] == 7)
    							rectIn = true;
    					}
    					jTreeInvoices.setSelectionRow(-1);
    					if (rectIn) {
    						mw.getJTreeInvoices().expandRow(5);
    						jTreeInvoices.setSelectionRow(8);
    					} else
    						jTreeInvoices.setSelectionRow(3);
    					// Wait for invoices refresh
    					try {
    						th.join();
    					} catch (Exception e) {}
    					// Set the new invoice selected
    					mw.getJTableInvoices().clearSelection();
    					if (jTableInvoices.getRowCount() != 0 && id != null)
    						for (int i = 0; i < jTableInvoices.getRowCount(); ++i) {
    							if (id.equals(jTableInvoices.getModel().getValueAt(i, 0))) {
    								jTableInvoices.setRowSelectionInterval(i, i);
    								break;
    							}
    						}
    					
    					// Filter combos refresh
    					loadData();
    				}
    			}
    		}});
    		th2.start();
    	}
		if (mw != null)
			mw.refreshProgressBar(0);
    }
    
    /**
     * Updates the bar state for progress representation
     * @param val.- Rate for completed progress ([0, 100])
     */
    public void refreshProgressBar(int val) {
    	if (val < 0)
    		val = 0;
    	else if (val > 100)
    		val = 100;
    	jProgressBar.setValue(val);
    	if (val == 100){
    		try {
    			Thread.sleep(1000);;
			} catch (InterruptedException e) {
				logger.error(e.getMessage(),e);
			}
    		jProgressBar.setValue(0);
    	}
    }
    
    public JTree getJTreeInvoices() {
    	return jTreeInvoices;
    }
    public JTable getJTableInvoices() {
    	return jTableInvoices;
    }
    public Thread getActionThread() {
    	return th;
    } 
    public JComboBox getJComboBoxGenVersion() {
		return jComboBoxGenVersion;
	}
    public Transition getTransition(){
    	return t;
    }
    
    
    private class ButtonCursor extends MouseAdapter {
    	public void mouseEntered(java.awt.event.MouseEvent evt) {
            MainWindow.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            MainWindow.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
    }
    
    private class WindowClosing implements WindowListener {
		public void windowClosing(WindowEvent arg0) {
			appClose();
		}
		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}
    }
    
    private void appClose() {
    	int res = Constants.DIALOG.showCloseAppDialog();
		if (JOptionPane.YES_OPTION == res) {
			FacturaeManager.getInstance().commitAndClose();
			Window[] childs = mw.getOwnedWindows();
			for(int i = 0; i < childs.length; ++i) {
				Window child = childs[i];
				child.setVisible(false);
				child.dispose();
			}
			mw.setVisible(false);
			mw.dispose();
			Thread finalTh = new Thread(new Runnable() { public void run() {
	    		FinalWindow.getInstance();
			}});
			finalTh.start();
	    	uncontrolledExit = false;
		}
    }
    
    private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_MainWindow_"+Constants.LANG.getLocale().getLanguage()+".html");
    	if (helpFile == null) {
    		new GUIUtils().showErrorHelp();
	    	return;
    	}

    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
    }
    public void refreshInvoices() {
		if (th == null || !th.isAlive()) {
			th = new Thread(new Runnable() { public void run() {
				try {
					t.putTransitionPanel(Constants.LANG.getString("LoadingInvoices"));
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {}
					MainWindow.this.valueChanged();
				} finally {
					t.removeTransitionPanel();
				}
			}});
			th.start();
		}
    }

    
    // JPanel
    private JPanel mainPanel;
    private JPanel jPanelInvoiceFilter;
    private JPanel jPanelProgress;
    private JPanel jPanelDate;
    // JTabbedPane
    private InvoiceActionsPane jTabbedPaneInvoiceActions;
    // JLabel
    private JLabel jLabelTopBar;
    private JLabel jLabelFiltIssuer;
    private JLabel jLabelFiltReceiver;
    private JLabel jLabelFiltItem;
    private JLabel jLabelFiltTo;
    private JLabel jLabelLogo;
    private JLabel jLabelProgress;
    // JButton
    private JButton jButtonFilter;
    private JButton jButtonGenerate;
    private JButton jButtonReceive;
    private JButton jButtonConfig;
    private JButton jButtonSearch;
    private JButton jButtonHistorical;
    private JButton jButtonImpExp;
    private JButton jButtonHelp;
    private JButton jButtonExit;
    // JCalendarComboBox
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxFiltFrom;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxFiltTo;
    // JComboBox
    private JComboBox jComboBoxGenVersion;
    private JComboBox jComboBoxFiltIssuer;
    private JComboBox jComboBoxFiltReceiver;
    private JComboBox jComboBoxFiltItem;
    // JScrollPane
    private JScrollPane jScrollPaneTree;
    private JScrollPane jScrollPaneTable;
    // JTable
    private JTable jTableInvoices;
    // JTree
    private JTree jTreeInvoices;
    // JProgressBar
    private JProgressBar jProgressBar;
    // JSeparator
    private JSeparator jSeparator;
    private JSeparator jSeparator2;
    // JRadioButton
    private JRadioButton jRadioButtonFiltDate;
    // Images
    private Image imgBackground = null;
    private Image imgWhite = null;
    private Image imgLogoApp = null;
}