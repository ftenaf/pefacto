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
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import es.mityc.appfacturae.ui.dialogs.InputTaxesOutputsDialog;
import es.mityc.appfacturae.ui.dialogs.InputTaxesWithheldDialog;
import es.mityc.appfacturae.ui.dialogs.Invoice32DetailDialog;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.DoubleUtil;

public class Invoice32DetailTaxesPanel extends JPanel implements InvoiceDetailTaxesPanel{
	
	private Properties decimalProps = null;
	private Boolean specialBaseRequired;
	
	public Invoice32DetailTaxesPanel(Boolean specialBaseRequired){
		super();
		this.specialBaseRequired = specialBaseRequired;
        initComponents();
	}
	
	private void initComponents(){
		
		jTabbedPaneT = new javax.swing.JTabbedPane();
        jPanelTOutputs = new javax.swing.JPanel();
        jScrollPaneTOutputs = new javax.swing.JScrollPane();
        jTableTOutputs = new javax.swing.JTable();
        jButtonTDelOutput = new javax.swing.JButton();
        jButtonTAddOutput = new javax.swing.JButton();
        jPanelTWithhelds = new javax.swing.JPanel();
        jScrollPaneTWithhelds = new javax.swing.JScrollPane();
        jTableTWithhelds = new javax.swing.JTable();
        jButtonTAddWithheld = new javax.swing.JButton();
        jButtonTDelWithheld = new javax.swing.JButton();
        
        jSpecialEvent = new javax.swing.JLabel();
        jSpecialEventCode = new javax.swing.JLabel();
        jSpecialEventReason = new javax.swing.JLabel();
        jComboBoxSpecial = new javax.swing.JComboBox();
        
        jSpecialEvent.setFont(Constants.FONT_BOLD);
        jSpecialEvent.setForeground(Constants.FONT_COLOR);
        jSpecialEvent.setText(Constants.LANG.getString("SpecialTaxableEvent") ); 
        jSpecialEventCode.setFont(Constants.FONT_PLAIN);
        jSpecialEventCode.setForeground(Constants.FONT_COLOR);
        jSpecialEventCode.setText(Constants.LANG.getString("SpecialTaxableEventCode") ); 
        jSpecialEventReason.setFont(Constants.FONT_PLAIN);
        jSpecialEventReason.setForeground(Constants.FONT_COLOR);
        jSpecialEventReason.setText(Constants.LANG.getString("SpecialTaxableEventReason") );
        jSpecialEventReason.setEnabled(false);
        
        jComboBoxSpecial.setForeground(Constants.FONT_COLOR);
        jComboBoxSpecial.setFont(Constants.FONT_PLAIN);
        jComboBoxSpecial.setRenderer(new ComboBoxRenderer(true));
        String[] specialCodes = Constants.APP_PROP.getProperty("specialTaxableEvent").split(";");
        String[] specialCodesDesc = new String[specialCodes.length];
        specialCodesDesc[0] = specialCodes[0];
        for (int i = 1 ; i < specialCodes.length ; i++)
        	specialCodesDesc[i] = Constants.LANG.getString(specialCodes[i]);
        jComboBoxSpecial.setModel(new javax.swing.DefaultComboBoxModel(specialCodesDesc));
        jComboBoxSpecial.setSelectedIndex(0);  
        
        jComboBoxSpecial.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuCanceled(PopupMenuEvent evt) {
            	((Invoice32DetailDialog)jComboBoxSpecial.getTopLevelAncestor()).getFadingCanvas().setVisible(true);
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent evt) {
            	((Invoice32DetailDialog)jComboBoxSpecial.getTopLevelAncestor()).getFadingCanvas().setVisible(true);
            }
            public void popupMenuWillBecomeVisible(PopupMenuEvent evt) {
            	((Invoice32DetailDialog)jComboBoxSpecial.getTopLevelAncestor()).getFadingCanvas().setVisible(false);
            	
            }
        });
        
        jTextFieldSpecial = new javax.swing.JTextField();
        jTextFieldSpecial.setText("");
        jTextFieldSpecial.setBackground(Constants.BKG_MAIN_COLOR);
        
        this.setBackground(Constants.BKG_MAIN_COLOR);
        this.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Taxes") , javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

        jPanelTOutputs.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneTOutputs.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneTOutputs.setAutoscrolls(true);
        jScrollPaneTOutputs.setOpaque(false);
        jScrollPaneTOutputs.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableTOutputs.setFont(Constants.FONT_PLAIN);
        jTableTOutputs.setForeground(Constants.FONT_COLOR);
        jTableTOutputs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                Constants.LANG.getString("TaxType"), Constants.LANG.getString("Rate"), Constants.LANG.getString("TB")+" ("+Constants.LANG.getString("EuroSimbol")+")", Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")", Constants.LANG.getString("SpecialTaxableBase")+" ("+Constants.LANG.getString("EuroSimbol")+")", Constants.LANG.getString("SpecialTaxAmount")+" ("+Constants.LANG.getString("EuroSimbol")+")", Constants.LANG.getString("EquivalenceSurcharge"), Constants.LANG.getString("EquivalenceSurchargeAmount")+" ("+Constants.LANG.getString("EuroSimbol")+")"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableTOutputs.setGridColor(Constants.SELECTION_COLOR);
        jTableTOutputs.getTableHeader().setReorderingAllowed(false);
        jScrollPaneTOutputs.setViewportView(jTableTOutputs);
        jTableTOutputs.getColumnModel().getColumn(0).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableTOutputs.getColumnModel().getColumn(1).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTOutputs.getColumnModel().getColumn(2).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTOutputs.getColumnModel().getColumn(3).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTOutputs.getColumnModel().getColumn(4).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTOutputs.getColumnModel().getColumn(5).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(5).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTOutputs.getColumnModel().getColumn(6).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(6).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTOutputs.getColumnModel().getColumn(7).setResizable(false);
        jTableTOutputs.getColumnModel().getColumn(7).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        for (int i = 0 ; i < jTableTOutputs.getColumnCount() ; i++){
            jTableTOutputs.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jButtonTDelOutput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_minus.jpg"))); 
        jButtonTDelOutput.setToolTipText(Constants.LANG.getString("Remove")); 
        jButtonTDelOutput.setBorderPainted(false);
        jButtonTDelOutput.setContentAreaFilled(false);
        jButtonTDelOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	Invoice32DetailTaxesPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	Invoice32DetailTaxesPanel.this.mouseExited(evt);
            }
        });
        jButtonTDelOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delOutput(evt);
            }
        });

        jButtonTAddOutput.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonTAddOutput.setForeground(Constants.BKG_MAIN_COLOR);
        jButtonTAddOutput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_plus.jpg"))); 
        jButtonTAddOutput.setToolTipText(Constants.LANG.getString("Add")); 
        jButtonTAddOutput.setBorderPainted(false);
        jButtonTAddOutput.setContentAreaFilled(false);
        jButtonTAddOutput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	Invoice32DetailTaxesPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	Invoice32DetailTaxesPanel.this.mouseExited(evt);
            }
        });
        jButtonTAddOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOutput(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanelTOutputsLayout = new org.jdesktop.layout.GroupLayout(jPanelTOutputs);
        jPanelTOutputs.setLayout(jPanelTOutputsLayout);
        jPanelTOutputsLayout.setHorizontalGroup(
            jPanelTOutputsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelTOutputsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPaneTOutputs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 719, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanelTOutputsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButtonTAddOutput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE)
                    .add(jButtonTDelOutput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, Short.MAX_VALUE))
                .add(20, 20, 20))
        );
        jPanelTOutputsLayout.setVerticalGroup(
            jPanelTOutputsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelTOutputsLayout.createSequentialGroup()
                .add(jPanelTOutputsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelTOutputsLayout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(jButtonTAddOutput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonTDelOutput, 0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelTOutputsLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPaneTOutputs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPaneT.addTab(Constants.LANG.getString("TaxesOutputs") , jPanelTOutputs);

        jPanelTWithhelds.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneTWithhelds.setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneTWithhelds.setAutoscrolls(true);
        jScrollPaneTWithhelds.setOpaque(false);
        jScrollPaneTWithhelds.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableTWithhelds.setFont(Constants.FONT_PLAIN);
        jTableTWithhelds.setForeground(Constants.FONT_COLOR);
        jTableTWithhelds.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                Constants.LANG.getString("TaxType"), Constants.LANG.getString("Rate"), Constants.LANG.getString("TaxableBase")+" ("+Constants.LANG.getString("EuroSimbol")+")", Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableTWithhelds.setGridColor(Constants.SELECTION_COLOR);
        jTableTWithhelds.getTableHeader().setReorderingAllowed(false);
        jScrollPaneTWithhelds.setViewportView(jTableTWithhelds);
        jTableTWithhelds.getColumnModel().getColumn(0).setResizable(false);
        jTableTWithhelds.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
        jTableTWithhelds.getColumnModel().getColumn(1).setResizable(false);
        jTableTWithhelds.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTWithhelds.getColumnModel().getColumn(2).setResizable(false);
        jTableTWithhelds.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        jTableTWithhelds.getColumnModel().getColumn(3).setResizable(false);
        jTableTWithhelds.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.RIGHT));
        for (int i = 0 ; i < jTableTWithhelds.getColumnCount() ; i++) {
            jTableTWithhelds.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jButtonTAddWithheld.setBackground(Constants.BKG_MAIN_COLOR);
        jButtonTAddWithheld.setForeground(Constants.BKG_MAIN_COLOR);
        jButtonTAddWithheld.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_plus.jpg"))); 
        jButtonTAddWithheld.setToolTipText(Constants.LANG.getString("Add")); 
        jButtonTAddWithheld.setBorderPainted(false);
        jButtonTAddWithheld.setContentAreaFilled(false);
        jButtonTAddWithheld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWithheld(evt);
            }
        });
        jButtonTAddWithheld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	Invoice32DetailTaxesPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	Invoice32DetailTaxesPanel.this.mouseExited(evt);
            }
        });

        jButtonTDelWithheld.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_minus.jpg"))); 
        jButtonTDelWithheld.setToolTipText(Constants.LANG.getString("Remove")); 
        jButtonTDelWithheld.setContentAreaFilled(false);
        jButtonTDelWithheld.setBorderPainted(false);
        jButtonTDelWithheld.setDefaultCapable(false);
        jButtonTDelWithheld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delWithheld(evt);
            }
        });
        jButtonTDelWithheld.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	Invoice32DetailTaxesPanel.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Invoice32DetailTaxesPanel.this.mouseExited(evt);
            }
        });

        jComboBoxSpecial.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (((JComboBox)e.getSource()).getSelectedIndex() > 0){
					int numRows = jTableTOutputs.getModel().getRowCount();
			    	for (int i = numRows-1; i > -1; i--){
			    		((DefaultTableModel)jTableTOutputs.getModel()).removeRow(i);
			    	}
			    	jSpecialEventReason.setEnabled(true);
			    	jTextFieldSpecial.setEnabled(true);
			    	jTextFieldSpecial.setBackground(java.awt.Color.WHITE);
				}
				else{
					jTextFieldSpecial.setText("");
					jSpecialEventReason.setEnabled(false);
					jTextFieldSpecial.setBackground(Constants.BKG_MAIN_COLOR);
					jTextFieldSpecial.setEnabled(false);
				}
			}
        });
        
        jTextFieldSpecial.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
        
        org.jdesktop.layout.GroupLayout jPanelTWithheldsLayout = new org.jdesktop.layout.GroupLayout(jPanelTWithhelds);
        jPanelTWithhelds.setLayout(jPanelTWithheldsLayout);
        jPanelTWithheldsLayout.setHorizontalGroup(
            jPanelTWithheldsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelTWithheldsLayout.createSequentialGroup()
                .add(99, 99, 99)
                .add(jScrollPaneTWithhelds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 538, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(40, 40, 40)
                .add(jPanelTWithheldsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jButtonTDelWithheld, 0, 0, Short.MAX_VALUE)
                    .add(jButtonTAddWithheld, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE))
                .add(93, 93, 93))
        );
        jPanelTWithheldsLayout.setVerticalGroup(
            jPanelTWithheldsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelTWithheldsLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelTWithheldsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING,false)
                	.add(org.jdesktop.layout.GroupLayout.LEADING, jPanelTWithheldsLayout.createSequentialGroup()
                		.add(10, 10, 10)
                        .add(jButtonTAddWithheld, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(4,4,4)
                        .add(jButtonTDelWithheld, 0, 0, Short.MAX_VALUE))
                        .add(4,4,4)
                    .add(jScrollPaneTWithhelds, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(16, 16, 16))
        );

        jTabbedPaneT.addTab(Constants.LANG.getString("TaxesWithheld") , jPanelTWithhelds); 

        org.jdesktop.layout.GroupLayout jPanelMainLayout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTabbedPaneT, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                    .add(jPanelMainLayout.createSequentialGroup()
                    		.add(jSpecialEvent)
                    		.add(15,15,15)
                    		.add(jSpecialEventCode)
                    		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    		.add(jComboBoxSpecial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    		.add(10,10,10)
                    		.add(jSpecialEventReason)
                    		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    		.add(jTextFieldSpecial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
            	.add(jTabbedPaneT, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 110, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                		.add(jSpecialEvent)
                		.add(jSpecialEventCode)
                		.add(jComboBoxSpecial)
                		.add(jSpecialEventReason)
                		.add(jTextFieldSpecial))
                .addContainerGap())
        );
        
     // The required properties are loaded
		try {
			decimalProps = new Properties();
			decimalProps.load(this.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact31d_resource")));
		} catch (IOException e) {
			((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("MainWindowException1"), Constants.ERROR_MSG_COLOR);
		}
	}
	
	
	private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void addOutput(java.awt.event.ActionEvent evt) {
    	
    	// Se recupera el importe total para calcular el descuento
    	String tibString = ((Invoice32DetailDialog)this.getTopLevelAncestor()).jPanelGeneral.getJTextFieldGGROSS().getText().trim();
    	Double tib = null;
    	if (tibString != null && !"".equals(tibString)) {
    		try {
    			tib = Double.parseDouble(tibString);
    			tib = DoubleUtil.round(tib, 2);
    		} catch (NumberFormatException e) {
        		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
    			return;
    		}
    	}
    	
    	// Negative value support
    	if (tib == null) {
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTaxItemRequired"), Constants.ERROR_MSG_COLOR);
    		return;
    	}
    	
    	InputTaxesOutputsDialog itod = new InputTaxesOutputsDialog(null, true, tib, specialBaseRequired,"3.2");
    	
    	itod.setVisible(true);
    	
    	String[] result = itod.getValues();
    	
    	if (result == null || result.length != 8 || result[0] == null || "".equals(result[0])) {
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);		
    		return;
    	}
    	
    	// Check if tax already exists, or if there is a tax with negative charges
    	TableModel outputsModel = jTableTOutputs.getModel();
    	for (int i = 0; i < outputsModel.getRowCount(); ++i) {   		
    		if (outputsModel.getValueAt(i, 0).toString().equals(result[0]) && outputsModel.getValueAt(i, 1).toString().equals(result[1]) && (outputsModel.getValueAt(i, 6).toString().equals(result[6]) || ("0.0".equals(outputsModel.getValueAt(i, 6).toString()) && "".equals(result[6])) || ("".equals(outputsModel.getValueAt(i, 6).toString()) && "0.0".equals(result[6])) ) ) {
    			((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageTaxAlreadyExists"), Constants.OK_MSG_COLOR);
    			return; 			
    		}
    		if (Double.parseDouble(outputsModel.getValueAt(i, 3).toString()) >= 0) {
    			// The other negative taxes may be equals
    			if (outputsModel.getValueAt(i, 3) != null && Double.parseDouble(outputsModel.getValueAt(i, 3).toString()) < 0) {
    				boolean flag = false;
    				// Tax type
    				if ( (result[0] != null && !result[0].equals(outputsModel.getValueAt(i, 0))) ||
    						(result[0] == null && outputsModel.getValueAt(i, 0) != null) )
    					flag = true;
    				// Tax rate
    				if ( (result[1] != null && !result[1].equals(outputsModel.getValueAt(i, 1))) ||
    						(result[1] == null && outputsModel.getValueAt(i, 1) != null) )
    					flag = true;
    				// Charge amount
    				if ( (result[3] != null && !result[3].equals(outputsModel.getValueAt(i, 3))) ||
    						(result[3] == null && outputsModel.getValueAt(i, 3) != null) )
    					flag = true;

    				if (flag){
    					((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageTaxNegativeNotEquals"), Constants.ERROR_MSG_COLOR);
    					return;
    				}
    			}
    		} else if (outputsModel.getValueAt(i, 3) != null && Double.parseDouble(outputsModel.getValueAt(i, 3).toString()) < 0) { // If item have a negative OutputTax...
    			// The other taxes may be equals
    			boolean flag = false;
    			// Tax type
    			if ( (result[0] != null && !result[0].equals(outputsModel.getValueAt(i, 0))) ||
    					(result[0] == null && outputsModel.getValueAt(i, 0) != null) )
    				flag = true;
    			// Tax rate
    			if ( (result[1] != null && !result[1].equals(outputsModel.getValueAt(i, 1))) ||
    					(result[1] == null && outputsModel.getValueAt(i, 1) != null) )
    				flag = true;
    			// Charge amount
    			if ( (result[3] != null && !result[3].equals(outputsModel.getValueAt(i, 3))) ||
    					(result[3] == null && outputsModel.getValueAt(i, 3) != null) )
    				flag = true;

    			if (flag){
    				((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageTaxNegativeNotEquals"), Constants.ERROR_MSG_COLOR);
    				return;
    			}
    		}
    	}

    	if (result[4] != null && !"".equals(result[4].trim()))
    		specialBaseRequired = true;
    	else
    		specialBaseRequired = false;
	
    	((javax.swing.table.DefaultTableModel)outputsModel).addRow(result);    	
    	
    	jTextFieldSpecial.setText("");
    	jTextFieldSpecial.setEnabled(false);
    	jTextFieldSpecial.setBackground(Constants.BKG_MAIN_COLOR);
    	jSpecialEventReason.setEnabled(false);
    	jComboBoxSpecial.setSelectedIndex(0);
    	
    	((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageTaxOutPutAdded"), Constants.OK_MSG_COLOR);
    }
    
    private void delOutput(java.awt.event.ActionEvent evt) {
    	int row = jTableTOutputs.getSelectedRow();
    	if (row >= 0) {
    		((javax.swing.table.DefaultTableModel)jTableTOutputs.getModel()).removeRow(row);
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageTaxOutPutDeleted"), Constants.OK_MSG_COLOR);
        	if (jTableTOutputs.getModel().getRowCount() == 0)
        		specialBaseRequired = ((Invoice32DetailDialog)this.getTopLevelAncestor()).specialBaseRequired;
    	} else
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
    }
    
    private void addWithheld(java.awt.event.ActionEvent evt) {
    	
    	// Se recupera el importe total para calcular el descuento
    	String tibString = ((Invoice32DetailDialog)this.getTopLevelAncestor()).jPanelGeneral.getJTextFieldGGROSS().getText().trim();
    	Double tib = null;
    	if (tibString != null && !"".equals(tibString)) {
    		try {
    			tib = Double.parseDouble(tibString);
    			tib = DoubleUtil.round(tib, 2);
    		} catch (NumberFormatException e) {
    			((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);	
    			return;
    		}
    	}
    	
    	// Negative value support
    	if (tib == null) {
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTaxItemRequired"), Constants.ERROR_MSG_COLOR);
    		return;
    	}
    	
    	InputTaxesWithheldDialog itod = new InputTaxesWithheldDialog(null, true, tib,"3.2");
    	
    	itod.setVisible(true);
    	
    	String[] result = itod.getValues();
    	
    	// Check if tax already exists
    	TableModel withHeldModel = jTableTWithhelds.getModel();
    	for (int i = 0; i < withHeldModel.getRowCount(); ++i) {   		
    		if (withHeldModel.getValueAt(i, 0).toString().equals(result[0]) && withHeldModel.getValueAt(i, 1).toString().equals(result[1]))  {
    			((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageTaxAlreadyExists"), Constants.OK_MSG_COLOR);
    			return; 			
    		}
    	}
    	
    	
    	if (result == null || result.length != 4 || result[0] == null || "".equals(result[0])) {
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);		
    		return;
    	}
    		
    	((javax.swing.table.DefaultTableModel)jTableTWithhelds.getModel()).addRow(result);    
    	((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageTaxWithHeldAdded"), Constants.OK_MSG_COLOR);
    }
    
    private void delWithheld(java.awt.event.ActionEvent evt) {
    	int row = jTableTWithhelds.getSelectedRow();
    	if (row >= 0){
    		((javax.swing.table.DefaultTableModel)jTableTWithhelds.getModel()).removeRow(row);
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageTaxWithHeldDeleted"), Constants.OK_MSG_COLOR);
    	}else
    		((Invoice32DetailDialog)this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
    }
    
    public void grossAmountChanged() {
    	Double total = DoubleUtil.round(Double.parseDouble(((Invoice32DetailDialog)jTableTOutputs.getTopLevelAncestor()).jPanelGeneral.getJTextFieldGGROSS().getText()),Integer.parseInt(decimalProps.getProperty("item_taxable_base"))); 
    	String value = null;
    	Double d = 0.0;
    	
    	// Output table is updated
    	TableModel modelO = jTableTOutputs.getModel();
    	for (int i = 0; i < modelO.getRowCount(); ++i) {
    		// Rate read
    		value = modelO.getValueAt(i, 1).toString();
    		// Base actualization
    		modelO.setValueAt(total, i, 2);
    		// If rate exists, amount may be recalculated
    		if (value != null && !"".equals(value.trim())) { 
    			d = Double.parseDouble(value);
    			d = (d * total) / 100;
    			d = DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("item_tax_Out")));
    			modelO.setValueAt(d, i, 3);
    		}
    		value = null;
    		// Special base read
    		if(modelO.getValueAt(i, 4) != null )
    			value = modelO.getValueAt(i, 4).toString();
    		// If there is not a special base, then special amount may be recalculated using normal base
    		if (value == null || "".equals(value.trim())) { 
    			// Rate read
    			if(modelO.getValueAt(i, 6) != null ){
        			value = modelO.getValueAt(i, 6).toString();
    				// If rate exists, amount may be recalculated
	    			if (value != null && !"".equals(value.trim())) { 
	    				d = Double.parseDouble(value);
	    				d = (d * total) / 100;
	    				d = DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("special_taxable_amount")));
	    				modelO.setValueAt(d, i, 7);
	    			}
    			}
    		}
    	}
    	
    	// Withheld table is updated
    	TableModel modelW = jTableTWithhelds.getModel();
    	for (int i = 0; i < modelW.getRowCount(); ++i) {
    		// Rate read
    		value = modelW.getValueAt(i, 1).toString();
    		// Base actualization
    		modelW.setValueAt(total, i, 2);
    		// If rate exists, amount may be recalculated
    		if (value != null && !"".equals(value.trim())) { 
    			d = Double.parseDouble(value);
    			d = (d * total) / 100;
    			d = DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("item_tax_With")));
    			modelW.setValueAt(d, i, 3);
    		}
    	}
    }
    
    // Getters and Setters for visible JTables and JButtons
	public javax.swing.JTable getJTableTOutputs() {
		return jTableTOutputs;
	}

	public javax.swing.JTable getJTableTWithhelds() {
		return jTableTWithhelds;
	}
	
	public Boolean getSpecialBaseRequired() {
		return specialBaseRequired;
	}

	public void setSpecialBaseRequired(Boolean specialBaseRequired) {
		this.specialBaseRequired = specialBaseRequired;
	}

	public javax.swing.JButton getJButtonTAddOutput() {
		return jButtonTAddOutput;
	}

	public javax.swing.JButton getJButtonTDelOutput() {
		return jButtonTDelOutput;
	}

	public javax.swing.JButton getJButtonTAddWithheld() {
		return jButtonTAddWithheld;
	}

	public javax.swing.JButton getJButtonTDelWithheld() {
		return jButtonTDelWithheld;
	}
	
	public javax.swing.JComboBox getJComboBoxSpecial() {
		return jComboBoxSpecial;
	}
	
	public javax.swing.JTextField getJTextFieldSpecial() {
		return jTextFieldSpecial;
	}
    
	// JPanel
	private javax.swing.JPanel jPanelTOutputs;
	private javax.swing.JPanel jPanelTWithhelds;
	// JScrollPane
	private javax.swing.JScrollPane jScrollPaneTOutputs;
	private javax.swing.JScrollPane jScrollPaneTWithhelds;
	// JTabbedPane
    private javax.swing.JTabbedPane jTabbedPaneT;
    // JTable
    private javax.swing.JTable jTableTOutputs;
    private javax.swing.JTable jTableTWithhelds;
    // JButton
    private javax.swing.JButton jButtonTAddOutput;
    private javax.swing.JButton jButtonTDelOutput;
    private javax.swing.JButton jButtonTAddWithheld;
    private javax.swing.JButton jButtonTDelWithheld;
    // JLabel
    private javax.swing.JLabel jSpecialEvent = null;
    private javax.swing.JLabel jSpecialEventCode = null;
    private javax.swing.JLabel jSpecialEventReason = null;
    // JCombo
    private javax.swing.JComboBox jComboBoxSpecial = null;
    // JTextField
    private javax.swing.JTextField jTextFieldSpecial = null;
}
