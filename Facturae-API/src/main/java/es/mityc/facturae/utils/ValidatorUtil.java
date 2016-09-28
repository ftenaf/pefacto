/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

/**
 * Validator
 * 
 * <p>It validates the invoice file (XML / XSIG) against the schema.</p>
 * <p>This component supports the validation of 3.2 and 3.2.1 facturae format version.</p>
 * <p>In facturae 3.2 and 3.2.1 cases, this validator is able to validate the schema extensions added.</p>
 */
public class ValidatorUtil {

	private static Log logger = LogFactory.getLog(ValidatorUtil.class);
	private static ValidatorUtil instance;
	private ValidatorUnit[] validators;

	/**
	 * ValidatorUnit
	 * 
	 * <p>It is made up of a Validator and a Schema.</p>
	 * 
	 */
	private class ValidatorUnit {
		private Validator validator;
		private String schema;

		/**
		 * Constructor
		 * 
		 * @param validator - Represents the validator object (javax.xml.validation.Validator) 
		 * @param schema - A string that contains the schema name that will be used to validate.
		 */
		public ValidatorUnit(Validator validator, String schema) {
			this.validator = validator;
			this.schema = schema;
		}

		public Validator getValidator() {
			return validator;
		}

		public String getEsquema() {
			return schema;
		}
	}

	protected ValidatorUtil() throws ValidationException {
		init();
	}

	/**
	 *  Loading the schemas
	 */
	protected void init() throws ValidationException {
		logger.info("Initializing the Validators");
		ArrayList<ValidatorUnit> list = new ArrayList<ValidatorUnit>(3);

		try {
			logger.info("Loading the configuration properties");
			/** Config properties */
			Properties configprops = new Properties();
			configprops.load(this.getClass().getResourceAsStream(
				es.mityc.facturae.utils.constants.Constants.CONFIG_PATH));

			logger.info("Loading the internal schemas properties");
			/** Internal schemas properties */
			Properties schemaProp = new Properties();
			InputStream is = this.getClass().getResourceAsStream("/config/schemaLocation.properties");
			schemaProp.load(is);
			is.close();
			String[] schemas = schemaProp.getProperty("SCHEMAS").split(";");

			logger.info("Loading the external schemas (extensions) properties");
			/** External schemas properties */
			Properties schemaPropExt = new Properties();
			File f = new File(configprops.getProperty("extensions_file"));
			if (f.exists()) {
				logger.info("A extensions file created by user exists, so it is loaded");
				schemaPropExt.load(new FileInputStream(f));
			} else {
				logger.info("There is not a custom extensions file created by user, so the default resource included in the API jar is loaded (no extensions are used in 3.1 and over version)");
				schemaPropExt.load(SignatureUtil.class.getResourceAsStream(configprops.getProperty("extensions_resource")));
			}

			String[] schemasExt = schemaPropExt.getProperty("EXTENSION_SCHEMAS").split(";");
			for (int i = 0; i < schemas.length; i++) {
				SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				InternalResolver resResolv;
				try {
					resResolv = new InternalResolver();
				} catch (ValidationException ve) {
					throw ve;
				}
				logger.info("Adding the external schemas to the resolver");
				// Adding the external schemas to the resolver
				for (int j = 0; j < schemasExt.length && !schemasExt[j].trim().equals(""); j++) {
					resResolv.addSchema(schemaPropExt.getProperty(schemasExt[j] + "_EXTENSION_NAMESPACE"),
						schemaPropExt.getProperty("EXTENSION_SCHEMA_" + schemasExt[j]), false);
				}
				logger.info("Adding the internal schema " + schemas[i] +  " to the resolver ");
				// Adding the internal schemas to the resolver
				resResolv.addSchema(schemaProp.getProperty("XMLSIG_NAMESPACE"),
					schemaProp.getProperty("SCHEMA_XMLSIG"), true);
				resResolv.addSchema(schemaProp.getProperty(schemas[i] + "_NAMESPACE"),
					schemaProp.getProperty("SCHEMA_" + schemas[i]), true);
				sf.setResourceResolver(resResolv);

				List<Source> sourceSchemas = new ArrayList<Source>();
				logger.info("Loading internal schema...");
				/** Internal schema facturae */
				Source schemaFacturae = new StreamSource(this.getClass().getResourceAsStream(
					schemaProp.getProperty("SCHEMA_" + schemas[i])));
				Source schemaExt = null;
				sourceSchemas.add(schemaFacturae);
				logger.info("Loading external schemas...");
				/** External schemas facturae */
				for (int z = 1; z < schemasExt.length + 1 && !schemasExt[z - 1].trim().equals(""); z++) {
					try {
						schemaExt = new StreamSource(new FileInputStream(
							schemaPropExt.getProperty("EXTENSION_SCHEMA_" + schemasExt[z - 1])));
						sourceSchemas.add(schemaExt);
					} catch (FileNotFoundException fnfe) {
						logger.error(fnfe.getMessage());
						if (logger.isDebugEnabled()) {
							logger.error("", fnfe);
						}
					}
				}

				// All external schemas (in addition to internals) are included, forzing to use them
				Source[] sourceSchemasArray = new Source[sourceSchemas.size()];
				Schema schemaInvoice = sf.newSchema(sourceSchemas.toArray(sourceSchemasArray));

				logger.info("Creating new validator");
				Validator val = schemaInvoice.newValidator();
				val.setResourceResolver(resResolv);
				logger.info("Adding new validator to the validators list");
				list.add(new ValidatorUnit(val, schemaProp.getProperty(schemas[i] + "_VERSION")));
			}

		} catch (SAXException saxe) {
			logger.error(saxe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", saxe);
			}
			throw new ValidationException("An error has been produced during ValidatorUtil object built", saxe);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ioe);
			}
			throw new ValidationException("A IO error has been produced during ValidatorUtil object built", ioe);
		} catch (Exception e) {
			logger.error(e.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", e);
			}
			throw new ValidationException("An unknown error has been produced during ValidatorUtil object built", e);
		}

		if (list.size() > 0) {
			logger.info("Transforming Validators list to ValidatorUnits array");
			validators = new ValidatorUnit[list.size()];
			validators = list.toArray(validators);
		}
	}

	public static void reload() {
		instance = null;
	}

	public static ValidatorUtil getInstance() throws ValidationException {
		if (instance == null) {
			try {
				instance = new ValidatorUtil();
			} catch (ValidationException ve) {
				throw ve;
			}
		}
		return instance;
	}

	public boolean validate(File f, String version) throws SAXException, IOException {
		Source source = new StreamSource(f);
		return validate(source, version);
	}

	public boolean validate(Source source, String version) throws SAXException, IOException {
		if (validators != null) {
			for (int i = 0; i < validators.length; i++) {
				if (validators[i].getEsquema().equals(version)) {
					logger.info("Validating...");
					validators[i].getValidator().validate(source);
					return true;
				}
			}
		} else {
			logger.error("There are not validators created");
		}

		return false;
	}

	/**
	 * Internal Resolver
	 * 
	 * <p>This class is responsible for provide the required resources by other schemas.</p>
	 *
	 */
	protected class InternalResolver implements LSResourceResolver {

		/**
		 * Schema Info
		 * 
		 * <p>It contains the schema information.</p>
		 *
		 */
		protected class SchemaInfo {
			String namespace;
			boolean intern;
			String location;

			/**
			 * Constructor
			 * 
			 * @param namespace - The schema's namespace. 
			 * @param intern - If the schema is internal or external.
			 * @param location - The schema's location.
			 */
			public SchemaInfo(String namespace, boolean intern, String location) {
				this.namespace = namespace;
				this.intern = intern;
				this.location = location;
			}
		}

		private ArrayList<SchemaInfo> esquemas = null;

		private DOMImplementationLS domLS = null;

		public InternalResolver() throws ValidationException {
			try {
				DOMImplementation di = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation();
				domLS = (DOMImplementationLS) di;
			} catch (ClassCastException cce) {
				throw new ValidationException("A cast error happened during Internal Resolver built", cce);
			} catch (ParserConfigurationException pce) {
				throw new ValidationException("A parser configuration error happened during Internal Resolver built",
					pce);
			}
			esquemas = new ArrayList<SchemaInfo>();
		}

		public void addSchema(String namespaceURI, String resource, boolean intern) {
			esquemas.add(new SchemaInfo(namespaceURI, intern, resource));

		}

		public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
			String baseURI) {
			if (domLS != null) {
				Iterator<SchemaInfo> it = esquemas.iterator();
				while (it.hasNext()) {
					SchemaInfo esquema = it.next();
					if (esquema.namespace.equalsIgnoreCase(namespaceURI)) {
						LSInput ls = domLS.createLSInput();
						if (esquema.intern) {
							ls.setByteStream(this.getClass().getResourceAsStream(esquema.location));
						} else {
							try {
								ls.setByteStream(new FileInputStream(esquema.location));
							} catch (FileNotFoundException e) {
								ls = null;
								logger.error("The schema is not available. The application can't find the specified schema file",e);
							}
						}
						return ls;
					}
				}
			}
			return null;
		}

	}
}
