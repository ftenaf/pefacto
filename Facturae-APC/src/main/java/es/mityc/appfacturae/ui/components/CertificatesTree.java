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
package es.mityc.appfacturae.ui.components;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;
import org.bouncycastle.asn1.x509.X509Name;

public class CertificatesTree {

	private JTree tree;
    
    public CertificatesTree(X509Certificate[] certificateChain){
    	DefaultMutableTreeNode Node = null;
    	DefaultMutableTreeNode treeNode = null;
    	String name = null;
    	
    	for (int i = 0; i < certificateChain.length; i++) {
    		
    		try {
        		name = getSubjectCN(certificateChain[i]);
        	} catch (CertificateEncodingException e) {
        		name = certificateChain[i].getSubjectDN().getName();
        	}
        	
    		if (treeNode == null && Node == null)
    			Node = new DefaultMutableTreeNode(name);
    		else{
    			treeNode = new DefaultMutableTreeNode(name);
    			treeNode.add(Node);
    			Node = treeNode;
    		}
    	}
    	
    	if (treeNode != null)
    		tree = new JTree(treeNode);
    	else
    		tree = new JTree(new DefaultMutableTreeNode());
    }
    
    private String getSubjectCN(X509Certificate cert) throws CertificateEncodingException {
    	String subjectName = null;
    	try {
    		ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(cert.getTBSCertificate());
    		ASN1InputStream asn1inputstream = new ASN1InputStream(bytearrayinputstream);
    		TBSCertificateStructure tbscertificatestructure = new TBSCertificateStructure((ASN1Sequence)asn1inputstream.readObject());
    		X509Name name = tbscertificatestructure.getSubject();

    		Vector<?> commonNameOIDs = name.getOIDs();
    		Vector<?> commonName = name.getValues();
    		int longitudValues = commonName.size();

    		if (longitudValues != 0) {
    			// Se busca el valor "CN"
    			int indexCN = commonNameOIDs.indexOf(X509Name.CN);
    			if (indexCN != -1) {
    				Object elemento = commonName.get(indexCN);
    				if (elemento instanceof String)
    					subjectName = (String) elemento;
    			}

    			// If there is no return value, look for "OU"
    			if (subjectName == null || subjectName.equals("")) {
    				int indexOU = commonNameOIDs.indexOf(X509Name.OU);
    				if (indexOU != -1) {
    					Object elemento = commonName.get(indexOU);
    					if (elemento instanceof String)
    						subjectName = (String) elemento;
    				}
    			}

    			// If there is no return value, look for "O"
    			if (subjectName == null || subjectName.equals("")) {
    				int indexO = commonNameOIDs.indexOf(X509Name.O);
    				if (indexO != -1) {
    					Object elemento = commonName.get(indexO);
    					if (elemento instanceof String)
    						subjectName = (String) elemento;
    				}
    			}
    		}
    		
    		if (subjectName == null)
    			throw new CertificateEncodingException();
    		
    		return subjectName;

    	} catch(IOException ioe) {
    		throw new CertificateEncodingException(ioe.toString());
    	}
    }
    
    public TreeModel getModel(){
        return tree.getModel();
    }
}