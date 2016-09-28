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

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.Base64;

public class SendUtil {
	
	private static Log logger = LogFactory.getLog(SendUtil.class);
	
    public static boolean send(String mailDest, String mailSubject, String msg, File xml) {

    	try {
    		Properties p = new Properties();
    		Authenticator auth = null;

    		// Proxy configuration
    		if (!"0.0.0.0".equals(Constants.CONFIG_PROP.getProperty("proxy"))) {
    			p.setProperty("proxySet", "true");
    			p.setProperty("ProxyHost", Constants.CONFIG_PROP.getProperty("proxy")); // Also, key name could be "http.proxyHost"
    			p.setProperty("ProxyPort", Constants.CONFIG_PROP.getProperty("proxyPort").toString());// Also, key name could be "http.proxyPort"

    			if (Boolean.parseBoolean(Constants.CONFIG_PROP.getProperty("proxyAuth").toString()))
    				auth = new Authenticator() {
    				protected PasswordAuthentication getPasswordAuthentication() {
    					return new PasswordAuthentication(Constants.CONFIG_PROP.getProperty("proxyUser").toString(), Constants.CONFIG_PROP.getProperty("proxyPwd").toString());
    				}
    			};
    			logger.info("Proxy configuration set: " + Constants.CONFIG_PROP.getProperty("proxy"));
    		}		

    		// SMTP server configuration
    		p.put("mail.smtp.host", Constants.CONFIG_PROP.getProperty("SMTPServer"));
    		p.put("mail.smtp.port", Constants.CONFIG_PROP.getProperty("mailPort"));
    		p.put("mail.from", Constants.CONFIG_PROP.getProperty("EMail"));
    		logger.info("Server configuration set: " + Constants.CONFIG_PROP.getProperty("SMTPServer") + ":" + Constants.CONFIG_PROP.getProperty("Port") + ", Mail from: " + Constants.CONFIG_PROP.getProperty("EMail"));
    		if (Boolean.parseBoolean(Constants.CONFIG_PROP.getProperty("SSL").toString())) {
    			p.put("mail.smtps.starttls.enable", "true");
    			p.put("mail.smtps.socketFactory.class", "es.mityc.appfacturae.utils.SSLFactory");
    			p.put("mail.smtps.ssl.protocols", "SSLv3 TLSv1");
    			logger.info("SSL checked");
    		}
    		
    		Session s = Session.getDefaultInstance(p, auth);
    		s.setDebug(true);
    		if (Boolean.parseBoolean(Constants.CONFIG_PROP.getProperty("Authentication"))) {
    			s.setPasswordAuthentication(
    					new URLName(Constants.CONFIG_PROP.getProperty("SMTPServer")),
    					new PasswordAuthentication(Constants.CONFIG_PROP.getProperty("mailUser"), new String(Base64.decode(Constants.CONFIG_PROP.getProperty("mailPwd"))))
    			);
    			logger.info("Authentication is required and the name and password are specified");
    		}

    		MimeMessage message = new MimeMessage(s);
    		try {
    			// Message header
    			message.setFrom(new InternetAddress(Constants.CONFIG_PROP.getProperty("EMail"))); // Sender email
    			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailDest)); // receiver email
    			message.setSubject(mailSubject); // Email subject

    			// Message body
    			MimeMultipart mm = new MimeMultipart();
    			MimeBodyPart mbpMsg = new MimeBodyPart();
    			mbpMsg.setContent(msg,"text/html");

    			// Message attach
    			MimeBodyPart mbpAtt = new MimeBodyPart();
    			mbpAtt.attachFile(xml);

    			// Message is build
    			mm.addBodyPart(mbpMsg);
    			mm.addBodyPart(mbpAtt);
    			message.setContent(mm);
    			message.setSentDate(new java.util.Date());
    			message.saveChanges();
    		}
    		catch(MessagingException e) {
    			logger.error("Error creating the message to send: " + e.getMessage());
    			Constants.DIALOG.showErrorSend(e.getMessage());
    			return false;
    			
    		} catch (IOException e) {
    			logger.error("Error creating the message to send: " + e.getMessage());
    			Constants.DIALOG.showErrorSend(e.getMessage());
    			return false;
    		}

    		// Message is sent
    		try {
    			Transport transport = null;
    			if (Boolean.parseBoolean(Constants.CONFIG_PROP.getProperty("SSL").toString())){
    				transport = s.getTransport("smtps");
    				logger.info("Transport used : smtps");
    			} else {
    				transport = s.getTransport("smtp");
    				logger.info("Transport used : smtp");
    			}
    			
    			try {
    				logger.info("Conecting to " + Constants.CONFIG_PROP.getProperty("SMTPServer") + ":" + Constants.CONFIG_PROP.getProperty("mailPort") + ", User: " + Constants.CONFIG_PROP.getProperty("mailUser"));
    				transport.connect(Constants.CONFIG_PROP.getProperty("SMTPServer"), Integer.parseInt(Constants.CONFIG_PROP.getProperty("mailPort")), Constants.CONFIG_PROP.getProperty("mailUser"), new String(Base64.decode(Constants.CONFIG_PROP.getProperty("mailPwd"))));
    			} catch (AuthenticationFailedException afe) {
    				logger.error("Error connecting. Authentication failed: " + afe.getMessage());
        			Constants.DIALOG.showErrorSend(afe.getMessage());
    			}
    			try { 
    				transport.sendMessage(message, message.getAllRecipients());
    			} catch(SendFailedException e) {
    				logger.error("Error sending the message: " + e.getMessage());
    				Constants.DIALOG.showErrorSend(e.getMessage());
    			}

    			transport.close();
    		}
    		catch(NoSuchProviderException e) {     
    			logger.error("Error looking for provider: " + e.getMessage());
    			Constants.DIALOG.showErrorSend(e.getMessage());
    			return false;
    		} 
    	} catch(MessagingException e) {
    		logger.error("General error sending the message: " + e.getMessage());
    		Constants.DIALOG.showErrorSend(e.getMessage());
    		return false;
    	}
    	
    	return true;
    }
}