/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.Base64;
import es.mityc.facturaeface.FACeConfig.FACeCertificateProvider;
import es.mityc.facturaeface.FACeConnector;
import es.mityc.facturaeface.bean.SSPPFactura;
import es.mityc.facturaeface.bean.SSPPFicheroAnexo;
import es.mityc.facturaeface.bean.SSPPFicheroFactura;
import es.mityc.facturaeface.bean.SSPPOrganoGestorUnidadTramitadora;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarUnidades;
import es.mityc.facturaeface.bean.SSPPResultadoEnviarFactura;
import es.mityc.facturaeface.bean.SSPPUnidadDir;
import es.mityc.facturaeface.exception.FACeException;
import es.mityc.javasign.pkstore.DefaultPassStoreKS;

public class FACeUtils {

	private static FACeUtils instance = null;
	private static Log logger = LogFactory.getLog(FACeUtils.class);
	private List<SSPPUnidadDir> organosGestor = new ArrayList<SSPPUnidadDir>();
	private Hashtable<String, List<SSPPUnidadDir>> unidadesTramitadoras = new Hashtable<String, List<SSPPUnidadDir>>();
	private Hashtable<String, List<SSPPUnidadDir>> oficinasContables = new Hashtable<String, List<SSPPUnidadDir>>();
	private List<SSPPUnidadDir> administraciones = new ArrayList<SSPPUnidadDir>();

	private FACeUtils() {

	}

	public static FACeUtils getInstance() {
		if (instance == null) {
			instance = new FACeUtils();
		}
		return instance;
	}

	public boolean checkConfig() {
		String faceMail = Constants.CONFIG_PROP.getProperty("FACeEmail");
		String faceCert = Constants.CONFIG_PROP.getProperty("FACeCert");
		String faceProvider = Constants.CONFIG_PROP.getProperty("FACeProvider");
		if (faceMail == null || faceMail.length() == 0 || faceCert == null || faceCert.length() == 0
			|| faceProvider == null || faceProvider.length() == 0) {
			logger.error("Error de configuracion de FACe");
			Constants.DIALOG.showErrorFACe(Constants.LANG.getString("FACeErrorMandatoryParameters"));
			return false;
		}
		return true;
	}

	public List<ComboOption> getAccountingOffices(String managementAgencyId, String codigoAdministracion) {
		List<ComboOption> ret = new ArrayList<ComboOption>();
		synchronized (instance) {
			if (oficinasContables == null || oficinasContables.isEmpty()) {
				loadUnidades(codigoAdministracion);
			}
		}
		List<SSPPUnidadDir> currentList = oficinasContables.get(managementAgencyId);
		if (currentList != null) {
			for (SSPPUnidadDir current : currentList) {
				ret.add(new ComboOption(current.getNombre(), current.getCodigo_dir()));
			}
			Collections.sort(ret);
		}
		return ret;
	}

	public List<ComboOption> getManagementAgencys(String codigoAdministracion) {
		List<ComboOption> ret = new ArrayList<ComboOption>();
		synchronized (instance) {
			//if (organosGestor == null || organosGestor.isEmpty()) {
				organosGestor = new ArrayList<SSPPUnidadDir>();
				loadUnidades(codigoAdministracion);
			//}
		}
		for (SSPPUnidadDir currentManagementAgency : organosGestor) {
			ret.add(new ComboOption(currentManagementAgency.getNombre(), currentManagementAgency.getCodigo_dir()));
		}
		Collections.sort(ret);
		return ret;
	}

	public List<ComboOption> getProcessingAuthorityUnits(String managementAgencyId, String codigoAdministracion) {
		List<ComboOption> ret = new ArrayList<ComboOption>();
		synchronized (instance) {
			if (unidadesTramitadoras == null || unidadesTramitadoras.isEmpty()) {
				loadUnidades(codigoAdministracion);
			}
		}
		List<SSPPUnidadDir> currentList = unidadesTramitadoras.get(managementAgencyId);
		if (currentList != null) {
			for (SSPPUnidadDir currentProcessingAuthorityUnit : currentList) {
				ret.add(new ComboOption(currentProcessingAuthorityUnit.getNombre(), currentProcessingAuthorityUnit
					.getCodigo_dir()));
			}
			Collections.sort(ret);
		}
		return ret;
	}

	public SSPPResultadoEnviarFactura sendInvoice(File fOut, String notificationMail, List<String> attachements)
		throws FACeException {
		FACeConnector fc = getFACeConnector();
		if (fc == null) {
			return null;
		}
		try {
			SSPPFactura request = new SSPPFactura();
			request.setCorreo(notificationMail);
			SSPPFicheroFactura fichero_factura = new SSPPFicheroFactura();
			fichero_factura.setNombre(fOut.getName());
			fichero_factura.setFactura(Base64.encodeFromFile(fOut.getAbsolutePath()));
			request.setFichero_factura(fichero_factura);

			if (attachements != null && attachements.size() > 0) {
				SSPPFicheroAnexo[] ficheros_anexos = new SSPPFicheroAnexo[attachements.size()];
				for (int i = 0; i < attachements.size(); i++) {
					File attachementFile = new File(attachements.get(i));
					if (attachementFile.exists()) {
						SSPPFicheroAnexo currentAttach = new SSPPFicheroAnexo();
						//List mimeList = MimeTypeResolver.resolve(attachementFile);
						//if(mimeList.size() > 0){
						//	MimeType mt = (MimeType)mimeList.get(0);
						//	currentAttach.setMime(mt.getName());
						//} else {
						currentAttach.setMime("application/zip");
						//}
						currentAttach.setNombre(attachementFile.getName());
						currentAttach.setAnexo(Base64.encodeFromFile(attachements.get(i)));
						ficheros_anexos[i] = currentAttach;
					}
				}
				request.setFicheros_anexos(ficheros_anexos);
			}

			return fc.enviarFactura(request);
		} finally {
			fc.finish();
		}
	}

	public SSPPResultadoConsultarFactura getInvoiceStatus(String registerCode) throws FACeException {
		FACeConnector fc = getFACeConnector();
		if (fc == null) {
			return null;
		}
		try {
			return fc.consultarFactura(registerCode);
		} finally {
			fc.finish();
		}
	}

	private FACeConnector getFACeConnector() {
		if (checkConfig()) {
			boolean useProxy = false;
			if (!"0.0.0.0".equals(Constants.CONFIG_PROP.getProperty("proxy"))) {
				useProxy = true;
				logger.info("Proxy configuration set: " + Constants.CONFIG_PROP.getProperty("proxy"));
			}
			FACeCertificateProvider certificateProvider = FACeCertificateProvider.valueOf(Constants.CONFIG_PROP
				.getProperty("FACeProvider"));
			char[] password = new char[0];
			if (certificateProvider.equals(FACeCertificateProvider.JAVA)) {
				DefaultPassStoreKS handler = new DefaultPassStoreKS();
				handler.setPINMessage(Constants.LANG.getString("FACePIN"));
				password = handler.getPassword(null, null);
			}
			FACeConnector fc = new FACeConnector(Constants.APP_PROP.getProperty("FACE_URL"),
				Constants.CONFIG_PROP.getProperty("FACeCert"), password, useProxy,
				Constants.CONFIG_PROP.getProperty("proxy"), Constants.CONFIG_PROP.getProperty("proxyPort").toString(),
				new Boolean(Constants.CONFIG_PROP.getProperty("proxyAuth").toString()).booleanValue(),
				Constants.CONFIG_PROP.getProperty("proxyUser").toString(), Constants.CONFIG_PROP
					.getProperty("proxyPwd").toString(), Constants.FACE_KEYSTORE_FILE,
				Constants.FACE_KEYSTORE_PASSWORD, certificateProvider, Integer.parseInt(Constants.CONFIG_PROP
					.getProperty("timeoutFACEMilis")));
			return fc;
		}
		return null;
	}

	private void loadUnidades(String codAdministracion) {
		FACeConnector fc = getFACeConnector();
		if (fc == null) {
			return;
		}
		try {
			Hashtable<String, String> organosGestorIds = new Hashtable<String, String>();
			Hashtable<String, String> unidadesTramitadorasIds = new Hashtable<String, String>();
			SSPPResultadoConsultarUnidades unidades = fc.consultarUnidadesPorAdministracion(codAdministracion);
			for (SSPPOrganoGestorUnidadTramitadora currentUnidad : unidades.getUnidades()) {
				if (organosGestorIds.containsKey(currentUnidad.getOrgano_gestor().getCodigo_dir())) {
					if (!unidadesTramitadorasIds.containsKey(currentUnidad.getOrgano_gestor().getCodigo_dir() + "#"
						+ currentUnidad.getUnidad_tramitadora().getCodigo_dir())) {
						unidadesTramitadoras.get(currentUnidad.getOrgano_gestor().getCodigo_dir()).add(
							currentUnidad.getUnidad_tramitadora());
						unidadesTramitadorasIds.put(currentUnidad.getOrgano_gestor().getCodigo_dir() + "#"
							+ currentUnidad.getUnidad_tramitadora().getCodigo_dir(), "");
					}
				} else {
					organosGestorIds.put(currentUnidad.getOrgano_gestor().getCodigo_dir(), "");
					organosGestor.add(currentUnidad.getOrgano_gestor());

					List<SSPPUnidadDir> unidadesTramitadorasList = new ArrayList<SSPPUnidadDir>();
					unidadesTramitadorasList.add(currentUnidad.getUnidad_tramitadora());
					unidadesTramitadorasIds.put(currentUnidad.getOrgano_gestor().getCodigo_dir() + "#"
						+ currentUnidad.getUnidad_tramitadora().getCodigo_dir(), "");
					unidadesTramitadoras
						.put(currentUnidad.getOrgano_gestor().getCodigo_dir(), unidadesTramitadorasList);
				}
				if (unidadesTramitadorasIds.containsKey(currentUnidad.getOrgano_gestor().getCodigo_dir() + "#"
					+ currentUnidad.getUnidad_tramitadora().getCodigo_dir())) {
					List<SSPPUnidadDir> oficinasContablesList = new ArrayList<SSPPUnidadDir>();
					oficinasContablesList.add(currentUnidad.getOficina_contable());
					oficinasContables.put(currentUnidad.getOrgano_gestor().getCodigo_dir() + "#"
						+ currentUnidad.getUnidad_tramitadora().getCodigo_dir(), oficinasContablesList);
				} else {
					List<SSPPUnidadDir> unidadesTramitadorasList = new ArrayList<SSPPUnidadDir>();
					unidadesTramitadorasList.add(currentUnidad.getUnidad_tramitadora());
					unidadesTramitadorasIds.put(currentUnidad.getOrgano_gestor().getCodigo_dir() + "#"
						+ currentUnidad.getUnidad_tramitadora().getCodigo_dir(), "");
					unidadesTramitadoras
						.put(currentUnidad.getOrgano_gestor().getCodigo_dir(), unidadesTramitadorasList);
				}
			}
		} catch (FACeException e) {
			logger.error(
				"Error cargando organos gestor, unidades tramitadoras y oficinas contables: " + e.getMessage(), e);
			Constants.DIALOG
				.showErrorFACe("Error cargando órganos gestores, unidades tramitadoras y oficinas contables: "
					+ e.getMessage() + ". \n\n\nIMPORTANTE: Puede introducir a continuación los datos de forma manual.");
		} finally {
			fc.finish();
		}
	}

	public List<ComboOption> getAdministrations() {
		List<ComboOption> ret = new ArrayList<ComboOption>();
		if (administraciones == null || administraciones.isEmpty()) {
			FACeConnector fc = getFACeConnector();
			if (fc == null) {
				return null;
			}
			try {
				SSPPUnidadDir[] admins = fc.consultarAdministraciones();
				//Añadimos el codigo al nombre de la administracion para distinguir administraciones duplicadas
				for (int i = 0; i < admins.length; i++) {
					SSPPUnidadDir unidad = admins[i];
					unidad.setNombre(unidad.getNombre() + " (" + unidad.getCodigo_dir() + ")");
					administraciones.add(unidad);
				}
			} catch (FACeException e) {
				logger.error("Error cargando administraciones: " + e.getMessage(), e);
				Constants.DIALOG.showErrorFACe("Error cargando administraciones: " + e.getMessage()
					+ ". \n\n\nIMPORTANTE: Puede introducir a continuación los datos de forma manual.");
			} finally {
				fc.finish();
			}
		}
		if (administraciones != null) {
			for (SSPPUnidadDir current : administraciones) {
				ret.add(new ComboOption(current.getNombre(), current.getCodigo_dir()));
			}
			Collections.sort(ret);
		}

		return ret;
	}

}
