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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.DoubleUtil;

public class InputReimDialog extends JDialog {
	
	private static Log logger = LogFactory.getLog(InputReimDialog.class);
	private Properties decimalProps = null;	
	private FadingCanvas fd;
	private String version = null;
	
	public InputReimDialog (Frame parent, boolean modal, String version) {
		super(parent, modal);
		this.version = version;
		init();
		setLocationRelativeTo(parent);
	}

	private void init() {
		panelPrin = new JPanel();
		panel = new JPanel();
		jScroll = new JScrollPane();
		jButtonOk = new JButton();
		jButtonCancel = new JButton();
		fd = new FadingCanvas();

		panelPrin.setBackground(Constants.BKG_MAIN_COLOR);
		
		panel.setBackground(Constants.BKG_MAIN_COLOR);
		panel.setLayout(new GridBagLayout());

		jButtonOk.setBorderPainted(false);
		jButtonOk.setContentAreaFilled(false);
		jButtonOk.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonOk.setIcon(new ImageIcon(getClass().getResource("/images/button_accept.jpg")));
		jButtonOk.setToolTipText(Constants.LANG.getString("Accept"));
		jButtonOk.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonOk.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jButtonOk.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
		jButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean flag = false;
				if ( jTextFieldAmount.getText() == null || 
						"".equals(jTextFieldAmount.getText().trim()) ) {								
					flag = true;
					jTextFieldAmount.requestFocus();  
					jTextFieldAmount.setBackground(Constants.BKG_ERROR_COLOR);
				}
				
				if ( jComboBoxIssuerType.getSelectedItem() != null && !"".equals(jComboBoxIssuerType.getSelectedItem().toString().trim()) ||
						jComboBoxIssuerResidenceType.getSelectedItem() != null && !"".equals(jComboBoxIssuerResidenceType.getSelectedItem().toString().trim()) ||
						jTextFieldIssuerFiscalId.getText() != null && !"".equals(jTextFieldIssuerFiscalId.getText().trim())) {
					
					if ( jComboBoxIssuerType.getSelectedItem() == null || 
							"".equals(jComboBoxIssuerType.getSelectedItem().toString().trim()) ) {
						flag = true;
						jComboBoxIssuerType.requestFocus();  
						jComboBoxIssuerType.setBackground(Constants.BKG_ERROR_COLOR);						
					}	
					if ( jComboBoxIssuerResidenceType.getSelectedItem() == null || 
							"".equals(jComboBoxIssuerResidenceType.getSelectedItem().toString().trim()) ) {
						flag = true;
						jComboBoxIssuerResidenceType.requestFocus();  
						jComboBoxIssuerResidenceType.setBackground(Constants.BKG_ERROR_COLOR);						
					}					
					if ( jTextFieldIssuerFiscalId.getText() == null || 
							"".equals(jTextFieldIssuerFiscalId.getText().trim()) ) {
						flag = true;
						jTextFieldIssuerFiscalId.requestFocus();  
						jTextFieldIssuerFiscalId.setBackground(Constants.BKG_ERROR_COLOR);
					}
				}

				if ( jComboBoxReceiverType.getSelectedItem() != null && !"".equals(jComboBoxReceiverType.getSelectedItem().toString().trim()) ||
						jComboBoxReceiverResidenceType.getSelectedItem() != null && !"".equals(jComboBoxReceiverResidenceType.getSelectedItem().toString().trim()) ||
						jTextFieldReceiverFiscalId.getText() != null && !"".equals(jTextFieldReceiverFiscalId.getText().trim())) {
					
					if ( jComboBoxReceiverType.getSelectedItem() == null || 
							"".equals(jComboBoxReceiverType.getSelectedItem().toString().trim()) ) {
						flag = true;
						jComboBoxReceiverType.requestFocus();  
						jComboBoxReceiverType.setBackground(Constants.BKG_ERROR_COLOR);						
					}	
					if ( jComboBoxReceiverResidenceType.getSelectedItem() == null || 
							"".equals(jComboBoxReceiverResidenceType.getSelectedItem().toString().trim()) ) {
						flag = true;
						jComboBoxReceiverResidenceType.requestFocus();  
						jComboBoxReceiverResidenceType.setBackground(Constants.BKG_ERROR_COLOR);						
					}					
					if ( jTextFieldReceiverFiscalId.getText() == null || 
							"".equals(jTextFieldReceiverFiscalId.getText().trim()) ) {
						flag = true;
						jTextFieldReceiverFiscalId.requestFocus();  
						jTextFieldReceiverFiscalId.setBackground(Constants.BKG_ERROR_COLOR);
					}
				}

				if (flag) {
					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
					return;
				} else {
					setVisible(false);
					dispose();
				}
			}
		});

		jButtonCancel.setBorderPainted(false);
		jButtonCancel.setContentAreaFilled(false);
		jButtonCancel.setIcon(new ImageIcon(getClass().getResource("/images/button_cancel.jpg")));
		jButtonCancel.setToolTipText(Constants.LANG.getString("Cancel"));
		jButtonCancel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonCancel.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
		    }
			public void mouseExited(MouseEvent evt) {
				jButtonCancel.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonCancelActionPerformed();
			}
		});
				
		jScroll.setViewportView(panel);
		jScroll.setBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true));
		
		// Se construye el panel de entrada	
		jLabelBoxIssuerType = new JLabel(Constants.LANG.getString("IssuerType"));
	    GridBagConstraints issuerTypeLabelConstraints = new GridBagConstraints();
	    issuerTypeLabelConstraints.gridx = 1;
	    issuerTypeLabelConstraints.gridy = 0;
	    issuerTypeLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
	    issuerTypeLabelConstraints.gridwidth=2;
	    issuerTypeLabelConstraints.weightx = 0.01;
	    issuerTypeLabelConstraints.insets = new Insets(0,3,0,10);
	    jLabelBoxIssuerType.setForeground(Constants.FONT_COLOR);
	    jLabelBoxIssuerType.setFont(Constants.FONT_PLAIN);
	    jLabelBoxIssuerType.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelBoxIssuerType.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelBoxIssuerType, issuerTypeLabelConstraints);

		jComboBoxIssuerType = new JComboBox();
		jComboBoxIssuerType.setBackground(Color.white);
		jComboBoxIssuerType.setModel(new javax.swing.DefaultComboBoxModel(new String[] {Constants.LANG.getString("Individual"),Constants.LANG.getString("LegalEntity")}));
		jComboBoxIssuerType.setBounds(200, 70, 80, 20);
		jComboBoxIssuerType.setSelectedIndex(-1);
		jComboBoxIssuerType.setForeground(Constants.FONT_COLOR);
		jComboBoxIssuerType.setFont(Constants.FONT_PLAIN);
		jComboBoxIssuerType.setMaximumRowCount(2);
		jComboBoxIssuerType.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	((JComboBox)e.getSource()).setBackground(Color.WHITE);
		    }
		});

		GridBagConstraints jComboBoxIssuerTypeConstraints = new GridBagConstraints();
		jComboBoxIssuerTypeConstraints.gridx = 2;
		jComboBoxIssuerTypeConstraints.gridy = 0;
		jComboBoxIssuerTypeConstraints.fill = GridBagConstraints.HORIZONTAL;
		jComboBoxIssuerTypeConstraints.weightx = 1.0;
		jComboBoxIssuerTypeConstraints.gridwidth=2;
		jComboBoxIssuerTypeConstraints.insets = new Insets(4,0,6,5);
		panel.add(jComboBoxIssuerType, jComboBoxIssuerTypeConstraints);		
		
		jLabelIssuerResidenceType = new JLabel(Constants.LANG.getString("ResidenceType"));
	    GridBagConstraints issuerResidenceLabelConstraints = new GridBagConstraints();
	    issuerResidenceLabelConstraints.gridx = 1;
	    issuerResidenceLabelConstraints.gridy = 1;
	    issuerResidenceLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
	    issuerResidenceLabelConstraints.weightx = 0.01;
	    issuerResidenceLabelConstraints.insets = new Insets(0,3,0,10);
	    jLabelIssuerResidenceType.setForeground(Constants.FONT_COLOR);
	    jLabelIssuerResidenceType.setFont(Constants.FONT_PLAIN);
	    jLabelIssuerResidenceType.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelIssuerResidenceType, issuerResidenceLabelConstraints);
		
		jComboBoxIssuerResidenceType = new JComboBox();
		jComboBoxIssuerResidenceType.setBackground(Color.white);
		jComboBoxIssuerResidenceType.setModel(new javax.swing.DefaultComboBoxModel(new String[] {Constants.LANG.getString("Foreigner"),Constants.LANG.getString("Resident"),Constants.LANG.getString("ResidentEU")}));
		jComboBoxIssuerResidenceType.setBounds(200, 70, 80, 20);
		jComboBoxIssuerResidenceType.setSelectedIndex(-1);
		jComboBoxIssuerResidenceType.setForeground(Constants.FONT_COLOR);
		jComboBoxIssuerResidenceType.setFont(Constants.FONT_PLAIN);
		jComboBoxIssuerResidenceType.setMaximumRowCount(4);
		jComboBoxIssuerResidenceType.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	((JComboBox)e.getSource()).setBackground(Color.WHITE);
		    }
		});
		GridBagConstraints jComboBoxIssuerResidenceConstraints = new GridBagConstraints();
		jComboBoxIssuerResidenceConstraints.gridx = 2;
		jComboBoxIssuerResidenceConstraints.gridy = 1;
		jComboBoxIssuerResidenceConstraints.fill = GridBagConstraints.HORIZONTAL;
		jComboBoxIssuerResidenceConstraints.gridwidth = 2;
		jComboBoxIssuerResidenceConstraints.weightx = 1.0;
		jComboBoxIssuerResidenceConstraints.insets = new Insets(0,0,6,5);
		panel.add(jComboBoxIssuerResidenceType, jComboBoxIssuerResidenceConstraints);
		
		jLabelIssuerFiscalId = new JLabel(Constants.LANG.getString("TaxIdentificationNumber"));
	    GridBagConstraints jLabelIssuerFiscalIdConstraints = new GridBagConstraints();
	    jLabelIssuerFiscalIdConstraints.gridx = 1;
	    jLabelIssuerFiscalIdConstraints.gridy = 2;
	    jLabelIssuerFiscalIdConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelIssuerFiscalIdConstraints.weightx = 0.01;
	    jLabelIssuerFiscalIdConstraints.insets = new Insets(0,3,0,10);
	    jLabelIssuerFiscalId.setForeground(Constants.FONT_COLOR);
	    jLabelIssuerFiscalId.setFont(Constants.FONT_PLAIN);
	    jLabelIssuerFiscalId.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelIssuerFiscalId.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelIssuerFiscalId, jLabelIssuerFiscalIdConstraints);

		jTextFieldIssuerFiscalId = new JTextField();
		jTextFieldIssuerFiscalId.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldIssuerFiscalId.setForeground(Constants.FONT_COLOR);
		jTextFieldIssuerFiscalId.setFont(Constants.FONT_PLAIN);
		jTextFieldIssuerFiscalId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints jTextFieldIssuerFiscalIdConstraints = new GridBagConstraints();
		jTextFieldIssuerFiscalIdConstraints.gridx = 2;
		jTextFieldIssuerFiscalIdConstraints.gridy = 2;
		jTextFieldIssuerFiscalIdConstraints.weightx = 1.0;
		jTextFieldIssuerFiscalIdConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldIssuerFiscalIdConstraints.gridwidth = 2;
		jTextFieldIssuerFiscalIdConstraints.insets = new Insets(0,0,6,5);
	    TaxIdentificationNumberValidator tinvi = new TaxIdentificationNumberValidator();
	    jTextFieldIssuerFiscalId.addFocusListener(tinvi);
		panel.add(jTextFieldIssuerFiscalId, jTextFieldIssuerFiscalIdConstraints);
		
		jLabelReceiverType = new JLabel(Constants.LANG.getString("ReceiverType"));
	    GridBagConstraints jLabelReceiverTypeConstraints = new GridBagConstraints();
	    jLabelReceiverTypeConstraints.gridx = 1;
	    jLabelReceiverTypeConstraints.gridy = 3;
	    jLabelReceiverTypeConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelReceiverTypeConstraints.weightx = 0.01;
	    jLabelReceiverTypeConstraints.insets = new Insets(0,3,0,10);
	    jLabelReceiverType.setForeground(Constants.FONT_COLOR);
	    jLabelReceiverType.setFont(Constants.FONT_PLAIN);
	    jLabelReceiverType.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelReceiverType.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelReceiverType, jLabelReceiverTypeConstraints);

		jComboBoxReceiverType = new JComboBox();
		jComboBoxReceiverType.setBackground(Color.white);
		jComboBoxReceiverType.setModel(new javax.swing.DefaultComboBoxModel(new String[] {Constants.LANG.getString("Individual"),Constants.LANG.getString("LegalEntity")}));
		jComboBoxReceiverType.setBounds(200, 70, 80, 20);
		jComboBoxReceiverType.setSelectedIndex(-1);
		jComboBoxReceiverType.setForeground(Constants.FONT_COLOR);
		jComboBoxReceiverType.setFont(Constants.FONT_PLAIN);
		jComboBoxReceiverType.setMaximumRowCount(2);
		jComboBoxReceiverType.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	((JComboBox)e.getSource()).setBackground(Color.WHITE);
		    }
		});
		GridBagConstraints jComboBoxReceiverTypeConstraints = new GridBagConstraints();
		jComboBoxReceiverTypeConstraints.gridx = 2;
		jComboBoxReceiverTypeConstraints.gridy = 3;
		jComboBoxReceiverTypeConstraints.fill = GridBagConstraints.HORIZONTAL;
		jComboBoxReceiverTypeConstraints.gridwidth = 2;
		jComboBoxReceiverTypeConstraints.weightx = 1.0;
		jComboBoxReceiverTypeConstraints.insets = new Insets(0,0,6,5);
		panel.add(jComboBoxReceiverType, jComboBoxReceiverTypeConstraints);
		
		jLabelReceiverResidenceType = new JLabel(Constants.LANG.getString("ResidenceType"));
	    GridBagConstraints jLabelReceiverResidenceTypeConstraints = new GridBagConstraints();
	    jLabelReceiverResidenceTypeConstraints.gridx = 1;
	    jLabelReceiverResidenceTypeConstraints.gridy = 4;
	    jLabelReceiverResidenceTypeConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelReceiverResidenceTypeConstraints.weightx = 0.01;
	    jLabelReceiverResidenceTypeConstraints.insets = new Insets(0,3,0,10);
	    jLabelReceiverResidenceType.setForeground(Constants.FONT_COLOR);
	    jLabelReceiverResidenceType.setFont(Constants.FONT_PLAIN);
	    jLabelReceiverResidenceType.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelReceiverResidenceType.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelReceiverResidenceType, jLabelReceiverResidenceTypeConstraints);

		jComboBoxReceiverResidenceType = new JComboBox();
		jComboBoxReceiverResidenceType.setBackground(Color.white);
		jComboBoxReceiverResidenceType.setModel(new javax.swing.DefaultComboBoxModel(new String[] {Constants.LANG.getString("Foreigner"),Constants.LANG.getString("Resident"),Constants.LANG.getString("ResidentEU")}));
		jComboBoxReceiverResidenceType.setBounds(200, 70, 80, 20);
		jComboBoxReceiverResidenceType.setSelectedIndex(-1);
		jComboBoxReceiverResidenceType.setForeground(Constants.FONT_COLOR);
		jComboBoxReceiverResidenceType.setFont(Constants.FONT_PLAIN);
		jComboBoxReceiverResidenceType.setMaximumRowCount(3);
		jComboBoxReceiverResidenceType.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
		    	((JComboBox)e.getSource()).setBackground(Color.WHITE);
		    }
		});
		GridBagConstraints jComboBoxdReceiverResidenceTypeConstraints = new GridBagConstraints();
		jComboBoxdReceiverResidenceTypeConstraints.gridx = 2;
		jComboBoxdReceiverResidenceTypeConstraints.gridy = 4;
		jComboBoxdReceiverResidenceTypeConstraints.fill = GridBagConstraints.HORIZONTAL;
		jComboBoxdReceiverResidenceTypeConstraints.gridwidth = 2;
		jComboBoxdReceiverResidenceTypeConstraints.weightx = 1.0;
		jComboBoxdReceiverResidenceTypeConstraints.insets = new Insets(0,0,6,5);
		panel.add(jComboBoxReceiverResidenceType, jComboBoxdReceiverResidenceTypeConstraints);
		
		jLabelReceiverFiscalId = new JLabel(Constants.LANG.getString("TaxIdentificationNumber"));
	    GridBagConstraints jLabelReceiverFiscalIdConstraints = new GridBagConstraints();
	    jLabelReceiverFiscalIdConstraints.gridx = 1;
	    jLabelReceiverFiscalIdConstraints.gridy = 5;
	    jLabelReceiverFiscalIdConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelReceiverFiscalIdConstraints.weightx = 0.01;
	    jLabelReceiverFiscalIdConstraints.insets = new Insets(0,3,0,10);
	    jLabelReceiverFiscalId.setForeground(Constants.FONT_COLOR);
	    jLabelReceiverFiscalId.setFont(Constants.FONT_PLAIN);
	    jLabelReceiverFiscalId.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelReceiverFiscalId.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelReceiverFiscalId, jLabelReceiverFiscalIdConstraints);
			
		jTextFieldReceiverFiscalId = new JTextField();
		jTextFieldReceiverFiscalId.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldReceiverFiscalId.setForeground(Constants.FONT_COLOR);
		jTextFieldReceiverFiscalId.setFont(Constants.FONT_PLAIN);
		jTextFieldReceiverFiscalId.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints jTextFieldReceiverFiscalIdConstraints = new GridBagConstraints();
		jTextFieldReceiverFiscalIdConstraints.gridx = 2;
		jTextFieldReceiverFiscalIdConstraints.gridy = 5;
		jTextFieldReceiverFiscalIdConstraints.gridwidth = 2;
		jTextFieldReceiverFiscalIdConstraints.weightx = 1.0;
		jTextFieldReceiverFiscalIdConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldReceiverFiscalIdConstraints.insets = new Insets(0,0,6,5);
	    TaxIdentificationNumberValidator tinvr = new TaxIdentificationNumberValidator();
	    jTextFieldReceiverFiscalId.addFocusListener(tinvr);
		panel.add(jTextFieldReceiverFiscalId, jTextFieldReceiverFiscalIdConstraints);
		
		jLabelAmount = new JLabel(Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")");
	    GridBagConstraints jLabelAmountConstraints = new GridBagConstraints();
	    jLabelAmountConstraints.gridx = 1;
	    jLabelAmountConstraints.gridy = 6;
	    jLabelAmountConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelAmountConstraints.weightx = 0.01;
	    jLabelAmountConstraints.insets = new Insets(0,3,0,10);
	    jLabelAmount.setForeground(Constants.FONT_COLOR);
	    jLabelAmount.setFont(Constants.FONT_PLAIN);
	    jLabelAmount.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelAmount.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
	    jLabelAmount.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelAmount, jLabelAmountConstraints);

		jTextFieldAmount = new JTextField();
		jTextFieldAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldAmount.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints jTextFieldAmountConstraints = new GridBagConstraints();
		jTextFieldAmountConstraints.gridx = 2;
		jTextFieldAmountConstraints.gridy = 6;
		jTextFieldAmountConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldAmountConstraints.gridwidth = 2;
		jTextFieldAmountConstraints.weightx = 1.0;
		jTextFieldAmountConstraints.insets = new Insets(0,0,5,5);
		AmountValidator av = new AmountValidator();
		jTextFieldAmount.addFocusListener(av);
		jTextFieldAmount.setForeground(Constants.FONT_COLOR);
		jTextFieldAmount.setFont(Constants.FONT_PLAIN);
		panel.add(jTextFieldAmount, jTextFieldAmountConstraints);
	    		
		// Se construye el panel Canvas
		fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
		
		// Se construye el panel principal
		panelPrin.setLayout(new GridBagLayout());
		
		GridBagConstraints canvasConstraints = new GridBagConstraints();
		canvasConstraints.gridx = 0;
		canvasConstraints.gridy = 0;
		canvasConstraints.fill = GridBagConstraints.HORIZONTAL;
		canvasConstraints.weightx = 1.0;
		canvasConstraints.ipady = 10;
		canvasConstraints.gridwidth = 4;
		canvasConstraints.insets = new Insets(0,2,0,2);
		canvasConstraints.anchor = GridBagConstraints.CENTER;
		panelPrin.add(fd, canvasConstraints);
		
		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 1;
		panelConstraints.fill = GridBagConstraints.BOTH;
		panelConstraints.weightx = 1.0;
		panelConstraints.weighty = 1.0;
		panelConstraints.gridwidth = 4;
		panelConstraints.insets = new Insets(4,10,10,10);
		panelPrin.add(jScroll, panelConstraints);
		
		JPanel relleno = new JPanel();
		relleno.setBackground(panelPrin.getBackground());
		GridBagConstraints rellenoConstraints = new GridBagConstraints();
		rellenoConstraints.gridx = 0;
		rellenoConstraints.gridy = 2;
		rellenoConstraints.fill = GridBagConstraints.HORIZONTAL;
		rellenoConstraints.weightx = 0.3;
		panelPrin.add(relleno, rellenoConstraints);
		
		GridBagConstraints buttonAceptarConstraints = new GridBagConstraints();
		buttonAceptarConstraints.gridx = 1;
		buttonAceptarConstraints.gridy = 2;
		buttonAceptarConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonAceptarConstraints.weightx = 0.2;
		buttonAceptarConstraints.insets = new Insets(1,1,8,1);
		panelPrin.add(jButtonOk, buttonAceptarConstraints);
		
		GridBagConstraints buttonCancelarConstraints = new GridBagConstraints();
		buttonCancelarConstraints.gridx = 2;
		buttonCancelarConstraints.gridy = 2;
		buttonCancelarConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonCancelarConstraints.weightx = 0.2;
		buttonCancelarConstraints.insets = new Insets(1,1,8,1);
		panelPrin.add(jButtonCancel, buttonCancelarConstraints);

		JPanel relleno2 = new JPanel();
		relleno2.setBackground(panelPrin.getBackground());
		rellenoConstraints.gridx = 3;
		panelPrin.add(relleno2, rellenoConstraints);
		
		add(panelPrin);
		
		setResizable(false);
		setSize(370, 315);
		fd.setSize(getWidth(), 13);
		setTitle(Constants.LANG.getString("GlobalReimAddTitle"));
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
		
		// Required properties load
		try {
			decimalProps = new Properties();
			if(version.equalsIgnoreCase(Constants.FACTURAE321)){
				decimalProps.load(this.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact321d_resource")));
			}else{
				decimalProps.load(this.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact32d_resource")));
			}
		} catch (IOException e) {
			logger.error("An exception occurred when loading the properties of the decimals file: " + e.getMessage());
			jButtonCancel.doClick();
		}
	}
	
	private void jButtonCancelActionPerformed() {
		jComboBoxIssuerType.setSelectedItem(null);
    	jComboBoxIssuerResidenceType.setSelectedItem(null);
    	jTextFieldIssuerFiscalId.setText(null);
    	jComboBoxReceiverType.setSelectedItem(null);
    	jComboBoxReceiverResidenceType.setSelectedItem(null);
    	jTextFieldReceiverFiscalId.setText(null);
    	jTextFieldAmount.setText(null);

		setVisible(false);
	}
	
	public String[] getValues() {
		
		String[] result = new String[7];	
		if (jTextFieldAmount.getText() != null && !"".equals(jTextFieldAmount.getText().trim()))
			result[6] = jTextFieldAmount.getText();
		else
			return result;
		
		if (jComboBoxIssuerType.getSelectedItem() != null)
			result[0] = jComboBoxIssuerType.getSelectedItem().toString();
		if (jComboBoxIssuerResidenceType.getSelectedItem() != null)
			result[1] = jComboBoxIssuerResidenceType.getSelectedItem().toString();
    	result[2] = jTextFieldIssuerFiscalId.getText();
		if (jComboBoxReceiverType.getSelectedItem() != null)
			result[3] = jComboBoxReceiverType.getSelectedItem().toString();
		if (jComboBoxReceiverResidenceType.getSelectedItem() != null)
			result[4] = jComboBoxReceiverResidenceType.getSelectedItem().toString();
    	result[5] = jTextFieldReceiverFiscalId.getText();
		
		return result;
	}
	
	private class TaxIdentificationNumberValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			String value = ((JTextField)evt.getSource()).getText();
			// The TaxidentificationNumer field must have 3-30 characters, taking into account two characters corresponding to the issuer s country name (idem with the receiver)
			if (value.length() > 27){
				((JTextField)evt.getSource()).setText("");
				fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
			}
		}
	}	
	
	/**
	 * 
	 * ReimbursableExpenses
	 * Suplidos incorporados a la factura.
	 *
	 */
	private class AmountValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			
			String value = ((JTextField)evt.getSource()).getText().trim();
			if (value != null && !"".equals(value)) {
				Double doubleObj = null;
				try{
					doubleObj = DoubleUtil.getDoubleFromPrettyNumber(value);
					doubleObj = DoubleUtil.roundByVersion(doubleObj, Integer.parseInt(decimalProps.getProperty("item_reim")), version);
					if (doubleObj.doubleValue() < 0){
						doubleObj = 0.0;
						fd.showMessage(Constants.LANG.getString("NOOKParamNegative"), Constants.ERROR_MSG_COLOR);
						((JTextField)evt.getSource()).setText("");
					} else
						((JTextField)evt.getSource()).setText(DoubleUtil.formatDecimal(doubleObj,Integer.parseInt(decimalProps.getProperty("item_reim"))));
				}
				catch(NumberFormatException nfe){
					if (((JTextField)evt.getSource()).getText() != null && 
							!"".equals(((JTextField)evt.getSource()).getText().trim()) ) {
						((JTextField)evt.getSource()).setText("");
						fd.showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
					}
					return;
				}
			}
		}
	}	

    private JLabel jLabelBoxIssuerType = null;
    private JLabel jLabelIssuerResidenceType = null;
    private JLabel jLabelIssuerFiscalId = null;
    private JLabel jLabelReceiverType = null;
    private JLabel jLabelReceiverResidenceType = null;
    private JLabel jLabelReceiverFiscalId = null;
    private JLabel jLabelAmount = null;

    private JComboBox jComboBoxIssuerType = null;
    private JComboBox jComboBoxIssuerResidenceType = null;
    private JTextField jTextFieldIssuerFiscalId = null;
    private JComboBox jComboBoxReceiverType = null;
    private JComboBox jComboBoxReceiverResidenceType = null;
    private JTextField jTextFieldReceiverFiscalId = null;
    private JTextField jTextFieldAmount = null;
    
    private JPanel panelPrin = null;
    private JPanel panel = null;
    private JScrollPane jScroll = null;
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
}