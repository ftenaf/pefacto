/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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
package es.mityc.facturaeface;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.Security;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.facturaeface.FACeConfig.FACeCertificateProvider;
import es.mityc.facturaeface.bean.SSPPEstados;
import es.mityc.facturaeface.bean.SSPPFactura;
import es.mityc.facturaeface.bean.SSPPResultadoAnularFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarUnidades;
import es.mityc.facturaeface.bean.SSPPResultadoEnviarFactura;
import es.mityc.facturaeface.bean.SSPPUnidadDir;
import es.mityc.facturaeface.dnie.DnieProviderWrapper;
import es.mityc.facturaeface.exception.FACeException;
import es.mityc.facturaeface.service.SSPPWebServiceProxyBindingStub;
import es.mityc.facturaeface.service.SSPPWebServiceProxyPort;
import es.mityc.facturaeface.service.SSPPWebServiceProxyServiceLocator;

public class FACeConnector {
	private static final Log LOG = LogFactory.getLog(FACeConnector.class);
	
	public FACeConnector(String urlFACeEndpoint, String certificateAlias, char[] certificatePassword, boolean useProxy,
		String proxyHost, String proxyPort, boolean authProxy, String proxyUser, String proxyPassword,
		String storeFile, char[] storePassword, FACeCertificateProvider certificateProvider, int timeoutMilis) {
		LOG.info("Creando conector con "+urlFACeEndpoint+".");
		FACeConfig.getInstance().setCertificateAlias(certificateAlias);
		FACeConfig.getInstance().setCertificatePassword(certificatePassword);
		FACeConfig.getInstance().setUrlFACeEndpoint(urlFACeEndpoint);
		FACeConfig.getInstance().setUseProxy(useProxy);
		FACeConfig.getInstance().setProxyHost(proxyHost);
		FACeConfig.getInstance().setProxyPort(proxyPort);
		FACeConfig.getInstance().setAuthProxy(authProxy);		
		FACeConfig.getInstance().setProxyUser(proxyUser);
		FACeConfig.getInstance().setProxyPassword(proxyPassword);
		FACeConfig.getInstance().setStoreFile(storeFile);
		FACeConfig.getInstance().setStorePassword(storePassword);
		FACeConfig.getInstance().setCertificateProvider(certificateProvider);
		FACeConfig.getInstance().setFACETimeoutMilis(timeoutMilis);
	}
	
	public SSPPResultadoEnviarFactura enviarFactura(SSPPFactura request) throws FACeException {
		try {
			if (request != null
				&& request.getFichero_factura() != null
				&& (request.getFichero_factura().getMime() == null || request.getFichero_factura().getMime().length() == 0)) {
				request.getFichero_factura().setMime(ConstantsFACe.INVOICE_MIME_TYPE);
			}
			SSPPWebServiceProxyBindingStub webservice = new SSPPWebServiceProxyBindingStub(new URL(FACeConfig.getInstance().getUrlFACeEndpoint()), null);
			return webservice.enviarFactura(request);
		} catch (AxisFault e) {
			LOG.error("Respuesta de error: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			LOG.error("Dirección Erronea: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (RemoteException e) {
			LOG.error("Error remoto: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		}
    }
	
	public SSPPResultadoConsultarFactura consultarFactura(String numeroRegistro) throws FACeException {
		try {
			SSPPWebServiceProxyBindingStub webservice = new SSPPWebServiceProxyBindingStub(new URL(FACeConfig.getInstance().getUrlFACeEndpoint()), null);
			return webservice.consultarFactura(numeroRegistro);
		} catch (AxisFault e) {
			LOG.error("Respuesta de error: " + e.getMessage(), e);
			throw new FACeException(e.getFaultString(), e);
		} catch (MalformedURLException e) {
			LOG.error("Dirección Erronea: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (RemoteException e) {
			LOG.error("Error remoto: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		}
	}
	
	public SSPPResultadoAnularFactura anularFactura(String numeroRegistro, String motivo) throws FACeException {
		try {
			SSPPWebServiceProxyBindingStub webservice = new SSPPWebServiceProxyBindingStub(new URL(FACeConfig.getInstance().getUrlFACeEndpoint()), null);
			return webservice.anularFactura(numeroRegistro, motivo);
		} catch (AxisFault e) {
			LOG.error("Respuesta de error: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			LOG.error("Dirección Erronea: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (RemoteException e) {
			LOG.error("Error remoto: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		}
	}
	
	public SSPPEstados consultarEstados() throws FACeException {
		try {
			SSPPWebServiceProxyBindingStub webservice = new SSPPWebServiceProxyBindingStub(new URL(FACeConfig.getInstance().getUrlFACeEndpoint()), null);
			return webservice.consultarEstados();
		} catch (AxisFault e) {
			LOG.error("Respuesta de error: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			LOG.error("Direcciï¿½n Erronea: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (RemoteException e) {
			LOG.error("Error remoto: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		}
	}
	
	public  SSPPResultadoConsultarUnidades consultarUnidades() throws FACeException {
		try {
			SSPPWebServiceProxyPort webservice = new SSPPWebServiceProxyServiceLocator()
				.getSSPPWebServiceProxyPort(new URL(FACeConfig.getInstance().getUrlFACeEndpoint()));
			return webservice.consultarUnidades();
		} catch (AxisFault e) {
			LOG.error("Respuesta de error: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			LOG.error("Direcciï¿½n Erronea: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (RemoteException e) {
			LOG.error("Error remoto: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (ServiceException e) {
			LOG.error("Error en servicio: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		}
	}

	public void finish() {
	       switch(FACeConfig.getInstance().getCertificateProvider()) {
		       case WINDOWS:
		           es.mityc.javasign.pkstore.mscapi.mityc.SunMSCAPI_MITyC providerWindows = new es.mityc.javasign.pkstore.mscapi.mityc.SunMSCAPI_MITyC();
		           Security.removeProvider(providerWindows.getName());
		       case DNIE:
		           DnieProviderWrapper providerDnie = new DnieProviderWrapper();
		           Security.removeProvider(providerDnie.getName());
		           Security.removeProvider("BC");
		       case JAVA:
		           Security.removeProvider("BC");
	       }
	}
	
	public  SSPPUnidadDir[] consultarAdministraciones() throws FACeException {
		try {
			SSPPWebServiceProxyPort webservice = new SSPPWebServiceProxyServiceLocator()
				.getSSPPWebServiceProxyPort(new URL(FACeConfig.getInstance().getUrlFACeEndpoint()));
			return webservice.consultarAdministraciones();
		} catch (AxisFault e) {
			LOG.error("Respuesta de error: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			LOG.error("Direcciï¿½n Erronea: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (RemoteException e) {
			LOG.error("Error remoto: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (ServiceException e) {
			LOG.error("Error en servicio: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		}
	}
	
	public  SSPPResultadoConsultarUnidades consultarUnidadesPorAdministracion(String codigoDir) throws FACeException {
		try {
			SSPPWebServiceProxyPort webservice = new SSPPWebServiceProxyServiceLocator()
				.getSSPPWebServiceProxyPort(new URL(FACeConfig.getInstance().getUrlFACeEndpoint()));
			return webservice.consultarUnidadesPorAdministracion(codigoDir);
		} catch (AxisFault e) {
			LOG.error("Respuesta de error: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (MalformedURLException e) {
			LOG.error("Direcciï¿½n Erronea: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (RemoteException e) {
			LOG.error("Error remoto: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		} catch (ServiceException e) {
			LOG.error("Error en servicio: " + e.getMessage(), e);
			throw new FACeException(e.getMessage(), e);
		}
	}	
	
}
