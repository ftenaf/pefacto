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
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import es.mityc.facturae.ui.CertificatesWindow;
import es.mityc.javasign.bridge.ConfigurationException;
import es.mityc.javasign.bridge.ISignFacade;
import es.mityc.javasign.bridge.SignFactory;

/**
 * Signee
 * 
 * <p>It signs the invoice file, transforming a XML file into a XSIG file.</p>
 * <p>This component supports the sign of 3.0, 3.1 and 3.2 facturae format version.</p>
 * 
 */
public class SignatureUtil {
	
	private static Log logger = LogFactory.getLog(SignatureUtil.class);
	
	/**
	 * This method gets the default store and param from the properties file. It is used when
	 * the store is not specified.
	 * 
	 * @return String[] with 2 strings. The first one contains the store to be used. The second
	 * one contains the param needed depending on the store or an empty string if it is not required.
	 * @throws SigningException If an error happens during the values capture process
	 */
	private static String[] getDefaultStoreAndParam() throws SigningException  {
		Properties configprops = new Properties();
		try {
			configprops.load(SignatureUtil.class.getResourceAsStream(es.mityc.facturae.utils.constants.Constants.CONFIG_PATH));
			File f = new File(configprops.getProperty("sign_file"));
			if (f.exists()){
				logger.info("A configuration file created by user exists, so it is loaded");
				configprops.load(new FileInputStream(f));
			}
			else{
				logger.info("There is not a custom configuration file created by user, so the default resource included in the API jar is loaded");
				configprops.load(SignatureUtil.class.getResourceAsStream(configprops.getProperty("sign_resource")));
			}
			return new String[]{configprops.getProperty("store"),configprops.getProperty("store.param")};
		}
		catch(IOException ioe){
			logger.error("An error has ocurred during the properties loading :"+ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ioe);
			}
			throw new SigningException("Error loading the properties file \""+es.mityc.facturae.utils.constants.Constants.CONFIG_PATH+"\"", ioe);
		}
	}
	
	/**
	 * This method signs a document.
	 * 
	 * <p>The document should contain a xml that represents an invoice.</p>
	 * <p>This method is implemented in order to keep the compatibility with the previous version of
	 * the API. This method creates the ISignFacade object. Then, the store and auxiliar param are
	 * set. Finally, the sign method (document + facade) is invoked.</p>
	 * @param facturae - The document containing an invoice.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static Document sign(Document facturae) throws SigningException, ConfigurationException {
		ISignFacade isf = SignFactory.getInstance().getSignFacade();
		String[] storeParams = getDefaultStoreAndParam();
		isf.setStoreManager(storeParams[0],storeParams[1]);
		return sign (facturae, isf);
	}
	
	/**
	 * This method signs a document.
	 * 
	 * <p>The document should contain a xml that represents an invoice.</p>
	 * <p>The certificate is used to sign the invoice. This method offers and alternative to the 
	 * graphic interface of certificates selection included in the API.</p>
	 * <p>This method creates the ISignFacade object. Then, the store and auxiliar param are
	 * set. Finally, the sign method (document + facade) is invoked.</p>
	 * @param facturae - Document to sign.
	 * @param cert - Certificate to sign.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static Document sign(Document facturae, X509Certificate cert) throws SigningException, ConfigurationException {
		ISignFacade isf = SignFactory.getInstance().getSignFacade();
		String[] storeParams = getDefaultStoreAndParam();
		isf.setStoreManager(storeParams[0],storeParams[1]);
		return sign (facturae, cert, isf);
	}
	
	/**
	 * This method signs a File.
	 * 
	 * <p>The file should contain a xml file that represents an invoice.</p>
	 * <p>This method is implemented in order to keep the compatibility with the previous version of
	 * the API. This method extracts the Document from the file passed as param. Then, the method in
	 * which the facade is included, is invoked.</p>
	 * @param facturae - The file containing an invoice.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static Document sign(File facturae) throws SigningException, ConfigurationException {
		ISignFacade isf = SignFactory.getInstance().getSignFacade();
		String[] storeParams = getDefaultStoreAndParam();
		isf.setStoreManager(storeParams[0],storeParams[1]);
		return sign (facturae, isf);
	}
		
	/**
	 * This method signs a File, and writes the document signed to a new file.
	 * 
	 * <p>The file should contain a xml file that represents an invoice.</p>
	 * <p>This method is implemented in order to keep the compatibility with the previous version of
	 * the API. This method extracts the Document from the file passed as param. Then, the previos
	 * method which indicates the facade, is invoked. Finally, the document signed is written to
	 * a file specified by the pathSignedFile param.</p>
	 * @param facturae - The file containing an invoice.
	 * @param pathSignedFile - The path where the new file will be placed.
	 * @return File signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static File sign(File facturae, String pathSignedFile) throws SigningException, ConfigurationException {
		ISignFacade isf = SignFactory.getInstance().getSignFacade();
		String[] storeParams = getDefaultStoreAndParam();
		isf.setStoreManager(storeParams[0],storeParams[1]);
		return sign (facturae, pathSignedFile, isf);
	}
	
	/**
	 * This method signs a document.
	 * 
	 * <p>The document should contain a xml that represents an invoice.</p>
	 * <p>This method creates the ISignFacade object. Then, the passed params, store and auxiliar
	 * param, are set. Finally, the sign method (document + facade) is invoked.</p>
	 * @param facturae - The document containing an invoice.
	 * @param store - Certificates store to be used.
	 * @param path - Auxiliar param used depending on the certificates store.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static Document sign(Document facturae, String store, String path) throws SigningException, ConfigurationException {
		logger.info("Creating sign facade object1");
		ISignFacade signFacade = SignFactory.getInstance().getSignFacade();
		signFacade.setStoreManager(store, path);
		return sign (facturae, signFacade);
	}
	
	/**
	 * This method signs a document.
	 * 
	 * <p>The document should contain a xml that represents an invoice.</p>
	 * <p>The certificate is used to sign the invoice. This method offers and alternative to the 
	 * graphic interface of certificates selection included in the API.</p>
	 * <p>This method creates the ISignFacade object. Then, the store and path params, passed to
	 * the method are set. Finally, the sign method (document + facade) is invoked.</p>
	 * @param facturae - Document to sign.
	 * @param cert - Certificate to sign.
	 * @param store - Certificates store to be used.
	 * @param path - Auxiliar param used depending on the certificates store.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static Document sign(Document facturae, X509Certificate cert, String store, String path) throws SigningException, ConfigurationException {
		logger.info("Creating sign facade object2");
		ISignFacade signFacade = SignFactory.getInstance().getSignFacade();
		signFacade.setStoreManager(store, path);
		return sign (facturae, cert, signFacade);
	}
	
	/**
	 * This method signs a File.
	 * 
	 * <p>The file should contain a xml file that represents an invoice.</p>
	 * <p>This method extracts the Document from the file passed as param. Then, the previous
	 * method is invoked.</p>
	 * @param facturae - The file containing an invoice.
	 * @param store - Certificates store to be used.
	 * @param path - Auxiliar param used depending on the certificates store.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static Document sign(File facturae, String store, String path) throws SigningException, ConfigurationException {
		logger.info("Creating sign facade object3");
		ISignFacade signFacade = SignFactory.getInstance().getSignFacade();
		signFacade.setStoreManager(store, path);
		return sign (facturae, signFacade);
	}
	
	/**
	 * This method signs a File, and writes the document signed to a new file.
	 * 
	 * <p>The file should contain a xml file that represents an invoice.</p>
	 * <p>This method extracts the Document from the file passed as param. The facade is created
	 * and the store and param set. Then, the previos method is invoked. Finally, the document
	 * signed is written to a file.</p>
	 * @param facturae - The file containing an invoice.
	 * @param pathSignedFile - The path where the new file will be placed.
	 * @param store - Certificates store to be used.
	 * @param path - Auxiliar param used depending on the certificates store.
	 * @return File signed.
	 * @throws SigningException If an error happens during the sign process
	 * @throws ConfigurationException If a configuration error happens during the sign process
	 */
	public static File sign(File facturae, String pathSignedFile, String store, String path) throws SigningException, ConfigurationException {
		logger.info("Creating sign facade object4");
		ISignFacade signFacade = SignFactory.getInstance().getSignFacade();
		signFacade.setStoreManager(store, path);
		return sign (facturae, pathSignedFile, signFacade);
	}
	
	/**
	 * <p> This method signs a document.
	 * 
	 * <p> The initialization params, configured in sign.properties, are:
	 * <ul>
	 * 	<li><code>store</code>: It indicates the store to access in order to obtain the certificates. The allowed values are:
	 * 		<ul><li><code>Explorer</code>: Intenet Explorer store (default)</li>
	 * 			<li><code>Mozilla</code>: Mozilla Firefox store</li>
	 * 			<li><code>MacOsX</code>: MacOs store</li>
	 * 			<li><code>MITyC</code>: Java store</li>
	 * 			<li><code>ClassName</code>: Generic store</li></ul>
	 *  <li><code>store.param</code>: It corresponds with an auxiliar param that depends on the certificates store selected:
	 *  	<ul><li><code>Profile</code>: When the certificates store is Mozilla</li>
	 *      	<li><code>Path</code>: When the certificates store is Java</li>
	 *      	<li>Empty in other cases</li></ul>
	 * 	</li>
	 *  <li><code>sign.policy</code>: It indicates the sign policy that will be included in the signature. The allowed values are:
	 * 		<ul><li><code>facturae30</code>: facturae 3.0 policy</li>
	 * 			<li><code>facturae31</code>: facturae 3.1 policy (default)</li></ul>
	 * 	</li>
	 * 	<li><code>sign.xades.schema</code>: It indicates de XAdES schema version that will be used in the signature. The allowed values are:
	 * 		<ul><li><code>1.2.2</code>: version 1.2.2</li>
	 * 			<li><code>1.3.2</code>: version 1.3.2 (default)</li></ul>
	 * 	</li>
	 *  <li><code>locale.language</code>: It indicates the language used in the certificate selection window.
	 * 		<ul><li><code>es</code>: Spanish (default)</li>
	 * 			<li><code>en</code>: English</li></ul>
	 * 	</li>
	 *  <li><code>locale.country</code>: It indicates the country.
	 * 		<ul><li><code>ES</code>: Spain (default)</li>
	 * 			<li><code>US</code>: United States</li></ul>
	 * 	</li>
	 *  <li><code>lookAndFeel</code>: It indicates the look and feel used in the certificate selection window.
	 * 		<ul><li><code>so</code>: Operative System (default)</li>
	 * 			<li><code>metal</code>: Java Metal L&F</li>
	 * 			<li><code>windows</code>: Java Windows L&F</li>
	 * 			<li><code>motif</code>: Java Motif L&F</li>
	 * 			<li><code>facturae</code>: It is very specific for the facturae application (not recommended)</li></ul>
	 *  </li>
	 *  <li><code>lookAndFeelTheme</code>: It indicates the theme used in the certificate selection window. It will be considered only if lookAndFeel is "metal".
	 * 		<ul><li><code>0</code>: Default Theme (default)</li>
	 * 			<li><code>1</code>: Ocean Theme</li></ul>
	 * 	</li>
	 * 
	 * @param facturae - Document to sign.
	 * @param isf - ISignFacade - facade object that uses the signature components.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 */
	public static Document sign(Document facturae, ISignFacade isf) throws SigningException {
		Document signedDoc = null;

		if (isf == null){
			logger.error("The facade is null");
			throw new SigningException("The facade is null");
		}
					
		Properties configprops = new Properties(), props = new Properties();
		try{
			logger.info("Loading configuration properties");
			configprops.load(SignatureUtil.class.getResourceAsStream(es.mityc.facturae.utils.constants.Constants.CONFIG_PATH));
			File f = new File(configprops.getProperty("sign_file"));
    		if (f.exists()){
    			logger.info("A configuration file created by user exists, so it is loaded");
    			props.load(new FileInputStream(f));
    		}
    		else{
    			logger.info("There is not a custom configuration file created by user, so the default resource included in the API jar is loaded");
    			props.load(SignatureUtil.class.getResourceAsStream(configprops.getProperty("sign_resource")));
    		}
    		
    		logger.info("Initializing facade and setting the locale language and country");
			isf.init(props);
			Locale l = new Locale(props.getProperty("locale.language"),props.getProperty("locale.country"));
			Locale.setDefault(l);
			
			logger.info("Getting the certificates from the store");
			List<X509Certificate> certs = isf.getSignCertificates();
			if (certs == null){
				throw new SigningException("Error loading certificates");
			}
			
			logger.info("Establishing the look & feel");
			// Look & Feel selection
			try{
				if (props.getProperty("lookAndFeel").equals("facturae") || props.getProperty("lookAndFeel").equals("so"))
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				else if (props.getProperty("lookAndFeel").equals("metal")){
					if (props.getProperty("lookAndFeelTheme").equals("0")) 
						MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
					else if (props.getProperty("lookAndFeelTheme").equals("1"))
						MetalLookAndFeel.setCurrentTheme(new OceanTheme());
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				}
				else if (props.getProperty("lookAndFeel").equals("windows"))
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				else if (props.getProperty("lookAndFeel").equals("motif"))
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			}
	        catch(Exception e){
	        	logger.error("An error has occurred when the look and feel was being set :"+e.getMessage());
				if (logger.isDebugEnabled()) {
					logger.error("", e);
				}
				throw new SigningException("Error establishing the look and feel for the certificates window.", e);
	        }
	        CertificatesWindow cw = new CertificatesWindow(certs,props.getProperty("lookAndFeel"),isf);
			int returnValue = cw.showOpenDialog();
			if (returnValue == 0){
				logger.info("Getting the selected certificate...");
				X509Certificate cert = cw.getCert();
				logger.info("Selected certificate: "+cert);
				try {
					logger.info("Signing the invoice: "+facturae);
					signedDoc = isf.sign(cert, facturae);
					logger.info("Signing process finished !");
				} catch (es.mityc.javasign.bridge.SigningException sex) {
					logger.error("An error has occurred during the document signature :"+sex.getMessage());
					if (logger.isDebugEnabled()) {
						logger.error("", sex);
					}
					
					throw new SigningException("Error signing the document. " + sex.getMessage(), sex);
				}
			}
			else{
				logger.info("The action has been cancelled by the user");
			}
			cw.dispose();
			cw = null;
		}
		catch(IOException ioe){
			logger.error("An error has ocurred during the properties loading :"+ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ioe);
			}
			throw new SigningException("Error loading the properties file \""+es.mityc.facturae.utils.constants.Constants.CONFIG_PATH+"\"", ioe);
		}
		catch(ConfigurationException ce){
			logger.error("An error has occurred trying to configure the facade :"+ce.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ce);
			}
			throw new SigningException("Error configuring the facade to sign.", ce);
		}
		catch(Exception e){
			throw new SigningException("Generic error during signing process: " + e.getMessage(), e);
		}
		return signedDoc;
	}
	
	/**
	 * <p> This method signs a document with a certificate specified. The interface by default is not used.
	 * 
	 * <p> The initialization params, configured in sign.properties, are:
	 * <ul>
	 * 	<li><code>store</code>: It indicates the store to access in order to obtain the certificates. The allowed values are:
	 * 		<ul><li><code>Explorer</code>: Intenet Explorer store (default)</li>
	 * 			<li><code>Mozilla</code>: Mozilla Firefox store</li>
	 * 			<li><code>MacOsX</code>: MacOs store</li>
	 * 			<li><code>MITyC</code>: Java store</li>
	 * 			<li><code>ClassName</code>: Generic store</li></ul>
	 *  <li><code>store.param</code>: It corresponds with an auxiliar param that depends on the certificates store selected:
	 *  	<ul><li><code>Profile</code>: When the certificates store is Mozilla</li>
	 *      	<li><code>Path</code>: When the certificates store is Java</li>
	 *      	<li>Empty in other cases</li></ul>
	 * 	</li>
	 *  <li><code>sign.policy</code>: It indicates the sign policy that will be included in the signature. The allowed values are:
	 * 		<ul><li><code>facturae30</code>: facturae 3.0 policy</li>
	 * 			<li><code>facturae31</code>: facturae 3.1 policy (default)</li></ul>
	 * 	</li>
	 * 	<li><code>sign.xades.schema</code>: It indicates de XAdES schema version that will be used in the signature. The allowed values are:
	 * 		<ul><li><code>1.2.2</code>: version 1.2.2</li>
	 * 			<li><code>1.3.2</code>: version 1.3.2 (default)</li></ul>
	 * 	</li>
	 * </ul>
	 * <p> The other params contained in sign.properties are ignored due to the interface is not being used.
	 * @param facturae - Document to sign.
	 * @param cert - Certificate to sign.
	 * @param isf - ISignFacade - facade object that uses the signature components.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 */
	public static Document sign(Document facturae, X509Certificate cert, ISignFacade isf) throws SigningException {
		Document signedDoc = null;
		
		if (isf == null){
			logger.error("The facade is null");
			throw new SigningException("The facade is null");
		}
		
		Properties configprops = new Properties(), props = new Properties();
		try{
			logger.info("Loading configuration properties");
			configprops.load(SignatureUtil.class.getResourceAsStream(es.mityc.facturae.utils.constants.Constants.CONFIG_PATH));
			File f = new File(configprops.getProperty("sign_file"));
    		if (f.exists()){
    			logger.info("A configuration file created by user exists, so it is loaded");
    			props.load(new FileInputStream(f));
    		}
    		else{
    			logger.info("There is not a custom configuration file created by user, so the default resource included in the API jar is loaded");
    			props.load(SignatureUtil.class.getResourceAsStream(configprops.getProperty("sign_resource")));
    		}
    		
    		logger.info("Initializing facade");
			isf.init(props);
			
			try {
				logger.info("Signing the invoice: "+facturae);
				signedDoc = isf.sign(cert, facturae);
				logger.info("Signing process finished !");
			} catch (es.mityc.javasign.bridge.SigningException sex) {
				logger.error("An error has occurred during the document signature :"+sex.getMessage());
				if (logger.isDebugEnabled()) {
					logger.error("", sex);
				}
				throw new SigningException("Error signing the document.", sex);
			}
		}
		catch(IOException ioe){
			logger.error("An error has ocurred during the properties loading :"+ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ioe);
			}
			throw new SigningException("Error loading the properties file \""+es.mityc.facturae.utils.constants.Constants.CONFIG_PATH+"\".", ioe);
		}
		catch(ConfigurationException ce){
			logger.error("An error has occurred trying to configure the facade :"+ce.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ce);
			}
			throw new SigningException("Error configuring the facade to sign.", ce);
		}
		return signedDoc;
	}
	
	/**
	 * This method signs a File.
	 * 
	 * <p>The file should contain a xml file that represents an invoice.</p>
	 * <p>This method extracts the Document from the file passed as param. Then, the previous method is invoked.</p>
	 * @param facturae - The file containing an invoice.
	 * @param isf - ISignFacade - facade object that uses the signature components.
	 * @return Document signed.
	 * @throws SigningException If an error happens during the sign process
	 */
	public static Document sign(File facturae, ISignFacade isf) throws SigningException {
		logger.info("Signing the invoice file - sign(File)");
		
		if (isf == null){
			logger.error("The facade is null");
			throw new SigningException("The facade is null");
		}
		
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try{
			logger.info("Obtaining the document from the file");
			doc = dbf.newDocumentBuilder().parse(facturae);
		}
		catch (SAXException saxe){
			logger.error("Error parsing the file :"+saxe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", saxe);
			}
			throw new SigningException("Error parsing the document to sign.", saxe);
		}
		catch (IOException ioe){
			logger.error("Input / output error :"+ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ioe);
			}
			throw new SigningException("Error parsing the document to sign.", ioe);
		}
		catch (ParserConfigurationException pce){
			logger.error("Parser configuration error :"+pce.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", pce);
			}
			throw new SigningException("Error configuring the XML parser.", pce);
		}
		logger.info("Calling sign(Document)");
		doc = sign(doc, isf);
		return doc;
	}
	
	/**
	 * This method signs a File, and writes the document signed to a new file.
	 * 
	 * <p>The file should contain a xml file that represents an invoice.</p>
	 * <p>This method extracts the Document from the file passed as param. Then, the previos
	 * method is invoked. Finally, the document signed is written to a file.</p>
	 * @param facturae - The file containing an invoice.
	 * @param pathSignedFile - The path where the new file will be placed.
	 * @param isf - ISignFacade - facade object that uses the signature components.
	 * @return File signed.
	 * @throws SigningException If an error happens during the sign process
	 */
	public static File sign(File facturae, String pathSignedFile, ISignFacade isf) throws SigningException {
		logger.info("Signing the invoice file and writing the signed file - sign(File,PathSignedFile)");
		
		if (isf == null){
			logger.error("The facade is null");
			throw new SigningException("The facade is null");
		}
		
		File signedFile = null;
		logger.info("Calling sign(File)");
		Document doc = sign(facturae, isf);
		if (doc != null){
			logger.info("Calling writeXml2File(Document,PathSignedFile)");
			try {
				signedFile = XMLUtil.writeXml2File (doc,pathSignedFile);
			} catch (FileNotFoundException e) {
				throw new SigningException("The document "+pathSignedFile+" cannot be written.", e);
			}
		}
		
		return signedFile;
	}

	/**
	 * This method validate a facturae signature.
	 * 
	 * @param signedDoc - Document to validate.
	 * @return boolean - true is valid, false is invalid.
	 * @throws InvalidSignatureException If an error happens during the validation process
	 */
	public static Map<String, Object> validateSignature(Document signedDoc) throws InvalidSignatureException {
		ISignFacade signFacade =  SignFactory.getInstance().getSignFacade();
		Properties configprops = new Properties(), props = new Properties();
		try{
			logger.info("Loading configuration properties");
			configprops.load(SignatureUtil.class.getResourceAsStream(es.mityc.facturae.utils.constants.Constants.CONFIG_PATH));
			File f = new File(configprops.getProperty("sign_file"));
    		if (f.exists()){
    			logger.info("A configuration file created by user exists, so it is loaded");
    			props.load(new FileInputStream(f));
    		}
    		else{
    			logger.info("There is not a custom configuration file created by user, so the default resource included in the API jar is loaded");
    			props.load(SignatureUtil.class.getResourceAsStream(configprops.getProperty("sign_resource")));
    		}
    		logger.info("Initializing facade and setting the locale language and country");
			signFacade.init(props);
			Map<String, Object> res = validateSignature(signedDoc, signFacade);
			return res;
		}
		catch(IOException ioe){
			logger.error("An error has ocurred during the properties loading :"+ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ioe);
			}
			throw new InvalidSignatureException("Error loading the properties file \""+es.mityc.facturae.utils.constants.Constants.CONFIG_PATH+"\".", ioe);
		}
		catch(ConfigurationException ce){
			logger.error("An error has occurred trying to configure the facade :"+ce.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ce);
			}
			throw new InvalidSignatureException("Error configuring the facade to validate.", ce);
		}
	}
	
	/**
	 * This method validate a facturae signature.
	 * 
	 * @param signedDoc - Document to validate.
	 * @param isf - ISignFacade (interfaz de la fachada de los componentes de firma)
	 * @return boolean - true is valid, false is invalid.
	 * @throws InvalidSignatureException If an error happens during the validation process
	 */
	public static Map<String, Object> validateSignature(Document signedDoc, ISignFacade isf) throws InvalidSignatureException {
		ISignFacade signFacade =  isf;
		List<Map<String, Object>> listaResultado = null;
		Map<String, Object> msig = null;
		try{
			logger.info("Validating the signature");
			listaResultado = signFacade.validate(signedDoc);
			msig = listaResultado.get(0);
			logger.info("Signature OK. The signature process has finished successfully !!!");
			return msig;
		}
		catch (es.mityc.javasign.bridge.InvalidSignatureException isex) {
			logger.error("The signed document is not valid :"+isex.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", isex);
			}
			throw new InvalidSignatureException("Error validating the signature.", isex);
		}
	}
	
	/**
	 * This method validate a facturae signature.
	 * 
	 * @param signedFile - File to validate.
	 * @return boolean - true is valid, false is invalid.
	 * @throws InvalidSignatureException If an error happens during the validation process
	 */
	public static Map<String, Object> validateSignature(File signedFile) throws InvalidSignatureException {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try{
			logger.info("Obtaining the document from the file");
			doc = dbf.newDocumentBuilder().parse(signedFile);
		}
		catch (SAXException saxe){
			logger.error("Error parsing the file :"+saxe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", saxe);
			}
			throw new InvalidSignatureException("Error parsing the document to validate.", saxe);
		}
		catch (IOException ioe){
			logger.error("Input / output error :"+ioe.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", ioe);
			}
			throw new InvalidSignatureException("Error parsing the document to validate.", ioe);
		}
		catch (ParserConfigurationException pce){
			logger.error("Parser configuration error :"+pce.getMessage());
			if (logger.isDebugEnabled()) {
				logger.error("", pce);
			}
			throw new InvalidSignatureException("Error configuring the XML parser.", pce);
		}
		logger.info("Calling sign(Document)");
		return validateSignature(doc);
	}
	
}
