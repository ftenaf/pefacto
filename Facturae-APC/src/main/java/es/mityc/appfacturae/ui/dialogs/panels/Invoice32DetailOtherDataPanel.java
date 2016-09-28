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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.wannawork.jcalendar.JCalendarComboBox;
import es.mityc.appfacturae.ui.dialogs.InputDelivery32Dialog;
import es.mityc.appfacturae.ui.dialogs.InvoiceDetailDialog;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.utils.constants.Constants;

public class Invoice32DetailOtherDataPanel extends JPanel implements InvoiceDetailOtherDataPanel{
	
	public Invoice32DetailOtherDataPanel(){
		super();
		initComponents();
	}
	
	private void initComponents(){
		jLabelOIssCRef = new javax.swing.JLabel();
        jLabelOIssORef = new javax.swing.JLabel();
        jLabelORecCRef = new javax.swing.JLabel();
        jLabelORecORef = new javax.swing.JLabel();
        jLabelOSequence = new javax.swing.JLabel();
        jLabelOIssCDat = new javax.swing.JLabel();
        jLabelOIssODat = new javax.swing.JLabel();
        jLabelORecCDat = new javax.swing.JLabel();
        jLabelORecODat = new javax.swing.JLabel();
        jLabelOFileRef = new javax.swing.JLabel();
        jLabelOFileDat = new javax.swing.JLabel();
        jLabelOArticle = new javax.swing.JLabel();
        jTextFieldOIssCRef = new javax.swing.JTextField();
        jTextFieldOIssORef = new javax.swing.JTextField();
        jTextFieldORecCRef = new javax.swing.JTextField();
        jTextFieldORecORef = new javax.swing.JTextField();
        jTextFieldOOrdRef = new javax.swing.JTextField();
        jTextFieldOFileRef = new javax.swing.JTextField();
        jTextFieldOArticle = new javax.swing.JTextField();
        jScrollPaneODelivery = new javax.swing.JScrollPane();
        jTableODelivery = new javax.swing.JTable();
        jButtonOAddDelivery = new javax.swing.JButton();
        jButtonODelDelivery = new javax.swing.JButton();
        
        jTabbedPaneRef = new javax.swing.JTabbedPane();
        
        jPanelIssuer = new javax.swing.JPanel();
        jPanelReceiver = new javax.swing.JPanel();
        jPanelFile = new javax.swing.JPanel();
        
    	jCalendarComboBoxOIssC = new de.wannawork.jcalendar.JCalendarComboBox();
    	jCalendarComboBoxOIssO = new de.wannawork.jcalendar.JCalendarComboBox();
    	jCalendarComboBoxORecC = new de.wannawork.jcalendar.JCalendarComboBox();
    	jCalendarComboBoxORecO = new de.wannawork.jcalendar.JCalendarComboBox();
    	jCalendarComboBoxOFile = new de.wannawork.jcalendar.JCalendarComboBox();
        
    	((JFormattedTextField)((JSpinner)jCalendarComboBoxOIssC.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapterDate());
		((JFormattedTextField)((JSpinner)jCalendarComboBoxOIssO.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapterDate());
    	((JFormattedTextField)((JSpinner)jCalendarComboBoxORecC.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapterDate());
		((JFormattedTextField)((JSpinner)jCalendarComboBoxORecO.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapterDate());
		((JFormattedTextField)((JSpinner)jCalendarComboBoxOFile.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapterDate());
		
		jCalendarComboBoxOIssC.setVisible(false);
		jCalendarComboBoxOIssO.setVisible(false);
		jCalendarComboBoxORecC.setVisible(false);
		jCalendarComboBoxORecO.setVisible(false);
		jCalendarComboBoxOFile.setVisible(false);
		
		jRadioButtonOIssC = new javax.swing.JRadioButton();
    	jRadioButtonOIssO = new javax.swing.JRadioButton();
    	jRadioButtonORecC = new javax.swing.JRadioButton();
    	jRadioButtonORecO = new javax.swing.JRadioButton();
    	jRadioButtonOFile = new javax.swing.JRadioButton();
    	
    	Insets ins = new Insets(0,0,0,10);
    	
    	jRadioButtonOIssC.setMargin(ins);
    	jRadioButtonOIssC.setContentAreaFilled(false);
    	jRadioButtonOIssC.addActionListener(new ActionListenerDate(jCalendarComboBoxOIssC));
    	
    	jRadioButtonOIssO.setMargin(ins);
    	jRadioButtonOIssO.setContentAreaFilled(false);
    	jRadioButtonOIssO.addActionListener(new ActionListenerDate(jCalendarComboBoxOIssO));
    	
    	jRadioButtonORecC.setMargin(ins);
    	jRadioButtonORecC.setContentAreaFilled(false);
    	jRadioButtonORecC.addActionListener(new ActionListenerDate(jCalendarComboBoxORecC));
    	
    	jRadioButtonORecO.setMargin(ins);
    	jRadioButtonORecO.setContentAreaFilled(false);
    	jRadioButtonORecO.addActionListener(new ActionListenerDate(jCalendarComboBoxORecO));
    	
    	jRadioButtonOFile.setMargin(ins);
    	jRadioButtonOFile.setContentAreaFilled(false);
    	jRadioButtonOFile.addActionListener(new ActionListenerDate(jCalendarComboBoxOFile));
    	
        jPanelIssuer.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelReceiver.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelFile.setBackground(Constants.BKG_MAIN_COLOR);
        
        this.setBackground(Constants.BKG_MAIN_COLOR);
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("OtherData") , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

        jLabelOIssCRef.setFont(Constants.FONT_PLAIN);
        jLabelOIssCRef.setForeground(Constants.FONT_COLOR);
        jLabelOIssCRef.setText(Constants.LANG.getString("ContractReference") ); 

        jLabelOIssORef.setFont(Constants.FONT_PLAIN);
        jLabelOIssORef.setForeground(Constants.FONT_COLOR);
        jLabelOIssORef.setText(Constants.LANG.getString("TransactionReference") ); 

        jLabelORecCRef.setFont(Constants.FONT_PLAIN);
        jLabelORecCRef.setForeground(Constants.FONT_COLOR);
        jLabelORecCRef.setText(Constants.LANG.getString("ContractReference") ); 

        jLabelORecORef.setFont(Constants.FONT_PLAIN);
        jLabelORecORef.setForeground(Constants.FONT_COLOR);
        jLabelORecORef.setText(Constants.LANG.getString("TransactionReference") ); 

        jLabelOSequence.setFont(Constants.FONT_PLAIN);
        jLabelOSequence.setForeground(Constants.FONT_COLOR);
        jLabelOSequence.setText(Constants.LANG.getString("PurchaseOderReference") ); 
        
        jLabelOIssCDat.setFont(Constants.FONT_PLAIN);
        jLabelOIssCDat.setForeground(Constants.FONT_COLOR);
        jLabelOIssCDat.setText(Constants.LANG.getString("IssuerContractDate") );
        
        jLabelOIssODat.setFont(Constants.FONT_PLAIN);
        jLabelOIssODat.setForeground(Constants.FONT_COLOR);
        jLabelOIssODat.setText(Constants.LANG.getString("IssuerTransactionDate") );
        
        jLabelORecCDat.setFont(Constants.FONT_PLAIN);
        jLabelORecCDat.setForeground(Constants.FONT_COLOR);
        jLabelORecCDat.setText(Constants.LANG.getString("ReceiverContractDate") ); 
        
        jLabelORecODat.setFont(Constants.FONT_PLAIN);
        jLabelORecODat.setForeground(Constants.FONT_COLOR);
        jLabelORecODat.setText(Constants.LANG.getString("ReceiverTransactionDate") ); 
        
        jLabelOFileRef.setFont(Constants.FONT_PLAIN);
        jLabelOFileRef.setForeground(Constants.FONT_COLOR);
        jLabelOFileRef.setText(Constants.LANG.getString("FileReference") ); 
        
        jLabelOFileDat.setFont(Constants.FONT_PLAIN);
        jLabelOFileDat.setForeground(Constants.FONT_COLOR);
        jLabelOFileDat.setText(Constants.LANG.getString("FileDate") ); 
        
        
        jLabelOArticle.setFont(Constants.FONT_PLAIN);
        jLabelOArticle.setForeground(Constants.FONT_COLOR);
        jLabelOArticle.setText(Constants.LANG.getString("ArticleCode")); 
        
        LenghtValidator lv = new LenghtValidator();
        jTextFieldOIssCRef.addFocusListener(lv);
        jTextFieldOIssORef.addFocusListener(lv);
        jTextFieldORecCRef.addFocusListener(lv);
        jTextFieldORecORef.addFocusListener(lv);
        
        jTextFieldOIssCRef.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldOIssORef.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldORecCRef.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldORecORef.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldOFileRef.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldOArticle.setHorizontalAlignment(SwingConstants.RIGHT);

        jScrollPaneODelivery.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneODelivery.setAutoscrolls(true);
        jScrollPaneODelivery.setOpaque(false);
        jScrollPaneODelivery.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableODelivery.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                Constants.LANG.getString("DeliveryNote"),
                Constants.LANG.getString("Date")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false,
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableODelivery.setGridColor(Constants.SELECTION_COLOR);
        jTableODelivery.getTableHeader().setReorderingAllowed(false);
        jScrollPaneODelivery.setViewportView(jTableODelivery);
        jTableODelivery.getColumnModel().getColumn(0).setResizable(false);
        jTableODelivery.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableODelivery.getColumnModel().getColumn(1).setResizable(false);
        jTableODelivery.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        
        for (int i = 0 ; i < jTableODelivery.getColumnCount() ; i++){
            jTableODelivery.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jButtonOAddDelivery.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonOAddDelivery.setForeground(Constants.BKG_MAIN_COLOR);
        jButtonOAddDelivery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_plus.jpg"))); 
        jButtonOAddDelivery.setToolTipText(Constants.LANG.getString("Add")); 
        jButtonOAddDelivery.setBorderPainted(false);
        jButtonOAddDelivery.setContentAreaFilled(false);
        jButtonOAddDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDelivery(evt);
            }
        });
        jButtonOAddDelivery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Invoice32DetailOtherDataPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	Invoice32DetailOtherDataPanel.this.mouseExited(evt);
            }
        });

        jButtonODelDelivery.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonODelDelivery.setForeground(Constants.BKG_MAIN_COLOR);
        jButtonODelDelivery.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_minus.jpg"))); 
        jButtonODelDelivery.setToolTipText(Constants.LANG.getString("Remove")); 
        jButtonODelDelivery.setBorderPainted(false);
        jButtonODelDelivery.setContentAreaFilled(false);
        jButtonODelDelivery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	Invoice32DetailOtherDataPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	Invoice32DetailOtherDataPanel.this.mouseExited(evt);
            }
        });
        jButtonODelDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delDelivery(evt);
            }
        });
        
        PurchaseOrderValidator pov = new PurchaseOrderValidator();
        jTextFieldOOrdRef.addFocusListener(pov);
        jTextFieldOOrdRef.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
        jTextFieldOOrdRef.setHorizontalAlignment(SwingConstants.RIGHT);
        
        org.jdesktop.layout.GroupLayout jPanelIssuerLayout = new org.jdesktop.layout.GroupLayout(jPanelIssuer);
        jPanelIssuer.setLayout(jPanelIssuerLayout);
        jPanelIssuerLayout.setHorizontalGroup(
        		jPanelIssuerLayout.createSequentialGroup()
        			.addContainerGap()
        			.add(jPanelIssuerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        				.add(jLabelOIssCRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.add(jPanelIssuerLayout.createSequentialGroup()
        					.add(jRadioButtonOIssC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        					.add(jLabelOIssCDat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jLabelOIssORef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanelIssuerLayout.createSequentialGroup()
                        	.add(jRadioButtonOIssO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        	.add(jLabelOIssODat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelIssuerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    	.add(jTextFieldOIssCRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)    		
                    	.add(jCalendarComboBoxOIssC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    	.add(jTextFieldOIssORef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)
                    	.add(jCalendarComboBoxOIssO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()
        );
        jPanelIssuerLayout.setVerticalGroup(
        		jPanelIssuerLayout.createSequentialGroup()
        			.addContainerGap()
        			.add(jPanelIssuerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        				.add(jLabelOIssCRef)
        				.add(jTextFieldOIssCRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        			.add(jPanelIssuerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        				.add(jRadioButtonOIssC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.add(jLabelOIssCDat)
        				.add(jCalendarComboBoxOIssC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			.add(10,10,10)
        			.add(jPanelIssuerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        				.add(jLabelOIssORef)
        				.add(jTextFieldOIssORef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        			.add(jPanelIssuerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        				.add(jRadioButtonOIssO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        				.add(jLabelOIssODat)
        				.add(jCalendarComboBoxOIssO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        			.addContainerGap()
        );
        
        org.jdesktop.layout.GroupLayout jPanelReceiverLayout = new org.jdesktop.layout.GroupLayout(jPanelReceiver);
        jPanelReceiver.setLayout(jPanelReceiverLayout);
        jPanelReceiverLayout.setHorizontalGroup(
        		jPanelReceiverLayout.createSequentialGroup()
	        		.addContainerGap()
	        		.add(jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	    				.add(jLabelORecCRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	    				.add(jPanelReceiverLayout.createSequentialGroup()
	    					.add(jRadioButtonORecC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)	
	    					.add(jLabelORecCDat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
	                    .add(jLabelORecORef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    .add(jPanelReceiverLayout.createSequentialGroup()
	                    	.add(jRadioButtonORecO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    	.add(jLabelORecODat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
	                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                .add(jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                	.add(jTextFieldORecCRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)    		
	                	.add(jCalendarComboBoxORecC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                	.add(jTextFieldORecORef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)
	                	.add(jCalendarComboBoxORecO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
	                .addContainerGap()
        );
        jPanelReceiverLayout.setVerticalGroup(
        		jPanelReceiverLayout.createSequentialGroup()
    			.addContainerGap()
    			.add(jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    				.add(jLabelORecCRef)
    				.add(jTextFieldORecCRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    			.add(jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    				.add(jRadioButtonORecC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				.add(jLabelORecCDat)
    				.add(jCalendarComboBoxORecC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    			.add(10,10,10)
    			.add(jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    				.add(jLabelORecORef)
    				.add(jTextFieldORecORef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
    			.add(jPanelReceiverLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    				.add(jRadioButtonORecO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				.add(jLabelORecODat)
    				.add(jCalendarComboBoxORecO, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    			.addContainerGap()
        );
        
        org.jdesktop.layout.GroupLayout jPanelFileLayout = new org.jdesktop.layout.GroupLayout(jPanelFile);
        jPanelFile.setLayout(jPanelFileLayout);
        jPanelFileLayout.setHorizontalGroup(
        		jPanelFileLayout.createSequentialGroup()
        		.addContainerGap()
    			.add(jPanelFileLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
    				.add(jLabelOFileRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				.add(jPanelFileLayout.createSequentialGroup()
    					.add(jRadioButtonOFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)	
    					.add(jLabelOFileDat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelFileLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                	.add(jTextFieldOFileRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)    		
                	.add(jCalendarComboBoxOFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap()
        );
        jPanelFileLayout.setVerticalGroup(
        		jPanelFileLayout.createSequentialGroup()
    			.addContainerGap()
    			.add(jPanelFileLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    				.add(jLabelOFileRef)
    				.add(jTextFieldOFileRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    			.add(30,30,30)
    			.add(jPanelFileLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
    				.add(jRadioButtonOFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
    				.add(jLabelOFileDat)
    				.add(jCalendarComboBoxOFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
    			.addContainerGap()
        );
        
        jTabbedPaneRef.addTab(Constants.LANG.getString("IssuerRefs") , jPanelIssuer);
        jTabbedPaneRef.addTab(Constants.LANG.getString("ReceiverRefs") , jPanelReceiver);
        jTabbedPaneRef.addTab(Constants.LANG.getString("FileRefs") , jPanelFile);
        
        org.jdesktop.layout.GroupLayout jPanelMainLayout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
        	jPanelMainLayout.createSequentialGroup()
        		.addContainerGap()
        		.add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        			.add(jTabbedPaneRef, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        			.add(jPanelMainLayout.createSequentialGroup()
                        .add(jScrollPaneODelivery, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jButtonOAddDelivery, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jButtonODelDelivery, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanelMainLayout.createSequentialGroup()
                    	.add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabelOSequence, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 155, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelOArticle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 155, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        	.add(jTextFieldOOrdRef, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        	.add(jTextFieldOArticle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    )
                 )
                 .addContainerGap()
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createSequentialGroup()
            	.add(jTabbedPaneRef, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            	.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            	.add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
            		.add(jLabelOSequence)
            		.add(jTextFieldOOrdRef, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                	.add(jScrollPaneODelivery, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                	.add(jPanelMainLayout.createSequentialGroup()
                        .add(jButtonOAddDelivery)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonODelDelivery, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
            		.add(jLabelOArticle)
            		.add(jTextFieldOArticle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
	}
	
	private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private class LenghtValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			String value = ((JTextField)evt.getSource()).getText();
			// Value may be a 20 length max. string
			if (value.length() > 20){
				((JTextField)evt.getSource()).setText("");
				((InvoiceDetailDialog)((JTextField)evt.getSource()).getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
			}
		}
    }
    
    private void addDelivery(java.awt.event.ActionEvent evt) {
    	
    	InputDelivery32Dialog idd = new InputDelivery32Dialog(null, true);
    	
    	idd.setVisible(true);
    	
    	String[] result = idd.getValues();
    	
    	if (result == null || result.length == 0 || result[0] == null || result[0].equals("")) {
    		// Operation cancelled by user
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"),Constants.OK_MSG_COLOR);	
    	}
    	else{
    		((javax.swing.table.DefaultTableModel)jTableODelivery.getModel()).addRow(result);
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageDeliveryAdded"),Constants.OK_MSG_COLOR);
    	} 
    }
    
    private void delDelivery(java.awt.event.ActionEvent evt) {
    	int row = jTableODelivery.getSelectedRow();
    	if (row >= 0){
    		((javax.swing.table.DefaultTableModel)jTableODelivery.getModel()).removeRow(row);
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageDeliveryDeleted"),Constants.OK_MSG_COLOR);
    	}
    	else{
    		((InvoiceDetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageColumnNotSelected"),Constants.ERROR_MSG_COLOR);
    	}
    }
    
    private class PurchaseOrderValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			String value = ((JTextField)evt.getSource()).getText();
			// The purchase order reference must be double type
			try{
				Double.parseDouble(value);
			}
			catch(NumberFormatException nfe){
				if (((JTextField)evt.getSource()).getText() != null) {
					boolean empty = ((JTextField)evt.getSource()).getText().trim().equals("");
					((JTextField)evt.getSource()).setText("");
					if (!empty){
						((JTextField)evt.getSource()).setBackground(Constants.BKG_ERROR_COLOR);
						((InvoiceDetailDialog)((JTextField)evt.getSource()).getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
					}
				}
			}
		}
	}
    
    private class FocusAdapterDate extends FocusAdapter{
    	public void focusLost(FocusEvent evt){
			if ( !((JFormattedTextField)evt.getSource()).isEditValid() ) {
		   		((InvoiceDetailDialog)((JFormattedTextField)evt.getSource()).getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotValid"), Constants.ERROR_MSG_COLOR);
		   	}
		}
    }
    
    private class ActionListenerDate implements java.awt.event.ActionListener{
    	JCalendarComboBox jccb;
    	public ActionListenerDate(JCalendarComboBox jccb){
    		this.jccb = jccb;
    	}
		public void actionPerformed(ActionEvent e) {
			if (((JRadioButton)e.getSource()).isSelected())
				jccb.setVisible(true);
	        else
	        	jccb.setVisible(false);
		}
    }
    
    // Getters and Setters for visible components
    public javax.swing.JTable getJTableODelivery() {
		return jTableODelivery;
	}
	public void setJTableODelivery(javax.swing.JTable tableODelivery) {
		jTableODelivery = tableODelivery;
	}
	public javax.swing.JTextField getJTextFieldOIssCRef() {
		return jTextFieldOIssCRef;
	}
	public void setJTextFieldOIssCRef(javax.swing.JTextField textFieldOIssCRef) {
		jTextFieldOIssCRef = textFieldOIssCRef;
	}
	public javax.swing.JTextField getJTextFieldOIssORef() {
		return jTextFieldOIssORef;
	}
	public void setJTextFieldOIssORef(javax.swing.JTextField textFieldOIssORef) {
		jTextFieldOIssORef = textFieldOIssORef;
	}
	public javax.swing.JTextField getJTextFieldORecCRef() {
		return jTextFieldORecCRef;
	}
	public void setJTextFieldORecCRef(javax.swing.JTextField textFieldORecCRef) {
		jTextFieldORecCRef = textFieldORecCRef;
	}
	public javax.swing.JTextField getJTextFieldORecORef() {
		return jTextFieldORecORef;
	}
	public void setJTextFieldORecORef(javax.swing.JTextField textFieldORecORef) {
		jTextFieldORecORef = textFieldORecORef;
	}
	public javax.swing.JTextField getJTextFieldOOrdRef() {
		return jTextFieldOOrdRef;
	}
	public void setJTextFieldOOrdRef(javax.swing.JTextField textFieldOOrdRef) {
		jTextFieldOOrdRef = textFieldOOrdRef;
	}
	
	public javax.swing.JTextField getJTextFieldOFileRef() {
		return jTextFieldOFileRef;
	}
	public void setJTextFieldOFileRef(javax.swing.JTextField textFieldOFileRef) {
		jTextFieldOFileRef = textFieldOFileRef;
	}
	
	public javax.swing.JTextField getJTextFieldOArticle() {
		return jTextFieldOArticle;
	}
	public void setJTextFieldOArticle(javax.swing.JTextField textFieldOArticle) {
		jTextFieldOArticle = textFieldOArticle;
	}
	
	public javax.swing.JButton getJButtonODelDelivery() {
		return jButtonODelDelivery;
	}

	public javax.swing.JButton getJButtonOAddDelivery() {
		return jButtonOAddDelivery;
	}
	
	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxOIssC() {
		return jCalendarComboBoxOIssC;
	}
	
	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxOIssO() {
		return jCalendarComboBoxOIssO;
	}
	
	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxORecC() {
		return jCalendarComboBoxORecC;
	}
	
	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxORecO() {
		return jCalendarComboBoxORecO;
	}
	
	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxOFile() {
		return jCalendarComboBoxOFile;
	}
	
	public javax.swing.JRadioButton getJRadioButtonOIssC() {
		return jRadioButtonOIssC;
	}
	
	public javax.swing.JRadioButton getJRadioButtonOIssO() {
		return jRadioButtonOIssO;
	}
	
	public javax.swing.JRadioButton getJRadioButtonORecC() {
		return jRadioButtonORecC;
	}
	
	public javax.swing.JRadioButton getJRadioButtonORecO() {
		return jRadioButtonORecO;
	}
	
	public javax.swing.JRadioButton getJRadioButtonOFile() {
		return jRadioButtonOFile;
	}
	
	// JScrollPane
    private javax.swing.JScrollPane jScrollPaneODelivery;
    // JTable
    private javax.swing.JTable jTableODelivery;
    // JTextField
    private javax.swing.JTextField jTextFieldOIssCRef;
    private javax.swing.JTextField jTextFieldOIssORef;
    private javax.swing.JTextField jTextFieldORecCRef;
    private javax.swing.JTextField jTextFieldORecORef;
    private javax.swing.JTextField jTextFieldOOrdRef;
    private javax.swing.JTextField jTextFieldOFileRef;
    private javax.swing.JTextField jTextFieldOArticle;
    // JButton
    private javax.swing.JButton jButtonODelDelivery;
    private javax.swing.JButton jButtonOAddDelivery;
    // JLabel
    private javax.swing.JLabel jLabelOIssCRef;
    private javax.swing.JLabel jLabelOIssCDat;
    private javax.swing.JLabel jLabelOIssORef;
    private javax.swing.JLabel jLabelOIssODat;
    private javax.swing.JLabel jLabelORecCRef;
    private javax.swing.JLabel jLabelORecCDat;
    private javax.swing.JLabel jLabelORecORef;
    private javax.swing.JLabel jLabelORecODat;
    private javax.swing.JLabel jLabelOFileRef;
    private javax.swing.JLabel jLabelOFileDat;
    private javax.swing.JLabel jLabelOArticle;
    
    private javax.swing.JLabel jLabelOSequence;
    // JPanel
	private javax.swing.JPanel jPanelIssuer;
	private javax.swing.JPanel jPanelReceiver;
	private javax.swing.JPanel jPanelFile;
	// JTabbedPane
    private javax.swing.JTabbedPane jTabbedPaneRef;
    // JCalendarComboBox
	private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxOIssC;
	private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxOIssO;
	private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxORecC;
	private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxORecO;
	private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxOFile;
    // JRadioButtons
    private javax.swing.JRadioButton jRadioButtonOIssC;
    private javax.swing.JRadioButton jRadioButtonOIssO;
    private javax.swing.JRadioButton jRadioButtonORecC;
    private javax.swing.JRadioButton jRadioButtonORecO;
    private javax.swing.JRadioButton jRadioButtonOFile;

}
