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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import es.mityc.facturae.FacturaeVersion;
import es.mityc.facturae.utils.mappers.FacturaeNamespacePrefixMapper;

/**
 * Marshaller
 * 
 * <p>It allows the transformation of a Java object (class "Facturae") into a XML file, which represents a facturae invoice without signature.</p>
 * <p>This component supports the transformation of 3.0, 3.1 and 3.2 facturae format version objects.</p>
 */
public class MarshallerUtil {
	
	private static Log logger = LogFactory.getLog(MarshallerUtil.class);

	/** The context path of Facturae (version 3.0) java objects */
	private static final String CONTEXT_PATH_FACTURAE_30 = "es.mityc.facturae30";
	/** The context path of Facturae (version 3.1) java objects */
	private static final String CONTEXT_PATH_FACTURAE_31 = "es.mityc.facturae31";
	/** The context path of Facturae (version 3.2) java objects */
	private static final String CONTEXT_PATH_FACTURAE_32 = "es.mityc.facturae32";
	/** The context path of Facturae (version 3.2.1) java objects */
	private static final String CONTEXT_PATH_FACTURAE_321 = "es.mityc.facturae321";	


	/** The extension used to serialize Facturae (version 3.0) java objects to disk */
	private static final String EXTENSION_FACTURAE_30 = "xml";
	/** The extension used to serialize Facturae (version 3.1) java objects to disk */
	private static final String EXTENSION_FACTURAE_31 = "xsig";
	/** The extension used to serialize Facturae (version 3.2) java objects to disk */
	private static final String EXTENSION_FACTURAE_32 = "xsig";
	/** The extension used to serialize Facturae (version 3.2.1) java objects to disk */
	private static final String EXTENSION_FACTURAE_321 = "xsig";
	
	/** The unique instance for Factura 3.0 objects */
	private static MarshallerUtil instance30;
	/** The unique instance for Factura 3.1 objects */
	private static MarshallerUtil instance31;
	/** The unique instance for Factura 3.2 objects */
	private static MarshallerUtil instance32;
	/** The unique instance for Factura 3.2.1 objects */
	private static MarshallerUtil instance321;
	
	/**
	 * The JAXB Marshaller to use
	 */
	private Marshaller marshaller;
	
	/**
	 * The extension used to serialize java objects to disk;
	 */
	private String extension;
	
	/**
	 * Returns a <code>MarshallerUtil</code> associated to an specific electronic invoice type
	 * @param facturaeVersion The electronic invoice type to marshal
	 * @return The <code>MarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	public static synchronized MarshallerUtil getInstance(FacturaeVersion facturaeVersion) throws MarshalException {
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
			throw new MarshalException("The electronic invoice type is not supported.");
		}
	}
	
	/**
	 * Returns a <code>MarshallerUtil</code> associated to FacturaE version 3.0 objects
	 * @return The <code>MarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	private static synchronized MarshallerUtil getInstanceFacturae30() throws MarshalException {
		if (instance30 == null) {
			instance30 = new MarshallerUtil(CONTEXT_PATH_FACTURAE_30, EXTENSION_FACTURAE_30);
		}
		return instance30;
	}

	/**
	 * Returns a <code>MarshallerUtil</code> associated to FacturaE version 3.1 objects
	 * @return The <code>MarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	private static synchronized MarshallerUtil getInstanceFacturae31() throws MarshalException {
		if (instance31 == null) {
			instance31 = new MarshallerUtil(CONTEXT_PATH_FACTURAE_31, EXTENSION_FACTURAE_31);
		}
		return instance31;
	}

	/**
	 * Returns a <code>MarshallerUtil</code> associated to FacturaE version 3.2 objects
	 * @return The <code>MarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	private static synchronized MarshallerUtil getInstanceFacturae32() throws MarshalException {
		if (instance32 == null) {
			instance32 = new MarshallerUtil(CONTEXT_PATH_FACTURAE_32, EXTENSION_FACTURAE_32);
		}
		return instance32;
	}
	
	/**
	 * Returns a <code>MarshallerUtil</code> associated to FacturaE version 3.2.1 objects
	 * @return The <code>MarshallerUtil</code> associated that will be unique for ClassLoader
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	private static synchronized MarshallerUtil getInstanceFacturae321() throws MarshalException {
		if (instance321 == null) {
			instance321 = new MarshallerUtil(CONTEXT_PATH_FACTURAE_321, EXTENSION_FACTURAE_321);
		}
		return instance321;
	}	

	/**
	 * Private constructor to implement singleton pattern
	 */
	private MarshallerUtil() {
	}

	/**
	 * Constructs a <code>MarshallerUtil</code> associated to an especific electronic invoice type
	 * @param contextPath The context path asociated to the objects to marshal
	 * @param extension Extension used to serialize java objects to disk
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	private MarshallerUtil(String contextPath, String extension) throws MarshalException {
		this.extension = extension;
		try {
			if (logger.isInfoEnabled()) {
				logger.info("Loading context "+contextPath);
			}

			JAXBContext jc = JAXBContext.newInstance(contextPath);

			logger.info("Creating marshaller");
			marshaller = jc.createMarshaller();

			FacturaeNamespacePrefixMapper fnpm = new FacturaeNamespacePrefixMapper();
		
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", fnpm);
			
		} catch (JAXBException e) {
			logger.error("Error creating the internal marshaller. The MarshallerUtil will not work correctly");
			if (logger.isDebugEnabled()) {
				logger.debug("", e);
			}
			throw new MarshalException("Error creating the internal marshaller.", e);
		}

	}

	/**
	 * This method carries out the marshal or build of the xml file.
	 *  
	 * @param invoice It is a generic invoice java object. It contains the invoice java representation.
	 * @param fileName File's name without extension where the invoice will be created.
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	public void marshal(es.mityc.facturae.GenericFacturae invoice, String fileName) throws MarshalException {
		String fileNameWithExtension = fileName + "." + extension;
		try {

			FileOutputStream file = new FileOutputStream(fileNameWithExtension);
			
			logger.info("Starting the marshal process");
			marshaller.marshal( invoice , file );
			logger.info("Marshal process executed OK ! ");
					
			file.close();
			
		} catch (JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.debug("", jaxbe);
			}
			throw new MarshalException(jaxbe);
		} catch (FileNotFoundException fnfe){
			logger.error(fnfe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.debug("", fnfe);
			}
			throw new MarshalException("The file \"" + fileNameWithExtension + "\" does not exist.", fnfe);
		} catch (IOException ioe){
			logger.error(ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.debug("", ioe);
			}
			throw new MarshalException("Error closing the file \"" + fileNameWithExtension + "\".", ioe);
		}
	}

	/**
	 * This method carries out the marshal of the invoice to an <code>OutputStream</code>.
	 *  
	 * @param invoice It is a generic invoice java object. It contains the invoice java representation.
	 * @param outputStream OutputStream where the invoice will be dumped.
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	public void marshal(es.mityc.facturae.GenericFacturae invoice, OutputStream outputStream) throws MarshalException {

		try {
			logger.info("Starting the marshal process");
			marshaller.marshal( invoice , outputStream );
			logger.info("Marshal process executed OK ! ");
			
		} catch (JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.debug("", jaxbe);
			}
			throw new MarshalException(jaxbe);
		}
	}

	/**
	 * This method carries out the marshal of the invoice to an <code>Writer</code>.
	 * It's required to specifying UTF-8 encoding.
	 * @param invoice It is a generic invoice java object. It contains the invoice java representation.
	 * @param writer Writer where the invoice will be dumped.
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	public void marshal(es.mityc.facturae.GenericFacturae invoice, Writer writer) throws MarshalException {

		try {
			logger.info("Starting the marshal process");
			marshaller.marshal( invoice , writer );
			logger.info("Marshal process executed OK ! ");
			
		} catch (JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.debug("", jaxbe);
			}
			throw new MarshalException(jaxbe);
		}	
	}

	/**
	 * This method carries out the marshal of the invoice to a xml node.
	 *  
	 * @param invoice It is a generic invoice java object. It contains the invoice java representation.
	 * @param node XML Node where the invoice will be dumped 
	 * @throws MarshalException If an error with the internal marshaller happens
	 */
	public void marshal(es.mityc.facturae.GenericFacturae invoice, Node node) throws MarshalException {

		try {
			logger.info("Starting the marshal process");
			marshaller.marshal( invoice , node );
			logger.info("Marshal process executed OK ! ");
			
		} catch (JAXBException jaxbe){
			logger.error(jaxbe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.debug("", jaxbe);
			}
			throw new MarshalException(jaxbe);
		}
	}

}
