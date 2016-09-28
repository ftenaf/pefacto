/**
 * Copyright 2013 Ministerio de Industria Energía y Turismo
 *
 * Este fichero es parte de "Facturae-Bridge".
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
package es.mityc.javasign.bridge.comp104;

import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;

/**
 * <p>Interfaz que ha de implementar la clase que gestiona el acceso a almacenes genéricos.</p>
 * <p>El almacén genérico permite definir una pasarela de acceso a un almacén propio a través de este interfaz.</p>
 * 
 */
public interface IGenericStore {
	
	/**
	 * <p>Devuelve un array con los certificados disponibles para firma en el almacén.</p>
	 * <p>Los certificados en este listado deben tener asociada una clave privada.</p>
	 * @return array de certificados
	 */
	X509Certificate[] getSignCertificates();
	
	/**
	 * <p>Devuelve la clave privada asociada al certificado indicado.</p>
	 * @param certificate Certificado del que se quiere la clave privada
	 * @return Clave privada
	 */
	PrivateKey getPrivateKey(X509Certificate certificate);
	
	/**
	 * <p>Devuelve el provider asociado a las claves privadas provistas por el almacén que permite realizar labores de firma.</p>
	 * @return Provider de firma asociado al almacén
	 */
	Provider getProvider();
}
