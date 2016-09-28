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
package es.mityc.appfacturae.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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
import org.hibernate.SQLQuery;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.SignInvoiceException;
import es.mityc.appfacturae.facturae.AdditionalDataType;
import es.mityc.appfacturae.facturae.AmountsWithheldType;
import es.mityc.appfacturae.facturae.AttachedDocumentsType;
import es.mityc.appfacturae.facturae.AttachmentType;
import es.mityc.appfacturae.facturae.BusinessType;
import es.mityc.appfacturae.facturae.ChargeType;
import es.mityc.appfacturae.facturae.ChargesType;
import es.mityc.appfacturae.facturae.CorrectiveType;
import es.mityc.appfacturae.facturae.DiscountType;
import es.mityc.appfacturae.facturae.DiscountsAndRebatesType;
import es.mityc.appfacturae.facturae.ExtensionsType;
import es.mityc.appfacturae.facturae.Facturae;
import es.mityc.appfacturae.facturae.IndividualType;
import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.facturae.InvoiceHeaderType;
import es.mityc.appfacturae.facturae.InvoiceIssueDataType;
import es.mityc.appfacturae.facturae.InvoiceTotalsType;
import es.mityc.appfacturae.facturae.InvoiceType;
import es.mityc.appfacturae.facturae.LegalEntityType;
import es.mityc.appfacturae.facturae.LegalLiteralsType;
import es.mityc.appfacturae.facturae.PartiesType;
import es.mityc.appfacturae.facturae.PeriodDates;
import es.mityc.appfacturae.facturae.PlaceOfIssueType;
import es.mityc.appfacturae.facturae.TaxIdentificationType;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.FacturaeStatics;
import es.mityc.appfacturae.hibernate.auxClass.AttachedDocument;
import es.mityc.appfacturae.hibernate.auxClass.EnumOperationType;
import es.mityc.appfacturae.hibernate.auxClass.FACeSentResult;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.hibernate.auxClass.xmlData;
import es.mityc.appfacturae.ui.LookAndFeelLoading;
import es.mityc.appfacturae.ui.components.NoEdiTableModel;
import es.mityc.appfacturae.ui.dialogs.CorrectionDataDialog;
import es.mityc.appfacturae.ui.dialogs.FACeSentResultDialog;
import es.mityc.appfacturae.ui.dialogs.SendDialog;
import es.mityc.appfacturae.ui.windows.GenerateInvoice32Window;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.ui.windows.panels.InvoiceDetail32Panel;
import es.mityc.appfacturae.ui.windows.panels.InvoiceGeneralPanel;
import es.mityc.appfacturae.ui.windows.panels.PartyPanel;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.ComboUtil;
import es.mityc.appfacturae.utils.io.DoubleUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.appfacturae.utils.storemanager.PluginAlmCertUCCD;
import es.mityc.facturae.FacturaeVersion;
import es.mityc.facturae.utils.MarshalException;
import es.mityc.facturae.utils.MarshallerUtil;
import es.mityc.facturae.utils.SigningException;
import es.mityc.facturae.utils.UnmarshalException;
import es.mityc.facturae.utils.UnmarshallerUtil;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.ValidatorUtil;
import es.mityc.facturae.utils.XMLUtil;
import es.mityc.facturae32.AdministrativeCentreType;
import es.mityc.facturaeface.bean.SSPPResultadoEnviarFactura;
import es.mityc.facturaeface.exception.FACeException;
import es.mityc.javasign.bridge.ConfigurationException;
import es.mityc.javasign.bridge.ISignFacade;
import es.mityc.javasign.bridge.SignFactory;

public class Invoice32Util implements InvoiceUtil {
	
	private static Log logger = LogFactory.getLog(Invoice32Util.class);
	private static final String codeVersion = Constants.FACTURAE32;
	private static final String version = "3.2";
    private static Properties decimalProps = null;
    private String[] store = null;
    
    public Invoice32Util(){
    	store = new PluginAlmCertUCCD().getStore();
    }
    
	/**
     * Charges an invoice stored in a File to screen
     * 
     * Argument openType.- 
     * 					true  .- See invoice  (all disabled, no dialog)
     * 					false .- Corrective Invoice (nothing disabled, a dialog is shown)
     * 					null  .- Edit invoice (nothing disabled, no dialog)
	 * @throws UnmarshalException 
     */
	public void chargeInvoice(final Boolean openType, String id) throws UnmarshalException {

		GenerateInvoice32Window in32WinInstance = GenerateInvoice32Window.getInstance(null);

		if (in32WinInstance != null) {	
			File f = FacturaeManager.getInstance().loadInvoice(id);
			Facturae inv = null;
			
			if (f != null) {
				try {
					UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
					inv = IntermediaryUtil.getApplicationFacturae((es.mityc.facturae32.Facturae)unmarshallerUtil.unmarshal(f));
				} catch (UnmarshalException e) {
					throw e;
				}
				SQLQuery s = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_FACTURAE_BY_NUMBER.replace("$1", id));
				List<?> ls = s.list();
				if (ls != null && ls.size() > 0){
					int idBuyer = Integer.parseInt(((Object[])ls.get(0))[0].toString());
					inv.getParties().getBuyerParty().setId(idBuyer);
					if(inv.getParties().getBuyerParty().isIndividualType())
						inv.getParties().getBuyerParty().getIndividual().setId(idBuyer);
					else
						inv.getParties().getBuyerParty().getLegalEntity().setId(idBuyer);
					int idSeller = Integer.parseInt(((Object[])ls.get(0))[1].toString());
					inv.getParties().getSellerParty().setId(idSeller);
					if(inv.getParties().getSellerParty().isIndividualType())
						inv.getParties().getSellerParty().getIndividual().setId(idSeller);
					else
						inv.getParties().getSellerParty().getLegalEntity().setId(idSeller);
				}
				setInvoiceData(inv, in32WinInstance.getJPanelInvoiceGeneral(), in32WinInstance.getJPanelIssuer(), in32WinInstance.getJPanelReceiver(), in32WinInstance.getJPanelInvoiceDetail());
			}
			
			if (openType != null) {
				if (openType) {
					setFieldsDisabled(in32WinInstance.getJPanelInvoiceGeneral(), in32WinInstance.getJPanelIssuer(), in32WinInstance.getJPanelReceiver(), in32WinInstance.getJPanelInvoiceDetail());
					try {
						FacturaeManager.getInstance().saveAction(EnumOperationType.SeeIn, id);
					} catch (DatabaseOperationException e) {
						// The user is not informed about action not saved event. An error log has been created previously.
					}
					
				} else {
					CorrectiveType ct = inv.getInvoices().getInvoice().get(0).getInvoiceHeader().getCorrective();
					if (ct != null)
						((GenerateInvoice32Window)in32WinInstance).setRectifyEnabled(ct.getInvoiceSeriesCode(), ct.getInvoiceNumber());
					else
						((GenerateInvoice32Window)in32WinInstance).setRectifyEnabled("", "");
					try {
						FacturaeManager.getInstance().saveAction(EnumOperationType.RectIn, id);
					} catch (DatabaseOperationException e) {
						// The user is not informed about action not saved event. An error log has been created previously.
					}
				}
			} else
				try {
					FacturaeManager.getInstance().saveAction(EnumOperationType.EditIn, id);
				} catch (DatabaseOperationException e) {
					// The user is not informed about action not saved event. An error log has been created previously.
				}
			
			in32WinInstance.setVisible(true);   
			if (in32WinInstance.getState() == JFrame.ICONIFIED)
				in32WinInstance.setState(JFrame.NORMAL);
		}
	}
    
    /** This method sets the form information about the invoice 
     * 
     * @argument inv .- Invoice source
     * @argument JPanel .- Panels containing fields to be filled (from GenerateInvoice32Window)
     */
    private static void setInvoiceData(Facturae inv, InvoiceGeneralPanel jPanelGeneral, PartyPanel jPanelIssuer, PartyPanel jPanelReceiver, InvoiceDetail32Panel jPanelDetail) {
    	
    	es.mityc.facturae32.Facturae inv32 = IntermediaryUtil32.getfacturae32(inv);
    	InvoiceType invoice = null;
    	es.mityc.facturae32.InvoiceType invoice32 = null;
    	if (inv32 == null || inv32.getInvoices() == null || inv32.getInvoices().getInvoice() == null) {
    		logger.error("Error setting the form information because the invoice is empty");
    		return;
    	}

    	if (decimalProps == null)
    		loadDecimalProperties();
    	
    	for (int i = 0; i < inv.getInvoices().getInvoice().size(); ++i) {
    		invoice = inv.getInvoices().getInvoice().get(i);
    		invoice32 = inv32.getInvoices().getInvoice().get(i);

    		/** InvoiceGeneralPanel **/ 
    		InvoiceHeaderType ih = invoice.getInvoiceHeader();    	
    		/** SeriesCode **/
    		if (jPanelGeneral.getJTextFieldSeries()!=null && ih != null && ih.getInvoiceSeriesCode() != null)
    			jPanelGeneral.getJTextFieldSeries().setText(ih.getInvoiceSeriesCode());
    		else if (jPanelGeneral.getJTextFieldSeries()!=null)
    			jPanelGeneral.getJTextFieldSeries().setText("");
    		/** Invoice Number **/
    		if (jPanelGeneral.getJTextFieldNumber()!=null && ih != null && ih.getInvoiceNumber() != null)
    			jPanelGeneral.getJTextFieldNumber().setText(ih.getInvoiceNumber());
    		else if (jPanelGeneral.getJTextFieldNumber()!=null)
    			jPanelGeneral.getJTextFieldNumber().setText("");

    		/** Invoice Issue Date **/ 
    		InvoiceIssueDataType iid = invoice.getInvoiceIssueData();
    		es.mityc.facturae32.InvoiceIssueDataType iid32 = invoice32.getInvoiceIssueData();
    		if (iid == null) { // Bad Formed Invoice
    			logger.error("Bad formed invoice - Issue data is missing");
    			return;
    		}
    		if (iid.getIssueDate() != null) 
    			jPanelGeneral.getJCalendarComboBoxExpDate().setCalendar(iid.getIssueDate().toGregorianCalendar());
    		/** Invoice Optional Date **/
    		if (iid32.getOperationDate() != null) {
    			jPanelGeneral.getJRadioButtonOpDate().setSelected(true);
    			jPanelGeneral.getJCalendarComboBoxOpDate().setVisible(true);
    			jPanelGeneral.getJCalendarComboBoxOpDate().setCalendar(iid32.getOperationDate().toGregorianCalendar());
    		}
    		/** Invoice Place **/
    		PlaceOfIssueType poi = iid.getPlaceOfIssue();
    		if (jPanelGeneral.getJTextFieldPlPCode() != null && poi != null && poi.getPostCode() != null)
    			jPanelGeneral.getJTextFieldPlPCode().setText(poi.getPostCode());
    		if (jPanelGeneral.getJTextFieldPlDesc() != null && poi != null && poi.getPlaceOfIssueDescription() != null)
    			jPanelGeneral.getJTextFieldPlDesc().setText(poi.getPlaceOfIssueDescription());
    		/** Invoicing Period **/
    		PeriodDates ip = iid.getInvoicingPeriod();
    		if (ip != null) {	
    			jPanelGeneral.getJRadioButtonPeriod().setSelected(true);
    			jPanelGeneral.getJCalendarComboBoxPeriodFrom().setVisible(true);
    			jPanelGeneral.getJCalendarComboBoxPeriodTo().setVisible(true);
    			if (ip.getStartDate()!=null)
    				jPanelGeneral.getJCalendarComboBoxPeriodFrom().setCalendar(ip.getStartDate().toGregorianCalendar());
    			if (ip.getEndDate()!=null)
    				jPanelGeneral.getJCalendarComboBoxPeriodTo().setCalendar(ip.getEndDate().toGregorianCalendar());
    		}

    		if (jPanelGeneral.getJCheckBoxIsReceived() != null && invoice.getState() != null)
    			jPanelGeneral.getJCheckBoxIsReceived().setSelected((invoice.getState() == InvoiceStatusType.R));
    	} 	

    	/** PartyPanel **/
    	PartiesType party = inv.getParties();
    	
    	/** Issuer **/    	
    	BusinessType issuer = party.getSellerParty();
    	TaxIdentificationType ti = issuer.getTaxIdentification();
    	JTextField jtfIssuer[] =jPanelIssuer.getJTextField();

    	/** Legal Entity **/
    	LegalEntityType issuerLE = issuer.getLegalEntity();

    	if (issuerLE != null) {
    		jPanelIssuer.getJRadioButtonLEn().setSelected(true);
    		
    		/** Legal Entity - Residence **/
    		if (jPanelIssuer.getJComboBoxResidenceType()!= null && ti!= null && ti.getResidenceTypeCode()!=null)
        		jPanelIssuer.getJComboBoxResidenceType().setSelectedIndex(ti.getResidenceTypeCode().ordinal());
    		
    		/** Legal Entity - Id **/
    		if (jtfIssuer[0] != null && issuer != null) {
    			String selItem = null;
    			String id = null;
    			for (int i = 0; i < jPanelIssuer.getJComboBoxItems().getItemCount(); i++) {
    				selItem = jPanelIssuer.getJComboBoxItems().getItemAt(i).toString();
    				id = issuerLE.getCorporateName() + " (" + issuerLE.getId() + ")";
    				if (selItem.equals(id)) {
    					jPanelIssuer.getJComboBoxItems().setSelectedIndex(i);
    					break;
    				}
    			}
    		}
    	}

    	/** Individual **/

    	IndividualType issuerInd = issuer.getIndividual();
    	if (issuer.isIndividualType()) {
    		jPanelIssuer.getJRadioButtonInd().setSelected(true);
    		jPanelIssuer.jRadioButtonGroupActionPerformed();
    		
    		/** Individual - Residence **/
    		if (jPanelIssuer.getJComboBoxResidenceType() != null && ti != null && ti.getResidenceTypeCode() != null)
        		jPanelIssuer.getJComboBoxResidenceType().setSelectedIndex(ti.getResidenceTypeCode().ordinal());
    		
    		/** Individual - Id **/
    		if(jtfIssuer[24] != null && issuer != null) {
    			String selItem = null;
    			String id = issuerInd.getFirstSurname();
    			if (issuerInd.getSecondSurname() != null)
    				id = id + " " + issuerInd.getSecondSurname();
    			id = id + ", " + issuerInd.getName() + " (" + issuerInd.getId() + ")";
    			for (int i = 0; i < jPanelIssuer.getJComboBoxItems().getItemCount(); i++) {
    				selItem = jPanelIssuer.getJComboBoxItems().getItemAt(i).toString();
    				if (id != null && id.equals(selItem)) {
    					jPanelIssuer.getJComboBoxItems().setSelectedIndex(i);
    					break;
    				}
    			}
    		}
    	}

    	/** PartyPanel - Receiver **/ 
    	BusinessType receiver = party.getBuyerParty();
    	JTextField jtfReceiver[] = jPanelReceiver.getJTextField();
    	TaxIdentificationType taxIdentification = receiver.getTaxIdentification();

    	/** Receiver - Legal Entity **/
    	LegalEntityType receiverLE= receiver.getLegalEntity();
    	
    	if (receiverLE != null) {
    		jPanelReceiver.getJRadioButtonLEn().setSelected(true);
    		
    		if(jPanelReceiver.getJComboBoxResidenceType()!= null && taxIdentification != null && taxIdentification.getResidenceTypeCode() != null)
        		jPanelReceiver.getJComboBoxResidenceType().setSelectedIndex(taxIdentification.getResidenceTypeCode().ordinal());

    		if(jtfReceiver[0] != null && receiver != null) {
    			String selItem = null;
    			String id = null;
    			for (int i = 0; i < jPanelReceiver.getJComboBoxItems().getItemCount(); i++) {
    				selItem = jPanelReceiver.getJComboBoxItems().getItemAt(i).toString();
    				id = receiverLE.getCorporateName() + " (" + receiverLE.getId() + ")";
    				if (selItem.equals(id)) {
    					jPanelReceiver.getJComboBoxItems().setSelectedIndex(i);
    					break;
    				}
    			}
    		}
    	}

    	/** Receiver - Individual **/
    	IndividualType receiverInd = receiver.getIndividual();

    	if (receiver.isIndividualType()) {
    		jPanelReceiver.getJRadioButtonInd().setSelected(true);		
    		jPanelReceiver.jRadioButtonGroupActionPerformed();

    		if(jPanelReceiver.getJComboBoxResidenceType()!= null && taxIdentification != null && taxIdentification.getResidenceTypeCode() != null)
    			jPanelReceiver.getJComboBoxResidenceType().setSelectedIndex(taxIdentification.getResidenceTypeCode().ordinal());

    		if(jtfReceiver[24] != null && receiver != null) {
    			String selItem = null;
    			String id = receiverInd.getFirstSurname();
    			if (receiverInd.getSecondSurname() != null)
    				id = id + " " + receiverInd.getSecondSurname();
    			id = id + ", " + receiverInd.getName() + " ("+receiverInd.getId() + ")";
    			for (int i = 0; i < jPanelReceiver.getJComboBoxItems().getItemCount(); i++) {
    				selItem = jPanelReceiver.getJComboBoxItems().getItemAt(i).toString();
    				if (id != null && id.equals(selItem)) {
    					jPanelReceiver.getJComboBoxItems().setSelectedIndex(i);
    					break;
    				}
    			}
    		}
    	}

    	/** InvoiceDetailPanel **/
    	DefaultTableModel tmDis = (DefaultTableModel)jPanelDetail.getJTableGlobDisc().getModel();
    	DefaultTableModel tmChar= (DefaultTableModel)jPanelDetail.getJTableGlobChar().getModel();
    	DefaultTableModel tmReim = (DefaultTableModel)jPanelDetail.getJTableGlobReim().getModel();

    	// Invoice information fields
    	InvoiceTotalsType it = null;
    	es.mityc.facturae32.InvoiceTotalsType it32 = null;
    	double total = 0.0;
    	DiscountsAndRebatesType generalDiscounts = null;
    	int rowsNumberDis = 0;
    	ChargesType generalSurcharges = null;
    	ChargeType charge = null;
    	int rowsNumberChar = 0;
    	DiscountType discount = null;
    	int rowsNumberReim = 0;
    	es.mityc.facturae32.ReimbursableExpenses reimbursableExps32 = null;
    	es.mityc.facturae32.ReimbursableExpensesType reimbursableExp = null;
    	es.mityc.facturae32.TaxIdentificationType reimTaxId = null;

    	for (int i = 0; i < inv.getInvoices().getInvoice().size(); ++i) {
    		invoice = inv.getInvoices().getInvoice().get(i);
    		it = invoice.getInvoiceTotals();
    		it32 = invoice32.getInvoiceTotals();
    		/** GeneralDiscounts **/
    		generalDiscounts = it.getGeneralDiscounts();
    		if (generalDiscounts != null) {
    			rowsNumberDis = generalDiscounts.getDiscount().size();
    			if (rowsNumberDis>0) {
    				for (int j = 0; j < rowsNumberDis; j++) {
    					discount = generalDiscounts.getDiscount().get(j);
    					Object[] row = { discount.getDiscountReason(), 
    								(discount.getDiscountRate()!= null)? discount.getDiscountRate(): "", 
    								discount.getDiscountAmount() 
    							};
    					tmDis.addRow(row);
    					total = total + discount.getDiscountAmount();
    				}
    			}
    		}
    		// Total discount
    		jPanelDetail.getJTextFieldTotalDisc().setText(String.valueOf(total));

    		/** GeneralSurcharges **/
    		total = 0.0;

    		generalSurcharges = it.getGeneralSurcharges();
    		if (generalSurcharges != null) {
    			rowsNumberChar = generalSurcharges.getCharge().size();
    			if (rowsNumberChar > 0) {
    				for (int j = 0; j < rowsNumberChar; j++) {
    					charge = generalSurcharges.getCharge().get(j);
    					Object[] row = { charge.getChargeReason(), 
    								(charge.getChargeRate() != null)? charge.getChargeRate(): "", 
    								charge.getChargeAmount() 
    							};
    					tmChar.addRow(row);
    					total = total + charge.getChargeAmount();
    				}
    				it.setGeneralSurcharges(generalSurcharges);	
    			}
    		}
    		// Total Charges
    		jPanelDetail.getJTextFieldTotalChar().setText(String.valueOf(total));

    		/** Reimbursable expenses **/
    		total = 0.0;   		
    		
    		reimbursableExps32 = it32.getReimbursableExpenses();
    		if (reimbursableExps32 != null) {
    			rowsNumberReim = reimbursableExps32.getReimbursableExpenses().size();
    			if (rowsNumberReim > 0) {
    				for (int j = 0; j < rowsNumberReim; j++) {
    					Object[] row = new Object[7];
    					reimbursableExp = reimbursableExps32.getReimbursableExpenses().get(j);
    					// Seller
    					reimTaxId = reimbursableExp.getReimbursableExpensesSellerParty();

    					if (reimTaxId != null) {
    						if (reimTaxId.getPersonTypeCode() != null) {
    							int index = reimTaxId.getPersonTypeCode().ordinal();
    							if (index == 0)
    								row[0] = Constants.LANG.getString("Individual");
    							else
    								row[0] = Constants.LANG.getString("LegalEntity");
    						}
    						if (reimTaxId.getResidenceTypeCode() != null) {
    							int index = reimTaxId.getResidenceTypeCode().ordinal();
    							if (index == 0)
    								row[1] = Constants.LANG.getString("Foreigner");
    							else if (index == 1)
    								row[1] = Constants.LANG.getString("Resident");
    							else
    								row[1] = Constants.LANG.getString("ResidentEU");
    						}
    						row[2] = reimTaxId.getTaxIdentificationNumber();
    					}

    					// Buyer
    					reimTaxId = reimbursableExp.getReimbursableExpensesBuyerParty();
    					if (reimTaxId != null) {
    						if (reimTaxId.getPersonTypeCode() != null) {
    							int index = reimTaxId.getPersonTypeCode().ordinal();
    							if (index == 0)
    								row[3] = Constants.LANG.getString("Individual");
    							else
    								row[3] = Constants.LANG.getString("LegalEntity");
    						}
    						if (reimTaxId.getResidenceTypeCode() != null) {
    							int index = reimTaxId.getResidenceTypeCode().ordinal();
    							if (index == 0)
    								row[4] = Constants.LANG.getString("Foreigner");
    							else if (index == 1)
    								row[4] = Constants.LANG.getString("Resident");
    							else
    								row[4] = Constants.LANG.getString("ResidentEU");
    						}
    						row[5] = reimTaxId.getTaxIdentificationNumber();
    					}
    					
	        			// Amount
	        			row[6] = String.valueOf(reimbursableExp.getReimbursableExpensesAmount());
	        			tmReim.addRow(row);
	        			
	    				total = total + reimbursableExp.getReimbursableExpensesAmount();
    				}
    				
    			}
    		}
    	}
    	// TotalReimbursableExpenses
    	jPanelDetail.getJTextFieldTotalReim().setText(String.valueOf(total));

    	/** Total values **/
    	JTextField totalResults[] = jPanelDetail.getJTextFieldResults();
    	// TotalGrossAmount
    	if (totalResults[0] != null)
    		totalResults[0].setText(String.valueOf(it.getTotalGrossAmount()));

    	// TotalDiscounts	
    	totalResults[1].setText(String.valueOf(jPanelDetail.getJTextFieldTotalDisc().getText()));
    	
    	// TotalSurcharges
    	totalResults[2].setText(String.valueOf(jPanelDetail.getJTextFieldTotalChar().getText()));

    	// TotalGrossAmountBeforeTaxes
    	if (totalResults[3] != null)
    		totalResults[3].setText(String.valueOf(it.getTotalGrossAmountBeforeTaxes()));

    	// TotalTaxOutputs
    	if (totalResults[4] != null)
    		totalResults[4].setText(String.valueOf(it.getTotalTaxOutputs()));

    	// TotalTaxesWithheld
    	if (totalResults[5] != null)
    		totalResults[5].setText(String.valueOf(it.getTotalTaxesWithheld()));

    	// InvoiceTotal & TotalOutstandingAmount
    	if (totalResults[6] != null) {
    		totalResults[6].setText(String.valueOf(it.getInvoiceTotal()));
    	}		
    	
    	// TotalReimbursables
    	totalResults[8].setText(String.valueOf(jPanelDetail.getJTextFieldTotalReim().getText()));
    	
    	// TotalFinancialExpenses
    	if (totalResults[9] != null && it32.getTotalFinancialExpenses() != null)
    		totalResults[9].setText(String.valueOf(it32.getTotalFinancialExpenses()));

    	// TotalExecutableAmount
    	if (totalResults[10] != null)
    		totalResults[10].setText(String.valueOf(it.getTotalExecutableAmount()));

    	/** AmountsWithheld **/
    	AmountsWithheldType awt = it.getAmountsWithheld();
    	if (awt != null) {
    		if (totalResults[7] != null)
    			totalResults[7].setText(String.valueOf(awt.getWithholdingAmount()));	   
    		if (jPanelDetail.getJTextFieldWithItem() != null && awt.getWithholdingReason() != null)
    			jPanelDetail.getJTextFieldWithItem().setText(awt.getWithholdingReason());
    		if (jPanelDetail.getJTextFieldWithPerc() != null && awt.getWithholdingRate() != null)
    			jPanelDetail.getJTextFieldWithPerc().setText(String.valueOf(awt.getWithholdingRate()));
    		if (jPanelDetail.getJTextFieldWithAmou() != null)
    			jPanelDetail.getJTextFieldWithAmou().setText(String.valueOf(awt.getWithholdingAmount()));
    	}

    	/** LegalLiterals **/
    	LegalLiteralsType ll = invoice.getLegalLiterals();
    	if (ll != null && ll.getLegalReference().size() > 0) {
    		// Inerza - Añadir texto legales formato libre. (Mantis #60978)
    		for(int i = 0; i < ll.getLegalReference().size(); i++) {
    			String value = ll.getLegalReference().get(i);
    			jPanelDetail.getLiteralsListDestModel().addElement(value);
    		}
    	}

    	/** AttachedDocuments **/    	
    	AttachmentType attachment = null;
    	DefaultTableModel tmAttach= (DefaultTableModel)jPanelDetail.getJTableGlobAttc().getModel();
    	AdditionalDataType adt = invoice.getAdditionalData();
    	if (adt != null) {
    		if (jPanelDetail.getJTextAreaXMLExtAddData().getText() != null && adt.getExtensions() != null){
        		ExtensionsType extension = adt.getExtensions();
        		if (extension != null) {
        			OutputStream xml = new ByteArrayOutputStream();
        			for (int j = 0; j < extension.getAny().size(); ++j) {
        				Element element = (Element) extension.getAny().get(j);
        		        try {
        		        	Transformer xformer = TransformerFactory.newInstance().newTransformer();
        		            xformer.transform(new DOMSource(element), new StreamResult(xml));    		            
        		        } catch (TransformerConfigurationException e) {
        		        	logger.error("The invoice could not be reconstructed: " + e.getMessage(), e);
        		        	Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
    					} catch (TransformerFactoryConfigurationError e) {
    						logger.error("The invoice could not be reconstructed: " + e.getMessage(), e);
        		        	Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
    					} catch (TransformerException e) {
    						logger.error("The invoice could not be reconstructed: " + e.getMessage(), e);
        		        	Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
    					} 
        			}
        			jPanelDetail.getJTextAreaXMLExtAddData().setText(xml.toString());
        		}
        	}
    	}

    	String facId = invoice.getInvoiceHeader().getInvoiceSeriesCode() + invoice.getInvoiceHeader().getInvoiceNumber();
    	try {
    		SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT * FROM ATTACHMENT WHERE INVOICE = '" + facId + "'");
    		List<?> ls = s.list();
    		if (ls != null && ls.size() > 0 && ls.get(0) != null) {
    			for (int i = 0; i < ls.size(); i++) {
    				Object[] value = (Object[])ls.get(i);

    				if ((Integer)value[5] == 1) {
    					File attF = new File((String)value[4]);
    					if (!attF.exists()) {
    						AttachedDocumentsType ads = adt.getRelatedDocuments();
    						for (int j = 0; j < ads.getAttachment().size(); j++) {
    							attachment = ads.getAttachment().get(j);
    							if (attachment.getAttachmentDescription().equals(value[3])) { // Unique description
    								String data = attachment.getAttachmentData();
    								try {				    
    									attF = File.createTempFile(attF.getName().substring(0, attF.getName().length() - 4), attF.getName().substring(attF.getName().length() - 4)); 
    									attF.deleteOnExit();
    									FileOutputStream out = new FileOutputStream(attF);
    									out.write(data.getBytes());
    									out.close();
    								} catch (IOException e) {
    									logger.error("Error reading the attachments: " + e.getMessage(), e);
    									Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
    								}

    								value[4] = attF.getCanonicalPath();
    							}
    						}
    					}
    				}

    				Object[] row = { value[2], 
    						value[4], 
    						value[3],
    						(Integer)value[5] == 0? Constants.LANG.getString("No"): Constants.LANG.getString("Yes") // If it appears in data object, it is included in XML file
    				};
    				tmAttach.addRow(row);
    			}
    		}
    	} catch (Exception e) {
    		logger.error("An error occurred when loading the database information: " + e.getMessage(), e);
    		Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
    	}

    	/** PaymentDetails **/
    	es.mityc.facturae32.InstallmentsType installments = invoice32.getPaymentDetails();
    	es.mityc.facturae32.InstallmentType installment = null;

    	if (installments != null) {
    		for (int i = 0; i < installments.getInstallment().size(); ++i) {
    			installment = installments.getInstallment().get(i);

    			if (jPanelDetail.getJTextFieldPaymentAmo() != null) {
    				jPanelDetail.getJTextFieldPaymentAmo().setText(String.valueOf(installment.getInstallmentAmount()));
    				jPanelDetail.getJCalendarComboBoxPaymDate().setCalendar(installment.getInstallmentDueDate().toGregorianCalendar());			
    				if (jPanelDetail.getJTextFieldPaymentRefIssuer() != null && installment.getPaymentReconciliationReference() != null)
    					jPanelDetail.getJTextFieldPaymentRefIssuer().setText(installment.getPaymentReconciliationReference());
    				if (jPanelDetail.getJTextFieldPaymentRefReceiver() != null && installment.getDebitReconciliationReference() != null)
    					jPanelDetail.getJTextFieldPaymentRefReceiver().setText(installment.getDebitReconciliationReference());
    				if (jPanelDetail.getJComboBoxPayMean() != null)
    					jPanelDetail.getJComboBoxPayMean().setSelectedIndex(Integer.valueOf(installment.getPaymentMeans()) - 1);	
    				if (jPanelDetail.getJTextFieldPayCIBAN() != null && installment.getAccountToBeCredited() != null) {
    					es.mityc.facturae32.AccountType at = installment.getAccountToBeCredited();
    					
    					for (int j = 0; j < jPanelDetail.getJComboBoxPayAccountC().getItemCount(); ++j) {
    						if (((String)jPanelDetail.getJComboBoxPayAccountC().getItemAt(j)).contains(at.getIBAN()))
    							jPanelDetail.getJComboBoxPayAccountC().setSelectedIndex(j);
    					}
    					
    					jPanelDetail.getJTextFieldPayCIBAN().setText(at.getIBAN());

    					if (jPanelDetail.getJTextFieldPayCBank() != null && at.getBankCode() != null)
    						jPanelDetail.getJTextFieldPayCBank().setText(at.getBankCode());

    					if (jPanelDetail.getJTextFieldPayCBran() != null && at.getBranchCode() != null)
    						jPanelDetail.getJTextFieldPayCBran().setText(at.getBranchCode());

    					if (jPanelDetail.getJTextFieldPayCAddr() != null && jPanelDetail.getJTextFieldPayCAddr().getText() != null /* && !"".equals(jPanelDetail.getJTextFieldPayCAddr().getText().trim()) */) {
    						if (at.getBranchInSpainAddress() != null) {
    							es.mityc.facturae32.AddressType addr = at.getBranchInSpainAddress();
    							jPanelDetail.getJComboBoxPayCCoun().setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(addr.getCountryCode().ordinal())));
    							jPanelDetail.getJTextFieldPayCAddr().setText(addr.getAddress());
    							jPanelDetail.getJTextFieldPayCPCod().setText(addr.getPostCode());
    							jPanelDetail.getJTextFieldPayCCity().setText(addr.getTown());
    							jPanelDetail.getJTextFieldPayCProv().setText(addr.getProvince());
    						} else if(at.getOverseasBranchAddress() != null){
    							es.mityc.facturae32.OverseasAddressType oaddr = at.getOverseasBranchAddress();
    							jPanelDetail.getJComboBoxPayCCoun().setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(oaddr.getCountryCode().ordinal())));
    							jPanelDetail.getJTextFieldPayCAddr().setText(oaddr.getAddress());
    							jPanelDetail.getJTextFieldPayCProv().setText(oaddr.getProvince());
    							int k = oaddr.getPostCodeAndTown().indexOf(" ");
    							jPanelDetail.getJTextFieldPayCPCod().setText(oaddr.getPostCodeAndTown().substring(0,k));
    							jPanelDetail.getJTextFieldPayCCity().setText(oaddr.getPostCodeAndTown().substring(k+1));	
    						}	
    					}
    				}

    				if (jPanelDetail.getJTextFieldPayDIBAN() != null && installment.getAccountToBeDebited() != null) {
    					es.mityc.facturae32.AccountType at = installment.getAccountToBeDebited();
    					
    					for (int j = 0; j < jPanelDetail.getJComboBoxPayAccountD().getItemCount(); ++j ) {
    						if (((String)jPanelDetail.getJComboBoxPayAccountD().getItemAt(j)).contains(at.getIBAN()))
    							jPanelDetail.getJComboBoxPayAccountD().setSelectedIndex(j);
    					}
    					
    					jPanelDetail.getJTextFieldPayDIBAN().setText(at.getIBAN());

    					if (jPanelDetail.getJTextFieldPayDBank() != null && at.getBankCode() != null)
    						jPanelDetail.getJTextFieldPayDBank().setText(at.getBankCode());

    					if (jPanelDetail.getJTextFieldPayDBran() != null && at.getBranchCode() != null)
    						jPanelDetail.getJTextFieldPayDBran().setText(at.getBranchCode());

    					if (jPanelDetail.getJTextFieldPayDAddr() != null && jPanelDetail.getJTextFieldPayDAddr().getText() != null /* && !"".equals(jPanelDetail.getJTextFieldPayDAddr().getText().trim()) */) {
    						if (at.getBranchInSpainAddress() != null) {
    							es.mityc.facturae32.AddressType addr = at.getBranchInSpainAddress();
    							jPanelDetail.getJComboBoxPayDCoun().setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(addr.getCountryCode().ordinal())));
    							jPanelDetail.getJTextFieldPayDAddr().setText(addr.getAddress());
    							jPanelDetail.getJTextFieldPayDPCod().setText(addr.getPostCode());
    							jPanelDetail.getJTextFieldPayDCity().setText(addr.getTown());
    							jPanelDetail.getJTextFieldPayDProv().setText(addr.getProvince());
    						} else if(at.getOverseasBranchAddress() != null) {
    							es.mityc.facturae32.OverseasAddressType oaddr = at.getOverseasBranchAddress();
    							jPanelDetail.getJComboBoxPayDCoun().setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(oaddr.getCountryCode().ordinal())));
    							jPanelDetail.getJTextFieldPayDAddr().setText(oaddr.getAddress());
    							jPanelDetail.getJTextFieldPayDProv().setText(oaddr.getProvince());
    							int k = oaddr.getPostCodeAndTown().indexOf(" ");
    							jPanelDetail.getJTextFieldPayDPCod().setText(oaddr.getPostCodeAndTown().substring(0,k));
    							jPanelDetail.getJTextFieldPayDCity().setText(oaddr.getPostCodeAndTown().substring(k+1));	
    						}
    					}
    				}
    			}
    		}
    	}
        
        // ext-csaamar - 20150409 - Añadir endosatario.
        if(inv32.getFileHeader() != null && inv32.getFileHeader().getFactoringAssignmentData() != null) {
            es.mityc.facturae32.FactoringAssignmentDataType fadt = inv32.getFileHeader().getFactoringAssignmentData();
            
            if(jPanelDetail.getJTextFieldPayFactIBAN() != null) {
            	installments = fadt.getPaymentDetails();
            	for(int i = 0; i < installments.getInstallment().size(); i++) {
            		installment = installments.getInstallment().get(i);
            		jPanelDetail.getJCalendarComboBoxPaymDate().setCalendar(installment.getInstallmentDueDate().toGregorianCalendar());
    				jPanelDetail.getJTextFieldPaymentAmo().setText(String.valueOf(installment.getInstallmentAmount()));
            		if (jPanelDetail.getJTextFieldPaymentRefIssuer() != null && installment.getPaymentReconciliationReference() != null)
            			jPanelDetail.getJTextFieldPaymentRefIssuer().setText(installment.getPaymentReconciliationReference());
            		if (jPanelDetail.getJTextFieldPaymentRefReceiver() != null && installment.getDebitReconciliationReference() != null)
            			jPanelDetail.getJTextFieldPaymentRefReceiver().setText(installment.getDebitReconciliationReference());
            		if (jPanelDetail.getJComboBoxPayMean() != null)
            			jPanelDetail.getJComboBoxPayMean().setSelectedIndex(Integer.valueOf(installment.getPaymentMeans()) - 1);	

            		es.mityc.facturae32.AccountType at = null;
            		if(installment.getAccountToBeCredited() != null) {
            			at = installment.getAccountToBeCredited();
            		} else if (installment.getAccountToBeDebited() != null) {
            			at = installment.getAccountToBeDebited();
            		}
            		
            		if(at != null) {
            			for (int j = 0; j < jPanelDetail.getJComboBoxPayAccountFactoring().getItemCount(); ++j) {
            				if (((String) jPanelDetail.getJComboBoxPayAccountFactoring().getItemAt(j)).contains(at.getIBAN())) {
            					jPanelDetail.getJComboBoxPayAccountFactoring().setSelectedIndex(j);
            				}
            			}
            		}
            	}
            }
            
            if(jPanelDetail.getJTextFieldPayFactClausula() != null && fadt.getFactoringAssignmentClauses() != null) {
            	jPanelDetail.getJTextFieldPayFactClausula().setText(fadt.getFactoringAssignmentClauses());
            }
            
            if (fadt.getAssignee() != null && fadt.getAssignee().getLegalEntity() != null) {
            	es.mityc.facturae32.LegalEntityType let = fadt.getAssignee().getLegalEntity();
            	if (jPanelDetail.getJTextFieldPayFactSociedad() != null && let.getCorporateName() != null) {
	            	jPanelDetail.getJTextFieldPayFactSociedad().setText(let.getCorporateName());
	            }
	            
	            if (let.getAddressInSpain() != null) {
	                es.mityc.facturae32.AddressType addr = let.getAddressInSpain();
	                jPanelDetail.getJComboBoxPayFactCoun().setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(addr.getCountryCode().ordinal())));
	                jPanelDetail.getJTextFieldPayFactAddr().setText(addr.getAddress());
	                jPanelDetail.getJTextFieldPayFactPCod().setText(addr.getPostCode());
	                jPanelDetail.getJTextFieldPayFactCity().setText(addr.getTown());
	                jPanelDetail.getJTextFieldPayFactProv().setText(addr.getProvince());
	            } else if (let.getOverseasAddress() != null) {
	                es.mityc.facturae32.OverseasAddressType oaddr = let.getOverseasAddress();
	                jPanelDetail.getJComboBoxPayFactCoun().setSelectedIndex(ComboUtil.calculateComboIndex(Constants.COUNTRIES31, String.valueOf(oaddr.getCountryCode().ordinal())));
	                jPanelDetail.getJTextFieldPayFactAddr().setText(oaddr.getAddress());
	                jPanelDetail.getJTextFieldPayFactProv().setText(oaddr.getProvince());
	                int k = oaddr.getPostCodeAndTown().indexOf(" ");
	                jPanelDetail.getJTextFieldPayFactPCod().setText(oaddr.getPostCodeAndTown().substring(0, k));
	                jPanelDetail.getJTextFieldPayFactCity().setText(oaddr.getPostCodeAndTown().substring(k + 1));
	            }
            }
        }
        // ext-csaamar - 20150409 - Añadir endosatario.

    	/** Invoice lines **/
    	jPanelDetail.setItems(invoice.getItems());

    	int itemsCount = invoice32.getItems().getInvoiceLine().size();
    	for (int i = 0; i < itemsCount; i++) {
    		es.mityc.facturae32.InvoiceLineType ilt = invoice32.getItems().getInvoiceLine().get(i);
    		Object[] ob = new Object[] {"","","","","","","",""};
    		// Description
    		if (ilt.getItemDescription() != null) 
    			ob[0] = ilt.getItemDescription();
    		// Quantity
    		ob[1] = ilt.getQuantity();
    		// Unit of Measure
    		if (ilt.getUnitOfMeasure() != null && !ilt.getUnitOfMeasure().trim().equals(""))
    			ob[2] = Constants.APP_PROP.getProperty("unitsOfMeasure"+codeVersion).split(";")[Integer.parseInt(ilt.getUnitOfMeasure())-1];
    		// Total Cost	
    		ob[3] = ilt.getTotalCost();
    		// Taxable Base
    		ob[4] = ilt.getGrossAmount();
    		// Outputs taxes
    		double amountTax = 0.00;
    		if (ilt.getTaxesOutputs() != null && ilt.getTaxesOutputs().getTax() != null) {	
    			int numTaxes = ilt.getTaxesOutputs().getTax().size();
    			String typeTax = "";
    			boolean different = false;			
    			for (int j = 0 ; j < numTaxes ; j++){
    				es.mityc.facturae32.InvoiceLineType.TaxesOutputs.Tax t = ilt.getTaxesOutputs().getTax().get(j);
    				if (!different){
    					if (typeTax.equals(""))
    						typeTax = t.getTaxTypeCode();
    					else if (!typeTax.equals(t.getTaxTypeCode())){
    						typeTax = Constants.LANG.getString("DifferentValues");
    						different = true;
    					}
    				}

    				if (t.getSpecialTaxAmount() != null && t.getSpecialTaxAmount().getTotalAmount() != 0.00) {
    					amountTax = amountTax + t.getSpecialTaxAmount().getTotalAmount();
    					// Special TaxableBase is Used
    					jPanelDetail.setItemCountOutputTax();
    				}
    				else{
    					if (t.getTaxAmount() != null)
    						amountTax = amountTax + t.getTaxAmount().getTotalAmount();
    				}

    				if (t.getEquivalenceSurchargeAmount() != null && t.getEquivalenceSurchargeAmount().getTotalAmount() != 0)
    					amountTax = amountTax + t.getEquivalenceSurchargeAmount().getTotalAmount();
    			}
    			if (numTaxes > 0) {
    				if (!"".equals(typeTax) && !Constants.LANG.getString("DifferentValues").equals(typeTax))
    					typeTax = Constants.LANG.getString(Constants.APP_PROP.getProperty("taxesType"+codeVersion).split(";")[Integer.valueOf(typeTax)-1]).split(" ")[0];
    				if (decimalProps != null && decimalProps.getProperty("item_tax_Out") != null)
    					ob[5]=typeTax + " - " + String.valueOf(DoubleUtil.round(amountTax, Integer.parseInt(decimalProps.getProperty("item_tax_Out"))) );
    				else
    					ob[5]=typeTax + " - " + String.valueOf(DoubleUtil.round(amountTax, 2) );
    			}
    			else
    				ob[5] = "";
    		}

    		// Withheld taxes
    		double amountTaxW = 0.00;
    		if (ilt.getTaxesWithheld() != null && ilt.getTaxesWithheld().getTax() != null) {	
    			int numTaxesW = ilt.getTaxesWithheld().getTax().size();
    			String typeTaxW = "";
    			boolean differentW = false;
    			for (int j = 0 ; j < numTaxesW ; j++) {
    				es.mityc.facturae32.TaxType t = ilt.getTaxesWithheld().getTax().get(j);
        			if (!differentW) {
        				if (typeTaxW.equals(""))
        					typeTaxW = t.getTaxTypeCode();
        				else if (!typeTaxW.equals(t.getTaxTypeCode())) {
        					typeTaxW = Constants.LANG.getString("DifferentValues");
        					differentW = true;
        				}
        			}
        			amountTaxW = amountTaxW + t.getTaxAmount().getTotalAmount();
        		}
        		
        		if (numTaxesW > 0) {
        			if (typeTaxW != null && !"".equals(typeTaxW) && !Constants.LANG.getString("DifferentValues").equals(typeTaxW))
        				typeTaxW = Constants.LANG.getString(Constants.APP_PROP.getProperty("taxesType"+codeVersion).split(";")[Integer.valueOf(typeTaxW)-1]).split(" ")[0];
        			if (decimalProps != null && decimalProps.getProperty("item_tax_With") != null)
        				ob[6]=typeTaxW + " - " + String.valueOf(DoubleUtil.round(amountTaxW, Integer.parseInt(decimalProps.getProperty("item_tax_With"))) );
        			else
        				ob[6]=typeTaxW + " - " + String.valueOf(DoubleUtil.round(amountTaxW, 2) );
        		}
        		else
        			ob[6] = "";
    		}

    		// Total cost
    		if (decimalProps != null && decimalProps.getProperty("item_total_cost") != null)
    			ob[7] = DoubleUtil.round(ilt.getGrossAmount() + amountTax - amountTaxW,  Integer.parseInt(decimalProps.getProperty("item_total_cost")) );
    		else
    			ob[7] = DoubleUtil.round(ilt.getGrossAmount() + amountTax - amountTaxW,  2);

    		// Item add
    		((NoEdiTableModel)jPanelDetail.getJTableItems().getModel()).addRow(ob);
    	}

    	/** Facturae Extension **/
    	if (jPanelDetail.getJTextAreaTOTXMLExtAddData().getText() != null) {
    		ExtensionsType extension = inv.getExtensions();
    		if (extension != null) {
    			OutputStream xml = new ByteArrayOutputStream();
    			for (int j = 0; j < extension.getAny().size(); ++j) {
    				Element element = (Element) extension.getAny().get(j);
    		        try {
    		        	Transformer xformer = TransformerFactory.newInstance().newTransformer();
    		            xformer.transform(new DOMSource(element), new StreamResult(xml));    		            
    		        } catch (TransformerConfigurationException e) {
    		        	logger.error("The invoice could not be reconstructed: " + e.getMessage(), e);
    		        	Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
					} catch (TransformerFactoryConfigurationError e) {
						logger.error("The invoice could not be reconstructed: " + e.getMessage(), e);
    		        	Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
					} catch (TransformerException e) {
						logger.error("The invoice could not be reconstructed: " + e.getMessage(), e);
    		        	Constants.DIALOG.showErrorChargeInvoice(e.getMessage());
					} 
    			}
    			jPanelDetail.getJTextAreaTOTXMLExtAddData().setText(xml.toString());
    		}
    	}
    }
    
    /**
     * Sets form fields Disabled for visualization.
     * @argument JPanel.- Panels containing fields to be disabled
     */
    private static void setFieldsDisabled(InvoiceGeneralPanel jPanelGeneral, PartyPanel jPanelIssuer, PartyPanel jPanelReceiver, InvoiceDetail32Panel jPanelDetail) {

    	/** InvoiceGeneralPanel **/ 
    	jPanelGeneral.getJRadioButtonOpDate().setEnabled(false);
    	jPanelGeneral.getJCalendarComboBoxExpDate().setEnabled(false);
    	jPanelGeneral.getJCalendarComboBoxOpDate().setEnabled(false);
    	jPanelGeneral.getJTextFieldPlPCode().setEnabled(false);
    	jPanelGeneral.getJTextFieldPlDesc().setEnabled(false);
    	jPanelGeneral.getJRadioButtonPeriod().setEnabled(false);
    	jPanelGeneral.getJCalendarComboBoxPeriodFrom().setEnabled(false);
    	jPanelGeneral.getJCalendarComboBoxPeriodTo().setEnabled(false);
    	jPanelGeneral.getJCheckBoxIsReceived().setEnabled(false);	

    	/** PartyPanel - Issuer **/    	
    	jPanelIssuer.getJRadioButtonLEn().setEnabled(false);
    	jPanelIssuer.getJComboBoxResidenceType().setEnabled(false);
    	jPanelIssuer.getJComboBoxItems().setEnabled(false);
    	jPanelIssuer.getJRadioButtonInd().setEnabled(false);
    	jPanelIssuer.getJComboBoxIndCountry().setEnabled(false);
    	jPanelIssuer.getJComboBoxLEnCountry().setEnabled(false);
    	jPanelIssuer.getJButtonNewUser().setEnabled(false);
    	jPanelIssuer.getJButtonDelUser().setEnabled(false);
    	jPanelIssuer.getJButtonSave().setEnabled(false);
    	jPanelIssuer.getJButtonNewUser().setDisabledIcon(new ImageIcon(jPanelIssuer.getClass().getResource("/images/button_new_user_disabled.jpg")));
    	jPanelIssuer.getJButtonDelUser().setDisabledIcon(new ImageIcon(jPanelIssuer.getClass().getResource("/images/button_delete_user_disabled.jpg")));
    	jPanelIssuer.getJButtonEditUser().setDisabledIcon(new ImageIcon(jPanelIssuer.getClass().getResource("/images/button_edit_user_disabled.jpg")));
    	jPanelIssuer.getJButtonSave().setDisabledIcon(new ImageIcon(jPanelIssuer.getClass().getResource("/images/button_save_disabled.jpg")));
    	
    	JTextField jtfIssuer[] =jPanelIssuer.getJTextField();
    	jtfIssuer[0].setEnabled(false);
    	jtfIssuer[1].setEnabled(false);
    	jtfIssuer[2].setEnabled(false);
    	jtfIssuer[3].setEnabled(false);
    	jtfIssuer[4].setEnabled(false);
    	jtfIssuer[5].setEnabled(false);
    	jtfIssuer[6].setEnabled(false);
    	jtfIssuer[7].setEnabled(false);

    	jtfIssuer[9].setEnabled(false);
    	jtfIssuer[10].setEnabled(false);
    	jtfIssuer[11].setEnabled(false);
    	jtfIssuer[12].setEnabled(false);
    	jtfIssuer[13].setEnabled(false);
    	jtfIssuer[14].setEnabled(false);
    	jtfIssuer[15].setEnabled(false);
    	jtfIssuer[16].setEnabled(false);
    	jtfIssuer[17].setEnabled(false);
    	jtfIssuer[18].setEnabled(false);
    	jtfIssuer[19].setEnabled(false);
    	jtfIssuer[20].setEnabled(false);
    	jtfIssuer[21].setEnabled(false);
    	jtfIssuer[22].setEnabled(false);
    	jtfIssuer[23].setEnabled(false);
    	jtfIssuer[24].setEnabled(false);
    	jtfIssuer[25].setEnabled(false);
    	jtfIssuer[26].setEnabled(false);
    	jtfIssuer[27].setEnabled(false);		
    	jtfIssuer[28].setEnabled(false);
    	jtfIssuer[29].setEnabled(false);
    	jtfIssuer[30].setEnabled(false);
    	jtfIssuer[31].setEnabled(false);
    	jtfIssuer[32].setEnabled(false);

    	jtfIssuer[34].setEnabled(false);
    	jtfIssuer[35].setEnabled(false);
    	jtfIssuer[36].setEnabled(false);
    	jtfIssuer[37].setEnabled(false);
    	jtfIssuer[38].setEnabled(false);
    	jtfIssuer[39].setEnabled(false);
    	jtfIssuer[40].setEnabled(false);
    	jtfIssuer[41].setEnabled(false);

    	/** PartyPanel - Receiver **/ 
    	jPanelReceiver.getJRadioButtonLEn().setEnabled(false);
    	jPanelReceiver.getJComboBoxResidenceType().setEnabled(false);
    	jPanelReceiver.getJComboBoxItems().setEnabled(false);
       	jPanelReceiver.getJComboBoxLEnCountry().setEnabled(false);
       	jPanelReceiver.getJComboBoxIndCountry().setEnabled(false);
    	jPanelReceiver.getJRadioButtonInd().setEnabled(false);
    	jPanelReceiver.getJButtonNewUser().setEnabled(false);
    	jPanelReceiver.getJButtonDelUser().setEnabled(false);
    	jPanelReceiver.getJButtonSave().setEnabled(false);
    	jPanelReceiver.getJButtonNewUser().setDisabledIcon(new ImageIcon(jPanelReceiver.getClass().getResource("/images/button_new_user_disabled.jpg")));
    	jPanelReceiver.getJButtonDelUser().setDisabledIcon(new ImageIcon(jPanelReceiver.getClass().getResource("/images/button_delete_user_disabled.jpg")));
    	jPanelReceiver.getJButtonEditUser().setDisabledIcon(new ImageIcon(jPanelReceiver.getClass().getResource("/images/button_edit_user_disabled.jpg")));
    	jPanelReceiver.getJButtonSave().setDisabledIcon(new ImageIcon(jPanelReceiver.getClass().getResource("/images/button_save_disabled.jpg")));
       	
    	JTextField jtfReceiver[] = jPanelReceiver.getJTextField();
    	jtfReceiver[0].setEnabled(false);
    	jtfReceiver[1].setEnabled(false);
    	jtfReceiver[2].setEnabled(false);
    	jtfReceiver[3].setEnabled(false);
    	jtfReceiver[4].setEnabled(false);
    	jtfReceiver[5].setEnabled(false);
    	jtfReceiver[6].setEnabled(false);
    	jtfReceiver[7].setEnabled(false);

    	jtfReceiver[9].setEnabled(false);
    	jtfReceiver[10].setEnabled(false);
    	jtfReceiver[11].setEnabled(false);
    	jtfReceiver[12].setEnabled(false);
    	jtfReceiver[13].setEnabled(false);
    	jtfReceiver[14].setEnabled(false);
    	jtfReceiver[15].setEnabled(false);
    	jtfReceiver[16].setEnabled(false);
    	jtfReceiver[17].setEnabled(false);
    	jtfReceiver[18].setEnabled(false);
    	jtfReceiver[19].setEnabled(false);
    	jtfReceiver[20].setEnabled(false);
    	jtfReceiver[21].setEnabled(false);
    	jtfReceiver[22].setEnabled(false);
    	jtfReceiver[23].setEnabled(false);
    	jtfReceiver[24].setEnabled(false);
    	jtfReceiver[25].setEnabled(false);
    	jtfReceiver[26].setEnabled(false);
    	jtfReceiver[27].setEnabled(false);
    	jtfReceiver[28].setEnabled(false);
    	jtfReceiver[29].setEnabled(false);
    	jtfReceiver[30].setEnabled(false);
    	jtfReceiver[31].setEnabled(false);
    	jtfReceiver[32].setEnabled(false);

    	jtfReceiver[34].setEnabled(false);
    	jtfReceiver[35].setEnabled(false);
    	jtfReceiver[36].setEnabled(false);
    	jtfReceiver[37].setEnabled(false);
    	jtfReceiver[38].setEnabled(false);
    	jtfReceiver[39].setEnabled(false);
    	jtfReceiver[40].setEnabled(false);
    	jtfReceiver[41].setEnabled(false);

    	/** InvoiceDetailPanel **/
    	jPanelDetail.getJButtonItemAdd().setEnabled(false);
    	jPanelDetail.getJButtonItemDel().setEnabled(false);
    	jPanelDetail.getJButtonItemAdd().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
    	jPanelDetail.getJButtonItemDel().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
    	
    	jPanelDetail.getJButtonDiscAdd().setEnabled(false);
    	jPanelDetail.getJButtonDiscDel().setEnabled(false);
    	jPanelDetail.getJButtonDiscAdd().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
    	jPanelDetail.getJButtonDiscDel().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
    	
    	jPanelDetail.getJButtonReimAdd().setEnabled(false);
    	jPanelDetail.getJButtonReimDel().setEnabled(false);
    	jPanelDetail.getJButtonReimAdd().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
    	jPanelDetail.getJButtonReimDel().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
    	
    	jPanelDetail.getJButtonCharAdd().setEnabled(false);
    	jPanelDetail.getJButtonCharDel().setEnabled(false);
    	jPanelDetail.getJButtonCharAdd().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
    	jPanelDetail.getJButtonCharDel().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_minus_disabled.jpg")));

    	/** Total values **/
    	jPanelDetail.getJButtonTOTXMLAddData().setEnabled(false);
    	jPanelDetail.getJButtonTOTXMLAddDataZoom().setEnabled(false);
    	jPanelDetail.getJButtonTOTXMLAddData().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_accept_disabled.jpg")));
    	jPanelDetail.getJButtonTOTXMLAddDataZoom().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_zoom_disabled.jpg")));
    	
    	JTextField totalResults[] = jPanelDetail.getJTextFieldResults();
    	totalResults[9].setEnabled(false);

    	/** AmountsWithheld **/
    	jPanelDetail.getJTextFieldWithItem().setEnabled(false);
    	jPanelDetail.getJTextFieldWithPerc().setEnabled(false);
    	jPanelDetail.getJTextFieldWithAmou().setEnabled(false);

    	/** LegalLiterals **/
    	jPanelDetail.getJListLiterals().setEnabled(false);
    	jPanelDetail.getJTextAreaLegal().setEnabled(false);

    	/** AttachedDocuments **/    	
    	jPanelDetail.getJTableGlobAttc().setEnabled(false);
    	jPanelDetail.getJTextAreaXMLExtAddData().setEnabled(false);
    	jPanelDetail.getJButtonAttcAdd().setEnabled(false);
    	jPanelDetail.getJButtonAttcDel().setEnabled(false);
    	jPanelDetail.getJButtonXMLAddData().setEnabled(false);
    	jPanelDetail.getJButtonXMLAddDataZoom().setEnabled(false);
    	jPanelDetail.getJButtonAttcAdd().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_plus_disabled.jpg")));
    	jPanelDetail.getJButtonAttcDel().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_minus_disabled.jpg")));
    	jPanelDetail.getJButtonXMLAddData().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_accept_disabled.jpg")));
    	jPanelDetail.getJButtonXMLAddDataZoom().setDisabledIcon(new ImageIcon(jPanelDetail.getClass().getResource("/images/button_mini_zoom_disabled.jpg")));

    	/** PaymentDetails **/
    	jPanelDetail.getJComboBoxPayment().setEnabled(false);
    	jPanelDetail.getJComboBoxPayMean().setEnabled(false);
    	jPanelDetail.getJTextFieldPaymentAmo().setEnabled(false);
    	jPanelDetail.getJCalendarComboBoxPaymDate().setEnabled(false);
    	jPanelDetail.getJTextFieldPaymentRefIssuer().setEnabled(false);
    	jPanelDetail.getJTextFieldPaymentRefReceiver().setEnabled(false);
    	
    	jPanelDetail.getJComboBoxPayAccountC().setEnabled(false);
    	jPanelDetail.getJComboBoxPayCCoun().setEnabled(false);
    	jPanelDetail.getJTextFieldPayCIBAN().setEnabled(false);
    	jPanelDetail.getJTextFieldPayCBank().setEnabled(false);
    	jPanelDetail.getJTextFieldPayCBran().setEnabled(false);
    	jPanelDetail.getJTextFieldPayCAddr().setEnabled(false);
    	jPanelDetail.getJTextFieldPayCPCod().setEnabled(false);
    	jPanelDetail.getJTextFieldPayCCity().setEnabled(false);
    	jPanelDetail.getJTextFieldPayCProv().setEnabled(false);

    	jPanelDetail.getJComboBoxPayAccountD().setEnabled(false);
    	jPanelDetail.getJComboBoxPayDCoun().setEnabled(false);
    	jPanelDetail.getJTextFieldPayDIBAN().setEnabled(false);
    	jPanelDetail.getJTextFieldPayDBank().setEnabled(false);
    	jPanelDetail.getJTextFieldPayDBran().setEnabled(false);
    	jPanelDetail.getJTextFieldPayDAddr().setEnabled(false);
    	jPanelDetail.getJTextFieldPayDPCod().setEnabled(false);
    	jPanelDetail.getJTextFieldPayDCity().setEnabled(false);
    	jPanelDetail.getJTextFieldPayDProv().setEnabled(false);

    	/** Invoice lines **/
    	jPanelDetail.setItemsForVisualization(true);

    	/** Facturae Extension **/
    	jPanelDetail.getJTextAreaTOTXMLExtAddData().setEnabled(false);
    	
    	/** Save-Sign buttons and Received checkbox**/
    	((GenerateInvoice32Window)jPanelGeneral.getTopLevelAncestor()).getJButtonSaveDraft().setVisible(false);
    	((GenerateInvoice32Window)jPanelGeneral.getTopLevelAncestor()).getJButtonSign().setVisible(false);
    	((GenerateInvoice32Window)jPanelGeneral.getTopLevelAncestor()).getJPanelInvoiceGeneral().getJCheckBoxIsReceived().setVisible(false);
    }
    
    /** Sign an invoice loading its attached documents stored in data base 
     * 
     *  @argument id.- Serie + number for selected invoice
     *  @argument rectIn.- It sets signed invoice as corrective invoice
     *  @argument MainWindow.- MainWindow instance for progress bar (could be null) 
     *  @return .- New id for signed invoice stored in DB
     * @throws DatabaseOperationException If a database error happens
     * @throws UnmarshalException 
     * @throws SigningException 
     * @throws MarshalException 
     */
	public String signInvoice(String id, boolean rectIn, MainWindow mw) throws DatabaseOperationException, SignInvoiceException,ValidationException {
		es.mityc.facturae32.Facturae fac = null;
		try {
			UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
			fac = (es.mityc.facturae32.Facturae)unmarshallerUtil.unmarshal(FacturaeManager.getInstance().loadInvoice(id));
		} catch (UnmarshalException e) {
			throw new SignInvoiceException("Unmarshalling error during invoice sign", e);
		}
		ArrayList<AttachedDocument> ads = new ArrayList<AttachedDocument> ();
		AttachedDocument ad = null;
		SQLQuery s = FacturaeManager.getInstance().executeQuery("SELECT * FROM ATTACHMENT WHERE INVOICE = " + DatabaseUtil.escapeWithMarks(id));
		List<?> ls = s.list();
		if (ls != null && ls.size() > 0 && ls.get(0) != null) {
			for (int i = 0; i < ls.size(); i++) {
				Object[] value = (Object[])ls.get(i);
				ad = new AttachedDocument();
				ad.setInvoiceId(value[1].toString());
				ad.setExtension(value[2].toString());
				ad.setDescription(value[3].toString());
				ad.setPath(value[4].toString());
				ad.setIsIncluded(Integer.parseInt(value[5].toString()));

				ads.add(ad);
			}
		}
		
//		FacturaeManager.getInstance().commit();
		Facturae inv = null;
		inv = IntermediaryUtil.getApplicationFacturae(fac);
		SQLQuery s2 = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_FACTURAE_BY_NUMBER.replace("$1", id));
		List<?> ls2 = s2.list();
		if (ls2 != null && ls2.size() > 0){
			int idBuyer = Integer.parseInt(((Object[])ls2.get(0))[0].toString());
			inv.getParties().getBuyerParty().setId(idBuyer);
			if(inv.getParties().getBuyerParty().isIndividualType())
				inv.getParties().getBuyerParty().getIndividual().setId(idBuyer);
			else
				inv.getParties().getBuyerParty().getLegalEntity().setId(idBuyer);
			int idSeller = Integer.parseInt(((Object[])ls2.get(0))[1].toString());
			inv.getParties().getSellerParty().setId(idSeller);
			if(inv.getParties().getSellerParty().isIndividualType())
				inv.getParties().getSellerParty().getIndividual().setId(idSeller);
			else
				inv.getParties().getSellerParty().getLegalEntity().setId(idSeller);
		}

		return signInvoice(inv, ads, rectIn, mw);
	}

    /** This method invokes the invoice signature process
     * @param f .- Facturae object to sign
     * @param ads .- Attachment objects to store with
     * @param rectIn .- Flag for Rectified invoices
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return .- Id for signed invoice
     * @throws DatabaseOperationException If a database error happens
     * @throws ValidationException 
     * @throws MarshalException 
     * @throws SigningException 
     */
    public String signInvoice(Facturae f, ArrayList<AttachedDocument> ads, boolean rectIn, MainWindow mw) throws DatabaseOperationException, SignInvoiceException, ValidationException {
    	
    	String invId = "";
    	
    	if (decimalProps == null)
    		loadDecimalProperties();
    	
    	if (mw != null)
			mw.refreshProgressBar(35);
    	
	    // IntermediaryUtils build a specific facturae 3.1 object
    	es.mityc.facturae32.Facturae f32 = IntermediaryUtil32.getfacturae32(f);
    	// Original id are saved for delete
    	es.mityc.facturae32.InvoiceHeaderType ih = f32.getInvoices().getInvoice().get(0).getInvoiceHeader();
    	String originalId = ih.getInvoiceSeriesCode() + ih.getInvoiceNumber();
    	
    	// Corrective
    	if (rectIn) {
    		// Sets Corrective identification for Invoice Header
    		ih.setInvoiceClass(es.mityc.facturae32.InvoiceClassType.OR);
    		ih.setInvoiceSeriesCode((String)Constants.CONFIG_PROP.get("CorrectedSeries"));
    		ih.setInvoiceNumber((String)Constants.CONFIG_PROP.get("c-issuedId"));
    	} else
			// Sets Issued series and number, if it is a Draft
			if (Constants.LANG.getString("Draft").equals(ih.getInvoiceSeriesCode())) {
				ih.setInvoiceSeriesCode(Constants.CONFIG_PROP.getProperty("series"));
				ih.setInvoiceNumber(Constants.CONFIG_PROP.getProperty("issuedId"));
				String batchIdentifier = f32.getParties().getBuyerParty().getTaxIdentification().getTaxIdentificationNumber();
		    	batchIdentifier = batchIdentifier + f32.getInvoices().getInvoice().get(0).getInvoiceHeader().getInvoiceNumber();
		    	String series = f32.getInvoices().getInvoice().get(0).getInvoiceHeader().getInvoiceSeriesCode(); 
		    	if (series != null && !"".equals(series))
		    		batchIdentifier = batchIdentifier + series;
		    	f32.getFileHeader().getBatch().setBatchIdentifier(batchIdentifier);
			}
    	
    	if (mw != null)
			mw.refreshProgressBar(65);
    	
    	try {
    		// The new facturae 3.2 object is marshalled to a temporal file
    		MarshallerUtil marshallerUtil = MarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
    		marshallerUtil.marshal(f32,"temp");
    	} catch (MarshalException e) {
    		throw new SignInvoiceException("Marshalling error during invoice sign", e);
    	}
		// The invoice 3.2 file is signed. The result is a signed document object
		File tempFile = new File("temp.xsig");
		
    	
    	
    	// Validamos contra esquema
		try
		{ 
			ValidatorUtil vu = ValidatorUtil.getInstance();
			vu.validate(tempFile, version);
		}
		catch (IOException ioe) {
			logger.error("Error validating xml: " + ioe.getMessage(), ioe);
			tempFile.delete();
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+ioe.getMessage());
		}catch (SAXParseException spe) {
			logger.error("Error validating xml: " + spe.getMessage(), spe);
			tempFile.delete();
			// Como ha fallado la firma seguimos dejandola en estado borrador
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+spe.getMessage());
		}catch (SAXException se) {
			logger.error("Error validating xml: " + se.getMessage(), se);
			tempFile.delete();
			// Como ha fallado la firma seguimos dejandola en estado borrador
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+se.getMessage());
		}
    	
    	
		if (mw != null)
			mw.refreshProgressBar(70);

		Document docSigned = null;
		try {
			docSigned = es.mityc.facturae.utils.SignatureUtil.sign(tempFile,store[0],store[1]);
		} catch (SigningException e) {
			throw new SignInvoiceException(e.getMessage());
		} catch (ConfigurationException e) {
			throw new SignInvoiceException("Signing error due to a configuration problem during invoice sign", e);
		}
		LookAndFeelLoading.load();
		Locale.setDefault(new Locale(Constants.CONFIG_PROP.getProperty("language")));

		if (mw != null)
			mw.refreshProgressBar(75);

		tempFile.delete();
		if (docSigned == null) {
			ISignFacade isignfacade = SignFactory.getInstance().getSignFacade();
			Properties properties = new Properties();
			try {
				logger.info("Loading configuration properties");
				properties.load(Invoice32Util.class.getResourceAsStream("/config/config.properties"));
				File file = new File(properties.getProperty("sign_file"));
				if(file.exists()) {
					// A configuration file created by user exists, so it is loaded
					properties.load(new FileInputStream(file));
				} else {
					// There is not a custom configuration file created by user, so the default resource included in the API jar is loaded
					properties.load(Invoice32Util.class.getResourceAsStream(properties.getProperty("sign_resource")));
				}
				logger.info("Initializing facade and setting the locale language and country");
				isignfacade.init(properties);
			} catch (IOException e) {
				logger.error("I/O error loading properties and configuring sign facade");
				throw new SignInvoiceException("I/O error loading properties and configuring sign facade", e);
			} 
			catch (ConfigurationException e) {
				logger.error("Configuration error loading properties and configuring sign facade");
				throw new SignInvoiceException("Configuration error loading properties and configuring sign facade", e);
			}

			List<?> list = isignfacade.getSignCertificates();     

			if (list == null || list.size() == 0) { // If there is not sign certificates
				Constants.DIALOG.showWarnNoCertificates();
			}
			// If the sign operation is cancelled a draft is saved
			saveDraft(f, ads, rectIn, mw);
			Constants.DIALOG.showWarnDraftCreated();
		} else {
			// The invoice file name is created depending on the series code and number
			String issuedNumber = ih.getInvoiceNumber();
			if (ih.getInvoiceSeriesCode() != null && !"".equals(ih.getInvoiceSeriesCode()))
				invId = ih.getInvoiceSeriesCode();
			invId = invId + issuedNumber;

			if (mw != null)
				mw.refreshProgressBar(80);

			// If there is no exceptions, the doc is valid and it can be written to a file
			try {
				XMLUtil.writeXml2File(docSigned,
					Constants.APP_PROP.getProperty("invoices_root") + System.getProperty("file.separator") + invId
						+ Constants.XSIG);
				XMLUtil.writeXml2File(
					docSigned,
					System.getProperty("user.home") + File.separator
						+ Constants.APP_PROP.getProperty("invoices_stealth_dir") + File.separator + invId
						+ Constants.XSIG);
			} catch (FileNotFoundException e) {
				logger.error("Error writing configuration properties: " + e.getMessage());
				throw new SignInvoiceException("Error writing configuration properties", e);
			}

			if (mw != null)
				mw.refreshProgressBar(85);

			// Finally the new invoice is stored in the data base
			es.mityc.facturae32.Facturae facturae32signed = null;
			// The signed file
			File fsigned = new File(Constants.APP_PROP.getProperty("invoices_root")
				+ System.getProperty("file.separator") + invId + Constants.XSIG);
			// A new facturae31 Facturae object is obtained from the file
			try {
				UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
				facturae32signed = (es.mityc.facturae32.Facturae) unmarshallerUtil.unmarshal(fsigned);
			} catch (UnmarshalException e) {
				logger.error("Unmarshalling error during invoice sign: " + e.getMessage());
				throw new SignInvoiceException("Unmarshalling error during invoice sign", e);
			}
			// A new appfacturae Facturae object is obtained from the facturae31 Facturae object
			Facturae f2store = es.mityc.appfacturae.utils.IntermediaryUtil.getApplicationFacturae(facturae32signed);

			// Facturae object is completed with signature info
			byte[] data = new byte[(int) fsigned.length()];
			try {
				StreamUtil.readStream(new FileInputStream(fsigned), data);
			} catch (IOException e) {
				logger.error("I/O error during invoice sign" + e.getMessage());
				throw new SignInvoiceException("I/O error during invoice sign", e);
			}
			xmlData xsig = new xmlData();
			xsig.createBlob(data);
			f2store.setXsig(xsig);

			// The status is set
			f2store.getInvoices().getInvoice().get(0).setState(InvoiceStatusType.I);

			// Set de parties
			f2store.getParties().setBuyerParty(f.getParties().getBuyerParty());
			f2store.getParties().setSellerParty(f.getParties().getSellerParty());

			// The attachments are saved
			for (int i = 0; i < ads.size(); i++) {
				ads.get(i).setInvoiceId(invId);
				FacturaeManager.getInstance().saveAttachmentNoTrans(ads.get(i));
				if (mw != null)
					mw.refreshProgressBar((int) (i / (ads.size() * 1.) * 15) + 80);
			}

			// Signed invoice are stored
			FacturaeManager.getInstance().saveFacturae(f2store, null);
			if (rectIn)
				Constants.CONFIG_PROP.setProperty("c-issuedId",
					String.valueOf(Integer.parseInt((String) Constants.CONFIG_PROP.get("c-issuedId")) + 1));
			else
				Constants.CONFIG_PROP.setProperty("issuedId", String.valueOf(Integer.parseInt(issuedNumber) + 1));
			try {
				Constants.CONFIG_PROP.store(new FileOutputStream(
					new File(Constants.APP_PROP.getProperty("config_file"))), new String("issuedId property updated"));
			} catch (FileNotFoundException e) {
				logger.error("Error writing configuration properties: " + e.getMessage());
				throw new SignInvoiceException("Error writing configuration properties", e);
			} catch (IOException e) {
				logger.error("Error writing configuration properties: " + e.getMessage());
				throw new SignInvoiceException("Error writing configuration properties", e);
			}

			// Original draft invoice (if it exists) are deleted
			Facturae fe = IntermediaryUtil.getApplicationFacturae(f32);
			SQLQuery s = FacturaeManager.getInstance().executeQuery(
					"SELECT FACTURAE_ID,PARTY_ID_SELLER,PARTY_ID_BUYER,XML FROM FACTURAE WHERE FACTURAE_ID IN ("+
						"SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = "
						+ DatabaseUtil.escapeWithMarks(originalId) + " AND STATE = 0))");
			List<?> ls = s.list();
			if (ls != null && ls.size() > 0) {
				fe.setId(Integer.parseInt(((Object[]) ls.get(0))[0].toString()));
				// The ids and xml, which don't belong to facturae, have to be set
				fe.getParties().getSellerParty().setId(Integer.parseInt(((Object[]) ls.get(0))[1].toString()));
				fe.getParties().getBuyerParty().setId(Integer.parseInt(((Object[]) ls.get(0))[2].toString()));
				xmlData xd = new xmlData();
				xd.setXsig((java.sql.Blob) ((Object[]) ls.get(0))[3]);
				fe.setXsig(xd);

				FacturaeManager.getInstance().deleteFacturae(fe, false, version);

				// Delete the associated attachments
				SQLQuery satt = FacturaeManager.getInstance().executeQuery(
					"SELECT ID FROM ATTACHMENT WHERE INVOICE = " + DatabaseUtil.escapeWithMarks(originalId));
				List<?> lsAtt = satt.list();
				AttachedDocument ad = null;
				if (lsAtt != null && lsAtt.size() > 0) {
					for (int i = 0; i < lsAtt.size(); i++) {
						ad = new AttachedDocument();
						ad.setId(Integer.valueOf(lsAtt.get(i).toString()));
						FacturaeManager.getInstance().deleteAttachmentNoTrans(ad);
					}
				}
			}

			FacturaeManager.getInstance().saveAction(EnumOperationType.SignIn, invId);
		}
		
		return invId;
    }
    
    /** This method invokes the save draft process
     * 
     * @param f .- Facturae object to save
     * @param ads .- Attachment objects to store with
     * @param rectIn .- Flag for Rectified invoices
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return .- Id for saved invoice
     * @throws DatabaseOperationException If a database error happens
     */
    public String saveDraft(Facturae f, ArrayList<AttachedDocument> ads, boolean rectIn, MainWindow mw) throws DatabaseOperationException,ValidationException {
    	String id = null;
    	try {
    		// The new facturae 3.2 object is marshalled to a temporal file
    		MarshallerUtil marshallerUtil = MarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
    		marshallerUtil.marshal(IntermediaryUtil32.getfacturae32(f),"temp");
    	} catch (MarshalException e) {
    		throw new ValidationException("Marshalling error during invoice sign", e);
    	}
		// The invoice 3.2 file is signed. The result is a signed document object
		File tempFile = new File("temp.xsig");
    	// Validamos contra esquema
		try
		{ 
			ValidatorUtil vu = ValidatorUtil.getInstance();
			vu.validate(tempFile, version);
		}
		catch (IOException ioe) {
			logger.error("Error validating xml: " + ioe.getMessage(), ioe);
			String content = null;
			try {
				content = new Scanner(tempFile).useDelimiter("\\Z").next();
				logger.info(content);
			} catch (FileNotFoundException e) {}
			tempFile.delete();
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+ioe.getMessage());
		}catch (SAXParseException spe) {
			logger.error("Error validating xml: " + spe.getMessage(), spe);
			String content = null;
			try {
				content = new Scanner(tempFile).useDelimiter("\\Z").next();
				logger.info(content);
			} catch (FileNotFoundException e) {}
			tempFile.delete();
			// Como ha fallado la firma seguimos dejandola en estado borrador
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+spe.getMessage());
		}catch (SAXException se) {
			logger.error("Error validating xml: " + se.getMessage(), se);
			String content = null;
			try {
				content = new Scanner(tempFile).useDelimiter("\\Z").next();
				logger.info(content);
			} catch (FileNotFoundException e) {}			
			tempFile.delete();
			// Como ha fallado la firma seguimos dejandola en estado borrador
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+se.getMessage());
		}
    	
    	if (decimalProps == null)
    		loadDecimalProperties();
    	
    	InvoiceHeaderType ih = f.getInvoices().getInvoice().get(0).getInvoiceHeader();
    	String draftPrefix = null;
    	String draftSufix = null;
    	if (rectIn) {
    		if (Constants.CONFIG_PROP.get("CorrectedSeries") == null || "".equals((String)Constants.CONFIG_PROP.get("CorrectedSeries")))
    			draftPrefix = Constants.LANG.getString("CorrectedDraft");
    		else
    			draftPrefix = Constants.LANG.getString("Draft") + (String)Constants.CONFIG_PROP.get("CorrectedSeries");
    		draftSufix = ih.getInvoiceNumber();
    		// Sets Corrective identification for Invoice Header
    		ih.setInvoiceClass(InvoiceClassType.OR);
    		
    		// The old corrective draft has to be deleted
			SQLQuery s = FacturaeManager.getInstance().executeQuery(
					"SELECT FACTURAE_ID,PARTY_ID_SELLER,PARTY_ID_BUYER,XML FROM FACTURAE WHERE FACTURAE_ID IN ("
						+ "SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = "
						+ DatabaseUtil.escapeWithMarks(draftPrefix + draftSufix) + "))");
    		List<?> ls = s.list();
    		if (ls != null && ls.size() > 0) {
    			Object[] subquery = ((Object[])ls.get(0));
    			f.setId(Integer.parseInt(subquery[0].toString()));
    			//f.getParties().getSellerParty().setId(Integer.parseInt(subquery[1].toString()));
				//f.getParties().getBuyerParty().setId(Integer.parseInt(subquery[2].toString()));
    			xmlData xd = new xmlData();
    			xd.setXsig((java.sql.Blob)subquery[3]);
    			f.setXsig(xd);    			
    			FacturaeManager.getInstance().deleteFacturae(f, false, version);
    		}
    		else {
    			draftSufix = (String)Constants.CONFIG_PROP.get("c-draftId");
        		Constants.CONFIG_PROP.setProperty("c-draftId", String.valueOf(Integer.parseInt(draftSufix)+1));
    		}
    	} else if (Constants.LANG.getString("Draft").equals(ih.getInvoiceSeriesCode())) {
    		draftPrefix = Constants.LANG.getString("Draft");
    		draftSufix = ih.getInvoiceNumber();
    		// The old draft has to be deleted
			SQLQuery s = FacturaeManager.getInstance().executeQuery(
					"SELECT FACTURAE_ID,PARTY_ID_SELLER,PARTY_ID_BUYER,XML FROM FACTURAE WHERE FACTURAE_ID IN ("
						+ "SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = "
						+ DatabaseUtil.escapeWithMarks(draftPrefix + draftSufix) + "))");
    		List<?> ls = s.list();
    		if (ls != null && ls.size() > 0) {
    			Object[] subquery = ((Object[])ls.get(0));
    			f.setId(Integer.parseInt(subquery[0].toString()));
    			//f.getParties().getSellerParty().setId(Integer.parseInt(subquery[1].toString()));
				//f.getParties().getBuyerParty().setId(Integer.parseInt(subquery[2].toString()));
    			xmlData xd = new xmlData();
    			xd.setXsig((java.sql.Blob)subquery[3]);
    			f.setXsig(xd);    			
    			FacturaeManager.getInstance().deleteFacturae(f, false, version);
    		}
    		else {
    			draftPrefix = Constants.LANG.getString("Draft");
        		draftSufix = Constants.CONFIG_PROP.getProperty("draftId");
        		Constants.CONFIG_PROP.setProperty("draftId", String.valueOf(Integer.parseInt(draftSufix)+1));
    		}
    	} else {
    		draftPrefix = Constants.LANG.getString("Draft");
    		draftSufix = Constants.CONFIG_PROP.getProperty("draftId");
    		Constants.CONFIG_PROP.setProperty("draftId", String.valueOf(Integer.parseInt(draftSufix)+1));
    	}
    	
    	ih.setInvoiceSeriesCode(draftPrefix);
    	ih.setInvoiceNumber(draftSufix);
    	
    	if (mw != null)
    		mw.refreshProgressBar(35);
			
    	// The status is set
    	f.getInvoices().getInvoice().get(0).setState(InvoiceStatusType.D);
	    	
    	// If the invoice is an old draft, its old attachments have to be deleted
    	SQLQuery satt = FacturaeManager.getInstance().executeQuery("SELECT ID FROM ATTACHMENT WHERE INVOICE = " 
    		+ DatabaseUtil.escapeWithMarks(draftPrefix + draftSufix) );

    	List<?> ls = satt.list();
    	AttachedDocument ad = null;
    	if (ls != null && ls.size() > 0) {
    		for (int i = 0; i < ls.size(); i++){
    			ad = new AttachedDocument();
    			ad.setId(Integer.valueOf(ls.get(i).toString()));
    			FacturaeManager.getInstance().deleteAttachmentNoTrans(ad);
    		}
    	}
	    	
    	// The new attachments are saved
    	for (int i = 0; i < ads.size(); i++){
    		ads.get(i).setInvoiceId(draftPrefix+draftSufix);
    		FacturaeManager.getInstance().saveAttachmentNoTrans(ads.get(i));
    		if (mw != null)
    			mw.refreshProgressBar( (int)(i / (ads.size() * 1.) * 50) + 35 );
    	}
	    	
    	if (mw != null)
    		mw.refreshProgressBar(85);		
    	
    	FacturaeManager.getInstance().saveFacturae(f,null);
    	id = ih.getInvoiceSeriesCode() + ih.getInvoiceNumber();
    	FacturaeManager.getInstance().saveAction(EnumOperationType.SaveDraft, id);
    	
    	try {
    		Constants.CONFIG_PROP.store(new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file"))), new String("issuedId property updated"));
    	}
    	catch(FileNotFoundException e){
    		logger.error("The signing process could not be completed: " + e.getMessage(), e);
    	}
    	catch(IOException e){
    		logger.error("The signing process could not be completed: " + e.getMessage(), e);
    	}
    	catch(Exception e) {
    		logger.error("The signing process could not be completed: " + e.getMessage(), e);
    	}

    	return id;
    }
 
    /** This method invokes the save received process
     * 
     * @param f .- Facturae object to save
     * @param ads .- Attachment objects to store with
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return .- Id for saved invoice
     * @throws DatabaseOperationException If a database error happens
     */    
    public String saveReceived(Facturae f, ArrayList<AttachedDocument> ads, MainWindow mw) throws DatabaseOperationException,ValidationException {
    	
    	String id = null;
    	
    	try {
    		// The new facturae 3.2 object is marshalled to a temporal file
    		MarshallerUtil marshallerUtil = MarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
    		marshallerUtil.marshal(IntermediaryUtil32.getfacturae32(f),"temp");
    	} catch (MarshalException e) {
    		throw new ValidationException("Marshalling error during invoice sign", e);
    	}
		// The invoice 3.2 file is signed. The result is a signed document object
		File tempFile = new File("temp.xsig");
    	// Validamos contra esquema
		try
		{ 
			ValidatorUtil vu = ValidatorUtil.getInstance();
			vu.validate(tempFile, version);
		}
		catch (IOException ioe) {
			logger.error("Error validating xml: " + ioe.getMessage(), ioe);
			tempFile.delete();
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+ioe.getMessage());
		}catch (SAXParseException spe) {
			logger.error("Error validating xml: " + spe.getMessage(), spe);
			tempFile.delete();
			// Como ha fallado la firma seguimos dejandola en estado borrador
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+spe.getMessage());
		}catch (SAXException se) {
			logger.error("Error validating xml: " + se.getMessage(), se);
			tempFile.delete();
			// Como ha fallado la firma seguimos dejandola en estado borrador
			throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+se.getMessage());
		}
    	    	
    	if (decimalProps == null)
    		loadDecimalProperties();

    	InvoiceHeaderType ih = f.getInvoices().getInvoice().get(0).getInvoiceHeader();
    	String invPrefix = ih.getInvoiceSeriesCode();
    	String invSufix = ih.getInvoiceNumber();
    	
    	// If the old invoice exists, it have to be deleted
		SQLQuery s = FacturaeManager.getInstance().executeQuery(
				"SELECT FACTURAE_ID,PARTY_ID_SELLER,PARTY_ID_BUYER,XML FROM FACTURAE WHERE FACTURAE_ID IN ("
					+ "SELECT FACTURAE_ID FROM FACTURAE_INVOICES WHERE INVOICE_ID IN (SELECT INVOICE_ID FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = "
					+ DatabaseUtil.escapeWithMarks(invPrefix + invSufix) + "))");
		List<?> ls = s.list();
		if (ls != null && ls.size() > 0) {
			Object[] subquery = ((Object[])ls.get(0));
			f.setId(Integer.parseInt(subquery[0].toString()));
			f.getParties().getSellerParty().setId(Integer.parseInt(subquery[1].toString()));
			f.getParties().getBuyerParty().setId(Integer.parseInt(subquery[2].toString()));
			xmlData xd = new xmlData();
			xd.setXsig((java.sql.Blob)subquery[3]);
			f.setXsig(xd);    			
			FacturaeManager.getInstance().deleteFacturae(f ,false, version);
		}
    	
		// If the invoice is an old one, its old attachments have to be deleted
		SQLQuery satt = FacturaeManager.getInstance().executeQuery("SELECT ID FROM ATTACHMENT WHERE INVOICE = " + DatabaseUtil.escapeWithMarks(invPrefix + invSufix));
		ls = satt.list();
		AttachedDocument ad = null;
		if (ls != null && ls.size() > 0) {
			for (int i = 0; i < ls.size(); i++) {
				ad = new AttachedDocument();
				ad.setId(Integer.valueOf(ls.get(i).toString()));
				FacturaeManager.getInstance().deleteAttachmentNoTrans(ad);
			}
		}
	
		if (mw != null)
			mw.refreshProgressBar(35);	
	
		f.getInvoices().getInvoice().get(0).setState(InvoiceStatusType.R);
		id = Constants.CONFIG_PROP.getProperty("receivedId");
		FacturaeManager.getInstance().saveFacturae(f, id);
		Constants.CONFIG_PROP.setProperty("receivedId", String.valueOf(Integer.parseInt(id)+1));
		try {
			Constants.CONFIG_PROP.store(new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file"))), new String("receivedId property updated"));
		} catch(Exception e) {
			logger.error("The configuration could not be saved: " + e.getMessage(), e);
		}
		    	
		if (mw != null)
			mw.refreshProgressBar(75);
		    	
		ih = f.getInvoices().getInvoice().get(0).getInvoiceHeader();
		id = ih.getInvoiceSeriesCode() + ih.getInvoiceNumber(); 
		    	
		// The new attachments are saved
		for (int i = 0; i < ads.size(); i++) {
			ads.get(i).setInvoiceId(id);
			FacturaeManager.getInstance().saveAttachmentNoTrans(ads.get(i));
			if (mw != null)
				mw.refreshProgressBar( (int)(i / (ads.size() * 1.) * 25) + 75 );
		}
		    	
		if (mw != null)
			mw.refreshProgressBar(95);
		
		FacturaeManager.getInstance().saveAction(EnumOperationType.ReceiveIn, id);

		return id;
    }
    
    /**
     * Show a Corrective element
     * @param id.- Id for invoice corrective element to be shown
     */
    public void seeCorrections(String id) {
    	File f = FacturaeManager.getInstance().loadInvoice(id);
		Facturae inv = null;
		if (f != null) {
			try {
				UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
				inv = IntermediaryUtil.getApplicationFacturae((es.mityc.facturae32.Facturae)unmarshallerUtil.unmarshal(f));
				if (inv.getInvoices().getInvoice().get(0).getInvoiceHeader().getCorrective() != null) {
					CorrectionDataDialog cdd = new CorrectionDataDialog(inv.getInvoices().getInvoice().get(0).getInvoiceHeader().getCorrective(), version);
					cdd.setVisible(true);
				}
			} catch (UnmarshalException e) {
				Constants.DIALOG.showErrorSeeCorrections();
			}
		}
	}

    /**
     * Send a Facturae object using simple mail transfer protocol
     * @param id.- Identifier for invoice to be send
     * @param mw .- MainWindow instance for progress bar (could be null for no progress bar)
     * @return boolean.- True if it was sent
     */
    public boolean sendInvoice(String id, MainWindow mw) {
    	// ext-csaamar: 20140930 - Evitar envío de facturas a FACe.
 //   	return false;
   	
    	String mail = "";
    	es.mityc.facturae32.Facturae f32 = null;
		if (mw != null)
			mw.refreshProgressBar(5);
    	try {
    		UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeVersion.FACTURAE_32);
    		f32 = (es.mityc.facturae32.Facturae)unmarshallerUtil.unmarshal(FacturaeManager.getInstance().loadInvoice(id));
    	} catch (UnmarshalException e) {
    		logger.error("Error unmarshalling the invoice to send: " + e.getMessage(), e);
			Constants.DIALOG.showErrorSend(e.getMessage());
			return false;
    	}
    	int numMandatoryAdmsFACe = 0;
    	int admCentresNum = 0;
    	es.mityc.facturae32.BusinessType buyerParty = f32.getParties().getBuyerParty();
    	if (buyerParty.getAdministrativeCentres() != null && buyerParty.getAdministrativeCentres().getAdministrativeCentre() != null && buyerParty.getAdministrativeCentres().getAdministrativeCentre().size() > 0){
    		admCentresNum = buyerParty.getAdministrativeCentres().getAdministrativeCentre().size();
    		for (int i = 0 ; i < admCentresNum ; i++){
    			AdministrativeCentreType act = buyerParty.getAdministrativeCentres().getAdministrativeCentre().get(i);
    			// If there is an Administrative Centre with 'Receiver' role
    			if (!"".equals(act.getRoleTypeCode())){
    				if (act.getRoleTypeCode().equals("02")){
	    				if (act.getContactDetails() != null && act.getContactDetails().getElectronicMail() != null && !"".equals(act.getContactDetails().getElectronicMail().trim())){
	    					if(mail.length()<=0){
	    						mail = act.getContactDetails().getElectronicMail().trim();
	    					}
	    					//i = admCentresNum;
	    				}
	    				numMandatoryAdmsFACe++;
    				}
	    			else if (act.getRoleTypeCode().equals("01")){
	    				if (act.getContactDetails() != null && act.getContactDetails().getElectronicMail() != null && !"".equals(act.getContactDetails().getElectronicMail().trim())){
	    					if(mail.length()<=0){
	    						mail = act.getContactDetails().getElectronicMail().trim();
	    					}
	    				}
	    			} else if (act.getRoleTypeCode().equals("03")){
	    				numMandatoryAdmsFACe++;
	    			}
    			}
    		}
    	}
    	if ("".equals(mail)){
    		if (buyerParty.getIndividual() != null && buyerParty.getIndividual().getContactDetails() != null && buyerParty.getIndividual().getContactDetails().getElectronicMail() != null && !"".equals(buyerParty.getIndividual().getContactDetails().getElectronicMail().trim())){
    			mail = buyerParty.getIndividual().getContactDetails().getElectronicMail().trim();
    		}
    		else if (buyerParty.getLegalEntity() != null && buyerParty.getLegalEntity().getContactDetails() != null && buyerParty.getLegalEntity().getContactDetails().getElectronicMail() != null && !"".equals(buyerParty.getLegalEntity().getContactDetails().getElectronicMail().trim())){
    			mail = buyerParty.getLegalEntity().getContactDetails().getElectronicMail().trim();
    		}
    	}
		String query = "SELECT FACE FROM LEGAL_ENTITY LE, FACTURAE FAC, FACTURAE_INVOICES FI, INVOICE INV WHERE LE.PARTY_ID = FAC.PARTY_ID_BUYER AND FAC.FACTURAE_ID = FI.FACTURAE_ID AND FI.INVOICE_ID = INV.INVOICE_ID AND NVL(INV.SERIES_CODE,'')||INV.NUMBER='"+ id +"'";
		//String query = "SELECT * FROM FACTURAE FAC WHERE FAC.FACTURAE_ID = '"+ id +"'";
		SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
		List<?> ls = s.list();
		Boolean face = null;
		if (mw != null)
			mw.refreshProgressBar(25);
		if (ls != null && ls.size() > 0)
			face = (Boolean)ls.get(0);

		if(face != null && !face.booleanValue()) {
			face = (admCentresNum > 0);
		}
		
    	if(face != null && face.booleanValue()) {
    		//Check admCentres
    		if(numMandatoryAdmsFACe < 2) {
    			//Popup error
    			logger.info("Bad number of mandatory FACe centers");
    			Constants.DIALOG.showErrorFACe(Constants.LANG.getString("FACeErrorMandatoryAdmCenters"));
    			if (mw != null)
    				mw.refreshProgressBar(100);
    			return false;
    		}
    		//Check configuration
    		String faceMail = Constants.CONFIG_PROP.getProperty("FACeEmail");
    		if(!PeFACUtils.getInstance().checkConfig()){
    			if (mw != null)
    				mw.refreshProgressBar(100);
    			return false;
    		}
    		//Popup de confirmacion
    		if(Constants.DIALOG.showConfirmSendToFACe() != JOptionPane.YES_OPTION) {
    			//Canceled by user
    			if (mw != null)
    				mw.refreshProgressBar(100);
    			return false;
    		}

    		File f = FacturaeManager.getInstance().loadInvoice(id);
			if (f != null) {
				SSPPResultadoEnviarFactura sendResult = null;
				try {
		    		//recuperar path attach no en xml de fzct
		    		String attachQuery = "SELECT PATH FROM ATTACHMENT WHERE INCLUDED = 0 AND INVOICE = '" + id + "'";
		    		SQLQuery asq = FacturaeManager.getInstance().executeQuery(attachQuery);
		    		@SuppressWarnings("unchecked")
					List<String> lsa = (List<String>)asq.list();

		    		if (mw != null)
						mw.refreshProgressBar(40);
					sendResult = PeFACUtils.getInstance().sendInvoice(f, faceMail, lsa);
					if (mw != null)
						mw.refreshProgressBar(60);
					logger.debug("Factura enviada: " + sendResult);
					//preparar resultado
					FACeSentResult fsr = new FACeSentResult();
					fsr.setDeliveryJustification(sendResult.getPdf_justificante());
					fsr.setRegisterCode(sendResult.getCodigo_registro());

					try{
						DatatypeFactory df = DatatypeFactory.newInstance();
						GregorianCalendar calendar = new GregorianCalendar();
						SimpleDateFormat sdfFACeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						calendar.setTime(sdfFACeDateFormat.parse(sendResult.getFecha_recepcion()));
						XMLGregorianCalendar deliveryDate = df.newXMLGregorianCalendar(calendar);
						
						fsr.setDate(deliveryDate);

						fsr.setInvoiceNumber(f32.getInvoices().getInvoice().get(0).getInvoiceHeader().getInvoiceNumber());
						fsr.setInvoiceSeriesCode(f32.getInvoices().getInvoice().get(0).getInvoiceHeader().getInvoiceSeriesCode());
						//guardar resultado
						try{
							FacturaeManager.getInstance().saveFACeSentResult(fsr);						
						} catch (DatabaseOperationException e) {
							// The user is not informed about action not saved event. An error log has been created previously.
						}
					} catch (DatatypeConfigurationException ex) {
						logger.error("Error saving a FACeSentResult. Error creating a XMLGregorianCalendar object: " + ex.getMessage(),ex);
					} catch (ParseException ex2) {
						logger.error("Error saving a FACeSentResult. Error creating a XMLGregorianCalendar object: " + ex2.getMessage(),ex2);
					}
					//mostrar pantalla resumen de envío
    				mw.getTransition().removeTransitionPanel();
					FACeSentResultDialog.showFACeReceiverDialog(mw, fsr, true);
				} catch (FACeException e) {
					if (mw != null)
						mw.refreshProgressBar(60);
					logger.error("Error enviando factura: " + e.getMessage(), e);
					try {
						FacturaeManager.getInstance().saveAction(EnumOperationType.SendFace, id, e.getMessage());
					} catch (DatabaseOperationException ex) {
						// The user is not informed about action not saved event. An error log has been created previously.
					}
					//Popup de error					
					Constants.DIALOG.showErrorFACe(e.getMessage());
				} catch(Exception e) {
					logger.error("Error al enviar la factura", e);
				} finally {
					f.delete();
					if (sendResult==null) {
						logger.info("Unable to send selected invoice: " + id);
						if (mw != null)
							mw.refreshProgressBar(100);
						Constants.DIALOG.showErrorFACe(Constants.LANG.getString("MsgPeFACSendInvoiceError"));
						return false;
					}
				}
				if (mw != null)
					mw.refreshProgressBar(75);
			}
    	} else {
	    	SendDialog sd = new SendDialog(mw, true, mail);
	    	
	    	String[] result = new String[3];
	    	result = sd.getValues();
	    	
	    	if (result == null || result.length != 3 || result[0] == null || "".equals(result[0])){ // Canceled by user
	    		if (mw != null)
	    			mw.refreshProgressBar(100);
	    		return false;
	    	}
	    	
	    	File f = FacturaeManager.getInstance().loadInvoice(id);
			if (f != null) {
				File fOut = new File(id + ".xsig");
				f.renameTo(fOut);
				boolean sendResult = false;
				try {
					if (mw != null)
						mw.refreshProgressBar(40);
					sendResult = SendUtil.send(result[0], result[1], result[2], fOut);
				} finally {
					fOut.delete();
					if (!sendResult) {
						logger.info("Unable to send selected invoice: " + id);
						if (mw != null)
							mw.refreshProgressBar(100);
						return false;
					}
				}
			}			
    	}	
		if (mw != null)
			mw.refreshProgressBar(85);
		// Signed invoice is updated (marked as sended)
		FacturaeManager.getInstance().executeUpdate("UPDATE INVOICE SET STATE = 2 WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = '" + id + "' AND STATE = 1");
		
		try {
			if(face != null && face.booleanValue()) {
				FacturaeManager.getInstance().saveAction(EnumOperationType.SendFace, id);
			} else {
				FacturaeManager.getInstance().saveAction(EnumOperationType.SendIn, id);
			}
		} catch (DatabaseOperationException e) {
			// The user is not informed about action not saved event. An error log has been created previously.
		}
		
		if (mw != null)
			mw.refreshProgressBar(100);
		return true;

    }
    

    private static void loadDecimalProperties() {

    	/** Loading decimal configuration properties */
    	if (decimalProps == null) {
    		try {
    			File f = new File(Constants.APP_PROP.getProperty("fact"+codeVersion+"d_file"));
    			decimalProps = new Properties();
    			if (f.exists())
    				decimalProps.load(new FileInputStream(f));
    			else
    				decimalProps.load(Constants.APP_PROP.getClass().getResourceAsStream(Constants.APP_PROP.getProperty("fact"+codeVersion+"d_resource")));
    		}
    		catch(IOException e) {
    			logger.error("It is not possible to obtain the configuration properties file: " + e.getMessage(), e);
    		}
    	}
    }
    
    
  
}