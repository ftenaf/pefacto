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
import java.io.InputStream;
import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import es.mityc.facturae.FacturaeVersion;

/**
 * Unmarshaller
 * 
 * <p>
 * It allows the transformation of a XML file or XSIG file if it has been
 * signed, which represents a generic invoice, into a Java object (class
 * "GenericInvoice").
 * </p>
 * <p>
 * This component supports the transformation of 3.0, 3.1 and 3.2 facturae
 * format version objects.
 * </p>
 */
public class UnmarshallerUtil {

	private static Log logger = LogFactory.getLog(UnmarshallerUtil.class);

	/** The context path of Facturae (version 3.0) java objects */
	private static final String CONTEXT_PATH_FACTURAE_30 = "es.mityc.facturae30";
	/** The context path of Facturae (version 3.1) java objects */
	private static final String CONTEXT_PATH_FACTURAE_31 = "es.mityc.facturae31";
	/** The context path of Facturae (version 3.2) java objects */
	private static final String CONTEXT_PATH_FACTURAE_32 = "es.mityc.facturae32";
	/** The context path of Facturae (version 3.2.1) java objects */
	private static final String CONTEXT_PATH_FACTURAE_321 = "es.mityc.facturae321";
	
	/** The unique instance for Factura 3.0 objects */
	private static UnmarshallerUtil instance30;
	/** The unique instance for Factura 3.1 objects */
	private static UnmarshallerUtil instance31;
	/** The unique instance for Factura 3.2 objects */
	private static UnmarshallerUtil instance32;
	/** The unique instance for Factura 3.2.1 objects */
	private static UnmarshallerUtil instance321;
	
	/**
	 * The JAXB Unmarshaller to use
	 */
	private Unmarshaller unmarshaller;

	/**
	 * Returns a <code>UnmarshallerUtil</code> associated to an especific electronic invoice type
	 * @param facturaeVersion The electronic invoice type to unmarshal
	 * @return The <code>UnmarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws UnmarshalException If an error with the internal unmarshaller happens
	 */
	public static synchronized UnmarshallerUtil getInstance(FacturaeVersion facturaeVersion) throws UnmarshalException {
		switch (facturaeVersion) {
		case FACTURAE_30:
			return getInstanceFacturae30();
		case FACTURAE_31:
			return getInstanceFacturae31();
		case FACTURAE_32:
			return getInstanceFacturae32();
		case FACTURAE_321:
			return getInstanceFacturae321();			
		default:
			throw new UnmarshalException("The electronic invoice type is not supported.");
		}
	}
	
	/**
	 * Returns a <code>UnmarshallerUtil</code> associated to FacturaE version 3.0 objects
	 * @return The <code>UnmarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws UnmarshalException If an error with the internal unmarshaller happens
	 */
	private static synchronized UnmarshallerUtil getInstanceFacturae30() throws UnmarshalException {
		if (instance30 == null) {
			instance30 = new UnmarshallerUtil(CONTEXT_PATH_FACTURAE_30);
		}
		return instance30;
	}

	/**
	 * Returns a <code>UnmarshallerUtil</code> associated to FacturaE version 3.1 objects
	 * @return The <code>UnmarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws UnmarshalException If an error with the internal unmarshaller happens
	 */
	private static synchronized UnmarshallerUtil getInstanceFacturae31() throws UnmarshalException {
		if (instance31 == null) {
			instance31 = new UnmarshallerUtil(CONTEXT_PATH_FACTURAE_31);
		}
		return instance31;
	}

	/**
	 * Returns a <code>UnmarshallerUtil</code> associated to FacturaE version 3.2 objects
	 * @return The <code>UnmarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws UnmarshalException If an error with the internal unmarshaller happens
	 */
	private static synchronized UnmarshallerUtil getInstanceFacturae32() throws UnmarshalException {
		if (instance32 == null) {
			instance32 = new UnmarshallerUtil(CONTEXT_PATH_FACTURAE_32);
		}
		return instance32;
	}

	/**
	 * Returns a <code>UnmarshallerUtil</code> associated to FacturaE version 3.2.1 objects
	 * @return The <code>UnmarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws UnmarshalException If an error with the internal unmarshaller happens
	 */
	private static synchronized UnmarshallerUtil getInstanceFacturae321() throws UnmarshalException {
		if (instance321 == null) {
			instance321 = new UnmarshallerUtil(CONTEXT_PATH_FACTURAE_321);
		}
		return instance321;
	}
	
	/**
	 * Private constructor to implement singleton pattern
	 */
	private UnmarshallerUtil() {
	}

	/**
	 * Constructs a <code>UnmarshallerUtil</code> associated to an especific electronic invoice type
	 * @param contextPath The context path asociated to the objects to unmarshal
	 * @throws UnmarshalException If an unmarshal error happens
	 */
	private UnmarshallerUtil(String contextPath) throws UnmarshalException {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("Loading context "+contextPath);
			}

			JAXBContext jc = JAXBContext.newInstance(contextPath);

			logger.info("Creating unmarshaller");
			unmarshaller = jc.createUnmarshaller();

		} catch (JAXBException e) {
			logger.error("Error creating the internal unmarshaller. The UnmarshallerUtil will not work correctly");
			if (logger.isDebugEnabled()) {
				logger.debug("", e);
			}
			throw new UnmarshalException("Error creating the internal unmarshaller.", e);
		}

	}

	/**
	 * This method carries out the unmarshal from the xml file.
	 *  
	 * @param f File class Java object that contains a generic electronic invoice (XML).
	 * @return facturae.GenericInvoice class Java object.
	 * @throws UnmarshalException If an unmarshal error happens
	 */
	public es.mityc.facturae.GenericFacturae unmarshal(File f) throws UnmarshalException {
		
		try {
			logger.info("Starting the unmarshal process");
			es.mityc.facturae.GenericFacturae genericInvoice = (es.mityc.facturae.GenericFacturae)unmarshaller.unmarshal(f);
			logger.info("The unmarshal process is complete !!!");
			
			return genericInvoice;
		
		} catch(JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", jaxbe);
			}
			throw new UnmarshalException(jaxbe);
		}
	}

	/**
	 * This method carries out the unmarshal from the xml <code>InputStream</code>.
	 *  
	 * @param inputStream InputStream that contains a generic electronic invoice (XML).
	 * @return facturae.GenericInvoice class Java object.
	 * @throws UnmarshalException If an unmarshal error happens
	 */
	public es.mityc.facturae.GenericFacturae unmarshal(InputStream inputStream) throws UnmarshalException {
		
		try {
			logger.info("Starting the unmarshal process");
			es.mityc.facturae.GenericFacturae genericInvoice = (es.mityc.facturae.GenericFacturae)unmarshaller.unmarshal(inputStream);
			logger.info("The unmarshal process is complete !!!");
			
			return genericInvoice;
		
		} catch(JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", jaxbe);
			}
			throw new UnmarshalException(jaxbe);
		}
	}

	/**
	 * This method carries out the unmarshal from the xml <code>Reader</code>.
	 *  
	 * @param reader Reader that contains a generic electronic invoice (XML).
	 * @return facturae.GenericInvoice class Java object.
	 * @throws UnmarshalException If an unmarshal error happens
	 */
	public es.mityc.facturae.GenericFacturae unmarshal(Reader reader) throws UnmarshalException {
		
		try {
			logger.info("Starting the unmarshal process");
			es.mityc.facturae.GenericFacturae genericInvoice = (es.mityc.facturae.GenericFacturae)unmarshaller.unmarshal(reader);
			logger.info("The unmarshal process is complete !!!");
			
			return genericInvoice;
		
		} catch(JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", jaxbe);
			}
			throw new UnmarshalException(jaxbe);
		}
	}

	/**
	 * This method carries out the unmarshal from a XML node.
	 *  
	 * @param node XML node that contains a generic electronic invoice (XML).
	 * @return facturae.GenericInvoice class Java object.
	 * @throws UnmarshalException If an unmarshal error happens
	 */
	public es.mityc.facturae.GenericFacturae unmarshal(Node node) throws UnmarshalException {
		
		try{
			logger.info("Starting the unmarshal process");
			es.mityc.facturae.GenericFacturae genericInvoice = (es.mityc.facturae.GenericFacturae)unmarshaller.unmarshal(node);
			logger.info("The unmarshal process is complete !!!");
			
			return genericInvoice;
		
		} catch(JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", jaxbe);
			}
			throw new UnmarshalException(jaxbe);
		}
	}

}
