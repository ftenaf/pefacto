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
package es.mityc.appfacturae.ui.dialogs;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.wannawork.jcalendar.JCalendarComboBox;
import es.mityc.appfacturae.facturae.AmountType;
import es.mityc.appfacturae.facturae.ChargeType;
import es.mityc.appfacturae.facturae.ChargesType;
import es.mityc.appfacturae.facturae.DeliveryNotesReferencesType;
import es.mityc.appfacturae.facturae.DiscountType;
import es.mityc.appfacturae.facturae.DiscountsAndRebatesType;
import es.mityc.appfacturae.facturae.ExtensionsType;
import es.mityc.appfacturae.facturae.TaxType;
import es.mityc.appfacturae.facturae.TaxesType;
import es.mityc.appfacturae.facturae321.DeliveryNoteType;
import es.mityc.appfacturae.facturae321.InvoiceLineType;
import es.mityc.appfacturae.facturae321.SpecialTaxableEventType;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.dialogs.panels.Invoice32DetailOtherDataPanel;
import es.mityc.appfacturae.ui.dialogs.panels.Invoice321DetailTaxesPanel;
import es.mityc.appfacturae.ui.dialogs.panels.InvoiceDetailGeneralPanel;
import es.mityc.appfacturae.ui.dialogs.panels.InvoiceDetailOtherDataPanel;
import es.mityc.appfacturae.ui.dialogs.panels.InvoiceDetailTaxesPanel;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.DoubleUtil;
import es.mityc.appfacturae.utils.io.IntegerUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.utils.ValidationException;

public class Invoice321DetailDialog extends javax.swing.JDialog implements InvoiceDetailDialog {

	private static final long serialVersionUID = 8291342967130897932L;
	private FadingCanvas fd = null;
	private Properties decimalProps = null;
	private static final String codeVersion = Constants.FACTURAE321;
	private static Log logger = LogFactory.getLog(Invoice321DetailDialog.class);
	public Boolean specialBaseRequired;

	/** Creates new form InvoiceDetailFrame */
	public Invoice321DetailDialog(Frame parent, boolean modal, Boolean specialBaseRequired, boolean setDisabled) {
		super(parent, modal);
		this.specialBaseRequired = specialBaseRequired;

		loadDecimalConfiguration();
		initComponents();
		setSize(870, 615);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				jButtonCancelActionPerformed();
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
		if (setDisabled)
			setFieldsDisabled();
	}

	private void initComponents() {

		mainPanel = new javax.swing.JPanel();

		fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		fd.setForeground(Constants.FONT_COLOR);

		jPanelOtherData = new Invoice32DetailOtherDataPanel();
		jPanelGeneral = new InvoiceDetailGeneralPanel(Constants.VERSION321);
		jPanelTaxes = new Invoice321DetailTaxesPanel(specialBaseRequired);

		jButtonAccept = new javax.swing.JButton();
		jButtonCancel = new javax.swing.JButton();
		jPanelTopBar = new javax.swing.JPanel();
		jLabelTopBarImage = new javax.swing.JLabel();
		jLabelTopBarMsg2 = new javax.swing.JLabel();
		jLabelTopBarMsg1 = new javax.swing.JLabel();
		jButtonHelp = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(Constants.LANG.getString("InvoiceDetailData"));

		mainPanel.setBackground(Constants.BKG_MAIN_COLOR);

		jButtonAccept.setFont(Constants.TITLE_FONT);
		jButtonAccept.setForeground(Constants.FONT_COLOR);
		jButtonAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_accept.jpg")));
		jButtonAccept.setToolTipText(Constants.LANG.getString("Accept"));
		jButtonAccept.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
		jButtonAccept.setContentAreaFilled(false);
		jButtonAccept.setBorderPainted(false);
		jButtonAccept.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boolean flag = false;

				jPanelGeneral.getJButtonXMLAddData().doClick();
				if (jPanelGeneral.getJButtonXMLAddDataZoom().getIcon().toString()
					.contains("button_mini_zoom_error.jpg")) {
					jPanelGeneral.getJButtonXMLAddDataZoom().requestFocus();
					return;
				}

				if (jPanelGeneral.getJTextFieldGUnitPrice().getText() == null
					|| "".equals(jPanelGeneral.getJTextFieldGUnitPrice().getText().trim())) {
					flag = true;
					jPanelGeneral.getJTextFieldGUnitPrice().requestFocus();
					jPanelGeneral.getJTextFieldGUnitPrice().setBackground(Constants.BKG_ERROR_COLOR);
				}

				if (jPanelGeneral.getJTextFieldGQuantity().getText() == null
					|| "".equals(jPanelGeneral.getJTextFieldGQuantity().getText().trim())) {
					flag = true;
					jPanelGeneral.getJTextFieldGQuantity().requestFocus();
					jPanelGeneral.getJTextFieldGQuantity().setBackground(Constants.BKG_ERROR_COLOR);
				}

				if (jPanelGeneral.getJComboBoxGDesc().getSelectedItem() == null
					|| "".equals(jPanelGeneral.getJComboBoxGDesc().getSelectedItem().toString().trim())) {
					flag = true;
					jPanelGeneral.getJComboBoxGDesc().requestFocus();
					jPanelGeneral.getJComboBoxGDesc().getEditor().getEditorComponent()
						.setBackground(Constants.BKG_ERROR_COLOR);
				}

				if (Constants.ERROR_MSG_COLOR.equals(jPanelOtherData.getJTextFieldOOrdRef().getBackground())) {
					jPanelOtherData.getJTextFieldOOrdRef().requestFocus();
					return;
				}

				if (jPanelTaxes.getJComboBoxSpecial().getSelectedIndex() > 0
					&& (jPanelTaxes.getJTextFieldSpecial() == null
						|| jPanelTaxes.getJTextFieldSpecial().getText() == null || "".equals(jPanelTaxes
						.getJTextFieldSpecial().getText().trim()))) {
					flag = true;
					jPanelTaxes.getJTextFieldSpecial().requestFocus();
					jPanelTaxes.getJTextFieldSpecial().setBackground(Constants.BKG_ERROR_COLOR);
				}

				if (flag)
					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
				else {
					if (jPanelTaxes.getJTableTOutputs().getRowCount() == 0) {
						if (jPanelTaxes.getJComboBoxSpecial().getSelectedIndex() > 0)
							((javax.swing.table.DefaultTableModel) jPanelTaxes.getJTableTOutputs().getModel())
								.addRow(new String[] { Constants.LANG.getString("VATTax") + " - 01", "0.00", "0.00",
										"0.00", "", "", "", "" });
						else {
							fd.showMessage(Constants.LANG.getString("NOOKParamTaxOutputRequired"),
								Constants.ERROR_MSG_COLOR);
							return;
						}
					}
					String value1 = "";
					if (jPanelTaxes.getJTableTOutputs().getModel().getValueAt(0, 4) != null)
						value1 = jPanelTaxes.getJTableTOutputs().getModel().getValueAt(0, 4).toString();
					int outputsRows = jPanelTaxes.getJTableTOutputs().getRowCount();
					for (int i = 1; i < outputsRows; i++) {
						String value2 = "";
						if (jPanelTaxes.getJTableTOutputs().getModel().getValueAt(i, 4) != null)
							value2 = jPanelTaxes.getJTableTOutputs().getModel().getValueAt(i, 4).toString();
						if (!value1.equals(value2)) {
							fd.showMessage(Constants.LANG.getString("NOOKDifferentSpecialTaxableBases"),
								Constants.ERROR_MSG_COLOR);
							return;
						} else {
							value1 = value2;
						}
					}
					setVisible(false);
					dispose();
				}
			}
		});
		jButtonAccept.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				Invoice321DetailDialog.this.mouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				Invoice321DetailDialog.this.mouseExited(evt);
			}
		});

		jButtonCancel.setFont(Constants.TITLE_FONT);
		jButtonCancel.setForeground(Constants.FONT_COLOR);
		jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_cancel.jpg")));
		jButtonCancel.setToolTipText(Constants.LANG.getString("Cancel"));
		jButtonCancel.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
		jButtonCancel.setContentAreaFilled(false);
		jButtonCancel.setBorderPainted(false);
		jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonCancelActionPerformed();
			}
		});
		jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				Invoice321DetailDialog.this.mouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				Invoice321DetailDialog.this.mouseExited(evt);
			}
		});

		jPanelTopBar.setBackground(new java.awt.Color(255, 255, 255));
		jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

		jLabelTopBarImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/topbar2.jpg")));

		jLabelTopBarMsg2.setFont(Constants.TITLE_FONT_ITALIC);
		jLabelTopBarMsg2.setForeground(Constants.FONT_COLOR);
		jLabelTopBarMsg2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabelTopBarMsg2.setText(Constants.LANG.getString("TopBarMessage2"));

		jLabelTopBarMsg1.setFont(Constants.TITLE_FONT_ITALIC);
		jLabelTopBarMsg1.setForeground(Constants.FONT_COLOR);
		jLabelTopBarMsg1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabelTopBarMsg1.setText(Constants.LANG.getString("TopBarMessage1"));

		org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
		jPanelTopBar.setLayout(jPanel9Layout);
		jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			.add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				jPanel9Layout
					.createSequentialGroup()
					.add(jLabelTopBarImage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 404,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 47, Short.MAX_VALUE)
					.add(
						jPanel9Layout
							.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
							.add(jLabelTopBarMsg2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.add(jLabelTopBarMsg1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
					.addContainerGap()));
		jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
			jPanel9Layout
				.createSequentialGroup()
				.add(
					jPanel9Layout
						.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(
							jPanel9Layout.createSequentialGroup().add(4, 4, 4).add(jLabelTopBarMsg1).add(1, 1, 1)
								.add(jLabelTopBarMsg2)).add(jLabelTopBarImage))
				.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jButtonHelp.setBackground(Constants.BKG_MAIN_COLOR);
		jButtonHelp.setForeground(Constants.BKG_MAIN_COLOR);
		jButtonHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_help.jpg")));
		jButtonHelp.setToolTipText(Constants.LANG.getString("Help"));
		jButtonHelp.setBorderPainted(false);
		jButtonHelp.setContentAreaFilled(false);
		jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonHelpActionPerformed();
			}
		});
		jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				Invoice321DetailDialog.this.mouseEntered(evt);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				Invoice321DetailDialog.this.mouseExited(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanelMainLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
		mainPanel.setLayout(jPanelMainLayout);
		jPanelMainLayout.setHorizontalGroup(jPanelMainLayout.createParallelGroup(
			org.jdesktop.layout.GroupLayout.LEADING).add(
			jPanelMainLayout
				.createSequentialGroup()
				.addContainerGap()
				.add(
					jPanelMainLayout
						.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
							org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(jPanelTaxes, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
							org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.add(
							org.jdesktop.layout.GroupLayout.TRAILING,
							jPanelMainLayout
								.createSequentialGroup()
								.add(jPanelGeneral, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
									org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
									org.jdesktop.layout.GroupLayout.DEFAULT_SIZE)
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanelOtherData, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
									org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
									org.jdesktop.layout.GroupLayout.DEFAULT_SIZE))
						.add(
							org.jdesktop.layout.GroupLayout.TRAILING,
							jPanelMainLayout
								.createSequentialGroup()
								.add(jButtonAccept)
								.add(50, 50, 50)
								.add(jButtonCancel)
								.add(20, 20, 20)
								.add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 550,
									org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(20, 20, 20)
								.add(jButtonHelp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37,
									org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		jPanelMainLayout.setVerticalGroup(jPanelMainLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
			.add(
				jPanelMainLayout
					.createSequentialGroup()
					.addContainerGap()
					.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
					.add(
						jPanelMainLayout
							.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							.add(jPanelOtherData, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
							.add(jPanelGeneral, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
					.add(jPanelTaxes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					//              .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
					.add(
						jPanelMainLayout
							.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
							.add(jButtonAccept)
							.add(jButtonCancel)
							.add(fd)
							.add(jButtonHelp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap()));

		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("language/lang");
		jPanelGeneral.getAccessibleContext().setAccessibleName(bundle.getString("General"));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(mainPanel,
			org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 865, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(mainPanel,
			org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 576, Short.MAX_VALUE));

		pack();
	}

	private void jButtonCancelActionPerformed() {
		jPanelGeneral.getJComboBoxGDesc().setSelectedItem(null);
		jPanelGeneral.getJTextFieldGUnitPrice().setText(null);
		jPanelGeneral.getJTextFieldGQuantity().setText(null);
		jPanelGeneral.getJComboBoxGUnitsOfM().setSelectedItem(null);

		setVisible(false);
		dispose();
	}

	private void mouseEntered(java.awt.event.MouseEvent evt) {
		this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
	}

	private void mouseExited(java.awt.event.MouseEvent evt) {
		this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
	}

	private void loadDecimalConfiguration() {
		try {
			decimalProps = new Properties();
			decimalProps.load(this.getClass().getResourceAsStream(
				Constants.APP_PROP.getProperty("fact" + codeVersion + "d_resource")));
		} catch (Exception e) {
			logger.error("An exception occurred when loading the properties of the configuration file: "
				+ e.getMessage());
		}
	}

	public void showMessage(String message, Color msgColor) {
		fd.showMessage(message, msgColor);
	}

	public InvoiceLineType getValues() {

		InvoiceLineType result = new InvoiceLineType();

		String desc = null;
		String quantity = null;
		String unitPrice = null;
		if (jPanelGeneral.getJComboBoxGDesc().getSelectedItem() != null) {
			desc = jPanelGeneral.getJComboBoxGDesc().getSelectedItem().toString();
		}
		if (jPanelGeneral.getJTextFieldGQuantity() != null)
			quantity = jPanelGeneral.getJTextFieldGQuantity().getText();
		if (jPanelGeneral.getJTextFieldGUnitPrice() != null)
			unitPrice = jPanelGeneral.getJTextFieldGUnitPrice().getText();

		if (desc == null || "".equals(desc.trim()) || quantity == null || "".equals(quantity.trim())
			|| unitPrice == null || "".equals(unitPrice.trim())) // Canceled by user
			// || jPanelTaxes.getJTableTOutputs().getRowCount() == 0 --> The invoice could be no taxable or exempt.
			return result;

		// General panel
		// 3.1.6.1.7 ItemDescription
		result.setItemDescription(desc);
		// 3.1.6.1.8 Quantity
		result.setQuantity(DoubleUtil.getDoubleFromPrettyNumber(quantity));
		// 3.1.6.1.9 UnitOfMeasure
		result.setUnitOfMeasure(IntegerUtil.to2String(jPanelGeneral.getJComboBoxGUnitsOfM().getSelectedIndex() + 1));
		// 3.1.6.1.10 UnitPriceWithoutTax
		result.setUnitPriceWithoutTax(DoubleUtil.getDoubleFromPrettyNumber(unitPrice));
		// 3.1.6.1.11 TotalCost
		result.setTotalCost(DoubleUtil.getDoubleFromPrettyNumber(jPanelGeneral.getJTextFieldGTotAmount().getText()));
		// 3.1.6.1.14 GrossAmount
		result.setGrossAmount(DoubleUtil.roundWithPrecisionDouble(DoubleUtil.getDoubleFromPrettyNumber(jPanelGeneral.getJTextFieldGGROSS().getText()),
			Integer.parseInt(decimalProps.getProperty("item_gross_cost"))));
		// 3.1.6.1.19 Aditional Information
		result.setAdditionalLineItemInformation(jPanelGeneral.getJTextAreaGAddInfo().getText());
		// 3.1.6.20 Extensions
		if (jPanelGeneral.getJTextAreaXMLExtAddData().getText() != null
			&& !"".equals(jPanelGeneral.getJTextAreaXMLExtAddData().getText().trim())) {
			ExtensionsType extension = new es.mityc.appfacturae.facturae32.ExtensionsType();
			InputStream isExt = null;
			try {
				isExt = new ByteArrayInputStream(jPanelGeneral.getJTextAreaXMLExtAddData().getText().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("Error creating a bytes array with extension data. The encoding is unsupported: "
					+ e.getMessage());
			}
			try {
				NodeList nl = StreamUtil.createDoc(isExt).getChildNodes();
				for (int i = 0; i < nl.getLength(); i++)
					((es.mityc.appfacturae.facturae32.ExtensionsType) extension).getAny().add(nl.item(i));
				result.setExtensions(extension);
			} catch (ValidationException ve) {
				logger.error("A validation error occurred. The extension element is not valid: " + ve.getMessage());
			}
		}

		// 3.1.6.1.12 DiscountsAndRebates
		DiscountsAndRebatesType discountsAndRebates = new DiscountsAndRebatesType();
		TableModel modelD = jPanelGeneral.getJTableGDiscounts().getModel();
		int discountSize = modelD.getRowCount();
		for (int i = 0; i < discountSize; ++i) {
			// 3.1.6.1.12.1 Discount
			DiscountType discount = new DiscountType();
			// 3.1.6.1.12.1.1 DiscountReason
			discount.setDiscountReason(jPanelGeneral.getJTableGDiscounts().getModel().getValueAt(i, 0).toString());
			// 3.1.6.1.12.1.2 DiscountRate
			if (modelD.getValueAt(i, 1) != null && !"".equals(modelD.getValueAt(i, 1).toString().trim()))
				discount.setDiscountRate(DoubleUtil.getDoubleFromPrettyNumber(modelD.getValueAt(i, 1).toString()));
			// 3.1.6.1.12.1.3 DiscountAmount
			discount.setDiscountAmount(DoubleUtil.getDoubleFromPrettyNumber(jPanelGeneral.getJTableGDiscounts().getModel()
				.getValueAt(i, 2).toString()));

			discountsAndRebates.getDiscount().add(discount);
		}

		result.setDiscountsAndRebates(discountsAndRebates);

		// 3.1.6.1.13 Charges
		ChargesType charges = new ChargesType();
		TableModel modelC = jPanelGeneral.getJTableGCharges().getModel();
		int chargesSize = modelC.getRowCount();
		for (int i = 0; i < chargesSize; ++i) {
			// 3.1.6.1.13.1 Charge
			ChargeType charge = new ChargeType();
			// 3.1.6.1.13.1.1 ChargeReason
			charge.setChargeReason(modelC.getValueAt(i, 0).toString());
			// 3.1.6.1.13.1.2 ChargeRate
			if (modelC.getValueAt(i, 1) != null && !"".equals(modelC.getValueAt(i, 1).toString().trim()))
				charge.setChargeRate(DoubleUtil.getDoubleFromPrettyNumber(modelC.getValueAt(i, 1).toString()));
			// 3.1.6.1.13.1.3 ChargeAmount
			charge.setChargeAmount(DoubleUtil.getDoubleFromPrettyNumber(modelC.getValueAt(i, 2).toString()));

			charges.getCharge().add(charge);
		}

		result.setCharges(charges);

		// Other data panel
		// 3.1.6.1.1 IssuerContractReference
		result.setIssuerContractReference(jPanelOtherData.getJTextFieldOIssCRef().getText());
		// 3.1.6.1.2 IssuerContractDate
		XMLGregorianCalendar issuerContractDate = Invoice321DetailDialog.getDateFromCombo(jPanelOtherData
			.getJCalendarComboBoxOIssC());
		if (issuerContractDate != null && jPanelOtherData.getJRadioButtonOIssC().isSelected())
			result.setIssuerContractDate(issuerContractDate);
		else
			result.setIssuerContractDate(null);
		// 3.1.6.1.3 IssuerTransactionReference
		result.setIssuerTransactionReference(jPanelOtherData.getJTextFieldOIssORef().getText());
		// 3.1.6.1.4 IssuerTransactionDate
		XMLGregorianCalendar issuerTransactionDate = Invoice321DetailDialog.getDateFromCombo(jPanelOtherData
			.getJCalendarComboBoxOIssO());
		;
		if (issuerTransactionDate != null && jPanelOtherData.getJRadioButtonOIssO().isSelected())
			result.setIssuerTransactionDate(issuerTransactionDate);
		else
			result.setIssuerTransactionDate(null);
		// 3.1.6.1.5 ReceiverContractReference
		result.setReceiverContractReference(jPanelOtherData.getJTextFieldORecCRef().getText());
		// 3.1.6.1.6 ReceiverContractDate
		XMLGregorianCalendar receiverContractDate = Invoice321DetailDialog.getDateFromCombo(jPanelOtherData
			.getJCalendarComboBoxORecC());
		;
		if (receiverContractDate != null && jPanelOtherData.getJRadioButtonORecC().isSelected())
			result.setReceiverContractDate(receiverContractDate);
		else
			result.setReceiverContractDate(null);
		// 3.1.6.1.7 ReceiverTransactionReference
		result.setReceiverTransactionReference(jPanelOtherData.getJTextFieldORecORef().getText());
		// 3.1.6.1.8 ReceiverTransactionDate
		XMLGregorianCalendar receiverTransactionDate = Invoice321DetailDialog.getDateFromCombo(jPanelOtherData
			.getJCalendarComboBoxORecO());
		;
		if (receiverTransactionDate != null && jPanelOtherData.getJRadioButtonORecO().isSelected())
			result.setReceiverTransactionDate(receiverTransactionDate);
		else
			result.setReceiverTransactionDate(null);
		// 3.1.6.1.9 FileReference
		result.setFileReference(jPanelOtherData.getJTextFieldOFileRef().getText());
		// 3.1.6.1.10 FileDate
		XMLGregorianCalendar fileDate = Invoice321DetailDialog.getDateFromCombo(jPanelOtherData
			.getJCalendarComboBoxOFile());
		;
		if (fileDate != null && jPanelOtherData.getJRadioButtonOFile().isSelected())
			result.setFileDate(fileDate);
		else
			result.setFileDate(null);
		// 3.1.6.1.11 SequenceNumber
		if (jPanelOtherData.getJTextFieldOOrdRef().getText() != null
			&& !"".equals(jPanelOtherData.getJTextFieldOOrdRef().getText().trim()))
			result.setSequenceNumber(DoubleUtil.getDoubleFromPrettyNumber(jPanelOtherData.getJTextFieldOOrdRef().getText()));

		// 3.1.6.1.12 DeliveryNotesReferences
		DeliveryNotesReferencesType deliveryNotesReferences = new DeliveryNotesReferencesType();
		int deliverySize = jPanelOtherData.getJTableODelivery().getModel().getRowCount();
		for (int i = 0; i < deliverySize; ++i) {
			// 3.1.6.1.12.1 DeliveryNote
			DeliveryNoteType deliveryNote = new DeliveryNoteType();
			// 3.1.6.1.12.1.1 DeliveryNoteNumber
			deliveryNote.setDeliveryNoteNumber(jPanelOtherData.getJTableODelivery().getModel().getValueAt(i, 0)
				.toString());
			// 3.1.6.1.12.1.2 DeliveryNoteDate
			XMLGregorianCalendar deliveryNoteDate = null;
			if (jPanelOtherData.getJTableODelivery().getModel().getValueAt(i, 1) != null
				&& !"".equals(jPanelOtherData.getJTableODelivery().getModel().getValueAt(i, 1).toString().trim())) {
				try {
					DatatypeFactory df;
					df = DatatypeFactory.newInstance();
					deliveryNoteDate = df.newXMLGregorianCalendar();
					GregorianCalendar calendar = new GregorianCalendar();
					calendar.setTime(Constants.DATE_FORMAT.parse(jPanelOtherData.getJTableODelivery().getModel()
						.getValueAt(i, 1).toString()));
					deliveryNoteDate.setDay(calendar.get(Calendar.DAY_OF_MONTH));
					deliveryNoteDate.setMonth(calendar.get(Calendar.MONTH) + 1);
					deliveryNoteDate.setYear(calendar.get(Calendar.YEAR));
					deliveryNote.setDeliveryNoteDate(deliveryNoteDate);
				} catch (DatatypeConfigurationException e) {
					logger.error("Error getting invoice line data while creating a XMLGregorianCalendar:"
						+ e.getMessage());
					return null;
				} catch (ParseException e) {
					logger.error("Error getting invoice line data while parsing Date:" + e.getMessage());
					return null;
				}
			}
			deliveryNotesReferences.getDeliveryNote().add(deliveryNote);
		}

		result.setDeliveryNotesReferences(deliveryNotesReferences);

		// Tax panel
		// 3.1.3 TaxesOutputs
		InvoiceLineType.TaxesOutputs taxesOutputs = new InvoiceLineType.TaxesOutputs();
		TableModel modelO = jPanelTaxes.getJTableTOutputs().getModel();
		int outputsSize = modelO.getRowCount();
		for (int i = 0; i < outputsSize; i++) {
			// 3.1.3.1 Tax
			InvoiceLineType.TaxesOutputs.Tax taxOut = new InvoiceLineType.TaxesOutputs.Tax();
			// 3.1.3.1.1 TaxTypeCode
			if (modelO.getValueAt(i, 0) != null && !"".equals(modelO.getValueAt(i, 0).toString().trim())) {
				String taxTypeCode = modelO.getValueAt(i, 0).toString().split(" - ")[1];
				taxOut.setTaxTypeCode(taxTypeCode);
			}
			// 3.1.3.1.2 TaxRate
			if (modelO.getValueAt(i, 1) != null && !"".equals(modelO.getValueAt(i, 1).toString().trim()))
				taxOut.setTaxRate(DoubleUtil.getDoubleFromPrettyNumber(modelO.getValueAt(i, 1).toString()));

			// 3.1.3.1.3 TaxableBase
			AmountType taxableBase = new AmountType();
			// 3.1.3.1.3.1 TotalAmount
			taxableBase.setTotalAmount(DoubleUtil.getDoubleFromPrettyNumber(modelO.getValueAt(i, 2).toString()));

			taxOut.setTaxableBase(taxableBase);

			// 3.1.3.1.4 TaxAmount
			AmountType taxAmount5 = new AmountType();
			// 3.1.3.1.4.1 TotalAmount
			taxAmount5.setTotalAmount(DoubleUtil.getDoubleFromPrettyNumber(modelO.getValueAt(i, 3).toString()));

			taxOut.setTaxAmount(taxAmount5);

			// 3.1.3.1.5 SpecialTaxableBase
			if (modelO.getValueAt(i, 4) != null && !"".equals(modelO.getValueAt(i, 4).toString().trim())) {
				AmountType specialTaxableBase = new AmountType();
				// 3.1.3.1.5.1 TotalAmount
				specialTaxableBase.setTotalAmount(DoubleUtil.getDoubleFromPrettyNumber(modelO.getValueAt(i, 4).toString()));

				taxOut.setSpecialTaxableBase(specialTaxableBase);
			}

			// 3.1.3.1.6 SpecialTaxAmount
			if (modelO.getValueAt(i, 5) != null && !"".equals(modelO.getValueAt(i, 5).toString().trim())) {
				AmountType specialTaxAmount = new AmountType();
				// 3.1.3.1.6.1 TotalAmount
				specialTaxAmount.setTotalAmount(DoubleUtil.getDoubleFromPrettyNumber(modelO.getValueAt(i, 5).toString()));

				taxOut.setSpecialTaxAmount(specialTaxAmount);
			}
			// 3.1.3.1.7 EquivalenceSurcharge
			if (modelO.getValueAt(i, 6) != null && !"".equals(modelO.getValueAt(i, 6).toString().trim()))
				taxOut.setEquivalenceSurcharge(DoubleUtil.getDoubleFromPrettyNumber(modelO.getValueAt(i, 6).toString()));

			// 3.1.3.1.8 EquivalenceSurchargeAmount
			if (modelO.getValueAt(i, 7) != null && !"".equals(modelO.getValueAt(i, 7).toString().trim())) {
				AmountType equivalenceSurchargeAmount = new AmountType();
				// 3.1.3.1.8.1 TotalAmount
				equivalenceSurchargeAmount.setTotalAmount(DoubleUtil.getDoubleFromPrettyNumber(modelO.getValueAt(i, 7).toString()));

				taxOut.setEquivalenceSurchargeAmount(equivalenceSurchargeAmount);
			}

			taxesOutputs.getTax().add(taxOut);
		}

		result.setTaxesOutputs(taxesOutputs);

		// 3.1.4 TaxesWithheld
		TaxesType taxesWithheld = new TaxesType();
		TableModel modelW = jPanelTaxes.getJTableTWithhelds().getModel();
		int withheldSize = modelW.getRowCount();
		for (int i = 0; i < withheldSize; ++i) {
			// 3.1.4.1 Tax
			TaxType tax = new TaxType();

			// 3.1.4.1.1 TaxTypeCode
			String taxTypeCode = modelW.getValueAt(i, 0).toString().split(" - ")[1];
			tax.setTaxTypeCode(taxTypeCode);

			// 3.1.4.1.2 TaxRate
			tax.setTaxRate(DoubleUtil.getDoubleFromPrettyNumber(modelW.getValueAt(i, 1).toString()));
			// 3.1.4.1.3 TaxableBase
			AmountType taxableBase = new AmountType();
			// 3.1.4.1.3.1 TotalAmount
			taxableBase.setTotalAmount(DoubleUtil.getDoubleFromPrettyNumber(modelW.getValueAt(i, 2).toString()));

			tax.setTaxableBase(taxableBase);

			// 3.1.4.1.4 TaxAmount
			AmountType taxAmount6 = new AmountType();
			// 3.1.4.1.4.1 TotalAmount
			taxAmount6.setTotalAmount(DoubleUtil.getDoubleFromPrettyNumber(modelW.getValueAt(i, 3).toString()));

			tax.setTaxAmount(taxAmount6);

			taxesWithheld.getTax().add(tax);
		}
		result.setTaxesWithheld(taxesWithheld);

		// 3.1.6.1.26 SpecialTaxableEvent
		if (jPanelTaxes.getJComboBoxSpecial().getSelectedItem() != null
			&& !"".equals(jPanelTaxes.getJComboBoxSpecial().getSelectedItem().toString())) {
			SpecialTaxableEventType specialTaxableEvent = new SpecialTaxableEventType();
			// 3.1.6.1.26.1 SpecialTaxableEventCode
			specialTaxableEvent.setSpecialTaxableEventCode(IntegerUtil.to2String(jPanelTaxes.getJComboBoxSpecial()
				.getSelectedIndex()));
			// 3.1.6.1.26.2 SpecialTaxableEventReason
			specialTaxableEvent.setSpecialTaxableEventReason(jPanelTaxes.getJTextFieldSpecial().getText());
			result.setSpecialTaxableEvent(specialTaxableEvent);
		}

		// 3.1.6.1.27 ArticleCode
		result.setArticleCode(jPanelOtherData.getJTextFieldOArticle().getText());

		return result;
	}

	public void setValues(InvoiceLineType ilt) {

		// General panel

		// 3.1.6.1.7 ItemDescription	
		String desc = ilt.getItemDescription();
		if (desc != null)
			jPanelGeneral.getJComboBoxGDesc().setSelectedItem(desc);

		// 3.1.6.1.8 Quantity		
		Double quantity = ilt.getQuantity();
		if (quantity != null)
			jPanelGeneral.getJTextFieldGQuantity().setText(DoubleUtil.formatDecimal(quantity,-1));

		// 3.1.6.1.9 UnitOfMeasure
		if (ilt.getUnitOfMeasure() != null)
			jPanelGeneral.getJComboBoxGUnitsOfM().setSelectedIndex(Integer.parseInt(ilt.getUnitOfMeasure()) - 1);

		// 3.1.6.1.10 UnitPriceWithoutTax
		Double unitPriceWithoutTax = ilt.getUnitPriceWithoutTax();
		if (unitPriceWithoutTax != null)
			jPanelGeneral.getJTextFieldGUnitPrice().setText(DoubleUtil.formatDecimal(unitPriceWithoutTax,Integer.parseInt(decimalProps.getProperty("item_unit_price"))));

		// 3.1.6.1.11 TotalCost
		Double totalCost = ilt.getTotalCost();
		if (totalCost != null)
			jPanelGeneral.getJTextFieldGTotAmount().setText(DoubleUtil.formatDecimal(totalCost,Integer.parseInt(decimalProps.getProperty("item_total_cost"))));

		// 3.1.6.1.14 GrossAmount
		Double grossAmount = ilt.getGrossAmount();
		if (grossAmount != null)
			jPanelGeneral.getJTextFieldGGROSS().setText(DoubleUtil.formatDecimal(grossAmount,Integer.parseInt(decimalProps.getProperty("item_total_cost"))));

		// 3.1.6.1.19 Additional Information
		if (ilt.getAdditionalLineItemInformation() != null)
			jPanelGeneral.getJTextAreaGAddInfo().setText(ilt.getAdditionalLineItemInformation());

		// 3.1.6.20 Extensions
		if (ilt.getExtensions() != null) {
			ExtensionsType extension = ilt.getExtensions();
			if (extension != null) {
				OutputStream xml = new ByteArrayOutputStream();
				for (int j = 0; j < extension.getAny().size(); ++j) {
					Element element = (Element) extension.getAny().get(j);
					try {
						Transformer xformer = TransformerFactory.newInstance().newTransformer();
						xformer.transform(new DOMSource(element), new StreamResult(xml));
					} catch (TransformerConfigurationException e) {
						logger.error("The extension has not been set properly: " + e.getMessage());
						fd.showMessage(Constants.LANG.getString("NOOKExtensionSet"), Constants.ERROR_MSG_COLOR);
					} catch (TransformerFactoryConfigurationError e) {
						logger.error("The extension has not been set properly: " + e.getMessage());
						fd.showMessage(Constants.LANG.getString("NOOKExtensionSet"), Constants.ERROR_MSG_COLOR);
					} catch (TransformerException e) {
						logger.error("The extension has not been set properly: " + e.getMessage());
						fd.showMessage(Constants.LANG.getString("NOOKExtensionSet"), Constants.ERROR_MSG_COLOR);
					}
				}
				jPanelGeneral.getJTextAreaXMLExtAddData().setText(xml.toString());
			}
		}

		// 3.1.6.1.12 DiscountsAndRebates
		TableModel modelD = jPanelGeneral.getJTableGDiscounts().getModel();

		for (int i = modelD.getRowCount() - 1; i > -1; i--)
			((DefaultTableModel) modelD).removeRow(i);

		if (ilt.getDiscountsAndRebates() != null) {
			int discountSize = ilt.getDiscountsAndRebates().getDiscount().size();

			for (int i = 0; i < discountSize; i++) {

				Object[] ob = new Object[3];

				String discountReason = ilt.getDiscountsAndRebates().getDiscount().get(i).getDiscountReason();
				if (discountReason != null)
					ob[0] = discountReason;

				if (ilt.getDiscountsAndRebates().getDiscount().get(i).getDiscountRate() != null)
					ob[1] = DoubleUtil.formatDecimal(ilt.getDiscountsAndRebates().getDiscount().get(i).getDiscountRate(),Integer.parseInt(decimalProps.getProperty("item_discount_rate")));

				Double discountAmount = ilt.getDiscountsAndRebates().getDiscount().get(i).getDiscountAmount();
				if (discountAmount != null)
					ob[2] = DoubleUtil.formatDecimal(discountAmount,Integer.parseInt(decimalProps.getProperty("item_discount")));

				((DefaultTableModel) modelD).addRow(ob);
			}

			jPanelGeneral.getJTableGDiscounts().setModel(modelD);
		}

		// 3.1.6.1.13 Charges
		TableModel modelC = jPanelGeneral.getJTableGCharges().getModel();

		for (int i = modelC.getRowCount() - 1; i > -1; i--)
			((DefaultTableModel) modelC).removeRow(i);

		if (ilt.getCharges() != null) {
			int chargesSize = ilt.getCharges().getCharge().size();
			for (int i = 0; i < chargesSize; i++) {

				Object[] ob = new Object[3];
				String chargeReason = ilt.getCharges().getCharge().get(i).getChargeReason();
				if (chargeReason != null)
					ob[0] = chargeReason;

				if (ilt.getCharges().getCharge().get(i).getChargeRate() != null)
					ob[1] = ilt.getCharges().getCharge().get(i).getChargeRate();

				Double chargeAmount = ilt.getCharges().getCharge().get(i).getChargeAmount();
				if (chargeAmount != null)
					ob[2] = chargeAmount;

				((DefaultTableModel) modelC).addRow(ob);

			}

			jPanelGeneral.getJTableGCharges().setModel(modelC);
		}

		// Other data panel
		// 3.1.6.1.1 IssuerContractReference
		String issuerContractReference = ilt.getIssuerContractReference();
		if (issuerContractReference != null)
			jPanelOtherData.getJTextFieldOIssCRef().setText(issuerContractReference);
		// 3.1.6.1.2 IssuerContractDate
		if (ilt.getIssuerContractDate() != null) {
			jPanelOtherData.getJCalendarComboBoxOIssC().setCalendar(ilt.getIssuerContractDate().toGregorianCalendar());
			jPanelOtherData.getJRadioButtonOIssC().setSelected(true);
			jPanelOtherData.getJCalendarComboBoxOIssC().setVisible(true);
		}
		// 3.1.6.1.3 IssuerTransactionReference
		String issuerTransactionReference = ilt.getIssuerTransactionReference();
		if (issuerTransactionReference != null)
			jPanelOtherData.getJTextFieldOIssORef().setText(issuerTransactionReference);
		// 3.1.6.1.4 IssuerTransactionDate
		if (ilt.getIssuerTransactionDate() != null) {
			jPanelOtherData.getJCalendarComboBoxOIssO().setCalendar(
				ilt.getIssuerTransactionDate().toGregorianCalendar());
			jPanelOtherData.getJRadioButtonOIssO().setSelected(true);
			jPanelOtherData.getJCalendarComboBoxOIssO().setVisible(true);
		}
		// 3.1.6.1.5 ReceiverContractReference
		String receiverContractReference = ilt.getReceiverContractReference();
		if (receiverContractReference != null)
			jPanelOtherData.getJTextFieldORecCRef().setText(receiverContractReference);
		// 3.1.6.1.6 ReceiverContractDate
		if (ilt.getReceiverContractDate() != null) {
			jPanelOtherData.getJCalendarComboBoxORecC()
				.setCalendar(ilt.getReceiverContractDate().toGregorianCalendar());
			jPanelOtherData.getJRadioButtonORecC().setSelected(true);
			jPanelOtherData.getJCalendarComboBoxORecC().setVisible(true);
		}
		// 3.1.6.1.7 ReceiverTransactionReference
		String receiverTransactionReference = ilt.getReceiverTransactionReference();
		if (receiverTransactionReference != null)
			jPanelOtherData.getJTextFieldORecORef().setText(receiverTransactionReference);
		// 3.1.6.1.8 ReceiverTransactionDate
		if (ilt.getReceiverTransactionDate() != null) {
			jPanelOtherData.getJCalendarComboBoxORecO().setCalendar(
				ilt.getReceiverTransactionDate().toGregorianCalendar());
			jPanelOtherData.getJRadioButtonORecO().setSelected(true);
			jPanelOtherData.getJCalendarComboBoxORecO().setVisible(true);
		}
		// 3.1.6.1.9 FileReference
		if (ilt.getFileReference() != null)
			jPanelOtherData.getJTextFieldOFileRef().setText(ilt.getFileReference());
		// 3.1.6.1.10 FileDate
		if (ilt.getFileDate() != null) {
			jPanelOtherData.getJCalendarComboBoxOFile().setCalendar(ilt.getFileDate().toGregorianCalendar());
			jPanelOtherData.getJRadioButtonOFile().setSelected(true);
			jPanelOtherData.getJCalendarComboBoxOFile().setVisible(true);
		}
		// 3.1.6.1.11 SequenceNumber
		if (ilt.getSequenceNumber() != null)
			jPanelOtherData.getJTextFieldOOrdRef().setText(ilt.getSequenceNumber().toString());

		// 3.1.6.1.6 DeliveryNotesReferences
		TableModel modelDNR = jPanelOtherData.getJTableODelivery().getModel();

		for (int i = modelDNR.getRowCount() - 1; i > -1; i--)
			((DefaultTableModel) modelDNR).removeRow(i);

		if (ilt.getDeliveryNotesReferences() != null) {
			int deliverySize = ilt.getDeliveryNotesReferences().getDeliveryNote().size();
			for (int i = 0; i < deliverySize; i++) {

				Object[] ob = new Object[2];
				String deliveryNoteNumber = ilt.getDeliveryNotesReferences().getDeliveryNote().get(i)
					.getDeliveryNoteNumber();
				XMLGregorianCalendar deliveryNoteDate = ((es.mityc.appfacturae.facturae321.DeliveryNoteType) ilt
					.getDeliveryNotesReferences().getDeliveryNote().get(i)).getDeliveryNoteDate();
				if (deliveryNoteNumber != null)
					ob[0] = deliveryNoteNumber;
				if (deliveryNoteDate != null)
					ob[1] = Constants.DATE_FORMAT.format(deliveryNoteDate.toGregorianCalendar().getTime());

				((DefaultTableModel) modelDNR).addRow(ob);
			}
			jPanelOtherData.getJTableODelivery().setModel(modelDNR);
		}

		// Tax panel
		// 3.1.3 TaxesOutputs
		TableModel modelO = jPanelTaxes.getJTableTOutputs().getModel();

		for (int i = modelO.getRowCount() - 1; i > -1; i--)
			((DefaultTableModel) modelO).removeRow(i);

		int outputsSize = ilt.getTaxesOutputs().getTax().size();

		for (int i = 0; i < outputsSize; i++) {

			Object[] ob = new Object[8];

			// 3.1.3.1.1 TaxTypeCode
			String taxTypeCode = ilt.getTaxesOutputs().getTax().get(i).getTaxTypeCode();
			if (taxTypeCode != null) {
				String[] taxes = Constants.APP_PROP.getProperty("taxesType" + codeVersion).split(";");
				String typeTax = Constants.LANG.getString(taxes[Integer.valueOf(taxTypeCode) - 1]).split(" ")[0];
				ob[0] = typeTax + " - " + taxTypeCode;
			}

			// 3.1.3.1.2 TaxRate
			Double taxRate = ilt.getTaxesOutputs().getTax().get(i).getTaxRate();
			if (taxRate != null)
				ob[1] = taxRate;

			// 3.1.3.1.3 TaxableBase
			Double taxableBase = ilt.getTaxesOutputs().getTax().get(i).getTaxableBase().getTotalAmount();
			if (taxableBase != null)
				ob[2] = taxableBase;

			// 3.1.3.1.4 TaxAmount
			if (ilt.getTaxesOutputs().getTax().get(i).getTaxAmount() != null) {
				Double taxAmount = ilt.getTaxesOutputs().getTax().get(i).getTaxAmount().getTotalAmount();
				ob[3] = taxAmount;
			}

			// 3.1.3.1.5 SpecialTaxableBase	
			if (ilt.getTaxesOutputs().getTax().get(i).getSpecialTaxableBase() != null) {
				Double specialTaxableBase = ilt.getTaxesOutputs().getTax().get(i).getSpecialTaxableBase()
					.getTotalAmount();
				ob[4] = specialTaxableBase;
			}

			// 3.1.3.1.6 SpecialTaxAmount
			if (ilt.getTaxesOutputs().getTax().get(i).getSpecialTaxAmount() != null) {
				Double specialTaxAmount = ilt.getTaxesOutputs().getTax().get(i).getSpecialTaxAmount().getTotalAmount();
				ob[5] = specialTaxAmount;
			}

			// 3.1.3.1.7 EquivalenceSurcharge

			if (ilt.getTaxesOutputs().getTax().get(i).getEquivalenceSurcharge() != null) {
				Double equivalenceSurcharge = ilt.getTaxesOutputs().getTax().get(i).getEquivalenceSurcharge();
				ob[6] = equivalenceSurcharge;
			}

			// 3.1.3.1.8 EquivalenceSurchargeAmount
			if (ilt.getTaxesOutputs().getTax().get(i).getEquivalenceSurchargeAmount() != null) {
				Double equivalenceSurchargeAmount = ilt.getTaxesOutputs().getTax().get(i)
					.getEquivalenceSurchargeAmount().getTotalAmount();
				ob[7] = equivalenceSurchargeAmount;
			}

			((DefaultTableModel) modelO).addRow(ob);

		}

		jPanelTaxes.getJTableTOutputs().setModel(modelO);

		// 3.1.4 TaxesWithheld
		TableModel modelW = jPanelTaxes.getJTableTWithhelds().getModel();

		for (int i = modelW.getRowCount() - 1; i > -1; i--)
			((DefaultTableModel) modelW).removeRow(i);

		if (ilt.getTaxesWithheld() != null) {
			int withheldSize = ilt.getTaxesWithheld().getTax().size();
			for (int i = 0; i < withheldSize; i++) {

				Object[] ob = new Object[4];
				// 3.1.4.1.1 TaxTypeCode

				String taxTypeCode = ilt.getTaxesWithheld().getTax().get(i).getTaxTypeCode();
				if (taxTypeCode != null) {
					String[] taxes = Constants.APP_PROP.getProperty("taxesType" + codeVersion).split(";");
					String typeTax = Constants.LANG.getString(taxes[Integer.valueOf(taxTypeCode) - 1]).split(" ")[0];
					ob[0] = typeTax + " - " + taxTypeCode;
				}

				// 3.1.4.1.2 TaxRate
				Double taxRate = ilt.getTaxesWithheld().getTax().get(i).getTaxRate();
				if (taxRate != null)
					ob[1] = taxRate;

				// 3.1.4.1.3 TaxableBase
				Double taxableBase = ilt.getTaxesWithheld().getTax().get(i).getTaxableBase().getTotalAmount();
				if (taxableBase != null)
					ob[2] = taxableBase;

				// 3.1.4.1.4 TaxAmount

				if (ilt.getTaxesWithheld().getTax().get(i).getTaxAmount() != null) {
					Double taxAmount = ilt.getTaxesWithheld().getTax().get(i).getTaxAmount().getTotalAmount();
					ob[3] = taxAmount;
				}

				((DefaultTableModel) modelW).addRow(ob);

			}

			jPanelTaxes.getJTableTWithhelds().setModel(modelW);
		}

		// 3.1.6.1.26 SpecialTaxableEvent
		if (ilt.getSpecialTaxableEvent() != null) {
			if (!"".equals(ilt.getSpecialTaxableEvent().getSpecialTaxableEventCode()))
				jPanelTaxes.getJComboBoxSpecial().setSelectedIndex(
					Integer.parseInt(ilt.getSpecialTaxableEvent().getSpecialTaxableEventCode()));
			jPanelTaxes.getJTextFieldSpecial().setText(ilt.getSpecialTaxableEvent().getSpecialTaxableEventReason());
		}

		// 3.1.6.1.27 ArticleCode
		if (ilt.getArticleCode() != null)
			jPanelOtherData.getJTextFieldOArticle().setText(ilt.getArticleCode());

	}

	private void setFieldsDisabled() {
		jButtonAccept.setEnabled(false);
		jButtonAccept.setDisabledIcon(new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_accept_disabled.jpg")));    	
		//jButtonCancel.setEnabled(false);
		//jButtonCancel.setDisabledIcon(new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_cancel_disabled.jpg")));
		
		// General panel active components
		jPanelGeneral.getJComboBoxGDesc().setEnabled(false);
		jPanelGeneral.getJTextFieldGQuantity().setEnabled(false);
		jPanelGeneral.getJComboBoxGUnitsOfM().setEnabled(false);
		jPanelGeneral.getJTextFieldGUnitPrice().setEnabled(false);
		jPanelGeneral.getJTextAreaGAddInfo().setEnabled(false);
		jPanelGeneral.getJTextAreaXMLExtAddData().setEnabled(false);
		jPanelGeneral.getJButtonXMLAddData().setEnabled(false);
		jPanelGeneral.getJButtonXMLAddData().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_accept_disabled.jpg")));
		jPanelGeneral.getJButtonXMLAddDataZoom().setEnabled(false);
		jPanelGeneral.getJButtonXMLAddDataZoom().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_zoom_disabled.jpg")));
		jPanelGeneral.getJButtonGAddCharge().setEnabled(false);
		jPanelGeneral.getJButtonGAddCharge().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
		jPanelGeneral.getJButtonGAddDiscount().setEnabled(false);
		jPanelGeneral.getJButtonGAddDiscount().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
		jPanelGeneral.getJButtonGDelCharge().setEnabled(false);
		jPanelGeneral.getJButtonGDelCharge().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
		jPanelGeneral.getJButtonGDelDiscount().setEnabled(false);
		jPanelGeneral.getJButtonGDelDiscount().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_minus_disabled.jpg")));

		// Other data active components
		jPanelOtherData.getJTextFieldOIssCRef().setEnabled(false);
		jPanelOtherData.getJCalendarComboBoxOIssC().setEnabled(false);
		jPanelOtherData.getJTextFieldOIssORef().setEnabled(false);
		jPanelOtherData.getJCalendarComboBoxOIssO().setEnabled(false);
		jPanelOtherData.getJTextFieldOOrdRef().setEnabled(false);
		jPanelOtherData.getJTextFieldORecCRef().setEnabled(false);
		jPanelOtherData.getJCalendarComboBoxORecC().setEnabled(false);
		jPanelOtherData.getJTextFieldORecORef().setEnabled(false);
		jPanelOtherData.getJCalendarComboBoxORecO().setEnabled(false);
		jPanelOtherData.getJButtonOAddDelivery().setEnabled(false);
		jPanelOtherData.getJButtonOAddDelivery().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
		jPanelOtherData.getJButtonODelDelivery().setEnabled(false);
		jPanelOtherData.getJButtonODelDelivery().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
		jPanelOtherData.getJTextFieldOFileRef().setEnabled(false);
		jPanelOtherData.getJCalendarComboBoxOFile().setEnabled(false);
		jPanelOtherData.getJTextFieldOArticle().setEnabled(false);
		jPanelOtherData.getJRadioButtonOFile().setEnabled(false);
		jPanelOtherData.getJRadioButtonOIssC().setEnabled(false);
		jPanelOtherData.getJRadioButtonOIssO().setEnabled(false);
		jPanelOtherData.getJRadioButtonORecC().setEnabled(false);
		jPanelOtherData.getJRadioButtonORecO().setEnabled(false);

		// Taxex active components
		jPanelTaxes.getJButtonTAddOutput().setEnabled(false);
		jPanelTaxes.getJButtonTAddOutput().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
		jPanelTaxes.getJButtonTDelOutput().setEnabled(false);
		jPanelTaxes.getJButtonTDelOutput().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
		jPanelTaxes.getJButtonTAddWithheld().setEnabled(false);
		jPanelTaxes.getJButtonTAddWithheld().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
		jPanelTaxes.getJButtonTDelWithheld().setEnabled(false);
		jPanelTaxes.getJButtonTDelWithheld().setDisabledIcon(
			new ImageIcon(jPanelGeneral.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
		jPanelTaxes.getJComboBoxSpecial().setEnabled(false);
		jPanelTaxes.getJTextFieldSpecial().setEditable(false);
	}

	public InvoiceDetailTaxesPanel getPanelTaxes() {
		return jPanelTaxes;
	}

	public InvoiceDetailOtherDataPanel getPanelOtherData() {
		return jPanelOtherData;
	}

	private void jButtonHelpActionPerformed() {
		URL helpFile = this.getClass().getResource(
			"/html/help_InvoiceDetailDialog_" + Constants.LANG.getLocale().getLanguage() + ".html");
		if (helpFile == null) {
			Constants.DIALOG.showErrorHelp();
			return;
		}

		ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
		chd.setVisible(true);
		chd.dispose();
	}

	public FadingCanvas getFadingCanvas() {
		return fd;
	}

	private static XMLGregorianCalendar getDateFromCombo(JCalendarComboBox jc) {
		XMLGregorianCalendar date = null;
		try {
			DatatypeFactory df = DatatypeFactory.newInstance();
			date = df.newXMLGregorianCalendar();
			date.setDay(jc.getCalendar().get(Calendar.DAY_OF_MONTH));
			date.setMonth(jc.getCalendar().get(Calendar.MONTH) + 1);
			date.setYear(jc.getCalendar().get(Calendar.YEAR));
		} catch (DatatypeConfigurationException e) {
			logger.error("Error getting invoice data while creating a XMLGregorianCalendar:" + e.getMessage());
			return null;
		}
		return date;
	}

	// JPanel
	private javax.swing.JPanel mainPanel = null;
	private javax.swing.JPanel jPanelTopBar = null;
	private Invoice32DetailOtherDataPanel jPanelOtherData = null;
	public InvoiceDetailGeneralPanel jPanelGeneral = null;
	public Invoice321DetailTaxesPanel jPanelTaxes = null;
	// JButton
	private javax.swing.JButton jButtonHelp = null;
	private javax.swing.JButton jButtonAccept = null;
	private javax.swing.JButton jButtonCancel = null;
	// JLabel
	private javax.swing.JLabel jLabelTopBarImage = null;
	private javax.swing.JLabel jLabelTopBarMsg2 = null;
	private javax.swing.JLabel jLabelTopBarMsg1 = null;
}