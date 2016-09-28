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
package es.mityc.appfacturae.ui.dialogs;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import es.mityc.appfacturae.ui.components.CertificatesTree;
import es.mityc.appfacturae.ui.renderers.CertificatesTreeRenderer;
import es.mityc.appfacturae.utils.constants.Constants;

public class TrustSMTPServerCertificateDialog extends JDialog {
	
	private boolean result = false;
	
	public TrustSMTPServerCertificateDialog(Frame owner, boolean modal, String title, java.security.cert.X509Certificate[] certificatesChain) {
		super(owner,title,modal);
		
		setLocationRelativeTo(owner);
		
		init(certificatesChain);
		
		this.setSize(400,340);
		this.setResizable(false);
    	this.setLocationRelativeTo(owner);
    	this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    	 this.addWindowListener(new WindowListener() {
         	public void windowClosing(WindowEvent e) {
         		result = false;
         		setVisible(false);
         		dispose();
         	}
 			public void windowActivated(WindowEvent e) {}
 			public void windowClosed(WindowEvent e) {}
 			public void windowDeactivated(WindowEvent e) {}
 			public void windowDeiconified(WindowEvent e) {}
 			public void windowIconified(WindowEvent e) {}
 			public void windowOpened(WindowEvent e) {}
         });		
    	this.setTitle(Constants.LANG.getString(title));
	}
	
	private void init(java.security.cert.X509Certificate[] certificateChain){
		
		mainPanel = new JPanel();
		buttonPanel = new JPanel();
		
		jButtonOk = new JButton();
        jButtonCancel = new JButton();
        
        jTextArea = new JTextArea();
        
        certificatesTree = new JTree();
        
        jButtonOk.setBorderPainted(false);
        jButtonOk.setContentAreaFilled(false);
        jButtonOk.setHorizontalAlignment(SwingConstants.CENTER);
        jButtonOk.setIcon(new ImageIcon(getClass().getResource("/images/button_accept.jpg")));
        jButtonOk.setToolTipText(Constants.LANG.getString("Accept"));
        jButtonOk.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonOk.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent evt) {
				jButtonOk.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});	
        jButtonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				result = true;
				setVisible(false);
				dispose();
			}
		});

        jButtonCancel.setBorderPainted(false);
        jButtonCancel.setContentAreaFilled(false);
        jButtonCancel.setIcon(new ImageIcon(getClass().getResource("/images/button_cancel.jpg")));
        jButtonCancel.setToolTipText(Constants.LANG.getString("Cancel"));
        jButtonCancel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonCancel.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
		    }
			public void mouseExited(MouseEvent evt) {
				jButtonCancel.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
        jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
			   result = false;
			   setVisible(false);
			   dispose();
			}
		});
				
        mainPanel.setLayout(new GridBagLayout());
        
		// TextArea
		jTextArea = new JTextArea(3,100);
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setText(Constants.LANG.getString("TrustCertificateQuestion"));
		jTextArea.setEditable(false);
		jTextArea.setFont(Constants.FONT_BOLD);
		jTextArea.setForeground(Constants.FONT_COLOR);
		jTextArea.setBackground(Constants.BKG_MAIN_COLOR);
		jTextArea.setMargin(new Insets(20,20,20,20));
		jTextArea.setBorder(null);
		
		GridBagConstraints jTextAreaConstraints = new GridBagConstraints();
	    jTextAreaConstraints.gridx = 0;
	    jTextAreaConstraints.gridy = 0;
	    jTextAreaConstraints.gridwidth = 3;
	    jTextAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
	    jTextAreaConstraints.weightx = 1.0;
	    jTextAreaConstraints.insets = new Insets(40,20,10,20);
	    mainPanel.add(jTextArea, jTextAreaConstraints);
		
        // JTree
	    certificatesTree.setBackground(Constants.BKG_MAIN_COLOR);
	    certificatesTree.setAlignmentY(SwingConstants.CENTER);
	    certificatesTree.setAlignmentX(SwingConstants.CENTER);
        certificatesTree.setCellRenderer(new CertificatesTreeRenderer());
        certificatesTree.setModel(new CertificatesTree(certificateChain).getModel());
        certificatesTree.setSize(250,150);
        
        for (int i = 0; i < certificateChain.length; ++i)
        	certificatesTree.expandRow(i);
        
	    jScrollPaneTree = new JScrollPane();
	    jScrollPaneTree.setBackground(Constants.BORDER_COLOR);
        jScrollPaneTree.setBorder(BorderFactory.createTitledBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("CertificatesTree"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Constants.TITLE_FONT, Constants.FONT_COLOR)); 
        jScrollPaneTree.setForeground(Constants.BORDER_COLOR);
        jScrollPaneTree.setOpaque(false);
        jScrollPaneTree.setViewportView(certificatesTree);
		jScrollPaneTree.setBorder(new TitledBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true), "Cadena de confianza"));
		jScrollPaneTree.setSize(350, 200);
		
        GridBagConstraints treeConstraints = new GridBagConstraints();
	    treeConstraints.gridx = 0;
	    treeConstraints.gridy = 1;
	    treeConstraints.gridwidth = 3;
	    treeConstraints.fill = GridBagConstraints.HORIZONTAL;
	    treeConstraints.weightx = 1.0;
	    treeConstraints.ipady = 70;
	    treeConstraints.insets = new Insets(10,20,10,20);
	    mainPanel.add(jScrollPaneTree, treeConstraints);
	    
	    buttonPanel.add(jButtonOk);
	    buttonPanel.add(jButtonCancel);
	    GridBagConstraints buttonPanelConstraints = new GridBagConstraints();
	    buttonPanelConstraints.gridx = 0;
	    buttonPanelConstraints.gridy = 2;
	    buttonPanelConstraints.ipadx = 32;
	    buttonPanelConstraints.ipady = 32;
	    buttonPanelConstraints.gridwidth = 4;
	    buttonPanelConstraints.anchor = GridBagConstraints.CENTER;
	    buttonPanelConstraints.insets = new Insets(1,1,8,1);
		mainPanel.add(buttonPanel, buttonPanelConstraints);
	    
        add(mainPanel);
	}
	
	/** Variables declaration */
	private JPanel mainPanel = null;
	private JPanel buttonPanel = null;
	private JTree certificatesTree = null;
	
	private JButton jButtonOk = null;
	private JButton jButtonCancel = null;
	
	private JScrollPane jScrollPaneTree = null;
	
	private JTextArea jTextArea = null;

	public boolean getResult() {
		return result;
	}
}