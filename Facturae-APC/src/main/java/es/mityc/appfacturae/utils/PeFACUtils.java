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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gobiernodecanarias.ceh.efactura.ws.v2.Anexo;
import org.gobiernodecanarias.ceh.efactura.ws.v2.EstadoFactura;
import org.gobiernodecanarias.ceh.efactura.ws.v2.FacturaExtendidaV2;
import org.gobiernodecanarias.ceh.efactura.ws.v2.ServicioError_Exception;
import org.gobiernodecanarias.cehs.efactura.ws.client.FacturaClient;

import es.mityc.appfacturae.ui.components.ComboOption;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.Base64;
import es.mityc.facturaeface.FACeConfig.FACeCertificateProvider;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFacturaEstado;
import es.mityc.facturaeface.bean.SSPPResultadoEnviarFactura;
import es.mityc.facturaeface.bean.SSPPUnidadDir;
import es.mityc.facturaeface.exception.FACeException;
import es.mityc.javasign.pkstore.DefaultPassStoreKS;

public class PeFACUtils {
	private static PeFACUtils instance = null;
	private static Log logger = LogFactory.getLog(PeFACUtils.class);
	private List<SSPPUnidadDir> organosGestor = new ArrayList<SSPPUnidadDir>();
	private Hashtable<String, List<SSPPUnidadDir>> unidadesTramitadoras = new Hashtable<String, List<SSPPUnidadDir>>();
	private Hashtable<String, List<SSPPUnidadDir>> oficinasContables = new Hashtable<String, List<SSPPUnidadDir>>();

	private PeFACUtils() {

	}

	public static PeFACUtils getInstance() {
		if (instance == null) {
			instance = new PeFACUtils();
		}
		return instance;
	}

	public boolean checkConfig() {
		String faceMail = Constants.CONFIG_PROP.getProperty("FACeEmail");
		String faceCert = Constants.CONFIG_PROP.getProperty("FACeCert");
		String faceProvider = Constants.CONFIG_PROP.getProperty("FACeProvider");
		if (faceMail == null || faceMail.length() == 0 || faceCert == null || faceCert.length() == 0
				|| faceProvider == null || faceProvider.length() == 0) {
			logger.error("Error de configuracion de PeFAC");
			Constants.DIALOG.showErrorFACe(Constants.LANG.getString("FACeErrorMandatoryParameters"));
			return false;
		}
		return true;
	}

	public List<ComboOption> getAccountingOffices(String managementAgencyId) {
		List<ComboOption> ret = new ArrayList<ComboOption>();
		synchronized (instance) {
			if (oficinasContables == null || oficinasContables.isEmpty()) {
				loadUnidades();
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

	public List<ComboOption> getManagementAgencys() {
		List<ComboOption> ret = new ArrayList<ComboOption>();
		synchronized (instance) {
			if (organosGestor == null || organosGestor.isEmpty()) {
				loadUnidades();
			}
		}
		for (SSPPUnidadDir currentManagementAgency : organosGestor) {
			ret.add(new ComboOption(currentManagementAgency.getNombre(), currentManagementAgency.getCodigo_dir()));
		}
		Collections.sort(ret);
		return ret;
	}

	public List<ComboOption> getProcessingAuthorityUnits(String managementAgencyId) {
		List<ComboOption> ret = new ArrayList<ComboOption>();
		synchronized (instance) {
			if (unidadesTramitadoras == null || unidadesTramitadoras.isEmpty()) {
				loadUnidades();
			}
		}
		List<SSPPUnidadDir> currentList = unidadesTramitadoras.get(managementAgencyId);
		if (currentList != null) {
			for (SSPPUnidadDir currentProcessingAuthorityUnit : currentList) {
				ret.add(new ComboOption(currentProcessingAuthorityUnit.getNombre(),
						currentProcessingAuthorityUnit.getCodigo_dir()));
			}
			Collections.sort(ret);
		}
		return ret;
	}

	public SSPPResultadoEnviarFactura sendInvoice(File fOut, String notificationMail, List<String> attachements)
			throws FACeException {

		FacturaClient fc = getFacturaCliente();
		if (fc == null) {
			return null;
		}

		DataHandler dh = null;
		List<Anexo> anexos = null;
		try {
			dh = new DataHandler(new FileDataSource(fOut));

			anexos = null;
			if (attachements != null && attachements.size() > 0) {
				anexos = new ArrayList<Anexo>(attachements.size());
				;
				for (int i = 0; i < attachements.size(); i++) {
					File attachementFile = new File(attachements.get(i));

					if (attachementFile.exists()) {
						Anexo anexo = new Anexo();
						anexo.setNombre(attachementFile.getName());
						anexo.setContenido(new DataHandler(attachementFile.toURI().toURL()));
						anexos.add(anexo);

					}
				}
			}
		} catch (MalformedURLException e) {
			logger.error(e, e);
			throw new FACeException(e.getMessage(), e);
		}

		FacturaExtendidaV2 factura = null;
		if (dh != null) {
			try {
				factura = fc.enviarFactura(dh, anexos);
			} catch (ServicioError_Exception e) {
				logger.error(e, e);
				throw new FACeException(e.getMessage(), e);
			}
		}

		SSPPResultadoEnviarFactura resultadoEnviarFactura = null;
		if (factura != null) {
			resultadoEnviarFactura = new SSPPResultadoEnviarFactura();
			resultadoEnviarFactura.setCodigo_registro(factura.getId().toString());
			resultadoEnviarFactura.setFecha_recepcion(factura.getFechaRegistro().toString());
			resultadoEnviarFactura.setIdentificador_emisor(factura.getNifProveedor());
			resultadoEnviarFactura.setNumero_factura(factura.getNumero());
			resultadoEnviarFactura.setOficina_contable(factura.getOficinaContable());
			resultadoEnviarFactura.setOrgano_gestor(factura.getOrganoGestor());

			try {
				// obtenemos el contenido del comprobante de registro
				// (DataHandler)
				byte[] bytes = null;
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				factura.getComprobanteRegistro().writeTo(bos);
				bos.flush();
				bos.close();
				bytes = bos.toByteArray();
				String result = Base64.encodeBytes(bytes);
				resultadoEnviarFactura.setPdf_justificante(result);
			} catch (IOException e) {
				logger.error(e, e);
				throw new FACeException(e.getMessage(), e);
			}

			Integer yearFechaRegistro = factura.getFechaRegistro().getYear();
			resultadoEnviarFactura.setSerie_factura(yearFechaRegistro.toString());
			resultadoEnviarFactura.setUnidad_tramitadora(factura.getUnidadTramitadora());
		} else {
			throw new FACeException("No se ha podido presentar la factura");
		}
		return resultadoEnviarFactura;
	}

	// método para conectar con el web-service de enviar facura de PEFAC
	private FacturaClient getFacturaCliente() {
		if (checkConfig()) {
			boolean useProxy = !"0.0.0.0".equals(Constants.CONFIG_PROP.getProperty("proxy"));
			if (useProxy) {
				logger.info("Proxy configuration set: " + Constants.CONFIG_PROP.getProperty("proxy"));
			}

			FACeCertificateProvider certificateProvider = FACeCertificateProvider
					.valueOf(Constants.CONFIG_PROP.getProperty("FACeProvider"));
			char[] password = new char[0];
			KeyStore ks = null;
			if (certificateProvider.equals(FACeCertificateProvider.JAVA)) {
				DefaultPassStoreKS handler = new DefaultPassStoreKS();
				handler.setPINMessage(Constants.LANG.getString("FACePIN"));
				password = handler.getPassword(null, null);
				ks = FacturaClient.loadKeyStoreFormJceks(Constants.FACE_KEYSTORE_FILE,
						Constants.FACE_KEYSTORE_PASSWORD);
			} else if (certificateProvider.equals(FACeCertificateProvider.WINDOWS)) {
				try {
					ks = FacturaClient.loadKeyStoreFormIE("Windows-MY", null);
				} catch (Exception e) {
					logger.error("Error al obtener el almacen de certificados del Usuario", e);
				}
				if (ks == null) {
					try {
						ks = FacturaClient.loadKeyStoreFormIE("Windows-LocalMachine-MY", null);
					} catch (Exception e) {
						logger.error("Error al obtener el almacen de certificados del Usuario local", e);
					}
				}
				if (ks == null) {
					try {
						ks = FacturaClient.loadKeyStoreFormIE("Windows-ROOT", null);
					} catch (Exception e) {
						logger.error("Error al obtener el almacen de certificados de Windows", e);
					}
				}
			}

			if (ks != null) {
				try {
					FacturaClient fc = new FacturaClient(ks, password, null,
							Constants.WSCONFIG_PROP.getProperty("PeFAC_URL"), 
							useProxy,
							Constants.CONFIG_PROP.getProperty("proxy"), 
							Constants.CONFIG_PROP.getProperty("proxyPort").toString(),
							new Boolean(Constants.CONFIG_PROP.getProperty("proxyAuth").toString()).booleanValue(),
							Constants.CONFIG_PROP.getProperty("proxyUser").toString(), 
							Constants.CONFIG_PROP.getProperty("proxyPwd").toString());
					return fc;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				logger.error("No se ha podido obtener un KeyStore válido.");
			}
		}
		return null;
	}

	public SSPPResultadoConsultarFactura getInvoiceStatus(String registerCode) throws FACeException {
		try {
			FacturaExtendidaV2 factura = consultarFacturaPefac(Long.valueOf(registerCode));
			if (factura == null) {
				return null;
			}
			SSPPResultadoConsultarFactura resultadoConsultaFactura = new SSPPResultadoConsultarFactura();

			SSPPResultadoConsultarFacturaEstado anulacion = new SSPPResultadoConsultarFacturaEstado();
			SSPPResultadoConsultarFacturaEstado tramitacion = new SSPPResultadoConsultarFacturaEstado();
			if (factura.getEstadoFactura().equals(EstadoFactura.ANULADA) && factura.getFechaAnulacion() != null) {
				anulacion.setCodigo_estado(EstadoFactura.ANULADA.toString());
				anulacion.setDescripcion_estado(factura.getObservacionesAnulacion());
				anulacion.setMotivo_estado("");
				resultadoConsultaFactura.setAnulacion(anulacion);
				tramitacion.setCodigo_estado("");
				tramitacion.setDescripcion_estado("");
				tramitacion.setMotivo_estado("");
				resultadoConsultaFactura.setTramitacion(tramitacion);
			} else {
				anulacion.setCodigo_estado("");
				anulacion.setDescripcion_estado("");
				anulacion.setMotivo_estado("");
				resultadoConsultaFactura.setAnulacion(anulacion);
				tramitacion.setCodigo_estado(factura.getEstadoFactura().toString());
				tramitacion.setDescripcion_estado(factura.getObservacionesEstado());
				tramitacion.setMotivo_estado("");
				resultadoConsultaFactura.setTramitacion(tramitacion);
			}
			resultadoConsultaFactura.setNumero_registro(registerCode);
			return resultadoConsultaFactura;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new FACeException(e.getMessage(), e);
		}
	}

	public FacturaExtendidaV2 consultarFacturaPefac(Long idFactura) throws FACeException {
		FacturaClient fc = getFacturaCliente();
		if (fc == null) {
			return null;
		}
		try {
			FacturaExtendidaV2 factura = fc.consultarFactura(idFactura);
			return factura;
		} catch (ServicioError_Exception e) {
			throw new FACeException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Fallo la consulta de los datos de la factura ID=" + idFactura);
			throw new FACeException("Fallo la consulta de los datos de la factura.", e);
		}
	}

	private void loadUnidades() {
		return;
	}
}
