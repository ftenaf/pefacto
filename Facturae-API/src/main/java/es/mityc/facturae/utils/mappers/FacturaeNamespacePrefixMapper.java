/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae.utils.mappers;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * Adapts the Facturae prefix used. 
 * 
 * <p>It sets the wished prefixes for the invoice root element 'Facturae' and the 'Signature' node.
 * It is needed in order to override the default prefixes established in the marshal process.</p>
 * <p>In this case, 'fe' and 'ds' are the desired prefixes.</p> 
 */
public class FacturaeNamespacePrefixMapper extends NamespacePrefixMapper {

	private final String namespace30 = "http://www.facturae.es/Facturae/2007/v3.0/Facturae";
	private final String namespace31 = "http://www.facturae.es/Facturae/2007/v3.1/Facturae";
	private final String namespace32 = "http://www.facturae.es/Facturae/2009/v3.2/Facturae";
	private final String namespace321 = "http://www.facturae.es/Facturae/2014/v3.2.1/Facturae";
	private final String namespaceds = "http://www.w3.org/2000/09/xmldsig#";
	private final String prefix = "fe";
	private final String prefixds = "ds";

	/**
	* Returns a preferred prefix for the given namespace URI.
	* 
	* This method is intended to be overrided by a derived class.
	* 
	* @param namespaceUri
	*      The namespace URI for which the prefix needs to be found.
	*      It will be never be null. "" is used to denote the default namespace.
	* @param suggestion
	*      When the content tree has a suggestion for the prefix
	*      to the given namespaceUri, that suggestion is passed as a
	*      parameter. Typically this value comes from QName.getPrefix()
	*      to show the preference of the content tree. This parameter
	*      may be null, and this parameter may represent an already
	*      occupied prefix. 
	* @param requirePrefix
	*      If this method is expected to return non-empty prefix.
	*      When this flag is true, it means that the given namespace URI
	*      cannot be set as the default namespace.
	* 
	* @return
	*      null if there's no preferred prefix for the namespace URI.
	*      In this case, the system will generate a prefix for you.
	* 
	*      Otherwise the system will try to use the returned prefix,
	*      but generally there's no guarantee if the prefix will be
	*      actually used or not.
	* 
	*      return "" to map this namespace URI to the default namespace.
	*      Again, there's no guarantee that this preference will be
	*      honored.
	* 
	*      If this method returns "" when requirePrefix=true, the return
	*      value will be ignored and the system will generate one.
	*/

	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		if (namespaceUri.equals(namespace30) || namespaceUri.equals(namespace31) || namespaceUri.equals(namespace32)
			|| namespaceUri.equals(namespace321))
			return prefix;
		else if (namespaceUri.equals(namespaceds))
			return prefixds;
		else
			return null;
	}
}
