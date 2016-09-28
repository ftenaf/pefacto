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
package es.mityc.appfacturae.ui.dialogs.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.xml.sax.SAXException;

import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.ui.dialogs.InputChargeDialog;
import es.mityc.appfacturae.ui.dialogs.InputDiscountDialog;
import es.mityc.appfacturae.ui.dialogs.InputExtensionDialog;
import es.mityc.appfacturae.ui.dialogs.InvoiceDetailDialog;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.utils.DatabaseUtil;
import es.mityc.appfacturae.utils.XMLFacturaeUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.DoubleUtil;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.ValidatorUtil;

public class InvoiceDetailGeneralPanel extends JPanel{
	
	private static final long serialVersionUID = -2410601059482424370L;

	private Properties decimalProps = null;
	private String version = null;
	private String codeVersion = null;
	
	private boolean newItem = false;
	
	private static Log logger = LogFactory.getLog(InvoiceDetailGeneralPanel.class);
	
	public InvoiceDetailGeneralPanel(String version) {
		super();
		this.version = version;
		codeVersion = FacturaeUtil.getVersionConst(version);
		newItem = false;
		loadDecimalConfiguration();
        initComponents();
	}
	
	private void initComponents(){
		
		jLabelGUnitPrice = new javax.swing.JLabel();
        jLabelGTotAmount = new javax.swing.JLabel();
        jComboBoxGDesc = new javax.swing.JComboBox();
        jTextFieldGQuantity = new javax.swing.JTextField();
        jTextFieldGUnitPrice = new javax.swing.JTextField();
        jTextFieldGTotAmount = new javax.swing.JTextField();
        jComboBoxGUnitsOfM = new javax.swing.JComboBox();
        jTabbedPaneG = new javax.swing.JTabbedPane();
        jPanelGDiscounts = new javax.swing.JPanel();
        jScrollPaneGDiscounts = new javax.swing.JScrollPane();
        jTableGDiscounts = new javax.swing.JTable();
        jButtonGDelDiscount = new javax.swing.JButton();
        jButtonGAddDiscount = new javax.swing.JButton();
        jPanelGCharges = new javax.swing.JPanel();
        jScrollPaneGCharges = new javax.swing.JScrollPane();
        jTableGCharges = new javax.swing.JTable();
        jButtonGDelCharge = new javax.swing.JButton();
        jButtonGAddCharge = new javax.swing.JButton();
        jLabelGGROSS = new javax.swing.JLabel();
        jTextFieldGGROSS = new javax.swing.JTextField();
        jScrollPaneGAddInfo = new javax.swing.JScrollPane();
        jLabelGAddInfo = new javax.swing.JLabel();
        jTextAreaGAddInfo = new javax.swing.JTextArea(0,0);
        jLabelGDesc = new javax.swing.JLabel();
        jLabelGQuantity = new javax.swing.JLabel();
        jScrollPaneXMLAddData = new javax.swing.JScrollPane();
        jTextAreaXMLExtAddData = new javax.swing.JTextArea();
        jButtonXMLAddData = new javax.swing.JButton();
        jButtonXMLAddDataZoom = new javax.swing.JButton();
        jLabelXMLAddData = new javax.swing.JLabel();
        
        discountAmount = new Double(0);
        chargeAmount = new Double(0);
        
        this.setBackground(Constants.BKG_MAIN_COLOR);
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(
				new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1,true), Constants.LANG.getString("General"),
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				Constants.FONT_BOLD, Constants.FONT_COLOR)); 

        jLabelGUnitPrice.setFont(Constants.FONT_PLAIN);
        jLabelGUnitPrice.setForeground(Constants.FONT_COLOR);
        jLabelGUnitPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
        jLabelGUnitPrice.setText(Constants.LANG.getString("UnitPriceWithoutTax")+" ("+Constants.LANG.getString("EuroSimbol")+")" ); 
        
        jTextFieldGUnitPrice.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldGUnitPrice.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});

        jLabelGTotAmount.setFont(Constants.FONT_PLAIN);
        jLabelGTotAmount.setForeground(Constants.FONT_COLOR);
        jLabelGTotAmount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
        jLabelGTotAmount.setText(Constants.LANG.getString("TotalAmount")+" ("+Constants.LANG.getString("EuroSimbol")+")" ); 
        
        jTextFieldGTotAmount.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldGTotAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldGTotAmount.setEditable(false);
        
        QuantityValidator qv = new QuantityValidator();
        jTextFieldGQuantity.addFocusListener(qv);
        
        UnitPriceValidator upv = new UnitPriceValidator();
        jTextFieldGUnitPrice.addFocusListener(upv);

        jComboBoxGUnitsOfM.setRenderer(new ComboBoxRenderer(true));
        String[] unitsOfMeasure = Constants.APP_PROP.getProperty("unitsOfMeasure"+codeVersion).split(";");
        for (int i = 0 ; i < unitsOfMeasure.length ; i++)
        	unitsOfMeasure[i] = Constants.LANG.getString(unitsOfMeasure[i]);
        jComboBoxGUnitsOfM.setModel(new javax.swing.DefaultComboBoxModel(unitsOfMeasure));
        jComboBoxGUnitsOfM.setSelectedIndex(0);
        
        jPanelGDiscounts.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneGDiscounts.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneGDiscounts.setAutoscrolls(true);
        jScrollPaneGDiscounts.setOpaque(false);
        jScrollPaneGDiscounts.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
        
        jTableGDiscounts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
	            new String [] {
					Constants.LANG.getString("Item"),
					Constants.LANG.getString("Rate"),
					Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")"
	            }
	        ) {
			private static final long serialVersionUID = 641727957531284651L;
			boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableGDiscounts.setGridColor(Constants.SELECTION_COLOR);
        jTableGDiscounts.getTableHeader().setReorderingAllowed(false);
        jScrollPaneGDiscounts.setViewportView(jTableGDiscounts);
        jTableGDiscounts.getColumnModel().getColumn(0).setResizable(false);
        jTableGDiscounts.getColumnModel().getColumn(0).setHeaderValue(Constants.LANG.getString("Item") ); 
		jTableGDiscounts.getColumnModel().getColumn(0).setCellRenderer(
			new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR,
				Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGDiscounts.getColumnModel().getColumn(1).setResizable(false);
        jTableGDiscounts.getColumnModel().getColumn(1).setHeaderValue(Constants.LANG.getString("Rate") ); 
		jTableGDiscounts.getColumnModel().getColumn(1).setCellRenderer(
			new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR,
				Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableGDiscounts.getColumnModel().getColumn(2).setResizable(false);
        jTableGDiscounts.getColumnModel().getColumn(2).setHeaderValue(Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")" ); 
		jTableGDiscounts.getColumnModel().getColumn(2).setCellRenderer(
			new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR,
				Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        for (int i = 0 ; i < jTableGDiscounts.getColumnCount() ; i++){
            jTableGDiscounts.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jButtonGDelDiscount.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonGDelDiscount.setForeground(Constants.BKG_MAIN_COLOR);
        jButtonGDelDiscount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_minus.jpg"))); 
        jButtonGDelDiscount.setToolTipText(Constants.LANG.getString("Remove")); 
        jButtonGDelDiscount.setBorderPainted(false);
        jButtonGDelDiscount.setContentAreaFilled(false);
        jButtonGDelDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delDiscount(evt);
            }
        });
        jButtonGDelDiscount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseExited(evt);
            }
        });

        jButtonGAddDiscount.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonGAddDiscount.setForeground(Constants.BKG_MAIN_COLOR);
        jButtonGAddDiscount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_plus.jpg"))); 
        jButtonGAddDiscount.setToolTipText(Constants.LANG.getString("Add")); 
        jButtonGAddDiscount.setBorderPainted(false);
        jButtonGAddDiscount.setContentAreaFilled(false);
        jButtonGAddDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDiscount(evt);
            }
        });
        jButtonGAddDiscount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseExited(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanelGDiscounts);
        jPanelGDiscounts.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPaneGDiscounts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(11, 11, 11)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(jButtonGAddDiscount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4Layout.createSequentialGroup()
                        .add(jButtonGDelDiscount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPaneGDiscounts, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(19, 19, 19)
                        .add(jButtonGAddDiscount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(5, 5, 5)
                        .add(jButtonGDelDiscount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPaneG.addTab(Constants.LANG.getString("Discounts") , jPanelGDiscounts); 

        jPanelGCharges.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneGCharges.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneGCharges.setAutoscrolls(true);
        jScrollPaneGCharges.setOpaque(false);
        jScrollPaneGCharges.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableGCharges.setFont(Constants.FONT_PLAIN);
        jTableGCharges.setForeground(Constants.FONT_COLOR);
        jTableGCharges.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
	            new String [] {
					Constants.LANG.getString("Item"),
					Constants.LANG.getString("Rate"),
					Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")"
	            }
        	) {
			private static final long serialVersionUID = 4350006664052080134L;
			boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableGCharges.setGridColor(Constants.SELECTION_COLOR);
        jTableGCharges.getTableHeader().setReorderingAllowed(false);
        jScrollPaneGCharges.setViewportView(jTableGCharges);
        jTableGCharges.getColumnModel().getColumn(0).setResizable(false);
        jTableGCharges.getColumnModel().getColumn(0).setHeaderValue(Constants.LANG.getString("Item") ); 
		jTableGCharges.getColumnModel().getColumn(0).setCellRenderer(
			new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR,
				Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGCharges.getColumnModel().getColumn(1).setResizable(false);
        jTableGCharges.getColumnModel().getColumn(1).setHeaderValue(Constants.LANG.getString("Rate") ); 
		jTableGCharges.getColumnModel().getColumn(1).setCellRenderer(
			new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR,
				Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableGCharges.getColumnModel().getColumn(2).setResizable(false);
        jTableGCharges.getColumnModel().getColumn(2).setHeaderValue(Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")" ); 
		jTableGCharges.getColumnModel().getColumn(2).setCellRenderer(
			new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR,
				Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        for (int i = 0 ; i < jTableGCharges.getColumnCount() ; i++){
            jTableGCharges.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jButtonGDelCharge.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonGDelCharge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_minus.jpg"))); 
        jButtonGDelCharge.setToolTipText(Constants.LANG.getString("Remove")); 
        jButtonGDelCharge.setBorderPainted(false);
        jButtonGDelCharge.setContentAreaFilled(false);
        jButtonGDelCharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delCharge(evt);
            }
        });
        jButtonGDelCharge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseExited(evt);
            }
        });

        jButtonGAddCharge.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonGAddCharge.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_plus.jpg"))); 
        jButtonGAddCharge.setToolTipText(Constants.LANG.getString("Add")); 
        jButtonGAddCharge.setBorderPainted(false);
        jButtonGAddCharge.setContentAreaFilled(false);
        jButtonGAddCharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCharge(evt);
            }
        });
        jButtonGAddCharge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetailGeneralPanel.this.mouseExited(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanelGCharges);
        jPanelGCharges.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPaneGCharges, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 11, Short.MAX_VALUE)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jButtonGAddCharge, 0, 0, Short.MAX_VALUE)
                    .add(jButtonGDelCharge, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(30, 30, 30)
                        .add(jButtonGAddCharge, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(5, 5, 5)
                        .add(jButtonGDelCharge, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPaneGCharges, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPaneG.addTab(Constants.LANG.getString("Charges") , jPanelGCharges); 

        jLabelGGROSS.setFont(Constants.FONT_PLAIN);
        jLabelGGROSS.setForeground(Constants.FONT_COLOR);
        jLabelGGROSS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
        jLabelGGROSS.setText(Constants.LANG.getString("GROSSAMOUNT")+" ("+Constants.LANG.getString("EuroSimbol")+")" ); 

        jTextFieldGGROSS.setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldGGROSS.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldGGROSS.setEditable(false);
        
        jLabelGAddInfo.setFont(Constants.FONT_PLAIN);
        jLabelGAddInfo.setForeground(Constants.FONT_COLOR);
        jLabelGAddInfo.setText(Constants.LANG.getString("AdditionalInformation") ); 

        jLabelGDesc.setFont(Constants.FONT_PLAIN);
        jLabelGDesc.setForeground(Constants.FONT_COLOR);
        jLabelGDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
        jLabelGDesc.setText(Constants.LANG.getString("Description")+"-id"); 
        
        jComboBoxGDesc.setEditable(true);
        jComboBoxGDesc.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent evt) {
				((Component)evt.getSource()).setBackground(Color.WHITE);
				if (!newItem){
					newItem = true;
					jTextFieldGQuantity.setText("");
					jTextFieldGUnitPrice.setText("");
					jComboBoxGUnitsOfM.setSelectedIndex(0);
					jTextAreaGAddInfo.setText("");
					jTextFieldGTotAmount.setText("");
					jTextFieldGGROSS.setText("");
					int outSize = ((DefaultTableModel) ((InvoiceDetailDialog) jTextFieldGTotAmount
						.getTopLevelAncestor()).getPanelTaxes().getJTableTOutputs().getModel()).getRowCount();
					int witSize = ((DefaultTableModel) ((InvoiceDetailDialog) jTextFieldGTotAmount
						.getTopLevelAncestor()).getPanelTaxes()
						.getJTableTWithhelds().getModel()).getRowCount();
					for (int i = outSize - 1; i >= 0; i--)
						((DefaultTableModel)((InvoiceDetailDialog)jTextFieldGTotAmount.getTopLevelAncestor()).getPanelTaxes().getJTableTOutputs().getModel()).removeRow(i);
					for (int i = witSize-1 ; i >= 0; i--)
						((DefaultTableModel)((InvoiceDetailDialog)jTextFieldGTotAmount.getTopLevelAncestor()).getPanelTaxes().getJTableTWithhelds().getModel()).removeRow(i);
				}	    		
			}				
		});
        jComboBoxGDesc.setRenderer(new ComboBoxRenderer(true));
        jComboBoxGDesc.setBackground(Color.RED);
        SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT DISTINCT DESCRIPTION FROM ITEM");
        List<?> ls = s.list();
        if (ls.size() > 0) {
        	ArrayList<String> items = new ArrayList<String> ();
        	// It may include only items wich unit of measure are inside its version
        	for (int i = 0; i < ls.size(); ++i) {
        		items.add(ls.get(i).toString());
        	}
        	jComboBoxGDesc.setModel(new javax.swing.DefaultComboBoxModel(items.toArray()));
        }
        jComboBoxGDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	((JComboBox)evt.getSource()).getEditor().getEditorComponent().setBackground(Color.WHITE);
            	if (((JComboBox)evt.getSource()).getSelectedIndex() >= 0){
            		newItem = false;
            		jTextFieldGQuantity.setText("");
            		setItemData(evt);
            	}
            }
        });
        jComboBoxGDesc.setSelectedItem(null);
        jComboBoxGDesc.getEditor().getEditorComponent().addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if(jComboBoxGDesc.isEnabled()){
	            	if (jComboBoxGDesc.isShowing())
	            		jComboBoxGDesc.showPopup();
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}	
        });
        jComboBoxGDesc.addFocusListener(new FocusAdapter() {
        	public void focusLost(FocusEvent evt) {
        		if (((JComboBox)evt.getSource()) != null && ((JComboBox)evt.getSource()).getSelectedItem() != null){
        			String value = ((JComboBox)evt.getSource()).getSelectedItem().toString();
        			if (value.length() > 2500){
        				((JComboBox)evt.getSource()).setSelectedItem(null);
						((InvoiceDetailDialog) ((JComboBox) evt.getSource()).getTopLevelAncestor()).showMessage(
							Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
        			}
        		}
        	}
        });
              
        jLabelGQuantity.setFont(Constants.FONT_PLAIN);
        jLabelGQuantity.setForeground(Constants.FONT_COLOR);
        jLabelGQuantity.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
        jLabelGQuantity.setText(Constants.LANG.getString("Quantity") ); 
        
        jTextFieldGQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldGQuantity.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
        
        jTextAreaGAddInfo.setFont(Constants.FONT_PLAIN);
        jTextAreaGAddInfo.addFocusListener(new FocusAdapter(){
        	public void focusLost(FocusEvent evt) {
        		if (((JTextArea)evt.getSource()) != null){
        			String value = ((JTextArea)evt.getSource()).getText().toString();
        			if (value.length() > 2500){
        				((JTextArea)evt.getSource()).setText("");
        				((InvoiceDetailDialog)((JTextArea)evt.getSource()).getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"),Constants.ERROR_MSG_COLOR);
        			}
        		}
        	}
        });
        jScrollPaneGAddInfo.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneGAddInfo.setAutoscrolls(true);
        jScrollPaneGAddInfo.setOpaque(false);
        jScrollPaneGAddInfo.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneGAddInfo.setViewportView(jTextAreaGAddInfo);
                
        jTextAreaXMLExtAddData.setSize(0,0);
        jTextAreaXMLExtAddData.setFont(Constants.FONT_PLAIN);
        jTextAreaXMLExtAddData.setForeground(Constants.FONT_COLOR);
        jTextAreaXMLExtAddData.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
        jTextAreaXMLExtAddData.setEditable(true);
        jScrollPaneXMLAddData.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneXMLAddData.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
        jScrollPaneXMLAddData.setViewportView(jTextAreaXMLExtAddData);
        
        jButtonXMLAddData.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_accept.jpg"))); 
        jButtonXMLAddData.setToolTipText(Constants.LANG.getString("Verify")); 
        jButtonXMLAddData.setBorderPainted(false);
        jButtonXMLAddData.setContentAreaFilled(false);
        jButtonXMLAddData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseExited(evt);
            }
        });
        jButtonXMLAddData.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		verifyXML(evt);
        	}
        	private void verifyXML(ActionEvent evt) {
        		if (jTextAreaXMLExtAddData.getText() == null || "".equals(jTextAreaXMLExtAddData.getText().trim())){
        			error = "";
        			jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
        		}
        		else{
        			// Valid Invoice
        			InputStream is;
        			try {
        				String streamResEmptyInv = Constants.class.getDeclaredField("I"+FacturaeUtil.getVersionConst(version)+"_EMPTY_PATH").get(null).toString();
						is = this.getClass().getResourceAsStream(streamResEmptyInv);
        				// Extension introduced by user
        				InputStream isExt = null;
        				try {
        					isExt = new ByteArrayInputStream(jTextAreaXMLExtAddData.getText().getBytes("UTF-8"));
        				} catch (UnsupportedEncodingException e) {
        					logger.error("Error due to unsupported encoding: " + e.getMessage());
        				}
        				String[] nodeRoute = {"Invoices","Invoice","Items","InvoiceLine","Extensions"};
        				File f = null;
        				try {
        					f = XMLFacturaeUtil.insertarExtension(is, isExt, nodeRoute);
        				} catch (ValidationException ve) {
        					jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
        					error = ve.getMessage();
        					((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
        				} catch (FileNotFoundException e) {
        					jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
        					error = e.getMessage();
        					((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
        				}
        				if (f != null){
        					try {
        						ValidatorUtil vu = ValidatorUtil.getInstance();
        						if (version != null && !"".equals(version))
        							vu.validate(f, version);
        						error = "";
        						jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
        						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageExtensionAdded"), Constants.OK_MSG_COLOR);
        					} catch (ValidationException ve) {
        						jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
        						error = ve.getMessage();
        						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
        					} catch (SAXException se) {
        						jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
        						error = se.getMessage();
        						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
        					} catch (IOException ioe) {
        						jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
        						error = ioe.getMessage();
        						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
        					}
        				}

        			} catch (SecurityException e1) {
						logger.error("SecurityException trying to get the empty Invoice for version " + version + ": " + e1.getMessage(), e1);
						error = e1.getMessage();
						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
					} catch (NoSuchFieldException e1) {
						logger.error("NoSuchFieldException trying to get the empty Invoice for version " + version + ": " + e1.getMessage(), e1);
						error = e1.getMessage();
						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
					} catch (IllegalArgumentException e1) {
						logger.error("IllegalArgumentException trying to get the empty Invoice for version " + version + ": " + e1.getMessage(), e1);
						error = e1.getMessage();
						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
					} catch (IllegalAccessException e1) {
						logger.error("IllegalAccessException trying to get the empty Invoice for version " + version + ": " + e1.getMessage(), e1);
						error = e1.getMessage();
						((InvoiceDetailDialog)jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
					}
				}
			}
        });
        
        jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg"))); 
        jButtonXMLAddDataZoom.setToolTipText(Constants.LANG.getString("Zoom")); 
        jButtonXMLAddDataZoom.setBorderPainted(false);
        jButtonXMLAddDataZoom.setContentAreaFilled(false);
        jButtonXMLAddDataZoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	InvoiceDetailGeneralPanel.this.mouseExited(evt);
            }
        });
        jButtonXMLAddDataZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	InputExtensionDialog ied = new InputExtensionDialog(null, true, jTextAreaXMLExtAddData.getText(), error);
            	ied.setVisible(true);
            	jTextAreaXMLExtAddData.setText(ied.getValues());
			}
        });
        
        jLabelXMLAddData = new JLabel();
        jLabelXMLAddData.setText(Constants.LANG.getString("InvoiceLine")+" - "+Constants.LANG.getString("Extension")+" (XML)");
        jLabelXMLAddData.setFont(Constants.FONT_PLAIN);
        jLabelXMLAddData.setForeground(Constants.FONT_COLOR);
        
        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelGQuantity, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelGDesc, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                            .add(jLabelGTotAmount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelGUnitPrice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelGGROSS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED,10,Short.MAX_VALUE)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        		.add(11, 11, 11)
                        		.add(org.jdesktop.layout.GroupLayout.TRAILING, jComboBoxGDesc, 40, 68, Short.MAX_VALUE)
                        		.add(jPanel3Layout.createSequentialGroup()
                        			.add(jTextFieldGQuantity, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        			.add(11, 11, 11)
                        			.add(jComboBoxGUnitsOfM, 0, 90, Short.MAX_VALUE)
                                )
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextFieldGUnitPrice, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextFieldGTotAmount, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextFieldGGROSS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED,10,Short.MAX_VALUE)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jTabbedPaneG, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 259, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    	.add(jPanel3Layout.createSequentialGroup()
                    		.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    			.add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPaneGAddInfo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    			.add(jLabelGAddInfo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    		.add(15,15,15)
                    		.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    			.add(jLabelXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 200, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    			.add(jScrollPaneXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    		.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    			.add(jButtonXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    			.add(jButtonXMLAddDataZoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))	
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jComboBoxGDesc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelGDesc))
                        .add(12, 12, 12)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jComboBoxGUnitsOfM, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jTextFieldGQuantity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelGQuantity))
                        .add(12, 12, 12)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldGUnitPrice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelGUnitPrice))
                        .add(12, 12, 12)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldGTotAmount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelGTotAmount))
                        .add(20, 20, 20)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jTextFieldGGROSS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelGGROSS)))
                    .add(jTabbedPaneG, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                    	.add(jLabelGAddInfo)
                    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    	.add(jScrollPaneGAddInfo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                    .add(jPanel3Layout.createSequentialGroup()
                    	.add(jLabelXMLAddData)
                    	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    	.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    		.add(jScrollPaneXMLAddData, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    		.add(jPanel3Layout.createSequentialGroup()
                    			.add(jButtonXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        		.add(jButtonXMLAddDataZoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
	}
	
	private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void loadDecimalConfiguration(){
    	try{
    		decimalProps = new Properties();
    		decimalProps.load(this.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact"+codeVersion+"d_resource")));
    	}
    	catch(Exception e){
    		 logger.error("An exception occurred when loading the properties of the configuration file: " + e.getMessage());
    	}
    }
    
    private void setItemData(java.awt.event.ActionEvent evt) {
    	if (((JComboBox)evt.getSource()).getSelectedItem() != null && !"".equals(((JComboBox)evt.getSource()).getSelectedItem().toString())){
    		String value = ((JComboBox)evt.getSource()).getSelectedItem().toString(), itemId = "-1";
    		SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT ITEM_ID,PRICE_WITHOUT_TAX,UNIT,ADDITIONAL_INFO,SPECIAL_TAX_EVENT_CODE,SPECIAL_TAX_EVENT_REASON,ARTICLE_CODE FROM ITEM WHERE DESCRIPTION = '" + DatabaseUtil.escapeChars(value) + "' AND ITEM_ID IN (SELECT MAX(ITEM_ID) FROM ITEM GROUP BY DESCRIPTION)");
    		List<?> ls = s.list();
    		if (ls != null && ls.size() > 0) {
    			itemId = ((Object[])ls.get(0))[0].toString();
    			jTextFieldGUnitPrice.setBackground(Color.white);
    			jTextFieldGUnitPrice.setText(((Object[])ls.get(0))[1].toString());
    			jTextFieldGUnitPrice.setCaretPosition(0);
    			if (((Object[])ls.get(0))[2] != null) {
    				int index = Integer.parseInt(((Object[])ls.get(0))[2].toString()) - 1;
    				if (index >= 0 && (index + 1) <= jComboBoxGUnitsOfM.getItemCount())
    					jComboBoxGUnitsOfM.setSelectedIndex(index);
    				else
    					jComboBoxGUnitsOfM.setSelectedIndex(4);
    			}
    			else
					jComboBoxGUnitsOfM.setSelectedIndex(4);
    			
    			if (((Object[])ls.get(0))[3] != null)
    				jTextAreaGAddInfo.setText(((Object[])ls.get(0))[3].toString());
    			if (((Object[])ls.get(0))[6] != null && ( version.equals(Constants.VERSION32) || version.equals(Constants.VERSION321)) )
    				((Invoice32DetailOtherDataPanel)((InvoiceDetailDialog)this.getTopLevelAncestor()).getPanelOtherData()).getJTextFieldOArticle().setText(((Object[])ls.get(0))[6].toString());
    			
    			jTextFieldGUnitPrice.requestFocus();
    			jTextFieldGTotAmount.requestFocus();
    			jTextFieldGQuantity.requestFocus();
    		}
    		
    		int count = ((DefaultTableModel)((InvoiceDetailDialog)this.getTopLevelAncestor()).getPanelTaxes().getJTableTOutputs().getModel()).getRowCount();
    		for (int i = count-1 ; i >= 0; i--)
    			((DefaultTableModel)((InvoiceDetailDialog)this.getTopLevelAncestor()).getPanelTaxes().getJTableTOutputs().getModel()).removeRow(i);
    		
			if (version.equals(Constants.VERSION32)) {
				// Si no hay un motivo: SPECIAL_TAX_EVENT_CODE, SPECIAL_TAX_EVENT_REASON 
				if (((Object[]) ls.get(0))[4] == null) {
					s = FacturaeManager.getInstance().executeQuery(
							"SELECT DISTINCT TYPE,RATE,SURCHARGE FROM TAX,ITEM_TAX,ITEM WHERE TAX.TAX_ID = ITEM_TAX.TAX_ID AND ITEM_ID = '" + itemId + "'");
					ls = s.list();
					if (ls != null && ls.size() > 0) {
						int count2 = ls.size();
						Object[] o = null;
						// If the item has predefined taxes they will be loaded without special taxable base. If it is required,
						// the user will have to delete the tax to introduce a new one.
						((InvoiceDetailDialog) this.getTopLevelAncestor()).getPanelTaxes().setSpecialBaseRequired(false);
						for (int i = 0; i < count2; i++) {
							o = new Object[8];
							String taxesType = Constants.APP_PROP.getProperty("taxesType" + codeVersion);
							o[0] = Constants.LANG.getString(taxesType.split(";")[Integer.valueOf(((Object[]) ls.get(i))[0].toString()) - 1]).split(" ")[0]
									+ " - " + ((Object[]) ls.get(i))[0].toString();
							o[1] = ((Object[]) ls.get(i))[1].toString();
							if (jTextFieldGGROSS != null && !"".equals(jTextFieldGGROSS.getText()))
								o[2] = jTextFieldGGROSS.getText();
							else
								o[2] = "0.0";
							o[3] = DoubleUtil.getDoubleFromPrettyNumber(o[1].toString()) * DoubleUtil.getDoubleFromPrettyNumber(o[2].toString()) / 100.00;
							o[4] = "";
							o[5] = "";
							o[6] = ((Object[]) ls.get(i))[2].toString();
							o[7] = DoubleUtil.getDoubleFromPrettyNumber(o[6].toString()) * DoubleUtil.getDoubleFromPrettyNumber(o[2].toString()) / 100.00;
							((DefaultTableModel) ((InvoiceDetailDialog) this.getTopLevelAncestor()).getPanelTaxes().getJTableTOutputs().getModel()).addRow(o);
						}
					}
				} else {
					Invoice32DetailTaxesPanel taxPanel = ((Invoice32DetailTaxesPanel) ((InvoiceDetailDialog) this.getTopLevelAncestor()).getPanelTaxes());
					taxPanel.getJComboBoxSpecial().setSelectedIndex(Integer.parseInt(((Object[]) ls.get(0))[4].toString()));
					if (((Object[]) ls.get(0))[5] != null) {
						taxPanel.getJTextFieldSpecial().setText(((Object[]) ls.get(0))[5].toString());
						taxPanel.getJTextFieldSpecial().setCaretPosition(0);
					}
				}

			}
		}
    }
        
    private void addDiscount(java.awt.event.ActionEvent evt) {
    	// The gross amount and total cost
    	String tibString = jTextFieldGGROSS.getText();
    	Double tib = null;
    	String totString = jTextFieldGTotAmount.getText();
    	Double tot = null;
    	// If the total amount text and total cost is not null
    	Double oldchargeAmount = chargeAmount;
    	if (tibString != null && !"".equals(tibString.trim())) {
    		try {
    			tib = DoubleUtil.getDoubleFromPrettyNumber(tibString);
    			tib = DoubleUtil.roundByVersion(tib, 2, version);
    			tot = DoubleUtil.getDoubleFromPrettyNumber(totString);
    			tot = DoubleUtil.roundByVersion(tot, 2, version);
    			InputDiscountDialog igd = new InputDiscountDialog(null, true, tib, tot, version, false);
    	    	
    	    	igd.setVisible(true);
    	    	
    	    	String[] result = igd.getValues();
    	    		
    	    	String item = result[0];
    	    	String rate = result[1];
    	    	String amount = result[2];
				if (item != null && !item.trim().equals("")) {
					((javax.swing.table.DefaultTableModel) jTableGDiscounts.getModel()).addRow(new Object[] { item, rate, amount });
					Double discount = DoubleUtil.getDoubleFromPrettyNumber(result[2]);
					Double initialAmount = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGGROSS.getText());
					discountAmount = DoubleUtil.roundByVersion( (discountAmount + discount), Integer.parseInt(decimalProps.getProperty("item_discount")), version);
					jTextFieldGGROSS.setText(DoubleUtil.roundByVersionFormatted(
							new Double(initialAmount - discount), Integer.parseInt(decimalProps.getProperty("item_gross_cost")), version));
					jTextFieldGGROSS.setCaretPosition(0);
					InvoiceDetailDialog detailDialog = (InvoiceDetailDialog) this.getTopLevelAncestor();
					detailDialog.getPanelTaxes().grossAmountChanged();
					detailDialog.showMessage(Constants.LANG.getString("OKMessageDiscountAdded"), Constants.OK_MSG_COLOR);
				} else {
					// Operation cancelled by user
					((InvoiceDetailDialog) this.getTopLevelAncestor())
							.showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);
				}
    		} catch (NumberFormatException nfe) {
    			logger.error("Error due to unexpected format: " + nfe.getMessage());
    			return;
			} finally {
				if (chargeAmount != oldchargeAmount) {
					removeTaxes();
    			}
    		}
		} else {
			((InvoiceDetailDialog) this.getTopLevelAncestor()).showMessage(
				Constants.LANG.getString("NOOKMessageAmountRequired"), Constants.ERROR_MSG_COLOR);
		}
    }
    
    private void delDiscount(java.awt.event.ActionEvent evt) {
    	Double oldchargeAmount = chargeAmount;
    	int row = jTableGDiscounts.getSelectedRow();
    	if (row >= 0){
    		Double discount = DoubleUtil.getDoubleFromPrettyNumber(jTableGDiscounts.getModel().getValueAt(row, 2).toString());
    		Double initialAmount = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGGROSS.getText());
    		discountAmount = DoubleUtil.roundByVersion((discountAmount - discount), Integer.parseInt(decimalProps.getProperty("item_discount")), version);
			jTextFieldGGROSS.setText(DoubleUtil.roundByVersionFormatted(new Double(initialAmount + discount),
				Integer.parseInt(decimalProps.getProperty("item_gross_cost")), version));
			jTextFieldGGROSS.setCaretPosition(0);
			InvoiceDetailDialog detailDialog = (InvoiceDetailDialog) this.getTopLevelAncestor();
			detailDialog.getPanelTaxes().grossAmountChanged();
			((javax.swing.table.DefaultTableModel)jTableGDiscounts.getModel()).removeRow(row);
			((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageDiscountDeleted"),Constants.OK_MSG_COLOR);
    	}
    	else{
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageColumnNotSelected"),Constants.ERROR_MSG_COLOR);
    	}
		if (chargeAmount != oldchargeAmount) {
			removeTaxes();
		}     	
    }
    
    private void addCharge(java.awt.event.ActionEvent evt) {
    	// The total amount is needed for calculations
    	String tibString = jTextFieldGTotAmount.getText();
    	Double tib = null;
    	Double oldchargeAmount = chargeAmount;
    	if (tibString != null && !"".equals(tibString)) {
    		try {
    			tib = DoubleUtil.getDoubleFromPrettyNumber(tibString);
    			tib = DoubleUtil.roundByVersion(tib, 2, version);
    			// The gross amount must be positive to apply a discount
    			InputChargeDialog icd = new InputChargeDialog(null, true, tib, version, false);

    			icd.setVisible(true);

    			String[] result = icd.getValues();

    			String item = result[0];
    			String rate = result[1];
    			String amount = result[2];

    			if (item != null && !"".equals(item)){
    				((javax.swing.table.DefaultTableModel)jTableGCharges.getModel()).addRow(new Object[] {item, rate, amount});
    				Double charge = DoubleUtil.getDoubleFromPrettyNumber(result[2]);
    				Double initialAmount = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGGROSS.getText());
    				chargeAmount = DoubleUtil.roundByVersion((chargeAmount + charge), Integer.parseInt(decimalProps.getProperty("item_charge")), version);
					jTextFieldGGROSS.setText(DoubleUtil.roundByVersionFormatted(new Double(initialAmount + charge), Integer.parseInt(decimalProps.getProperty("item_gross_cost")), version));
					jTextFieldGGROSS.setCaretPosition(0);
					InvoiceDetailDialog detailDialog = (InvoiceDetailDialog) this.getTopLevelAncestor();
					detailDialog.getPanelTaxes().grossAmountChanged();
					detailDialog.showMessage(Constants.LANG.getString("OKMessageChargeAdded"),Constants.OK_MSG_COLOR);
    			} else
    	    		// Operation cancelled by user
    	    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"),Constants.OK_MSG_COLOR);

    		} catch (NumberFormatException nfe) {
    			logger.error("Error due to unexpected format: " + nfe.getMessage());
    			return;
			} finally {
				if (chargeAmount != oldchargeAmount) {
					removeTaxes();
    			}
    		}
    	} else {
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageAmountRequired"),Constants.ERROR_MSG_COLOR);
    	}
    }
    
    private void delCharge(java.awt.event.ActionEvent evt) {
    	Double oldchargeAmount = chargeAmount;
    	int row = jTableGCharges.getSelectedRow();
    	if (row >= 0){
    		Double charge = DoubleUtil.getDoubleFromPrettyNumber(jTableGCharges.getModel().getValueAt(row, 2).toString());
    		Double initialAmount = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGGROSS.getText());
    		chargeAmount = DoubleUtil.roundByVersion((chargeAmount - charge), Integer.parseInt(decimalProps.getProperty("item_charge")), version);
    		jTextFieldGGROSS.setText(DoubleUtil.roundByVersionFormatted(new Double(initialAmount - charge), 
    				Integer.parseInt(decimalProps.getProperty("item_gross_cost")), version));
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).getPanelTaxes().grossAmountChanged();
    		jTextFieldGGROSS.setCaretPosition(0);
    		((javax.swing.table.DefaultTableModel)jTableGCharges.getModel()).removeRow(row);
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageChargeDeleted"),Constants.OK_MSG_COLOR);
    	} else
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageColumnNotSelected"),Constants.ERROR_MSG_COLOR);
		if (chargeAmount != oldchargeAmount) {
			removeTaxes();
		}	
    }
    
    public void totalChanged() {
    	Double total = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGTotAmount.getText()); 
    	String value = null;
    	Double d = 0.0;
    	
    	// The count is reset to 0
    	discountAmount = 0.0;
    	chargeAmount = 0.0;
    	
    	// Discount table is actualizated
    	TableModel modelDis = jTableGDiscounts.getModel();
    	for (int i = 0; i < modelDis.getRowCount(); ++i) {
    		// Rate read
    		if (modelDis.getValueAt(i, 1) != null)
    			value = modelDis.getValueAt(i, 1).toString();
    		else
    			value = "";
    		if (value != null && !"".equals(value.trim())) { // If rate exists, amount may be recalculated
    			d = DoubleUtil.getDoubleFromPrettyNumber(value);
    			d = (d * total) / 100;
    			modelDis.setValueAt(DoubleUtil.roundByVersionFormatted(d, Integer.parseInt(decimalProps.getProperty("item_discount")), version), i, 2);
    			discountAmount = discountAmount + d;
    		} else {
    			d = DoubleUtil.getDoubleFromPrettyNumber(modelDis.getValueAt(i, 2).toString());
    			discountAmount = discountAmount + d;
    		}
    	}
    	
    	// Charge table is actualizated
    	TableModel modelCha = jTableGCharges.getModel();
    	for (int i = 0; i < modelCha.getRowCount(); ++i) {
    		// Rate read
    		if (modelCha.getValueAt(i, 1) != null)
    			value = modelCha.getValueAt(i, 1).toString();
    		else
    			value = "";
    		if (value != null && !"".equals(value.trim())) { // If rate exists, amount may be recalculated
    			d = DoubleUtil.getDoubleFromPrettyNumber(value);
    			d = (d * total) / 100;
    			modelCha.setValueAt(DoubleUtil.roundByVersionFormatted(d, Integer.parseInt(decimalProps.getProperty("item_charge")), version), i, 2);
    			chargeAmount = chargeAmount + d;
    		} else {
    			d = DoubleUtil.getDoubleFromPrettyNumber(modelCha.getValueAt(i, 2).toString());
    			chargeAmount = chargeAmount + d;
    		}
    	}
    	jTextFieldGGROSS.setText(DoubleUtil.roundByVersionFormatted(total - discountAmount + chargeAmount, 
    			Integer.parseInt(decimalProps.getProperty("item_gross_cost")), version));
    	jTextFieldGGROSS.setCaretPosition(0);
    	((InvoiceDetailDialog)this.getTopLevelAncestor()).getPanelTaxes().grossAmountChanged();
    }
    
    /**
     * Quantity: 3.1.6.1.14
     * Cantidad. Número de unidades servidas/prestadas
     * 
     */
    private class QuantityValidator extends FocusAdapter {
    	
		private double dQuantity;
		
		public void focusGained(FocusEvent evt) {
			try {
				dQuantity = DoubleUtil.getDoubleFromPrettyNumber(((JTextField) evt.getSource()).getText());
			} catch (NumberFormatException nfe) {}
		}
		
		public void focusLost(FocusEvent evt){
			try {
				JTextField jTextFieldSource = (JTextField) evt.getSource();
				String value = jTextFieldSource.getText();
				// The unit price must be double type
				double q = DoubleUtil.getDoubleFromPrettyNumber(value);
				// JTextFields update
				
				if (q < 0) {
					q = 0.0;
					((InvoiceDetailDialog) jTextFieldGQuantity.getTopLevelAncestor()).showMessage(
							Constants.LANG.getString("NOOKParamNegative"),Constants.ERROR_MSG_COLOR);
				}
				if (jTextFieldGUnitPrice != null
						&& !jTextFieldGUnitPrice.getText().equals("")) {
					Double tot = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGUnitPrice.getText())* q;
					jTextFieldGTotAmount.setText(DoubleUtil.roundByVersionFormatted(tot, Integer.parseInt(decimalProps.getProperty("item_total_cost")), version));
					jTextFieldGGROSS.setCaretPosition(0);
					totalChanged();
					((InvoiceDetailDialog) jTextFieldGQuantity.getTopLevelAncestor()).getPanelTaxes().grossAmountChanged();
				}
				jTextFieldSource.setText(DoubleUtil.formatDecimal(q, -1));
				jTextFieldSource.setCaretPosition(0);
			} catch (NumberFormatException nfe) {
				if (((JTextField) evt.getSource()).getText() != null) {
					boolean empty = ((JTextField) evt.getSource()).getText().trim().equals("");
					((JTextField) evt.getSource()).setText("");
					jTextFieldGTotAmount.setText("");
					jTextFieldGGROSS.setText("");
					if (!empty)
						((InvoiceDetailDialog) jTextFieldGQuantity.getTopLevelAncestor()).showMessage(
								Constants.LANG.getString("NOOKParamNumericFormat"),Constants.ERROR_MSG_COLOR);
				}
			} finally {
				try {
					if (version.equals(Constants.VERSION321)) {
						double changedValue = DoubleUtil.getDoubleFromPrettyNumber(((JTextField) evt.getSource()).getText());
						if(dQuantity!=changedValue)
							removeTaxes();
					}
				} catch (NumberFormatException nfe) {}
			}
		}
	}
    
	private class UnitPriceValidator extends FocusAdapter {
		private double dUnitPrice;

		public void focusGained(FocusEvent evt) {
			try {
				dUnitPrice = DoubleUtil.getDoubleFromPrettyNumber(((JTextField) evt.getSource()).getText());
			} catch (NumberFormatException nfe) {}
		}
		public void focusLost(FocusEvent evt) {
			try {
				JTextField jTextFieldSource = (JTextField) evt.getSource();
				String value = jTextFieldSource.getText();
				// The unit price must be double type
				Double up = DoubleUtil.getDoubleFromPrettyNumber(value);
				// JTextFields update
				jTextFieldSource.setText(DoubleUtil.roundByVersionFormatted(up, Integer.parseInt(decimalProps.getProperty("item_unit_price")), version));
				jTextFieldSource.setCaretPosition(0);
				if (jTextFieldGQuantity != null && !jTextFieldGQuantity.getText().equals("")) {
					//Double tot = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGQuantity.getText()) * up; ctg
					Double tot = DoubleUtil.getDoubleFromPrettyNumber(jTextFieldGQuantity.getText()) * up;
					jTextFieldGTotAmount.setText(DoubleUtil.roundByVersionFormatted(tot,
						Integer.parseInt(decimalProps.getProperty("item_total_cost")), version));
					jTextFieldGTotAmount.setCaretPosition(0);
					totalChanged();
					((InvoiceDetailDialog) jTextFieldGUnitPrice.getTopLevelAncestor()).getPanelTaxes().grossAmountChanged();
				}
			} catch (NumberFormatException nfe) {
				if (((JTextField) evt.getSource()).getText() != null) {
					boolean empty = ((JTextField) evt.getSource()).getText().trim().equals("");
					((JTextField) evt.getSource()).setText("");
					jTextFieldGTotAmount.setText("");
					jTextFieldGGROSS.setText("");
					if (!empty)
						((InvoiceDetailDialog) jTextFieldGUnitPrice.getTopLevelAncestor()).showMessage(
							Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
				}
			} finally {
				try {
					if (version.equals(Constants.VERSION321)) {
						double changedValue = DoubleUtil.getDoubleFromPrettyNumber(((JTextField) evt.getSource()).getText());
						if(dUnitPrice!=changedValue)
							removeTaxes();
					}
				} catch (NumberFormatException nfe) {}
			}
		}
	}
	
	public void removeTaxes() {
		if(version.equals(Constants.VERSION321)){
			DefaultTableModel tableModel =	((DefaultTableModel) ((InvoiceDetailDialog) jTextFieldGTotAmount
					.getTopLevelAncestor()).getPanelTaxes().getJTableTOutputs().getModel());
			if (tableModel.getRowCount() > 0) {
				for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
					tableModel.removeRow(i);
				}
			}
		}
	}	
    
    // Getters for visible components
    public javax.swing.JTable getJTableGDiscounts() {
		return jTableGDiscounts;
	}

	public javax.swing.JTable getJTableGCharges() {
		return jTableGCharges;
	}

	public javax.swing.JComboBox getJComboBoxGDesc() {
		return jComboBoxGDesc;
	}

	public javax.swing.JTextField getJTextFieldGQuantity() {
		return jTextFieldGQuantity;
	}

	public javax.swing.JTextField getJTextFieldGUnitPrice() {
		return jTextFieldGUnitPrice;
	}

	public javax.swing.JTextField getJTextFieldGTotAmount() {
		return jTextFieldGTotAmount;
	}
	
	public javax.swing.JTextField getJTextFieldGGROSS() {
		return jTextFieldGGROSS;
	}
	
	public javax.swing.JTextArea getJTextAreaGAddInfo() {
		return jTextAreaGAddInfo;
	}
	
	public javax.swing.JComboBox getJComboBoxGUnitsOfM() {
		return jComboBoxGUnitsOfM;
	}
	
	public javax.swing.JTextArea getJTextAreaXMLExtAddData() {
		return jTextAreaXMLExtAddData;
	}
	
	public javax.swing.JButton getJButtonXMLAddDataZoom () {
		return jButtonXMLAddDataZoom;
	}
	
	public javax.swing.JButton getJButtonXMLAddData () {
		return jButtonXMLAddData;
	}
	
	public javax.swing.JButton getJButtonGAddDiscount() {
		return jButtonGAddDiscount;
	}

	public javax.swing.JButton getJButtonGDelDiscount() {
		return jButtonGDelDiscount;
	}

	public javax.swing.JButton getJButtonGAddCharge() {
		return jButtonGAddCharge;
	}

	public javax.swing.JButton getJButtonGDelCharge() {
		return jButtonGDelCharge;
	}
	
	// JPanel
	private javax.swing.JPanel jPanelGDiscounts;
    private javax.swing.JPanel jPanelGCharges;
    // JScrollPane
    private javax.swing.JScrollPane jScrollPaneGDiscounts;
    private javax.swing.JScrollPane jScrollPaneGCharges;
    private javax.swing.JScrollPane jScrollPaneGAddInfo;
    private javax.swing.JScrollPane jScrollPaneXMLAddData;
    
    // JTabbedPane
    private javax.swing.JTabbedPane jTabbedPaneG;
    // JTable
    private javax.swing.JTable jTableGDiscounts;
    private javax.swing.JTable jTableGCharges;
    // JTextField
    private javax.swing.JTextField jTextFieldGQuantity;
    private javax.swing.JTextField jTextFieldGUnitPrice;
    private javax.swing.JTextField jTextFieldGTotAmount;
    private javax.swing.JTextField jTextFieldGGROSS;
    private javax.swing.JTextArea jTextAreaGAddInfo;
    private javax.swing.JTextArea jTextAreaXMLExtAddData;
    // JButton
    private javax.swing.JButton jButtonGAddDiscount;
    private javax.swing.JButton jButtonGDelDiscount;
    private javax.swing.JButton jButtonGAddCharge;
    private javax.swing.JButton jButtonGDelCharge;
    private javax.swing.JButton jButtonXMLAddData;
    private javax.swing.JButton jButtonXMLAddDataZoom;
    // JComboBox
    private javax.swing.JComboBox jComboBoxGUnitsOfM;
    private javax.swing.JComboBox jComboBoxGDesc;
    // JLabel
    private javax.swing.JLabel jLabelGUnitPrice;
    private javax.swing.JLabel jLabelGTotAmount;
    private javax.swing.JLabel jLabelGGROSS;
    private javax.swing.JLabel jLabelGAddInfo;
    private javax.swing.JLabel jLabelGQuantity;
    private javax.swing.JLabel jLabelGDesc;
    private javax.swing.JLabel jLabelXMLAddData;
    
    private String error;
    
    public Double discountAmount;
    public Double chargeAmount;
}
