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
package es.mityc.appfacturae.utils.storemanager;

import java.security.cert.CertPath;
import java.security.cert.X509Certificate;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Modelo visual de la estructura árbol que muestra los datos del certificado
 */
public class CadenaModeloTree extends DefaultTreeModel   { // implements ConstantesCliente
	
	public CadenaModeloTree(DefaultMutableTreeNode root, CertPath cadena) 
	{		
		super(root);
		// Se recupera el array de certificados
		int certSize = cadena.getCertificates().size();
		DefaultMutableTreeNode encadenamiento = root;
		
		// Se escriben sus datos 		
		for (int i = certSize - 2; i >= 0; --i) {
			X509Certificate certificado = (X509Certificate)cadena.getCertificates().get(i);
			root = new DefaultMutableTreeNode(certificado.getSubjectX500Principal().getName());
			encadenamiento.add(root);
			encadenamiento = root;
		}
	}
}
