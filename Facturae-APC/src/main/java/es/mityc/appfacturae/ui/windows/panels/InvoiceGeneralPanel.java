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
package es.mityc.appfacturae.ui.windows.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import es.mityc.appfacturae.ui.windows.GenerateInvoiceWindow;
import es.mityc.appfacturae.utils.constants.Constants;

public class InvoiceGeneralPanel extends JPanel {
    
	private String version = null;
    
	public InvoiceGeneralPanel(String version){
		super();
		this.version = version;
		initComponents();
	}
	
	/** This method is called from within the constructor to initialize the form.*/
    
    private void initComponents() {
    	
	    jLabelInvNum = new javax.swing.JLabel();
		jTextFieldSeries = new javax.swing.JTextField();
		jTextFieldNumber = new javax.swing.JTextField();
		jLabelExpDate = new javax.swing.JLabel();
	    jLabelOpDate = new javax.swing.JLabel();
	    jPanelPlace = new javax.swing.JPanel();
		    jLabelPCode = new javax.swing.JLabel();
		    jLabelPlDesc = new javax.swing.JLabel();
		    jTextFieldPlPCode = new javax.swing.JTextField();
		    jTextFieldPlDesc = new javax.swing.JTextField();
		    jCheckBoxIsReceived = new javax.swing.JCheckBox();
		jPanelPeriod = new javax.swing.JPanel();
		    jLabelPeriodFrom = new javax.swing.JLabel();
		    jLabelPeriodTo = new javax.swing.JLabel();
		    jCalendarComboBoxPeriodTo = new de.wannawork.jcalendar.JCalendarComboBox();
		    jCalendarComboBoxPeriodFrom = new de.wannawork.jcalendar.JCalendarComboBox();
		    jRadioButtonPeriod = new javax.swing.JRadioButton();
		    
		    jCalendarComboBoxExpDate = new de.wannawork.jcalendar.JCalendarComboBox();	   
		    jCalendarComboBoxOpDate = new de.wannawork.jcalendar.JCalendarComboBox();
		    jRadioButtonOpDate = new javax.swing.JRadioButton();
		    
		((JFormattedTextField)((JSpinner)jCalendarComboBoxExpDate.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapter(){
		   	public void focusLost(FocusEvent evt){
		   		if ( !((JFormattedTextField)evt.getSource()).isEditValid() ) {
		   			jCalendarComboBoxExpDate.setBackground(Constants.BKG_ERROR_COLOR);
		   			((GenerateInvoiceWindow)jCalendarComboBoxExpDate.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotValid"), Constants.ERROR_MSG_COLOR);
		   		}
		   	}
		});

		((JFormattedTextField)((JSpinner)jCalendarComboBoxOpDate.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapter(){
		  	public void focusLost(FocusEvent evt){
		   		if ( !((JFormattedTextField)evt.getSource()).isEditValid() ) {
		   			jCalendarComboBoxOpDate.setBackground(Constants.BKG_ERROR_COLOR);
		   			((GenerateInvoiceWindow)jCalendarComboBoxOpDate.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotValid"), Constants.ERROR_MSG_COLOR);
		   		}
		   	}
		});
		
		jLabelInvNum.setFont(Constants.FONT_PLAIN);
		jLabelInvNum.setForeground(Constants.FONT_COLOR);
		jLabelInvNum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		jLabelInvNum.setText(Constants.LANG.getString("InvoiceNumber")); 
	
		jTextFieldSeries.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldSeries.setEditable(false);
		jTextFieldSeries.setForeground(java.awt.Color.gray);
		jTextFieldSeries.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		jTextFieldSeries.setToolTipText(Constants.LANG.getString("InvoiceSeriesTip")); 
		jTextFieldSeries.setDisabledTextColor(Constants.SELECTION_COLOR);
		// The series value is loaded from Configuration.properties file.
		jTextFieldSeries.setText(Constants.CONFIG_PROP.getProperty("series"));
		jTextFieldNumber.setBackground(Constants.BKG_MAIN_COLOR);
// 21-11-2014 permitir modificar el número de factura
//		jTextFieldNumber.setEditable(false);
		jTextFieldNumber.setEditable(true);		
		jTextFieldNumber.setForeground(java.awt.Color.gray);
		jTextFieldNumber.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		jTextFieldNumber.setToolTipText(Constants.LANG.getString("InvoiceNumberTip")); 
		jTextFieldNumber.setDisabledTextColor(Constants.SELECTION_COLOR);
		// The issued invoice number value is loaded from Configuration.properties file.
		jTextFieldNumber.setText(Constants.CONFIG_PROP.getProperty("issuedId"));
	
		jLabelExpDate.setFont(Constants.FONT_PLAIN);
		jLabelExpDate.setForeground(Constants.FONT_COLOR);
		jLabelExpDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		jLabelExpDate.setText(Constants.LANG.getString("ExpeditionDate")); 
		
		jLabelOpDate.setFont(Constants.FONT_PLAIN);
		jLabelOpDate.setForeground(Constants.FONT_COLOR);
		jLabelOpDate.setText(Constants.LANG.getString("OperationDate")); 
	
		jPanelPlace.setBackground(Constants.BKG_MAIN_COLOR);
		jPanelPlace.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("Place"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
	
		jLabelPCode.setFont(Constants.FONT_PLAIN);
		jLabelPCode.setForeground(Constants.FONT_COLOR);
		jLabelPCode.setText(Constants.LANG.getString("PostCode")); 
	
		jLabelPlDesc.setFont(Constants.FONT_PLAIN);
		jLabelPlDesc.setForeground(Constants.FONT_COLOR);
		jLabelPlDesc.setText(Constants.LANG.getString("Description")); 
	
		jTextFieldPlPCode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		ExpressionValidator evPCode = new ExpressionValidator("[0-9]{5}","NOOKParam5Digit");
		jTextFieldPlPCode.addFocusListener(evPCode);
		
		jTextFieldPlDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
		ExpressionValidator evPDesc = new ExpressionValidator(".{0,20}","NOOKParamTooHigh");
		jTextFieldPlDesc.addFocusListener(evPDesc);
		
		org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanelPlace);
		jPanelPlace.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(
		    jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		    .add(jPanel5Layout.createSequentialGroup()
		        .addContainerGap()
		        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		            .add(jLabelPlDesc, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .add(jLabelPCode, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
		        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jTextFieldPlDesc, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
		            .add(jTextFieldPlPCode, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
		        .addContainerGap())
		);
		jPanel5Layout.setVerticalGroup(
		    jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		    .add(jPanel5Layout.createSequentialGroup()
		        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		            .add(jTextFieldPlPCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		            .add(jLabelPCode))
		        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		            .add(jTextFieldPlDesc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		            .add(jLabelPlDesc))
		        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
	
		jCheckBoxIsReceived.setBackground(Constants.BKG_MAIN_COLOR);
		jCheckBoxIsReceived.setFont(Constants.FONT_PLAIN);
		jCheckBoxIsReceived.setForeground(Constants.FONT_COLOR);
		jCheckBoxIsReceived.setText(Constants.LANG.getString("IsReceivedInvoice"));
		jCheckBoxIsReceived.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		        jCheckBoxIsReceivedActionPerformed();
		    }
		});
	
		jPanelPeriod.setBackground(Constants.BKG_MAIN_COLOR);
		jPanelPeriod.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("InvoicingPeriod"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
		jPanelPeriod.setLayout(null);
	
		jLabelPeriodFrom.setFont(Constants.FONT_PLAIN);
		jLabelPeriodFrom.setForeground(Constants.FONT_COLOR);
		jLabelPeriodFrom.setText(Constants.LANG.getString("From")); 
		jPanelPeriod.add(jLabelPeriodFrom);
		jLabelPeriodFrom.setBounds(70, 25, 40, 14);
	
		((JFormattedTextField)((JSpinner)jCalendarComboBoxPeriodTo.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent evt) {
				if ( !((JFormattedTextField)evt.getSource()).isEditValid() ) {
					jCalendarComboBoxPeriodTo.setBackground(Constants.BKG_ERROR_COLOR);
					((GenerateInvoiceWindow)jCalendarComboBoxPeriodTo.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotValid"), Constants.ERROR_MSG_COLOR);
				} else if (jCalendarComboBoxPeriodFrom.getCalendar().getTime() != null) {
					if (jCalendarComboBoxPeriodFrom.getCalendar().getTime().after(jCalendarComboBoxPeriodTo.getCalendar().getTime())) {
						jCalendarComboBoxPeriodTo.setBackground(Constants.BKG_ERROR_COLOR);
						jCalendarComboBoxPeriodTo.setCalendar(jCalendarComboBoxPeriodFrom.getCalendar());
						((GenerateInvoiceWindow)jCalendarComboBoxPeriodTo.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotAfterOrigin"), Constants.ERROR_MSG_COLOR);
					}
				}
			}
		});
		
		jLabelPeriodTo.setFont(Constants.FONT_PLAIN);
		jLabelPeriodTo.setForeground(Constants.FONT_COLOR);
		jLabelPeriodTo.setText(Constants.LANG.getString("To")); 
		jPanelPeriod.add(jLabelPeriodTo);
		jLabelPeriodTo.setBounds(70, 55, 40, 14);
		jPanelPeriod.add(jCalendarComboBoxPeriodTo);
		jCalendarComboBoxPeriodTo.setBounds(120, 50, 120, 18);
		jCalendarComboBoxPeriodTo.setVisible(false);
		jPanelPeriod.add(jCalendarComboBoxPeriodFrom);
		jCalendarComboBoxPeriodFrom.setBounds(120, 20, 120, 18);
		jCalendarComboBoxPeriodFrom.setVisible(false);
		((JFormattedTextField)((JSpinner)jCalendarComboBoxPeriodFrom.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent evt){
				if ( !((JFormattedTextField)evt.getSource()).isEditValid() ) {
					jCalendarComboBoxPeriodFrom.setBackground(Constants.BKG_ERROR_COLOR);
					((GenerateInvoiceWindow)jCalendarComboBoxPeriodFrom.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotValid"), Constants.ERROR_MSG_COLOR);
				} else if (jCalendarComboBoxPeriodTo.getCalendar().getTime() != null) {
					if (jCalendarComboBoxPeriodFrom.getCalendar().getTime().before(jCalendarComboBoxPeriodFrom.getCalendar().getTime())) {
						jCalendarComboBoxPeriodFrom.setBackground(Constants.BKG_ERROR_COLOR);
						jCalendarComboBoxPeriodFrom.setCalendar(jCalendarComboBoxPeriodFrom.getCalendar());
						((GenerateInvoiceWindow)jCalendarComboBoxPeriodFrom.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotAfterOrigin"), Constants.ERROR_MSG_COLOR);
					}
				}
			}
		});
	
		jRadioButtonPeriod.setBackground(Constants.BKG_MAIN_COLOR);
		jRadioButtonPeriod.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        jRadioButtonActionPerformed();
		    }
		});
		jPanelPeriod.add(jRadioButtonPeriod);
		jRadioButtonPeriod.setBounds(25, 35, 20, 21);
	
		jRadioButtonOpDate.setContentAreaFilled(false);
		jRadioButtonOpDate.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		        jRadioButton5ActionPerformed(evt);
		    }
		});
//21-11-2014 aumentar el tamaño del número de factura y disminuir el de la serie
		org.jdesktop.layout.GroupLayout jPanel19Layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(jPanel19Layout);
		jPanel19Layout.setHorizontalGroup(
		    jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		    .add(jPanel19Layout.createSequentialGroup()
		        .addContainerGap()
		        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(org.jdesktop.layout.GroupLayout.TRAILING, jCheckBoxIsReceived, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
		            .add(jPanel19Layout.createSequentialGroup()
		                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
		                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelPeriod, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanelPlace, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel19Layout.createSequentialGroup()
		                        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(jPanel19Layout.createSequentialGroup()
		                                .add(jRadioButtonOpDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
		                                .add(jLabelOpDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                            .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
		                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelInvNum, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelExpDate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)))
		                        .add(4, 4, 4)
		                        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel19Layout.createSequentialGroup()
		                                .add(jTextFieldSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                                .add(jTextFieldNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                            .add(jPanel19Layout.createSequentialGroup()
		                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		                                    .add(jCalendarComboBoxExpDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                                    .add(jCalendarComboBoxOpDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))))
		                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
		);
		jPanel19Layout.setVerticalGroup(
		    jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		    .add(jPanel19Layout.createSequentialGroup()
		        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		            .add(jTextFieldNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		            .add(jTextFieldSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		            .add(jLabelInvNum, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		            .add(jCalendarComboBoxExpDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		            .add(jLabelExpDate))
		        .add(14, 14, 14)
		        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		            .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                .add(jRadioButtonOpDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                .add(jLabelOpDate))
		            .add(jCalendarComboBoxOpDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        .add(jPanelPlace, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        .add(jPanelPeriod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
		        .add(jCheckBoxIsReceived, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        .addContainerGap(15, Short.MAX_VALUE))
		);
	
		jCalendarComboBoxOpDate.setVisible(false);
		
		this.setBackground(Constants.BKG_MAIN_COLOR);
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("InvoiceGeneralData"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 
    }
	
    private void jCheckBoxIsReceivedActionPerformed() {
    	((GenerateInvoiceWindow)this.getTopLevelAncestor()).jCheckBoxIsReceivedChangePerformed(jCheckBoxIsReceived.isSelected());
    }
    
    /** If the radio button is selected, the associated CalendarComboBoxes are shown (Period Dates) */
    private void jRadioButtonActionPerformed() {
        if (jRadioButtonPeriod.isSelected()){
            jCalendarComboBoxPeriodTo.setVisible(true);
            jCalendarComboBoxPeriodFrom.setVisible(true);
        }
        else{
            jCalendarComboBoxPeriodTo.setVisible(false);
            jCalendarComboBoxPeriodFrom.setVisible(false);
        }
    }
 
    /** If the radio button is selected, the associated CalendarComboBox is shown (Operation Date) */
    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jRadioButtonOpDate.isSelected())
            jCalendarComboBoxOpDate.setVisible(true);
        else
            jCalendarComboBoxOpDate.setVisible(false);
    }
    
    private class ExpressionValidator extends FocusAdapter {
		Pattern p = null;
		Matcher m = null;
		String error = "";
		public ExpressionValidator(String expression, String error){
			p = Pattern.compile(expression);
			this.error = error;
		}
		public void focusLost(FocusEvent evt){
			String value =((JTextField)evt.getSource()).getText();
			if (value != null){
				m = p.matcher(value);
				if (!m.matches() && !"".equals(value)){
					((JTextField)evt.getSource()).setText("");
					((GenerateInvoiceWindow)((JTextField)evt.getSource()).getTopLevelAncestor()).showMessage(Constants.LANG.getString(error), Constants.ERROR_MSG_COLOR);
				}
			}
		}
	}
    
    
	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxExpDate() {
		return jCalendarComboBoxExpDate;
	}

	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxOpDate() {
		return jCalendarComboBoxOpDate;
	}

	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxPeriodFrom() {
		return jCalendarComboBoxPeriodFrom;
	}

	public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxPeriodTo() {
		return jCalendarComboBoxPeriodTo;
	}

	public javax.swing.JCheckBox getJCheckBoxIsReceived() {
		return jCheckBoxIsReceived;
	}

	public javax.swing.JRadioButton getJRadioButtonOpDate() {
		return jRadioButtonOpDate;
	}

	public javax.swing.JRadioButton getJRadioButtonPeriod() {
		return jRadioButtonPeriod;
	}

	public javax.swing.JTextField getJTextFieldSeries() {
		return jTextFieldSeries;
	}

	public javax.swing.JTextField getJTextFieldNumber() {
		return jTextFieldNumber;
	}

	public javax.swing.JTextField getJTextFieldPlPCode() {
		return jTextFieldPlPCode;
	}

	public javax.swing.JTextField getJTextFieldPlDesc() {
		return jTextFieldPlDesc;
	}
	
	public void setJCheckBoxIsReceivedSelected(boolean b) {
		jCheckBoxIsReceived.setSelected(b);
	}
    
    
	// JPanel
	private javax.swing.JPanel jPanelPlace;
    private javax.swing.JPanel jPanelPeriod;
	// JCalendarComboBox
	private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxExpDate;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxOpDate;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxPeriodFrom;
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxPeriodTo;
    // JCheckBox
    private javax.swing.JCheckBox jCheckBoxIsReceived;
    // JLabel
    private javax.swing.JLabel jLabelInvNum;
    private javax.swing.JLabel jLabelExpDate;
    private javax.swing.JLabel jLabelOpDate;
    private javax.swing.JLabel jLabelPCode;
    private javax.swing.JLabel jLabelPlDesc;
    private javax.swing.JLabel jLabelPeriodFrom;
    private javax.swing.JLabel jLabelPeriodTo;
    // JRadioButtons
    private javax.swing.JRadioButton jRadioButtonOpDate;
    private javax.swing.JRadioButton jRadioButtonPeriod;
    // JTextField
    private javax.swing.JTextField jTextFieldSeries;
    private javax.swing.JTextField jTextFieldNumber;
    private javax.swing.JTextField jTextFieldPlPCode;
    private javax.swing.JTextField jTextFieldPlDesc;  
}