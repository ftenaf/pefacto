/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-APC".
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto sean aprobadas por
 * la Comisión Europea– versiones posteriores de la EUPL (la Licencia); Solo
 * podrá usarse esta obra si se respeta la Licencia.
 *
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Salvo cuando lo exija la legislación aplicable o se acuerde por escrito, el
 * programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL», SIN
 * GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas. Véase la
 * Licencia en el idioma concreto que rige los permisos y limitaciones que
 * establece la Licencia.
 */
package es.mityc.appfacturae.ui.windows.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.jdesktop.layout.GroupLayout;
import org.xml.sax.SAXException;

import es.mityc.appfacturae.facturae.InvoiceLineType;
import es.mityc.appfacturae.facturae.ItemsType;
import es.mityc.appfacturae.facturae.TaxType;
import es.mityc.appfacturae.facturae32.InvoiceLineType.TaxesOutputs.Tax;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.ui.components.NoEdiTableModel;
import es.mityc.appfacturae.ui.dialogs.InputAttachmentDialog;
import es.mityc.appfacturae.ui.dialogs.InputChargeDialog;
import es.mityc.appfacturae.ui.dialogs.InputDiscountDialog;
import es.mityc.appfacturae.ui.dialogs.InputExtensionDialog;
import es.mityc.appfacturae.ui.dialogs.InputReimDialog;
import es.mityc.appfacturae.ui.dialogs.Invoice32DetailDialog;
import es.mityc.appfacturae.ui.renderers.ComboBoxRenderer;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.ui.windows.GenerateInvoice32Window;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.XMLFacturaeUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.ComboUtil;
import es.mityc.appfacturae.utils.io.DoubleUtil;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.ValidatorUtil;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class InvoiceDetail32Panel extends JPanel {
	private static final long serialVersionUID = 7147555204705934069L;
	private Properties decimalProps = null;
    private static final String codeVersion = Constants.FACTURAE32;
    private static Log logger = LogFactory.getLog(InvoiceDetail32Panel.class);

    private boolean setVisualizationOnly = false;

    public InvoiceDetail32Panel() {
        super();
        decimalProps = new Properties();
        loadDecimalConfiguration();
        loadDBData();
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     */
    private void initComponents() {

        jTabbedPaneGlobal = new JTabbedPane();
        jPanelGlbDiscounts = new JPanel();
        jScrollPaneGlobDisc = new JScrollPane();
        jTableGlobDisc = new JTable();
        jTextFieldTotalDisc = new JTextField();
        jLabelTotalDisc = new JLabel();
        jButtonDiscAdd = new JButton();
        jButtonDiscDel = new JButton();
        jPanelGlbReimbExp = new JPanel();
        jScrollPaneGlobReim = new JScrollPane();
        jTableGlobReim = new JTable();
        jLabelTotalReim = new JLabel();
        jTextFieldTotalReim = new JTextField();
        jButtonReimAdd = new JButton();
        jButtonReimDel = new JButton();
        jPanelGlbCharges = new JPanel();
        jScrollPaneGlobChar = new JScrollPane();
        jTableGlobChar = new JTable();
        jButtonCharAdd = new JButton();
        jButtonCharDel = new JButton();
        jLabelTotalChar = new JLabel();
        jTextFieldTotalChar = new JTextField();
        jPanelGlbAttchDoc = new JPanel();
        jScrollPaneGlobAttc = new JScrollPane();
        jTableGlobAttc = new JTable();
        jButtonAttcAdd = new JButton();
        jButtonAttcDel = new JButton();
        jTextAreaXMLExtAddData = new JTextArea();
        jLabelLegalLiteral = new JLabel();
        jTextAreaLegal = new JTextArea();
		jComboBoxLegalLiterals = new JComboBox();
        jButtonXMLAddData = new JButton();
        jButtonXMLAddDataZoom = new JButton();
        jLabelXMLAddData = new JLabel();
        jScrollPaneXMLAddData = new JScrollPane();
        jPanelGlbPayment = new JPanel();
        jTabbedPaneGlbPayment = new JTabbedPane();
        jPanelGlbPaymentGen2 = new JPanel();
        jLabelTotalPayDate = new JLabel();
        jLabelTotalPayAmou = new JLabel();
        jTextFieldPaymentAmo = new JTextField();
        jLabelTotalPayMean = new JLabel();
        jComboBoxPayMean = new JComboBox();
        jLabelTotalPayRefIssuer = new JLabel();
        jLabelTotalPayRefReceiver = new JLabel();
        jTextFieldPaymentRefIssuer = new JTextField();
        jTextFieldPaymentRefReceiver = new JTextField();
        jCalendarComboBoxPaymDate = new de.wannawork.jcalendar.JCalendarComboBox();
        jLabelPayment = new JLabel();
        jComboBoxPayment = new JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jTextAreaPayExist = new JTextArea();
        jPanelGlbPaymentAccDebited = new JPanel();
        jLabelTotalPayDIBAN = new JLabel();
        jLabelTotalPayDBank = new JLabel();
        jLabelTotalPayDBran = new JLabel();
        jTextFieldPayDIBAN = new JTextField();
        jTextFieldPayDBank = new JTextField();
        jTextFieldPayDBran = new JTextField();
        jLabelTotalPayDAddr = new JLabel();
        jLabelTotalPayDPCod = new JLabel();
        jTextFieldPayDAddr = new JTextField();
        jTextFieldPayDPCod = new JTextField();
        jLabelTotalPayDCity = new JLabel();
        jLabelTotalPayDProv = new JLabel();
        jLabelTotalPayDCoun = new JLabel();
        jTextFieldPayDCity = new JTextField();
        jTextFieldPayDProv = new JTextField();
        jComboBoxPayDCoun = new JComboBox();
        jComboBoxPayAccountD = new JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jTextAreaAccExist = new JTextArea();
        jPanelGlbPaymentAccCharged = new JPanel();
        jLabelTotalPayCIBAN = new JLabel();
        jTextFieldPayCIBAN = new JTextField();
        jTextFieldPayCBank = new JTextField();
        jLabelTotalPayCBank = new JLabel();
        jTextFieldPayCBran = new JTextField();
        jLabelTotalPayCBran = new JLabel();
        jLabelTotalPayCAddr = new JLabel();
        jTextFieldPayCAddr = new JTextField();
        jLabelTotalPayCCity = new JLabel();
        jLabelTotalPayCPCod = new JLabel();
        jTextFieldPayCPCod = new JTextField();
        jLabelTotalPayCProv = new JLabel();
        jTextFieldPayCProv = new JTextField();
        jTextFieldPayCCity = new JTextField();
        jLabelTotalPayCCoun = new JLabel();
        jComboBoxPayCCoun = new JComboBox();
        jComboBoxPayAccountC = new JComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jTextAreaAccExist2 = new JTextArea();
        jPanelGlbPaymentAccFactoring = new JPanel();
        jLabelTotalPayFactIBAN = new JLabel();
        jTextFieldPayFactIBAN = new JTextField();
        jTextFieldPayFactSociedad = new JTextField();
        jLabelTotalPayFactSociedad = new JLabel();
        jTextFieldPayFactNifSociedad = new JTextField();
        jLabelTotalPayFactNifSociedad = new JLabel();
        jLabelTotalPayFactAddr = new JLabel();
        jTextFieldPayFactAddr = new JTextField();
        jLabelTotalPayFactCity = new JLabel();
        jLabelTotalPayFactPCod = new JLabel();
        jTextFieldPayFactPCod = new JTextField();
        jLabelTotalPayFactClausula = new JLabel();
        jTextFieldPayFactClausula = new JTextField();
        jLabelTotalPayFactProv = new JLabel();
        jTextFieldPayFactProv = new JTextField();
        jTextFieldPayFactCity = new JTextField();
        jLabelTotalPayFactCoun = new JLabel();
        jComboBoxPayFactCoun = new JComboBox();
        jComboBoxPayAccountFactoring = new JComboBox();
        jSeparatorFactoring = new javax.swing.JSeparator();
        jTextAreaAccExistFactoring = new JTextArea();
        jPanelGlbWhithhelds = new JPanel();
        jLabelTotalWithItem = new JLabel();
        jTextFieldWithItem = new JTextField();
        jLabelTotalWithAmou = new JLabel();
        jTextFieldWithAmou = new JTextField();
        jTextFieldWithPerc = new JTextField();
        jLabelTotalWithPerc = new JLabel();
        jPanelGlbLiterals = new JPanel();
        jScrollPaneLitList = new JScrollPane();
		jScrollPaneLitEdit = new JScrollPane();
		jScrollPaneLitDest = new JScrollPane();
        jListLiterals = new JList();
		if(getLiteralsListDestModel()==null) 
			setLiteralsListDestModel(new DefaultListModel());
		jListLiteralsDest = new JList(getLiteralsListDestModel());

		jButtonLitItemAdd = new JButton();
		jButtonLitItemRemove = new JButton();

        jScrollPaneItems = new JScrollPane();
        jTableItems = new JTable();

        jLabelTOTTaxWith = new JLabel();
        jLabelTOTMinus2 = new JLabel();
        jLabelTOTEqual2 = new JLabel();
        jLabelTOTInvTotal = new JLabel();
        jLabelTOTGrossAmount = new JLabel();
        jLabelTOTMinus1 = new JLabel();
        jLabelTOTDiscounts = new JLabel();
        jLabelTOTTaxBase2 = new JLabel();
        jLabelTOTInvTotal2 = new JLabel();
        jLabelTOTGlobWith = new JLabel();
        jLabelTOTTaxOut = new JLabel();
        jLabelTOTPlus3 = new JLabel();
        jLabelTOTReimb = new JLabel();
        jLabelTOTCharges = new JLabel();
        jLabelTOTPlus1 = new JLabel();
        jLabelTOTEqual1 = new JLabel();
        jLabelTOTTaxBase = new JLabel();
        jLabelTOTFinancial = new JLabel();
        jLabelTOTPlus4 = new JLabel();
        jLabelTOTInvExec = new JLabel();
        jLabelTOTEqual3 = new JLabel();
        jLabelTOTALS = new JLabel();
        jLabelTOTPlus2 = new JLabel();
        jLabelTOTMinus3 = new JLabel();
        jButtonItemAdd = new JButton();
        jButtonItemDel = new JButton();
        jLabelTOTAsterisk = new JLabel();
        jLabelTOTMessage = new JLabel();
        jTextAreaTOTXMLExtAddData = new JTextArea();
        jButtonTOTXMLAddData = new JButton();
        jButtonTOTXMLAddDataZoom = new JButton();
        jLabelTOTXMLAddData = new JLabel();
        jScrollPaneTOTXMLAddData = new JScrollPane();

        items = new ItemsType();

        if (accounts == null) {
            accounts = new DefaultComboBoxModel();
        }
        if (accounts2 == null) {
            accounts2 = new DefaultComboBoxModel();
        }
        if (accounts3 == null) {
            accounts3 = new DefaultComboBoxModel();
        }

        jLabelCurrency = new JLabel[10];
        String currency = "";
        if (Constants.APP_PROP.getProperty("Currency").equals("EUR")) {
            currency = Constants.LANG.getString("EuroSimbol");
        }
        for (int i = 0; i < jLabelCurrency.length; i++) {
            jLabelCurrency[i] = new JLabel();
            jLabelCurrency[i].setText(currency);
            jLabelCurrency[i].setFont(Constants.FONT_PLAIN);
            jLabelCurrency[i].setForeground(Constants.FONT_COLOR);
        }

        jLabelTCurrency = new JLabel();
        jLabelTCurrency.setText(currency);
        jLabelTCurrency.setFont(Constants.BUTTON_FONT);
        jLabelTCurrency.setForeground(Constants.FONT_COLOR);

        jTextFieldResults = new JTextField[11];
        for (int i = 0; i < jTextFieldResults.length; i++) {
            jTextFieldResults[i] = new JTextField();
        }

        jTabbedPaneGlobal.setBackground(Constants.BKG_MAIN_COLOR);

        jPanelGlbDiscounts.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneGlobDisc.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableGlobDisc.setModel(new NoEdiTableModel(
                new Object[][]{},
                new String[]{
                    Constants.LANG.getString("Item"),
                    Constants.LANG.getString("Rate"),
                    Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")"
                },
                new int[]{}
        ));

        for (int i = 0; i < jTableGlobDisc.getColumnCount(); i++) {
            jTableGlobDisc.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }
        jTableGlobDisc.setGridColor(Constants.SELECTION_COLOR);
        jScrollPaneGlobDisc.setViewportView(jTableGlobDisc);
        jTableGlobDisc.getTableHeader().setReorderingAllowed(false);
        jTableGlobDisc.getColumnModel().getColumn(0).setResizable(false);
        jTableGlobDisc.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobDisc.getColumnModel().getColumn(1).setResizable(false);
        jTableGlobDisc.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableGlobDisc.getColumnModel().getColumn(2).setResizable(false);
        jTableGlobDisc.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));

        jTextFieldTotalDisc.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldTotalDisc.setEditable(false);
        jTextFieldTotalDisc.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelTotalDisc.setFont(Constants.FONT_PLAIN);
        jLabelTotalDisc.setForeground(Constants.FONT_COLOR);
        jLabelTotalDisc.setText(Constants.LANG.getString("TotalDiscount") + " (" + Constants.LANG.getString("EuroSimbol") + ")");

        jButtonDiscAdd.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus.jpg")));
        jButtonDiscAdd.setToolTipText(Constants.LANG.getString("Add"));
        jButtonDiscAdd.setBorderPainted(false);
        jButtonDiscAdd.setContentAreaFilled(false);
        jButtonDiscAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonDiscAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDiscount(evt);
            }
        });

        jButtonDiscDel.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus.jpg")));
        jButtonDiscDel.setToolTipText(Constants.LANG.getString("Remove"));
        jButtonDiscDel.setBorderPainted(false);
        jButtonDiscDel.setContentAreaFilled(false);
        jButtonDiscDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonDiscDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delDiscount(evt);
            }
        });

        jTextFieldWithItem.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
                ((JTextField) arg0.getSource()).setForeground(Constants.FONT_COLOR);
            }
        });

        jTextFieldWithItem.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = jTextFieldWithItem.getText();
                String value3 = jTextFieldWithAmou.getText();

                if (value != null && !"".equals(value.trim())) {
                    if (value3 == null || "".equals(value3.trim())) {
                        jTextFieldWithAmou.setBackground(Constants.BKG_ERROR_COLOR);
                        ((GenerateInvoice32Window) jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("ParameterRequired"), Constants.ERROR_MSG_COLOR);
                    } else {
                        // Amount may be a 2500 max. String
                        if (value == null || value.length() > 2500) {
                            ((JTextField) evt.getSource()).setText("");
                            ((GenerateInvoice32Window) jTextFieldWithItem.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                        }
                    }
                } else if (value3 != null && !"".equals(value3.trim())) {
                    jTextFieldWithItem.setBackground(Constants.BKG_ERROR_COLOR);
                    ((GenerateInvoice32Window) jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("ParameterRequired"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jTextFieldWithPerc.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
                ((JTextField) arg0.getSource()).setForeground(Constants.FONT_COLOR);
            }
        });

        jTextFieldWithPerc.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = jTextFieldWithItem.getText();
                String value2 = ((JTextField) evt.getSource()).getText().trim();
                if (value2 != null && !"".equals(value2)) {
                    Double doubleObj = null;
                    try {
                        doubleObj = Double.parseDouble(value2);
                        if (doubleObj.doubleValue() > 100) {
                            doubleObj = 100.0000;
                            ((GenerateInvoice32Window) jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                        } else if (doubleObj.doubleValue() < 0) {
                            doubleObj = 0.0;
                            ((GenerateInvoice32Window) jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNegative"), Constants.ERROR_MSG_COLOR);
                        }
                        ((JTextField) evt.getSource()).setText(String.valueOf(DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_charge_rate")))));
                    } catch (NumberFormatException nfe) {
                        ((JTextField) evt.getSource()).setText("");
                        ((GenerateInvoice32Window) jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                        return;
                    }

                    // Total cost
                    String totalMsg = jTextFieldResults[6].getText();
                    if (totalMsg != null && !"".equals(totalMsg)) {
                        Double total = Double.parseDouble(totalMsg);
                        doubleObj = (total * doubleObj.doubleValue()) / 100;

    					// Negative invoices are allowed
                        // if (doubleObj.doubleValue() < 0){
                        // 		doubleObj = 0.0;
                        // 		((GenerateInvoice32Window)jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNegative"), Constants.ERROR_MSG_COLOR);
                        // }
                        jTextFieldWithAmou.setText(String.valueOf(DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_tax_With")))));
                        jTextFieldWithAmou.setBackground(Color.white);
                        jTextFieldResults[7].setText(String.valueOf(DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_tax_With")))));
                    } else {
                        jTextFieldWithPerc.setText("");
                        jTextFieldWithAmou.setText("");
                        jTextFieldResults[7].setText("");
                        ((GenerateInvoice32Window) jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageAmountRequired"), Constants.ERROR_MSG_COLOR);
                    }
                    recalculateTotals();
                    if (value == null || "".equals(value.trim())) {
                        jTextFieldWithItem.setBackground(Constants.BKG_ERROR_COLOR);
                        ((GenerateInvoice32Window) jTextFieldWithPerc.getTopLevelAncestor()).showMessage(Constants.LANG.getString("ParameterRequired"), Constants.ERROR_MSG_COLOR);
                    }
                }
            }
        });

        jTextFieldWithAmou.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
                ((JTextField) arg0.getSource()).setForeground(Constants.FONT_COLOR);
            }
        });

        jTextFieldWithAmou.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = jTextFieldWithItem.getText();
                String value3 = jTextFieldWithAmou.getText();

                if (value != null && !"".equals(value.trim())
                        && (value3 == null || "".equals(value3.trim()))) {
                    jTextFieldWithAmou.setBackground(Constants.BKG_ERROR_COLOR);
                    ((GenerateInvoice32Window) jTextFieldWithAmou.getTopLevelAncestor()).showMessage(Constants.LANG.getString("ParameterRequired"), Constants.ERROR_MSG_COLOR);
                } else if (value3 != null && !"".equals(value3.trim())) {
                    if (value == null || "".equals(value.trim())) {
                        jTextFieldWithItem.setBackground(Constants.BKG_ERROR_COLOR);
                        ((GenerateInvoice32Window) jTextFieldWithAmou.getTopLevelAncestor()).showMessage(Constants.LANG.getString("ParameterRequired"), Constants.ERROR_MSG_COLOR);
                    }

                    // Value should be a two decimals double
                    Double doubleObj = null;
                    try {
                        doubleObj = Double.parseDouble(value3);
                        ((JTextField) evt.getSource()).setText(String.valueOf(DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("global_withheld")))));
                        jTextFieldResults[7].setText(String.valueOf(DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("global_withheld")))));
                    } catch (NumberFormatException nfe) {
                        if (((JTextField) evt.getSource()).getText() != null
                                && !"".equals(((JTextField) evt.getSource()).getText().trim())) {
                            ((JTextField) evt.getSource()).setText("");
                            ((GenerateInvoice32Window) jTextFieldWithAmou.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                        }
                        return;
                    }

                    // Relative withheld clean up if there is no correspondence
                    String value2 = jTextFieldWithPerc.getText();
                    if (value2 != null && !"".equals(value2.trim())) {
                        Double rateReaded = Double.parseDouble(value2); // %

                        String totalMsg = jTextFieldResults[6].getText();   // Total cost
                        Double total = null;
                        if (totalMsg != null && !"".equals(totalMsg)) {
                            total = Double.parseDouble(totalMsg);
                        }

                        Double amountExpected = (total * rateReaded.doubleValue()) / 100;   // Amount expected
                        amountExpected = DoubleUtil.round(amountExpected, Integer.parseInt(decimalProps.getProperty("global_withheld")));

                        Double amountReaded = null;
                        amountReaded = Double.parseDouble(value3);  // Amount readed

                        if (amountReaded != null && amountReaded.compareTo(amountExpected) != 0) {
                            jTextFieldWithPerc.setText("");
                        }
                    }
                } else {
                    jTextFieldWithItem.setText("");
                    jTextFieldWithPerc.setText("");
                    jTextFieldResults[7].setText("");
                }
                recalculateTotals();
            }

        });

        org.jdesktop.layout.GroupLayout jPanel21Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbDiscounts);
        jPanelGlbDiscounts.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
                jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel21Layout.createSequentialGroup()
                                .add(jLabelTotalDisc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldTotalDisc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jScrollPaneGlobDisc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 887, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(5, 5, 5)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jButtonDiscDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .add(jButtonDiscAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
        );
        jPanel21Layout.setVerticalGroup(
                jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jScrollPaneGlobDisc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel21Layout.createSequentialGroup()
                                .add(jButtonDiscAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jButtonDiscDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(3, 3, 3)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                        .add(jTextFieldTotalDisc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelTotalDisc))
                .addContainerGap());

        jTabbedPaneGlobal.addTab(Constants.LANG.getString("Discounts"), jPanelGlbDiscounts);

        jPanelGlbReimbExp.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneGlobReim.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableGlobReim.setModel(new NoEdiTableModel(
                new Object[][]{},
                new String[]{
                    Constants.LANG.getString("IssuerType"),
                    Constants.LANG.getString("ResidenceType"),
                    Constants.LANG.getString("TaxIdentificationNumber"),
                    Constants.LANG.getString("ReceiverType"),
                    Constants.LANG.getString("ResidenceType"),
                    Constants.LANG.getString("TaxIdentificationNumber"),
                    Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")"
                },
                new int[]{}
        ));

        for (int i = 0; i < jTableGlobReim.getColumnCount(); i++) {
            jTableGlobReim.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }
        jTableGlobReim.setGridColor(Constants.SELECTION_COLOR);
        jScrollPaneGlobReim.setViewportView(jTableGlobReim);
        jTableGlobReim.getTableHeader().setReorderingAllowed(false);
        jTableGlobReim.getColumnModel().getColumn(0).setResizable(false);
        jTableGlobReim.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobReim.getColumnModel().getColumn(1).setResizable(false);
        jTableGlobReim.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobReim.getColumnModel().getColumn(2).setResizable(false);
        jTableGlobReim.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobReim.getColumnModel().getColumn(3).setResizable(false);
        jTableGlobReim.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobReim.getColumnModel().getColumn(4).setResizable(false);
        jTableGlobReim.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobReim.getColumnModel().getColumn(5).setResizable(false);
        jTableGlobReim.getColumnModel().getColumn(5).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobReim.getColumnModel().getColumn(6).setResizable(false);
        jTableGlobReim.getColumnModel().getColumn(6).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));

        jLabelTotalReim.setFont(Constants.FONT_PLAIN);
        jLabelTotalReim.setForeground(Constants.FONT_COLOR);
        jLabelTotalReim.setText(Constants.LANG.getString("TotalReimbursableExpenses") + " (" + Constants.LANG.getString("EuroSimbol") + ")");

        jTextFieldTotalReim.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldTotalReim.setEditable(false);
        jTextFieldTotalReim.setBackground(Constants.BKG_MAIN_COLOR);

        jButtonReimAdd.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus.jpg")));
        jButtonReimAdd.setToolTipText(Constants.LANG.getString("Add"));
        jButtonReimAdd.setBorderPainted(false);
        jButtonReimAdd.setContentAreaFilled(false);
        jButtonReimAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonReimAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addReim(evt);
            }
        });

        jButtonReimDel.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus.jpg")));
        jButtonReimDel.setToolTipText(Constants.LANG.getString("Remove"));
        jButtonReimDel.setBorderPainted(false);
        jButtonReimDel.setContentAreaFilled(false);
        jButtonReimDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonReimDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delReim(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel23Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbReimbExp);
        jPanelGlbReimbExp.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
                jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel23Layout.createSequentialGroup()
                                .add(jLabelTotalReim, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldTotalReim, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jScrollPaneGlobReim, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 887, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(5, 5, 5)
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jButtonReimDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .add(jButtonReimAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
        );
        jPanel23Layout.setVerticalGroup(
                jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jScrollPaneGlobReim, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel23Layout.createSequentialGroup()
                                .add(jButtonReimAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jButtonReimDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(3, 3, 3)
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                        .add(jTextFieldTotalReim, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelTotalReim))
                .addContainerGap());

        jTabbedPaneGlobal.addTab(Constants.LANG.getString("ReimbursableExpenses"), jPanelGlbReimbExp);

        jPanelGlbCharges.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneGlobChar.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableGlobChar.setModel(new NoEdiTableModel(
                new Object[][]{},
                new String[]{
                    Constants.LANG.getString("Item"),
                    Constants.LANG.getString("Rate"),
                    Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")"
                },
                new int[]{}
        ));
        for (int i = 0; i < jTableGlobChar.getColumnCount(); i++) {
            jTableGlobChar.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }
        jTableGlobChar.setGridColor(Constants.SELECTION_COLOR);
        jScrollPaneGlobChar.setViewportView(jTableGlobChar);
        jTableGlobChar.getTableHeader().setReorderingAllowed(false);
        jTableGlobChar.getColumnModel().getColumn(0).setResizable(false);
        jTableGlobChar.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobChar.getColumnModel().getColumn(1).setResizable(false);
        jTableGlobChar.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableGlobChar.getColumnModel().getColumn(2).setResizable(false);
        jTableGlobChar.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));

        jButtonCharAdd.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus.jpg")));
        jButtonCharAdd.setToolTipText(Constants.LANG.getString("Add"));
        jButtonCharAdd.setBorderPainted(false);
        jButtonCharAdd.setContentAreaFilled(false);
        jButtonCharAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonCharAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCharge(evt);
            }
        });

        jButtonCharDel.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus.jpg")));
        jButtonCharDel.setToolTipText(Constants.LANG.getString("Remove"));
        jButtonCharDel.setBorderPainted(false);
        jButtonCharDel.setContentAreaFilled(false);
        jButtonCharDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonCharDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delCharge(evt);
            }
        });

        jLabelTotalChar.setFont(Constants.FONT_PLAIN);
        jLabelTotalChar.setForeground(Constants.FONT_COLOR);
        jLabelTotalChar.setText(Constants.LANG.getString("TotalCharges") + " (" + Constants.LANG.getString("EuroSimbol") + ")");

        jTextFieldTotalChar.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldTotalChar.setEditable(false);
        jTextFieldTotalChar.setBackground(Constants.BKG_MAIN_COLOR);

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbCharges);
        jPanelGlbCharges.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
                jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel15Layout.createSequentialGroup()
                                .add(jLabelTotalChar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldTotalChar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jScrollPaneGlobChar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 887, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(5, 5, 5)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jButtonCharDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .add(jButtonCharAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
        );
        jPanel15Layout.setVerticalGroup(
                jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jScrollPaneGlobChar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel15Layout.createSequentialGroup()
                                .add(jButtonCharAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jButtonCharDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(3, 3, 3)
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                        .add(jTextFieldTotalChar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelTotalChar))
                .addContainerGap());

        jTabbedPaneGlobal.addTab(Constants.LANG.getString("Charges"), jPanelGlbCharges);

        jPanelGlbAttchDoc.setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneGlobAttc.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jTableGlobAttc.setModel(new NoEdiTableModel(
                new Object[][]{},
                new String[]{
                    Constants.LANG.getString("Format"),
                    Constants.LANG.getString("Path"),
                    Constants.LANG.getString("Description"),
                    Constants.LANG.getString("IncludeInXML")
                },
                new int[]{}
        ));
        for (int i = 0; i < jTableGlobAttc.getColumnCount(); i++) {
            jTableGlobAttc.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }
        jTableGlobAttc.setGridColor(Constants.SELECTION_COLOR);
        jScrollPaneGlobAttc.setViewportView(jTableGlobAttc);
        jTableGlobAttc.getTableHeader().setReorderingAllowed(false);
        jTableGlobAttc.getColumnModel().getColumn(0).setResizable(false);
        jTableGlobAttc.getColumnModel().getColumn(0).setMaxWidth(65);
        jTableGlobAttc.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobAttc.getColumnModel().getColumn(1).setResizable(false);
        jTableGlobAttc.getColumnModel().getColumn(1).setMaxWidth(190);
        jTableGlobAttc.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobAttc.getColumnModel().getColumn(2).setResizable(false);
        jTableGlobAttc.getColumnModel().getColumn(2).setMaxWidth(190);
        jTableGlobAttc.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableGlobAttc.getColumnModel().getColumn(3).setResizable(false);
        jTableGlobAttc.getColumnModel().getColumn(3).setMaxWidth(85);
        jTableGlobAttc.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));

        jButtonAttcAdd.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus.jpg")));
        jButtonAttcAdd.setToolTipText(Constants.LANG.getString("Add"));
        jButtonAttcAdd.setBorderPainted(false);
        jButtonAttcAdd.setContentAreaFilled(false);
        jButtonAttcAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonAttcAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAttachment(evt);
            }
        });

        jButtonAttcDel.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus.jpg")));
        jButtonAttcDel.setToolTipText(Constants.LANG.getString("Remove"));
        jButtonAttcDel.setBorderPainted(false);
        jButtonAttcDel.setContentAreaFilled(false);
        jButtonAttcDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonAttcDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delAttachment(evt);
            }
        });

        jTextAreaXMLExtAddData.setSize(100, 10);
        jTextAreaXMLExtAddData.setFont(Constants.FONT_PLAIN);
        jTextAreaXMLExtAddData.setForeground(Constants.FONT_COLOR);
        jTextAreaXMLExtAddData.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
        jTextAreaXMLExtAddData.setEditable(true);
        jScrollPaneXMLAddData.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneXMLAddData.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
        jScrollPaneXMLAddData.setViewportView(jTextAreaXMLExtAddData);

        jButtonXMLAddData.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_accept.jpg")));
        jButtonXMLAddData.setToolTipText(Constants.LANG.getString("Verify"));
        jButtonXMLAddData.setBorderPainted(false);
        jButtonXMLAddData.setContentAreaFilled(false);
        jButtonXMLAddData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonXMLAddData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyXML(evt);
            }

            private void verifyXML(ActionEvent evt) {
                if (jTextAreaXMLExtAddData.getText() == null || "".equals(jTextAreaXMLExtAddData.getText().trim())) {
                    error = "";
                    jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
                } else {
                    // Valid Invoice 3.1
                    InputStream is = this.getClass().getResourceAsStream(Constants.I32_EMPTY_PATH);
                    // Extension introduced by user
                    InputStream isExt = null;
                    try {
                        isExt = new ByteArrayInputStream(jTextAreaXMLExtAddData.getText().getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        logger.error("Error creating a bytes array with extension data. The encoding is unsupported: " + e.getMessage());
                    }
                    String[] nodeRoute = {"Invoices", "Invoice", "AdditionalData", "Extensions"};
                    File f = null;
                    try {
                        f = XMLFacturaeUtil.insertarExtension(is, isExt, nodeRoute);
                    } catch (ValidationException ve) {
                        jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                        error = ve.getMessage();
                        ((GenerateInvoice32Window) jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                    } catch (FileNotFoundException e) {
                        jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                        error = e.getMessage();
                        ((GenerateInvoice32Window) jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                    }

                    if (f != null) {
                        try {
                            ValidatorUtil vu = ValidatorUtil.getInstance();
                            vu.validate(f, Constants.VERSION32);
                            error = "";
                            jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
                            ((GenerateInvoice32Window) jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageExtensionAdded"), Constants.OK_MSG_COLOR);
                        } catch (ValidationException ve) {
                            jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                            error = ve.getMessage();
                            ((GenerateInvoice32Window) jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                        } catch (SAXException se) {
                            jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                            error = se.getMessage();
                            ((GenerateInvoice32Window) jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                        } catch (IOException ioe) {
                            jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                            error = ioe.getMessage();
                            ((GenerateInvoice32Window) jButtonXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                        }
                    }
                }
            }
        });

        jButtonXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
        jButtonXMLAddDataZoom.setToolTipText(Constants.LANG.getString("Zoom"));
        jButtonXMLAddDataZoom.setBorderPainted(false);
        jButtonXMLAddDataZoom.setContentAreaFilled(false);
        jButtonXMLAddDataZoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonXMLAddDataZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputExtensionDialog ied = new InputExtensionDialog((JFrame) jTextAreaXMLExtAddData.getTopLevelAncestor(), true, jTextAreaXMLExtAddData.getText(), error);
                ied.setVisible(true);
                jTextAreaXMLExtAddData.setText(ied.getValues());
            }
        });

        jLabelXMLAddData = new JLabel();
        jLabelXMLAddData.setText(Constants.LANG.getString("AdditionalInformation") + " - " + Constants.LANG.getString("Extension") + " (XML)");
        jLabelXMLAddData.setFont(Constants.FONT_PLAIN);
        jLabelXMLAddData.setForeground(Constants.FONT_COLOR);

        org.jdesktop.layout.GroupLayout jPanel24Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbAttchDoc);
        jPanelGlbAttchDoc.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
                jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPaneGlobAttc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 530, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jButtonAttcDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .add(jButtonAttcAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                .add(10, 10, 10)
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jLabelXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jScrollPaneXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jButtonXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .add(jButtonXMLAddDataZoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                .add(10, 10, 10)
        );
        jPanel24Layout.setVerticalGroup(
                jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(jScrollPaneGlobAttc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jPanel24Layout.createSequentialGroup()
                                .add(jButtonAttcAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jButtonAttcDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel24Layout.createSequentialGroup()
                                .add(jLabelXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jScrollPaneXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel24Layout.createSequentialGroup()
                                .add(jButtonXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jButtonXMLAddDataZoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap());

        jTabbedPaneGlobal.addTab(Constants.LANG.getString("Attachments"), jPanelGlbAttchDoc);

        jPanelGlbPayment.setBackground(Constants.BKG_MAIN_COLOR);

        jTabbedPaneGlbPayment.setBackground(Constants.BKG_MAIN_COLOR);

        jPanelGlbPaymentGen2.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelTotalPayDate.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDate.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDate.setText(Constants.LANG.getString("DueDate"));

        jLabelTotalPayAmou.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayAmou.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayAmou.setText(Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")");

        jTextFieldPaymentAmo.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPaymentAmo.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                // Amount may be a Double value with 2 decimals
                String value = ((JTextField) evt.getSource()).getText().trim();
                if (value != null && !"".equals(value)) {
                    Double doubleObj = null;
                    try {
                        doubleObj = Double.parseDouble(value);
                        doubleObj = DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_payment_amount")));
                        ((JTextField) evt.getSource()).setText(String.valueOf(doubleObj));
                    } catch (NumberFormatException nfe) {
                        if (((JTextField) evt.getSource()).getText() != null
                                && !"".equals(((JTextField) evt.getSource()).getText().trim())) {
                            ((JTextField) evt.getSource()).setText("");
                            ((GenerateInvoice32Window) jTextFieldPaymentAmo.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                        }
                        return;
                    }
                }
            }
        });
        jTextFieldPaymentAmo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayMean.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayMean.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayMean.setText(Constants.LANG.getString("PaymentMean"));

        String[] arrayPaymentMeansModel = Constants.APP_PROP.getProperty("paymentMeansCombo" + Constants.FACTURAE31).split(";");
        for (int i = 0; i < arrayPaymentMeansModel.length; i++) {
            arrayPaymentMeansModel[i] = Constants.LANG.getString(arrayPaymentMeansModel[i]);
        }
        DefaultComboBoxModel PaymentMeansModel = new DefaultComboBoxModel(arrayPaymentMeansModel);
        PaymentMeansModel.addElement("");
        jComboBoxPayMean.setModel(PaymentMeansModel);
        jComboBoxPayMean.setSelectedItem(null);
        jComboBoxPayMean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ((JComboBox) evt.getSource()).setBackground(Color.white);
                if (((JComboBox) evt.getSource()) != null && ((JComboBox) evt.getSource()).getSelectedItem() != null && !"".equals(((JComboBox) evt.getSource()).getSelectedItem().toString()) && jTextFieldPaymentAmo != null && "".equals(jTextFieldPaymentAmo.getText())) {
                	jTextFieldPaymentAmo.setText(jTextFieldResults[10].getText());
                }
            }
        });
        jComboBoxPayMean.setRenderer(new ComboBoxRenderer(true));

        jLabelTotalPayRefIssuer.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayRefIssuer.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayRefIssuer.setText(Constants.LANG.getString("PaymentReferenceIssuer"));

        jLabelTotalPayRefReceiver.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayRefReceiver.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayRefReceiver.setText(Constants.LANG.getString("PaymentReferenceReceiver"));

        jTextFieldPaymentRefIssuer.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPaymentRefIssuer.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Payment reference may be a 60 character max. string
                if (value.length() > 60) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPaymentRefIssuer.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jTextFieldPaymentRefReceiver.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPaymentRefReceiver.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Payment reference may be a 60 character max. string
                if (value.length() > 60) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPaymentRefReceiver.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        if (payments == null) {
            payments = new DefaultComboBoxModel();
        }
        jComboBoxPayment.setModel(payments);
        jComboBoxPayment.setSelectedItem(null);
        jComboBoxPayment.setRenderer(new ComboBoxRenderer(true));
        jComboBoxPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                jTextFieldPaymentRefIssuer.setText("");
                jTextFieldPaymentRefReceiver.setText("");
                jTextFieldPaymentAmo.setText("");
                jTextFieldPayDBran.setText("");
                jComboBoxPayMean.setSelectedIndex(-1);
                jComboBoxPayAccountD.setSelectedIndex(-1);
                jComboBoxPayAccountC.setSelectedIndex(-1);
                jComboBoxPayAccountFactoring.setSelectedIndex(-1);

                if (((JComboBox) evt.getSource()) != null && ((JComboBox) evt.getSource()).getSelectedItem() != null && !"".equals(((JComboBox) evt.getSource()).getSelectedItem().toString())) {
                    String valueId = ((JComboBox) evt.getSource()).getSelectedItem().toString().split(":")[1].trim();

                    /**
                     * There is a bug in Hibernate using Aliases. It is not
                     * possible to query the account to be debited and the
                     * account to be credited at the same time. Firstly, the
                     * account to be debited will be asked. Then in another
                     * query, the account to be credited
                     */
                    String query = "SELECT DUE_DATE,AMOUNT,MEAN,RECONCILIATION_REF,ADDITIONAL_INFO,ACCOUNT_TO_BE_DEBITED,ACCOUNT_TO_BE_CREDITED,DEBIT_RECONCILIATION_REF FROM PAYMENT_DETAIL WHERE PAYMENT_ID='" + valueId + "'";

                    SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
                    List<?> ls = s.list();
                    if (ls != null && ls.size() > 0 && ls.get(0) != null) {
                        Object[] values = (Object[]) ls.get(0);

                        if (values[0] != null && values[0] instanceof Date) {
                            jCalendarComboBoxPaymDate.getModel().setValue((Date) (values[0]));
                        }
                        if (values[1] != null) {
                            jTextFieldPaymentAmo.setText(String.valueOf(values[1]));
                        }
                        if (values[2] != null && !"0".equals(String.valueOf(values[2]))) {
                            jComboBoxPayMean.setSelectedIndex(Integer.parseInt(String.valueOf(values[2])) - 1);
                        }
                        if (values[3] != null) {
                            jTextFieldPaymentRefIssuer.setText(String.valueOf(values[3]));
                        }
                        if (values[5] != null) {
                            String query2 = "SELECT ACCOUNT_ID,IBAN FROM ACCOUNT WHERE ACCOUNT_ID='" + values[5] + "'";
                            SQLQuery s2 = FacturaeManager.getInstance().executeQuery(query2);
                            List<?> ls2 = s2.list();
                            if (ls2 != null && ls2.size() > 0 && ls2.get(0) != null) {
                                Object[] values2 = (Object[]) ls2.get(0);
                                if (values2[0] != null && values2[1] != null) {
                                    jComboBoxPayAccountD.setSelectedItem(values2[0] + ": " + values2[1]);
                                }
                            }
                        }
                        if (values[6] != null) {
                            String query3 = "SELECT ACCOUNT_ID,IBAN FROM ACCOUNT WHERE ACCOUNT_ID='" + values[6] + "'";
                            SQLQuery s3 = FacturaeManager.getInstance().executeQuery(query3);
                            List<?> ls3 = s3.list();
                            if (ls3 != null && ls3.size() > 0 && ls3.get(0) != null) {
                                Object[] values2 = (Object[]) ls3.get(0);
                                if (values2[0] != null && values2[1] != null) {
                                    String query4 = "SELECT PAYMENT_ID FROM INVOICE_FACTORING WHERE PAYMENT_ID='" + values2[0] + "'";
                                    SQLQuery s4 = FacturaeManager.getInstance().executeQuery(query4);
                                    List<?> ls4 = s4.list();
                                    if(ls4 != null && !ls4.isEmpty() && ls4.get(0) != null) {
                                        jComboBoxPayAccountFactoring.setSelectedItem(values2[0] + ": " + values2[1]);
                                    }
                                }
                            }
                        }
                        if (values[7] != null) {
                            jTextFieldPaymentRefReceiver.setText(String.valueOf(values[3]));
                        }
                    }
                }
            }
        });

        ((JFormattedTextField) ((JSpinner) jCalendarComboBoxPaymDate.getComponent(0)).getEditor().getComponent(0)).addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                if (!((JFormattedTextField) evt.getSource()).isEditValid()) {
                    ((GenerateInvoice32Window) jCalendarComboBoxPaymDate.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageDateNotValid"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jLabelPayment.setFont(Constants.FONT_PLAIN);
        jLabelPayment.setForeground(Constants.FONT_COLOR);
        jLabelPayment.setText(Constants.LANG.getString("PaymentDetails"));

        jSeparator1.setForeground(Constants.SELECTION_COLOR);
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextAreaPayExist.setSize(100, 10);
        jTextAreaPayExist.setText(Constants.LANG.getString("MessageExistingPayment"));
        jTextAreaPayExist.setFont(Constants.FONT_ITALIC);
        jTextAreaPayExist.setForeground(Constants.FONT_COLOR);
        jTextAreaPayExist.setEditable(false);
        jTextAreaPayExist.setBackground(Constants.BKG_MAIN_COLOR);
        jTextAreaPayExist.setFocusable(false);

        org.jdesktop.layout.GroupLayout jPanelGlbPaymentGen2Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbPaymentGen2);
        jPanelGlbPaymentGen2.setLayout(jPanelGlbPaymentGen2Layout);
        jPanelGlbPaymentGen2Layout.setHorizontalGroup(
                jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelGlbPaymentGen2Layout.createSequentialGroup()
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(org.jdesktop.layout.GroupLayout.CENTER, jPanelGlbPaymentGen2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .add(5, 5, 5)
                                        .add(jLabelPayment, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                        .add(5, 5, 5)
                                        .add(jComboBoxPayment, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                        .add(10, 10, 10))
                                .add(org.jdesktop.layout.GroupLayout.CENTER, jPanelGlbPaymentGen2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .add(5, 5, 5)
                                        .add(jTextAreaPayExist, 0, 260, Short.MAX_VALUE)))
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(10, 10, 10)
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(jLabelTotalPayDate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .add(jLabelTotalPayMean, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .add(5, 5, 5)
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(jCalendarComboBoxPaymDate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .add(jComboBoxPayMean, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .add(20, 20, 20)
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(jLabelTotalPayRefIssuer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                .add(jLabelTotalPayAmou, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .add(5, 5, 5)
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(org.jdesktop.layout.GroupLayout.CENTER, jPanelGlbPaymentGen2Layout.createSequentialGroup()
                                        .add(jTextFieldPaymentRefIssuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(10, 10, 10)
                                        .add(jLabelTotalPayRefReceiver, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jTextFieldPaymentRefReceiver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(jTextFieldPaymentAmo, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .add(20, 20, 20)));

        jPanelGlbPaymentGen2Layout.setVerticalGroup(
                jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelGlbPaymentGen2Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabelPayment)
                                .add(jComboBoxPayment, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(5, 5, 5)
                        .add(jTextAreaPayExist, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(5, 5, 5))
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanelGlbPaymentGen2Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                .add(jPanelGlbPaymentGen2Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabelTotalPayDate)
                                .add(jCalendarComboBoxPaymDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayRefIssuer)
                                .add(jLabelTotalPayRefReceiver)
                                .add(jTextFieldPaymentRefIssuer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jTextFieldPaymentRefReceiver, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(5, 5, 5)
                        .add(jPanelGlbPaymentGen2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabelTotalPayMean)
                                .add(jComboBoxPayMean, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayAmou)
                                .add(jTextFieldPaymentAmo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(5, 5, 5)));

        jTabbedPaneGlbPayment.addTab(Constants.LANG.getString("General"), jPanelGlbPaymentGen2);

        jPanelGlbPaymentAccDebited.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelTotalPayDIBAN.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDIBAN.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDIBAN.setText(Constants.LANG.getString("IBAN"));

        jTextFieldPayDIBAN.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayDIBAN.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Payment reference may be a [5 min. - 34 max.] length string
                if (value.length() < 5 && value.length() > 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDIBAN.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                if (value.length() > 34) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDIBAN.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayDIBAN.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayDBank.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDBank.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDBank.setText(Constants.LANG.getString("BankCode"));

        jTextFieldPayDBank.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayDBank.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Bank code reference may be a 60 max. length string
                if (value.length() > 60) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDBank.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jLabelTotalPayDBran.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDBran.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDBran.setText(Constants.LANG.getString("BranchCode"));

        jTextFieldPayDBran.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayDBran.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Branch code reference may be a 60 max. length string
                if (value.length() > 60) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDBran.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jLabelTotalPayDAddr.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDAddr.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDAddr.setText(Constants.LANG.getString("Address"));

        jTextFieldPayDAddr.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayDAddr.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Address may be a 80 max. length string
                if (value.length() > 80) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDAddr.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayDAddr.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayDPCod.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDPCod.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDPCod.setText(Constants.LANG.getString("PostCode"));

        jTextFieldPayDPCod.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayDPCod.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                if (value != null) {
                    value = value.trim();
                }
                // Postal code may be a 5 length string (But numeric format [0-9]*)
                if (value.length() != 5 && value.length() != 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDPCod.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamPostCodeLength"), Constants.ERROR_MSG_COLOR);
                    return;
                }
                try {
                    Integer.parseInt(value.trim());
                } catch (NumberFormatException nfe) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDPCod.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                    return;
                }
            }
        });
        jTextFieldPayDPCod.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayDCity.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDCity.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDCity.setText(Constants.LANG.getString("Town"));

        jTextFieldPayDCity.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayDCity.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // City may be a 50 max. length string
                if (value.length() > 50) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDCity.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayDCity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayDProv.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDProv.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDProv.setText(Constants.LANG.getString("Province"));

        jTextFieldPayDProv.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayDProv.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Province may be a 20 max. length string
                if (value.length() > 20) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayDProv.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayDProv.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayDCoun.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayDCoun.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayDCoun.setText(Constants.LANG.getString("Country"));

        DefaultComboBoxModel CountryModel = new DefaultComboBoxModel(Constants.COUNTRIES31.toArray());
        CountryModel.addElement("");
        jComboBoxPayDCoun.setModel(CountryModel);
        jComboBoxPayDCoun.setRenderer(new ComboBoxRenderer(true));
        jComboBoxPayDCoun.setSelectedItem(null);
        jComboBoxPayDCoun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ((JComboBox) evt.getSource()).setBackground(Color.white);
            }
        });

        if (accounts == null) {
            accounts = new DefaultComboBoxModel();
        }
        jComboBoxPayAccountD.setModel(accounts);
        jComboBoxPayAccountD.setSelectedItem(null);
        jComboBoxPayAccountD.setRenderer(new ComboBoxRenderer(true));
        jComboBoxPayAccountD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                jTextFieldPayDIBAN.setText("");
                jTextFieldPayDBank.setText("");
                jTextFieldPayDBran.setText("");
                jTextFieldPayDAddr.setText("");
                jTextFieldPayDCity.setText("");
                jTextFieldPayDPCod.setText("");
                jTextFieldPayDProv.setText("");
                jComboBoxPayDCoun.setSelectedIndex(-1);

                if (((JComboBox) evt.getSource()) != null && ((JComboBox) evt.getSource()).getSelectedItem() != null && !"".equals(((JComboBox) evt.getSource()).getSelectedItem().toString())) {
                    String valueId = ((JComboBox) evt.getSource()).getSelectedItem().toString().split(":")[0];
                    String query = "SELECT IBAN,BANK_CODE,BRANCH_CODE,ADDRESS FROM ACCOUNT WHERE ACCOUNT.ACCOUNT_ID='" + valueId + "'";
                    SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
                    List<?> ls = s.list();

                    if (ls != null && ls.size() > 0 && ls.get(0) != null) {
                        Object[] values = (Object[]) ls.get(0);
                        if (values[0] != null) {
                            jTextFieldPayDIBAN.setText(String.valueOf(values[0]));
                        }
                        if (values[1] != null) {
                            jTextFieldPayDBank.setText(String.valueOf(values[1]));
                        }
                        if (values[2] != null) {
                            jTextFieldPayDBran.setText(String.valueOf(values[2]));
                        }
                        if (values[3] != null) {
                            String query2 = "SELECT ADDRESS,TOWN,POST_CODE,PROVINCE,COUNTRY FROM ADDRESS WHERE ADDRESS_ID='" + String.valueOf(values[3]) + "'";
                            SQLQuery s2 = FacturaeManager.getInstance().executeQuery(query2);
                            List<?> ls2 = s2.list();
                            if (ls2 != null && ls2.size() > 0 && ls2.get(0) != null) {
                                Object[] values2 = (Object[]) ls2.get(0);
                                if (values2[0] != null) {
                                    jTextFieldPayDAddr.setText(String.valueOf(values2[0]));
                                }
                                if (values2[1] != null) {
                                    jTextFieldPayDCity.setText(String.valueOf(values2[1]));
                                }
                                if (values2[2] != null) {
                                    jTextFieldPayDPCod.setText(String.valueOf(values2[2]));
                                }
                                if (values2[3] != null) {
                                    jTextFieldPayDProv.setText(String.valueOf(values2[3]));
                                }
                                if (values2[4] != null) {
                                    jComboBoxPayDCoun.setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(values2[4])));
                                }
                            }
                        }

                    }
                }
            }
        });

        jSeparator2.setForeground(Constants.SELECTION_COLOR);
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextAreaAccExist.setSize(100, 10);
        jTextAreaAccExist.setText(Constants.LANG.getString("MessageExistingAccount"));
        jTextAreaAccExist.setFont(Constants.FONT_ITALIC);
        jTextAreaAccExist.setForeground(Constants.FONT_COLOR);
        jTextAreaAccExist.setEditable(false);
        jTextAreaAccExist.setBackground(Constants.BKG_MAIN_COLOR);
        jTextAreaAccExist.setFocusable(false);

        org.jdesktop.layout.GroupLayout jPanel26Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbPaymentAccDebited);
        jPanelGlbPaymentAccDebited.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
                jPanel26Layout.createSequentialGroup()
                .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel26Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxPayAccountD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .add(10, 10, 10))
                        .add(jPanel26Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextAreaAccExist, 0, 260, Short.MAX_VALUE)))
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10)
                .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel26Layout.createSequentialGroup()
                                .add(jLabelTotalPayDIBAN)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayDIBAN, 190, 190, 190))
                        .add(jPanel26Layout.createSequentialGroup()
                                .add(jLabelTotalPayDBank)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayDBank, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayDBran)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayDBran, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(15, 15, 15)
                .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel26Layout.createSequentialGroup()
                                .add(jLabelTotalPayDAddr)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayDAddr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayDCity)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayDCity, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                        .add(jPanel26Layout.createSequentialGroup()
                                .add(jLabelTotalPayDPCod)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayDPCod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayDProv)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayDProv, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayDCoun)
                                .add(jComboBoxPayDCoun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap());

        jPanel26Layout.setVerticalGroup(
                jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel26Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jComboBoxPayAccountD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextAreaAccExist, 0, 260, Short.MAX_VALUE))
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel26Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                .add(jPanel26Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jLabelTotalPayDIBAN)
                                .add(jTextFieldPayDIBAN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayDAddr)
                                .add(jTextFieldPayDAddr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayDCity)
                                .add(jTextFieldPayDCity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jLabelTotalPayDBank)
                                .add(jTextFieldPayDBank, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayDBran)
                                .add(jTextFieldPayDBran, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayDPCod)
                                .add(jTextFieldPayDPCod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayDProv)
                                .add(jTextFieldPayDProv, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayDCoun)
                                .add(jComboBoxPayDCoun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jTabbedPaneGlbPayment.addTab(Constants.LANG.getString("AccountToBeDebited"), jPanelGlbPaymentAccDebited);

        jPanelGlbPaymentAccCharged.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelTotalPayCIBAN.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCIBAN.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCIBAN.setText(Constants.LANG.getString("IBAN"));

        jTextFieldPayCIBAN.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayCIBAN.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Payment reference may be a [5 min. - 34 max.] length string
                if (value.length() < 5 && value.length() > 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCIBAN.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                if (value.length() > 34) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCIBAN.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayCIBAN.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayCBank.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCBank.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCBank.setText(Constants.LANG.getString("BankCode"));

        jTextFieldPayCBank.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayCBank.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Bank code reference may be a 60 max. length string
                if (value.length() > 60) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCBank.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jLabelTotalPayCBran.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCBran.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCBran.setText(Constants.LANG.getString("BranchCode"));

        jTextFieldPayCBran.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayCBran.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Branch code reference may be a 60 max. length string
                if (value.length() > 60) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCBran.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jLabelTotalPayCAddr.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCAddr.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCAddr.setText(Constants.LANG.getString("Address"));

        jTextFieldPayCAddr.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayCAddr.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Address may be a 80 max. length string
                if (value.length() > 80) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCAddr.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayCAddr.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayCCity.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCCity.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCCity.setText(Constants.LANG.getString("Town"));

        jTextFieldPayCCity.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayCCity.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // City may be a 50 max. length string
                if (value.length() > 50) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCCity.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayCCity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayCPCod.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCPCod.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCPCod.setText(Constants.LANG.getString("PostCode"));

        jTextFieldPayCPCod.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayCPCod.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Post code may be a 5 length string (But numeric format [0-9]*)
                if (value != null) {
                    value = value.trim();
                }
                if (value.length() != 5 && value.length() != 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCPCod.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamPostCodeLength"), Constants.ERROR_MSG_COLOR);
                    return;
                }
                try {
                    Integer.parseInt(value.trim());
                } catch (NumberFormatException nfe) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCPCod.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                    return;
                }
            }
        });
        jTextFieldPayCPCod.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayCProv.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCProv.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCProv.setText(Constants.LANG.getString("Province"));

        jTextFieldPayCProv.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayCProv.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Province may be a 20 max. length string
                if (value.length() > 20) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayCProv.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayCProv.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayCCoun.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayCCoun.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayCCoun.setText(Constants.LANG.getString("Country"));

        DefaultComboBoxModel CountryModel2 = new DefaultComboBoxModel(Constants.COUNTRIES31.toArray());
        CountryModel2.addElement("");
        jComboBoxPayCCoun.setModel(CountryModel2);
        jComboBoxPayCCoun.setRenderer(new ComboBoxRenderer(true));
        jComboBoxPayCCoun.setSelectedItem(null);
        jComboBoxPayCCoun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ((JComboBox) evt.getSource()).setBackground(Color.white);
            }
        });

        if (accounts2 == null) {
            accounts2 = new DefaultComboBoxModel();
        }
        jComboBoxPayAccountC.setModel(accounts2);
        jComboBoxPayAccountC.setSelectedItem(null);
        jComboBoxPayAccountC.setRenderer(new ComboBoxRenderer(true));
        jComboBoxPayAccountC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                jTextFieldPayCIBAN.setText("");
                jTextFieldPayCBank.setText("");
                jTextFieldPayCBran.setText("");
                jTextFieldPayCAddr.setText("");
                jTextFieldPayCCity.setText("");
                jTextFieldPayCPCod.setText("");
                jTextFieldPayCProv.setText("");
                jComboBoxPayCCoun.setSelectedIndex(-1);

                if (((JComboBox) evt.getSource()) != null && ((JComboBox) evt.getSource()).getSelectedItem() != null && !"".equals(((JComboBox) evt.getSource()).getSelectedItem().toString())) {
                    String valueId = ((JComboBox) evt.getSource()).getSelectedItem().toString().split(":")[0];
                    String query = "SELECT IBAN,BANK_CODE,BRANCH_CODE,ADDRESS FROM ACCOUNT WHERE ACCOUNT.ACCOUNT_ID='" + valueId + "'";
                    SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
                    List<?> ls = s.list();

                    if (ls != null && ls.size() > 0 && ls.get(0) != null) {
                        Object[] values = (Object[]) ls.get(0);
                        if (values[0] != null) {
                            jTextFieldPayCIBAN.setText(String.valueOf(values[0]));
                        }
                        if (values[1] != null) {
                            jTextFieldPayCBank.setText(String.valueOf(values[1]));
                        }
                        if (values[2] != null) {
                            jTextFieldPayCBran.setText(String.valueOf(values[2]));
                        }
                        if (values[3] != null) {
                            String query2 = "SELECT ADDRESS,TOWN,POST_CODE,PROVINCE,COUNTRY FROM ADDRESS WHERE ADDRESS_ID='" + String.valueOf(values[3]) + "'";
                            SQLQuery s2 = FacturaeManager.getInstance().executeQuery(query2);
                            List<?> ls2 = s2.list();
                            if (ls2 != null && ls2.size() > 0 && ls2.get(0) != null) {
                                Object[] values2 = (Object[]) ls2.get(0);
                                if (values2[0] != null) {
                                    jTextFieldPayCAddr.setText(String.valueOf(values2[0]));
                                }
                                if (values2[1] != null) {
                                    jTextFieldPayCCity.setText(String.valueOf(values2[1]));
                                }
                                if (values2[2] != null) {
                                    jTextFieldPayCPCod.setText(String.valueOf(values2[2]));
                                }
                                if (values2[3] != null) {
                                    jTextFieldPayCProv.setText(String.valueOf(values2[3]));
                                }
                                if (values2[4] != null) {
                                    jComboBoxPayCCoun.setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(values2[4])));
                                }
                            }
                        }
                    }
                }
            }
        });

        jSeparator3.setForeground(Constants.SELECTION_COLOR);
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextAreaAccExist2.setSize(100, 10);
        jTextAreaAccExist2.setText(Constants.LANG.getString("MessageExistingAccount"));
        jTextAreaAccExist2.setFont(Constants.FONT_ITALIC);
        jTextAreaAccExist2.setForeground(Constants.FONT_COLOR);
        jTextAreaAccExist2.setEditable(false);
        jTextAreaAccExist2.setBackground(Constants.BKG_MAIN_COLOR);
        jTextAreaAccExist2.setFocusable(false);

        org.jdesktop.layout.GroupLayout jPanel27Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbPaymentAccCharged);
        jPanelGlbPaymentAccCharged.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
                jPanel27Layout.createSequentialGroup()
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel27Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxPayAccountC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .add(10, 10, 10))
                        .add(jPanel27Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextAreaAccExist2, 0, 260, Short.MAX_VALUE)))
                .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10)
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel27Layout.createSequentialGroup()
                                .add(jLabelTotalPayCIBAN)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayCIBAN, 190, 190, 190))
                        .add(jPanel27Layout.createSequentialGroup()
                                .add(jLabelTotalPayCBank)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayCBank, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayCBran)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayCBran, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(15, 15, 15)
                .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel27Layout.createSequentialGroup()
                                .add(jLabelTotalPayCAddr)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayCAddr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayCCity)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayCCity, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                        .add(jPanel27Layout.createSequentialGroup()
                                .add(jLabelTotalPayCPCod)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayCPCod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayCProv)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayCProv, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayCCoun)
                                .add(jComboBoxPayCCoun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap());

        jPanel27Layout.setVerticalGroup(
                jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel27Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jComboBoxPayAccountC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextAreaAccExist2, 0, 260, Short.MAX_VALUE))
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 40, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                .add(jPanel27Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jLabelTotalPayCIBAN)
                                .add(jTextFieldPayCIBAN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayCAddr)
                                .add(jTextFieldPayCAddr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayCCity)
                                .add(jTextFieldPayCCity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jLabelTotalPayCBank)
                                .add(jTextFieldPayCBank, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayCBran)
                                .add(jTextFieldPayCBran, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayCPCod)
                                .add(jTextFieldPayCPCod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayCProv)
                                .add(jTextFieldPayCProv, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayCCoun)
                                .add(jComboBoxPayCCoun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jTabbedPaneGlbPayment.addTab(Constants.LANG.getString("AccountToBeCredited"), jPanelGlbPaymentAccCharged);

// ext-csaamar - 20150303 - Añadir datos del endosatario
        jPanelGlbPaymentAccFactoring.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelTotalPayFactIBAN.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactIBAN.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactIBAN.setText(Constants.LANG.getString("IBAN"));
        jLabelTotalPayFactIBAN.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactIBAN.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactIBAN.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Payment reference may be a [5 min. - 34 max.] length string
                if (value.length() < 5 && value.length() > 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactIBAN.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                if (value.length() > 34) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactIBAN.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayFactIBAN.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactSociedad.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactSociedad.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactSociedad.setText(Constants.LANG.getString("Sociedad"));
        jLabelTotalPayFactSociedad.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactSociedad.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactSociedad.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                if (value.length() == 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactSociedad.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                // Bank code reference may be a 80 max. length string
                if (value.length() > 80) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactSociedad.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayFactSociedad.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactNifSociedad.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactNifSociedad.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactNifSociedad.setText(Constants.LANG.getString("NifSociedad"));
        jLabelTotalPayFactNifSociedad.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactNifSociedad.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactNifSociedad.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Branch code reference may be a 60 max. length string
                if (value.length() < 3 && value.length() > 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactNifSociedad.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                if (value.length() > 30) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactNifSociedad.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayFactNifSociedad.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactAddr.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactAddr.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactAddr.setText(Constants.LANG.getString("Address"));
        jLabelTotalPayFactAddr.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactAddr.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactAddr.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                if (value.length() == 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactAddr.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                // Address may be a 80 max. length string
                if (value.length() > 80) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactAddr.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayFactAddr.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactCity.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactCity.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactCity.setText(Constants.LANG.getString("Town"));
        jLabelTotalPayFactCity.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactCity.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactCity.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                if (value.length() == 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactCity.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                // City may be a 50 max. length string
                if (value.length() > 50) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactCity.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayFactCity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactPCod.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactPCod.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactPCod.setText(Constants.LANG.getString("PostCodeShort"));
        jLabelTotalPayFactPCod.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactPCod.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactPCod.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                // Post code may be a 5 length string (But numeric format [0-9]*)
                if (value != null) {
                    value = value.trim();
                }
                if (value.length() != 5 && value.length() != 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactPCod.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamPostCodeLength"), Constants.ERROR_MSG_COLOR);
                    return;
                }
                try {
                    Integer.parseInt(value.trim());
                } catch (NumberFormatException nfe) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactPCod.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                    return;
                }
            }
        });
        jTextFieldPayFactPCod.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactClausula.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactClausula.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactClausula.setText(Constants.LANG.getString("Clausula"));
        jLabelTotalPayFactClausula.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactClausula.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactClausula.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                if (value.length() == 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactClausula.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                if (value.length() > 2500) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactClausula.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });
        jTextFieldPayFactClausula.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactProv.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactProv.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactProv.setText(Constants.LANG.getString("Province"));
        jLabelTotalPayFactProv.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        jTextFieldPayFactProv.setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldPayFactProv.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                String value = ((JTextField) evt.getSource()).getText();
                if (value.length() == 0) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactProv.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooLow"), Constants.ERROR_MSG_COLOR);
                }
                // Province may be a 20 max. length string
                if (value.length() > 20) {
                    ((JTextField) evt.getSource()).setText("");
                    ((GenerateInvoice32Window) jTextFieldPayFactProv.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamTooHigh"), Constants.ERROR_MSG_COLOR);
                }
            }
        });

        jTextFieldPayFactProv.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
            }
        });

        jLabelTotalPayFactCoun.setFont(Constants.FONT_PLAIN);
        jLabelTotalPayFactCoun.setForeground(Constants.FONT_COLOR);
        jLabelTotalPayFactCoun.setText(Constants.LANG.getString("Country"));
        jLabelTotalPayFactCoun.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));

        DefaultComboBoxModel CountryModel3 = new DefaultComboBoxModel(Constants.COUNTRIES31.toArray());
        CountryModel3.addElement("");
        jComboBoxPayFactCoun.setModel(CountryModel3);
        jComboBoxPayFactCoun.setRenderer(new ComboBoxRenderer(true));
        jComboBoxPayFactCoun.setSelectedItem(null);
        jComboBoxPayFactCoun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ((JComboBox) evt.getSource()).setBackground(Color.white);
            }
        });

        if (accounts3 == null) {
            accounts3 = new DefaultComboBoxModel();
        }
        jComboBoxPayAccountFactoring.setModel(accounts3);
        jComboBoxPayAccountFactoring.setSelectedItem(null);
        jComboBoxPayAccountFactoring.setRenderer(new ComboBoxRenderer(true));
        jComboBoxPayAccountFactoring.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                jTextFieldPayFactIBAN.setText("");
                jTextFieldPayFactSociedad.setText("");
                jTextFieldPayFactNifSociedad.setText("");
                jTextFieldPayFactAddr.setText("");
                jTextFieldPayFactCity.setText("");
                jTextFieldPayFactPCod.setText("");
                jTextFieldPayFactClausula.setText("");
                jTextFieldPayFactProv.setText("");
                jComboBoxPayFactCoun.setSelectedIndex(-1);

                // Se ha seleccionado una cuenta?
                boolean hasSelectedAccount = false;
                int idAddress = -1;
                // Obtener los datos de la cuenta.
                if (((JComboBox) evt.getSource()) != null && ((JComboBox) evt.getSource()).getSelectedItem() != null && !"".equals(((JComboBox) evt.getSource()).getSelectedItem().toString())) {
                    String valueId = ((JComboBox) evt.getSource()).getSelectedItem().toString().split(":")[0];
                    String query = "SELECT IBAN,BANK_CODE,BRANCH_CODE,ADDRESS FROM ACCOUNT WHERE ACCOUNT.ACCOUNT_ID='" + valueId + "'";
                    SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
                    List<?> ls = s.list();

                    if (ls != null && ls.size() > 0 && ls.get(0) != null) {
                        Object[] values = (Object[]) ls.get(0);
                        if (values[0] != null) {
                            jTextFieldPayFactIBAN.setText(String.valueOf(values[0]));
                            hasSelectedAccount = true;
                        }
                        if(values[3] != null) {
                        	try {
                        		idAddress = Integer.valueOf(values[3].toString());
                        	} catch(NumberFormatException e) {
                        		idAddress = -1;
                        	}
                        }
                    }
                }

                // Si se ha seleccionado una cuenta con IBAN se muestra la información relacionada con la cuenta.
                if(hasSelectedAccount) {
	                // Obtener el ID de la factura seleccionada (si existe alguna).
	                int selRow = MainWindow.getInstance().getJTableInvoices().getSelectedRow();
	                String idInvoice = null;
	                if(selRow > -1) {
		                String originalId = MainWindow.getInstance().getJTableInvoices().getModel().getValueAt(selRow, 0).toString();
						SQLQuery sInvoice = FacturaeManager.getInstance().executeQuery("SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = '"+ originalId + "' AND STATE = 0");
						List<?> lsInvoice = sInvoice.list();
						if (lsInvoice != null && lsInvoice.size() > 0) {
							idInvoice = String.valueOf(lsInvoice.get(0));
						}
	                }
	                
	                // Obtener información del endoso.
	                String idAssigneeNumber = null; 
					if(idInvoice != null && !"".equals(idInvoice.trim())) {
	                    String queryPayment = "SELECT ASSIGNEE,CLAUSES FROM FACTORING WHERE INVOICE_ID='" + idInvoice + "'";
	                    SQLQuery sPayment = FacturaeManager.getInstance().executeQuery(queryPayment);
	                    List<?> lsPayment = sPayment.list();
	                    if (lsPayment != null && lsPayment.size() > 0 && lsPayment.get(0) != null) {
	                        Object valuesFactoring[] = (Object[]) lsPayment.get(0);
	                        if (valuesFactoring[1] != null && !"".equals(valuesFactoring[1])) {
	                            jTextFieldPayFactClausula.setText(String.valueOf(valuesFactoring[1]));
	                        }
	                        if (valuesFactoring[0] != null && !"".equals(valuesFactoring[0])) {
	                        	idAssigneeNumber = String.valueOf(valuesFactoring[0]);
	                        }
	                    }
					}
					
					// Obtener información del cesionario
					if(idAssigneeNumber != null && !"".equals(idAssigneeNumber.trim())) {
		                String assigneeQuery = "SELECT TAX_IDENTIFICATION, LEGAL_ENTITY, INDIVIDUAL_ENTITY FROM ASSIGNEE WHERE ASSIGNEE_ID_NUMBER='" + idAssigneeNumber + "'";
		                SQLQuery sAssignee = FacturaeManager.getInstance().executeQuery(assigneeQuery);
		                List<?> lsAssignee = sAssignee.list();
		                if (lsAssignee != null && lsAssignee.size() > 0) {
		                    Object[] results = (Object[]) lsAssignee.get(0);
		                    if (results[0] != null) {
		                        jTextFieldPayFactNifSociedad.setText(String.valueOf(results[0]));
		                    }
		                    if (results[1] != null) {
		                        String legalQuery = "SELECT CORPORATE_NAME, TRADE_NAME, FACE, REGISTRATION_DATA, ADDRESS, CONTACT FROM LEGAL_ENTITY WHERE PARTY_ID='" + results[1] + "'";
		                        SQLQuery sLegal = FacturaeManager.getInstance().executeQuery(legalQuery);
		                        List<?> sLegalResult = sLegal.list();
		                        if (sLegalResult != null && sLegalResult.size() > 0) {
		                            Object[] values = (Object[]) sLegalResult.get(0);
		                            if (values[0] != null) {
		                                jTextFieldPayFactSociedad.setText(String.valueOf(values[0]));
		                            }
		                            if (values[4] != null && idAddress == -1) {
		                            	idAddress = Integer.valueOf(values[4].toString());
		                            }
		                        }
		                    }
		                }
					}
					if(idAddress > -1) {
	                    String qAddress = "SELECT ADDRESS,TOWN,POST_CODE,PROVINCE,COUNTRY FROM ADDRESS WHERE ADDRESS_ID='" + String.valueOf(idAddress) + "'";
	                    SQLQuery sAddress = FacturaeManager.getInstance().executeQuery(qAddress);
	                    List<?> lsAddress = sAddress.list();
	                    if (lsAddress != null && !lsAddress.isEmpty() && lsAddress.get(0) != null) {
	                        Object[] values2 = (Object[]) lsAddress.get(0);
	                        if (values2[0] != null) {
	                            jTextFieldPayFactAddr.setText(String.valueOf(values2[0]));
	                        }
	                        if (values2[1] != null) {
	                            jTextFieldPayFactCity.setText(String.valueOf(values2[1]));
	                        }
	                        if (values2[2] != null) {
	                            jTextFieldPayFactPCod.setText(String.valueOf(values2[2]));
	                        }
	                        if (values2[3] != null) {
	                            jTextFieldPayFactProv.setText(String.valueOf(values2[3]));
	                        }
	                        if (values2[4] != null) {
	                            jComboBoxPayFactCoun.setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(values2[4])));
	                        }
	                    }
					}
                }
            }
        });

        jSeparatorFactoring.setForeground(Constants.SELECTION_COLOR);
        jSeparatorFactoring.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextAreaAccExistFactoring.setSize(100, 10);
        jTextAreaAccExistFactoring.setText(Constants.LANG.getString("MessageExistingAccount"));
        jTextAreaAccExistFactoring.setFont(Constants.FONT_ITALIC);
        jTextAreaAccExistFactoring.setForeground(Constants.FONT_COLOR);
        jTextAreaAccExistFactoring.setEditable(false);
        jTextAreaAccExistFactoring.setBackground(Constants.BKG_MAIN_COLOR);
        jTextAreaAccExistFactoring.setFocusable(false);

        jTextAreaWarnFactoring = new JTextArea();
        jTextAreaWarnFactoring.setSize(100, 10);
        jTextAreaWarnFactoring.setText(Constants.LANG.getString("MessageWarnFactoring"));
        jTextAreaWarnFactoring.setFont(Constants.FONT_ITALIC);
        jTextAreaWarnFactoring.setForeground(Constants.WARN_MSG_COLOR);
        jTextAreaWarnFactoring.setBackground(Constants.BKG_MAIN_COLOR);
        jTextAreaWarnFactoring.setEditable(false);
        jTextAreaWarnFactoring.setFocusable(false);

        org.jdesktop.layout.GroupLayout jPanel31Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbPaymentAccFactoring);
        jPanelGlbPaymentAccFactoring.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(jPanel31Layout.createSequentialGroup()
                .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel31Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxPayAccountFactoring, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .add(10, 10, 10))
                        .add(jPanel31Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextAreaAccExistFactoring, 0, 260, Short.MAX_VALUE))
                        .add(jPanel31Layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextAreaWarnFactoring, 0, 260, Short.MAX_VALUE))
                .add(5, 5, 5))
                .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactIBAN)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactIBAN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 170, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        ).add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactSociedad)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactSociedad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabelTotalPayFactNifSociedad)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactNifSociedad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        ))
                .add(5, 5, 5)
                .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactAddr)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactAddr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        ).add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactProv)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactProv, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                        )
                 .add(5, 5, 5)
                 .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                		 .add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactCity)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactCity, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                         ).add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactClausula)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactClausula, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                         )
                  .add(5, 5, 5)
                  .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                		  .add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactPCod)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextFieldPayFactPCod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jPanel31Layout.createSequentialGroup()
                                .add(jLabelTotalPayFactCoun)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBoxPayFactCoun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                ).addContainerGap());

        jPanel31Layout.setVerticalGroup(
        		jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel31Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jComboBoxPayAccountFactoring, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextAreaAccExistFactoring, 0, 260, Short.MAX_VALUE)
                        .add(jTextAreaWarnFactoring, 0, 260, Short.MAX_VALUE))
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel31Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                .add(jPanel31Layout.createSequentialGroup()
                        .add(5, 5, 5)
                        .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE)
                                .add(jLabelTotalPayFactIBAN)
                                .add(jTextFieldPayFactIBAN, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayFactAddr)
                                .add(jTextFieldPayFactAddr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayFactCity)
                                .add(jTextFieldPayFactCity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayFactPCod)
                                .add(jTextFieldPayFactPCod, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        ).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE)
                                .add(jLabelTotalPayFactSociedad)
                                .add(jTextFieldPayFactSociedad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayFactNifSociedad)
                                .add(jTextFieldPayFactNifSociedad, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayFactProv)
                                .add(jTextFieldPayFactProv, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayFactClausula)
                                .add(jTextFieldPayFactClausula, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalPayFactCoun)
                                .add(jComboBoxPayFactCoun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jTabbedPaneGlbPayment.addTab(Constants.LANG.getString("FactoringDataTitle"), jPanelGlbPaymentAccFactoring);
// ext-csaamar - 20150303 - Añadir datos del endosatario

        org.jdesktop.layout.GroupLayout jPanel28Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbPayment);
        jPanelGlbPayment.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
                jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTabbedPaneGlbPayment, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 931, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(13, 13, 13))
        );
        jPanel28Layout.setVerticalGroup(
                jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel28Layout.createSequentialGroup()
                        .add(jPanel28Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jPanel28Layout.createSequentialGroup()
                                        .addContainerGap())
                                .add(jPanel28Layout.createSequentialGroup()
                                        .add(4, 4, 4)
                                        .add(jTabbedPaneGlbPayment, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                        .addContainerGap())
        );

        jTabbedPaneGlobal.addTab(Constants.LANG.getString("PaymentDetails"), jPanelGlbPayment);

        jPanelGlbWhithhelds.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelTotalWithItem.setFont(Constants.FONT_PLAIN);
        jLabelTotalWithItem.setForeground(Constants.FONT_COLOR);
        jLabelTotalWithItem.setText(Constants.LANG.getString("Item"));

        jTextFieldWithItem.setHorizontalAlignment(JTextField.RIGHT);

        jLabelTotalWithAmou.setFont(Constants.FONT_PLAIN);
        jLabelTotalWithAmou.setForeground(Constants.FONT_COLOR);
        jLabelTotalWithAmou.setText(Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")");

        jTextFieldWithAmou.setHorizontalAlignment(JTextField.RIGHT);

        jTextFieldWithPerc.setHorizontalAlignment(JTextField.RIGHT);

        jLabelTotalWithPerc.setFont(Constants.FONT_PLAIN);
        jLabelTotalWithPerc.setForeground(Constants.FONT_COLOR);
        jLabelTotalWithPerc.setText(Constants.LANG.getString("Rate"));

        org.jdesktop.layout.GroupLayout jPanel29Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbWhithhelds);
        jPanelGlbWhithhelds.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
                jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel29Layout.createSequentialGroup()
                        .add(56, 56, 56)
                        .add(jLabelTotalWithItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldWithItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(79, 79, 79)
                        .add(jLabelTotalWithPerc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldWithPerc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(127, 127, 127)
                        .add(jLabelTotalWithAmou, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldWithAmou, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 118, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
                jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel29Layout.createSequentialGroup()
                        .add(35, 35, 35)
                        .add(jPanel29Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jTextFieldWithItem, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jTextFieldWithPerc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jTextFieldWithAmou, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabelTotalWithItem)
                                .add(jLabelTotalWithPerc)
                                .add(jLabelTotalWithAmou))
                        .addContainerGap(44, Short.MAX_VALUE))
        );

        jTabbedPaneGlobal.addTab(Constants.LANG.getString("AmountsWithheld"), jPanelGlbWhithhelds);

        jPanelGlbLiterals.setBackground(Constants.BKG_MAIN_COLOR);

        jLabelLegalLiteral.setFont(Constants.FONT_PLAIN);
        jLabelLegalLiteral.setForeground(Constants.FONT_COLOR);
        jLabelLegalLiteral.setText(Constants.LANG.getString("TextoLegal"));
        
		if(Constants.LEGAL_LITERALS != null) {
			DefaultComboBoxModel legalLiteralModel = new DefaultComboBoxModel(Constants.LEGAL_LITERALS.toArray());			
			jComboBoxLegalLiterals.setModel(legalLiteralModel);
			jListLiterals.setModel(jComboBoxLegalLiterals.getModel());
		}
		
        jTextAreaLegal.setSize(100, 10);
        jTextAreaLegal.setFont(Constants.FONT_PLAIN);
        jTextAreaLegal.setForeground(Constants.FONT_COLOR);
        jTextAreaLegal.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
        jTextAreaLegal.setEditable(true);
        jTextAreaLegal.setWrapStyleWord(true);
        jTextAreaLegal.setLineWrap(true);

        ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				int selection = jListLiterals.getSelectedIndex();
				ComboOption legalLiteral = (ComboOption) jComboBoxLegalLiterals.getItemAt(selection);
				jTextAreaLegal.setText(legalLiteral.getValue());
				jTextAreaLegal.setCaretPosition(0);
			}
		};

		jListLiterals.addListSelectionListener(listSelectionListener);
		jListLiterals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPaneLitList.setViewportView(jListLiterals);
		jScrollPaneLitEdit.setViewportView(jTextAreaLegal);
		jScrollPaneLitDest.setViewportView(jListLiteralsDest);
        
		jButtonLitItemAdd.setIcon(new ImageIcon(getClass().getResource("/images/button_list_add.jpg")));
		jButtonLitItemAdd.setToolTipText(Constants.LANG.getString("Add"));
		jButtonLitItemAdd.setBorderPainted(false);
		jButtonLitItemAdd.setContentAreaFilled(false);		
		jButtonLitItemAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(!getLiteralsListDestModel().contains(jTextAreaLegal.getText()))
					getLiteralsListDestModel().addElement(jTextAreaLegal.getText());
			}
		});
		jButtonLitItemRemove.setIcon(new ImageIcon(getClass().getResource("/images/button_list_remove.jpg")));			
		jButtonLitItemRemove.setToolTipText(Constants.LANG.getString("Remove"));
		jButtonLitItemRemove.setBorderPainted(false);
		jButtonLitItemRemove.setContentAreaFilled(false);
		jButtonLitItemRemove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (jListLiteralsDest.getSelectedIndices().length > 0) {
					int[] currentIndexes = jListLiteralsDest.getSelectedIndices();
					int[] selectedIndices = jListLiteralsDest.getSelectedIndices();
					for (int i = selectedIndices.length-1; i >=0; i--) {
						currentIndexes = jListLiteralsDest.getSelectedIndices();
						getLiteralsListDestModel().removeElementAt(currentIndexes[i]);
					}
				}
			}
		});			

        org.jdesktop.layout.GroupLayout jPanel30Layout = new org.jdesktop.layout.GroupLayout(jPanelGlbLiterals);
        jPanelGlbLiterals.setLayout(jPanel30Layout);
		jPanel30Layout.setHorizontalGroup(
			jPanel30Layout.createParallelGroup(GroupLayout.TRAILING)
				.add(jPanel30Layout.createSequentialGroup()
					.add(jPanel30Layout.createParallelGroup(GroupLayout.LEADING)
						.add(jPanel30Layout.createSequentialGroup()
							.add(jScrollPaneLitList, 0, 385, Short.MAX_VALUE)
							.add(jPanel30Layout.createParallelGroup(GroupLayout.LEADING)
								.add(jButtonLitItemAdd, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
								.add(jButtonLitItemRemove, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
						.add(jScrollPaneLitEdit, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE))
					.add(jScrollPaneLitDest, 0, 380, Short.MAX_VALUE))
		);
		jPanel30Layout.setVerticalGroup(
			jPanel30Layout.createParallelGroup(GroupLayout.LEADING)
				.add(jPanel30Layout.createSequentialGroup()
					.add(jPanel30Layout.createParallelGroup(GroupLayout.BASELINE)
						.add(jScrollPaneLitDest, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.add(jPanel30Layout.createSequentialGroup()
							.add(jPanel30Layout.createParallelGroup(GroupLayout.LEADING)
								.add(jPanel30Layout.createSequentialGroup()
									.add(jScrollPaneLitList, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
									//.addPreferredGap(ComponentPlacement.RELATED))
								.add(GroupLayout.TRAILING, jPanel30Layout.createSequentialGroup()
									.add(2,2,2)
									.add(jButtonLitItemRemove)
									.add(2,2,2)
									.add(jButtonLitItemAdd)
									.add(2,2,2)))
							.add(jScrollPaneLitEdit, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.add(2,2,2)
							)))
		);

        jTabbedPaneGlobal.addTab(Constants.LANG.getString("LegalLiterals"), jPanelGlbLiterals);

        jScrollPaneItems.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

        jScrollPaneItems.setBorder(new LineBorder(Constants.SECOND_COLOR, 2, false));

        jTableItems.setModel(new NoEdiTableModel(
                new Object[][]{},
                new String[]{
                    Constants.LANG.getString("Description"),
                    Constants.LANG.getString("Quantity"),
                    Constants.LANG.getString("UnitOfMeasure"),
                    Constants.LANG.getString("Amount") + " (" + Constants.LANG.getString("EuroSimbol") + ")",
                    Constants.LANG.getString("TaxableBase") + " (" + Constants.LANG.getString("EuroSimbol") + ")",
                    Constants.LANG.getString("TaxesOutputs") + " (" + Constants.LANG.getString("EuroSimbol") + ")",
                    Constants.LANG.getString("TaxesWithheld") + " (" + Constants.LANG.getString("EuroSimbol") + ")",
                    Constants.LANG.getString("TotalAmount") + " (" + Constants.LANG.getString("EuroSimbol") + ")"
                },
                new int[]{}
        ));
        for (int i = 0; i < jTableItems.getColumnCount(); i++) {
            jTableItems.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
        }

        jTableItems.setGridColor(Constants.SELECTION_COLOR);
        jScrollPaneItems.setViewportView(jTableItems);
        jTableItems.getTableHeader().setReorderingAllowed(false);
        jTableItems.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableItems.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableItems.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.LEFT));
        jTableItems.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableItems.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableItems.getColumnModel().getColumn(5).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableItems.getColumnModel().getColumn(6).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));
        jTableItems.getColumnModel().getColumn(7).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR, java.awt.Color.white, Constants.FONT_COLOR, Constants.FONT_COLOR, Constants.FONT_PLAIN, javax.swing.SwingConstants.RIGHT));

        jTableItems.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                InvoiceLineType iltBefore = null;
                int row = jTableItems.getSelectedRow();
                if (e.getClickCount() == 2) {
                    if (row >= 0) {
                        iltBefore = items.getInvoiceLine().get(row);
                        addItem(null, iltBefore, row);
                    }
                }

            }
        });

        jLabelTOTTaxWith.setFont(Constants.FONT_PLAIN);
        jLabelTOTTaxWith.setForeground(Constants.FONT_COLOR);
        jLabelTOTTaxWith.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTTaxWith.setText(Constants.LANG.getString("TaxesWithheld"));

        jTextFieldResults[5].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[5].setEditable(false);
        jTextFieldResults[5].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTMinus2.setFont(Constants.FONT_PLAIN);
        jLabelTOTMinus2.setForeground(Constants.FONT_COLOR);
        jLabelTOTMinus2.setText("-");

        jLabelTOTEqual2.setFont(Constants.FONT_PLAIN);
        jLabelTOTEqual2.setForeground(Constants.FONT_COLOR);
        jLabelTOTEqual2.setText("=");

        jLabelTOTInvTotal.setFont(Constants.FONT_PLAIN);
        jLabelTOTInvTotal.setForeground(Constants.FONT_COLOR);
        jLabelTOTInvTotal.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTInvTotal.setText(Constants.LANG.getString("InvoiceTotal"));

        jTextFieldResults[6].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[6].setEditable(false);
        jTextFieldResults[6].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTGrossAmount.setFont(Constants.FONT_PLAIN);
        jLabelTOTGrossAmount.setForeground(Constants.FONT_COLOR);
        jLabelTOTGrossAmount.setText(Constants.LANG.getString("GrossAmount"));

        jTextFieldResults[0].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[0].setEditable(false);
        jTextFieldResults[0].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTMinus1.setFont(Constants.FONT_PLAIN);
        jLabelTOTMinus1.setForeground(Constants.FONT_COLOR);
        jLabelTOTMinus1.setText("-");

        jLabelTOTDiscounts.setFont(Constants.FONT_PLAIN);
        jLabelTOTDiscounts.setForeground(Constants.FONT_COLOR);
        jLabelTOTDiscounts.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTDiscounts.setText(Constants.LANG.getString("Discounts"));

        jTextFieldResults[1].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[1].setEditable(false);
        jTextFieldResults[1].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTTaxBase2.setFont(Constants.FONT_PLAIN);
        jLabelTOTTaxBase2.setForeground(Constants.FONT_COLOR);
        jLabelTOTTaxBase2.setText(Constants.LANG.getString("TaxableBase"));

        jLabelTOTInvTotal2.setFont(Constants.FONT_PLAIN);
        jLabelTOTInvTotal2.setForeground(Constants.FONT_COLOR);
        jLabelTOTInvTotal2.setText(Constants.LANG.getString("InvoiceTotal"));

        jLabelTOTGlobWith.setFont(Constants.FONT_PLAIN);
        jLabelTOTGlobWith.setForeground(Constants.FONT_COLOR);
        jLabelTOTGlobWith.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTGlobWith.setText(Constants.LANG.getString("AmountsWithheld"));

        jLabelTOTTaxOut.setFont(Constants.FONT_PLAIN);
        jLabelTOTTaxOut.setForeground(Constants.FONT_COLOR);
        jLabelTOTTaxOut.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTTaxOut.setText(Constants.LANG.getString("TaxesOutputs"));

        jTextFieldResults[7].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[7].setEditable(false);
        jTextFieldResults[7].setHorizontalAlignment(JTextField.RIGHT);

        jTextFieldResults[4].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[4].setEditable(false);
        jTextFieldResults[4].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTPlus3.setFont(Constants.FONT_PLAIN);
        jLabelTOTPlus3.setForeground(Constants.FONT_COLOR);
        jLabelTOTPlus3.setText("+");

        jLabelTOTReimb.setFont(Constants.FONT_PLAIN);
        jLabelTOTReimb.setForeground(Constants.FONT_COLOR);
        jLabelTOTReimb.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTReimb.setText(Constants.LANG.getString("ReimbursableExpenses"));

        jTextFieldResults[8].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[8].setEditable(false);
        jTextFieldResults[8].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTCharges.setFont(Constants.FONT_PLAIN);
        jLabelTOTCharges.setForeground(Constants.FONT_COLOR);
        jLabelTOTCharges.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTCharges.setText(Constants.LANG.getString("Charges"));

        jLabelTOTPlus1.setFont(Constants.FONT_PLAIN);
        jLabelTOTPlus1.setForeground(Constants.FONT_COLOR);
        jLabelTOTPlus1.setText("+");

        jTextFieldResults[2].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[2].setEditable(false);
        jTextFieldResults[2].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTEqual1.setFont(Constants.FONT_PLAIN);
        jLabelTOTEqual1.setForeground(Constants.FONT_COLOR);
        jLabelTOTEqual1.setText("=");

        jLabelTOTTaxBase.setFont(Constants.FONT_PLAIN);
        jLabelTOTTaxBase.setForeground(Constants.FONT_COLOR);
        jLabelTOTTaxBase.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTTaxBase.setText(Constants.LANG.getString("TaxableBase"));

        jTextFieldResults[3].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[3].setEditable(false);
        jTextFieldResults[3].setHorizontalAlignment(JTextField.RIGHT);

        jTextFieldResults[9].setEditable(true);
        jTextFieldResults[9].setHorizontalAlignment(JTextField.RIGHT);
        jTextFieldResults[9].addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                if (((JTextField) evt.getSource()) != null && ((JTextField) evt.getSource()).getText() != null) {
                    if (!((JTextField) evt.getSource()).getText().trim().equals("")) {
                        try {
                            Double d = Double.valueOf(((JTextField) evt.getSource()).getText());
                            if (d.doubleValue() < 0) {
                                d = 0.0;
                                ((JTextField) evt.getSource()).setForeground(Constants.BKG_ERROR_COLOR);
                                ((GenerateInvoice32Window) jTextFieldWithItem.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNegative"), Constants.ERROR_MSG_COLOR);
                            }
                            ((JTextField) evt.getSource()).setText(String.valueOf(DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("item_financial_expenses")))));
                        } catch (NumberFormatException nfe) {
                            ((JTextField) evt.getSource()).setText("");
                            ((JTextField) evt.getSource()).setForeground(Constants.BKG_ERROR_COLOR);
                            ((GenerateInvoice32Window) jTextFieldWithItem.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                        }
                    }
                    recalculateTotals();
                }
            }
        });
        jTextFieldResults[9].addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent arg0) {
                ((JTextField) arg0.getSource()).setBackground(Color.white);
                ((JTextField) arg0.getSource()).setForeground(Constants.FONT_COLOR);
            }
        });

        jLabelTOTFinancial.setFont(Constants.FONT_PLAIN);
        jLabelTOTFinancial.setForeground(Constants.FONT_COLOR);
        jLabelTOTFinancial.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTFinancial.setText(Constants.LANG.getString("FinancialExpenses"));

        jLabelTOTPlus4.setFont(Constants.FONT_PLAIN);
        jLabelTOTPlus4.setForeground(Constants.FONT_COLOR);
        jLabelTOTPlus4.setText("+");

        jLabelTOTInvExec.setFont(Constants.FONT_PLAIN);
        jLabelTOTInvExec.setForeground(Constants.FONT_COLOR);
        jLabelTOTInvExec.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelTOTInvExec.setText(Constants.LANG.getString("TotalExecutable"));

        jTextFieldResults[10].setBackground(Constants.BKG_MAIN_COLOR);
        jTextFieldResults[10].setEditable(false);
        jTextFieldResults[10].setHorizontalAlignment(JTextField.RIGHT);

        jLabelTOTEqual3.setFont(Constants.FONT_PLAIN);
        jLabelTOTEqual3.setForeground(Constants.FONT_COLOR);
        jLabelTOTEqual3.setText("=");

        jLabelTOTALS.setFont(Constants.FONT_BOLD);
        jLabelTOTALS.setForeground(Constants.FONT_COLOR);
        jLabelTOTALS.setText(Constants.LANG.getString("TOTALS"));

        jLabelTOTPlus2.setFont(Constants.FONT_PLAIN);
        jLabelTOTPlus2.setForeground(Constants.FONT_COLOR);
        jLabelTOTPlus2.setText("+");

        jLabelTOTMinus3.setFont(Constants.FONT_PLAIN);
        jLabelTOTMinus3.setForeground(Constants.FONT_COLOR);
        jLabelTOTMinus3.setText("-");

        jButtonItemAdd.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_plus.jpg")));
        jButtonItemAdd.setToolTipText(Constants.LANG.getString("Add"));
        jButtonItemAdd.setBorderPainted(false);
        jButtonItemAdd.setContentAreaFilled(false);
        jButtonItemAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonItemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItem(evt, null, -1);
            }
        });

        jButtonItemDel.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_minus.jpg")));
        jButtonItemDel.setToolTipText(Constants.LANG.getString("Remove"));
        jButtonItemDel.setBorderPainted(false);
        jButtonItemDel.setContentAreaFilled(false);
        jButtonItemDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonItemDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delItem(evt, -1);
            }
        });

        jLabelTOTMessage.setFont(Constants.FONT_ITALIC);
        jLabelTOTAsterisk.setFont(Constants.FONT_ITALIC);
        jLabelTOTMessage.setForeground(Constants.OK_MSG_COLOR);
        jLabelTOTAsterisk.setForeground(Constants.OK_MSG_COLOR);

        jTextAreaTOTXMLExtAddData.setSize(0, 0);
        jTextAreaTOTXMLExtAddData.setFont(Constants.FONT_PLAIN);
        jTextAreaTOTXMLExtAddData.setForeground(Constants.FONT_COLOR);
        jTextAreaTOTXMLExtAddData.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
        jTextAreaTOTXMLExtAddData.setEditable(true);
        jScrollPaneTOTXMLAddData.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
        jScrollPaneTOTXMLAddData.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
        jScrollPaneTOTXMLAddData.setViewportView(jTextAreaTOTXMLExtAddData);

        jButtonTOTXMLAddData.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_accept.jpg")));
        jButtonTOTXMLAddData.setToolTipText(Constants.LANG.getString("Verify"));
        jButtonTOTXMLAddData.setBorderPainted(false);
        jButtonTOTXMLAddData.setContentAreaFilled(false);
        jButtonTOTXMLAddData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonTOTXMLAddData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyXML(evt);
            }

            private void verifyXML(ActionEvent evt) {
                if (jTextAreaTOTXMLExtAddData.getText() == null || "".equals(jTextAreaTOTXMLExtAddData.getText().trim())) {
                    error2 = "";
                    jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
                } else {
                    // Valid Invoice 3.1
                    InputStream is = this.getClass().getResourceAsStream(Constants.I32_EMPTY_PATH);
                    // Extension introduced by user
                    InputStream isExt = null;
                    try {
                        isExt = new ByteArrayInputStream(jTextAreaTOTXMLExtAddData.getText().getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        logger.error("Error creating a bytes array with extension data. The encoding is unsupported: " + e.getMessage());
                    }

                    String[] nodeRoute = {"Extensions"};

                    File f = null;
                    try {
                        f = XMLFacturaeUtil.insertarExtension(is, isExt, nodeRoute);
                    } catch (ValidationException ve) {
                        jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                        error2 = ve.getMessage();
                        ((GenerateInvoice32Window) jButtonTOTXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                    } catch (FileNotFoundException e) {
                        jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                        error2 = e.getMessage();
                        ((GenerateInvoice32Window) jButtonTOTXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                    }

                    if (f != null) {
                        try {
                            ValidatorUtil vu = ValidatorUtil.getInstance();
                            vu.validate(f, Constants.VERSION32);
                            error2 = "";
                            jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
                            ((GenerateInvoice32Window) jButtonTOTXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageExtensionAdded"), Constants.OK_MSG_COLOR);
                        } catch (ValidationException ve) {
                            jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                            error2 = ve.getMessage();
                            ((GenerateInvoice32Window) jButtonTOTXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                        } catch (SAXException se) {
                            jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                            error2 = se.getMessage();
                            ((GenerateInvoice32Window) jButtonTOTXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                        } catch (IOException ioe) {
                            jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom_error.jpg")));
                            error2 = ioe.getMessage();
                            ((GenerateInvoice32Window) jButtonTOTXMLAddData.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKMessageExtensionAdded"), Constants.ERROR_MSG_COLOR);
                        }
                    }
                }
            }
        });

        jButtonTOTXMLAddDataZoom.setIcon(new ImageIcon(getClass().getResource("/images/button_mini_zoom.jpg")));
        jButtonTOTXMLAddDataZoom.setToolTipText(Constants.LANG.getString("Zoom"));
        jButtonTOTXMLAddDataZoom.setBorderPainted(false);
        jButtonTOTXMLAddDataZoom.setContentAreaFilled(false);
        jButtonTOTXMLAddDataZoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseEntered(evt);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                InvoiceDetail32Panel.this.mouseExited(evt);
            }
        });
        jButtonTOTXMLAddDataZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputExtensionDialog ied = new InputExtensionDialog((JFrame) jTextAreaTOTXMLExtAddData.getTopLevelAncestor(), true, jTextAreaTOTXMLExtAddData.getText(), error2);
                ied.setVisible(true);
                jTextAreaTOTXMLExtAddData.setText(ied.getValues());
            }
        });

        jLabelTOTXMLAddData = new JLabel();
        jLabelTOTXMLAddData.setText(Constants.LANG.getString("Extension") + " (XML)");
        jLabelTOTXMLAddData.setFont(Constants.FONT_PLAIN);
        jLabelTOTXMLAddData.setForeground(Constants.FONT_COLOR);

        int textFieldTOTsize = 60;

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jTabbedPaneGlobal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 970, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4Layout.createSequentialGroup()
                                        .add(jScrollPaneItems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 900, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                                .add(jButtonItemAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jButtonItemDel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(16, 16, 16))
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .add(10, 10, 10)
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                .add(jPanel4Layout.createSequentialGroup()
                                                        .add(jLabelTOTALS, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                                                .add(jPanel4Layout.createSequentialGroup()
                                                        .add(jLabelTOTGrossAmount, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[0], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[0])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTMinus1)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTDiscounts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[1], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[1])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTPlus1)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTCharges, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[2], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[2])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTEqual1)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTTaxBase, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[3], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[3])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTAsterisk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                                .add(jPanel4Layout.createSequentialGroup()
                                                        .add(jLabelTOTTaxBase2)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTPlus2)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTTaxOut, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[4], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[4])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTMinus2)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTTaxWith, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[5], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[5])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTEqual2)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTInvTotal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                                        .add(jTextFieldResults[6], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[6]))
                                                .add(jPanel4Layout.createSequentialGroup()
                                                        .add(jLabelTOTInvTotal2)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTMinus3)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTGlobWith, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[7], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[7])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTPlus3)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTReimb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[8], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[8])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTPlus4)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTFinancial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                                        .add(jTextFieldResults[9], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelCurrency[9])
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                                        .add(jLabelTOTEqual3)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTOTInvExec, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jTextFieldResults[10], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, textFieldTOTsize, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                        .add(jLabelTCurrency))
                                        )
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                                .add(jPanel4Layout.createSequentialGroup()
                                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                                .add(jLabelTOTXMLAddData)
                                                                .add(jTextAreaTOTXMLExtAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                                .add(jButtonTOTXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jButtonTOTXMLAddDataZoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
        );

        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(jScrollPaneItems, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jPanel4Layout.createSequentialGroup()
                                        .add(jButtonItemAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jButtonItemDel)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTabbedPaneGlobal, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 126, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                .add(jPanel4Layout.createSequentialGroup()
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                                .add(jLabelTOTALS, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(jLabelTOTMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(6, 6, 6)
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                                .add(jTextFieldResults[0], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTMinus1)
                                                .add(jTextFieldResults[1], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTPlus1)
                                                .add(jTextFieldResults[2], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTEqual1)
                                                .add(jTextFieldResults[3], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTAsterisk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTGrossAmount)
                                                .add(jLabelTOTDiscounts)
                                                .add(jLabelTOTCharges)
                                                .add(jLabelTOTTaxBase)
                                                .add(jLabelCurrency[0])
                                                .add(jLabelCurrency[1])
                                                .add(jLabelCurrency[2])
                                                .add(jLabelCurrency[3]))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                                .add(jLabelTOTPlus2)
                                                .add(jTextFieldResults[4], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTMinus2)
                                                .add(jTextFieldResults[5], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTEqual2)
                                                .add(jLabelTOTTaxBase2)
                                                .add(jLabelTOTTaxOut)
                                                .add(jLabelTOTTaxWith)
                                                .add(jLabelTOTInvTotal)
                                                .add(jTextFieldResults[6], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelCurrency[4])
                                                .add(jLabelCurrency[5])
                                                .add(jLabelCurrency[6]))
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                                .add(jLabelTOTMinus3)
                                                .add(jTextFieldResults[7], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTPlus3)
                                                .add(jTextFieldResults[8], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTPlus4)
                                                .add(jLabelTOTInvTotal2)
                                                .add(jLabelTOTGlobWith)
                                                .add(jLabelTOTReimb)
                                                .add(jTextFieldResults[9], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTEqual3)
                                                .add(jTextFieldResults[10], org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jLabelTOTInvExec)
                                                .add(jLabelTOTFinancial)
                                                .add(jLabelCurrency[7])
                                                .add(jLabelCurrency[8])
                                                .add(jLabelCurrency[9])
                                                .add(jLabelTCurrency)))
                                .add(jPanel4Layout.createSequentialGroup()
                                        .add(jLabelTOTXMLAddData, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                                                .add(jTextAreaTOTXMLExtAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(jPanel4Layout.createSequentialGroup()
                                                        .add(jButtonTOTXMLAddData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                        .add(jButtonTOTXMLAddDataZoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                        .add(22, 22, 22))
        );

        this.setBackground(Constants.BKG_MAIN_COLOR);
        this.setBorder(
        		BorderFactory.createTitledBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true), 
        				Constants.LANG.getString("InvoiceDetailData"), 
        				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
        				Constants.FONT_BOLD, Constants.FONT_COLOR));
    }

    /**
     * This method load the configuration information
     */
    private void loadDecimalConfiguration() {
        try {
            decimalProps.load(this.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact" + codeVersion + "d_resource")));
        } catch (IOException ioe) {
            logger.error("It is not possible to obtain the decimals 3.2 properties file: " + ioe.getMessage());
        }
    }

    /**
     * This method load the data base information
     */
    private void loadDBData() {
        SQLQuery s;
        try {
            s = FacturaeManager.getInstance().executeQuery("SELECT PAYMENT_ID FROM PAYMENT_DETAIL");
            List<?> ls = s.list();
            payments = new DefaultComboBoxModel<String>();

            if (ls != null && ls.size() > 0 && ls.get(0) != null) {
                int count = ls.size();
                for (int i = 0; i < count; i++) {
                    payments.addElement(new String(Constants.LANG.getString("PaymentDetails") + ": " + ((Integer) ls.get(i)).toString()));
                }
                payments.addElement("");
            }

            s = FacturaeManager.getInstance().executeQuery("SELECT ACCOUNT_ID,IBAN FROM ACCOUNT");
            ls = s.list();

            accounts = new DefaultComboBoxModel<String>();
            accounts2 = new DefaultComboBoxModel<String>();
            accounts3 = new DefaultComboBoxModel<String>();

            if (ls != null && ls.size() > 0 && ls.get(0) != null) {
                int count = ls.size();
                for (int i = 0; i < count; i++) {
                    String elem = new String(((Object[]) ls.get(i))[0].toString() + ": " + ((Object[]) ls.get(i))[1].toString());
                    accounts.addElement(elem);
                    accounts2.addElement(elem);
                    accounts3.addElement(elem);
                }
                accounts.addElement("");
                accounts2.addElement("");
                accounts3.addElement("");
            }
        } catch (Exception e) {
            logger.error("An error occurred when loading the database information: " + e.getMessage());
        }
    }

    private void addDiscount(java.awt.event.ActionEvent evt) {

        // Total gross amount and total cost is actualized for recalculate
        String tibString = jTextFieldResults[0].getText();
        Double tib = null;
        String totString = jTextFieldResults[3].getText();
        Double tot = null;
        if (tibString != null && !"".equals(tibString)) {
            try {
                tib = Double.parseDouble(tibString);
                tib = DoubleUtil.round(tib, 2);
                tot = Double.parseDouble(totString);
                tot = DoubleUtil.round(tot, 2);
            } catch (NumberFormatException e) {
                logger.error("An number format exception occurred while a discount was being added: " + e.getMessage());
                ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                return;
            }
        }

        if ((tib == null || "".equals(tib))
                || (tot == null || "".equals(tot))) { // If gross amount or total cost does not have a positive value
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamItemRequired"), Constants.ERROR_MSG_COLOR);
            return;
        }

        InputDiscountDialog igd = new InputDiscountDialog((JFrame) jPanelGlbDiscounts.getTopLevelAncestor(), true, tot, tib, Constants.VERSION32, true); // Tot is the source, tib is the value that may not exceed

        igd.setVisible(true);

        String[] result = igd.getValues();

        if (result == null || result.length != 3) {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);
            return;
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageDiscountAdded"), Constants.OK_MSG_COLOR);
        }

        String item = result[0];
        String rate = result[1];
        String amount = result[2];

        if (item != null && !"".equals(item)) {
            ((NoEdiTableModel) jTableGlobDisc.getModel()).addRow(new Object[]{item, rate, amount});
            Double totDiscount = 0.00;
            if (jTextFieldTotalDisc.getText() != null && !jTextFieldTotalDisc.getText().trim().equals("")) {
                totDiscount = Double.valueOf(jTextFieldTotalDisc.getText());
            }
            jTextFieldTotalDisc.setText(String.valueOf(DoubleUtil.round((totDiscount + Double.valueOf(amount)), Integer.parseInt(decimalProps.getProperty("item_discount"))).toString()));
            jTextFieldResults[1].setText(jTextFieldTotalDisc.getText());
            recalculateTotals();
        }
    }

    private void delDiscount(java.awt.event.ActionEvent evt) {
        int row = jTableGlobDisc.getSelectedRow();
        if (row >= 0) {

            Double totalDiscount = 0.00;
            try {
                if (jTextFieldTotalDisc != null && jTextFieldTotalDisc.getText() != null && !jTextFieldTotalDisc.getText().trim().equals("")) {
                    totalDiscount = Double.parseDouble(jTextFieldTotalDisc.getText());
                }
                jTextFieldTotalDisc.setText(String.valueOf(DoubleUtil.round((totalDiscount - Double.parseDouble(((NoEdiTableModel) jTableGlobDisc.getModel()).getValueAt(row, 2).toString())), Integer.parseInt(decimalProps.getProperty("item_discount"))).toString()));
            } catch (NumberFormatException e) {
                logger.error("An number format exception occurred while a discount was being deleted: " + e.getMessage());
                ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                return;
            }
            jTextFieldResults[1].setText(jTextFieldTotalDisc.getText());
            ((NoEdiTableModel) jTableGlobDisc.getModel()).removeRow(row);
            recalculateTotals();
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageDiscountDeleted"), Constants.OK_MSG_COLOR);
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
        }
    }

    private void addReim(java.awt.event.ActionEvent evt) {

        // Total gross amount is actualized. If gross amount does not have a value then return
        String tibString = jTextFieldResults[0].getText();
        if (tibString == null || "".equals(tibString.trim())) {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage("Debe facturar algun concepto antes de aplicar suplidos", Constants.ERROR_MSG_COLOR);
            return;
        }

        InputReimDialog igd = new InputReimDialog((JFrame) jPanelGlbDiscounts.getTopLevelAncestor(), true, codeVersion);

        igd.setVisible(true);

        String[] result = igd.getValues();

        if (result == null || result.length != 7) {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKIncompleteForm"), Constants.ERROR_MSG_COLOR);
            return;
        }

        String amount = result[6];

        if (amount != null && !"".equals(amount)) {// If not, it was canceled by user

            ((NoEdiTableModel) jTableGlobReim.getModel()).addRow(new Object[]{
                result[0],
                result[1],
                result[2],
                result[3],
                result[4],
                result[5],
                result[6],});

            String strTotCharge = jTextFieldTotalReim.getText();
            if (strTotCharge == null || "".equals(strTotCharge.trim())) {
                strTotCharge = "0.0";
            }
            Double totValue = Double.valueOf(strTotCharge);
            Double doubleAmount = Double.valueOf(amount);
            jTextFieldTotalReim.setText(String.valueOf(DoubleUtil.round((totValue + doubleAmount), Integer.parseInt(decimalProps.getProperty("item_total_reim_amount")))));
            jTextFieldResults[8].setText(String.valueOf(DoubleUtil.round((totValue + doubleAmount), Integer.parseInt(decimalProps.getProperty("item_total_reim_amount")))));
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageReimAdded"), Constants.OK_MSG_COLOR);
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);
        }
        recalculateTotals();
    }

    private void delReim(java.awt.event.ActionEvent evt) {
        int row = jTableGlobReim.getSelectedRow();
        if (row >= 0) {
            String strTotReim = jTextFieldTotalReim.getText();
            String strLineAmount = ((NoEdiTableModel) jTableGlobReim.getModel()).getValueAt(row, 6).toString();
            if (strTotReim == null || "".equals(strTotReim.trim())) {
                strTotReim = "0.0";
            }

            Double totValue = null;
            Double lineAmount = null;
            try {
                totValue = Double.valueOf(strTotReim);
                lineAmount = Double.valueOf(strLineAmount);
            } catch (NumberFormatException e) {
                logger.error("An number format exception occurred while a reimbursable expense was being deleted: " + e.getMessage());
                ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                return;
            }
            jTextFieldTotalReim.setText(String.valueOf(DoubleUtil.round((totValue - lineAmount), Integer.parseInt(decimalProps.getProperty("item_total_reim_amount")))));
            jTextFieldResults[8].setText(String.valueOf(DoubleUtil.round((totValue - lineAmount), Integer.parseInt(decimalProps.getProperty("item_total_reim_amount")))));
            ((NoEdiTableModel) jTableGlobReim.getModel()).removeRow(row);
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageReimDeleted"), Constants.OK_MSG_COLOR);
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
        }
        recalculateTotals();
    }

    private void addAttachment(java.awt.event.ActionEvent evt) {

        paintAndFocusAttcArea(false, Constants.BKG_MAIN_COLOR);

        ArrayList<String> prevDesc = new ArrayList<String>();
        DefaultTableModel dtm = (DefaultTableModel) jTableGlobAttc.getModel();
        int includedLength = 0;
        for (int i = 0; i < dtm.getRowCount(); ++i) {
            prevDesc.add((String) dtm.getValueAt(i, 2));
            if (dtm.getValueAt(i, 3) != null && Constants.LANG.getString("Yes").equals(dtm.getValueAt(i, 3).toString())) {
                File f = new File(dtm.getValueAt(i, 1).toString());
                includedLength += f.length();
            }
        }

        InputAttachmentDialog iad = InputAttachmentDialog.getInstance((JFrame) jPanelGlbDiscounts.getTopLevelAncestor(), prevDesc, true, includedLength);

        iad.setVisible(true);

        String[] result = iad.getValues();

        if (result == null || result.length != 4) {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKIncompleteForm"), Constants.ERROR_MSG_COLOR);
            return;
        }
        String format = result[0];
        String document = result[1];
        String description = result[2];
        String includingXML = result[3];

        if (format != null && !"".equals(format) && document != null && !"".equals(document)) {// If not, it was canceled
            ((NoEdiTableModel) jTableGlobAttc.getModel()).addRow(new Object[]{
                format,
                document,
                description,
                includingXML
            });
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageAttachAdded"), Constants.OK_MSG_COLOR);
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);
        }
    }

    private void delAttachment(java.awt.event.ActionEvent evt) {
        int row = jTableGlobAttc.getSelectedRow();
        if (row >= 0) {
            ((NoEdiTableModel) jTableGlobAttc.getModel()).removeRow(row);
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMesaggeAttachDeleted"), Constants.OK_MSG_COLOR);
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
        }
    }

    private void addItem(java.awt.event.ActionEvent evt, InvoiceLineType lineToEdit, int lineNumber) {
        Invoice32DetailDialog idd = null;
        if (jTableItems.getModel().getRowCount() == 0) {
            idd = new Invoice32DetailDialog((JFrame) jPanelGlbDiscounts.getTopLevelAncestor(), true, null, false);
        } else {
            idd = new Invoice32DetailDialog((JFrame) jPanelGlbDiscounts.getTopLevelAncestor(), true, ((GenerateInvoice32Window) this.getTopLevelAncestor()).specialTB, setVisualizationOnly);
        }

        if (lineToEdit != null) {
            idd.setValues((es.mityc.appfacturae.facturae32.InvoiceLineType) lineToEdit);
        }

        idd.setVisible(true);

        InvoiceLineType ilt = idd.getValues();
        Object[] ob = new Object[]{"", "", "", "", "", "", "", ""};
        if (ilt.getItemDescription() != null && !ilt.getItemDescription().trim().equals("")
                && ilt.getQuantity() != 0.0 && !"".equals(ilt.getQuantity())) { // If not, it was canceled by user
            ob[0] = ilt.getItemDescription();
            ob[1] = ilt.getQuantity();
            if (ilt.getUnitOfMeasure() != null && !ilt.getUnitOfMeasure().trim().equals("")) {
                ob[2] = Constants.LANG.getString(Constants.APP_PROP.getProperty("unitsOfMeasure" + codeVersion).split(";")[Integer.parseInt(ilt.getUnitOfMeasure()) - 1]);
            }
            ob[3] = ilt.getTotalCost();
            ob[4] = DoubleUtil.round(ilt.getGrossAmount(), Integer.parseInt(decimalProps.getProperty("item_taxable_base")));
            int numTaxes = 0;
            if (((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs() != null && ((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs().getTax() != null) {
                numTaxes = ((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs().getTax().size();
            }
            String typeTax = "";
            boolean different = false;
            double amountTax = 0.00;
            for (int i = 0; i < numTaxes; i++) {
                Tax t = ((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs().getTax().get(i);
                if (!different) {
                    if (typeTax.equals("")) {
                        typeTax = t.getTaxTypeCode();
                    } else if (!typeTax.equals(t.getTaxTypeCode())) {
                        typeTax = Constants.LANG.getString("DifferentValues");
                        different = true;
                    }
                }

                if (t.getSpecialTaxAmount() != null && t.getSpecialTaxAmount().getTotalAmount() > 0.00) {
                    amountTax = amountTax + t.getSpecialTaxAmount().getTotalAmount();
                    // Special TaxableBase is Used
                    jLabelTOTMessage.setText(Constants.LANG.getString("MessageTaxableBaseUsed"));
                    jLabelTOTAsterisk.setText("*");
                    ((GenerateInvoice32Window) this.getTopLevelAncestor()).specialTB = true;
                } else {
                    amountTax = amountTax + t.getTaxAmount().getTotalAmount();
                }

                if (t.getEquivalenceSurchargeAmount() != null && t.getEquivalenceSurchargeAmount().getTotalAmount() != 0) {
                    amountTax = amountTax + t.getEquivalenceSurchargeAmount().getTotalAmount();
                }
            }
            if (numTaxes > 0) {
                if (!"".equals(typeTax) && !Constants.LANG.getString("DifferentValues").equals(typeTax)) {
                    typeTax = Constants.LANG.getString(Constants.APP_PROP.getProperty("taxesType" + codeVersion).split(";")[Integer.valueOf(typeTax) - 1]).split(" ")[0];
                }
                ob[5] = typeTax + " - " + String.valueOf(DoubleUtil.round(amountTax, Integer.parseInt(decimalProps.getProperty("item_tax_Out"))));
            } else {
                ob[5] = Constants.LANG.getString(Constants.APP_PROP.getProperty("specialTaxableEvent").split(";")[Integer.parseInt(((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getSpecialTaxableEvent().getSpecialTaxableEventCode())]);
            }
            int numTaxesW = ilt.getTaxesWithheld().getTax().size();
            String typeTaxW = "";
            boolean differentW = false;
            double amountTaxW = 0.00;
            for (int i = 0; i < numTaxesW; i++) {
                TaxType t = ilt.getTaxesWithheld().getTax().get(i);
                if (!differentW) {
                    if (typeTaxW.equals("")) {
                        typeTaxW = t.getTaxTypeCode();
                    } else if (!typeTaxW.equals(t.getTaxTypeCode())) {
                        typeTaxW = Constants.LANG.getString("DifferentValues");
                        differentW = true;
                    }
                }
                amountTaxW = amountTaxW + t.getTaxAmount().getTotalAmount();
            }

            if (numTaxesW > 0) {
                if (!"".equals(typeTaxW) && !Constants.LANG.getString("DifferentValues").equals(typeTaxW)) {
                    typeTaxW = Constants.LANG.getString(Constants.APP_PROP.getProperty("taxesType" + codeVersion).split(";")[Integer.valueOf(typeTaxW) - 1]).split(" ")[0];
                }
                ob[6] = typeTaxW + " - " + String.valueOf(DoubleUtil.round(amountTaxW, Integer.parseInt(decimalProps.getProperty("item_tax_With"))));
            } else {
                ob[6] = "";
            }

            Double doubleObj = (Double.valueOf(ob[4].toString()) + amountTax) - amountTaxW;
            doubleObj = DoubleUtil.round(doubleObj, Integer.parseInt(decimalProps.getProperty("item_total_cost")));
            ob[7] = doubleObj;

            if (lineToEdit != null) {
                delItem(evt, lineNumber);
                items.getInvoiceLine().add(lineNumber, ilt);
                ((NoEdiTableModel) jTableItems.getModel()).insertRow(lineNumber, ob);
            } else {
                items.getInvoiceLine().add(ilt);
                ((NoEdiTableModel) jTableItems.getModel()).addRow(ob);
            }

            if (jTextFieldResults[0].getText() != null && !jTextFieldResults[0].getText().trim().equals("")) {
                jTextFieldResults[0].setText(String.valueOf(DoubleUtil.round((Double.parseDouble(jTextFieldResults[0].getText()) + ilt.getGrossAmount()), Integer.parseInt(decimalProps.getProperty("item_total_cost")))));
            } else {
                jTextFieldResults[0].setText(String.valueOf(ilt.getGrossAmount()));
            }

            if (jTextFieldResults[4].getText() != null && !jTextFieldResults[4].getText().trim().equals("")) {
                jTextFieldResults[4].setText(String.valueOf(DoubleUtil.round((Double.parseDouble(jTextFieldResults[4].getText()) + amountTax), Integer.parseInt(decimalProps.getProperty("item_tax_Out")))));
            } else {
                jTextFieldResults[4].setText(String.valueOf(DoubleUtil.round(amountTax, Integer.parseInt(decimalProps.getProperty("item_tax_Out")))));
            }

            if (jTextFieldResults[5].getText() != null && !jTextFieldResults[5].getText().trim().equals("")) {
                jTextFieldResults[5].setText(String.valueOf(DoubleUtil.round((Double.parseDouble(jTextFieldResults[5].getText()) + amountTaxW), Integer.parseInt(decimalProps.getProperty("item_tax_With")))));
            } else {
                jTextFieldResults[5].setText(String.valueOf(DoubleUtil.round(amountTaxW, Integer.parseInt(decimalProps.getProperty("item_tax_With")))));
            }
            tibChanged();
            recalculateTotals();
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);
        }
    }

    private void delItem(java.awt.event.ActionEvent evt, int lineNumber) {
        int row = lineNumber;
        if (lineNumber == -1) {
            row = jTableItems.getSelectedRow();
        }
        if (row >= 0) {
            InvoiceLineType ilt = items.getInvoiceLine().get(row);
            int numTaxes = 0;
            if (((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs() != null && ((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs().getTax() != null) {
                numTaxes = ((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs().getTax().size();
            }
            for (int i = 0; i < numTaxes; i++) {
                Tax t = ((es.mityc.appfacturae.facturae32.InvoiceLineType) ilt).getTaxesOutputs().getTax().get(i);
                if (t.getSpecialTaxAmount() != null && t.getSpecialTaxAmount().getTotalAmount() > 0.00) {
                    // Special TaxableBase is used. If its only one, it will be deleted --> No Special Taxable Base.
                    if (jTableItems.getRowCount() == 1) {
                        ((GenerateInvoice32Window) this.getTopLevelAncestor()).specialTB = false;
                        jLabelTOTMessage.setText("");
                        jLabelTOTAsterisk.setText("");
                    }
                }
            }
            jTextFieldResults[0].setText(String.valueOf(DoubleUtil.round((Double.parseDouble(jTextFieldResults[0].getText()) - ilt.getGrossAmount()), Integer.parseInt(decimalProps.getProperty("item_gross_cost")))));
            Object amountTaxObject = ((NoEdiTableModel) jTableItems.getModel()).getValueAt(row, 5);
            if (amountTaxObject != null && !"".equals(amountTaxObject.toString())) {
                String[] amountTaxArray = amountTaxObject.toString().split(" - ");
                if (amountTaxArray[amountTaxArray.length - 1] != null
                        && !"".equals(amountTaxArray[amountTaxArray.length - 1])) {
                    double amountTax = Double.parseDouble(amountTaxArray[amountTaxArray.length - 1]);
                    jTextFieldResults[4].setText(String.valueOf(DoubleUtil.round((Double.parseDouble(jTextFieldResults[4].getText()) - amountTax), Integer.parseInt(decimalProps.getProperty("item_tax_Out")))));
                }
            }
            Object amountTaxWObject = ((NoEdiTableModel) jTableItems.getModel()).getValueAt(row, 6);
            if (amountTaxWObject != null && !"".equals(amountTaxWObject.toString())) {
                String[] amountTaxWArray = amountTaxWObject.toString().split(" - ");
                if (amountTaxWArray[amountTaxWArray.length - 1] != null
                        && !"".equals(amountTaxWArray[amountTaxWArray.length - 1])) {
                    double amountTaxW = Double.parseDouble(amountTaxWArray[amountTaxWArray.length - 1]);
                    jTextFieldResults[5].setText(String.valueOf(DoubleUtil.round((Double.parseDouble(jTextFieldResults[5].getText()) - amountTaxW), Integer.parseInt(decimalProps.getProperty("item_tax_With")))));
                }
            }
            items.getInvoiceLine().remove(row);
            ((NoEdiTableModel) jTableItems.getModel()).removeRow(row);
            tibChanged();
            recalculateTotals();
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
        }
    }

    public void setItemCountOutputTax() {
        jLabelTOTMessage.setText(Constants.LANG.getString("MessageTaxableBaseUsed"));
        jLabelTOTAsterisk.setText("*");
        ((GenerateInvoice32Window) this.getTopLevelAncestor()).specialTB = true;
    }

    private void addCharge(java.awt.event.ActionEvent evt) {
        String tibString = jTextFieldResults[0].getText();
        Double tib = null;
        if (tibString != null && !"".equals(tibString)) {
            try {
                tib = Double.parseDouble(tibString);
                tib = DoubleUtil.round(tib, 2);
            } catch (NumberFormatException e) {
                logger.error("An number format exception occurred while a charge was being added: " + e.getMessage());
                ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                return;
            }
        }

        if (tib == null || "".equals(tib)) {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage("Debe facturar algun concepto antes de aplicar cargos", Constants.ERROR_MSG_COLOR);
            return;
        }

        InputChargeDialog igd = new InputChargeDialog((JFrame) jPanelGlbDiscounts.getTopLevelAncestor(), true, tib, Constants.VERSION32, true);

        igd.setVisible(true);

        String[] result = igd.getValues();

        if (result == null || result.length != 3) {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKIncompleteForm"), Constants.ERROR_MSG_COLOR);
            return;
        }

        String item = result[0];
        String rate = result[1];
        String amount = result[2];

        if (item != null && !"".equals(item)) {// En caso contrario, se cancelo
            ((NoEdiTableModel) jTableGlobChar.getModel()).addRow(new Object[]{
                item,
                rate,
                amount
            });

            String strTotCharge = jTextFieldTotalChar.getText();
            if (strTotCharge == null || "".equals(strTotCharge.trim())) {
                strTotCharge = "0.0";
            }
            Double totValue = Double.valueOf(strTotCharge);
            Double doubleAmount = Double.valueOf(amount);
            jTextFieldTotalChar.setText(String.valueOf(DoubleUtil.round(totValue + doubleAmount, Integer.parseInt(decimalProps.getProperty("item_charge")))));
            jTextFieldResults[2].setText(jTextFieldTotalChar.getText());
            recalculateTotals();
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageChargeAdded"), Constants.OK_MSG_COLOR);
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);
        }
    }

    private void delCharge(java.awt.event.ActionEvent evt) {
        int row = jTableGlobChar.getSelectedRow();
        if (row >= 0) {
            String strTotCharge = jTextFieldTotalChar.getText();
            String strLineAmount = ((NoEdiTableModel) jTableGlobChar.getModel()).getValueAt(row, 2).toString();
            if (strTotCharge == null || "".equals(strTotCharge.trim())) {
                strTotCharge = "0.0";
            }

            Double totValue = null;
            Double lineAmount = null;
            try {
                totValue = Double.valueOf(strTotCharge);
                lineAmount = Double.valueOf(strLineAmount);
            } catch (NumberFormatException e) {
                logger.error("An number format exception occurred while a charge was being deleted: " + e.getMessage());
                ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
                return;
            }
            totValue = totValue - lineAmount;
            if (totValue < 0) {
                totValue = 0.0;
            }
            jTextFieldTotalChar.setText(String.valueOf(DoubleUtil.round(totValue, Integer.parseInt(decimalProps.getProperty("item_charge")))));
            ((NoEdiTableModel) jTableGlobChar.getModel()).removeRow(row);
            jTextFieldResults[2].setText(jTextFieldTotalChar.getText());
            recalculateTotals();
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("OKMessageChargeDeleted"), Constants.OK_MSG_COLOR);
        } else {
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
        }
    }

    /**
     * Action on mouse entered
     */
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    /**
     * Action on mouse exited
     */
    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }

    public void tibChanged() {
        Double tib = Double.parseDouble(jTextFieldResults[0].getText());
        String value = null;
        Double d = 0.0;

        // Discount table is updated
        TableModel modelDisc = jTableGlobDisc.getModel();
        Double totDis = 0.0;
        for (int i = 0; i < modelDisc.getRowCount(); ++i) {
            // Rate read
            value = modelDisc.getValueAt(i, 1).toString();
            // If rate exists, amount may be recalculated
            if (value != null && !"".equals(value.trim())) {
                d = Double.parseDouble(value);
                d = (d * tib) / 100;
                d = DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("item_discount")));
                totDis = totDis + d;
                modelDisc.setValueAt(d, i, 2);
            } else {
                d = Double.parseDouble(modelDisc.getValueAt(i, 2).toString());
                totDis = totDis + d;
            }
        }
        jTextFieldTotalDisc.setText(String.valueOf(DoubleUtil.round(totDis, Integer.parseInt(decimalProps.getProperty("item_discount")))));
        jTextFieldResults[1].setText(String.valueOf(DoubleUtil.round(totDis, Integer.parseInt(decimalProps.getProperty("item_discount")))));

        // Charge table is updated
        TableModel modelChar = jTableGlobChar.getModel();
        Double totChar = 0.0;
        for (int i = 0; i < modelChar.getRowCount(); ++i) {
            // Rate read
            value = modelChar.getValueAt(i, 1).toString();
            // If rate exists, amount may be recalculated
            if (value != null && !"".equals(value.trim())) {
                d = Double.parseDouble(value);
                d = (d * tib) / 100;
                d = DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("item_charge")));
                totChar = totChar + d;
                modelChar.setValueAt(d, i, 2);
            } else {
                d = Double.parseDouble(modelChar.getValueAt(i, 2).toString());
                totChar = totChar + d;
            }
        }
        jTextFieldTotalChar.setText(String.valueOf(DoubleUtil.round(totChar, Integer.parseInt(decimalProps.getProperty("item_charge")))));
        jTextFieldResults[2].setText(String.valueOf(DoubleUtil.round(totChar, Integer.parseInt(decimalProps.getProperty("item_charge")))));

    	// Withhelds are updated
        // Rate read
        value = jTextFieldWithPerc.getText();
        // If rate exists, amount may be recalculated
        if (value != null && !"".equals(value.trim())) {
            d = Double.parseDouble(value);
            d = (d * tib) / 100;
            jTextFieldWithAmou.setText(String.valueOf(DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("item_tax_With")))));
            jTextFieldResults[7].setText(String.valueOf(DoubleUtil.round(d, Integer.parseInt(decimalProps.getProperty("item_tax_With")))));
        }
    }

    public void recalculateTotals() {
        double grossAmount = 0.0;
        if (jTextFieldResults[0].getText() != null && !jTextFieldResults[0].getText().trim().equals("")) {
            grossAmount = Double.parseDouble(jTextFieldResults[0].getText());
        }
        double discountAmount = 0.0;
        if (jTextFieldTotalDisc.getText() != null && !jTextFieldTotalDisc.getText().trim().equals("")) {
            discountAmount = Double.parseDouble(jTextFieldTotalDisc.getText());
        }
        jTextFieldResults[1].setText(String.valueOf(discountAmount));
        double chargeAmount = 0.0;
        if (jTextFieldTotalChar.getText() != null && !jTextFieldTotalChar.getText().trim().equals("")) {
            chargeAmount = Double.parseDouble(jTextFieldTotalChar.getText());
        }
        jTextFieldResults[2].setText(String.valueOf(chargeAmount));
        // BI
        double biAmount = DoubleUtil.round(grossAmount - discountAmount + chargeAmount, Integer.parseInt(decimalProps.getProperty("item_taxable_base")));
        jTextFieldResults[3].setText(String.valueOf(biAmount));

        // Se obtiene porcentaje total de descuentos globales
        double discountsTotalRate = 0.0;
        for (int i = jTableGlobDisc.getModel().getRowCount() - 1; i >= 0; i--) {
            double discountRate = 0.0;
            try {
                discountRate = Double.parseDouble(jTableGlobDisc.getModel().getValueAt(i, 1).toString());
            } catch (Exception e) {
                ((DefaultTableModel) jTableGlobDisc.getModel()).removeRow(i);
                recalculateTotals();
                ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKGlobalDiscount"), Constants.ERROR_MSG_COLOR);
            }
            discountsTotalRate += discountRate;
        }
        // Se obtiene porcentaje total de cargos globales
        double chargesTotalRate = 0.0;
        for (int i = jTableGlobChar.getModel().getRowCount() - 1; i >= 0; i--) {
            double chargeRate = 0.0;
            try {
                chargeRate = Double.parseDouble(jTableGlobChar.getModel().getValueAt(i, 1).toString());
            } catch (Exception e) {
                ((DefaultTableModel) jTableGlobChar.getModel()).removeRow(i);
                recalculateTotals();
                ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKGlobalCharge"), Constants.ERROR_MSG_COLOR);
            }
            chargesTotalRate += chargeRate;
        }
        // Se recorren las líneas para obtener la nueva base imponible y los nuevos impuestos
        int itemsSize = items.getInvoiceLine().size();
        double gross2Amount = 0.0;
        double bi2Amount = 0.0;
        double outputsAmount = 0.0;
        double withheldAmount = 0.0;
        HashMap<String, Double> TaxesOutputsMap = new HashMap<String, Double>();
        HashMap<String, Double> TaxesWithheldMap = new HashMap<String, Double>();
        for (int i = 0; i < itemsSize; i++) {
            gross2Amount = items.getInvoiceLine().get(i).getGrossAmount();
            bi2Amount = gross2Amount - (discountsTotalRate / 100.00 * gross2Amount) + (chargesTotalRate / 100.00 * gross2Amount);
            // Se recorren los impuestos repercutidos
            int taxesOutputsSize = ((es.mityc.appfacturae.facturae32.InvoiceLineType) items.getInvoiceLine().get(i)).getTaxesOutputs().getTax().size();
            for (int j = 0; j < taxesOutputsSize; j++) {
                es.mityc.appfacturae.facturae32.InvoiceLineType.TaxesOutputs.Tax tax = ((es.mityc.appfacturae.facturae32.InvoiceLineType) items.getInvoiceLine().get(i)).getTaxesOutputs().getTax().get(j);
                String key = tax.getTaxTypeCode() + " - " + tax.getTaxRate() + " - 0.0";
                if (tax.getEquivalenceSurcharge() != null) {
                    key = tax.getTaxTypeCode() + " - " + tax.getTaxRate() + " - " + tax.getEquivalenceSurcharge();
                }
                Double value = new Double(0.0);
                value = bi2Amount;
                if (tax.getSpecialTaxableBase() != null && tax.getSpecialTaxableBase().getTotalAmount() != 0) {
                    value = tax.getSpecialTaxableBase().getTotalAmount();
                }
                if (TaxesOutputsMap.containsKey(key)) {
                    Double storeValue = (Double) TaxesOutputsMap.get(key);
                    TaxesOutputsMap.put(key, new Double(storeValue + value));
                } else {
                    TaxesOutputsMap.put(key, value);
                }
            }
            // Se recorren los impuestos retenidos
            int taxesWithheldSize = 0;
            if (items.getInvoiceLine().get(i).getTaxesWithheld() != null && items.getInvoiceLine().get(i).getTaxesWithheld().getTax() != null) {
                taxesWithheldSize = items.getInvoiceLine().get(i).getTaxesWithheld().getTax().size();
            }
            for (int j = 0; j < taxesWithheldSize; j++) {
                es.mityc.appfacturae.facturae.TaxType tax = items.getInvoiceLine().get(i).getTaxesWithheld().getTax().get(j);
                String key = tax.getTaxTypeCode() + " - " + tax.getTaxRate() + " - 0.0";
                Double value = new Double(bi2Amount);
                if (TaxesWithheldMap.containsKey(key)) {
                    Double storeValue = (Double) TaxesWithheldMap.get(key);
                    TaxesWithheldMap.put(key, new Double(storeValue + value));
                } else {
                    TaxesWithheldMap.put(key, value);
                }
            }
        }

        Iterator<Map.Entry<String, Double>> it = TaxesOutputsMap.entrySet().iterator();
        Double value = new Double(0.0);
        while (it.hasNext()) {
            Map.Entry<String, Double> e = (Map.Entry<String, Double>) it.next();
            value = Double.parseDouble(e.getValue().toString());

            value = DoubleUtil.round(value, Integer.parseInt(decimalProps.getProperty("item_taxable_base")));

            outputsAmount += ((Double.parseDouble(e.getKey().toString().split(" - ")[1].toString()) / 100 * value)
                    + (Double.parseDouble(e.getKey().toString().split(" - ")[2].toString()) / 100 * value));
        }

        it = TaxesWithheldMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Double> e = (Map.Entry<String, Double>) it.next();
            value = Double.parseDouble(e.getValue().toString());

            value = DoubleUtil.round(value, Integer.parseInt(decimalProps.getProperty("item_taxable_base")));

            withheldAmount += Double.parseDouble(e.getKey().toString().split(" - ")[1].toString()) / 100 * value;
        }

        outputsAmount = DoubleUtil.round(outputsAmount, Integer.parseInt(decimalProps.getProperty("item_tax_Out")));
        withheldAmount = DoubleUtil.round(withheldAmount, Integer.parseInt(decimalProps.getProperty("item_tax_With")));

        jTextFieldResults[4].setText(String.valueOf(outputsAmount));
        jTextFieldResults[5].setText(String.valueOf(withheldAmount));

        double totalAmount = biAmount + outputsAmount - withheldAmount;
        try {
            jTextFieldResults[6].setText(String.valueOf(DoubleUtil.round(totalAmount, Integer.parseInt(decimalProps.getProperty("item_total_cost")))));
        } catch (NumberFormatException nfe) {
            jTextFieldResults[6].setText("");
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
        }

        if (jTextFieldWithPerc.getText() != null && !"".equals(jTextFieldWithPerc.getText().trim())) {
            jTextFieldWithAmou.setText(String.valueOf(DoubleUtil.round((Double.parseDouble(jTextFieldResults[6].getText()) * Double.parseDouble(jTextFieldWithPerc.getText()) / 100.0), Integer.parseInt(decimalProps.getProperty("global_withheld")))));
        }
        if (jTextFieldWithAmou.getText() != null && !"".equals(jTextFieldWithAmou.getText().trim())) {
            jTextFieldResults[7].setText(jTextFieldWithAmou.getText());
        }

        double glbwithheldAmount = 0.0;
        if (jTextFieldResults[7].getText() != null && !jTextFieldResults[7].getText().trim().equals("")) {
            glbwithheldAmount = Double.parseDouble(jTextFieldResults[7].getText());
        }
        double reimbursableAmount = 0.0;
        if (jTextFieldResults[8].getText() != null && !jTextFieldResults[8].getText().trim().equals("")) {
            reimbursableAmount = Double.parseDouble(jTextFieldResults[8].getText());
        }
        double financialAmount = 0.0;
        if (jTextFieldResults[9].getText() != null && !jTextFieldResults[9].getText().trim().equals("")) {
            try {
                financialAmount = Double.parseDouble(jTextFieldResults[9].getText());
            } catch (NumberFormatException nfe) {
                financialAmount = 0.0;
                jTextFieldResults[9].setText("");
            }
        }
        double executableAmount = totalAmount - glbwithheldAmount + reimbursableAmount + financialAmount;
        try {
            jTextFieldResults[10].setText(String.valueOf(DoubleUtil.round(executableAmount, Integer.parseInt(decimalProps.getProperty("item_total_cost")))));
        } catch (NumberFormatException nfe) {
            jTextFieldResults[10].setText("");
            ((GenerateInvoice32Window) this.getTopLevelAncestor()).showMessage(Constants.LANG.getString("NOOKParamNumericFormat"), Constants.ERROR_MSG_COLOR);
        }
    }

    public boolean validateInstallment() {

        boolean requiredGeneralFilled = (jTextFieldPaymentAmo.getText() != null && !"".equals(jTextFieldPaymentAmo.getText().trim())) || (jComboBoxPayMean.getSelectedItem() != null && !"".equals(jComboBoxPayMean.getSelectedItem().toString().trim()));
        boolean generalFilled = requiredGeneralFilled || (jTextFieldPaymentRefIssuer.getText() != null && !"".equals(jTextFieldPaymentRefIssuer.getText().trim())) || (jTextFieldPaymentRefReceiver.getText() != null && !"".equals(jTextFieldPaymentRefReceiver.getText().trim()));

        boolean requiredDebitedFilled = (jTextFieldPayDIBAN.getText() != null && !"".equals(jTextFieldPayDIBAN.getText().trim()));
        boolean debitedAddressFilled = (jTextFieldPayDAddr.getText() != null && !"".equals(jTextFieldPayDAddr.getText().trim())) || (jTextFieldPayDCity.getText() != null && !"".equals(jTextFieldPayDCity.getText().trim())) || (jTextFieldPayDPCod.getText() != null && !"".equals(jTextFieldPayDPCod.getText().trim())) || (jTextFieldPayDProv.getText() != null && !"".equals(jTextFieldPayDProv.getText().trim())) || (jComboBoxPayDCoun.getSelectedItem() != null && !"".equals(jComboBoxPayDCoun.getSelectedItem().toString().trim()));
        boolean debitedFilled = requiredDebitedFilled || (jTextFieldPayDBank.getText() != null && !"".equals(jTextFieldPayDBank.getText().trim())) || (jTextFieldPayDBran.getText() != null && !"".equals(jTextFieldPayDBran.getText().trim())) || debitedAddressFilled;

        boolean requiredCreditedFilled = (jTextFieldPayCIBAN.getText() != null && !"".equals(jTextFieldPayCIBAN.getText().trim()));
        boolean creditedAddressFilled = (jTextFieldPayCAddr.getText() != null && !"".equals(jTextFieldPayCAddr.getText().trim())) || (jTextFieldPayCCity.getText() != null && !"".equals(jTextFieldPayCCity.getText().trim())) || (jTextFieldPayCPCod.getText() != null && !"".equals(jTextFieldPayCPCod.getText().trim())) || (jTextFieldPayCProv.getText() != null && !"".equals(jTextFieldPayCProv.getText().trim())) || (jComboBoxPayCCoun.getSelectedItem() != null && !"".equals(jComboBoxPayCCoun.getSelectedItem().toString().trim()));
        boolean creditedFilled = requiredCreditedFilled || (jTextFieldPayCBank.getText() != null && !"".equals(jTextFieldPayCBank.getText().trim())) || (jTextFieldPayCBran.getText() != null && !"".equals(jTextFieldPayCBran.getText().trim())) || creditedAddressFilled;

        boolean requiredFactoringFilled = jTextFieldPayFactIBAN.getText() != null && !"".equals(jTextFieldPayFactIBAN.getText().trim());
        boolean factoringAddressFilled = (jTextFieldPayFactAddr.getText() != null && !"".equals(jTextFieldPayFactAddr.getText().trim())) || (jTextFieldPayFactCity.getText() != null && !"".equals(jTextFieldPayFactCity.getText().trim())) || (jTextFieldPayFactPCod.getText() != null && !"".equals(jTextFieldPayFactPCod.getText().trim())) || (jTextFieldPayFactProv.getText() != null && !"".equals(jTextFieldPayFactProv.getText().trim()));
        boolean factoringFielled = requiredFactoringFilled || (jTextFieldPayFactClausula.getText() != null && !"".equals(jTextFieldPayFactClausula.getText().trim())) || (jTextFieldPayFactNifSociedad.getText() != null && !"".equals(jTextFieldPayFactNifSociedad.getText().trim())) || (jTextFieldPayFactSociedad.getText() != null && !"".equals(jTextFieldPayFactSociedad.getText().trim())) || factoringAddressFilled;

        if (!generalFilled && !debitedFilled && !creditedFilled && !factoringFielled) {
            // OK The Installment is empty --> That's correct because the installment is optional.
            return true;
        } else {
            if ((jTextFieldPaymentAmo.getText() == null || "".equals(jTextFieldPaymentAmo.getText().trim())) || (jComboBoxPayMean.getSelectedItem() == null || "".equals(jComboBoxPayMean.getSelectedItem().toString().trim()))) {
                // NO OK These are the Installment's required attributes
                if (jComboBoxPayMean.getSelectedItem() == null || "".equals(jComboBoxPayMean.getSelectedItem().toString().trim())) {
                    jComboBoxPayMean.setBackground(Constants.BKG_ERROR_COLOR);
                }
                if (jTextFieldPaymentAmo.getText() == null || "".equals(jTextFieldPaymentAmo.getText().trim())) {
                    jTextFieldPaymentAmo.setBackground(Constants.BKG_ERROR_COLOR);
                    jTextFieldPaymentAmo.requestFocus();
                }
                jTabbedPaneGlobal.setSelectedIndex(4);
                jTabbedPaneGlbPayment.setSelectedIndex(0);
                return false;
            } else {
                if (debitedFilled) {
                    if (!requiredDebitedFilled) {
                        // NO OK The account to be debited is filled but not its required attributes.
                        jTextFieldPayDIBAN.setBackground(Constants.BKG_ERROR_COLOR);
                        jTextFieldPayDIBAN.requestFocus();
                        jTabbedPaneGlobal.setSelectedIndex(4);
                        jTabbedPaneGlbPayment.setSelectedIndex(1);
                        return false;
                    } else {
                        if (debitedAddressFilled) {
                            if (!((jTextFieldPayDAddr.getText() != null && !"".equals(jTextFieldPayDAddr.getText().trim())) && (jTextFieldPayDCity.getText() != null && !"".equals(jTextFieldPayDCity.getText().trim())) && (((jTextFieldPayDPCod.getText() != null && !"".equals(jTextFieldPayDPCod.getText().trim()))) || (jComboBoxPayDCoun.getSelectedItem() != null && Constants.SPAINCODE31 != Integer.parseInt(((ComboOption) jComboBoxPayDCoun.getSelectedItem()).getValue()))) && (jTextFieldPayDProv.getText() != null && !"".equals(jTextFieldPayDProv.getText().trim())) && (jComboBoxPayDCoun.getSelectedItem() != null && !"".equals(jComboBoxPayDCoun.getSelectedItem().toString().trim())))) {
                                // NO OK The account to be debited address is filled but not all its attributes.
                                if (jTextFieldPayDAddr.getText() == null || "".equals(jTextFieldPayDAddr.getText().trim())) {
                                    jTextFieldPayDAddr.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayDAddr.requestFocus();
                                }
                                if (jTextFieldPayDCity.getText() == null || "".equals(jTextFieldPayDCity.getText().trim())) {
                                    jTextFieldPayDCity.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayDCity.requestFocus();
                                }
                                if (jComboBoxPayDCoun.getSelectedItem() != null && Constants.SPAINCODE31 == Integer.parseInt(((ComboOption) jComboBoxPayDCoun.getSelectedItem()).getValue()) && (jTextFieldPayDPCod.getText() == null || "".equals(jTextFieldPayDPCod.getText().trim()))) {
                                    jTextFieldPayDPCod.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayDPCod.requestFocus();
                                }
                                if (jTextFieldPayDProv.getText() == null || "".equals(jTextFieldPayDProv.getText().trim())) {
                                    jTextFieldPayDProv.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayDProv.requestFocus();
                                }
                                if (jComboBoxPayDCoun.getSelectedItem() == null || "".equals(jComboBoxPayDCoun.getSelectedItem().toString().trim())) {
                                    jComboBoxPayDCoun.setBackground(Constants.BKG_ERROR_COLOR);
                                }
                                jTabbedPaneGlobal.setSelectedIndex(4);
                                jTabbedPaneGlbPayment.setSelectedIndex(1);
                                return false;
                            }
                        }
                    }
                }
                if (creditedFilled) {
                    if (!requiredCreditedFilled) {
                        // NO OK The account to be credited is filled but not its required attributes.
                        jTextFieldPayCIBAN.setBackground(Constants.BKG_ERROR_COLOR);
                        jTextFieldPayCIBAN.requestFocus();
                        jTabbedPaneGlobal.setSelectedIndex(4);
                        jTabbedPaneGlbPayment.setSelectedIndex(2);
                        return false;
                    } else {
                        if (creditedAddressFilled) {
                            if (!((jTextFieldPayCAddr.getText() != null && !"".equals(jTextFieldPayCAddr.getText().trim())) && (jTextFieldPayCCity.getText() != null && !"".equals(jTextFieldPayCCity.getText().trim())) && (((jTextFieldPayCPCod.getText() != null && !"".equals(jTextFieldPayCPCod.getText().trim()))) || (jComboBoxPayCCoun.getSelectedItem() != null && Constants.SPAINCODE31 != Integer.parseInt(((ComboOption) jComboBoxPayCCoun.getSelectedItem()).getValue()))) && (jTextFieldPayCProv.getText() != null && !"".equals(jTextFieldPayCProv.getText().trim())) && (jComboBoxPayCCoun.getSelectedItem() != null && !"".equals(jComboBoxPayCCoun.getSelectedItem().toString().trim())))) {
                                // NO OK The account to be credited address is filled but not all its attributes.
                                if (jTextFieldPayCAddr.getText() == null || "".equals(jTextFieldPayCAddr.getText().trim())) {
                                    jTextFieldPayCAddr.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayCAddr.requestFocus();
                                }
                                if (jTextFieldPayCCity.getText() == null || "".equals(jTextFieldPayCCity.getText().trim())) {
                                    jTextFieldPayCCity.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayCCity.requestFocus();
                                }
                                if (jComboBoxPayCCoun.getSelectedItem() != null && Constants.SPAINCODE31 == Integer.valueOf(((ComboOption) jComboBoxPayCCoun.getSelectedItem()).getValue()) && (jTextFieldPayCPCod.getText() == null || "".equals(jTextFieldPayCPCod.getText().trim()))) {
                                    jTextFieldPayCPCod.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayCPCod.requestFocus();
                                }
                                if (jTextFieldPayCProv.getText() == null || "".equals(jTextFieldPayCProv.getText().trim())) {
                                    jTextFieldPayCProv.setBackground(Constants.BKG_ERROR_COLOR);
                                    jTextFieldPayCProv.requestFocus();
                                }
                                if (jComboBoxPayCCoun.getSelectedItem() == null || "".equals(jComboBoxPayCCoun.getSelectedItem().toString().trim())) {
                                    jComboBoxPayCCoun.setBackground(Constants.BKG_ERROR_COLOR);
                                }
                                jTabbedPaneGlobal.setSelectedIndex(4);
                                jTabbedPaneGlbPayment.setSelectedIndex(2);
                                return false;
                            }
                        }
                    }
                }
                if(factoringFielled) {
                	if(!requiredFactoringFilled) {
                		// NO OK The account to be factoring is filled but not its required attributes.
                		jTextFieldPayFactIBAN.setBackground(Constants.BKG_ERROR_COLOR);
                		jTextFieldPayFactIBAN.requestFocus();
                		jTabbedPaneGlobal.setSelectedIndex(4);
                		jTabbedPaneGlbPayment.setSelectedIndex(3);
                		return false;
                	} else {
                		if(jTextFieldPayFactAddr.getText() == null || "".equals(jTextFieldPayFactAddr.getText().trim()) || 
                				jTextFieldPayFactCity.getText() == null || "".equals(jTextFieldPayFactCity.getText().trim()) ||
                				jComboBoxPayFactCoun.getSelectedItem() != null && Constants.SPAINCODE31 == Integer.valueOf(((ComboOption) jComboBoxPayFactCoun.getSelectedItem()).getValue()) && (jTextFieldPayFactPCod.getText() == null || "".equals(jTextFieldPayFactPCod.getText().trim())) ||
                				jTextFieldPayFactProv.getText() == null || "".equals(jTextFieldPayFactProv.getText().trim()) ||
                				jComboBoxPayFactCoun.getSelectedItem() == null || "".equals(jComboBoxPayFactCoun.getSelectedItem().toString().trim()) ||
                				jTextFieldPayFactNifSociedad.getText() == null || "".equals(jTextFieldPayFactNifSociedad.getText().trim()) ||
                				jTextFieldPayFactClausula.getText() == null || "".equals(jTextFieldPayFactClausula.getText().trim()) ||
                				jTextFieldPayFactSociedad.getText() == null || "".equals(jTextFieldPayFactSociedad.getText().trim())) {
	                        // NO OK The account to be credited address is filled but not all its attributes.
	                        if (jTextFieldPayFactAddr.getText() == null || "".equals(jTextFieldPayFactAddr.getText().trim())) {
	                        	jTextFieldPayFactAddr.setBackground(Constants.BKG_ERROR_COLOR);
	                        	jTextFieldPayFactAddr.requestFocus();
	                        }
	                        if (jTextFieldPayFactCity.getText() == null || "".equals(jTextFieldPayFactCity.getText().trim())) {
	                        	jTextFieldPayFactCity.setBackground(Constants.BKG_ERROR_COLOR);
	                        	jTextFieldPayFactCity.requestFocus();
	                        }
	                        if (jComboBoxPayFactCoun.getSelectedItem() != null && Constants.SPAINCODE31 == Integer.valueOf(((ComboOption) jComboBoxPayFactCoun.getSelectedItem()).getValue()) && (jTextFieldPayFactPCod.getText() == null || "".equals(jTextFieldPayFactPCod.getText().trim()))) {
	                        	jTextFieldPayFactPCod.setBackground(Constants.BKG_ERROR_COLOR);
	                        	jTextFieldPayFactPCod.requestFocus();
	                        }
	                        if (jTextFieldPayFactProv.getText() == null || "".equals(jTextFieldPayFactProv.getText().trim())) {
	                        	jTextFieldPayFactProv.setBackground(Constants.BKG_ERROR_COLOR);
	                        	jTextFieldPayFactProv.requestFocus();
	                        }
	                        if (jComboBoxPayFactCoun.getSelectedItem() == null || "".equals(jComboBoxPayFactCoun.getSelectedItem().toString().trim())) {
	                        	jComboBoxPayFactCoun.setBackground(Constants.BKG_ERROR_COLOR);
	                        }
	                        if (jTextFieldPayFactNifSociedad.getText() == null || "".equals(jTextFieldPayFactNifSociedad.getText().trim())) {
	                        	jTextFieldPayFactNifSociedad.setBackground(Constants.BKG_ERROR_COLOR);
	                        	jTextFieldPayFactNifSociedad.requestFocus();
	                        }
	                        if (jTextFieldPayFactClausula.getText() == null || "".equals(jTextFieldPayFactClausula.getText().trim())) {
	                        	jTextFieldPayFactClausula.setBackground(Constants.BKG_ERROR_COLOR);
	                        	jTextFieldPayFactClausula.requestFocus();
	                        }
	                        if (jTextFieldPayFactSociedad.getText() == null || "".equals(jTextFieldPayFactSociedad.getText().trim())) {
	                        	jTextFieldPayFactSociedad.setBackground(Constants.BKG_ERROR_COLOR);
	                        	jTextFieldPayFactSociedad.requestFocus();
	                        }
	                        jTabbedPaneGlobal.setSelectedIndex(4);
	                        jTabbedPaneGlbPayment.setSelectedIndex(3);
	                        return false;
                		}
                	}
                }
            }
        }
        return true;
    }

    public ItemsType getItems() {
        return items;
    }

    public void setItems(ItemsType items) {
        this.items = items;
    }

    public JTextField getJTextFieldTotalDisc() {
        return jTextFieldTotalDisc;
    }

    public void setJTextFieldTotalDisc(JTextField textFieldTotalDisc) {
        jTextFieldTotalDisc = textFieldTotalDisc;
    }

    public JTextField getJTextFieldTotalReim() {
        return jTextFieldTotalReim;
    }

    public void setJTextFieldTotalReim(JTextField textFieldTotalReim) {
        jTextFieldTotalReim = textFieldTotalReim;
    }

    public JTextField getJTextFieldTotalChar() {
        return jTextFieldTotalChar;
    }

    public void setJTextFieldTotalChar(JTextField textFieldTotalChar) {
        jTextFieldTotalChar = textFieldTotalChar;
    }

    public JTextField getJTextFieldPaymentRefIssuer() {
        return jTextFieldPaymentRefIssuer;
    }

    public void setJTextFieldPaymentRefIssuer(JTextField textFieldPaymentRefIssuer) {
        jTextFieldPaymentRefIssuer = textFieldPaymentRefIssuer;
    }

    public JTextField getJTextFieldPaymentRefReceiver() {
        return jTextFieldPaymentRefReceiver;
    }

    public void setJTextFieldPaymentRefReceiver(JTextField textFieldPaymentRefReceiver) {
        jTextFieldPaymentRefReceiver = textFieldPaymentRefReceiver;
    }

    public JTextField getJTextFieldPaymentAmo() {
        return jTextFieldPaymentAmo;
    }

    public void setJTextFieldPaymentAmo(JTextField textFieldPaymentAmo) {
        jTextFieldPaymentAmo = textFieldPaymentAmo;
    }

    public JTextField getJTextFieldPayDIBAN() {
        return jTextFieldPayDIBAN;
    }

    public void setJTextFieldPayDIBAN(JTextField textFieldPayDIBAN) {
        jTextFieldPayDIBAN = textFieldPayDIBAN;
    }

    public JTextField getJTextFieldPayDBank() {
        return jTextFieldPayDBank;
    }

    public void setJTextFieldPayDBank(JTextField textFieldPayDBank) {
        jTextFieldPayDBank = textFieldPayDBank;
    }

    public JTextField getJTextFieldPayDBran() {
        return jTextFieldPayDBran;
    }

    public void setJTextFieldPayDBran(JTextField textFieldPayDBran) {
        jTextFieldPayDBran = textFieldPayDBran;
    }

    public JTextField getJTextFieldPayDAddr() {
        return jTextFieldPayDAddr;
    }

    public void setJTextFieldPayDAddr(JTextField textFieldPayDAddr) {
        jTextFieldPayDAddr = textFieldPayDAddr;
    }

    public JTextField getJTextFieldPayDCity() {
        return jTextFieldPayDCity;
    }

    public void setJTextFieldPayDCity(JTextField textFieldPayDCity) {
        jTextFieldPayDCity = textFieldPayDCity;
    }

    public JTextField getJTextFieldPayDPCod() {
        return jTextFieldPayDPCod;
    }

    public void setJTextFieldPayDPCod(JTextField textFieldPayDPCod) {
        jTextFieldPayDPCod = textFieldPayDPCod;
    }

    public JTextField getJTextFieldPayDProv() {
        return jTextFieldPayDProv;
    }

    public void setJTextFieldPayDProv(JTextField textFieldPayDProv) {
        jTextFieldPayDProv = textFieldPayDProv;
    }

    public JTextField getJTextFieldPayCIBAN() {
        return jTextFieldPayCIBAN;
    }

    public void setJTextFieldPayCIBAN(JTextField textFieldPayCIBAN) {
        jTextFieldPayCIBAN = textFieldPayCIBAN;
    }

    public JTextField getJTextFieldPayCBank() {
        return jTextFieldPayCBank;
    }

    public void setJTextFieldPayCBank(JTextField textFieldPayCBank) {
        jTextFieldPayCBank = textFieldPayCBank;
    }

    public JTextField getJTextFieldPayCBran() {
        return jTextFieldPayCBran;
    }

    public void setJTextFieldPayCBran(JTextField textFieldPayCBran) {
        jTextFieldPayCBran = textFieldPayCBran;
    }

    public JTextField getJTextFieldPayCAddr() {
        return jTextFieldPayCAddr;
    }

    public void setJTextFieldPayCAddr(JTextField textFieldPayCAddr) {
        jTextFieldPayCAddr = textFieldPayCAddr;
    }

    public JTextField getJTextFieldPayCCity() {
        return jTextFieldPayCCity;
    }

    public void setJTextFieldPayCCity(JTextField textFieldPayCCity) {
        jTextFieldPayCCity = textFieldPayCCity;
    }

    public JTextField getJTextFieldPayCPCod() {
        return jTextFieldPayCPCod;
    }

    public void setJTextFieldPayCPCod(JTextField textFieldPayCPCod) {
        jTextFieldPayCPCod = textFieldPayCPCod;
    }

    public JTextField getJTextFieldPayCProv() {
        return jTextFieldPayCProv;
    }

    public JComboBox getJComboBoxPayMean() {
        return jComboBoxPayMean;
    }

    public JComboBox getJComboBoxPayCCoun() {
        return jComboBoxPayCCoun;
    }

    public JComboBox getJComboBoxPayDCoun() {
        return jComboBoxPayDCoun;
    }

    public de.wannawork.jcalendar.JCalendarComboBox getJCalendarComboBoxPaymDate() {
        return jCalendarComboBoxPaymDate;
    }

    public JTextField getJTextFieldWithItem() {
        return jTextFieldWithItem;
    }

    public JTextField getJTextFieldWithPerc() {
        return jTextFieldWithPerc;
    }

    public JTextField getJTextFieldWithAmou() {
        return jTextFieldWithAmou;
    }

    public JTextField[] getJTextFieldResults() {
        return jTextFieldResults;
    }

    public JTable getJTableGlobDisc() {
        return jTableGlobDisc;
    }

    public void setJTableGlobDisc(JTable tableGlobDisc) {
        jTableGlobDisc = tableGlobDisc;
    }

    public JTable getJTableGlobReim() {
        return jTableGlobReim;
    }

    public void setJTableGlobReim(JTable tableGlobReim) {
        jTableGlobReim = tableGlobReim;
    }

    public JTable getJTableGlobChar() {
        return jTableGlobChar;
    }

    public void setJTableGlobChar(JTable tableGlobChar) {
        jTableGlobChar = tableGlobChar;
    }

    public JTable getJTableGlobAttc() {
        return jTableGlobAttc;
    }

    public void setJTableGlobAttc(JTable tableGlobAttc) {
        jTableGlobAttc = tableGlobAttc;
    }

    public JTable getJTableItems() {
        return jTableItems;
    }

    public JList getJListLiterals() {
        return jListLiterals;
    }

    public void setJListLiterals(JList listLiterals) {
        jListLiterals = listLiterals;
    }
    
    public JTextArea getJTextAreaLegal() {
    	return jTextAreaLegal;
    }
    
    public void setJTextAreaLegal(JTextArea jTextAreaLegal) {
    	this.jTextAreaLegal = jTextAreaLegal;
    }
	
	public JList getjListLiteralsDest() {
		return jListLiteralsDest;
	}
	
	public void setjListLiteralsDest(JList jListLiteralsDest) {
		this.jListLiteralsDest = jListLiteralsDest;
	}

    public JTextArea getJTextAreaTOTXMLExtAddData() {
        return jTextAreaTOTXMLExtAddData;
    }

    public void setJTextAreaTOTXMLExtAddData(JTextArea textAreaTOTXMLExtAddData) {
        jTextAreaTOTXMLExtAddData = textAreaTOTXMLExtAddData;
    }

    public JTextArea getJTextAreaXMLExtAddData() {
        return jTextAreaXMLExtAddData;
    }

    public void setJTextAreaXMLExtAddData(JTextArea textAreaXMLExtAddData) {
        jTextAreaXMLExtAddData = textAreaXMLExtAddData;
    }

    public JButton getJButtonItemAdd() {
        return jButtonItemAdd;
    }

    public JButton getJButtonItemDel() {
        return jButtonItemDel;
    }

    public JButton getJButtonDiscAdd() {
        return jButtonDiscAdd;
    }

    public JButton getJButtonDiscDel() {
        return jButtonDiscDel;
    }

    public JButton getJButtonReimAdd() {
        return jButtonReimAdd;
    }

    public JButton getJButtonReimDel() {
        return jButtonReimDel;
    }

    public JButton getJButtonCharAdd() {
        return jButtonCharAdd;
    }

    public JButton getJButtonCharDel() {
        return jButtonCharDel;
    }

    public JButton getJButtonAttcAdd() {
        return jButtonAttcAdd;
    }

    public JButton getJButtonAttcDel() {
        return jButtonAttcDel;
    }

    public JButton getJButtonXMLAddData() {
        return jButtonXMLAddData;
    }

    public JButton getJButtonXMLAddDataZoom() {
        return jButtonXMLAddDataZoom;
    }

    public JButton getJButtonTOTXMLAddData() {
        return jButtonTOTXMLAddData;
    }

    public JButton getJButtonTOTXMLAddDataZoom() {
        return jButtonTOTXMLAddDataZoom;
    }

    public JComboBox getJComboBoxPayment() {
        return jComboBoxPayment;
    }

    public JComboBox getJComboBoxPayAccountC() {
        return jComboBoxPayAccountC;
    }

    public JComboBox getJComboBoxPayAccountD() {
        return jComboBoxPayAccountD;
    }

    // flag for set disabled items dialog
    public void setItemsForVisualization(boolean b) {
        setVisualizationOnly = b;
    }

    public JTextField getJTextFieldPayFactIBAN() {
        return jTextFieldPayFactIBAN;
    }

    public void setJTextFieldPayFactIBAN(JTextField jTextFieldPayFactIBAN) {
        this.jTextFieldPayFactIBAN = jTextFieldPayFactIBAN;
    }

    public JTextField getJTextFieldPayFactSociedad() {
        return jTextFieldPayFactSociedad;
    }

    public void setJTextFieldPayFactSociedad(JTextField jTextFieldPayFactSociedad) {
        this.jTextFieldPayFactSociedad = jTextFieldPayFactSociedad;
    }

    public JTextField getJTextFieldPayFactNifSociedad() {
        return jTextFieldPayFactNifSociedad;
    }

    public void setJTextFieldPayFactNifSociedad(
            JTextField jTextFieldPayFactNifSociedad) {
        this.jTextFieldPayFactNifSociedad = jTextFieldPayFactNifSociedad;
    }

    public JTextField getJTextFieldPayFactAddr() {
        return jTextFieldPayFactAddr;
    }

    public void setJTextFieldPayFactAddr(JTextField jTextFieldPayFactAddr) {
        this.jTextFieldPayFactAddr = jTextFieldPayFactAddr;
    }

    public JTextField getJTextFieldPayFactCity() {
        return jTextFieldPayFactCity;
    }

    public void setJTextFieldPayFactCity(JTextField jTextFieldPayFactCity) {
        this.jTextFieldPayFactCity = jTextFieldPayFactCity;
    }

    public JTextField getJTextFieldPayFactPCod() {
        return jTextFieldPayFactPCod;
    }

    public void setJTextFieldPayFactPCod(JTextField jTextFieldPayFactPCod) {
        this.jTextFieldPayFactPCod = jTextFieldPayFactPCod;
    }

    public JTextField getJTextFieldPayFactClausula() {
        return jTextFieldPayFactClausula;
    }

    public void setJTextFieldPayFactClausula(JTextField jTextFieldPayFactClausula) {
        this.jTextFieldPayFactClausula = jTextFieldPayFactClausula;
    }

    public JComboBox getJComboBoxPayFactCoun() {
        return jComboBoxPayFactCoun;
    }

    public void setJComboBoxPayFactCoun(JComboBox jComboBoxPayFactCoun) {
        this.jComboBoxPayFactCoun = jComboBoxPayFactCoun;
    }

    public void setJTextFieldPayFactProv(JTextField jTextFieldPayFactProv) {
        this.jTextFieldPayFactProv = jTextFieldPayFactProv;
    }

    public JTextField getJTextFieldPayFactProv() {
        return jTextFieldPayFactProv;
    }

    public JComboBox getJComboBoxPayAccountFactoring() {
        return jComboBoxPayAccountFactoring;
    }
	
	public JButton getjButtonLitItemAdd() {
		return jButtonLitItemAdd;
	}

	public void setjButtonLitItemAdd(JButton jButtonLitItemAdd) {
		this.jButtonLitItemAdd = jButtonLitItemAdd;
	}

	public JButton getjButtonLitItemRemove() {
		return jButtonLitItemRemove;
	}

	public void setjButtonLitItemRemove(JButton jButtonLitItemRemove) {
		this.jButtonLitItemRemove = jButtonLitItemRemove;
	}	

    public void paintAndFocusAttcArea(boolean b, java.awt.Color c) {
        jScrollPaneGlobAttc.getViewport().setBackground(c);
        if (b) {
            int count = jTabbedPaneGlobal.getTabCount();
            String nodeName = Constants.LANG.getString("Attachments");
            for (int i = 0; i < count; i++) {
                if (nodeName.equals(jTabbedPaneGlobal.getTitleAt(i))) {
                    jTabbedPaneGlobal.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public DefaultListModel getLiteralsListDestModel() {
		return literalsListDestModel;
	}

	public void setLiteralsListDestModel(DefaultListModel literalsListDestModel) {
		this.literalsListDestModel = literalsListDestModel;
	}

	// JButtons
    private JButton jButtonItemAdd;
    private JButton jButtonItemDel;
    private JButton jButtonDiscAdd;
    private JButton jButtonDiscDel;
    private JButton jButtonReimAdd;
    private JButton jButtonReimDel;
    private JButton jButtonCharAdd;
    private JButton jButtonCharDel;
    private JButton jButtonAttcAdd;
    private JButton jButtonAttcDel;
    private JButton jButtonXMLAddData;
    private JButton jButtonXMLAddDataZoom;
    private JButton jButtonTOTXMLAddData;
    private JButton jButtonTOTXMLAddDataZoom;
	private JButton jButtonLitItemAdd;
	private JButton jButtonLitItemRemove;
    // JComboBox
    private JComboBox jComboBoxPayMean;
    private JComboBox jComboBoxPayCCoun;
    private JComboBox jComboBoxPayFactCoun;
    private JComboBox jComboBoxPayDCoun;
    private JComboBox jComboBoxPayment;
    private JComboBox jComboBoxPayAccountC;
    private JComboBox jComboBoxPayAccountFactoring;
    private JComboBox jComboBoxPayAccountD;
    private JComboBox jComboBoxLegalLiterals;
	private JScrollPane jScrollPaneLitEdit;
	private JScrollPane jScrollPaneLitDest;
	private DefaultListModel literalsListDestModel;    
    // JCalendarComboBox
    private de.wannawork.jcalendar.JCalendarComboBox jCalendarComboBoxPaymDate;
    // JLabels
    private JLabel jLabelTotalDisc;
    private JLabel jLabelTotalReim;
    private JLabel jLabelTotalChar;
    private JLabel jLabelTotalPayDate;
    private JLabel jLabelTotalPayRefIssuer;
    private JLabel jLabelTotalPayRefReceiver;
    private JLabel jLabelTotalPayMean;
    private JLabel jLabelTotalPayAmou;
    private JLabel jLabelTotalPayDIBAN;
    private JLabel jLabelTotalPayDBank;
    private JLabel jLabelTotalPayDBran;
    private JLabel jLabelTotalPayDAddr;
    private JLabel jLabelTotalPayDCity;
    private JLabel jLabelTotalPayDPCod;
    private JLabel jLabelTotalPayDProv;
    private JLabel jLabelTotalPayDCoun;
    private JLabel jLabelTotalPayCIBAN;
    private JLabel jLabelTotalPayCBank;
    private JLabel jLabelTotalPayCBran;
    private JLabel jLabelTotalPayCAddr;
    private JLabel jLabelTotalPayCCity;
    private JLabel jLabelTotalPayCPCod;
    private JLabel jLabelTotalPayCProv;
    private JLabel jLabelTotalPayCCoun;

    private JLabel jLabelTotalPayFactIBAN;
    private JLabel jLabelTotalPayFactSociedad;
    private JLabel jLabelTotalPayFactNifSociedad;
    private JLabel jLabelTotalPayFactAddr;
    private JLabel jLabelTotalPayFactCity;
    private JLabel jLabelTotalPayFactPCod;
    private JLabel jLabelTotalPayFactClausula;
    private JLabel jLabelTotalPayFactCoun;
    private JLabel jLabelTotalPayFactProv;
    private JLabel jLabelTotalWithItem;
    private JLabel jLabelTotalWithPerc;
    private JLabel jLabelTotalWithAmou;
    private JLabel jLabelTOTALS;
    private JLabel jLabelTOTMessage;
    private JLabel jLabelTOTAsterisk;
    private JLabel jLabelTOTGrossAmount;
    private JLabel jLabelTOTMinus1;
    private JLabel jLabelTOTDiscounts;
    private JLabel jLabelTOTPlus1;
    private JLabel jLabelTOTCharges;
    private JLabel jLabelTOTEqual1;
    private JLabel jLabelTOTTaxBase;
    private JLabel jLabelTOTTaxBase2;
    private JLabel jLabelTOTPlus2;
    private JLabel jLabelTOTTaxOut;
    private JLabel jLabelTOTMinus2;
    private JLabel jLabelTOTTaxWith;
    private JLabel jLabelTOTEqual2;
    private JLabel jLabelTOTInvTotal;
    private JLabel jLabelTOTInvTotal2;
    private JLabel jLabelTOTMinus3;
    private JLabel jLabelTOTGlobWith;
    private JLabel jLabelTOTPlus3;
    private JLabel jLabelTOTReimb;
    private JLabel jLabelTOTPlus4;
    private JLabel jLabelTOTFinancial;
    private JLabel jLabelTOTEqual3;
    private JLabel jLabelTOTInvExec;
    private JLabel jLabelPayment;
    private JLabel[] jLabelCurrency;
    private JLabel jLabelTCurrency;
    private JLabel jLabelXMLAddData;
    private JLabel jLabelTOTXMLAddData;
    // JList
    private JList jListLiterals;
	private JList jListLiteralsDest;
    // JPanel
    private JTabbedPane jTabbedPaneGlobal;
    private JTabbedPane jTabbedPaneGlbPayment;
    private JPanel jPanelGlbDiscounts;
    private JPanel jPanelGlbReimbExp;
    private JPanel jPanelGlbCharges;
    private JPanel jPanelGlbAttchDoc;
    private JPanel jPanelGlbPayment;
    private JPanel jPanelGlbPaymentGen2;
    private JPanel jPanelGlbPaymentAccDebited;
    private JPanel jPanelGlbPaymentAccCharged;
    private JPanel jPanelGlbPaymentAccFactoring;
    private JPanel jPanelGlbWhithhelds;
    private JPanel jPanelGlbLiterals;
    // JScrollPane
    private JScrollPane jScrollPaneGlobDisc;
    private JScrollPane jScrollPaneGlobReim;
    private JScrollPane jScrollPaneGlobChar;
    private JScrollPane jScrollPaneGlobAttc;
    private JScrollPane jScrollPaneItems;
    private JScrollPane jScrollPaneLitList;
    private JScrollPane jScrollPaneXMLAddData;
    private JScrollPane jScrollPaneTOTXMLAddData;
    // JTables
    private JTable jTableGlobDisc;
    private JTable jTableGlobReim;
    private JTable jTableGlobChar;
    private JTable jTableGlobAttc;
    private JTable jTableItems;
    // JTextFields
    private JTextField jTextFieldTotalDisc;
    private JTextField jTextFieldTotalReim;
    private JTextField jTextFieldTotalChar;
    private JTextField jTextFieldPaymentRefIssuer;
    private JTextField jTextFieldPaymentRefReceiver;
    private JTextField jTextFieldPaymentAmo;
    private JTextField jTextFieldPayDIBAN;
    private JTextField jTextFieldPayDBank;
    private JTextField jTextFieldPayDBran;
    private JTextField jTextFieldPayDAddr;
    private JTextField jTextFieldPayDCity;
    private JTextField jTextFieldPayDPCod;
    private JTextField jTextFieldPayDProv;
    private JTextField jTextFieldPayCIBAN;
    private JTextField jTextFieldPayCBank;
    private JTextField jTextFieldPayCBran;
    private JTextField jTextFieldPayCAddr;
    private JTextField jTextFieldPayCCity;
    private JTextField jTextFieldPayCPCod;
    private JTextField jTextFieldPayCProv;
    private JTextField jTextFieldPayFactIBAN;
    private JTextField jTextFieldPayFactSociedad;
    private JTextField jTextFieldPayFactNifSociedad;
    private JTextField jTextFieldPayFactAddr;
    private JTextField jTextFieldPayFactCity;
    private JTextField jTextFieldPayFactPCod;
    private JTextField jTextFieldPayFactClausula;
    private JTextField jTextFieldPayFactProv;
    private JTextField jTextFieldWithItem;
    private JTextField jTextFieldWithPerc;
    private JTextField jTextFieldWithAmou;
    private JTextField jTextFieldResults[];

    private JTextArea jTextAreaPayExist = null;
    private JTextArea jTextAreaAccExist = null;
    private JTextArea jTextAreaAccExist2 = null;
    private JTextArea jTextAreaAccExistFactoring = null;
	private JTextArea jTextAreaWarnFactoring = null;

    private JTextArea jTextAreaXMLExtAddData = null;
    private JLabel jLabelLegalLiteral = null;
    private JTextArea jTextAreaLegal = null;

    private String error = null;

    private JTextArea jTextAreaTOTXMLExtAddData = null;
    private String error2 = null;

    private ItemsType items;

    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparatorFactoring;

    private DefaultComboBoxModel<String> payments;
    private DefaultComboBoxModel<String> accounts;
    private DefaultComboBoxModel<String> accounts2;
    private DefaultComboBoxModel<String> accounts3;
}
