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

package es.mityc.facturaeface.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.util.Properties;

import javax.security.cert.CertificateException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.SOAPPart;
import org.apache.axis.handlers.BasicHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.CredentialException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import es.mityc.facturaeface.FACeConfig;
import es.mityc.facturaeface.FACeConfig.FACeCertificateProvider;
import es.mityc.facturaeface.dnie.DnieProviderWrapper;

public class FACeClientHandler extends BasicHandler {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1190816926923566427L;
	private static final Log LOG = LogFactory.getLog(FACeClientHandler.class);

	private boolean needsValidation = false;
	
	public FACeClientHandler(boolean needsValidation) {
		super();
		this.needsValidation = needsValidation; 
	}
	
	public void invoke(MessageContext msgContext) throws AxisFault {
        SOAPMessage msg, secMsg;
        Document doc = null;
        //secMsg = null;
        try {
            if (needsValidation) {
	            //Obtenciï¿½n del documento XML que representa la peticiï¿½n SOAP
	            msg = msgContext.getCurrentMessage();
	            doc = ((org.apache.axis.message.SOAPEnvelope) msg.getSOAPPart().getEnvelope()).getAsDocument();
	            secMsg = this.createBinarySecurityToken(doc);    
	            msgContext.setProperty(org.apache.axis.client.Call.CHECK_MUST_UNDERSTAND, Boolean.FALSE);
	            if(LOG.isDebugEnabled()) {
	                String textRequest = "";
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                secMsg.writeTo(baos);
	                textRequest = new String(baos.toByteArray());
	                LOG.debug("secMSG: " + textRequest);
	            }
	            
	            //Modificaciï¿½n de la peticiï¿½n SOAP
	            ((SOAPPart) msgContext.getRequestMessage().getSOAPPart()).setCurrentMessage(secMsg.getSOAPPart().getEnvelope(), SOAPPart.FORM_SOAPENVELOPE);
            } else {
	            if(LOG.isDebugEnabled()) {
	                String textRequest = "";
	                ByteArrayOutputStream baos = new ByteArrayOutputStream();
	                msgContext.getMessage().writeTo(baos);
	                textRequest = new String(baos.toByteArray());
	                LOG.debug("MSG: " + textRequest);
	            }            	
            }
        } catch (WSSecurityException wse) {
            LOG.error("Error accediendo a almacen: " + wse.getMessage(), wse);
            if(wse.getCause() instanceof UnrecoverableKeyException) {
            	UnrecoverableKeyException ex = new UnrecoverableKeyException("Contraseña incorrecta");
            	ex.setStackTrace(wse.getCause().getStackTrace());
            	throw AxisFault.makeFault(ex);
            } else {
            	throw AxisFault.makeFault((Exception)wse.getCause());
            }
        } catch (Exception e) {
            LOG.error("Error en llamada a webservice: " + e.getMessage(), e);
            throw AxisFault.makeFault(e);
         }
	}

   /**
    * Securiza, mediante el tag BinarySecurityToken y firma, una peticiï¿½n SOAP no securizada.
    * @param soapEnvelopeRequest Documento xml que representa la peticiï¿½n SOAP sin securizar.
    * @return Un mensaje SOAP que contiene la peticiï¿½n SOAP de entrada securizada
    * mediante el tag BinarySecurityToken.
    * @throws TransformerFactoryConfigurationError
    * @throws TransformerException
    * @throws TransformerConfigurationException
    * @throws SOAPException
    * @throws IOException
    * @throws KeyStoreException
    * @throws CredentialException
    * @throws CertificateException
    * @throws NoSuchAlgorithmException
    */
    private SOAPMessage createBinarySecurityToken(Document soapEnvelopeRequest) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException, SOAPException, KeyStoreException, CredentialException, NoSuchAlgorithmException, CertificateException {
        ByteArrayOutputStream baos;
        Crypto crypto;
        Document secSOAPReqDoc;
        DOMSource source;
        Element element;
        StreamResult streamResult;
        String secSOAPReq;
        SOAPMessage res = null;
        WSSecSignature wsSecSignature;
        WSSecHeader wsSecHeader;
        crypto = null;
        wsSecHeader = null;
        wsSecSignature = null;
        //Inserciï¿½n del tag wsse:Security y BinarySecurityToken
        wsSecHeader = new WSSecHeader(null, false);
        wsSecSignature = new WSSecSignature();
        try {
        	crypto = CryptoFactory.getInstance(this.initializateCryptoProperties());
        } catch (RuntimeException e){
        	if(FACeConfig.getInstance().getCertificateProvider().equals(FACeCertificateProvider.DNIE)) {
        		throw new CertificateException("Tarjeta no preparada");
        	} else {
        		throw e;
        	}
        }
        //Indicaciï¿½n para que inserte el tag BinarySecurityToken
        wsSecSignature.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
        String alias = FACeConfig.getInstance().getCertificateAlias();
        switch(FACeConfig.getInstance().getCertificateProvider()) {
	        case WINDOWS:
	        	String[] aliases = crypto.getAliasesForDN(alias);
	        	if(aliases.length == 0) {
	        		throw new CertificateException("Bad Certificate");
	        	}
	        	alias = aliases[aliases.length - 1];
	        	break;
	        case DNIE:
	        	alias = "CertFirmaDigital";
	        	break;
        }
        wsSecSignature.setUserInfo(alias, new String(FACeConfig.getInstance().getCertificatePassword()));
        wsSecHeader.insertSecurityHeader(soapEnvelopeRequest);
        wsSecSignature.prepare(soapEnvelopeRequest, crypto, wsSecHeader);
        //Modificaciï¿½n y firma de la peticiï¿½n
        secSOAPReqDoc = wsSecSignature.build(soapEnvelopeRequest, crypto, wsSecHeader);
        element = secSOAPReqDoc.getDocumentElement();
        //Transformaciï¿½n del elemento DOM a String
        source = new DOMSource(element);
        baos = new ByteArrayOutputStream();
        streamResult = new StreamResult(baos);
        TransformerFactory.newInstance().newTransformer().transform(source, streamResult);
        secSOAPReq = new String(baos.toByteArray());
        if(LOG.isDebugEnabled()) {
            LOG.debug("secSOAPReq: " + secSOAPReq);
        }
        //Creaciï¿½n de un nuevo mensaje SOAP a partir del mensaje SOAP securizado formado
        MessageFactory mf = new org.apache.axis.soap.MessageFactoryImpl();
        res = mf.createMessage(null, new ByteArrayInputStream(secSOAPReq.getBytes()));
        return res;
    }
    
    /**
     * Establece el conjunto de propiedades con el que serï¿½ inicializado el gestor criptogrï¿½fico de WSS4J.
     * @return Devuelve el conjunto de propiedades con el que serï¿½ inicializado el gestor criptogrï¿½fico de WSS4J.
     */
    private Properties initializateCryptoProperties() {
       Properties res = new Properties();
       res.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
       
       //CONFIGURAR KEYSTORE
       switch(FACeConfig.getInstance().getCertificateProvider()) {
	       case WINDOWS:
	           es.mityc.javasign.pkstore.mscapi.mityc.SunMSCAPI_MITyC providerWindows = new es.mityc.javasign.pkstore.mscapi.mityc.SunMSCAPI_MITyC();
	           Security.addProvider(providerWindows);
	           res.setProperty("org.apache.ws.security.crypto.merlin.keystore.provider" , providerWindows.getName());
		       res.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", "Windows-MY");
		       res.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", "");
	    	   break;
	       case DNIE:
	           DnieProviderWrapper providerDnie = new DnieProviderWrapper();
	           Security.addProvider(providerDnie);
	           res.setProperty("org.apache.ws.security.crypto.merlin.keystore.provider" , providerDnie.getName());
		       res.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", "DNIw");
		       //res.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", "");	    	   
	    	   break;
    	   default:
    	       res.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", "JCEKS");
    	       res.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", new String(FACeConfig.getInstance().getStorePassword()));
    		   break;
       }
       
       res.setProperty("org.apache.ws.security.crypto.merlin.file", FACeConfig.getInstance().getStoreFile());
       return res;
    }    
}
