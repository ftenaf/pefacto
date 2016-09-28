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

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.mityc.appfacturae.facturae.CorrectionMethodDescriptionType;
import es.mityc.appfacturae.facturae32.ReasonDescriptionType;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.utils.constants.Constants;

public class InputCorrectiveDialog extends JDialog {
	
	private FadingCanvas fd;
	private String version;
	private boolean flag = false;
	
	public InputCorrectiveDialog (Frame parent, boolean modal, String version) {
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
				if (flag)
					return;
				flag = false;
				if ( jComboBoxDesc.getSelectedItem() == null) {								
					flag = true;
					jComboBoxDesc.requestFocus();  
					jComboBoxDesc.setBackground(Constants.BKG_ERROR_COLOR);
				}

				if (jComboBoxDescMeth.getSelectedItem() == null) {
					flag = true;
					jComboBoxDescMeth.requestFocus();  
					jComboBoxDescMeth.setBackground(Constants.BKG_ERROR_COLOR);
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
		
		// Main panel
	    jLabelDesc = new JLabel(Constants.LANG.getString("Description"));
	    GridBagConstraints descLabelConstraints = new GridBagConstraints();
	    descLabelConstraints.gridx = 1;
	    descLabelConstraints.gridy = 0;
	    descLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
	    descLabelConstraints.weightx = 0.01;
	    descLabelConstraints.insets = new Insets(0,3,0,10);
	    jLabelDesc.setForeground(Constants.FONT_COLOR);
	    jLabelDesc.setFont(Constants.FONT_PLAIN);
	    jLabelDesc.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelDesc.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
	    jLabelDesc.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelDesc, descLabelConstraints);

		jComboBoxDesc = new JComboBox();
		ReasonDescriptionType[] values = ReasonDescriptionType.values();
		String[] valStrings = new String[values.length];
		for (int i = 0; i < values.length; ++i)
			valStrings[i] = values[i].value();
		jComboBoxDesc.setModel(new DefaultComboBoxModel(valStrings));
		jComboBoxDesc.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints descConstraints = new GridBagConstraints();
		descConstraints.gridx = 2;
		descConstraints.gridy = 0;
		descConstraints.fill = GridBagConstraints.HORIZONTAL;
		descConstraints.weightx = 1.0;
		descConstraints.insets = new Insets(0,0,6,5);
		panel.add(jComboBoxDesc, descConstraints);		
		
	    jLabelDescMeth = new JLabel(Constants.LANG.getString("Method"));
	    GridBagConstraints methodConstraints = new GridBagConstraints();
	    methodConstraints.gridx = 1;
	    methodConstraints.gridy = 1;
	    methodConstraints.fill = GridBagConstraints.HORIZONTAL;
	    methodConstraints.weightx = 0.01;
	    methodConstraints.insets = new Insets(0,3,0,10);
	    jLabelDescMeth.setForeground(Constants.FONT_COLOR);
	    jLabelDescMeth.setFont(Constants.FONT_PLAIN);
	    jLabelDescMeth.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelDescMeth.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
	    jLabelDescMeth.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelDescMeth, methodConstraints);
		
		jComboBoxDescMeth = new JComboBox();
		CorrectionMethodDescriptionType[] valuesMethod = CorrectionMethodDescriptionType.values();
		valStrings = new String[valuesMethod.length];
		for (int i = 0; i < valuesMethod.length; ++i)
			valStrings[i] = valuesMethod[i].value();
		jComboBoxDescMeth.setModel(new DefaultComboBoxModel(valStrings));
		jComboBoxDescMeth.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				((JTextField)arg0.getSource()).setBackground(Color.WHITE);				
			}				
		});
		GridBagConstraints methodComboConstraints = new GridBagConstraints();
		methodComboConstraints.gridx = 2;
		methodComboConstraints.gridy = 1;
		methodComboConstraints.fill = GridBagConstraints.HORIZONTAL;
		methodComboConstraints.weightx = 1.0;
		methodComboConstraints.insets = new Insets(0,0,6,5);
		panel.add(jComboBoxDescMeth, methodComboConstraints);
	    
		jLabelAddReason = new JLabel(Constants.LANG.getString("AdditionalReasonCorrective"));
	    GridBagConstraints reasonConstraints = new GridBagConstraints();
	    reasonConstraints.gridx = 1;
	    reasonConstraints.gridy = 2;
	    reasonConstraints.fill = GridBagConstraints.HORIZONTAL;
	    reasonConstraints.weightx = 0.01;
	    reasonConstraints.insets = new Insets(0,3,0,10);
	    jLabelAddReason.setForeground(Constants.FONT_COLOR);
	    jLabelAddReason.setFont(Constants.FONT_PLAIN);
	    jLabelAddReason.setHorizontalTextPosition(SwingConstants.RIGHT);
	    jLabelAddReason.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(jLabelAddReason, reasonConstraints);
				
		jTextFieldReason = new JTextField();
		GridBagConstraints reasonTextFieldConstraints = new GridBagConstraints();
		reasonTextFieldConstraints.gridx = 2;
		reasonTextFieldConstraints.gridy = 2;
		reasonTextFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
		reasonTextFieldConstraints.weightx = 0.01;
		reasonTextFieldConstraints.insets = new Insets(0,3,0,10);
		panel.add(jTextFieldReason, reasonTextFieldConstraints);
		
		if (!version.equals("3.2")){
			jLabelAddReason.setVisible(false);
			jTextFieldReason.setVisible(false);
		}
		
		jTextFieldReason.addFocusListener(new FocusAdapter() {
        	public void focusLost(FocusEvent evt) {
        		if (((JTextField)evt.getSource()).getText() != null && ((JTextField)evt.getSource()).getText().length() > 2500){
        			fd.showMessage(Constants.LANG.getString("NOOKParamTooHigh"),Constants.ERROR_MSG_COLOR);
        			flag = true;
        		}
        		else {
        			flag = false;
        		}
        	}
        });
		
		// Se construye el panel Canvas
		fd.setFont(Constants.TITLE_FONT);
		
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
		setSize(650, 220);
		fd.setSize(getWidth(), 13);
		setTitle(Constants.LANG.getString("CorrectiveDescTitle"));
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
		jComboBoxDesc.setSelectedItem(null);
	    jComboBoxDescMeth.setSelectedItem(null);

	    setVisible(false);
		dispose();
	}
	
	public Object[] getValues() {
		
		Object[] result = new String[3];
		result[0] = jComboBoxDesc.getSelectedItem();
		result[1] = jComboBoxDescMeth.getSelectedItem();
		result[2] = jTextFieldReason.getText();
		
		return result;
	}
	
    private JLabel jLabelDesc = null;
    private JLabel jLabelDescMeth = null;
    private JLabel jLabelAddReason = null;
    
    private JTextField jTextFieldReason = null;
    
    private JComboBox jComboBoxDesc= null;
    private JComboBox jComboBoxDescMeth= null;
    
    private JPanel panelPrin = null;
    private JPanel panel = null;
    private JScrollPane jScroll = null;
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
}
