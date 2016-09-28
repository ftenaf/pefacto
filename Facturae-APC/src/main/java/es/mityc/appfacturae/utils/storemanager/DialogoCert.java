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

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;

/**
 * <p>Clase que muestra una nueva ventana modal con los datos en formato árbol
 * del certificado sobre el cual se ha hecho doble clic en los datos de firma.</p>
 */

public class DialogoCert extends JDialog {
	
	Log logger = LogFactory.getLog(DialogoCert.class);

	private static final String STR_ICON_CERT =	"/images/CertSmall.png";
	
	private CertPath certPath = null;
	
    public DialogoCert(Frame owner) {    	
    	super(owner);
    }
    
    public void muestraInfo (X509Certificate cert) {
		try {
			List<X509Certificate> list = new ArrayList<X509Certificate>();
			list.add(cert);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			this.certPath = cf.generateCertPath(list);  
		} catch (CertificateException e) {
			logger.error(Constants.LANG.getString("Store.CertPathNull"), e);
			return;
		}	
    	muestraDialogo();
    }
    
    public void muestraInfo (CertPath certPath) {
    	this.certPath = certPath;  	
    	muestraDialogo();
    }
    
    private void muestraDialogo() {
    	if (certPath != null) {
    		// marca el primero del certpath
    		X509Certificate certMostrado = (X509Certificate)certPath.getCertificates().get(0);

	    	DefaultMutableTreeNode top = new DefaultMutableTreeNode(new TypeTreeNode(
	    			Constants.LANG.getString("Store.Certificate"),
					EnumTipoNodo.TIPO_CERT,
					Constants.LANG.getString("Store.Certificate"),
					null,
					null));
	
	    	ImageIcon iconoCertificado = new ImageIcon(getClass().getResource(STR_ICON_CERT));
	    	if (iconoCertificado != null) {
	    		DefaultTreeCellRenderer renderer = 
	    			new DefaultTreeCellRenderer();
	    		renderer.setLeafIcon(iconoCertificado);
	    		renderer.setOpenIcon(iconoCertificado);
	    		renderer.setClosedIcon(iconoCertificado);
	    		renderer.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0), 2)); // Margen transparente de dos pixels
	    		jTreeDatosCadena.setCellRenderer(renderer);
	    	}
	
	    	// Se muestran los certificados que componen la cadena, comenzando por el raíz
	    	int numCerts = certPath.getCertificates().size();
	    	DefaultMutableTreeNode certificadoNodo = new DefaultMutableTreeNode(
	    			((X509Certificate)certPath.getCertificates().get(numCerts - 1)).getSubjectX500Principal().getName());
	    	jTreeDatosCadena.setModel(new CadenaModeloTree(certificadoNodo, certPath));
	
	    	// Se expanden y bloquean los nodos
	    	for (int i = 0; i < numCerts-1; ++i) {
	    		jTreeDatosCadena.expandRow(i);
	    	}
	
	    	// Marcamos el certificado de firma (el último)
	    	jTreeDatosCadena.setSelectionRow(numCerts - 1);
	
	    	// Se escriben los datos del certificado de firma
	    	CertificadoModeloTree cmt = new CertificadoModeloTree(top, certMostrado);
	    	jTreeDatoCertificado.setModel(cmt);
	    	cmt.reload();
	    	setVisible(true);
	    	jTreeDatoCertificado.validate();
	    	jTreeDatoCertificado.repaint();
    	} else
    		logger.debug(Constants.LANG.getString("Store.CertPathNull"));
    }
	
	 /**
     * Inicialización de los componentes visuales
     */
    @Override
	protected void dialogInit()
    {
    	super.dialogInit();
    	panPrincipal = new JPanel();
        jDatosCertificadoScrollPane = new JScrollPane();
        jTreeDatoCertificado = new JTree();
        jTreeDatoCertificado.setCellRenderer(new NodoRenderer());
        jDatosCadenaScrollPane = new JScrollPane();
        jTreeDatosCadena = new JTree() { // Se bloquea su expansor para que solo expanda nodos (nunca colapsen)
        	@Override
        	protected void setExpandedState(TreePath path, boolean state) {
        		if (state) {
        			super.setExpandedState(path, state);
        		}
        	}
        };
        jCerrarButton = new JButton();
        jExportarCertificadoButton = new JButton();
        
        setLayout(new GridBagLayout());
        panPrincipal.setLayout(new GridBagLayout());
        
        // Diálogo Datos del Certificado       
        GridBagConstraints DatosCadenaScrollPaneConstraints = new GridBagConstraints();
        DatosCadenaScrollPaneConstraints.gridx = 0;
        DatosCadenaScrollPaneConstraints.gridy = 0;
        DatosCadenaScrollPaneConstraints.weightx = 1.0;
        DatosCadenaScrollPaneConstraints.weighty = 0.4;
        DatosCadenaScrollPaneConstraints.gridwidth = 4;
        DatosCadenaScrollPaneConstraints.fill = GridBagConstraints.BOTH;
        DatosCadenaScrollPaneConstraints.insets = new Insets(10,10,5,10);
        jDatosCadenaScrollPane.setViewportView(jTreeDatosCadena);
        jTreeDatosCadena.addTreeSelectionListener(new TreeSelectionListener() 
        {
        	public void valueChanged(TreeSelectionEvent evt) {
        		jTreeDatosCadenaListener(evt);
        	}
        });

		GridBagConstraints DatosCertificadoScrollPaneConstraints = new GridBagConstraints();
		DatosCertificadoScrollPaneConstraints.gridx = 0;
		DatosCertificadoScrollPaneConstraints.gridy = 1;
		DatosCertificadoScrollPaneConstraints.weightx = 1.0;
		DatosCertificadoScrollPaneConstraints.weighty = 1.0;
		DatosCertificadoScrollPaneConstraints.gridwidth = 4;
		DatosCertificadoScrollPaneConstraints.fill = GridBagConstraints.BOTH;
		DatosCertificadoScrollPaneConstraints.insets = new Insets(10,10,10,10);
		jDatosCertificadoScrollPane.setBorder(BorderFactory.createTitledBorder(Constants.LANG.getString("Store.CertData")));
        jDatosCertificadoScrollPane.setViewportView(jTreeDatoCertificado);

		GridBagConstraints CerrarButtonConstraints = new GridBagConstraints();
		CerrarButtonConstraints.gridx = 1;
		CerrarButtonConstraints.gridy = 2;
		CerrarButtonConstraints.insets = new Insets(0,225,10,10);
        jCerrarButton.setText(Constants.LANG.getString("Store.Close"));
        jCerrarButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                jCerrarButtonActionPerformed(evt);
            }
        });
        
        GridBagConstraints ExportarButtonConstraints = new GridBagConstraints();
        ExportarButtonConstraints.gridx = 2;
        ExportarButtonConstraints.gridy = 2;
        ExportarButtonConstraints.insets = new Insets(0,78,10,0);
        jExportarCertificadoButton.setText(Constants.LANG.getString("Store.ExportCert"));
        jExportarCertificadoButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
            	int numCerts = certPath.getCertificates().size();
            	X509Certificate certMostrado = (X509Certificate)certPath.getCertificates()
            		.get(numCerts - jTreeDatosCadena.getSelectionPath().getPathCount());
                jExportarCertButtonActionPerformed(certMostrado);
            }
        });
        
        panPrincipal.add(jDatosCadenaScrollPane, DatosCadenaScrollPaneConstraints);
        panPrincipal.add(jDatosCertificadoScrollPane, DatosCertificadoScrollPaneConstraints);
        panPrincipal.add(jCerrarButton, CerrarButtonConstraints);
        panPrincipal.add(jExportarCertificadoButton, ExportarButtonConstraints);
        
        GridBagConstraints PanPrincipalConstraints = new GridBagConstraints();
        PanPrincipalConstraints.fill = GridBagConstraints.BOTH;
        PanPrincipalConstraints.weightx = 1.0;
        PanPrincipalConstraints.weighty = 1.0;
        
        add(panPrincipal, PanPrincipalConstraints);

        setTitle(Constants.LANG.getString("Store.Certificate"));
        setSize(585,355);
    	setLocationRelativeTo(getOwner());
    	setModal(true);
        setResizable(false);
    }
    
    /**
     * Muestra los datos del certificado de la cadena seleccionado
     * @param evt
     */
    private void jTreeDatosCadenaListener(TreeSelectionEvent evt)
    {
    	int numCerts = certPath.getCertificates().size();
    	X509Certificate certMostrado = (X509Certificate)certPath.getCertificates().get(numCerts - (evt.getPath().getPathCount())); 
    	
    	DefaultMutableTreeNode top =
    		new DefaultMutableTreeNode(new TypeTreeNode(
		    	Constants.LANG.getString("Store.Certificate"),
				EnumTipoNodo.TIPO_CERT,
				Constants.LANG.getString("Store.Certificate"),
				null,
				null));
    	 
    	jTreeDatoCertificado.setModel(new CertificadoModeloTree(top, certMostrado));
		repaint();
    }
    
    /**
     * Cierra la ventana donde se muestran los datos del certificado seleccionado
     * @param evt
     */
    private void jCerrarButtonActionPerformed(ActionEvent evt)
    {
    	setVisible(false);
    }
    
    /**
     * Exporta el certificado seleccionado
     * @param evt
     */
    private void jExportarCertButtonActionPerformed(X509Certificate certMostrado)
    {
    	// Se pide la ruta sobre la que guardar el certifiado
    	JFileChooser fc = new JFileChooser();
    	File retorno = null;
    	fc = new JFileChooser();
    	fc.setDialogTitle(Constants.LANG.getString("Store.ExportCert"));
    	fc.setMultiSelectionEnabled(true);
    	fc.setSelectedFile(new File("*.cer"));    
    	fc.setDialogType(JFileChooser.SAVE_DIALOG);
    	int seleccion = fc.showSaveDialog(this);
    	// Se muestra el diálogo, cuando el usuario le de a algo…
    	if (seleccion != JFileChooser.CANCEL_OPTION)
    		retorno = fc.getSelectedFile();
    	
    	if (retorno != null) {
    		// Se salva el certificado seleccionado
    		BufferedOutputStream f = null;
    		try {
    			f = new BufferedOutputStream(new FileOutputStream(retorno));
    			f.write(certMostrado.getEncoded()); // Certificado guardado
    			f.flush();
    		} catch (FileNotFoundException e) {
    			logger.error(Constants.LANG.getString("Store.Error") + " " + e.getMessage());
    		} catch (IOException e) {
    			logger.error(Constants.LANG.getString("Store.Error") + " " + e.getMessage());
    		} catch (CertificateEncodingException e) {
    			logger.error(Constants.LANG.getString("Store.Error") + " " + e.getMessage());
    		} finally {
    			try {
    				if (f != null)
    					//AppPerfect: Falso positivo
    					f.close() ;
    			} catch (IOException e) {
    				logger.error(e.getMessage());
    			}
    		}
    	}
    }
    
	/**
	 * Declaración de los componentes visuales
	 */
    private JPanel panPrincipal;
	
	private JButton jCerrarButton;
	private JButton jExportarCertificadoButton;

    private JScrollPane jDatosCertificadoScrollPane;
    private JTree jTreeDatoCertificado;
    private JScrollPane jDatosCadenaScrollPane;
    private JTree jTreeDatosCadena;

}
