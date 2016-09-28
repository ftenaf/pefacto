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
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.ui.renderers.StateClassCellRenderer;
import es.mityc.appfacturae.ui.transitions.GifAnimatedIcon;
import es.mityc.appfacturae.ui.transitions.GifWaitIndicator;
import es.mityc.appfacturae.ui.transitions.WaitIndicator;
import es.mityc.appfacturae.utils.InvoiceUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.facturae.utils.UnmarshalException;

public class AdvancedSearchWindow extends javax.swing.JFrame {
    
	private static Log logger = LogFactory.getLog(AdvancedSearchWindow.class);
    private FadingCanvas fd = null;
    
    // Transition panel

    private WaitIndicator waiter = null;
    private JLabel jLabelTransition = null;
    private Thread waiterTh = null;
    private Thread th = null;


    /** Creates new form AdvancedSearchWindow */
    public AdvancedSearchWindow() {
    	
    	loadConfiguration();
    	
    	initComponents();
    	
    	this.setSize(1024,465);
        this.setResizable(false);
        if (imgLogoApp != null)
        	this.setIconImage(imgLogoApp);
        this.setLocationRelativeTo(null);
    }
    
    
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        
        BufferedImage imgTopBar2 = null;
    	try {
			imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
		} catch (IOException ioe) {
            Logger.getLogger(ReceiveInvoiceWindow.class.getName()).log(Level.SEVERE, null, ioe);
		}
    	jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
    	
        jLabelTopBar = new javax.swing.JLabel();
        jLabelTopBarMsg2 = new javax.swing.JLabel();
        jLabelTopBarMsg1 = new javax.swing.JLabel();
        jPanelResults = new javax.swing.JPanel();
        jScrollResults = new javax.swing.JScrollPane();
        jTableResults = new javax.swing.JTable();
        jButtonReturn = new javax.swing.JButton();
        jButtonHelp = new javax.swing.JButton();
        jButtonClean = new javax.swing.JButton();
        jButtonSearch = new javax.swing.JButton();
        jPanelItem = new javax.swing.JPanel();
        jLabelInvoiceNumber = new javax.swing.JLabel();
        jTextFieldSeries = new javax.swing.JTextField();
        jTextFieldId = new javax.swing.JTextField();
        jLabelInvoiceItem = new javax.swing.JLabel();
        jLabelInvoiceType = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox();
        jTextFieldItem = new javax.swing.JTextField();
        jLabelIssueFrom = new javax.swing.JLabel();
        jCalendarComboBoxIssueFrom = new de.wannawork.jcalendar.JCalendarComboBox();
        jLabelIssueTo = new javax.swing.JLabel();
        jCalendarComboBoxIssueTo = new de.wannawork.jcalendar.JCalendarComboBox();
        jLabelInvoicingFrom = new javax.swing.JLabel();
        jCalendarComboBoxInvoiFrom = new de.wannawork.jcalendar.JCalendarComboBox();
        jLabelInvoicingTo = new javax.swing.JLabel();
        jCalendarComboBoxInvoiTo = new de.wannawork.jcalendar.JCalendarComboBox();
        jLabelAmountFrom = new javax.swing.JLabel();
        jTextFieldAmountFrom = new javax.swing.JTextField();
        jLabelAmountTo = new javax.swing.JLabel();
        jTextFieldAmountTo = new javax.swing.JTextField();
        jRadioButtonIssueD = new javax.swing.JRadioButton();
        jRadioButtonInvoicingP = new javax.swing.JRadioButton();
        jLabelAmount = new javax.swing.JLabel();
        jPanelReceiver = new javax.swing.JPanel();
        jTabbedPaneReceiver = new javax.swing.JTabbedPane();
        jPanelRIndividual = new javax.swing.JPanel();
        jLabelRNIF = new javax.swing.JLabel();
        jLabelRName = new javax.swing.JLabel();
        jLabelR1Surname = new javax.swing.JLabel();
        jLabelR2Surname = new javax.swing.JLabel();
        jTextFieldRNIF = new javax.swing.JTextField();
        jTextFieldRName = new javax.swing.JTextField();
        jTextFieldR1Surname = new javax.swing.JTextField();
        jTextFieldR2Surname = new javax.swing.JTextField();
        jPanelRLegEntity = new javax.swing.JPanel();
        jLabelRCIF = new javax.swing.JLabel();
        jLabelRCorpName = new javax.swing.JLabel();
        jLabelRTradeName = new javax.swing.JLabel();
        jTextFieldRCIF = new javax.swing.JTextField();
        jTextFieldRCorpName = new javax.swing.JTextField();
        jTextFieldRTradeName = new javax.swing.JTextField();
        jPanelIssuer = new javax.swing.JPanel();
        jTabbedPaneIssuer = new javax.swing.JTabbedPane();
        jPanelIIndividual = new javax.swing.JPanel();
        jLabelINIF = new javax.swing.JLabel();
        jLabelIName = new javax.swing.JLabel();
        jLabelI1Surname = new javax.swing.JLabel();
        jLabelI2Surname = new javax.swing.JLabel();
        jTextFieldINIF = new javax.swing.JTextField();
        jTextFieldIName = new javax.swing.JTextField();
        jTextFieldI1Surname = new javax.swing.JTextField();
        jTextFieldI2Surname = new javax.swing.JTextField();
        jPanelILegEntity = new javax.swing.JPanel();
        jLabelICIF = new javax.swing.JLabel();
        jLabelICorpName = new javax.swing.JLabel();
        jLabelITradeName = new javax.swing.JLabel();
        jTextFieldICIF = new javax.swing.JTextField();
        jTextFieldICorpName = new javax.swing.JTextField();
        jTextFieldITradeName = new javax.swing.JTextField();
        jLabelCriteria = new javax.swing.JLabel();

        
        /** Transition label **/
        jLabelTransition = new JLabel();
        jLabelTransition.setVerticalAlignment(SwingConstants.CENTER);
        jLabelTransition.setVerticalTextPosition(SwingConstants.TOP);
        jLabelTransition.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTransition.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabelTransition.setFont(Constants.FONT_PLAIN);
        jLabelTransition.setForeground(Constants.FONT_COLOR);
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		closeWindow();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
        setTitle(Constants.LANG.getString("AdvancedSearchWindowTitle")); 
        
        fd = new FadingCanvas();
        fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
        
        mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
        
        jPanelTopBar.setBackground(java.awt.Color.white);
        jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

        jLabelTopBarMsg2.setFont(Constants.TITLE_FONT_ITALIC);
        jLabelTopBarMsg2.setForeground(Constants.FONT_COLOR);
        jLabelTopBarMsg2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTopBarMsg2.setText(Constants.LANG.getString("TopBarMessage2"));

        jLabelTopBarMsg1.setFont(Constants.TITLE_FONT_ITALIC);
        jLabelTopBarMsg1.setForeground(Constants.FONT_COLOR);
        jLabelTopBarMsg1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTopBarMsg1.setText(Constants.LANG.getString("TopBarMessage1"));

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
        jPanelTopBar.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jLabelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 575, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabelTopBarMsg2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .add(jLabelTopBarMsg1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jLabelTopBarMsg1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelTopBarMsg2))
        );

        jPanelResults.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelResults.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("SearchResult"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR));

        jScrollResults.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollResults.setAutoscrolls(true);
        jScrollResults.setOpaque(false);
        jScrollResults.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableResults.setFont(Constants.FONT_PLAIN);
        jTableResults.setForeground(Constants.FONT_COLOR);
        jTableResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
            		Constants.LANG.getString("Id"),Constants.LANG.getString("InvoiceNumber"),Constants.LANG.getString("Version"),Constants.LANG.getString("Issuer"),Constants.LANG.getString("TaxIdentificationNumberIssuer"),Constants.LANG.getString("Receiver"),Constants.LANG.getString("TaxIdentificationNumberReceiver"), Constants.LANG.getString("IssueDate"), Constants.LANG.getString("Amount"), Constants.LANG.getString("State"),Constants.LANG.getString("Class")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableResults.setGridColor(Constants.SELECTION_COLOR);
        jTableResults.setRowSelectionAllowed(true);
        jTableResults.getTableHeader().setReorderingAllowed(false);
        jTableResults.setColumnSelectionAllowed(false);
        jScrollResults.setViewportView(jTableResults);
        jTableResults.getColumnModel().getColumn(0).setMaxWidth(0);
        jTableResults.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,Constants.BKG_MAIN_COLOR,java.awt.Color.gray,java.awt.Color.gray,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        
        jTableResults.getColumnModel().getColumn(1).setResizable(false);
        jTableResults.getColumnModel().getColumn(1).setPreferredWidth(80);
        jTableResults.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
       
        jTableResults.getColumnModel().getColumn(2).setResizable(false);
        jTableResults.getColumnModel().getColumn(2).setPreferredWidth(30);
        jTableResults.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
      
        jTableResults.getColumnModel().getColumn(3).setResizable(false);
        jTableResults.getColumnModel().getColumn(3).setPreferredWidth(130);
        jTableResults.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
       
        jTableResults.getColumnModel().getColumn(4).setResizable(false);
        jTableResults.getColumnModel().getColumn(4).setPreferredWidth(90);
        jTableResults.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        
        jTableResults.getColumnModel().getColumn(5).setResizable(false);
        jTableResults.getColumnModel().getColumn(5).setPreferredWidth(130);
        jTableResults.getColumnModel().getColumn(5).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
       
        jTableResults.getColumnModel().getColumn(6).setResizable(false);
        jTableResults.getColumnModel().getColumn(6).setPreferredWidth(90);
        jTableResults.getColumnModel().getColumn(6).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        
        jTableResults.getColumnModel().getColumn(7).setResizable(false);
        jTableResults.getColumnModel().getColumn(7).setPreferredWidth(90);
        jTableResults.getColumnModel().getColumn(7).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        
        jTableResults.getColumnModel().getColumn(8).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableResults.getColumnModel().getColumn(8).setPreferredWidth(90);
        
        jTableResults.getColumnModel().getColumn(9).setMaxWidth(20);
        jTableResults.getColumnModel().getColumn(9).setCellRenderer(new StateClassCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.CENTER,1));
        
        jTableResults.getColumnModel().getColumn(10).setMaxWidth(20);
        jTableResults.getColumnModel().getColumn(10).setCellRenderer(new StateClassCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.CENTER,0));
        for (int i = 0 ; i < jTableResults.getColumnCount() ; i++){
        	jTableResults.getColumnModel().getColumn(i).setResizable(false);
            jTableResults.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jTableResults.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent e){
		    	int row = jTableResults.getSelectedRow();
		        if (e.getClickCount() == 2 && row >= 0){
		        	try{
		    			((InvoiceUtil) Class.forName(Constants.APP_PROP.getProperty("appUtilsPath")+"Invoice"+FacturaeUtil.getVersionConst(jTableResults.getValueAt(row, 2).toString())+"Util").newInstance()).chargeInvoice(true, jTableResults.getValueAt(row, 1).toString());
		    		} catch (InstantiationException e1) {
		    			Constants.DIALOG.showErrorChargeInvoice("InstantiationException:" + e1.getMessage());
		    		} catch (IllegalAccessException e1) {
		    			Constants.DIALOG.showErrorChargeInvoice("IllegalAccessException:" + e1.getMessage());
		    		} catch (ClassNotFoundException e1) {
		    			Constants.DIALOG.showErrorChargeInvoice("ClassNotFoundException:" + e1.getMessage());
		    		} catch (UnmarshalException e1) {
		    			Constants.DIALOG.showErrorChargeInvoice(e1.getMessage());
		    		}
		        }
		    }    
		 });
        
        
        org.jdesktop.layout.GroupLayout jPanel26Layout = new org.jdesktop.layout.GroupLayout(jPanelResults);
        jPanelResults.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollResults, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .add(jScrollResults, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg"))); 
        jButtonReturn.setToolTipText(Constants.LANG.getString("Return"));
        jButtonReturn.setBorderPainted(false);
        jButtonReturn.setContentAreaFilled(false);
        jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeWindow();
            }
        });
        jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AdvancedSearchWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	AdvancedSearchWindow.this.mouseExited(evt);
            }
        });

        jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/images/button_help.jpg"))); 
        jButtonHelp.setToolTipText(Constants.LANG.getString("Help")); 
        jButtonHelp.setBorderPainted(false);
        jButtonHelp.setContentAreaFilled(false);
        jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	AdvancedSearchWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	AdvancedSearchWindow.this.mouseExited(evt);
            }
        });
        
        jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButtonHelpActionPerformed(evt);
            }
        });
        
        
        jButtonClean.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif")));
        jButtonClean.setFont(Constants.TITLE_FONT);
        jButtonClean.setForeground(Constants.FONT_COLOR);
        jButtonClean.setText(Constants.LANG.getString("CleanFilter"));
        jButtonClean.setToolTipText(Constants.LANG.getString("CleanFilter")); 
        jButtonClean.setBorderPainted(false);
        jButtonClean.setContentAreaFilled(false);
        jButtonClean.setMargin(new Insets(1, 1, 1, 1));
        jButtonClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	int rowCount = jTableResults.getRowCount();
            	if(rowCount > 0){
            		for(int i = rowCount; i > 0; i--){
            			((DefaultTableModel)jTableResults.getModel()).removeRow(i-1);
            		}            		
            	}

            	jTextFieldSeries.setText("");
            	jTextFieldId.setText("");
                jTextFieldItem.setText("");
                jTextFieldAmountFrom.setText("");
                jTextFieldAmountTo.setText("");
                jTextFieldINIF.setText("");
                jTextFieldIName.setText("");
                jTextFieldI1Surname.setText("");
                jTextFieldI2Surname.setText("");
                jTextFieldICIF.setText("");
                jTextFieldICorpName.setText("");
                jTextFieldITradeName.setText("");
                jTextFieldRNIF.setText("");
                jTextFieldRName.setText("");
                jTextFieldR1Surname.setText("");
                jTextFieldR2Surname.setText("");
                jTextFieldRCIF.setText("");
                jTextFieldRCorpName.setText("");
                jTextFieldRTradeName.setText("");

                jComboBoxType.setSelectedIndex(0);          	
                jRadioButtonIssueD.setSelected(false);
                jRadioButtonInvoicingP.setSelected(false);
                jLabelIssueFrom.setVisible(false);
                jLabelIssueTo.setVisible(false);
                jLabelInvoicingFrom.setVisible(false);
                jLabelInvoicingTo.setVisible(false);
                
                Calendar cal = Calendar.getInstance();
                jCalendarComboBoxIssueFrom.setCalendar(cal);
                jCalendarComboBoxIssueFrom.setVisible(jRadioButtonIssueD.isSelected());
                jCalendarComboBoxIssueTo.setCalendar(cal);
                jCalendarComboBoxIssueTo.setVisible(jRadioButtonIssueD.isSelected());
                jCalendarComboBoxInvoiFrom.setCalendar(cal);
                jCalendarComboBoxInvoiFrom.setVisible(jRadioButtonInvoicingP.isSelected());
                jCalendarComboBoxInvoiTo.setCalendar(cal);
                jCalendarComboBoxInvoiTo.setVisible(jRadioButtonInvoicingP.isSelected());

            }
        });
        jButtonClean.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	AdvancedSearchWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	AdvancedSearchWindow.this.mouseExited(evt);
            }
        });
        
        jButtonSearch.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif")));
        jButtonSearch.setFont(Constants.TITLE_FONT);
        jButtonSearch.setForeground(Constants.FONT_COLOR);
        jButtonSearch.setText(Constants.LANG.getString("Search"));
        jButtonSearch.setToolTipText(Constants.LANG.getString("Search")); 
        jButtonSearch.setBorderPainted(false);
        jButtonSearch.setContentAreaFilled(false);
        jButtonSearch.setMargin(new Insets(1, 1, 1, 1));
        jButtonSearch.setHorizontalAlignment(SwingConstants.LEFT);
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (th == null || !th.isAlive()) {
            		th = new Thread(new Runnable() { public void run() {
            			try {
            				putTransitionPanel(Constants.LANG.getString("Searching"));
            				search();
            			} finally {
            				removeTransitionPanel();
            			}
            		}});
            		th.start();
            	}
            }
        });
        jButtonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	AdvancedSearchWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	AdvancedSearchWindow.this.mouseExited(evt);
            }
        });

        jPanelItem.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelItem.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("InvoiceData"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR));
        
        jLabelInvoiceNumber.setFont(Constants.FONT_PLAIN);
        jLabelInvoiceNumber.setForeground(Constants.FONT_COLOR);
        jLabelInvoiceNumber.setText(Constants.LANG.getString("InvoiceNumber")+":");

        jLabelInvoiceItem.setFont(Constants.FONT_PLAIN);
        jLabelInvoiceItem.setForeground(Constants.FONT_COLOR);
        jLabelInvoiceItem.setText(Constants.LANG.getString("Item")+":");

        jLabelInvoiceType.setFont(Constants.FONT_PLAIN);
        jLabelInvoiceType.setForeground(Constants.FONT_COLOR);
        jLabelInvoiceType.setText(Constants.LANG.getString("State")+":");

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel(new String[]{ Constants.LANG.getString("ANY"),Constants.LANG.getString("DraftStr"),Constants.LANG.getString("Issued"),Constants.LANG.getString("Sent"),Constants.LANG.getString("ReceivedStr"),Constants.LANG.getString("Corrective"),Constants.LANG.getString("CorrectiveDraft"),Constants.LANG.getString("CorrectiveIssued"),Constants.LANG.getString("CorrectiveSent") }));
        jComboBoxType.setMaximumRowCount(7);
        jComboBoxType.setForeground(Constants.FONT_COLOR);
        jComboBoxType.setFont(Constants.FONT_PLAIN);
        jComboBoxType.setRenderer(new ComboBoxRenderer(true));
        
        jLabelIssueFrom.setFont(Constants.FONT_PLAIN);
        jLabelIssueFrom.setForeground(Constants.FONT_COLOR);
        jLabelIssueFrom.setText(Constants.LANG.getString("From"));

        jLabelIssueTo.setFont(Constants.FONT_PLAIN);
        jLabelIssueTo.setForeground(Constants.FONT_COLOR);
        jLabelIssueTo.setText(Constants.LANG.getString("To"));

        jLabelInvoicingFrom.setFont(Constants.FONT_PLAIN);
        jLabelInvoicingFrom.setForeground(Constants.FONT_COLOR);
        jLabelInvoicingFrom.setText(Constants.LANG.getString("From"));

        jLabelInvoicingTo.setFont(Constants.FONT_PLAIN);
        jLabelInvoicingTo.setForeground(Constants.FONT_COLOR);
        jLabelInvoicingTo.setText(Constants.LANG.getString("To"));

        jLabelAmountFrom.setFont(Constants.FONT_PLAIN);
        jLabelAmountFrom.setForeground(Constants.FONT_COLOR);
        jLabelAmountFrom.setText(Constants.LANG.getString("From"));

        jLabelAmountTo.setFont(Constants.FONT_PLAIN);
        jLabelAmountTo.setForeground(Constants.FONT_COLOR);
        jLabelAmountTo.setText(Constants.LANG.getString("To"));

        jCalendarComboBoxIssueFrom.setVisible(false);
        jCalendarComboBoxIssueTo.setVisible(false);
        jLabelIssueFrom.setVisible(false);
        jLabelIssueTo.setVisible(false);
        
    	jCalendarComboBoxInvoiFrom.setVisible(false);
        jCalendarComboBoxInvoiTo.setVisible(false);
        jLabelInvoicingFrom.setVisible(false);
        jLabelInvoicingTo.setVisible(false);
        
        jRadioButtonIssueD.setText(Constants.LANG.getString("IssueDate"));
        jRadioButtonIssueD.setSelected(false);
        jRadioButtonIssueD.setBackground(Constants.BKG_MAIN_COLOR);
        jRadioButtonIssueD.setFont(Constants.FONT_PLAIN);
        jRadioButtonIssueD.setForeground(Constants.FONT_COLOR);
        jRadioButtonIssueD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonExpeditionDate(evt);
            }
        });

        jRadioButtonInvoicingP.setText(Constants.LANG.getString("InvoicingPeriod"));
        jRadioButtonInvoicingP.setBackground(Constants.BKG_MAIN_COLOR);
        jRadioButtonInvoicingP.setFont(Constants.FONT_PLAIN);
        jRadioButtonInvoicingP.setForeground(Constants.FONT_COLOR);
        jRadioButtonInvoicingP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonInvoicingPeriod(evt);
            }
        });

        jLabelAmount.setFont(Constants.FONT_PLAIN);
        jLabelAmount.setForeground(Constants.FONT_COLOR);
        jLabelAmount.setText(Constants.LANG.getString("Amount")+":");

        org.jdesktop.layout.GroupLayout jPanelItemLayout = new org.jdesktop.layout.GroupLayout(jPanelItem);
        jPanelItem.setLayout(jPanelItemLayout);
        jPanelItemLayout.setHorizontalGroup(
            jPanelItemLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                	.add(jLabelInvoiceNumber)
                	.add(jLabelInvoiceItem)
                	.add(jLabelAmount)
                	.add(jRadioButtonIssueD,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                	.add(jRadioButtonInvoicingP,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                	.add(jPanelItemLayout.createSequentialGroup()
                		.add(jTextFieldSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                		.add(jTextFieldId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED*2)
                		.add(jLabelInvoiceType)
                		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                		.add(jComboBoxType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jTextFieldItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanelItemLayout.createSequentialGroup()
                        .add(jLabelAmountFrom)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldAmountFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabelAmountTo)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldAmountTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelItemLayout.createSequentialGroup() 
                        .add(jLabelIssueFrom)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jCalendarComboBoxIssueFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(5,5,5)
                        .add(jLabelIssueTo)
                        .add(5,5,5)
                        .add(jCalendarComboBoxIssueTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanelItemLayout.createSequentialGroup()
                        .add(jLabelInvoicingFrom)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jCalendarComboBoxInvoiFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(5,5,5)
                        .add(jLabelInvoicingTo)
                        .add(5,5,5)
                        .add(jCalendarComboBoxInvoiTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap()               
        );

        jPanelItemLayout.setVerticalGroup(
        	jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
            .add(jPanelItemLayout.createSequentialGroup()
            	.addContainerGap()
                .add(jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelInvoiceNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelInvoiceType)
                    .add(jComboBoxType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelInvoiceItem)
                    .add(jTextFieldItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelAmount)
                    .add(jLabelAmountFrom)
                    .add(jTextFieldAmountFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelAmountTo)
                    .add(jTextFieldAmountTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelIssueFrom)
                    .add(jRadioButtonIssueD)
                    .add(jCalendarComboBoxIssueFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelIssueTo)
                    .add(jCalendarComboBoxIssueTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelItemLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelInvoicingFrom)
                    .add(jRadioButtonInvoicingP)
                    .add(jCalendarComboBoxInvoiFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelInvoicingTo)
                    .add(jCalendarComboBoxInvoiTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelReceiver.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelReceiver.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("Receiver"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR));
        
        jPanelRIndividual.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelRIndividual.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));

        jLabelRNIF.setFont(Constants.FONT_PLAIN);
        jLabelRNIF.setForeground(Constants.FONT_COLOR);
        jLabelRNIF.setText(Constants.LANG.getString("NIF")+":");

        jLabelRName.setFont(Constants.FONT_PLAIN);
        jLabelRName.setForeground(Constants.FONT_COLOR);
        jLabelRName.setText(Constants.LANG.getString("Name")+":");

        jLabelR1Surname.setFont(Constants.FONT_PLAIN);
        jLabelR1Surname.setForeground(Constants.FONT_COLOR);
        jLabelR1Surname.setText(Constants.LANG.getString("FirstSurname")+":");

        jLabelR2Surname.setFont(Constants.FONT_PLAIN);
        jLabelR2Surname.setForeground(Constants.FONT_COLOR);
        jLabelR2Surname.setText(Constants.LANG.getString("SecondSurname")+":");

        org.jdesktop.layout.GroupLayout jPanelRecIndLayout = new org.jdesktop.layout.GroupLayout(jPanelRIndividual);
        jPanelRIndividual.setLayout(jPanelRecIndLayout);
        jPanelRecIndLayout.setHorizontalGroup(
            jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelRecIndLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelRName)
                    .add(jLabelR1Surname)
                    .add(jLabelR2Surname)
                    .add(jLabelRNIF))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTextFieldRNIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldRName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldR2Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldR1Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelRecIndLayout.setVerticalGroup(
            jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelRecIndLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelRNIF)
                    .add(jTextFieldRNIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelRName)
                    .add(jTextFieldRName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelR1Surname)
                    .add(jTextFieldR1Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelRecIndLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelR2Surname)
                    .add(jTextFieldR2Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneReceiver.addTab(Constants.LANG.getString("Individual"), jPanelRIndividual);

        jPanelRLegEntity.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelRLegEntity.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));

        jLabelRCIF.setFont(Constants.FONT_PLAIN);
        jLabelRCIF.setForeground(Constants.FONT_COLOR);
        jLabelRCIF.setText(Constants.LANG.getString("CIF")+":");

        jLabelRCorpName.setFont(Constants.FONT_PLAIN);
        jLabelRCorpName.setForeground(Constants.FONT_COLOR);
        jLabelRCorpName.setText(Constants.LANG.getString("CorporateName")+":");

        jLabelRTradeName.setFont(Constants.FONT_PLAIN);
        jLabelRTradeName.setForeground(Constants.FONT_COLOR);
        jLabelRTradeName.setText(Constants.LANG.getString("TradeName")+":");

        org.jdesktop.layout.GroupLayout jPanel25Layout = new org.jdesktop.layout.GroupLayout(jPanelRLegEntity);
        jPanelRLegEntity.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelRCIF)
                    .add(jLabelRCorpName)
                    .add(jLabelRTradeName))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTextFieldRCIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldRCorpName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldRTradeName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelRCIF)
                    .add(jTextFieldRCIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelRCorpName)
                    .add(jTextFieldRCorpName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelRTradeName)
                    .add(jTextFieldRTradeName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneReceiver.addTab(Constants.LANG.getString("LegalEntity"), jPanelRLegEntity);

        org.jdesktop.layout.GroupLayout jPanelReceiverLayout = new org.jdesktop.layout.GroupLayout(jPanelReceiver);
        jPanelReceiver.setLayout(jPanelReceiverLayout);
        jPanelReceiverLayout.setHorizontalGroup(
            jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelReceiverLayout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPaneReceiver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelReceiverLayout.setVerticalGroup(
            jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelReceiverLayout.createSequentialGroup()
                .add(jTabbedPaneReceiver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelIssuer.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelIssuer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("Issuer"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR));
        
        jPanelIIndividual.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelIIndividual.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));

        jLabelINIF.setFont(Constants.FONT_PLAIN);
        jLabelINIF.setForeground(Constants.FONT_COLOR);
        jLabelINIF.setText(Constants.LANG.getString("NIF")+":");

        jLabelIName.setFont(Constants.FONT_PLAIN);
        jLabelIName.setForeground(Constants.FONT_COLOR);
        jLabelIName.setText(Constants.LANG.getString("Name")+":");

        jLabelI1Surname.setFont(Constants.FONT_PLAIN);
        jLabelI1Surname.setForeground(Constants.FONT_COLOR);
        jLabelI1Surname.setText(Constants.LANG.getString("FirstSurname")+":");

        jLabelI2Surname.setFont(Constants.FONT_PLAIN);
        jLabelI2Surname.setForeground(Constants.FONT_COLOR);
        jLabelI2Surname.setText(Constants.LANG.getString("SecondSurname")+":");

        org.jdesktop.layout.GroupLayout jPanel27Layout = new org.jdesktop.layout.GroupLayout(jPanelIIndividual);
        jPanelIIndividual.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelIName)
                    .add(jLabelI1Surname)
                    .add(jLabelI2Surname)
                    .add(jLabelINIF))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTextFieldINIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldI2Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldI1Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldIName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelINIF)
                    .add(jTextFieldINIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelIName)
                    .add(jTextFieldIName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelI1Surname)
                    .add(jTextFieldI1Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelI2Surname)
                    .add(jTextFieldI2Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneIssuer.addTab(Constants.LANG.getString("Individual"), jPanelIIndividual);

        jPanelILegEntity.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelILegEntity.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));

        jLabelICIF.setFont(Constants.FONT_PLAIN);
        jLabelICIF.setForeground(Constants.FONT_COLOR);
        jLabelICIF.setText(Constants.LANG.getString("CIF")+":");

        jLabelICorpName.setFont(Constants.FONT_PLAIN);
        jLabelICorpName.setForeground(Constants.FONT_COLOR);
        jLabelICorpName.setText(Constants.LANG.getString("CorporateName")+":");

        jLabelITradeName.setFont(Constants.FONT_PLAIN);
        jLabelITradeName.setForeground(Constants.FONT_COLOR);
        jLabelITradeName.setText(Constants.LANG.getString("TradeName")+":");

        org.jdesktop.layout.GroupLayout jPanel28Layout = new org.jdesktop.layout.GroupLayout(jPanelILegEntity);
        jPanelILegEntity.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelICIF)
                    .add(jLabelICorpName)
                    .add(jLabelITradeName))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTextFieldICIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldICorpName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldITradeName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelICIF)
                    .add(jTextFieldICIF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelICorpName)
                    .add(jTextFieldICorpName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jLabelITradeName)
                    .add(jTextFieldITradeName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPaneIssuer.addTab(Constants.LANG.getString("LegalEntity"), jPanelILegEntity);

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanelIssuer);
        jPanelIssuer.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPaneIssuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .add(jTabbedPaneIssuer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelCriteria.setText(Constants.LANG.getString("SearchCriteria"));
        jLabelCriteria.setFont(Constants.FONT_BOLD);
        jLabelCriteria.setForeground(Constants.FONT_COLOR);
        
        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
            		.addContainerGap()
            		.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            			.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1000, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            			.add(jPanelResults, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1000, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            			.add(jLabelCriteria)
            			.add(mainPanelLayout.createSequentialGroup()
            					.add(jPanelItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 450, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanelIssuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanelReceiver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                		.add(mainPanelLayout.createSequentialGroup()
                				.add(jButtonSearch)
                				.add(18, 18, 18)
                				.add(jButtonClean)
                				.add(55, 55, 55)
                				.add(fd,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 647, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                				.add(jButtonHelp,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                				.add(jButtonReturn,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                	.addContainerGap())
        );
                				
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelResults, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelCriteria)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanelReceiver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanelIssuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanelItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jButtonSearch)
                    .add(jButtonClean)
                    .add(jButtonHelp)
                    .add(jButtonReturn)
                    .add(fd,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(15,15,15))
                
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 450, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        
        pack();

    }
    
    private void loadConfiguration(){
    	/** Loading images */
        try {
        	imgLogoApp = ImageIO.read(this.getClass().getResourceAsStream("/images/logoapp.jpg"));
        } catch(IOException ioe) {
        	logger.error("The Facturae logo could not be loaded: " + ioe.getMessage());
        }
    }
    
    private void closeWindow(){
    	setVisible(false);
		dispose();
    }
    
    private void search(){
    	
    	String issuerQuery = "";
    	String receiverQuery = "";
    	
    	String itemDescQuery = "";
    	String amountQuery = "";
    	String issueDateQuery = "";
    	String invoicingPQuery = "";
    	String seriesQuery = "";
    	String idQuery = "";
    	String stateClassQuery = "";
    	
    	String itemQuery = "";
    	String invoiceQuery = "";
    	String facturaeQuery = "";
    	
    	/** ISSUER **/
    	if (jTabbedPaneIssuer.getSelectedIndex() != -1){
    		
    		// Individual
    		if (jTabbedPaneIssuer.getSelectedIndex() == 0){
    			issuerQuery = "SELECT PARTY_ID FROM INDIVIDUAL,PARTY,TAX_IDENTIFICATION WHERE INDIVIDUAL.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER";
    			String copy = "SELECT PARTY_ID FROM INDIVIDUAL,PARTY,TAX_IDENTIFICATION WHERE INDIVIDUAL.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER";
    			if (jTextFieldINIF.getText() != null && !"".equals(jTextFieldINIF.getText().trim()))
    				issuerQuery = issuerQuery + " AND TAX_IDENTIFICATION.TAX_ID_NUMBER LIKE '%" + jTextFieldINIF.getText().trim() + "%'";
    			if (jTextFieldIName.getText() != null && !"".equals(jTextFieldIName.getText().trim()))
    				issuerQuery = issuerQuery + " AND INDIVIDUAL.NAME LIKE '%" + jTextFieldIName.getText().trim() + "%'";
    			if (jTextFieldI1Surname.getText() != null && !"".equals(jTextFieldI1Surname.getText().trim()))
    				issuerQuery = issuerQuery + " AND INDIVIDUAL.FIRST_SURNAME LIKE '%" + jTextFieldI1Surname.getText().trim() + "%'";
    			if (jTextFieldI2Surname.getText() != null && !"".equals(jTextFieldI2Surname.getText().trim()))
    				issuerQuery = issuerQuery + " AND INDIVIDUAL.SECOND_SURNAME LIKE '%" + jTextFieldI2Surname.getText().trim() + "%'";
    			if (!copy.equals(issuerQuery))
    				issuerQuery = " AND PARTY_ID_SELLER IN ("+issuerQuery+")";
    			else
    				issuerQuery = "";
    		}
    		// Legal Entity
    		else{
    			issuerQuery = "SELECT PARTY_ID FROM LEGAL_ENTITY,PARTY,TAX_IDENTIFICATION WHERE LEGAL_ENTITY.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER";
    			String copy = "SELECT PARTY_ID FROM LEGAL_ENTITY,PARTY,TAX_IDENTIFICATION WHERE LEGAL_ENTITY.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER";
    			if (jTextFieldICIF.getText() != null && !"".equals(jTextFieldICIF.getText().trim()))
    				issuerQuery = issuerQuery + " AND TAX_IDENTIFICATION.TAX_ID_NUMBER LIKE '%" + jTextFieldICIF.getText().trim() + "%'";
    			if (jTextFieldICorpName.getText() != null && !"".equals(jTextFieldICorpName.getText().trim()))
    				issuerQuery = issuerQuery + " AND LEGAL_ENTITY.CORPORATE_NAME LIKE '%" + jTextFieldICorpName.getText().trim() + "%'";
    			if (jTextFieldITradeName.getText() != null && !"".equals(jTextFieldITradeName.getText().trim()))
    				issuerQuery = issuerQuery + " AND LEGAL_ENTITY.TRADE_NAME LIKE '%" + jTextFieldITradeName.getText().trim() + "%'";
    			if (!copy.equals(issuerQuery))
    				issuerQuery = " AND PARTY_ID_SELLER IN ("+issuerQuery+")";
    			else
    				issuerQuery = "";
    		}
    		
    	}
    	
    	/** RECEIVER **/
    	if (jTabbedPaneReceiver.getSelectedIndex() != -1){
    		
    		// Individual
    		if (jTabbedPaneReceiver.getSelectedIndex() == 0){
    			receiverQuery = "SELECT PARTY_ID FROM INDIVIDUAL,PARTY,TAX_IDENTIFICATION WHERE INDIVIDUAL.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER";
    			String copyRe = "SELECT PARTY_ID FROM INDIVIDUAL,PARTY,TAX_IDENTIFICATION WHERE INDIVIDUAL.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER";
    			if (jTextFieldRNIF.getText() != null && !"".equals(jTextFieldRNIF.getText().trim()))
    				receiverQuery = receiverQuery + " AND TAX_IDENTIFICATION.TAX_ID_NUMBER LIKE '%" + jTextFieldRNIF.getText().trim() + "%'";
    			if (jTextFieldRName.getText() != null && !"".equals(jTextFieldRName.getText().trim()))
    				receiverQuery = receiverQuery + " AND INDIVIDUAL.NAME LIKE '%" + jTextFieldRName.getText().trim() + "%'";
    			if (jTextFieldR1Surname.getText() != null && !"".equals(jTextFieldR1Surname.getText().trim()))
    				receiverQuery = receiverQuery + " AND INDIVIDUAL.FIRST_SURNAME LIKE '%" + jTextFieldR1Surname.getText().trim() + "%'";
    			if (jTextFieldR2Surname.getText() != null && !"".equals(jTextFieldR2Surname.getText().trim()))
    				receiverQuery = receiverQuery + " AND INDIVIDUAL.SECOND_SURNAME LIKE '%" + jTextFieldR2Surname.getText().trim() + "%'";
    			if (!copyRe.equals(receiverQuery))
    				receiverQuery = " AND PARTY_ID_BUYER IN ("+receiverQuery+")";
    			else
    				receiverQuery = "";
    		}
    		// Legal Entity
    		else{
    			receiverQuery = "SELECT PARTY_ID FROM LEGAL_ENTITY,PARTY,TAX_IDENTIFICATION WHERE LEGAL_ENTITY.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER";
    			String copyRe = "SELECT PARTY_ID FROM LEGAL_ENTITY,PARTY,TAX_IDENTIFICATION WHERE LEGAL_ENTITY.PARTY_ID = PARTY.PARTY_ID AND PARTY.TAX_IDENTIFICATION = TAX_IDENTIFICATION.TAX_ID_NUMBER"; 
    			if (jTextFieldRCIF.getText() != null && !"".equals(jTextFieldRCIF.getText().trim()))
    				receiverQuery = receiverQuery + " AND TAX_IDENTIFICATION.TAX_ID_NUMBER LIKE '%" + jTextFieldRCIF.getText().trim() + "%'";
    			if (jTextFieldRCorpName.getText() != null && !"".equals(jTextFieldRCorpName.getText().trim()))
    				receiverQuery = receiverQuery + " AND LEGAL_ENTITY.CORPORATE_NAME LIKE '%" + jTextFieldRCorpName.getText().trim() + "%'";
    			if (jTextFieldRTradeName.getText() != null && !"".equals(jTextFieldRTradeName.getText().trim()))
    				receiverQuery = receiverQuery + " AND LEGAL_ENTITY.TRADE_NAME LIKE '%" + jTextFieldRTradeName.getText().trim() + "%'";
    			if (!copyRe.equals(receiverQuery))
    				receiverQuery = " AND PARTY_ID_BUYER IN ("+receiverQuery+")";
    			else
    				receiverQuery = "";
    		}
    		
    	}
    	
    	
    	/** AMOUNT **/
    	boolean amountFrom = jTextFieldAmountFrom.getText() != null && !"".equals(jTextFieldAmountFrom.getText().trim());
    	boolean amountTo = jTextFieldAmountTo.getText() != null && !"".equals(jTextFieldAmountTo.getText().trim());
    	try{
	    	if (amountFrom && amountTo){
	    		if (Double.parseDouble(jTextFieldAmountFrom.getText()) > Double.parseDouble(jTextFieldAmountTo.getText())){
	    			fd.showMessage(Constants.LANG.getString("NOOKMessageAmountRange"), Constants.ERROR_MSG_COLOR);
	    			return;
	    		}
	    		else{
	    			amountQuery = " AND TOTAL_EXECUTABLE BETWEEN " + jTextFieldAmountFrom.getText() + " AND " + jTextFieldAmountTo.getText();
	    		}
	    	}
	    	else if (amountFrom && !amountTo){
	    		Double.parseDouble(jTextFieldAmountFrom.getText());
	    		amountQuery = " AND TOTAL_EXECUTABLE >= " + jTextFieldAmountFrom.getText();
	    	}
	    	else if (!amountFrom && amountTo){
	    		Double.parseDouble(jTextFieldAmountTo.getText());
	    		amountQuery = " AND TOTAL_EXECUTABLE <= " + jTextFieldAmountTo.getText();
	    	}
    	}
    	catch (NumberFormatException nfe){
    		fd.showMessage(Constants.LANG.getString("NOOKMessageAmountFormat"), Constants.ERROR_MSG_COLOR);
    		return;
    	}
    	
    	sdf = new SimpleDateFormat("yyyy-MM-dd");
    	
    	/** ISSUE_DATE **/
    	if (jRadioButtonIssueD.isSelected()){
    		if(jCalendarComboBoxIssueFrom.getCalendar().getTime().compareTo(jCalendarComboBoxIssueTo.getCalendar().getTime()) > 0){
    			fd.showMessage(Constants.LANG.getString("NOOKMessageDateRange"), Constants.ERROR_MSG_COLOR);
    			return;
    		}else{
    			issueDateQuery = " AND ISSUE_DATE BETWEEN '" + sdf.format(jCalendarComboBoxIssueFrom.getCalendar().getTime()) + "' AND '" + sdf.format(jCalendarComboBoxIssueTo.getCalendar().getTime())+"'";
    		}
    	}
    	
    	/** INVOICING_PERIOD **/
    	if (jRadioButtonInvoicingP.isSelected()){
    		if(jCalendarComboBoxInvoiFrom.getCalendar().getTime().compareTo(jCalendarComboBoxInvoiTo.getCalendar().getTime()) > 0){
    			fd.showMessage(Constants.LANG.getString("NOOKMessageDateRange"), Constants.ERROR_MSG_COLOR);
    			return;
    		}else{
    			invoicingPQuery = " AND INVOICING_PERIOD_START <= '" + sdf.format(jCalendarComboBoxInvoiTo.getCalendar().getTime()) + "' AND INVOICING_PERIOD_END >= '" + sdf.format(jCalendarComboBoxInvoiFrom.getCalendar().getTime())+"'";
    		}
    	}	
    	
    	/** INVOICE **/
    	if (jTextFieldSeries.getText() != null && !"".equals(jTextFieldSeries.getText()))
    		seriesQuery = " AND SERIES_CODE LIKE '%" + jTextFieldSeries.getText() + "%'";
    	if (jTextFieldId.getText() != null && !"".equals(jTextFieldId.getText()))
    		idQuery = " AND NUMBER = '" + jTextFieldId.getText() + "'";
		if (jComboBoxType.getSelectedIndex() > 0){
			switch(jComboBoxType.getSelectedIndex()){
			case 1: 
			case 2:
			case 3:
			case 4: 
				stateClassQuery = " AND STATE= "+ String.valueOf(jComboBoxType.getSelectedIndex()-1) +" AND CLASS=0";
				break;
			case 6:
			case 7:
			case 8:
				stateClassQuery = " AND STATE= "+ String.valueOf(jComboBoxType.getSelectedIndex()-6);
			case 5:
				stateClassQuery = stateClassQuery + " AND CLASS=1";
				break;
			}
		}
		
		/** ITEM **/
    	if (jTextFieldItem.getText() != null && !"".equals(jTextFieldItem.getText().trim()))
    		itemDescQuery = " AND ITEM_ID IN (SELECT ITEM_ID FROM ITEM WHERE DESCRIPTION LIKE '%" + jTextFieldItem.getText().trim() + "%')";
    	
    	itemQuery = "SELECT ITEM_ID FROM ITEM WHERE ITEM_ID IS NOT NULL";
    	facturaeQuery = "SELECT FACTURAE_ID FROM FACTURAE WHERE FACTURAE_ID IS NOT NULL";
		invoiceQuery = "SELECT INVOICE_ID FROM INVOICE WHERE INVOICE_ID IS NOT NULL";
		
		SQLQuery s = FacturaeManager.getInstance().executeQuery ("SELECT FACTURAE_ID,INVOICE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE_ITEM WHERE ITEM_ID IN ("+itemQuery+itemDescQuery+")) AND INVOICE_ID IN ("+invoiceQuery+amountQuery+issueDateQuery+invoicingPQuery+seriesQuery+idQuery+stateClassQuery+") AND FACTURAE_ID IN ("+facturaeQuery+issuerQuery+receiverQuery+")");
    	List<?> ls = s.list();
    	sdf = Constants.DATE_FORMAT;
    	
    	for (int i = jTableResults.getRowCount() - 1; i >= 0; i--)
    		((DefaultTableModel)jTableResults.getModel()).removeRow(i);
    	
    	if (ls != null && ls.size() > 0) {
    		for (int i = 0; i < ls.size(); i++) {
    			SQLQuery sFacturae = FacturaeManager.getInstance().executeQuery ("SELECT PARTY_ID_SELLER,PARTY_ID_BUYER FROM FACTURAE WHERE FACTURAE_ID = '"+((Object[])ls.get(i))[0]+"'");
    			List<?> lsFacturae = sFacturae.list();
    			SQLQuery sInvoice = FacturaeManager.getInstance().executeQuery ("SELECT NVL(SERIES_CODE+NUMBER,NUMBER),VERSION,ISSUE_DATE,TOTAL_EXECUTABLE,STATE,CLASS FROM INVOICE WHERE INVOICE_ID = '"+((Object[])ls.get(i))[1]+"'");
    			List<?> lsInvoice = sInvoice.list();
    			
    			Object[] queryResult = new Object[9];
    			queryResult[0] = ((Object[])ls.get(i))[1];
    			queryResult[1] = ((Object[])lsInvoice.get(0))[0];
    			queryResult[2] = ((Object[])lsInvoice.get(0))[1];
    			queryResult[3] = ((Object[])lsFacturae.get(0))[0];
    			queryResult[4] = ((Object[])lsFacturae.get(0))[1];
    			queryResult[5] = ((Object[])lsInvoice.get(0))[2];
    			queryResult[6] = ((Object[])lsInvoice.get(0))[3];
		    	queryResult[7] = ((Object[])lsInvoice.get(0))[4];
    		    queryResult[8] = ((Object[])lsInvoice.get(0))[5];	
    				
    			Object[] lineResult = new Object[11];
    			
    			lineResult[0] = queryResult[0];
    			lineResult[1] = queryResult[1];
    			lineResult[2] = queryResult[2];
    			
    			SQLQuery s2 = FacturaeManager.getInstance().executeQuery("SELECT FIRST_SURNAME, SECOND_SURNAME, NAME, TAX_IDENTIFICATION FROM INDIVIDUAL,PARTY WHERE INDIVIDUAL.PARTY_ID=PARTY.PARTY_ID AND INDIVIDUAL.PARTY_ID="+queryResult[3]);
    			List<?> ls2 = s2.list();
    			if (ls2 != null && ls2.size() == 0) {
        			s2 = FacturaeManager.getInstance().executeQuery("SELECT CORPORATE_NAME,TAX_IDENTIFICATION FROM LEGAL_ENTITY,PARTY WHERE LEGAL_ENTITY.PARTY_ID=PARTY.PARTY_ID AND LEGAL_ENTITY.PARTY_ID="+queryResult[3]);
        			ls2 = s2.list();
    			}
    			if (ls2 != null && ls2.size() > 0) {
    				Object[] oArray = (Object[])ls2.get(0);
    				if(oArray.length == 4) {
        				if(oArray[1] != null)
        					lineResult[3] = oArray[0].toString() +" "+ oArray[1].toString()+", "+ oArray[2].toString();
        				else
        					lineResult[3] = oArray[0].toString() +", "+ oArray[2].toString();
            			lineResult[4] = oArray[3].toString(); 
    				} else {
        				lineResult[3] = ((Object[])ls2.get(0))[0];
        				lineResult[4] = ((Object[])ls2.get(0))[1];
    				}
    			}
    			
    			s2 = FacturaeManager.getInstance().executeQuery("SELECT FIRST_SURNAME, SECOND_SURNAME, NAME, TAX_IDENTIFICATION FROM INDIVIDUAL,PARTY WHERE INDIVIDUAL.PARTY_ID=PARTY.PARTY_ID AND INDIVIDUAL.PARTY_ID="+queryResult[4]);
    			ls2 = s2.list();
    			if (ls2 != null && ls2.size() == 0){
    				s2 = FacturaeManager.getInstance().executeQuery("SELECT CORPORATE_NAME, TAX_IDENTIFICATION FROM LEGAL_ENTITY,PARTY WHERE LEGAL_ENTITY.PARTY_ID=PARTY.PARTY_ID AND LEGAL_ENTITY.PARTY_ID="+queryResult[4]);
    				ls2 = s2.list();
    			}
    			if (ls2 != null && ls2.size() > 0){
    				Object[] oArray = (Object[])ls2.get(0);
    				if(oArray.length == 4){
        				if(oArray[1] != null)
        					lineResult[5] = oArray[0].toString() +" "+ oArray[1].toString()+", "+ oArray[2].toString();
        				else
        					lineResult[5] = oArray[0].toString() +", "+ oArray[2].toString();
            			lineResult[6] = oArray[3].toString(); 
    				}else{
        				lineResult[5] = ((Object[])ls2.get(0))[0];
        				lineResult[6] = ((Object[])ls2.get(0))[1];
    				}
    			}
    			
    			lineResult[7] = sdf.format((java.util.Date)queryResult[5]);
    			
    			lineResult[8] = queryResult[6];
    			lineResult[9] = queryResult[7];
    			lineResult[10] = queryResult[8];
    			
    			((DefaultTableModel)jTableResults.getModel()).addRow(lineResult);
    		}
    	}else{
    		fd.showMessage(Constants.LANG.getString("NoInvoicesFoundMessage"), Constants.OK_MSG_COLOR);
    	}
    }
    
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void jRadioButtonExpeditionDate(java.awt.event.ActionEvent evt) {
        jCalendarComboBoxIssueFrom.setVisible(((JRadioButton)evt.getSource()).isSelected());
        jCalendarComboBoxIssueTo.setVisible(((JRadioButton)evt.getSource()).isSelected());
        jLabelIssueFrom.setVisible(((JRadioButton)evt.getSource()).isSelected());
        jLabelIssueTo.setVisible(((JRadioButton)evt.getSource()).isSelected());
    }

    private void jRadioButtonInvoicingPeriod(java.awt.event.ActionEvent evt) {
    	jCalendarComboBoxInvoiFrom.setVisible(((JRadioButton)evt.getSource()).isSelected());
        jCalendarComboBoxInvoiTo.setVisible(((JRadioButton)evt.getSource()).isSelected());
        jLabelInvoicingFrom.setVisible(((JRadioButton)evt.getSource()).isSelected());
        jLabelInvoicingTo.setVisible(((JRadioButton)evt.getSource()).isSelected());
    }
    
    private void jButtonHelpActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	URL helpFile = this.getClass().getResource("/html/help_AdvancedSearch_"+Constants.LANG.getLocale().getLanguage()+".html");
    	if (helpFile == null) {
    		Constants.DIALOG.showErrorHelp();
	    	return;
    	}

    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
    	
    }
    
    /** Puts a semi-translucent panel in MainWindow showing a parametric message with a animated icon
        @param message.- Message to show in the translucent panel
    **/
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

    /** Removes the semi-translucent panel **/
    public void removeTransitionPanel() {

    	if (waiterTh != null) {
    		try {
    			waiterTh.join(500);
    		} catch (InterruptedException e) {
    			
    		}
    		waiterTh.interrupt();
    		if (waiter != null) {
    			waiter.setVisible(false);
    			waiter.dispose();
    		}
    	}     
    	
    }
    
    /** Internal variables */
    private javax.swing.JPanel mainPanel = null;
    private javax.swing.JPanel jPanelTopBar = null;
    private javax.swing.JPanel jPanelItem = null;
    private javax.swing.JPanel jPanelIssuer = null;
    private javax.swing.JPanel jPanelReceiver = null;
    
    private javax.swing.JTabbedPane jTabbedPaneIssuer = null;
    private javax.swing.JPanel jPanelIIndividual = null;
    private javax.swing.JPanel jPanelILegEntity = null;
    private javax.swing.JTabbedPane jTabbedPaneReceiver = null;
    private javax.swing.JPanel jPanelRIndividual = null;
    private javax.swing.JPanel jPanelRLegEntity = null;
    
    private javax.swing.JPanel jPanelResults = null;
    
    private javax.swing.JButton jButtonReturn = null;
    private javax.swing.JButton jButtonHelp = null;
    private javax.swing.JButton jButtonClean = null;
    private javax.swing.JButton jButtonSearch = null;
    
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxIssueFrom = null;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxIssueTo = null;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxInvoiFrom = null;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxInvoiTo = null;
    
    private javax.swing.JComboBox jComboBoxType = null;
    
    private javax.swing.JLabel jLabelTopBar = null;
    private javax.swing.JLabel jLabelTopBarMsg1 = null;
    private javax.swing.JLabel jLabelTopBarMsg2 = null;
    private javax.swing.JLabel jLabelCriteria = null;
    private javax.swing.JLabel jLabelInvoiceNumber = null;
    private javax.swing.JLabel jLabelInvoiceType = null;
    private javax.swing.JLabel jLabelInvoiceItem = null;
    private javax.swing.JLabel jLabelAmount = null;
    private javax.swing.JLabel jLabelAmountFrom = null;
    private javax.swing.JLabel jLabelAmountTo = null;
    private javax.swing.JLabel jLabelIssueFrom = null;
    private javax.swing.JLabel jLabelIssueTo = null;
    private javax.swing.JLabel jLabelInvoicingFrom = null;
    private javax.swing.JLabel jLabelInvoicingTo = null;
    private javax.swing.JLabel jLabelINIF = null;
    private javax.swing.JLabel jLabelIName = null;
    private javax.swing.JLabel jLabelI1Surname = null;
    private javax.swing.JLabel jLabelI2Surname = null;
    private javax.swing.JLabel jLabelICIF = null;
    private javax.swing.JLabel jLabelICorpName = null;
    private javax.swing.JLabel jLabelITradeName = null;
    private javax.swing.JLabel jLabelRNIF = null;
    private javax.swing.JLabel jLabelRName = null;
    private javax.swing.JLabel jLabelR1Surname = null;
    private javax.swing.JLabel jLabelR2Surname = null;
    private javax.swing.JLabel jLabelRCIF = null;
    private javax.swing.JLabel jLabelRCorpName = null;
    private javax.swing.JLabel jLabelRTradeName = null;
    
    private javax.swing.JRadioButton jRadioButtonIssueD = null;
    private javax.swing.JRadioButton jRadioButtonInvoicingP = null;
    
    private javax.swing.JScrollPane jScrollResults = null;
    private javax.swing.JTable jTableResults = null;
    
    private javax.swing.JTextField jTextFieldSeries = null;
    private javax.swing.JTextField jTextFieldId = null;
    private javax.swing.JTextField jTextFieldItem = null;
    private javax.swing.JTextField jTextFieldAmountFrom = null;
    private javax.swing.JTextField jTextFieldAmountTo = null;
    private javax.swing.JTextField jTextFieldINIF = null;
    private javax.swing.JTextField jTextFieldIName = null;
    private javax.swing.JTextField jTextFieldI1Surname = null;
    private javax.swing.JTextField jTextFieldI2Surname = null;
    private javax.swing.JTextField jTextFieldICIF = null;
    private javax.swing.JTextField jTextFieldICorpName = null;
    private javax.swing.JTextField jTextFieldITradeName = null;
    private javax.swing.JTextField jTextFieldRNIF = null;
    private javax.swing.JTextField jTextFieldRName = null;
    private javax.swing.JTextField jTextFieldR1Surname = null;
    private javax.swing.JTextField jTextFieldR2Surname = null;
    private javax.swing.JTextField jTextFieldRCIF = null;
    private javax.swing.JTextField jTextFieldRCorpName = null;
    private javax.swing.JTextField jTextFieldRTradeName = null;
    
    private Image imgLogoApp = null;
    private SimpleDateFormat sdf = null;
}