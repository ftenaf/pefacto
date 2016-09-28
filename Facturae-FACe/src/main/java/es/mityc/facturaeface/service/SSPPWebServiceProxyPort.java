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
package es.mityc.facturaeface.service;

import es.mityc.facturaeface.bean.SSPPEstados;
import es.mityc.facturaeface.bean.SSPPFactura;
import es.mityc.facturaeface.bean.SSPPResultadoAnularFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarUnidades;
import es.mityc.facturaeface.bean.SSPPResultadoEnviarFactura;
import es.mityc.facturaeface.bean.SSPPUnidadDir;

public interface SSPPWebServiceProxyPort extends java.rmi.Remote {

	/**
	 * enviarFactura
	 */
	public SSPPResultadoEnviarFactura enviarFactura(SSPPFactura facturaWS) throws java.rmi.RemoteException;

	/**
	 * consultarFactura
	 */
	public SSPPResultadoConsultarFactura consultarFactura(java.lang.String numeroRegistro)
		throws java.rmi.RemoteException;

	/**
	 * anularFactura
	 */
	public SSPPResultadoAnularFactura anularFactura(java.lang.String numeroRegistro, java.lang.String motivo)
		throws java.rmi.RemoteException;

	/**
	 * Consultar los estados publicos de una factura
	 */
	public SSPPEstados consultarEstados() throws java.rmi.RemoteException;

	/**
	 * Consultar las unidades tramitadoras y organos gestores existentes
	 * en el sistema.
	 */
	public SSPPResultadoConsultarUnidades consultarUnidades() throws java.rmi.RemoteException;

	/**
	 * Retorna un listado de unidades dir padres de primer nivel
	 */
	public SSPPUnidadDir[] consultarAdministraciones() throws java.rmi.RemoteException;

	/**
	 * Retorna un listado de UT relacionadas con OG activas y visibles
	 * por Administracion
	 */
	public SSPPResultadoConsultarUnidades consultarUnidadesPorAdministracion(java.lang.String codigoDir)
		throws java.rmi.RemoteException;

	/**
	 * Consulta el estado de un listado de facturas entregada al RCF
	 */
	public SSPPResultadoConsultarFactura[] consultarListadoFacturas(java.lang.Object[] listadoFacturas)
		throws java.rmi.RemoteException;
}
