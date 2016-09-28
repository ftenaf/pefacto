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
package es.mityc.appfacturae.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.xml.sax.SAXParseException;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.exceptions.DuplicatedInvoiceException;
import es.mityc.appfacturae.facturae.AccountType;
import es.mityc.appfacturae.facturae.Address;
import es.mityc.appfacturae.facturae.AdministrativeCentreType;
import es.mityc.appfacturae.facturae.AssigneeType;
import es.mityc.appfacturae.facturae.BatchType;
import es.mityc.appfacturae.facturae.BusinessType;
import es.mityc.appfacturae.facturae.ContactDetailsType;
import es.mityc.appfacturae.facturae.CountryType;
import es.mityc.appfacturae.facturae.Entity;
import es.mityc.appfacturae.facturae.FactoringAssignmentDataType;
import es.mityc.appfacturae.facturae.FileHeaderType;
import es.mityc.appfacturae.facturae.IndividualType;
import es.mityc.appfacturae.facturae.InstallmentType;
import es.mityc.appfacturae.facturae.InstallmentsType;
import es.mityc.appfacturae.facturae.InvoiceHeaderType;
import es.mityc.appfacturae.facturae.InvoiceLineType;
import es.mityc.appfacturae.facturae.InvoiceType;
import es.mityc.appfacturae.facturae.LegalEntityType;
import es.mityc.appfacturae.facturae.TaxIdentificationType;
import es.mityc.appfacturae.facturae32.AddressType;
import es.mityc.appfacturae.facturae32.OverseasAddressType;
import es.mityc.appfacturae.hibernate.auxClass.Action;
import es.mityc.appfacturae.hibernate.auxClass.AttachedDocument;
import es.mityc.appfacturae.hibernate.auxClass.EnumOperationType;
import es.mityc.appfacturae.hibernate.auxClass.FACeSentResult;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.hibernate.auxClass.Operation;
import es.mityc.appfacturae.hibernate.auxClass.Tax;
import es.mityc.appfacturae.hibernate.auxClass.xmlData;
import es.mityc.appfacturae.utils.DatabaseUtil;
import es.mityc.appfacturae.utils.IntermediaryUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.appfacturae.utils.io.StreamUtil;
import es.mityc.facturae.utils.MarshalException;
import es.mityc.facturae.utils.MarshallerUtil;
import es.mityc.facturae.utils.ValidationException;
import es.mityc.facturae.utils.ValidatorUtil;

public class FacturaeManager {

	private static Log logger = LogFactory.getLog(FacturaeManager.class);

	// Singleton
	private static FacturaeManager fm = null;

	private static Session session = null;

	private FacturaeManager() {
		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory();
		}

	}

	public static FacturaeManager getInstance() {
		if (fm == null || session == null || !session.isOpen())
			fm = new FacturaeManager();

		return fm;
	}

	public static void reset() {
		if (!session.isOpen())
			session = HibernateUtil.getSessionFactory();
		session.createSQLQuery("SHUTDOWN");
		if (session.isOpen())
			session.close();
		session = null;
		HibernateUtil.reset();
		fm = null;
	}

	public void beginTransaction() {
		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory();
			logger.info("The session has been opened");
		}
		try {
			session.beginTransaction();
		} catch (HibernateException e) {
			logger.error("The session is in use: " + e.getMessage(), e);
			return;
		}

		logger.info("The transaction has been initiated");
	}

	public void commit() {
		if (session.getTransaction().isActive()) {
			session.getTransaction().commit();
			logger.info("Commit done!");
			// The session has to be closed in order to create a new one (one
			// session per transaction)
			session.close();
		}
	}

	private void rollback() {
		if (session.getTransaction().isActive()) {
			session.getTransaction().rollback();
			logger.info("Rollback done!");
			// The session has to be closed in order to create a new one (one
			// session per transaction)
			session.close();
		}
	}

	public SQLQuery executeQuery(String query) {
		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory();
			logger.info("The session has been opened");
		}

		return session.createSQLQuery(query);
	}

	public int executeUpdate(String query) {
		int resultUpdate = session.createSQLQuery(query).executeUpdate();
		commit();
		return resultUpdate;
	}

	/**
	 * Initializes data base with operation types, if it is empty
	 */
	public void setOperationTypes() throws DatabaseOperationException {
		try {
			beginTransaction();
			Query s = session.createSQLQuery("SELECT CODE FROM OPERATION");
			List<?> ls = s.list();
			EnumOperationType[] opTypes = EnumOperationType.values();
			if (ls.size() < opTypes.length) {
				Operation o = null;
				for (int i = ls.size(); i < opTypes.length; i++) {
					o = EnumOperationType.getOperation(opTypes[i]);
					if (o != null)
						saveOperation(o);
				}
			}
			// if (opTypes.length > 0) --> Hay que cerrar la transacción
			// commit(); --> El salvado de una operación ya realiza el commit
		} catch (Exception e) {
			logger.error("Error during operations save: " + e.getMessage(), e);
			rollback();
			throw new DatabaseOperationException("Error during operations save", e);
		}

	}

	public void commitAndClose() {
		Thread th = new Thread(new Runnable() {
			public void run() {
				if (session.isOpen()) {
					commit();
					while (session.isOpen() && session.isDirty()) {
						try {
							Thread.sleep(333);
						} catch (InterruptedException e) {
						}
					}
					reset();
				}
			}
		});
		th.start();
	}

	public void saveAttachment(AttachedDocument ad) throws DatabaseOperationException {
		try {
			beginTransaction();
			logger.info("Saving an attached document");
			SQLQuery s;
			s = session.createSQLQuery("SELECT * FROM ATTACHMENT WHERE PATH =" + DatabaseUtil.escapeWithMarks(ad.getPath()) 
				+ " AND INVOICE = '" + ad.getInvoiceId() + "'");
			// If the element is already contained in the Data Base it is not saved
			List<?> ls = s.list();
			if (ls.size() == 0) {
				session.save(ad);
				commit();
			}
		} catch (Exception e) {
			logger.error("An error has been produced during attachment save: " + e.getMessage(), e);
			rollback();
			throw new DatabaseOperationException("NOOKMesaggeAttachAdded",
					"An error has been produced during attachment save", e);
		}
	}

	public void saveAttachmentNoTrans(AttachedDocument ad) {
		session.save(ad);
		session.flush();
	}

	public void deleteAttachment(AttachedDocument ad) throws DatabaseOperationException {
		try {
			beginTransaction();
			logger.info("Deleting an attached document");
			SQLQuery s = fm.executeQuery("SELECT ID FROM ATTACHMENT WHERE PATH = " + DatabaseUtil.escapeWithMarks(ad.getPath())
				+ " AND EXTENSION = " + DatabaseUtil.escapeWithMarks(ad.getExtension()) 
				+ " AND DESCRIPTION = " + DatabaseUtil.escapeWithMarks(ad.getDescription())
				+ " AND INCLUDED= '" + ad.getIsIncluded() + "' AND INVOICE = '" + ad.getInvoiceId() + "'");
			// If the element is contained in the Data Base it is deleted
			List<?> ls = s.list();
			if (ls.size() > 0) {
				ad.setId(Integer.parseInt(ls.get(0).toString()));
				session.delete(ad);
				commit();
			}
		} catch (Exception e) {
			logger.error("An error has been produced during attachment delete: " + e.getMessage(), e);
			rollback();
			throw new DatabaseOperationException("NOOKMesaggeAttachDeleted",
					"An error has been produced during attachment delete", e);
		}
	}

	public void deleteAttachmentNoTrans(AttachedDocument ad) {
		session.delete(ad);
		session.flush();
	}

	public void saveOperation(Operation o) throws DatabaseOperationException {
		try {
			beginTransaction();
			logger.info("Saving a new operation type");
			SQLQuery s = session.createSQLQuery("SELECT CODE FROM OPERATION WHERE CODE ='" + o.getCode() + "'");
			// If the element is already contained in the Data Base it is not
			// saved
			List<?> ls = s.list();
			if (ls.size() == 0) {
				session.save(o);
				commit();
			}
		} catch (Exception e) {
			logger.error("An error has been produced during operation save: " + e.getMessage(), e);
			rollback();
			throw new DatabaseOperationException("An error has been produced during operation save", e);
		}
	}

	public void saveFACeSentResult(FACeSentResult fsr) throws DatabaseOperationException {
		try {
			beginTransaction();
			logger.info("Saving a new FACeSentResult type");
			session.save(fsr);
			commit();
		} catch (Exception e) {
			logger.error("An error has been produced during FACeSentResult save: " + e.getMessage(), e);
			rollback();
			throw new DatabaseOperationException("An error has been produced during FACeSentResult save", e);
		}
	}

	public int saveAccount(AccountType at, String version) throws DatabaseOperationException {
		try {
			SQLQuery s = null, s2 = null;
			int idAccount = -1;
			String bankCodeQuery = " AND BANK_CODE='" + at.getBankCode() + "'",
					branchCodeQuery = " AND BRANCH_CODE='" + at.getBranchCode() + "'", addressQuery = "";
			if (at.getBankCode() == null || "".equals(at.getBankCode().trim())) {
				bankCodeQuery = " AND BANK_CODE IS NULL";
			}
			if (at.getBranchCode() == null || "".equals(at.getBranchCode().trim())) {
				branchCodeQuery = " AND BRANCH_CODE IS NULL";
			}

			if (at.getGeneralAddress() != null) {

				s2 = addressExist(at.getAddressType(), version, at.getGeneralAddress());
				if (s2 != null) {
					List<?> ls2 = s2.list();
					if (ls2.size() == 0) {
						session.save(at.getGeneralAddress());
						session.save(at);
						session.flush();
					} else {
						at.getGeneralAddress().setId(Integer.valueOf(ls2.get(0).toString()));
						addressQuery = " AND ADDRESS='" + ls2.get(0).toString() + "'";
						s = session.createSQLQuery("SELECT ACCOUNT_ID FROM ACCOUNT WHERE IBAN ='" + at.getIban() + "'"
								+ bankCodeQuery + branchCodeQuery + addressQuery);
						List<?> ls = s.list();
						if (ls != null && ls.size() > 0) {
							idAccount = Integer.valueOf(String.valueOf(ls.get(0)));
						} else {
							session.save(at);
							session.flush();
						}
					}
				}

			} else {
				addressQuery = " AND ADDRESS IS NULL";
				s = session.createSQLQuery("SELECT ACCOUNT_ID FROM ACCOUNT WHERE IBAN ='" + at.getIban() + "'"
						+ bankCodeQuery + branchCodeQuery + addressQuery);
				List<?> ls = s.list();
				if (ls != null && ls.size() > 0) {
					idAccount = Integer.valueOf(String.valueOf(ls.get(0)));
				} else {
					session.save(at);
					session.flush();
				}
			}
			return idAccount;
		} catch (Exception e) {
			logger.error("An error has been produced during account save: " + e.getMessage(), e);
			throw new DatabaseOperationException("An error has been produced during account save", e);
		}

	}

	public int saveParty(es.mityc.appfacturae.facturae.BusinessType b, String version)
			throws DatabaseOperationException {
		boolean newTransaction = false;
		try {
			logger
				.info("Saving a party, including address, contact details and registration data in legal entity case");
			if (!session.getTransaction().isActive()) {
				beginTransaction();
				newTransaction = true;
			}
			SQLQuery s, s2;
			// Auxiliar
			String addressType = "";
			// TaxIdentifications
			s = session.createSQLQuery("SELECT TAX_ID_NUMBER FROM TAX_IDENTIFICATION WHERE TAX_ID_NUMBER='"
				+ b.getTaxIdentification().getTaxIdentificationNumber() + "'");
			// If the element is already contained in the Data Base it is not saved.
			List<?> ls = s.list();
			if (ls.size() == 0)
				session.save(b.getTaxIdentification());

			// Administrative Centres
			Address centreA = null;
			ContactDetailsType centreCd = null;
			String centreAddressType = "";
			int centreIdAddress = -1;
			int centreIdContact = -1;
			boolean isChangedAdmCentre = false;

			if (b.getAdministrativeCentres() != null && b.getAdministrativeCentres().getAdministrativeCentre() != null
				&& b.getAdministrativeCentres().getAdministrativeCentre().size() > 0) {
				for (int i = 0; i < b.getAdministrativeCentres().getAdministrativeCentre().size(); i++) {
					AdministrativeCentreType act = null;
					if (version.equals(Constants.VERSION321))
						act = new es.mityc.appfacturae.facturae321.AdministrativeCentreType();
					else if (version.equals(Constants.VERSION32))
						act = new es.mityc.appfacturae.facturae32.AdministrativeCentreType();
					act = b.getAdministrativeCentres().getAdministrativeCentre().get(i);

					centreAddressType = act.getAddressType();
					centreA = act.getGeneralAddress();
					s2 = addressExist(centreAddressType, version, centreA);
					List<?> ls2 = s2.list();
					if (s2 != null && ls2.size() == 0) {
						session.save(centreA);
						centreIdAddress = 0;
					} else {
						centreIdAddress = Integer.valueOf(ls2.get(0).toString());
						if (centreIdAddress != act.getGeneralAddress().getId()) {
							act.getGeneralAddress().setId(centreIdAddress);
							centreIdAddress = 0;
						}
					}
					centreCd = act.getContactDetails();
					// Contact Details
					if (centreCd != null) {
						String telephoneQuery = "TELEPHONE =" + DatabaseUtil.escapeWithMarks(centreCd.getTelephone()); 
						String faxQuery = "AND FAX=" + DatabaseUtil.escapeWithMarks(centreCd.getTeleFax()); 
						String webQuery = "AND WEB=" + DatabaseUtil.escapeWithMarks(centreCd.getWebAddress()); 
						String mailQuery = "AND EMAIL=" + DatabaseUtil.escapeWithMarks(centreCd.getElectronicMail()); 
						String contactPersonQuery = "AND CONTACT_PERSON=" + DatabaseUtil.escapeWithMarks(centreCd.getContactPersons());
						String cnocnaeQuery = "AND CNO_CNAE=" + DatabaseUtil.escapeWithMarks(centreCd.getCnoCnae()); 
						String inetowncodeQuery = "AND INE_TOWN_CODE=" + DatabaseUtil.escapeWithMarks(centreCd.getIneTownCode()); 
						String additionalDataContQuery = "AND ADDITIONAL_DATA=" + DatabaseUtil.escapeWithMarks(centreCd.getAdditionalContactDetails());
						if (centreCd.getTelephone() == null)
							telephoneQuery = "TELEPHONE IS NULL";
						if (centreCd.getTeleFax() == null)
							faxQuery = "AND FAX IS NULL";
						if (centreCd.getWebAddress() == null)
							webQuery = "AND WEB IS NULL";
						if (centreCd.getElectronicMail() == null)
							mailQuery = "AND EMAIL IS NULL";
						if (centreCd.getContactPersons() == null)
							contactPersonQuery = "AND CONTACT_PERSON IS NULL";
						if (centreCd.getCnoCnae() == null)
							cnocnaeQuery = "AND CNO_CNAE IS NULL";
						if (centreCd.getIneTownCode() == null)
							inetowncodeQuery = "AND INE_TOWN_CODE IS NULL";
						if (centreCd.getAdditionalContactDetails() == null)
							additionalDataContQuery = "AND ADDITIONAL_DATA IS NULL";
						s2 = session.createSQLQuery("SELECT CONTACT_ID FROM CONTACT_DETAILS WHERE " + telephoneQuery
							+ " " + faxQuery + " " + webQuery + " " + mailQuery + " " + contactPersonQuery + " "
							+ cnocnaeQuery + " " + inetowncodeQuery + " " + additionalDataContQuery);
						ls2 = s2.list();
						if (ls2.size() == 0) {
							session.save(centreCd);
							centreIdContact = 0;
						} else {
							centreIdContact = Integer.valueOf(ls2.get(0).toString());
							if (centreIdContact != act.getContactDetails().getId()) {
								act.getContactDetails().setId(centreIdContact);
								centreIdContact = 0;
							}
						}
					}
					if (centreIdAddress == 0 || centreIdContact == 0) {
						session.save(act);
						isChangedAdmCentre = true;
					} else {
						String codeQuery = "CENTRE_CODE =" + DatabaseUtil.escapeWithMarks(act.getCentreCode());
						String roleQuery = "AND ROLE_TYPE_CODE=" + DatabaseUtil.escapeWithMarks(act.getRoleTypeCode());
						String nameQuery = "AND NAME=" + DatabaseUtil.escapeWithMarks(act.getName());
						String firstSurnameQuery = "AND FIRST_SURNAME=" + DatabaseUtil.escapeWithMarks(act.getFirstSurname());
						String secondSurnameQuery = "AND SECOND_SURNAME=" + DatabaseUtil.escapeWithMarks(act.getSecondSurname());
						String addressQuery = "AND ADDRESS='" + centreIdAddress + "'";
						String contactQuery = "AND CONTACT='" + centreIdContact + "'";
						String physicalGlnQuery = "AND PHYSICAL_GLN='" + act.getPhysicalGLN() + "'";
						String logicaOperationPointQuery = "AND LOGICAL_OPERATION_POINT='" + act.getLogicalOperationalPoint() + "'";
						String descriptionQuery = "";
						boolean isCentreDescription = true;
						if (version.equals(Constants.VERSION32))
							isCentreDescription = ((es.mityc.appfacturae.facturae32.AdministrativeCentreType) act).getCentreDescription() != null;
						else if  (version.equals(Constants.VERSION321))
							isCentreDescription = ((es.mityc.appfacturae.facturae321.AdministrativeCentreType) act).getCentreDescription() != null;
						if (!isCentreDescription) {
							descriptionQuery = "AND CENTRE_DESCRIPTION IS NULL";
						}else{
							String centreDescription ="";
							if (version.equals(Constants.VERSION32))
								centreDescription = ((es.mityc.appfacturae.facturae32.AdministrativeCentreType) act).getCentreDescription();
							else if  (version.equals(Constants.VERSION321))
								centreDescription = ((es.mityc.appfacturae.facturae321.AdministrativeCentreType) act).getCentreDescription();
							descriptionQuery = "AND CENTRE_DESCRIPTION = " + DatabaseUtil.escapeWithMarks(centreDescription);
						}
						if (act.getCentreCode() == null)
							codeQuery = "CENTRE_CODE IS NULL";
						if (act.getRoleTypeCode() == null)
							roleQuery = "AND ROLE_TYPE_CODE IS NULL";
						if (act.getName() == null)
							nameQuery = "AND NAME IS NULL";
						if (act.getFirstSurname() == null)
							firstSurnameQuery = "AND FIRST_SURNAME IS NULL";
						if (act.getSecondSurname() == null)
							secondSurnameQuery = "AND SECOND_SURNAME IS NULL";
						if (centreIdAddress == -1)
							addressQuery = "AND ADDRESS IS NULL";
						if (centreIdContact == -1)
							contactQuery = "AND CONTACT IS NULL";
						if (act.getPhysicalGLN() == null)
							physicalGlnQuery = "AND PHYSICAL_GLN IS NULL";
						if (act.getLogicalOperationalPoint() == null)
							logicaOperationPointQuery = "AND LOGICAL_OPERATION_POINT IS NULL";
						s2 = session.createSQLQuery("SELECT ADMCENTRE_ID FROM ADMCENTRE WHERE " + codeQuery + " "
							+ roleQuery + " " + nameQuery + " " + firstSurnameQuery + " " + secondSurnameQuery + " "
							+ addressQuery + " " + contactQuery + " " + physicalGlnQuery + " "
							+ logicaOperationPointQuery + " " + descriptionQuery);
						ls2 = s2.list();
						if (ls2.size() == 0) {
							session.save(act);
							isChangedAdmCentre = true;
						} else {
							if (act.getId() != Integer.valueOf(ls2.get(0).toString())) {
								act.setId(Integer.valueOf(ls2.get(0).toString()));
								isChangedAdmCentre = true;
							}
						}
					}
				}
			}

			// Individual => Address - ContactDetails - Entity
			// LegalEntity => Address - ContactDetails - RegistrationData - Entity
			Address a = null;
			ContactDetailsType cd = null;
			Entity e = null;
			int idAddress = -1, idRegistration = -1, idContact = -1;
			if (b.isIndividualType()) {
				IndividualType ib = b.getIndividual();
				a = ib.getGeneralAddress();
				cd = ib.getContactDetails();
				e = ib;
				addressType = ib.getAddressType();
			} else {
				LegalEntityType lb = b.getLegalEntity();
				a = lb.getGeneralAddress();
				cd = lb.getContactDetails();
				e = lb;
				addressType = lb.getAddressType();
				// Registration Data
				if (lb.getRegistrationData() != null) {
					String bookQuery = "BOOK=" + DatabaseUtil.escapeWithMarks(lb.getRegistrationData().getBook());
					String locationQuery = "AND LOCATION=" + DatabaseUtil.escapeWithMarks(lb.getRegistrationData().getRegisterOfCompaniesLocation());
					String sheetQuery = "AND SHEET=" + DatabaseUtil.escapeWithMarks(lb.getRegistrationData().getSheet());
					String folioQuery = "AND FOLIO=" + DatabaseUtil.escapeWithMarks(lb.getRegistrationData().getFolio());
					String sectionQuery = "AND SECTION=" + DatabaseUtil.escapeWithMarks(lb.getRegistrationData().getSection());
					String volumeQuery = "AND VOLUME=" + DatabaseUtil.escapeWithMarks(lb.getRegistrationData().getVolume());
					String additionalDataRegQuery = "AND ADDITIONAL_DATA=" + DatabaseUtil.escapeWithMarks(lb.getRegistrationData().getAdditionalRegistrationData());
					if (lb.getRegistrationData().getBook() == null)
						bookQuery = "BOOK IS NULL";
					if (lb.getRegistrationData().getRegisterOfCompaniesLocation() == null)
						locationQuery = "AND LOCATION IS NULL";
					if (lb.getRegistrationData().getSheet() == null)
						sheetQuery = "AND SHEET IS NULL";
					if (lb.getRegistrationData().getFolio() == null)
						folioQuery = "AND FOLIO IS NULL";
					if (lb.getRegistrationData().getSection() == null)
						sectionQuery = "AND SECTION IS NULL";
					if (lb.getRegistrationData().getVolume() == null)
						volumeQuery = "AND VOLUME IS NULL";
					if (lb.getRegistrationData().getAdditionalRegistrationData() == null)
						additionalDataRegQuery = "AND ADDITIONAL_DATA IS NULL";
					s2 = session.createSQLQuery("SELECT REGISTRATION_ID FROM REGISTRATION_DATA WHERE " + bookQuery
						+ " " + locationQuery + " " + sheetQuery + " " + folioQuery + " " + sectionQuery + " "
						+ volumeQuery + " " + additionalDataRegQuery);
					List<?> ls2 = s2.list();
					if (ls2.size() == 0) {
						session.save(lb.getRegistrationData());
						idRegistration = 0;
					} else {
						idRegistration = Integer.valueOf(ls2.get(0).toString());
						lb.getRegistrationData().setId(idRegistration);
						e = lb;
					}
				}
			}

			// Address
			s2 = addressExist(addressType, version, a);
			List<?> ls2 = s2.list();
			if (ls2 != null && ls2.size() == 0) {
				session.save(a);
				idAddress = 0;
			} else {
				idAddress = Integer.valueOf(ls2.get(0).toString());
				e.getGeneralAddress().setId(idAddress);
			}

			// Contact Details
			if (cd != null) {
				String telephoneQuery = "TELEPHONE =" + DatabaseUtil.escapeWithMarks(cd.getTelephone());
				String faxQuery = "AND FAX=" + DatabaseUtil.escapeWithMarks(cd.getTeleFax());
				String webQuery = "AND WEB=" + DatabaseUtil.escapeWithMarks(cd.getWebAddress());
				String mailQuery = "AND EMAIL=" + DatabaseUtil.escapeWithMarks(cd.getElectronicMail());
				String contactPersonQuery = "AND CONTACT_PERSON=" + DatabaseUtil.escapeWithMarks(cd.getContactPersons());
				String cnocnaeQuery = "AND CNO_CNAE=" + DatabaseUtil.escapeWithMarks(cd.getCnoCnae());
				String inetowncodeQuery = "AND INE_TOWN_CODE=" + DatabaseUtil.escapeWithMarks(cd.getIneTownCode());
				String additionalDataContQuery = "AND ADDITIONAL_DATA=" + DatabaseUtil.escapeWithMarks(cd.getAdditionalContactDetails());
				if (cd.getTelephone() == null)
					telephoneQuery = "TELEPHONE IS NULL";
				if (cd.getTeleFax() == null)
					faxQuery = "AND FAX IS NULL";
				if (cd.getWebAddress() == null)
					webQuery = "AND WEB IS NULL";
				if (cd.getElectronicMail() == null)
					mailQuery = "AND EMAIL IS NULL";
				if (cd.getContactPersons() == null)
					contactPersonQuery = "AND CONTACT_PERSON IS NULL";
				if (cd.getCnoCnae() == null)
					cnocnaeQuery = "AND CNO_CNAE IS NULL";
				if (cd.getIneTownCode() == null)
					inetowncodeQuery = "AND INE_TOWN_CODE IS NULL";
				if (cd.getAdditionalContactDetails() == null)
					additionalDataContQuery = "AND ADDITIONAL_DATA IS NULL";
				s2 = session.createSQLQuery("SELECT CONTACT_ID FROM CONTACT_DETAILS WHERE " + telephoneQuery + " "
					+ faxQuery + " " + webQuery + " " + mailQuery + " " + contactPersonQuery + " " + cnocnaeQuery + " "
					+ inetowncodeQuery + " " + additionalDataContQuery);
				ls2 = s2.list();
				if (ls2.size() == 0) {
					session.save(cd);
					idContact = 0;
				} else {
					idContact = Integer.valueOf(ls2.get(0).toString());
					e.getContactDetails().setId(idContact);
				}
			}

			// Party 
			// The entity (Individual or LegalEntity) is automatically saved due to the one-to-one relationship
			String idContactQuery = "AND CONTACT='" + idContact + "'";
			String idRegistrationQuery = "AND REGISTRATION_DATA='" + idRegistration + "'";
			if (idContact == -1)
				idContactQuery = "AND CONTACT IS NULL";
			if (idRegistration == -1)
				idRegistrationQuery = "AND REGISTRATION_DATA IS NULL";
			if(b.getGeneralEntity().getId() <= 0) { // Si el tercero no tiene identificador válido.
				s = FacturaeManager.getInstance().executeQuery("SELECT MAX(PARTY_ID) FROM PARTY");
				ls = s.list();
				if (ls != null && ls.size() > 0 && ls.get(0) != null)
					b.getGeneralEntity().setId((Integer) ls.get(0) + 1);
				else
					b.getGeneralEntity().setId(1);
				s = null;
			}
			if (b.isIndividualType()) {
				b.setIndividual((IndividualType) e);
				if (idContact == 0 || idAddress == 0) {
					session.save(b);
					session.flush();
				} else {
					String secondSurnameQuery = "AND SECOND_SURNAME=" + DatabaseUtil.escapeWithMarks(((IndividualType) e).getSecondSurname());
					if (((IndividualType) e).getSecondSurname() == null)
						secondSurnameQuery = "AND SECOND_SURNAME IS NULL";
					s = session.createSQLQuery("SELECT PARTY_ID FROM INDIVIDUAL WHERE NAME ='"
						+ ((IndividualType) e).getName() + "' AND FIRST_SURNAME='"
						+ ((IndividualType) e).getFirstSurname() + "' " + secondSurnameQuery + " AND ADDRESS='"
						+ idAddress + "' " + idContactQuery);
				}
			} else {
				b.setLegalEntity((LegalEntityType) e);
				if (idContact == 0 || idAddress == 0 || idRegistration == 0) {
					session.save(b);
					session.flush();
				} else {
					String corporateName = ((LegalEntityType) e).getCorporateName();
					String tradeNameQuery = "AND TRADE_NAME=" + DatabaseUtil.escapeWithMarks(((LegalEntityType) e).getTradeName());
					if (((LegalEntityType) e).getTradeName() == null) {
						tradeNameQuery = "AND TRADE_NAME IS NULL";
					}
					s = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_LEGAL_ENTITY_BY_CORPORATE_NAME +
						DatabaseUtil.escapeWithMarks(corporateName) + " " + tradeNameQuery + " " + idRegistrationQuery
						+ " AND ADDRESS='" + idAddress + "' " + idContactQuery);					
				}
			}

			if (s != null) {
				ls = s.list();
				if (ls.size() == 0 || isChangedAdmCentre) {
					session.save(b);
					session.flush();
				} else {
					if (newTransaction)
						commit();
					if (b.getId() == 0) {
						int id = Integer.valueOf(ls.get(0).toString());
						b.setId(id);
						if(b.isIndividualType()) {
							b.getIndividual().setId(id);
						} else {
							b.getLegalEntity().setId(id);
						}
					}
					return b.getId();
				}
			}
			if (newTransaction)
				commit();
			return 0;
		} catch (Exception e) {
			logger.error("An error has been produced during party save: " + e.getMessage(), e);
			if (newTransaction)
				rollback();
			throw new DatabaseOperationException("NOOKMessageSave", "An error has been produced during party save", e);
		}
	}

	public String deleteParty(int id, boolean isIndividual, String version) throws DatabaseOperationException {
		boolean newTransaction = false;
		try {
			logger.info("Deleting a party");
			if (!session.getTransaction().isActive()) {
				beginTransaction();
				newTransaction = true;
			}
			SQLQuery s = null, s2 = null, s3 = null, s4 = null, s5 = null, s6 = null, s7 = null, s8 = null, s9 = null;
			// Check if the party exists
			if (!isIndividual)
				s = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_BY_ID_NOT_INDIVIDUAL + id);
			else
				s = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_BY_ID + id);

			List<?> ls = s.list();
			if (ls != null && ls.size() > 0) {
				//If it exists, find out if this party is used in some invoice
				s2 = session.createSQLQuery(FacturaeStatics.QUERY_FACTURAE_ID_BY_PARTY_ID_SELLER + id + " OR PARTY_ID_BUYER=" + id);
				List<?> ls2 = s2.list();
				if (ls2.size() > 0) {
					// It can't be deleted because it is used by some invoices
					logger.info("Party used in some invoices, It can't be deleted");
					return "NOOKMessageDeleteUsed";
				} else {
					// Administrative Centres
					s3 = executeQuery(FacturaeStatics.QUERY_PARTY_ADMCENTRE_BY_ID + id);
					List<?> centresId = s3.list();
					List<Integer> addressesId = new ArrayList<Integer>();
					List<Integer> contactId = new ArrayList<Integer>();

					for (int i = 0; i < centresId.size(); i++) {
						s4 = executeQuery(FacturaeStatics.QUERY_PARTY_ADMCENTRE_BY_ADMCENTRE_ID + centresId.get(i).toString() + " AND PARTY_ID <>" + id);
						List<?> lsAdm = s4.list();
						if (lsAdm != null && lsAdm.size() > 0) {
							logger.info("Adm. Centre used in some party, It can't be deleted");
						} else {
							s5 = executeQuery(FacturaeStatics.QUERY_ADDRESS_CONTACT_ADMCENTRE_BY_ID+ centresId.get(i).toString());
							List<?> lsAdmAddr = s5.list();
							if (lsAdmAddr != null && lsAdmAddr.size() > 0) {
								if (((Object[]) lsAdmAddr.get(0))[0] != null) {
									addressesId.add(Integer.valueOf(((Object[]) lsAdmAddr.get(0))[0].toString()));
								}
								if (((Object[]) lsAdmAddr.get(0))[1] != null) {
									contactId.add(Integer.valueOf(((Object[]) lsAdmAddr.get(0))[1].toString()));
								}
							}

							AdministrativeCentreType act = null;
							if (version.equals(Constants.VERSION321)){
								act = new es.mityc.appfacturae.facturae321.AdministrativeCentreType();							
							}else if (version.equals(Constants.VERSION32)){
								act = new es.mityc.appfacturae.facturae32.AdministrativeCentreType();
							}
							act.setId(Integer.valueOf(centresId.get(i).toString()));
							session.delete(act);
							List<?> ls6, ls7, ls8, ls9;
							for (int j = 0; j < addressesId.size(); j++) {
								s6 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_LEGAL_ENTITY_BY_ADDRESS + addressesId.get(j).toString());
								s7 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_INDIVIDUAL_BY_ADDRESS + addressesId.get(j).toString());
								s8 = session.createSQLQuery(FacturaeStatics.QUERY_ACCOUNT_ID_BY_ADDRESS + addressesId.get(j).toString());
								s9 = session.createSQLQuery(FacturaeStatics.QUERY_ADMCENTRE_BY_ID + addressesId.get(j).toString() 
									+ " AND ADMCENTRE_ID<>" + centresId.get(i).toString());
								ls6 = s6.list();
								ls7 = s7.list();
								ls8 = s8.list();
								ls9 = s9.list();
								if (ls6.size() == 0 && ls7.size() == 0 && ls8.size() == 0 && ls9.size() == 0) {
									es.mityc.appfacturae.facturae.Address a = new es.mityc.appfacturae.facturae32.AddressType();
									a.setId(Integer.parseInt(addressesId.get(j).toString()));
									((es.mityc.appfacturae.facturae32.AddressType) a).setAddress("");
									((es.mityc.appfacturae.facturae32.AddressType) a).setCountryCode(CountryType.ABW);
									((es.mityc.appfacturae.facturae32.AddressType) a).setProvince("");
									((es.mityc.appfacturae.facturae32.AddressType) a).setTown("");
									session.delete(a);
								} else {
									// It can't be deleted because it is used by
									// some invoices
									logger.info("Address is used, It can't be deleted");
								}
							}

							for (int j = 0; j < contactId.size(); j++) {
								s6 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_LEGAL_ENTITY_BY_CONTACT + contactId.get(j).toString());
								s7 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_INDIVIDUAL_BY_CONTACT + contactId.get(j).toString());
								s8 = session.createSQLQuery(FacturaeStatics.QUERY_ADMCENTRE_BY_CONTACT
										+ contactId.get(j).toString() + " AND ADMCENTRE_ID<>" + centresId.get(i).toString());
								ls6 = s6.list();
								ls7 = s7.list();
								ls8 = s8.list();
								if (ls6.size() == 0 && ls7.size() == 0 && ls8.size() == 0) {
									es.mityc.appfacturae.facturae.ContactDetailsType c = new es.mityc.appfacturae.facturae.ContactDetailsType();
									c.setId(Integer.parseInt(contactId.get(j).toString()));
									session.delete(c);
								} else {
									// It can't be deleted because it is used by some parties
									logger.info("Contact details are used, they can't be deleted");
								}
							}
						}
					}

					// If it is not used by a facturae invoice, it can be deleted
					Object[] o = (Object[]) ls.get(0);
					// Empty address
					Address addemp = new es.mityc.appfacturae.facturae32.AddressType();
					addemp.setId(-1);
					BusinessType b = new BusinessType();
					b.setId(Integer.parseInt(o[0].toString()));
					// 1st I or LE
					if (!isIndividual) {
						es.mityc.appfacturae.facturae.LegalEntityType le = new es.mityc.appfacturae.facturae.LegalEntityType();
						le.setId(Integer.parseInt(o[0].toString()));
						le.setCorporateName("");
						le.setGeneralAddress(addemp);
						b.setLegalEntity(le);
					} else {
						es.mityc.appfacturae.facturae.IndividualType ind = new es.mityc.appfacturae.facturae.IndividualType();
						ind.setId(Integer.parseInt(o[0].toString()));
						ind.setName("");
						ind.setFirstSurname("");
						ind.setGeneralAddress(addemp);
						b.setIndividual(ind);
					}
					session.delete(b);
					session.flush();
					// 2nd R,A,C
					if (o.length == 5 && o[4] != null) {
						s2 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_LEGAL_ENTITY_BY_REGISTRATION_DATA
							+ o[4].toString() + " AND PARTY_ID<>" + o[0].toString());
						ls2 = s2.list();
						if (ls2.size() == 0) {
							es.mityc.appfacturae.facturae.RegistrationDataType r = new es.mityc.appfacturae.facturae.RegistrationDataType();
							r.setId(Integer.parseInt(o[4].toString()));
							session.delete(r);
						} else {
							// It can't be deleted because it is used by some
							// parties
							logger.info("Registration data are used in some parties, they can't be deleted");
						}
					}
					if (o[2] != null) {
						s2 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_LEGAL_ENTITY_BY_CONTACT
							+ o[2].toString() + " AND PARTY_ID<>" + o[0].toString());
						s3 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_INDIVIDUAL_BY_CONTACT + o[2].toString()
							+ " AND PARTY_ID<>" + o[0].toString());
						s4 = session.createSQLQuery(FacturaeStatics.QUERY_ADMCENTRE_BY_CONTACT + o[2].toString());
						ls2 = s2.list();
						List<?> ls3 = s3.list();
						List<?> ls4 = s4.list();
						if (ls2.size() == 0 && ls3.size() == 0 && ls4.size() == 0) {
							es.mityc.appfacturae.facturae.ContactDetailsType c = new es.mityc.appfacturae.facturae.ContactDetailsType();
							c.setId(Integer.parseInt(o[2].toString()));
							session.delete(c);
						} else {
							// It can't be deleted because it is used by some
							// parties
							logger.info("Contact details are used, they can't be deleted");
						}
					}

					s2 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_LEGAL_ENTITY_BY_ADDRESS + o[3].toString() + " AND PARTY_ID<>" + o[0].toString());
					s3 = session.createSQLQuery(FacturaeStatics.QUERY_PARTY_ID_INDIVIDUAL_BY_ADDRESS + o[3].toString() + " AND PARTY_ID<>" + o[0].toString());
					s4 = session.createSQLQuery(FacturaeStatics.QUERY_ACCOUNT_ID_BY_ADDRESS + o[3].toString());
					s5 = session.createSQLQuery(FacturaeStatics.QUERY_ADMCENTRE_BY_ID + o[3].toString());
					ls2 = s2.list();
					List<?> ls3 = s3.list();
					List<?> ls4 = s4.list();
					List<?> ls5 = s5.list();

					if (ls2.size() == 0 && ls3.size() == 0 && ls4.size() == 0 && ls5.size() == 0) {
						es.mityc.appfacturae.facturae.Address a = new es.mityc.appfacturae.facturae32.AddressType();
						a.setId(Integer.parseInt(o[3].toString()));
						((es.mityc.appfacturae.facturae32.AddressType) a).setAddress("");
						((es.mityc.appfacturae.facturae32.AddressType) a).setCountryCode(CountryType.ABW);
						((es.mityc.appfacturae.facturae32.AddressType) a).setProvince("");
						((es.mityc.appfacturae.facturae32.AddressType) a).setTown("");
						session.delete(a);
					} else {
						// It can't be deleted because it is used by some
						// invoices
						logger.info("Address is used, It can't be deleted");
					}
					// 3rd Tax_Id. It could be used by another party, so it is must be checked
					es.mityc.appfacturae.facturae.TaxIdentificationType t = new es.mityc.appfacturae.facturae.TaxIdentificationType();
					s = session.createSQLQuery("SELECT TAX_IDENTIFICATION FROM PARTY WHERE TAX_IDENTIFICATION='"
						+ o[1].toString() + "'");
					ls = s.list();
					if (ls == null || ls.size() == 0) {
						t.setTaxIdentificationNumber(o[1].toString());
						session.delete(t);
					}

				}
				if (newTransaction) {
					commit();
				}
				return "";
			} else {
				// No existe (es una nueva generacion)
				if (newTransaction) {
					rollback();
				}
				return "NOOKMessageDeleteNoExist";
			}
		} catch (Exception e) {
			logger.error("Error deleting party: " + e.getMessage(), e);
			if (newTransaction) {
				rollback();
			}
			throw new DatabaseOperationException("NOOKMessageDelete", "Error deleting party", e);
		}

	}

	public void saveFacturae(es.mityc.appfacturae.facturae.Facturae fapp, String receivedId)
		throws DatabaseOperationException, ValidationException {
		try {
			if (!session.getTransaction().isActive()) {
				beginTransaction();
			}

			logger.info("Saving a facturae");

			SQLQuery s, s2;
			// This variable controls if the invoice has been already inserted in the Data Base
			boolean contained = false;

			String version = fapp.getFileHeader().getSchemaVersion();

			// Counters
			int cont, cont2, cont3, cont4, cont5;

			/************************* INVOICES *************************/
			// If the facturae file (batch of invoices) contains any invoice previously
			// inserted in the Data Base, it is not added
			cont = fapp.getInvoices().getInvoice().size();
			InvoiceHeaderType iht = null;

			for (int x = 0; x < cont; x++) {

				iht = fapp.getInvoices().getInvoice().get(x).getInvoiceHeader();
				String seriesnumber = "";
				if (iht.getInvoiceSeriesCode() != null) {
					seriesnumber = iht.getInvoiceSeriesCode();
				}
				seriesnumber += iht.getInvoiceNumber();
				s = session.createSQLQuery(
						"SELECT * FROM INVOICE WHERE NVL(SERIES_CODE + NUMBER,NUMBER) ='" + seriesnumber + "'");
				// If the element is already contained in the Data Base it is
				// not saved
				List<?> ls = s.list();
				if (ls.size() > 0 && receivedId == null) {
					contained = true;
					x = fapp.getInvoices().getInvoice().size();
				}

			}

			if (!contained) {

				try {
					MarshallerUtil marshallerUtil = MarshallerUtil.getInstance(FacturaeUtil.getVersionOb(version));
					marshallerUtil.marshal(IntermediaryUtil.getfacturae(fapp), "temp");
				} catch (MarshalException e) {
					logger.error("Marshal error trying to save an invoice on Data Base: " + e.getMessage(), e);
					throw new DatabaseOperationException("NOOKMessageSave",
							"Marshal error trying to save an invoice on Data Base", e);
				}

				// The xml (xsig) file is created in the local file system and
				// it is included like a blob object in the Data Base
				try {
					File f = new File("temp.xsig");
					// If the invoice has not xsig related or is Draft type (overwrite the xsig) 
					if (fapp.getXsig() == null
						|| fapp.getInvoices().getInvoice().get(0).getState() == InvoiceStatusType.D) {
						// The file is read and inserted in a xmlData object as blob type
						FileInputStream fos = new FileInputStream(f);
						byte[] data = new byte[(int) f.length()];
						StreamUtil.readStream(fos, data);
						try
						{ 
							ValidatorUtil vu = ValidatorUtil.getInstance();
							vu.validate(f, version);
							xmlData xsig = new xmlData();
							xsig.createBlob(data);
							fapp.setXsig(xsig);
						} catch (SAXParseException spe) {
							fapp.setXsig(null);
							logger.error("Error validating xml: " + spe.getMessage(), spe);
							throw new ValidationException( Constants.LANG.getString("NOOKSchemeValidation")+ " "+spe.getMessage());
						}
						finally {
							fos.close();
						}
					}

					f.delete();
				} catch (ValidationException ve) {
					throw ve;
				} catch (FileNotFoundException fnfe) {
					logger.error("Error saving a invoice: " + fnfe.getMessage(), fnfe);
				} catch (IOException ioe) {
					logger.error("Error saving a invoice: " + ioe.getMessage(), ioe);
				} catch (Exception e) {
					logger.error("Error saving a invoice: " + e.getMessage(), e);
				} finally {
					if (fapp.getXsig() == null) {
						logger.error("Saved invoice is not properly stored - XML field is empty");
					}
				}

				cont = fapp.getInvoices().getInvoice().size();
				cont2 = 0;

				// It iterates over the invoices
				for (int i = 0; i < cont; i++) {
					/**
					 * Once the internal xml/xsig content is written, it has to be checked if the invoice 
					 * is a received one. In that case, the internal invoice's series and number will be 
					 * overwritten with the corresponding received's series and number.
					 */
					if (receivedId != null) {
						fapp.getInvoices().getInvoice().get(i).getInvoiceHeader()
								.setInvoiceSeriesCode(Constants.LANG.getString("Received"));
						fapp.getInvoices().getInvoice().get(i).getInvoiceHeader().setInvoiceNumber(receivedId);
					}

					// Invoice
					InvoiceType it = fapp.getInvoices().getInvoice().get(i);
					// FOR: It iterates over the invoice lines
					cont2 = it.getItems().getInvoiceLine().size();
					for (int j = 0; j < cont2; j++) {

						// Item
						InvoiceLineType il = it.getItems().getInvoiceLine().get(j);
						boolean existItemId = true;

						if (il.getTaxesPersistence() != null) {
							cont3 = il.getTaxesPersistence().size();
						} else {
							cont3 = 0;
						}

						// FOR: It iterates over the line output taxes
						for (int k = 0; k < cont3; k++) {

							// Tax
							Tax t = il.getTaxesPersistence().get(k);
							if (t.getEquivalenceSurcharge() == null)
								t.setEquivalenceSurcharge(0.0);
							s2 = session.createSQLQuery("SELECT TAX_ID FROM TAX WHERE TYPE=" + DatabaseUtil.escapeWithMarks(t.getTaxTypeCode())
								+ " AND RATE=" + t.getTaxRate() + " AND SURCHARGE=" + t.getEquivalenceSurcharge());
							List<?> ls2 = s2.list();
							if (ls2 != null && ls2.size() > 0) {
								t.setId(Integer.valueOf(ls2.get(0).toString()));
								it.getItems().getInvoiceLine().get(j).getTaxesPersistence().set(k, t);
							} else {
								session.save(t);
								// It is sure the item does not exist
								existItemId = false;
							}

						}

						if (existItemId) {
							String additionalInfoQuery = "AND ADDITIONAL_INFO=" + DatabaseUtil.escapeWithMarks(il.getAdditionalLineItemInformation());
							if (il.getAdditionalLineItemInformation() == null)
								additionalInfoQuery = "AND ADDITIONAL_INFO IS NULL";
							String itemQuery = FacturaeStatics.QUERY_ITEM_ID_BY_DESCRIPTION + DatabaseUtil.escapeWithMarks(il.getItemDescription()) 
								+ " AND PRICE_WITHOUT_TAX='" + il.getUnitPriceWithoutTax() + "' " + additionalInfoQuery 
								+ " AND UNIT='" + il.getUnitOfMeasure() + "'";
							boolean isVersion32x = version.equals(Constants.VERSION321)
								|| version.equals(Constants.VERSION32);
							if (isVersion32x) {
								boolean isSpecialTaxableEvent = false;
								boolean isSpecialTaxableEventCode = false;
								boolean isSpecialTaxableEventReason = false;
								String specialTaxableEventCode = null;
								String specialTaxableEventReason = null;
								String articleCode = null;
								if (version.equals(Constants.VERSION32)) {
									articleCode = ((es.mityc.appfacturae.facturae32.InvoiceLineType) il)
										.getArticleCode();
									isSpecialTaxableEvent = ((es.mityc.appfacturae.facturae32.InvoiceLineType) il)
										.getSpecialTaxableEvent() != null;
									if (isSpecialTaxableEvent) {
										isSpecialTaxableEventCode = ((es.mityc.appfacturae.facturae32.InvoiceLineType) il)
											.getSpecialTaxableEvent().getSpecialTaxableEventCode() != null;
										if (isSpecialTaxableEventCode)
											specialTaxableEventCode = ((es.mityc.appfacturae.facturae32.InvoiceLineType) il)
												.getSpecialTaxableEvent().getSpecialTaxableEventCode();
										isSpecialTaxableEventReason = ((es.mityc.appfacturae.facturae32.InvoiceLineType) il)
											.getSpecialTaxableEvent().getSpecialTaxableEventReason() != null;
										if (isSpecialTaxableEventReason)
											specialTaxableEventReason = ((es.mityc.appfacturae.facturae32.InvoiceLineType) il)
												.getSpecialTaxableEvent().getSpecialTaxableEventReason();
									}
								}
								if (version.equals(Constants.VERSION321)) {
									articleCode = ((es.mityc.appfacturae.facturae321.InvoiceLineType) il)
										.getArticleCode();
									isSpecialTaxableEvent = ((es.mityc.appfacturae.facturae321.InvoiceLineType) il)
										.getSpecialTaxableEvent() != null;
									if (isSpecialTaxableEvent) {
										isSpecialTaxableEventCode = ((es.mityc.appfacturae.facturae321.InvoiceLineType) il)
											.getSpecialTaxableEvent().getSpecialTaxableEventCode() != null;
										if (isSpecialTaxableEventCode)
											specialTaxableEventCode = ((es.mityc.appfacturae.facturae321.InvoiceLineType) il)
												.getSpecialTaxableEvent().getSpecialTaxableEventCode();
										isSpecialTaxableEventReason = ((es.mityc.appfacturae.facturae321.InvoiceLineType) il)
											.getSpecialTaxableEvent().getSpecialTaxableEventReason() != null;
										specialTaxableEventReason = ((es.mityc.appfacturae.facturae321.InvoiceLineType) il)
											.getSpecialTaxableEvent().getSpecialTaxableEventReason();
									}
								}
								String specialCodeQuery = "";
								String specialReasonQuery = "";
								String articleQuery = "AND ARTICLE_CODE = " + DatabaseUtil.escapeWithMarks(articleCode);
								if (isSpecialTaxableEvent) {
									if (isSpecialTaxableEventCode)
										specialCodeQuery = "AND SPECIAL_TAX_EVENT_CODE = " + DatabaseUtil.escapeWithMarks(specialTaxableEventCode);
									else
										specialCodeQuery = "AND SPECIAL_TAX_EVENT_CODE IS NULL";
									if (isSpecialTaxableEventReason)
										specialReasonQuery = "AND SPECIAL_TAX_EVENT_REASON = "+ DatabaseUtil.escapeWithMarks(specialTaxableEventReason);
									else
										specialReasonQuery = "AND SPECIAL_TAX_EVENT_REASON IS NULL";
								}
								if (articleCode == null)
									articleQuery = "AND ARTICLE_CODE IS NULL";
								itemQuery += " " + specialCodeQuery + " " + specialReasonQuery + " " + articleQuery;
							}
							s = session.createSQLQuery(itemQuery);
							List<?> ls = s.list();
							if (ls != null && ls.size() > 0) {
								cont3 = ls.size();
								// FOR: It iterates over the equal items (without taking into account the taxes)
								for (int k = 0; k < cont3; k++) {
									// It is necessary to compare the taxes
									s2 = session.createSQLQuery(FacturaeStatics.QUERY_TAX_ID_BY_ITEM_ID
										+ DatabaseUtil.escapeWithMarks(ls.get(k).toString()));
									List<?> ls2 = s2.list();
									if (ls2 != null && ls2.size() > 0) {
										if (ls2.size() == it.getItems().getInvoiceLine().get(j).getTaxesPersistence()
												.size()) {
											cont4 = ls2.size();
											boolean contained2 = false;
											// FOR: It iterates over the equal
											// line output taxes
											for (int l = 0; l < cont4; l++) {
												cont5 = it.getItems().getInvoiceLine().get(j).getTaxesPersistence()
														.size();
												contained2 = false;
												// FOR: It iterates over the
												// object to save output taxes
												for (int m = 0; m < cont5; m++) {
													if (it.getItems().getInvoiceLine().get(j).getTaxesPersistence()
															.get(m).getId() == Integer.valueOf(ls2.get(l).toString())) {
														contained2 = true;
													}
												}
												if (!contained2) {
													l = cont4;
												}
											}
											if (contained2) {
												// If the items are equal and they have the same output taxes, it is not
												// necessary to save a new item
												il.setId(Integer.valueOf(ls.get(k).toString()));
												it.getItems().getInvoiceLine().set(j, il);
												cont3 = 0;
											} else {
												// If it is the last iteration and the item doesn't exist (different taxes), it is saved
												if (k == cont3 - 1) {
													session.save(il);
													session.flush();
												}
											}
										}
										// If the item doesn't exist (different number of taxes), it is saved unless there is more items to compare
										else {
											if (k == cont3 - 1) {
												session.save(il);
												session.flush();
											}
										}
									}
								}
								// If the item doesn't exist (different taxes), it is saved
								if (cont3 != 0) {
									session.save(il);
									session.flush();
								}
							}
							// If the item doesn't exist (different item attributes), it is saved
							else {
								session.save(il);
								session.flush();
							}
						}
						// If the item doesn't exist (a new tax has been created), it is saved
						else {
							session.save(il);
							session.flush();
						}
					}
					// END FOR: It iterates over the invoice lines

					// Payment
					List<InstallmentType> arrayInstallment = null;

					if (it.getPaymentDetails() != null && it.getPaymentDetails().getInstallment() != null) {
						arrayInstallment = it.getPaymentDetails().getInstallment();
					}

					if (arrayInstallment != null && arrayInstallment.size() > 0 && arrayInstallment.get(0) != null) {
						// FOR: It iterates over the installments
						cont2 = arrayInstallment.size();
						for (int j = 0; j < cont2; j++) {

							// Firstly, the accounts must be saved if they
							// aren't
							AccountType atCredited = null, atDebited = null;
							int idCredited = -1;
							int idDebited = -1;

							// 1 Account to be credited ; 2 Account to be
							// debited
							// Account to be credited
							atCredited = arrayInstallment.get(j).getAccountToBeCredited();

							if (atCredited != null) {
								idCredited = saveAccount(atCredited, version);
								if (idCredited != -1) {
									arrayInstallment.get(j).getAccountToBeCredited().setId(idCredited);
								}
							}

							// Account to be debited
							atDebited = arrayInstallment.get(j).getAccountToBeDebited();

							if (atDebited != null) {
								idDebited = saveAccount(atDebited, version);
								if (idDebited != -1) {
									arrayInstallment.get(j).getAccountToBeDebited().setId(idDebited);
								}
							}

							// If the two accounts used exist...
							// or one is null and the other exist...
							// or one exist and the other is null...
							// or both are null...
							// ... then it is possible that the installment
							// exists.
							if ((idCredited != -1 && idDebited != -1) || (atCredited == null && idDebited != -1)
									|| (idCredited != -1 && atDebited == null)
									|| (atCredited == null && atDebited == null)) {

								String recRefQuery = " AND RECONCILIATION_REF='"
										+ arrayInstallment.get(j).getPaymentReconciliationReference() + "'",
										addInfoQuery = " AND ADDITIONAL_INFO='"
												+ arrayInstallment.get(j).getCollectionAdditionalInformation() + "'",
										accCreditedQuery = " AND ACCOUNT_TO_BE_CREDITED='" + idCredited + "'",
										accDebitedQuery = " AND ACCOUNT_TO_BE_DEBITED='" + idDebited + "'";
								String debRefQuery = "";
								if (version.equals(Constants.VERSION32)) {
									if (((es.mityc.appfacturae.facturae32.InstallmentType) arrayInstallment.get(j))
											.getDebitReconciliationReference() != null) {
										debRefQuery = " AND DEBIT_RECONCILIATION_REF='"
												+ ((es.mityc.appfacturae.facturae32.InstallmentType) arrayInstallment
														.get(j)).getDebitReconciliationReference()
												+ "'";
									} else {
										debRefQuery = " AND DEBIT_RECONCILIATION_REF IS NULL";
									}
								}
								if (version.equals(Constants.VERSION321)) {
									if (((es.mityc.appfacturae.facturae321.InstallmentType) arrayInstallment.get(j))
										.getDebitReconciliationReference() != null)
										debRefQuery = " AND DEBIT_RECONCILIATION_REF='"
											+ ((es.mityc.appfacturae.facturae321.InstallmentType) arrayInstallment
												.get(j)).getDebitReconciliationReference() + "'";
									else
										debRefQuery = " AND DEBIT_RECONCILIATION_REF IS NULL";
								}
								if (arrayInstallment.get(j).getPaymentReconciliationReference() == null) {
									recRefQuery = " AND RECONCILIATION_REF IS NULL";
								}
								if (arrayInstallment.get(j).getCollectionAdditionalInformation() == null) {
									addInfoQuery = " AND ADDITIONAL_INFO IS NULL";
								}
								if (atCredited == null) {
									accCreditedQuery = " AND ACCOUNT_TO_BE_CREDITED IS NULL";
								}
								if (atDebited == null) {
									accDebitedQuery = " AND ACCOUNT_TO_BE_DEBITED IS NULL";
								}

								s = session.createSQLQuery("SELECT PAYMENT_ID FROM PAYMENT_DETAIL WHERE DUE_DATE='"
										+ arrayInstallment.get(j).getInstallmentDueDate() + "' AND AMOUNT='"
										+ arrayInstallment.get(j).getInstallmentAmount() + "' " + "AND MEAN='"
										+ arrayInstallment.get(j).getPaymentMeans() + "'" + recRefQuery + addInfoQuery
										+ accCreditedQuery + accDebitedQuery + debRefQuery);
								List<?> ls = s.list();
								if (ls.size() == 0) {
									session.save(arrayInstallment.get(j));
								} else {
									arrayInstallment.get(j).setId(Integer.valueOf(ls.get(0).toString()));
								}
							} else {
								session.save(arrayInstallment.get(j));
							}

						}
						// END FOR: It iterates over the installments
					}

					session.save(it);

				}

				/************************* PARTIES *************************/

				//20131119 Se elimina la opción que se almacene de nuevo los Parties al almacenar la factura, puesto
				//que ya se ha almacenado al darle al disco, excepto que sean por importar (id=0)
				BusinessType buyer = fapp.getParties().getBuyerParty();
				BusinessType seller = fapp.getParties().getSellerParty();

				// TaxIdentifications
				if (buyer.getId() == 0) {
					logger.debug("Almacenar información del comprador (buyer)");
					int res = saveParty(buyer, version/*, true*/);
					logger.debug("Almacenar información del comprador (buyer) buyer.id=" + res);
					if (res != 0) {
						buyer.setId(res);
					}
				}

				if (seller.getId() == 0) {
					logger.debug("Almacenar información del vendedor (seller)");
					int res = saveParty(seller, version/*, true*/);
					logger.debug("Almacenar información del vendedor (seller) seller.id=" + res);
					if (res != 0) {
						seller.setId(res);
					}
				}

				/**
				 * *********************** ******** ************************
				 */
				FileHeaderType fht = fapp.getFileHeader();
				// Batch
				BatchType bt = fht.getBatch();
				session.save(bt);

				// ext-csaamar - 20150312 - Añadir factoring >
				FactoringAssignmentDataType factoring = fht.getFactoringAssignmentData();
				if (factoring != null) {
					AssigneeType assignee = factoring.getAssignee();
					if (assignee != null) {
						BusinessType businessType = new BusinessType();
						TaxIdentificationType taxIdentification = assignee.getTaxIdentification();
						if (taxIdentification != null) {
							SQLQuery sqlTaxIdentification = session
									.createSQLQuery("SELECT TAX_ID_NUMBER FROM TAX_IDENTIFICATION WHERE TAX_ID_NUMBER='"
											+ taxIdentification.getTaxIdentificationNumber() + "'");
							List<?> lsTaxIdentification = sqlTaxIdentification.list();
							if (lsTaxIdentification == null || lsTaxIdentification.isEmpty()
									|| lsTaxIdentification.get(0) == null) {
								session.save(taxIdentification);
							}
							businessType.setPartyIdentification(taxIdentification.getTaxIdentificationNumber());
							businessType.setTaxIdentification(taxIdentification);
						}
						
						IndividualType individual = assignee.getIndividual();
						if (individual != null) {
							businessType.setIndividual(individual);
							assignee.setIndividual(individual);
						}

						LegalEntityType legalEntity = assignee.getLegalEntity();
						if (legalEntity != null) {
							businessType.setLegalEntity(legalEntity);
							assignee.setLegalEntity(legalEntity);
						}
						saveParty(businessType, version);
						session.flush();
						
						// Calcular el identificador
						SQLQuery sqlTaxIdentificationMax = session.createSQLQuery("SELECT MAX(ASSIGNEE_ID_NUMBER) FROM ASSIGNEE");
						List<?> lsTaxIdentificationMax = sqlTaxIdentificationMax.list();
						if(lsTaxIdentificationMax != null && !lsTaxIdentificationMax.isEmpty() && lsTaxIdentificationMax.get(0) != null) {
							assignee.setAssigneeNumber(Integer.parseInt(lsTaxIdentificationMax.get(0).toString()) + 1);
						} else {
							assignee.setAssigneeNumber(1);
						}
						session.save(assignee);
						session.flush();
					}

					InstallmentsType paymentsDetails = factoring.getPaymentDetails();
					if (paymentsDetails != null && paymentsDetails.getInstallment() != null
							&& !paymentsDetails.getInstallment().isEmpty()) {
						List<InstallmentType> installments = paymentsDetails.getInstallment();
						for (InstallmentType installment : installments) {
							saveUpdateInstallment(installment, null, version);
						}
					}

					for (InvoiceType it : fapp.getInvoices().getInvoice()) {
						String seriesnumber = "";
						if (iht.getInvoiceSeriesCode() != null) {
							seriesnumber = it.getInvoiceHeader().getInvoiceSeriesCode();
						}
						seriesnumber += it.getInvoiceHeader().getInvoiceNumber();
						SQLQuery sql = session.createSQLQuery(
								"SELECT FACTORING_ID FROM FACTORING F INNER JOIN INVOICE I ON (F.INVOICE_ID=I.INVOICE_ID) WHERE NVL(SERIES_CODE + NUMBER,NUMBER) = '"
										+ seriesnumber + "'");
						List<?> sqlResult = sql.list();
						if (sqlResult == null || sqlResult.isEmpty()) {
							factoring.setInvoice((InvoiceType) it);
							session.save(factoring);
						} else {
							session.update(factoring);
						}
					}
				}
				fht.setFactoringAssignmentData(factoring);
				// ext-csaamar - 20150312 - Añadir factoring <
				// Facturae
				session.save(fapp);
				commit();
			} else {
				contained = false;
				logger.error(
						"There is a invoice that is already inserted in the Data Base. It is not allowed to save two invoices with the same number and series code");
				rollback();
				throw new DuplicatedInvoiceException("The invoice already exists in the database");
			}
		} catch (DatabaseOperationException e) {
			throw e;
		}
		catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			logger.error("An error has been produced during the facturae saving: " + e.getMessage(), e);
			rollback();
			throw new DatabaseOperationException("NOOKMessageSave",
					"An error has been produced during the facturae saving", e);
		}

	}

	private void saveUpdateInstallment(InstallmentType installment, Address adddres, String version) {
		AccountType atCredited = installment.getAccountToBeCredited();
		AccountType atDebited = installment.getAccountToBeDebited();
		if (atCredited != null)
			atCredited.setGeneralAddress(adddres);
		if (atDebited != null)
			atDebited.setGeneralAddress(adddres);
		saveUpdateAccount(atCredited);
		saveUpdateAccount(atDebited);
		String recRefQuery = " AND RECONCILIATION_REF='" + installment.getPaymentReconciliationReference() + "'",
				addInfoQuery = " AND ADDITIONAL_INFO='" + installment.getCollectionAdditionalInformation() + "'";
		String debRefQuery = "";
		if (version.equals("3.2")) {
			if (((es.mityc.appfacturae.facturae32.InstallmentType) installment)
					.getDebitReconciliationReference() != null) {
				debRefQuery = " AND DEBIT_RECONCILIATION_REF='"
						+ ((es.mityc.appfacturae.facturae32.InstallmentType) installment)
								.getDebitReconciliationReference()
						+ "'";
			} else {
				debRefQuery = " AND DEBIT_RECONCILIATION_REF IS NULL";
			}
		}
		if (installment.getPaymentReconciliationReference() == null) {
			recRefQuery = " AND RECONCILIATION_REF IS NULL";
		}
		if (installment.getCollectionAdditionalInformation() == null) {
			addInfoQuery = " AND ADDITIONAL_INFO IS NULL";
		}
		String accCreditedQuery = null;
		if (atCredited == null) {
			accCreditedQuery = " AND ACCOUNT_TO_BE_CREDITED IS NULL";
		} else {
			accCreditedQuery = " AND ACCOUNT_TO_BE_CREDITED='" + atCredited.getId() + "'";
		}
		String accDebitedQuery = null;
		if (atDebited == null) {
			accDebitedQuery = " AND ACCOUNT_TO_BE_DEBITED IS NULL";
		} else {
			accDebitedQuery = " AND ACCOUNT_TO_BE_DEBITED='" + atDebited.getId() + "'";
		}

		SQLQuery s = session.createSQLQuery("SELECT PAYMENT_ID FROM PAYMENT_DETAIL WHERE DUE_DATE='"
				+ installment.getInstallmentDueDate() + "' AND AMOUNT='" + installment.getInstallmentAmount() + "' "
				+ "AND MEAN='" + installment.getPaymentMeans() + "'" + recRefQuery + addInfoQuery + accCreditedQuery
				+ accDebitedQuery + debRefQuery);
		List<?> ls = s.list();
		if (ls.size() == 0) {
			session.save(installment);
		} else {
			installment.setId(Integer.valueOf(ls.get(0).toString()));
		}
	}

	private void saveUpdateAccount(AccountType account) {
		if (account != null) {
			// Verificar si la cuenta de crédito existe en la base de datos.
			boolean toSave = false;
			SQLQuery sExiste = session
					.createSQLQuery("SELECT ACCOUNT_ID FROM ACCOUNT WHERE IBAN='" + account.getIban() + "'");
			List<?> lsExiste = sExiste.list();
			if (lsExiste != null && !lsExiste.isEmpty() && lsExiste.get(0) != null) {
				account.setId(Integer.valueOf(lsExiste.get(0).toString()));
			} else {
				toSave = true;
				SQLQuery maxAccountId = session.createSQLQuery("SELECT MAX(ACCOUNT_ID) FROM ACCOUNT");
				List<?> lsMaxAccountId = maxAccountId.list();
				if (lsMaxAccountId != null && !lsMaxAccountId.isEmpty() && lsMaxAccountId.get(0) != null) {
					account.setId(Integer.valueOf(lsMaxAccountId.get(0).toString()) + 1);
				} else {
					account.setId(1);
				}
			}
			saveUpdateAddress((AddressType) account.getBranchInSpainAddress());
			saveUpdateAddress((OverseasAddressType) account.getOverseasBranchAddress());
			if (toSave) {
				session.save(account);
			}
		}
	}

	private void saveUpdateAddress(OverseasAddressType overseasBranchAddress) {
		if (overseasBranchAddress != null) {
			session.saveOrUpdate(overseasBranchAddress);
		}
	}

	private void saveUpdateAddress(AddressType address) {
		if (address != null) {
			session.saveOrUpdate(address);
		}
	}

	public void deleteFacturae(es.mityc.appfacturae.facturae.Facturae fe, boolean endTransaction, String version)
			throws DatabaseOperationException {
		if (fe != null) {
			// The parties used are not deleted although they are not used by other invoices
			try {

				beginTransaction();
				SQLQuery s = executeQuery(FacturaeStatics.QUERY_FACTURAE_INVOICES_BY_ID + " '" + fe.getId() + "'");
				List<?> invoicesId = s.list();

				List<Integer> itemsId = new ArrayList<Integer>();
				List<Integer> paymentsId = new ArrayList<Integer>();
				List<Integer> attachmentsId = new ArrayList<Integer>();
				List<Integer> taxesId = new ArrayList<Integer>();
				List<Integer> accountsId = new ArrayList<Integer>();
				List<Integer> addressesId = new ArrayList<Integer>();

				for (int i = 0; i < invoicesId.size(); i++) {
					SQLQuery s2 = executeQuery(FacturaeStatics.QUERY_ITEM_ID_BY_INVOICE_ID + String.valueOf(invoicesId.get(i)));
					List<?> ls2 = s2.list();
					for (int j = 0; ls2 != null && j < ls2.size(); j++)
						itemsId.add(new Integer(ls2.get(j).toString()));
					s2 = executeQuery(FacturaeStatics.QUERY_PAYMENT_ID_BY_INVOICE_ID + String.valueOf(invoicesId.get(i)));
					ls2 = s2.list();
					for (int j = 0; ls2 != null && j < ls2.size(); j++)
						paymentsId.add(new Integer(ls2.get(j).toString()));
					s2 = executeQuery("SELECT ID FROM ATTACHMENT WHERE INVOICE IN (SELECT NVL(SERIES_CODE+NUMBER,NUMBER) FROM INVOICE WHERE INVOICE_ID = "
						+ String.valueOf(invoicesId.get(i)) + ")");
					ls2 = s2.list();
					for (int j = 0; ls2 != null && j < ls2.size(); j++)
						attachmentsId.add(new Integer(ls2.get(j).toString()));
				}
				for (int i = 0; i < itemsId.size(); i++) {
					SQLQuery s2 = executeQuery("SELECT TAX_ID FROM ITEM_TAX WHERE ITEM_ID = "
						+ String.valueOf(itemsId.get(i)));
					List<?> ls2 = s2.list();
					for (int j = 0; ls2 != null && j < ls2.size(); j++)
						taxesId.add(new Integer(ls2.get(j).toString()));
				}
				for (int i = 0; i < paymentsId.size(); i++) {
					SQLQuery s2 = executeQuery(FacturaeStatics.QUERY_PAYMENT_DETAIL_BY_ID + String.valueOf(paymentsId.get(i)));
					List<?> ls2 = s2.list();
					if (((Object[]) ls2.get(0))[0] != null)
						accountsId.add(new Integer(((Object[]) ls2.get(0))[0].toString()));
					if (((Object[]) ls2.get(0))[1] != null)
						accountsId.add(new Integer(((Object[]) ls2.get(0))[1].toString()));
				}

				for (int i = 0; i < accountsId.size(); i++) {
					SQLQuery s2 = executeQuery("SELECT ADDRESS FROM ACCOUNT WHERE ACCOUNT_ID = "
						+ String.valueOf(accountsId.get(i)));
					List<?> ls2 = s2.list();
					if (ls2.get(0) != null)
						addressesId.add(new Integer(ls2.get(0).toString()));
				}
				BatchType bt = fe.getFileHeader().getBatch();
				bt.setId(fe.getId());

				session.delete(fe);
				session.flush(); // Se elimina la línea de FACTURAE_INVOICES

				// The parties are not deleted
				s = null;

				// Eliminar las entradas de facturing de los Invoices
				for (int i = 0; i < invoicesId.size(); i++) {
					s = executeQuery("SELECT FACTORING_ID FROM FACTORING WHERE INVOICE_ID=" + invoicesId.get(i));
					List<?> ls = s.list();
					if (ls != null && !ls.isEmpty()) {
						// FIXME: Añadir versión 3.2.1
						FactoringAssignmentDataType fadt = new es.mityc.appfacturae.facturae32.FactoringAssignmentDataType();
						for (Object idFactoring : ls) {
							((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) fadt).setId(Integer.valueOf(idFactoring.toString()));
							session.delete(fadt);
							session.flush();
						}
					}
				}

				InvoiceType it = fe.getInvoices().getInvoice().get(0);
				for (int i = 0; i < invoicesId.size(); i++) {
					s = executeQuery("SELECT * FROM FACTURAE_INVOICES WHERE INVOICE_ID = " + invoicesId.get(i));
					List<?> ls = s.list();
					if (ls == null || ls.size() == 0) {
						it.setState(InvoiceStatusType.D);
						it.setInternalId(Integer.parseInt(invoicesId.get(i).toString()));
						session.delete(it);
						session.flush(); // Se elimina la línea de INVOICE_ITEM, INVOICE_PAYMENT
					}
				}

				InvoiceLineType ilt = fe.getInvoices().getInvoice().get(0).getItems().getInvoiceLine().get(0);
				for (int i = 0; i < itemsId.size(); i++) {
					s = executeQuery("SELECT * FROM INVOICE_ITEM WHERE ITEM_ID = " + itemsId.get(i));
					List<?> ls = s.list();
					if (ls == null || ls.size() == 0) {
						ilt.setId(itemsId.get(i));
						session.delete(ilt);
					}
				}
				session.flush(); // Se elimina la línea de ITEM_TAX

				AttachedDocument ad = new AttachedDocument();
				for (int i = 0; i < attachmentsId.size(); i++) {
					ad.setId(attachmentsId.get(i));
					session.delete(ad);
				}

				InstallmentType inst = null;
				if (version.equals(Constants.VERSION321))
					inst = new es.mityc.appfacturae.facturae321.InstallmentType();
				if (version.equals(Constants.VERSION32))
					inst = new es.mityc.appfacturae.facturae32.InstallmentType();
				inst.setInstallmentAmount(0.0);
				XMLGregorianCalendar date = null;
				try {
					DatatypeFactory df = DatatypeFactory.newInstance();
					date = df.newXMLGregorianCalendar((GregorianCalendar) GregorianCalendar.getInstance());
				} catch (DatatypeConfigurationException e) {
					logger.error(
							"Error deleting invoice. Error creating a XMLGregorianCalendar object: " + e.getMessage(),
							e);
					rollback();
					throw new DatabaseOperationException("NOOKMessageDelete",
							"Error deleting invoice. Error creating a XMLGregorianCalendar object", e);
				}
				inst.setInstallmentDueDate(date);
				inst.setPaymentMeans("");
				for (int i = 0; i < paymentsId.size(); i++) {
					s = executeQuery("SELECT * FROM INVOICE_PAYMENT WHERE PAYMENT_ID = " + paymentsId.get(i));
					List<?> ls = s.list();
					s = executeQuery("SELECT * FROM INVOICE_FACTORING where payment_id=" + paymentsId.get(i));
					ls.addAll(s.list());
					if (ls == null || ls.size() == 0) {
						inst.setId(paymentsId.get(i));
						session.delete(inst);
						session.flush(); // Se elimina la línea de
											// PAYMENT_DETAIL
					}
				}

				AccountType at = null;
				if (version.equals(Constants.VERSION321))
					at = new es.mityc.appfacturae.facturae321.AccountType();
				if (version.equals(Constants.VERSION32))
					at = new es.mityc.appfacturae.facturae32.AccountType();
				at.setIban("");
				for (int i = 0; i < accountsId.size(); i++) {
					s = executeQuery(FacturaeStatics.QUERY_PAYMENT_DETAIL_BY_ACCOUNT_TO_BE_CREDITED + accountsId.get(i)
						+ " OR ACCOUNT_TO_BE_DEBITED = " + accountsId.get(i));
					List<?> ls = s.list();
					if (ls == null || ls.size() == 0) {
						at.setId(accountsId.get(i));
						session.delete(at);
						session.flush(); // Se elimina la línea de ACCOUNT
					}
				}

				AddressType add = new AddressType();
				add.setAddress("");
				add.setCountryCode(CountryType.ABW);
				add.setProvince("");
				add.setTown("");
				for (int i = 0; i < addressesId.size(); i++) {
					s = executeQuery("SELECT * FROM ACCOUNT WHERE ADDRESS = " + addressesId.get(i));
					List<?> ls = s.list();
					if (ls == null || ls.size() == 0) {
						s = executeQuery("SELECT * FROM INDIVIDUAL WHERE ADDRESS = " + addressesId.get(i));
						ls = s.list();
						if (ls == null || ls.size() == 0) {
							s = executeQuery("SELECT * FROM LEGAL_ENTITY WHERE ADDRESS = " + addressesId.get(i));
							ls = s.list();
							if (ls == null || ls.size() == 0) {
								s = executeQuery("SELECT * FROM ADMCENTRE WHERE ADDRESS = " + addressesId.get(i));
								ls = s.list();
								if (ls == null || ls.size() == 0) {
									add.setId(addressesId.get(i));
									session.delete(add);
								}
							}
						}
					}
				}

				Tax t = new Tax();
				t.setTaxRate(0.0);
				t.setTaxTypeCode("");
				for (int i = 0; i < taxesId.size(); i++) {
					s = executeQuery("SELECT * FROM ITEM_TAX WHERE TAX_ID = " + taxesId.get(i));
					List<?> ls = s.list();
					if (ls == null || ls.size() == 0) {
						t.setId(taxesId.get(i));
						session.delete(t);
					}
				}

			} catch (Exception e) {
				logger.error("Error deleting invoice: " + e.getMessage(), e);
				rollback();
				throw new DatabaseOperationException("NOOKMessageDelete", "Error deleting invoice", e);
			}

			if (endTransaction) {
				commit();
			} else {
				session.flush();
			}

		} else {
			throw new DatabaseOperationException("NOOKMessageDelete", "The Facturae object is null");
		}
	}

	/**
	 * This method carries on a SQL query against the Data Base looking for equal Addresses 
	 * @param addressType: String containing the addressType (Spain or Overseas) 
	 * @param version: String containing the facturae version
	 * @param a: Interface Address (with subinterfaces AddressType and OverseasAddressType)
	 * @return SQLQuery: The query's result
	 */
	public SQLQuery addressExist(String addressType, String version, Address a) {
		SQLQuery sql = null;
		if (addressType.equals("Spain")) {
			AddressType addressT = (es.mityc.appfacturae.facturae32.AddressType) a;
			String address = addressT.getAddress();
			String postCode = addressT.getPostCode();
			String town = addressT.getTown();
			String province = addressT.getProvince();
			String countryCode = addressT.getCountryCode().toString();
			sql = session.createSQLQuery(FacturaeStatics.QUERY_ADDRESS_ID_BY_ADDRESS
				+ DatabaseUtil.escapeWithMarks(address) + " AND POST_CODE='" + postCode
				+ "' AND TOWN=" + DatabaseUtil.escapeWithMarks(town) 
				+ " AND PROVINCE=" + DatabaseUtil.escapeWithMarks(province) + 
				" AND COUNTRY='" + EnumUtils.calculateEnumCode(countryCode, es.mityc.appfacturae.facturae.CountryType.values())
				+ "' AND TYPE_VERSION = 'Spain-3.1'");
		} else if (addressType.equals("Overseas")) {
			OverseasAddressType overseasAddress = (es.mityc.appfacturae.facturae32.OverseasAddressType) a;
			String address = overseasAddress.getAddress();
			String postCodeAndTown = overseasAddress.getPostCodeAndTown();
			String province = overseasAddress.getProvince();
			String countryCode = overseasAddress.getCountryCode().toString();
			sql = session.createSQLQuery(FacturaeStatics.QUERY_ADDRESS_ID_BY_ADDRESS
				+ DatabaseUtil.escapeWithMarks(address) 
				+ " AND TOWN=" + DatabaseUtil.escapeWithMarks(postCodeAndTown)
				+ " AND PROVINCE=" + DatabaseUtil.escapeWithMarks(province)
				+ " AND COUNTRY='" + EnumUtils.calculateEnumCode(countryCode, es.mityc.appfacturae.facturae.CountryType.values())
				+ "' AND TYPE_VERSION = 'Overseas-3.1'");
		} else {
			logger.error("The address type specified is not valid");
			sql = null;
		}
		return sql;
	}

	public void saveAction(EnumOperationType op, String invoiceId) throws DatabaseOperationException {
		saveAction(op, invoiceId, null);
	}

	public void saveAction(EnumOperationType op, String invoiceId, String error) throws DatabaseOperationException {
		try {
			beginTransaction();

			logger.info("Saving a new user action in the historical");

			Action a = new Action();
			XMLGregorianCalendar date = null; // Today s date
			try {
				DatatypeFactory df = DatatypeFactory.newInstance();
				date = df.newXMLGregorianCalendar();
				Calendar today = Calendar.getInstance();
				date.setDay(today.get(Calendar.DAY_OF_MONTH));
				date.setMonth(today.get(Calendar.MONTH) + 1);
				date.setYear(today.get(Calendar.YEAR));
				date.setHour(today.get(Calendar.HOUR_OF_DAY));
				date.setMinute(today.get(Calendar.MINUTE));
				date.setSecond(today.get(Calendar.SECOND));
			} catch (DatatypeConfigurationException e) {
				logger.error("Error saving an action. Error creating a XMLGregorianCalendar object: " + e.getMessage(),
						e);
				throw new DatabaseOperationException(
						"Error saving an action. Error creating a XMLGregorianCalendar object", e);
			}
			a.setDate(date);

			// Operation type
			a.setOperation(EnumOperationType.getOperation(op));

			// Invoice
			if (invoiceId != null) {
				a.setInvoice(invoiceId);
			}

			a.setError(error);

			session.save(a);

			commit();

		} catch (Exception e) {
			logger.error("Error saving an action: " + e.getMessage(), e);
			rollback();
			throw new DatabaseOperationException("Error saving an action", e);
		}
	}

	/**
	 * Loads an invoice from data base to a temporal File
	 *
	 * @param id
	 *            .- Serie + number for selected invoice
	 * @return .- A temp File containing invoice xml
	 */
	public File loadInvoice(String id) {
		SQLQuery s = FacturaeManager.getInstance().executeQuery(FacturaeStatics.QUERY_FACTURAE_XML_BY_CODE_NUMBER.replace("$1", id));
		List<?> ls = s.list();
		if (ls != null && ls.size() == 1) {
			Blob data = (Blob) ls.get(0);
			File f = null;
			FileOutputStream out = null;
			try {
				f = File.createTempFile("facturae", null);
				f.deleteOnExit();
				out = new FileOutputStream(f);
				out.write(data.getBytes((long) 1, (int) data.length()));
			} catch (SQLException e) {
				logger.error("SQL error loading an invoice: " + e.getMessage(), e);
			} catch (IOException e) {
				logger.error("IO error loading an invoice: " + e.getMessage(), e);
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
				}
			}

			return f;
		}

		logger.debug("No invoice found with its Id equals to :" + id);
		return null;
	}

	public FACeSentResult loadFACeSentResultFromCode(String value) throws DatabaseOperationException {
		try {
			logger.info("Loading FACeSentResult type");
			Query q = session.createQuery("from FACeSentResult where registerCode = ?");
			q.setString(0, value);
			return (FACeSentResult) q.uniqueResult();
		} catch (Exception e) {
			logger.error("An error has been produced loading FACeSentResult: " + e.getMessage(), e);
			throw new DatabaseOperationException("An error has been produced loading FACeSentResult", e);
		}
	}

}
