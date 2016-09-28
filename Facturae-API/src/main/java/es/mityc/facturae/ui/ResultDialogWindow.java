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
package es.mityc.facturae.ui;

import javax.swing.ImageIcon;

import es.mityc.facturae.utils.constants.Constants;

/**
 * This window is the general dialog window. It shows errors and other notifications. It is 
 * based on JDialog. 
 */
public class ResultDialogWindow extends javax.swing.JDialog {
    
    /** Creates new form ResultDialogWindow */
    public ResultDialogWindow(java.awt.Frame parent, boolean modal, String windowTitle, String message, ImageIcon ii) {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel.setBackground(Constants.BKG_MAIN_COLOR);

        jTextMessage.setColumns(20);
        jTextMessage.setEditable(false);
        jTextMessage.setLineWrap(true);
        jTextMessage.setRows(5);
        jTextMessage.setWrapStyleWord(true);
        jTextMessage.setOpaque(false);
        jTextMessage.setText(message);
        jTextMessage.setFont(Constants.TITLE_FONT);
        jTextMessage.setForeground(Constants.FONT_COLOR);
        jTextMessage.setAlignmentX(1/2);

        jLabelImage.setPreferredSize(new java.awt.Dimension(40, 40));
        jLabelImage.setIcon(ii);
        
        jButtonOk.setBorderPainted(false);
        jButtonOk.setContentAreaFilled(false);
        jButtonOk.setPreferredSize(new java.awt.Dimension(27, 27));
        jButtonOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ok_mini.jpg")));
        jButtonOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ResultDialogWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	ResultDialogWindow.this.mouseExited(evt);
            }
        });
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(panel);
        panel.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createSequentialGroup()
                    	.add(20, 20, 20)
                        .add(jLabelImage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(40, 40, 40)
                        .add(jTextMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(137, 137, 137)
                        .add(jButtonOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
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
                .add(jButtonOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
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
    	this.dispose();
    }
    
    // Variables declaration
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JPanel panel;
    private javax.swing.JTextArea jTextMessage;
    
}
