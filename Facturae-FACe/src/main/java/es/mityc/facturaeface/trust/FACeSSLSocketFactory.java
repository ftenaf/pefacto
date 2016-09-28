/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-FAce".
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
package es.mityc.facturaeface.trust;

import java.io.IOException;
import java.util.Hashtable;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.axis.components.net.JSSESocketFactory;
import org.apache.axis.components.net.SecureSocketFactory;

public class FACeSSLSocketFactory extends JSSESocketFactory implements SecureSocketFactory {
    /**
     * Constructor SSLSocketFactory
     *
     * @param attributes
     */
    @SuppressWarnings("rawtypes")
    public FACeSSLSocketFactory(Hashtable attributes) {
        super(attributes);
    }

    /**
     * Initialises SSL Context
     *
     * This overrides the parent class to provide our SocketFactory implementation.
     * @throws IOException
     */
    @Override
   protected void initFactory() throws IOException {

        try {
            SSLContext context = getContext();
            sslFactory = context.getSocketFactory();
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw (IOException) e;
            }
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Gets a custom SSL Context. 
     * 
     * Initialise a SSLContext
     *
     * @return SSLContext
     * @throws WebServiceClientConfigException 
     * @throws Exception
     */
    protected SSLContext getContext() throws Exception 
    {
       try 
       {
           SSLContext sslContext = SSLContext.getInstance("SSL");      
           sslContext.init(null, getTrustManager(), new java.security.SecureRandom());
    
           return sslContext;
       } 
       catch (Exception e)
       {
           throw new Exception("Error creating context for SSLSocket!", e);
       }
    }
    
    /**
     *  Create a trust manager that trusts all certificates
     *  It is not using a particular keyStore
     */
     protected TrustManager[] getTrustManager()
     {
       TrustManager[] trustAllCerts = new TrustManager[]
        { 
               new AllFACeTrustedManager() 
        }; 
           
        return trustAllCerts;
     }

}
