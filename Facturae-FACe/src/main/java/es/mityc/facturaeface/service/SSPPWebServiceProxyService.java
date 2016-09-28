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

public interface SSPPWebServiceProxyService extends javax.xml.rpc.Service {
    public java.lang.String getSSPPWebServiceProxyPortAddress();

    public SSPPWebServiceProxyPort getSSPPWebServiceProxyPort() throws javax.xml.rpc.ServiceException;

    public SSPPWebServiceProxyPort getSSPPWebServiceProxyPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
