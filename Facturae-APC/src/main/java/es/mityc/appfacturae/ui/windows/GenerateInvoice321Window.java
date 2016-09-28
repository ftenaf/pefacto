/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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
package es.mityc.appfacturae.ui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.w3c.dom.NodeList;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.SignInvoiceException;
import es.mityc.appfacturae.facturae.AdditionalDataType;
import es.mityc.appfacturae.facturae.AddressType;
import es.mityc.appfacturae.facturae.AmountType;
import es.mityc.appfacturae.facturae.AmountsWithheldType;
import es.mityc.appfacturae.facturae.AssigneeType;
import es.mityc.appfacturae.facturae.AttachedDocumentsType;
import es.mityc.appfacturae.facturae.AttachmentCompressionAlgorithmType;
import es.mityc.appfacturae.facturae.AttachmentEncodingType;
import es.mityc.appfacturae.facturae.AttachmentFormatType;
import es.mityc.appfacturae.facturae.AttachmentType;
import es.mityc.appfacturae.facturae.BatchType;
import es.mityc.appfacturae.facturae.BusinessType;
import es.mityc.appfacturae.facturae.ChargeType;
import es.mityc.appfacturae.facturae.ChargesType;
import es.mityc.appfacturae.facturae.ContactDetailsType;
import es.mityc.appfacturae.facturae.CorrectionMethodDescriptionType;
import es.mityc.appfacturae.facturae.CurrencyCodeType;
import es.mityc.appfacturae.facturae.DiscountType;
import es.mityc.appfacturae.facturae.DiscountsAndRebatesType;
import es.mityc.appfacturae.facturae.ExtensionsType;
import es.mityc.appfacturae.facturae.Facturae;
import es.mityc.appfacturae.facturae.FileHeaderType;
import es.mityc.appfacturae.facturae.IndividualType;
import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.facturae.InvoiceDocumentTypeType;
import es.mityc.appfacturae.facturae.InvoiceHeaderType;
import es.mityc.appfacturae.facturae.InvoiceIssuerTypeType;
import es.mityc.appfacturae.facturae.InvoiceTotalsType;
import es.mityc.appfacturae.facturae.InvoiceType;
import es.mityc.appfacturae.facturae.InvoicesType;
import es.mityc.appfacturae.facturae.LanguageCodeType;
import es.mityc.appfacturae.facturae.LegalEntityType;
import es.mityc.appfacturae.facturae.LegalLiteralsType;
import es.mityc.appfacturae.facturae.ModalityType;
import es.mityc.appfacturae.facturae.OverseasAddressType;
import es.mityc.appfacturae.facturae.PartiesType;
import es.mityc.appfacturae.facturae.PeriodDates;
import es.mityc.appfacturae.facturae.PersonTypeCodeType;
import es.mityc.appfacturae.facturae.PlaceOfIssueType;
import es.mityc.appfacturae.facturae.RegistrationDataType;
import es.mityc.appfacturae.facturae.ReimbursableExpenses;
import es.mityc.appfacturae.facturae.ReimbursableExpensesType;
import es.mityc.appfacturae.facturae.ResidenceTypeCodeType;
import es.mityc.appfacturae.facturae.TaxIdentificationType;
import es.mityc.appfacturae.facturae.TaxType;
import es.mityc.appfacturae.facturae.TaxesType;
import es.mityc.appfacturae.facturae321.FactoringAssignmentDataType;
import es.mityc.appfacturae.facturae321.AccountType;
import es.mityc.appfacturae.facturae321.InstallmentType;
import es.mityc.appfacturae.facturae321.InvoiceLineType;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.FacturaeStatics;
import es.mityc.appfacturae.hibernate.auxClass.AttachedDocument;
import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.ui.components.CoupledComboModel;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.IndividualRow;
import es.mityc.appfacturae.ui.components.LegalEntityRow;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.dialogs.InputCorrectiveDialog;
import es.mityc.appfacturae.ui.transitions.Transition;
import es.mityc.appfacturae.ui.windows.panels.InvoiceDetail321Panel;
import es.mityc.appfacturae.ui.windows.panels.InvoiceGeneralPanel;
import es.mityc.appfacturae.ui.windows.panels.PartyPanel;
import es.mityc.appfacturae.utils.Invoice321Util;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.Base64;
import es.mityc.appfacturae.utils.io.DoubleUtil;
import es.mityc.appfacturae.utils.io.IntegerUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.utils.ValidationException;

@SuppressWarnings({"unchecked", "rawtypes", "serial"})
public class GenerateInvoice321Window extends JFrame implements GenerateInvoiceWindow {

	private static Log logger = LogFactory.getLog(GenerateInvoice321Window.class);
	private Properties appProperties = null, decimalProps = null;
	private FadingCanvas fd = null;
	private MainWindow mw = null;
	private ArrayList<AttachedDocument> ads = null;
	private String[] rectIn = null;
	private static GenerateInvoice321Window gi321 = null;
	private Transition t = null;

	static public GenerateInvoice321Window getInstance(MainWindow mw) {
		if (gi321 == null){
			gi321 = new GenerateInvoice321Window(mw);
			return gi321;
		}else{
			Constants.DIALOG.showWarnGeneratingInvoice();
			return null;
		}
	}

	/** Creates new form GenerateInvoice321Window */
	private GenerateInvoice321Window(MainWindow mwInstance) {
		super();
		if (mwInstance == null)
			this.mw = MainWindow.getInstance();
		else
			this.mw = mwInstance;
		mw.refreshProgressBar(1);
		loadConfiguration();
		mw.refreshProgressBar(25);
		loadDBData();
		mw.refreshProgressBar(60);
		initComponents();
		mw.refreshProgressBar(95);
		this.setSize(Integer.parseInt(appProperties.getProperty("Width")),
			Integer.parseInt(appProperties.getProperty("Height")));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		// If screen size is less or equals to 1024x768, initial window position must be at 0,0 
		// instead of centered on the screen, due to visibility problem
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		if (dim.getHeight() <= 768)
			this.setLocation(0, 0);
		this.setIconImage(imgLogoApp);
		this.setTitle(Constants.LANG.getString("GenerateInvoice321WindowTitle"));
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				jButtonReturnActionPerformed();
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}
		});

		mw.refreshProgressBar(0);
	}

	/** This method is called from within the constructor to initialize the form.*/
	private void initComponents() {

		// The components (their initialization) are placed considering the jFrame structure
		mainPanel = new JPanel();
		t = new Transition(mainPanel);

		fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		fd.setForeground(Constants.FONT_COLOR);

		jPanelTopBar = new JPanel();
		jLabelTopBarImg = new JLabel();
		jLabelTopBarMsg2 = new JLabel();
		jLabelTopBarMsg1 = new JLabel();

		jPanelInvoiceGeneral = new InvoiceGeneralPanel(Constants.VERSION321);

		jPanelIssuer = new PartyPanel(Constants.LANG.getLocale(), ccmIssuerInd, ccmIssuerLEn, Constants.VERSION321,
			Constants.LANG.getString("Issuer"));
		jPanelReceiver = new PartyPanel(Constants.LANG.getLocale(), ccmReceiverInd, ccmReceiverLEn,
			Constants.VERSION321, Constants.LANG.getString("Receiver"));

		jPanelInvoiceDetail = new InvoiceDetail321Panel();

		jButtonHelp = new JButton();
		jButtonReturn = new JButton();
		jButtonSign = new JButton();
		jButtonSaveDraft = new JButton();

		mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
		mainPanel.setMaximumSize(new Dimension(0, 0));

		jPanelTopBar.setBackground(Color.white);
		jPanelTopBar.setBorder(new LineBorder(Constants.BORDER_COLOR, 1, true));

		jLabelTopBarImg.setIcon(new ImageIcon(getClass().getResource("/images/topbar2.jpg")));
		jLabelTopBarImg.setText("");

		jLabelTopBarMsg2.setFont(Constants.TITLE_FONT_ITALIC);
		jLabelTopBarMsg2.setForeground(Constants.FONT_COLOR);
		jLabelTopBarMsg2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelTopBarMsg2.setText(Constants.LANG.getString("TopBarMessage2"));

		jLabelTopBarMsg1.setFont(Constants.TITLE_FONT_ITALIC);
		jLabelTopBarMsg1.setForeground(Constants.FONT_COLOR);
		jLabelTopBarMsg1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelTopBarMsg1.setText(Constants.LANG.getString("TopBarMessage1"));

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
		jPanelTopBar.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			.add(
				jPanel1Layout
					.createSequentialGroup()
					.add(jLabelTopBarImg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
					.add(
						jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
							.add(jLabelTopBarMsg2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
							.add(jLabelTopBarMsg1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE))
					.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout
			.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			.add(
				jPanel1Layout.createSequentialGroup().add(jLabelTopBarMsg1)
					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jLabelTopBarMsg2))
			.add(jLabelTopBarImg, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE));

		mw.refreshProgressBar(65);

		jPanelIssuer.setBackground(Constants.BKG_MAIN_COLOR);
		jPanelIssuer.setBorder(BorderFactory.createTitledBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true),
			Constants.LANG.getString("Issuer"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
			Constants.FONT_BOLD, Constants.FONT_COLOR));
		jPanelIssuer.setLayout(null);
		((PartyPanel) jPanelIssuer).addListener(jPanelReceiver);

		jPanelReceiver.setBackground(Constants.BKG_MAIN_COLOR);
		jPanelReceiver.setBorder(BorderFactory.createTitledBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true),
			Constants.LANG.getString("Receiver"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
			Constants.FONT_BOLD, Constants.FONT_COLOR));
		jPanelReceiver.setLayout(null);
		((PartyPanel) jPanelReceiver).addListener(jPanelIssuer);
		jPanelReceiver.setTransition(t);

		jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/images/button_help.jpg")));
		jButtonHelp.setToolTipText(Constants.LANG.getString("Help"));
		jButtonHelp.setBorderPainted(false);
		jButtonHelp.setContentAreaFilled(false);
		jButtonHelp.addMouseListener(new ButtonCursor());
		jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonHelpActionPerformed();
			}
		});

		jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg")));
		jButtonReturn.setToolTipText(Constants.LANG.getString("Return"));
		jButtonReturn.setBorderPainted(false);
		jButtonReturn.setContentAreaFilled(false);
		jButtonReturn.addMouseListener(new ButtonCursor());

		jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonReturnActionPerformed();
			}
		});

		jButtonSign.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif")));
		jButtonSign.setFont(Constants.FONT_BOLD);
		jButtonSign.setForeground(Constants.FONT_COLOR);
		jButtonSign.setText(Constants.LANG.getString("Sign"));
		jButtonSign.setBorderPainted(false);
		jButtonSign.setContentAreaFilled(false);
		jButtonSign.setMargin(new Insets(1, 1, 1, 1));
		jButtonSign.addMouseListener(new ButtonCursor());
		jButtonSign.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int flag = validateForm();
				if (flag == 0)
					return;
				else if (flag < 0)
					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
				else {
					if (jPanelInvoiceDetail.getItems() == null
						|| jPanelInvoiceDetail.getItems().getInvoiceLine() == null
						|| jPanelInvoiceDetail.getItems().getInvoiceLine().size() == 0) {
						fd.showMessage(Constants.LANG.getString("NOOKParamItemRequired"), Constants.ERROR_MSG_COLOR);
						return;
					}

					Thread th = new Thread(new Runnable() {
						public void run() {
							String id = null;
							try {
								if (gi321 == null)
									gi321 = (GenerateInvoice321Window) mainPanel.getRootPane().getParent();
								gi321.setVisible(false);

								mw.getTransition().putTransitionPanel(Constants.LANG.getString("SigningInvoice"));
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
								}

								// A facturae application object is created and initialized with the form data
								Facturae f = getInvoiceData();
								if (f == null) // Canceled by user 
									return;
								try {
									id = new Invoice321Util().signInvoice(f, ads, (rectIn != null), mw);
									gi321.dispose();
									gi321 = null;
								} catch (ValidationException e) {
									gi321.setVisible(true);
									mw.refreshProgressBar(0);
									Constants.DIALOG.showError(e.getMessage());									
								} catch (SignInvoiceException e) {
									gi321.setVisible(true);
									Constants.DIALOG.showErrorSignProccess(e.getMessage()
										+ (e.getCause() != null ? ": " + e.getCause().getMessage() : ""));
								}
								mw.refreshProgressBar(100);

							} catch (DatabaseOperationException e) {
								gi321.setVisible(true);
								mw.refreshProgressBar(0);
								Constants.DIALOG.showErrorSignProccess(e.getMessage());
							} catch (Exception e) {
								gi321.setVisible(true);
								mw.refreshProgressBar(0);
								Constants.DIALOG.showErrorGeneratingInvoice(e.getMessage());
							} finally {
								mw.getTransition().removeTransitionPanel();
								if (id == null || "".equals(id)) { // Sign was canceled by user
									mw.loadData();
									return;
								}
								// Invoices table refresh
								mw.getJTreeInvoices().setSelectionRow(-1);
								if (rectIn != null) {
									mw.getJTreeInvoices().expandRow(5);
									mw.getJTreeInvoices().setSelectionRow(7);
								} else
									mw.getJTreeInvoices().setSelectionRow(2);
								// Wait for invoices refresh
								try {
									mw.getActionThread().join();
								} catch (Exception e) {
								}
								// Set the new invoice selected
								mw.getJTableInvoices().clearSelection();
								if (mw.getJTableInvoices().getRowCount() != 0 && id != null) {
									for (int i = 0; i < mw.getJTableInvoices().getRowCount(); ++i) {
										if (id.equals(mw.getJTableInvoices().getModel().getValueAt(i, 0))) {
											mw.getJTableInvoices().setRowSelectionInterval(i, i);
											break;
										}
									}
								}

								// Filter combos refresh
								mw.loadData();
							}
						}
					});
					th.start();
				}

				mw.loadData();
			}
		});

		mw.refreshProgressBar(75);

		jButtonSaveDraft.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif")));
		jButtonSaveDraft.setFont(Constants.FONT_BOLD);
		jButtonSaveDraft.setForeground(Constants.FONT_COLOR);
		jButtonSaveDraft.setText(Constants.LANG.getString("SaveDraft"));
		jButtonSaveDraft.setBorderPainted(false);
		jButtonSaveDraft.setContentAreaFilled(false);
		jButtonSaveDraft.setMargin(new Insets(1, 1, 1, 1));
		jButtonSaveDraft.addMouseListener(new ButtonCursor());
		jButtonSaveDraft.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int flag = validateForm();
				if (flag == 0)
					return;
				else if (flag < 0)
					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
				else {
					if (jPanelInvoiceDetail.getItems() == null
						|| jPanelInvoiceDetail.getItems().getInvoiceLine() == null
						|| jPanelInvoiceDetail.getItems().getInvoiceLine().size() == 0) {
						fd.showMessage(Constants.LANG.getString("NOOKParamItemRequired"), Constants.ERROR_MSG_COLOR);
						return;
					}
					Thread th = new Thread(new Runnable() {
						public void run() {
							String id = null;
							try {
								if (gi321 == null)
									gi321 = (GenerateInvoice321Window) mainPanel.getRootPane().getParent();
								gi321.setVisible(false);

								if (Constants.LANG.getString("SaveReceived").equals(jButtonSaveDraft.getText()))
									mw.getTransition().putTransitionPanel(Constants.LANG.getString("ReceivingInvoice"));
								else
									mw.getTransition().putTransitionPanel(Constants.LANG.getString("SavingDraft"));
								try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
								}

								// A facturae application object is created and initialized with the form data
								Facturae f = getInvoiceData();
								if (f == null) // Correction canceled by user 
									return;
								if (Constants.LANG.getString("SaveReceived").equals(jButtonSaveDraft.getText()))
									id = new Invoice321Util().saveReceived(f, ads, mw);
								else
									id = new Invoice321Util().saveDraft(f, ads, (rectIn != null), mw);
								mw.refreshProgressBar(100);
								gi321.dispose();
								gi321 = null;
							} 
							catch (ValidationException e) {
								gi321.setVisible(true);
								mw.refreshProgressBar(0);
								Constants.DIALOG.showError(e.getMessage());
							} catch (DatabaseOperationException e) {
								gi321.setVisible(true);
								mw.refreshProgressBar(0);
								Constants.DIALOG.showErrorSaveDraft();
							} catch (Exception e) {
								gi321.setVisible(true);
								mw.refreshProgressBar(0);
								Constants.DIALOG.showErrorGeneratingInvoice(e.getMessage());
							} finally {
								mw.getTransition().removeTransitionPanel();
								// Invoices table refresh
								mw.getJTreeInvoices().setSelectionRow(-1);
								if (rectIn != null) {
									mw.getJTreeInvoices().expandRow(5);
									mw.getJTreeInvoices().setSelectionRow(6);
								} else if (Constants.LANG.getString("SaveReceived").equals(jButtonSaveDraft.getText()))
									mw.getJTreeInvoices().setSelectionRow(4);
								else
									mw.getJTreeInvoices().setSelectionRow(1);
								// Wait for invoices refresh
								try {
									mw.getActionThread().join();
								} catch (Exception e) {
								}
								// Set the new invoice selected
								mw.getJTableInvoices().clearSelection();
								if (mw.getJTableInvoices().getRowCount() != 0 && id != null) {
									for (int i = 0; i < mw.getJTableInvoices().getRowCount(); ++i) {
										if (id.equals(mw.getJTableInvoices().getModel().getValueAt(i, 0))) {
											mw.getJTableInvoices().setRowSelectionInterval(i, i);
											break;
										}
									}
								}

								// Filter combos refresh
								mw.loadData();
							}
						}
					});
					th.start();
				}

				mw.loadData();
			}
		});

		org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			.add(
				mainPanelLayout.createSequentialGroup()
					.addContainerGap()
					.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							.add(mainPanelLayout.createSequentialGroup()
									.add(jButtonSaveDraft)
									.add(123, 123, 123)
									.add(jButtonSign)
									.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 550, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 25, Short.MAX_VALUE)
									.add(jButtonHelp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jButtonReturn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
							.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
									.add(org.jdesktop.layout.GroupLayout.LEADING, jPanelInvoiceDetail, 0, 993, Short.MAX_VALUE)
									.add(org.jdesktop.layout.GroupLayout.LEADING, mainPanelLayout.createSequentialGroup()
											.add(jPanelInvoiceGeneral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												285, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
											.add(jPanelIssuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 351,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
											.add(jPanelReceiver, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
									.add(org.jdesktop.layout.GroupLayout.LEADING, jPanelTopBar,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(21, Short.MAX_VALUE)));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			.add(mainPanelLayout.createSequentialGroup()
					.add(2, 2, 2)
					.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					.add(2, 2, 2)
					.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							.add(jPanelReceiver, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
							.add(jPanelInvoiceGeneral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.add(jPanelIssuer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
					.add(2, 2, 2)
					.add(jPanelInvoiceDetail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					.add(2, 2, 2)
					.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
							.add(jButtonSaveDraft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.add(jButtonSign, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.add(fd)
							.add(jButtonReturn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.add(jButtonHelp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).add(2, 2, 2)));

		mw.refreshProgressBar(90);
		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			.add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(mainPanel,
			org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
			org.jdesktop.layout.GroupLayout.PREFERRED_SIZE));

		pack();
	}

	private void jButtonReturnActionPerformed() {
		if (gi321 == null)
			gi321 = (GenerateInvoice321Window) mainPanel.getRootPane().getParent();
		gi321.setVisible(false);
		gi321.dispose();
		gi321 = null;
	}

	/** This method load the configuration information */
	private void loadConfiguration() {

		/** Loading logo image */
		try {
			imgLogoApp = ImageIO.read(getClass().getResource("/images/logoapp.jpg"));
		} catch (IOException ioe) {
			logger.error("An exception occurred when loading the images: " + ioe.getMessage());
		}

		/** Loading application properties */
		try {
			InputStream f = this.getClass().getResourceAsStream(Constants.APP_CONFIG_PATH);
			appProperties = new Properties();
			appProperties.load(f);
			f.close();
		} catch (Exception e) {
			logger.error("An exception occurred when loading the properties of the configuration file: "
				+ e.getMessage());
			return;
		}

		/** Loading decimal configuration properties */
		if (decimalProps == null) {
			try {
				File f = new File(Constants.APP_PROP.getProperty("fact" + Constants.FACTURAE321 + "d_file"));
				decimalProps = new Properties();
				if (f.exists())
					decimalProps.load(new FileInputStream(f));
				else
					decimalProps.load(Constants.APP_PROP.getClass().getResourceAsStream(
						Constants.APP_PROP.getProperty("fact" + Constants.FACTURAE321 + "d_resource")));
			} catch (IOException e) {
				logger.error("It is not possible to obtain the configuration properties file: " + e.getMessage(), e);
			}
		}

		mw.refreshProgressBar(10);
	}

	/** This method load the data base information */
	private void loadDBData() {
		SQLQuery s = null, s2 = null;

		// Individuals
		try {
			s = FacturaeManager.getInstance().executeQuery(
					"SELECT " + FacturaeStatics.parametersInd
						+ " FROM INDIVIDUAL,ADDRESS,PARTY,TAX_IDENTIFICATION WHERE INDIVIDUAL.ADDRESS = ADDRESS.ADDRESS_ID AND PARTY.PARTY_ID = INDIVIDUAL.PARTY_ID AND" 
						+ " TAX_IDENTIFICATION.TAX_ID_NUMBER = PARTY.TAX_IDENTIFICATION AND (TYPE_VERSION='Spain-3.1' OR TYPE_VERSION='Overseas-3.1') AND" 
						+ " INDIVIDUAL.PARTY_ID IN (" 
						// Se permiten todos los NIFs duplicados, ya que, pueden existir multiples direcciones para una persona física.
							+ "SELECT DISTINCT PARTY_ID FROM INDIVIDUAL,PARTY,ADDRESS WHERE INDIVIDUAL.PARTY_ID = PARTY.PARTY_ID " 
							+ "AND INDIVIDUAL.ADDRESS = ADDRESS.ADDRESS_ID) ORDER BY FIRST_SURNAME,SECOND_SURNAME,NAME");
			List<?> ls = s.list();
			dcbmInd = new DefaultComboBoxModel();
			int count = ls.size();
			for (int i = 0; i < count; i++) {
				mw.refreshProgressBar((int) (i / (count * 1.) * 10) + 35);
				dcbmInd.addElement(new IndividualRow((Object[]) ls.get(i)));
			}
			ccmIssuerInd = new CoupledComboModel(dcbmInd);
			ccmReceiverInd = new CoupledComboModel(dcbmInd);
		} catch (Exception e) {
			logger.error("An error occurred when loading the database information: " + e.getMessage());
		}

		// Legal Entities
		try {
			//Para FACe se permiten CIFs duplicados, ya que puede haber distintos centros con el mismo CIF  
			final String FILTER_BY_LEGAL_ENTITY ="AND LEGAL_ENTITY.PARTY_ID IN (" 
				+ "SELECT PARTY_ID FROM LEGAL_ENTITY,PARTY,ADDRESS WHERE LEGAL_ENTITY.PARTY_ID = PARTY.PARTY_ID " 
				+ "AND LEGAL_ENTITY.ADDRESS = ADDRESS.ADDRESS_ID) ";
			s2 = FacturaeManager.getInstance().executeQuery(
					"SELECT " + FacturaeStatics.parametersLEn
						+ " FROM LEGAL_ENTITY,ADDRESS,PARTY,TAX_IDENTIFICATION WHERE LEGAL_ENTITY.ADDRESS = ADDRESS_ID AND PARTY.PARTY_ID = LEGAL_ENTITY.PARTY_ID AND"
						+ "	TAX_IDENTIFICATION.TAX_ID_NUMBER = PARTY.TAX_IDENTIFICATION AND (TYPE_VERSION='Spain-3.1' OR TYPE_VERSION='Overseas-3.1') " 
						+ FILTER_BY_LEGAL_ENTITY 
						+ " ORDER BY CORPORATE_NAME"); 
			List<?> ls2 = s2.list();
			dcbmLEn = new DefaultComboBoxModel();
			int count = ls2.size();
			for (int i = 0; i < count; i++) {
				mw.refreshProgressBar((int) (i / (count * 1.) * 15) + 45);
				dcbmLEn.addElement(new LegalEntityRow((Object[]) ls2.get(i)));
			}
			ccmIssuerLEn = new CoupledComboModel(dcbmLEn);
			ccmReceiverLEn = new CoupledComboModel(dcbmLEn);
		} catch (Exception e) {
			logger.error("An error occurred when loading the database information: " + e.getMessage());
		}
	}

	/** This method gathers the form information about the invoice */
	private Facturae getInvoiceData() {
		boolean flag = false;
		Facturae facturae = new Facturae();
		InvoiceType invoice = new es.mityc.appfacturae.facturae321.InvoiceType();
		
		/** InvoiceGeneralPanel **/
		InvoiceHeaderType ih = new InvoiceHeaderType();
		if (jPanelInvoiceGeneral.getJTextFieldSeries() != null
			&& !"".equals(jPanelInvoiceGeneral.getJTextFieldSeries().getText()))
			ih.setInvoiceSeriesCode(jPanelInvoiceGeneral.getJTextFieldSeries().getText());
		if (jPanelInvoiceGeneral.getJTextFieldNumber() != null
			&& !"".equals(jPanelInvoiceGeneral.getJTextFieldNumber().getText()))
			ih.setInvoiceNumber(jPanelInvoiceGeneral.getJTextFieldNumber().getText());
		
		/** If invoice is a corrective invoice **/
		if (rectIn != null) {
			InputCorrectiveDialog icd = new InputCorrectiveDialog(null, true, Constants.VERSION321);
			icd.setVisible(true);
			Object[] result = icd.getValues();
			String desc = null;
			String methodDesc = null;
			if (result[0] != null && result[1] != null) {
				desc = (String) result[0];
				methodDesc = (String) result[1];
			} else
				return null;

			es.mityc.appfacturae.facturae321.CorrectiveType ct = new es.mityc.appfacturae.facturae321.CorrectiveType();

			// 3.1.1.5.1 InvoiceNumber
			if ("".equals(rectIn[1]))
				ct.setInvoiceNumber(ih.getInvoiceNumber());
			else
				ct.setInvoiceNumber(rectIn[1]);

			// 3.1.1.5.2 InvoiceSeriesCode
			if ("".equals(rectIn[0]))
				ct.setInvoiceSeriesCode(ih.getInvoiceSeriesCode());
			else
				ct.setInvoiceSeriesCode(rectIn[0]);

			// 3.1.1.5.3 ReasonCode
			int reasonCode = es.mityc.appfacturae.facturae32.ReasonDescriptionType.fromValue(desc).ordinal();
			if (reasonCode > 15)
				reasonCode = reasonCode + 64;
			ct.setReasonCode(IntegerUtil.to2String(reasonCode + 1));

			// 3.1.1.5.4 ReasonDescription
			es.mityc.appfacturae.facturae32.ReasonDescriptionType reasonDescription = es.mityc.appfacturae.facturae32.ReasonDescriptionType
				.fromValue(desc);
			ct.setReasonDescription(reasonDescription);

			// 3.1.1.5.5 TaxPeriod
			PeriodDates taxPeriod = new PeriodDates();
			// 3.1.1.5.5.1 StartDate
			XMLGregorianCalendar startDate = null; // Equals to expedition date
			try {
				DatatypeFactory df = DatatypeFactory.newInstance();
				startDate = df.newXMLGregorianCalendar();
				startDate.setDay(jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getCalendar()
					.get(Calendar.DAY_OF_MONTH));
				startDate
					.setMonth(jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getCalendar().get(Calendar.MONTH) + 1);
				startDate.setYear(jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getCalendar().get(Calendar.YEAR));
			} catch (DatatypeConfigurationException e) {
				logger.error("Error getting invoice data while creating a XMLGregorianCalendar:" + e.getMessage());
				return null;
			}
			taxPeriod.setStartDate(startDate);
			// 3.1.1.5.5.2 EndDate
			XMLGregorianCalendar endDate = null; // Today
			try {
				DatatypeFactory df = DatatypeFactory.newInstance();
				endDate = df.newXMLGregorianCalendar();
				Calendar today = Calendar.getInstance();
				endDate.setDay(today.get(Calendar.DAY_OF_MONTH));
				endDate.setMonth(today.get(Calendar.MONTH) + 1);
				endDate.setYear(today.get(Calendar.YEAR));
			} catch (DatatypeConfigurationException e) {
				logger.error("Error getting invoice data while creating a XMLGregorianCalendar:" + e.getMessage());
				return null;
			}
			taxPeriod.setEndDate(endDate);

			ct.setTaxPeriod(taxPeriod);

			// 3.1.1.5.6 CorrectionMethod
			int correctionMethod = CorrectionMethodDescriptionType.fromValue(methodDesc).ordinal();
			ct.setCorrectionMethod(IntegerUtil.to2String(correctionMethod + 1));

			// 3.1.1.5.7 CorrectionMethodDescription
			CorrectionMethodDescriptionType correctionMethodDescription = CorrectionMethodDescriptionType
				.fromValue(methodDesc);
			ct.setCorrectionMethodDescription(correctionMethodDescription);

			// 3.1.1.5.8 AdditionalReasonDescription
			if (result[2] != null && !"".equals(result[2].toString().trim()))
				ct.setAdditionalReasonDescription((String) result[2]);

			ih.setCorrective(ct);
		}

		ih.setInvoiceDocumentType(InvoiceDocumentTypeType.FC);
		ih.setInvoiceClass(InvoiceClassType.OO);

		invoice.setInvoiceHeader(ih);

		mw.refreshProgressBar(3);

		es.mityc.appfacturae.facturae321.InvoiceIssueDataType iid = new es.mityc.appfacturae.facturae321.InvoiceIssueDataType();

		if (jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getCalendar().getTime() != null) {
			XMLGregorianCalendar expDate = null;
			try {
				DatatypeFactory df = DatatypeFactory.newInstance();
				expDate = df.newXMLGregorianCalendar();
				expDate.setDay(jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getCalendar()
					.get(Calendar.DAY_OF_MONTH));
				expDate
					.setMonth(jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getCalendar().get(Calendar.MONTH) + 1);
				expDate.setYear(jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getCalendar().get(Calendar.YEAR));
			} catch (DatatypeConfigurationException e) {
				logger.error("Error getting invoice data while creating a XMLGregorianCalendar:" + e.getMessage());
				return null;
			}
			iid.setIssueDate(expDate);
		}

		if (jPanelInvoiceGeneral.getJRadioButtonOpDate().isSelected()) {
			if (jPanelInvoiceGeneral.getJCalendarComboBoxOpDate().getCalendar().getTime() != null) {
				XMLGregorianCalendar opDate = null;
				try {
					DatatypeFactory df = DatatypeFactory.newInstance();
					opDate = df.newXMLGregorianCalendar();
					opDate.setDay(jPanelInvoiceGeneral.getJCalendarComboBoxOpDate().getCalendar().get(Calendar.DAY_OF_MONTH));
					opDate.setMonth(jPanelInvoiceGeneral.getJCalendarComboBoxOpDate().getCalendar().get(Calendar.MONTH) + 1);
					opDate.setYear(jPanelInvoiceGeneral.getJCalendarComboBoxOpDate().getCalendar().get(Calendar.YEAR));
				} catch (DatatypeConfigurationException e) {
					logger.error("Error getting invoice data while creating a XMLGregorianCalendar:" + e.getMessage());
					return null;
				}
				iid.setOperationDate(opDate);
			}
		}

		PlaceOfIssueType poi = new PlaceOfIssueType();
		if (jPanelInvoiceGeneral.getJTextFieldPlPCode() != null
			&& !"".equals(jPanelInvoiceGeneral.getJTextFieldPlPCode().getText())) {
			poi.setPostCode(jPanelInvoiceGeneral.getJTextFieldPlPCode().getText());
			flag = true;
		}
		if (jPanelInvoiceGeneral.getJTextFieldPlDesc() != null
			&& !"".equals(jPanelInvoiceGeneral.getJTextFieldPlDesc().getText())) {
			poi.setPlaceOfIssueDescription(jPanelInvoiceGeneral.getJTextFieldPlDesc().getText());
			flag = true;
		}
		if (flag) {
			iid.setPlaceOfIssue(poi);
			flag = false;
		}

		if (jPanelInvoiceGeneral.getJRadioButtonPeriod().isSelected()) {
			PeriodDates ip = new PeriodDates();
			if (jPanelInvoiceGeneral.getJCalendarComboBoxPeriodFrom().getCalendar().getTime() != null) {
				XMLGregorianCalendar periodFromDate = null;
				try {
					DatatypeFactory df = DatatypeFactory.newInstance();
					periodFromDate = df.newXMLGregorianCalendar();
					periodFromDate.setDay(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodFrom().getCalendar()
						.get(Calendar.DAY_OF_MONTH));
					periodFromDate.setMonth(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodFrom().getCalendar()
						.get(Calendar.MONTH) + 1);
					periodFromDate.setYear(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodFrom().getCalendar()
						.get(Calendar.YEAR));
				} catch (DatatypeConfigurationException e) {
					logger.error("Error getting invoice data while creating a XMLGregorianCalendar:" + e.getMessage());
					return null;
				}
				ip.setStartDate(periodFromDate);
			}
			if (jPanelInvoiceGeneral.getJCalendarComboBoxPeriodTo().getCalendar().getTime() != null) {
				XMLGregorianCalendar periodToDate = null;
				try {
					DatatypeFactory df = DatatypeFactory.newInstance();
					periodToDate = df.newXMLGregorianCalendar();
					periodToDate.setDay(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodTo().getCalendar()
						.get(Calendar.DAY_OF_MONTH));
					periodToDate.setMonth(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodTo().getCalendar()
						.get(Calendar.MONTH) + 1);
					periodToDate.setYear(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodTo().getCalendar()
						.get(Calendar.YEAR));
				} catch (DatatypeConfigurationException e) {
					logger.error("Error getting invoice data while creating a XMLGregorianCalendar:" + e.getMessage());
					return null;
				}
				ip.setEndDate(periodToDate);
			}
			iid.setInvoicingPeriod(ip);
		}
		iid.setInvoiceCurrencyCode(CurrencyCodeType.EUR);
		iid.setTaxCurrencyCode(CurrencyCodeType.EUR);
		if (!"".equals(Constants.LANG.getLocale().getLanguage()))
			iid.setLanguageName(LanguageCodeType.valueOf(Constants.LANG.getLocale().getLanguage().toUpperCase()));
		else
			iid.setLanguageName(LanguageCodeType.valueOf("ES"));

		invoice.setInvoiceIssueData(iid);

		mw.refreshProgressBar(5);

		/** PartyPanel - Issuer **/
		PartiesType party = new PartiesType();
		BusinessType issuer = new BusinessType();
		JTextField jtfIssuer[] = jPanelIssuer.getJTextField();
		TaxIdentificationType ti = new TaxIdentificationType();

		if (jPanelIssuer.getJComboBoxResidenceType() != null
			&& jPanelIssuer.getJComboBoxResidenceType().getSelectedItem() != null
			&& !"".equals(jPanelIssuer.getJComboBoxResidenceType().getSelectedItem().toString()))
			ti.setResidenceTypeCode(ResidenceTypeCodeType.values()[jPanelIssuer.getJComboBoxResidenceType()
				.getSelectedIndex()]);

		/** Legal Entity **/
		if (jPanelIssuer.getJRadioButtonLEn().isSelected()) {
			LegalEntityType issuerLE = new LegalEntityType();
			ti.setPersonTypeCode(PersonTypeCodeType.J);
			issuerLE.setFace(jPanelIssuer.isFACe());
			if (jtfIssuer[0] != null && !"".equals(jtfIssuer[0].getText())) {
				issuer.setId(Integer.parseInt(jtfIssuer[0].getText()));
				issuerLE.setId(Integer.parseInt(jtfIssuer[0].getText()));
			}
			if (jtfIssuer[1] != null && !"".equals(jtfIssuer[1].getText()))
				ti.setTaxIdentificationNumber(jtfIssuer[1].getText());
			issuer.setTaxIdentification(ti);
			if (jtfIssuer[2] != null && !"".equals(jtfIssuer[2].getText()))
				issuerLE.setCorporateName(jtfIssuer[2].getText());
			if (jtfIssuer[3] != null && !"".equals(jtfIssuer[3].getText()))
				issuerLE.setTradeName(jtfIssuer[3].getText());

			/** AddressInSpain or OverseasAddress **/
			if (Integer.parseInt(((ComboOption) jPanelIssuer.getJComboBoxLEnCountry().getSelectedItem()).getValue()) == Constants.SPAINCODE31) {
				es.mityc.appfacturae.facturae.AddressType issuerAddressSpain = new es.mityc.appfacturae.facturae32.AddressType();
				((es.mityc.appfacturae.facturae32.AddressType) issuerAddressSpain)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelIssuer.getJComboBoxLEnCountry().getSelectedItem()).getValue())]);
				if (jtfIssuer[4] != null && !"".equals(jtfIssuer[4].getText())) {
					issuerAddressSpain.setAddress(jtfIssuer[4].getText());
					flag = true;
				}
				if (jtfIssuer[5] != null && !"".equals(jtfIssuer[5].getText())) {
					issuerAddressSpain.setPostCode(jtfIssuer[5].getText());
					flag = true;
				}
				if (jtfIssuer[6] != null && !"".equals(jtfIssuer[6].getText())) {
					issuerAddressSpain.setTown(jtfIssuer[6].getText());
					flag = true;
				}
				if (jtfIssuer[7] != null && !"".equals(jtfIssuer[7].getText())) {
					issuerAddressSpain.setProvince(jtfIssuer[7].getText());
					flag = true;
				}
				if (flag) {
					issuerLE.setAddressInSpain(issuerAddressSpain);
					flag = false;
				}
			} else {
				es.mityc.appfacturae.facturae.OverseasAddressType issuerOverseasAdd = new es.mityc.appfacturae.facturae32.OverseasAddressType();
				((es.mityc.appfacturae.facturae32.OverseasAddressType) issuerOverseasAdd)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelIssuer.getJComboBoxLEnCountry().getSelectedItem()).getValue())]);
				if (jtfIssuer[4] != null && !"".equals(jtfIssuer[4].getText())) {
					issuerOverseasAdd.setAddress(jtfIssuer[4].getText());
					flag = true;
				}
				if (jtfIssuer[6] != null && !"".equals(jtfIssuer[6].getText())) {
					if (jtfIssuer[5] != null && !"".equals(jtfIssuer[5].getText()))
						issuerOverseasAdd.setPostCodeAndTown(jtfIssuer[5].getText() + " " + jtfIssuer[6].getText());
					else
						issuerOverseasAdd.setPostCodeAndTown(jtfIssuer[6].getText());
					flag = true;
				}
				if (jtfIssuer[7] != null && !"".equals(jtfIssuer[7].getText())) {
					issuerOverseasAdd.setProvince(jtfIssuer[7].getText());
					flag = true;
				}
				if (flag) {
					issuerLE.setOverseasAddress(issuerOverseasAdd);
					flag = false;
				}
			}

			RegistrationDataType registration = new RegistrationDataType();
			if (jtfIssuer[9] != null && !"".equals(jtfIssuer[9].getText())) {
				registration.setBook(jtfIssuer[9].getText());
				flag = true;
			}
			if (jtfIssuer[10] != null && !"".equals(jtfIssuer[10].getText())) {
				registration.setSheet(jtfIssuer[10].getText());
				flag = true;
			}
			if (jtfIssuer[11] != null && !"".equals(jtfIssuer[11].getText())) {
				registration.setSection(jtfIssuer[11].getText());
				flag = true;
			}
			if (jtfIssuer[12] != null && !"".equals(jtfIssuer[12].getText())) {
				registration.setRegisterOfCompaniesLocation(jtfIssuer[12].getText());
				flag = true;
			}
			if (jtfIssuer[13] != null && !"".equals(jtfIssuer[13].getText())) {
				registration.setFolio(jtfIssuer[13].getText());
				flag = true;
			}
			if (jtfIssuer[14] != null && !"".equals(jtfIssuer[14].getText())) {
				registration.setVolume(jtfIssuer[14].getText());
				flag = true;
			}
			if (jtfIssuer[15] != null && !"".equals(jtfIssuer[15].getText())) {
				registration.setAdditionalRegistrationData(jtfIssuer[15].getText());
				flag = true;
			}

			if (flag) {
				issuerLE.setRegistrationData(registration);
				flag = false;
			}

			ContactDetailsType cd = new ContactDetailsType();
			if (jtfIssuer[16] != null && !"".equals(jtfIssuer[16].getText())) {
				cd.setTelephone(jtfIssuer[16].getText());
				flag = true;
			}
			if (jtfIssuer[17] != null && !"".equals(jtfIssuer[17].getText())) {
				cd.setWebAddress(jtfIssuer[17].getText());
				flag = true;
			}
			if (jtfIssuer[18] != null && !"".equals(jtfIssuer[18].getText())) {
				cd.setContactPersons(jtfIssuer[18].getText());
				flag = true;
			}
			if (jtfIssuer[19] != null && !"".equals(jtfIssuer[19].getText())) {
				cd.setCnoCnae(jtfIssuer[19].getText());
				flag = true;
			}
			if (jtfIssuer[20] != null && !"".equals(jtfIssuer[20].getText())) {
				cd.setTeleFax(jtfIssuer[20].getText());
				flag = true;
			}
			if (jtfIssuer[21] != null && !"".equals(jtfIssuer[21].getText())) {
				cd.setElectronicMail(jtfIssuer[21].getText());
				flag = true;
			}
			if (jtfIssuer[22] != null && !"".equals(jtfIssuer[22].getText())) {
				cd.setIneTownCode(jtfIssuer[22].getText());
				flag = true;
			}
			if (jtfIssuer[23] != null && !"".equals(jtfIssuer[23].getText())) {
				cd.setAdditionalContactDetails(jtfIssuer[23].getText());
				flag = true;
			}
			if (flag) {
				issuerLE.setContactDetails(cd);
				flag = false;
			}

			issuer.setLegalEntity(issuerLE);
		}

		/** Individual **/
		if (jPanelIssuer.getJRadioButtonInd().isSelected()) {
			IndividualType issuerInd = new IndividualType();
			ti.setPersonTypeCode(PersonTypeCodeType.F);
			if (jtfIssuer[24] != null && !"".equals(jtfIssuer[24].getText())) {
				issuer.setId(Integer.parseInt(jtfIssuer[24].getText()));
				issuerInd.setId(Integer.parseInt(jtfIssuer[24].getText()));
			}
			if (jtfIssuer[25] != null && !"".equals(jtfIssuer[25].getText()))
				ti.setTaxIdentificationNumber(jtfIssuer[25].getText());
			issuer.setTaxIdentification(ti);
			if (jtfIssuer[26] != null && !"".equals(jtfIssuer[26].getText()))
				issuerInd.setName(jtfIssuer[26].getText());
			if (jtfIssuer[27] != null && !"".equals(jtfIssuer[27].getText()))
				issuerInd.setFirstSurname(jtfIssuer[27].getText());
			if (jtfIssuer[28] != null && !"".equals(jtfIssuer[28].getText()))
				issuerInd.setSecondSurname(jtfIssuer[28].getText());

			/** AddressInSpain or OverseasAddress **/
			if (Integer.parseInt(((ComboOption) jPanelIssuer.getJComboBoxIndCountry().getSelectedItem()).getValue()) == Constants.SPAINCODE31) {
				es.mityc.appfacturae.facturae.AddressType issuerAddressSpain = new es.mityc.appfacturae.facturae32.AddressType();
				((es.mityc.appfacturae.facturae32.AddressType) issuerAddressSpain)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelIssuer.getJComboBoxIndCountry().getSelectedItem()).getValue())]);
				if (jtfIssuer[29] != null && !"".equals(jtfIssuer[29].getText())) {
					issuerAddressSpain.setAddress(jtfIssuer[29].getText());
					flag = true;
				}
				if (jtfIssuer[30] != null && !"".equals(jtfIssuer[30].getText())) {
					issuerAddressSpain.setPostCode(jtfIssuer[30].getText());
					flag = true;
				}
				if (jtfIssuer[31] != null && !"".equals(jtfIssuer[31].getText())) {
					issuerAddressSpain.setTown(jtfIssuer[31].getText());
					flag = true;
				}
				if (jtfIssuer[32] != null && !"".equals(jtfIssuer[32].getText())) {
					issuerAddressSpain.setProvince(jtfIssuer[32].getText());
					flag = true;
				}

				if (flag) {
					issuerInd.setAddressInSpain(issuerAddressSpain);
					flag = false;
				}
			} else {
				es.mityc.appfacturae.facturae.OverseasAddressType issuerOverseasAdd = new es.mityc.appfacturae.facturae32.OverseasAddressType();
				((es.mityc.appfacturae.facturae32.OverseasAddressType) issuerOverseasAdd)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelIssuer.getJComboBoxIndCountry().getSelectedItem()).getValue())]);
				if (jtfIssuer[29] != null && !"".equals(jtfIssuer[29].getText())) {
					issuerOverseasAdd.setAddress(jtfIssuer[29].getText());
					flag = true;
				}
				if (jtfIssuer[31] != null && !"".equals(jtfIssuer[31].getText())) {
					if (jtfIssuer[30] != null && !"".equals(jtfIssuer[30].getText()))
						((es.mityc.appfacturae.facturae32.OverseasAddressType) issuerOverseasAdd)
							.setPostCodeAndTown(jtfIssuer[30].getText() + " " + jtfIssuer[31].getText());
					else
						((es.mityc.appfacturae.facturae32.OverseasAddressType) issuerOverseasAdd)
							.setPostCodeAndTown(jtfIssuer[31].getText());
					flag = true;
				}
				if (jtfIssuer[32] != null && !"".equals(jtfIssuer[32].getText())) {
					issuerOverseasAdd.setProvince(jtfIssuer[32].getText());
					flag = true;
				}
				if (flag) {
					issuerInd.setOverseasAddress(issuerOverseasAdd);
					flag = false;
				}
			}

			ContactDetailsType cd = new ContactDetailsType();
			if (jtfIssuer[34] != null && !"".equals(jtfIssuer[34].getText())) {
				cd.setTelephone(jtfIssuer[34].getText());
				flag = true;
			}
			if (jtfIssuer[35] != null && !"".equals(jtfIssuer[35].getText())) {
				cd.setWebAddress(jtfIssuer[35].getText());
				flag = true;
			}
			if (jtfIssuer[36] != null && !"".equals(jtfIssuer[36].getText())) {
				cd.setContactPersons(jtfIssuer[36].getText());
				flag = true;
			}
			if (jtfIssuer[37] != null && !"".equals(jtfIssuer[37].getText())) {
				cd.setCnoCnae(jtfIssuer[37].getText());
				flag = true;
			}
			if (jtfIssuer[38] != null && !"".equals(jtfIssuer[38].getText())) {
				cd.setTeleFax(jtfIssuer[38].getText());
				flag = true;
			}
			if (jtfIssuer[39] != null && !"".equals(jtfIssuer[39].getText())) {
				cd.setElectronicMail(jtfIssuer[39].getText());
				flag = true;
			}
			if (jtfIssuer[40] != null && !"".equals(jtfIssuer[40].getText())) {
				cd.setIneTownCode(jtfIssuer[40].getText());
				flag = true;
			}
			if (jtfIssuer[41] != null && !"".equals(jtfIssuer[41].getText())) {
				cd.setAdditionalContactDetails(jtfIssuer[41].getText());
				flag = true;
			}
			if (flag) {
				issuerInd.setContactDetails(cd);
				flag = false;
			}

			issuer.setIndividual(issuerInd);
		}

		issuer.setAdministrativeCentres(jPanelIssuer.getCentres());

		party.setSellerParty(issuer);
		mw.refreshProgressBar(10);

		/** PartyPanel - Receiver **/
		BusinessType receiver = new BusinessType();
		JTextField jtfReceiver[] = jPanelReceiver.getJTextField();
		TaxIdentificationType taxIdentification = new TaxIdentificationType();

		if (jPanelReceiver.getJComboBoxResidenceType().getSelectedItem() != null
			&& jPanelReceiver.getJComboBoxResidenceType().getSelectedItem() != "")
			taxIdentification
				.setResidenceTypeCode(es.mityc.appfacturae.facturae.ResidenceTypeCodeType.values()[jPanelReceiver
					.getJComboBoxResidenceType().getSelectedIndex()]);

		/** Legal Entity **/
		if (jPanelReceiver.getJRadioButtonLEn().isSelected()) {
			LegalEntityType receiverLE = new LegalEntityType();
			taxIdentification.setPersonTypeCode(PersonTypeCodeType.J);
			receiverLE.setFace(jPanelReceiver.isFACe());
			if (jtfReceiver[0] != null && !"".equals(jtfReceiver[0].getText())) {
				receiver.setId(Integer.parseInt(jtfReceiver[0].getText()));
				receiverLE.setId(Integer.parseInt(jtfReceiver[0].getText()));
			}
			if (jtfReceiver[1] != null && !"".equals(jtfReceiver[1].getText()))
				taxIdentification.setTaxIdentificationNumber(jtfReceiver[1].getText());
			receiver.setTaxIdentification(taxIdentification);
			if (jtfReceiver[2] != null && !"".equals(jtfReceiver[2].getText()))
				receiverLE.setCorporateName(jtfReceiver[2].getText());
			if (jtfReceiver[3] != null && !"".equals(jtfReceiver[3].getText()))
				receiverLE.setTradeName(jtfReceiver[3].getText());

			/** AddressInSpain or OverseasAddress **/
			if (Integer.parseInt(((ComboOption) jPanelReceiver.getJComboBoxLEnCountry().getSelectedItem()).getValue()) == Constants.SPAINCODE31) {
				AddressType receiverAddressSpain = new es.mityc.appfacturae.facturae32.AddressType();
				((es.mityc.appfacturae.facturae32.AddressType) receiverAddressSpain)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelReceiver.getJComboBoxLEnCountry().getSelectedItem()).getValue())]);
				if (jtfReceiver[4] != null && !"".equals(jtfReceiver[4].getText())) {
					receiverAddressSpain.setAddress(jtfReceiver[4].getText());
					flag = true;
				}
				if (jtfReceiver[5] != null && !"".equals(jtfReceiver[5].getText())) {
					receiverAddressSpain.setPostCode(jtfReceiver[5].getText());
					flag = true;
				}
				if (jtfReceiver[6] != null && !"".equals(jtfReceiver[6].getText())) {
					receiverAddressSpain.setTown(jtfReceiver[6].getText());
					flag = true;
				}
				if (jtfReceiver[7] != null && !"".equals(jtfReceiver[7].getText())) {
					receiverAddressSpain.setProvince(jtfReceiver[7].getText());
					flag = true;
				}
				if (flag) {
					receiverLE.setAddressInSpain(receiverAddressSpain);
					flag = false;
				}
			} else {
				OverseasAddressType receiverOverseasAdd = new es.mityc.appfacturae.facturae32.OverseasAddressType();
				((es.mityc.appfacturae.facturae32.OverseasAddressType) receiverOverseasAdd)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelReceiver.getJComboBoxLEnCountry().getSelectedItem()).getValue())]);

				if (jtfReceiver[4] != null && !"".equals(jtfReceiver[4].getText())) {
					receiverOverseasAdd.setAddress(jtfReceiver[4].getText());
					flag = true;
				}
				if (jtfReceiver[6] != null && !"".equals(jtfReceiver[6].getText())) {
					if (jtfReceiver[5] != null && !"".equals(jtfReceiver[5].getText()))
						receiverOverseasAdd.setPostCodeAndTown(jtfReceiver[5].getText() + " "
							+ jtfReceiver[6].getText());
					else
						receiverOverseasAdd.setPostCodeAndTown(jtfReceiver[6].getText());
					flag = true;
				}
				if (jtfReceiver[7] != null && !"".equals(jtfReceiver[7].getText())) {
					receiverOverseasAdd.setProvince(jtfReceiver[7].getText());
					flag = true;
				}
				if (flag) {
					receiverLE.setOverseasAddress(receiverOverseasAdd);
					flag = false;
				}
			}

			RegistrationDataType registration = new RegistrationDataType();
			if (jtfReceiver[9] != null && !"".equals(jtfReceiver[9].getText())) {
				registration.setBook(jtfReceiver[9].getText());
				flag = true;
			}
			if (jtfReceiver[10] != null && !"".equals(jtfReceiver[10].getText())) {
				registration.setSheet(jtfReceiver[10].getText());
				flag = true;
			}
			if (jtfReceiver[11] != null && !"".equals(jtfReceiver[11].getText())) {
				registration.setSection(jtfReceiver[11].getText());
				flag = true;
			}
			if (jtfReceiver[12] != null && !"".equals(jtfReceiver[12].getText())) {
				registration.setRegisterOfCompaniesLocation(jtfReceiver[12].getText());
				flag = true;
			}
			if (jtfReceiver[13] != null && !"".equals(jtfReceiver[13].getText())) {
				registration.setFolio(jtfReceiver[13].getText());
				flag = true;
			}
			if (jtfReceiver[14] != null && !"".equals(jtfReceiver[14].getText())) {
				registration.setVolume(jtfReceiver[14].getText());
				flag = true;
			}
			if (jtfReceiver[15] != null && !"".equals(jtfReceiver[15].getText())) {
				registration.setAdditionalRegistrationData(jtfReceiver[15].getText());
				flag = true;
			}
			if (flag) {
				receiverLE.setRegistrationData(registration);
				flag = false;
			}

			ContactDetailsType cd = new ContactDetailsType();
			if (jtfReceiver[16] != null && !"".equals(jtfReceiver[16].getText())) {
				cd.setTelephone(jtfReceiver[16].getText());
				flag = true;
			}
			if (jtfReceiver[17] != null && !"".equals(jtfReceiver[17].getText())) {
				cd.setWebAddress(jtfReceiver[17].getText());
				flag = true;
			}
			if (jtfReceiver[18] != null && !"".equals(jtfReceiver[18].getText())) {
				cd.setContactPersons(jtfReceiver[18].getText());
				flag = true;
			}
			if (jtfReceiver[19] != null && !"".equals(jtfReceiver[19].getText())) {
				cd.setCnoCnae(jtfReceiver[19].getText());
				flag = true;
			}
			if (jtfReceiver[20] != null && !"".equals(jtfReceiver[20].getText())) {
				cd.setTeleFax(jtfReceiver[20].getText());
				flag = true;
			}
			if (jtfReceiver[21] != null && !"".equals(jtfReceiver[21].getText())) {
				cd.setElectronicMail(jtfReceiver[21].getText());
				flag = true;
			}
			if (jtfReceiver[22] != null && !"".equals(jtfReceiver[22].getText())) {
				cd.setIneTownCode(jtfReceiver[22].getText());
				flag = true;
			}
			if (jtfReceiver[23] != null && !"".equals(jtfReceiver[23].getText())) {
				cd.setAdditionalContactDetails(jtfReceiver[23].getText());
				flag = true;
			}
			if (flag) {
				receiverLE.setContactDetails(cd);
				flag = false;
			}

			receiver.setLegalEntity(receiverLE);
		}

		/** Individual **/
		if (jPanelReceiver.getJRadioButtonInd().isSelected()) {
			IndividualType receiverInd = new IndividualType();
			taxIdentification.setPersonTypeCode(PersonTypeCodeType.F);
			if (jtfReceiver[24] != null && !"".equals(jtfReceiver[24].getText())) {
				receiver.setId(Integer.parseInt(jtfReceiver[24].getText()));
				receiverInd.setId(Integer.parseInt(jtfReceiver[24].getText()));
			}
			if (jtfReceiver[25] != null && !"".equals(jtfReceiver[25].getText()))
				taxIdentification.setTaxIdentificationNumber(jtfReceiver[25].getText());
			receiver.setTaxIdentification(taxIdentification);
			if (jtfReceiver[26] != null && !"".equals(jtfReceiver[26].getText()))
				receiverInd.setName(jtfReceiver[26].getText());
			if (jtfReceiver[27] != null && !"".equals(jtfReceiver[27].getText()))
				receiverInd.setFirstSurname(jtfReceiver[27].getText());
			if (jtfReceiver[28] != null && !"".equals(jtfReceiver[28].getText()))
				receiverInd.setSecondSurname(jtfReceiver[28].getText());

			/** AddressInSpain or OverseasAddress **/
			if (Integer.parseInt(((ComboOption) jPanelReceiver.getJComboBoxIndCountry().getSelectedItem()).getValue()) == Constants.SPAINCODE31) {
				AddressType receiverAddressSpain = new es.mityc.appfacturae.facturae32.AddressType();
				((es.mityc.appfacturae.facturae32.AddressType) receiverAddressSpain)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelReceiver.getJComboBoxIndCountry().getSelectedItem()).getValue())]);
				if (jtfReceiver[29] != null && !"".equals(jtfReceiver[29].getText())) {
					receiverAddressSpain.setAddress(jtfReceiver[29].getText());
					flag = true;
				}
				if (jtfReceiver[30] != null && !"".equals(jtfReceiver[30].getText())) {
					receiverAddressSpain.setPostCode(jtfReceiver[30].getText());
					flag = true;
				}
				if (jtfReceiver[31] != null && !"".equals(jtfReceiver[31].getText())) {
					receiverAddressSpain.setTown(jtfReceiver[31].getText());
					flag = true;
				}
				if (jtfReceiver[32] != null && !"".equals(jtfReceiver[32].getText())) {
					receiverAddressSpain.setProvince(jtfReceiver[32].getText());
					flag = true;
				}
				if (flag) {
					receiverInd.setAddressInSpain(receiverAddressSpain);
					flag = false;
				}
			} else {
				OverseasAddressType receiverOverseasAdd = new es.mityc.appfacturae.facturae32.OverseasAddressType();
				((es.mityc.appfacturae.facturae32.OverseasAddressType) receiverOverseasAdd)
					.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
						.parseInt(((ComboOption) jPanelReceiver.getJComboBoxIndCountry().getSelectedItem()).getValue())]);
				if (jtfReceiver[29] != null && !"".equals(jtfReceiver[29].getText())) {
					receiverOverseasAdd.setAddress(jtfReceiver[29].getText());
					flag = true;
				}
				if (jtfReceiver[31] != null && !"".equals(jtfReceiver[31].getText())) {
					if (jtfReceiver[30] != null && !"".equals(jtfReceiver[30].getText()))
						receiverOverseasAdd.setPostCodeAndTown(jtfReceiver[30].getText() + " "
							+ jtfReceiver[31].getText());
					else
						receiverOverseasAdd.setPostCodeAndTown(jtfReceiver[31].getText());
					flag = true;
				}
				if (jtfReceiver[32] != null && !"".equals(jtfReceiver[32].getText())) {
					receiverOverseasAdd.setProvince(jtfReceiver[32].getText());
					flag = true;
				}
				if (flag) {
					receiverInd.setOverseasAddress(receiverOverseasAdd);
					flag = false;
				}
			}

			ContactDetailsType cd = new ContactDetailsType();
			if (jtfReceiver[34] != null && !"".equals(jtfReceiver[34].getText())) {
				cd.setTelephone(jtfReceiver[34].getText());
				flag = true;
			}
			if (jtfReceiver[35] != null && !"".equals(jtfReceiver[35].getText())) {
				cd.setWebAddress(jtfReceiver[35].getText());
				flag = true;
			}
			if (jtfReceiver[36] != null && !"".equals(jtfReceiver[36].getText())) {
				cd.setContactPersons(jtfReceiver[36].getText());
				flag = true;
			}
			if (jtfReceiver[37] != null && !"".equals(jtfReceiver[37].getText())) {
				cd.setCnoCnae(jtfReceiver[37].getText());
				flag = true;
			}
			if (jtfReceiver[38] != null && !"".equals(jtfReceiver[38].getText())) {
				cd.setTeleFax(jtfReceiver[38].getText());
				flag = true;
			}
			if (jtfReceiver[39] != null && !"".equals(jtfReceiver[39].getText())) {
				cd.setElectronicMail(jtfReceiver[39].getText());
				flag = true;
			}
			if (jtfReceiver[40] != null && !"".equals(jtfReceiver[40].getText())) {
				cd.setIneTownCode(jtfReceiver[40].getText());
				flag = true;
			}
			if (jtfReceiver[41] != null && !"".equals(jtfReceiver[41].getText())) {
				cd.setAdditionalContactDetails(jtfReceiver[41].getText());
				flag = true;
			}
			if (flag) {
				receiverInd.setContactDetails(cd);
				flag = false;
			}

			receiver.setIndividual(receiverInd);
		}

		receiver.setAdministrativeCentres(jPanelReceiver.getCentres());

		party.setBuyerParty(receiver);
		facturae.setParties(party);
		mw.refreshProgressBar(15);

		/** InvoiceDetailPanel **/
		InvoiceTotalsType it = new es.mityc.appfacturae.facturae321.InvoiceTotalsType();
		// GeneralDiscounts
		DiscountsAndRebatesType generalDiscounts = new es.mityc.appfacturae.facturae.DiscountsAndRebatesType();
		int rowsNumberDis = jPanelInvoiceDetail.getJTableGlobDisc().getRowCount();
		TableModel tmDis = jPanelInvoiceDetail.getJTableGlobDisc().getModel();
		if (rowsNumberDis > 0) {
			for (int i = 0; i < rowsNumberDis; i++) {
				DiscountType discount = new DiscountType();
				discount.setDiscountReason(tmDis.getValueAt(i, 0).toString());
				if (tmDis.getValueAt(i, 1) != null && !"".equals(tmDis.getValueAt(i, 1).toString()))
					discount.setDiscountRate(DoubleUtil.getDoubleFromPrettyNumber(tmDis.getValueAt(i, 1).toString()));
				discount.setDiscountAmount(DoubleUtil.getDoubleFromPrettyNumber(tmDis.getValueAt(i, 2).toString()));

				generalDiscounts.getDiscount().add(discount);
			}
			it.setGeneralDiscounts(generalDiscounts);
		}

		// TotalGeneralDiscounts
		if (jPanelInvoiceDetail.getJTextFieldTotalDisc() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextFieldTotalDisc().getText()))
			it.setTotalGeneralDiscounts(DoubleUtil.getDoubleFromPrettyNumber(jPanelInvoiceDetail.getJTextFieldTotalDisc().getText()));

		// GeneralSurcharges
		ChargesType generalSurcharges = new ChargesType();
		int rowsNumberChar = jPanelInvoiceDetail.getJTableGlobChar().getRowCount();
		TableModel tmChar = jPanelInvoiceDetail.getJTableGlobChar().getModel();
		if (rowsNumberChar > 0) {
			for (int i = 0; i < rowsNumberChar; i++) {
				ChargeType charge = new ChargeType();
				charge.setChargeReason(tmChar.getValueAt(i, 0).toString());
				if (tmChar.getValueAt(i, 1) != null && !"".equals(tmChar.getValueAt(i, 1).toString()))
					charge.setChargeRate(DoubleUtil.getDoubleFromPrettyNumber(tmChar.getValueAt(i, 1).toString()));

				charge.setChargeAmount(DoubleUtil.getDoubleFromPrettyNumber(tmChar.getValueAt(i, 2).toString()));

				generalSurcharges.getCharge().add(charge);
			}
			it.setGeneralSurcharges(generalSurcharges);
		}

		// TotalGeneralcharges
		if (jPanelInvoiceDetail.getJTextFieldTotalChar() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextFieldTotalChar().getText()))
			it.setTotalGeneralSurcharges(DoubleUtil.getDoubleFromPrettyNumber(jPanelInvoiceDetail.getJTextFieldTotalChar().getText()));

		// Reimbursable expenses
		ReimbursableExpenses reimbursableExp = new ReimbursableExpenses();
		int rowsNumberReim = jPanelInvoiceDetail.getJTableGlobReim().getRowCount();
		TableModel tmReim = jPanelInvoiceDetail.getJTableGlobReim().getModel();
		if (rowsNumberReim > 0) {
			for (int i = 0; i < rowsNumberReim; i++) {
				ReimbursableExpensesType re = new ReimbursableExpensesType();
				TaxIdentificationType reimTaxId = new TaxIdentificationType();
				if (tmReim.getValueAt(i, 0) != null && !"".equals(tmReim.getValueAt(i, 0).toString())) {
					int index = -1;
					if (tmReim.getValueAt(i, 0).toString().equals(Constants.LANG.getString("Individual")))
						index = 0;
					else
						index = 1;
					reimTaxId.setPersonTypeCode(PersonTypeCodeType.values()[index]);
					if (tmReim.getValueAt(i, 1).toString().equals(Constants.LANG.getString("Foreigner")))
						index = 0;
					else if (tmReim.getValueAt(i, 1).toString().equals(Constants.LANG.getString("Resident")))
						index = 1;
					else
						index = 2;
					reimTaxId.setResidenceTypeCode(ResidenceTypeCodeType.values()[index]);
					reimTaxId.setTaxIdentificationNumber(tmReim.getValueAt(i, 2).toString());
					re.setReimbursableExpensesSellerParty(reimTaxId);
				}
				if (tmReim.getValueAt(i, 3) != null && !"".equals(tmReim.getValueAt(i, 3).toString())) {
					reimTaxId = new TaxIdentificationType();
					int index = -1;
					if (tmReim.getValueAt(i, 3).toString().equals(Constants.LANG.getString("Individual")))
						index = 0;
					else
						index = 1;
					reimTaxId.setPersonTypeCode(PersonTypeCodeType.values()[index]);
					if (tmReim.getValueAt(i, 4).toString().equals(Constants.LANG.getString("Foreigner")))
						index = 0;
					else if (tmReim.getValueAt(i, 4).toString().equals(Constants.LANG.getString("Resident")))
						index = 1;
					else
						index = 2;
					reimTaxId.setResidenceTypeCode(ResidenceTypeCodeType.values()[index]);
					reimTaxId.setTaxIdentificationNumber(tmReim.getValueAt(i, 5).toString());
					re.setReimbursableExpensesBuyerParty(reimTaxId);
				}
				re.setReimbursableExpensesAmount(DoubleUtil.getDoubleFromPrettyNumber(tmReim.getValueAt(i, 6).toString()));
				reimbursableExp.getReimbursableExpenses().add(re);
			}
			((es.mityc.appfacturae.facturae321.InvoiceTotalsType) it).setReimbursableExpenses(reimbursableExp);
		}

		// TotalReimbursableExpenses
		if (jPanelInvoiceDetail.getJTextFieldTotalReim() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextFieldTotalReim().getText()))
			((es.mityc.appfacturae.facturae321.InvoiceTotalsType) it).setTotalReimbursableExpenses(Double
				.parseDouble(jPanelInvoiceDetail.getJTextFieldTotalReim().getText()));

		JTextField totalResults[] = jPanelInvoiceDetail.getJTextFieldResults();
		// TotalGrossAmount
		if (totalResults[0] != null && !"".equals(totalResults[0].getText()))
			it.setTotalGrossAmount(DoubleUtil.getDoubleFromPrettyNumber(totalResults[0].getText()));

		// TotalGrossAmountBeforeTaxes
		if (totalResults[3] != null && !"".equals(totalResults[3].getText()))
			it.setTotalGrossAmountBeforeTaxes(DoubleUtil.getDoubleFromPrettyNumber(totalResults[3].getText()));

		// TotalTaxOutputs
		if (totalResults[4] != null && !"".equals(totalResults[4].getText()))
			it.setTotalTaxOutputs(DoubleUtil.getDoubleFromPrettyNumber(totalResults[4].getText()));

		// TotalTaxesWithheld
		if (totalResults[5] != null && !"".equals(totalResults[5].getText()))
			it.setTotalTaxesWithheld(DoubleUtil.getDoubleFromPrettyNumber(totalResults[5].getText()));

		// InvoiceTotal & TotalOutstandingAmount
		if (totalResults[6] != null && !"".equals(totalResults[6].getText())) {
			it.setInvoiceTotal(DoubleUtil.getDoubleFromPrettyNumber(totalResults[6].getText()));
			it.setTotalOutstandingAmount(DoubleUtil.getDoubleFromPrettyNumber(totalResults[6].getText()));
		}
		
		// TotalFinancialExpenses
		if (totalResults[9] != null && !"".equals(totalResults[9].getText()))
			((es.mityc.appfacturae.facturae321.InvoiceTotalsType) it).setTotalFinancialExpenses(Double
				.parseDouble(totalResults[9].getText()));

		// TotalExecutableAmount
		if (totalResults[10] != null && !"".equals(totalResults[10].getText()))
			it.setTotalExecutableAmount(DoubleUtil.getDoubleFromPrettyNumber(totalResults[10].getText()));

		AmountsWithheldType awt = new AmountsWithheldType();
		// AmountsWithheld
		if (totalResults[7] != null && !"".equals(totalResults[7].getText())) {
			awt.setWithholdingAmount(DoubleUtil.getDoubleFromPrettyNumber(totalResults[7].getText()));
			flag = true;
		}
		if (jPanelInvoiceDetail.getJTextFieldWithItem() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextFieldWithItem().getText())) {
			awt.setWithholdingReason(jPanelInvoiceDetail.getJTextFieldWithItem().getText());
			flag = true;
		}
		if (jPanelInvoiceDetail.getJTextFieldWithPerc() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextFieldWithPerc().getText())) {
			awt.setWithholdingRate(DoubleUtil.getDoubleFromPrettyNumber(jPanelInvoiceDetail.getJTextFieldWithPerc().getText()));
			flag = true;
		}
		if (flag) {
			it.setAmountsWithheld(awt);
			flag = false;
		}
		invoice.setInvoiceTotals(it);

	    // LegalLiterals
	    Object[] legalliterals = new Object[0];
	    LegalLiteralsType ll = new LegalLiteralsType();
		if (jPanelInvoiceDetail.getJListLiterals() != null) {
			legalliterals = new Object[jPanelInvoiceDetail.getjListLiteralsDest().getModel().getSize()];
			for (int i = 0; i < jPanelInvoiceDetail.getjListLiteralsDest().getModel().getSize(); i++) {
				legalliterals[i] = jPanelInvoiceDetail.getjListLiteralsDest().getModel().getElementAt(i);
			}
			for (int i = 0; i < legalliterals.length; i++) {
				ll.getLegalReference().add(legalliterals[i].toString());
				flag = true;
			}
			if (flag) {
				((es.mityc.appfacturae.facturae321.InvoiceType) invoice).setLegalLiterals(ll);
				flag = false;
			}
		}

		// AttachedDocuments
		AttachedDocumentsType relatedDoc = null;
		AttachmentType attachment = null;
		AttachedDocument ad = null;
		int rowsNumberAttach = jPanelInvoiceDetail.getJTableGlobAttc().getRowCount();
		TableModel tmAttach = jPanelInvoiceDetail.getJTableGlobAttc().getModel();
		ads = new ArrayList<AttachedDocument>();
		if (rowsNumberAttach > 0) {
			relatedDoc = new AttachedDocumentsType();
			int includedSize = 0;
			for (int i = 0; i < rowsNumberAttach; i++) {
				ad = new AttachedDocument();
				attachment = new AttachmentType();
				if (tmAttach.getValueAt(i, 3) != null
					&& tmAttach.getValueAt(i, 3).toString().equals(Constants.LANG.getString("Yes"))) {
					try {
						attachment.setAttachmentFormat(AttachmentFormatType.valueOf(tmAttach.getValueAt(i, 0).toString()));
					} catch (IllegalArgumentException iae) {
						attachment.setAttachmentFormat(AttachmentFormatType.valueOf("XML"));
					}
					attachment.setAttachmentCompressionAlgorithm(AttachmentCompressionAlgorithmType.NONE);
					attachment.setAttachmentEncoding(AttachmentEncodingType.BASE_64);
					// The file is read and inserted in a Base64 String
					File f = new File(tmAttach.getValueAt(i, 1).toString());
					includedSize += f.length();
					if (includedSize > Constants.AttIncludedLimit) { // Then, it is not included in XML
						Constants.DIALOG.showErrorAttachmentSize();

						ad.setIsIncluded(0);
						ad.setPath(tmAttach.getValueAt(i, 1).toString());
						ad.setExtension(tmAttach.getValueAt(i, 0).toString());
						if (tmAttach.getValueAt(i, 2) != null && !"".equals(tmAttach.getValueAt(i, 2).toString()))
							ad.setDescription(tmAttach.getValueAt(i, 2).toString());

						String invoiceId = "";
						if (ih.getInvoiceSeriesCode() != null && !"".equals(ih.getInvoiceSeriesCode()))
							invoiceId = ih.getInvoiceSeriesCode();
						invoiceId = invoiceId + ih.getInvoiceNumber();
						ad.setInvoiceId(invoiceId);
						ads.add(ad);
						continue;
					}
					String strEncoded = Base64.encodeFromFile(f.getAbsolutePath());
					if (strEncoded == null) {
						logger.error("Error al extraer fichero.");
						return null;
					}
					attachment.setAttachmentData(strEncoded);
					if (tmAttach.getValueAt(i, 2) != null && !"".equals(tmAttach.getValueAt(i, 2).toString()))
						attachment.setAttachmentDescription(tmAttach.getValueAt(i, 2).toString());
					relatedDoc.getAttachment().add(attachment);
					ad.setIsIncluded(1);
				} else
					ad.setIsIncluded(0);

				ad.setPath(tmAttach.getValueAt(i, 1).toString());
				ad.setExtension(tmAttach.getValueAt(i, 0).toString());
				//ctg attach
				if (tmAttach.getValueAt(i, 2) != null && !"".equals(tmAttach.getValueAt(i, 2).toString()))
					ad.setDescription(tmAttach.getValueAt(i, 2).toString());
				String invoiceId = "";
				if (ih.getInvoiceSeriesCode() != null && !"".equals(ih.getInvoiceSeriesCode()))
					invoiceId = ih.getInvoiceSeriesCode();
				invoiceId = invoiceId + ih.getInvoiceNumber();
				ad.setInvoiceId(invoiceId);
				/** The attachment can't be saved in DB until knowing if the invoice is issued
				 *  or stored like a draft. The id associated change depending on this */
				ads.add(ad);
			}
		}

		AdditionalDataType adt = new es.mityc.appfacturae.facturae32.AdditionalDataType();
		if (relatedDoc != null && relatedDoc.getAttachment() != null && relatedDoc.getAttachment().size() > 0) {
			adt.setRelatedDocuments(relatedDoc);
			flag = true;
		}
		if (jPanelInvoiceDetail.getJTextAreaXMLExtAddData().getText() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextAreaXMLExtAddData().getText().trim())) {
			ExtensionsType extension = new es.mityc.appfacturae.facturae32.ExtensionsType();
			InputStream isExt = null;
			try {
				isExt = new ByteArrayInputStream(jPanelInvoiceDetail.getJTextAreaXMLExtAddData().getText()
					.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("Error creating a bytes array with extension data. The encoding is unsupported: "
					+ e.getMessage());
				return null;
			}
			try {
				NodeList nl = StreamUtil.createDoc(isExt).getChildNodes();
				for (int i = 0; i < nl.getLength(); i++)
					((es.mityc.appfacturae.facturae32.ExtensionsType) extension).getAny().add(nl.item(i));
				adt.setExtensions(extension);
				flag = true;
			} catch (ValidationException ve) {
				logger.error("A validation error occurred. The extension element is not valid: " + ve.getMessage());
				return null;
			}
		}
		if (flag) {
			invoice.setAdditionalData(adt);
			flag = false;
		}

		mw.refreshProgressBar(20);

		// PaymentDetails
		es.mityc.appfacturae.facturae32.InstallmentsType installments = new es.mityc.appfacturae.facturae32.InstallmentsType();
		InstallmentType installment = new InstallmentType();

		if (jPanelInvoiceDetail.getJTextFieldPaymentAmo() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextFieldPaymentAmo().getText())) {
			installment.setInstallmentAmount(Double
				.parseDouble(jPanelInvoiceDetail.getJTextFieldPaymentAmo().getText()));
			XMLGregorianCalendar paymDate = null;
			try {
				DatatypeFactory df = DatatypeFactory.newInstance();
				paymDate = df.newXMLGregorianCalendar();
				paymDate.setDay(jPanelInvoiceDetail.getJCalendarComboBoxPaymDate().getCalendar()
					.get(Calendar.DAY_OF_MONTH));
				paymDate
					.setMonth(jPanelInvoiceDetail.getJCalendarComboBoxPaymDate().getCalendar().get(Calendar.MONTH) + 1);
				paymDate.setYear(jPanelInvoiceDetail.getJCalendarComboBoxPaymDate().getCalendar().get(Calendar.YEAR));
			} catch (DatatypeConfigurationException dce) {
				logger.error("An error has been produced during the date construction: " + dce.getMessage());
				return null;
			}
			installment.setInstallmentDueDate(paymDate);

			if (jPanelInvoiceDetail.getJTextFieldPaymentRefIssuer() != null
				&& !"".equals(jPanelInvoiceDetail.getJTextFieldPaymentRefIssuer().getText()))
				installment.setPaymentReconciliationReference(jPanelInvoiceDetail.getJTextFieldPaymentRefIssuer()
					.getText());

			if (jPanelInvoiceDetail.getJTextFieldPaymentRefReceiver() != null
				&& !"".equals(jPanelInvoiceDetail.getJTextFieldPaymentRefReceiver().getText()))
				installment.setDebitReconciliationReference(jPanelInvoiceDetail.getJTextFieldPaymentRefReceiver()
					.getText());

			if (jPanelInvoiceDetail.getJComboBoxPayMean().getSelectedItem() != null
				&& !"".equals(jPanelInvoiceDetail.getJComboBoxPayMean().getSelectedItem().toString()))
				installment.setPaymentMeans(IntegerUtil.to2String(jPanelInvoiceDetail.getJComboBoxPayMean()
					.getSelectedIndex() + 1));

			if (jPanelInvoiceDetail.getJTextFieldPayCIBAN() != null
				&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayCIBAN().getText())) {
				AccountType at = new AccountType();
				at.setIban(jPanelInvoiceDetail.getJTextFieldPayCIBAN().getText());

				if (jPanelInvoiceDetail.getJTextFieldPayCBank() != null
					&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayCBank().getText()))
					at.setBankCode(jPanelInvoiceDetail.getJTextFieldPayCBank().getText());

				if (jPanelInvoiceDetail.getJTextFieldPayCBran() != null
					&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayCBran().getText()))
					at.setBranchCode(jPanelInvoiceDetail.getJTextFieldPayCBran().getText());

				if (jPanelInvoiceDetail.getJTextFieldPayCAddr() != null
					&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayCAddr().getText())) {

					if (Integer.parseInt(((ComboOption) jPanelInvoiceDetail.getJComboBoxPayCCoun().getSelectedItem())
						.getValue()) == Constants.SPAINCODE31) {
						AddressType addr = new es.mityc.appfacturae.facturae32.AddressType();
						((es.mityc.appfacturae.facturae32.AddressType) addr)
							.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
								.parseInt(((ComboOption) jPanelInvoiceDetail.getJComboBoxPayCCoun().getSelectedItem())
									.getValue())]);
						((es.mityc.appfacturae.facturae32.AddressType) addr).setAddress(jPanelInvoiceDetail
							.getJTextFieldPayCAddr().getText());
						((es.mityc.appfacturae.facturae32.AddressType) addr).setPostCode(jPanelInvoiceDetail
							.getJTextFieldPayCPCod().getText());
						((es.mityc.appfacturae.facturae32.AddressType) addr).setTown(jPanelInvoiceDetail
							.getJTextFieldPayCCity().getText());
						((es.mityc.appfacturae.facturae32.AddressType) addr).setProvince(jPanelInvoiceDetail
							.getJTextFieldPayCProv().getText());
						at.setBranchInSpainAddress(addr);
					} else {
						OverseasAddressType oaddr = new es.mityc.appfacturae.facturae32.OverseasAddressType();
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr)
							.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
								.parseInt(((ComboOption) jPanelInvoiceDetail.getJComboBoxPayCCoun().getSelectedItem())
									.getValue())]);
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr).setAddress(jPanelInvoiceDetail
							.getJTextFieldPayCAddr().getText());
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr)
							.setPostCodeAndTown((jPanelInvoiceDetail.getJTextFieldPayCPCod().getText() + " " + jPanelInvoiceDetail
								.getJTextFieldPayCCity().getText()).trim());
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr).setProvince(jPanelInvoiceDetail
							.getJTextFieldPayCProv().getText());
						at.setOverseasBranchAddress(oaddr);
					}
				}
				installment.setAccountToBeCredited(at);
			}

			if (jPanelInvoiceDetail.getJTextFieldPayDIBAN() != null
				&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayDIBAN().getText())) {
				AccountType at = new AccountType();
				at.setIban(jPanelInvoiceDetail.getJTextFieldPayDIBAN().getText());
				if (jPanelInvoiceDetail.getJTextFieldPayDBank() != null
					&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayDBank().getText()))
					at.setBankCode(jPanelInvoiceDetail.getJTextFieldPayDBank().getText());
				if (jPanelInvoiceDetail.getJTextFieldPayDBran() != null
					&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayDBran().getText()))
					at.setBranchCode(jPanelInvoiceDetail.getJTextFieldPayDBran().getText());
				if (jPanelInvoiceDetail.getJTextFieldPayDAddr() != null
					&& !"".equals(jPanelInvoiceDetail.getJTextFieldPayDAddr().getText())) {
					if (Integer.parseInt(((ComboOption) jPanelInvoiceDetail.getJComboBoxPayDCoun().getSelectedItem())
						.getValue()) == Constants.SPAINCODE31) {
						AddressType addr = new es.mityc.appfacturae.facturae32.AddressType();
						((es.mityc.appfacturae.facturae32.AddressType) addr)
							.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
								.parseInt(((ComboOption) jPanelInvoiceDetail.getJComboBoxPayDCoun().getSelectedItem()).getValue())]);
						((es.mityc.appfacturae.facturae32.AddressType) addr).setAddress(jPanelInvoiceDetail.getJTextFieldPayDAddr().getText());
						((es.mityc.appfacturae.facturae32.AddressType) addr).setPostCode(jPanelInvoiceDetail.getJTextFieldPayDPCod().getText());
						((es.mityc.appfacturae.facturae32.AddressType) addr).setTown(jPanelInvoiceDetail.getJTextFieldPayDCity().getText());
						((es.mityc.appfacturae.facturae32.AddressType) addr).setProvince(jPanelInvoiceDetail.getJTextFieldPayDProv().getText());
						at.setBranchInSpainAddress(addr);
					} else {
						OverseasAddressType oaddr = new es.mityc.appfacturae.facturae32.OverseasAddressType();
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr)
							.setCountryCode(es.mityc.appfacturae.facturae.CountryType.values()[Integer
								.parseInt(((ComboOption) jPanelInvoiceDetail.getJComboBoxPayDCoun().getSelectedItem()).getValue())]);
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr).setAddress(jPanelInvoiceDetail.getJTextFieldPayDAddr().getText());
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr)
							.setPostCodeAndTown((jPanelInvoiceDetail.getJTextFieldPayDPCod().getText() + " " + jPanelInvoiceDetail
								.getJTextFieldPayDCity().getText()).trim());
						((es.mityc.appfacturae.facturae32.OverseasAddressType) oaddr).setProvince(jPanelInvoiceDetail.getJTextFieldPayDProv().getText());
						at.setOverseasBranchAddress(oaddr);
					}
				}
				installment.setAccountToBeDebited(at);
			}
			installments.getInstallment().add(installment);
			((es.mityc.appfacturae.facturae321.InvoiceType) invoice).setPaymentDetails(installments);
		}

		
// Inerza S.A. - Añadir endosatario v321 - #0062026
		FactoringAssignmentDataType factoring = null;
        if(jPanelInvoiceDetail.getJTextFieldPayFactIBAN() != null && !"".equals(jPanelInvoiceDetail.getJTextFieldPayFactIBAN().getText())) {
        	factoring = new FactoringAssignmentDataType();
        	InstallmentType installmentFactoring = new InstallmentType();
        	AccountType at = new AccountType();
            at.setIban(jPanelInvoiceDetail.getJTextFieldPayFactIBAN().getText());
            
            AssigneeType assigneeType = new AssigneeType();
            TaxIdentificationType taxIdentFactoring = new TaxIdentificationType();
            if(jPanelInvoiceDetail.getJTextFieldPayFactNifSociedad() != null && !"".equals(jPanelInvoiceDetail.getJTextFieldPayFactNifSociedad().getText())) {
                taxIdentFactoring.setTaxIdentificationNumber(jPanelInvoiceDetail.getJTextFieldPayFactNifSociedad().getText());
                taxIdentFactoring.setPersonTypeCode(PersonTypeCodeType.J);
                taxIdentFactoring.setResidenceTypeCode(ResidenceTypeCodeType.R);
            }
            assigneeType.setTaxIdentification(taxIdentFactoring);
            LegalEntityType legalFactoring = new LegalEntityType();
            if(jPanelInvoiceDetail.getJTextFieldPayFactSociedad() != null && !"".equals(jPanelInvoiceDetail.getJTextFieldPayFactSociedad().getText())) {
                legalFactoring.setCorporateName(jPanelInvoiceDetail.getJTextFieldPayFactSociedad().getText());
            }
            if(Integer.parseInt(((ComboOption)jPanelInvoiceDetail.getJComboBoxPayFactCoun().getSelectedItem()).getValue()) == Constants.SPAINCODE31) {
                es.mityc.appfacturae.facturae.AddressType addressInSpainFactoring = new es.mityc.appfacturae.facturae32.AddressType();
                addressInSpainFactoring.setAddress(jPanelInvoiceDetail.getJTextFieldPayFactAddr().getText());
                addressInSpainFactoring.setPostCode(jPanelInvoiceDetail.getJTextFieldPayFactPCod().getText());
                addressInSpainFactoring.setTown(jPanelInvoiceDetail.getJTextFieldPayFactCity().getText());
                addressInSpainFactoring.setProvince(jPanelInvoiceDetail.getJTextFieldPayFactProv().getText());
                ((es.mityc.appfacturae.facturae32.AddressType)addressInSpainFactoring).setCountryCode(es.mityc.appfacturae.facturae.CountryType.ESP);
                legalFactoring.setAddressInSpain(addressInSpainFactoring);
            } else {
                es.mityc.appfacturae.facturae.OverseasAddressType addressOverseas = new es.mityc.appfacturae.facturae32.OverseasAddressType();
                addressOverseas.setAddress(jPanelInvoiceDetail.getJTextFieldPayFactAddr().getText());
                addressOverseas.setProvince(jPanelInvoiceDetail.getJTextFieldPayFactProv().getText());
                StringBuilder codeAndTown = new StringBuilder();
                if(jPanelInvoiceDetail.getJTextFieldPayFactPCod() != null && !"".equals(jPanelInvoiceDetail.getJTextFieldPayFactPCod().getText())) {
                    codeAndTown.append(jPanelInvoiceDetail.getJTextFieldPayFactPCod().getText());
                }
                if(jPanelInvoiceDetail.getJTextFieldPayFactCity() != null && !"".equals(jPanelInvoiceDetail.getJTextFieldPayFactCity().getText())) {
                    if(codeAndTown.length() > 0) {
                        codeAndTown.append(" ");
                    }
                    codeAndTown.append(jPanelInvoiceDetail.getJTextFieldPayFactCity().getText());
                }
                addressOverseas.setPostCodeAndTown(codeAndTown.toString());
                legalFactoring.setOverseasAddress(addressOverseas);
            }
            assigneeType.setLegalEntity(legalFactoring);
            factoring.setAssignee(assigneeType);
            if(jPanelInvoiceDetail.getJTextFieldPayFactClausula() != null && !"".equals(jPanelInvoiceDetail.getJTextFieldPayFactClausula().getText())) {
                factoring.setFactoringAssignmentClauses(jPanelInvoiceDetail.getJTextFieldPayFactClausula().getText());
            }
	        if(jPanelInvoiceDetail.getJComboBoxPayMean().getSelectedIndex() + 1 == 4) {
	            installmentFactoring.setAccountToBeCredited(at);
	        }
	        if(jPanelInvoiceDetail.getJComboBoxPayMean().getSelectedIndex() + 1 == 2) {
	            installmentFactoring.setAccountToBeDebited(at);
	        }
	        installmentFactoring.setCollectionAdditionalInformation(installment.getCollectionAdditionalInformation());
	        installmentFactoring.setDebitReconciliationReference(installment.getDebitReconciliationReference());
	        installmentFactoring.setInstallmentAmount(installment.getInstallmentAmount());
	        installmentFactoring.setInstallmentDueDate(installment.getInstallmentDueDate());
	        installmentFactoring.setPaymentMeans(installment.getPaymentMeans());
	        installmentFactoring.setPaymentReconciliationReference(installment.getPaymentReconciliationReference());
	        installmentFactoring.setRegulatoryReportingData(installment.getRegulatoryReportingData());
	        es.mityc.appfacturae.facturae32.InstallmentsType installmentsFactoring = new es.mityc.appfacturae.facturae32.InstallmentsType();
	        installmentsFactoring.getInstallment().add(installmentFactoring);
	        factoring.setPaymentDetails(installmentsFactoring);
        }
// Inerza S.A. - Añadir endosatario v321 - #0062026		
		
		
		invoice.setItems(jPanelInvoiceDetail.getItems());

		HashMap<String, Double[]> TaxesOutputsMap = new HashMap<String, Double[]>();
		HashMap<String, Double> TaxesWithheldMap = new HashMap<String, Double>();

		// Flag to indicate that the special taxable base is being used (it is the only way
		// to differentiate null from 0.0 specific value)
		boolean specialTBUsed = false;
		
		//20150330: Calculamos porcentaje de descuento
		double totalGrossAmount = it.getTotalGrossAmount();
		double discountAmount = it.getTotalGeneralDiscounts();
		double chargeAmount = it.getTotalGeneralSurcharges();
		
		// Se establece el porcentaje a descontar calculado a partir del total
		Double discountsTotalRate = (100 * discountAmount) / totalGrossAmount ;
		
		//Calculamos porcentaje de cargo
		Double chargesTotalRate = (100 * chargeAmount) / totalGrossAmount ;

		double biAmount = 0.0;
		int itemsCount = jPanelInvoiceDetail.getItems().getInvoiceLine().size();
		for (int i = 0; i < itemsCount; i++) {
			int taxesOutputsCount = 0;
			try {
				taxesOutputsCount = ((es.mityc.appfacturae.facturae321.InvoiceLineType) jPanelInvoiceDetail.getItems()
					.getInvoiceLine().get(i)).getTaxesOutputs().getTax().size();
			} catch (Exception e) {
				logger.error("Error getting taxes outputs: " + e.getMessage());
				return null;
			}
			
			// Nueva base imponible (tras aplicar cargos / descuentos globales)
			//biAmount = grossAmount - discountsTotal + chargesTotal;
			double lineGrossAmount = jPanelInvoiceDetail.getItems().getInvoiceLine().get(i).getGrossAmount();
			biAmount = lineGrossAmount - (discountsTotalRate / 100.00 * lineGrossAmount) + (chargesTotalRate / 100.00 * lineGrossAmount);
			biAmount = DoubleUtil.round(biAmount, Integer.parseInt(decimalProps.getProperty("item_gross_cost")));
			
			//ArrayList<Double> taxAmountList = new ArrayList<Double>();
			for (int j = 0; j < taxesOutputsCount; j++) {
				InvoiceLineType invoiceLine = (InvoiceLineType) jPanelInvoiceDetail.getItems().getInvoiceLine().get(i);
				InvoiceLineType.TaxesOutputs.Tax tax = invoiceLine.getTaxesOutputs().getTax().get(j);
				String key = "";
				if (tax.getEquivalenceSurcharge() != null)
					key = tax.getTaxTypeCode() + " - " + tax.getTaxRate() + " - " + tax.getEquivalenceSurcharge();
				else
					key = tax.getTaxTypeCode() + " - " + tax.getTaxRate() + " - 0.0";
				Double[] values = new Double[] { 0.0, 0.0 };
				values[0] = biAmount;
				if (tax.getSpecialTaxableBase() != null) {
					specialTBUsed = true;
					if (tax.getSpecialTaxableBase().getTotalAmount() != 0)
						values[1] = tax.getSpecialTaxableBase().getTotalAmount();
				}
				if (TaxesOutputsMap.containsKey(key)) {
					Double[] storeValue = (Double[]) TaxesOutputsMap.get(key);
					TaxesOutputsMap.put(key, new Double[] { storeValue[0] + values[0], storeValue[1] + values[1] });
				} else
					TaxesOutputsMap.put(key, values);
				
				//NUEVO
				/*
				// Se coge la cuota del impuesto.
				double cuota = tax.getTaxAmount().getTotalAmount() ;

				// Si hay cuota imponible especial se usará la cuota especial en lugar de la cuota del impuesto
				if (tax.getSpecialTaxAmount() != null && tax.getSpecialTaxAmount().getTotalAmount() != 0)
					cuota = tax.getSpecialTaxAmount().getTotalAmount();
				
				// En ambos casos se suma el recargo de equivalencia
				if (tax.getEquivalenceSurchargeAmount() != null)
					cuota += tax.getEquivalenceSurchargeAmount().getTotalAmount();

				
				// Calculamos el porcentaje de impuestos
				taxAmountList.add( cuota - (discountsTotalRate / 100.00 * cuota) + (chargesTotalRate / 100.00 * cuota));
				*/
				
			}
			if (jPanelInvoiceDetail.getItems().getInvoiceLine().get(i).getTaxesWithheld() != null) {
				int taxesWithheldCount = jPanelInvoiceDetail.getItems().getInvoiceLine().get(i).getTaxesWithheld()
					.getTax().size();
				for (int j = 0; j < taxesWithheldCount; j++) {
					TaxType tax = jPanelInvoiceDetail.getItems().getInvoiceLine().get(i).getTaxesWithheld().getTax()
						.get(j);
					String key = tax.getTaxTypeCode() + " - " + tax.getTaxRate();
					Double value = new Double(0.0);
					value = biAmount;
					if (TaxesWithheldMap.containsKey(key)) {
						Double storeValue = (Double) TaxesWithheldMap.get(key);
						TaxesWithheldMap.put(key, new Double(storeValue + value));
					} else
						TaxesWithheldMap.put(key, value);
				}
			}
		}

		Set<?> sTaxesOutputs = TaxesOutputsMap.keySet();
		es.mityc.appfacturae.facturae321.InvoiceLineType.TaxesOutputs.Tax tax = null;
		es.mityc.appfacturae.facturae.AmountType amount = null;

		es.mityc.appfacturae.facturae321.InvoiceType.TaxesOutputs taxesOutputsInvoice = new es.mityc.appfacturae.facturae321.InvoiceType.TaxesOutputs();

		Object key = null;
		for (Iterator<?> iterator = sTaxesOutputs.iterator(); iterator.hasNext();) {
			key = iterator.next();
			String keyStr = key.toString();
			String[] sTaxType = keyStr.split(" - ");
			Double[] values = (Double[]) TaxesOutputsMap.get(key);
			values[0] = DoubleUtil.roundWithPrecisionDouble(values[0], Integer.parseInt(decimalProps.getProperty("item_taxable_base")));
			tax = new es.mityc.appfacturae.facturae321.InvoiceLineType.TaxesOutputs.Tax();
			amount = new AmountType();
			amount.setTotalAmount(values[0]);
			tax.setTaxableBase(amount);
			amount = new AmountType();
			Double totalAmount = DoubleUtil.getDoubleFromPrettyNumber(sTaxType[1]) / 100 * values[0];
			amount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(totalAmount,Integer.parseInt(decimalProps.getProperty("taxesOutputs_totalAmount"))));
			tax.setTaxAmount(amount);
			if (specialTBUsed) {
				amount = new AmountType();
				amount.setTotalAmount(values[1]);
				tax.setSpecialTaxableBase(amount);
				amount = new AmountType();
				Double specialTaxableAmount = DoubleUtil.getDoubleFromPrettyNumber(sTaxType[1]) / 100 * values[1];
				amount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(specialTaxableAmount, Integer.parseInt(decimalProps.getProperty("special_taxable_amount"))));
				tax.setSpecialTaxAmount(amount);
			}
			if (!"0.0".equals(sTaxType[2])) {
				amount = new AmountType();
				if (values[1] != 0){
					totalAmount = DoubleUtil.getDoubleFromPrettyNumber(sTaxType[2]) / 100 * values[1];
					amount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(totalAmount,Integer.parseInt(decimalProps.getProperty("equivalence_surcharge_amount"))));
				}else{
					totalAmount = DoubleUtil.getDoubleFromPrettyNumber(sTaxType[2]) / 100 * values[0];
					amount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(totalAmount,Integer.parseInt(decimalProps.getProperty("equivalence_surcharge_amount"))));
				}
				tax.setEquivalenceSurchargeAmount(amount);
				tax.setEquivalenceSurcharge(DoubleUtil.getDoubleFromPrettyNumber(sTaxType[2]));
			}
			tax.setTaxTypeCode(sTaxType[0]);
			tax.setTaxRate(DoubleUtil.getDoubleFromPrettyNumber(sTaxType[1]));
			taxesOutputsInvoice.getTax().add(tax);
		}

		((es.mityc.appfacturae.facturae321.InvoiceType) invoice).setTaxesOutputs(taxesOutputsInvoice);

		Set<?> sTaxesWithheld = TaxesWithheldMap.keySet();
		TaxType taxW = null;
		TaxesType taxesWithheldInvoice = new TaxesType();
		for (Iterator<?> iterator = sTaxesWithheld.iterator(); iterator.hasNext();) {
			key = iterator.next();
			String keyStr = key.toString();
			String[] sTaxType = keyStr.split(" - ");
			Double value = DoubleUtil.roundWithPrecisionDouble((Double) TaxesWithheldMap.get(key), Integer.parseInt(decimalProps.getProperty("taxesWithheld_totalAmount")));

			taxW = new TaxType();
			amount = new AmountType();

			amount.setTotalAmount(value);
			taxW.setTaxableBase(amount);
			amount = new AmountType();
			Double totalAmount = DoubleUtil.getDoubleFromPrettyNumber(sTaxType[1]) / 100 * value;
			amount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(totalAmount,Integer.parseInt(decimalProps.getProperty("taxesWithheld_totalAmount"))));
			taxW.setTaxAmount(amount);

			taxW.setTaxTypeCode(sTaxType[0]);
			taxW.setTaxRate(DoubleUtil.getDoubleFromPrettyNumber(sTaxType[1]));

			taxesWithheldInvoice.getTax().add(taxW);
		}
		invoice.setTaxesWithheld(taxesWithheldInvoice);

		InvoicesType its = new InvoicesType();
		its.getInvoice().add(invoice);
		facturae.setInvoices(its);

		/** Facturae FileHeader **/
		FileHeaderType fht = new FileHeaderType();
		fht.setSchemaVersion(Constants.VERSION321);
		fht.setModality(ModalityType.I);
		fht.setInvoiceIssuerType(InvoiceIssuerTypeType.EM);
		fht.setFactoringAssignmentData(factoring);

		// Batch
		BatchType batch = new es.mityc.appfacturae.facturae32.BatchType();
		String batchIdentifier = facturae.getParties().getBuyerParty().getTaxIdentification()
			.getTaxIdentificationNumber();
		batchIdentifier = batchIdentifier
			+ facturae.getInvoices().getInvoice().get(0).getInvoiceHeader().getInvoiceNumber();
		String series = facturae.getInvoices().getInvoice().get(0).getInvoiceHeader().getInvoiceSeriesCode();
		if (series != null && !"".equals(series))
			batchIdentifier = batchIdentifier + series;
		batch.setBatchIdentifier(batchIdentifier);
		batch.setInvoicesCount(1);

		AmountType totalInvoicesAmount = new AmountType();
		Double dTotalInvoicesAmount = facturae.getInvoices().getInvoice().get(0).getInvoiceTotals().getInvoiceTotal();
		totalInvoicesAmount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(dTotalInvoicesAmount,
			Integer.parseInt(decimalProps.getProperty("batch_total_invoices_amount"))));
		batch.setTotalInvoicesAmount(totalInvoicesAmount);

		AmountType totalOutstandingAmount = new AmountType();
		Double dTotalOutstandingAmount = facturae.getInvoices().getInvoice().get(0).getInvoiceTotals().getTotalOutstandingAmount();
		totalOutstandingAmount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(dTotalOutstandingAmount,
			Integer.parseInt(decimalProps.getProperty("batch_total_outstanding_amount"))));
		batch.setTotalOutstandingAmount(totalOutstandingAmount);

		AmountType totalExecutableAmount = new AmountType();
		Double dTotalExecutableAmount = facturae.getInvoices().getInvoice().get(0).getInvoiceTotals().getTotalExecutableAmount();
		totalExecutableAmount.setTotalAmount(DoubleUtil.roundWithPrecisionDouble(dTotalExecutableAmount,
			Integer.parseInt(decimalProps.getProperty("batch_total_executable_amount"))));
		batch.setTotalExecutableAmount(totalExecutableAmount);
		((es.mityc.appfacturae.facturae32.BatchType) batch)
			.setInvoiceCurrencyCode(es.mityc.appfacturae.facturae.CurrencyCodeType.EUR);

		fht.setBatch(batch);

		facturae.setFileHeader(fht);

		/** Facturae Extension **/
		if (jPanelInvoiceDetail.getJTextAreaTOTXMLExtAddData().getText() != null
			&& !"".equals(jPanelInvoiceDetail.getJTextAreaTOTXMLExtAddData().getText().trim())) {
			ExtensionsType extension = new es.mityc.appfacturae.facturae32.ExtensionsType();
			InputStream isExt = null;
			try {
				isExt = new ByteArrayInputStream(jPanelInvoiceDetail.getJTextAreaTOTXMLExtAddData().getText()
					.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("Error creating a bytes array with extension data. The encoding is unsupported: "
					+ e.getMessage());
				return null;
			}
			try {
				NodeList nl = StreamUtil.createDoc(isExt).getChildNodes();
				for (int i = 0; i < nl.getLength(); i++)
					((es.mityc.appfacturae.facturae32.ExtensionsType) extension).getAny().add(nl.item(i));
				facturae.setExtensions(extension);
			} catch (ValidationException ve) {
				logger.error("A validation error occurred. The extension element is not valid: " + ve.getMessage());
				return null;
			}
		}

		mw.refreshProgressBar(25);
		return facturae;
	}

	public void jCheckBoxIsReceivedChangePerformed(boolean isReceived) {
		jButtonSign.setVisible(!isReceived);
		if (isReceived)
			jButtonSaveDraft.setText(Constants.LANG.getString("SaveReceived"));
		else
			jButtonSaveDraft.setText(Constants.LANG.getString("SaveDraft"));
	}

	private class ButtonCursor extends MouseAdapter {
		public void mouseEntered(java.awt.event.MouseEvent evt) {
			GenerateInvoice321Window.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		}

		public void mouseExited(java.awt.event.MouseEvent evt) {
			GenerateInvoice321Window.this
				.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
		}
	}

	private void jButtonHelpActionPerformed() {
		URL helpFile = this.getClass().getResource(
			"/html/help_GenerateInvoice321_" + Constants.LANG.getLocale().getLanguage() + ".html");
		if (helpFile == null) {
			Constants.DIALOG.showErrorHelp();
			return;
		}

		ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
		chd.setVisible(true);
		chd.dispose();
	}

	/** Show error message through the canvas object */
	public void showMessage(String message, Color color) {
		fd.showMessage(message, color);
	}

	/** Give the invoice identification */
	public String getInvoiceId() {
		return jPanelInvoiceGeneral.getJTextFieldSeries().getText()
			+ jPanelInvoiceGeneral.getJTextFieldNumber().getText();
	}

	/** Sets Dialog in rectified mode */
	public void setRectifyEnabled(String serie, String number) {
		rectIn = new String[] { serie, number };
	}

	/** Gives related panels */
	public InvoiceGeneralPanel getJPanelInvoiceGeneral() {
		return jPanelInvoiceGeneral;
	}

	public PartyPanel getJPanelIssuer() {
		return jPanelIssuer;
	}

	public PartyPanel getJPanelReceiver() {
		return jPanelReceiver;
	}

	public InvoiceDetail321Panel getJPanelInvoiceDetail() {
		return jPanelInvoiceDetail;
	}

	public JButton getJButtonSaveDraft() {
		return jButtonSaveDraft;
	}

	public JButton getJButtonSign() {
		return jButtonSign;
	}

	private int validateForm() {
		if (getJPanelInvoiceGeneral().getJCheckBoxIsReceived().isSelected()) {
			if (getJPanelInvoiceDetail().getJTableGlobAttc().getRowCount() <= 0) {
				fd.showMessage(Constants.LANG.getString("NOOKAttachmentRequired"), Constants.ERROR_MSG_COLOR);
				getJPanelInvoiceDetail().paintAndFocusAttcArea(true, Constants.BKG_ERROR_COLOR);
				return 0;
			}
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().getBackground())) {
			jPanelInvoiceGeneral.getJCalendarComboBoxExpDate().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceGeneral.getJCalendarComboBoxOpDate().getBackground())) {
			jPanelInvoiceGeneral.getJCalendarComboBoxOpDate().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodFrom().getBackground())) {
			jPanelInvoiceGeneral.getJCalendarComboBoxPeriodFrom().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceGeneral.getJCalendarComboBoxPeriodTo().getBackground())) {
			jPanelInvoiceGeneral.getJCalendarComboBoxPeriodTo().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceDetail.getJCalendarComboBoxPaymDate().getBackground())) {
			jPanelInvoiceDetail.getJCalendarComboBoxPaymDate().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceDetail.getJTextFieldResults()[9].getForeground())) {
			jPanelInvoiceDetail.getJTextFieldResults()[9].setForeground(Constants.FONT_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceDetail.getJTextFieldWithItem().getBackground())) {
			jPanelInvoiceDetail.getJTextFieldWithItem().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceDetail.getJTextFieldWithPerc().getBackground())) {
			jPanelInvoiceDetail.getJTextFieldWithPerc().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (Constants.BKG_ERROR_COLOR.equals(jPanelInvoiceDetail.getJTextFieldWithAmou().getBackground())) {
			jPanelInvoiceDetail.getJTextFieldWithAmou().setBackground(Constants.BKG_MAIN_COLOR);
			return 0;
		}
		if (jPanelInvoiceGeneral.getJTextFieldPlDesc() != null
			&& !"".equals(jPanelInvoiceGeneral.getJTextFieldPlDesc().getText())) {
			if (jPanelInvoiceGeneral.getJTextFieldPlPCode() != null
				&& !"".equals(jPanelInvoiceGeneral.getJTextFieldPlPCode().getText())) {
			} else {
				jPanelInvoiceGeneral.getJTextFieldPlPCode().setBackground(Constants.BKG_ERROR_COLOR);
				return 0;
			}
		}
		jPanelInvoiceDetail.getJButtonTOTXMLAddData().doClick();
		if (jPanelInvoiceDetail.getJButtonTOTXMLAddDataZoom().getIcon().toString()
			.contains("button_mini_zoom_error.jpg")) {
			jPanelInvoiceDetail.getJButtonTOTXMLAddDataZoom().requestFocus();
			return 0;
		}
		jPanelInvoiceDetail.getJButtonXMLAddData().doClick();
		if (jPanelInvoiceDetail.getJButtonXMLAddDataZoom().getIcon().toString().contains("button_mini_zoom_error.jpg")) {
			jPanelInvoiceDetail.getJButtonXMLAddDataZoom().requestFocus();
			((JTabbedPane) jPanelInvoiceDetail.getJButtonXMLAddDataZoom().getParent().getParent())
				.setSelectedComponent(jPanelInvoiceDetail.getJButtonXMLAddDataZoom().getParent());
			return 0;
		}
		boolean flag = !jPanelInvoiceDetail.validateInstallment();
		if (jPanelIssuer.getJComboBoxItems().getSelectedItem() == null) {
			jPanelIssuer.getJComboBoxItems().setBackground(Constants.BKG_ERROR_COLOR);
			flag = true;
		}
		if (jPanelReceiver.getJComboBoxItems().getSelectedItem() == null) {
			jPanelReceiver.getJComboBoxItems().setBackground(Constants.BKG_ERROR_COLOR);
			flag = true;
		}
		if (flag)
			return -1;
		else
			return 1;
	}

	// JButtons
	private JButton jButtonSaveDraft = null;
	private JButton jButtonSign = null;
	private JButton jButtonHelp = null;
	private JButton jButtonReturn = null;
	// JLabels
	private JLabel jLabelTopBarImg = null;
	private JLabel jLabelTopBarMsg1 = null;
	private JLabel jLabelTopBarMsg2 = null;
	// InvoiceGeneralPanel
	private InvoiceGeneralPanel jPanelInvoiceGeneral = null;
	// PartyPanels
	private PartyPanel jPanelIssuer = null;
	private PartyPanel jPanelReceiver = null;
	// InvoiceDetailPanel
	private InvoiceDetail321Panel jPanelInvoiceDetail = null;
	public Boolean specialTB = false;
	// JPanels
	private JPanel mainPanel = null;
	private JPanel jPanelTopBar = null;

	private Image imgLogoApp = null;

	private DefaultComboBoxModel dcbmInd = null;
	private DefaultComboBoxModel dcbmLEn = null;
	private CoupledComboModel ccmIssuerInd = null;
	private CoupledComboModel ccmIssuerLEn = null;
	private CoupledComboModel ccmReceiverInd = null;
	private CoupledComboModel ccmReceiverLEn = null;
}
