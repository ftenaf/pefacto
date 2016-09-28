/**
 * Copyright 2013 Ministerio de Industria EnergÃ­a y Turismo
 *
 * Este fichero es parte de "Facturae-Bridge".
 *
 * Licencia con arreglo a la EUPL, VersiÃ³n 1.1 o â€“en cuanto sean aprobadas por la ComisiÃ³n Europeaâ€“ versiones posteriores de la EUPL (la Licencia);
 * Solo podrÃ¡ usarse esta obra si se respeta la Licencia.
 *
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Salvo cuando lo exija la legislaciÃ³n aplicable o se acuerde por escrito, el programa distribuido con arreglo a la Licencia se distribuye Â«TAL CUALÂ»,
 * SIN GARANTÃ�AS NI CONDICIONES DE NINGÃšN TIPO, ni expresas ni implÃ­citas.
 * VÃ©ase la Licencia en el idioma concreto que rige los permisos y limitaciones que establece la Licencia.
 */
package es.mityc.javasign.bridge.comp104;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import es.mityc.firmaJava.libreria.utilidades.UtilidadFirmaElectronica;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.DatosFirma;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.ResultadoValidacion;
import es.mityc.firmaJava.libreria.xades.ValidarFirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.libreria.xades.DataToSign.XADES_X_TYPES;
import es.mityc.firmaJava.libreria.xades.errores.FirmaXMLError;
import es.mityc.firmaJava.ocsp.ConstantesOCSP;
import es.mityc.firmaJava.role.SimpleClaimedRole;
import es.mityc.javasign.bridge.ConfigurationException;
import es.mityc.javasign.bridge.ISignFacade;
import es.mityc.javasign.bridge.InvalidCertificateException;
import es.mityc.javasign.bridge.InvalidSignatureException;
import es.mityc.javasign.bridge.RevokedCertificateException;
import es.mityc.javasign.bridge.SigningException;
import es.mityc.javasign.certificate.CertStatusException;
import es.mityc.javasign.certificate.ICertStatus;
import es.mityc.javasign.certificate.ICertStatusRecoverer;
import es.mityc.javasign.certificate.ocsp.OCSPLiveConsultant;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.DefaultPassStoreKS;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.iexplorer.IExplorerStore;
import es.mityc.javasign.pkstore.macosx.MacOSXStore;
import es.mityc.javasign.pkstore.mitycstore.MITyCStore;
import es.mityc.javasign.pkstore.mozilla.MozillaStoreJSS;
import es.mityc.javasign.pkstore.mscapi.MSCAPIStore;
import es.mityc.javasign.trust.TrustAbstract;
import es.mityc.javasign.trust.TrustFactory;
import es.mityc.javasign.ts.HTTPTimeStampGenerator;
import es.mityc.javasign.utils.OSTool;
import es.mityc.javasign.xml.refs.AllXMLToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import es.mityc.javasign.xml.xades.policy.PolicyResult;
 
/**
 * Facade de servicios de firma para los componentes de firma 1.7 de MINETUR.
 * 
 * <p>Permite el acceso a los almacenes de certificados de IExplorer y Mozilla, y la firma SHA1withRSA con sus certificados.</p>
 * <p>Los certificados de firma son filtrados por su uso bÃ¡sico (permitiendo los que no tienen indicado usos, los de firma digital y los
 * de no repudio). La rutina filtra el certificado de autenticaciÃ³n del DNIe.</p>
 * <p>La validaciÃ³n del certificado se realiza mediante una consulta OCSP vÃ­a HTTP a un conjunto de servidores preconfigurados.</p>
 * 
 */
public class SignMITyCComp10Facade implements ISignFacade {
	
	/** Key para el nivel de firma. */
	private static final String PROP_XADES_LEVEL = "sign.xades.level";
	/** AlmacÃ©n al que se accederÃ¡. */
	private static final String PROP_STORE = "store";
	/** AlmacÃ©n Internet Explorer. */
	private static final String PROP_STORE_IEXPLORER = "iexplorer";
	/** AlmacÃ©n Mozilla. */
	private static final String PROP_STORE_MOZILLA = "mozilla";
	/** Key para la ruta al profile del almacÃ©n de Mozilla. */
	private static final String PROP_MOZILLA_PROFILE = "store.mozilla.profile";
	/** Key para el tipo de token para autenticarse frente a Firefox. */
	private static final String PROP_MOZILLA_LOGIN_TYPE = "store.mozilla.logingtype";
	/** Key para el tiempo de log para Firefox. */
	private static final String PROP_MOZILLA_LOG_TIME = "store.mozilla.log.time";
	/** AlmacÃ©n Mac. */
	private static final String PROP_STORE_MACOS = "macos";
	/** AlmacÃ©n Java. */
	private static final String PROP_STORE_JAVA = "java";
	/** Key para la ruta al fichero de configuraciÃ³n para el almacÃ©n Java. */
	private static final String PROP_STORE_JAVA_CONF_PATH = "store.java.conf.path";
	/** AlmacÃ©n GenÃ©rico. */
	private static final String PROP_STORE_GENERIC = "generic";
	/** Key para el nombre de la clase que gestiona el acceso al almacÃ©n genÃ©rico. */
	private static final String PROP_GENERIC_CLASSNAME = "store.generic.class";
	/** Key para el identificador de polÃ­tica de firma aplicada. */
	private static final String PROP_POLICY = "sign.policy";
	/** Key para el esquema XAdES que se aplicarÃ¡ a la firma. */
	private static final String PROP_XADES_SCHEMA = "sign.xades.schema";
	/** Key para la ruta del servidor de validaciÃ³n OCSP. */
	private static final String PROP_OCSP_SERVER = "ocsp.server";
	/** Key para la ruta del servidor de validaciÃ³n OCSP. */
	private static final String PROP_TSA_SERVER = "tsa.server";
	/** Key para el identificador del validador de confianza. */
	private static final String PROP_TRUSTER_ID = "truster.id"; 
	
	/** Esquema de XAdES 1.2.2. */
	private static final String XADES_SCHEMA_122 = "XAdES_122";
	/** Esquema de XAdES 1.3.2. */
	private static final String XADES_SCHEMA_132 = "XAdES_132";
	
	/** Lista de almacenes de certificados disponibles para 1.0.4 .*/
	public static final String STORE_EXPLORER = "Explorer";
	public static final String STORE_MOZILLA = "Mozilla";
	public static final String STORE_MACOSX = "MacOsX";
	public static final String STORE_MITYC = "MITyC";
	public static final String STORE_CLASS_NAME = "ClassName";
	
	/** Logger. */
	private static final Log LOG = LogFactory.getLog(SignMITyCComp10Facade.class);
	
	/** Nivel de firma configurado. */
	private EnumFormatoFirma xadesLevel = EnumFormatoFirma.XAdES_BES;
	/** Manager del almacÃ©n de certificados con el que se realizan las labores de firma y acceso a certificados. */
	private IPKStoreManager iStore = null;
	/** Ruta del perfil de Mozilla. */
	private String mozillaProfile = null;
	/** PolÃ­tica de firma a aplicar. */
	private String policy = null;
	/** VersiÃ³n de esquema XAdES utilizado en la firma. */
	private String schema = XADES_SCHEMA_132;
	/** Servidor OCSP contra el cual se harÃ¡n las validaciones. */
	private String ocspServer = ConstantesOCSP.USAR_OCSP_MULTIPLE;
	/** Servidor TSA para la generaciÃ³n de sellos de tiempo. */
	private String tsaServer = null;
	/** Administrador de confianza para certificados. */
	private TrustAbstract truster = null;
	
	/**
	 * Constructor.
	 * 
	 * <p>Requerido por la factorÃ­a de facades de firma.</p>
	 */
	public SignMITyCComp10Facade() {
	}

	/**
	 * Inicializa el facade con las propiedades indicadas.
	 * 
	 * <p>Los parÃ¡metros de inicializaciÃ³n del facade son:
	 * <ul>
	 * 	<li><code>store</code>: indica el almacÃ©n al que se accederÃ¡ para obtener los certificados. Los valores permitidos son:
	 * 		<ul><li><code>iexplorer</code>: almacÃ©n de Intenet Explorer (valor por defecto)</li>
	 * 			<li><code>mozilla</code>: almacÃ©n de Mozilla</li>
	 * 			<li><code>generic</code>: almacÃ©n genÃ©rico</li>
	 * 		</ul>
	 * 	</li>
	 * 	<li><code>store.mozilla.profile</code>: si se ha configurado el almacÃ©n de mozilla indica la ruta donde se encuentra el perfil
	 * de usuario de mozilla.</li>
	 * <li><code>store.generic.class</code>: si se ha configurado el almacÃ©n genÃ©rico indica la clase que gestiona los accesos al almacÃ©n
	 * de certificados. La clase debe implementar el interfaz {@link IGenericStore}.</li>
	 * 	<li><code>sign.policy</code>: indica la polÃ­tica de firma que se incluirÃ¡ en la firma. Los valores permitidos son:
	 * 		<ul><li><code>facturae30</code>: polÃ­tica de facturae 3.0</li>
	 * 			<li><code>facturae31</code>: polÃ­tica de facturae 3.1</li></ul>
	 * 	</li>
	 * 	<li><code>sign.xades.schema</code>: indica la versiÃ³n del esquema XAdES que se utilizarÃ¡ para la firma. Los valores permitidos son:
	 * 		<ul><li><code>1.2.2</code>: versiÃ³n 1.2.2</li>
	 * 			<li><code>1.3.2</code>: versiÃ³n 1.3.2 (valor por defecto)</li></ul>
	 * 	</li>
	 * 	<li><code>ocsp.server</code>: indica el servidor al que se le harÃ¡ la consulta de estado del certificado. Los valores permitidos son:
	 * 		<ul><li><code>MULTIPLE</code>: si se quiere que el servidor se seleccione en funciÃ³n de una lista interna de servidores OCSP</li>
	 * 			<li><code>&lt;url&gt;</code>: <code>String</code> con la direcciÃ³n del servidor OCSP al que se harÃ¡ la consulta</li></ul>
	 * 	</li>
	 * </ul>
	 * </p>
	 * 
	 * @param props propiedades de comportamiento del facade
	 * @throws ConfigurationException lanzada cuando alguna de las propiedades indicadas tiene un valor errÃ³neo o falta
	 */
	public void init(final Properties props) throws ConfigurationException {
		// Se recupera el nivel de firma configurado 
		String xades = props.getProperty(PROP_XADES_LEVEL);
		if (xades != null && "".equals(xades.trim())) {
			xadesLevel = EnumFormatoFirma.valueOf(xades);
		}
		
		// Se carga la polÃ­tica de firma 
		policy = props.getProperty(PROP_POLICY);
		
		// Se carga el esquema de firma configurado
		String sch = props.getProperty(PROP_XADES_SCHEMA);
		if ("1.2.2".equals(sch)) {
			schema = XADES_SCHEMA_122;
		} else if ((sch == null) || ("".equals(sch.trim())) || ("1.3.2".equals(sch))) { 
			schema = XADES_SCHEMA_132;
		} else {
			throw new ConfigurationException("Esquema de firma XAdES no reconocido");
		}
		
		// Se carga la URL del servidor OCSP para la validaciÃ³n del estado de certificados
		ocspServer = props.getProperty(PROP_OCSP_SERVER, ConstantesOCSP.USAR_OCSP_MULTIPLE);
		if (ocspServer == null || "".equals(ocspServer.trim())) {
			ocspServer = ConstantesOCSP.USAR_OCSP_MULTIPLE;
		}
		
		// Se carga la URL de la TSA para le generaciÃ³n de sellos de tiempo
		tsaServer = props.getProperty(PROP_TSA_SERVER);
		
		// Se carga el administrador de confianza configurado
		String trusterId = props.getProperty(PROP_TRUSTER_ID);
		if (trusterId != null && !"".equals(trusterId.trim())) {
			truster = TrustFactory.getInstance().getTruster(trusterId);
		}
	}
	
	/**
	 * <p>Establece el almacÃ©n de certificados a utilizar. Por ejemplo:
	 *      - MSCAPIStore(PassHandler)
	 *      - IExplorerStore()
	 *      - MozillaStore(Path2Profile)
	 *      - MacOSXStore(PassHandler)
	 *      - MITyCStore(StreamConf, AutoGenerateBool)
	 *      - getGenericStore(ClassName)</p>
	 *      
	 * @param store - Instancia del CSP
	 * @param extraProperty - Propiedad extra para el almacÃ©n seleccionado
	 * @throws ConfigurationException lanzada cuando falta algÃºn parÃ¡metro de configuraciÃ³n necesario para el facade
	 */
	public void setStoreManager(String store, String extraProperty) throws ConfigurationException {	
		// Se carga la instancia del almacÃ©n configurado
		if ((STORE_EXPLORER.equals(store))) {
			if (OSTool.getSO().isWindows()) { // SÃ³lo se permite bajo Windows
				try {
					iStore = new MSCAPIStore(null);
				} catch (CertStoreException ex) {
					iStore = new IExplorerStore();
				}
			}
		} else if (STORE_MOZILLA.equals(store)) {
			mozillaProfile = extraProperty;
			if ((mozillaProfile == null) || ("".equals(mozillaProfile.trim()))) {
				throw new ConfigurationException("No se ha indicado ruta al perfil de Mozilla");
			}
			try {
				iStore = new MozillaStoreJSS(mozillaProfile);
			} catch (CertStoreException e) {
				LOG.error(e.getMessage(), e);
				throw new ConfigurationException("No se ha podido inicializar el almacÃ©n de Mozilla");				
			}
		} else if (STORE_MACOSX.equals(store)) {
			if (OSTool.getSO().isMacOsX()) { // SÃ³lo se permite bajo Mac OS
				try {
					iStore = new MacOSXStore(new DefaultPassStoreKS());
				} catch (CertStoreException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} else if (STORE_MITYC.equals(store)) { // Este almacÃ©n es independiente del entorno de ejecuciÃ³n
			InputStream is = null;
			if (extraProperty == null || "".equals(extraProperty.trim())) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("No se encontrÃ³ el fichero de configuraciÃ³n " + extraProperty);
				}
				return;
			} else {
				try {
					is = new FileInputStream(extraProperty);
				} catch (FileNotFoundException e) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(e.getMessage(),e);
					}
					return;
				}
			}
			try {
				iStore = new MITyCStore(is, true);
			} catch (CertStoreException e) {
				LOG.error(e.getMessage(), e);
			}
		} else if (STORE_CLASS_NAME.equals(store)) {
			if ((extraProperty == null) || ("".equals(extraProperty.trim()))) {
				throw new ConfigurationException("No se ha indicado clase para gestionar acceso a almacÃ©n genÃ©rico");
			}
			iStore = getGenericStore(extraProperty);
		} else {
			throw new ConfigurationException("No se reconoce el tipo de almacÃ©n de certificados indicado: " + store);
		}
		
		if (iStore == null) {
			throw new ConfigurationException("No se pudo instanciar el almacÃ©n de certificados indicado: " + store);
		}
	}
	
	public IPKStoreManager getStoreManager() throws ConfigurationException {
		if(iStore == null){
			throw new ConfigurationException("iStore not configured");
		}
		return iStore;
	}

	/**
	 * <p>Recupera el ClassLoader de contexto si estÃ¡ disponible.</p>
	 * <p>Si no estÃ¡ disponible el de contexto devuelve el propio de la clase.</p>
	 * @return ClassLoader
	 */
	private static ClassLoader getClassLoader() {
		try {
			ClassLoader cl = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
			    public ClassLoader run() {
					ClassLoader classLoader = null;
					try {
					    classLoader = Thread.currentThread().getContextClassLoader();
					} catch (SecurityException ex) {
					}
					return classLoader;
			    }
			});
			if (cl != null) {
				return cl;
			}
		} catch (Exception ex) {
		}
		return SignMITyCComp10Facade.class.getClassLoader();
	}
	
	/**
	 * <p>Devuelve una instancia del gestionador de almacÃ©n de certificados indicado.</p> 
	 * @param classname nombre de la clase que implementa el interfaz IGenericStore
	 * @return Una instancia del gestionador de almacÃ©n o <code>null</code> si no hay ninguno asociado o no se puede instanciar.
	 */
	private IPKStoreManager getGenericStore(final String classname) {
		IPKStoreManager genericStore = null;
		try {
			ClassLoader cl = getClassLoader();
			Class< ? > classTemp = null;
			if (cl != null) {
				classTemp = cl.loadClass(classname);
			} else {
				classTemp = Class.forName(classname);
			}
			if (classTemp != null) {
				genericStore = (IPKStoreManager) classTemp.getConstructor((Class[]) null).newInstance();
			}
		} catch (InstantiationException ex) {
			LOG.error("Error instanciando IGenericStore " + classname);
			if (LOG.isDebugEnabled()) {
				LOG.error("", ex);
			}
		} catch (InvocationTargetException ex) {
			LOG.error("Error instanciando IGenericStore " + classname);
			if (LOG.isDebugEnabled()) {
				LOG.error("", ex);
			}
		} catch (IllegalAccessException ex) {
			LOG.error("Error instanciando IGenericStore " + classname);
			if (LOG.isDebugEnabled()) {
				LOG.error("", ex);
			}
		} catch (ClassNotFoundException ex) {
			LOG.error("Error instanciando IGenericStore " + classname);
			if (LOG.isDebugEnabled()) {
				LOG.error("", ex);
			}
		} catch (ClassCastException ex) {
			LOG.error("Error instanciando IGenericStore " + classname);
			if (LOG.isDebugEnabled()) {
				LOG.error("", ex);
			}
		} catch (NoSuchMethodException ex) {
			LOG.error("Error instanciando IGenericStore " + classname);
			if (LOG.isDebugEnabled()) {
				LOG.error("", ex);
			}
		}
		return genericStore;
	}

	/**
	 * Accede al almacÃ©n de certificados configurado y obtiene la lista de los certificados de usuario que pueden realizar firma digital.
	 * 
	 * <p>En el caso de disponer de certificados de DNIe filtra, elimina el certificado de autenticaciÃ³n.</p>
	 * <p><b>Nota Mozilla</b>: para el almacÃ©n de mozilla el criterio para seleccionar certificados es incluir los certificados que no
	 * tienen indicado ningÃºn tipo de uso (i.e. no existe la extensiÃ³n <code>keyUsage</code>), o tienen indicado el uso <i>No repudio</i> y/o
	 * <i>Firma digital</i>.
	 * 
	 * @return Lista de certificados obtenida, <code>null</code> si no se ha configurado ningÃºn almacÃ©n o se produce un error al intentar
	 * 			acceder al almacÃ©n.
	 */
	public List<X509Certificate> getSignCertificates() {
		if (iStore != null) {
			try {
				List<X509Certificate> certs = iStore.getSignCertificates();
				if (certs != null) {
					certs = UtilidadFirmaElectronica.filtraDNIe(certs);
				}
				return certs;
			} catch (CertStoreException ex) {
				LOG.error("Error accediendo a almacÃ©n de certificados: " + ex.getMessage());
				if (LOG.isDebugEnabled()) {
					LOG.debug(ex);
				}
			}

		}
		
		return null;
	}

	/**
	 * <p>Firma el documento XML indicado utilizando el certificado.</p>
	 * 
	 * @param cert Certificado con el que realizar la firma
	 * @param doc documento con el que realizar la firma
	 * @return devuelve el documento con la firma incluida
	 * @throws SigningException lanzada cuando se produce un error al intentar realizar la firma.
	 */
	public Document sign(final X509Certificate cert, Document doc) throws SigningException {
		if (iStore != null) {			
			Provider provider = iStore.getProvider(cert);
			try {
				if (provider != null) {
					Security.addProvider(provider);
				}
				
	            FirmaXML sxml = new FirmaXML();
	            
	            PrivateKey pk = iStore.getPrivateKey(cert);
	            
	            DataToSign dataToSign = new DataToSign();
	            dataToSign.setXadesFormat(xadesLevel);
	            dataToSign.setXAdESXType(XADES_X_TYPES.TYPE_1);
	            // Validador de confianza de certificados
	            if (truster == null) {
	            	if (LOG.isDebugEnabled()) {
	            		LOG.debug("No se ha encontrado el validador de confianza");
	            	}
	            }
	            
	            // Se establece el validador OCSP a utilizar
	            dataToSign.setCertStatusManager(new OCSPLiveConsultant(ocspServer, truster));
	            
	            // Se establece el esquema de firma
	            dataToSign.setEsquema(XAdESSchemas.valueOf(schema));
	            	            
	            // PolÃ­tica de firma
	            if (policy != null) {
	            	dataToSign.setAddPolicy(true);
		            dataToSign.setPolicyKey(policy);
	            }
	            
	            // Servidor de sellado de tiempo
	            if (tsaServer != null) {
		            dataToSign.setTimeStampGenerator(new HTTPTimeStampGenerator(tsaServer, UtilidadFirmaElectronica.DIGEST_ALG_SHA256));
	            }
	            	
	            dataToSign.setXMLEncoding("UTF-8");
	            // Se aÃ±ade un rol de firma
	            dataToSign.addClaimedRol(new SimpleClaimedRole("emisor"));
	            dataToSign.setEnveloped(true);
	            dataToSign.addObject(new ObjectToSign(new AllXMLToSign(), "Factura electrónica", null, "text/xml", null));
	            dataToSign.setDocument(doc);
	            
	            Object[] res = sxml.signFile(cert, dataToSign, pk, provider);

	            return (Document) res[0];
	        } catch (Throwable t) {
	            throw new SigningException(t);
	        }
	        finally {
	        	if (provider != null) {
	        		Security.removeProvider(provider.getName());
	        	}
	        }
		} else {
			throw new SigningException("No hay almacÃ©n para acceder a clave privada");
		}
	}

	/**
	 * Valida una firma XML.
	 * 
	 * <p>La informaciÃ³n provista en el mapa es:
	 * 	<ul>
	 * 		<li><code>sign.data</code>, <code>Data</code>: datos firmados.</li>
	 * 		<li><code>sign.certificate</code>, <code>X509Certificate</code>: certificado con el que se ha firmado.</li>
	 * 		<li><code>sign.date</code>, <code>Date</code>: fecha de la firma (no es sello de tiempo).</li>
	 * 		<li><code>sign.roles</code>, <code>List&lt;String&gt;</code>: roles aplicados a la firma.</li>
	 * 		<li><code>sign.xades.schema</code>, <code>String</code>: esquema XAdES de la firma. Valores posibles:
	 * 		<ul><li>1.2.2</li><li>1.3.2</li></ul></li>
	 * 		<li><code>sign.policy</code>, <code>String</code>: polÃ­tica asociada a la firma. Valores posibles:
	 * 		<ul><li>facturae30</li><li>facturae31</li></ul></li>
	 * 	</ul>
	 * </p>
	 * 
	 * @param doc Documento XML con la firma a validar
	 * @return Lista de mapas con un conjunto de informaciÃ³n sobre la firma
	 * @throws InvalidSignatureException lanzada cuando la firma es invÃ¡lida
	 */
	public List<Map<String, Object>> validate(final Document doc) throws InvalidSignatureException {			
		
		// Se prepara la estructura de salida
		Map<String, Object> resultado = new HashMap<String, Object>();
		List<Map<String, Object>> listaResultado = new ArrayList<Map<String, Object>>();
		
		// Se instancia la clase encargada de realizar la validaciÃ³n de la/s firma/s
		ValidarFirmaXML validator = new ValidarFirmaXML();
		try {
			// Se realiza la validaciÃ³n
			ArrayList<ResultadoValidacion> rv = validator.validar(doc, new File("./").getAbsolutePath(), null, null);

			// Se recorren todos los resultados obtenidos, uno por cada firma
			Iterator<ResultadoValidacion> it = rv.iterator();
			ResultadoValidacion result = it.next();

			if (!result.isValidate()) {
				throw new InvalidSignatureException("Firma invÃ¡lida");
			}

			// Se recuperan los datos firmados
			DatosFirma df = result.getDatosFirma();
			if (df != null) {
				resultado.put("sign.data", df);
			} else {
				throw new InvalidSignatureException("No se pudieron recuperar los datos de firma");
			}

			// Se recupera la fecha de firma
			Date date = df.getFechaFirma();
			if (date != null) {
				resultado.put("sign.date", date);
			}

			// Se recupera el rol de firma
			ArrayList<String> roles = df.getRoles();
			if ((roles != null) && (roles.size() > 0)) {
				resultado.put("sign.roles", roles);
			}

			// Se recupera el certificado firmante
			resultado.put("sign.certificate", df.getCadenaFirma().getCertificates().get(0));

			// Se recupera el esquema de firma
			switch (df.getEsquema()) {
			case XAdES_122:
				resultado.put("sign.xades.schema", "1.2.2");
				break;
			case XAdES_132:
				resultado.put("sign.xades.schema", "1.3.2");
				break;
			default:
			}

			// Se recuperan las polÃ­ticas de firma
			List<PolicyResult> policies = df.getPoliticas();
			if ((policies != null) && (policies.size() > 0)) {
				PolicyResult pr = policies.get(0);
				resultado.put("sign.policy", pr.getPolicyID());
			}
			
			listaResultado.add(resultado);

		} catch (FirmaXMLError ex) {
			throw new InvalidSignatureException("Error validando la firma: " + ex.getMessage(), ex);
		}
		
		return listaResultado;
	}

	/**
	 * Valida el certificado indicado mediante una consulta OCSP.
	 * 
	 * <p>El servidor al que se consulta el estado depende de la parametrizaciÃ³n indicada en la inicializaciÃ³n del Facade.</p>
	 * <p>Los parÃ¡metros de acceso vÃ­a proxy se obtienen mediante las variables de sistema de comunicaciones de java:
	 * <ul>
	 * 	<li><code>http.proxyHost</code>: host del proxy</li>
	 * 	<li><code>http.proxyPort</code>: puerto del proxy</li>
	 * </ul>
	 * En caso de necesitar credenciales las obtendrÃ¡ a travÃ©s de la clase Authenticator de java.</p>
	 * 
	 * @param cert Certificado que se desea validar 
	 * @throws InvalidCertificateException Lanzada cuando se produce un resultado no vÃ¡lido en la validaciÃ³n:
	 * <ul>
	 * 	<li>{@link InvalidCertificateException}: No se ha podido realizar la consulta o ha sucedido un error en su transcurso</li>
	 * 	<li>{@link UnknownCertificateException}: El certificado no es reconocido por la Autoridad de ValidaciÃ³n consultada</li>
	 * 	<li>{@link RevokedCertificateException}: El certificado estÃ¡ revocado</li>
	 * 	<li>{@link OCSPServerException}: La autoridad de ValidaciÃ³n no ha podido responder a la consulta (consulta malformada, error interno,
	 * 	base de datos caducada, etc.)</li>
	 * </ul>
	 * @see java.net.Authenticator
	 */
	public void validateCert(final X509Certificate cert) throws InvalidCertificateException {
		
		// Se obtiene el validador de confianza de certificados
        if (truster == null) {
        	if (LOG.isDebugEnabled()) {
        		LOG.debug("No se ha encontrado el validador de confianza configurado");
        	}
        	return;
        }
        
		ICertStatusRecoverer ocspCliente = new OCSPLiveConsultant(ocspServer, truster);
		try {
			ICertStatus respuesta = ocspCliente.getCertStatus(cert);
			if (respuesta == null) {
				throw new InvalidCertificateException("Error realizando la consulta OCSP: no se ha recuperado resultado");
			}
			
			if (respuesta != null) {
	            switch (respuesta.getStatus()) {
	            case valid:
	                System.out.println("El certificado consultado es vÃ¡lido.");
	                break;
	            case revoked:
	                System.out.println("El certificado consultado fue revocado el " + 
	                respuesta.getRevokedInfo().getRevokedDate() + ".");
	                throw new RevokedCertificateException();
	            default:
	                System.out.println("Se desconoce el estado del certificado.");
	            }
	        } 
		} catch (CertStatusException e) { 
			throw new InvalidCertificateException("Error No se puede recuperar el estado del certificado");
		} catch (IllegalArgumentException ex) {
			throw new InvalidCertificateException("Error realizando la consulta OCSP: " + ex.getMessage(), ex);
		}
	}
	
	/**
	 * Valida la cadena de certificados indicada mediante consultas OCSP.
	 * 
	 * <p>El servidor al que se consulta el estado depende de la parametrizaciÃ³n indicada en la inicializaciÃ³n del Facade.</p>
	 * <p>Los parÃ¡metros de acceso vÃ­a proxy se obtienen mediante las variables de sistema de comunicaciones de java:
	 * <ul>
	 * 	<li><code>http.proxyHost</code>: host del proxy</li>
	 * 	<li><code>http.proxyPort</code>: puerto del proxy</li>
	 * </ul>
	 * En caso de necesitar credenciales las obtendrÃ¡ a travÃ©s de la clase Authenticator de java.</p>
	 * 
	 * @param cert Certificado que se desea validar 
	 * @throws InvalidCertificateException Lanzada cuando se produce un resultado no vÃ¡lido en la validaciÃ³n:
	 * <ul>
	 * 	<li>{@link InvalidCertificateException}: No se ha podido realizar la consulta o ha sucedido un error en su transcurso</li>
	 * 	<li>{@link UnknownCertificateException}: El certificado no es reconocido por la Autoridad de ValidaciÃ³n consultada</li>
	 * 	<li>{@link RevokedCertificateException}: El certificado estÃ¡ revocado</li>
	 * 	<li>{@link OCSPServerException}: La autoridad de ValidaciÃ³n no ha podido responder a la consulta (consulta malformada, error interno,
	 * 	base de datos caducada, etc.)</li>
	 * </ul>
	 * @see java.net.Authenticator
	 */
	public void validateCertChain(final X509Certificate cert) throws InvalidCertificateException {
		
		// Se obtiene el validador de confianza de certificados
        if (truster == null) {
        	if (LOG.isDebugEnabled()) {
        		LOG.debug("No se ha encontrado el validador de confianza configurado");
        	}
        	return;
        }
        
		ICertStatusRecoverer ocspCliente = new OCSPLiveConsultant(ocspServer, truster);
		try {
			List<ICertStatus> respuesta = ocspCliente.getCertChainStatus(cert);
			if (respuesta == null) {
				throw new InvalidCertificateException("Error realizando la consulta OCSP: no se ha recuperado resultado");
			}
			
			ICertStatus result = null;
			for (int i = 0; i < respuesta.size(); ++i) {
				result = respuesta.get(i);
				if (result != null) {
					switch (result.getStatus()) {
					case valid:
						System.out.println("El certificado consultado es vÃ¡lido.");
						break;
					case revoked:
						System.out.println("El certificado consultado fue revocado el " + 
								result.getRevokedInfo().getRevokedDate() + ".");
						throw new RevokedCertificateException();
					default:
						System.out.println("Se desconoce el estado del certificado.");
					}
				} else {
					System.out.println("Hubo un error al contactar con el servidor OCSP " + ocspServer);
				}
			}
		} catch (CertStatusException e) { 
			throw new InvalidCertificateException("Error No se puede recuperar el estado del certificado");
		} catch (IllegalArgumentException ex) {
			throw new InvalidCertificateException("Error realizando la consulta OCSP: " + ex.getMessage(), ex);
		}
	}
}
