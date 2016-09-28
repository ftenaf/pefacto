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

import java.security.cert.X509Certificate;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import es.mityc.appfacturae.utils.constants.Constants;

/**
 * Modelo visual de la estructura árbol que muestra los datos del certificado
 */
public class CertificadoModeloTree extends DefaultTreeModel   { // implements ConstantesCliente

 
	private static final String STR_COLON_SPACE = ": ";
	
	public CertificadoModeloTree(DefaultMutableTreeNode root, X509Certificate cert) 
	{
		super(root);
		
		String datos = "";
		
		DefaultMutableTreeNode sujeto = new DefaultMutableTreeNode(new TypeTreeNode(
				Constants.LANG.getString("Store.IssuedFor") + STR_COLON_SPACE + 
        		cert.getSubjectX500Principal().getName(),
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
		
		DefaultMutableTreeNode sujetoDN = new DefaultMutableTreeNode(new TypeTreeNode(
				Constants.LANG.getString("Store.IssuedFor") + STR_COLON_SPACE + 
        		cert.getSubjectDN(),
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
		
		sujeto.add(sujetoDN);
  
	    root.add(sujeto);
	    
	    DefaultMutableTreeNode emisor = new DefaultMutableTreeNode(new TypeTreeNode(
	    		Constants.LANG.getString("Store.IssuedBy") + STR_COLON_SPACE + 
	    		cert.getIssuerX500Principal().getName(),
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
	    
	    DefaultMutableTreeNode emisorDN = new DefaultMutableTreeNode(new TypeTreeNode(
				Constants.LANG.getString("Store.IssuedBy") + STR_COLON_SPACE + 
        		cert.getIssuerDN(),
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
		
		emisor.add(emisorDN);
	    
	    root.add(emisor);
	    
	    try
	    {
	    	String aviso = "";
	    	if ((new Date()).before(cert.getNotBefore()))
	    		aviso = " " + Constants.LANG.getString("Store.YetNotValidCert");
	    	if ((new Date()).after(cert.getNotAfter()))
	    		aviso = " " + Constants.LANG.getString("Store.ExpiredCert");
	    		
	    	datos = Constants.LANG.getString("Store.From") + " " + 
	    		cert.getNotBefore() + " " + 
	    		Constants.LANG.getString("Store.To") + " " + 
	    		cert.getNotAfter() + aviso;
	    }
	    catch(Throwable t)
	    {
	    	datos = Constants.LANG.getString("Store.NoData");
	    }
	    DefaultMutableTreeNode validez = new DefaultMutableTreeNode(new TypeTreeNode(
	    		Constants.LANG.getString("Store.Validity") + STR_COLON_SPACE + datos,
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
  
	    root.add(validez);
	    
	    try
	    {
	    	datos = cert.getSerialNumber().toString();
	    }
	    catch(Throwable t)
	    {
	    	datos = Constants.LANG.getString("Store.NoData");
	    }
	    DefaultMutableTreeNode nroSerie = new DefaultMutableTreeNode(new TypeTreeNode(
	    		Constants.LANG.getString("Store.SeriesNumber") + STR_COLON_SPACE + datos,
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
 
	    root.add(nroSerie);
        
		//Usos del certificado: 
		//F Firma digital,N no repudio, Cc cifrado de claves, 
		//Cd cifrado de datos, Ac Acuerdo de claves, Fc Firma de certificados, 
		//Fcrl Firma de CRL, Sc Solo cifrado, Sf solo firma

        StringBuilder claveUsoString = new StringBuilder("");
		String[] clavesUsadasArray = new String[9];
		
		clavesUsadasArray [0] = Constants.LANG.getString("Store.DigitalSignature");
		clavesUsadasArray [1] = Constants.LANG.getString("Store.NonRepudiation");
		clavesUsadasArray [2] = Constants.LANG.getString("Store.EncodingKeys");
		clavesUsadasArray [3] = Constants.LANG.getString("Store.EncodingData");
		clavesUsadasArray [4] = Constants.LANG.getString("Store.KeysAgreement");
		clavesUsadasArray [5] = Constants.LANG.getString("Store.CertificatesSignature");
		clavesUsadasArray [6] = Constants.LANG.getString("Store.CRLSignature");
		clavesUsadasArray [7] = Constants.LANG.getString("Store.OnlyEncoded");
		clavesUsadasArray [8] = Constants.LANG.getString("Store.OnlySignature");

        // AppPerfect: Falso Positivo
		boolean[] claveUso = cert.getKeyUsage() ;
		if(claveUso!=null)
		{
			for (int z=0; z<claveUso.length; z++)
			{
				if(claveUso[z])
				{
					claveUsoString.append(clavesUsadasArray[z]);
					claveUsoString.append(",");
				}
			}
			claveUsoString.deleteCharAt(claveUsoString.length()-1);
		}
		else  
		{
			claveUsoString.append(Constants.LANG.getString("Store.NonDefined"));
		}	
		
	    DefaultMutableTreeNode uso = new DefaultMutableTreeNode(new TypeTreeNode(
	    		Constants.LANG.getString("Store.KeyUsage") + STR_COLON_SPACE + claveUsoString.toString(),
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
  
	    root.add(uso);
	    
	    try
	    {
	    	datos = cert.getSigAlgName();
	    }
	    catch(Throwable t)
	    {
	    	datos = Constants.LANG.getString("Store.NoData");
	    }
	    DefaultMutableTreeNode algoritmo = new DefaultMutableTreeNode(new TypeTreeNode(
	    		Constants.LANG.getString("Store.SignatureAlgorithm") + STR_COLON_SPACE + datos,
				EnumTipoNodo.TIPO_CERT_INFO,
				null,
				null,
				null));
           
	    root.add(algoritmo);
	}
}
