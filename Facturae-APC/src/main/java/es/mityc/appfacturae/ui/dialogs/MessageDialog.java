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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;

import es.mityc.appfacturae.utils.constants.Constants;

public class MessageDialog extends javax.swing.JDialog {
    
	public static final int OK = 1;
	public static final int CANCEL = -1;
	
	int result = MessageDialog.CANCEL;
	
    /** Creates new form with Accept and Cancel options */
    public MessageDialog(java.awt.Frame parent, boolean modal, String windowTitle, String message, ImageIcon ii) {
        super(parent, modal);
        initComponents(message,ii);
        this.setTitle(windowTitle);
        this.setLocationRelativeTo(null);
    }
   
    private void initComponents(String message, ImageIcon ii) {

        panel = new javax.swing.JPanel();
        jTextMessage = new javax.swing.JTextArea();
        jLabelImage = new javax.swing.JLabel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonCancelPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });

        panel.setBackground(Constants.BKG_MAIN_COLOR);

        jTextMessage.setColumns(20);
        jTextMessage.setEditable(false);
        jTextMessage.setLineWrap(true);
        jTextMessage.setRows(5);
        jTextMessage.setWrapStyleWord(true);
        jTextMessage.setOpaque(false);
        jTextMessage.setText(message);
        jTextMessage.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        jTextMessage.setForeground(new java.awt.Color(19,91,135));
        jTextMessage.setAlignmentX(1/2);

        jLabelImage.setPreferredSize(new java.awt.Dimension(40, 40));
        jLabelImage.setIcon(ii);
        
        jButtonOk.setText(Constants.LANG.getString("Accept"));
        jButtonOk.setPreferredSize(new java.awt.Dimension(27, 27));
        jButtonOk.setIcon(Constants.OK_ICON);
        jButtonOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MessageDialog.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	MessageDialog.this.mouseExited(evt);
            }
        });
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkPerformed(evt);
            }
        });
        
        jButtonCancel.setText(Constants.LANG.getString("Cancel"));
        jButtonCancel.setPreferredSize(new java.awt.Dimension(27, 27));
        jButtonCancel.setIcon(Constants.CANCEL_ICON);
        jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MessageDialog.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	MessageDialog.this.mouseExited(evt);
            }
        });
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelPerformed();
            }
        });

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(panel);
        panel.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(jPanel9Layout.createSequentialGroup()
                    	.add(20, 20, 20)
                        .add(jLabelImage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(40, 40, 40)
                        .add(jTextMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                    .add(jPanel9Layout.createSequentialGroup()
                    	.add(jButtonOk)
                        .add(10,10,10)
                        .add(jButtonCancel)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelImage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                	.add(jButtonCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                	.add(jButtonOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }
    
    private void mouseEntered(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
    }

    private void mouseExited(java.awt.event.MouseEvent evt) {
        this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    }
    
    private void jButtonOkPerformed(java.awt.event.ActionEvent evt) {
    	result = MessageDialog.OK;
    	this.dispose();
    }
    
    private void jButtonCancelPerformed() {
    	result = MessageDialog.CANCEL;
    	this.dispose();
    }

    public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
    // Variables declaration
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JPanel panel;
    private javax.swing.JTextArea jTextMessage;
    
}
