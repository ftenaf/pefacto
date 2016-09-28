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
package es.mityc.appfacturae.utils;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.SQLQuery;

import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.utils.io.DoubleUtil;

public class IntermediaryUtil32 {

	/**
	 * Converts an es.mityc.facturae32.Facturae object into an es.mityc.appfacturae.facturae.Facturae object  ***
	 * @param facturae The es.mityc.facturae32.Facturae object to convert
	 * @return The es.mityc.appfacturae.facturae.Facturae object
	 */
	public static es.mityc.appfacturae.facturae.Facturae getApplicationFacturae(es.mityc.facturae32.Facturae facturae) {

		// 0 es.mityc.facturae31.Facturae
		es.mityc.appfacturae.facturae.Facturae invoice = new es.mityc.appfacturae.facturae.Facturae();

		// 1 FileHeader
		es.mityc.appfacturae.facturae.FileHeaderType fileHeader = new es.mityc.appfacturae.facturae.FileHeaderType();

		// 1.1 SchemaVersion 
		String schemaVersion = facturae.getFileHeader().getSchemaVersion();
		fileHeader.setSchemaVersion(schemaVersion);

		// 1.2 Modality
		es.mityc.appfacturae.facturae.ModalityType modality = es.mityc.appfacturae.facturae.ModalityType
			.fromValue(facturae.getFileHeader().getModality().value());
		fileHeader.setModality(modality);

		// 1.3 InvoiceIssuerType
		es.mityc.appfacturae.facturae.InvoiceIssuerTypeType invoiceIssuerType = es.mityc.appfacturae.facturae.InvoiceIssuerTypeType
			.fromValue(facturae.getFileHeader().getInvoiceIssuerType().value());
		fileHeader.setInvoiceIssuerType(invoiceIssuerType);

		// 1.4 Third Party
		if (facturae.getFileHeader().getThirdParty() != null) {
			es.mityc.appfacturae.facturae.ThirdPartyType thirdParty = new es.mityc.appfacturae.facturae.ThirdPartyType();

			// 1.4.1 TaxIdentification
			es.mityc.appfacturae.facturae.TaxIdentificationType taxIdentification3 = new es.mityc.appfacturae.facturae.TaxIdentificationType();

			// 1.4.1.1 PersonTypeCode
			es.mityc.appfacturae.facturae.PersonTypeCodeType personTypeCode3 = es.mityc.appfacturae.facturae.PersonTypeCodeType
				.fromValue(facturae.getFileHeader().getThirdParty().getTaxIdentification().getPersonTypeCode().value());
			taxIdentification3.setPersonTypeCode(personTypeCode3);

			// 1.4.1.2 ResidenceTypeCode
			es.mityc.appfacturae.facturae.ResidenceTypeCodeType residenceTypeCode3 = es.mityc.appfacturae.facturae.ResidenceTypeCodeType
				.fromValue(facturae.getFileHeader().getThirdParty().getTaxIdentification().getResidenceTypeCode()
					.value());
			taxIdentification3.setResidenceTypeCode(residenceTypeCode3);

			// 1.4.1.3 TaxIdentificationNumber
			String taxIdentificationNumber3 = facturae.getFileHeader().getThirdParty().getTaxIdentification()
				.getTaxIdentificationNumber();
			taxIdentification3.setTaxIdentificationNumber(taxIdentificationNumber3);

			thirdParty.setTaxIdentification(taxIdentification3);

			// 1.4.2
			// if 1
			if (facturae.getFileHeader().getThirdParty().getLegalEntity() != null) {

				// 1.4.2.1 LegalEntity
				es.mityc.appfacturae.facturae.LegalEntityType legalEntity5 = new es.mityc.appfacturae.facturae.LegalEntityType();

				// 1.4.2.1.1 CorporateName
				String corporateName = facturae.getFileHeader().getThirdParty().getLegalEntity().getCorporateName();
				legalEntity5.setCorporateName(corporateName);

				// 1.4.2.1.2 TradeName
				String tradeName = facturae.getFileHeader().getThirdParty().getLegalEntity().getTradeName();
				legalEntity5.setTradeName(tradeName);

				// 1.4.2.1.3 RegistrationData
				es.mityc.appfacturae.facturae.RegistrationDataType registrationData = new es.mityc.appfacturae.facturae.RegistrationDataType();

				// 1.4.2.1.3.1 Book
				String book = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData().getBook();
				registrationData.setBook(book);

				// 1.4.2.1.3.2 RegisterOfCompaniesLocation
				String registerOfCompaniesLocation = facturae.getFileHeader().getThirdParty().getLegalEntity()
					.getRegistrationData().getRegisterOfCompaniesLocation();
				registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);

				// 1.4.2.1.3.3 Sheet
				String sheet = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData().getSheet();
				registrationData.setSheet(sheet);

				// 1.4.2.1.3.4 Folio
				String folio = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData().getFolio();
				registrationData.setFolio(folio);

				// 1.4.2.1.3.5 Section
				String section = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData().getSection();
				registrationData.setSection(section);

				// 1.4.2.1.3.6 Volume
				String volume = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData().getVolume();
				registrationData.setVolume(volume);

				// 1.4.2.1.3.7 AdditionalRegistrationData
				String additionalRegistrationData = facturae.getFileHeader().getThirdParty().getLegalEntity()
					.getRegistrationData().getAdditionalRegistrationData();
				registrationData.setAdditionalRegistrationData(additionalRegistrationData);

				legalEntity5.setRegistrationData(registrationData);

				// 1.4.2.1.4

				// if 2
				if (facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain() != null) {

					// 1.4.2.1.4.1 AddressInSpain
					es.mityc.appfacturae.facturae32.AddressType addressInSpain5 = new es.mityc.appfacturae.facturae32.AddressType();

					// 1.4.2.1.4.1.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain().getAddress();
					addressInSpain5.setAddress(address5);

					// 1.4.2.1.4.1.2 PostCode
					String postCode5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain().getPostCode();
					addressInSpain5.setPostCode(postCode5);

					// 1.4.2.1.4.1.3 Town
					String town5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain().getTown();
					addressInSpain5.setTown(town5);

					// 1.4.2.1.4.1.4 Province
					String province5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain().getProvince();
					addressInSpain5.setProvince(province5);

					// 1.4.2.1.4.1.5 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode5 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain()
							.getCountryCode().value());
					addressInSpain5.setCountryCode(countryCode5);

					legalEntity5.setAddressInSpain(addressInSpain5);

				} else if (facturae.getFileHeader().getThirdParty().getLegalEntity().getOverseasAddress() != null) {

					// 1.4.2.1.4.2 OverseasAddress
					es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress5 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

					// 1.4.2.1.4.2.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getOverseasAddress()
						.getAddress();
					overseasAddress5.setAddress(address5);

					// 1.4.2.1.4.2.2 PostCodeAndTown
					String postCodeandTown5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
						.getOverseasAddress().getPostCodeAndTown();
					overseasAddress5.setPostCodeAndTown(postCodeandTown5);

					// 1.4.2.1.4.2.3 Province
					String province5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getOverseasAddress()
						.getProvince();
					overseasAddress5.setProvince(province5);

					// 1.4.2.1.4.2.4 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode5 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getThirdParty().getLegalEntity().getOverseasAddress()
							.getCountryCode().value());
					overseasAddress5.setCountryCode(countryCode5);

					legalEntity5.setOverseasAddress(overseasAddress5);

				} // if 2

				// 1.4.2.1.5 ContactDetails
				if (facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails() != null) {
					es.mityc.appfacturae.facturae.ContactDetailsType contactDetails5 = new es.mityc.appfacturae.facturae.ContactDetailsType();

					// 1.4.2.1.5.1 Telephone
					String telephone5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails().getTelephone();
					if (!"".equals(telephone5))
						contactDetails5.setTelephone(telephone5);

					// 1.4.2.1.5.2 TeleFax
					String teleFax5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails().getTeleFax();
					if (!"".equals(teleFax5))
						contactDetails5.setTeleFax(teleFax5);

					// 1.4.2.1.5.3 WebAddress
					String webAddress5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails().getWebAddress();
					if (!"".equals(webAddress5))
						contactDetails5.setWebAddress(webAddress5);

					// 1.4.2.1.5.4 ElectronicMail
					String electronicMail5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
						.getContactDetails().getElectronicMail();
					if (!"".equals(electronicMail5))
						contactDetails5.setElectronicMail(electronicMail5);

					// 1.4.2.1.5.5 ContactPersons
					String contactPersons5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
						.getContactDetails().getContactPersons();
					if (!"".equals(contactPersons5))
						contactDetails5.setContactPersons(contactPersons5);

					// 1.4.2.1.5.6 CnoCnae
					String cnoCnae5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails().getCnoCnae();
					if (!"".equals(cnoCnae5))
						contactDetails5.setCnoCnae(cnoCnae5);

					// 1.4.2.1.5.7 INETownCode
					String ineTownCode5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails().getINETownCode();
					if (!"".equals(ineTownCode5))
						contactDetails5.setIneTownCode(ineTownCode5);

					// 1.4.2.1.5.8 AdditionalContactDetails
					String additionalContactDetails5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
						.getContactDetails().getAdditionalContactDetails();
					if (!"".equals(contactDetails5))
						contactDetails5.setAdditionalContactDetails(additionalContactDetails5);

					legalEntity5.setContactDetails(contactDetails5);
				}

				String query = "SELECT FACE FROM LEGAL_ENTITY LE WHERE CORPORATE_NAME = '"
					+ DatabaseUtil.escapeChars(legalEntity5.getCorporateName()) + "' ORDER BY PARTY_ID DESC";
				SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
				List<?> ls = s.list();
				Boolean face = null;
				if (ls != null && ls.size() > 0)
					face = (Boolean) ls.get(0);
				if (face != null) {
					legalEntity5.setFace(face.booleanValue());
				}

				thirdParty.setLegalEntity(legalEntity5);

			} else if (facturae.getFileHeader().getThirdParty().getIndividual() != null) {

				// 1.4.2.2 Individual
				es.mityc.appfacturae.facturae.IndividualType individual5 = new es.mityc.appfacturae.facturae.IndividualType();

				// 1.4.2.2.1 Name
				String name5 = facturae.getFileHeader().getThirdParty().getIndividual().getName();
				individual5.setName(name5);

				// 1.4.2.2.2 FirstSurname
				String firstSurname5 = facturae.getFileHeader().getThirdParty().getIndividual().getFirstSurname();
				individual5.setFirstSurname(firstSurname5);

				// 1.4.2.2.3 SecondSurname
				String secondSurname5 = facturae.getFileHeader().getThirdParty().getIndividual().getSecondSurname();
				individual5.setSecondSurname(secondSurname5);

				// 1.4.2.2.4

				// if 3
				if (facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain() != null) {

					// 1.4.2.2.4.1 AddressInSpain
					es.mityc.appfacturae.facturae32.AddressType addressInSpain5 = new es.mityc.appfacturae.facturae32.AddressType();

					// 1.4.2.2.4.1.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain().getAddress();
					addressInSpain5.setAddress(address5);

					// 1.4.2.2.4.1.2 PostCode
					String postCode5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain().getPostCode();
					addressInSpain5.setPostCode(postCode5);

					// 1.4.2.2.4.1.3 Town
					String town5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain().getTown();
					addressInSpain5.setTown(town5);

					// 1.4.2.2.4.1.4 Province
					String province5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain().getProvince();
					addressInSpain5.setProvince(province5);

					// 1.4.2.2.4.1.5 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode5 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain()
							.getCountryCode().value());
					addressInSpain5.setCountryCode(countryCode5);

					individual5.setAddressInSpain(addressInSpain5);

				} else if (facturae.getFileHeader().getThirdParty().getIndividual().getOverseasAddress() != null) {

					// 1.4.2.2.4.2 OverseasAddress
					es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddressType5 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

					// 1.4.2.2.4.2.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getIndividual().getOverseasAddress()
						.getAddress();
					overseasAddressType5.setAddress(address5);

					// 1.4.2.2.4.2.2 PostCodeAndTown
					String postCodeandTown5 = facturae.getFileHeader().getThirdParty().getIndividual()
						.getOverseasAddress().getPostCodeAndTown();
					overseasAddressType5.setPostCodeAndTown(postCodeandTown5);

					// 1.4.2.2.4.2.3 Province
					String province5 = facturae.getFileHeader().getThirdParty().getIndividual().getOverseasAddress()
						.getProvince();
					overseasAddressType5.setProvince(province5);

					// 1.4.2.2.4.2.4 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode5 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getThirdParty().getIndividual().getOverseasAddress()
							.getCountryCode().value());
					overseasAddressType5.setCountryCode(countryCode5);

					individual5.setOverseasAddress(overseasAddressType5);

				} //if 3

				// 1.4.2.2.5 ContactDetails
				if (facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails() != null) {
					es.mityc.appfacturae.facturae.ContactDetailsType contactDetails5 = new es.mityc.appfacturae.facturae.ContactDetailsType();

					// 1.4.2.2.5.1 Telephone
					String telephone5 = facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails().getTelephone();
					if (!"".equals(telephone5))
						contactDetails5.setTelephone(telephone5);

					// 1.4.2.2.5.2 TeleFax
					String teleFax5 = facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails().getTeleFax();
					if (!"".equals(teleFax5))
						contactDetails5.setTeleFax(teleFax5);

					// 1.4.2.2.5.3 WebAddress
					String webAddress5 = facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails().getWebAddress();
					if (!"".equals(webAddress5))
						contactDetails5.setWebAddress(webAddress5);

					// 1.4.2.2.5.4 ElectronicMail
					String electronicMail5 = facturae.getFileHeader().getThirdParty().getIndividual()
						.getContactDetails().getElectronicMail();
					if (!"".equals(electronicMail5))
						contactDetails5.setElectronicMail(electronicMail5);

					// 1.4.2.2.5.5 ContactPersons
					String contactPersons5 = facturae.getFileHeader().getThirdParty().getIndividual()
						.getContactDetails().getContactPersons();
					if (!"".equals(contactPersons5))
						contactDetails5.setContactPersons(contactPersons5);

					// 1.4.2.2.5.6 CnoCnae
					String cnoCnae5 = facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails().getCnoCnae();
					if (!"".equals(cnoCnae5))
						contactDetails5.setCnoCnae(cnoCnae5);

					// 1.4.2.2.5.7 INETownCode
					String ineTownCode5 = facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails().getINETownCode();
					if (!"".equals(ineTownCode5))
						contactDetails5.setIneTownCode(ineTownCode5);

					// 1.4.2.2.5.8 AdditionalContactDetails
					String additionalContactDetails5 = facturae.getFileHeader().getThirdParty().getIndividual()
						.getContactDetails().getAdditionalContactDetails();
					if (!"".equals(additionalContactDetails5))
						contactDetails5.setAdditionalContactDetails(additionalContactDetails5);

					individual5.setContactDetails(contactDetails5);
				}

				thirdParty.setIndividual(individual5);

			} // if 1

			fileHeader.setThirdParty(thirdParty);
		}

		// 1.5 Batch
		es.mityc.appfacturae.facturae32.BatchType batch = new es.mityc.appfacturae.facturae32.BatchType();

		// 1.5.1 BatchIdentifier
		String batchIdentifier = facturae.getFileHeader().getBatch().getBatchIdentifier();
		batch.setBatchIdentifier(batchIdentifier);

		// 1.5.2 InvoicesCount
		long invoicesCount = facturae.getFileHeader().getBatch().getInvoicesCount();
		batch.setInvoicesCount(invoicesCount);

		// 1.5.3 TotalInvoicesAmount
		es.mityc.appfacturae.facturae.AmountType totalInvoicesAmount = new es.mityc.appfacturae.facturae.AmountType();

		// 1.5.3.1 TotalAmount
		double totalAmount = facturae.getFileHeader().getBatch().getTotalInvoicesAmount().getTotalAmount();
		totalInvoicesAmount.setTotalAmount(totalAmount);
		// 1.5.3.2 EquivalentInEuros
		if (facturae.getFileHeader().getBatch().getTotalInvoicesAmount().getEquivalentInEuros() != null) {
			double equivalentInEuros = facturae.getFileHeader().getBatch().getTotalInvoicesAmount().getEquivalentInEuros();
			totalInvoicesAmount.setEquivalentInEuros(equivalentInEuros);
		}

		batch.setTotalInvoicesAmount(totalInvoicesAmount);

		// 1.5.4 TotalOutstandingAmount
		es.mityc.appfacturae.facturae.AmountType totalOutstandingAmount = new es.mityc.appfacturae.facturae.AmountType();

		// 1.5.4.1 TotalAmount
		double totalAmount2 = facturae.getFileHeader().getBatch().getTotalOutstandingAmount().getTotalAmount();
		totalOutstandingAmount.setTotalAmount(totalAmount2);
		// 1.5.4.2 EquivalentInEuros
		if (facturae.getFileHeader().getBatch().getTotalOutstandingAmount().getEquivalentInEuros() != null) {
			double equivalentInEuros2 = facturae.getFileHeader().getBatch().getTotalOutstandingAmount().getEquivalentInEuros();
			totalOutstandingAmount.setEquivalentInEuros(equivalentInEuros2);
		}

		batch.setTotalOutstandingAmount(totalOutstandingAmount);

		// 1.5.5 TotalExecutableAmount
		es.mityc.appfacturae.facturae.AmountType totalExecutableAmount = new es.mityc.appfacturae.facturae.AmountType();

		// 1.5.5.1 TotalAmount
		double totalAmount3 = facturae.getFileHeader().getBatch().getTotalExecutableAmount().getTotalAmount();
		totalExecutableAmount.setTotalAmount(totalAmount3);
		// 1.5.5.2 EquivalentInEuros
		if (facturae.getFileHeader().getBatch().getTotalExecutableAmount().getEquivalentInEuros() != null) {
			double equivalentInEuros3 = facturae.getFileHeader().getBatch().getTotalExecutableAmount().getEquivalentInEuros();
			totalExecutableAmount.setEquivalentInEuros(equivalentInEuros3);
		}

		batch.setTotalExecutableAmount(totalExecutableAmount);

		// 1.5.6 InvoiceCurrencyCode	
		es.mityc.appfacturae.facturae.CurrencyCodeType invoiceCurrencyCode = es.mityc.appfacturae.facturae.CurrencyCodeType
			.fromValue(facturae.getFileHeader().getBatch().getInvoiceCurrencyCode().value());
		batch.setInvoiceCurrencyCode(invoiceCurrencyCode);

		fileHeader.setBatch(batch);

		// 1.6 FactoringAssignmentData
		if (facturae.getFileHeader().getFactoringAssignmentData() != null) {
			es.mityc.appfacturae.facturae32.FactoringAssignmentDataType factoringAssignmentData = new es.mityc.appfacturae.facturae32.FactoringAssignmentDataType();

			// 1.6.1 Assignee
			es.mityc.appfacturae.facturae.AssigneeType assignee = new es.mityc.appfacturae.facturae.AssigneeType();

			// 1.6.1.1 TaxIdentification
			es.mityc.appfacturae.facturae.TaxIdentificationType taxIdentification4 = new es.mityc.appfacturae.facturae.TaxIdentificationType();

			// 1.6.1.1.1 PersonTypeCode
			es.mityc.appfacturae.facturae.PersonTypeCodeType personTypeCode4 = es.mityc.appfacturae.facturae.PersonTypeCodeType
				.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getTaxIdentification()
					.getPersonTypeCode().value());
			taxIdentification4.setPersonTypeCode(personTypeCode4);

			// 1.6.1.1.2 ResidenceTypeCode
			es.mityc.appfacturae.facturae.ResidenceTypeCodeType residenceTypeCode4 = es.mityc.appfacturae.facturae.ResidenceTypeCodeType
				.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getTaxIdentification()
					.getResidenceTypeCode().value());
			taxIdentification4.setResidenceTypeCode(residenceTypeCode4);

			// 1.6.1.1.3 TaxIdentificationNumber
			String taxIdentificationNumber4 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
				.getTaxIdentification().getTaxIdentificationNumber();
			taxIdentification4.setTaxIdentificationNumber(taxIdentificationNumber4);

			assignee.setTaxIdentification(taxIdentification4);

			// 1.6.1.2

			// if 4
			if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity() != null) {

				// 1.6.1.2.1 LegalEntity
				es.mityc.appfacturae.facturae.LegalEntityType legalEntity = new es.mityc.appfacturae.facturae.LegalEntityType();

				// 1.6.1.2.1.1 CorporateName
				String corporateName = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
					.getLegalEntity().getCorporateName();
				legalEntity.setCorporateName(corporateName);

				// 1.6.1.2.1.2 TradeName
				String tradeName = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getTradeName();
				legalEntity.setTradeName(tradeName);

				if(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity().getRegistrationData() != null) {
					// 1.6.1.2.1.3 RegistrationData
					es.mityc.appfacturae.facturae.RegistrationDataType registrationData = new es.mityc.appfacturae.facturae.RegistrationDataType();
					
					// 1.6.1.2.1.3.1 Book
					String book = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getBook();
					registrationData.setBook(book);
					// 1.6.1.2.1.3.2 RegisterOfCompaniesLocation
					String registerOfCompaniesLocation = facturae.getFileHeader().getFactoringAssignmentData()
						.getAssignee().getLegalEntity().getRegistrationData().getRegisterOfCompaniesLocation();
					registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);
					// 1.6.1.2.1.3.3 Sheet
					String sheet = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getSheet();
					registrationData.setSheet(sheet);
					// 1.6.1.2.1.3.4 Folio
					String folio = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getFolio();
					registrationData.setFolio(folio);
					// 1.6.1.2.1.3.5 Section
					String section = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getSection();
					registrationData.setSection(section);
					// 1.6.1.2.1.3.6 Volume
					String volume = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getVolume();
					registrationData.setVolume(volume);
					// 1.6.1.2.1.3.7 AdditionalRegistrationData
					String additionalRegistrationData = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getRegistrationData().getAdditionalRegistrationData();
					registrationData.setAdditionalRegistrationData(additionalRegistrationData);
	
					legalEntity.setRegistrationData(registrationData);
				}
				// 1.6.1.2.1.4

				// if 5
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity().getAddressInSpain() != null) {

					// 1.6.1.2.1.4.1 AddressInSpain
					es.mityc.appfacturae.facturae32.AddressType addressInSpain6 = new es.mityc.appfacturae.facturae32.AddressType();

					// 1.6.1.2.1.4.1.1 Address
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getAddressInSpain().getAddress();
					addressInSpain6.setAddress(address6);

					// 1.6.1.2.1.4.1.2 PostCode
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getAddressInSpain().getPostCode();
					addressInSpain6.setPostCode(postCode6);

					// 1.6.1.2.1.4.1.3 Town
					String town6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getAddressInSpain().getTown();
					addressInSpain6.setTown(town6);

					// 1.6.1.2.1.4.1.4 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getAddressInSpain().getProvince();
					addressInSpain6.setProvince(province6);

					// 1.6.1.2.1.4.1.5 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode6 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
							.getAddressInSpain().getCountryCode().value());
					addressInSpain6.setCountryCode(countryCode6);

					legalEntity.setAddressInSpain(addressInSpain6);

				} else if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getOverseasAddress() != null) {

					// 1.6.1.2.1.4.2 OverseasAddress
					es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress6 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

					// 1.6.1.2.1.4.2.1 Address
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getOverseasAddress().getAddress();
					overseasAddress6.setAddress(address6);

					// 1.6.1.2.1.4.2.2 PostCodeAndTown
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getOverseasAddress().getPostCodeAndTown();
					overseasAddress6.setPostCodeAndTown(postCode6);

					// 1.6.1.2.1.4.2.3 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getOverseasAddress().getProvince();
					overseasAddress6.setProvince(province6);

					// 1.6.1.2.1.4.2.4 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode6 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
							.getOverseasAddress().getCountryCode().value());
					overseasAddress6.setCountryCode(countryCode6);

					legalEntity.setOverseasAddress(overseasAddress6);

				} // if 5

				// 1.6.1.2.1.5 ContactDetails
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getContactDetails() != null) {
					es.mityc.appfacturae.facturae.ContactDetailsType contactDetails6 = new es.mityc.appfacturae.facturae.ContactDetailsType();

					// 1.6.1.2.1.5.1 Telephone
					String telephone6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getContactDetails().getTelephone();
					if (!"".equals(telephone6))
						contactDetails6.setTelephone(telephone6);

					// 1.6.1.2.1.5.2 TeleFax
					String teleFax6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getContactDetails().getTeleFax();
					if (!"".equals(teleFax6))
						contactDetails6.setTeleFax(teleFax6);

					// 1.6.1.2.1.5.3 WebAddress
					String webAddress6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getContactDetails().getWebAddress();
					if (!"".equals(webAddress6))
						contactDetails6.setWebAddress(webAddress6);

					// 1.6.1.2.1.5.4 ElectronicMail
					String electronicMail6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getContactDetails().getElectronicMail();
					if (!"".equals(electronicMail6))
						contactDetails6.setElectronicMail(electronicMail6);

					// 1.6.1.2.1.5.5 ContactPersons
					String contactPersons6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getContactDetails().getContactPersons();
					if (!"".equals(contactPersons6))
						contactDetails6.setContactPersons(contactPersons6);

					// 1.6.1.2.1.5.6 CnoCnae
					String cnoCnae6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getContactDetails().getCnoCnae();
					if (!"".equals(cnoCnae6))
						contactDetails6.setCnoCnae(cnoCnae6);

					// 1.6.1.2.1.5.7 INETownCode
					String ineTownCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getContactDetails().getINETownCode();
					if (!"".equals(ineTownCode6))
						contactDetails6.setIneTownCode(ineTownCode6);

					// 1.6.1.2.1.5.8 AdditionalContactDetails
					String additionalContactDetails6 = facturae.getFileHeader().getFactoringAssignmentData()
						.getAssignee().getLegalEntity().getContactDetails().getAdditionalContactDetails();
					if (!"".equals(additionalContactDetails6))
						contactDetails6.setAdditionalContactDetails(additionalContactDetails6);

					legalEntity.setContactDetails(contactDetails6);
				}

				String query = "SELECT FACE FROM LEGAL_ENTITY LE WHERE CORPORATE_NAME = '"
					+ DatabaseUtil.escapeChars(legalEntity.getCorporateName()) + "' ORDER BY PARTY_ID DESC";
				SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
				List<?> ls = s.list();
				Boolean face = null;
				if (ls != null && ls.size() > 0)
					face = (Boolean) ls.get(0);
				if (face != null) {
					legalEntity.setFace(face.booleanValue());
				}

				assignee.setLegalEntity(legalEntity);

			} else if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual() != null) {

				//1.6.1.2.2 Individual
				es.mityc.appfacturae.facturae.IndividualType individual = new es.mityc.appfacturae.facturae.IndividualType();

				//1.6.1.2.2.1 Name
				String name = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getName();
				individual.setName(name);

				//1.6.1.2.2.2 FirstSurname	
				String firstSurname = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
					.getIndividual().getFirstSurname();
				individual.setFirstSurname(firstSurname);

				//1.6.1.2.2.3 SecondSurname
				String secondSurname = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
					.getIndividual().getSecondSurname();
				individual.setSecondSurname(secondSurname);

				//1.6.1.2.2.4	

				// if 6
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getAddressInSpain() != null) {

					//1.6.1.2.2.4.1 AddressInSpain	
					es.mityc.appfacturae.facturae32.AddressType addressInSpain6 = new es.mityc.appfacturae.facturae32.AddressType();

					//1.6.1.2.2.4.1.1 Address	
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getAddressInSpain().getAddress();
					addressInSpain6.setAddress(address6);

					//1.6.1.2.2.4.1.2 PostCode	
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getAddressInSpain().getPostCode();
					addressInSpain6.setPostCode(postCode6);

					//1.6.1.2.2.4.1.3 Town	
					String town6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getAddressInSpain().getTown();
					addressInSpain6.setTown(town6);

					//1.6.1.2.2.4.1.4 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getAddressInSpain().getProvince();
					addressInSpain6.setProvince(province6);

					//1.6.1.2.2.4.1.5 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode6 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
							.getAddressInSpain().getCountryCode().value());
					addressInSpain6.setCountryCode(countryCode6);

					individual.setAddressInSpain(addressInSpain6);

				} else if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getOverseasAddress() != null) {

					// 1.6.1.2.2.4.2 OverseasAddress
					es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress6 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

					// 1.6.1.2.2.4.2.1 Address
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getOverseasAddress().getAddress();
					overseasAddress6.setAddress(address6);

					// 1.6.1.2.2.4.2.2 PostCodeAndTown
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getOverseasAddress().getPostCodeAndTown();
					overseasAddress6.setPostCodeAndTown(postCode6);

					// 1.6.1.2.2.4.2.3 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getOverseasAddress().getProvince();
					overseasAddress6.setProvince(province6);

					// 1.6.1.2.2.4.2.4 CountryCode	
					es.mityc.appfacturae.facturae.CountryType countryCode6 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
							.getOverseasAddress().getCountryCode().value());
					overseasAddress6.setCountryCode(countryCode6);

					individual.setOverseasAddress(overseasAddress6);

				} // if 6

				// 1.6.1.2.2.5 ContactDetails	
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getContactDetails() != null) {
					es.mityc.appfacturae.facturae.ContactDetailsType contactDetails6 = new es.mityc.appfacturae.facturae.ContactDetailsType();

					// 1.6.1.2.2.5.1 Telephone
					String telephone6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getContactDetails().getTelephone();
					if (!"".equals(telephone6))
						contactDetails6.setTelephone(telephone6);

					// 1.6.1.2.2.5.2 TeleFax
					String teleFax6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getContactDetails().getTeleFax();
					if (!"".equals(teleFax6))
						contactDetails6.setTeleFax(teleFax6);

					// 1.6.1.2.2.5.3 WebAddress	
					String webAddress6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getContactDetails().getWebAddress();
					if (!"".equals(webAddress6))
						contactDetails6.setWebAddress(webAddress6);

					// 1.6.1.2.2.5.4 ElectronicMail
					String electronicMail6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getContactDetails().getElectronicMail();
					if (!"".equals(electronicMail6))
						contactDetails6.setElectronicMail(electronicMail6);

					// 1.6.1.2.2.5.5 ContactPersons	
					String contactPersons6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getContactDetails().getContactPersons();
					if (!"".equals(contactPersons6))
						contactDetails6.setContactPersons(contactPersons6);

					// 1.6.1.2.2.5.6 CnoCnae	
					String cnoCnae6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getContactDetails().getCnoCnae();
					if (!"".equals(cnoCnae6))
						contactDetails6.setCnoCnae(cnoCnae6);

					// 1.6.1.2.2.5.7 INETownCode
					String ineTownCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getContactDetails().getINETownCode();
					if (!"".equals(ineTownCode6))
						contactDetails6.setIneTownCode(ineTownCode6);

					// 1.6.1.2.2.5.8 AdditionalContactDetails
					String additionalContactDetails6 = facturae.getFileHeader().getFactoringAssignmentData()
						.getAssignee().getIndividual().getContactDetails().getAdditionalContactDetails();
					if (!"".equals(additionalContactDetails6))
						contactDetails6.setAdditionalContactDetails(additionalContactDetails6);

					individual.setContactDetails(contactDetails6);
				}
				assignee.setIndividual(individual);

			} //if 4

			factoringAssignmentData.setAssignee(assignee);

			// 1.6.2 PaymentDetails
			es.mityc.appfacturae.facturae32.InstallmentsType paymentDetails = new es.mityc.appfacturae.facturae32.InstallmentsType();

			es.mityc.appfacturae.facturae32.InstallmentType installment2 = null;

			int installmentCount2 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
				.getInstallment().size();
			for (int j = 0; j < installmentCount2; j++) {

				installment2 = new es.mityc.appfacturae.facturae32.InstallmentType();

				// 1.6.2.1.1 InstallmentDueDate
				XMLGregorianCalendar installmentDueDate = facturae.getFileHeader().getFactoringAssignmentData()
					.getPaymentDetails().getInstallment().get(j).getInstallmentDueDate();
				installment2.setInstallmentDueDate(installmentDueDate);

				// 1.6.2.1.2 InstallmentAmount
				double installmentAmount = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
					.getInstallment().get(j).getInstallmentAmount();
				installment2.setInstallmentAmount(installmentAmount);

				// 1.6.2.1.3 PaymentMeans	
				String paymentMeans = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
					.getInstallment().get(j).getPaymentMeans();
				installment2.setPaymentMeans(paymentMeans);

				// 1.6.2.1.4 AccountToBeCredited
				if (facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails().getInstallment().get(j)
					.getAccountToBeCredited() != null) {
					es.mityc.appfacturae.facturae32.AccountType accountToBeCredited = new es.mityc.appfacturae.facturae32.AccountType();

					// 1.6.2.1.4.1

					// 1.6.2.1.4.1.1 IBAN
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeCredited().getIBAN())) {
						String iban2 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getIBAN();
						accountToBeCredited.setIban(iban2);
					}

					// 1.6.2.1.4.1.2 AccountNumber
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeCredited().getAccountNumber())) {
						String accountNumber2 = facturae.getFileHeader().getFactoringAssignmentData()
							.getPaymentDetails().getInstallment().get(j).getAccountToBeCredited().getAccountNumber();
						accountToBeCredited.setAccountNumber(accountNumber2);
					}

					// 1.6.2.1.4.2 BankCode
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeCredited().getBankCode())) {
						String bankCode = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getBankCode();
						accountToBeCredited.setBankCode(bankCode);
					}

					// 1.6.2.1.4.3 BranchCode
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeCredited().getBranchCode())) {
						String branchCode = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getBranchCode();
						accountToBeCredited.setBranchCode(branchCode);
					}

					// 1.6.2.1.4.4

					if (facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails().getInstallment()
						.get(j).getAccountToBeCredited().getBranchInSpainAddress() != null) {

						// 1.6.2.1.4.4.1 BranchInSpainAddress
						es.mityc.appfacturae.facturae32.AddressType branchInSpainAddress = new es.mityc.appfacturae.facturae32.AddressType();

						// 1.6.2.1.4.4.1.1 Address
						String address7 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress().getAddress();
						branchInSpainAddress.setAddress(address7);

						// 1.6.2.1.4.4.1.2 PostCode
						String postCode8 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress().getPostCode();
						branchInSpainAddress.setPostCode(postCode8);

						// 1.6.2.1.4.4.1.3 Town
						String town7 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress().getTown();
						branchInSpainAddress.setTown(town7);

						// 1.6.2.1.4.4.1.4 Province
						String province7 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress().getProvince();
						branchInSpainAddress.setProvince(province7);

						// 1.6.2.1.4.4.1.5 CountryCode
						es.mityc.appfacturae.facturae.CountryType countryCode7 = es.mityc.appfacturae.facturae.CountryType
							.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress()
								.getCountryCode().value());
						branchInSpainAddress.setCountryCode(countryCode7);

						accountToBeCredited.setBranchInSpainAddress(branchInSpainAddress);

					} else if (facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress() != null) {

						// 1.6.2.1.4.4.2 OverseasBranchAddress
						es.mityc.appfacturae.facturae32.OverseasAddressType overseasBranchAddress = new es.mityc.appfacturae.facturae32.OverseasAddressType();

						// 1.6.2.1.4.4.2.1 Address
						String address7 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress().getAddress();
						overseasBranchAddress.setAddress(address7);

						// 1.6.2.1.4.4.2.2 PostCodeAndTown
						String postCodeAndTown8 = facturae.getFileHeader().getFactoringAssignmentData()
							.getPaymentDetails().getInstallment().get(j).getAccountToBeCredited()
							.getOverseasBranchAddress().getPostCodeAndTown();
						overseasBranchAddress.setPostCodeAndTown(postCodeAndTown8);

						// 1.6.2.1.4.4.2.3 Province
						String province7 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress().getProvince();
						overseasBranchAddress.setProvince(province7);

						// 1.6.2.1.4.4.2.4 CountryCode
						es.mityc.appfacturae.facturae.CountryType countryCode7 = es.mityc.appfacturae.facturae.CountryType
							.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress()
								.getCountryCode().value());
						overseasBranchAddress.setCountryCode(countryCode7);

						accountToBeCredited.setOverseasBranchAddress(overseasBranchAddress);
					}

					// 1.6.2.1.4.5 BIC
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeCredited().getBIC())) {
						String BIC = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeCredited().getBIC();
						accountToBeCredited.setBic(BIC);
					}

					installment2.setAccountToBeCredited(accountToBeCredited);
				}

				// 1.6.2.1.5 PaymentReconciliationReference
				if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
					.getInstallment().get(j).getPaymentReconciliationReference())) {
					String paymentReconciliationReference = facturae.getFileHeader().getFactoringAssignmentData()
						.getPaymentDetails().getInstallment().get(j).getPaymentReconciliationReference();
					installment2.setPaymentReconciliationReference(paymentReconciliationReference);
				}

				// 1.6.2.1.6 AccountToBeDebited
				if (facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails().getInstallment().get(j)
					.getAccountToBeDebited() != null) {
					es.mityc.appfacturae.facturae32.AccountType accountToBeDebited = new es.mityc.appfacturae.facturae32.AccountType();

					// 1.6.2.1.6.1

					// 1.6.2.1.6.1.1 IBAN
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeDebited().getIBAN())) {
						String iban3 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getIBAN();
						accountToBeDebited.setIban(iban3);
					}

					// 1.6.2.1.6.1.2 AccountNumber
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeDebited().getAccountNumber())) {
						String accountNumber3 = facturae.getFileHeader().getFactoringAssignmentData()
							.getPaymentDetails().getInstallment().get(j).getAccountToBeDebited().getAccountNumber();
						accountToBeDebited.setAccountNumber(accountNumber3);
					}

					// 1.6.2.1.6.2 BankCode
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeDebited().getBankCode())) {
						String bankCode2 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getBankCode();
						accountToBeDebited.setBankCode(bankCode2);
					}

					// 1.6.2.1.6.3 BranchCode
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeDebited().getBranchCode())) {
						String branchCode2 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getBranchCode();
						accountToBeDebited.setBranchCode(branchCode2);
					}

					// 1.6.2.1.6.4 

					if (facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails().getInstallment()
						.get(j).getAccountToBeDebited().getBranchInSpainAddress() != null) {

						// 1.6.2.1.6.4.1 BranchInSpainAddress
						es.mityc.appfacturae.facturae32.AddressType branchInSpainAddress2 = new es.mityc.appfacturae.facturae32.AddressType();

						// 1.6.2.1.6.4.1.1 Address
						String address8 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress().getAddress();
						branchInSpainAddress2.setAddress(address8);

						// 1.6.2.1.6.4.1.2 PostCode
						String postCode9 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress().getPostCode();
						branchInSpainAddress2.setPostCode(postCode9);

						// 1.6.2.1.6.4.1.3 Town
						String town8 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress().getTown();
						branchInSpainAddress2.setTown(town8);

						// 1.6.2.1.6.4.1.4 Province
						String province8 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress().getProvince();
						branchInSpainAddress2.setProvince(province8);

						// 1.6.2.1.6.4.1.5 CountryCode
						es.mityc.appfacturae.facturae.CountryType countryCode8 = es.mityc.appfacturae.facturae.CountryType
							.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress()
								.getCountryCode().value());
						branchInSpainAddress2.setCountryCode(countryCode8);

						accountToBeDebited.setBranchInSpainAddress(branchInSpainAddress2);

					} else if (facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress() != null) {

						// 1.6.2.1.6.4.2 OverseasBranchAddress
						es.mityc.appfacturae.facturae32.OverseasAddressType overseasBranchAddress2 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

						// 1.6.2.1.6.4.2.1 Address
						String address8 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress().getAddress();
						overseasBranchAddress2.setAddress(address8);

						// 1.6.2.1.6.4.2.2 PostCodeAndTown
						String postCodeAndTown9 = facturae.getFileHeader().getFactoringAssignmentData()
							.getPaymentDetails().getInstallment().get(j).getAccountToBeDebited()
							.getOverseasBranchAddress().getPostCodeAndTown();
						overseasBranchAddress2.setPostCodeAndTown(postCodeAndTown9);

						// 1.6.2.1.6.4.2.3 Province
						String province8 = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress().getProvince();
						overseasBranchAddress2.setProvince(province8);

						// 1.6.2.1.6.4.2.4 CountryCode
						es.mityc.appfacturae.facturae.CountryType countryCode8 = es.mityc.appfacturae.facturae.CountryType
							.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress()
								.getCountryCode().value());
						overseasBranchAddress2.setCountryCode(countryCode8);

						accountToBeDebited.setOverseasBranchAddress(overseasBranchAddress2);

					}

					// 1.6.2.1.6.5 BIC
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
						.getInstallment().get(j).getAccountToBeDebited().getBIC())) {
						String BIC = facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
							.getInstallment().get(j).getAccountToBeDebited().getBIC();
						accountToBeDebited.setBic(BIC);
					}

					installment2.setAccountToBeDebited(accountToBeDebited);
				}

				// 1.6.2.1.7 CollectionAdditionalInformation
				if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
					.getInstallment().get(j).getCollectionAdditionalInformation())) {
					String collectionAdditionalInformation = facturae.getFileHeader().getFactoringAssignmentData()
						.getPaymentDetails().getInstallment().get(j).getCollectionAdditionalInformation();
					installment2.setCollectionAdditionalInformation(collectionAdditionalInformation);
				}

				// 1.6.2.1.8 RegulatoryReportingData
				if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
					.getInstallment().get(j).getRegulatoryReportingData())) {
					String regulatoryReportingData = facturae.getFileHeader().getFactoringAssignmentData()
						.getPaymentDetails().getInstallment().get(j).getRegulatoryReportingData();
					installment2.setRegulatoryReportingData(regulatoryReportingData);
				}

				// 1.6.2.1.9 DebitReconciliationReference
				if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getPaymentDetails()
					.getInstallment().get(j).getDebitReconciliationReference())) {
					String debitReconciliationReference = facturae.getFileHeader().getFactoringAssignmentData()
						.getPaymentDetails().getInstallment().get(j).getDebitReconciliationReference();
					installment2.setDebitReconciliationReference(debitReconciliationReference);
				}

				paymentDetails.getInstallment().add(installment2);

			}

			((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) factoringAssignmentData)
				.setPaymentDetails(paymentDetails);

			// 1.6.3 FactoringAssignmentClauses
			String factoringAssignmentClauses = facturae.getFileHeader().getFactoringAssignmentData()
				.getFactoringAssignmentClauses();
			factoringAssignmentData.setFactoringAssignmentClauses(factoringAssignmentClauses);

			fileHeader.setFactoringAssignmentData(factoringAssignmentData);
		}

		invoice.setFileHeader(fileHeader);

		// 2 PartiesType
		es.mityc.appfacturae.facturae.PartiesType parties = new es.mityc.appfacturae.facturae.PartiesType();

		// 2.1 SellerParty
		es.mityc.appfacturae.facturae.BusinessType sellerParty = new es.mityc.appfacturae.facturae.BusinessType();

		// 2.1.1 TaxIdentification
		es.mityc.appfacturae.facturae.TaxIdentificationType taxIdentification = new es.mityc.appfacturae.facturae.TaxIdentificationType();

		// 2.1.1.1 PersonTypeCode
		es.mityc.appfacturae.facturae.PersonTypeCodeType personTypeCode = es.mityc.appfacturae.facturae.PersonTypeCodeType
			.fromValue(facturae.getParties().getSellerParty().getTaxIdentification().getPersonTypeCode().value());
		taxIdentification.setPersonTypeCode(personTypeCode);

		// 2.1.1.2 ResidenceTypeCode
		es.mityc.appfacturae.facturae.ResidenceTypeCodeType residenceTypeCode = es.mityc.appfacturae.facturae.ResidenceTypeCodeType
			.fromValue(facturae.getParties().getSellerParty().getTaxIdentification().getResidenceTypeCode().value());
		taxIdentification.setResidenceTypeCode(residenceTypeCode);

		// 2.1.1.3 TaxIdentificationNumber
		String taxIdentificationNumber = facturae.getParties().getSellerParty().getTaxIdentification()
			.getTaxIdentificationNumber();
		taxIdentification.setTaxIdentificationNumber(taxIdentificationNumber);

		sellerParty.setTaxIdentification(taxIdentification);

		// 2.1.2 PartyIdentification
		if (!"".equals(facturae.getParties().getSellerParty().getPartyIdentification())) {
			String partyIdentification = facturae.getParties().getSellerParty().getPartyIdentification();
			sellerParty.setPartyIdentification(partyIdentification);
		}

		// 2.1.3 AdministrativeCentres
		if (facturae.getParties().getSellerParty().getAdministrativeCentres() != null
			&& facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre() != null
			&& facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre().size() > 0) {
			es.mityc.appfacturae.facturae.AdministrativeCentresType administrativeCentres = new es.mityc.appfacturae.facturae.AdministrativeCentresType();

			// 2.1.3.1 AdministrativeCentre
			es.mityc.appfacturae.facturae32.AdministrativeCentreType administrativeCentre = null;

			int adminCentresCount = facturae.getParties().getSellerParty().getAdministrativeCentres()
				.getAdministrativeCentre().size();
			for (int i = 0; i < adminCentresCount; i++) {

				administrativeCentre = new es.mityc.appfacturae.facturae32.AdministrativeCentreType();

				// 2.1.3.1.1 CentreCode
				String centreCode = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getCentreCode();
				if (!"".equals(centreCode))
					administrativeCentre.setCentreCode(centreCode);

				// 2.1.3.1.2 RoleTypeCode
				String roleTypeCode = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getRoleTypeCode();
				if (!"".equals(roleTypeCode))
					administrativeCentre.setRoleTypeCode(roleTypeCode);

				// 2.1.3.1.3 Name
				String name3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getName();
				if (!"".equals(name3))
					administrativeCentre.setName(name3);

				// 2.1.3.1.4 FirstSurname
				String firstSurname3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getFirstSurname();
				if (!"".equals(firstSurname3))
					administrativeCentre.setFirstSurname(firstSurname3);

				// 2.1.3.1.5 SecondSurname
				String secondSurname3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getSecondSurname();
				if (!"".equals(secondSurname3))
					administrativeCentre.setSecondSurname(secondSurname3);

				// 2.1.3.1.6 

				// if 7
				if (facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getAddressInSpain() != null) {

					// 2.1.3.1.6.1 AddressInSpain		
					es.mityc.appfacturae.facturae32.AddressType addressInSpain3 = new es.mityc.appfacturae.facturae32.AddressType();

					// 2.1.3.1.6.1.1 Address
					String address3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getAddress();
					addressInSpain3.setAddress(address3);

					// 2.1.3.1.6.1.2 PostCode
					String postCode3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getPostCode();
					addressInSpain3.setPostCode(postCode3);

					// 2.1.3.1.6.1.3 Town
					String town3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getTown();
					addressInSpain3.setTown(town3);

					// 2.1.3.1.6.1.4 Province
					String province3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getProvince();
					addressInSpain3.setProvince(province3);

					// 2.1.3.1.6.1.5 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode3 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getAddressInSpain().getCountryCode().value());
					addressInSpain3.setCountryCode(countryCode3);

					administrativeCentre.setAddressInSpain(addressInSpain3);

				} else if (facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre()
					.get(i).getOverseasAddress() != null) {

					// 2.1.3.1.6.2 OverSeasAddress
					es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress3 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

					// 2.1.3.1.6.2.1 Address
					String address3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getAddress();
					overseasAddress3.setAddress(address3);

					// 2.1.3.1.6.2.2 PostCodeAndTown
					String postCodeandTown3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getPostCodeAndTown();
					overseasAddress3.setPostCodeAndTown(postCodeandTown3);

					// 2.1.3.1.6.2.3 Province
					String province3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getProvince();
					overseasAddress3.setProvince(province3);

					// 2.1.3.1.6.2.4 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode3 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getOverseasAddress().getCountryCode().value());
					overseasAddress3.setCountryCode(countryCode3);

					administrativeCentre.setOverseasAddress(overseasAddress3);

				} // if 7

				// 2.1.3.1.7 ContactDetails
				if (facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getContactDetails() != null) {
					es.mityc.appfacturae.facturae.ContactDetailsType contactDetails3 = new es.mityc.appfacturae.facturae.ContactDetailsType();

					// 2.1.3.1.7.1 Telephone
					String telephone3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTelephone();
					if (!"".equals(telephone3))
						contactDetails3.setTelephone(telephone3);

					// 2.1.3.1.7.2 TeleFax
					String teleFax3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTeleFax();
					if (!"".equals(teleFax3))
						contactDetails3.setTeleFax(teleFax3);

					// 2.1.3.1.7.3 WebAddress
					String webAddress3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getWebAddress();
					if (!"".equals(webAddress3))
						contactDetails3.setWebAddress(webAddress3);

					// 2.1.3.1.7.4 ElectronicMail
					String electronicMail3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getElectronicMail();
					if (!"".equals(electronicMail3))
						contactDetails3.setElectronicMail(electronicMail3);

					// 2.1.3.1.7.5 ContactPersons
					String contactPersons3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getContactPersons();
					if (!"".equals(contactPersons3))
						contactDetails3.setContactPersons(contactPersons3);

					// 2.1.3.1.7.6 CnoCnae
					String cnoCnae3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getCnoCnae();
					if (!"".equals(cnoCnae3))
						contactDetails3.setCnoCnae(cnoCnae3);

					// 2.1.3.1.7.7 INETownCode
					String ineTownCode3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getINETownCode();
					if (!"".equals(ineTownCode3))
						contactDetails3.setIneTownCode(ineTownCode3);

					// 2.1.3.1.7.8 AdditionalContactDetails
					String additionalContactDetails3 = facturae.getParties().getSellerParty()
						.getAdministrativeCentres().getAdministrativeCentre().get(i).getContactDetails()
						.getAdditionalContactDetails();
					if (!"".equals(additionalContactDetails3))
						contactDetails3.setAdditionalContactDetails(additionalContactDetails3);
					administrativeCentre.setContactDetails(contactDetails3);
				}

				// 2.1.3.1.8 PhysicalGLN
				String physicalGLN = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getPhysicalGLN();
				if (!"".equals(physicalGLN))
					administrativeCentre.setPhysicalGLN(physicalGLN);

				// 2.1.3.1.9 LogicalOperationPoint
				String logicalOperationalPoint = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getLogicalOperationalPoint();
				if (!"".equals(logicalOperationalPoint))
					administrativeCentre.setLogicalOperationalPoint(logicalOperationalPoint);

				// 2.1.3.1.10 CentreDescription
				String centreDescription = facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getCentreDescription();
				if (!"".equals(centreDescription))
					administrativeCentre.setCentreDescription(centreDescription);

				administrativeCentres.getAdministrativeCentre().add(administrativeCentre);

			} //for

			sellerParty.setAdministrativeCentres(administrativeCentres);
		}

		// if 8
		if (facturae.getParties().getSellerParty().getLegalEntity() != null) {

			// 2.1.4.1 LegalEntity
			es.mityc.appfacturae.facturae.LegalEntityType legalEntity = new es.mityc.appfacturae.facturae.LegalEntityType();

			// 2.1.4.1.1 CorporateName
			String corporateName = facturae.getParties().getSellerParty().getLegalEntity().getCorporateName();
			legalEntity.setCorporateName(corporateName);

			// 2.1.4.1.2 TradeName
			if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getTradeName())) {
				String tradeName = facturae.getParties().getSellerParty().getLegalEntity().getTradeName();
				legalEntity.setTradeName(tradeName);
			}

			// 2.1.4.1.3 RegistrationData
			if (facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData() != null) {
				es.mityc.appfacturae.facturae.RegistrationDataType registrationData = new es.mityc.appfacturae.facturae.RegistrationDataType();

				// 2.1.4.1.3.1 Book
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getBook())) {
					String book = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getBook();
					registrationData.setBook(book);
				}

				// 2.1.4.1.3.2 RegisterOfCompaniesLocation
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getRegisterOfCompaniesLocation())) {
					String registerOfCompaniesLocation = facturae.getParties().getSellerParty().getLegalEntity()
						.getRegistrationData().getRegisterOfCompaniesLocation();
					registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);
				}

				// 2.1.4.1.3.3 Sheet
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getSheet())) {
					String sheet = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getSheet();
					registrationData.setSheet(sheet);
				}

				// 2.1.4.1.3.4 Folio
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getFolio())) {
					String folio = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getFolio();
					registrationData.setFolio(folio);
				}

				// 2.1.4.1.3.5 Section
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getSection())) {
					String section = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getSection();
					registrationData.setSection(section);
				}

				// 2.1.4.1.3.6 Volume
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getVolume())) {
					String volume = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getVolume();
					registrationData.setVolume(volume);
				}

				// 2.1.4.1.3.7 AdditionalRegistrationData
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
					.getAdditionalRegistrationData())) {
					String additionalRegistrationData = facturae.getParties().getSellerParty().getLegalEntity()
						.getRegistrationData().getAdditionalRegistrationData();
					registrationData.setAdditionalRegistrationData(additionalRegistrationData);
				}

				legalEntity.setRegistrationData(registrationData);
			}

			//2.1.4.1.4

			// if 9
			if (facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain() != null) {

				//2.1.4.1.4.1 AddressInSpain
				es.mityc.appfacturae.facturae32.AddressType addressInSpain = new es.mityc.appfacturae.facturae32.AddressType();

				// 2.1.4.1.4.1.1 Address
				String address = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain().getAddress();
				addressInSpain.setAddress(address);

				// 2.1.4.1.4.1.2 PostCode
				String postCode = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain().getPostCode();
				addressInSpain.setPostCode(postCode);

				// 2.1.4.1.4.1.3 Town
				String town = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain().getTown();
				addressInSpain.setTown(town);

				// 2.1.4.1.4.1.4 Province
				String province = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain().getProvince();
				addressInSpain.setProvince(province);

				// 2.1.4.1.4.1.5 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain().getCountryCode().value());
				addressInSpain.setCountryCode(countryCode);

				legalEntity.setAddressInSpain(addressInSpain);

			} else if (facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress() != null) {

				// 2.1.4.1.4.2 OverseasAddress
				es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress = new es.mityc.appfacturae.facturae32.OverseasAddressType();
				// 2.1.4.1.4.2.1 Address
				String address = facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress().getAddress();
				overseasAddress.setAddress(address);

				// 2.1.4.1.4.2.2 PostCodeAndTown
				String postCodeandTown = facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress().getPostCodeAndTown();
				overseasAddress.setPostCodeAndTown(postCodeandTown);

				// 2.1.4.1.4.2.3 Province
				String province = facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress().getProvince();
				overseasAddress.setProvince(province);

				// 2.1.4.1.4.2.4 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress().getCountryCode().value());
				overseasAddress.setCountryCode(countryCode);

				legalEntity.setOverseasAddress(overseasAddress);

			} // if 9

			// 2.1.4.1.5 ContactDetails
			if (facturae.getParties().getSellerParty().getLegalEntity().getContactDetails() != null) {
				es.mityc.appfacturae.facturae.ContactDetailsType contactDetails = new es.mityc.appfacturae.facturae.ContactDetailsType();

				// 2.1.4.1.5.1 Telephone
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails().getTelephone())) {
					String telephone = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails().getTelephone();
					contactDetails.setTelephone(telephone);
				}

				// 2.1.4.1.5.2 TeleFax
				if (!""
					.equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails().getTeleFax())) {
					String teleFax = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails().getTeleFax();
					contactDetails.setTeleFax(teleFax);
				}

				// 2.1.4.1.5.3 WebAddress
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getWebAddress())) {
					String webAddress = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getWebAddress();
					contactDetails.setWebAddress(webAddress);
				}

				// 2.1.4.1.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getElectronicMail())) {
					String electronicMail = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getElectronicMail();
					contactDetails.setElectronicMail(electronicMail);
				}

				// 2.1.4.1.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getContactPersons())) {
					String contactPersons = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getContactPersons();
					contactDetails.setContactPersons(contactPersons);
				}

				// 2.1.4.1.5.6 CnoCnae
				if (!""
					.equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails().getCnoCnae())) {
					String cnoCnae = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getCnoCnae();
					contactDetails.setCnoCnae(cnoCnae);
				}

				// 2.1.4.1.5.7 INETownCode
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getINETownCode())) {
					String ineTownCode = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getINETownCode();
					contactDetails.setIneTownCode(ineTownCode);
				}

				// 2.1.4.1.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails = facturae.getParties().getSellerParty().getLegalEntity()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails.setAdditionalContactDetails(additionalContactDetails);
				}

				legalEntity.setContactDetails(contactDetails);
			}

			String query = "SELECT FACE FROM LEGAL_ENTITY LE WHERE CORPORATE_NAME = '"
				+ DatabaseUtil.escapeChars(legalEntity.getCorporateName()) + "' ORDER BY PARTY_ID DESC";
			SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
			List<?> ls = s.list();
			Boolean face = null;
			if (ls != null && ls.size() > 0)
				face = (Boolean) ls.get(0);
			if (face != null) {
				legalEntity.setFace(face.booleanValue());
			}

			sellerParty.setLegalEntity(legalEntity);

		} else if (facturae.getParties().getSellerParty().getIndividual() != null) {

			// 2.1.4.2 Individual
			es.mityc.appfacturae.facturae.IndividualType individual = new es.mityc.appfacturae.facturae.IndividualType();

			// 2.1.4.2.1 Name
			String name = facturae.getParties().getSellerParty().getIndividual().getName();
			individual.setName(name);

			// 2.1.4.2.2 FirstSurname
			String firstSurname = facturae.getParties().getSellerParty().getIndividual().getFirstSurname();
			individual.setFirstSurname(firstSurname);

			// 2.1.4.2.3 SecondSurname
			if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getSecondSurname())) {
				String secondSurname = facturae.getParties().getSellerParty().getIndividual().getSecondSurname();
				individual.setSecondSurname(secondSurname);
			}

			// 2.1.4.2.4

			// if 10
			if (facturae.getParties().getSellerParty().getIndividual().getAddressInSpain() != null) {

				// 2.1.4.2.4.1 AddressInSpain
				es.mityc.appfacturae.facturae32.AddressType addressInSpain = new es.mityc.appfacturae.facturae32.AddressType();

				// 2.1.4.2.4.1.1 Address
				String address = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain()
					.getAddress();
				addressInSpain.setAddress(address);

				// 2.1.4.2.4.1.2 PostCode
				String postCode = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain()
					.getPostCode();
				addressInSpain.setPostCode(postCode);

				// 2.1.4.2.4.1.3 Town
				String town = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain().getTown();
				addressInSpain.setTown(town);

				// 2.1.4.2.4.1.4 Province
				String province = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain()
					.getProvince();
				addressInSpain.setProvince(province);

				// 2.1.4.2.4.1.5 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getSellerParty().getIndividual().getAddressInSpain()
						.getCountryCode().value());
				addressInSpain.setCountryCode(countryCode);

				individual.setAddressInSpain(addressInSpain);

			} else if (facturae.getParties().getSellerParty().getIndividual().getOverseasAddress() != null) {

				// 2.1.4.2.4.2 OverseasAddress
				es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress = new es.mityc.appfacturae.facturae32.OverseasAddressType();

				// 2.1.4.2.4.2.1 Address
				String address = facturae.getParties().getSellerParty().getIndividual().getOverseasAddress()
					.getAddress();
				overseasAddress.setAddress(address);

				// 2.1.4.2.4.2.2 PostCodeAndTown
				String postCodeandTown = facturae.getParties().getSellerParty().getIndividual().getOverseasAddress()
					.getPostCodeAndTown();
				overseasAddress.setPostCodeAndTown(postCodeandTown);

				// 2.1.4.2.4.2.3 Province
				String province = facturae.getParties().getSellerParty().getIndividual().getOverseasAddress()
					.getProvince();
				overseasAddress.setProvince(province);

				// 2.1.4.2.4.2.4 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getSellerParty().getIndividual().getOverseasAddress()
						.getCountryCode().value());
				overseasAddress.setCountryCode(countryCode);

				individual.setOverseasAddress(overseasAddress);

			} // if 10

			// 2.1.4.2.5 ContactDetails
			if (facturae.getParties().getSellerParty().getIndividual().getContactDetails() != null) {
				es.mityc.appfacturae.facturae.ContactDetailsType contactDetails = new es.mityc.appfacturae.facturae.ContactDetailsType();

				// 2.1.4.2.5.1 Telephone
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getTelephone())) {
					String telephone = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getTelephone();
					contactDetails.setTelephone(telephone);
				}

				// 2.1.4.2.5.2 TeleFax
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails().getTeleFax())) {
					String teleFax = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getTeleFax();
					contactDetails.setTeleFax(teleFax);
				}

				// 2.1.4.2.5.3 WebAddress
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getWebAddress())) {
					String webAddress = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getWebAddress();
					contactDetails.setWebAddress(webAddress);
				}

				// 2.1.4.2.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getElectronicMail())) {
					String electronicMail = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getElectronicMail();
					contactDetails.setElectronicMail(electronicMail);
				}

				// 2.1.4.2.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getContactPersons())) {
					String contactPersons = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getContactPersons();
					contactDetails.setContactPersons(contactPersons);
				}

				// 2.1.4.2.5.6 CnoCnae
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails().getCnoCnae())) {
					String cnoCnae = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getCnoCnae();
					contactDetails.setCnoCnae(cnoCnae);
				}

				// 2.1.4.2.5.7 INETownCode
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getINETownCode())) {
					String ineTownCode = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getINETownCode();
					contactDetails.setIneTownCode(ineTownCode);
				}

				// 2.1.4.2.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails = facturae.getParties().getSellerParty().getIndividual()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails.setAdditionalContactDetails(additionalContactDetails);
				}

				individual.setContactDetails(contactDetails);
			}

			sellerParty.setIndividual(individual);

		} // if 8

		parties.setSellerParty(sellerParty);

		// 2.2 BuyerParty 	
		es.mityc.appfacturae.facturae.BusinessType buyerParty = new es.mityc.appfacturae.facturae.BusinessType();

		// 2.2.1 TaxIdentification
		es.mityc.appfacturae.facturae.TaxIdentificationType taxIdentification2 = new es.mityc.appfacturae.facturae.TaxIdentificationType();

		// 2.2.1.1 PersonTypeCode
		es.mityc.appfacturae.facturae.PersonTypeCodeType personTypeCode2 = es.mityc.appfacturae.facturae.PersonTypeCodeType
			.fromValue(facturae.getParties().getBuyerParty().getTaxIdentification().getPersonTypeCode().value());
		taxIdentification2.setPersonTypeCode(personTypeCode2);

		// 2.2.1.2 ResidenceTypeCode
		es.mityc.appfacturae.facturae.ResidenceTypeCodeType residenceTypeCode2 = es.mityc.appfacturae.facturae.ResidenceTypeCodeType
			.fromValue(facturae.getParties().getBuyerParty().getTaxIdentification().getResidenceTypeCode().value());
		taxIdentification2.setResidenceTypeCode(residenceTypeCode2);

		// 2.2.1.3 TaxIdentificationNumber
		String taxIdentificationNumber2 = facturae.getParties().getBuyerParty().getTaxIdentification()
			.getTaxIdentificationNumber();
		taxIdentification2.setTaxIdentificationNumber(taxIdentificationNumber2);

		buyerParty.setTaxIdentification(taxIdentification2);

		// 2.2.2 PartyIdentification
		if (!"".equals(facturae.getParties().getBuyerParty().getPartyIdentification())) {
			String partyIdentification2 = facturae.getParties().getBuyerParty().getPartyIdentification();
			buyerParty.setPartyIdentification(partyIdentification2);
		}

		// 2.2.3 AdministrativeCentres
		if (facturae.getParties().getBuyerParty().getAdministrativeCentres() != null
			&& facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre() != null
			&& facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().size() > 0) {
			es.mityc.appfacturae.facturae.AdministrativeCentresType administrativeCentres2 = new es.mityc.appfacturae.facturae.AdministrativeCentresType();

			// 2.2.3.1 AdministrativeCentre
			es.mityc.appfacturae.facturae32.AdministrativeCentreType administrativeCentre2 = null;

			int adminCentresCount = facturae.getParties().getBuyerParty().getAdministrativeCentres()
				.getAdministrativeCentre().size();
			for (int i = 0; i < adminCentresCount; i++) {

				administrativeCentre2 = new es.mityc.appfacturae.facturae32.AdministrativeCentreType();

				// 2.2.3.1.1 CentreCode	  	
				String centreCode2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getCentreCode();
				if (!"".equals(centreCode2))
					administrativeCentre2.setCentreCode(centreCode2);

				// 2.2.3.1.2 RoleTypeCode 
				String roleTypeCode2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getRoleTypeCode();
				if (!"".equals(roleTypeCode2))
					administrativeCentre2.setRoleTypeCode(roleTypeCode2);

				// 2.2.3.1.3 Name	
				String name4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getName();
				if (!"".equals(name4))
					administrativeCentre2.setName(name4);

				// 2.2.3.1.4 FirstSurname  
				String firstSurname4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getFirstSurname();
				if (!"".equals(firstSurname4))
					administrativeCentre2.setFirstSurname(firstSurname4);

				// 2.2.3.1.5 SecondSurname	
				String secondSurname4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getSecondSurname();
				if (!"".equals(secondSurname4))
					administrativeCentre2.setSecondSurname(secondSurname4);

				// 2.2.3.1.6 

				// if 11
				if (facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getAddressInSpain() != null) {

					// 2.2.3.1.6.1 AddressInSpain
					es.mityc.appfacturae.facturae32.AddressType addressInSpain4 = new es.mityc.appfacturae.facturae32.AddressType();

					// 2.2.3.1.6.1.1 Address
					String address4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getAddress();
					addressInSpain4.setAddress(address4);

					// 2.2.3.1.6.1.2 PostCode
					String postCode4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getPostCode();
					addressInSpain4.setPostCode(postCode4);

					// 2.2.3.1.6.1.3 Town
					String town4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getTown();
					addressInSpain4.setTown(town4);

					// 2.2.3.1.6.1.4 Province
					String province4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getProvince();
					addressInSpain4.setProvince(province4);

					// 2.2.3.1.6.1.5 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode4 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getAddressInSpain().getCountryCode().value());
					addressInSpain4.setCountryCode(countryCode4);

					administrativeCentre2.setAddressInSpain(addressInSpain4);

				} else if (facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre()
					.get(i).getOverseasAddress() != null) {

					// 2.2.3.1.6.2 OverSeasAddress
					es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress4 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

					// 2.2.3.1.6.2.1 Address
					String address4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getAddress();
					overseasAddress4.setAddress(address4);

					// 2.2.3.1.6.2.2 PostCodeAndTown
					String postCodeAndTown4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getPostCodeAndTown();
					overseasAddress4.setPostCodeAndTown(postCodeAndTown4);

					// 2.2.3.1.6.2.3 Province
					String province4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getProvince();
					overseasAddress4.setProvince(province4);

					// 2.2.3.1.6.2.4 CountryCode
					es.mityc.appfacturae.facturae.CountryType countryCode4 = es.mityc.appfacturae.facturae.CountryType
						.fromValue(facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getOverseasAddress().getCountryCode().value());
					overseasAddress4.setCountryCode(countryCode4);

					administrativeCentre2.setOverseasAddress(overseasAddress4);

				} // if 11

				// 2.2.3.1.7 ContactDetails
				if (facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getContactDetails() != null) {
					es.mityc.appfacturae.facturae.ContactDetailsType contactDetails4 = new es.mityc.appfacturae.facturae.ContactDetailsType();

					// 2.2.3.1.7.1 Telephone
					String telephone4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTelephone();
					if (!"".equals(telephone4))
						contactDetails4.setTelephone(telephone4);

					// 2.2.3.1.7.2 TeleFax
					String teleFax4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTeleFax();
					if (!"".equals(teleFax4))
						contactDetails4.setTeleFax(teleFax4);

					// 2.2.3.1.7.3 WebAddress
					String webAddress4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getWebAddress();
					if (!"".equals(webAddress4))
						contactDetails4.setWebAddress(webAddress4);

					// 2.2.3.1.7.4 ElectronicMail
					String electronicMail4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getElectronicMail();
					if (!"".equals(electronicMail4))
						contactDetails4.setElectronicMail(electronicMail4);

					// 2.2.3.1.7.5 ContactPersons
					String contactPersons4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getContactPersons();
					if (!"".equals(contactPersons4))
						contactDetails4.setContactPersons(contactPersons4);

					// 2.2.3.1.7.6 CnoCnae
					String cnoCnae4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getCnoCnae();
					if (!"".equals(cnoCnae4))
						contactDetails4.setCnoCnae(cnoCnae4);

					// 2.2.3.1.7.7 INETownCode
					String ineTownCode4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getINETownCode();
					if (!"".equals(ineTownCode4))
						contactDetails4.setIneTownCode(ineTownCode4);

					// 2.2.3.1.7.8 AdditionalContactDetails
					String additionalContactDetails4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getAdditionalContactDetails();
					if (!"".equals(additionalContactDetails4))
						contactDetails4.setAdditionalContactDetails(additionalContactDetails4);

					administrativeCentre2.setContactDetails(contactDetails4);
				}

				// 2.2.3.1.8 PhysicalGLN
				String physicalGLN2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getPhysicalGLN();
				if (!"".equals(physicalGLN2))
					administrativeCentre2.setPhysicalGLN(physicalGLN2);

				// 2.2.3.1.9 LogicalOperationPoint
				String logicalOperationalPoint2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getLogicalOperationalPoint();
				if (!"".equals(logicalOperationalPoint2))
					administrativeCentre2.setLogicalOperationalPoint(logicalOperationalPoint2);

				// 2.2.3.1.10 CentreDescription
				String centreDescription = facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getCentreDescription();
				if (!"".equals(centreDescription))
					administrativeCentre2.setCentreDescription(centreDescription);

				administrativeCentres2.getAdministrativeCentre().add(administrativeCentre2);

			} //for

			buyerParty.setAdministrativeCentres(administrativeCentres2);
		}

		// 2.2.4 

		// if 12
		if (facturae.getParties().getBuyerParty().getLegalEntity() != null) {

			// 2.2.4.1 LegalEntity
			es.mityc.appfacturae.facturae.LegalEntityType legalEntity = new es.mityc.appfacturae.facturae.LegalEntityType();

			// 2.2.4.1.1 CorporateName
			String corporateName = facturae.getParties().getBuyerParty().getLegalEntity().getCorporateName();
			legalEntity.setCorporateName(corporateName);

			// 2.2.4.1.2 TradeName
			if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getTradeName())) {
				String tradeName = facturae.getParties().getBuyerParty().getLegalEntity().getTradeName();
				legalEntity.setTradeName(tradeName);
			}

			// 2.2.4.1.3 RegistrationData
			if (facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData() != null) {
				es.mityc.appfacturae.facturae.RegistrationDataType registrationData = new es.mityc.appfacturae.facturae.RegistrationDataType();

				// 2.2.4.1.3.1 Book
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getBook())) {
					String book = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getBook();
					registrationData.setBook(book);
				}

				// 2.2.4.1.3.2 RegisterOfCompaniesLocation
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
					.getRegisterOfCompaniesLocation())) {
					String registerOfCompaniesLocation = facturae.getParties().getBuyerParty().getLegalEntity()
						.getRegistrationData().getRegisterOfCompaniesLocation();
					registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);
				}

				// 2.2.4.1.3.3 Sheet
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getSheet())) {
					String sheet = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getSheet();
					registrationData.setSheet(sheet);
				}

				// 2.2.4.1.3.4 Folio
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getFolio())) {
					String folio = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getFolio();
					registrationData.setFolio(folio);
				}

				// 2.2.4.1.3.5 Section
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
					.getSection())) {
					String section = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getSection();
					registrationData.setSection(section);
				}

				// 2.2.4.1.3.6 Volume
				if (!""
					.equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getVolume())) {
					String volume = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getVolume();
					registrationData.setVolume(volume);
				}

				// 2.2.4.1.3.7 AdditionalRegistrationData
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
					.getAdditionalRegistrationData())) {
					String additionalRegistrationData = facturae.getParties().getBuyerParty().getLegalEntity()
						.getRegistrationData().getAdditionalRegistrationData();
					registrationData.setAdditionalRegistrationData(additionalRegistrationData);
				}

				legalEntity.setRegistrationData(registrationData);
			}

			//2.2.4.1.4

			// if 13
			if (facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain() != null) {

				//2.2.4.1.4.1 AddressInSpain
				es.mityc.appfacturae.facturae32.AddressType addressInSpain2 = new es.mityc.appfacturae.facturae32.AddressType();

				// 2.2.4.1.4.1.1 Address
				String address2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain()
					.getAddress();
				addressInSpain2.setAddress(address2);

				// 2.2.4.1.4.1.2 PostCode
				String postCode2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain()
					.getPostCode();
				addressInSpain2.setPostCode(postCode2);

				// 2.2.4.1.4.1.3 Town
				String town2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain().getTown();
				addressInSpain2.setTown(town2);

				// 2.2.4.1.4.1.4 Province
				String province2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain()
					.getProvince();
				addressInSpain2.setProvince(province2);

				// 2.2.4.1.4.1.5 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode2 = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain()
						.getCountryCode().value());
				addressInSpain2.setCountryCode(countryCode2);

				legalEntity.setAddressInSpain(addressInSpain2);

			} else if (facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress() != null) {

				// 2.2.4.1.4.2 OverseasAddress
				es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress2 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

				// 2.2.4.1.4.2.1 Address
				String address2 = facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress()
					.getAddress();
				overseasAddress2.setAddress(address2);

				// 2.2.4.1.4.2.2 PostCodeAndTown
				String postCodeAndTown2 = facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress()
					.getPostCodeAndTown();
				overseasAddress2.setPostCodeAndTown(postCodeAndTown2);

				// 2.2.4.1.4.2.3 Province
				String province2 = facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress()
					.getProvince();
				overseasAddress2.setProvince(province2);

				// 2.2.4.1.4.2.4 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode2 = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress()
						.getCountryCode().value());
				overseasAddress2.setCountryCode(countryCode2);

				legalEntity.setOverseasAddress(overseasAddress2);

			} // if 13

			// 2.2.4.1.5 ContactDetails
			if (facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails() != null) {
				es.mityc.appfacturae.facturae.ContactDetailsType contactDetails2 = new es.mityc.appfacturae.facturae.ContactDetailsType();

				// 2.2.4.1.5.1 Telephone
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getTelephone())) {
					String telephone2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getTelephone();
					contactDetails2.setTelephone(telephone2);
				}

				// 2.2.4.1.5.2 TeleFax
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails().getTeleFax())) {
					String teleFax2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getTeleFax();
					contactDetails2.setTeleFax(teleFax2);
				}

				// 2.2.4.1.5.3 WebAddress
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getWebAddress())) {
					String webAddress2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getWebAddress();
					contactDetails2.setWebAddress(webAddress2);
				}

				// 2.2.4.1.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getElectronicMail())) {
					String electronicMail2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getElectronicMail();
					contactDetails2.setElectronicMail(electronicMail2);
				}

				// 2.2.4.1.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getContactPersons())) {
					String contactPersons2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getContactPersons();
					contactDetails2.setContactPersons(contactPersons2);
				}

				// 2.2.4.1.5.6 CnoCnae
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails().getCnoCnae())) {
					String cnoCnae2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getCnoCnae();
					contactDetails2.setCnoCnae(cnoCnae2);
				}

				// 2.2.4.1.5.7 INETownCode
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getINETownCode())) {
					String ineTownCode2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getINETownCode();
					contactDetails2.setIneTownCode(ineTownCode2);
				}

				// 2.2.4.1.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails2 = facturae.getParties().getBuyerParty().getLegalEntity()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails2.setAdditionalContactDetails(additionalContactDetails2);
				}

				legalEntity.setContactDetails(contactDetails2);
			}

			String query = "SELECT FACE FROM LEGAL_ENTITY LE WHERE CORPORATE_NAME = '"
				+ DatabaseUtil.escapeChars(legalEntity.getCorporateName()) + "' ORDER BY PARTY_ID DESC";
			SQLQuery s = FacturaeManager.getInstance().executeQuery(query);
			List<?> ls = s.list();
			Boolean face = null;
			if (ls != null && ls.size() > 0)
				face = (Boolean) ls.get(0);
			if (face != null) {
				legalEntity.setFace(face.booleanValue());
			}

			buyerParty.setLegalEntity(legalEntity);

		} else if (facturae.getParties().getBuyerParty().getIndividual() != null) {

			// 2.2.4.2 Individual
			es.mityc.appfacturae.facturae.IndividualType individual2 = new es.mityc.appfacturae.facturae.IndividualType();

			// 2.2.4.2.1 Name
			String name2 = facturae.getParties().getBuyerParty().getIndividual().getName();
			individual2.setName(name2);

			// 2.2.4.2.2 FirstSurname
			String firstSurname2 = facturae.getParties().getBuyerParty().getIndividual().getFirstSurname();
			individual2.setFirstSurname(firstSurname2);

			// 2.2.4.2.3 SecondSurname
			if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getSecondSurname())) {
				String secondSurname2 = facturae.getParties().getBuyerParty().getIndividual().getSecondSurname();
				individual2.setSecondSurname(secondSurname2);
			}

			// 2.2.4.2.4

			// if 14
			if (facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain() != null) {

				//2.2.4.2.4.1 AddressInSpain
				es.mityc.appfacturae.facturae32.AddressType addressInSpain2 = new es.mityc.appfacturae.facturae32.AddressType();

				// 2.2.4.2.4.1.1 Address
				String address2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain()
					.getAddress();
				addressInSpain2.setAddress(address2);

				// 2.2.4.2.4.1.2 PostCode
				String postCode2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain()
					.getPostCode();
				addressInSpain2.setPostCode(postCode2);

				// 2.2.4.2.4.1.3 Town
				String town2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain().getTown();
				addressInSpain2.setTown(town2);

				// 2.2.4.2.4.1.4 Province
				String province2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain()
					.getProvince();
				addressInSpain2.setProvince(province2);

				// 2.2.4.2.4.1.5 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode2 = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain()
						.getCountryCode().value());
				addressInSpain2.setCountryCode(countryCode2);

				individual2.setAddressInSpain(addressInSpain2);

			} else if (facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress() != null) {

				// 2.2.4.2.4.2 OverseasAddress
				es.mityc.appfacturae.facturae32.OverseasAddressType overseasAddress2 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

				// 2.2.4.2.4.2.1 Address
				String address2 = facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress()
					.getAddress();
				overseasAddress2.setAddress(address2);

				// 2.2.4.2.4.2.2 PostCodeAndTown
				String postCodeAndTown2 = facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress()
					.getPostCodeAndTown();
				overseasAddress2.setPostCodeAndTown(postCodeAndTown2);

				// 2.2.4.2.4.2.3 Province
				String province2 = facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress()
					.getProvince();
				overseasAddress2.setProvince(province2);

				// 2.2.4.2.4.2.4 CountryCode
				es.mityc.appfacturae.facturae.CountryType countryCode2 = es.mityc.appfacturae.facturae.CountryType
					.fromValue(facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress()
						.getCountryCode().value());
				overseasAddress2.setCountryCode(countryCode2);

				individual2.setOverseasAddress(overseasAddress2);

			} // if 14

			// 2.2.4.2.5 ContactDetails
			if (facturae.getParties().getBuyerParty().getIndividual().getContactDetails() != null) {
				es.mityc.appfacturae.facturae.ContactDetailsType contactDetails2 = new es.mityc.appfacturae.facturae.ContactDetailsType();

				// 2.2.4.2.5.1 Telephone
				if (!""
					.equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails().getTelephone())) {
					String telephone2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getTelephone();
					contactDetails2.setTelephone(telephone2);
				}

				// 2.2.4.2.5.2 TeleFax
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails().getTeleFax())) {
					String teleFax2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getTeleFax();
					contactDetails2.setTeleFax(teleFax2);
				}

				// 2.2.4.2.5.3 WebAddress
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getWebAddress())) {
					String webAddress2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getWebAddress();
					contactDetails2.setWebAddress(webAddress2);
				}

				// 2.2.4.2.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getElectronicMail())) {
					String electronicMail2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getElectronicMail();
					contactDetails2.setElectronicMail(electronicMail2);
				}

				// 2.2.4.2.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getContactPersons())) {
					String contactPersons2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getContactPersons();
					contactDetails2.setContactPersons(contactPersons2);
				}

				// 2.2.4.2.5.6 CnoCnae
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails().getCnoCnae())) {
					String cnoCnae2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getCnoCnae();
					contactDetails2.setCnoCnae(cnoCnae2);
				}

				// 2.2.4.2.5.7 INETownCode
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getINETownCode())) {
					String ineTownCode2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getINETownCode();
					contactDetails2.setIneTownCode(ineTownCode2);
				}

				// 2.2.4.2.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails2 = facturae.getParties().getBuyerParty().getIndividual()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails2.setAdditionalContactDetails(additionalContactDetails2);
				}

				individual2.setContactDetails(contactDetails2);
			}

			buyerParty.setIndividual(individual2);

		} // if 12

		parties.setBuyerParty(buyerParty);

		invoice.setParties(parties);

		// 3 Invoices
		es.mityc.appfacturae.facturae.InvoicesType invoices = new es.mityc.appfacturae.facturae.InvoicesType();

		// 3.1 Invoice
		es.mityc.appfacturae.facturae32.InvoiceType invoiceType = null;

		// for 1
		int invoicesCount2 = facturae.getInvoices().getInvoice().size();
		for (int i = 0; i < invoicesCount2; i++) {

			invoiceType = new es.mityc.appfacturae.facturae32.InvoiceType();

			// 3.1.1 InvoiceHeader
			es.mityc.appfacturae.facturae.InvoiceHeaderType invoiceHeader = new es.mityc.appfacturae.facturae.InvoiceHeaderType();

			// 3.1.1.1 InvoiceNumber
			String invoiceNumber = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceNumber();
			invoiceHeader.setInvoiceNumber(invoiceNumber);

			// 3.1.1.2 InvoiceSeriesCode
			if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceSeriesCode())) {
				String invoiceSeriesCode = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
					.getInvoiceSeriesCode();
				invoiceHeader.setInvoiceSeriesCode(invoiceSeriesCode);
			}

			// 3.1.1.3 InvoiceDocumentType
			es.mityc.appfacturae.facturae.InvoiceDocumentTypeType invoiceDocumentType = es.mityc.appfacturae.facturae.InvoiceDocumentTypeType
				.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceDocumentType()
					.value());
			invoiceHeader.setInvoiceDocumentType(invoiceDocumentType);

			// 3.1.1.4 InvoiceClass 	
			es.mityc.appfacturae.facturae.InvoiceClassType invoiceClass = es.mityc.appfacturae.facturae.InvoiceClassType
				.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceClass().value());
			invoiceHeader.setInvoiceClass(invoiceClass);

			// 3.1.1.5 Corrective
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective() != null) {
				es.mityc.appfacturae.facturae32.CorrectiveType corrective = new es.mityc.appfacturae.facturae32.CorrectiveType();

				// 3.1.1.5.1 InvoiceNumber
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getInvoiceNumber())) {
					String invoiceNumber2 = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
						.getCorrective().getInvoiceNumber();
					corrective.setInvoiceNumber(invoiceNumber2);
				}

				// 3.1.1.5.2 InvoiceSeriesCode
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getInvoiceSeriesCode())) {
					String invoiceSeriesCode2 = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
						.getCorrective().getInvoiceSeriesCode();
					corrective.setInvoiceSeriesCode(invoiceSeriesCode2);
				}

				// 3.1.1.5.3 ReasonCode
				String reasonCode = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getReasonCode();
				corrective.setReasonCode(reasonCode);

				// 3.1.1.5.4 ReasonDescription
				es.mityc.appfacturae.facturae32.ReasonDescriptionType reasonDescription = es.mityc.appfacturae.facturae32.ReasonDescriptionType
					.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
						.getReasonDescription().value());
				corrective.setReasonDescription(reasonDescription);

				// 3.1.1.5.5 TaxPeriod
				es.mityc.appfacturae.facturae.PeriodDates taxPeriod = new es.mityc.appfacturae.facturae.PeriodDates();

				// 3.1.1.5.5.1 StartDate
				XMLGregorianCalendar startDate = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
					.getCorrective().getTaxPeriod().getStartDate();
				taxPeriod.setStartDate(startDate);

				// 3.1.1.5.5.2 EndDate
				XMLGregorianCalendar endDate = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
					.getCorrective().getTaxPeriod().getEndDate();
				taxPeriod.setEndDate(endDate);

				corrective.setTaxPeriod(taxPeriod);

				// 3.1.1.5.6 CorrectionMethod
				String correctionMethod = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getCorrectionMethod();
				corrective.setCorrectionMethod(correctionMethod);

				// 3.1.1.5.7 CorrectionMethodDescription
				es.mityc.appfacturae.facturae.CorrectionMethodDescriptionType correctionMethodDescription = es.mityc.appfacturae.facturae.CorrectionMethodDescriptionType
					.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
						.getCorrectionMethodDescription().value());
				corrective.setCorrectionMethodDescription(correctionMethodDescription);

				// 3.1.1.5.8 AdditionalReasonDescription
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getAdditionalReasonDescription())) {
					String additionalReasonDescription = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
						.getCorrective().getAdditionalReasonDescription();
					corrective.setAdditionalReasonDescription(additionalReasonDescription);
				}

				invoiceHeader.setCorrective(corrective);
			}

			invoiceType.setInvoiceHeader(invoiceHeader);

			// 3.1.2 InvoiceIssueData
			es.mityc.appfacturae.facturae32.InvoiceIssueDataType invoiceIssueData = new es.mityc.appfacturae.facturae32.InvoiceIssueDataType();

			// 3.1.2.1 IssueDate
			XMLGregorianCalendar issueDate = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
				.getIssueDate();
			invoiceIssueData.setIssueDate(issueDate);

			// 3.1.2.2 OperationDate
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getOperationDate() != null) {
				XMLGregorianCalendar operationDate = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
					.getOperationDate();
				invoiceIssueData.setOperationDate(operationDate);
			}

			// 3.1.2.3 PlaceOfIssue
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getPlaceOfIssue() != null) {
				es.mityc.appfacturae.facturae.PlaceOfIssueType placeOfIssue = new es.mityc.appfacturae.facturae.PlaceOfIssueType();

				// 3.1.2.3.1 PostCode
				String postCode7 = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getPlaceOfIssue()
					.getPostCode();
				placeOfIssue.setPostCode(postCode7);

				// 3.1.2.3.2 PlaceOfIssueDescription
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getPlaceOfIssue()
					.getPlaceOfIssueDescription())) {
					String placeOfIssueDescription = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
						.getPlaceOfIssue().getPlaceOfIssueDescription();
					placeOfIssue.setPlaceOfIssueDescription(placeOfIssueDescription);
				}

				invoiceIssueData.setPlaceOfIssue(placeOfIssue);
			}

			// 3.1.2.4 InvoicingPeriod
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getInvoicingPeriod() != null) {
				es.mityc.appfacturae.facturae.PeriodDates invoicingPeriod = new es.mityc.appfacturae.facturae.PeriodDates();

				// 3.1.2.4.1 StartDate
				XMLGregorianCalendar startDate2 = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
					.getInvoicingPeriod().getStartDate();
				invoicingPeriod.setStartDate(startDate2);

				// 3.1.2.4.2 EndDate
				XMLGregorianCalendar endDate2 = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
					.getInvoicingPeriod().getEndDate();
				invoicingPeriod.setEndDate(endDate2);

				invoiceIssueData.setInvoicingPeriod(invoicingPeriod);
			}

			// 3.1.2.5 InvoiceCurrencyCode
			es.mityc.appfacturae.facturae.CurrencyCodeType invoiceCurrencyCode2 = es.mityc.appfacturae.facturae.CurrencyCodeType
				.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getInvoiceCurrencyCode()
					.value());
			invoiceIssueData.setInvoiceCurrencyCode(invoiceCurrencyCode2);

			// 3.1.2.6 ExchangeRateDetails
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getExchangeRateDetails() != null) {
				es.mityc.appfacturae.facturae.ExchangeRateDetailsType exchangeRateDetails = new es.mityc.appfacturae.facturae.ExchangeRateDetailsType();

				// 3.1.2.6.1 ExchangeRate
				double exchangeRate = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
					.getExchangeRateDetails().getExchangeRate();
				exchangeRateDetails.setExchangeRate(exchangeRate);

				// 3.1.2.6.2 ExchangeRateDate
				XMLGregorianCalendar exchangeRateDate = facturae.getInvoices().getInvoice().get(i)
					.getInvoiceIssueData().getExchangeRateDetails().getExchangeRateDate();
				exchangeRateDetails.setExchangeRateDate(exchangeRateDate);

				invoiceIssueData.setExchangeRateDetails(exchangeRateDetails);
			}

			// 3.1.2.7 TaxCurrencyCode
			es.mityc.appfacturae.facturae.CurrencyCodeType taxCurrencyCode = es.mityc.appfacturae.facturae.CurrencyCodeType
				.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getTaxCurrencyCode()
					.value());
			invoiceIssueData.setTaxCurrencyCode(taxCurrencyCode);

			// 3.1.2.8 LanguageName
			es.mityc.appfacturae.facturae.LanguageCodeType languageName = es.mityc.appfacturae.facturae.LanguageCodeType
				.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getLanguageName().value());
			invoiceIssueData.setLanguageName(languageName);

			invoiceType.setInvoiceIssueData(invoiceIssueData);

			// 3.1.3 TaxesOutputs
			es.mityc.appfacturae.facturae32.InvoiceType.TaxesOutputs taxesOutputs = new es.mityc.appfacturae.facturae32.InvoiceType.TaxesOutputs();

			// 3.1.3.1 Tax
			es.mityc.appfacturae.facturae32.TaxOutputType tax = null;

			// for 2	
			int taxCount = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().size();
			for (int j = 0; j < taxCount; j++) {
				tax = new es.mityc.appfacturae.facturae32.TaxOutputType();

				// 3.1.3.1.1 TaxTypeCode
				String taxTypeCode = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j).getTaxTypeCode();
				tax.setTaxTypeCode(taxTypeCode);

				// 3.1.3.1.2 TaxRate
				double taxRate = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
					.getTaxRate();
				tax.setTaxRate(taxRate);

				// 3.1.3.1.3 TaxableBase
				es.mityc.appfacturae.facturae.AmountType taxableBase = new es.mityc.appfacturae.facturae.AmountType();

				// 3.1.3.1.3.1 TotalAmount
				double totalAmount4 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
					.getTaxableBase().getTotalAmount();
				taxableBase.setTotalAmount(totalAmount4);

				// 3.1.3.1.3.2 EquivalentInEuros
				if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j).getTaxableBase()
					.getEquivalentInEuros() != null) {
					double equivalentInEuros4 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax()
						.get(j).getTaxableBase().getEquivalentInEuros();
					taxableBase.setEquivalentInEuros(equivalentInEuros4);
				}

				tax.setTaxableBase(taxableBase);

				// 3.1.3.1.4 TaxAmount
				if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j).getTaxAmount() != null) {
					es.mityc.appfacturae.facturae.AmountType taxAmount = new es.mityc.appfacturae.facturae.AmountType();

					// 3.1.3.1.4.1 TotalAmount
					double totalAmount5 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
						.getTaxAmount().getTotalAmount();
					taxAmount.setTotalAmount(totalAmount5);

					// 3.1.3.1.4.2 EquivalentInEuros
					if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j).getTaxAmount()
						.getEquivalentInEuros() != null) {
						double equivalentInEuros5 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs()
							.getTax().get(j).getTaxAmount().getEquivalentInEuros();
						taxAmount.setEquivalentInEuros(equivalentInEuros5);
					}
					tax.setTaxAmount(taxAmount);
				}

				// 3.1.3.1.5 SpecialTaxableBase
				if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
					.getSpecialTaxableBase() != null) {
					es.mityc.appfacturae.facturae.AmountType specialTaxableBase = new es.mityc.appfacturae.facturae.AmountType();

					// 3.1.3.1.5.1 TotalAmount
					double totalAmount17 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
						.getSpecialTaxableBase().getTotalAmount();
					specialTaxableBase.setTotalAmount(totalAmount17);

					// 3.1.3.1.5.2 EquivalentInEuros
					if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
						.getSpecialTaxableBase().getEquivalentInEuros() != null) {
						double equivalentInEuros17 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs()
							.getTax().get(j).getSpecialTaxableBase().getEquivalentInEuros();
						specialTaxableBase.setEquivalentInEuros(equivalentInEuros17);
					}

					tax.setSpecialTaxableBase(specialTaxableBase);
				}

				// 3.1.3.1.6 SpecialTaxAmount
				if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j).getSpecialTaxAmount() != null) {
					es.mityc.appfacturae.facturae.AmountType specialTaxAmount = new es.mityc.appfacturae.facturae.AmountType();

					// 3.1.3.1.6.1 TotalAmount
					double totalAmount18 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
						.getSpecialTaxAmount().getTotalAmount();
					specialTaxAmount.setTotalAmount(totalAmount18);

					// 3.1.3.1.6.2 EquivalentInEuros
					if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
						.getSpecialTaxAmount().getEquivalentInEuros() != null) {
						double equivalentInEuros18 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs()
							.getTax().get(j).getSpecialTaxAmount().getEquivalentInEuros();
						specialTaxAmount.setEquivalentInEuros(equivalentInEuros18);
					}

					tax.setSpecialTaxAmount(specialTaxAmount);
				}

				// 3.1.3.1.7 EquivalenceSurcharge
				if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
					.getEquivalenceSurcharge() != null) {
					Double equivalenceSurcharge = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax()
						.get(j).getEquivalenceSurcharge();
					tax.setEquivalenceSurcharge(equivalenceSurcharge);
				}

				// 3.1.3.1.8 EquivalenceSurchargeAmount
				if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
					.getEquivalenceSurchargeAmount() != null) {
					es.mityc.appfacturae.facturae.AmountType equivalenceSurchargeAmount = new es.mityc.appfacturae.facturae.AmountType();

					// 3.1.3.1.8.1 TotalAmount
					double totalAmount19 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
						.getEquivalenceSurchargeAmount().getTotalAmount();
					equivalenceSurchargeAmount.setTotalAmount(totalAmount19);

					// 3.1.3.1.8.2 EquivalentInEuros
					if (facturae.getInvoices().getInvoice().get(i).getTaxesOutputs().getTax().get(j)
						.getEquivalenceSurchargeAmount().getEquivalentInEuros() != null) {
						double equivalentInEuros19 = facturae.getInvoices().getInvoice().get(i).getTaxesOutputs()
							.getTax().get(j).getEquivalenceSurchargeAmount().getEquivalentInEuros();
						equivalenceSurchargeAmount.setEquivalentInEuros(equivalentInEuros19);
					}
					tax.setEquivalenceSurchargeAmount(equivalenceSurchargeAmount);
				}
				taxesOutputs.getTax().add(tax);
			} // for 2

			invoiceType.setTaxesOutputs(taxesOutputs);

			// 3.1.4 TaxesWithheld
			if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld() != null
				&& facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax() != null
				&& facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().size() > 0) {
				es.mityc.appfacturae.facturae.TaxesType taxesWithheld = new es.mityc.appfacturae.facturae.TaxesType();

				// 3.1.4.1 Tax
				es.mityc.appfacturae.facturae.TaxType tax2 = null;

				// for 3
				int taxCount2 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().size();
				for (int j = 0; j < taxCount2; j++) {

					tax2 = new es.mityc.appfacturae.facturae.TaxType();

					// 3.1.4.1.1 TaxTypeCode
					String taxTypeCode2 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j).getTaxTypeCode();
					tax2.setTaxTypeCode(taxTypeCode2);

					// 3.1.4.1.2 TaxRate
					double taxRate2 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j).getTaxRate();
					tax2.setTaxRate(taxRate2);

					// 3.1.4.1.3 TaxableBase
					es.mityc.appfacturae.facturae.AmountType taxableBase2 = new es.mityc.appfacturae.facturae.AmountType();

					// 3.1.4.1.3.1 TotalAmount
					double totalAmount6 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j)
						.getTaxableBase().getTotalAmount();
					taxableBase2.setTotalAmount(totalAmount6);

					// 3.1.4.1.3.2 EquivalentInEuros
					if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j).getTaxableBase()
						.getEquivalentInEuros() != null) {
						double equivalentInEuros6 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld()
							.getTax().get(j).getTaxableBase().getEquivalentInEuros();
						taxableBase2.setEquivalentInEuros(equivalentInEuros6);
					}
					tax2.setTaxableBase(taxableBase2);

					// 3.1.4.1.4 TaxAmount
					if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j).getTaxAmount() != null) {
						es.mityc.appfacturae.facturae.AmountType taxAmount2 = new es.mityc.appfacturae.facturae.AmountType();

						// 3.1.4.1.4.1 TotalAmount
						double totalAmount7 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax()
							.get(j).getTaxAmount().getTotalAmount();
						taxAmount2.setTotalAmount(totalAmount7);

						// 3.1.4.1.4.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j)
							.getTaxAmount().getEquivalentInEuros() != null) {
							double equivalentInEuros7 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld()
								.getTax().get(j).getTaxAmount().getEquivalentInEuros();
							taxAmount2.setEquivalentInEuros(equivalentInEuros7);
						}

						tax2.setTaxAmount(taxAmount2);
					}

					taxesWithheld.getTax().add(tax2);

				} //for 3

				invoiceType.setTaxesWithheld(taxesWithheld);
			}

			// 3.1.5 InvoiceTotals
			es.mityc.appfacturae.facturae32.InvoiceTotalsType invoiceTotals = new es.mityc.appfacturae.facturae32.InvoiceTotalsType();

			// 3.1.5.1 TotalGrossAmount
			double totalGrossAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalGrossAmount();
			invoiceTotals.setTotalGrossAmount(totalGrossAmount);

			// 3.1.5.2 GeneralDiscounts
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts().getDiscount() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts().getDiscount()
					.size() > 0) {
				es.mityc.appfacturae.facturae.DiscountsAndRebatesType generalDiscounts = new es.mityc.appfacturae.facturae.DiscountsAndRebatesType();

				// 3.1.5.2.1 Discount
				es.mityc.appfacturae.facturae.DiscountType discount = null;

				// for 4
				int discountCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts()
					.getDiscount().size();
				for (int j = 0; j < discountCount; j++) {

					discount = new es.mityc.appfacturae.facturae.DiscountType();

					// 3.1.5.2.1.1 DiscountReason
					String discountReason = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralDiscounts().getDiscount().get(j).getDiscountReason();
					discount.setDiscountReason(discountReason);

					// 3.1.5.2.1.2 DiscountRate
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts()
						.getDiscount().get(j).getDiscountRate() != null) {
						Double discountRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getGeneralDiscounts().getDiscount().get(j).getDiscountRate();
						discount.setDiscountRate(discountRate);
					}

					// 3.1.5.2.1.3 DiscountAmount
					double discountAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralDiscounts().getDiscount().get(j).getDiscountAmount();
					discount.setDiscountAmount(discountAmount);

					generalDiscounts.getDiscount().add(discount);

				} // for 4

				invoiceTotals.setGeneralDiscounts(generalDiscounts);
			}

			// 3.1.5.3 GeneralSurcharges
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges().getCharge() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges().getCharge()
					.size() > 0) {
				es.mityc.appfacturae.facturae.ChargesType generalSurcharges = new es.mityc.appfacturae.facturae.ChargesType();

				// 3.1.5.3.1 Charge
				es.mityc.appfacturae.facturae.ChargeType charge = null;

				// for 5
				int chargeCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges()
					.getCharge().size();
				for (int j = 0; j < chargeCount; j++) {

					charge = new es.mityc.appfacturae.facturae.ChargeType();

					// 3.1.5.3.1.1 ChargeReason
					String chargeReason = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralSurcharges().getCharge().get(j).getChargeReason();
					charge.setChargeReason(chargeReason);

					// 3.1.5.3.1.2 ChargeRate
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges()
						.getCharge().get(j).getChargeRate() != null) {
						Double chargeRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getGeneralSurcharges().getCharge().get(j).getChargeRate();
						charge.setChargeRate(chargeRate);
					}

					// 3.1.5.3.1.3 ChargeAmount
					double chargeAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralSurcharges().getCharge().get(j).getChargeAmount();
					charge.setChargeAmount(chargeAmount);

					generalSurcharges.getCharge().add(charge);

				} // for 5

				invoiceTotals.setGeneralSurcharges(generalSurcharges);
			}

			// 3.1.5.4 TotalGeneralDiscounts
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalGeneralDiscounts() != null) {
				Double totalGeneralDiscounts = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalGeneralDiscounts();
				invoiceTotals.setTotalGeneralDiscounts(totalGeneralDiscounts);
			}

			// 3.1.5.5 TotalGeneralSurcharges
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalGeneralSurcharges() != null) {
				Double totalGeneralSurcharges = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalGeneralSurcharges();
				invoiceTotals.setTotalGeneralSurcharges(totalGeneralSurcharges);
			}

			// 3.1.5.6 TotalGrossAmountBeforeTaxes
			double totalGrossAmountBeforeTaxes = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalGrossAmountBeforeTaxes();
			invoiceTotals.setTotalGrossAmountBeforeTaxes(totalGrossAmountBeforeTaxes);

			// 3.1.5.7 TotalTaxOutputs
			double totalTaxOutputs = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalTaxOutputs();
			invoiceTotals.setTotalTaxOutputs(totalTaxOutputs);

			// 3.1.5.8 TotalTaxesWithheld
			double totalTaxesWithheld = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalTaxesWithheld();
			invoiceTotals.setTotalTaxesWithheld(totalTaxesWithheld);

			// 3.1.5.9 InvoiceTotal
			double invoiceTotal = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getInvoiceTotal();
			invoiceTotals.setInvoiceTotal(invoiceTotal);

			// 3.1.5.10 Subsidies
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies().getSubsidy() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies().getSubsidy().size() > 0) {
				es.mityc.appfacturae.facturae.SubsidiesType subsidies = new es.mityc.appfacturae.facturae.SubsidiesType();

				// 3.1.5.10.1 Subsidy
				es.mityc.appfacturae.facturae.SubsidyType subsidy = null;

				// for 6
				int subsidyCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies()
					.getSubsidy().size();
				for (int j = 0; j < subsidyCount; j++) {

					subsidy = new es.mityc.appfacturae.facturae.SubsidyType();

					// 3.1.5.10.1.1 SubsidyDescription
					String subsidyDescription = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getSubsidies().getSubsidy().get(j).getSubsidyDescription();
					subsidy.setSubsidyDescription(subsidyDescription);

					// 3.1.5.10.1.2 SubsidyRate
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies().getSubsidy()
						.get(j).getSubsidyRate() != null) {
						Double subsidyRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getSubsidies().getSubsidy().get(j).getSubsidyRate();
						subsidy.setSubsidyRate(subsidyRate);
					}

					// 3.1.5.10.1.3 SubsidyAmount
					double subsidyAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies()
						.getSubsidy().get(j).getSubsidyAmount();
					subsidy.setSubsidyAmount(subsidyAmount);

					subsidies.getSubsidy().add(subsidy);

				} // for 6

				invoiceTotals.setSubsidies(subsidies);
			}

			// 3.1.5.11 PaymentsOnAccount
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getPaymentsOnAccount() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getPaymentsOnAccount()
					.getPaymentOnAccount() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getPaymentsOnAccount()
					.getPaymentOnAccount().size() > 0) {
				es.mityc.appfacturae.facturae.PaymentsOnAccountType paymentsOnAccount = new es.mityc.appfacturae.facturae.PaymentsOnAccountType();

				// 3.1.5.11.1 PaymentOnAccount
				es.mityc.appfacturae.facturae.PaymentOnAccountType paymentOnAccount = null;

				// for 7
				int paymOnAccountCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getPaymentsOnAccount().getPaymentOnAccount().size();
				for (int j = 0; j < paymOnAccountCount; j++) {

					paymentOnAccount = new es.mityc.appfacturae.facturae.PaymentOnAccountType();

					// 3.1.5.11.1.1 PaymentOnAccountDate
					XMLGregorianCalendar paymentOnAccountDate = facturae.getInvoices().getInvoice().get(i)
						.getInvoiceTotals().getPaymentsOnAccount().getPaymentOnAccount().get(j)
						.getPaymentOnAccountDate();
					paymentOnAccount.setPaymentOnAccountDate(paymentOnAccountDate);

					// 3.1.5.11.1.2 PaymentOnAccountAmount
					double paymentOnAccountAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getPaymentsOnAccount().getPaymentOnAccount().get(j).getPaymentOnAccountAmount();
					paymentOnAccount.setPaymentOnAccountAmount(paymentOnAccountAmount);

					paymentsOnAccount.getPaymentOnAccount().add(paymentOnAccount);

				} // for 7

				invoiceTotals.setPaymentsOnAccount(paymentsOnAccount);
			}

			// 3.1.5.12 ReimbursableExpenses
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses()
					.getReimbursableExpenses() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses()
					.getReimbursableExpenses().size() > 0) {
				es.mityc.appfacturae.facturae.ReimbursableExpenses reimbursableExpenses = new es.mityc.appfacturae.facturae.ReimbursableExpenses();

				// 3.1.5.12.1 ReimbursableExpenses
				es.mityc.appfacturae.facturae.ReimbursableExpensesType reimbursableExpenses2 = null;

				// for 7 bis
				int reimExpCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getReimbursableExpenses().getReimbursableExpenses().size();
				for (int j = 0; j < reimExpCount; j++) {

					reimbursableExpenses2 = new es.mityc.appfacturae.facturae.ReimbursableExpensesType();

					// 3.1.5.12.1.1 ReimbursableExpensesSellerParty
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses()
						.getReimbursableExpenses().get(j).getReimbursableExpensesSellerParty() != null) {
						es.mityc.appfacturae.facturae.TaxIdentificationType reimbursableExpensesSellerParty = new es.mityc.appfacturae.facturae.TaxIdentificationType();

						// 3.1.5.12.1.1.1 PersonTypeCode
						es.mityc.appfacturae.facturae.PersonTypeCodeType personTypeCode9 = es.mityc.appfacturae.facturae.PersonTypeCodeType
							.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
								.getReimbursableExpenses().getReimbursableExpenses().get(j)
								.getReimbursableExpensesSellerParty().getPersonTypeCode().value());
						reimbursableExpensesSellerParty.setPersonTypeCode(personTypeCode9);

						// 3.1.5.12.1.1.2 ResidenceTypeCode
						es.mityc.appfacturae.facturae.ResidenceTypeCodeType residenceTypeCode9 = es.mityc.appfacturae.facturae.ResidenceTypeCodeType
							.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
								.getReimbursableExpenses().getReimbursableExpenses().get(j)
								.getReimbursableExpensesSellerParty().getResidenceTypeCode().value());
						reimbursableExpensesSellerParty.setResidenceTypeCode(residenceTypeCode9);

						// 3.1.5.12.1.1.3 TaxIdentificationNumber
						String taxIdentificationNumber9 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getReimbursableExpenses().getReimbursableExpenses().get(j)
							.getReimbursableExpensesSellerParty().getTaxIdentificationNumber();
						reimbursableExpensesSellerParty.setTaxIdentificationNumber(taxIdentificationNumber9);

						reimbursableExpenses2.setReimbursableExpensesSellerParty(reimbursableExpensesSellerParty);
					}

					// 3.1.5.12.1.2 ReimbursableExpensesBuyerParty
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses()
						.getReimbursableExpenses().get(j).getReimbursableExpensesBuyerParty() != null) {
						es.mityc.appfacturae.facturae.TaxIdentificationType reimbursableExpensesBuyerParty = new es.mityc.appfacturae.facturae.TaxIdentificationType();

						// 3.1.5.12.1.2.1 PersonTypeCode
						es.mityc.appfacturae.facturae.PersonTypeCodeType personTypeCode10 = es.mityc.appfacturae.facturae.PersonTypeCodeType
							.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
								.getReimbursableExpenses().getReimbursableExpenses().get(j)
								.getReimbursableExpensesBuyerParty().getPersonTypeCode().value());
						reimbursableExpensesBuyerParty.setPersonTypeCode(personTypeCode10);

						// 3.1.5.12.1.2.2 ResidenceTypeCode
						es.mityc.appfacturae.facturae.ResidenceTypeCodeType residenceTypeCode10 = es.mityc.appfacturae.facturae.ResidenceTypeCodeType
							.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
								.getReimbursableExpenses().getReimbursableExpenses().get(j)
								.getReimbursableExpensesBuyerParty().getResidenceTypeCode().value());
						reimbursableExpensesBuyerParty.setResidenceTypeCode(residenceTypeCode10);

						// 3.1.5.12.1.2.3 TaxIdentificationNumber
						String taxIdentificationNumber10 = facturae.getInvoices().getInvoice().get(i)
							.getInvoiceTotals().getReimbursableExpenses().getReimbursableExpenses().get(j)
							.getReimbursableExpensesBuyerParty().getTaxIdentificationNumber();
						reimbursableExpensesBuyerParty.setTaxIdentificationNumber(taxIdentificationNumber10);

						reimbursableExpenses2.setReimbursableExpensesBuyerParty(reimbursableExpensesBuyerParty);
					}

					// 3.1.5.12.1.3 IssueDate
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses()
						.getReimbursableExpenses().get(j).getIssueDate() != null) {
						XMLGregorianCalendar issueDate3 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getReimbursableExpenses().getReimbursableExpenses().get(j).getIssueDate();
						reimbursableExpenses2.setIssueDate(issueDate3);
					}

					// 3.1.5.12.1.4 InvoiceNumber
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses()
						.getReimbursableExpenses().get(j).getInvoiceNumber() != null) {
						String invoiceNumber5 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getReimbursableExpenses().getReimbursableExpenses().get(j).getInvoiceNumber();
						reimbursableExpenses2.setInvoiceNumber(invoiceNumber5);
					}

					// 3.1.5.12.1.5 InvoiceSeriesCode
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getReimbursableExpenses()
						.getReimbursableExpenses().get(j).getInvoiceSeriesCode() != null) {
						String invoiceSeriesCode5 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getReimbursableExpenses().getReimbursableExpenses().get(j).getInvoiceSeriesCode();
						reimbursableExpenses2.setInvoiceSeriesCode(invoiceSeriesCode5);
					}

					// 3.1.5.12.1.6 ReimbursableExpensesAmount
					double reimbursableExpensesAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getReimbursableExpenses().getReimbursableExpenses().get(j).getReimbursableExpensesAmount();
					reimbursableExpenses2.setReimbursableExpensesAmount(reimbursableExpensesAmount);

					reimbursableExpenses.getReimbursableExpenses().add(reimbursableExpenses2);

				} // for 7 bis

				invoiceTotals.setReimbursableExpenses(reimbursableExpenses);
			}

			// 3.1.5.13 TotalFinancialExpenses
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalFinancialExpenses() != null) {
				Double totalFinancialExpenses = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalFinancialExpenses();
				invoiceTotals.setTotalFinancialExpenses(totalFinancialExpenses);
			}

			// 3.1.5.14 TotalOutstandingAmount
			double totalOutstandingAmount4 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalOutstandingAmount();
			invoiceTotals.setTotalOutstandingAmount(totalOutstandingAmount4);

			// 3.1.5.15 TotalPaymentsOnAccount
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalPaymentsOnAccount() != null) {
				Double totalPaymentsOnAccount2 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalPaymentsOnAccount();
				invoiceTotals.setTotalPaymentsOnAccount(totalPaymentsOnAccount2);
			}

			// 3.1.5.16 AmountsWithheld
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getAmountsWithheld() != null) {
				es.mityc.appfacturae.facturae.AmountsWithheldType amountsWithheld = new es.mityc.appfacturae.facturae.AmountsWithheldType();

				// 3.1.5.16.1 WithholdingReason
				String withholdingReason = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getAmountsWithheld().getWithholdingReason();
				amountsWithheld.setWithholdingReason(withholdingReason);

				// 3.1.5.16.2 WithholdingRate
				if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getAmountsWithheld()
					.getWithholdingRate() != null) {
					Double withholdingRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getAmountsWithheld().getWithholdingRate();
					amountsWithheld.setWithholdingRate(withholdingRate);
				}

				// 3.1.5.16.3 WithholdingAmount
				double withholdingAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getAmountsWithheld().getWithholdingAmount();
				amountsWithheld.setWithholdingAmount(withholdingAmount);

				invoiceTotals.setAmountsWithheld(amountsWithheld);
			}

			// 3.1.5.17 TotalExecutableAmount
			double totalExecutableAmount4 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalExecutableAmount();
			invoiceTotals.setTotalExecutableAmount(totalExecutableAmount4);

			// 3.1.5.18 TotalReimbursableExpenses
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalReimbursableExpenses() != null) {
				Double totalReimbursableExpenses = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalReimbursableExpenses();
				invoiceTotals.setTotalReimbursableExpenses(totalReimbursableExpenses);
			}

			invoiceType.setInvoiceTotals(invoiceTotals);

			// 3.1.6 Items
			es.mityc.appfacturae.facturae.ItemsType items = new es.mityc.appfacturae.facturae.ItemsType();

			// 3.1.6.1 InvoiceLine
			es.mityc.appfacturae.facturae32.InvoiceLineType invoiceLine = null;

			// for 8
			int invoiceLineCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().size();
			for (int j = 0; j < invoiceLineCount; j++) {

				invoiceLine = new es.mityc.appfacturae.facturae32.InvoiceLineType();

				// 3.1.6.1.1 IssuerContractReference
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getIssuerContractReference())) {
					String issuerContractReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getIssuerContractReference();
					invoiceLine.setIssuerContractReference(issuerContractReference);
				}

				// 3.1.6.1.2 IssuerContractDate
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getIssuerContractDate())) {
					XMLGregorianCalendar issuerContractDate = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getIssuerContractDate();
					invoiceLine.setIssuerContractDate(issuerContractDate);
				}

				// 3.1.6.1.3 IssuerTransactionReference
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getIssuerTransactionReference())) {
					String issuerTransactionReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getIssuerTransactionReference();
					invoiceLine.setIssuerTransactionReference(issuerTransactionReference);
				}

				// 3.1.6.1.4 IssuerTransactionDate
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getIssuerTransactionDate())) {
					XMLGregorianCalendar issuerTransactionDate = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getIssuerTransactionDate();
					invoiceLine.setIssuerTransactionDate(issuerTransactionDate);
				}

				// 3.1.6.1.5 ReceiverContractReference
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getReceiverContractReference())) {
					String receiverContractReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getReceiverContractReference();
					invoiceLine.setReceiverContractReference(receiverContractReference);
				}

				// 3.1.6.1.6 ReceiverContractDate
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getReceiverContractDate())) {
					XMLGregorianCalendar receiverContractDate = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getReceiverContractDate();
					invoiceLine.setReceiverContractDate(receiverContractDate);
				}

				// 3.1.6.1.7 ReceiverTransactionReference
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getReceiverTransactionReference())) {
					String receiverTransactionReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getReceiverTransactionReference();
					invoiceLine.setReceiverTransactionReference(receiverTransactionReference);
				}

				// 3.1.6.1.8 ReceiverTransactionDate
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getReceiverTransactionDate())) {
					XMLGregorianCalendar receiverTransactionDate = facturae.getInvoices().getInvoice().get(i)
						.getItems().getInvoiceLine().get(j).getReceiverTransactionDate();
					invoiceLine.setReceiverTransactionDate(receiverTransactionDate);
				}

				// 3.1.6.1.9 FileReference
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getFileReference())) {
					String fileReference = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
						.get(j).getFileReference();
					invoiceLine.setFileReference(fileReference);
				}

				// 3.1.6.1.10 FileDate
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getFileDate())) {
					XMLGregorianCalendar fileDate = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getFileDate();
					invoiceLine.setFileDate(fileDate);
				}

				// 3.1.6.1.11 SequenceNumber
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getSequenceNumber())) {
					Double sequenceNumber = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
						.get(j).getSequenceNumber();
					invoiceLine.setSequenceNumber(sequenceNumber);
				}

				// 3.1.6.1.12 DeliveryNotesReferences
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getDeliveryNotesReferences() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDeliveryNotesReferences().getDeliveryNote() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDeliveryNotesReferences().getDeliveryNote().size() > 0) {
					es.mityc.appfacturae.facturae.DeliveryNotesReferencesType deliveryNotesReferences = new es.mityc.appfacturae.facturae.DeliveryNotesReferencesType();

					// 3.1.6.1.12.1 DeliveryNote
					es.mityc.appfacturae.facturae32.DeliveryNoteType deliveryNote = null;

					// for 9
					int delivNotesCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDeliveryNotesReferences().getDeliveryNote().size();
					for (int k = 0; k < delivNotesCount; k++) {

						deliveryNote = new es.mityc.appfacturae.facturae32.DeliveryNoteType();

						// 3.1.6.1.12.1.1 DeliveryNoteNumber
						String deliveryNoteNumber = facturae.getInvoices().getInvoice().get(i).getItems()
							.getInvoiceLine().get(j).getDeliveryNotesReferences().getDeliveryNote().get(k)
							.getDeliveryNoteNumber();
						deliveryNote.setDeliveryNoteNumber(deliveryNoteNumber);

						// 3.1.6.1.12.1.2 DeliveryNoteDate
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getDeliveryNotesReferences().getDeliveryNote().get(k).getDeliveryNoteDate())) {
							XMLGregorianCalendar deliveryNoteDate = facturae.getInvoices().getInvoice().get(i)
								.getItems().getInvoiceLine().get(j).getDeliveryNotesReferences().getDeliveryNote()
								.get(k).getDeliveryNoteDate();
							deliveryNote.setDeliveryNoteDate(deliveryNoteDate);
						}

						deliveryNotesReferences.getDeliveryNote().add(deliveryNote);

					} // for 9

					invoiceLine.setDeliveryNotesReferences(deliveryNotesReferences);
				}

				// 3.1.6.1.13 ItemDescription
				String itemDescription = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getItemDescription();
				invoiceLine.setItemDescription(itemDescription);

				// 3.1.6.1.14 Quantity
				double quantity = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getQuantity();
				invoiceLine.setQuantity(quantity);

				// 3.1.6.1.15 UnitOfMeasure
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getUnitOfMeasure())) {
					String unitOfMeasure = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
						.get(j).getUnitOfMeasure();
					invoiceLine.setUnitOfMeasure(unitOfMeasure);
				}

				// 3.1.6.1.16 UnitPriceWithoutTax
				double unitPriceWithoutTax = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
					.get(j).getUnitPriceWithoutTax();
				invoiceLine.setUnitPriceWithoutTax(unitPriceWithoutTax);

				// 3.1.6.1.17 TotalCost
				double totalCost = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getTotalCost();
				invoiceLine.setTotalCost(totalCost);

				// 3.1.6.1.18 DiscountsAndRebates
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getDiscountsAndRebates() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDiscountsAndRebates().getDiscount() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDiscountsAndRebates().getDiscount().size() > 0) {
					es.mityc.appfacturae.facturae.DiscountsAndRebatesType discountsAndRebates = new es.mityc.appfacturae.facturae.DiscountsAndRebatesType();

					// 3.1.6.1.18.1 Discount
					es.mityc.appfacturae.facturae.DiscountType discount2 = null;

					// for 10
					int discountCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDiscountsAndRebates().getDiscount().size();
					for (int k = 0; k < discountCount; k++) {

						discount2 = new es.mityc.appfacturae.facturae.DiscountType();

						// 3.1.6.1.18.1.1 DiscountReason
						String discountReason2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getDiscountsAndRebates().getDiscount().get(k).getDiscountReason();
						discount2.setDiscountReason(discountReason2);

						// 3.1.6.1.18.1.2 DiscountRate
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getDiscountsAndRebates().getDiscount().get(k).getDiscountRate() != null) {
							Double discountRate2 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getDiscountsAndRebates().getDiscount().get(k)
								.getDiscountRate();
							discount2.setDiscountRate(discountRate2);
						}

						// 3.1.6.1.18.1.3 DiscountAmount
						double discountAmount2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getDiscountsAndRebates().getDiscount().get(k).getDiscountAmount();
						discount2.setDiscountAmount(discountAmount2);

						discountsAndRebates.getDiscount().add(discount2);

					} // for 10

					invoiceLine.setDiscountsAndRebates(discountsAndRebates);
				}

				// 3.1.6.1.19 Charges
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges()
						.getCharge() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges()
						.getCharge().size() > 0) {
					es.mityc.appfacturae.facturae.ChargesType charges = new es.mityc.appfacturae.facturae.ChargesType();

					// 3.1.6.1.19.1 Charge
					es.mityc.appfacturae.facturae.ChargeType charge2 = null;

					//for 11
					int chargeCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getCharges().getCharge().size();
					for (int k = 0; k < chargeCount; k++) {

						charge2 = new es.mityc.appfacturae.facturae.ChargeType();

						// 3.1.6.1.19.1.1 ChargeReason
						String chargeReason2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getCharges().getCharge().get(k).getChargeReason();
						charge2.setChargeReason(chargeReason2);

						// 3.1.6.1.19.1.2 ChargeRate
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges()
							.getCharge().get(k).getChargeRate() != null) {
							Double chargeRate2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
								.get(j).getCharges().getCharge().get(k).getChargeRate();
							charge2.setChargeRate(chargeRate2);
						}
						// 3.1.6.1.19.1.3 ChargeAmount
						double chargeAmount2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getCharges().getCharge().get(k).getChargeAmount();
						charge2.setChargeAmount(chargeAmount2);

						charges.getCharge().add(charge2);

					} //for 11

					invoiceLine.setCharges(charges);
				}

				// 3.1.6.1.20 GrossAmount
				double grossAmount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getGrossAmount();
				invoiceLine.setGrossAmount(grossAmount);

				// 3.1.6.1.21 TaxesWithheld
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesWithheld() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesWithheld()
						.getTax() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesWithheld()
						.getTax().size() > 0) {
					es.mityc.appfacturae.facturae.TaxesType taxesWithheld2 = new es.mityc.appfacturae.facturae.TaxesType();

					// 3.1.6.1.21.1 Tax
					es.mityc.appfacturae.facturae.TaxType tax3 = null;

					// for 12
					int taxCount2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getTaxesWithheld().getTax().size();
					for (int k = 0; k < taxCount2; k++) {

						tax3 = new es.mityc.appfacturae.facturae.TaxType();

						// 3.1.6.1.21.1.1 TaxTypeCode
						String taxTypeCode3 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesWithheld().getTax().get(k).getTaxTypeCode();
						tax3.setTaxTypeCode(taxTypeCode3);

						// 3.1.6.1.21.1.2 TaxRate
						double taxRate3 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesWithheld().getTax().get(k).getTaxRate();
						tax3.setTaxRate(taxRate3);

						// 3.1.6.1.21.1.3 TaxableBase
						es.mityc.appfacturae.facturae.AmountType taxableBase3 = new es.mityc.appfacturae.facturae.AmountType();

						// 3.1.6.1.21.1.3.1 TotalAmount
						double totalAmount8 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesWithheld().getTax().get(k).getTaxableBase().getTotalAmount();
						taxableBase3.setTotalAmount(totalAmount8);

						// 3.1.6.1.21.1.3.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesWithheld().getTax().get(k).getTaxableBase().getEquivalentInEuros() != null) {
							Double equivalentInEuros8 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesWithheld().getTax().get(k).getTaxableBase()
								.getEquivalentInEuros();
							taxableBase3.setEquivalentInEuros(equivalentInEuros8);
						}

						tax3.setTaxableBase(taxableBase3);

						// 3.1.6.1.21.1.4 TaxAmount
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesWithheld().getTax().get(k).getTaxAmount() != null) {
							es.mityc.appfacturae.facturae.AmountType taxAmount3 = new es.mityc.appfacturae.facturae.AmountType();

							// 3.1.6.1.21.1.4.1 TotalAmount
							double totalAmount9 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesWithheld().getTax().get(k).getTaxAmount()
								.getTotalAmount();
							taxAmount3.setTotalAmount(totalAmount9);

							// 3.1.6.1.21.1.4.2 EquivalentInEuros
							if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
								.getTaxesWithheld().getTax().get(k).getTaxAmount().getEquivalentInEuros() != null) {
								Double equivalentInEuros9 = facturae.getInvoices().getInvoice().get(i).getItems()
									.getInvoiceLine().get(j).getTaxesWithheld().getTax().get(k).getTaxAmount()
									.getEquivalentInEuros();
								taxAmount3.setEquivalentInEuros(equivalentInEuros9);
							}

							tax3.setTaxAmount(taxAmount3);
						}

						taxesWithheld2.getTax().add(tax3);

					} // for 12

					invoiceLine.setTaxesWithheld(taxesWithheld2);
				}

				// 3.1.6.1.22 TaxesOutputs
				es.mityc.appfacturae.facturae32.InvoiceLineType.TaxesOutputs taxesOutputs2 = new es.mityc.appfacturae.facturae32.InvoiceLineType.TaxesOutputs();

				// 3.1.6.1.22.1 Tax
				es.mityc.appfacturae.facturae32.InvoiceLineType.TaxesOutputs.Tax tax4 = null;

				// for 13
				int taxCount3 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getTaxesOutputs().getTax().size();
				for (int k = 0; k < taxCount3; k++) {

					tax4 = new es.mityc.appfacturae.facturae32.InvoiceLineType.TaxesOutputs.Tax();

					// 3.1.6.1.22.1.1 TaxTypeCode
					String taxTypeCode4 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getTaxesOutputs().getTax().get(k).getTaxTypeCode();
					tax4.setTaxTypeCode(taxTypeCode4);

					// 3.1.6.1.22.1.2 TaxRate
					double taxRate4 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getTaxesOutputs().getTax().get(k).getTaxRate();
					tax4.setTaxRate(taxRate4);

					// 3.1.6.1.22.1.3 TaxableBase
					es.mityc.appfacturae.facturae.AmountType taxableBase4 = new es.mityc.appfacturae.facturae.AmountType();

					// 3.1.6.1.22.1.3.1 TotalAmount
					double totalAmount10 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
						.get(j).getTaxesOutputs().getTax().get(k).getTaxableBase().getTotalAmount();
					taxableBase4.setTotalAmount(totalAmount10);

					// 3.1.6.1.22.1.3.2 EquivalentInEuros
					if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesOutputs()
						.getTax().get(k).getTaxableBase().getEquivalentInEuros() != null) {
						Double equivalentInEuros10 = facturae.getInvoices().getInvoice().get(i).getItems()
							.getInvoiceLine().get(j).getTaxesOutputs().getTax().get(k).getTaxableBase()
							.getEquivalentInEuros();
						taxableBase4.setEquivalentInEuros(equivalentInEuros10);
					}

					tax4.setTaxableBase(taxableBase4);

					// 3.1.6.1.22.1.4 TaxAmount
					if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesOutputs()
						.getTax().get(k).getTaxAmount() != null) {
						es.mityc.appfacturae.facturae.AmountType taxAmount = new es.mityc.appfacturae.facturae.AmountType();

						// 3.1.6.1.22.1.4.1 TotalAmount
						double totalAmount5 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesOutputs().getTax().get(k).getTaxAmount().getTotalAmount();
						taxAmount.setTotalAmount(totalAmount5);

						// 3.1.6.1.22.1.4.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesOutputs().getTax().get(k).getTaxAmount().getEquivalentInEuros() != null) {
							Double equivalentInEuros5 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesOutputs().getTax().get(k).getTaxAmount()
								.getEquivalentInEuros();
							taxAmount.setEquivalentInEuros(equivalentInEuros5);
						}

						tax4.setTaxAmount(taxAmount);
					}

					// 3.1.6.1.22.1.5 SpecialTaxableBase
					if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesOutputs()
						.getTax().get(k).getSpecialTaxableBase() != null) {
						es.mityc.appfacturae.facturae.AmountType specialTaxableBase = new es.mityc.appfacturae.facturae.AmountType();

						// 3.1.6.1.22.1.5.1 TotalAmount
						double totalAmount17 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesOutputs().getTax().get(k).getSpecialTaxableBase().getTotalAmount();
						specialTaxableBase.setTotalAmount(totalAmount17);

						// 3.1.6.1.22.1.5.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesOutputs().getTax().get(k).getSpecialTaxableBase().getEquivalentInEuros() != null) {
							Double equivalentInEuros17 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesOutputs().getTax().get(k).getSpecialTaxableBase()
								.getEquivalentInEuros();
							specialTaxableBase.setEquivalentInEuros(equivalentInEuros17);
						}

						tax4.setSpecialTaxableBase(specialTaxableBase);
					}

					// 3.1.6.1.22.1.6 SpecialTaxAmount
					if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesOutputs()
						.getTax().get(k).getSpecialTaxAmount() != null) {
						es.mityc.appfacturae.facturae.AmountType specialTaxAmount = new es.mityc.appfacturae.facturae.AmountType();

						// 3.1.6.1.22.1.6.1 TotalAmount
						double totalAmount18 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesOutputs().getTax().get(k).getSpecialTaxAmount().getTotalAmount();
						specialTaxAmount.setTotalAmount(totalAmount18);

						// 3.1.6.1.22.1.6.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesOutputs().getTax().get(k).getSpecialTaxAmount().getEquivalentInEuros() != null) {
							Double equivalentInEuros18 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesOutputs().getTax().get(k).getSpecialTaxAmount()
								.getEquivalentInEuros();
							specialTaxAmount.setEquivalentInEuros(equivalentInEuros18);
						}

						tax4.setSpecialTaxAmount(specialTaxAmount);
					}

					// 3.1.6.1.22.1.7 EquivalenceSurcharge
					if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesOutputs()
						.getTax().get(k).getEquivalenceSurcharge() != null) {
						Double equivalenceSurcharge = facturae.getInvoices().getInvoice().get(i).getItems()
							.getInvoiceLine().get(j).getTaxesOutputs().getTax().get(k).getEquivalenceSurcharge();
						tax4.setEquivalenceSurcharge(equivalenceSurcharge);
					}

					// 3.1.6.1.22.1.8 EquivalenceSurchargeAmount
					if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesOutputs()
						.getTax().get(k).getEquivalenceSurchargeAmount() != null) {
						es.mityc.appfacturae.facturae.AmountType equivalenceSurchargeAmount = new es.mityc.appfacturae.facturae.AmountType();

						// 3.1.6.1.22.1.8.1 TotalAmount
						double totalAmount19 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesOutputs().getTax().get(k).getEquivalenceSurchargeAmount().getTotalAmount();
						equivalenceSurchargeAmount.setTotalAmount(totalAmount19);

						// 3.1.6.1.22.1.8.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesOutputs().getTax().get(k).getEquivalenceSurchargeAmount().getEquivalentInEuros() != null) {
							Double equivalentInEuros19 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesOutputs().getTax().get(k)
								.getEquivalenceSurchargeAmount().getEquivalentInEuros();
							equivalenceSurchargeAmount.setEquivalentInEuros(equivalentInEuros19);
						}

						tax4.setEquivalenceSurchargeAmount(equivalenceSurchargeAmount);
					}

					taxesOutputs2.getTax().add(tax4);

				} // for 13

				invoiceLine.setTaxesOutputs(taxesOutputs2);

				// 3.1.6.1.23 LineItemPeriod
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getLineItemPeriod() != null) {
					es.mityc.appfacturae.facturae.PeriodDates lineItemPeriod = new es.mityc.appfacturae.facturae.PeriodDates();

					// 3.1.6.1.23.1 StartDate
					XMLGregorianCalendar startDate3 = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getLineItemPeriod().getStartDate();
					lineItemPeriod.setStartDate(startDate3);

					// 3.1.6.1.23.2 EndDate
					XMLGregorianCalendar endDate3 = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getLineItemPeriod().getEndDate();
					lineItemPeriod.setEndDate(endDate3);

					invoiceLine.setLineItemPeriod(lineItemPeriod);
				}

				// 3.1.6.1.24 TransactionDate
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTransactionDate() != null) {
					XMLGregorianCalendar transactionDate = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getTransactionDate();
					invoiceLine.setTransactionDate(transactionDate);
				}

				// 3.1.6.1.25 AdditionalLineItemInformation
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getAdditionalLineItemInformation())) {
					String additionalLineItemInformation = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getAdditionalLineItemInformation();
					invoiceLine.setAdditionalLineItemInformation(additionalLineItemInformation);
				}

				// 3.1.6.1.26 SpecialTaxableEvent
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getSpecialTaxableEvent() != null) {
					es.mityc.appfacturae.facturae32.SpecialTaxableEventType specialTaxableEvent = new es.mityc.appfacturae.facturae32.SpecialTaxableEventType();

					// 3.1.6.1.26.1 SpecialTaxableEventCode
					String specialTaxableEventCode = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getSpecialTaxableEvent().getSpecialTaxableEventCode();
					specialTaxableEvent.setSpecialTaxableEventCode(specialTaxableEventCode);

					// 3.1.6.1.26.2 SpecialTaxableEventReason
					String specialTaxableEventReason = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getSpecialTaxableEvent().getSpecialTaxableEventReason();
					specialTaxableEvent.setSpecialTaxableEventReason(specialTaxableEventReason);

					invoiceLine.setSpecialTaxableEvent(specialTaxableEvent);
				}

				// 3.1.6.1.27 ArticleCode
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getArticleCode())) {
					String articleCode = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getArticleCode();
					invoiceLine.setArticleCode(articleCode);
				}

				// 3.1.6.1.28 Extensions
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getExtensions() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getExtensions()
						.getAny() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getExtensions()
						.getAny().size() > 0) {
					es.mityc.appfacturae.facturae32.ExtensionsType extensions = null;

					// 3.1.6.1.28.1 [any]
					int extensionsCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getExtensions().getAny().size();
					for (int k = 0; k < extensionsCount; k++) {
						extensions = new es.mityc.appfacturae.facturae32.ExtensionsType();
						extensions.getAny().add(
							facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
								.getExtensions().getAny().get(k));
					}

					invoiceLine.setExtensions(extensions);
				}

				items.getInvoiceLine().add(invoiceLine);

			} // for 8

			invoiceType.setItems(items);

			// 3.1.7 PaymentDetails
			if (facturae.getInvoices().getInvoice().get(i).getPaymentDetails() != null
				&& facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment() != null
				&& facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment().size() > 0) {
				es.mityc.appfacturae.facturae32.InstallmentsType paymentDetails2 = new es.mityc.appfacturae.facturae32.InstallmentsType();

				// 3.1.7.1 Installment	
				es.mityc.appfacturae.facturae32.InstallmentType installment = null;

				// for 14
				int installmentCount = facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
					.size();
				for (int j = 0; j < installmentCount; j++) {

					installment = new es.mityc.appfacturae.facturae32.InstallmentType();

					// 3.1.7.1.1 InstallmentDueDate
					XMLGregorianCalendar installmentDueDate = facturae.getInvoices().getInvoice().get(i)
						.getPaymentDetails().getInstallment().get(j).getInstallmentDueDate();
					installment.setInstallmentDueDate(installmentDueDate);

					// 3.1.7.1.2 InstallmentAmount
					double installmentAmount = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
						.getInstallment().get(j).getInstallmentAmount();
					installment.setInstallmentAmount(installmentAmount);

					// 3.1.7.1.3 PaymentMeans	
					String paymentMeans = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
						.getInstallment().get(j).getPaymentMeans();
					installment.setPaymentMeans(paymentMeans);

					// 3.1.7.1.4 AccountToBeCredited
					if (facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited() != null) {
						es.mityc.appfacturae.facturae32.AccountType accountToBeCredited = new es.mityc.appfacturae.facturae32.AccountType();

						// 3.1.7.1.4.1

						// 3.1.7.1.4.1.1 IBAN
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeCredited().getIBAN())) {
							String iban3 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getIBAN();
							accountToBeCredited.setIban(iban3);
						}

						// 3.1.7.1.4.1.2 AccountNumber
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeCredited().getAccountNumber())) {
							String accountNumber3 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getAccountNumber();
							accountToBeCredited.setAccountNumber(accountNumber3);
						}

						// 3.1.7.1.4.2 BankCode
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeCredited().getBankCode())) {
							String bankCode = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBankCode();
							accountToBeCredited.setBankCode(bankCode);
						}

						// 3.1.7.1.4.3 BranchCode
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeCredited().getBranchCode())) {
							String branchCode = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBranchCode();
							accountToBeCredited.setBranchCode(branchCode);
						}

						// 3.1.7.1.4.4

						// if 15
						if (facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBranchInSpainAddress() != null) {

							// 3.1.7.1.4.4.1 BranchInSpainAddress
							es.mityc.appfacturae.facturae32.AddressType branchInSpainAddress = new es.mityc.appfacturae.facturae32.AddressType();

							// 3.1.7.1.4.4.1.1 Address
							String address7 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress()
								.getAddress();
							branchInSpainAddress.setAddress(address7);

							// 3.1.7.1.4.4.1.2 PostCode
							String postCode8 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress()
								.getPostCode();
							branchInSpainAddress.setPostCode(postCode8);

							// 3.1.7.1.4.4.1.3 Town
							String town7 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress().getTown();
							branchInSpainAddress.setTown(town7);

							// 3.1.7.1.4.4.1.4 Province
							String province7 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress()
								.getProvince();
							branchInSpainAddress.setProvince(province7);

							// 3.1.7.1.4.4.1.5 CountryCode
							es.mityc.appfacturae.facturae.CountryType countryCode7 = es.mityc.appfacturae.facturae.CountryType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
									.getInstallment().get(j).getAccountToBeCredited().getBranchInSpainAddress()
									.getCountryCode().value());
							branchInSpainAddress.setCountryCode(countryCode7);

							accountToBeCredited.setBranchInSpainAddress(branchInSpainAddress);

						} else if (facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeCredited().getOverseasBranchAddress() != null) {

							// 3.1.7.1.4.4.2 OverseasBranchAddress
							es.mityc.appfacturae.facturae32.OverseasAddressType overseasBranchAddress = new es.mityc.appfacturae.facturae32.OverseasAddressType();

							// 3.1.7.1.4.4.2.1 Address
							String address7 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress()
								.getAddress();
							overseasBranchAddress.setAddress(address7);

							// 3.1.7.1.4.4.2.2 PostCodeAndTown
							String postCodeAndTown8 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress()
								.getPostCodeAndTown();
							overseasBranchAddress.setPostCodeAndTown(postCodeAndTown8);

							// 3.1.7.1.4.4.2.3 Province
							String province7 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress()
								.getProvince();
							overseasBranchAddress.setProvince(province7);

							// 3.1.7.1.4.4.2.4 CountryCode
							es.mityc.appfacturae.facturae.CountryType countryCode7 = es.mityc.appfacturae.facturae.CountryType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
									.getInstallment().get(j).getAccountToBeCredited().getOverseasBranchAddress()
									.getCountryCode().value());
							overseasBranchAddress.setCountryCode(countryCode7);

							accountToBeCredited.setOverseasBranchAddress(overseasBranchAddress);

						} // if 15

						// 3.1.7.1.4.5 BIC
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeCredited().getBIC())) {
							String BIC = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeCredited().getBIC();
							accountToBeCredited.setBic(BIC);
						}

						installment.setAccountToBeCredited(accountToBeCredited);
					}

					// 3.1.7.1.5 PaymentReconciliationReference
					if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
						.get(j).getPaymentReconciliationReference())) {
						String paymentReconciliationReference = facturae.getInvoices().getInvoice().get(i)
							.getPaymentDetails().getInstallment().get(j).getPaymentReconciliationReference();
						installment.setPaymentReconciliationReference(paymentReconciliationReference);
					}

					// 3.1.7.1.6 AccountToBeDebited
					if (facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited() != null) {
						es.mityc.appfacturae.facturae32.AccountType accountToBeDebited = new es.mityc.appfacturae.facturae32.AccountType();

						// 3.1.7.1.6.1

						// 3.1.7.1.6.1.1 IBAN
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeDebited().getIBAN())) {
							String iban3 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getIBAN();
							accountToBeDebited.setIban(iban3);
						}

						// 3.1.7.1.6.1.2 AccountNumber
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeDebited().getAccountNumber())) {
							String accountNumber3 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getAccountNumber();
							accountToBeDebited.setAccountNumber(accountNumber3);
						}

						// 3.1.7.1.6.2 BankCode
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeDebited().getBankCode())) {
							String bankCode2 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBankCode();
							accountToBeDebited.setBankCode(bankCode2);
						}

						// 3.1.7.1.6.3 BranchCode
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeDebited().getBranchCode())) {
							String branchCode2 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBranchCode();
							accountToBeDebited.setBranchCode(branchCode2);
						}

						// 3.1.7.1.6.4 

						// if 16
						if (facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBranchInSpainAddress() != null) {

							// 3.1.7.1.6.4.1 BranchInSpainAddress
							es.mityc.appfacturae.facturae32.AddressType branchInSpainAddress2 = new es.mityc.appfacturae.facturae32.AddressType();

							// 3.1.7.1.6.4.1.1 Address
							String address8 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress().getAddress();
							branchInSpainAddress2.setAddress(address8);

							// 3.1.7.1.6.4.1.2 PostCode
							String postCode9 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress()
								.getPostCode();
							branchInSpainAddress2.setPostCode(postCode9);

							// 3.1.7.1.6.4.1.3 Town
							String town8 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress().getTown();
							branchInSpainAddress2.setTown(town8);

							// 3.1.7.1.6.4.1.4 Province
							String province8 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress()
								.getProvince();
							branchInSpainAddress2.setProvince(province8);

							// 3.1.7.1.6.4.1.5 CountryCode
							es.mityc.appfacturae.facturae.CountryType countryCode8 = es.mityc.appfacturae.facturae.CountryType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
									.getInstallment().get(j).getAccountToBeDebited().getBranchInSpainAddress()
									.getCountryCode().value());
							branchInSpainAddress2.setCountryCode(countryCode8);

							accountToBeDebited.setBranchInSpainAddress(branchInSpainAddress2);

						} else if (facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeDebited().getOverseasBranchAddress() != null) {

							// 3.1.7.1.6.4.2 OverseasBranchAddress
							es.mityc.appfacturae.facturae32.OverseasAddressType overseasBranchAddress2 = new es.mityc.appfacturae.facturae32.OverseasAddressType();

							// 3.1.7.1.6.4.2.1 Address
							String address8 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress()
								.getAddress();
							overseasBranchAddress2.setAddress(address8);

							// 3.1.7.1.6.4.2.2 PostCodeAndTown
							String postCodeAndTown9 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress()
								.getPostCodeAndTown();
							overseasBranchAddress2.setPostCodeAndTown(postCodeAndTown9);

							// 3.1.7.1.6.4.2.3 Province
							String province8 = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress()
								.getProvince();
							overseasBranchAddress2.setProvince(province8);

							// 3.1.7.1.6.4.2.4 CountryCode
							es.mityc.appfacturae.facturae.CountryType countryCode8 = es.mityc.appfacturae.facturae.CountryType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
									.getInstallment().get(j).getAccountToBeDebited().getOverseasBranchAddress()
									.getCountryCode().value());
							overseasBranchAddress2.setCountryCode(countryCode8);

							accountToBeDebited.setOverseasBranchAddress(overseasBranchAddress2);

						} // if 16

						// 1.6.2.1.6.5 BIC
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
							.get(j).getAccountToBeDebited().getBIC())) {
							String BIC = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
								.getInstallment().get(j).getAccountToBeDebited().getBIC();
							accountToBeDebited.setBic(BIC);
						}

						installment.setAccountToBeDebited(accountToBeDebited);
					}

					// 3.1.7.1.7 CollectionAdditionalInformation
					if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
						.get(j).getCollectionAdditionalInformation())) {
						String collectionAdditionalInformation = facturae.getInvoices().getInvoice().get(i)
							.getPaymentDetails().getInstallment().get(j).getCollectionAdditionalInformation();
						installment.setCollectionAdditionalInformation(collectionAdditionalInformation);
					}

					// 3.1.7.1.8 RegulatoryReportingData
					if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
						.get(j).getRegulatoryReportingData())) {
						String regulatoryReportingData = facturae.getInvoices().getInvoice().get(i).getPaymentDetails()
							.getInstallment().get(j).getRegulatoryReportingData();
						installment.setRegulatoryReportingData(regulatoryReportingData);
					}

					// 3.1.7.1.9 DebitReconciliationReference
					if (!"".equals(facturae.getInvoices().getInvoice().get(i).getPaymentDetails().getInstallment()
						.get(j).getDebitReconciliationReference())) {
						String debitReconciliationReference = facturae.getInvoices().getInvoice().get(i)
							.getPaymentDetails().getInstallment().get(j).getDebitReconciliationReference();
						installment.setDebitReconciliationReference(debitReconciliationReference);
					}

					paymentDetails2.getInstallment().add(installment);

				} // for 14

				invoiceType.setPaymentDetails(paymentDetails2);
			}

			// 3.1.8 LegalLiterals
			if (facturae.getInvoices().getInvoice().get(i).getLegalLiterals() != null
				&& facturae.getInvoices().getInvoice().get(i).getLegalLiterals().getLegalReference() != null) {
				es.mityc.appfacturae.facturae.LegalLiteralsType legalLiterals = new es.mityc.appfacturae.facturae.LegalLiteralsType();

				// 3.1.8.1 LegalReference
				String legalReference = null;

				// for 15
				int legalRefCount = facturae.getInvoices().getInvoice().get(i).getLegalLiterals().getLegalReference()
					.size();
				for (int j = 0; j < legalRefCount; j++) {
					legalReference = new String();
					legalReference = facturae.getInvoices().getInvoice().get(i).getLegalLiterals().getLegalReference()
						.get(j);
					legalLiterals.getLegalReference().add(legalReference);
				} // for 15

				invoiceType.setLegalLiterals(legalLiterals);
			}
			//ctg attach
			// 3.1.9 AdditionalData
			if (facturae.getInvoices().getInvoice().get(i).getAdditionalData() != null) {
				es.mityc.appfacturae.facturae32.AdditionalDataType additionalData = new es.mityc.appfacturae.facturae32.AdditionalDataType();

				// 3.1.9.1 RelatedInvoice
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedInvoice())) {
					String relatedInvoice = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
						.getRelatedInvoice();
					additionalData.setRelatedInvoice(relatedInvoice);
				}

				// 3.1.9.2 RelatedDocuments
				if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
						.getAttachment() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
						.getAttachment().size() > 0) {
					es.mityc.appfacturae.facturae.AttachedDocumentsType relatedDocuments = new es.mityc.appfacturae.facturae.AttachedDocumentsType();

					// 3.1.9.2.1 Attachment
					es.mityc.appfacturae.facturae.AttachmentType attachment = null;

					// for 16
					int attachmentCount = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
						.getRelatedDocuments().getAttachment().size();
					for (int j = 0; j < attachmentCount; j++) {

						attachment = new es.mityc.appfacturae.facturae.AttachmentType();

						// 3.1.9.2.1.1 AttachmentCompressionAlgorithm
						if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
							.getAttachment().get(j).getAttachmentCompressionAlgorithm() != null) {
							es.mityc.appfacturae.facturae.AttachmentCompressionAlgorithmType attachmentCompressionAlgorithm = es.mityc.appfacturae.facturae.AttachmentCompressionAlgorithmType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
									.getRelatedDocuments().getAttachment().get(j).getAttachmentCompressionAlgorithm()
									.value());
							attachment.setAttachmentCompressionAlgorithm(attachmentCompressionAlgorithm);
						}

						// 3.1.9.2.1.2 AttachmentFormat
						es.mityc.appfacturae.facturae.AttachmentFormatType attachmentFormat = es.mityc.appfacturae.facturae.AttachmentFormatType
							.fromValue(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
								.getRelatedDocuments().getAttachment().get(j).getAttachmentFormat().value());
						attachment.setAttachmentFormat(attachmentFormat);

						// 3.1.9.2.1.3 AttachmentEncoding
						if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
							.getAttachment().get(j).getAttachmentEncoding() != null) {
							es.mityc.appfacturae.facturae.AttachmentEncodingType attachmentEncoding = es.mityc.appfacturae.facturae.AttachmentEncodingType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
									.getRelatedDocuments().getAttachment().get(j).getAttachmentEncoding().value());
							attachment.setAttachmentEncoding(attachmentEncoding);
						}

						// 3.1.9.2.1.4 AttachmentDescription
						if (!"".equals(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
							.getRelatedDocuments().getAttachment().get(j).getAttachmentDescription())) {
							String attachmentDescription = facturae.getInvoices().getInvoice().get(i)
								.getAdditionalData().getRelatedDocuments().getAttachment().get(j)
								.getAttachmentDescription();
							attachment.setAttachmentDescription(attachmentDescription);
						}

						// 3.1.9.2.1.5 AttachmentData
						String attachmentData = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
							.getRelatedDocuments().getAttachment().get(j).getAttachmentData();
						attachment.setAttachmentData(attachmentData);

						relatedDocuments.getAttachment().add(attachment);

					} // for 16

					additionalData.setRelatedDocuments(relatedDocuments);
				}

				// 3.1.9.3 InvoiceAdditionalInformation
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
					.getInvoiceAdditionalInformation())) {
					String invoiceAdditionalInformation = facturae.getInvoices().getInvoice().get(i)
						.getAdditionalData().getInvoiceAdditionalInformation();
					additionalData.setInvoiceAdditionalInformation(invoiceAdditionalInformation);
				}

				// 3.1.9.4 Extensions
				if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions().getAny() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions().getAny().size() > 0) {
					es.mityc.appfacturae.facturae32.ExtensionsType extensions2 = null;

					// 3.1.9.4.1 [any]
					int extensionsCount = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
						.getExtensions().getAny().size();
					for (int k = 0; k < extensionsCount; k++) {
						extensions2 = new es.mityc.appfacturae.facturae32.ExtensionsType();
						extensions2.getAny().add(
							facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions().getAny()
								.get(k));
					}

					additionalData.setExtensions(extensions2);
				}

				invoiceType.setAdditionalData(additionalData);
			}

			invoices.getInvoice().add(invoiceType);

		} // for 1

		invoice.setInvoices(invoices);

		// 4 Extensions
		if (facturae.getExtensions() != null && facturae.getExtensions().getAny() != null
			&& facturae.getExtensions().getAny().size() > 0) {
			es.mityc.appfacturae.facturae32.ExtensionsType extensions3 = null;

			// 4.1 [any]
			int extensionsCount2 = facturae.getExtensions().getAny().size();
			for (int k = 0; k < extensionsCount2; k++) {
				extensions3 = new es.mityc.appfacturae.facturae32.ExtensionsType();
				extensions3.getAny().add(facturae.getExtensions().getAny().get(k));
			}

			invoice.setExtensions(extensions3);
		}

		return invoice;

	}

	/**
	 * Converts an es.mityc.appfacturae.facturae.Facturae object into an es.mityc.facturae32.Facturae object  ***
	 * @param facturae The es.mityc.appfacturae.facturae.Facturae object to convert
	 * @return The es.mityc.facturae32.Facturae object
	 */
	public static es.mityc.facturae32.Facturae getfacturae32(es.mityc.appfacturae.facturae.Facturae facturae) {

		// 0 es.mityc.facturae32.Facturae
		es.mityc.facturae32.Facturae invoice32 = new es.mityc.facturae32.Facturae();

		// 1 FileHeader
		es.mityc.facturae32.FileHeaderType fileHeader = new es.mityc.facturae32.FileHeaderType();

		// 1.1 SchemaVersion 
		String schemaVersion = facturae.getFileHeader().getSchemaVersion();
		fileHeader.setSchemaVersion(schemaVersion);

		// 1.2 Modality
		es.mityc.facturae32.ModalityType modality = es.mityc.facturae32.ModalityType.fromValue(facturae.getFileHeader()
			.getModality().value());
		fileHeader.setModality(modality);

		// 1.3 InvoiceIssuerType
		es.mityc.facturae32.InvoiceIssuerTypeType invoiceIssuerType = es.mityc.facturae32.InvoiceIssuerTypeType
			.fromValue(facturae.getFileHeader().getInvoiceIssuerType().value());
		fileHeader.setInvoiceIssuerType(invoiceIssuerType);

		// 1.4 Third Party

		if (facturae.getFileHeader().getThirdParty() != null) {

			es.mityc.facturae32.ThirdPartyType thirdParty = new es.mityc.facturae32.ThirdPartyType();

			// 1.4.1 TaxIdentification
			es.mityc.facturae32.TaxIdentificationType taxIdentification3 = new es.mityc.facturae32.TaxIdentificationType();

			// 1.4.1.1 PersonTypeCode
			es.mityc.facturae32.PersonTypeCodeType personTypeCode3 = es.mityc.facturae32.PersonTypeCodeType
				.fromValue(facturae.getFileHeader().getThirdParty().getTaxIdentification().getPersonTypeCode().value());
			taxIdentification3.setPersonTypeCode(personTypeCode3);

			// 1.4.1.2 ResidenceTypeCode
			es.mityc.facturae32.ResidenceTypeCodeType residenceTypeCode3 = es.mityc.facturae32.ResidenceTypeCodeType
				.fromValue(facturae.getFileHeader().getThirdParty().getTaxIdentification().getResidenceTypeCode()
					.value());
			taxIdentification3.setResidenceTypeCode(residenceTypeCode3);

			// 1.4.1.3 TaxIdentificationNumber
			String taxIdentificationNumber3 = facturae.getFileHeader().getThirdParty().getTaxIdentification()
				.getTaxIdentificationNumber();
			taxIdentification3.setTaxIdentificationNumber(taxIdentificationNumber3);

			thirdParty.setTaxIdentification(taxIdentification3);

			// 1.4.2
			// if 1
			if (facturae.getFileHeader().getThirdParty().getLegalEntity() != null) {

				// 1.4.2.1 LegalEntity
				es.mityc.facturae32.LegalEntityType legalEntity5 = new es.mityc.facturae32.LegalEntityType();

				// 1.4.2.1.1 CorporateName
				String corporateName = facturae.getFileHeader().getThirdParty().getLegalEntity().getCorporateName();
				legalEntity5.setCorporateName(corporateName);

				// 1.4.2.1.2 TradeName
				if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getTradeName())) {
					String tradeName = facturae.getFileHeader().getThirdParty().getLegalEntity().getTradeName();
					legalEntity5.setTradeName(tradeName);
				}

				// 1.4.2.1.3 RegistrationData
				if (facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData() != null) {
					es.mityc.facturae32.RegistrationDataType registrationData = new es.mityc.facturae32.RegistrationDataType();

					// 1.4.2.1.3.1 Book
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
						.getBook())) {
						String book = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
							.getBook();
						registrationData.setBook(book);
					}

					// 1.4.2.1.3.2 RegisterOfCompaniesLocation
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
						.getRegisterOfCompaniesLocation())) {
						String registerOfCompaniesLocation = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getRegistrationData().getRegisterOfCompaniesLocation();
						registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);
					}

					// 1.4.2.1.3.3 Sheet
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
						.getSheet())) {
						String sheet = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
							.getSheet();
						registrationData.setSheet(sheet);
					}

					// 1.4.2.1.3.4 Folio
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
						.getFolio())) {
						String folio = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
							.getFolio();
						registrationData.setFolio(folio);
					}

					// 1.4.2.1.3.5 Section
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
						.getSection())) {
						String section = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getRegistrationData().getSection();
						registrationData.setSection(section);
					}

					// 1.4.2.1.3.6 Volume
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
						.getVolume())) {
						String volume = facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
							.getVolume();
						registrationData.setVolume(volume);
					}

					// 1.4.2.1.3.7 AdditionalRegistrationData
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getRegistrationData()
						.getAdditionalRegistrationData())) {
						String additionalRegistrationData = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getRegistrationData().getAdditionalRegistrationData();
						registrationData.setAdditionalRegistrationData(additionalRegistrationData);
					}

					legalEntity5.setRegistrationData(registrationData);
				}

				// 1.4.2.1.4

				// if 2
				if (facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain() != null) {

					// 1.4.2.1.4.1 AddressInSpain
					es.mityc.facturae32.AddressType addressInSpain5 = new es.mityc.facturae32.AddressType();

					// 1.4.2.1.4.1.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain()
						.getAddress();
					addressInSpain5.setAddress(address5);

					// 1.4.2.1.4.1.2 PostCode
					String postCode5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain()
						.getPostCode();
					addressInSpain5.setPostCode(postCode5);

					// 1.4.2.1.4.1.3 Town
					String town5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain()
						.getTown();
					addressInSpain5.setTown(town5);

					// 1.4.2.1.4.1.4 Province
					String province5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getAddressInSpain()
						.getProvince();
					addressInSpain5.setProvince(province5);

					// 1.4.2.1.4.1.5 CountryCode
					es.mityc.facturae32.CountryType countryCode5 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getFileHeader()
							.getThirdParty().getLegalEntity().getAddressInSpain())).getCountryCode().value());
					addressInSpain5.setCountryCode(countryCode5);

					legalEntity5.setAddressInSpain(addressInSpain5);

				} else if (facturae.getFileHeader().getThirdParty().getLegalEntity().getOverseasAddress() != null) {

					// 1.4.2.1.4.2 OverseasAddress
					es.mityc.facturae32.OverseasAddressType overseasAddress5 = new es.mityc.facturae32.OverseasAddressType();

					// 1.4.2.1.4.2.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getOverseasAddress()
						.getAddress();
					overseasAddress5.setAddress(address5);

					// 1.4.2.1.4.2.2 PostCodeAndTown
					String postCodeandTown5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
						.getOverseasAddress().getPostCodeAndTown();
					overseasAddress5.setPostCodeAndTown(postCodeandTown5);

					// 1.4.2.1.4.2.3 Province
					String province5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getOverseasAddress()
						.getProvince();
					overseasAddress5.setProvince(province5);

					// 1.4.2.1.4.2.4 CountryCode
					es.mityc.facturae32.CountryType countryCode5 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getFileHeader()
							.getThirdParty().getLegalEntity().getOverseasAddress())).getCountryCode().value());
					overseasAddress5.setCountryCode(countryCode5);

					legalEntity5.setOverseasAddress(overseasAddress5);

				} // if 2

				// 1.4.2.1.5 ContactDetails
				if (facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails() != null) {
					es.mityc.facturae32.ContactDetailsType contactDetails5 = new es.mityc.facturae32.ContactDetailsType();

					// 1.4.2.1.5.1 Telephone
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getTelephone())) {
						String telephone5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getContactDetails().getTelephone();
						contactDetails5.setTelephone(telephone5);
					}

					// 1.4.2.1.5.2 TeleFax
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getTeleFax())) {
						String teleFax5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
							.getTeleFax();
						contactDetails5.setTeleFax(teleFax5);
					}

					// 1.4.2.1.5.3 WebAddress
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getWebAddress())) {
						String webAddress5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getContactDetails().getWebAddress();
						contactDetails5.setWebAddress(webAddress5);
					}

					// 1.4.2.1.5.4 ElectronicMail
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getElectronicMail())) {
						String electronicMail5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getContactDetails().getElectronicMail();
						contactDetails5.setElectronicMail(electronicMail5);
					}

					// 1.4.2.1.5.5 ContactPersons
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getContactPersons())) {
						String contactPersons5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getContactDetails().getContactPersons();
						contactDetails5.setContactPersons(contactPersons5);
					}

					// 1.4.2.1.5.6 CnoCnae
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getCnoCnae())) {
						String cnoCnae5 = facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
							.getCnoCnae();
						contactDetails5.setCnoCnae(cnoCnae5);
					}

					// 1.4.2.1.5.7 INETownCode
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getIneTownCode())) {
						String ineTownCode5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getContactDetails().getIneTownCode();
						contactDetails5.setINETownCode(ineTownCode5);
					}

					// 1.4.2.1.5.8 AdditionalContactDetails
					if (!"".equals(facturae.getFileHeader().getThirdParty().getLegalEntity().getContactDetails()
						.getAdditionalContactDetails())) {
						String additionalContactDetails5 = facturae.getFileHeader().getThirdParty().getLegalEntity()
							.getContactDetails().getAdditionalContactDetails();
						contactDetails5.setAdditionalContactDetails(additionalContactDetails5);
					}

					legalEntity5.setContactDetails(contactDetails5);
				}

				thirdParty.setLegalEntity(legalEntity5);

			} else if (facturae.getFileHeader().getThirdParty().getIndividual() != null) {

				// 1.4.2.2 Individual
				es.mityc.facturae32.IndividualType individual5 = new es.mityc.facturae32.IndividualType();

				// 1.4.2.2.1 Name
				String name5 = facturae.getFileHeader().getThirdParty().getIndividual().getName();
				individual5.setName(name5);

				// 1.4.2.2.2 FirstSurname
				String firstSurname5 = facturae.getFileHeader().getThirdParty().getIndividual().getFirstSurname();
				individual5.setFirstSurname(firstSurname5);

				// 1.4.2.2.3 SecondSurname
				if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getSecondSurname())) {
					String secondSurname5 = facturae.getFileHeader().getThirdParty().getIndividual().getSecondSurname();
					individual5.setSecondSurname(secondSurname5);
				}

				// 1.4.2.2.4

				// if 3
				if (facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain() != null) {

					// 1.4.2.2.4.1 AddressInSpain
					es.mityc.facturae32.AddressType addressInSpain5 = new es.mityc.facturae32.AddressType();

					// 1.4.2.2.4.1.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain()
						.getAddress();
					addressInSpain5.setAddress(address5);

					// 1.4.2.2.4.1.2 PostCode
					String postCode5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain()
						.getPostCode();
					addressInSpain5.setPostCode(postCode5);

					// 1.4.2.2.4.1.3 Town
					String town5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain()
						.getTown();
					addressInSpain5.setTown(town5);

					// 1.4.2.2.4.1.4 Province
					String province5 = facturae.getFileHeader().getThirdParty().getIndividual().getAddressInSpain()
						.getProvince();
					addressInSpain5.setProvince(province5);

					// 1.4.2.2.4.1.5 CountryCode
					es.mityc.facturae32.CountryType countryCode5 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getFileHeader()
							.getThirdParty().getIndividual().getAddressInSpain())).getCountryCode().value());
					addressInSpain5.setCountryCode(countryCode5);

					individual5.setAddressInSpain(addressInSpain5);

				} else if (facturae.getFileHeader().getThirdParty().getIndividual().getOverseasAddress() != null) {

					// 1.4.2.2.4.2 OverseasAddress
					es.mityc.facturae32.OverseasAddressType overseasAddressType5 = new es.mityc.facturae32.OverseasAddressType();

					// 1.4.2.2.4.2.1 Address
					String address5 = facturae.getFileHeader().getThirdParty().getIndividual().getOverseasAddress()
						.getAddress();
					overseasAddressType5.setAddress(address5);

					// 1.4.2.2.4.2.2 PostCodeAndTown
					String postCodeandTown5 = facturae.getFileHeader().getThirdParty().getIndividual()
						.getOverseasAddress().getPostCodeAndTown();
					overseasAddressType5.setPostCodeAndTown(postCodeandTown5);

					// 1.4.2.2.4.2.3 Province
					String province5 = facturae.getFileHeader().getThirdParty().getIndividual().getOverseasAddress()
						.getProvince();
					overseasAddressType5.setProvince(province5);

					// 1.4.2.2.4.2.4 CountryCode
					es.mityc.facturae32.CountryType countryCode5 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getFileHeader()
							.getThirdParty().getIndividual().getOverseasAddress())).getCountryCode().value());
					overseasAddressType5.setCountryCode(countryCode5);

					individual5.setOverseasAddress(overseasAddressType5);

				} //if 3

				// 1.4.2.2.5 ContactDetails
				if (facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails() != null) {
					es.mityc.facturae32.ContactDetailsType contactDetails5 = new es.mityc.facturae32.ContactDetailsType();

					// 1.4.2.2.5.1 Telephone
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getTelephone())) {
						String telephone5 = facturae.getFileHeader().getThirdParty().getIndividual()
							.getContactDetails().getTelephone();
						contactDetails5.setTelephone(telephone5);
					}

					// 1.4.2.2.5.2 TeleFax
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getTeleFax())) {
						String teleFax5 = facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
							.getTeleFax();
						contactDetails5.setTeleFax(teleFax5);
					}

					// 1.4.2.2.5.3 WebAddress
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getWebAddress())) {
						String webAddress5 = facturae.getFileHeader().getThirdParty().getIndividual()
							.getContactDetails().getWebAddress();
						contactDetails5.setWebAddress(webAddress5);
					}

					// 1.4.2.2.5.4 ElectronicMail
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getElectronicMail())) {
						String electronicMail5 = facturae.getFileHeader().getThirdParty().getIndividual()
							.getContactDetails().getElectronicMail();
						contactDetails5.setElectronicMail(electronicMail5);
					}

					// 1.4.2.2.5.5 ContactPersons
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getContactPersons())) {
						String contactPersons5 = facturae.getFileHeader().getThirdParty().getIndividual()
							.getContactDetails().getContactPersons();
						contactDetails5.setContactPersons(contactPersons5);
					}

					// 1.4.2.2.5.6 CnoCnae
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getCnoCnae())) {
						String cnoCnae5 = facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
							.getCnoCnae();
						contactDetails5.setCnoCnae(cnoCnae5);
					}

					// 1.4.2.2.5.7 INETownCode
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getIneTownCode())) {
						String ineTownCode5 = facturae.getFileHeader().getThirdParty().getIndividual()
							.getContactDetails().getIneTownCode();
						contactDetails5.setINETownCode(ineTownCode5);
					}

					// 1.4.2.2.5.8 AdditionalContactDetails
					if (!"".equals(facturae.getFileHeader().getThirdParty().getIndividual().getContactDetails()
						.getAdditionalContactDetails())) {
						String additionalContactDetails5 = facturae.getFileHeader().getThirdParty().getIndividual()
							.getContactDetails().getAdditionalContactDetails();
						contactDetails5.setAdditionalContactDetails(additionalContactDetails5);
					}

					individual5.setContactDetails(contactDetails5);
				}

				thirdParty.setIndividual(individual5);

			} // if 1

			fileHeader.setThirdParty(thirdParty);

		}

		// 1.5 Batch
		es.mityc.facturae32.BatchType batch = new es.mityc.facturae32.BatchType();

		// 1.5.1 BatchIdentifier
		String batchIdentifier = facturae.getFileHeader().getBatch().getBatchIdentifier();
		batch.setBatchIdentifier(batchIdentifier);

		// 1.5.2 InvoicesCount
		long invoicesCount = facturae.getFileHeader().getBatch().getInvoicesCount();
		batch.setInvoicesCount(invoicesCount);

		// 1.5.3 TotalInvoicesAmount
		es.mityc.facturae32.AmountType totalInvoicesAmount = new es.mityc.facturae32.AmountType();

		// 1.5.3.1 TotalAmount
		double totalAmount = facturae.getFileHeader().getBatch().getTotalInvoicesAmount().getTotalAmount();
		totalInvoicesAmount.setTotalAmount(totalAmount);
		// 1.5.3.2 EquivalentInEuros
		if (facturae.getFileHeader().getBatch().getTotalInvoicesAmount().getEquivalentInEuros() != null) {
			Double equivalentInEuros = facturae.getFileHeader().getBatch().getTotalInvoicesAmount()
				.getEquivalentInEuros();
			totalInvoicesAmount.setEquivalentInEuros(equivalentInEuros);
		}

		batch.setTotalInvoicesAmount(totalInvoicesAmount);

		// 1.5.4 TotalOutstandingAmount
		es.mityc.facturae32.AmountType totalOutstandingAmount = new es.mityc.facturae32.AmountType();

		// 1.5.4.1 TotalAmount
		double totalAmount2 = facturae.getFileHeader().getBatch().getTotalOutstandingAmount().getTotalAmount();
		totalOutstandingAmount.setTotalAmount(totalAmount2);
		// 1.5.4.2 EquivalentInEuros
		if (facturae.getFileHeader().getBatch().getTotalOutstandingAmount().getEquivalentInEuros() != null) {
			Double equivalentInEuros2 = facturae.getFileHeader().getBatch().getTotalOutstandingAmount()
				.getEquivalentInEuros();
			totalOutstandingAmount.setEquivalentInEuros(equivalentInEuros2);
		}

		batch.setTotalOutstandingAmount(totalOutstandingAmount);

		// 1.5.5 TotalExecutableAmount
		es.mityc.facturae32.AmountType totalExecutableAmount = new es.mityc.facturae32.AmountType();

		// 1.5.5.1 TotalAmount
		double totalAmount3 = facturae.getFileHeader().getBatch().getTotalExecutableAmount().getTotalAmount();
		totalExecutableAmount.setTotalAmount(totalAmount3);
		// 1.5.5.2 EquivalentInEuros
		if (facturae.getFileHeader().getBatch().getTotalExecutableAmount().getEquivalentInEuros() != null) {
			Double equivalentInEuros3 = facturae.getFileHeader().getBatch().getTotalExecutableAmount()
				.getEquivalentInEuros();
			totalExecutableAmount.setEquivalentInEuros(equivalentInEuros3);
		}

		batch.setTotalExecutableAmount(totalExecutableAmount);

		// 1.5.6 InvoiceCurrencyCode	
		es.mityc.facturae32.CurrencyCodeType invoiceCurrencyCode = es.mityc.facturae32.CurrencyCodeType
			.fromValue(((es.mityc.appfacturae.facturae32.BatchType) (facturae.getFileHeader().getBatch()))
				.getInvoiceCurrencyCode().value());
		batch.setInvoiceCurrencyCode(invoiceCurrencyCode);

		fileHeader.setBatch(batch);

		// 1.6 FactoringAssignmentData

		if (facturae.getFileHeader().getFactoringAssignmentData() != null) {

			es.mityc.facturae32.FactoringAssignmentDataType factoringAssignmentData = new es.mityc.facturae32.FactoringAssignmentDataType();

			// 1.6.1 Assignee
			es.mityc.facturae32.AssigneeType assignee = new es.mityc.facturae32.AssigneeType();

			// 1.6.1.1 TaxIdentification
			es.mityc.facturae32.TaxIdentificationType taxIdentification4 = new es.mityc.facturae32.TaxIdentificationType();

			// 1.6.1.1.1 PersonTypeCode
			es.mityc.facturae32.PersonTypeCodeType personTypeCode4 = es.mityc.facturae32.PersonTypeCodeType
				.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getTaxIdentification()
					.getPersonTypeCode().value());
			taxIdentification4.setPersonTypeCode(personTypeCode4);

			// 1.6.1.1.2 ResidenceTypeCode
			es.mityc.facturae32.ResidenceTypeCodeType residenceTypeCode4 = es.mityc.facturae32.ResidenceTypeCodeType
				.fromValue(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getTaxIdentification()
					.getResidenceTypeCode().value());
			taxIdentification4.setResidenceTypeCode(residenceTypeCode4);

			// 1.6.1.1.3 TaxIdentificationNumber
			String taxIdentificationNumber4 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
				.getTaxIdentification().getTaxIdentificationNumber();
			taxIdentification4.setTaxIdentificationNumber(taxIdentificationNumber4);

			assignee.setTaxIdentification(taxIdentification4);

			// 1.6.1.2

			// if 4
			if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity() != null) {

				// 1.6.1.2.1 LegalEntity
				es.mityc.facturae32.LegalEntityType legalEntity = new es.mityc.facturae32.LegalEntityType();

				// 1.6.1.2.1.1 CorporateName
				String corporateName = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
					.getLegalEntity().getCorporateName();
				legalEntity.setCorporateName(corporateName);

				// 1.6.1.2.1.2 TradeName
				if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getTradeName())) {
					String tradeName = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getTradeName();
					legalEntity.setTradeName(tradeName);
				}

				// 1.6.1.2.1.3 RegistrationData
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getRegistrationData() != null) {
					es.mityc.facturae32.RegistrationDataType registrationData = new es.mityc.facturae32.RegistrationDataType();

					// 1.6.1.2.1.3.1 Book
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getBook())) {
						String book = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getRegistrationData().getBook();
						registrationData.setBook(book);
					}

					// 1.6.1.2.1.3.2 RegisterOfCompaniesLocation
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getRegisterOfCompaniesLocation())) {
						String registerOfCompaniesLocation = facturae.getFileHeader().getFactoringAssignmentData()
							.getAssignee().getLegalEntity().getRegistrationData().getRegisterOfCompaniesLocation();
						registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);
					}

					// 1.6.1.2.1.3.3 Sheet
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getSheet())) {
						String sheet = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getRegistrationData().getSheet();
						registrationData.setSheet(sheet);
					}

					// 1.6.1.2.1.3.4 Folio
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getFolio())) {
						String folio = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getRegistrationData().getFolio();
						registrationData.setFolio(folio);
					}

					// 1.6.1.2.1.3.5 Section
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getSection())) {
						String section = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getRegistrationData().getSection();
						registrationData.setSection(section);
					}

					// 1.6.1.2.1.3.6 Volume
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getVolume())) {
						String volume = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getRegistrationData().getVolume();
						registrationData.setVolume(volume);
					}

					// 1.6.1.2.1.3.7 AdditionalRegistrationData
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getRegistrationData().getAdditionalRegistrationData())) {
						String additionalRegistrationData = facturae.getFileHeader().getFactoringAssignmentData()
							.getAssignee().getLegalEntity().getRegistrationData().getAdditionalRegistrationData();
						registrationData.setAdditionalRegistrationData(additionalRegistrationData);
					}

					legalEntity.setRegistrationData(registrationData);
				}

				// 1.6.1.2.1.4

				// if 5
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getAddressInSpain() != null) {

					// 1.6.1.2.1.4.1 AddressInSpain
					es.mityc.facturae32.AddressType addressInSpain6 = new es.mityc.facturae32.AddressType();

					// 1.6.1.2.1.4.1.1 Address
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getAddressInSpain().getAddress();
					addressInSpain6.setAddress(address6);

					// 1.6.1.2.1.4.1.2 PostCode
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getAddressInSpain().getPostCode();
					addressInSpain6.setPostCode(postCode6);

					// 1.6.1.2.1.4.1.3 Town
					String town6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getAddressInSpain().getTown();
					addressInSpain6.setTown(town6);

					// 1.6.1.2.1.4.1.4 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getAddressInSpain().getProvince();
					addressInSpain6.setProvince(province6);

					// 1.6.1.2.1.4.1.5 CountryCode
					es.mityc.facturae32.CountryType countryCode6 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getFileHeader()
							.getFactoringAssignmentData().getAssignee().getLegalEntity().getAddressInSpain()))
							.getCountryCode().value());
					addressInSpain6.setCountryCode(countryCode6);

					legalEntity.setAddressInSpain(addressInSpain6);

				} else if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getOverseasAddress() != null) {

					// 1.6.1.2.1.4.2 OverseasAddress
					es.mityc.facturae32.OverseasAddressType overseasAddress6 = new es.mityc.facturae32.OverseasAddressType();

					// 1.6.1.2.1.4.2.1 Address
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getOverseasAddress().getAddress();
					overseasAddress6.setAddress(address6);

					// 1.6.1.2.1.4.2.2 PostCodeAndTown
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getOverseasAddress().getPostCodeAndTown();
					overseasAddress6.setPostCodeAndTown(postCode6);

					// 1.6.1.2.1.4.2.3 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getLegalEntity().getOverseasAddress().getProvince();
					overseasAddress6.setProvince(province6);

					// 1.6.1.2.1.4.2.4 CountryCode
					es.mityc.facturae32.CountryType countryCode6 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getFileHeader()
							.getFactoringAssignmentData().getAssignee().getLegalEntity().getOverseasAddress()))
							.getCountryCode().value());
					overseasAddress6.setCountryCode(countryCode6);

					legalEntity.setOverseasAddress(overseasAddress6);

				} // if 5

				// 1.6.1.2.1.5 ContactDetails
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
					.getContactDetails() != null) {
					es.mityc.facturae32.ContactDetailsType contactDetails6 = new es.mityc.facturae32.ContactDetailsType();

					// 1.6.1.2.1.5.1 Telephone
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getTelephone())) {
						String telephone6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getContactDetails().getTelephone();
						contactDetails6.setTelephone(telephone6);
					}

					// 1.6.1.2.1.5.2 TeleFax
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getTeleFax())) {
						String teleFax6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getContactDetails().getTeleFax();
						contactDetails6.setTeleFax(teleFax6);
					}

					// 1.6.1.2.1.5.3 WebAddress
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getWebAddress())) {
						String webAddress6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getContactDetails().getWebAddress();
						contactDetails6.setWebAddress(webAddress6);
					}

					// 1.6.1.2.1.5.4 ElectronicMail
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getElectronicMail())) {
						String electronicMail6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getContactDetails().getElectronicMail();
						contactDetails6.setElectronicMail(electronicMail6);
					}

					// 1.6.1.2.1.5.5 ContactPersons
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getContactPersons())) {
						String contactPersons6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getContactDetails().getContactPersons();
						contactDetails6.setContactPersons(contactPersons6);
					}

					// 1.6.1.2.1.5.6 CnoCnae
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getCnoCnae())) {
						String cnoCnae6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getContactDetails().getCnoCnae();
						contactDetails6.setCnoCnae(cnoCnae6);
					}

					// 1.6.1.2.1.5.7 INETownCode
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getIneTownCode())) {
						String ineTownCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getLegalEntity().getContactDetails().getIneTownCode();
						contactDetails6.setINETownCode(ineTownCode6);
					}

					// 1.6.1.2.1.5.8 AdditionalContactDetails
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getLegalEntity()
						.getContactDetails().getAdditionalContactDetails())) {
						String additionalContactDetails6 = facturae.getFileHeader().getFactoringAssignmentData()
							.getAssignee().getLegalEntity().getContactDetails().getAdditionalContactDetails();
						contactDetails6.setAdditionalContactDetails(additionalContactDetails6);
					}

					legalEntity.setContactDetails(contactDetails6);
				}

				assignee.setLegalEntity(legalEntity);

			} else if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual() != null) {

				//1.6.1.2.2 Individual
				es.mityc.facturae32.IndividualType individual = new es.mityc.facturae32.IndividualType();

				//1.6.1.2.2.1 Name
				String name = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getName();
				individual.setName(name);

				//1.6.1.2.2.2 FirstSurname	
				String firstSurname = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
					.getIndividual().getFirstSurname();
				individual.setFirstSurname(firstSurname);

				//1.6.1.2.2.3 SecondSurname
				if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getSecondSurname())) {
					String secondSurname = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getSecondSurname();
					individual.setSecondSurname(secondSurname);
				}

				//1.6.1.2.2.4	

				// if 6
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getAddressInSpain() != null) {

					//1.6.1.2.2.4.1 AddressInSpain	
					es.mityc.facturae32.AddressType addressInSpain6 = new es.mityc.facturae32.AddressType();

					//1.6.1.2.2.4.1.1 Address	
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getAddressInSpain().getAddress();
					addressInSpain6.setAddress(address6);

					//1.6.1.2.2.4.1.2 PostCode	
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getAddressInSpain().getPostCode();
					addressInSpain6.setPostCode(postCode6);

					//1.6.1.2.2.4.1.3 Town	
					String town6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getAddressInSpain().getTown();
					addressInSpain6.setTown(town6);

					//1.6.1.2.2.4.1.4 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getAddressInSpain().getProvince();
					addressInSpain6.setProvince(province6);

					//1.6.1.2.2.4.1.5 CountryCode
					es.mityc.facturae32.CountryType countryCode6 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getFileHeader()
							.getFactoringAssignmentData().getAssignee().getIndividual().getAddressInSpain()))
							.getCountryCode().value());
					addressInSpain6.setCountryCode(countryCode6);

					individual.setAddressInSpain(addressInSpain6);

				} else if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getOverseasAddress() != null) {

					// 1.6.1.2.2.4.2 OverseasAddress
					es.mityc.facturae32.OverseasAddressType overseasAddress6 = new es.mityc.facturae32.OverseasAddressType();

					// 1.6.1.2.2.4.2.1 Address
					String address6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getOverseasAddress().getAddress();
					overseasAddress6.setAddress(address6);

					// 1.6.1.2.2.4.2.2 PostCodeAndTown
					String postCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getOverseasAddress().getPostCodeAndTown();
					overseasAddress6.setPostCodeAndTown(postCode6);

					// 1.6.1.2.2.4.2.3 Province
					String province6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
						.getIndividual().getOverseasAddress().getProvince();
					overseasAddress6.setProvince(province6);

					// 1.6.1.2.2.4.2.4 CountryCode	
					es.mityc.facturae32.CountryType countryCode6 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getFileHeader()
							.getFactoringAssignmentData().getAssignee().getIndividual().getOverseasAddress()))
							.getCountryCode().value());
					overseasAddress6.setCountryCode(countryCode6);

					individual.setOverseasAddress(overseasAddress6);

				} // if 6

				// 1.6.1.2.2.5 ContactDetails
				if (facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
					.getContactDetails() != null) {
					es.mityc.facturae32.ContactDetailsType contactDetails6 = new es.mityc.facturae32.ContactDetailsType();

					// 1.6.1.2.2.5.1 Telephone
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getTelephone())) {
						String telephone6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getIndividual().getContactDetails().getTelephone();
						contactDetails6.setTelephone(telephone6);
					}

					// 1.6.1.2.2.5.2 TeleFax
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getTeleFax())) {
						String teleFax6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getIndividual().getContactDetails().getTeleFax();
						contactDetails6.setTeleFax(teleFax6);
					}

					// 1.6.1.2.2.5.3 WebAddress	
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getWebAddress())) {
						String webAddress6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getIndividual().getContactDetails().getWebAddress();
						contactDetails6.setWebAddress(webAddress6);
					}

					// 1.6.1.2.2.5.4 ElectronicMail
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getElectronicMail())) {
						String electronicMail6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getIndividual().getContactDetails().getElectronicMail();
						contactDetails6.setElectronicMail(electronicMail6);
					}

					// 1.6.1.2.2.5.5 ContactPersons	
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getContactPersons())) {
						String contactPersons6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getIndividual().getContactDetails().getContactPersons();
						contactDetails6.setContactPersons(contactPersons6);
					}

					// 1.6.1.2.2.5.6 CnoCnae	
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getCnoCnae())) {
						String cnoCnae6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getIndividual().getContactDetails().getCnoCnae();
						contactDetails6.setCnoCnae(cnoCnae6);
					}

					// 1.6.1.2.2.5.7 INETownCode
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getIneTownCode())) {
						String ineTownCode6 = facturae.getFileHeader().getFactoringAssignmentData().getAssignee()
							.getIndividual().getContactDetails().getIneTownCode();
						contactDetails6.setINETownCode(ineTownCode6);
					}

					// 1.6.1.2.2.5.8 AdditionalContactDetails
					if (!"".equals(facturae.getFileHeader().getFactoringAssignmentData().getAssignee().getIndividual()
						.getContactDetails().getAdditionalContactDetails())) {
						String additionalContactDetails6 = facturae.getFileHeader().getFactoringAssignmentData()
							.getAssignee().getIndividual().getContactDetails().getAdditionalContactDetails();
						contactDetails6.setAdditionalContactDetails(additionalContactDetails6);
					}

					individual.setContactDetails(contactDetails6);
				}

				assignee.setIndividual(individual);

			} //if 4

			factoringAssignmentData.setAssignee(assignee);

			// 1.6.2 PaymentDetails
			es.mityc.facturae32.InstallmentsType paymentDetails = new es.mityc.facturae32.InstallmentsType();

			// 1.6.2.1 Installment	
			es.mityc.facturae32.InstallmentType installment = null;

			// for 14
			int paymentsCount = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
				.getFactoringAssignmentData()).getPaymentDetails().getInstallment().size();
			for (int j = 0; j < paymentsCount; j++) {

				installment = new es.mityc.facturae32.InstallmentType();

				// 1.6.2.1.1 InstallmentDueDate
				XMLGregorianCalendar installmentDueDate = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
					.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
					.getInstallmentDueDate();
				installment.setInstallmentDueDate(installmentDueDate);

				// 1.6.2.1.2 InstallmentAmount
				double installmentAmount = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
					.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
					.getInstallmentAmount();
				installment.setInstallmentAmount(installmentAmount);

				// 1.6.2.1.3 PaymentMeans	
				String paymentMeans = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
					.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
					.getPaymentMeans();
				installment.setPaymentMeans(paymentMeans);

				// 1.6.2.1.4 AccountToBeCredited
				if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
					.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j).getAccountToBeCredited() != null
					&& ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited().getIban() != null
					&& !"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited().getIban())) {
					es.mityc.facturae32.AccountType accountToBeCredited = new es.mityc.facturae32.AccountType();

					// 1.6.2.1.4.1

					// 1.6.2.1.4.1.1 IBAN
					if (!"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited().getIban())) {
						String iban3 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getIban();
						accountToBeCredited.setIBAN(iban3);
					}

					// 1.6.2.1.4.1.2 AccountNumber
					if (!""
						.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited()).getAccountNumber())) {
						String accountNumber3 = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited()).getAccountNumber();
						accountToBeCredited.setAccountNumber(accountNumber3);
					}

					// 1.6.2.1.4.2 BankCode
					if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited().getBankCode() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBankCode())) {
						String bankCode = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBankCode();
						accountToBeCredited.setBankCode(bankCode);
					}

					// 1.6.2.1.4.3 BranchCode
					if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited().getBranchCode() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBranchCode())) {
						String branchCode = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBranchCode();
						accountToBeCredited.setBranchCode(branchCode);
					}

					// 1.6.2.1.4.4

					// if 15
					if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited().getBranchInSpainAddress() != null) {

						// 1.6.2.1.4.4.1 BranchInSpainAddress
						es.mityc.facturae32.AddressType branchInSpainAddress = new es.mityc.facturae32.AddressType();

						// 1.6.2.1.4.4.1.1 Address
						String address7 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBranchInSpainAddress().getAddress();
						branchInSpainAddress.setAddress(address7);

						// 1.6.2.1.4.4.1.2 PostCode
						String postCode8 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBranchInSpainAddress().getPostCode();
						branchInSpainAddress.setPostCode(postCode8);

						// 1.6.2.1.4.4.1.3 Town
						String town7 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBranchInSpainAddress().getTown();
						branchInSpainAddress.setTown(town7);

						// 1.6.2.1.4.4.1.4 Province
						String province7 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getBranchInSpainAddress().getProvince();
						branchInSpainAddress.setProvince(province7);

						// 1.6.2.1.4.4.1.5 CountryCode
						es.mityc.facturae32.CountryType countryCode7 = es.mityc.facturae32.CountryType
							.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
								.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment()
								.get(j).getAccountToBeCredited().getBranchInSpainAddress())).getCountryCode().value());
						branchInSpainAddress.setCountryCode(countryCode7);

						accountToBeCredited.setBranchInSpainAddress(branchInSpainAddress);

					} else if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeCredited().getOverseasBranchAddress() != null) {

						// 1.6.2.1.4.4.2 OverseasBranchAddress
						es.mityc.facturae32.OverseasAddressType overseasBranchAddress = new es.mityc.facturae32.OverseasAddressType();

						// 1.6.2.1.4.4.2.1 Address
						String address7 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getOverseasBranchAddress().getAddress();
						overseasBranchAddress.setAddress(address7);

						// 1.6.2.1.4.4.2.2 PostCodeAndTown
						String postCodeAndTown8 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getOverseasBranchAddress().getPostCodeAndTown();
						overseasBranchAddress.setPostCodeAndTown(postCodeAndTown8);

						// 1.6.2.1.4.4.2.3 Province
						String province7 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited().getOverseasBranchAddress().getProvince();
						overseasBranchAddress.setProvince(province7);

						// 1.6.2.1.4.4.2.4 CountryCode
						es.mityc.facturae32.CountryType countryCode7 = es.mityc.facturae32.CountryType
							.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
								.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment()
								.get(j).getAccountToBeCredited().getOverseasBranchAddress())).getCountryCode().value());
						overseasBranchAddress.setCountryCode(countryCode7);

						accountToBeCredited.setOverseasBranchAddress(overseasBranchAddress);

					} // if 15

					// 1.6.2.1.4.5 BIC
					if (!""
						.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited()).getBic())) {
						String BIC = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeCredited()).getBic();
						accountToBeCredited.setBIC(BIC);
					}

					installment.setAccountToBeCredited(accountToBeCredited);
				}

				// 1.6.2.1.5 PaymentReconciliationReference
				if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
					.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
					.getPaymentReconciliationReference() != null
					&& !"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getPaymentReconciliationReference())) {
					String paymentReconciliationReference = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getPaymentReconciliationReference();
					installment.setPaymentReconciliationReference(paymentReconciliationReference);
				}

				// 1.6.2.1.6 AccountToBeDebited
				if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
					.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j).getAccountToBeDebited() != null
					&& ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited().getIban() != null
					&& !"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited().getIban())) {
					es.mityc.facturae32.AccountType accountToBeDebited = new es.mityc.facturae32.AccountType();

					// 1.6.2.1.6.1

					// 1.6.2.1.6.1.1 IBAN
					if (!"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited().getIban())) {
						String iban3 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getIban();
						accountToBeDebited.setIBAN(iban3);
					}

					// 1.6.2.1.6.1.2 AccountNumber
					if (!""
						.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited()).getAccountNumber())) {
						String accountNumber3 = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited()).getAccountNumber();
						accountToBeDebited.setAccountNumber(accountNumber3);
					}

					// 1.6.2.1.6.2 BankCode
					if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited().getBankCode() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBankCode())) {
						String bankCode2 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBankCode();
						accountToBeDebited.setBankCode(bankCode2);
					}

					// 1.6.2.1.6.3 BranchCode
					if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited().getBranchCode() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBranchCode())) {
						String branchCode2 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBranchCode();
						accountToBeDebited.setBranchCode(branchCode2);
					}

					// 1.6.2.1.6.4 

					// if 16
					if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited().getBranchInSpainAddress() != null) {

						// 1.6.2.1.6.4.1 BranchInSpainAddress
						es.mityc.facturae32.AddressType branchInSpainAddress2 = new es.mityc.facturae32.AddressType();

						// 1.6.2.1.6.4.1.1 Address
						String address8 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBranchInSpainAddress().getAddress();
						branchInSpainAddress2.setAddress(address8);

						// 1.6.2.1.6.4.1.2 PostCode
						String postCode9 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBranchInSpainAddress().getPostCode();
						branchInSpainAddress2.setPostCode(postCode9);

						// 1.6.2.1.6.4.1.3 Town
						String town8 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBranchInSpainAddress().getTown();
						branchInSpainAddress2.setTown(town8);

						// 1.6.2.1.6.4.1.4 Province
						String province8 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getBranchInSpainAddress().getProvince();
						branchInSpainAddress2.setProvince(province8);

						// 1.6.2.1.6.4.1.5 CountryCode
						es.mityc.facturae32.CountryType countryCode8 = es.mityc.facturae32.CountryType
							.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
								.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment()
								.get(j).getAccountToBeDebited().getBranchInSpainAddress())).getCountryCode().value());
						branchInSpainAddress2.setCountryCode(countryCode8);

						accountToBeDebited.setBranchInSpainAddress(branchInSpainAddress2);

					} else if (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
						.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getAccountToBeDebited().getOverseasBranchAddress() != null) {

						// 1.6.2.1.6.4.2 OverseasBranchAddress
						es.mityc.facturae32.OverseasAddressType overseasBranchAddress2 = new es.mityc.facturae32.OverseasAddressType();

						// 1.6.2.1.6.4.2.1 Address
						String address8 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getOverseasBranchAddress().getAddress();
						overseasBranchAddress2.setAddress(address8);

						// 1.6.2.1.6.4.2.2 PostCodeAndTown
						String postCodeAndTown9 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getOverseasBranchAddress().getPostCodeAndTown();
						overseasBranchAddress2.setPostCodeAndTown(postCodeAndTown9);

						// 1.6.2.1.6.4.2.3 Province
						String province8 = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited().getOverseasBranchAddress().getProvince();
						overseasBranchAddress2.setProvince(province8);

						// 1.6.2.1.6.4.2.4 CountryCode
						es.mityc.facturae32.CountryType countryCode8 = es.mityc.facturae32.CountryType
							.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
								.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment()
								.get(j).getAccountToBeDebited().getOverseasBranchAddress())).getCountryCode().value());
						overseasBranchAddress2.setCountryCode(countryCode8);

						accountToBeDebited.setOverseasBranchAddress(overseasBranchAddress2);

					} // if 16

					// 1.6.2.1.6.5 BIC
					if (!""
						.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited()).getBic())) {
						String BIC = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
							.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
							.getAccountToBeDebited()).getBic();
						accountToBeDebited.setBIC(BIC);
					}

					installment.setAccountToBeDebited(accountToBeDebited);
				}

				// 1.6.2.1.7 CollectionAdditionalInformation
				if (!"".equals(((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae.getFileHeader()
					.getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
					.getCollectionAdditionalInformation())) {
					String collectionAdditionalInformation = ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j)
						.getCollectionAdditionalInformation();
					installment.setCollectionAdditionalInformation(collectionAdditionalInformation);
				}

				// 1.6.2.1.8 RegulatoryReportingData
				if (!""
					.equals(((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j))
						.getRegulatoryReportingData())) {
					String regulatoryReportingData = ((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j))
						.getRegulatoryReportingData();
					installment.setRegulatoryReportingData(regulatoryReportingData);
				}

				// 1.6.2.1.9 DebitReconciliationReference
				if (!""
					.equals(((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j))
						.getDebitReconciliationReference())) {
					String debitReconciliationReference = ((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.FactoringAssignmentDataType) facturae
						.getFileHeader().getFactoringAssignmentData()).getPaymentDetails().getInstallment().get(j))
						.getDebitReconciliationReference();
					installment.setDebitReconciliationReference(debitReconciliationReference);
				}

				paymentDetails.getInstallment().add(installment);

			} // for 14
			factoringAssignmentData.setPaymentDetails(paymentDetails);

			// 1.6.3 FactoringAssignmentClauses
			String factoringAssignmentClauses = facturae.getFileHeader().getFactoringAssignmentData()
				.getFactoringAssignmentClauses();
			factoringAssignmentData.setFactoringAssignmentClauses(factoringAssignmentClauses);

			fileHeader.setFactoringAssignmentData(factoringAssignmentData);

		}

		invoice32.setFileHeader(fileHeader);

		// 2 PartiesType
		es.mityc.facturae32.PartiesType parties = new es.mityc.facturae32.PartiesType();

		// 2.1 SellerParty
		es.mityc.facturae32.BusinessType sellerParty = new es.mityc.facturae32.BusinessType();

		// 2.1.1 TaxIdentification
		es.mityc.facturae32.TaxIdentificationType taxIdentification = new es.mityc.facturae32.TaxIdentificationType();

		// 2.1.1.1 PersonTypeCode
		es.mityc.facturae32.PersonTypeCodeType personTypeCode = es.mityc.facturae32.PersonTypeCodeType
			.fromValue(facturae.getParties().getSellerParty().getTaxIdentification().getPersonTypeCode().value());
		taxIdentification.setPersonTypeCode(personTypeCode);

		// 2.1.1.2 ResidenceTypeCode
		es.mityc.facturae32.ResidenceTypeCodeType residenceTypeCode = es.mityc.facturae32.ResidenceTypeCodeType
			.fromValue(facturae.getParties().getSellerParty().getTaxIdentification().getResidenceTypeCode().value());
		taxIdentification.setResidenceTypeCode(residenceTypeCode);

		// 2.1.1.3 TaxIdentificationNumber
		String taxIdentificationNumber = facturae.getParties().getSellerParty().getTaxIdentification()
			.getTaxIdentificationNumber();
		taxIdentification.setTaxIdentificationNumber(taxIdentificationNumber);

		sellerParty.setTaxIdentification(taxIdentification);

		// 2.1.2 PartyIdentification
		if (!"".equals(facturae.getParties().getSellerParty().getPartyIdentification())) {
			String partyIdentification = facturae.getParties().getSellerParty().getPartyIdentification();
			sellerParty.setPartyIdentification(partyIdentification);
		}

		// 2.1.3 AdministrativeCentres
		if (facturae.getParties().getSellerParty().getAdministrativeCentres() != null
			&& facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre() != null
			&& facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre().size() > 0) {
			es.mityc.facturae32.AdministrativeCentresType administrativeCentres = new es.mityc.facturae32.AdministrativeCentresType();

			// 2.1.3.1 AdministrativeCentre
			es.mityc.facturae32.AdministrativeCentreType administrativeCentre = null;

			int adminCentreCount = facturae.getParties().getSellerParty().getAdministrativeCentres()
				.getAdministrativeCentre().size();
			for (int i = 0; i < adminCentreCount; i++) {

				administrativeCentre = new es.mityc.facturae32.AdministrativeCentreType();

				// 2.1.3.1.1 CentreCode
				if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getCentreCode())) {
					String centreCode = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getCentreCode();
					administrativeCentre.setCentreCode(centreCode);
				}

				// 2.1.3.1.2 RoleTypeCode
				if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getRoleTypeCode())) {
					String roleTypeCode = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getRoleTypeCode();
					administrativeCentre.setRoleTypeCode(roleTypeCode);
				}

				// 2.1.3.1.3 Name
				if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getName())) {
					String name3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getName();
					administrativeCentre.setName(name3);
				}

				// 2.1.3.1.4 FirstSurname
				if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getFirstSurname())) {
					String firstSurname3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getFirstSurname();
					administrativeCentre.setFirstSurname(firstSurname3);
				}

				// 2.1.3.1.5 SecondSurname
				if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getSecondSurname())) {
					String secondSurname3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getSecondSurname();
					administrativeCentre.setSecondSurname(secondSurname3);
				}

				// 2.1.3.1.6 

				// if 7
				if (facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getAddressInSpain() != null) {

					// 2.1.3.1.6.1 AddressInSpain		
					es.mityc.facturae32.AddressType addressInSpain3 = new es.mityc.facturae32.AddressType();

					// 2.1.3.1.6.1.1 Address
					String address3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getAddress();
					addressInSpain3.setAddress(address3);

					// 2.1.3.1.6.1.2 PostCode
					String postCode3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getPostCode();
					addressInSpain3.setPostCode(postCode3);

					// 2.1.3.1.6.1.3 Town
					String town3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getTown();
					addressInSpain3.setTown(town3);

					// 2.1.3.1.6.1.4 Province
					String province3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getProvince();
					addressInSpain3.setProvince(province3);

					// 2.1.3.1.6.1.5 CountryCode
					es.mityc.facturae32.CountryType countryCode3 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getParties()
							.getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
							.getAddressInSpain())).getCountryCode().value());
					addressInSpain3.setCountryCode(countryCode3);

					administrativeCentre.setAddressInSpain(addressInSpain3);

				} else if (facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre()
					.get(i).getOverseasAddress() != null) {

					// 2.1.3.1.6.2 OverSeasAddress
					es.mityc.facturae32.OverseasAddressType overseasAddress3 = new es.mityc.facturae32.OverseasAddressType();

					// 2.1.3.1.6.2.1 Address
					String address3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getAddress();
					overseasAddress3.setAddress(address3);

					// 2.1.3.1.6.2.2 PostCodeAndTown
					String postCodeandTown3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getPostCodeAndTown();
					overseasAddress3.setPostCodeAndTown(postCodeandTown3);

					// 2.1.3.1.6.2.3 Province
					String province3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getProvince();
					overseasAddress3.setProvince(province3);

					// 2.1.3.1.6.2.4 CountryCode
					es.mityc.facturae32.CountryType countryCode3 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getParties()
							.getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
							.getOverseasAddress())).getCountryCode().value());
					overseasAddress3.setCountryCode(countryCode3);

					administrativeCentre.setOverseasAddress(overseasAddress3);

				} // if 7

				// 2.1.3.1.7 ContactDetails
				if (facturae.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getContactDetails() != null) {
					es.mityc.facturae32.ContactDetailsType contactDetails3 = new es.mityc.facturae32.ContactDetailsType();

					// 2.1.3.1.7.1 Telephone
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTelephone())) {
						String telephone3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getTelephone();
						contactDetails3.setTelephone(telephone3);
					}

					// 2.1.3.1.7.2 TeleFax
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTeleFax())) {
						String teleFax3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getTeleFax();
						contactDetails3.setTeleFax(teleFax3);
					}

					// 2.1.3.1.7.3 WebAddress
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getWebAddress())) {
						String webAddress3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getWebAddress();
						contactDetails3.setWebAddress(webAddress3);
					}

					// 2.1.3.1.7.4 ElectronicMail
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getElectronicMail())) {
						String electronicMail3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getElectronicMail();
						contactDetails3.setElectronicMail(electronicMail3);
					}

					// 2.1.3.1.7.5 ContactPersons
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getContactPersons())) {
						String contactPersons3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getContactPersons();
						contactDetails3.setContactPersons(contactPersons3);
					}

					// 2.1.3.1.7.6 CnoCnae
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getCnoCnae())) {
						String cnoCnae3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getCnoCnae();
						contactDetails3.setCnoCnae(cnoCnae3);
					}

					// 2.1.3.1.7.7 INETownCode
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getIneTownCode())) {
						String ineTownCode3 = facturae.getParties().getSellerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getIneTownCode();
						contactDetails3.setINETownCode(ineTownCode3);
					}

					// 2.1.3.1.7.8 AdditionalContactDetails
					if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getAdditionalContactDetails())) {
						String additionalContactDetails3 = facturae.getParties().getSellerParty()
							.getAdministrativeCentres().getAdministrativeCentre().get(i).getContactDetails()
							.getAdditionalContactDetails();
						contactDetails3.setAdditionalContactDetails(additionalContactDetails3);
					}

					administrativeCentre.setContactDetails(contactDetails3);

				}

				// 2.1.3.1.8 PhysicalGLN
				if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getPhysicalGLN())) {
					String physicalGLN = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getPhysicalGLN();
					administrativeCentre.setPhysicalGLN(physicalGLN);
				}

				// 2.1.3.1.9 LogicalOperationPoint
				if (!"".equals(facturae.getParties().getSellerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getLogicalOperationalPoint())) {
					String logicalOperationalPoint = facturae.getParties().getSellerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getLogicalOperationalPoint();
					administrativeCentre.setLogicalOperationalPoint(logicalOperationalPoint);
				}

				// 2.1.3.1.10 CentreDescription
				if (!"".equals(((es.mityc.appfacturae.facturae32.AdministrativeCentreType) facturae.getParties()
					.getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i))
					.getCentreDescription())) {
					String centreDescription = ((es.mityc.appfacturae.facturae32.AdministrativeCentreType) facturae
						.getParties().getSellerParty().getAdministrativeCentres().getAdministrativeCentre().get(i))
						.getCentreDescription();
					administrativeCentre.setCentreDescription(centreDescription);
				}

				administrativeCentres.getAdministrativeCentre().add(administrativeCentre);

			} //for

			sellerParty.setAdministrativeCentres(administrativeCentres);

		}

		// if 8
		if (facturae.getParties().getSellerParty().getLegalEntity() != null) {

			// 2.1.4.1 LegalEntity
			es.mityc.facturae32.LegalEntityType legalEntity = new es.mityc.facturae32.LegalEntityType();

			// 2.1.4.1.1 CorporateName
			String corporateName = facturae.getParties().getSellerParty().getLegalEntity().getCorporateName();
			legalEntity.setCorporateName(corporateName);

			// 2.1.4.1.2 TradeName

			if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getTradeName())) {
				String tradeName = facturae.getParties().getSellerParty().getLegalEntity().getTradeName();
				legalEntity.setTradeName(tradeName);
			}

			// 2.1.4.1.3 RegistrationData
			if (facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData() != null) {
				es.mityc.facturae32.RegistrationDataType registrationData = new es.mityc.facturae32.RegistrationDataType();

				// 2.1.4.1.3.1 Book
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getBook())) {
					String book = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
						.getBook();
					registrationData.setBook(book);
				}

				// 2.1.4.1.3.2 RegisterOfCompaniesLocation
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
					.getRegisterOfCompaniesLocation())) {
					String registerOfCompaniesLocation = facturae.getParties().getSellerParty().getLegalEntity()
						.getRegistrationData().getRegisterOfCompaniesLocation();
					registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);
				}

				// 2.1.4.1.3.3 Sheet
				if (!""
					.equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getSheet())) {
					String sheet = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
						.getSheet();
					registrationData.setSheet(sheet);
				}

				// 2.1.4.1.3.4 Folio
				if (!""
					.equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData().getFolio())) {
					String folio = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
						.getFolio();
					registrationData.setFolio(folio);
				}

				// 2.1.4.1.3.5 Section
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
					.getSection())) {
					String section = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
						.getSection();
					registrationData.setSection(section);
				}

				// 2.1.4.1.3.6 Volume
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
					.getVolume())) {
					String volume = facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
						.getVolume();
					registrationData.setVolume(volume);
				}

				// 2.1.4.1.3.7 AdditionalRegistrationData
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getRegistrationData()
					.getAdditionalRegistrationData())) {
					String additionalRegistrationData = facturae.getParties().getSellerParty().getLegalEntity()
						.getRegistrationData().getAdditionalRegistrationData();
					registrationData.setAdditionalRegistrationData(additionalRegistrationData);
				}

				legalEntity.setRegistrationData(registrationData);
			}

			//2.1.4.1.4

			// if 9
			if (facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain() != null) {

				//2.1.4.1.4.1 AddressInSpain
				es.mityc.facturae32.AddressType addressInSpain = new es.mityc.facturae32.AddressType();

				// 2.1.4.1.4.1.1 Address
				String address = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain()
					.getAddress();
				addressInSpain.setAddress(address);

				// 2.1.4.1.4.1.2 PostCode
				String postCode = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain()
					.getPostCode();
				addressInSpain.setPostCode(postCode);

				// 2.1.4.1.4.1.3 Town
				String town = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain().getTown();
				addressInSpain.setTown(town);

				// 2.1.4.1.4.1.4 Province
				String province = facturae.getParties().getSellerParty().getLegalEntity().getAddressInSpain()
					.getProvince();
				addressInSpain.setProvince(province);

				// 2.1.4.1.4.1.5 CountryCode
				es.mityc.facturae32.CountryType countryCode = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getParties().getSellerParty()
						.getLegalEntity().getAddressInSpain())).getCountryCode().value());
				addressInSpain.setCountryCode(countryCode);

				legalEntity.setAddressInSpain(addressInSpain);

			} else if (facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress() != null) {

				// 2.1.4.1.4.2 OverseasAddress
				es.mityc.facturae32.OverseasAddressType overseasAddress = new es.mityc.facturae32.OverseasAddressType();
				// 2.1.4.1.4.2.1 Address
				String address = facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress()
					.getAddress();
				overseasAddress.setAddress(address);

				// 2.1.4.1.4.2.2 PostCodeAndTown
				String postCodeandTown = facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress()
					.getPostCodeAndTown();
				overseasAddress.setPostCodeAndTown(postCodeandTown);

				// 2.1.4.1.4.2.3 Province
				String province = facturae.getParties().getSellerParty().getLegalEntity().getOverseasAddress()
					.getProvince();
				overseasAddress.setProvince(province);

				// 2.1.4.1.4.2.4 CountryCode
				es.mityc.facturae32.CountryType countryCode = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getParties()
						.getSellerParty().getLegalEntity().getOverseasAddress())).getCountryCode().value());
				overseasAddress.setCountryCode(countryCode);

				legalEntity.setOverseasAddress(overseasAddress);

			} // if 9

			// 2.1.4.1.5 ContactDetails
			if (facturae.getParties().getSellerParty().getLegalEntity().getContactDetails() != null) {
				es.mityc.facturae32.ContactDetailsType contactDetails = new es.mityc.facturae32.ContactDetailsType();

				// 2.1.4.1.5.1 Telephone
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getTelephone())) {
					String telephone = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getTelephone();
					contactDetails.setTelephone(telephone);
				}

				// 2.1.4.1.5.2 TeleFax
				if (!""
					.equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails().getTeleFax())) {
					String teleFax = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getTeleFax();
					contactDetails.setTeleFax(teleFax);
				}

				// 2.1.4.1.5.3 WebAddress
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getWebAddress())) {
					String webAddress = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getWebAddress();
					contactDetails.setWebAddress(webAddress);
				}

				// 2.1.4.1.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getElectronicMail())) {
					String electronicMail = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getElectronicMail();
					contactDetails.setElectronicMail(electronicMail);
				}

				// 2.1.4.1.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getContactPersons())) {
					String contactPersons = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getContactPersons();
					contactDetails.setContactPersons(contactPersons);
				}

				// 2.1.4.1.5.6 CnoCnae
				if (!""
					.equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails().getCnoCnae())) {
					String cnoCnae = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getCnoCnae();
					contactDetails.setCnoCnae(cnoCnae);
				}

				// 2.1.4.1.5.7 INETownCode
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getIneTownCode())) {
					String ineTownCode = facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
						.getIneTownCode();
					contactDetails.setINETownCode(ineTownCode);
				}

				// 2.1.4.1.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getSellerParty().getLegalEntity().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails = facturae.getParties().getSellerParty().getLegalEntity()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails.setAdditionalContactDetails(additionalContactDetails);
				}

				legalEntity.setContactDetails(contactDetails);
			}

			sellerParty.setLegalEntity(legalEntity);

		} else if (facturae.getParties().getSellerParty().getIndividual() != null) {

			// 2.1.4.2 Individual
			es.mityc.facturae32.IndividualType individual = new es.mityc.facturae32.IndividualType();

			// 2.1.4.2.1 Name
			String name = facturae.getParties().getSellerParty().getIndividual().getName();
			individual.setName(name);

			// 2.1.4.2.2 FirstSurname
			String firstSurname = facturae.getParties().getSellerParty().getIndividual().getFirstSurname();
			individual.setFirstSurname(firstSurname);

			// 2.1.4.2.3 SecondSurname
			if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getSecondSurname())) {
				String secondSurname = facturae.getParties().getSellerParty().getIndividual().getSecondSurname();
				individual.setSecondSurname(secondSurname);
			}

			// 2.1.4.2.4

			// if 10
			if (facturae.getParties().getSellerParty().getIndividual().getAddressInSpain() != null) {

				// 2.1.4.2.4.1 AddressInSpain
				es.mityc.facturae32.AddressType addressInSpain = new es.mityc.facturae32.AddressType();

				// 2.1.4.2.4.1.1 Address
				String address = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain()
					.getAddress();
				addressInSpain.setAddress(address);

				// 2.1.4.2.4.1.2 PostCode
				String postCode = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain()
					.getPostCode();
				addressInSpain.setPostCode(postCode);

				// 2.1.4.2.4.1.3 Town
				String town = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain().getTown();
				addressInSpain.setTown(town);

				// 2.1.4.2.4.1.4 Province
				String province = facturae.getParties().getSellerParty().getIndividual().getAddressInSpain()
					.getProvince();
				addressInSpain.setProvince(province);

				// 2.1.4.2.4.1.5 CountryCode
				es.mityc.facturae32.CountryType countryCode = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getParties().getSellerParty()
						.getIndividual().getAddressInSpain())).getCountryCode().value());
				addressInSpain.setCountryCode(countryCode);

				individual.setAddressInSpain(addressInSpain);

			} else if (facturae.getParties().getSellerParty().getIndividual().getOverseasAddress() != null) {

				// 2.1.4.2.4.2 OverseasAddress
				es.mityc.facturae32.OverseasAddressType overseasAddress = new es.mityc.facturae32.OverseasAddressType();

				// 2.1.4.2.4.2.1 Address
				String address = facturae.getParties().getSellerParty().getIndividual().getOverseasAddress()
					.getAddress();
				overseasAddress.setAddress(address);

				// 2.1.4.2.4.2.2 PostCodeAndTown
				String postCodeandTown = facturae.getParties().getSellerParty().getIndividual().getOverseasAddress()
					.getPostCodeAndTown();
				overseasAddress.setPostCodeAndTown(postCodeandTown);

				// 2.1.4.2.4.2.3 Province
				String province = facturae.getParties().getSellerParty().getIndividual().getOverseasAddress()
					.getProvince();
				overseasAddress.setProvince(province);

				// 2.1.4.2.4.2.4 CountryCode
				es.mityc.facturae32.CountryType countryCode = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getParties()
						.getSellerParty().getIndividual().getOverseasAddress())).getCountryCode().value());
				overseasAddress.setCountryCode(countryCode);

				individual.setOverseasAddress(overseasAddress);

			} // if 10

			// 2.1.4.2.5 ContactDetails
			if (facturae.getParties().getSellerParty().getIndividual().getContactDetails() != null) {
				es.mityc.facturae32.ContactDetailsType contactDetails = new es.mityc.facturae32.ContactDetailsType();

				// 2.1.4.2.5.1 Telephone
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getTelephone())) {
					String telephone = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getTelephone();
					contactDetails.setTelephone(telephone);
				}

				// 2.1.4.2.5.2 TeleFax
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails().getTeleFax())) {
					String teleFax = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getTeleFax();
					contactDetails.setTeleFax(teleFax);
				}

				// 2.1.4.2.5.3 WebAddress
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getWebAddress())) {
					String webAddress = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getWebAddress();
					contactDetails.setWebAddress(webAddress);
				}

				// 2.1.4.2.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getElectronicMail())) {
					String electronicMail = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getElectronicMail();
					contactDetails.setElectronicMail(electronicMail);
				}

				// 2.1.4.2.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getContactPersons())) {
					String contactPersons = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getContactPersons();
					contactDetails.setContactPersons(contactPersons);
				}

				// 2.1.4.2.5.6 CnoCnae
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails().getCnoCnae())) {
					String cnoCnae = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getCnoCnae();
					contactDetails.setCnoCnae(cnoCnae);
				}

				// 2.1.4.2.5.7 INETownCode
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getIneTownCode())) {
					String ineTownCode = facturae.getParties().getSellerParty().getIndividual().getContactDetails()
						.getIneTownCode();
					contactDetails.setINETownCode(ineTownCode);
				}

				// 2.1.4.2.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getSellerParty().getIndividual().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails = facturae.getParties().getSellerParty().getIndividual()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails.setAdditionalContactDetails(additionalContactDetails);
				}

				individual.setContactDetails(contactDetails);
			}

			sellerParty.setIndividual(individual);

		} // if 8

		parties.setSellerParty(sellerParty);

		// 2.2 BuyerParty 	
		es.mityc.facturae32.BusinessType buyerParty = new es.mityc.facturae32.BusinessType();

		// 2.2.1 TaxIdentification
		es.mityc.facturae32.TaxIdentificationType taxIdentification2 = new es.mityc.facturae32.TaxIdentificationType();

		// 2.2.1.1 PersonTypeCode
		es.mityc.facturae32.PersonTypeCodeType personTypeCode2 = es.mityc.facturae32.PersonTypeCodeType
			.fromValue(facturae.getParties().getBuyerParty().getTaxIdentification().getPersonTypeCode().value());
		taxIdentification2.setPersonTypeCode(personTypeCode2);

		// 2.2.1.2 ResidenceTypeCode
		es.mityc.facturae32.ResidenceTypeCodeType residenceTypeCode2 = es.mityc.facturae32.ResidenceTypeCodeType
			.fromValue(facturae.getParties().getBuyerParty().getTaxIdentification().getResidenceTypeCode().value());
		taxIdentification2.setResidenceTypeCode(residenceTypeCode2);

		// 2.2.1.3 TaxIdentificationNumber
		String taxIdentificationNumber2 = facturae.getParties().getBuyerParty().getTaxIdentification()
			.getTaxIdentificationNumber();
		taxIdentification2.setTaxIdentificationNumber(taxIdentificationNumber2);

		buyerParty.setTaxIdentification(taxIdentification2);

		// 2.2.2 PartyIdentification
		if (!"".equals(facturae.getParties().getBuyerParty().getPartyIdentification())) {
			String partyIdentification2 = facturae.getParties().getBuyerParty().getPartyIdentification();
			buyerParty.setPartyIdentification(partyIdentification2);
		}

		// 2.2.3 AdministrativeCentres
		if (facturae.getParties().getBuyerParty().getAdministrativeCentres() != null
			&& facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre() != null
			&& facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().size() > 0) {
			es.mityc.facturae32.AdministrativeCentresType administrativeCentres2 = new es.mityc.facturae32.AdministrativeCentresType();

			// 2.2.3.1 AdministrativeCentre
			es.mityc.facturae32.AdministrativeCentreType administrativeCentre2 = null;

			int adminCentreCount = facturae.getParties().getBuyerParty().getAdministrativeCentres()
				.getAdministrativeCentre().size();
			for (int i = 0; i < adminCentreCount; i++) {

				administrativeCentre2 = new es.mityc.facturae32.AdministrativeCentreType();

				// 2.2.3.1.1 CentreCode	 
				if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getCentreCode())) {
					String centreCode2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getCentreCode();
					administrativeCentre2.setCentreCode(centreCode2);
				}

				// 2.2.3.1.2 RoleTypeCode 
				if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getRoleTypeCode())) {
					String roleTypeCode2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getRoleTypeCode();
					administrativeCentre2.setRoleTypeCode(roleTypeCode2);
				}

				// 2.2.3.1.3 Name	
				if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getName())) {
					String name4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getName();
					administrativeCentre2.setName(name4);
				}

				// 2.2.3.1.4 FirstSurname  
				if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getFirstSurname())) {
					String firstSurname4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getFirstSurname();
					administrativeCentre2.setFirstSurname(firstSurname4);
				}

				// 2.2.3.1.5 SecondSurname	
				if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getSecondSurname())) {
					String secondSurname4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getSecondSurname();
					administrativeCentre2.setSecondSurname(secondSurname4);
				}
				// 2.2.3.1.6 

				// if 11
				if (facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getAddressInSpain() != null) {

					// 2.2.3.1.6.1 AddressInSpain
					es.mityc.facturae32.AddressType addressInSpain4 = new es.mityc.facturae32.AddressType();

					// 2.2.3.1.6.1.1 Address
					String address4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getAddress();
					addressInSpain4.setAddress(address4);

					// 2.2.3.1.6.1.2 PostCode
					String postCode4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getPostCode();
					addressInSpain4.setPostCode(postCode4);

					// 2.2.3.1.6.1.3 Town
					String town4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getTown();
					addressInSpain4.setTown(town4);

					// 2.2.3.1.6.1.4 Province
					String province4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getAddressInSpain().getProvince();
					addressInSpain4.setProvince(province4);

					// 2.2.3.1.6.1.5 CountryCode
					es.mityc.facturae32.CountryType countryCode4 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getParties()
							.getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
							.getAddressInSpain())).getCountryCode().value());
					addressInSpain4.setCountryCode(countryCode4);

					administrativeCentre2.setAddressInSpain(addressInSpain4);

				} else if (facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre()
					.get(i).getOverseasAddress() != null) {

					// 2.2.3.1.6.2 OverSeasAddress
					es.mityc.facturae32.OverseasAddressType overseasAddress4 = new es.mityc.facturae32.OverseasAddressType();

					// 2.2.3.1.6.2.1 Address
					String address4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getAddress();
					overseasAddress4.setAddress(address4);

					// 2.2.3.1.6.2.2 PostCodeAndTown
					String postCodeAndTown4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getPostCodeAndTown();
					overseasAddress4.setPostCodeAndTown(postCodeAndTown4);

					// 2.2.3.1.6.2.3 Province
					String province4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getOverseasAddress().getProvince();
					overseasAddress4.setProvince(province4);

					// 2.2.3.1.6.2.4 CountryCode
					es.mityc.facturae32.CountryType countryCode4 = es.mityc.facturae32.CountryType
						.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getParties()
							.getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
							.getOverseasAddress())).getCountryCode().value());
					overseasAddress4.setCountryCode(countryCode4);

					administrativeCentre2.setOverseasAddress(overseasAddress4);

				} // if 11

				// 2.2.3.1.7 ContactDetails
				if (facturae.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i)
					.getContactDetails() != null) {
					es.mityc.facturae32.ContactDetailsType contactDetails4 = new es.mityc.facturae32.ContactDetailsType();

					// 2.2.3.1.7.1 Telephone
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTelephone())) {
						String telephone4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getTelephone();
						contactDetails4.setTelephone(telephone4);
					}
					// 2.2.3.1.7.2 TeleFax
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getTeleFax())) {
						String teleFax4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getTeleFax();
						contactDetails4.setTeleFax(teleFax4);
					}

					// 2.2.3.1.7.3 WebAddress
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getWebAddress())) {
						String webAddress4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getWebAddress();
						contactDetails4.setWebAddress(webAddress4);
					}

					// 2.2.3.1.7.4 ElectronicMail
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getElectronicMail())) {
						String electronicMail4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getElectronicMail();
						contactDetails4.setElectronicMail(electronicMail4);
					}

					// 2.2.3.1.7.5 ContactPersons
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getContactPersons())) {
						String contactPersons4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getContactPersons();
						contactDetails4.setContactPersons(contactPersons4);
					}

					// 2.2.3.1.7.6 CnoCnae
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getCnoCnae())) {
						String cnoCnae4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getCnoCnae();
						contactDetails4.setCnoCnae(cnoCnae4);
					}

					// 2.2.3.1.7.7 INETownCode
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getIneTownCode())) {
						String ineTownCode4 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
							.getAdministrativeCentre().get(i).getContactDetails().getIneTownCode();
						contactDetails4.setINETownCode(ineTownCode4);
					}

					// 2.2.3.1.7.8 AdditionalContactDetails
					if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getContactDetails().getAdditionalContactDetails())) {
						String additionalContactDetails4 = facturae.getParties().getBuyerParty()
							.getAdministrativeCentres().getAdministrativeCentre().get(i).getContactDetails()
							.getAdditionalContactDetails();
						contactDetails4.setAdditionalContactDetails(additionalContactDetails4);
					}

					administrativeCentre2.setContactDetails(contactDetails4);
				}

				// 2.2.3.1.8 PhysicalGLN
				if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getPhysicalGLN())) {
					String physicalGLN2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getPhysicalGLN();
					administrativeCentre2.setPhysicalGLN(physicalGLN2);
				}

				// 2.2.3.1.9 LogicalOperationPoint
				if (!"".equals(facturae.getParties().getBuyerParty().getAdministrativeCentres()
					.getAdministrativeCentre().get(i).getLogicalOperationalPoint())) {
					String logicalOperationalPoint2 = facturae.getParties().getBuyerParty().getAdministrativeCentres()
						.getAdministrativeCentre().get(i).getLogicalOperationalPoint();
					administrativeCentre2.setLogicalOperationalPoint(logicalOperationalPoint2);
				}

				// 2.2.3.1.10 CentreDescription
				if (!"".equals(((es.mityc.appfacturae.facturae32.AdministrativeCentreType) facturae.getParties()
					.getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i))
					.getCentreDescription())) {
					String centreDescription = ((es.mityc.appfacturae.facturae32.AdministrativeCentreType) facturae
						.getParties().getBuyerParty().getAdministrativeCentres().getAdministrativeCentre().get(i))
						.getCentreDescription();
					administrativeCentre2.setCentreDescription(centreDescription);
				}

				administrativeCentres2.getAdministrativeCentre().add(administrativeCentre2);

			} //for

			buyerParty.setAdministrativeCentres(administrativeCentres2);
		}

		// 2.2.4 

		// if 12
		if (facturae.getParties().getBuyerParty().getLegalEntity() != null) {

			// 2.2.4.1 LegalEntity
			es.mityc.facturae32.LegalEntityType legalEntity = new es.mityc.facturae32.LegalEntityType();

			// 2.2.4.1.1 CorporateName
			String corporateName = facturae.getParties().getBuyerParty().getLegalEntity().getCorporateName();
			legalEntity.setCorporateName(corporateName);

			// 2.2.4.1.2 TradeName
			if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getTradeName())) {
				String tradeName = facturae.getParties().getBuyerParty().getLegalEntity().getTradeName();
				legalEntity.setTradeName(tradeName);
			}

			// 2.2.4.1.3 RegistrationData
			if (facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData() != null) {
				es.mityc.facturae32.RegistrationDataType registrationData = new es.mityc.facturae32.RegistrationDataType();

				// 2.2.4.1.3.1 Book
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getBook())) {
					String book = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getBook();
					registrationData.setBook(book);
				}

				// 2.2.4.1.3.2 RegisterOfCompaniesLocation
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
					.getRegisterOfCompaniesLocation())) {
					String registerOfCompaniesLocation = facturae.getParties().getBuyerParty().getLegalEntity()
						.getRegistrationData().getRegisterOfCompaniesLocation();
					registrationData.setRegisterOfCompaniesLocation(registerOfCompaniesLocation);
				}

				// 2.2.4.1.3.3 Sheet
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getSheet())) {
					String sheet = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getSheet();
					registrationData.setSheet(sheet);
				}

				// 2.2.4.1.3.4 Folio
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getFolio())) {
					String folio = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getFolio();
					registrationData.setFolio(folio);
				}

				// 2.2.4.1.3.5 Section
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
					.getSection())) {
					String section = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getSection();
					registrationData.setSection(section);
				}

				// 2.2.4.1.3.6 Volume
				if (!""
					.equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData().getVolume())) {
					String volume = facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
						.getVolume();
					registrationData.setVolume(volume);
				}

				// 2.2.4.1.3.7 AdditionalRegistrationData
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getRegistrationData()
					.getAdditionalRegistrationData())) {
					String additionalRegistrationData = facturae.getParties().getBuyerParty().getLegalEntity()
						.getRegistrationData().getAdditionalRegistrationData();
					registrationData.setAdditionalRegistrationData(additionalRegistrationData);
				}

				legalEntity.setRegistrationData(registrationData);
			}

			//2.2.4.1.4

			// if 13
			if (facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain() != null) {

				//2.2.4.1.4.1 AddressInSpain
				es.mityc.facturae32.AddressType addressInSpain2 = new es.mityc.facturae32.AddressType();

				// 2.2.4.1.4.1.1 Address
				String address2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain()
					.getAddress();
				addressInSpain2.setAddress(address2);

				// 2.2.4.1.4.1.2 PostCode
				String postCode2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain()
					.getPostCode();
				addressInSpain2.setPostCode(postCode2);

				// 2.2.4.1.4.1.3 Town
				String town2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain().getTown();
				addressInSpain2.setTown(town2);

				// 2.2.4.1.4.1.4 Province
				String province2 = facturae.getParties().getBuyerParty().getLegalEntity().getAddressInSpain()
					.getProvince();
				addressInSpain2.setProvince(province2);

				// 2.2.4.1.4.1.5 CountryCode
				es.mityc.facturae32.CountryType countryCode2 = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getParties().getBuyerParty()
						.getLegalEntity().getAddressInSpain())).getCountryCode().value());
				addressInSpain2.setCountryCode(countryCode2);

				legalEntity.setAddressInSpain(addressInSpain2);

			} else if (facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress() != null) {

				// 2.2.4.1.4.2 OverseasAddress
				es.mityc.facturae32.OverseasAddressType overseasAddress2 = new es.mityc.facturae32.OverseasAddressType();

				// 2.2.4.1.4.2.1 Address
				String address2 = facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress()
					.getAddress();
				overseasAddress2.setAddress(address2);

				// 2.2.4.1.4.2.2 PostCodeAndTown
				String postCodeAndTown2 = facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress()
					.getPostCodeAndTown();
				overseasAddress2.setPostCodeAndTown(postCodeAndTown2);

				// 2.2.4.1.4.2.3 Province
				String province2 = facturae.getParties().getBuyerParty().getLegalEntity().getOverseasAddress()
					.getProvince();
				overseasAddress2.setProvince(province2);

				// 2.2.4.1.4.2.4 CountryCode
				es.mityc.facturae32.CountryType countryCode2 = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getParties()
						.getBuyerParty().getLegalEntity().getOverseasAddress())).getCountryCode().value());
				overseasAddress2.setCountryCode(countryCode2);

				legalEntity.setOverseasAddress(overseasAddress2);

			} // if 13

			// 2.2.4.1.5 ContactDetails
			if (facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails() != null) {
				es.mityc.facturae32.ContactDetailsType contactDetails2 = new es.mityc.facturae32.ContactDetailsType();

				// 2.2.4.1.5.1 Telephone
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getTelephone())) {
					String telephone2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getTelephone();
					contactDetails2.setTelephone(telephone2);
				}

				// 2.2.4.1.5.2 TeleFax
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails().getTeleFax())) {
					String teleFax2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getTeleFax();
					contactDetails2.setTeleFax(teleFax2);
				}

				// 2.2.4.1.5.3 WebAddress
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getWebAddress())) {
					String webAddress2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getWebAddress();
					contactDetails2.setWebAddress(webAddress2);
				}

				// 2.2.4.1.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getElectronicMail())) {
					String electronicMail2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getElectronicMail();
					contactDetails2.setElectronicMail(electronicMail2);
				}

				// 2.2.4.1.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getContactPersons())) {
					String contactPersons2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getContactPersons();
					contactDetails2.setContactPersons(contactPersons2);
				}

				// 2.2.4.1.5.6 CnoCnae
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails().getCnoCnae())) {
					String cnoCnae2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getCnoCnae();
					contactDetails2.setCnoCnae(cnoCnae2);
				}

				// 2.2.4.1.5.7 INETownCode
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getIneTownCode())) {
					String ineTownCode2 = facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
						.getIneTownCode();
					contactDetails2.setINETownCode(ineTownCode2);
				}

				// 2.2.4.1.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getBuyerParty().getLegalEntity().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails2 = facturae.getParties().getBuyerParty().getLegalEntity()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails2.setAdditionalContactDetails(additionalContactDetails2);
				}

				legalEntity.setContactDetails(contactDetails2);
			}

			buyerParty.setLegalEntity(legalEntity);

		} else if (facturae.getParties().getBuyerParty().getIndividual() != null) {

			// 2.2.4.2 Individual
			es.mityc.facturae32.IndividualType individual2 = new es.mityc.facturae32.IndividualType();

			// 2.2.4.2.1 Name
			String name2 = facturae.getParties().getBuyerParty().getIndividual().getName();
			individual2.setName(name2);

			// 2.2.4.2.2 FirstSurname
			String firstSurname2 = facturae.getParties().getBuyerParty().getIndividual().getFirstSurname();
			individual2.setFirstSurname(firstSurname2);

			// 2.2.4.2.3 SecondSurname
			if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getSecondSurname())) {
				String secondSurname2 = facturae.getParties().getBuyerParty().getIndividual().getSecondSurname();
				individual2.setSecondSurname(secondSurname2);
			}
			// 2.2.4.2.4

			// if 14
			if (facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain() != null) {

				//2.2.4.2.4.1 AddressInSpain
				es.mityc.facturae32.AddressType addressInSpain2 = new es.mityc.facturae32.AddressType();

				// 2.2.4.2.4.1.1 Address
				String address2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain()
					.getAddress();
				addressInSpain2.setAddress(address2);

				// 2.2.4.2.4.1.2 PostCode
				String postCode2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain()
					.getPostCode();
				addressInSpain2.setPostCode(postCode2);

				// 2.2.4.2.4.1.3 Town
				String town2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain().getTown();
				addressInSpain2.setTown(town2);

				// 2.2.4.2.4.1.4 Province
				String province2 = facturae.getParties().getBuyerParty().getIndividual().getAddressInSpain()
					.getProvince();
				addressInSpain2.setProvince(province2);

				// 2.2.4.2.4.1.5 CountryCode
				es.mityc.facturae32.CountryType countryCode2 = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (facturae.getParties().getBuyerParty()
						.getIndividual().getAddressInSpain())).getCountryCode().value());
				addressInSpain2.setCountryCode(countryCode2);

				individual2.setAddressInSpain(addressInSpain2);

			} else if (facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress() != null) {

				// 2.2.4.2.4.2 OverseasAddress
				es.mityc.facturae32.OverseasAddressType overseasAddress2 = new es.mityc.facturae32.OverseasAddressType();

				// 2.2.4.2.4.2.1 Address
				String address2 = facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress()
					.getAddress();
				overseasAddress2.setAddress(address2);

				// 2.2.4.2.4.2.2 PostCodeAndTown
				String postCodeAndTown2 = facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress()
					.getPostCodeAndTown();
				overseasAddress2.setPostCodeAndTown(postCodeAndTown2);

				// 2.2.4.2.4.2.3 Province
				String province2 = facturae.getParties().getBuyerParty().getIndividual().getOverseasAddress()
					.getProvince();
				overseasAddress2.setProvince(province2);

				// 2.2.4.2.4.2.4 CountryCode
				es.mityc.facturae32.CountryType countryCode2 = es.mityc.facturae32.CountryType
					.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (facturae.getParties()
						.getBuyerParty().getIndividual().getOverseasAddress())).getCountryCode().value());
				overseasAddress2.setCountryCode(countryCode2);

				individual2.setOverseasAddress(overseasAddress2);

			} // if 14

			// 2.2.4.2.5 ContactDetails
			if (facturae.getParties().getBuyerParty().getIndividual().getContactDetails() != null) {
				es.mityc.facturae32.ContactDetailsType contactDetails2 = new es.mityc.facturae32.ContactDetailsType();

				// 2.2.4.2.5.1 Telephone
				if (!""
					.equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails().getTelephone())) {
					String telephone2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getTelephone();
					contactDetails2.setTelephone(telephone2);
				}

				// 2.2.4.2.5.2 TeleFax
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails().getTeleFax())) {
					String teleFax2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getTeleFax();
					contactDetails2.setTeleFax(teleFax2);
				}

				// 2.2.4.2.5.3 WebAddress
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getWebAddress())) {
					String webAddress2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getWebAddress();
					contactDetails2.setWebAddress(webAddress2);
				}

				// 2.2.4.2.5.4 ElectronicMail
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getElectronicMail())) {
					String electronicMail2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getElectronicMail();
					contactDetails2.setElectronicMail(electronicMail2);
				}

				// 2.2.4.2.5.5 ContactPersons
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getContactPersons())) {
					String contactPersons2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getContactPersons();
					contactDetails2.setContactPersons(contactPersons2);
				}

				// 2.2.4.2.5.6 CnoCnae
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails().getCnoCnae())) {
					String cnoCnae2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getCnoCnae();
					contactDetails2.setCnoCnae(cnoCnae2);
				}

				// 2.2.4.2.5.7 INETownCode
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getIneTownCode())) {
					String ineTownCode2 = facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
						.getIneTownCode();
					contactDetails2.setINETownCode(ineTownCode2);
				}

				// 2.2.4.2.5.8 AdditionalContactDetails
				if (!"".equals(facturae.getParties().getBuyerParty().getIndividual().getContactDetails()
					.getAdditionalContactDetails())) {
					String additionalContactDetails2 = facturae.getParties().getBuyerParty().getIndividual()
						.getContactDetails().getAdditionalContactDetails();
					contactDetails2.setAdditionalContactDetails(additionalContactDetails2);
				}

				individual2.setContactDetails(contactDetails2);
			}

			buyerParty.setIndividual(individual2);

		} // if 12

		parties.setBuyerParty(buyerParty);

		invoice32.setParties(parties);

		// 3 Invoices
		es.mityc.facturae32.InvoicesType invoices = new es.mityc.facturae32.InvoicesType();

		// 3.1 Invoice
		es.mityc.facturae32.InvoiceType invoiceType = null;

		// for 1
		int invoiceCount = facturae.getInvoices().getInvoice().size();
		for (int i = 0; i < invoiceCount; i++) {

			invoiceType = new es.mityc.facturae32.InvoiceType();

			// 3.1.1 InvoiceHeader
			es.mityc.facturae32.InvoiceHeaderType invoiceHeader = new es.mityc.facturae32.InvoiceHeaderType();

			// 3.1.1.1 InvoiceNumber
			String invoiceNumber = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceNumber();
			invoiceHeader.setInvoiceNumber(invoiceNumber);

			// 3.1.1.2 InvoiceSeriesCode
			if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceSeriesCode())) {
				String invoiceSeriesCode = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
					.getInvoiceSeriesCode();
				invoiceHeader.setInvoiceSeriesCode(invoiceSeriesCode);
			}

			// 3.1.1.3 InvoiceDocumentType
			es.mityc.facturae32.InvoiceDocumentTypeType invoiceDocumentType = es.mityc.facturae32.InvoiceDocumentTypeType
				.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceDocumentType()
					.value());
			invoiceHeader.setInvoiceDocumentType(invoiceDocumentType);

			// 3.1.1.4 InvoiceClass 	
			es.mityc.facturae32.InvoiceClassType invoiceClass = es.mityc.facturae32.InvoiceClassType.fromValue(facturae
				.getInvoices().getInvoice().get(i).getInvoiceHeader().getInvoiceClass().value());
			invoiceHeader.setInvoiceClass(invoiceClass);

			// 3.1.1.5 Corrective
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective() != null) {
				es.mityc.facturae32.CorrectiveType corrective = new es.mityc.facturae32.CorrectiveType();

				// 3.1.1.5.1 InvoiceNumber
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getInvoiceNumber())) {
					String invoiceNumber2 = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
						.getCorrective().getInvoiceNumber();
					corrective.setInvoiceNumber(invoiceNumber2);
				}

				// 3.1.1.5.2 InvoiceSeriesCode
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getInvoiceSeriesCode())) {
					String invoiceSeriesCode2 = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
						.getCorrective().getInvoiceSeriesCode();
					corrective.setInvoiceSeriesCode(invoiceSeriesCode2);
				}

				// 3.1.1.5.3 ReasonCode
				String reasonCode = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getReasonCode();
				corrective.setReasonCode(reasonCode);

				// 3.1.1.5.4 ReasonDescription
				es.mityc.facturae32.ReasonDescriptionType reasonDescription = es.mityc.facturae32.ReasonDescriptionType
					.fromValue(((es.mityc.appfacturae.facturae32.CorrectiveType) (facturae.getInvoices().getInvoice()
						.get(i).getInvoiceHeader().getCorrective())).getReasonDescription().value());
				corrective.setReasonDescription(reasonDescription);

				// 3.1.1.5.5 TaxPeriod
				es.mityc.facturae32.PeriodDates taxPeriod = new es.mityc.facturae32.PeriodDates();

				// 3.1.1.5.5.1 StartDate
				XMLGregorianCalendar startDate = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
					.getCorrective().getTaxPeriod().getStartDate();
				taxPeriod.setStartDate(startDate);

				// 3.1.1.5.5.2 EndDate
				XMLGregorianCalendar endDate = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader()
					.getCorrective().getTaxPeriod().getEndDate();
				taxPeriod.setEndDate(endDate);

				corrective.setTaxPeriod(taxPeriod);

				// 3.1.1.5.6 CorrectionMethod
				String correctionMethod = facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
					.getCorrectionMethod();
				corrective.setCorrectionMethod(correctionMethod);

				// 3.1.1.5.7 CorrectionMethodDescription
				es.mityc.facturae32.CorrectionMethodDescriptionType correctionMethodDescription = es.mityc.facturae32.CorrectionMethodDescriptionType
					.fromValue(facturae.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective()
						.getCorrectionMethodDescription().value());
				corrective.setCorrectionMethodDescription(correctionMethodDescription);

				// 3.1.1.5.8 AdditionalReasonDescription
				if (!"".equals(((es.mityc.appfacturae.facturae32.CorrectiveType) facturae.getInvoices().getInvoice()
					.get(i).getInvoiceHeader().getCorrective()).getAdditionalReasonDescription())) {
					String additionalReasonDescription = ((es.mityc.appfacturae.facturae32.CorrectiveType) facturae
						.getInvoices().getInvoice().get(i).getInvoiceHeader().getCorrective())
						.getAdditionalReasonDescription();
					corrective.setAdditionalReasonDescription(additionalReasonDescription);
				}

				invoiceHeader.setCorrective(corrective);
			}

			invoiceType.setInvoiceHeader(invoiceHeader);

			// 3.1.2 InvoiceIssueData
			es.mityc.facturae32.InvoiceIssueDataType invoiceIssueData = new es.mityc.facturae32.InvoiceIssueDataType();

			// 3.1.2.1 IssueDate
			XMLGregorianCalendar issueDate = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
				.getIssueDate();
			invoiceIssueData.setIssueDate(issueDate);

			// 3.1.2.2 OperationDate
			if (((es.mityc.appfacturae.facturae32.InvoiceIssueDataType) facturae.getInvoices().getInvoice().get(i)
				.getInvoiceIssueData()).getOperationDate() != null) {
				XMLGregorianCalendar operationDate = ((es.mityc.appfacturae.facturae32.InvoiceIssueDataType) (facturae
					.getInvoices().getInvoice().get(i).getInvoiceIssueData())).getOperationDate();
				invoiceIssueData.setOperationDate(operationDate);
			}

			// 3.1.2.3 PlaceOfIssue
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getPlaceOfIssue() != null) {
				es.mityc.facturae32.PlaceOfIssueType placeOfIssue = new es.mityc.facturae32.PlaceOfIssueType();

				// 3.1.2.3.1 PostCode
				String postCode7 = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getPlaceOfIssue()
					.getPostCode();
				placeOfIssue.setPostCode(postCode7);

				// 3.1.2.3.2 PlaceOfIssueDescription
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getPlaceOfIssue()
					.getPlaceOfIssueDescription())) {
					String placeOfIssueDescription = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
						.getPlaceOfIssue().getPlaceOfIssueDescription();
					placeOfIssue.setPlaceOfIssueDescription(placeOfIssueDescription);
				}

				invoiceIssueData.setPlaceOfIssue(placeOfIssue);
			}

			// 3.1.2.4 InvoicingPeriod
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getInvoicingPeriod() != null) {
				es.mityc.facturae32.PeriodDates invoicingPeriod = new es.mityc.facturae32.PeriodDates();

				// 3.1.2.4.1 StartDate
				XMLGregorianCalendar startDate2 = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
					.getInvoicingPeriod().getStartDate();
				invoicingPeriod.setStartDate(startDate2);

				// 3.1.2.4.2 EndDate
				XMLGregorianCalendar endDate2 = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
					.getInvoicingPeriod().getEndDate();
				invoicingPeriod.setEndDate(endDate2);

				invoiceIssueData.setInvoicingPeriod(invoicingPeriod);
			}

			// 3.1.2.5 InvoiceCurrencyCode
			es.mityc.facturae32.CurrencyCodeType invoiceCurrencyCode2 = es.mityc.facturae32.CurrencyCodeType
				.fromValue(((es.mityc.appfacturae.facturae32.InvoiceIssueDataType) (facturae.getInvoices().getInvoice()
					.get(i).getInvoiceIssueData())).getInvoiceCurrencyCode().value());
			invoiceIssueData.setInvoiceCurrencyCode(invoiceCurrencyCode2);

			// 3.1.2.6 ExchangeRateDetails
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData().getExchangeRateDetails() != null) {
				es.mityc.facturae32.ExchangeRateDetailsType exchangeRateDetails = new es.mityc.facturae32.ExchangeRateDetailsType();

				// 3.1.2.6.1 ExchangeRate
				double exchangeRate = facturae.getInvoices().getInvoice().get(i).getInvoiceIssueData()
					.getExchangeRateDetails().getExchangeRate();
				exchangeRateDetails.setExchangeRate(exchangeRate);

				// 3.1.2.6.2 ExchangeRateDate
				XMLGregorianCalendar exchangeRateDate = facturae.getInvoices().getInvoice().get(i)
					.getInvoiceIssueData().getExchangeRateDetails().getExchangeRateDate();
				exchangeRateDetails.setExchangeRateDate(exchangeRateDate);

				invoiceIssueData.setExchangeRateDetails(exchangeRateDetails);
			}

			// 3.1.2.7 TaxCurrencyCode
			es.mityc.facturae32.CurrencyCodeType taxCurrencyCode = es.mityc.facturae32.CurrencyCodeType
				.fromValue(((es.mityc.appfacturae.facturae32.InvoiceIssueDataType) (facturae.getInvoices().getInvoice()
					.get(i).getInvoiceIssueData())).getTaxCurrencyCode().value());
			invoiceIssueData.setTaxCurrencyCode(taxCurrencyCode);

			// 3.1.2.8 LanguageName
			es.mityc.facturae32.LanguageCodeType languageName = es.mityc.facturae32.LanguageCodeType
				.fromValue(((es.mityc.appfacturae.facturae32.InvoiceIssueDataType) (facturae.getInvoices().getInvoice()
					.get(i).getInvoiceIssueData())).getLanguageName().value());
			invoiceIssueData.setLanguageName(languageName);

			invoiceType.setInvoiceIssueData(invoiceIssueData);

			// 3.1.3 TaxesOutputs
			es.mityc.facturae32.InvoiceType.TaxesOutputs taxesOutputs = new es.mityc.facturae32.InvoiceType.TaxesOutputs();

			// 3.1.3.1 Tax
			es.mityc.facturae32.TaxOutputType tax = null;
			// for 2
			int taxCount = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
				.getTaxesOutputs().getTax().size();
			for (int j = 0; j < taxCount; j++) {

				tax = new es.mityc.facturae32.TaxOutputType();

				// 3.1.3.1.1 TaxTypeCode
				String taxTypeCode = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
					.getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getTaxTypeCode();
				tax.setTaxTypeCode(taxTypeCode);

				// 3.1.3.1.2 TaxRate
				double taxRate = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice()
					.get(i))).getTaxesOutputs().getTax().get(j).getTaxRate();
				tax.setTaxRate(taxRate);

				// 3.1.3.1.3 TaxableBase
				es.mityc.facturae32.AmountType taxableBase = new es.mityc.facturae32.AmountType();

				// 3.1.3.1.3.1 TotalAmount
				double totalAmount4 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
					.getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getTaxableBase().getTotalAmount();
				taxableBase.setTotalAmount(totalAmount4);

				// 3.1.3.1.3.2 EquivalentInEuros
				if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getTaxesOutputs().getTax().get(j).getTaxableBase().getEquivalentInEuros() != null) {
					Double equivalentInEuros4 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
						.getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getTaxableBase()
						.getEquivalentInEuros();
					taxableBase.setEquivalentInEuros(equivalentInEuros4);
				}

				tax.setTaxableBase(taxableBase);

				// 3.1.3.1.4 TaxAmount
				if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getTaxesOutputs().getTax().get(j).getTaxAmount() != null) {
					es.mityc.facturae32.AmountType taxAmount = new es.mityc.facturae32.AmountType();

					// 3.1.3.1.4.1 TotalAmount
					double totalAmount5 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
						.getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getTaxAmount().getTotalAmount();
					taxAmount.setTotalAmount(totalAmount5);

					// 3.1.3.1.4.2 EquivalentInEuros
					if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
						.getTaxesOutputs().getTax().get(j).getTaxAmount().getEquivalentInEuros() != null) {
						Double equivalentInEuros5 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getTaxAmount()
							.getEquivalentInEuros();
						taxAmount.setEquivalentInEuros(equivalentInEuros5);
					}

					tax.setTaxAmount(taxAmount);
				}

				// 3.1.3.1.5 SpecialTaxableBase
				if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getTaxesOutputs().getTax().get(j).getSpecialTaxableBase() != null) {
					es.mityc.facturae32.AmountType specialTaxableBase = new es.mityc.facturae32.AmountType();

					// 3.1.3.1.5.1 TotalAmount
					double totalAmount17 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
						.getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getSpecialTaxableBase()
						.getTotalAmount();
					specialTaxableBase.setTotalAmount(totalAmount17);

					// 3.1.3.1.5.2 EquivalentInEuros
					if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
						.getTaxesOutputs().getTax().get(j).getSpecialTaxableBase().getEquivalentInEuros() != null) {
						Double equivalentInEuros17 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getTaxesOutputs().getTax().get(j)
							.getSpecialTaxableBase().getEquivalentInEuros();
						specialTaxableBase.setEquivalentInEuros(equivalentInEuros17);
					}

					tax.setSpecialTaxableBase(specialTaxableBase);
				}

				// 3.1.3.1.6 SpecialTaxAmount
				if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getTaxesOutputs().getTax().get(j).getSpecialTaxAmount() != null) {
					es.mityc.facturae32.AmountType specialTaxAmount = new es.mityc.facturae32.AmountType();

					// 3.1.3.1.6.1 TotalAmount
					double totalAmount18 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
						.getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getSpecialTaxAmount().getTotalAmount();
					specialTaxAmount.setTotalAmount(totalAmount18);

					// 3.1.3.1.6.2 EquivalentInEuros
					if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
						.getTaxesOutputs().getTax().get(j).getSpecialTaxAmount().getEquivalentInEuros() != null) {
						Double equivalentInEuros18 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getTaxesOutputs().getTax().get(j)
							.getSpecialTaxAmount().getEquivalentInEuros();
						specialTaxAmount.setEquivalentInEuros(equivalentInEuros18);
					}

					tax.setSpecialTaxAmount(specialTaxAmount);
				}

				// 3.1.3.1.7 EquivalenceSurcharge
				if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getTaxesOutputs().getTax().get(j).getEquivalenceSurcharge() != null) {
					Double equivalenceSurcharge = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
						.getInvoices().getInvoice().get(i))).getTaxesOutputs().getTax().get(j)
						.getEquivalenceSurcharge();
					tax.setEquivalenceSurcharge(equivalenceSurcharge);
				}

				// 3.1.3.1.8 EquivalenceSurchargeAmount
				if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getTaxesOutputs().getTax().get(j).getEquivalenceSurchargeAmount() != null) {
					es.mityc.facturae32.AmountType equivalenceSurchargeAmount = new es.mityc.facturae32.AmountType();

					// 3.1.3.1.8.1 TotalAmount
					double totalAmount19 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
						.getInvoice().get(i))).getTaxesOutputs().getTax().get(j).getEquivalenceSurchargeAmount()
						.getTotalAmount();
					equivalenceSurchargeAmount.setTotalAmount(totalAmount19);

					// 3.1.3.1.8.2 EquivalentInEuros
					if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
						.getTaxesOutputs().getTax().get(j).getEquivalenceSurchargeAmount().getEquivalentInEuros() != null) {
						Double equivalentInEuros19 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getTaxesOutputs().getTax().get(j)
							.getEquivalenceSurchargeAmount().getEquivalentInEuros();
						equivalenceSurchargeAmount.setEquivalentInEuros(equivalentInEuros19);
					}

					tax.setEquivalenceSurchargeAmount(equivalenceSurchargeAmount);
				}

				taxesOutputs.getTax().add(tax);

			} // for 2

			invoiceType.setTaxesOutputs(taxesOutputs);

			// 3.1.4 TaxesWithheld
			if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld() != null
				&& facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax() != null
				&& facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().size() > 0) {
				es.mityc.facturae32.TaxesType taxesWithheld = new es.mityc.facturae32.TaxesType();

				// 3.1.4.1 Tax
				es.mityc.facturae32.TaxType tax2 = null;

				// for 3
				int taxCount2 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().size();
				for (int j = 0; j < taxCount2; j++) {

					tax2 = new es.mityc.facturae32.TaxType();

					// 3.1.4.1.1 TaxTypeCode
					String taxTypeCode2 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j)
						.getTaxTypeCode();
					tax2.setTaxTypeCode(taxTypeCode2);

					// 3.1.4.1.2 TaxRate
					double taxRate2 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j)
						.getTaxRate();
					tax2.setTaxRate(taxRate2);

					// 3.1.4.1.3 TaxableBase
					es.mityc.facturae32.AmountType taxableBase2 = new es.mityc.facturae32.AmountType();

					// 3.1.4.1.3.1 TotalAmount
					double totalAmount6 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j)
						.getTaxableBase().getTotalAmount();
					taxableBase2.setTotalAmount(totalAmount6);

					// 3.1.4.1.3.2 EquivalentInEuros
					if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j).getTaxableBase()
						.getEquivalentInEuros() != null) {
						Double equivalentInEuros6 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld()
							.getTax().get(j).getTaxableBase().getEquivalentInEuros();
						taxableBase2.setEquivalentInEuros(equivalentInEuros6);
					}

					tax2.setTaxableBase(taxableBase2);

					// 3.1.4.1.4 TaxAmount
					if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j).getTaxAmount() != null) {
						es.mityc.facturae32.AmountType taxAmount2 = new es.mityc.facturae32.AmountType();

						// 3.1.4.1.4.1 TotalAmount
						double totalAmount7 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax()
							.get(j).getTaxAmount().getTotalAmount();
						taxAmount2.setTotalAmount(totalAmount7);

						// 3.1.4.1.4.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getTaxesWithheld().getTax().get(j)
							.getTaxAmount().getEquivalentInEuros() != null) {
							Double equivalentInEuros7 = facturae.getInvoices().getInvoice().get(i).getTaxesWithheld()
								.getTax().get(j).getTaxAmount().getEquivalentInEuros();
							taxAmount2.setEquivalentInEuros(equivalentInEuros7);
						}

						tax2.setTaxAmount(taxAmount2);
					}

					taxesWithheld.getTax().add(tax2);

				} //for 3

				invoiceType.setTaxesWithheld(taxesWithheld);
			}

			// 3.1.5 InvoiceTotals
			es.mityc.facturae32.InvoiceTotalsType invoiceTotals = new es.mityc.facturae32.InvoiceTotalsType();

			// 3.1.5.1 TotalGrossAmount
			double totalGrossAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalGrossAmount();
			invoiceTotals.setTotalGrossAmount(DoubleUtil.redondeo(totalGrossAmount));

			// 3.1.5.2 GeneralDiscounts
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts().getDiscount() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts().getDiscount()
					.size() > 0) {
				es.mityc.facturae32.DiscountsAndRebatesType generalDiscounts = new es.mityc.facturae32.DiscountsAndRebatesType();

				// 3.1.5.2.1 Discount
				es.mityc.facturae32.DiscountType discount = null;

				// for 4
				int discountsCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getGeneralDiscounts().getDiscount().size();
				for (int j = 0; j < discountsCount; j++) {

					discount = new es.mityc.facturae32.DiscountType();

					// 3.1.5.2.1.1 DiscountReason
					String discountReason = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralDiscounts().getDiscount().get(j).getDiscountReason();
					discount.setDiscountReason(discountReason);

					// 3.1.5.2.1.2 DiscountRate
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralDiscounts()
						.getDiscount().get(j).getDiscountRate() != null) {
						Double discountRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getGeneralDiscounts().getDiscount().get(j).getDiscountRate();
						discount.setDiscountRate(discountRate);
					}

					// 3.1.5.2.1.3 DiscountAmount
					double discountAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralDiscounts().getDiscount().get(j).getDiscountAmount();
					discount.setDiscountAmount(discountAmount);

					generalDiscounts.getDiscount().add(discount);

				} // for 4

				invoiceTotals.setGeneralDiscounts(generalDiscounts);
			}

			// 3.1.5.3 GeneralSurcharges
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges().getCharge() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges().getCharge()
					.size() > 0) {
				es.mityc.facturae32.ChargesType generalSurcharges = new es.mityc.facturae32.ChargesType();

				// 3.1.5.3.1 Charge
				es.mityc.facturae32.ChargeType charge = null;

				// for 5
				int surchargesCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getGeneralSurcharges().getCharge().size();
				for (int j = 0; j < surchargesCount; j++) {

					charge = new es.mityc.facturae32.ChargeType();

					// 3.1.5.3.1.1 ChargeReason
					String chargeReason = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralSurcharges().getCharge().get(j).getChargeReason();
					charge.setChargeReason(chargeReason);

					// 3.1.5.3.1.2 ChargeRate
					if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getGeneralSurcharges()
						.getCharge().get(j).getChargeRate() != null) {
						Double chargeRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
							.getGeneralSurcharges().getCharge().get(j).getChargeRate();
						charge.setChargeRate(chargeRate);
					}

					// 3.1.5.3.1.3 ChargeAmount
					double chargeAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getGeneralSurcharges().getCharge().get(j).getChargeAmount();
					charge.setChargeAmount(chargeAmount);

					generalSurcharges.getCharge().add(charge);

				} // for 5

				invoiceTotals.setGeneralSurcharges(generalSurcharges);
			}

			// 3.1.5.4 TotalGeneralDiscounts
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalGeneralDiscounts() != null) {
				Double totalGeneralDiscounts = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalGeneralDiscounts();
				invoiceTotals.setTotalGeneralDiscounts(totalGeneralDiscounts);
			}

			// 3.1.5.5 TotalGeneralSurcharges
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalGeneralSurcharges() != null) {
				Double totalGeneralSurcharges = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalGeneralSurcharges();
				invoiceTotals.setTotalGeneralSurcharges(totalGeneralSurcharges);
			}

			// 3.1.5.6 TotalGrossAmountBeforeTaxes
			double totalGrossAmountBeforeTaxes = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalGrossAmountBeforeTaxes();
			invoiceTotals.setTotalGrossAmountBeforeTaxes(totalGrossAmountBeforeTaxes);

			// 3.1.5.7 TotalTaxOutputs
			double totalTaxOutputs = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalTaxOutputs();
			invoiceTotals.setTotalTaxOutputs(totalTaxOutputs);

			// 3.1.5.8 TotalTaxesWithheld
			double totalTaxesWithheld = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalTaxesWithheld();
			invoiceTotals.setTotalTaxesWithheld(totalTaxesWithheld);

			// 3.1.5.9 InvoiceTotal
			double invoiceTotal = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getInvoiceTotal();
			invoiceTotals.setInvoiceTotal(invoiceTotal);

			// 3.1.5.10 Subsidies
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies().getSubsidy() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies().getSubsidy().size() > 0) {
				es.mityc.facturae32.SubsidiesType subsidies = new es.mityc.facturae32.SubsidiesType();

				// 3.1.5.10.1 Subsidy
				es.mityc.facturae32.SubsidyType subsidy = null;

				// for 6
				int subsidyCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies()
					.getSubsidy().size();
				for (int j = 0; j < subsidyCount; j++) {

					subsidy = new es.mityc.facturae32.SubsidyType();

					// 3.1.5.10.1.1 SubsidyDescription
					String subsidyDescription = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getSubsidies().getSubsidy().get(j).getSubsidyDescription();
					subsidy.setSubsidyDescription(subsidyDescription);

					// 3.1.5.10.1.2 SubsidyRate
					Double subsidyRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies()
						.getSubsidy().get(j).getSubsidyRate();
					subsidy.setSubsidyRate(subsidyRate);

					// 3.1.5.10.1.3 SubsidyAmount
					double subsidyAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getSubsidies()
						.getSubsidy().get(j).getSubsidyAmount();
					subsidy.setSubsidyAmount(subsidyAmount);

					subsidies.getSubsidy().add(subsidy);

				} // for 6

				invoiceTotals.setSubsidies(subsidies);
			}

			// 3.1.5.11 PaymentsOnAccount
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getPaymentsOnAccount() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getPaymentsOnAccount()
					.getPaymentOnAccount() != null
				&& facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getPaymentsOnAccount()
					.getPaymentOnAccount().size() > 0) {
				es.mityc.facturae32.PaymentsOnAccountType paymentsOnAccount = new es.mityc.facturae32.PaymentsOnAccountType();

				// 3.1.5.11.1 PaymentOnAccount
				es.mityc.facturae32.PaymentOnAccountType paymentOnAccount = null;

				// for 7
				int paymentCount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getPaymentsOnAccount()
					.getPaymentOnAccount().size();
				for (int j = 0; j < paymentCount; j++) {

					paymentOnAccount = new es.mityc.facturae32.PaymentOnAccountType();

					// 3.1.5.11.1.1 PaymentOnAccountDate
					XMLGregorianCalendar paymentOnAccountDate = facturae.getInvoices().getInvoice().get(i)
						.getInvoiceTotals().getPaymentsOnAccount().getPaymentOnAccount().get(j)
						.getPaymentOnAccountDate();
					paymentOnAccount.setPaymentOnAccountDate(paymentOnAccountDate);

					// 3.1.5.11.1.2 PaymentOnAccountAmount
					double paymentOnAccountAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getPaymentsOnAccount().getPaymentOnAccount().get(j).getPaymentOnAccountAmount();
					paymentOnAccount.setPaymentOnAccountAmount(paymentOnAccountAmount);

					paymentsOnAccount.getPaymentOnAccount().add(paymentOnAccount);

				} // for 7

				invoiceTotals.setPaymentsOnAccount(paymentsOnAccount);
			}

			// 3.1.5.12 ReimbursableExpenses
			if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice().get(i)
				.getInvoiceTotals())).getReimbursableExpenses() != null
				&& ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice().get(i)
					.getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses() != null
				&& ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice().get(i)
					.getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses().size() > 0) {
				es.mityc.facturae32.ReimbursableExpenses reimbursableExpenses = new es.mityc.facturae32.ReimbursableExpenses();

				// 3.1.5.12.1 ReimbursableExpenses
				es.mityc.facturae32.ReimbursableExpensesType reimbursableExpenses2 = null;

				// for 7 bis
				int reimExpCount = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices()
					.getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses()
					.size();
				for (int j = 0; j < reimExpCount; j++) {

					reimbursableExpenses2 = new es.mityc.facturae32.ReimbursableExpensesType();

					// 3.1.5.12.1.1 ReimbursableExpensesSellerParty
					if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice()
						.get(i).getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses().get(j)
						.getReimbursableExpensesSellerParty() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices()
							.getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
							.getReimbursableExpenses().get(j).getReimbursableExpensesSellerParty()
							.getTaxIdentificationNumber())) {
						es.mityc.facturae32.TaxIdentificationType reimbursableExpensesSellerParty = new es.mityc.facturae32.TaxIdentificationType();

						// 3.1.5.12.1.1.1 PersonTypeCode
						es.mityc.facturae32.PersonTypeCodeType personTypeCode9 = es.mityc.facturae32.PersonTypeCodeType
							.fromValue(((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices()
								.getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
								.getReimbursableExpenses().get(j).getReimbursableExpensesSellerParty()
								.getPersonTypeCode().value());
						reimbursableExpensesSellerParty.setPersonTypeCode(personTypeCode9);

						// 3.1.5.12.1.1.2 ResidenceTypeCode
						es.mityc.facturae32.ResidenceTypeCodeType residenceTypeCode9 = es.mityc.facturae32.ResidenceTypeCodeType
							.fromValue(((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices()
								.getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
								.getReimbursableExpenses().get(j).getReimbursableExpensesSellerParty()
								.getResidenceTypeCode().value());
						reimbursableExpensesSellerParty.setResidenceTypeCode(residenceTypeCode9);

						// 3.1.5.12.1.1.3 TaxIdentificationNumber
						String taxIdentificationNumber9 = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
							.getInvoices().getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
							.getReimbursableExpenses().get(j).getReimbursableExpensesSellerParty()
							.getTaxIdentificationNumber();
						reimbursableExpensesSellerParty.setTaxIdentificationNumber(taxIdentificationNumber9);

						reimbursableExpenses2.setReimbursableExpensesSellerParty(reimbursableExpensesSellerParty);
					}

					// 3.1.5.12.1.2 ReimbursableExpensesBuyerParty
					if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice()
						.get(i).getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses().get(j)
						.getReimbursableExpensesBuyerParty() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices()
							.getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
							.getReimbursableExpenses().get(j).getReimbursableExpensesBuyerParty()
							.getTaxIdentificationNumber())) {
						es.mityc.facturae32.TaxIdentificationType reimbursableExpensesBuyerParty = new es.mityc.facturae32.TaxIdentificationType();

						// 3.1.5.12.1.2.1 PersonTypeCode
						es.mityc.facturae32.PersonTypeCodeType personTypeCode10 = es.mityc.facturae32.PersonTypeCodeType
							.fromValue(((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices()
								.getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
								.getReimbursableExpenses().get(j).getReimbursableExpensesBuyerParty()
								.getPersonTypeCode().value());
						reimbursableExpensesBuyerParty.setPersonTypeCode(personTypeCode10);

						// 3.1.5.12.1.2.2 ResidenceTypeCode
						es.mityc.facturae32.ResidenceTypeCodeType residenceTypeCode10 = es.mityc.facturae32.ResidenceTypeCodeType
							.fromValue(((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices()
								.getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
								.getReimbursableExpenses().get(j).getReimbursableExpensesBuyerParty()
								.getResidenceTypeCode().value());
						reimbursableExpensesBuyerParty.setResidenceTypeCode(residenceTypeCode10);

						// 3.1.5.12.1.2.3 TaxIdentificationNumber
						String taxIdentificationNumber10 = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
							.getInvoices().getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
							.getReimbursableExpenses().get(j).getReimbursableExpensesBuyerParty()
							.getTaxIdentificationNumber();
						reimbursableExpensesBuyerParty.setTaxIdentificationNumber(taxIdentificationNumber10);

						reimbursableExpenses2.setReimbursableExpensesBuyerParty(reimbursableExpensesBuyerParty);
					}

					// 3.1.5.12.1.3 IssueDate
					if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice()
						.get(i).getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses().get(j)
						.getIssueDate() != null) {
						XMLGregorianCalendar issueDate3 = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
							.getInvoices().getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
							.getReimbursableExpenses().get(j).getIssueDate();
						reimbursableExpenses2.setIssueDate(issueDate3);
					}

					// 3.1.5.12.1.4 InvoiceNumber
					if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice()
						.get(i).getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses().get(j)
						.getInvoiceNumber() != null) {
						String invoiceNumber5 = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
							.getInvoices().getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
							.getReimbursableExpenses().get(j).getInvoiceNumber();
						reimbursableExpenses2.setInvoiceNumber(invoiceNumber5);
					}

					// 3.1.5.12.1.5 InvoiceSeriesCode
					if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice()
						.get(i).getInvoiceTotals())).getReimbursableExpenses().getReimbursableExpenses().get(j)
						.getInvoiceSeriesCode() != null) {
						String invoiceSeriesCode5 = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
							.getInvoices().getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
							.getReimbursableExpenses().get(j).getInvoiceSeriesCode();
						reimbursableExpenses2.setInvoiceSeriesCode(invoiceSeriesCode5);
					}

					// 3.1.5.12.1.6 ReimbursableExpensesAmount
					double reimbursableExpensesAmount = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
						.getInvoices().getInvoice().get(i).getInvoiceTotals())).getReimbursableExpenses()
						.getReimbursableExpenses().get(j).getReimbursableExpensesAmount();
					reimbursableExpenses2.setReimbursableExpensesAmount(reimbursableExpensesAmount);

					reimbursableExpenses.getReimbursableExpenses().add(reimbursableExpenses2);

				} // for 7 bis

				invoiceTotals.setReimbursableExpenses(reimbursableExpenses);
			}

			// 3.1.5.13 TotalFinancialExpenses
			if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice().get(i)
				.getInvoiceTotals())).getTotalFinancialExpenses() != null) {
				Double totalFinancialExpenses = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
					.getInvoices().getInvoice().get(i).getInvoiceTotals())).getTotalFinancialExpenses();
				invoiceTotals.setTotalFinancialExpenses(totalFinancialExpenses);
			}

			// 3.1.5.14 TotalOutstandingAmount
			double totalOutstandingAmount4 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalOutstandingAmount();
			invoiceTotals.setTotalOutstandingAmount(totalOutstandingAmount4);

			// 3.1.5.15 TotalPaymentsOnAccount
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getTotalPaymentsOnAccount() != null) {
				Double totalPaymentsOnAccount2 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getTotalPaymentsOnAccount();
				invoiceTotals.setTotalPaymentsOnAccount(totalPaymentsOnAccount2);
			}

			// 3.1.5.16 AmountsWithheld
			if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getAmountsWithheld() != null) {
				es.mityc.facturae32.AmountsWithheldType amountsWithheld = new es.mityc.facturae32.AmountsWithheldType();

				// 3.1.5.16.1 WithholdingReason
				String withholdingReason = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getAmountsWithheld().getWithholdingReason();
				amountsWithheld.setWithholdingReason(withholdingReason);

				// 3.1.5.16.2 WithholdingRate
				if (facturae.getInvoices().getInvoice().get(i).getInvoiceTotals().getAmountsWithheld()
					.getWithholdingRate() != null) {
					Double withholdingRate = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
						.getAmountsWithheld().getWithholdingRate();
					amountsWithheld.setWithholdingRate(withholdingRate);
				}

				// 3.1.5.16.3 WithholdingAmount
				double withholdingAmount = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
					.getAmountsWithheld().getWithholdingAmount();
				amountsWithheld.setWithholdingAmount(withholdingAmount);

				invoiceTotals.setAmountsWithheld(amountsWithheld);
			}

			// 3.1.5.17 TotalExecutableAmount
			double totalExecutableAmount4 = facturae.getInvoices().getInvoice().get(i).getInvoiceTotals()
				.getTotalExecutableAmount();
			invoiceTotals.setTotalExecutableAmount(totalExecutableAmount4);

			// 3.1.5.18 TotalReimbursableExpenses
			if (((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae.getInvoices().getInvoice().get(i)
				.getInvoiceTotals())).getTotalReimbursableExpenses() != null) {
				Double totalReimbursableExpenses = ((es.mityc.appfacturae.facturae32.InvoiceTotalsType) (facturae
					.getInvoices().getInvoice().get(i).getInvoiceTotals())).getTotalReimbursableExpenses();
				invoiceTotals.setTotalReimbursableExpenses(totalReimbursableExpenses);
			}

			invoiceType.setInvoiceTotals(invoiceTotals);

			// 3.1.6 Items
			es.mityc.facturae32.ItemsType items = new es.mityc.facturae32.ItemsType();

			// 3.1.6.1 InvoiceLine
			es.mityc.facturae32.InvoiceLineType invoiceLine = null;

			// for 8
			int invLineCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().size();
			for (int j = 0; j < invLineCount; j++) {

				invoiceLine = new es.mityc.facturae32.InvoiceLineType();

				// 3.1.6.1.1 IssuerContractReference
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getIssuerContractReference() != null
					&& !"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getIssuerContractReference())) {
					String issuerContractReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getIssuerContractReference();
					invoiceLine.setIssuerContractReference(issuerContractReference);
				}

				// 3.1.6.1.2 IssuerContractDate
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getIssuerContractDate())) {
					XMLGregorianCalendar issuerContractDate = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae
						.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)).getIssuerContractDate();
					invoiceLine.setIssuerContractDate(issuerContractDate);
				}

				// 3.1.6.1.3 IssuerTransactionReference
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getIssuerTransactionReference() != null
					&& !"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getIssuerTransactionReference())) {
					String issuerTransactionReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getIssuerTransactionReference();
					invoiceLine.setIssuerTransactionReference(issuerTransactionReference);
				}

				// 3.1.6.1.4 IssuerTransactionDate
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getIssuerTransactionDate())) {
					XMLGregorianCalendar issuerTransactionDate = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae
						.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))
						.getIssuerTransactionDate();
					invoiceLine.setIssuerTransactionDate(issuerTransactionDate);
				}

				// 3.1.6.1.5 ReceiverContractReference
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getReceiverContractReference() != null
					&& !"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getReceiverContractReference())) {
					String receiverContractReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getReceiverContractReference();
					invoiceLine.setReceiverContractReference(receiverContractReference);
				}

				// 3.1.6.1.6 ReceiverContractDate
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getReceiverContractDate())) {
					XMLGregorianCalendar receiverContractDate = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae
						.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))
						.getReceiverContractDate();
					invoiceLine.setReceiverContractDate(receiverContractDate);
				}

				// 3.1.6.1.7 ReceiverTransactionReference
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getReceiverTransactionReference() != null
					&& !"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getReceiverTransactionReference())) {
					String receiverTransactionReference = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getReceiverTransactionReference();
					invoiceLine.setReceiverTransactionReference(receiverTransactionReference);
				}

				// 3.1.6.1.8 ReceiverTransactionDate
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getReceiverTransactionDate())) {
					XMLGregorianCalendar receiverTransactionDate = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae
						.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))
						.getReceiverTransactionDate();
					invoiceLine.setReceiverTransactionDate(receiverTransactionDate);
				}

				// 3.1.6.1.9 FileReference
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getFileReference())) {
					String fileReference = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices()
						.getInvoice().get(i).getItems().getInvoiceLine().get(j)).getFileReference();
					invoiceLine.setFileReference(fileReference);
				}

				// 3.1.6.1.10 FileDate
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getFileDate())) {
					XMLGregorianCalendar fileDate = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae
						.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)).getFileDate();
					invoiceLine.setFileDate(fileDate);
				}

				// 3.1.6.1.11 SequenceNumber
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getSequenceNumber())) {
					Double sequenceNumber = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices()
						.getInvoice().get(i).getItems().getInvoiceLine().get(j)).getSequenceNumber();
					invoiceLine.setSequenceNumber(sequenceNumber);
				}

				// 3.1.6.1.12 DeliveryNotesReferences
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getDeliveryNotesReferences() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDeliveryNotesReferences().getDeliveryNote() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDeliveryNotesReferences().getDeliveryNote().size() > 0) {
					es.mityc.facturae32.DeliveryNotesReferencesType deliveryNotesReferences = new es.mityc.facturae32.DeliveryNotesReferencesType();

					// 3.1.6.1.12.1 DeliveryNote
					es.mityc.facturae32.DeliveryNoteType deliveryNote = null;

					// for 9
					int deliveriesCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDeliveryNotesReferences().getDeliveryNote().size();
					for (int k = 0; k < deliveriesCount; k++) {

						deliveryNote = new es.mityc.facturae32.DeliveryNoteType();

						// 3.1.6.1.12.1.1 DeliveryNoteNumber
						String deliveryNoteNumber = facturae.getInvoices().getInvoice().get(i).getItems()
							.getInvoiceLine().get(j).getDeliveryNotesReferences().getDeliveryNote().get(k)
							.getDeliveryNoteNumber();
						deliveryNote.setDeliveryNoteNumber(deliveryNoteNumber);

						// 3.1.6.1.12.1.2 DeliveryNoteDate
						if (!"".equals(((es.mityc.appfacturae.facturae32.DeliveryNoteType) facturae.getInvoices()
							.getInvoice().get(i).getItems().getInvoiceLine().get(j).getDeliveryNotesReferences()
							.getDeliveryNote().get(k)).getDeliveryNoteDate())) {
							XMLGregorianCalendar deliveryNoteDate = ((es.mityc.appfacturae.facturae32.DeliveryNoteType) facturae
								.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
								.getDeliveryNotesReferences().getDeliveryNote().get(k)).getDeliveryNoteDate();
							deliveryNote.setDeliveryNoteDate(deliveryNoteDate);
						}
						deliveryNotesReferences.getDeliveryNote().add(deliveryNote);
					} // for 9
					invoiceLine.setDeliveryNotesReferences(deliveryNotesReferences);
				}

				// 3.1.6.1.13 ItemDescription
				String itemDescription = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getItemDescription();
				invoiceLine.setItemDescription(itemDescription);

				// 3.1.6.1.14 Quantity
				double quantity = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getQuantity();
				invoiceLine.setQuantity(quantity);

				// 3.1.6.1.15 UnitOfMeasure
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getUnitOfMeasure() != null
					&& !"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getUnitOfMeasure())) {
					String unitOfMeasure = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
						.get(j).getUnitOfMeasure();
					invoiceLine.setUnitOfMeasure(unitOfMeasure);
				}

				// 3.1.6.1.16 UnitPriceWithoutTax
				double unitPriceWithoutTax = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
					.get(j).getUnitPriceWithoutTax();
				invoiceLine.setUnitPriceWithoutTax(unitPriceWithoutTax);

				// 3.1.6.1.17 TotalCost
				double totalCost = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getTotalCost();
				invoiceLine.setTotalCost(totalCost);

				// 3.1.6.1.18 DiscountsAndRebates
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getDiscountsAndRebates() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDiscountsAndRebates().getDiscount() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDiscountsAndRebates().getDiscount().size() > 0) {
					es.mityc.facturae32.DiscountsAndRebatesType discountsAndRebates = new es.mityc.facturae32.DiscountsAndRebatesType();

					// 3.1.6.1.18.1 Discount
					es.mityc.facturae32.DiscountType discount2 = null;

					// for 10
					int discountsCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getDiscountsAndRebates().getDiscount().size();
					for (int k = 0; k < discountsCount; k++) {

						discount2 = new es.mityc.facturae32.DiscountType();

						// 3.1.6.1.18.1.1 DiscountReason
						String discountReason2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getDiscountsAndRebates().getDiscount().get(k).getDiscountReason();
						discount2.setDiscountReason(discountReason2);

						// 3.1.6.1.18.1.2 DiscountRate
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getDiscountsAndRebates().getDiscount().get(k).getDiscountRate() != null) {
							Double discountRate2 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getDiscountsAndRebates().getDiscount().get(k)
								.getDiscountRate();
							discount2.setDiscountRate(discountRate2);
						}

						// 3.1.6.1.18.1.3 DiscountAmount
						double discountAmount2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getDiscountsAndRebates().getDiscount().get(k).getDiscountAmount();
						discount2.setDiscountAmount(discountAmount2);

						discountsAndRebates.getDiscount().add(discount2);

					} // for 10

					invoiceLine.setDiscountsAndRebates(discountsAndRebates);
				}

				// 3.1.6.1.19 Charges
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges()
						.getCharge() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges()
						.getCharge().size() > 0) {
					es.mityc.facturae32.ChargesType charges = new es.mityc.facturae32.ChargesType();

					// 3.1.6.1.19.1 Charge
					es.mityc.facturae32.ChargeType charge2 = null;

					//for 11
					int chargesCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getCharges().getCharge().size();
					for (int k = 0; k < chargesCount; k++) {

						charge2 = new es.mityc.facturae32.ChargeType();

						// 3.1.6.1.19.1.1 ChargeReason
						String chargeReason2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getCharges().getCharge().get(k).getChargeReason();
						charge2.setChargeReason(chargeReason2);

						// 3.1.6.1.19.1.2 ChargeRate
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getCharges()
							.getCharge().get(k).getChargeRate() != null) {
							Double chargeRate2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
								.get(j).getCharges().getCharge().get(k).getChargeRate();
							charge2.setChargeRate(chargeRate2);
						}

						// 3.1.6.1.19.1.3 ChargeAmount
						double chargeAmount2 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getCharges().getCharge().get(k).getChargeAmount();
						charge2.setChargeAmount(chargeAmount2);

						charges.getCharge().add(charge2);

					} //for 11

					invoiceLine.setCharges(charges);
				}

				// 3.1.6.1.20 GrossAmount
				double grossAmount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getGrossAmount();
				invoiceLine.setGrossAmount(grossAmount);

				// 3.1.6.1.21 TaxesWithheld
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesWithheld() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesWithheld()
						.getTax() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTaxesWithheld()
						.getTax().size() > 0) {
					es.mityc.facturae32.TaxesType taxesWithheld2 = new es.mityc.facturae32.TaxesType();

					// 3.1.6.1.21.1 Tax
					es.mityc.facturae32.TaxType tax3 = null;

					// for 12
					int taxesWithheldCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
						.get(j).getTaxesWithheld().getTax().size();
					for (int k = 0; k < taxesWithheldCount; k++) {

						tax3 = new es.mityc.facturae32.TaxType();

						// 3.1.6.1.21.1.1 TaxTypeCode
						String taxTypeCode3 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesWithheld().getTax().get(k).getTaxTypeCode();
						tax3.setTaxTypeCode(taxTypeCode3);

						// 3.1.6.1.21.1.2 TaxRate
						double taxRate3 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesWithheld().getTax().get(k).getTaxRate();
						tax3.setTaxRate(taxRate3);

						// 3.1.6.1.21.1.3 TaxableBase
						es.mityc.facturae32.AmountType taxableBase3 = new es.mityc.facturae32.AmountType();

						// 3.1.6.1.21.1.3.1 TotalAmount
						double totalAmount8 = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine()
							.get(j).getTaxesWithheld().getTax().get(k).getTaxableBase().getTotalAmount();
						taxableBase3.setTotalAmount(totalAmount8);

						// 3.1.6.1.21.1.3.2 EquivalentInEuros
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesWithheld().getTax().get(k).getTaxableBase().getEquivalentInEuros() != null) {
							Double equivalentInEuros8 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesWithheld().getTax().get(k).getTaxableBase()
								.getEquivalentInEuros();
							taxableBase3.setEquivalentInEuros(equivalentInEuros8);
						}

						tax3.setTaxableBase(taxableBase3);

						// 3.1.6.1.21.1.4 TaxAmount
						if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
							.getTaxesWithheld().getTax().get(k).getTaxAmount() != null) {
							es.mityc.facturae32.AmountType taxAmount3 = new es.mityc.facturae32.AmountType();

							// 3.1.6.1.21.1.4.1 TotalAmount
							double totalAmount9 = facturae.getInvoices().getInvoice().get(i).getItems()
								.getInvoiceLine().get(j).getTaxesWithheld().getTax().get(k).getTaxAmount()
								.getTotalAmount();
							taxAmount3.setTotalAmount(totalAmount9);

							// 3.1.6.1.21.1.4.2 EquivalentInEuros
							if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
								.getTaxesWithheld().getTax().get(k).getTaxAmount().getEquivalentInEuros() != null) {
								Double equivalentInEuros9 = facturae.getInvoices().getInvoice().get(i).getItems()
									.getInvoiceLine().get(j).getTaxesWithheld().getTax().get(k).getTaxAmount()
									.getEquivalentInEuros();
								taxAmount3.setEquivalentInEuros(equivalentInEuros9);
							}

							tax3.setTaxAmount(taxAmount3);
						}

						taxesWithheld2.getTax().add(tax3);

					} // for 12

					invoiceLine.setTaxesWithheld(taxesWithheld2);
				}

				// 3.1.6.1.22 TaxesOutputs
				es.mityc.facturae32.InvoiceLineType.TaxesOutputs taxesOutputs2 = new es.mityc.facturae32.InvoiceLineType.TaxesOutputs();

				// 3.1.6.1.22.1 Tax
				es.mityc.facturae32.InvoiceLineType.TaxesOutputs.Tax tax4 = null;

				// for 13
				int taxesOutputsCount = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices()
					.getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().size();
				for (int k = 0; k < taxesOutputsCount; k++) {

					tax4 = new es.mityc.facturae32.InvoiceLineType.TaxesOutputs.Tax();

					// 3.1.6.1.22.1.1 TaxTypeCode
					String taxTypeCode4 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices()
						.getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
						.getTaxTypeCode();
					tax4.setTaxTypeCode(taxTypeCode4);

					// 3.1.6.1.22.1.2 TaxRate
					double taxRate4 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices()
						.getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
						.getTaxRate();
					tax4.setTaxRate(taxRate4);

					// 3.1.6.1.22.1.3 TaxableBase
					es.mityc.facturae32.AmountType taxableBase4 = new es.mityc.facturae32.AmountType();

					// 3.1.6.1.22.1.3.1 TotalAmount
					double totalAmount10 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices()
						.getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
						.getTaxableBase().getTotalAmount();
					taxableBase4.setTotalAmount(totalAmount10);

					// 3.1.6.1.22.1.3.2 EquivalentInEuros
					if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice().get(i)
						.getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k).getTaxableBase()
						.getEquivalentInEuros() != null) {
						Double equivalentInEuros10 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
							.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs()
							.getTax().get(k).getTaxableBase().getEquivalentInEuros();
						taxableBase4.setEquivalentInEuros(equivalentInEuros10);
					}

					tax4.setTaxableBase(taxableBase4);

					// 3.1.6.1.22.1.4 TaxAmount
					if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice().get(i)
						.getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k).getTaxAmount() != null) {
						es.mityc.facturae32.AmountType taxAmount = new es.mityc.facturae32.AmountType();

						// 3.1.6.1.22.1.4.1 TotalAmount
						double totalAmount5 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
							.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs()
							.getTax().get(k).getTaxAmount().getTotalAmount();
						taxAmount.setTotalAmount(totalAmount5);

						// 3.1.6.1.22.1.4.2 EquivalentInEuros
						if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice()
							.get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
							.getTaxAmount().getEquivalentInEuros() != null) {
							Double equivalentInEuros5 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
								.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)))
								.getTaxesOutputs().getTax().get(k).getTaxAmount().getEquivalentInEuros();
							taxAmount.setEquivalentInEuros(equivalentInEuros5);
						}

						tax4.setTaxAmount(taxAmount);
					}

					// 3.1.6.1.22.1.5 SpecialTaxableBase
					if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice().get(i)
						.getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k).getSpecialTaxableBase() != null) {
						es.mityc.facturae32.AmountType specialTaxableBase = new es.mityc.facturae32.AmountType();

						// 3.1.6.1.22.1.5.1 TotalAmount
						double totalAmount17 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
							.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs()
							.getTax().get(k).getSpecialTaxableBase().getTotalAmount();
						specialTaxableBase.setTotalAmount(totalAmount17);

						// 3.1.6.1.22.1.5.2 EquivalentInEuros
						if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice()
							.get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
							.getSpecialTaxableBase().getEquivalentInEuros() != null) {
							Double equivalentInEuros17 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
								.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)))
								.getTaxesOutputs().getTax().get(k).getSpecialTaxableBase().getEquivalentInEuros();
							specialTaxableBase.setEquivalentInEuros(equivalentInEuros17);
						}

						tax4.setSpecialTaxableBase(specialTaxableBase);
					}

					// 3.1.6.1.22.1.6 SpecialTaxAmount
					if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice().get(i)
						.getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k).getSpecialTaxAmount() != null) {
						es.mityc.facturae32.AmountType specialTaxAmount = new es.mityc.facturae32.AmountType();

						// 3.1.6.1.22.1.6.1 TotalAmount
						double totalAmount18 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
							.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs()
							.getTax().get(k).getSpecialTaxAmount().getTotalAmount();
						specialTaxAmount.setTotalAmount(totalAmount18);

						// 3.1.6.1.22.1.6.2 EquivalentInEuros
						if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice()
							.get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
							.getSpecialTaxAmount().getEquivalentInEuros() != null) {
							Double equivalentInEuros18 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
								.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)))
								.getTaxesOutputs().getTax().get(k).getSpecialTaxAmount().getEquivalentInEuros();
							specialTaxAmount.setEquivalentInEuros(equivalentInEuros18);
						}

						tax4.setSpecialTaxAmount(specialTaxAmount);
					}

					// 3.1.6.1.22.1.7 EquivalenceSurcharge
					if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice().get(i)
						.getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
						.getEquivalenceSurcharge() != null) {
						Double equivalenceSurcharge = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
							.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs()
							.getTax().get(k).getEquivalenceSurcharge();
						tax4.setEquivalenceSurcharge(equivalenceSurcharge);
					}

					// 3.1.6.1.22.1.8 EquivalenceSurchargeAmount
					if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice().get(i)
						.getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
						.getEquivalenceSurchargeAmount() != null) {
						es.mityc.facturae32.AmountType equivalenceSurchargeAmount = new es.mityc.facturae32.AmountType();

						// 3.1.6.1.22.1.81 TotalAmount
						double totalAmount19 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
							.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs()
							.getTax().get(k).getEquivalenceSurchargeAmount().getTotalAmount();
						equivalenceSurchargeAmount.setTotalAmount(totalAmount19);

						// 3.1.6.1.22.1.82 EquivalentInEuros
						if (((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae.getInvoices().getInvoice()
							.get(i).getItems().getInvoiceLine().get(j))).getTaxesOutputs().getTax().get(k)
							.getEquivalenceSurchargeAmount().getEquivalentInEuros() != null) {
							Double equivalentInEuros19 = ((es.mityc.appfacturae.facturae32.InvoiceLineType) (facturae
								.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)))
								.getTaxesOutputs().getTax().get(k).getEquivalenceSurchargeAmount()
								.getEquivalentInEuros();
							equivalenceSurchargeAmount.setEquivalentInEuros(equivalentInEuros19);
						}

						tax4.setEquivalenceSurchargeAmount(equivalenceSurchargeAmount);
					}

					taxesOutputs2.getTax().add(tax4);

				} // for 13

				invoiceLine.setTaxesOutputs(taxesOutputs2);

				// 3.1.6.1.23 LineItemPeriod
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getLineItemPeriod() != null) {
					es.mityc.facturae32.PeriodDates lineItemPeriod = new es.mityc.facturae32.PeriodDates();

					// 3.1.6.1.23.1 StartDate
					XMLGregorianCalendar startDate3 = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getLineItemPeriod().getStartDate();
					lineItemPeriod.setStartDate(startDate3);

					// 3.1.6.1.23.2 EndDate
					XMLGregorianCalendar endDate3 = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getLineItemPeriod().getEndDate();
					lineItemPeriod.setEndDate(endDate3);

					invoiceLine.setLineItemPeriod(lineItemPeriod);
				}

				// 3.1.6.1.24 TransactionDate
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getTransactionDate() != null) {
					XMLGregorianCalendar transactionDate = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getTransactionDate();
					invoiceLine.setTransactionDate(transactionDate);
				}

				// 3.1.6.1.25 AdditionalLineItemInformation
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
					.getAdditionalLineItemInformation() != null
					&& !"".equals(facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getAdditionalLineItemInformation())) {
					String additionalLineItemInformation = facturae.getInvoices().getInvoice().get(i).getItems()
						.getInvoiceLine().get(j).getAdditionalLineItemInformation();
					invoiceLine.setAdditionalLineItemInformation(additionalLineItemInformation);
				}

				// 3.1.6.1.26 SpecialTaxableEvent
				if (((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice().get(i)
					.getItems().getInvoiceLine().get(j)).getSpecialTaxableEvent() != null) {
					es.mityc.facturae32.SpecialTaxableEventType specialTaxableEvent = new es.mityc.facturae32.SpecialTaxableEventType();

					// 3.1.6.1.26.1 SpecialTaxableEventCode
					String specialTaxableEventCode = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae
						.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)).getSpecialTaxableEvent()
						.getSpecialTaxableEventCode();
					specialTaxableEvent.setSpecialTaxableEventCode(specialTaxableEventCode);

					// 3.1.6.1.26.2 SpecialTaxableEventReason
					String specialTaxableEventReason = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae
						.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)).getSpecialTaxableEvent()
						.getSpecialTaxableEventReason();
					specialTaxableEvent.setSpecialTaxableEventReason(specialTaxableEventReason);

					invoiceLine.setSpecialTaxableEvent(specialTaxableEvent);
				}

				// 3.1.6.1.27 ArticleCode
				if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices().getInvoice()
					.get(i).getItems().getInvoiceLine().get(j)).getArticleCode())) {
					String articleCode = ((es.mityc.appfacturae.facturae32.InvoiceLineType) facturae.getInvoices()
						.getInvoice().get(i).getItems().getInvoiceLine().get(j)).getArticleCode();
					invoiceLine.setArticleCode(articleCode);
				}

				// 3.1.6.1.28 Extensions
				if (facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getExtensions() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getExtensions()
						.getAny() != null
					&& facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j).getExtensions()
						.getAny().size() > 0) {
					es.mityc.facturae32.ExtensionsType extensions = null;

					// 3.1.6.1.20.1 [any]
					int extensionsCount = facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
						.getExtensions().getAny().size();
					for (int k = 0; k < extensionsCount; k++) {
						extensions = new es.mityc.facturae32.ExtensionsType();
						extensions.getAny().add(
							facturae.getInvoices().getInvoice().get(i).getItems().getInvoiceLine().get(j)
								.getExtensions().getAny().get(k));
					}

					invoiceLine.setExtensions(extensions);
				}

				items.getInvoiceLine().add(invoiceLine);

			} // for 8

			invoiceType.setItems(items);

			// 3.1.7 PaymentDetails
			if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
				.getPaymentDetails() != null
				&& ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getPaymentDetails().getInstallment() != null
				&& ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
					.getPaymentDetails().getInstallment().size() > 0) {
				es.mityc.facturae32.InstallmentsType paymentDetails2 = new es.mityc.facturae32.InstallmentsType();

				// 3.1.7.1 Installment	
				es.mityc.facturae32.InstallmentType installment = null;

				// for 14
				int paymentsCount = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice()
					.get(i))).getPaymentDetails().getInstallment().size();
				for (int j = 0; j < paymentsCount; j++) {

					installment = new es.mityc.facturae32.InstallmentType();

					// 3.1.7.1.1 InstallmentDueDate
					XMLGregorianCalendar installmentDueDate = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
						.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
						.getInstallmentDueDate();
					installment.setInstallmentDueDate(installmentDueDate);

					// 3.1.7.1.2 InstallmentAmount
					double installmentAmount = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
						.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j).getInstallmentAmount();
					installment.setInstallmentAmount(installmentAmount);

					// 3.1.7.1.3 PaymentMeans	
					String paymentMeans = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
						.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j).getPaymentMeans();
					installment.setPaymentMeans(paymentMeans);

					// 3.1.7.1.4 AccountToBeCredited
					if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
						.getPaymentDetails().getInstallment().get(j).getAccountToBeCredited() != null
						&& ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeCredited().getIban() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
							.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j).getAccountToBeCredited()
							.getIban())) {
						es.mityc.facturae32.AccountType accountToBeCredited = new es.mityc.facturae32.AccountType();

						// 3.1.7.1.4.1

						// 3.1.7.1.4.1.1 IBAN
						if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
							.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j).getAccountToBeCredited()
							.getIban())) {
							String iban3 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getIban();
							accountToBeCredited.setIBAN(iban3);
						}

						// 3.1.7.1.4.1.2 AccountNumber
						if (!""
							.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited()).getAccountNumber())) {
							String accountNumber3 = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited()).getAccountNumber();
							accountToBeCredited.setAccountNumber(accountNumber3);
						}

						// 3.1.7.1.4.2 BankCode
						if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeCredited().getBankCode() != null
							&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBankCode())) {
							String bankCode = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBankCode();
							accountToBeCredited.setBankCode(bankCode);
						}

						// 3.1.7.1.4.3 BranchCode
						if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeCredited().getBranchCode() != null
							&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBranchCode())) {
							String branchCode = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBranchCode();
							accountToBeCredited.setBranchCode(branchCode);
						}

						// 3.1.7.1.4.4

						// if 15
						if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeCredited()
							.getBranchInSpainAddress() != null) {

							// 3.1.7.1.4.4.1 BranchInSpainAddress
							es.mityc.facturae32.AddressType branchInSpainAddress = new es.mityc.facturae32.AddressType();

							// 3.1.7.1.4.4.1.1 Address
							String address7 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBranchInSpainAddress().getAddress();
							branchInSpainAddress.setAddress(address7);

							// 3.1.7.1.4.4.1.2 PostCode
							String postCode8 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBranchInSpainAddress().getPostCode();
							branchInSpainAddress.setPostCode(postCode8);

							// 3.1.7.1.4.4.1.3 Town
							String town7 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBranchInSpainAddress().getTown();
							branchInSpainAddress.setTown(town7);

							// 3.1.7.1.4.4.1.4 Province
							String province7 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getBranchInSpainAddress().getProvince();
							branchInSpainAddress.setProvince(province7);

							// 3.1.7.1.4.4.1.5 CountryCode
							es.mityc.facturae32.CountryType countryCode7 = es.mityc.facturae32.CountryType
								.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
									.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
									.getAccountToBeCredited().getBranchInSpainAddress())).getCountryCode().value());
							branchInSpainAddress.setCountryCode(countryCode7);

							accountToBeCredited.setBranchInSpainAddress(branchInSpainAddress);

						} else if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice()
							.get(i))).getPaymentDetails().getInstallment().get(j).getAccountToBeCredited()
							.getOverseasBranchAddress() != null) {

							// 3.1.7.1.4.4.2 OverseasBranchAddress
							es.mityc.facturae32.OverseasAddressType overseasBranchAddress = new es.mityc.facturae32.OverseasAddressType();

							// 3.1.7.1.4.4.2.1 Address
							String address7 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getOverseasBranchAddress().getAddress();
							overseasBranchAddress.setAddress(address7);

							// 3.1.7.1.4.4.2.2 PostCodeAndTown
							String postCodeAndTown8 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getOverseasBranchAddress().getPostCodeAndTown();
							overseasBranchAddress.setPostCodeAndTown(postCodeAndTown8);

							// 3.1.7.1.4.4.2.3 Province
							String province7 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited().getOverseasBranchAddress().getProvince();
							overseasBranchAddress.setProvince(province7);

							// 3.1.7.1.4.4.2.4 CountryCode
							es.mityc.facturae32.CountryType countryCode7 = es.mityc.facturae32.CountryType
								.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
									.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
									.getAccountToBeCredited().getOverseasBranchAddress())).getCountryCode().value());
							overseasBranchAddress.setCountryCode(countryCode7);

							accountToBeCredited.setOverseasBranchAddress(overseasBranchAddress);

						} // if 15

						// 3.1.7.1.4.5 BIC
						if (!""
							.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited()).getBic())) {
							String BIC = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeCredited()).getBic();
							accountToBeCredited.setBIC(BIC);
						}

						installment.setAccountToBeCredited(accountToBeCredited);
					}

					// 3.1.7.1.5 PaymentReconciliationReference
					if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
						.getPaymentDetails().getInstallment().get(j).getPaymentReconciliationReference() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
							.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
							.getPaymentReconciliationReference())) {
						String paymentReconciliationReference = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
							.getPaymentReconciliationReference();
						installment.setPaymentReconciliationReference(paymentReconciliationReference);
					}

					// 3.1.7.1.6 AccountToBeDebited
					if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
						.getPaymentDetails().getInstallment().get(j).getAccountToBeDebited() != null
						&& ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeDebited().getIban() != null
						&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
							.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j).getAccountToBeDebited()
							.getIban())) {
						es.mityc.facturae32.AccountType accountToBeDebited = new es.mityc.facturae32.AccountType();

						// 3.1.7.1.6.1

						// 3.1.7.1.6.1.1 IBAN
						if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
							.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j).getAccountToBeDebited()
							.getIban())) {
							String iban3 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getIban();
							accountToBeDebited.setIBAN(iban3);
						}

						// 3.1.7.1.6.1.2 AccountNumber
						if (!""
							.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited()).getAccountNumber())) {
							String accountNumber3 = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited()).getAccountNumber();
							accountToBeDebited.setAccountNumber(accountNumber3);
						}

						// 3.1.7.1.6.2 BankCode
						if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeDebited().getBankCode() != null
							&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBankCode())) {
							String bankCode2 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBankCode();
							accountToBeDebited.setBankCode(bankCode2);
						}

						// 3.1.7.1.6.3 BranchCode
						if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeDebited().getBranchCode() != null
							&& !"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBranchCode())) {
							String branchCode2 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBranchCode();
							accountToBeDebited.setBranchCode(branchCode2);
						}

						// 3.1.7.1.6.4 

						// if 16
						if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice().get(i)))
							.getPaymentDetails().getInstallment().get(j).getAccountToBeDebited()
							.getBranchInSpainAddress() != null) {

							// 3.1.7.1.6.4.1 BranchInSpainAddress
							es.mityc.facturae32.AddressType branchInSpainAddress2 = new es.mityc.facturae32.AddressType();

							// 3.1.7.1.6.4.1.1 Address
							String address8 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBranchInSpainAddress().getAddress();
							branchInSpainAddress2.setAddress(address8);

							// 3.1.7.1.6.4.1.2 PostCode
							String postCode9 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBranchInSpainAddress().getPostCode();
							branchInSpainAddress2.setPostCode(postCode9);

							// 3.1.7.1.6.4.1.3 Town
							String town8 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBranchInSpainAddress().getTown();
							branchInSpainAddress2.setTown(town8);

							// 3.1.7.1.6.4.1.4 Province
							String province8 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getBranchInSpainAddress().getProvince();
							branchInSpainAddress2.setProvince(province8);

							// 3.1.7.1.6.4.1.5 CountryCode
							es.mityc.facturae32.CountryType countryCode8 = es.mityc.facturae32.CountryType
								.fromValue(((es.mityc.appfacturae.facturae32.AddressType) (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
									.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
									.getAccountToBeDebited().getBranchInSpainAddress())).getCountryCode().value());
							branchInSpainAddress2.setCountryCode(countryCode8);

							accountToBeDebited.setBranchInSpainAddress(branchInSpainAddress2);

						} else if (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice()
							.get(i))).getPaymentDetails().getInstallment().get(j).getAccountToBeDebited()
							.getOverseasBranchAddress() != null) {

							// 3.1.7.1.6.4.2 OverseasBranchAddress
							es.mityc.facturae32.OverseasAddressType overseasBranchAddress2 = new es.mityc.facturae32.OverseasAddressType();

							// 3.1.7.1.6.4.2.1 Address
							String address8 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getOverseasBranchAddress().getAddress();
							overseasBranchAddress2.setAddress(address8);

							// 3.1.7.1.6.4.2.2 PostCodeAndTown
							String postCodeAndTown9 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getOverseasBranchAddress().getPostCodeAndTown();
							overseasBranchAddress2.setPostCodeAndTown(postCodeAndTown9);

							// 3.1.7.1.6.4.2.3 Province
							String province8 = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices()
								.getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited().getOverseasBranchAddress().getProvince();
							overseasBranchAddress2.setProvince(province8);

							// 3.1.7.1.6.4.2.4 CountryCode
							es.mityc.facturae32.CountryType countryCode8 = es.mityc.facturae32.CountryType
								.fromValue(((es.mityc.appfacturae.facturae32.OverseasAddressType) (((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
									.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
									.getAccountToBeDebited().getOverseasBranchAddress())).getCountryCode().value());
							overseasBranchAddress2.setCountryCode(countryCode8);

							accountToBeDebited.setOverseasBranchAddress(overseasBranchAddress2);

						} // if 16

						// 3.1.7.1.6.5 BIC
						if (!""
							.equals(((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited()).getBic())) {
							String BIC = ((es.mityc.appfacturae.facturae32.AccountType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
								.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
								.getAccountToBeDebited()).getBic();
							accountToBeDebited.setBIC(BIC);
						}

						installment.setAccountToBeDebited(accountToBeDebited);
					}

					// 3.1.7.1.7 CollectionAdditionalInformation
					if (!"".equals(((es.mityc.appfacturae.facturae32.InvoiceType) (facturae.getInvoices().getInvoice()
						.get(i))).getPaymentDetails().getInstallment().get(j).getCollectionAdditionalInformation())) {
						String collectionAdditionalInformation = ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j)
							.getCollectionAdditionalInformation();
						installment.setCollectionAdditionalInformation(collectionAdditionalInformation);
					}

					// 3.1.7.1.8 RegulatoryReportingData
					if (!""
						.equals(((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j))
							.getRegulatoryReportingData())) {
						String regulatoryReportingData = ((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j))
							.getRegulatoryReportingData();
						installment.setRegulatoryReportingData(regulatoryReportingData);
					}

					// 3.1.7.1.9 DebitReconciliationReference
					if (!""
						.equals(((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j))
							.getDebitReconciliationReference())) {
						String debitReconciliationReference = ((es.mityc.appfacturae.facturae32.InstallmentType) ((es.mityc.appfacturae.facturae32.InvoiceType) (facturae
							.getInvoices().getInvoice().get(i))).getPaymentDetails().getInstallment().get(j))
							.getDebitReconciliationReference();
						installment.setDebitReconciliationReference(debitReconciliationReference);
					}

					paymentDetails2.getInstallment().add(installment);

				} // for 14

				invoiceType.setPaymentDetails(paymentDetails2);
			}

			// 3.1.8 LegalLiterals
			if (facturae.getInvoices().getInvoice().get(i).getLegalLiterals() != null
				&& facturae.getInvoices().getInvoice().get(i).getLegalLiterals().getLegalReference() != null) {
				es.mityc.facturae32.LegalLiteralsType legalLiterals = new es.mityc.facturae32.LegalLiteralsType();
				// 3.1.8.1 LegalReference
				String legalReference = null;
				// for 15
				int literalsCount = facturae.getInvoices().getInvoice().get(i).getLegalLiterals().getLegalReference().size();
				for (int j = 0; j < literalsCount; j++) {
					legalReference = new String();
					legalReference = facturae.getInvoices().getInvoice().get(i).getLegalLiterals().getLegalReference()
						.get(j);
					legalLiterals.getLegalReference().add(legalReference);

				} // for 15
				invoiceType.setLegalLiterals(legalLiterals);
			}

			// 3.1.9 AdditionalData
			if (facturae.getInvoices().getInvoice().get(i).getAdditionalData() != null) {
				es.mityc.facturae32.AdditionalDataType additionalData = new es.mityc.facturae32.AdditionalDataType();

				// 3.1.9.1 RelatedInvoice
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedInvoice())) {
					String relatedInvoice = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
						.getRelatedInvoice();
					additionalData.setRelatedInvoice(relatedInvoice);
				}

				// 3.1.9.2 RelatedDocuments
				if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
						.getAttachment() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
						.getAttachment().size() > 0) {
					es.mityc.facturae32.AttachedDocumentsType relatedDocuments = new es.mityc.facturae32.AttachedDocumentsType();
					// 3.1.9.2.1 Attachment
					es.mityc.facturae32.AttachmentType attachment = null;
					// for 16
					int attachmentCount = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
						.getRelatedDocuments().getAttachment().size();
					for (int j = 0; j < attachmentCount; j++) {
						attachment = new es.mityc.facturae32.AttachmentType();

						// 3.1.9.2.1.1 AttachmentCompressionAlgorithm
						if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
							.getAttachment().get(j).getAttachmentCompressionAlgorithm() != null) {
							es.mityc.facturae32.AttachmentCompressionAlgorithmType attachmentCompressionAlgorithm = es.mityc.facturae32.AttachmentCompressionAlgorithmType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
									.getRelatedDocuments().getAttachment().get(j).getAttachmentCompressionAlgorithm()
									.value());
							attachment.setAttachmentCompressionAlgorithm(attachmentCompressionAlgorithm);
						}

						// 3.1.9.2.1.2 AttachmentFormat
						es.mityc.facturae32.AttachmentFormatType attachmentFormat = es.mityc.facturae32.AttachmentFormatType
							.fromValue(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
								.getRelatedDocuments().getAttachment().get(j).getAttachmentFormat().value());
						attachment.setAttachmentFormat(attachmentFormat);

						// 3.1.9.2.1.3 AttachmentEncoding
						if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
							.getAttachment().get(j).getAttachmentEncoding() != null) {
							es.mityc.facturae32.AttachmentEncodingType attachmentEncoding = es.mityc.facturae32.AttachmentEncodingType
								.fromValue(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
									.getRelatedDocuments().getAttachment().get(j).getAttachmentEncoding().value());
							attachment.setAttachmentEncoding(attachmentEncoding);
						}

						// 3.1.9.2.1.4 AttachmentDescription
						if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getRelatedDocuments()
							.getAttachment().get(j).getAttachmentDescription() != null) {
							String attachmentDescription = facturae.getInvoices().getInvoice().get(i)
								.getAdditionalData().getRelatedDocuments().getAttachment().get(j)
								.getAttachmentDescription();
							attachment.setAttachmentDescription(attachmentDescription);
						}

						// 3.1.9.2.1.5 AttachmentData
						String attachmentData = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
							.getRelatedDocuments().getAttachment().get(j).getAttachmentData();
						attachment.setAttachmentData(attachmentData);

						relatedDocuments.getAttachment().add(attachment);

					} // for 16
					additionalData.setRelatedDocuments(relatedDocuments);
				}

				// 3.1.9.3 InvoiceAdditionalInformation
				if (!"".equals(facturae.getInvoices().getInvoice().get(i).getAdditionalData()
					.getInvoiceAdditionalInformation())) {
					String invoiceAdditionalInformation = facturae.getInvoices().getInvoice().get(i)
						.getAdditionalData().getInvoiceAdditionalInformation();
					additionalData.setInvoiceAdditionalInformation(invoiceAdditionalInformation);
				}

				// 3.1.9.4 Extensions
				if (facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions().getAny() != null
					&& facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions().getAny().size() > 0) {
					es.mityc.facturae32.ExtensionsType extensions2 = null;

					// 3.1.9.4.1 [any]
					int extensionsCount = facturae.getInvoices().getInvoice().get(i).getAdditionalData()
						.getExtensions().getAny().size();
					for (int k = 0; k < extensionsCount; k++) {
						extensions2 = new es.mityc.facturae32.ExtensionsType();
						extensions2.getAny().add(
							facturae.getInvoices().getInvoice().get(i).getAdditionalData().getExtensions().getAny()
								.get(k));
					}
					additionalData.setExtensions(extensions2);
				}
				invoiceType.setAdditionalData(additionalData);
			}
			invoices.getInvoice().add(invoiceType);
		} // for 1

		invoice32.setInvoices(invoices);

		// 4 Extensions
		if (facturae.getExtensions() != null && facturae.getExtensions().getAny() != null
			&& facturae.getExtensions().getAny().size() > 0) {
			es.mityc.facturae32.ExtensionsType extensions3 = null;

			// 4.1 [any]
			int extensionsCount2 = facturae.getExtensions().getAny().size();
			for (int k = 0; k < extensionsCount2; k++) {
				extensions3 = new es.mityc.facturae32.ExtensionsType();
				extensions3.getAny().add(facturae.getExtensions().getAny().get(k));
			}

			invoice32.setExtensions(extensions3);
		}

		return invoice32;

	}

}
