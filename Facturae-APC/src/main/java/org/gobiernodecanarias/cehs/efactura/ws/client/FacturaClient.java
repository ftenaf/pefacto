
package org.gobiernodecanarias.cehs.efactura.ws.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataHandler;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import org.gobiernodecanarias.ceh.efactura.ws.v2.Anexo;
import org.gobiernodecanarias.ceh.efactura.ws.v2.FacturaExtendidaV2;
import org.gobiernodecanarias.ceh.efactura.ws.v2.FacturaService;
import org.gobiernodecanarias.ceh.efactura.ws.v2.FacturaTupla;
import org.gobiernodecanarias.ceh.efactura.ws.v2.Factura_Service;
import org.gobiernodecanarias.ceh.efactura.ws.v2.RespuestaConsultaGrupoFactura;
import org.gobiernodecanarias.ceh.efactura.ws.v2.RespuestaEstadoFactura;
import org.gobiernodecanarias.ceh.efactura.ws.v2.ServicioError_Exception;

import es.mityc.facturaeface.ProxyAuthenticator;


/**
 *
 * @author vsuarez
 */
public class FacturaClient implements FacturaService {
    KeyStore keyStore;
    char[] keyStorePass;
    String clientAlias;
    org.gobiernodecanarias.ceh.efactura.ws.v2.FacturaService port;
    boolean useProxy = false;
    String portUrl;
    String proxyHost;
    String proxyPort;
    boolean proxyAuth = false;
    String proxyUser;
    String proxyPasswd;
    

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public char[] getKeyStorePass() {
        return keyStorePass;
    }

    public String getClientAlias() {
        return clientAlias;
    }

    public String getPortUrl() {
        return portUrl;
    }
    
    public String getProxyHost() {
		return proxyHost;
	}
    
    public String getProxyPort() {
		return proxyPort;
	}
    
    public String getProxyUser() {
		return proxyUser;
	}
    
    public String getProxyPasswd() {
		return proxyPasswd;
	}

    public FacturaClient(KeyStore keyStore, char[] keyStorePass, String clientAlias, String portUrl, boolean useProxy, String proxyHost, String proxyPort, boolean proxyAuth, String proxyUser, String proxyPasswd) {
        this.keyStore = keyStore;
        this.keyStorePass = keyStorePass;
        this.clientAlias = clientAlias;
        this.useProxy = useProxy;
        this.portUrl = portUrl;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyAuth = proxyAuth;
        this.proxyUser = proxyUser;
        this.proxyPasswd = proxyPasswd;
    }
    
    private FacturaService getPort() {
        if (port == null) {
            Factura_Service service = new Factura_Service();
            port = service.getFacturaServicePort();
            
            BindingProvider bindingProvider = (BindingProvider) port;
            SSLSocketFactory sslSocketFactory = getSslSocketFactory(keyStore, keyStorePass, clientAlias);
            if(useProxy) {
            	System.setProperty("http.proxySet", "true");
	            System.setProperty("https.proxyHost", proxyHost);
	            System.setProperty("https.proxyPort", proxyPort);
	            if(proxyAuth) {
	            	Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPasswd));
	            } else {
	            	Authenticator.setDefault(null);
	            }
            }
            bindingProvider.getRequestContext().put(
                    "com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory", sslSocketFactory);
            bindingProvider.getRequestContext().put(
                    "com.sun.xml.internal.ws.transport.https.client.hostname.verifier", 
                    new HostnameVerifier() {

                @Override
                public boolean verify(String string, SSLSession ssls) {
                    return true;
                }
            });

            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, portUrl);
        }
        return port;
    }
    
    public void addHandlers(Handler... handler) {
        BindingProvider bindingProvider = (BindingProvider) getPort();
        List<Handler> handlers = bindingProvider.getBinding().getHandlerChain();
        handlers.addAll(Arrays.asList(handler));
        bindingProvider.getBinding().setHandlerChain(handlers);
    }
    
    public static KeyStore loadKeyStoreFromPkcs12(String path, char[] password) {
        try {
            FileInputStream fis = new FileInputStream(path);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, password);
            return ks;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static KeyStore loadKeyStoreFromJks(String path, char[] password) {
        try {
            FileInputStream fis = new FileInputStream(path);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(fis, password);
            return ks;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }

    public static KeyStore loadKeyStoreFormJceks(String path, char []password) {
    	KeyStore ks = null;
    	try {
    		FileInputStream fis = new FileInputStream(path);
			ks = KeyStore.getInstance("JCEKS");
			ks.load(fis, password);
			fis.close();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		return ks;
    }
    
    public static KeyStore loadKeyStoreFormIE(String keyStoreId, String []password) 
    		throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    	KeyStore ks = null;
		ks = KeyStore.getInstance(keyStoreId);
		ks.load(null, null);
    	return ks;
    }
    
    protected SSLSocketFactory getSslSocketFactory(final KeyStore keyStore,
            final char[] keyStorePass, final String clientAlias) {
        try {
            KeyManager[] keyManagers = null;

            if (keyStore != null) {
                if (clientAlias == null) {
                    KeyManagerFactory keyManagerFactory
                            = KeyManagerFactory.getInstance(KeyManagerFactory
                                    .getDefaultAlgorithm());
                    keyManagerFactory.init(keyStore, keyStorePass);
                    keyManagers = keyManagerFactory.getKeyManagers();
                } else {
                    keyManagers = new KeyManager[]{
                        new X509KeyManager() {

                            @Override
                            public String[] getClientAliases(String string,
                                    Principal[] prncpls) {
                                return new String[]{clientAlias};
                            }

                            @Override
                            public String chooseClientAlias(String[] strings,
                                    Principal[] prncpls,
                                    Socket socket) {
                                return clientAlias;
                            }

                            @Override
                            public String[] getServerAliases(String string,
                                    Principal[] prncpls) {
                                throw new UnsupportedOperationException("Not supported yet.");
                            }

                            @Override
                            public String chooseServerAlias(String string,
                                    Principal[] prncpls,
                                    Socket socket) {
                                throw new UnsupportedOperationException("Not supported yet.");
                            }

                            @Override
                            public X509Certificate[] getCertificateChain(String alias) {
                                try {
                                    Certificate[] certs = keyStore.getCertificateChain(alias);
                                    if (certs == null || certs.length == 0) {
                                        return null;
                                    }
                                    X509Certificate[] result = new X509Certificate[certs.length];
                                    for (int i = 0; i < certs.length; i++) {
                                        result[i] = (X509Certificate) certs[i];
                                    }
                                    return result;
                                } catch (KeyStoreException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            @Override
                            public PrivateKey getPrivateKey(String string) {
                                try {
                                    return (PrivateKey) keyStore.getKey(clientAlias, keyStorePass);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };
                }
            }

            TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] xcs,
                            String string)
                            throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] xcs,
                            String string)
                            throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagers, trustManagers, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return sslSocketFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FacturaExtendidaV2 enviarFactura(DataHandler facturae, List<Anexo> anexos) throws ServicioError_Exception {
    	
        return getPort().enviarFactura(facturae, anexos);
    }

    @Override
    public FacturaExtendidaV2 consultarFactura(long idFactura) throws ServicioError_Exception {
        return getPort().consultarFactura(idFactura);
    }

    @Override
    public FacturaExtendidaV2 consultarFacturaPorValores(String nifcifProveedor, int ejercicio, String serie, String numeroFactura) throws ServicioError_Exception {
        return getPort().consultarFacturaPorValores(nifcifProveedor, ejercicio, serie, numeroFactura);
    }

    @Override
    public FacturaExtendidaV2 anularFactura(long idFactura, String motivoAnulacion) throws ServicioError_Exception {
        return getPort().anularFactura(idFactura, motivoAnulacion);
    }

    @Override
    public FacturaExtendidaV2 anularFacturaPorValores(String nifcifProveedor, int ejercicio, String serie, String numeroFactura, String motivoAnulacion) throws ServicioError_Exception {
        return getPort().anularFacturaPorValores(nifcifProveedor, ejercicio, serie, numeroFactura, motivoAnulacion);
    }

    @Override
    public String version() {
        return getPort().version();
    }

	@Override
	public RespuestaEstadoFactura consultarEstadoMotivoFactura(long idFactura) throws ServicioError_Exception {
		return getPort().consultarEstadoMotivoFactura(idFactura);
	}

	@Override
	public RespuestaEstadoFactura consultarEstadoMotivoFacturaPorValores(String nifcifProveedor, int ejercicio,
			String serie, String numeroFactura) throws ServicioError_Exception {
		return getPort().consultarEstadoMotivoFacturaPorValores(nifcifProveedor, ejercicio, serie, numeroFactura);
	}

	@Override
	public List<RespuestaConsultaGrupoFactura> consultarGrupoEstadosFactura(List<Long> facturaId) {
		return getPort().consultarGrupoEstadosFactura(facturaId);
	}

	@Override
	public List<RespuestaConsultaGrupoFactura> consultarGrupoEstadoFacturasPorValores(List<FacturaTupla> facturaTupla) {
		return getPort().consultarGrupoEstadoFacturasPorValores(facturaTupla);
	}
}
