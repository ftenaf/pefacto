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

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.mityc.appfacturae.facturae.AdministrativeCentreType;
import es.mityc.appfacturae.hibernate.EnumUtils;
import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.ComboUtil;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.appfacturae.utils.io.IntegerUtil;

public class InputAdmCentreDialog extends javax.swing.JDialog {
    
    private ArrayList<ComboOption> countryOptions = null;
    private boolean disabled = false;
    private String version = "";
    
    public InputAdmCentreDialog(Frame f, boolean modal, AdministrativeCentreType act, boolean disabled, String version) {
        super (f,modal);
        this.version = version;
        countryOptions = FacturaeUtil.getCountries(version);
        this.disabled = disabled;
        
        initComponents();
        if (disabled)
        	setFieldsDisabled();
        if (act != null)
        	setValues(act);
        this.setSize(400, 540);
        this.setTitle(Constants.LANG.getString("AdministrativeCentre"));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
	private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        
        jButtonOk = new JButton();
        jButtonCancel = new JButton();
        jSeparatorGen = new JSeparator();
        jSeparatorGen2 = new JSeparator();
        jPanelGeneral = new javax.swing.JPanel();
        jLabelCentreCode = new javax.swing.JLabel();
        jTextFieldCentreCode = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabel1Surname = new javax.swing.JLabel();
        jTextField1Surname = new javax.swing.JTextField();
        jTextField2Surname = new javax.swing.JTextField();
        jLabelRole = new javax.swing.JLabel();
        jComboBoxRole = new javax.swing.JComboBox();
        jLabel2Surname = new javax.swing.JLabel();
        jPanelOtherData = new javax.swing.JPanel();
        jTabbedPaneOtherData = new javax.swing.JTabbedPane();
        jPanelAddress = new javax.swing.JPanel();
        jLabelAddress = new javax.swing.JLabel();
        jLabelPostCode = new javax.swing.JLabel();
        jLabelTown = new javax.swing.JLabel();
        jLabelProvince = new javax.swing.JLabel();
        jLabelCountry = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        jTextFieldPostCode = new javax.swing.JTextField();
        jTextFieldTown = new javax.swing.JTextField();
        jTextFieldProvince = new javax.swing.JTextField();
        jComboBoxCountry = new javax.swing.JComboBox();
        jPanelContact = new javax.swing.JPanel();
        jLabelTelephone = new javax.swing.JLabel();
        jLabelWeb = new javax.swing.JLabel();
        jLabelContacts = new javax.swing.JLabel();
        jTextFieldTelephone = new javax.swing.JTextField();
        jTextFieldWeb = new javax.swing.JTextField();
        jTextFieldContacts = new javax.swing.JTextField();
        jLabelFax = new javax.swing.JLabel();
        jLabelMail = new javax.swing.JLabel();
        jLabelTownCode = new javax.swing.JLabel();
        jTextFieldFax = new javax.swing.JTextField();
        jTextFieldMail = new javax.swing.JTextField();
        jTextFieldTownCode = new javax.swing.JTextField();
        jLabelCNOCNAE = new javax.swing.JLabel();
        jTextFieldCNOCNAE = new javax.swing.JTextField();
        jLabelOtherData = new javax.swing.JLabel();
        jLabelPhysicalGLN = new javax.swing.JLabel();
        jLabelLogicalOperationalPoint = new javax.swing.JLabel();
        jTextFieldOtherData = new javax.swing.JTextField();
        jTextFieldPhysicalGLN = new javax.swing.JTextField();
        jTextFieldLogicalOperationalPoint = new javax.swing.JTextField();
        
        jLabelCentreDescription = new javax.swing.JLabel();
        jTextFieldCentreDescription = new javax.swing.JTextField();
        
        fd = new FadingCanvas();
        fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
        
        jSeparatorGen.setForeground(Constants.SELECTION_COLOR);
        jSeparatorGen.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        
        jSeparatorGen2.setForeground(Constants.SELECTION_COLOR);
        jSeparatorGen2.setOrientation(javax.swing.SwingConstants.HORIZONTAL);
        
        ExpressionValidator evPCode = new ExpressionValidator("[0-9]{5}","NOOKParam5Digit");
        jTextFieldPostCode.addFocusListener(evPCode);
        jTextFieldCNOCNAE.addFocusListener(evPCode);
        
        ExpressionValidator evCentreCode = new ExpressionValidator("[a-zA-Z0-9]{0,10}","NOOKParamTooHigh");
        jTextFieldCentreCode.addFocusListener(evCentreCode);
        
        ExpressionValidator evName = new ExpressionValidator(".{0,40}","NOOKParamTooHigh");
        jTextFieldName.addFocusListener(evName);
        jTextField1Surname.addFocusListener(evName);
        jTextField2Surname.addFocusListener(evName);
        jTextFieldContacts.addFocusListener(evName);
        
        ExpressionValidator evAddress = new ExpressionValidator(".{0,80}","NOOKParamTooHigh");
        jTextFieldAddress.addFocusListener(evAddress);
        
        ExpressionValidator evTown = new ExpressionValidator(".{0,50}","NOOKParamTooHigh");
        jTextFieldTown.addFocusListener(evTown);
        
        ExpressionValidator evProvince = new ExpressionValidator(".{0,20}","NOOKParamTooHigh");
        jTextFieldProvince.addFocusListener(evProvince);
        
        ExpressionValidator evTelephone = new ExpressionValidator(".{0,15}","NOOKParamTooHigh");
        jTextFieldTelephone.addFocusListener(evTelephone);
        jTextFieldFax.addFocusListener(evTelephone);
        
        ExpressionValidator evWeb = new ExpressionValidator(".{0,60}","NOOKParamTooHigh");
        jTextFieldWeb.addFocusListener(evWeb);
        jTextFieldMail.addFocusListener(evWeb);
        jTextFieldOtherData.addFocusListener(evWeb);
        
        ExpressionValidator evINE = new ExpressionValidator(".{0,9}","NOOKParamTooHigh");
        jTextFieldTownCode.addFocusListener(evINE);
        
        ExpressionValidator evGLN = new ExpressionValidator(".{0,14}","NOOKParamTooHigh");
        jTextFieldPhysicalGLN.addFocusListener(evGLN);
        jTextFieldLogicalOperationalPoint.addFocusListener(evGLN);
        
        ExpressionValidator evDESC = new ExpressionValidator(".{0,2500}","NOOKParamTooHigh");
        jTextFieldCentreDescription.addFocusListener(evDESC);
        
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
        		if (!disabled){
	        		if (jTextFieldAddress == null || "".equals(jTextFieldAddress.getText().trim())) {
	        			jTextFieldAddress.setBackground(Constants.BKG_ERROR_COLOR);
	        			jTextFieldAddress.requestFocus();
	        			flag = true;
	        		}
	        		if (jTextFieldPostCode == null || "".equals(jTextFieldPostCode.getText().trim())) {
	        			jTextFieldPostCode.setBackground(Constants.BKG_ERROR_COLOR);
	        			jTextFieldPostCode.requestFocus();
	        			flag = true;
	        		}
	        		if (jTextFieldTown == null || "".equals(jTextFieldTown.getText().trim())) {
	        			jTextFieldTown.setBackground(Constants.BKG_ERROR_COLOR);
	        			jTextFieldTown.requestFocus();
	        			flag = true;
	        		}
	        		if (jTextFieldProvince == null || "".equals(jTextFieldProvince.getText().trim())) {
	        			jTextFieldProvince.setBackground(Constants.BKG_ERROR_COLOR);
	        			jTextFieldProvince.requestFocus();
	        			flag = true;
	        		}
	        		if (jComboBoxCountry == null || jComboBoxCountry.getSelectedItem() == null || "".equals(jComboBoxCountry.getSelectedItem().toString().trim())) {
	        			jComboBoxCountry.setBackground(Constants.BKG_ERROR_COLOR);
	        			jComboBoxCountry.requestFocus();
	        			flag = true;
	        		}
	        		if (!flag){
	        			setVisible(false);
	              		dispose();
	        		}
	        		else{
	        			fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
	        		}
        		}
        		else{
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
        
        jTextFieldAddress.addKeyListener(new keyListenerAdmCentre());
        jTextFieldPostCode.addKeyListener(new keyListenerAdmCentre());
        jTextFieldTown.addKeyListener(new keyListenerAdmCentre());
        jTextFieldProvince.addKeyListener(new keyListenerAdmCentre());
        jComboBoxCountry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((JComboBox)e.getSource()).setBackground(java.awt.Color.white);
			}
        });
        
        jTextField1Surname.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextField2Surname.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldAddress.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldCentreCode.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldCNOCNAE.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldContacts.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldFax.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldLogicalOperationalPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldMail.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldName.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldOtherData.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldPhysicalGLN.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldPostCode.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldProvince.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTelephone.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTown.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldTownCode.setHorizontalAlignment(SwingConstants.RIGHT);
        jTextFieldWeb.setHorizontalAlignment(SwingConstants.RIGHT);
        
        mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
       
        jPanelGeneral.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("General"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR));

        jLabelCentreCode.setText(Constants.LANG.getString("CentreCode"));
        jLabelCentreCode.setForeground(Constants.FONT_COLOR);
        jLabelCentreCode.setFont(Constants.FONT_PLAIN);
		
        jLabelName.setText(Constants.LANG.getString("Name"));
        jLabelName.setForeground(Constants.FONT_COLOR);
        jLabelName.setFont(Constants.FONT_PLAIN);
        
        jLabel1Surname.setText(Constants.LANG.getString("FirstSurname"));
        jLabel1Surname.setForeground(Constants.FONT_COLOR);
        jLabel1Surname.setFont(Constants.FONT_PLAIN);

        jLabelRole.setText(Constants.LANG.getString("Role"));
        jLabelRole.setForeground(Constants.FONT_COLOR);
        jLabelRole.setFont(Constants.FONT_PLAIN);
        
        String[] roles = Constants.APP_PROP.getProperty("roles32").split(";");
        String[] rolesNames = new String[roles.length];
        rolesNames[0] = roles[0];
        for (int i = 1 ; i < roles.length ; i++)
        	rolesNames[i] = Constants.LANG.getString(roles[i]);
        	
        jComboBoxRole.setModel(new javax.swing.DefaultComboBoxModel(rolesNames));
        jComboBoxRole.setRenderer(new ComboBoxRenderer(true));
        
        jLabel2Surname.setText(Constants.LANG.getString("SecondSurname"));
        jLabel2Surname.setForeground(Constants.FONT_COLOR);
        jLabel2Surname.setFont(Constants.FONT_PLAIN);
        
        jLabelPhysicalGLN.setText(Constants.LANG.getString("PhysicalGLN"));
        jLabelPhysicalGLN.setForeground(Constants.FONT_COLOR);
        jLabelPhysicalGLN.setFont(Constants.FONT_PLAIN);
        
        jLabelLogicalOperationalPoint.setText(Constants.LANG.getString("LogicalOperationalPoint"));
        jLabelLogicalOperationalPoint.setForeground(Constants.FONT_COLOR);
        jLabelLogicalOperationalPoint.setFont(Constants.FONT_PLAIN);
        
        jLabelCentreDescription.setText(Constants.LANG.getString("Description"));
        jLabelCentreDescription.setForeground(Constants.FONT_COLOR);
        jLabelCentreDescription.setFont(Constants.FONT_PLAIN);
        
        org.jdesktop.layout.GroupLayout jPanelGeneralLayout = new org.jdesktop.layout.GroupLayout(jPanelGeneral);
        jPanelGeneral.setLayout(jPanelGeneralLayout);
        jPanelGeneralLayout.setHorizontalGroup(
        		jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        			.add(jPanelGeneralLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
       						.add(jPanelGeneralLayout.createSequentialGroup()
		        				.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		        						.add(jLabelCentreCode)
		        						.add(jLabelName)
		        						.add(jLabelPhysicalGLN)
		        						.add(jLabelCentreDescription))
		        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        				.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		        					.add(jTextFieldCentreDescription,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 277, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        					.add(jPanelGeneralLayout.createSequentialGroup()
		        						.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		        							.add(jTextFieldCentreCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        							.add(jTextFieldName,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,80,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        							.add(jTextFieldPhysicalGLN,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,80,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		        							.add(20,20,20)
		        							.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		        								.add(jPanelGeneralLayout.createSequentialGroup()
		        									.add(jLabelRole)
		        									.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        									.add(jComboBoxRole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		        								.add(jPanelGeneralLayout.createSequentialGroup()
		        									.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		        										.add(jLabel1Surname)
		        										.add(jLabel2Surname)
		        										.add(jLabelLogicalOperationalPoint))
		        									.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		        									.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		        										.add(jTextField1Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        										.add(jTextField2Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        										.add(jTextFieldLogicalOperationalPoint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))))
		        			.add(jSeparatorGen,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,335,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		        			.add(jSeparatorGen2,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,335,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        				.addContainerGap())
				);
        jPanelGeneralLayout.setVerticalGroup(
        		jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        		.add(jPanelGeneralLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
        						.add(jLabelCentreCode)
        						.add(jTextFieldCentreCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jLabelRole)
        						.add(jComboBoxRole, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        						.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
        							.add(jLabelCentreDescription)
        							.add(jTextFieldCentreDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        						.add(jSeparatorGen)
        						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        						.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
        								.add(jLabelName)
        								.add(jTextFieldName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        								.add(jLabel1Surname)
        								.add(jTextField1Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        						.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
        								.add(jTextField2Surname, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        								.add(jLabel2Surname))
        						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        						.add(jSeparatorGen2)
        						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        						.add(jPanelGeneralLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
        								.add(jTextFieldPhysicalGLN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        								.add(jLabelPhysicalGLN)
        								.add(jTextFieldLogicalOperationalPoint, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        								.add(jLabelLogicalOperationalPoint)
        						)
        						.addContainerGap())
        );

        jPanelOtherData.setBackground(Constants.BKG_MAIN_COLOR);
        jPanelOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("OtherData"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR));

        jLabelAddress.setText(Constants.LANG.getString("Address"));
        jLabelAddress.setForeground(Constants.FONT_COLOR);
        jLabelAddress.setFont(Constants.FONT_PLAIN);
        jLabelAddress.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        
        jLabelPostCode.setText(Constants.LANG.getString("PostCode"));
        jLabelPostCode.setForeground(Constants.FONT_COLOR);
        jLabelPostCode.setFont(Constants.FONT_PLAIN);
        jLabelPostCode.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        
        jLabelTown.setText(Constants.LANG.getString("Town"));
        jLabelTown.setForeground(Constants.FONT_COLOR);
        jLabelTown.setFont(Constants.FONT_PLAIN);
        jLabelTown.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        
        jLabelProvince.setText(Constants.LANG.getString("Province"));
        jLabelProvince.setForeground(Constants.FONT_COLOR);
        jLabelProvince.setFont(Constants.FONT_PLAIN);
        jLabelProvince.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        
        jLabelCountry.setText(Constants.LANG.getString("Country"));
        jLabelCountry.setForeground(Constants.FONT_COLOR);
        jLabelCountry.setFont(Constants.FONT_PLAIN);
        jLabelCountry.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
        
        jComboBoxCountry.setModel(new javax.swing.DefaultComboBoxModel(countryOptions.toArray()));
        jComboBoxCountry.setRenderer(new ComboBoxRenderer(true));
        jComboBoxCountry.setSelectedItem(null);
        
        org.jdesktop.layout.GroupLayout jPanelAddressLayout = new org.jdesktop.layout.GroupLayout(jPanelAddress);
        jPanelAddress.setLayout(jPanelAddressLayout);
        jPanelAddressLayout.setHorizontalGroup(
        		jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        			.add(jPanelAddressLayout.createSequentialGroup()
        				.addContainerGap()
        				.add(jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        						.add(jLabelAddress)
        						.add(jLabelPostCode)
        						.add(jLabelTown)
        						.add(jLabelProvince)
        						.add(jLabelCountry))
        				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)	
        				.add(jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)	
        						.add(jTextFieldAddress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jTextFieldPostCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jTextFieldTown,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jComboBoxCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        						.add(jTextFieldProvince, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        				.addContainerGap())
        );
        jPanelAddressLayout.setVerticalGroup(
        		jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelAddressLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelAddress)
                        .add(jTextFieldAddress))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelPostCode)
                        .add(jTextFieldPostCode))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelTown)
                        .add(jTextFieldTown))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelProvince)
                        .add(jTextFieldProvince))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelAddressLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelCountry)
                        .add(jComboBoxCountry))
                    .addContainerGap())
        );

        jTabbedPaneOtherData.addTab(Constants.LANG.getString("Address"), jPanelAddress);

        jLabelTelephone.setText(Constants.LANG.getString("Telephone"));
        jLabelTelephone.setForeground(Constants.FONT_COLOR);
        jLabelTelephone.setFont(Constants.FONT_PLAIN);
        
        jLabelWeb.setText(Constants.LANG.getString("Web"));
        jLabelWeb.setForeground(Constants.FONT_COLOR);
        jLabelWeb.setFont(Constants.FONT_PLAIN);
        
        jLabelContacts.setText(Constants.LANG.getString("Contacts"));
        jLabelContacts.setForeground(Constants.FONT_COLOR);
        jLabelContacts.setFont(Constants.FONT_PLAIN);
        
        jLabelFax.setText(Constants.LANG.getString("Fax"));
        jLabelFax.setForeground(Constants.FONT_COLOR);
        jLabelFax.setFont(Constants.FONT_PLAIN);
        
        jLabelMail.setText(Constants.LANG.getString("EMail"));
        jLabelMail.setForeground(Constants.FONT_COLOR);
        jLabelMail.setFont(Constants.FONT_PLAIN);
        
        jLabelTownCode.setText(Constants.LANG.getString("INETownCode"));
        jLabelTownCode.setForeground(Constants.FONT_COLOR);
        jLabelTownCode.setFont(Constants.FONT_PLAIN);
        
        jLabelCNOCNAE.setText(Constants.LANG.getString("CNOCNAE"));
        jLabelCNOCNAE.setForeground(Constants.FONT_COLOR);
        jLabelCNOCNAE.setFont(Constants.FONT_PLAIN);
        
        jLabelOtherData.setText(Constants.LANG.getString("OtherContactData"));
        jLabelOtherData.setForeground(Constants.FONT_COLOR);
        jLabelOtherData.setFont(Constants.FONT_PLAIN);
        
        org.jdesktop.layout.GroupLayout jPanelContactLayout = new org.jdesktop.layout.GroupLayout(jPanelContact);
            jPanelContact.setLayout(jPanelContactLayout);
            jPanelContactLayout.setHorizontalGroup(
                jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                .add(jPanelContactLayout.createSequentialGroup()
	                    .addContainerGap()
	                    .add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
	                    	.add(jPanelContactLayout.createSequentialGroup()
	                    		.add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                    			.add(jLabelTelephone)
		                            .add(jLabelWeb)
		                            .add(jLabelContacts)
		                            .add(jLabelCNOCNAE)
	                    		)
	                    		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    		.add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                    			.add(jTextFieldTelephone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    			.add(jTextFieldWeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    			.add(jTextFieldContacts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                                .add(jTextFieldCNOCNAE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    		)
	                    		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    		.add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    				.add(jLabelFax)
                    				.add(jLabelMail)
	                    			.add(jLabelTownCode)
	                    		)
	                    		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    		.add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                    				.add(jTextFieldFax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    				.add(jTextFieldMail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    				.add(jTextFieldTownCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	                    		)
	                    	)
	                    	.add(jPanelContactLayout.createSequentialGroup()
	                    		.add(jLabelOtherData)
	                    		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
	                    	.add(jTextFieldOtherData,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,170,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                   		.addContainerGap())
        );
	        
        jPanelContactLayout.setVerticalGroup(
                jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelContactLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelTelephone)
                        .add(jTextFieldTelephone)
                        .add(jLabelFax)
                        .add(jTextFieldFax))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelWeb)
                        .add(jTextFieldWeb)
                        .add(jLabelMail)
                        .add(jTextFieldMail))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelContacts)
                        .add(jLabelTownCode)
                        .add(jTextFieldTownCode)
                        .add(jTextFieldContacts))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelCNOCNAE)
                        .add(jTextFieldCNOCNAE))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelContactLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jTextFieldOtherData)
                        .add(jLabelOtherData))
                    .addContainerGap())
        );

        jTabbedPaneOtherData.addTab(Constants.LANG.getString("ContactData"), jPanelContact);

        org.jdesktop.layout.GroupLayout jPanelOtherDataLayout = new org.jdesktop.layout.GroupLayout(jPanelOtherData);
            jPanelOtherData.setLayout(jPanelOtherDataLayout);
            jPanelOtherDataLayout.setHorizontalGroup(
                jPanelOtherDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                .add(jPanelOtherDataLayout.createSequentialGroup()
                	.addContainerGap()
                    .add(jTabbedPaneOtherData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        jPanelOtherDataLayout.setVerticalGroup(
                jPanelOtherDataLayout.createSequentialGroup()
                	.addContainerGap()
                	.add(jTabbedPaneOtherData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
            mainPanel.setLayout(mainPanelLayout);
            mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    	.add(fd,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,375,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    	.add(jPanelOtherData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 375, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanelGeneral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 375, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(mainPanelLayout.createSequentialGroup()
                            .add(jButtonOk)
                            .add(25,25,25)
                            .add(jButtonCancel)))
                    .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(mainPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(fd,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,30,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelGeneral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jPanelOtherData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jButtonOk)
                        .add(jButtonCancel))
                    .addContainerGap())
        );

	    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	            .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
	    );
	    
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
	    pack();
    }                    
    
    
    private void jButtonCancelActionPerformed() {
		jTextFieldAddress.setText("");
		setVisible(false);
		dispose();
	}
    
    private class keyListenerAdmCentre implements KeyListener {

    	public void keyPressed(KeyEvent arg0) {
			return;
		}

		public void keyReleased(KeyEvent arg0) {
			return;
		}

		public void keyTyped(KeyEvent arg0) {
			((JTextField)arg0.getSource()).setBackground(java.awt.Color.white);
		}
		
	}	
    
    public String[] getValues() {

		String[] result = new String[21];
		result[0] = jTextFieldCentreCode.getText().trim();
		if (jComboBoxRole.getSelectedIndex() > 0)
			result[1] = IntegerUtil.to2String(jComboBoxRole.getSelectedIndex());
		else
			result[1] = "";
		result[2] = jTextFieldName.getText().trim();
		result[3] = jTextField1Surname.getText().trim();
		result[4] = jTextField2Surname.getText().trim();
		result[5] = jTextFieldPhysicalGLN.getText().trim();
		result[6] = jTextFieldLogicalOperationalPoint.getText().trim();
		result[7] = jTextFieldAddress.getText().trim();
		result[8] = jTextFieldPostCode.getText().trim();
		result[9] = jTextFieldTown.getText().trim();
		result[10] = jTextFieldProvince.getText().trim();
		if (jComboBoxCountry.getSelectedIndex() >= 0)
			result[11] = ((ComboOption)jComboBoxCountry.getSelectedItem()).getValue();
		else
			result[11] = "";
		result[12] = jTextFieldTelephone.getText().trim(); 
		result[13] = jTextFieldWeb.getText().trim();
		result[14] = jTextFieldContacts.getText().trim();
		result[15] = jTextFieldCNOCNAE.getText().trim();
		result[16] = jTextFieldFax.getText().trim();
		result[17] = jTextFieldMail.getText().trim();
		result[18] = jTextFieldTownCode.getText().trim();
		result[19] = jTextFieldOtherData.getText().trim();
		
		result[20] = jTextFieldCentreDescription.getText().trim();

		return result;
	}
    
    private void setValues(AdministrativeCentreType act) {
    	if (act.getFirstSurname() != null)
    		jTextField1Surname.setText(act.getFirstSurname());
    	if (act.getSecondSurname() != null)
    		jTextField2Surname.setText(act.getSecondSurname());
    	int countryValueSelected = -1;
        if ("Spain".equals(act.getAddressType()) && act.getAddressInSpain() != null){
    		if (act.getAddressInSpain().getAddress() != null)
    			jTextFieldAddress.setText(act.getAddressInSpain().getAddress());
    		if (act.getAddressInSpain().getPostCode() != null)
    			jTextFieldPostCode.setText(act.getAddressInSpain().getPostCode());
    		if (act.getAddressInSpain().getTown() != null)
    			jTextFieldTown.setText(act.getAddressInSpain().getTown());
    		if (act.getAddressInSpain().getProvince() != null)
    			jTextFieldProvince.setText(act.getAddressInSpain().getProvince());
			countryValueSelected = EnumUtils.calculateEnumCode(
				((es.mityc.appfacturae.facturae32.AddressType) act.getAddressInSpain()).getCountryCode().toString(),
				 es.mityc.appfacturae.facturae.CountryType.values());
    	}
    	else{
    		if ("Overseas".equals(act.getAddressType()) && act.getOverseasAddress() != null){
    			jTextFieldPostCode.setText("");
    			if (act.getOverseasAddress().getAddress() != null)
        			jTextFieldAddress.setText(act.getOverseasAddress().getAddress());
        		if (act.getOverseasAddress().getPostCodeAndTown() != null)
        			jTextFieldTown.setText(act.getOverseasAddress().getPostCodeAndTown());
        		if (act.getOverseasAddress().getProvince() != null)
        			jTextFieldProvince.setText(act.getOverseasAddress().getProvince());
        		countryValueSelected = EnumUtils.calculateEnumCode(
    				((es.mityc.appfacturae.facturae32.OverseasAddressType)act.getOverseasAddress()).getCountryCode().toString(), 
    				 es.mityc.appfacturae.facturae.CountryType.values());         		
        	}
    	}
    	jComboBoxCountry.setSelectedIndex(ComboUtil.calculateComboIndex(countryOptions, String.valueOf(countryValueSelected)));
        if (act.getCentreCode() != null)
    		jTextFieldCentreCode.setText(act.getCentreCode());
    	if (act.getContactDetails() != null){
    		if (act.getContactDetails().getCnoCnae() != null)
    			jTextFieldCNOCNAE.setText(act.getContactDetails().getCnoCnae());
    		if (act.getContactDetails().getContactPersons() != null)
    			jTextFieldContacts.setText(act.getContactDetails().getContactPersons());
    		if (act.getContactDetails().getTeleFax() != null)
    			jTextFieldFax.setText(act.getContactDetails().getTeleFax());
    		if (act.getContactDetails().getElectronicMail() != null)
    			jTextFieldMail.setText(act.getContactDetails().getElectronicMail());
    		if (act.getContactDetails().getAdditionalContactDetails() != null)
    			jTextFieldOtherData.setText(act.getContactDetails().getAdditionalContactDetails());
    		if (act.getContactDetails().getTelephone() != null)
    			jTextFieldTelephone.setText(act.getContactDetails().getTelephone());
    		if (act.getContactDetails().getWebAddress() != null)
    			jTextFieldWeb.setText(act.getContactDetails().getWebAddress());
    		if (act.getContactDetails().getIneTownCode() != null)
    			jTextFieldTownCode.setText(act.getContactDetails().getIneTownCode());
    	}
    	if (act.getLogicalOperationalPoint() != null)
    		jTextFieldLogicalOperationalPoint.setText(act.getLogicalOperationalPoint());
    	if (act.getName() != null)
    		jTextFieldName.setText(act.getName());
    	if (act.getPhysicalGLN() != null)
    		jTextFieldPhysicalGLN.setText(act.getPhysicalGLN());
    	if (act.getRoleTypeCode() != null && !"".equals(act.getRoleTypeCode().trim()))
    		jComboBoxRole.setSelectedIndex(Integer.parseInt(act.getRoleTypeCode()));

		if (version.equals(Constants.VERSION321)
			&& ((es.mityc.appfacturae.facturae321.AdministrativeCentreType) act).getCentreDescription() != null
			&& !"".equals(((es.mityc.appfacturae.facturae321.AdministrativeCentreType) act).getCentreDescription()
				.trim())){
			jTextFieldCentreDescription.setText(((es.mityc.appfacturae.facturae321.AdministrativeCentreType) act)
				.getCentreDescription().trim());
		}
		if (version.equals(Constants.VERSION32)
			&& ((es.mityc.appfacturae.facturae32.AdministrativeCentreType) act).getCentreDescription() != null
			&& !"".equals(((es.mityc.appfacturae.facturae32.AdministrativeCentreType) act).getCentreDescription()
				.trim())){
			jTextFieldCentreDescription.setText(((es.mityc.appfacturae.facturae32.AdministrativeCentreType) act)
				.getCentreDescription().trim());
		}
	}
    
    public void setFieldsDisabled() {
		jTextField1Surname.setEditable(false);
		jTextField2Surname.setEditable(false);
		jTextFieldAddress.setEditable(false);
		jTextFieldCentreCode.setEditable(false);
		jTextFieldCNOCNAE.setEditable(false);
		jTextFieldContacts.setEditable(false);
		jTextFieldFax.setEditable(false);
		jTextFieldLogicalOperationalPoint.setEditable(false);
		jTextFieldMail.setEditable(false);
		jTextFieldName.setEditable(false);
		jTextFieldOtherData.setEditable(false);
		jTextFieldPhysicalGLN.setEditable(false);
		jTextFieldPostCode.setEditable(false);
		jTextFieldProvince.setEditable(false);
		jTextFieldTelephone.setEditable(false);
		jTextFieldTown.setEditable(false);
		jTextFieldTownCode.setEditable(false);
		jTextFieldWeb.setEditable(false);
		jTextFieldCentreDescription.setEditable(false);
		jComboBoxCountry.setEnabled(false);
		jComboBoxRole.setEnabled(false);
		jTextField1Surname.setBackground(Constants.BKG_MAIN_COLOR);
		jTextField2Surname.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldAddress.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldCentreCode.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldCNOCNAE.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldContacts.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldFax.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldLogicalOperationalPoint.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldCentreDescription.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldMail.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldName.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldOtherData.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldPhysicalGLN.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldPostCode.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldProvince.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldTelephone.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldTown.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldTownCode.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldWeb.setBackground(Constants.BKG_MAIN_COLOR);
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
					fd.showMessage(Constants.LANG.getString(error), Constants.ERROR_MSG_COLOR);
				}
			}
		}
	}
    
    // Variables declaration      
    private FadingCanvas fd;
    
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel jPanelGeneral;
    private javax.swing.JPanel jPanelOtherData;
    private javax.swing.JTabbedPane jTabbedPaneOtherData;
    private javax.swing.JPanel jPanelAddress;
    private javax.swing.JPanel jPanelContact;
    
    private javax.swing.JLabel jLabelCentreCode;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelRole;
    private javax.swing.JLabel jLabel1Surname;
    private javax.swing.JLabel jLabel2Surname;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelPostCode;
    private javax.swing.JLabel jLabelTown;
    private javax.swing.JLabel jLabelProvince;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelTelephone;
    private javax.swing.JLabel jLabelWeb;
    private javax.swing.JLabel jLabelContacts;
    private javax.swing.JLabel jLabelCNOCNAE;
    private javax.swing.JLabel jLabelFax;
    private javax.swing.JLabel jLabelMail;
    private javax.swing.JLabel jLabelTownCode;
    private javax.swing.JLabel jLabelOtherData;
    private javax.swing.JLabel jLabelPhysicalGLN;
    private javax.swing.JLabel jLabelLogicalOperationalPoint;
        
    private javax.swing.JTextField jTextFieldCentreCode;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextField1Surname;
    private javax.swing.JTextField jTextField2Surname;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldPostCode;
    private javax.swing.JTextField jTextFieldTown;
    private javax.swing.JTextField jTextFieldProvince;
    private javax.swing.JTextField jTextFieldTelephone;
    private javax.swing.JTextField jTextFieldWeb;
    private javax.swing.JTextField jTextFieldContacts;
    private javax.swing.JTextField jTextFieldCNOCNAE;
    private javax.swing.JTextField jTextFieldFax;
    private javax.swing.JTextField jTextFieldMail;
    private javax.swing.JTextField jTextFieldTownCode;
    private javax.swing.JTextField jTextFieldOtherData;
    private javax.swing.JTextField jTextFieldPhysicalGLN;
    private javax.swing.JTextField jTextFieldLogicalOperationalPoint;
    
    /* Only 3.2 */
    private javax.swing.JLabel jLabelCentreDescription;
    private javax.swing.JTextField jTextFieldCentreDescription;
    
    private javax.swing.JComboBox jComboBoxRole;
    private javax.swing.JComboBox jComboBoxCountry;
    
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
    
    private JSeparator jSeparatorGen = null;
    private JSeparator jSeparatorGen2 = null;
}
