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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.facturae.AdministrativeCentreType;
import es.mityc.appfacturae.facturae.AdministrativeCentresType;
import es.mityc.appfacturae.facturae.ResidenceTypeCodeType;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.EnumOperationType;
import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.ui.components.CoupledComboModel;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.IPartyPanelListener;
import es.mityc.appfacturae.ui.components.IndividualRow;
import es.mityc.appfacturae.ui.components.LegalEntityRow;
import es.mityc.appfacturae.ui.dialogs.AdmCentresDialog;
import es.mityc.appfacturae.ui.dialogs.InputFACeAdmCentreDialog;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.ui.transitions.Transition;
import es.mityc.appfacturae.ui.windows.GenerateInvoiceWindow;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.AdmCentreUtil;
import es.mityc.appfacturae.utils.PeFACUtils;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.ComboUtil;
import es.mityc.appfacturae.utils.io.FacturaeUtil;

public class PartyPanel extends JPanel implements IPartyPanelListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2989072157990227620L;

	private static Log logger = LogFactory.getLog(PartyPanel.class);

	private static final int FACE_TAB = 4;

	private String version = null;
	private String role = null;
	private int spainCode = 0;
	private ArrayList<ComboOption> countryOptions = null;
	private boolean disabled = false;
	private Transition t = null;
	//True when user clicks on loup
	//False when user clicks on pen or closes window (also to avoid multiple request of pass)
	private boolean isSearch = false;

	private boolean partyModified = false;
		
	public PartyPanel(Locale l,ComboBoxModel cbmInd, ComboBoxModel cbmLEn, String version, String role) {
		super();
		spainCode = FacturaeUtil.getSpainCode();
		countryOptions = FacturaeUtil.getCountries(version);
		this.cbmInd = cbmInd;
		this.cbmLEn = cbmLEn;
		this.version = version;
		this.role = role;
		initComponents();
	}
	
	private void initComponents() {
	    
		jLabelMain = new JLabel();
	    jComboBoxResidenceType = new JComboBox();
	    jButtonNewUser = new JButton();
	    jButtonDeleteUser = new JButton();
	    jButtonEditUser = new JButton();
	    buttonGroup = new ButtonGroup();
	    jRadioButtonLEn = new JRadioButton();
	    jRadioButtonInd = new JRadioButton();
	    jComboBoxItems = new JComboBox();
	    jLabelResidenceType = new JLabel();
	    jTabbedPaneLEn = new JTabbedPane();
	        jPanelLEnGeneral = new JPanel();
		        jLabelLEnId = new JLabel();
		        jLabelLEnCIF = new JLabel();
		        jLabelLEnCorpName = new JLabel();
		        jLabelLEnTradeName = new JLabel();
	        jPanelLEnAddress = new JPanel();
		        jLabelLEnAddress = new JLabel();
		        jLabelLEnPostCode = new JLabel();
		        jLabelLEnTown = new JLabel();
		        jLabelLEnProvince = new JLabel();
		        jLabelLEnCountry = new JLabel();
	        jPanelLEnRegistration = new JPanel();
		        jLabelLEnBook = new JLabel();
		        jLabelLEnLocation = new JLabel();
		        jLabelLEnSheet = new JLabel();
		        jLabelLEnFolio = new JLabel();
		        jLabelLEnSection = new JLabel();
		        jLabelLEnVolume = new JLabel();
		        jLabelLEnRegAddData = new JLabel();
	        jPanelLEnContact = new JPanel();
		        jLabelLEnTelephone = new JLabel();
		        jLabelLEnFax = new JLabel();
		        jLabelLEnWeb = new JLabel();
		        jLabelLEnMail = new JLabel();
		        jLabelLEnContacts = new JLabel();
		        jLabelLEnConAddData = new JLabel();
		        jLabelLEnINETownCode = new JLabel();
		        jLabelLEnCNOCNAE = new JLabel();
		    jPanelLEnFACe = new JPanel();
		    	jLabelLEnManagementAgency = new JLabel();
		    	jLabelLEnProcessingAuthorityUnit = new JLabel();
		    	jLabelLEnAccountingOffice = new JLabel();
		    	jLabelLEnProposingAgency = new JLabel();
			    jLabelLEnFACeLink = new JLabel();
			    jButtonLEnFACeManagementAgency = new JButton();
			    jButtonLEnFACeProcessingAuthorityUnit = new JButton();
			    jButtonLEnFACeAccountingOffice = new JButton();
			    jButtonLEnFACeProposingAgency = new JButton();
		jTabbedPaneInd = new JTabbedPane();
			jPanelIndGeneral = new JPanel();
		        jLabelIndId = new JLabel();
		        jLabelIndNIF = new JLabel();
		        jLabelIndName = new JLabel();
		        jLabelIndFirstSurname = new JLabel();
		        jLabelIndSecondSurname = new JLabel();
		    jPanelIndAddress = new JPanel();
		        jLabelIndAddress = new JLabel();
		        jLabelIndPostCode = new JLabel();
		        jLabelIndProvince = new JLabel();
		        jLabelIndCountry = new JLabel();
		        jLabelIndTown = new JLabel();
		    jPanelIndContact = new JPanel();
		        jLabelIndTelephone = new JLabel();
		        jLabelIndWeb = new JLabel();
		        jLabelIndContacts = new JLabel();
		        jLabelIndCNOCNAE = new JLabel();
		        jLabelIndConAddData = new JLabel();
		        jLabelIndFax = new JLabel();
		        jLabelIndMail = new JLabel();
		        jLabelIndINETownCode = new JLabel();
		        jButtonSave = new JButton();
		        jButtonAdmCentre = new JButton();
		        canvas1 = new FadingCanvas();
		        canvasPanel = new JPanel();
		        
		        centres = new AdministrativeCentresType();
		        
		        jTextField = new JTextField[46];
		        for (int i = 0 ; i < jTextField.length ; i++){
		        	jTextField[i] = new JTextField();
		        	jTextField[i].addKeyListener(new java.awt.event.KeyAdapter() {
			            public void keyTyped(java.awt.event.KeyEvent evt) {
			            	PartyPanel.this.keyTypedErrorTextField(evt);
			            }
			        });
		        	jTextField[i].setEditable(false);
		        	jTextField[i].setBackground(Constants.BKG_MAIN_COLOR);
		        }
		        disabled = true;
		        
		        // Country combo boxes
		        jComboBoxLEnCountry = new JComboBox();
		        jComboBoxIndCountry = new JComboBox();
		              
		        jComboBoxLEnCountry.setModel(new DefaultComboBoxModel(countryOptions.toArray()));
		        jComboBoxIndCountry.setModel(new DefaultComboBoxModel(countryOptions.toArray()));
		        jComboBoxLEnCountry.setSelectedIndex(-1);
		        jComboBoxIndCountry.setSelectedIndex(-1);
		        jComboBoxLEnCountry.setBackground(Constants.BKG_MAIN_COLOR);
		        jComboBoxIndCountry.setBackground(Constants.BKG_MAIN_COLOR);
		        		        
		        jComboBoxLEnCountry.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	PartyPanel.this.actionPerformedErrorComboBox(evt);
		            }
		        });
		        jComboBoxLEnCountry.addPopupMenuListener(new PopupMenuListener() {
		            public void popupMenuCanceled(PopupMenuEvent evt) {
		            	canvas1.setVisible(true);
		            }
		            public void popupMenuWillBecomeInvisible(PopupMenuEvent evt) {
		            	canvas1.setVisible(true);
		            }
		            public void popupMenuWillBecomeVisible(PopupMenuEvent evt) {
		            	canvas1.setVisible(false);
		            }
		        });
		        jComboBoxIndCountry.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	PartyPanel.this.actionPerformedErrorComboBox(evt);
		            }
		        });
		        jComboBoxIndCountry.addPopupMenuListener(new PopupMenuListener() {
		            public void popupMenuCanceled(PopupMenuEvent evt) {
		            	canvas1.setVisible(true);
		            }
		            public void popupMenuWillBecomeInvisible(PopupMenuEvent evt) {
		            	canvas1.setVisible(true);
		            }
		            public void popupMenuWillBecomeVisible(PopupMenuEvent evt) {
		            	canvas1.setVisible(false);
		            }
		        });
		        jComboBoxLEnCountry.setRenderer(new ComboBoxRenderer(true));
		        jComboBoxIndCountry.setRenderer(new ComboBoxRenderer(true));
		        
		        jLabelMain.setFont(Constants.FONT_PLAIN);
		        jLabelMain.setForeground(Constants.FONT_COLOR);
		        jLabelMain.setText(role); 
		        this.add(jLabelMain);
		        jLabelMain.setBounds(10, 20, 50, 14);
	
		        jComboBoxResidenceType.setModel(new DefaultComboBoxModel(new String[] {Constants.LANG.getString("Foreigner"),Constants.LANG.getString("Resident"),Constants.LANG.getString("ResidentEU")}));
		        this.add(jComboBoxResidenceType);
		        jComboBoxResidenceType.setBounds(200, 70, 120, 20);
		        jComboBoxResidenceType.setSelectedIndex(-1);
		        jComboBoxResidenceType.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	PartyPanel.this.actionPerformedErrorComboBox(evt);
		            }
		        });
		        jComboBoxResidenceType.setRenderer(new ComboBoxRenderer(true));
		        
		        jButtonNewUser.setIcon(new ImageIcon(getClass().getResource("/images/button_new_user.jpg"))); 
		        jButtonNewUser.setToolTipText(Constants.LANG.getString("NewUser")); 
		        jButtonNewUser.setBorderPainted(false);
		        jButtonNewUser.setContentAreaFilled(false);
		        jButtonNewUser.addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseEntered(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseEntered(evt);
		            }
		            public void mouseExited(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseExited(evt);
		            }
		        });
		        /** 
		         *  It avoid the posibility of reusing the same id two times:
		         *  1. New Issuer (an id is selected) but not saved.
		         *  2. New Receiver: The id is  obtained from the max(id) in the data base, so it will be the same.
		         *  3. The first party will be saved correctly, though not the other due to a constraint violation.
		         */
		        jButtonNewUser.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                Iterator<IPartyPanelListener> it = listeners.iterator();
		            	while (it.hasNext()){
							it.next().removeIds();
		            	}
						jButtonNewUserActionPerformed();
		            }
		        });
		        
		        this.add(jButtonNewUser);
		        jButtonNewUser.setBounds(25, 45, 30, 30);
	
		        jButtonEditUser.setIcon(new ImageIcon(getClass().getResource("/images/button_edit_user.jpg"))); 
		        jButtonEditUser.setToolTipText(Constants.LANG.getString("Edit")); 
		        jButtonEditUser.setBorderPainted(false);
		        jButtonEditUser.setContentAreaFilled(false);
		        jButtonEditUser.addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseEntered(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseEntered(evt);
		            }
		            public void mouseExited(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseExited(evt);
		            }
		        });
		        /** 
		         *  It allow the user edition:
		         */
		        jButtonEditUser.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jButtonEditActionPerformed();
		            }
		        });
		        
		        this.add(jButtonEditUser);
		        jButtonEditUser.setBounds(50, 75, 20, 20);
		        
		        jButtonDeleteUser.setIcon(new ImageIcon(getClass().getResource("/images/button_delete_user.jpg"))); 
		        jButtonDeleteUser.setToolTipText(Constants.LANG.getString("DeleteUser")); 
		        jButtonDeleteUser.setBorderPainted(false);
		        jButtonDeleteUser.setContentAreaFilled(false);
		        jButtonDeleteUser.addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseEntered(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseEntered(evt);
		            }
		            public void mouseExited(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseExited(evt);
		            }
		        });
		        jButtonDeleteUser.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                Object ob = jComboBoxItems.getModel().getSelectedItem();
			            if (ob == null){
							canvas1.showMessage(Constants.LANG.getString("NOOKMessageDeleteNotSelected"), Constants.ERROR_MSG_COLOR);
			            }
			            else{
		                	Iterator<IPartyPanelListener> it = listeners.iterator();
			            	while (it.hasNext()) {
								if (!it.next().tryDelete(ob)) {
									canvas1.showMessage(Constants.LANG.getString("OKMessageDeleteUsed"), Constants.OK_MSG_COLOR);
									break;
								}
								if (!it.hasNext())
									jButtonDeleteUserActionPerformed(evt);
							}
			            }
		            }
		        });
		        
		        this.add(jButtonDeleteUser);
		        jButtonDeleteUser.setBounds(10, 75, 16, 16);
		        
		        jRadioButtonLEn.setBackground(Constants.BKG_MAIN_COLOR);
		        buttonGroup.add(jRadioButtonLEn);
		        jRadioButtonLEn.setFont(Constants.FONT_PLAIN);
		        jRadioButtonLEn.setForeground(Constants.FONT_COLOR);
		        jRadioButtonLEn.setSelected(true);
		        jRadioButtonLEn.setText(Constants.LANG.getString("LegalEntity")); 
		        jRadioButtonLEn.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	jRadioButtonGroupActionPerformed();
		            }
		        });
	
		        this.add(jRadioButtonLEn);
		        jRadioButtonLEn.setBounds(80, 40, 120, 23);
	
		        jRadioButtonInd.setBackground(Constants.BKG_MAIN_COLOR);
		        buttonGroup.add(jRadioButtonInd);
		        jRadioButtonInd.setFont(Constants.FONT_PLAIN);
		        jRadioButtonInd.setForeground(Constants.FONT_COLOR);
		        jRadioButtonInd.setText(Constants.LANG.getString("Individual")); 
		        jRadioButtonInd.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		            	jRadioButtonGroupActionPerformed();
		            }
		        });
		        this.add(jRadioButtonInd);
		        jRadioButtonInd.setBounds(210, 40, 110, 23);
	
		        jComboBoxItems.setModel(cbmLEn);
		        this.add(jComboBoxItems);
		        jComboBoxItems.setBounds(70, 15, 250, 22);
		        jComboBoxItems.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jComboBoxItemsActionPerformed(evt);
		            }
		        });
		        jComboBoxItems.setRenderer(new ComboBoxRenderer(true));
	
		        jLabelResidenceType.setFont(Constants.FONT_PLAIN);
		        jLabelResidenceType.setForeground(Constants.FONT_COLOR);
		        jLabelResidenceType.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelResidenceType.setText(Constants.LANG.getString("ResidenceType")); 
		        this.add(jLabelResidenceType);
		        jLabelResidenceType.setBounds(80, 70, 120, 14);
	
		        jPanelLEnGeneral.setBackground(Constants.BKG_MAIN_COLOR);
	
		        jLabelLEnId.setFont(Constants.FONT_PLAIN);
		        jLabelLEnId.setForeground(Constants.FONT_COLOR);
		        jLabelLEnId.setText(Constants.LANG.getString("Identifier")); 
	
		        jTextField[0].setHorizontalAlignment(JTextField.RIGHT);
	
		        jLabelLEnCIF.setFont(Constants.FONT_PLAIN);
		        jLabelLEnCIF.setForeground(Constants.FONT_COLOR);
		        jLabelLEnCIF.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnCIF.setText(Constants.LANG.getString("TaxIdentificationNumberParty")); 
	
		        jTextField[1].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evCIF = new ExpressionValidator("[a-zA-Z0-9]{3,30}","NOOKParam3to30Digit");
		        jTextField[1].addFocusListener(evCIF);
		        
		        jLabelLEnCorpName.setFont(Constants.FONT_PLAIN);
		        jLabelLEnCorpName.setForeground(Constants.FONT_COLOR);
		        jLabelLEnCorpName.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnCorpName.setText(Constants.LANG.getString("CorporateName")); 
	
		        jTextField[2].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evCorpName = new ExpressionValidator(".{0,80}","NOOKParamTooHigh");
		        jTextField[2].addFocusListener(evCorpName);
		        
		        jLabelLEnTradeName.setFont(Constants.FONT_PLAIN);
		        jLabelLEnTradeName.setForeground(Constants.FONT_COLOR);
		        jLabelLEnTradeName.setText(Constants.LANG.getString("TradeName")); 
	
		        jTextField[3].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evTrade = new ExpressionValidator(".{0,40}","NOOKParamTooHigh");
		        jTextField[3].addFocusListener(evTrade);
		        
		        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanelLEnGeneral);
		        jPanelLEnGeneral.setLayout(jPanel7Layout);
		        jPanel7Layout.setHorizontalGroup(
		            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel7Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                    .add(jLabelLEnTradeName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(jLabelLEnId, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(jLabelLEnCIF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(jLabelLEnCorpName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                    .add(jTextField[0], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[1], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[2], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[3], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 201, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .addContainerGap())
		        );
		        jPanel7Layout.setVerticalGroup(
		            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel7Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[0], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnId))
		                .add(2, 2, 2)
		                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[1], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnCIF))
		                .add(2, 2, 2)
		                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[2], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnCorpName))
		                .add(2, 2, 2)
		                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jLabelLEnTradeName)
		                    .add(jTextField[3], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(2, 2, 2))
		        );
	
		        jTabbedPaneLEn.addTab(Constants.LANG.getString("General"), new ImageIcon(getClass().getResource("/images/asterisco.jpg")), jPanelLEnGeneral); 
	
		        jPanelLEnAddress.setBackground(Constants.BKG_MAIN_COLOR);
	
		        jLabelLEnAddress.setFont(Constants.FONT_PLAIN);
		        jLabelLEnAddress.setForeground(Constants.FONT_COLOR);
		        jLabelLEnAddress.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnAddress.setText(Constants.LANG.getString("Address")); 
	
		        jTextField[4].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evAddr = new ExpressionValidator(".{0,80}","NOOKParamTooHigh");
		        jTextField[4].addFocusListener(evAddr);
		        
		        jLabelLEnPostCode.setFont(Constants.FONT_PLAIN);
		        jLabelLEnPostCode.setForeground(Constants.FONT_COLOR);
		        jLabelLEnPostCode.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnPostCode.setText(Constants.LANG.getString("PostCode")); 
	
		        jTextField[5].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evPCode = new ExpressionValidator("[0-9]{5}","NOOKParam5Digit");
		        jTextField[5].addFocusListener(evPCode);
	
		        jLabelLEnTown.setFont(Constants.FONT_PLAIN);
		        jLabelLEnTown.setForeground(Constants.FONT_COLOR);
		        jLabelLEnTown.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnTown.setText(Constants.LANG.getString("Town")); 
	
		        jLabelLEnProvince.setFont(Constants.FONT_PLAIN);
		        jLabelLEnProvince.setForeground(Constants.FONT_COLOR);
		        jLabelLEnProvince.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnProvince.setText(Constants.LANG.getString("Province")); 
	
		        jTextField[7].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evPrv = new ExpressionValidator(".{0,20}","NOOKParamTooHigh");
		        jTextField[7].addFocusListener(evPrv);
		        
		        jLabelLEnCountry.setFont(Constants.FONT_PLAIN);
		        jLabelLEnCountry.setForeground(Constants.FONT_COLOR);
		        jLabelLEnCountry.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnCountry.setText(Constants.LANG.getString("Country")); 
	
		        jTextField[6].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evTown = new ExpressionValidator(".{0,50}","NOOKParamTooHigh");
		        jTextField[6].addFocusListener(evTown);
		        
		        jTextField[8].setHorizontalAlignment(JTextField.RIGHT);
	
		        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanelLEnAddress);
		        jPanelLEnAddress.setLayout(jPanel12Layout);
		        jPanel12Layout.setHorizontalGroup(
		            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel12Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                    .add(jLabelLEnCountry, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(jLabelLEnAddress, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
		                    .add(jLabelLEnPostCode, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(jLabelLEnTown, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                    .add(jLabelLEnProvince, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jTextField[6], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[5], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[4], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[7], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jComboBoxLEnCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .addContainerGap())
		        );
		        jPanel12Layout.setVerticalGroup(
		            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel12Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[4], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnAddress))
		                .add(2, 2, 2)
		                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[5], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnPostCode))
		                .add(2, 2, 2)
		                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[6], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnTown))
		                .add(2, 2, 2)
		                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[7], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnProvince))
		                .add(2, 2, 2)
		                .add(jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jComboBoxLEnCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnCountry))
		                .add(2, 2, 2))
		        );
	
		        jTabbedPaneLEn.addTab(Constants.LANG.getString("Address"), new ImageIcon(getClass().getResource("/images/asterisco.jpg")), jPanelLEnAddress); 
	
		        jPanelLEnRegistration.setBackground(Constants.BKG_MAIN_COLOR);
	
		        jLabelLEnBook.setFont(Constants.FONT_PLAIN);
		        jLabelLEnBook.setForeground(Constants.FONT_COLOR);
		        jLabelLEnBook.setText(Constants.LANG.getString("Book")); 
	
		        jLabelLEnLocation.setFont(Constants.FONT_PLAIN);
		        jLabelLEnLocation.setForeground(Constants.FONT_COLOR);
		        jLabelLEnLocation.setText(Constants.LANG.getString("RegisterOfCompaniesLocation")); 
	
		        jLabelLEnSheet.setFont(Constants.FONT_PLAIN);
		        jLabelLEnSheet.setForeground(Constants.FONT_COLOR);
		        jLabelLEnSheet.setText(Constants.LANG.getString("Sheet")); 
	
		        jLabelLEnFolio.setFont(Constants.FONT_PLAIN);
		        jLabelLEnFolio.setForeground(Constants.FONT_COLOR);
		        jLabelLEnFolio.setText(Constants.LANG.getString("Folio")); 
	
		        jLabelLEnSection.setFont(Constants.FONT_PLAIN);
		        jLabelLEnSection.setForeground(Constants.FONT_COLOR);
		        jLabelLEnSection.setText(Constants.LANG.getString("Section")); 
	
		        jLabelLEnVolume.setFont(Constants.FONT_PLAIN);
		        jLabelLEnVolume.setForeground(Constants.FONT_COLOR);
		        jLabelLEnVolume.setText(Constants.LANG.getString("Volume")); 
	
		        jLabelLEnRegAddData.setFont(Constants.FONT_PLAIN);
		        jLabelLEnRegAddData.setForeground(Constants.FONT_COLOR);
		        jLabelLEnRegAddData.setText(Constants.LANG.getString("OtherRegistrationData")); 
	
		        // All fields in registration data use the same validator because 
		        // they have to follow an identical regular expression.
		        ExpressionValidator evRegData = new ExpressionValidator(".{0,20}","NOOKParamTooHigh");
		        
		        jTextField[9].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[9].addFocusListener(evRegData);
		        
		        jTextField[10].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[10].addFocusListener(evRegData);
		        
		        jTextField[11].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[11].addFocusListener(evRegData);
		        
		        jTextField[12].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[12].addFocusListener(evRegData);
		        
		        jTextField[13].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[13].addFocusListener(evRegData);
		        
		        jTextField[14].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[14].addFocusListener(evRegData);
		        
		        jTextField[15].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[15].addFocusListener(evRegData);
		        
		        org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanelLEnRegistration);
		        jPanelLEnRegistration.setLayout(jPanel13Layout);
		        jPanel13Layout.setHorizontalGroup(
		            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel13Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jPanel13Layout.createSequentialGroup()
		                        .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(jLabelLEnSection, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(jLabelLEnBook, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(jLabelLEnSheet, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                        .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
		                            .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField[11], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                            .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField[10], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                            .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField[9], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
		                    .add(jLabelLEnRegAddData, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		                    .add(jPanel13Layout.createSequentialGroup()
		                        .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(jLabelLEnLocation, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(jLabelLEnFolio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(jLabelLEnVolume, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                        .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(jTextField[14], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
		                            .add(jTextField[13], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
		                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField[12], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
		                    .add(jTextField[15], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
		                .addContainerGap())
		        );
		        jPanel13Layout.setVerticalGroup(
		            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel13Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[12], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnLocation)
		                    .add(jTextField[9], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnBook))
		                .add(2, 2, 2)
		                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[13], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[10], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnSheet)
		                    .add(jLabelLEnFolio))
		                .add(2, 2, 2)
		                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jLabelLEnSection)
		                    .add(jTextField[14], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[11], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnVolume))
		                .add(2, 2, 2)
		                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[15], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnRegAddData))
		                .add(2, 2, 2))
		        );
	
		        jTabbedPaneLEn.addTab(Constants.LANG.getString("RegistrationData"), jPanelLEnRegistration); 
	
		        jPanelLEnContact.setBackground(Constants.BKG_MAIN_COLOR);
	
		        jLabelLEnTelephone.setFont(Constants.FONT_PLAIN);
		        jLabelLEnTelephone.setForeground(Constants.FONT_COLOR);
		        jLabelLEnTelephone.setText(Constants.LANG.getString("Telephone")); 
	
		        jLabelLEnFax.setFont(Constants.FONT_PLAIN);
		        jLabelLEnFax.setForeground(Constants.FONT_COLOR);
		        jLabelLEnFax.setText(Constants.LANG.getString("Fax")); 
	
		        jLabelLEnWeb.setFont(Constants.FONT_PLAIN);
		        jLabelLEnWeb.setForeground(Constants.FONT_COLOR);
		        jLabelLEnWeb.setText(Constants.LANG.getString("Web")); 
	
		        jLabelLEnMail.setFont(Constants.FONT_PLAIN);
		        jLabelLEnMail.setForeground(Constants.FONT_COLOR);
		        jLabelLEnMail.setText(Constants.LANG.getString("EMail")); 
	
		        jLabelLEnContacts.setFont(Constants.FONT_PLAIN);
		        jLabelLEnContacts.setForeground(Constants.FONT_COLOR);
		        jLabelLEnContacts.setText(Constants.LANG.getString("Contacts")); 
	
		        jLabelLEnConAddData.setFont(Constants.FONT_PLAIN);
		        jLabelLEnConAddData.setForeground(Constants.FONT_COLOR);
		        jLabelLEnConAddData.setText(Constants.LANG.getString("OtherContactData")); 
	
		        jLabelLEnINETownCode.setFont(Constants.FONT_PLAIN);
		        jLabelLEnINETownCode.setForeground(Constants.FONT_COLOR);
		        jLabelLEnINETownCode.setText(Constants.LANG.getString("INETownCode")); 
		        
		        jLabelLEnCNOCNAE.setFont(Constants.FONT_PLAIN);
		        jLabelLEnCNOCNAE.setForeground(Constants.FONT_COLOR);
		        jLabelLEnCNOCNAE.setText(Constants.LANG.getString("CNOCNAE")); 
	
		        jTextField[23].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evAddCD = new ExpressionValidator(".{0,60}","NOOKParamTooHigh");
		        jTextField[23].addFocusListener(evAddCD);
		        
		        jTextField[18].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evCont = new ExpressionValidator(".{0,40}","NOOKParamTooHigh");
		        jTextField[18].addFocusListener(evCont);
		        
		        jTextField[16].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evTeleph = new ExpressionValidator(".{0,15}","NOOKParamTooHigh");
		        jTextField[16].addFocusListener(evTeleph);
		        
		        jTextField[17].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evWeb = new ExpressionValidator(".{0,60}","NOOKParamTooHigh");
		        jTextField[17].addFocusListener(evWeb);
		        
		        jTextField[20].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evFax = new ExpressionValidator(".{0,15}","NOOKParamTooHigh");
		        jTextField[20].addFocusListener(evFax);
		        
		        jTextField[21].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evMail = new ExpressionValidator(".{0,60}","NOOKParamTooHigh");
		        jTextField[21].addFocusListener(evMail);
		        
		        jTextField[19].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evCnae = new ExpressionValidator("[0-9]{5}","NOOKParam5Digit");
		        jTextField[19].addFocusListener(evCnae);
		        
		        jTextField[22].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evTownC = new ExpressionValidator(".{0,9}","NOOKParamTooHigh");
		        jTextField[22].addFocusListener(evTownC);
		        
		        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanelLEnContact);
		        jPanelLEnContact.setLayout(jPanel6Layout);
		        jPanel6Layout.setHorizontalGroup(
		            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel6Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jPanel6Layout.createSequentialGroup()
		                        .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(jLabelLEnTelephone, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
		                            .add(jLabelLEnWeb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(jLabelLEnContacts, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(jLabelLEnCNOCNAE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                        .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(jTextField[19], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
		                            .add(jTextField[16], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
		                            .add(jTextField[17], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
		                            .add(jTextField[18], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)))
		                    .add(jLabelLEnConAddData, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		                    .add(jPanel6Layout.createSequentialGroup()
		                        .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(jLabelLEnINETownCode, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(jLabelLEnFax, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
		                            .add(jLabelLEnMail, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                        .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel6Layout.createSequentialGroup()
		                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                                .add(jTextField[20], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
		                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField[21], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
		                            .add(jTextField[22], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)))
		                    .add(jTextField[23], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
		                .addContainerGap())
		        );
		        jPanel6Layout.setVerticalGroup(
		            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel6Layout.createSequentialGroup()
		                .add(11, 11, 11)
		                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[20], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[16], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnTelephone)
		                    .add(jLabelLEnFax))
		                .add(2, 2, 2)
		                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[21], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[17], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnWeb)
		                    .add(jLabelLEnMail))
		                .add(2, 2, 2)
		                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jLabelLEnINETownCode)
		                    .add(jTextField[22], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[18], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnContacts))
		                .add(2, 2, 2)
		                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jTextField[19], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnCNOCNAE))
		                .add(2, 2, 2)
		                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[23], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelLEnConAddData))
		                .add(2, 2, 2))
		        );
	
		        jTabbedPaneLEn.addTab(Constants.LANG.getString("ContactData"), jPanelLEnContact); 
	
		        //jPanelLEnFACe
		        jPanelLEnFACe.setBackground(Constants.BKG_MAIN_COLOR);

		        jLabelLEnManagementAgency.setFont(Constants.FONT_PLAIN);
		        jLabelLEnManagementAgency.setForeground(Constants.FONT_COLOR);
		        jLabelLEnManagementAgency.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnManagementAgency.setText(Constants.LANG.getString("ManagementAgency")); 

		        jTextField[42].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[42].setEditable(false);
		        
			    jButtonLEnFACeManagementAgency.setBorderPainted(false);
			    jButtonLEnFACeManagementAgency.setContentAreaFilled(false);
			    jButtonLEnFACeManagementAgency.setHorizontalAlignment(SwingConstants.CENTER);
			    jButtonLEnFACeManagementAgency.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit.jpg")));
			    jButtonLEnFACeManagementAgency.setDisabledIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit_disabled.jpg")));
			    jButtonLEnFACeManagementAgency.setToolTipText(Constants.LANG.getString("ManagementAgencyButton"));
			    jButtonLEnFACeManagementAgency.addMouseListener(new MouseAdapter() {
		        	public void mouseEntered(MouseEvent evt) {
		        		if(evt.getComponent().isEnabled()) {
		        			setCursor(new Cursor(Cursor.HAND_CURSOR));
		        		}
		        	}
		        	public void mouseExited(MouseEvent evt) {
		        		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		        	}
		        });	
			    jButtonLEnFACeManagementAgency.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent evt) {
		        		new Thread(new Runnable() { public void run() {
		        	        if(t!=null) {
		        	        	t.putTransitionPanel(Constants.LANG.getString("ConsultingFACe"));
		        	        }
		        			
		        			jButtonFACeAdmCentreActionPerformed(Constants.FACE_ROLE_MANAGEMENTAGENCY, jTextField[42], jLabelLEnManagementAgency.getText());
			    	        if(t!=null) {
			    	        	t.removeTransitionPanel();
			    	        }
			    		}}).start();
		        	}
		        });
			    jButtonLEnFACeManagementAgency.setEnabled(false);
			    
		        jLabelLEnProcessingAuthorityUnit.setFont(Constants.FONT_PLAIN);
		        jLabelLEnProcessingAuthorityUnit.setForeground(Constants.FONT_COLOR);
		        jLabelLEnProcessingAuthorityUnit.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnProcessingAuthorityUnit.setText(Constants.LANG.getString("ProcessingAuthorityUnit"));
		        
		        jTextField[43].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[43].setEditable(false);

			    jButtonLEnFACeProcessingAuthorityUnit.setBorderPainted(false);
			    jButtonLEnFACeProcessingAuthorityUnit.setContentAreaFilled(false);
			    jButtonLEnFACeProcessingAuthorityUnit.setHorizontalAlignment(SwingConstants.CENTER);
			    jButtonLEnFACeProcessingAuthorityUnit.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit.jpg")));
			    jButtonLEnFACeProcessingAuthorityUnit.setDisabledIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit_disabled.jpg")));
			    jButtonLEnFACeProcessingAuthorityUnit.setToolTipText(Constants.LANG.getString("ManagementAgencyButton"));
			    jButtonLEnFACeProcessingAuthorityUnit.addMouseListener(new MouseAdapter() {
		        	public void mouseEntered(MouseEvent evt) {
		        		if(evt.getComponent().isEnabled()) {
		        			setCursor(new Cursor(Cursor.HAND_CURSOR));
		        		}
		        	}
		        	public void mouseExited(MouseEvent evt) {
		        		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		        	}
		        });	
			    jButtonLEnFACeProcessingAuthorityUnit.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent evt) {
		        		new Thread(new Runnable() { public void run() {
		        	        if(t!=null) {
		        	        	t.putTransitionPanel(Constants.LANG.getString("ConsultingFACe"));
		        	        }

		        	        jButtonFACeAdmCentreActionPerformed(Constants.FACE_ROLE_PROCESSINGAUTHORITYUNIT, jTextField[43], jLabelLEnProcessingAuthorityUnit.getText());

		        	        if(t!=null) {
			    	        	t.removeTransitionPanel();
			    	        }
			    		}}).start();

		        	}
		        });
			    jButtonLEnFACeProcessingAuthorityUnit.setEnabled(false);
			    
		        jLabelLEnAccountingOffice.setFont(Constants.FONT_PLAIN);
		        jLabelLEnAccountingOffice.setForeground(Constants.FONT_COLOR);
		        jLabelLEnAccountingOffice.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelLEnAccountingOffice.setText(Constants.LANG.getString("AccountingOffice"));
		        
		        jTextField[44].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[44].setEditable(false);

		        jButtonLEnFACeAccountingOffice.setBorderPainted(false);
		        jButtonLEnFACeAccountingOffice.setContentAreaFilled(false);
		        jButtonLEnFACeAccountingOffice.setHorizontalAlignment(SwingConstants.CENTER);
		        jButtonLEnFACeAccountingOffice.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit.jpg")));
		        jButtonLEnFACeAccountingOffice.setDisabledIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit_disabled.jpg")));
		        jButtonLEnFACeAccountingOffice.setToolTipText(Constants.LANG.getString("ManagementAgencyButton"));
		        jButtonLEnFACeAccountingOffice.addMouseListener(new MouseAdapter() {
		        	public void mouseEntered(MouseEvent evt) {
		        		if(evt.getComponent().isEnabled()) {
		        			setCursor(new Cursor(Cursor.HAND_CURSOR));
		        		}
		        	}
		        	public void mouseExited(MouseEvent evt) {
		        		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		        	}
		        });	
		        jButtonLEnFACeAccountingOffice.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent evt) {
		        		jButtonFACeAdmCentreActionPerformed(Constants.FACE_ROLE_ACCOUNTINGOFFICE, jTextField[44], jLabelLEnAccountingOffice.getText());
		        	}
		        });
		        jButtonLEnFACeAccountingOffice.setEnabled(false);
			    
		        jLabelLEnProposingAgency.setFont(Constants.FONT_PLAIN);
		        jLabelLEnProposingAgency.setForeground(Constants.FONT_COLOR);
		        jLabelLEnProposingAgency.setText(Constants.LANG.getString("ProposingAgency"));
		        
		        jTextField[45].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[45].setEditable(false);
		        
		        jButtonLEnFACeProposingAgency.setBorderPainted(false);
		        jButtonLEnFACeProposingAgency.setContentAreaFilled(false);
		        jButtonLEnFACeProposingAgency.setHorizontalAlignment(SwingConstants.CENTER);
		        jButtonLEnFACeProposingAgency.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit.jpg")));
		        jButtonLEnFACeProposingAgency.setDisabledIcon(new ImageIcon(getClass().getResource("/images/button_mini_edit_disabled.jpg")));
		        jButtonLEnFACeProposingAgency.setToolTipText(Constants.LANG.getString("ManagementAgencyButton"));
		        jButtonLEnFACeProposingAgency.addMouseListener(new MouseAdapter() {
		        	public void mouseEntered(MouseEvent evt) {
		        		if(evt.getComponent().isEnabled()) {
		        			setCursor(new Cursor(Cursor.HAND_CURSOR));
		        		}
		        	}
		        	public void mouseExited(MouseEvent evt) {
		        		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		        	}
		        });	
		        jButtonLEnFACeProposingAgency.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent evt) {
		        		jButtonFACeAdmCentreActionPerformed(Constants.FACE_ROLE_PROPOSINGAGENCY, jTextField[45], jLabelLEnProposingAgency.getText());
		        	}
		        });
		        jButtonLEnFACeProposingAgency.setEnabled(false);
			    			        
		        jLabelLEnFACeLink.setText(Constants.LANG.getString("LinkFACe"));
			    jLabelLEnFACeLink.setForeground(Constants.LINK_COLOR);
			    jLabelLEnFACeLink.setFont(Constants.FONT_LINK);
			    jLabelLEnFACeLink.setHorizontalAlignment(SwingConstants.LEFT);
			    jLabelLEnFACeLink.addMouseListener(new MouseAdapter() {
		        	public void mouseEntered(MouseEvent evt) {
		        		setCursor(new Cursor(Cursor.HAND_CURSOR));
		        	}
		        	public void mouseExited(MouseEvent evt) {
		        		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		        	}
		        	public void mouseReleased(MouseEvent e) {
		        		jLabelFACeLinkActionPerformed();
		        	}
		        	
		        });
			    
		        jPanelLEnFACe.setLayout(new GridBagLayout());
		        
			    GridBagConstraints jLabelLEnManagementAgencyConstraints = new GridBagConstraints();
			    jLabelLEnManagementAgencyConstraints.gridx = 0;
			    jLabelLEnManagementAgencyConstraints.gridy = 0;
			    jLabelLEnManagementAgencyConstraints.gridwidth = 1;
			    jLabelLEnManagementAgencyConstraints.gridheight = 1;
			    jLabelLEnManagementAgencyConstraints.anchor = GridBagConstraints.WEST;
			    jLabelLEnManagementAgencyConstraints.insets = new Insets(3,3,0,10);
			    jPanelLEnFACe.add(jLabelLEnManagementAgency, jLabelLEnManagementAgencyConstraints);					        
			    GridBagConstraints textManagementAgencyConstraints = new GridBagConstraints();
			    textManagementAgencyConstraints.gridx = 1;
			    textManagementAgencyConstraints.gridy = 0;
			    textManagementAgencyConstraints.gridwidth = 1;
			    textManagementAgencyConstraints.gridheight = 1;
			    textManagementAgencyConstraints.anchor = GridBagConstraints.WEST;
			    textManagementAgencyConstraints.fill = GridBagConstraints.HORIZONTAL;
			    textManagementAgencyConstraints.weightx = 0.1;
			    textManagementAgencyConstraints.insets = new Insets(3,3,0,3);
			    jPanelLEnFACe.add(jTextField[42], textManagementAgencyConstraints);					        
			    GridBagConstraints jButtonLEnFACeManagementAgencyConstraints = new GridBagConstraints();
			    jButtonLEnFACeManagementAgencyConstraints.gridx = 2;
			    jButtonLEnFACeManagementAgencyConstraints.gridy = 0;
			    jButtonLEnFACeManagementAgencyConstraints.gridwidth = 1;
			    jButtonLEnFACeManagementAgencyConstraints.gridheight = 1;
			    jButtonLEnFACeManagementAgencyConstraints.anchor = GridBagConstraints.WEST;
			    jButtonLEnFACeManagementAgencyConstraints.fill = GridBagConstraints.HORIZONTAL;
			    jButtonLEnFACeManagementAgencyConstraints.insets = new Insets(3,0,0,10);
			    jPanelLEnFACe.add(jButtonLEnFACeManagementAgency, jButtonLEnFACeManagementAgencyConstraints);					        
		        
			    GridBagConstraints jLabelLEnProcessingAuthorityUnitConstraints = new GridBagConstraints();
			    jLabelLEnProcessingAuthorityUnitConstraints.gridx = 0;
			    jLabelLEnProcessingAuthorityUnitConstraints.gridy = 1;
			    jLabelLEnProcessingAuthorityUnitConstraints.gridwidth = 1;
			    jLabelLEnProcessingAuthorityUnitConstraints.gridheight = 1;
			    jLabelLEnProcessingAuthorityUnitConstraints.anchor = GridBagConstraints.WEST;
			    jLabelLEnProcessingAuthorityUnitConstraints.insets = new Insets(3,3,0,10);
			    jPanelLEnFACe.add(jLabelLEnProcessingAuthorityUnit, jLabelLEnProcessingAuthorityUnitConstraints);					        
			    GridBagConstraints textProcessingAuthorityUnitConstraints = new GridBagConstraints();
			    textProcessingAuthorityUnitConstraints.gridx = 1;
			    textProcessingAuthorityUnitConstraints.gridy = 1;
			    textProcessingAuthorityUnitConstraints.gridwidth = 1;
			    textProcessingAuthorityUnitConstraints.gridheight = 1;
			    textProcessingAuthorityUnitConstraints.anchor = GridBagConstraints.WEST;
			    textProcessingAuthorityUnitConstraints.fill = GridBagConstraints.HORIZONTAL;
			    textProcessingAuthorityUnitConstraints.weightx = 0.1;
			    textProcessingAuthorityUnitConstraints.insets = new Insets(3,3,0,2);
			    jPanelLEnFACe.add(jTextField[43], textProcessingAuthorityUnitConstraints);					        
			    GridBagConstraints jButtonLEnFACeProcessingAuthorityUnitConstraints = new GridBagConstraints();
			    jButtonLEnFACeProcessingAuthorityUnitConstraints.gridx = 2;
			    jButtonLEnFACeProcessingAuthorityUnitConstraints.gridy = 1;
			    jButtonLEnFACeProcessingAuthorityUnitConstraints.gridwidth = 1;
			    jButtonLEnFACeProcessingAuthorityUnitConstraints.gridheight = 1;
			    jButtonLEnFACeProcessingAuthorityUnitConstraints.anchor = GridBagConstraints.WEST;
			    jButtonLEnFACeProcessingAuthorityUnitConstraints.fill = GridBagConstraints.HORIZONTAL;
			    jButtonLEnFACeProcessingAuthorityUnitConstraints.insets = new Insets(3,0,0,10);
			    jPanelLEnFACe.add(jButtonLEnFACeProcessingAuthorityUnit, jButtonLEnFACeProcessingAuthorityUnitConstraints);					        
		        
			    GridBagConstraints jLabelLEnAccountingOfficeConstraints = new GridBagConstraints();
			    jLabelLEnAccountingOfficeConstraints.gridx = 0;
			    jLabelLEnAccountingOfficeConstraints.gridy = 2;
			    jLabelLEnAccountingOfficeConstraints.gridwidth = 1;
			    jLabelLEnAccountingOfficeConstraints.gridheight = 1;
			    jLabelLEnAccountingOfficeConstraints.anchor = GridBagConstraints.WEST;
			    jLabelLEnAccountingOfficeConstraints.insets = new Insets(3,3,0,10);
			    jPanelLEnFACe.add(jLabelLEnAccountingOffice, jLabelLEnAccountingOfficeConstraints);					        
			    GridBagConstraints textAccountingOfficeConstraints = new GridBagConstraints();
			    textAccountingOfficeConstraints.gridx = 1;
			    textAccountingOfficeConstraints.gridy = 2;
			    textAccountingOfficeConstraints.gridwidth = 1;
			    textAccountingOfficeConstraints.gridheight = 1;
			    textAccountingOfficeConstraints.anchor = GridBagConstraints.WEST;
			    textAccountingOfficeConstraints.fill = GridBagConstraints.HORIZONTAL;
			    textAccountingOfficeConstraints.insets = new Insets(3,3,0,3);
			    jPanelLEnFACe.add(jTextField[44], textAccountingOfficeConstraints);					        
			    GridBagConstraints jButtonLEnFACeAccountingOfficeConstraints = new GridBagConstraints();
			    jButtonLEnFACeAccountingOfficeConstraints.gridx = 2;
			    jButtonLEnFACeAccountingOfficeConstraints.gridy = 2;
			    jButtonLEnFACeAccountingOfficeConstraints.gridwidth = 1;
			    jButtonLEnFACeAccountingOfficeConstraints.gridheight = 1;
			    jButtonLEnFACeAccountingOfficeConstraints.anchor = GridBagConstraints.WEST;
			    jButtonLEnFACeAccountingOfficeConstraints.fill = GridBagConstraints.HORIZONTAL;
			    jButtonLEnFACeAccountingOfficeConstraints.insets = new Insets(3,0,0,10);
			    jPanelLEnFACe.add(jButtonLEnFACeAccountingOffice, jButtonLEnFACeAccountingOfficeConstraints);					        
		        
			    GridBagConstraints jLabelLEnProposingAgencyConstraints = new GridBagConstraints();
			    jLabelLEnProposingAgencyConstraints.gridx = 0;
			    jLabelLEnProposingAgencyConstraints.gridy = 3;
			    jLabelLEnProposingAgencyConstraints.gridwidth = 1;
			    jLabelLEnProposingAgencyConstraints.gridheight = 1;
			    jLabelLEnProposingAgencyConstraints.anchor = GridBagConstraints.WEST;
			    jLabelLEnProposingAgencyConstraints.insets = new Insets(3,3,0,10);
			    jPanelLEnFACe.add(jLabelLEnProposingAgency, jLabelLEnProposingAgencyConstraints);					        
			    GridBagConstraints textProposingAgencyConstraints = new GridBagConstraints();
			    textProposingAgencyConstraints.gridx = 1;
			    textProposingAgencyConstraints.gridy = 3;
			    textProposingAgencyConstraints.gridwidth = 1;
			    textProposingAgencyConstraints.gridheight = 1;
			    textProposingAgencyConstraints.anchor = GridBagConstraints.WEST;
			    textProposingAgencyConstraints.fill = GridBagConstraints.HORIZONTAL;
			    textProposingAgencyConstraints.insets = new Insets(3,3,0,3);
			    jPanelLEnFACe.add(jTextField[45], textProposingAgencyConstraints);					        
			    GridBagConstraints jButtonLEnFACeProposingAgencyConstraints = new GridBagConstraints();
			    jButtonLEnFACeProposingAgencyConstraints.gridx = 2;
			    jButtonLEnFACeProposingAgencyConstraints.gridy = 3;
			    jButtonLEnFACeProposingAgencyConstraints.gridwidth = 1;
			    jButtonLEnFACeProposingAgencyConstraints.gridheight = 1;
			    jButtonLEnFACeProposingAgencyConstraints.anchor = GridBagConstraints.WEST;
			    jButtonLEnFACeProposingAgencyConstraints.fill = GridBagConstraints.HORIZONTAL;
			    jButtonLEnFACeProposingAgencyConstraints.insets = new Insets(3,0,0,10);
			    jPanelLEnFACe.add(jButtonLEnFACeProposingAgency, jButtonLEnFACeProposingAgencyConstraints);					        
		        
			    GridBagConstraints jLabelFACeLinkConstraints = new GridBagConstraints();
			    jLabelFACeLinkConstraints.gridx = 0;
			    jLabelFACeLinkConstraints.gridy = 4;
			    jLabelFACeLinkConstraints.gridwidth = 2;
			    jLabelFACeLinkConstraints.gridheight = 1;
			    jLabelFACeLinkConstraints.anchor = GridBagConstraints.WEST;
			    jLabelFACeLinkConstraints.insets = new Insets(3,3,0,10);
			    jPanelLEnFACe.add(jLabelLEnFACeLink, jLabelFACeLinkConstraints);					        
		        
		        jPanelLEnFACe.setEnabled(false);
		        jTabbedPaneLEn.addTab(Constants.LANG.getString("FACe"), new ImageIcon(getClass().getResource("/images/asterisco.jpg")), jPanelLEnFACe);
		        jTabbedPaneLEn.setEnabledAt(FACE_TAB, false);
		        
		        this.add(jTabbedPaneLEn);
		        jTabbedPaneLEn.setBounds(10, 100, 330, 155);
	
		        jPanelIndGeneral.setBackground(Constants.BKG_MAIN_COLOR);
	
		        jLabelIndId.setFont(Constants.FONT_PLAIN);
		        jLabelIndId.setForeground(Constants.FONT_COLOR);
		        jLabelIndId.setText(Constants.LANG.getString("Identifier")); 
	
		        jTextField[24].setHorizontalAlignment(JTextField.RIGHT);
	
		        jLabelIndNIF.setFont(Constants.FONT_PLAIN);
		        jLabelIndNIF.setForeground(Constants.FONT_COLOR);
		        jLabelIndNIF.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndNIF.setText(Constants.LANG.getString("TaxIdentificationNumberParty")); 
	
		        jTextField[25].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evNIF = new ExpressionValidator("[a-zA-Z0-9]{3,30}","NOOKParam3to30Digit");
		        jTextField[25].addFocusListener(evNIF);
		        
		        jLabelIndName.setFont(Constants.FONT_PLAIN);
		        jLabelIndName.setForeground(Constants.FONT_COLOR);
		        jLabelIndName.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndName.setText(Constants.LANG.getString("Name")); 
	
		        jTextField[26].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evName = new ExpressionValidator(".{0,40}","NOOKParamTooHigh");
		        jTextField[26].addFocusListener(evName);
		        
		        jLabelIndFirstSurname.setFont(Constants.FONT_PLAIN);
		        jLabelIndFirstSurname.setForeground(Constants.FONT_COLOR);
		        jLabelIndFirstSurname.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndFirstSurname.setText(Constants.LANG.getString("FirstSurname")); 
	
		        jTextField[27].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evSname = new ExpressionValidator(".{0,40}","NOOKParamTooHigh");
		        jTextField[27].addFocusListener(evSname);
		        
		        jLabelIndSecondSurname.setFont(Constants.FONT_PLAIN);
		        jLabelIndSecondSurname.setForeground(Constants.FONT_COLOR);
		        jLabelIndSecondSurname.setText(Constants.LANG.getString("SecondSurname")); 
	
		        jTextField[28].setHorizontalAlignment(JTextField.RIGHT);
		        ExpressionValidator evSname2 = new ExpressionValidator(".{0,40}","NOOKParamTooHigh");
		        jTextField[28].addFocusListener(evSname2);
		        
		        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanelIndGeneral);
		        jPanelIndGeneral.setLayout(jPanel10Layout);
		        jPanel10Layout.setHorizontalGroup(
		            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel10Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jLabelIndId, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
		                    .add(jLabelIndNIF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
		                    .add(jLabelIndName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
		                    .add(jLabelIndFirstSurname, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
		                    .add(jLabelIndSecondSurname, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                    .add(jTextField[28], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 205, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[27], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 205, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[26], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 205, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[25], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 205, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[24], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 205, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .addContainerGap())
		        );
		        jPanel10Layout.setVerticalGroup(
		            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel10Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[24], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndId))
		                .add(2, 2, 2)
		                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[25], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndNIF))
		                .add(2, 2, 2)
		                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[26], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndName))
		                .add(2, 2, 2)
		                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[27], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndFirstSurname))
		                .add(2, 2, 2)
		                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[28], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndSecondSurname))
		                .addContainerGap())
		        );
	
		        jTabbedPaneInd.addTab("General", new ImageIcon(getClass().getResource("/images/asterisco.jpg")), jPanelIndGeneral); 
	
		        jPanelIndAddress.setBackground(Constants.BKG_MAIN_COLOR);
	
		        jLabelIndAddress.setFont(Constants.FONT_PLAIN);
		        jLabelIndAddress.setForeground(Constants.FONT_COLOR);
		        jLabelIndAddress.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndAddress.setText(Constants.LANG.getString("Address")); 
	
		        jTextField[29].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[29].addFocusListener(evAddr);
		        
		        jLabelIndPostCode.setFont(Constants.FONT_PLAIN);
		        jLabelIndPostCode.setForeground(Constants.FONT_COLOR);
		        jLabelIndPostCode.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndPostCode.setText(Constants.LANG.getString("PostCode")); 
	
		        jTextField[30].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[30].addFocusListener(evPCode);
		        
		        jLabelIndProvince.setFont(Constants.FONT_PLAIN);
		        jLabelIndProvince.setForeground(Constants.FONT_COLOR);
		        jLabelIndProvince.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndProvince.setText(Constants.LANG.getString("Province")); 
	
		        jTextField[32].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[32].addFocusListener(evPrv);
		        
		        jLabelIndCountry.setFont(Constants.FONT_PLAIN);
		        jLabelIndCountry.setForeground(Constants.FONT_COLOR);
		        jLabelIndCountry.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndCountry.setText(Constants.LANG.getString("Country")); 
	
		        jTextField[33].setHorizontalAlignment(JTextField.RIGHT);
	
		        jLabelIndTown.setFont(Constants.FONT_PLAIN);
		        jLabelIndTown.setForeground(Constants.FONT_COLOR);
		        jLabelIndTown.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg"))); 
		        jLabelIndTown.setText(Constants.LANG.getString("Town")); 
	
		        jTextField[31].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[31].addFocusListener(evTown);
		        
		        org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanelIndAddress);
		        jPanelIndAddress.setLayout(jPanel16Layout);
		        jPanel16Layout.setHorizontalGroup(
		            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel16Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jLabelIndProvince, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
		                    .add(jLabelIndTown, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
		                    .add(jLabelIndPostCode, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
		                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabelIndAddress, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
		                    .add(jPanel16Layout.createSequentialGroup()
		                        .add(jLabelIndCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 105, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)))
		                .add(4, 4, 4)
		                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
		                    .add(jComboBoxIndCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[32], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[30], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[31], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[29], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 193, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .addContainerGap())
		        );
		        
	
		        jPanel16Layout.setVerticalGroup(
		            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel16Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[29], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndAddress))
		                .add(2, 2, 2)
		                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[30], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndPostCode))
		                .add(2, 2, 2)
		                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[31], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndTown))
		                .add(2, 2, 2)
		                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jLabelIndProvince)
		                    .add(jTextField[32], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .add(2, 2, 2)
		                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jLabelIndCountry)
		                    .add(jComboBoxIndCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .addContainerGap())
		        );
	
		        jTabbedPaneInd.addTab(Constants.LANG.getString("Address"), new ImageIcon(getClass().getResource("/images/asterisco.jpg")), jPanelIndAddress); 
	
		        jPanelIndContact.setBackground(Constants.BKG_MAIN_COLOR);
	
		        jLabelIndTelephone.setFont(Constants.FONT_PLAIN);
		        jLabelIndTelephone.setForeground(Constants.FONT_COLOR);
		        jLabelIndTelephone.setText(Constants.LANG.getString("Telephone")); 
	
		        jLabelIndWeb.setFont(Constants.FONT_PLAIN);
		        jLabelIndWeb.setForeground(Constants.FONT_COLOR);
		        jLabelIndWeb.setText(Constants.LANG.getString("Web")); 
	
		        jLabelIndContacts.setFont(Constants.FONT_PLAIN);
		        jLabelIndContacts.setForeground(Constants.FONT_COLOR);
		        jLabelIndContacts.setText(Constants.LANG.getString("Contacts")); 
	
		        jLabelIndCNOCNAE.setFont(Constants.FONT_PLAIN);
		        jLabelIndCNOCNAE.setForeground(Constants.FONT_COLOR);
		        jLabelIndCNOCNAE.setText(Constants.LANG.getString("CNOCNAE")); 
	
		        jLabelIndConAddData.setFont(Constants.FONT_PLAIN);
		        jLabelIndConAddData.setForeground(Constants.FONT_COLOR);
		        jLabelIndConAddData.setText(Constants.LANG.getString("OtherContactData")); 
	
		        jTextField[34].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[34].addFocusListener(evTeleph);
		        
		        jTextField[35].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[35].addFocusListener(evWeb);
		        
		        jTextField[36].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[36].addFocusListener(evCont);
		        
		        jTextField[37].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[37].addFocusListener(evCnae);
		        
		        jTextField[41].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[41].addFocusListener(evAddCD);
		        
		        jLabelIndFax.setFont(Constants.FONT_PLAIN);
		        jLabelIndFax.setForeground(Constants.FONT_COLOR);
		        jLabelIndFax.setText(Constants.LANG.getString("Fax")); 
	
		        jLabelIndMail.setFont(Constants.FONT_PLAIN);
		        jLabelIndMail.setForeground(Constants.FONT_COLOR);
		        jLabelIndMail.setText(Constants.LANG.getString("EMail")); 
	
		        jLabelIndINETownCode.setFont(Constants.FONT_PLAIN);
		        jLabelIndINETownCode.setForeground(Constants.FONT_COLOR);
		        jLabelIndINETownCode.setText(Constants.LANG.getString("INETownCode")); 
	
		        jTextField[38].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[38].addFocusListener(evFax);
		        
		        jTextField[39].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[39].addFocusListener(evMail);
		        
		        jTextField[40].setHorizontalAlignment(JTextField.RIGHT);
		        jTextField[40].addFocusListener(evTownC);
		        
		        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanelIndContact);
		        jPanelIndContact.setLayout(jPanel11Layout);
		        jPanel11Layout.setHorizontalGroup(
		            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel11Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jPanel11Layout.createSequentialGroup()
		                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
		                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelIndCNOCNAE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelIndWeb, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelIndTelephone, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                            .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelIndContacts, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
		                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(jTextField[34], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                            .add(jTextField[35], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                            .add(jTextField[36], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                            .add(jTextField[37], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)))
		                    .add(jLabelIndConAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                    .add(jTextField[41], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
		                    .add(jPanel11Layout.createSequentialGroup()
		                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		                            .add(jLabelIndINETownCode)
		                            .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
		                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelIndMail, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                                .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelIndFax, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)))
		                        .add(1, 1, 1)
		                        .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
		                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField[40], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
		                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField[38], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
		                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jTextField[39], org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))))
		                .addContainerGap(11, Short.MAX_VALUE))
		        );
		        jPanel11Layout.setVerticalGroup(
		            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
		            .add(jPanel11Layout.createSequentialGroup()
		                .addContainerGap()
		                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[34], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[38], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndTelephone)
		                    .add(jLabelIndFax))
		                .add(2, 2, 2)
		                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jLabelIndWeb)
		                    .add(jTextField[35], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[39], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndMail))
		                .add(2, 2, 2)
		                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jLabelIndINETownCode)
		                    .add(jTextField[36], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jTextField[40], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndContacts))
		                .add(2, 2, 2)
		                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[37], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndCNOCNAE))
		                .add(2, 2, 2)
		                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
		                    .add(jTextField[41], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		                    .add(jLabelIndConAddData))
		                .addContainerGap())
		        );
	
		        jTabbedPaneInd.addTab(Constants.LANG.getString("ContactData"), jPanelIndContact);
	
		        this.add(jTabbedPaneInd);
		        jTabbedPaneInd.setBounds(11, 100, 330, 155);
		        jTabbedPaneInd.setVisible(false);
	
		        jButtonSave.setIcon(new ImageIcon(getClass().getResource("/images/button_save.jpg"))); 
		        jButtonSave.setToolTipText(Constants.LANG.getString("Save")); 
		        jButtonSave.setActionCommand(Constants.LANG.getString("Save"));
		        jButtonSave.setBorderPainted(false);
		        jButtonSave.setContentAreaFilled(false);
		        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jButtonSaveActionPerformed();
		            }
		        });
		        jButtonSave.addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseEntered(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseEntered(evt);
		            }
		            public void mouseExited(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseExited(evt);
		            }
		        });
		        this.add(jButtonSave);
		        jButtonSave.setBounds(20, 260, 30, 30);
		        jButtonSave.setDisabledIcon(new ImageIcon(getClass().getResource("/images/button_save_disabled.jpg")));
		        jButtonSave.setEnabled(false);
		        
		        jButtonAdmCentre.setIcon(new ImageIcon(getClass().getResource("/images/button_admcentre.jpg"))); 
		        jButtonAdmCentre.setToolTipText(Constants.LANG.getString("AdministrativeCentres")); 
		        jButtonAdmCentre.setActionCommand(Constants.LANG.getString("AdministrativeCentres"));
		        jButtonAdmCentre.setBorderPainted(false);
		        jButtonAdmCentre.setContentAreaFilled(false);
		        jButtonAdmCentre.addActionListener(new java.awt.event.ActionListener() {
		            public void actionPerformed(java.awt.event.ActionEvent evt) {
		                jButtonAdmCentresActionPerformed();
		            }
		        });
		        jButtonAdmCentre.addMouseListener(new java.awt.event.MouseAdapter() {
		            public void mouseEntered(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseEntered(evt);
		            }
		            public void mouseExited(java.awt.event.MouseEvent evt) {
		                PartyPanel.this.mouseExited(evt);
		            }
		        });
		        this.add(jButtonAdmCentre);
		        jButtonAdmCentre.setBounds(300, 260, 30, 30);
		        
		        canvas1.setFont(Constants.TITLE_FONT);
		        canvas1.setForeground(Constants.FONT_COLOR);
		        canvas1.setBounds(0, 0, 250, 30);
		        canvasPanel.setBounds(40, 260, 250, 30);
		        canvasPanel.add(canvas1);
		        this.add(canvasPanel);
		}     
	        
	/** Save  Button Action Performed*/
    private void jButtonSaveActionPerformed() {
        Thread th = new Thread(new Runnable() { public void run() {
            jButtonSave.setEnabled(false);
            enableFACeButton(false);
            
            String validate = formValidation();
            if (validate.equals("")){
                es.mityc.appfacturae.facturae.BusinessType b = new es.mityc.appfacturae.facturae.BusinessType();
                if(partyModified) {
                    Object ob = jComboBoxItems.getSelectedItem();
                    int id = -1;
                    if(ob != null) {
	                    if (jRadioButtonLEn.isSelected())
	                     	id = Integer.parseInt(((LegalEntityRow)ob).attributes[0].toString());
	                    else
	                       	id = Integer.parseInt(((IndividualRow)ob).attributes[0].toString());
                    }
                    if(id > -1) {
	                	SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT * FROM PARTY WHERE PARTY_ID=" + id);
	        			List<?> ls = s.list();
	        			if(ls != null && ls.size() > 0 && ls.get(0) != null) {
	        				Object []datas = (Object [])ls.get(0);
	        				try {
		        				b.setId(((Integer)datas[0]).intValue());
		        				b.setPartyIdentification((String)datas[1]);
	        				} catch(ClassCastException e) {
	        					// Ignoramos el error y creamos un nuevo tercero
	        				}
	        			}
                    }
                } 
                String partyIdentification = "";
                es.mityc.appfacturae.facturae.TaxIdentificationType taxIdentification = new es.mityc.appfacturae.facturae.TaxIdentificationType();
                taxIdentification.setResidenceTypeCode(es.mityc.appfacturae.facturae.ResidenceTypeCodeType.values()[jComboBoxResidenceType.getSelectedIndex()]);
                if (jRadioButtonLEn.isSelected()){
                    partyIdentification = jTextField[0].getText();
                    taxIdentification.setPersonTypeCode(es.mityc.appfacturae.facturae.PersonTypeCodeType.J);
                    taxIdentification.setTaxIdentificationNumber(jTextField[1].getText());
                    es.mityc.appfacturae.facturae.LegalEntityType legalEntity = new es.mityc.appfacturae.facturae.LegalEntityType();
                    if(partyModified) {
                    	legalEntity.setId(Integer.parseInt(partyIdentification));
                    } else {
                    	legalEntity.setId(0); // -- Indicar que se trata de una nueva entidad legal.
                    }
                    legalEntity.setCorporateName(jTextField[2].getText());
                    if (jTextField[3].getText() != null && !jTextField[3].getText().trim().equals(""))
                        legalEntity.setTradeName(jTextField[3].getText());
                    if (jComboBoxLEnCountry.getSelectedItem() != null && Integer.parseInt(((ComboOption)jComboBoxLEnCountry.getSelectedItem()).getValue()) == spainCode){
                    	es.mityc.appfacturae.facturae.AddressType addressInSpain = null;
                		addressInSpain = new es.mityc.appfacturae.facturae32.AddressType();
                		((es.mityc.appfacturae.facturae32.AddressType)addressInSpain).setCountryCode(es.mityc.appfacturae.facturae.CountryType.ESP);
                    	addressInSpain.setAddress(jTextField[4].getText());
                        addressInSpain.setPostCode(jTextField[5].getText());
                        addressInSpain.setTown(jTextField[6].getText());
                        addressInSpain.setProvince(jTextField[7].getText());
                        legalEntity.setAddressInSpain(addressInSpain);
                    }
                    else{
                        es.mityc.appfacturae.facturae.OverseasAddressType overseasAddress = null;
                		overseasAddress = new es.mityc.appfacturae.facturae32.OverseasAddressType();
                		((es.mityc.appfacturae.facturae32.OverseasAddressType)overseasAddress).setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer.parseInt(((ComboOption)jComboBoxLEnCountry.getSelectedItem()).getValue())]);
                        overseasAddress.setAddress(jTextField[4].getText());
                        jTextField[6].setText((jTextField[5].getText()+" "+jTextField[6].getText()).trim());
                        overseasAddress.setPostCodeAndTown(jTextField[6].getText());
                        overseasAddress.setProvince(jTextField[7].getText());
                        legalEntity.setOverseasAddress(overseasAddress);
                    }
                    es.mityc.appfacturae.facturae.RegistrationDataType registrationData = new es.mityc.appfacturae.facturae.RegistrationDataType();
                    if (!jTextField[9].getText().trim().equals(""))
                        registrationData.setBook(jTextField[9].getText());
                    if (!jTextField[12].getText().trim().equals(""))
                        registrationData.setRegisterOfCompaniesLocation(jTextField[12].getText());
                    if (!jTextField[10].getText().trim().equals(""))
                        registrationData.setSheet(jTextField[10].getText());
                    if (!jTextField[13].getText().trim().equals(""))
                        registrationData.setFolio(jTextField[13].getText());
                    if (!jTextField[11].getText().trim().equals(""))
                        registrationData.setSection(jTextField[11].getText());
                    if (!jTextField[14].getText().trim().equals(""))
                        registrationData.setVolume(jTextField[14].getText());
                    if (!jTextField[15].getText().trim().equals(""))
                        registrationData.setAdditionalRegistrationData(jTextField[15].getText());
                    if (registrationData.getBook() != null || registrationData.getRegisterOfCompaniesLocation() != null ||  registrationData.getSheet() != null || registrationData.getFolio() != null || registrationData.getSection() != null || registrationData.getVolume() != null ||  registrationData.getAdditionalRegistrationData() != null)
                        legalEntity.setRegistrationData(registrationData);

                    es.mityc.appfacturae.facturae.ContactDetailsType contactDetails = new es.mityc.appfacturae.facturae.ContactDetailsType();
                    if (!jTextField[16].getText().trim().equals(""))
                        contactDetails.setTelephone(jTextField[16].getText());
                    if (!jTextField[20].getText().trim().equals(""))
                        contactDetails.setTeleFax(jTextField[20].getText());
                    if (!jTextField[17].getText().trim().equals(""))
                        contactDetails.setWebAddress(jTextField[17].getText());
                    if (!jTextField[21].getText().trim().equals(""))
                        contactDetails.setElectronicMail(jTextField[21].getText());
                    if (!jTextField[18].getText().trim().equals(""))
                        contactDetails.setContactPersons(jTextField[18].getText());
                    if (!jTextField[19].getText().trim().equals(""))
                        contactDetails.setCnoCnae(jTextField[19].getText());
                    if (!jTextField[22].getText().trim().equals(""))
                        contactDetails.setIneTownCode(jTextField[22].getText());
                    if (!jTextField[23].getText().trim().equals(""))
                        contactDetails.setAdditionalContactDetails(jTextField[23].getText());
                    if (contactDetails.getTelephone() != null || contactDetails.getTeleFax() != null || contactDetails.getWebAddress() != null ||  contactDetails.getElectronicMail() != null ||  contactDetails.getContactPersons() != null || contactDetails.getCnoCnae() != null || contactDetails.getIneTownCode() != null || contactDetails.getAdditionalContactDetails() != null)
                        legalEntity.setContactDetails(contactDetails);

                    //Guardar datos FACe
                    legalEntity.setFace(isFACe);

                    b.setLegalEntity(legalEntity);
                }
                else{
                    partyIdentification = jTextField[24].getText();
                    taxIdentification.setPersonTypeCode(es.mityc.appfacturae.facturae.PersonTypeCodeType.F);
                    taxIdentification.setTaxIdentificationNumber(jTextField[25].getText());
                    es.mityc.appfacturae.facturae.IndividualType individual = new es.mityc.appfacturae.facturae.IndividualType();
                    if(partyModified) {
                    	individual.setId(Integer.parseInt(partyIdentification));
                    } else {
                    	individual.setId(0);
                    }
                    individual.setName(jTextField[26].getText());
                    individual.setFirstSurname(jTextField[27].getText());
                    if (jTextField[28].getText() != null && !jTextField[28].getText().trim().equals(""))
                        individual.setSecondSurname(jTextField[28].getText());
                    if (jComboBoxIndCountry.getSelectedItem() != null && Integer.parseInt(((ComboOption)jComboBoxIndCountry.getSelectedItem()).getValue()) == spainCode){
                    	es.mityc.appfacturae.facturae.AddressType addressInSpain = null;
                		addressInSpain = new es.mityc.appfacturae.facturae32.AddressType();
                		((es.mityc.appfacturae.facturae32.AddressType)addressInSpain).setCountryCode(es.mityc.appfacturae.facturae.CountryType.ESP);
                        addressInSpain.setAddress(jTextField[29].getText());
                        addressInSpain.setPostCode(jTextField[30].getText());
                        addressInSpain.setTown(jTextField[31].getText());
                        addressInSpain.setProvince(jTextField[32].getText());
                        individual.setAddressInSpain(addressInSpain);
                    }
                    else{
                    	es.mityc.appfacturae.facturae.OverseasAddressType overseasAddress = null;
                		overseasAddress = new es.mityc.appfacturae.facturae32.OverseasAddressType();
                		((es.mityc.appfacturae.facturae32.OverseasAddressType)overseasAddress).setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer.parseInt(((ComboOption)jComboBoxIndCountry.getSelectedItem()).getValue())]);
                        overseasAddress.setAddress(jTextField[29].getText());
                        jTextField[31].setText((jTextField[30].getText()+" "+jTextField[31].getText()).trim());
                        overseasAddress.setPostCodeAndTown(jTextField[31].getText());
                        overseasAddress.setProvince(jTextField[32].getText());
                        individual.setOverseasAddress(overseasAddress);
                    }
                    es.mityc.appfacturae.facturae.ContactDetailsType contactDetails = new es.mityc.appfacturae.facturae.ContactDetailsType();
                    if (!jTextField[34].getText().trim().equals(""))
                        contactDetails.setTelephone(jTextField[34].getText());
                    if (!jTextField[38].getText().trim().equals(""))
                        contactDetails.setTeleFax(jTextField[38].getText());
                    if (!jTextField[35].getText().trim().equals(""))
                        contactDetails.setWebAddress(jTextField[35].getText());
                    if (!jTextField[39].getText().trim().equals(""))
                        contactDetails.setElectronicMail(jTextField[39].getText());
                    if (!jTextField[36].getText().trim().equals(""))
                        contactDetails.setContactPersons(jTextField[36].getText());
                    if (!jTextField[37].getText().trim().equals(""))
                        contactDetails.setCnoCnae(jTextField[37].getText());
                    if (!jTextField[40].getText().trim().equals(""))
                        contactDetails.setIneTownCode(jTextField[40].getText());
                    if (!jTextField[41].getText().trim().equals(""))
                        contactDetails.setAdditionalContactDetails(jTextField[41].getText());
                    if (contactDetails.getTelephone() != null || contactDetails.getTeleFax() != null || contactDetails.getWebAddress() != null ||  contactDetails.getElectronicMail() != null ||  contactDetails.getContactPersons() != null || contactDetails.getCnoCnae() != null || contactDetails.getIneTownCode() != null || contactDetails.getAdditionalContactDetails() != null)
                        individual.setContactDetails(contactDetails);

                    b.setIndividual(individual);
                }
               
                b.setTaxIdentification(taxIdentification);
                
                b.setAdministrativeCentres(centres);
                
                // SAVE
                int result = -1;
                try{
                	result = FacturaeManager.getInstance().saveParty(b, version/*, partyModified*/);
                	
                	MainWindow.getInstance().loadData();
                    
                	if (!jRadioButtonLEn.isSelected()){
                		if (result > -1){
                			if(partyModified) {
                				IndividualRow sir = (IndividualRow)((CoupledComboModel)cbmInd).getSelectedItem();
                				((CoupledComboModel)cbmInd).removeElement(sir);
                			}
                			IndividualRow ir = new IndividualRow(b, version);
                			((CoupledComboModel)cbmInd).addElement(ir);
                			jComboBoxItems.getModel().setSelectedItem(ir);
                		}
                	}
                	else{
                		if (result > -1){
                			if(partyModified) {
                				LegalEntityRow sler = (LegalEntityRow)((CoupledComboModel)cbmLEn).getSelectedItem();
                				((CoupledComboModel)cbmLEn).removeElement(sler);
                			}
                			LegalEntityRow ler = new LegalEntityRow(b, version);
                			((CoupledComboModel)cbmLEn).addElement(ler);
                			jComboBoxItems.getModel().setSelectedItem(ler);
                		}
                	}
                	try{
                		FacturaeManager.getInstance().saveAction(EnumOperationType.NewParty, null);
                	} catch (DatabaseOperationException e) {
                		// The user is not informed about action not saved event. An error log has been created previously.
                	}
                }
                catch(DatabaseOperationException e){
                	logger.error ("An error has been produced during party save");
                	canvas1.showMessage(Constants.LANG.getString(e.getMultiKey()), Constants.ERROR_MSG_COLOR);
                    jButtonSave.setEnabled(true);
                    enableFACeButton(true);

                }
                
                // SHOW RESULTS
                if (result == 0){
                    for (int i = 0 ; i < jTextField.length ; i++){
                        jTextField[i].setEditable(false);
                        jTextField[i].setBackground(Constants.BKG_MAIN_COLOR);
                    }
                    disabled = true;
                    jComboBoxResidenceType.setBackground(Constants.BKG_MAIN_COLOR);
                    jComboBoxLEnCountry.setBackground(Constants.BKG_MAIN_COLOR);
                    jComboBoxIndCountry.setBackground(Constants.BKG_MAIN_COLOR);
                    canvas1.showMessage(Constants.LANG.getString("OKMessageSave"), Constants.OK_MSG_COLOR);
                    jComboBoxItems.setSelectedIndex(jComboBoxItems.getSelectedIndex());
                }
                else if (result > 0 && partyModified){
                    canvas1.showMessage(Constants.LANG.getString("OKMessageSaveExist"), Constants.OK_MSG_COLOR);
                    jComboBoxItems.setSelectedIndex(jComboBoxItems.getSelectedIndex());
                }
            }
            else{
                canvas1.showMessage(Constants.LANG.getString("NOOKIncompleteForm"), Constants.ERROR_MSG_COLOR);
                jButtonSave.setEnabled(true);
                enableFACeButton(true);

            }

        }

        });

        th.start();
    }

    /** Individual or Legal Entity */
    public void jRadioButtonGroupActionPerformed() {
        if (jRadioButtonLEn.isSelected()){
            jRadioButtonInd.setSelected(false);
            jTabbedPaneLEn.setVisible(true);
            jTabbedPaneInd.setVisible(false);
            jComboBoxItems.setModel(cbmLEn);
        }
        else{
            jRadioButtonInd.setSelected(true);
            jTabbedPaneLEn.setVisible(false);
            jTabbedPaneInd.setVisible(true);
            jComboBoxItems.setModel(cbmInd);
        }
        jComboBoxItems.setSelectedIndex(-1);
        jComboBoxResidenceType.setSelectedIndex(-1);
        jComboBoxLEnCountry.setSelectedIndex(-1);
        jComboBoxIndCountry.setSelectedIndex(-1);
        for (int i = 0 ; i < jTextField.length ; i++)
            jTextField[i].setText("");
    }

    /** Item selected*/
    private void jComboBoxItemsActionPerformed(java.awt.event.ActionEvent evt) {
    	((JComboBox)evt.getSource()).setBackground(Color.WHITE);
    	String parametersCD = "CONTACT_ID,TELEPHONE,FAX,WEB,EMAIL,CONTACT_PERSON,CNO_CNAE,INE_TOWN_CODE,ADDITIONAL_DATA";
        String parametersRD = "REGISTRATION_ID,BOOK,LOCATION,SHEET,FOLIO,SECTION,VOLUME,REGISTRATION_DATA.ADDITIONAL_DATA";
        String sCentres = "";
        // Legal Entity
        if (jRadioButtonLEn.isSelected() && jComboBoxItems.getSelectedIndex() > -1){
        	jButtonSave.setEnabled(false);
            enableFACeButton(false);

            for (int i = 0 ; i < jTextField.length ; i++){
                    jTextField[i].setEditable(false);
                    jTextField[i].setBackground(Constants.BKG_MAIN_COLOR);
                    if (jComboBoxItems.getSelectedIndex() > -1)	
                    	jTextField[i].setText("");
            }
            disabled = true;
            // ROW PARAMETERS => ID,CORPORATE_NAME,TRADE_NAME,TYPE_VERSION,ADDRESS.ADDRESS,POST_CODE,TOWN,PROVINCE,COUNTRY,TAX_IDENTIFICATION,RESIDENCE_TYPE,CONTACT,REGISTRATION_DATA,CONTACT,REGISTRATION_DATA
            LegalEntityRow rowLeg = (LegalEntityRow)((CoupledComboModel)cbmLEn).getElementAt(jComboBoxItems.getSelectedIndex());
            for (int i = 0 ; i < rowLeg.attributes.length ; i++){
                if (jComboBoxItems.getSelectedIndex() == -1 || rowLeg.attributes[i] == null)
                    rowLeg.attributes[i] = "";
            }
            
            isFACe = Boolean.parseBoolean(rowLeg.attributes[13].toString());
	        jTabbedPaneLEn.setEnabledAt(FACE_TAB, isFACe);
            
            jTextField[0].setText(rowLeg.attributes[0].toString());
            jTextField[1].setText(rowLeg.attributes[9].toString());
            jTextField[2].setText(rowLeg.attributes[1].toString());
            if (rowLeg.attributes[2] != null)
                jTextField[3].setText(rowLeg.attributes[2].toString());

            jTextField[4].setText(rowLeg.attributes[4].toString());
            jTextField[5].setText(rowLeg.attributes[5].toString());
            jTextField[6].setText(rowLeg.attributes[6].toString());
            jTextField[7].setText(rowLeg.attributes[7].toString());
            jComboBoxLEnCountry.setSelectedIndex(ComboUtil.calculateComboIndex(countryOptions, rowLeg.attributes[8].toString()));
            jComboBoxLEnCountry.setBackground(Constants.BKG_MAIN_COLOR);

            try{
                SQLQuery s = null;
                if (!rowLeg.attributes[11].equals("")){
                    s = FacturaeManager.getInstance().executeQuery("SELECT "+parametersCD+" FROM CONTACT_DETAILS WHERE CONTACT_ID ="+ rowLeg.attributes[11].toString());
                    List<?> ls = s.list();
                    Object[] cd = (Object[])ls.get(0);
                    // ROW PARAMETERS => CONTACT_ID,TELEPHONE,FAX,WEB,EMAIL,CONTACT_PERSON,CNO_CNAE,INE_TOWN_CODE,ADDITIONAL_DATA
                    if (cd[1] != null)
                        jTextField[16].setText(cd[1].toString());
                    if (cd[3] != null)
                        jTextField[17].setText(cd[3].toString());
                    if (cd[5] != null)
                        jTextField[18].setText(cd[5].toString());
                    if (cd[6] != null)
                        jTextField[19].setText(cd[6].toString());
                    if (cd[2] != null)
                        jTextField[20].setText(cd[2].toString());
                    if (cd[4] != null)
                        jTextField[21].setText(cd[4].toString());
                    if (cd[7] != null)
                        jTextField[22].setText(cd[7].toString());
                    if (cd[8] != null)
                        jTextField[23].setText(cd[8].toString());
                }

                if (!rowLeg.attributes[12].equals("")){
                    s = FacturaeManager.getInstance().executeQuery("SELECT "+parametersRD+" FROM REGISTRATION_DATA WHERE REGISTRATION_ID ="+rowLeg.attributes[12].toString());
                    List<?> ls = s.list();
                    Object[] rd = (Object[])ls.get(0);
                    // ROW PARAMETERS => REGISTRATION_ID,BOOK,LOCATION,SHEET,FOLIO,SECTION,VOLUME,REGISTRATION_DATA.ADDITIONAL_DATA
                    if (rd[1] != null)
                        jTextField[9].setText(rd[1].toString());
                    if (rd[3] != null)
                        jTextField[10].setText(rd[3].toString());
                    if (rd[5] != null)
                        jTextField[11].setText(rd[5].toString());
                    if (rd[2] != null)
                        jTextField[12].setText(rd[2].toString());
                    if (rd[4] != null)
                        jTextField[13].setText(rd[4].toString());
                    if (rd[6] != null)
                        jTextField[14].setText(rd[6].toString());
                    if (rd[7] != null)
                        jTextField[15].setText(rd[7].toString());
                }

            }catch(Exception e){
                logger.error("An error occurred when loading the database information: " + e.getMessage());
                canvas1.showMessage(Constants.LANG.getString("NOOKDataBaseLoading"), Constants.ERROR_MSG_COLOR);
            }
            
            if(rowLeg.attributes[10].toString().equalsIgnoreCase(ResidenceTypeCodeType.E.toString())) {
            	jComboBoxResidenceType.setSelectedIndex(0);
            } else if(rowLeg.attributes[10].toString().equalsIgnoreCase(ResidenceTypeCodeType.R.toString())) {
            	jComboBoxResidenceType.setSelectedIndex(1);
            } else if(rowLeg.attributes[10].toString().equalsIgnoreCase(ResidenceTypeCodeType.U.toString())) {
            	jComboBoxResidenceType.setSelectedIndex(2);
            } else {
            	jComboBoxResidenceType.setSelectedIndex(Integer.parseInt(rowLeg.attributes[10].toString()));
            }
            jComboBoxResidenceType.setBackground(Constants.BKG_MAIN_COLOR);
            
            sCentres = "SELECT ADMCENTRE_ID FROM PARTY_ADMCENTRE WHERE PARTY_ID =" + jTextField[0].getText();
        }
        // Individual
        else if (jRadioButtonInd.isSelected() && jComboBoxItems.getSelectedIndex() > -1){
        	isFACe = false;
            // ROW PARAMETERS => ID,NAME,FIRST_SURNAME,SECOND_SURNAME,TYPE_VERSION,ADDRESS.ADDRESS,POST_CODE,TOWN,PROVINCE,COUNTRY,TAX_IDENTIFICATION,RESIDENCE_TYPE,CONTACT
            IndividualRow rowInd = (IndividualRow)((ComboBoxModel)cbmInd).getElementAt(jComboBoxItems.getSelectedIndex());
            for (int i = 0 ; i < rowInd.attributes.length ; i++){
                if (jComboBoxItems.getSelectedIndex() == -1 || rowInd.attributes[i] == null)
                    rowInd.attributes[i] = "";
            }
            jTextField[24].setText(rowInd.attributes[0].toString());
            jTextField[25].setText(rowInd.attributes[10].toString());
            jTextField[26].setText(rowInd.attributes[1].toString());
            jTextField[27].setText(rowInd.attributes[2].toString());
            if (rowInd.attributes[3] != null)
                jTextField[28].setText(rowInd.attributes[3].toString());

            jTextField[29].setText(rowInd.attributes[5].toString());
            jTextField[30].setText(rowInd.attributes[6].toString());
            jTextField[31].setText(rowInd.attributes[7].toString());
            jTextField[32].setText(rowInd.attributes[8].toString());
            jComboBoxIndCountry.setSelectedIndex(ComboUtil.calculateComboIndex(countryOptions, rowInd.attributes[9].toString()));
            jComboBoxIndCountry.setBackground(Constants.BKG_MAIN_COLOR);

            try{
                SQLQuery s = null;
                if (!rowInd.attributes[12].equals("")){
                    s = FacturaeManager.getInstance().executeQuery("SELECT "+parametersCD+" FROM CONTACT_DETAILS WHERE CONTACT_ID ="+rowInd.attributes[12].toString());
                    List<?> ls = s.list();
                    Object[] cd = (Object[])ls.get(0);
                    // ROW PARAMETERS => CONTACT_ID,TELEPHONE,FAX,WEB,EMAIL,CONTACT_PERSON,CNO_CNAE,INE_TOWN_CODE,ADDITIONAL_DATA
                    if (cd[1] != null)
                        jTextField[34].setText(cd[1].toString());
                    if (cd[3] != null)
                        jTextField[35].setText(cd[3].toString());
                    if (cd[5] != null)
                        jTextField[36].setText(cd[5].toString());
                    if (cd[6] != null)
                        jTextField[37].setText(cd[6].toString());
                    if (cd[2] != null)
                        jTextField[38].setText(cd[2].toString());
                    if (cd[4] != null)
                        jTextField[39].setText(cd[4].toString());
                    if (cd[7] != null)
                        jTextField[40].setText(cd[7].toString());
                    if (cd[8] != null)
                        jTextField[41].setText(cd[8].toString());
                }
            }catch(Exception e){
                logger.error("An error occurred when loading the database information: " + e.getMessage());
                canvas1.showMessage(Constants.LANG.getString("NOOKDataBaseLoading"), Constants.ERROR_MSG_COLOR);
            }
            if(rowInd.attributes[11].toString().equalsIgnoreCase(ResidenceTypeCodeType.E.toString())) {
            	jComboBoxResidenceType.setSelectedIndex(0);
            } else if(rowInd.attributes[11].toString().equalsIgnoreCase(ResidenceTypeCodeType.R.toString())) {
            	jComboBoxResidenceType.setSelectedIndex(1);
            } else if(rowInd.attributes[11].toString().equalsIgnoreCase(ResidenceTypeCodeType.U.toString())) {
            	jComboBoxResidenceType.setSelectedIndex(2);
            } else {
            	jComboBoxResidenceType.setSelectedIndex(Integer.parseInt(rowInd.attributes[11].toString()));
            }
            jComboBoxResidenceType.setBackground(Constants.BKG_MAIN_COLOR);
            sCentres = "SELECT ADMCENTRE_ID FROM PARTY_ADMCENTRE WHERE PARTY_ID =" + jTextField[24].getText();
        }
        
        centres = null;
        
        if (!sCentres.equals("")){
	        SQLQuery sqlCentres = FacturaeManager.getInstance().executeQuery(sCentres);
	        List<?> lsCentres = sqlCentres.list();
	        AdministrativeCentreType act;
	        for (int i = 0 ; i < lsCentres.size() ; i++){
	        	
	        	String columnsAdmCentreQuery = "CENTRE_CODE,ROLE_TYPE_CODE,NAME,FIRST_SURNAME,SECOND_SURNAME,PHYSICAL_GLN,LOGICAL_OPERATION_POINT,";
	        	String AdmCentreQuery = "";
	        	String centreDescriptionQuery = "";
	        	
				if (version.equals(Constants.VERSION321)) {
					act = new es.mityc.appfacturae.facturae321.AdministrativeCentreType();
					centreDescriptionQuery = ",CENTRE_DESCRIPTION";
				} else if (version.equals(Constants.VERSION32)){
	        		act = new es.mityc.appfacturae.facturae32.AdministrativeCentreType();
	        		centreDescriptionQuery = ",CENTRE_DESCRIPTION";
	        	}
	        	
	        	SQLQuery centresDataPreinfo = FacturaeManager.getInstance().executeQuery("SELECT ADDRESS,CONTACT FROM ADMCENTRE WHERE ADMCENTRE_ID ="+ lsCentres.get(i).toString());
	        	List<?> lsCentresDataPreinfo = centresDataPreinfo.list();
	        	if (lsCentresDataPreinfo != null && lsCentresDataPreinfo.size() > 0){
	        		if (((Object[])lsCentresDataPreinfo.get(0))[0] != null && ((Object[])lsCentresDataPreinfo.get(0))[1] != null){
	        			AdmCentreQuery = "SELECT "+columnsAdmCentreQuery+" ADDRESS.ADDRESS,POST_CODE,TOWN,PROVINCE,COUNTRY,TELEPHONE,WEB,CONTACT_PERSON,CNO_CNAE,FAX,EMAIL,INE_TOWN_CODE,ADDITIONAL_DATA"+centreDescriptionQuery+" FROM ADMCENTRE, ADDRESS, CONTACT_DETAILS WHERE ADMCENTRE.ADDRESS = ADDRESS.ADDRESS_ID AND ADMCENTRE.CONTACT = CONTACT_DETAILS.CONTACT_ID AND ADMCENTRE_ID ="+ lsCentres.get(i).toString();
		        	}
		        	else{
		        		if (((Object[])lsCentresDataPreinfo.get(0))[1] == null)
		        			AdmCentreQuery = "SELECT "+columnsAdmCentreQuery+" ADDRESS.ADDRESS,POST_CODE,TOWN,PROVINCE,COUNTRY,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL"+centreDescriptionQuery+" FROM ADMCENTRE, ADDRESS WHERE ADMCENTRE.ADDRESS = ADDRESS.ADDRESS_ID AND ADMCENTRE.CONTACT IS NULL AND ADMCENTRE_ID ="+ lsCentres.get(i).toString();
		        		else // It shouldn't be possible because the address is required
		        			AdmCentreQuery = "SELECT "+columnsAdmCentreQuery+" NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL"+centreDescriptionQuery+" FROM ADMCENTRE WHERE ADMCENTRE.ADDRESS IS NULL AND ADMCENTRE.CONTACT IS NULL AND ADMCENTRE_ID ="+ lsCentres.get(i).toString();
		        	}
		        	
		        	SQLQuery centresData = FacturaeManager.getInstance().executeQuery(AdmCentreQuery);
		        	
		        	List<?> lsCentresData = centresData.list();
		        	if (lsCentresData != null && lsCentresData.size() > 0){
		        		Object[] ob = (Object[])lsCentresData.get(0);
		        		String[] st = new String[ob.length];
		        		for (int j = 0 ; j < ob.length ; j++){
		        			if (ob[j] != null)
		        				st[j] = ob[j].toString();
		        			else
		        				st[j] = "";
		        		}

		        		act = AdmCentreUtil.makeCentre(st, spainCode, version);
		        		if (centres == null)
		        			centres = new AdministrativeCentresType();
		        		centres.getAdministrativeCentre().add(act);
		                if(isFACe) {
		                	if (act.getRoleTypeCode().equals(Constants.FACE_ROLE_MANAGEMENTAGENCY)) {
		                		jTextField[42].setText(act.getName());
		                	} else if (act.getRoleTypeCode().equals(Constants.FACE_ROLE_PROCESSINGAUTHORITYUNIT)) {
		                		jTextField[43].setText(act.getName());
		                	} else if (act.getRoleTypeCode().equals(Constants.FACE_ROLE_ACCOUNTINGOFFICE)) {
		                		jTextField[44].setText(act.getName());
		                	} else if (act.getRoleTypeCode().equals(Constants.FACE_ROLE_PROPOSINGAGENCY)) {
		                		jTextField[45].setText(act.getName());
		                	}
		                }
		        	}
	        	}
	        }
        }
        
        jComboBoxItems.repaint();
        
        jComboBoxItems.setSelectedIndex(jComboBoxItems.getSelectedIndex());
    }

    
    /** Edit Party*/
    private void jButtonEditActionPerformed() {
        Object ob = jComboBoxItems.getSelectedItem();
        int id = -1;
        int maxId = -1;
        if (jRadioButtonLEn.isSelected())
         	id = Integer.parseInt(((LegalEntityRow)ob).attributes[0].toString());
        else
           	id = Integer.parseInt(((IndividualRow)ob).attributes[0].toString());
        boolean hasInvoices = true;
        if(id > 0) {
			SQLQuery s2 = FacturaeManager.getInstance().executeQuery("SELECT FACTURAE_ID FROM FACTURAE WHERE PARTY_ID_SELLER=" + id + " OR PARTY_ID_BUYER=" + id);
			try {
				List<?> ls2 = s2.list();
				hasInvoices = ls2.size() > 0;
			} catch(Exception e) {
				logger.error("Ocurrio un error al comprobar si el tercero tiene facturas asociadas: " + e.getMessage());
			}
        }
		if (hasInvoices) { // ¿Tiene facturas?
			// Se debe crear un nuevo tercero (emisor / receptor)
			try{
				SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT MAX(PARTY_ID) FROM PARTY");
				List<?> ls = s.list();
				if (ls != null && ls.size() > 0 && ls.get(0) != null)
					maxId = (Integer) ls.get(0);
				else
					maxId = 0;
				maxId++; // Incremento el contador para crear el nuevo tercero
				canvas1.showMessage(Constants.LANG.getString("NOOKMessageEditUsed"), Constants.WARN_MSG_COLOR);
			}catch(Exception e){
				logger.error("An error occurred when loading the database information: " + e.getMessage());
				canvas1.showMessage(Constants.LANG.getString("NOOKDataBaseLoading"), Constants.ERROR_MSG_COLOR);
			}
		} else { // El tercero no tiene facturas, lo puedo modificar.
			maxId = id;
		}
		partyModified  = maxId == id;

        for (int i = 0 ; i < jTextField.length ; i++){
    		jTextField[i].setEditable(true);
            jTextField[i].setBackground(java.awt.Color.white);
        }
        disabled = false;
        jComboBoxResidenceType.setBackground(java.awt.Color.white);
        jComboBoxLEnCountry.setBackground(java.awt.Color.white);
        jComboBoxIndCountry.setBackground(java.awt.Color.white);

    	//IF isFACe then enable FACe TAB
        //ELSE disable FACe TAB 	
        jTabbedPaneLEn.setEnabledAt(FACE_TAB, isFACe);
        enableFACeButton(isFACe);
        
        jButtonSave.setEnabled(true);
        if (jRadioButtonLEn.isSelected()){
            jTextField[0].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[0].setEditable(false);
            jTextField[0].setText(String.valueOf(maxId));
            jTextField[1].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[1].setEditable(false);
            jTextField[2].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[2].setEditable(false);
            jTextField[42].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[42].setEditable(false);
            jTextField[43].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[43].setEditable(false);
            jTextField[44].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[44].setEditable(false);
            jTextField[45].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[45].setEditable(false);
        }
        else{
            jTextField[24].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[24].setEditable(false);
            jTextField[24].setText(String.valueOf(maxId));
            jTextField[25].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[25].setEditable(false);
            jTextField[26].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[26].setEditable(false);
            jTextField[27].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[27].setEditable(false);
        }
    }
    
    /** New Party*/
    private void jButtonNewUserActionPerformed() {
    	//Preguntamos si es un receptor FACe
//    	isFACe = false;
//    	if(this.version.equals(Constants.VERSION32) && this.role.equals(Constants.LANG.getString("Receiver"))) {
//        	//popup is FACe
//    		if(FACeReceiverDialog.showFACeReceiverDialog((JFrame)((GenerateInvoiceWindow)this.getTopLevelAncestor())) == JOptionPane.YES_OPTION) {
//    			isFACe = true;
//    			/*new Thread(){
//    				public void run(){
//    					FACeUtils.getInstance().getManagementAgencys();
//    				}
//    			}.start();*/
//    		} else {
//    			isFACe = false;
//    		}
//    	}
    	
    	isFACe = ((this.version.equals(Constants.VERSION32)||this.version.equals(Constants.VERSION321)) && this.role.equals(Constants.LANG.getString("Receiver")));
    	
        int maxId = -1;
        jComboBoxItems.setSelectedIndex(-1);
        try{
            SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT MAX(PARTY_ID) FROM PARTY");
            List<?> ls = s.list();
            if (ls != null && ls.size() > 0 && ls.get(0) != null)
            	maxId = (Integer) ls.get(0);
            else
            	maxId = 0;
        }catch(Exception e){
        	logger.error("An error occurred when loading the database information: " + e.getMessage());
            canvas1.showMessage(Constants.LANG.getString("NOOKDataBaseLoading"), Constants.ERROR_MSG_COLOR);
        }
        for (int i = 0 ; i < jTextField.length ; i++){
            jTextField[i].setEditable(true);
            jTextField[i].setBackground(java.awt.Color.white);
            jTextField[i].setText("");
        }
        disabled = false;
        centres = null;
        jComboBoxResidenceType.setBackground(java.awt.Color.white);
        jComboBoxLEnCountry.setBackground(java.awt.Color.white);
        jComboBoxIndCountry.setBackground(java.awt.Color.white);
        jComboBoxResidenceType.setSelectedIndex(-1);
        jComboBoxLEnCountry.setSelectedIndex(-1);
        jComboBoxIndCountry.setSelectedIndex(-1);

        //IF isFACe then enable FACe TAB
        //ELSE disable FACe TAB 	
        jTabbedPaneLEn.setEnabledAt(FACE_TAB, isFACe);
        enableFACeButton(isFACe);	        

        jButtonSave.setEnabled(true);
        if (jRadioButtonLEn.isSelected()){
            jTextField[0].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[0].setEditable(false);
            jTextField[0].setText(String.valueOf(maxId+1));
            jTextField[42].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[42].setEditable(false);
            jTextField[43].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[43].setEditable(false);
            jTextField[44].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[44].setEditable(false);
            jTextField[45].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[45].setEditable(false);
          }
        else{
            jTextField[24].setBackground(Constants.BKG_MAIN_COLOR);
            jTextField[24].setEditable(false);
            jTextField[24].setText(String.valueOf(maxId+1));
        }
    }

    /** Delete User*/
    private void jButtonDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {
        Object ob = jComboBoxItems.getSelectedItem();
        int id = -1;
        if (jRadioButtonLEn.isSelected())
         	id = Integer.parseInt(((LegalEntityRow)ob).attributes[0].toString());
        else
           	id = Integer.parseInt(((IndividualRow)ob).attributes[0].toString());
        try{
            String result = FacturaeManager.getInstance().deleteParty(id, !jRadioButtonLEn.isSelected(), version);
            if (result.equals("")){
            	((CoupledComboModel)jComboBoxItems.getModel()).removeElement(ob);
                for (int i = 0 ; i < jTextField.length ; i++)
                    jTextField[i].setText("");

                jComboBoxLEnCountry.setSelectedIndex(-1);
                jComboBoxIndCountry.setSelectedIndex(-1);
                jComboBoxResidenceType.setSelectedIndex(-1);
                centres = null;
                canvas1.showMessage(Constants.LANG.getString("OKMessageDelete"), Constants.OK_MSG_COLOR);
            }
            else{
                canvas1.showMessage(Constants.LANG.getString(result), Constants.ERROR_MSG_COLOR);
            }
        }
        catch(Exception e){
            canvas1.showMessage(Constants.LANG.getString("NOOKMessageDelete"), Constants.ERROR_MSG_COLOR);
        }
        
    }

    /**  Validation*/
    private String formValidation(){
            String result = "";
            if (jComboBoxResidenceType.getSelectedIndex() < 0){
                    jComboBoxResidenceType.setBackground(Constants.BKG_ERROR_COLOR);
                    result = "NOOKIncompleteForm";
            }
            if (jRadioButtonLEn.isSelected()){
            		if (jTextField[0].getText().trim().equals("")){
            			jTextField[0].setBackground(Constants.BKG_ERROR_COLOR);
            			result = "NOOKIncompleteForm";
            		}
            		if (jTextField[1].getText().trim().equals("")){
                            jTextField[1].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                    }
                    if (jTextField[2].getText().trim().equals("")){
                            jTextField[2].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                    }
                    for (int i = 4 ; i < 8 ; i++){
                            if (jTextField[i].getText().trim().equals("")){
                            jTextField[i].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                            }
                    }
                    if (jComboBoxLEnCountry.getSelectedIndex() < 0){
                            jComboBoxLEnCountry.setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                    }
                    if(isFACe) {
                    //Tvalidar obligatorio FACe 42 y 43
                    	if (jTextField[42].getText().trim().equals("")){
                            jTextField[42].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                    	}
                    	if (jTextField[43].getText().trim().equals("")){
                            jTextField[43].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                    	}
                    	if (jTextField[44].getText().trim().equals("")){
                            jTextField[44].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                    	}
                    }
            }
            else{
            		if (jTextField[24].getText().trim().equals("")){
            			jTextField[24].setBackground(Constants.BKG_ERROR_COLOR);
            			result = "NOOKIncompleteForm";
            		}
                    for (int i = 25 ; i < 28 ; i++){
                            if (jTextField[i].getText().trim().equals("")){
                            jTextField[i].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                            }
                    }
                    for (int i = 29 ; i < 33 ; i++){
                            if (jTextField[i].getText().trim().equals("")){
                            jTextField[i].setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                            }
                    }
                    if (jComboBoxIndCountry.getSelectedIndex() < 0){
                            jComboBoxIndCountry.setBackground(Constants.BKG_ERROR_COLOR);
                            result = "NOOKIncompleteForm";
                    }
            }
            return result;
    }
    
	private void jLabelFACeLinkActionPerformed() {
		try {
			Desktop.getDesktop().browse(new URI(Constants.FACE_URL));
        } catch (Exception ex) {
		}
	}

	private void jButtonFACeAdmCentreActionPerformed(String roleType, JTextField afectedTextField, String admCentreType) {
		//Abrir popup con la pantalla de elección de organo gestor/unidad tramitadora
		AdministrativeCentreType selectedCentre = null;
		String  selectedDependent = null;
		if(roleType.equals(Constants.FACE_ROLE_PROCESSINGAUTHORITYUNIT)){
			if(centres != null) {
				for(AdministrativeCentreType currentCentre : centres.getAdministrativeCentre()){
					if(currentCentre.getRoleTypeCode().equals(Constants.FACE_ROLE_MANAGEMENTAGENCY)){
						selectedDependent = currentCentre.getCentreCode();
						break;
					}
				}
			}			
		} else if(roleType.equals(Constants.FACE_ROLE_ACCOUNTINGOFFICE)){
			if(centres != null) {
				String organo = "";
				String unidad = "";
				for(AdministrativeCentreType currentCentre : centres.getAdministrativeCentre()){
					if(currentCentre.getRoleTypeCode().equals(Constants.FACE_ROLE_MANAGEMENTAGENCY)){
						organo = currentCentre.getCentreCode();
					}
					if(currentCentre.getRoleTypeCode().equals(Constants.FACE_ROLE_PROCESSINGAUTHORITYUNIT)){
						unidad = currentCentre.getCentreCode();
					}
				}
				selectedDependent = organo + "#" + unidad;
			}			
		}
		if(centres != null) {
			for(AdministrativeCentreType currentCentre : centres.getAdministrativeCentre()){
				if(currentCentre.getRoleTypeCode().equals(roleType)){
					selectedCentre = currentCentre;
					break;
				}
			}
		}
		
		//popup(selectedCentre, type)
		InputFACeAdmCentreDialog dialog = new InputFACeAdmCentreDialog(
			(JFrame) ((GenerateInvoiceWindow) this.getTopLevelAncestor()), true, selectedCentre, disabled,
			this.version, roleType, selectedDependent, admCentreType, isSearch, administration);
		if (t != null) {
			t.removeTransitionPanel();
		}
		dialog.setVisible(true);
		if (!disabled){
			String[] values = dialog.getValues();
			if (!"".equals(values[7])){
				
				// *** Cambios adaptación a 3.2 ***
				
				AdministrativeCentreType act = AdmCentreUtil.makeCentre(values,spainCode,version);
				
				if (centres == null)
					centres = new AdministrativeCentresType();

				if(selectedCentre != null) {
					centres.getAdministrativeCentre().remove(selectedCentre);
					if(roleType.equals(Constants.FACE_ROLE_MANAGEMENTAGENCY)) {
						if(!selectedCentre.getCentreCode().equals(act.getCentreCode())){
							AdministrativeCentreType[] admCentres = new AdministrativeCentreType[0];
							admCentres = centres.getAdministrativeCentre().toArray(admCentres);
							for(int i = 0; i<admCentres.length; i++){
								AdministrativeCentreType currentCentre = admCentres[i];
								if(currentCentre.getRoleTypeCode().equals(Constants.FACE_ROLE_PROCESSINGAUTHORITYUNIT)){
									centres.getAdministrativeCentre().remove(currentCentre);
									jTextField[43].setText("");
									jTextField[44].setText("");
								}else if (currentCentre.getRoleTypeCode().equals(Constants.FACE_ROLE_ACCOUNTINGOFFICE)){
									centres.getAdministrativeCentre().remove(currentCentre);
									jTextField[44].setText("");
								}

							}
						}
					}
					else if(roleType.equals(Constants.FACE_ROLE_PROCESSINGAUTHORITYUNIT)) {
						if(!selectedCentre.getCentreCode().equals(act.getCentreCode())){
							AdministrativeCentreType[] admCentres = new AdministrativeCentreType[0];
							admCentres = centres.getAdministrativeCentre().toArray(admCentres);
							for(int i = 0; i<admCentres.length; i++){
								AdministrativeCentreType currentCentre = admCentres[i];
								if(currentCentre.getRoleTypeCode().equals(Constants.FACE_ROLE_ACCOUNTINGOFFICE)){
									centres.getAdministrativeCentre().remove(currentCentre);
									jTextField[44].setText("");
									break;
								}
							}
						}
					}

				}
    			centres.getAdministrativeCentre().add(act);
    			afectedTextField.setText(act.getName());
    			
			}
		}
		enableFACeButton(true);
	}

	private void enableFACeButton(boolean setEnable) {
		if(isFACe) {
			new Thread(){
				public void run(){
					PeFACUtils.getInstance().getManagementAgencys();
				}
			}.start();
        	jButtonLEnFACeManagementAgency.setEnabled(setEnable);
        	if(jTextField[42].getText().trim().equals("")) {
        		jButtonLEnFACeProcessingAuthorityUnit.setEnabled(false);
        	} else {
        		jButtonLEnFACeProcessingAuthorityUnit.setEnabled(setEnable);
        	}
        	if(jTextField[43].getText().trim().equals("")) {
        		jButtonLEnFACeAccountingOffice.setEnabled(false);
        	} else {
            	jButtonLEnFACeAccountingOffice.setEnabled(setEnable);
        	} 
        	jButtonLEnFACeProposingAgency.setEnabled(setEnable);
        }
	}
	
    /** When you type in a textField, it is white painted (after an error)*/
    private void keyTypedErrorTextField(java.awt.event.KeyEvent evt){
            ((JTextField)evt.getSource()).setBackground(java.awt.Color.white);	
    }

    /** When you click in a comboBox, it is white painted (after an error)*/
    private void actionPerformedErrorComboBox(java.awt.event.ActionEvent evt){
            ((JComboBox)evt.getSource()).setBackground(java.awt.Color.white);	
    }

    /** Action on mouse entered*/
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    /** Action on mouse exited*/
    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    public void addListener(IPartyPanelListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(IPartyPanelListener listener) {
		listeners.remove(listener);
	}
	
	public boolean tryDelete(Object object) {
		return jComboBoxItems.getModel().getSelectedItem() != object;
	}

	public void removeIds() {
		if (jButtonSave.isEnabled()){
			for (int i = 0 ; i < jTextField.length ; i++ )
				jTextField[i].setText("");
			jComboBoxIndCountry.setSelectedItem(null);
			jComboBoxLEnCountry.setSelectedItem(null);
			jComboBoxResidenceType.setSelectedItem(null);
		}
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
					canvas1.showMessage(Constants.LANG.getString(error), Constants.ERROR_MSG_COLOR);
				}
			}
		}
	}
	
	private void jButtonAdmCentresActionPerformed() {
		AdmCentresDialog acd = new AdmCentresDialog((JFrame)((GenerateInvoiceWindow)this.getTopLevelAncestor()), true, version, centres, disabled);
		acd.setVisible(true);
		centres = acd.getCentres();
	}
	
	public JComboBox getJComboBoxResidenceType() {
		return jComboBoxResidenceType;
	}

	public JRadioButton getJRadioButtonLEn() {
		return jRadioButtonLEn;
	}

	public JRadioButton getJRadioButtonInd() {
		return jRadioButtonInd;
	}

	public JComboBox getJComboBoxItems() {
		return jComboBoxItems;
	}

	public JComboBox getJComboBoxLEnCountry() {
		return jComboBoxLEnCountry;
	}

	public JComboBox getJComboBoxIndCountry() {
		return jComboBoxIndCountry;
	}

	public JTextField[] getJTextField() {
		return jTextField;
	}
	
	public JButton getJButtonSave() {
		return jButtonSave;
	}
	
	public JButton getJButtonNewUser() {
		return jButtonNewUser;
	}
	
	public JButton getJButtonEditUser() {
		return jButtonEditUser;
	}
	
	public JButton getJButtonDelUser() {
		return jButtonDeleteUser;
	}

	public JLabel getJLabelMain() {
		return jLabelMain;
	}

	public void setJLabelMain(JLabel labelMain) {
		jLabelMain = labelMain;
	}
	
	public AdministrativeCentresType getCentres(){
		return centres;
	}
	
	public void showMessage(String msg, Color color) {
		canvas1.showMessage(msg, color);
	}
	
    // Variables declaration
    
    public boolean isFACe() {
		return isFACe;
	}

	public void setTransition(Transition t) {
		this.t = t;	
	}

	private FadingCanvas canvas1;
    private JPanel canvasPanel;
    private JLabel jLabelMain;
    private JComboBox jComboBoxResidenceType;
    private JButton jButtonNewUser;
    private JButton jButtonDeleteUser;
    private JButton jButtonEditUser;
    private ButtonGroup buttonGroup;
    private JRadioButton jRadioButtonLEn;
    private JRadioButton jRadioButtonInd;
    private JComboBox jComboBoxItems;
    private JLabel jLabelResidenceType;
    private JTabbedPane jTabbedPaneLEn;
    private JPanel jPanelLEnGeneral;
    private JLabel jLabelLEnId;
    private JLabel jLabelLEnCIF;
    private JLabel jLabelLEnCorpName;
    private JLabel jLabelLEnTradeName;
    private JPanel jPanelLEnAddress;
    private JLabel jLabelLEnAddress;
    private JLabel jLabelLEnPostCode;
    private JLabel jLabelLEnTown;
    private JLabel jLabelLEnProvince;
    private JLabel jLabelLEnCountry;
    private JPanel jPanelLEnRegistration;
    private JLabel jLabelLEnBook;
    private JLabel jLabelLEnLocation;
    private JLabel jLabelLEnSheet;
    private JLabel jLabelLEnFolio;
    private JLabel jLabelLEnSection;
    private JLabel jLabelLEnVolume;
    private JLabel jLabelLEnRegAddData;
    private JPanel jPanelLEnContact;
    private JLabel jLabelLEnMail;
    private JLabel jLabelLEnContacts;
    private JLabel jLabelLEnConAddData;
    private JLabel jLabelLEnINETownCode;
    private JLabel jLabelLEnCNOCNAE;
    private JLabel jLabelLEnWeb;
    private JLabel jLabelLEnFax;
    private JLabel jLabelLEnTelephone;
    private JPanel jPanelLEnFACe;
    private JLabel jLabelLEnManagementAgency;
    private JLabel jLabelLEnProcessingAuthorityUnit;
    private JLabel jLabelLEnAccountingOffice;
    private JLabel jLabelLEnProposingAgency;
	private JLabel jLabelLEnFACeLink;
	private JButton jButtonLEnFACeManagementAgency;
	private JButton jButtonLEnFACeProcessingAuthorityUnit;
	private JButton jButtonLEnFACeAccountingOffice;
	private JButton jButtonLEnFACeProposingAgency;	
    private JTabbedPane jTabbedPaneInd;
    private JPanel jPanelIndGeneral;
    private JLabel jLabelIndId;
    private JLabel jLabelIndNIF;
    private JLabel jLabelIndName;
    private JLabel jLabelIndFirstSurname;
    private JLabel jLabelIndSecondSurname;
    private JPanel jPanelIndAddress;
    private JLabel jLabelIndAddress;
    private JLabel jLabelIndPostCode;
    private JLabel jLabelIndProvince;
    private JLabel jLabelIndCountry;
    private JLabel jLabelIndTown;
    private JPanel jPanelIndContact;
    private JLabel jLabelIndTelephone;
    private JLabel jLabelIndWeb;
    private JLabel jLabelIndContacts;
    private JLabel jLabelIndCNOCNAE;
    private JLabel jLabelIndConAddData;
    private JLabel jLabelIndFax;
    private JLabel jLabelIndMail;
    private JLabel jLabelIndINETownCode;
    private JButton jButtonSave;
    private JButton jButtonAdmCentre;

    private JComboBox jComboBoxLEnCountry;
    private JComboBox jComboBoxIndCountry;

    private JTextField jTextField[];

    private ComboBoxModel cbmInd;
    private ComboBoxModel cbmLEn;       
    private String administration;

    private AdministrativeCentresType centres;
    
    private ArrayList<IPartyPanelListener> listeners = new ArrayList<IPartyPanelListener>();
    
    private boolean isFACe = false;
}