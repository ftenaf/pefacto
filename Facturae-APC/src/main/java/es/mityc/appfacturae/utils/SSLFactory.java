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

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ResourceBundle;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.ui.dialogs.TrustSMTPServerCertificateDialog;
import es.mityc.appfacturae.utils.constants.Constants;

public class SSLFactory extends SSLSocketFactory {
	
	private SSLSocketFactory intern;
	private static Log logger = LogFactory.getLog(SSLFactory.class);
	
	private SSLFactory(SSLSocketFactory internalFactory) {
		this.intern = internalFactory;
	}
	
	public static SocketFactory getDefault() {
		
		TrustManager[] trustCerts = new TrustManager[] {
				new X509TrustManager(){
					public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {}
					public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
						// arg0 --> certification chain the mail server provide
						// arg1 --> it returns a encryption algorithm to use with certificate associated key
						TrustSMTPServerCertificateDialog j = new TrustSMTPServerCertificateDialog(null, 
								true, 
								"TrustSMTPServerWindowTitle", 
								arg0);
						j.setVisible(true);
						if (!j.getResult())
							throw new CertificateException(ResourceBundle.getBundle("language/lang", Constants.LANG.getLocale()).getString("NOOKMessageTrustCertificate"));
					}
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return new java.security.cert.X509Certificate[0]; // The certificates root is returned instead of nothing
					}
				}
		};

		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustCerts, null);
			SSLSocketFactory factory = sslContext.getSocketFactory();
			return new SSLFactory(factory);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error during SSLFactory built: " + e.getMessage());
			return null;
		} catch (KeyManagementException e) {
			logger.error("Error during SSLFactory built: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Socket createSocket(Socket arg0, String arg1, int arg2, boolean arg3) throws IOException {
		return intern.createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return intern.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return intern.getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(String arg0, int arg1) throws IOException, UnknownHostException {
		return intern.createSocket(arg0, arg1);
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1) throws IOException {
		return intern.createSocket(arg0, arg1);
	}

	@Override
	public Socket createSocket(String arg0, int arg1, InetAddress arg2, int arg3) throws IOException, UnknownHostException {
		return intern.createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public Socket createSocket(InetAddress arg0, int arg1, InetAddress arg2, int arg3) throws IOException {
		return intern.createSocket(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public Socket createSocket() throws IOException {
		return intern.createSocket();
	}
}