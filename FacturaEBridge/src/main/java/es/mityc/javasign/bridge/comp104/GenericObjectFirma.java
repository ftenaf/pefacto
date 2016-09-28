/**
 * Copyright 2013 Ministerio de Industria Energía y Turismo
 *
 * Este fichero es parte de "Facturae-Bridge".
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

package es.mityc.javasign.bridge.comp104;

import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Vector;

import org.w3c.dom.Document;

import es.mityc.firmaJava.libreria.errores.ClienteError;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.libreria.xades.DataToSign.XADES_X_TYPES;
import es.mityc.firmaJava.libreria.xades.errores.FirmaXMLError;
import es.mityc.firmaJava.role.SimpleClaimedRole;
import es.mityc.javasign.certificate.ocsp.OCSPLiveConsultant;
import es.mityc.javasign.trust.TrustAbstract;
import es.mityc.javasign.trust.TrustFactory;
import es.mityc.javasign.xml.refs.AllXMLToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;

/**
 * <p>Este objeto gestiona los accesos a almacenes de certificados no disponibles de manera directa en el bridge.</p>
 * 
 */
public class GenericObjectFirma {
	
	/** OCSP responder para la validación de certificados. */
	private static final String URL_OCSP = null;
	/** Acceso al almacén de certificados. */
	private IGenericStore store = null;
	/** Certificado con el que se realizará la firma. */
	private X509Certificate certificate = null;
	
	/**
	 * <p>Constructor.</p>
	 * @param genericStore Instancia que gestiona el acceso al almacén de certificados
	 */
	public GenericObjectFirma(IGenericStore genericStore) {
		this.store = genericStore;
	}

	/**
	 * <p>Devuelve los certificados disponibles en el almacén.</p>
	 * @param key Clave de tipo de almacén (sin uso en este gestionador)
	 * @return Vector con los certificados
	 * @throws FirmaXMLError lanzada si sucede algún error en el acceso al almacén
	 */
	public Vector<X509Certificate> getAllCertificates(final String key) throws FirmaXMLError {
		X509Certificate[] certs = store.getSignCertificates();
		Vector<X509Certificate> res = null;
		if ((certs != null) && (certs.length > 0)) {
			res = new Vector<X509Certificate>(certs.length);
			for (int i = 0; i < certs.length; i++) {
				res.add(certs[i]);
			}
		} 
		return res;
	}
	
	/**
	 * <p>Establece el certificado que se utilizará en las próximas firmas.</p>
	 * @param signCert Certificado de firma
	 */
	public void setSignCertificate(final X509Certificate signCert) {
		certificate = signCert;
	}

	/**
	 * <p>Devuelve el certificado establecido para la firma.</p>
	 * @return certificado establecido
	 */
	public X509Certificate getSignCertificate() {
		return certificate;
	}

	/**
	 * <p>Realiza la firma XAdES del documento indicado.</p>
	 * @param doc Documento al que se quiere agregar la firma
	 * @return Documento con la firma incluida
	 * @throws ClienteError Lanzada si sucede algún error al intentar calcular e incluir la firma
	 */
	public Document sign(Document doc) throws ClienteError {
		Provider provider = store.getProvider();
		try {
			if (provider != null) {
				Security.addProvider(provider);
			}
			
            FirmaXML sxml = new FirmaXML();
            
            PrivateKey pk = store.getPrivateKey(getSignCertificate());
            
            DataToSign dataToSign = new DataToSign();
            dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_XL);
            dataToSign.setXAdESXType(XADES_X_TYPES.TYPE_1);
            
            // Validador de confianza de certificados
            TrustAbstract truster = TrustFactory.getInstance().getTruster("my");
            if (truster == null) {
                System.out.println("No se ha encontrado el validador de confianza");
                //System.exit(-1);
            }
            
            // Se establece el validador OCSP a utilizar
            dataToSign.setCertStatusManager(new OCSPLiveConsultant(URL_OCSP, truster));
            
            // Se establece el esquema de firma
            dataToSign.setEsquema(XAdESSchemas.XAdES_132);
            dataToSign.setXMLEncoding("UTF-8");
            
            // Se añade un rol de firma
            dataToSign.addClaimedRol(new SimpleClaimedRole("Rol de firma"));
            dataToSign.setEnveloped(true);
            dataToSign.addObject(new ObjectToSign(new AllXMLToSign(), "Documento de ejemplo", null, "text/xml", null));
            dataToSign.setDocument(doc);
            
            Object[] res = sxml.signFile(getSignCertificate(), dataToSign, pk, provider);

            return (Document) res[0];
        } catch (Throwable t) {
            throw new ClienteError(t);
        }
        finally {
        	if (provider != null) {
        		Security.removeProvider(provider.getName());
        	}
        }
	}
}
