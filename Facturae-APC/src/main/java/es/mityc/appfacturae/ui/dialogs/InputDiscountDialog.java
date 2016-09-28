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
import es.mityc.appfacturae.utils.io.FacturaeUtil;

public class InputDiscountDialog extends JDialog {
	
	private static Log logger = LogFactory.getLog(InputDiscountDialog.class);
	private Properties decimalProps = null;	
	private String codeVersion = null;
	private FadingCanvas fd;
	private Double total = null;
	private boolean global = false;
	
	public InputDiscountDialog (Frame parent, boolean modal, Double totalGross, Double total, String version, boolean global) {
		super(parent, modal);
		codeVersion = FacturaeUtil.getVersionConst(version);
	    this.total = total;
	    this.global = global;
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
				if ( conceptTextField.getText() == null || 
						"".equals(conceptTextField.getText().trim()) ) {								
					flag = true;
					conceptTextField.requestFocus();  
					conceptTextField.setBackground(Constants.BKG_ERROR_COLOR);
				}
				if (global){
					if ( percentTextField.getText() == null || "".equals(percentTextField.getText().trim())){
						flag = true;
						percentTextField.requestFocus();
						percentTextField.setBackground(Constants.BKG_ERROR_COLOR);
					}
				}
				else {
					if ( amountTextField.getText() == null || 
							"".equals(amountTextField.getText().trim()) ) {
						if ( (Constants.BKG_ERROR_COLOR).equals(percentTextField.getBackground()))
							return;
						flag = true;
						amountTextField.requestFocus();  
						amountTextField.setBackground(Constants.BKG_ERROR_COLOR);
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
	    conceptLabel = new JLabel(Constants.LANG.getString("Item"));
	    GridBagConstraints conceptLabelConstraints = new GridBagConstraints();
	    conceptLabelConstraints.gridx = 1;
	    conceptLabelConstraints.gridy = 0;
	    conceptLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
	    conceptLabelConstraints.weightx = 0.01;
	    conceptLabelConstraints.insets = new Insets(0,3,0,10);
		conceptLabel.setForeground(Constants.FONT_COLOR);
		conceptLabel.setFont(Constants.FONT_PLAIN);
		conceptLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		conceptLabel.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
		conceptLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(conceptLabel, conceptLabelConstraints);

		conceptTextField = new JTextField();
		conceptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		conceptTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints conceptTextConstraints = new GridBagConstraints();
		conceptTextConstraints.gridx = 2;
		conceptTextConstraints.gridy = 0;
		conceptTextConstraints.fill = GridBagConstraints.HORIZONTAL;
		conceptTextConstraints.weightx = 1.0;
		conceptTextConstraints.insets = new Insets(0,0,6,5);
		ConceptValidator cv = new ConceptValidator();
		conceptTextField.addFocusListener(cv);
		panel.add(conceptTextField, conceptTextConstraints);		
		
	    rateLabel = new JLabel(Constants.LANG.getString("Rate"));
	    GridBagConstraints rateLabelConstraints = new GridBagConstraints();
	    rateLabelConstraints.gridx = 1;
	    rateLabelConstraints.gridy = 1;
	    rateLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
	    rateLabelConstraints.weightx = 0.01;
	    rateLabelConstraints.insets = new Insets(0,3,0,10);
		rateLabel.setForeground(Constants.FONT_COLOR);
		rateLabel.setFont(Constants.FONT_PLAIN);
		rateLabel.setHorizontalAlignment(SwingConstants.LEFT);
		// Si el descuento es global, el porcentaje es obligatorio (no se permiten descuentos por cantidad directa)
		if (global)
			rateLabel.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
		panel.add(rateLabel, rateLabelConstraints);
		percentTextField = new JTextField();
		percentTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		percentTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints percentTextConstraints = new GridBagConstraints();
		percentTextConstraints.gridx = 2;
		percentTextConstraints.gridy = 1;
		percentTextConstraints.fill = GridBagConstraints.HORIZONTAL;
		percentTextConstraints.weightx = 1.0;
		percentTextConstraints.insets = new Insets(0,0,6,5);
		DiscountValidator dv = new DiscountValidator();
		percentTextField.addFocusListener(dv);
		panel.add(percentTextField, percentTextConstraints);
		
	    amountLabel = new JLabel(Constants.LANG.getString("Amount")+" ("+Constants.LANG.getString("EuroSimbol")+")");
	    GridBagConstraints amountLabelConstraints = new GridBagConstraints();
	    amountLabelConstraints.gridx = 1;
	    amountLabelConstraints.gridy = 2;
	    amountLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
	    amountLabelConstraints.weightx = 0.01;
	    amountLabelConstraints.insets = new Insets(0,3,0,10);
		amountLabel.setForeground(Constants.FONT_COLOR);
		amountLabel.setFont(Constants.FONT_PLAIN);
		amountLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		amountLabel.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
		amountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(amountLabel, amountLabelConstraints);
	    amountTextField = new JTextField();
	    // Si el descuento es global, no se permiten descuentos por cantidad directa (se calculan en función del porcentaje)
		if (global){
			amountTextField.setEditable(false);
			amountTextField.setBackground(Constants.BKG_MAIN_COLOR);
		}
		amountTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		amountTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints amountTextConstraints = new GridBagConstraints();
		amountTextConstraints.gridx = 2;
		amountTextConstraints.gridy = 2;
		amountTextConstraints.fill = GridBagConstraints.HORIZONTAL;
		amountTextConstraints.weightx = 1.0;
		amountTextConstraints.insets = new Insets(0,0,6,5);
		AmountValidator av = new AmountValidator();
		amountTextField.addFocusListener(av);
		panel.add(amountTextField, amountTextConstraints);
	    		
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
		panelConstraints.insets = new Insets(8,10,10,10);
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
		setSize(370, 210);
		fd.setSize(getWidth(), 13);
		if (global)
			setTitle(Constants.LANG.getString("GeneralDiscountAddTitle"));
		else
			setTitle(Constants.LANG.getString("DiscountAddTitle"));
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
		
		// Se cargan las propiedades requeridas
		try {
			decimalProps = new Properties();
			decimalProps.load(this.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact"+codeVersion+"d_resource")));
		} catch (IOException e) {
			logger.error("An exception occurred when loading the properties of the decimals file: " + e.getMessage());
			jButtonCancel.doClick();
		}
	}
	
	private void jButtonCancelActionPerformed() {
		conceptTextField.setText(null);
	    percentTextField.setText(null);
	    amountTextField.setText(null);

	    setVisible(false);
		dispose();
	}
	
	public String[] getValues() {
		
		String[] result = new String[3];
		result[0] = conceptTextField.getText();
		result[1] = percentTextField.getText();
		result[2] = amountTextField.getText();
		
		return result;
	}
	
	private class ConceptValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			String value = ((JTextField)evt.getSource()).getText().trim();
			// El concepto debe ser un String de 2500 caracteres maximo
			if (value.length() > 2500){
				((JTextField)evt.getSource()).setText("");
				fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
			}
		}
	}
	
	private class DiscountValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			String value = ((JTextField)evt.getSource()).getText().trim();
			if (value != null && !"".equals(value)) {
				Double doubleObj = null;
				try{
					doubleObj = Double.parseDouble(value);
					doubleObj = DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_general_discount_rate")));
					if (doubleObj.doubleValue() > 100){
						fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
						((JTextField)evt.getSource()).setText("");
						((JTextField)evt.getSource()).setBackground(Constants.BKG_ERROR_COLOR);
						amountTextField.setText("");
						return;
					}
					else if (doubleObj.doubleValue() < 0){
						fd.showMessage(Constants.LANG.getString("NOOKParamNegative"), Constants.ERROR_MSG_COLOR);
						((JTextField)evt.getSource()).setText("");
						((JTextField)evt.getSource()).setBackground(Constants.BKG_ERROR_COLOR);
						amountTextField.setText("");
						return;
					}
					((JTextField)evt.getSource()).setText(String.valueOf(doubleObj));
				}
				catch(NumberFormatException nfe){
					((JTextField)evt.getSource()).setText("");
					((JTextField)evt.getSource()).setBackground(Constants.BKG_ERROR_COLOR);
					amountTextField.setText("");
					fd.showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
					return;
				}

				// Se establece la cantidad total descontada, calculada a partir del total
				doubleObj = (total * doubleObj.doubleValue()) / 100 ;
				doubleObj = DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_general_discount")));
				
				amountTextField.setText(String.valueOf(doubleObj));
				if (global)
					amountTextField.setBackground(Constants.BKG_MAIN_COLOR);
				else
					amountTextField.setBackground(Color.WHITE);
			}
		}
	}
	
	private class AmountValidator extends FocusAdapter {
		public void focusLost(FocusEvent evt){
			// Value may be a two decimal double
			String value = ((JTextField)evt.getSource()).getText();
			Double doubleObj = null;
			try{
				doubleObj = Double.parseDouble(value);
				((JTextField)evt.getSource()).setText(String.valueOf(DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_general_discount")))));
				
			}
			catch(NumberFormatException nfe){
				if (((JTextField)evt.getSource()).getText() != null && 
						!"".equals(((JTextField)evt.getSource()).getText().trim()) ) {
					((JTextField)evt.getSource()).setText("");
					fd.showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
				}
				return;
			}
			// Rate clean up if there is no correspondence
			value = percentTextField.getText().trim();
			Double rateReaded = null;
			if (value != null && !"".equals(value))
				rateReaded = Double.parseDouble(value);
			else
				return;
			
			Double amountExpected = (total * rateReaded.doubleValue()) / 100;
			amountExpected = DoubleUtil.round(amountExpected, Integer.parseInt(decimalProps.getProperty("item_general_discount")));
			
			value = ((JTextField)evt.getSource()).getText().trim();
			Double amountReaded = null;
			amountReaded = Double.parseDouble(value);

			if (amountReaded != null && amountReaded.compareTo(amountExpected) != 0)
				percentTextField.setText("");
		}
	}
	
    private JLabel conceptLabel = null;
    private JLabel rateLabel = null;
    private JLabel amountLabel = null;
    
    private JTextField conceptTextField = null;
    private JTextField percentTextField = null;
    private JTextField amountTextField = null;
    
    private JPanel panelPrin = null;
    private JPanel panel = null;
    private JScrollPane jScroll = null;
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
}
