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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.ParseException;
import es.mityc.appfacturae.exceptions.ReceiveException;
import es.mityc.appfacturae.facturae.InvoiceHeaderType;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.EnumOperationType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.hibernate.auxClass.xmlData;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.appfacturae.utils.io.FileUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.utils.InvalidSignatureException;
import es.mityc.facturae.utils.SignatureUtil;
import es.mityc.facturae.utils.UnmarshalException;
import es.mityc.facturae.utils.UnmarshallerUtil;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.ValidatorUtil;

public class ReceiveUtil {

	private static int numOfFields = 12;
	private static Log logger = LogFactory.getLog(ReceiveUtil.class);

	public static Object[] receive(String invoicePath) throws ReceiveException {

		String[] path = null;
		int pathLength = 0;
		ReceiveException error = null;

		if (invoicePath != null && !"".equals(invoicePath.trim())) {
			path = invoicePath.split("\\.");
			pathLength = path.length;
		} else
			throw new ReceiveException(Constants.LANG.getString("NOOKMessageInsertInvoicePath"));

		if (pathLength > 0 && path[pathLength - 1].equalsIgnoreCase("xml")
			|| path[pathLength - 1].equalsIgnoreCase("xsig")) {

			File invoiceReceived = new File(invoicePath);

			org.w3c.dom.Document doc = null;
			String version = "";
			es.mityc.appfacturae.facturae.Facturae f2store = null;
			es.mityc.facturae.GenericFacturae facturaeG = null;

			try {
				/** Parsing File **/
				try {
					doc = FileUtil.createDoc(invoiceReceived);
				} catch (ParseException pe) {
					throw new ReceiveException(Constants.LANG.getString("NOOKMessageXMLFormat"));
				}

				version = XMLFacturaeUtil.getVersionStr(doc);

				/** Unmarshalling depending on the schema version **/
				Object[] receivedInvoiceData = new Object[numOfFields + 1];
				receivedInvoiceData[numOfFields] = null;

				/** 1.- Schema validation **/
				ValidatorUtil vu = null;
				try {
					vu = ValidatorUtil.getInstance();
					boolean SchemaValidationResult = vu.validate(invoiceReceived, version);
					if (SchemaValidationResult)
						receivedInvoiceData[Constants.SCHEMA_VALIDATION] = Constants.LANG.getString("OK");
					else {
						receivedInvoiceData[Constants.SCHEMA_VALIDATION] = Constants.LANG.getString("NOOK");
					}
				} catch (ValidationException e) {
					receivedInvoiceData[Constants.SCHEMA_VALIDATION] = Constants.LANG.getString("NOOK");
					logger.error(e.getMessage(), e);
					if (error == null)
						error = new ReceiveException(Constants.LANG.getString("NOOKMessageProcessReceive"), e);
				} catch (SAXException e) {
					receivedInvoiceData[Constants.SCHEMA_VALIDATION] = Constants.LANG.getString("NOOK");
					logger.error(e.getMessage(), e);
					if (error == null)
						error = new ReceiveException(Constants.LANG.getString("NOOKMessageProcessReceive"), e);
				} catch (IOException e) {
					receivedInvoiceData[Constants.SCHEMA_VALIDATION] = Constants.LANG.getString("NOOK");
					logger.error(e.getMessage(), e);
					if (error == null)
						error = new ReceiveException(Constants.LANG.getString("NOOKMessageProcessReceive"), e);
				}

				/** 2.- Sign validation **/
				Map<String, Object> signValidation = null;
				try {
					signValidation = SignatureUtil.validateSignature(invoiceReceived);
					if (signValidation != null)
						receivedInvoiceData[Constants.SIGN_VALIDATION] = Constants.LANG.getString("OK");
					else {
						receivedInvoiceData[Constants.SIGN_VALIDATION] = Constants.LANG.getString("NOOK");
					}
				} catch (InvalidSignatureException e) {
					receivedInvoiceData[Constants.SIGN_VALIDATION] = Constants.LANG.getString("NOOK");
					logger.error(e.getMessage(), e);
					if (error == null)
						error = new ReceiveException(Constants.LANG.getString("NOOKMessageProcessReceive"), e);
				}

				/** Unmarshalling Facturae **/
				UnmarshallerUtil unmarshallerUtil;
				try {
					//ctg attach
					unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeUtil.getVersionOb(version));
					facturaeG = unmarshallerUtil.unmarshal(invoiceReceived);
				} catch (UnmarshalException e) {
					logger.error(e.getMessage(), e);
					throw new ReceiveException(Constants.LANG.getString("NOOKMessageProcessReceive"), e);
				}

				/** Version **/
				receivedInvoiceData[Constants.VERSION] = version;

				/** Sign Policy **/
				if (signValidation != null && signValidation.get("sign.policy") != null
					&& !"".equals(signValidation.get("sign.policy").toString()))
					receivedInvoiceData[Constants.SIGN_POLICY] = signValidation.get("sign.policy").toString();
				else
					receivedInvoiceData[Constants.SIGN_POLICY] = Constants.LANG.getString("EmptyField");

				// After validations an application Facturae object is obtained
				f2store = IntermediaryUtil.getApplicationFacturae(facturaeG);

				/** Seller **/
				String seller = "";
				if (f2store.getParties().getSellerParty().getLegalEntity() != null
					&& !"".equals(f2store.getParties().getSellerParty().getLegalEntity().getCorporateName()))
					seller = f2store.getParties().getSellerParty().getLegalEntity().getCorporateName();
				else if (f2store.getParties().getSellerParty().getIndividual() != null
					&& !"".equals(f2store.getParties().getSellerParty().getIndividual().getName())
					&& !"".equals(f2store.getParties().getSellerParty().getIndividual().getFirstSurname()))
					seller = f2store.getParties().getSellerParty().getIndividual().getName() + " "
						+ f2store.getParties().getSellerParty().getIndividual().getFirstSurname();
				else
					seller = Constants.LANG.getString("EmptyField");

				receivedInvoiceData[Constants.SELLER] = seller;

				/** Seller Tax Identification**/
				String sellerTaxId = "";
				if (f2store.getParties().getSellerParty().getTaxIdentification() != null
					&& !"".equals(f2store.getParties().getSellerParty().getTaxIdentification()
						.getTaxIdentificationNumber()))
					sellerTaxId = f2store.getParties().getSellerParty().getTaxIdentification()
						.getTaxIdentificationNumber();
				else
					sellerTaxId = Constants.LANG.getString("EmptyField");
				receivedInvoiceData[Constants.SELLER_TAX_IDENTIFICATION] = sellerTaxId;

				/** Buyer **/
				String buyer = "";
				if (f2store.getParties().getBuyerParty().getLegalEntity() != null
					&& !"".equals(f2store.getParties().getBuyerParty().getLegalEntity().getCorporateName()))
					buyer = f2store.getParties().getBuyerParty().getLegalEntity().getCorporateName();
				else if (f2store.getParties().getBuyerParty().getIndividual() != null
					&& !"".equals(f2store.getParties().getBuyerParty().getIndividual().getName())
					&& !"".equals(f2store.getParties().getBuyerParty().getIndividual().getFirstSurname()))
					buyer = f2store.getParties().getBuyerParty().getIndividual().getName() + " "
						+ f2store.getParties().getBuyerParty().getIndividual().getFirstSurname();
				else
					buyer = Constants.LANG.getString("EmptyField");
				receivedInvoiceData[Constants.BUYER] = buyer;

				/** Buyer Tax Identification **/
				String buyerTaxId = "";
				if (f2store.getParties().getBuyerParty().getTaxIdentification() != null
					&& !"".equals(f2store.getParties().getBuyerParty().getTaxIdentification()
						.getTaxIdentificationNumber()))
					buyerTaxId = f2store.getParties().getBuyerParty().getTaxIdentification()
						.getTaxIdentificationNumber();
				else
					buyerTaxId = Constants.LANG.getString("EmptyField");
				receivedInvoiceData[Constants.BUYER_TAX_IDENTIFICATION] = buyerTaxId;

				/** Item **/
				String item = "";
				if (f2store.getInvoices().getInvoice().get(0).getItems().getInvoiceLine().get(0) != null
					&& !"".equals(f2store.getInvoices().getInvoice().get(0).getItems().getInvoiceLine().get(0)
						.getItemDescription()))
					item = f2store.getInvoices().getInvoice().get(0).getItems().getInvoiceLine().get(0)
						.getItemDescription();
				else
					item = Constants.LANG.getString("EmptyField");
				receivedInvoiceData[Constants.ITEM] = item;

				/** Amount **/
				double amount;
				String amountStr = "";
				if (f2store.getFileHeader().getBatch().getTotalExecutableAmount() != null) {
					amount = f2store.getFileHeader().getBatch().getTotalExecutableAmount().getTotalAmount();
					amountStr = amount + " " + Constants.LANG.getString("EuroSimbol");
				} else
					amountStr = Constants.LANG.getString("EmptyField");
				receivedInvoiceData[Constants.AMOUNT] = amountStr;

				/** Issue Date **/
				String date = "";
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = Constants.DATE_FORMAT;
				if (f2store.getInvoices().getInvoice().get(0).getInvoiceIssueData().getIssueDate() != null) {
					cal.set(f2store.getInvoices().getInvoice().get(0).getInvoiceIssueData().getIssueDate().getYear(),
						f2store.getInvoices().getInvoice().get(0).getInvoiceIssueData().getIssueDate().getMonth(),
						f2store.getInvoices().getInvoice().get(0).getInvoiceIssueData().getIssueDate().getDay());
					date = (sdf.format(cal.getTime())).toString();
				} else
					date = Constants.LANG.getString("EmptyField");
				receivedInvoiceData[Constants.ISSUE_DATE] = date;

				if (error != null)
					receivedInvoiceData[numOfFields] = error;

				if (!receivedInvoiceData[Constants.SCHEMA_VALIDATION].toString().equals(Constants.LANG.getString("NOOK"))
					&& !receivedInvoiceData[Constants.SIGN_VALIDATION].toString().equals(Constants.LANG.getString("NOOK"))) {
					try {
						f2store.getInvoices().getInvoice().get(0).setState(InvoiceStatusType.R);

						// Facturae object is completed with related XML
						byte[] data = new byte[(int) invoiceReceived.length()];
						StreamUtil.readStream(new FileInputStream(invoiceReceived), data);
						xmlData xsig = new xmlData();
						xsig.createBlob(data);
						f2store.setXsig(xsig);

						receivedInvoiceData[Constants.RECEIVEDID] = Constants.CONFIG_PROP.getProperty("receivedId");
						FacturaeManager.getInstance().saveFacturae(f2store, receivedInvoiceData[Constants.RECEIVEDID].toString());
						InvoiceHeaderType ih = f2store.getInvoices().getInvoice().get(0).getInvoiceHeader();
						FacturaeManager.getInstance().saveAction(EnumOperationType.ReceiveIn,
							ih.getInvoiceSeriesCode() + ih.getInvoiceNumber());
						Constants.CONFIG_PROP.setProperty("receivedId",
							String.valueOf(Integer.parseInt(receivedInvoiceData[Constants.RECEIVEDID].toString()) + 1));
						Constants.CONFIG_PROP.store(
							new FileOutputStream(new File(Constants.APP_PROP.getProperty("config_file"))), new String(
								"receivedId property updated"));
						receivedInvoiceData[Constants.RECEIVEDID] = ih.getInvoiceSeriesCode() + ih.getInvoiceNumber();
					} catch (ValidationException ve) {
						logger.error(
								"A validation error happened during an invoice reception: " + ve.getMessage(),
								ve.getCause());
							throw new ReceiveException(ve.getMessage(),
								"Se ha producido un error de validación durante la recepción de la factura", ve);
					}
					catch (DatabaseOperationException die) {
						logger.error(
							"A data base operation error happened during an invoice reception: " + die.getMessage(),
							die.getCause());
						throw new ReceiveException(die.getMultiKey(),
							"Se ha producido un error en base de datos durante la recepción de la factura", die);
					} catch (IOException ioe) {
						logger.error("A io error happened during an invoice reception: " + ioe.getMessage(), ioe);
						throw new ReceiveException("NOOKMessageProcessReceive",
							"A io error happened during an invoice reception", ioe);
					} catch (Exception e) {
						logger.error("An error happened during an invoice reception: " + e.getMessage(), e);
						throw new ReceiveException("NOOKMessageProcessReceive",
							"An error happened during an invoice reception", e);
					}
				}
				return receivedInvoiceData;
			} catch (ReceiveException e) {
				throw e;
			} catch (Exception e) {
				throw new ReceiveException(e);
			}
		} else {
			logger.error(Constants.LANG.getString("NOOKMessageFileExtension"));
			throw new ReceiveException(Constants.LANG.getString("NOOKMessageFileExtension"));
		}
	}
}
