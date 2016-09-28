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

import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.DoubleUtil;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.appfacturae.utils.io.IntegerUtil;

public class InputTaxesWithheldDialog extends JDialog {

	private Properties decimalProps = null;
	private FadingCanvas fd;
	private String version;
	private Double taxBase = null;
	
	public InputTaxesWithheldDialog (Frame parent, boolean modal, Double total, String version) {
		super(parent, modal);
	        
	    this.taxBase = total;
	    this.version = version;
	    
	    decimalProps = new Properties();
	    
	    loadConfiguration();
	    init();
		setLocationRelativeTo(parent);
	}

	private void init() {
		mainPanel = new JPanel();
		panel = new JPanel();
		jScroll = new JScrollPane();
		jButtonOk = new JButton();
		jButtonCancel = new JButton();
		fd = new FadingCanvas();

		mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
		
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
				if (jComboTaxType.getSelectedItem() != null && jComboTaxType.getSelectedIndex() != 0 && jComboTaxType.getSelectedIndex() != 2 && jComboTaxType.getSelectedIndex() != 3){
					if ( jTextFieldTaxTypeVal.getText() == null || 
							"".equals(jTextFieldTaxTypeVal.getText().trim() )) {
						if ( (Constants.BKG_ERROR_COLOR).equals(jTextFieldTaxTypeVal.getBackground()))
							return;
						flag = true;
						jTextFieldTaxTypeVal.requestFocus();  
						jTextFieldTaxTypeVal.setBackground(Constants.BKG_ERROR_COLOR);
					}					
				} else if ( jComboTaxTypeVal.getSelectedItem() == null || "".equals(jComboTaxTypeVal.getSelectedItem().toString().trim() )) {								
					flag = true;
					jComboTaxTypeVal.requestFocus();  
					jComboTaxTypeVal.setBackground(Constants.BKG_ERROR_COLOR);
				}
				
				if ( jComboTaxType.getSelectedItem() == null ) {								
					flag = true;
					jComboTaxType.requestFocus();  
					jComboTaxType.setBackground(Constants.BKG_ERROR_COLOR);
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

		// TaxType Label
		jLabelTaxType = new JLabel(Constants.LANG.getString("Tax"));
	    GridBagConstraints jLabelTaxTypeConstraints = new GridBagConstraints();
	    jLabelTaxTypeConstraints.gridx = 1;
	    jLabelTaxTypeConstraints.gridy = 0;
	    jLabelTaxTypeConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelTaxTypeConstraints.weightx = 0.01;
	    jLabelTaxTypeConstraints.insets = new Insets(0,3,0,10);
	    jLabelTaxType.setForeground(Constants.FONT_COLOR);
	    jLabelTaxType.setFont(Constants.FONT_PLAIN);
	    jLabelTaxType.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelTaxType.setHorizontalAlignment(SwingConstants.LEFT);
	    jLabelTaxType.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
		panel.add(jLabelTaxType, jLabelTaxTypeConstraints);

		// TaxType ComboBox
		jComboTaxType = new JComboBox();
		String[] arrayTaxesType = Constants.APP_PROP.getProperty("taxesType"+FacturaeUtil.getVersionConst(version)).split(";");
		for (int i = 0 ; i < arrayTaxesType.length ; i++) {
			String value = Constants.LANG.getString(arrayTaxesType[i]);
			if(value != null && value.trim().length() > 30) {
				value = value.substring(0, 30) + " ...";
			}
			arrayTaxesType[i] = value;
		}
		jComboTaxType.setModel(new javax.swing.DefaultComboBoxModel(arrayTaxesType));
		jComboTaxType.setSelectedIndex(-1);
		jComboTaxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTaxType(evt);
            }
        });
		jComboTaxType.setRenderer(new ComboBoxRenderer(true));

		GridBagConstraints jTextFieldTaxTypeConstraints = new GridBagConstraints();
		jTextFieldTaxTypeConstraints.gridx = 2;
		jTextFieldTaxTypeConstraints.gridy = 0;
		jTextFieldTaxTypeConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldTaxTypeConstraints.weightx = 1.0;
		jTextFieldTaxTypeConstraints.insets = new Insets(0,0,6,5);
		panel.add(jComboTaxType, jTextFieldTaxTypeConstraints);		
		
		// TaxRate Label
		jLabelTaxRate = new JLabel(Constants.LANG.getString("Rate"));
	    GridBagConstraints jLabelTaxRateConstraints = new GridBagConstraints();
	    jLabelTaxRateConstraints.gridx = 1;
	    jLabelTaxRateConstraints.gridy = 1;
	    jLabelTaxRateConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelTaxRateConstraints.weightx = 0.01;
	    jLabelTaxRateConstraints.insets = new Insets(0,3,0,10);
	    jLabelTaxRate.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
	    jLabelTaxRate.setForeground(Constants.FONT_COLOR);
	    jLabelTaxRate.setFont(Constants.FONT_PLAIN);
	    jLabelTaxRate.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelTaxRate, jLabelTaxRateConstraints);
		
		// TaxRate ComboBox
		jComboTaxTypeVal = new JComboBox();
		jComboTaxTypeVal.setModel(new javax.swing.DefaultComboBoxModel(new String[]{""}));
		jComboTaxTypeVal.setSelectedIndex(-1);
		GridBagConstraints jComboTaxTypeValConstraints = new GridBagConstraints();
		jComboTaxTypeValConstraints.gridx = 2;
		jComboTaxTypeValConstraints.gridy = 1;
		jComboTaxTypeValConstraints.fill = GridBagConstraints.HORIZONTAL;
		jComboTaxTypeValConstraints.weightx = 1.0;
		jComboTaxTypeValConstraints.insets = new Insets(0,0,6,5);
		jComboTaxTypeVal.setBackground(Constants.BKG_MAIN_COLOR);
		jComboTaxTypeVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectTaxRateValue(evt);
            }
        });
		panel.add(jComboTaxTypeVal, jComboTaxTypeValConstraints);		
		
		// TaxTypeVal TextField
		jTextFieldTaxTypeVal = new JTextField();
		jTextFieldTaxTypeVal.setText("");
		jTextFieldTaxTypeVal.setVisible(false);
		jTextFieldTaxTypeVal.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints jTextFieldTaxTypeValConstraints = new GridBagConstraints();
		jTextFieldTaxTypeValConstraints.gridx = 2;
		jTextFieldTaxTypeValConstraints.gridy = 1;
		jTextFieldTaxTypeValConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldTaxTypeValConstraints.weightx = 1.0;
		jTextFieldTaxTypeValConstraints.insets = new Insets(0,0,6,5);
		TaxTypeValValidator ttvv = new TaxTypeValValidator();
		jTextFieldTaxTypeVal.addFocusListener(ttvv);
		jTextFieldTaxTypeVal.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		panel.add(jTextFieldTaxTypeVal, jTextFieldTaxTypeValConstraints);
		
		// TaxableBase Label 
		jLabelTaxableBase = new JLabel(Constants.LANG.getString("TaxableBase")+" ("+Constants.LANG.getString("EuroSimbol")+")");
	    GridBagConstraints jLabelTaxableBaseConstraints = new GridBagConstraints();
	    jLabelTaxableBaseConstraints.gridx = 1;
	    jLabelTaxableBaseConstraints.gridy = 2;
	    jLabelTaxableBaseConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelTaxableBaseConstraints.weightx = 0.01;
	    jLabelTaxableBaseConstraints.insets = new Insets(0,3,0,10);
	    jLabelTaxableBase.setForeground(Constants.FONT_COLOR);
	    jLabelTaxableBase.setFont(Constants.FONT_PLAIN);
	    jLabelTaxableBase.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelTaxableBase.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelTaxableBase, jLabelTaxableBaseConstraints);
		
		// TaxableBase TextField
		jTextFieldTaxableBase = new JTextField();
		jTextFieldTaxableBase.setText(String.valueOf(taxBase));
		jTextFieldTaxableBase.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldTaxableBase.setEditable(false);
		jTextFieldTaxableBase.setBackground(Constants.BKG_MAIN_COLOR);
		GridBagConstraints jTextFieldTaxableBaseConstraints = new GridBagConstraints();
		jTextFieldTaxableBaseConstraints.gridx = 2;
		jTextFieldTaxableBaseConstraints.gridy = 2;
		jTextFieldTaxableBaseConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldTaxableBaseConstraints.weightx = 1.0;
		jTextFieldTaxableBaseConstraints.insets = new Insets(0,0,6,5);
		panel.add(jTextFieldTaxableBase, jTextFieldTaxableBaseConstraints);
		
		// TaxAmount Label
		jLabelTaxAmount = new JLabel(Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")");
	    GridBagConstraints jLabelTaxAmountConstraints = new GridBagConstraints();
	    jLabelTaxAmountConstraints.gridx = 1;
	    jLabelTaxAmountConstraints.gridy = 3;
	    jLabelTaxAmountConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jLabelTaxAmountConstraints.weightx = 0.01;
	    jLabelTaxAmountConstraints.insets = new Insets(0,3,0,10);
	    jLabelTaxAmount.setForeground(Constants.FONT_COLOR);
	    jLabelTaxAmount.setFont(Constants.FONT_PLAIN);
	    jLabelTaxAmount.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelTaxAmount.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelTaxAmount, jLabelTaxAmountConstraints);

		// TaxAmount TextField
		jTextFieldTaxAmount = new JTextField();
		jTextFieldTaxAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		jTextFieldTaxAmount.setEditable(false);
		jTextFieldTaxAmount.setBackground(Constants.BKG_MAIN_COLOR);
		GridBagConstraints jTextFieldTaxAmountConstraints = new GridBagConstraints();
		jTextFieldTaxAmountConstraints.gridx = 2;
		jTextFieldTaxAmountConstraints.gridy = 3;
		jTextFieldTaxAmountConstraints.fill = GridBagConstraints.HORIZONTAL;
		jTextFieldTaxAmountConstraints.weightx = 1.0;
		jTextFieldTaxAmountConstraints.insets = new Insets(0,0,6,5);
		panel.add(jTextFieldTaxAmount, jTextFieldTaxAmountConstraints);
	    		
		// Se construye el panel Canvas
		fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
		
		// Se construye el panel principal
		mainPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints canvasConstraints = new GridBagConstraints();
		canvasConstraints.gridx = 0;
		canvasConstraints.gridy = 0;
		canvasConstraints.fill = GridBagConstraints.HORIZONTAL;
		canvasConstraints.weightx = 1.0;
		canvasConstraints.ipady = 10;
		canvasConstraints.gridwidth = 4;
		canvasConstraints.insets = new Insets(0,2,0,2);
		canvasConstraints.anchor = GridBagConstraints.CENTER;
		mainPanel.add(fd, canvasConstraints);
		
		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 1;
		panelConstraints.fill = GridBagConstraints.BOTH;
		panelConstraints.weightx = 1.0;
		panelConstraints.weighty = 1.0;
		panelConstraints.gridwidth = 4;
		panelConstraints.insets = new Insets(8,10,10,10);
		mainPanel.add(jScroll, panelConstraints);
		
		JPanel background = new JPanel();
		background.setBackground(mainPanel.getBackground());
		GridBagConstraints bkgConstraints = new GridBagConstraints();
		bkgConstraints.gridx = 0;
		bkgConstraints.gridy = 2;
		bkgConstraints.fill = GridBagConstraints.HORIZONTAL;
		bkgConstraints.weightx = 0.3;
		mainPanel.add(background, bkgConstraints);
		
		GridBagConstraints buttonAcceptConstraints = new GridBagConstraints();
		buttonAcceptConstraints.gridx = 1;
		buttonAcceptConstraints.gridy = 2;
		buttonAcceptConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonAcceptConstraints.weightx = 0.2;
		buttonAcceptConstraints.insets = new Insets(1,1,8,1);
		mainPanel.add(jButtonOk, buttonAcceptConstraints);
		
		GridBagConstraints buttonCancelConstraints = new GridBagConstraints();
		buttonCancelConstraints.gridx = 2;
		buttonCancelConstraints.gridy = 2;
		buttonCancelConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonCancelConstraints.weightx = 0.2;
		buttonCancelConstraints.insets = new Insets(1,1,8,1);
		mainPanel.add(jButtonCancel, buttonCancelConstraints);

		JPanel background2 = new JPanel();
		background2.setBackground(mainPanel.getBackground());
		bkgConstraints.gridx = 3;
		mainPanel.add(background2, bkgConstraints);

		add(mainPanel);

		setResizable(false);
		setSize(390, 270);
		fd.setSize(getWidth(), 13);
		setTitle(Constants.LANG.getString("TaxWithHeldAddTitle"));
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
	
	private void jButtonCancelActionPerformed() {
		jComboTaxType.setSelectedItem(null);
		jComboTaxTypeVal.setSelectedItem(null);
		jTextFieldTaxableBase.setText(null);
		jTextFieldTaxAmount.setText(null);
		jTextFieldTaxTypeVal.setText(null);

		setVisible(false);
		dispose();
	}
	
	private void loadConfiguration(){
		// The required properties are loaded
		try {
			decimalProps.load(this.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact"+FacturaeUtil.getVersionConst(version)+"d_resource")));
		} catch (IOException e) {
			fd.showMessage(Constants.LANG.getString("MainWindowException1"), Constants.ERROR_MSG_COLOR);
			jButtonCancel.doClick();
		}
	}

	public String[] getValues() {

		String[] result = new String[4];
		if (jComboTaxType.getSelectedItem() != null)
			result[0] = jComboTaxType.getSelectedItem().toString().split(" ")[0] + " - " + IntegerUtil.to2String(jComboTaxType.getSelectedIndex()+1);
		if (jComboTaxTypeVal.getSelectedItem() != null)
			result[1] = jComboTaxTypeVal.getSelectedItem().toString();
		else
			result[1] = jTextFieldTaxTypeVal.getText();
		result[2] = jTextFieldTaxableBase.getText();
		result[3] = jTextFieldTaxAmount.getText(); 

		return result;
	}

	private void selectTaxRateValue(java.awt.event.ActionEvent evt) {
		
		if (((JComboBox)evt.getSource()).getSelectedItem() == null)
    		return;
		
		try{
			if (jComboTaxTypeVal.getSelectedItem() == null){
				jTextFieldTaxAmount.setText("");
	    		return;
			}
			String strTaxRate = jComboTaxTypeVal.getSelectedItem().toString().trim();
			if (strTaxRate != null && !strTaxRate.equals("")){
				Double taxRate = Double.parseDouble(strTaxRate);
				jTextFieldTaxAmount.setText(String.valueOf(DoubleUtil.round( (taxRate*Double.parseDouble(jTextFieldTaxableBase.getText())/100.00), Integer.parseInt(decimalProps.getProperty("item_tax_With"))) ));
			}
			else
				jTextFieldTaxAmount.setText("");
		}
		catch(NumberFormatException nfe){
			jTextFieldTaxAmount.setText("");
		}		
	}

	private class TaxTypeValValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			String baseValue = ((JTextField)evt.getSource()).getText().trim();
			if (baseValue != null && !"".equals(baseValue) ) {
				try{
					Double doubleObjRate = Double.parseDouble(baseValue);
					doubleObjRate = DoubleUtil.round(doubleObjRate, Integer.parseInt(decimalProps.getProperty("item_tax_rate_With")));
					if (doubleObjRate.doubleValue() > 100){
						((JTextField)evt.getSource()).setText("");
						((JTextField)evt.getSource()).setBackground(Constants.BKG_ERROR_COLOR);
						fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
						return;					
					}
					else if (doubleObjRate.doubleValue() < 0){
						((JTextField)evt.getSource()).setText("");
						((JTextField)evt.getSource()).setBackground(Constants.BKG_ERROR_COLOR);
						fd.showMessage(Constants.LANG.getString("NOOKParamNegative"), Constants.ERROR_MSG_COLOR);
						return;
					}
					((JTextField)evt.getSource()).setText(String.valueOf(doubleObjRate));
					Double doubleObj = doubleObjRate*Double.parseDouble(jTextFieldTaxableBase.getText())/100.00;
					doubleObj = DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_tax_With")));
					jTextFieldTaxAmount.setText(String.valueOf(doubleObj));
				}
				catch(NumberFormatException nfe){
					if (((JTextField) evt.getSource()).getText() != null){
						if (!"".equals(((JTextField) evt.getSource()).getText().trim()))
							fd.showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
					}
					((JTextField) evt.getSource()).setText("");
					return;
				}
			}
		}
	}	

    private void selectTaxType(java.awt.event.ActionEvent evt) {
    	
    	if (((JComboBox)evt.getSource()).getSelectedItem() == null)
    		return;
    	
    	if (jComboTaxType.getSelectedIndex() != -1) {
    		jComboTaxType.setBackground(Color.WHITE);
    	}
    	
    	if (jComboTaxType.getSelectedIndex() == 0 || jComboTaxType.getSelectedIndex() == 2 || jComboTaxType.getSelectedIndex() == 3){
    		if (jComboTaxType.getSelectedIndex() == 0)
    			jComboTaxTypeVal.setModel(new javax.swing.DefaultComboBoxModel((Constants.TAXRATE_PROP.getProperty("VATrates").split(";"))));
    		else if (jComboTaxType.getSelectedIndex() == 2)
    			jComboTaxTypeVal.setModel(new javax.swing.DefaultComboBoxModel((Constants.TAXRATE_PROP.getProperty("IGICrates").split(";"))));
    		else
       			jComboTaxTypeVal.setModel(new javax.swing.DefaultComboBoxModel((Constants.TAXRATE_PROP.getProperty("PITrates").split(";"))));
    		Double taxRate = Double.parseDouble(jComboTaxTypeVal.getSelectedItem().toString());
    		jTextFieldTaxAmount.setText(String.valueOf(DoubleUtil.round( (taxRate*Double.parseDouble(jTextFieldTaxableBase.getText())/100.0), Integer.parseInt(decimalProps.getProperty("item_tax_With"))) ));
    		jComboTaxTypeVal.setVisible(true);
    		jTextFieldTaxTypeVal.setVisible(false);
    		jTextFieldTaxTypeVal.setText("");
    	}
    	else{
    		jTextFieldTaxAmount.setText("");
    		jComboTaxTypeVal.setVisible(false);
    		jComboTaxTypeVal.setSelectedItem(null);
    		jTextFieldTaxTypeVal.setVisible(true);
    	}	
    }
        	
    private JLabel jLabelTaxType = null;
    private JLabel jLabelTaxRate = null;
    private JLabel jLabelTaxableBase = null;
    private JLabel jLabelTaxAmount = null;

    private JComboBox jComboTaxType = null;      
    private JComboBox jComboTaxTypeVal = null;
    
    private JTextField jTextFieldTaxableBase = null;
    private JTextField jTextFieldTaxAmount = null;
    private JTextField jTextFieldTaxTypeVal = null;
    
    private JPanel mainPanel = null;
    private JPanel panel = null;
    private JScrollPane jScroll = null;
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
}