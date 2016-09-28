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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.javasign.pkstore.IPKStoreMaintainer;
import es.mityc.javasign.pkstore.IPKStoreManager;

/**
 * <p>Componente para la presentación de un diálogo de selección de certificados.
 * Emplea el almacén de certificados del Ministrio de Industria Energía y Turismo.</p>
 */
public final class JavaStoreManager extends JDialog {
	
	/** Logger. */
	private static final Log LOG = LogFactory.getLog(JavaStoreManager.class);
	
	/** Ancho del diálogo. */
	private static final int WIDTH = 600;
	/** Alto del diálogo. */
	private static final int HEIGHT = 450;

	/** Botón "salir". */
	private JButton cancelBtn = null;
	
    /** Instancia del panel de administración de certificados. */
    private JavaStorePanel ksmp = null;
    /** Padre del diálogo. */
    private Frame owner = null;
    
    /** Instancia del diálogo. */
	private static JavaStoreManager jsm = null;
	
	/** Instancia del diálogo de certificado. */
	private static JFileChooser fc = null;
	
	public static JFileChooser getFileChooser(){
		if (fc == null)
			fc = new JFileChooser();
		return fc;
	}
	/**
	 * <p> devuelve una instancia de la ventana de adminsitración del almacén de certificado.</p>
	 * @param owner Ventana que invoca el diálogo
	 * @param modal Indica si es modal o no.
	 * @param pksm .- Clase de conexión con el Keystore
	 * @param pksma .- Clase de enlace con el administrador del KeyStore
	 * @return Instancia invisible de la ventana.
	 */
	public static JavaStoreManager getInstance(final Frame owner, final boolean modal, 
			final IPKStoreManager pksm, final IPKStoreMaintainer pksma) {
		if (jsm == null) {
			LOG.debug("Instanciando JavaStoreManager");
			jsm = new JavaStoreManager(owner, modal, pksm, pksma);
		}
		
		return jsm;
	}
	
	/**
	 * <p>Constructor del diálogo de administración.</p>
	 * @param own .- Padre del diálogo
	 * @param modal .- Establece si la pantalla es modal o no
	 * @param pksm .- Clase que implementa el acceso al Key Store
	 * @param pksma .- Clase que implementa las acciones que se pueden hacer sobre el Key Store
	 */
    private JavaStoreManager(Frame own, boolean modal, IPKStoreManager pksm, IPKStoreMaintainer pksma) {
		super(own, Constants.LANG.getString("Store.Store"), modal);
		this.owner = own;
		dialogInit(pksm, pksma);
	}
	
    /**
     * <p>Inicializa y conecta con el panel de administración, el diálogo.</p>
     * @param pksm .- Clase que implementa el acceso al Key Store
	 * @param pksma .- Clase que implementa las acciones que se pueden hacer sobre el Key Store
     */
    protected void dialogInit(final IPKStoreManager pksm, final IPKStoreMaintainer pksma) {
    	try {
    	// Panel de administración
			LOG.debug("Cargando panel de Administración");
    		ksmp = new JavaStorePanel(owner, pksm, pksma);
    		
    	// Acciones
    		// Libera la instancia del panel de administración y cierra el diálogo
    		Action quitAction = new AbstractAction("quit") {
    			public void actionPerformed(final ActionEvent e) {
    				ksmp = null;
    				jsm.setVisible(false);
    				jsm.dispose();
    			}
    		};
    		
    		cancelBtn = new JButton();
    		cancelBtn.setAction(quitAction);
    		cancelBtn.setMnemonic(KeyEvent.VK_ESCAPE);
    		// Cerrar
    		cancelBtn.setText(Constants.LANG.getString("Store.Close"));
    		
   		// Layouts            
            setLayout(new GridBagLayout());
    		GridBagConstraints mainPanelGrid = new GridBagConstraints();
    		mainPanelGrid.gridx = 0;
    		mainPanelGrid.gridy = 0;
    		mainPanelGrid.gridwidth = 4;
    		mainPanelGrid.insets = new Insets(10, 10, 10, 10);
    		mainPanelGrid.fill = GridBagConstraints.BOTH;
    		mainPanelGrid.weightx = 1.0;
    		mainPanelGrid.weighty = 1.0;
    		add(ksmp, mainPanelGrid);
    		
    		GridBagConstraints cancBtnGrid = new GridBagConstraints();
    		cancBtnGrid.gridx = 3;
    		cancBtnGrid.gridy = 1;
    		cancBtnGrid.insets = new Insets(10, 20, 10, 10);
    		add(cancelBtn, cancBtnGrid);
            
    	// Dialog
    		setBackground(ksmp.getBackground());
    		setLocationRelativeTo(owner);
    		setSize(WIDTH, HEIGHT);
    		if (owner == null) { // Si no existe la ventana padre, se centra en pantalla el diálogo
        		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    			setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
        	}
    		setResizable(false);
    		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    		addWindowListener(new WindowAdapter() {
    			@Override
    			public void windowClosing(final WindowEvent e) {
    				ksmp = null;
    				jsm.setVisible(false);
    				jsm.dispose();
    			}
    		});
    		
    		this.doLayout();
			LOG.debug("Diálogo inicializado");
    		
    	} catch (Exception ex) {
    		LOG.error(ex.getMessage(), ex);
    	}
    }
}
