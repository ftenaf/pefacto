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

import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;

import org.hibernate.SQLQuery;

import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.ui.transitions.Transition;
import es.mityc.appfacturae.utils.constants.Constants;

public class HistoricalWindow extends javax.swing.JFrame {
    
    private FadingCanvas fd = null;
    private SimpleDateFormat sdf = Constants.DATE_FORMAT_HOURS;
    
    private Thread th = null;
    
    // Histotical results table
    int historicalTableSize = -1;
    int start = -1;
    int end = -1;  
    
    // Singelton pattern
    private static HistoricalWindow hw = null;
    
    public static HistoricalWindow getInstance() {
    	if (hw == null)
    		hw = new HistoricalWindow();
    	
    	return hw;
    }
    
    /** Creates new form JFramePrincipal */
    private HistoricalWindow() {
        try {

        	initComponents();
            this.setSize(815, 500);
            this.setLocationRelativeTo(null);
            this.setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/logoapp.jpg")));
            this.setVisible(true);
            this.jButtonGenerateHistoricalActionPerformed(true);
            this.invoicesStatistics();
            
            
        } catch (IOException ex) {
            Logger.getLogger(HistoricalWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanelTopBar = new javax.swing.JPanel();
        jLabelTopbar2 = new javax.swing.JLabel();
        jLabelTopBarMessage2 = new javax.swing.JLabel();
        jLabel1TopBarMessage1 = new javax.swing.JLabel();
        jPanelScrollPane = new javax.swing.JPanel();
        jPanelSearchCriteria = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jLabelInvoiceType = new javax.swing.JLabel();
        jComboBoxInvoiceType = new javax.swing.JComboBox();
        jComboBoxActionType = new javax.swing.JComboBox();
        
        jCalendarComboBoxFrom = new de.wannawork.jcalendar.JCalendarComboBox();
        jCalendarComboBoxTo = new de.wannawork.jcalendar.JCalendarComboBox();
        
        fd = new FadingCanvas();
        fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
        
        jPanelStatistics = new javax.swing.JPanel();
        jLabelListInvoices = new javax.swing.JLabel();
        jTextFieldDrafts = new javax.swing.JTextField();
        jLabelDrafts = new javax.swing.JLabel();
        jLabelIssueds = new javax.swing.JLabel();
        jLabelSents = new javax.swing.JLabel();
        jLabelReceived = new javax.swing.JLabel();
        jLabelCorrectiveDrafts = new javax.swing.JLabel();
        jLabelAssociatedInvoice = new javax.swing.JLabel();
        jTextFieldAssociatedInvoice = new javax.swing.JTextField();
        jLabelActionType = new javax.swing.JLabel();
		jLabelDate = new javax.swing.JLabel();
		jLabelFrom = new javax.swing.JLabel();
		jLabelTo = new javax.swing.JLabel();
        
        jTextFieldSents = new javax.swing.JTextField();
        jTextFieldIssueds = new javax.swing.JTextField();
        jTextFieldReceived = new javax.swing.JTextField();
        jTextFieldCorrectiveDrafts = new javax.swing.JTextField();
        jTextFieldCorrectiveIssueds = new javax.swing.JTextField();
        jLabelCorrectiveIssueds = new javax.swing.JLabel();
        jLabelCorrectiveSents = new javax.swing.JLabel();
        jTextFieldCorrectiveSents = new javax.swing.JTextField();
        jLabelTotal = new javax.swing.JLabel();
        jTextFieldTotal = new javax.swing.JTextField();
        jButtonHelp = new javax.swing.JButton();
        jButtonReturn = new javax.swing.JButton();
        jButtonFilterHistorical = new javax.swing.JButton();
        
        jRadioButtonDate = new javax.swing.JRadioButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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
        setTitle(Constants.LANG.getString("HistoricalSummary")); 
        setResizable(false);

        mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));
        mainPanel.setMaximumSize(new java.awt.Dimension(814, 568));
        mainPanel.setMinimumSize(new java.awt.Dimension(814, 568));

        jPanelTopBar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

        jLabelTopbar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/topbar2.jpg"))); 

        jLabel1TopBarMessage1.setFont(Constants.TITLE_FONT_ITALIC);
        jLabel1TopBarMessage1.setForeground(Constants.FONT_COLOR);
        jLabel1TopBarMessage1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1TopBarMessage1.setText(Constants.LANG.getString("TopBarMessage1")); 
        
        jLabelTopBarMessage2.setFont(Constants.TITLE_FONT_ITALIC);
        jLabelTopBarMessage2.setForeground(Constants.FONT_COLOR);
        jLabelTopBarMessage2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTopBarMessage2.setText(Constants.LANG.getString("TopBarMessage2")); 

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
        jPanelTopBar.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .add(jLabelTopbar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 332, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(104, 104, 104)
                        .add(jLabel1TopBarMessage1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                    .add(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabelTopBarMessage2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(4, 4, 4)
                        .add(jLabel1TopBarMessage1)
                        .add(1, 1, 1)
                        .add(jLabelTopBarMessage2))
                    .add(jLabelTopbar2))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelScrollPane.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("HistoricalSummary"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

        jScrollPane.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPane.setAutoscrolls(true);
        jScrollPane.setOpaque(false);

        jTable.setFont(Constants.FONT_PLAIN);
        jTable.setForeground(Constants.FONT_COLOR);
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                Constants.LANG.getString("ActionType"), Constants.LANG.getString("Date"), Constants.LANG.getString("AssociatedInvoice")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable.setGridColor(Constants.SELECTION_COLOR);
        jTable.setRowSelectionAllowed(true);
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(jTable);
        
        jTable.getColumnModel().getColumn(0).setResizable(false);
        jTable.getColumnModel().getColumn(0).setHeaderValue(Constants.LANG.getString("TransactionType")); 
        jTable.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        jTable.getColumnModel().getColumn(1).setResizable(false);
        jTable.getColumnModel().getColumn(1).setHeaderValue(Constants.LANG.getString("Date")); 
        jTable.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.CENTER));
        jTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        jTable.getColumnModel().getColumn(2).setResizable(false);
        jTable.getColumnModel().getColumn(2).setHeaderValue(Constants.LANG.getString("InvoiceRelated")); 
        jTable.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.CENTER));
        jTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        for (int i = 0 ; i < jTable.getColumnCount() ; i++){
            jTable.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jScrollPane.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        org.jdesktop.layout.GroupLayout jPanel26Layout = new org.jdesktop.layout.GroupLayout(jPanelScrollPane);
        jPanelScrollPane.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel26Layout.createSequentialGroup()
                .add(jScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelInvoiceType.setFont(Constants.FONT_PLAIN);
        jLabelInvoiceType.setForeground(Constants.FONT_COLOR);
        jLabelInvoiceType.setText(Constants.LANG.getString("InvoiceType")); 

        jLabelAssociatedInvoice.setFont(Constants.FONT_PLAIN);
        jLabelAssociatedInvoice.setForeground(Constants.FONT_COLOR);
        jLabelAssociatedInvoice.setText(Constants.LANG.getString("AssociatedInvoice")); 
        
        jLabelActionType.setFont(Constants.FONT_PLAIN);
        jLabelActionType.setForeground(Constants.FONT_COLOR);
        jLabelActionType.setText(Constants.LANG.getString("ActionType")); 

        jLabelDate.setFont(Constants.FONT_PLAIN);
        jLabelDate.setForeground(Constants.FONT_COLOR);
        jLabelDate.setText(Constants.LANG.getString("Date")); 
        
        jLabelFrom.setFont(Constants.FONT_PLAIN);
        jLabelFrom.setForeground(Constants.FONT_COLOR);
        jLabelFrom.setText(Constants.LANG.getString("From")); 
        jLabelFrom.setVisible(false);
        
        jLabelTo.setFont(Constants.FONT_PLAIN);
        jLabelTo.setForeground(Constants.FONT_COLOR);
        jLabelTo.setText(Constants.LANG.getString("To")); 
        jLabelTo.setVisible(false);
        
        jComboBoxInvoiceType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { Constants.LANG.getString("ALL"),Constants.LANG.getString("Issued"), Constants.LANG.getString("Sent"), Constants.LANG.getString("ReceivedStr"), Constants.LANG.getString("Corrective"), Constants.LANG.getString("DraftStr") }));
        jComboBoxInvoiceType.setForeground(Constants.FONT_COLOR);
        jComboBoxInvoiceType.setFont(Constants.FONT_PLAIN);
        jComboBoxInvoiceType.setRenderer(new ComboBoxRenderer(true));
        
        jComboBoxInvoiceType.addFocusListener(new java.awt.event.FocusListener() {
        	
        	ToolTipManager ttm = ToolTipManager.sharedInstance();
        	
			public void focusGained(FocusEvent focusevent) {
				ttm.setEnabled(false);
			}

			public void focusLost(FocusEvent focusevent) {
				ttm.setEnabled(true);
				jComboBoxInvoiceType.setToolTipText(jComboBoxInvoiceType.getSelectedItem().toString());
			}
        });
        
        jComboBoxActionType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { Constants.LANG.getString("ALL"), Constants.LANG.getString("HistDesc_AppStart"), Constants.LANG.getString("HistDesc_AppEnd"), Constants.LANG.getString("HistDesc_SaveDraft"), Constants.LANG.getString("HistDesc_SignIn"), Constants.LANG.getString("HistDesc_RectIn"), Constants.LANG.getString("HistDesc_EditIn"), Constants.LANG.getString("HistDesc_SeeIn"), Constants.LANG.getString("HistDesc_NewParty"), Constants.LANG.getString("HistDesc_ReceiveIn"), Constants.LANG.getString("HistDesc_GenIVA"), Constants.LANG.getString("HistDesc_ImpBBDD"), Constants.LANG.getString("HistDesc_ExpBBDD"), Constants.LANG.getString("HistDesc_ConfChange"), Constants.LANG.getString("HistDesc_SendIn"), Constants.LANG.getString("HistDesc_ResendIn"), Constants.LANG.getString("HistDesc_SeeRect"), Constants.LANG.getString("HistDesc_SendFACe") }));
        jComboBoxActionType.setForeground(Constants.FONT_COLOR);
        jComboBoxActionType.setFont(Constants.FONT_PLAIN);
        jComboBoxActionType.setRenderer(new ComboBoxRenderer(true));
        
        jComboBoxActionType.addFocusListener(new java.awt.event.FocusListener() {
        	
        	ToolTipManager ttm2 = ToolTipManager.sharedInstance();
        	
			public void focusGained(FocusEvent focusevent) {
				ttm2.setEnabled(false);
			}

			public void focusLost(FocusEvent focusevent) {
				ttm2.setEnabled(true);
				jComboBoxActionType.setToolTipText(jComboBoxActionType.getSelectedItem().toString());
			}
        });
        
        jPanelStatistics.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelStatistics.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("Statistics"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelStatistics.setFont(new java.awt.Font("Tahoma", 1, 11));

        jPanelSearchCriteria.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelSearchCriteria.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("SearchCriteria"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
        jPanelSearchCriteria.setFont(new java.awt.Font("Tahoma", 1, 11));
        
        jLabelListInvoices.setFont(Constants.FONT_PLAIN);
        jLabelListInvoices.setForeground(Constants.FONT_COLOR);
        jLabelListInvoices.setText(Constants.LANG.getString("ListOfActualInvoices")); 

        jLabelDrafts.setFont(Constants.FONT_PLAIN);
        jLabelDrafts.setForeground(Constants.FONT_COLOR);
        jLabelDrafts.setText(Constants.LANG.getString("Drafts")); 

        jTextFieldDrafts.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldDrafts.setForeground(Constants.FONT_COLOR);
        jTextFieldDrafts.setEditable(false);

        jLabelIssueds.setFont(Constants.FONT_PLAIN);
        jLabelIssueds.setForeground(Constants.FONT_COLOR);
        jLabelIssueds.setText(Constants.LANG.getString("Issueds")); 

        jTextFieldIssueds.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldIssueds.setForeground(Constants.FONT_COLOR);
        jTextFieldIssueds.setEditable(false);
        
        jLabelSents.setFont(Constants.FONT_PLAIN);
        jLabelSents.setForeground(Constants.FONT_COLOR);
        jLabelSents.setText(Constants.LANG.getString("Sents")); 
        
        jTextFieldReceived.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldReceived.setForeground(Constants.FONT_COLOR);
        jTextFieldReceived.setEditable(false);
        
        jLabelReceived.setFont(Constants.FONT_PLAIN);
        jLabelReceived.setForeground(Constants.FONT_COLOR);
        jLabelReceived.setText(Constants.LANG.getString("Receiveds")); 
        
        jTextFieldCorrectiveDrafts.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldCorrectiveDrafts.setForeground(Constants.FONT_COLOR);
        jTextFieldCorrectiveDrafts.setEditable(false);
        
        jLabelCorrectiveDrafts.setFont(Constants.FONT_PLAIN);
        jLabelCorrectiveDrafts.setForeground(Constants.FONT_COLOR);
        jLabelCorrectiveDrafts.setText(Constants.LANG.getString("CorrectiveDrafts")); 

        jTextFieldSents.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldSents.setForeground(Constants.FONT_COLOR);
        jTextFieldSents.setEditable(false);

        jLabelCorrectiveIssueds.setFont(Constants.FONT_PLAIN);
        jLabelCorrectiveIssueds.setForeground(new java.awt.Color(19, 91, 138));
        jLabelCorrectiveIssueds.setText(Constants.LANG.getString("CorrectiveIssueds")); 

        jTextFieldCorrectiveIssueds.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldCorrectiveIssueds.setForeground(Constants.FONT_COLOR);
        jTextFieldCorrectiveIssueds.setEditable(false);
        
        jLabelCorrectiveSents.setFont(Constants.FONT_PLAIN);
        jLabelCorrectiveSents.setForeground(Constants.FONT_COLOR);
        jLabelCorrectiveSents.setText(Constants.LANG.getString("CorrectiveSents")); 

        jTextFieldCorrectiveSents.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldCorrectiveSents.setForeground(Constants.FONT_COLOR);
        jTextFieldCorrectiveSents.setEditable(false);
        
        jLabelTotal.setFont(Constants.FONT_PLAIN);
        jLabelTotal.setForeground(Constants.FONT_COLOR);
        jLabelTotal.setText(Constants.LANG.getString("Total")); 

        jTextFieldTotal.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldTotal.setForeground(Constants.FONT_COLOR);
        jTextFieldTotal.setEditable(false);
 
        jTextFieldAssociatedInvoice.setForeground(Constants.FONT_COLOR);
        jTextFieldAssociatedInvoice.setEditable(true);
        
        jCalendarComboBoxFrom.setVisible(false);
        jCalendarComboBoxTo.setVisible(false);
        
        jRadioButtonDate.setContentAreaFilled(false);
        jRadioButtonDate.setSelected(false);
		jRadioButtonDate.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        jRadioButtonDateActionPerformed(evt);
		    }
		});
        
        org.jdesktop.layout.GroupLayout jPanelSearchCriteriaLayout = new org.jdesktop.layout.GroupLayout(jPanelSearchCriteria);
        jPanelSearchCriteria.setLayout(jPanelSearchCriteriaLayout);
        jPanelSearchCriteriaLayout.setHorizontalGroup(
        	jPanelSearchCriteriaLayout.createSequentialGroup()
               .addContainerGap()
               .add(jPanelSearchCriteriaLayout.createParallelGroup()
            		   .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelSearchCriteriaLayout.createSequentialGroup()
            				   .add(jLabelInvoiceType)
            				   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            				   .add(jComboBoxInvoiceType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            				   .add(50,50,50)
            				   .add(jLabelActionType)
            				   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            				   .add(jComboBoxActionType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            				   .add(50,50,50)
            				   .add(jLabelAssociatedInvoice)
            				   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            				   .add(jTextFieldAssociatedInvoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            		   .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelSearchCriteriaLayout.createSequentialGroup()
            				   .add(jRadioButtonDate)
            				   .add(30,30,30)
            				   .add(jLabelDate)
            				   .add(50,50,50)
            				   .add(jLabelFrom)
            				   .add(30,30,30)
            				   .add(jCalendarComboBoxFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            				   .add(30,30,30)
            				   .add(jLabelTo)
            				   .add(30,30,30)
            				   .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            				   .add(jCalendarComboBoxTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            				   .add(30,30,30))
            			.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelSearchCriteriaLayout.createSequentialGroup()
            				   .add(jButtonFilterHistorical)))
            	.addContainerGap()
        );
        jPanelSearchCriteriaLayout.setVerticalGroup(
        	jPanelSearchCriteriaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelSearchCriteriaLayout.createSequentialGroup()
            	.addContainerGap()
                .add(jPanelSearchCriteriaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                        .add(jLabelInvoiceType)
                        .add(jComboBoxInvoiceType,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelActionType)
                        .add(jComboBoxActionType,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelAssociatedInvoice)
                        .add(jTextFieldAssociatedInvoice,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(20,20,20)
                .add(jPanelSearchCriteriaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                		.add(jRadioButtonDate)
                		.add(jLabelDate)
                		.add(jLabelFrom)
                		.add(jLabelTo)
                		.add(jCalendarComboBoxFrom,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jCalendarComboBoxTo,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jButtonFilterHistorical))
                .addContainerGap())
        );
        
        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanelStatistics);
        jPanelStatistics.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelListInvoices, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 189, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        	.add(jLabelTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        	.add(jLabelCorrectiveSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        	.add(jLabelCorrectiveIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        	.add(jLabelCorrectiveDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        	.add(jLabelReceived, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(14, 14, 14)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jTextFieldDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldReceived, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldCorrectiveDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldCorrectiveIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldCorrectiveSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .add(20, 20, 20)
                        .add(14, 14, 14)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                                .add(jLabelCorrectiveDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(jLabelCorrectiveIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 121, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelCorrectiveSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 124, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(17, 17, 17)
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jTextFieldCorrectiveSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jTextFieldCorrectiveDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(18, 18, 18)
                                .add(jTextFieldCorrectiveIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(22, 22, 22)
                        .add(20, 20, 20)
                        .add(jLabelTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(25, 25, 25)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelDrafts))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelIssueds))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelSents))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldReceived, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelReceived))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldCorrectiveDrafts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelCorrectiveDrafts))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldCorrectiveIssueds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelCorrectiveIssueds))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldCorrectiveSents, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelCorrectiveSents))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelTotal))
                         .addContainerGap())))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .add(14, 14, 14))
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabelListInvoices)
                .add(9, 9, 9)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        
        jButtonHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_help.jpg"))); 
        jButtonHelp.setToolTipText(Constants.LANG.getString("Help")); 
        jButtonHelp.setBorderPainted(false);
        jButtonHelp.setContentAreaFilled(false);
        jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonMouseExited(evt);
            }
        });
        jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButtonHelpActionPerformed();      	
            }
        });

        jButtonReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_return.jpg"))); 
        jButtonReturn.setToolTipText(Constants.LANG.getString("Return")); 
        jButtonReturn.setBorderPainted(false);
        jButtonReturn.setContentAreaFilled(false);
        jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        		jButtonReturnActionPerformed();
            }
        });
        jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonMouseExited(evt);
            }
        });
        
        jButtonFilterHistorical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif")));
        jButtonFilterHistorical.setFont(Constants.TITLE_FONT);
        jButtonFilterHistorical.setForeground(Constants.FONT_COLOR);
        jButtonFilterHistorical.setText(Constants.LANG.getString("FilterHistorical"));
        jButtonFilterHistorical.setToolTipText(Constants.LANG.getString("FilterHistorical")); 
        jButtonFilterHistorical.setBorderPainted(false);
        jButtonFilterHistorical.setContentAreaFilled(false);
        jButtonFilterHistorical.setMargin(new Insets(1, 1, 1, 1));
        jButtonFilterHistorical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (th == null || !th.isAlive()) {
        			th = new Thread(new Runnable() { public void run() {
        				Transition t = new Transition(mainPanel);
        				try {
        					t.putTransitionPanel(Constants.LANG.getString("Searching"));
        					jButtonGenerateHistoricalActionPerformed(false);
        				} finally {
        					t.removeTransitionPanel();
        				}
        			}});
        			th.start();
        		}
            }
        });
        jButtonFilterHistorical.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HistoricalWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	HistoricalWindow.this.mouseExited(evt);
            }
        });
        
        
        org.jdesktop.layout.GroupLayout mainPanel1Layout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanel1Layout);
        mainPanel1Layout.setHorizontalGroup(
            mainPanel1Layout.createSequentialGroup()
               	.addContainerGap()
               	.add(mainPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
               			.add(org.jdesktop.layout.GroupLayout.TRAILING,mainPanel1Layout.createSequentialGroup()
               					.add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               					.add(115, 115, 115)
               					.add(jButtonHelp,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
               					.add(jButtonReturn,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    	.add(jPanelSearchCriteria)
                    	.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(mainPanel1Layout.createSequentialGroup()
                        		.add(jPanelStatistics, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        		.add(jPanelScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 540, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap()
        );
        mainPanel1Layout.setVerticalGroup(
            mainPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelSearchCriteria)
                .add(mainPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                	.add(jPanelScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                .add(jPanelStatistics, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(mainPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                	.add(fd)
                	.add(jButtonReturn)
                    .add(jButtonHelp))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 812, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
    }// END:initComponents

    private void jRadioButtonDateActionPerformed(java.awt.event.ActionEvent evt) {
        jLabelFrom.setVisible(jRadioButtonDate.isSelected());
        jLabelTo.setVisible(jRadioButtonDate.isSelected());
        jCalendarComboBoxFrom.setVisible(jRadioButtonDate.isSelected());
        jCalendarComboBoxTo.setVisible(jRadioButtonDate.isSelected());
    }
    
    private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_Historical_"+Constants.LANG.getLocale().getLanguage()+".html");
    	if (helpFile == null) {
    		Constants.DIALOG.showErrorHelp();
	    	return;
    	}

    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
    }

    private void jButtonMouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void jButtonMouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    private void jButtonReturnActionPerformed() {
    	if (hw == null)
    		hw = this;
    	hw.setVisible(false);
    	hw.dispose();
    	hw = null;
    }
    
    private void jButtonGenerateHistoricalActionPerformed(boolean isFirstTime) {
    	
    	boolean openWindow = isFirstTime;

    	/** Clean the table and several jTextFields before searching the selected invoices and actions **/
    	int rowCount = jTable.getRowCount();
    	for(int i = rowCount; i > 0; i--){
    		((DefaultTableModel)jTable.getModel()).removeRow(i-1);
    	}
    	
        SQLQuery s;
    	String query = "", queryInvoiceType = "", queryActionType = "", queryDate = "", queryAssociatedInvoice = "";
    	String actionDescription = "";

    	/** Set the Invoice Type before starting the search **/
    	boolean all = false, corrective = false, allOperations = false;
        int invoiceState = -1;
        int invoiceClass = -1;
        
        switch(jComboBoxInvoiceType.getSelectedIndex()){
	        case 0:
	    		// ALL
	        	all = true;
	    		break;
	        case 1:
	    		// Issued
	    		invoiceState = 1;
	    		invoiceClass = 0;
	    		break;
	        case 2:
	    		// Sent
	    		invoiceState = 2;
	    		invoiceClass = 0;
	    		break;
	        case 3:
	    		// Received
	    		invoiceState = 3;
	    		invoiceClass = 0;
	    		break;
	        case 4:
	    		// Corrective (InvoiceState: 0,1,2 and invoiceClass: 1)
	        	corrective = true;
	    		break;
	        case 5:
	    		// Draft
	    		invoiceState = 0;
	    		invoiceClass = 0;
	    		break;
        }

        if(all){
        	queryInvoiceType = "SELECT OPERATION.DESCRIPTION, HISTORICAL.DATE, HISTORICAL.INVOICE, HISTORICAL.ERROR FROM HISTORICAL, OPERATION "+
        			" WHERE HISTORICAL.OPERATION=OPERATION.CODE";
        }else if(corrective){
        	queryInvoiceType = "SELECT DISTINCT OPERATION.DESCRIPTION, HISTORICAL.DATE, HISTORICAL.INVOICE, HISTORICAL.ERROR FROM HISTORICAL, OPERATION WHERE HISTORICAL.OPERATION=OPERATION.CODE AND HISTORICAL.INVOICE IN (SELECT NVL(SERIES_CODE+NUMBER,NUMBER) FROM INVOICE  WHERE  (STATE = '0' OR STATE = '1' OR STATE = '2') AND CLASS = '1')";
        }else{
        	queryInvoiceType = "SELECT DISTINCT OPERATION.DESCRIPTION, HISTORICAL.DATE, HISTORICAL.INVOICE, HISTORICAL.ERROR FROM HISTORICAL, OPERATION WHERE HISTORICAL.OPERATION=OPERATION.CODE AND HISTORICAL.INVOICE IN (SELECT NVL(SERIES_CODE+NUMBER,NUMBER) FROM INVOICE  WHERE STATE = '" + invoiceState + "' AND CLASS = '" + invoiceClass + "')";
        }
    	
        /** Operation Types **/
        switch(jComboBoxActionType.getSelectedIndex()){
	        case 0:
	    		// ALL
	        	allOperations = true;
	    		break;
	        case 1:
	        	actionDescription = "HistDesc_AppStart";
	    		break;
	        case 2:
	        	actionDescription = "HistDesc_EndStart";
	    		break;
	        case 3:
	        	actionDescription = "HistDesc_SaveDraft";
	    		break;
	        case 4:
	        	actionDescription = "HistDesc_SignIn";
	    		break;
	        case 5:
	        	actionDescription = "HistDesc_RectIn";
	    		break;
	        case 6:
	        	actionDescription = "HistDesc_EditIn";
	    		break;
	        case 7:
	        	actionDescription = "HistDesc_SeeIn";
	    		break;
	        case 8:
	        	actionDescription = "HistDesc_NewParty";
	    		break;
	        case 9:
	        	actionDescription = "HistDesc_ReceiveIn";
	    		break;
	        case 10:
	        	actionDescription = "HistDesc_GenIVA";
	    		break;
	        case 11:
	        	actionDescription = "HistDesc_ImpBBDD";
	    		break;
	        case 12:
	        	actionDescription = "HistDesc_ExpBBDD";
	    		break;
	        case 13:
	        	actionDescription = "HistDesc_ConfChange";
	    		break;
	        case 14:
	        	actionDescription = "HistDesc_SendIn";
	    		break;
	        case 15:
	        	actionDescription = "HistDesc_ResendIn";
	    		break;
	        case 16:
	        	actionDescription = "HistDesc_SeeRect";
	    		break;
	        case 17:
	        	actionDescription = "HistDesc_SendFACe";
	    		break;
        }
        
        if(allOperations){
        	queryActionType = "";
        }else{
        	queryActionType = " AND OPERATION.DESCRIPTION = '" + actionDescription + "'";
        }
        
        /** Date **/
        if(jRadioButtonDate.isSelected()){
        	sdf = new SimpleDateFormat("yyyy-MM-dd");
        	queryDate = " AND HISTORICAL.DATE >= '" + sdf.format(jCalendarComboBoxFrom.getCalendar().getTime()) + " " + Constants.LANG.getString("StartDate") ;
        	sdf = Constants.DATE_FORMAT_HOURS;
        	queryDate = queryDate + "' AND" + " HISTORICAL.DATE <= '" + sdf.format(jCalendarComboBoxTo.getCalendar().getTime()) + "'";
        }else{
        	queryDate = "";
        }
        
        /** Associated Invoice **/
        String associatedInvoice = "";
        if(jTextFieldAssociatedInvoice.getText() != null && !"".equals(jTextFieldAssociatedInvoice.getText().trim())){
        	associatedInvoice = jTextFieldAssociatedInvoice.getText().trim();
        	queryAssociatedInvoice = " AND HISTORICAL.INVOICE IS NOT NULL AND HISTORICAL.INVOICE = '" + associatedInvoice + "'";
        }else{
        	queryAssociatedInvoice ="";
        }

        query = queryInvoiceType + queryActionType + queryDate + queryAssociatedInvoice;
        if(jRadioButtonDate.isSelected() && jCalendarComboBoxFrom.getCalendar().getTime().compareTo(jCalendarComboBoxTo.getCalendar().getTime()) > 0){
			fd.showMessage(Constants.LANG.getString("NOOKMessageDateRange"), Constants.ERROR_MSG_COLOR);
			return;
        }else{
        	s = FacturaeManager.getInstance().executeQuery(query);
        }
        
        historicalTableSize = Integer.parseInt(Constants.APP_PROP.getProperty("HistoricalOperationsNumber"));
        List<?> ls = s.list();
        start = ls.size();
        
        if(start > 0){
        	
	        if(start < historicalTableSize){
	        	end = 0;
	        }else{
	        	end = start - historicalTableSize;
	        }
	        
	        Object[] h = null;
	        for (int i = start; i > end; --i) {
	            h = (Object[])ls.get(i-1);
	            h[0] = Constants.LANG.getString(h[0].toString()) + (h[3]!=null?": " + h[3]:"");
	            h[1] = sdf.format((java.sql.Timestamp)h[1]);
	            if (h[2] != null) {
	            	query = "SELECT SERIES_CODE, NUMBER FROM INVOICE WHERE NVL(SERIES_CODE + NUMBER,NUMBER)='" + h[2] + "'";
	            	SQLQuery s2 = FacturaeManager.getInstance().executeQuery(query);
	            	List<?> ls2 = s2.list();
	            	if (s2 != null && ls2.size() > 0) {
	            		Object[] invoice = (Object[])ls2.get(0);
	            		if (invoice[0] != null && invoice[1] != null)
	            			h[2] = invoice[0].toString() + invoice[1].toString();
	            		else if (invoice[1] != null)
	            			h[2] = invoice[1].toString();
	            	}
	            }
	            ((DefaultTableModel)jTable.getModel()).addRow(h);
	        }
	        
	        if(!openWindow)
	        	fd.showMessage(Constants.LANG.getString("OKMessageHistorialGenerated"), Constants.OK_MSG_COLOR);
	            
        }else{
        	fd.showMessage(Constants.LANG.getString("NoOperationsFoundMessage"), Constants.OK_MSG_COLOR);
        }
    	
    }
    
    private void invoicesStatistics(){
    	
        String query = "";
    	SQLQuery s;
    	
    	/** Drafts **/
    	query = "SELECT NUMBER FROM INVOICE WHERE STATE = 0 AND CLASS = 0";
        s = FacturaeManager.getInstance().executeQuery(query);
        List<?> ls = s.list();
        jTextFieldDrafts.setText(Integer.toString(ls.size()));
        jTextFieldDrafts.setHorizontalAlignment(SwingConstants.RIGHT);

        /** Issued **/
    	query = "SELECT NUMBER FROM INVOICE WHERE STATE = 1 AND CLASS = 0";
        s = FacturaeManager.getInstance().executeQuery(query);
        ls = s.list();
        jTextFieldIssueds.setText(Integer.toString(ls.size()));
        jTextFieldIssueds.setHorizontalAlignment(SwingConstants.RIGHT);
        
        /** Sent **/
    	query = "SELECT NUMBER FROM INVOICE WHERE STATE = 2 AND CLASS = 0";
        s = FacturaeManager.getInstance().executeQuery(query);
        ls = s.list();
        jTextFieldSents.setText(Integer.toString(ls.size()));
        jTextFieldSents.setHorizontalAlignment(SwingConstants.RIGHT);
        
        /** Received **/
    	query = "SELECT NUMBER FROM INVOICE WHERE STATE = 3 AND (CLASS = 0 OR CLASS = 1)";
        s = FacturaeManager.getInstance().executeQuery(query);
        ls = s.list();
        jTextFieldReceived.setText(Integer.toString(ls.size()));
        jTextFieldReceived.setHorizontalAlignment(SwingConstants.RIGHT);
    	
        /** Corrective: Drafts **/
    	query = "SELECT NUMBER FROM INVOICE WHERE STATE = 0 AND CLASS = 1";
        s = FacturaeManager.getInstance().executeQuery(query);
        ls = s.list();
        jTextFieldCorrectiveDrafts.setText(Integer.toString(ls.size()));
        jTextFieldCorrectiveDrafts.setHorizontalAlignment(SwingConstants.RIGHT);
        
        /** Corrective: Issued **/
    	query = "SELECT NUMBER FROM INVOICE WHERE STATE = 1 AND CLASS = 1";
        s = FacturaeManager.getInstance().executeQuery(query);
        ls = s.list();
        jTextFieldCorrectiveIssueds.setText(Integer.toString(ls.size()));
        jTextFieldCorrectiveIssueds.setHorizontalAlignment(SwingConstants.RIGHT);
        
        /** Corrective: Sent **/
    	query = "SELECT NUMBER FROM INVOICE WHERE STATE = 2 AND CLASS = 1";
        s = FacturaeManager.getInstance().executeQuery(query);
        ls = s.list();
        jTextFieldCorrectiveSents.setText(Integer.toString(ls.size()));
        jTextFieldCorrectiveSents.setHorizontalAlignment(SwingConstants.RIGHT);
        
        /** Corrective: Sent **/
    	query = "SELECT NUMBER FROM INVOICE";
        s = FacturaeManager.getInstance().executeQuery(query);
        ls = s.list();
        jTextFieldTotal.setText(Integer.toString(ls.size()));
        jTextFieldTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
    }
    
    
    
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    /** Variables declaration */    
    private javax.swing.JPanel mainPanel = null;
    private javax.swing.JPanel jPanelStatistics = null;
    private javax.swing.JPanel jPanelScrollPane = null;
    private javax.swing.JPanel jPanelSearchCriteria = null;
    private javax.swing.JPanel jPanelTopBar = null;
    
    private javax.swing.JTable jTable = null;
    private javax.swing.JScrollPane jScrollPane = null;
    
    private javax.swing.JButton jButtonReturn = null;
    private javax.swing.JButton jButtonHelp = null;
    private javax.swing.JButton jButtonFilterHistorical = null;
    
    private javax.swing.JComboBox jComboBoxInvoiceType = null;
    private javax.swing.JComboBox jComboBoxActionType = null;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxFrom = null;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxTo = null;
    private javax.swing.JRadioButton jRadioButtonDate = null;
    
    private javax.swing.JLabel jLabelListInvoices = null;
    private javax.swing.JLabel jLabelTotal = null;
    private javax.swing.JLabel jLabelTopBarMessage2 = null;
    private javax.swing.JLabel jLabel1TopBarMessage1 = null;
    private javax.swing.JLabel jLabelDrafts = null;
    private javax.swing.JLabel jLabelIssueds = null;
    private javax.swing.JLabel jLabelInvoiceType = null;
    private javax.swing.JLabel jLabelTopbar2 = null;
    private javax.swing.JLabel jLabelSents = null;
    private javax.swing.JLabel jLabelReceived = null;
    private javax.swing.JLabel jLabelCorrectiveDrafts = null;
    private javax.swing.JLabel jLabelCorrectiveIssueds = null;
    private javax.swing.JLabel jLabelCorrectiveSents = null;
    private javax.swing.JLabel jLabelAssociatedInvoice = null;
    private javax.swing.JLabel jLabelActionType = null;
    private javax.swing.JLabel jLabelDate = null;
    private javax.swing.JLabel jLabelFrom = null;
    private javax.swing.JLabel jLabelTo = null;
    
    private javax.swing.JTextField jTextFieldDrafts = null;
    private javax.swing.JTextField jTextFieldIssueds = null;
    private javax.swing.JTextField jTextFieldSents = null;
    private javax.swing.JTextField jTextFieldReceived = null;
    private javax.swing.JTextField jTextFieldCorrectiveDrafts = null;
    private javax.swing.JTextField jTextFieldCorrectiveIssueds = null;
    private javax.swing.JTextField jTextFieldCorrectiveSents = null;
    private javax.swing.JTextField jTextFieldTotal = null;
    private javax.swing.JTextField jTextFieldAssociatedInvoice = null;
}