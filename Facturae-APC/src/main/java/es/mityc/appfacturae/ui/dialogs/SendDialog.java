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
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.utils.constants.Constants;


public class SendDialog  extends JDialog {

	private FadingCanvas fd;
	private String mail = "";
	
	public SendDialog (Frame parent, boolean modal, String mail) {
		super(parent, modal);
	    
		this.mail = mail;
	    
	    this.setResizable(false);
	    this.setSize(440, 265);
	    this.setTitle(Constants.LANG.getString("SendInvoiceTitle"));
	    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    this.addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonCancelActionPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });		
	    
		init();
		setLocationRelativeTo(parent);
		this.setVisible(true);
	}

	private void init() {

		mainPanel = new JPanel();
		mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
		emailPanel = new JPanel();
		
		jScrollEmail = new JScrollPane();
		
        fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		
	    informationLabel = new JLabel("* "+Constants.LANG.getString("SMTPServerProxy")+": "+Constants.LANG.getString("MessageModifyConfigurationData"));
	    informationLabel.setForeground(Constants.FONT_COLOR);
	    informationLabel.setFont(Constants.FONT_ITALIC);
	    informationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		jButtonOk = new JButton();
		jButtonCancel = new JButton();
		jButtonHelp = new JButton();
		
		jButtonOk.setBorderPainted(false);
		jButtonOk.setContentAreaFilled(false);
		jButtonOk.setHorizontalAlignment(SwingConstants.LEFT);
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
				
				if(receiverTextField.getText() == null || "".equals(receiverTextField.getText().trim())){
					receiverTextField.setBackground(Constants.BKG_ERROR_COLOR);
					receiverTextField.requestFocus();  
					fd.showMessage(Constants.LANG.getString("ParameterRequiredMessage"), Constants.ERROR_MSG_COLOR);
					return;
				} else {
					setVisible(false);
					dispose();
				}

			}
		});

		jButtonCancel.setBorderPainted(false);
		jButtonCancel.setContentAreaFilled(false);
		jButtonOk.setHorizontalAlignment(SwingConstants.LEFT);
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
				jButtonCancelActionPerformed();
			}
		});
		
		jButtonHelp.setBorderPainted(false);
		jButtonHelp.setContentAreaFilled(false);
		jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/images/button_help.jpg")));
		jButtonHelp.setToolTipText(Constants.LANG.getString("Help"));
		jButtonHelp.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonHelp.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
		    }
			public void mouseExited(MouseEvent evt) {
				jButtonHelp.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonHelpActionPerformed();
			}
		});

		
		/*************************************** 
		 *            Email Panel 
		 **************************************/
		
		jScrollEmail.setViewportView(emailPanel);
		jScrollEmail.setBorder(null);

		emailPanel.setBackground(Constants.BKG_MAIN_COLOR);
		emailPanel.setLayout(new GridBagLayout());
		emailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(Constants.SELECTION_COLOR, 1, true), Constants.LANG.getString("SendData"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

	    receiverLabel = new JLabel(Constants.LANG.getString("Receiver"));
		receiverLabel.setForeground(Constants.FONT_COLOR);
		receiverLabel.setFont(Constants.FONT_PLAIN);
		receiverLabel.setIcon(new ImageIcon(getClass().getResource("/images/asterisco.jpg")));
		receiverLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		receiverLabel.setHorizontalAlignment(SwingConstants.LEFT);

		receiverTextField = new JTextField();
		receiverTextField.setForeground(Constants.FONT_COLOR);
		receiverTextField.setHorizontalAlignment(SwingConstants.LEFT);
		receiverTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
            	keyTypedErrorTextField(evt);
            }
        });
		receiverTextField.setText(mail);
		
	    subjectLabel = new JLabel(Constants.LANG.getString("Subject"));
		subjectLabel.setForeground(Constants.FONT_COLOR);
		subjectLabel.setFont(Constants.FONT_PLAIN);
		subjectLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		subjectTextField = new JTextField();
		subjectTextField.setForeground(Constants.FONT_COLOR);
		subjectTextField.setHorizontalAlignment(SwingConstants.LEFT);
		subjectTextField.setText(Constants.CONFIG_PROP.getProperty("DefaultSubject"));
		
	    messageLabel = new JLabel(Constants.LANG.getString("Message"));
	    messageLabel.setForeground(Constants.FONT_COLOR);
	    messageLabel.setFont(Constants.FONT_PLAIN);
	    messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
	    
	    messageTextArea = new JTextArea();
	    messageTextArea.setForeground(Constants.FONT_COLOR);
	    messageTextArea.setFont(Constants.FONT_PLAIN);
	    messageTextArea.setLineWrap(true);
	    messageTextArea.setBorder(new LineBorder(Constants.SELECTION_COLOR, 1, true));
	    
	    jScrollMessage = new javax.swing.JScrollPane();
	    jScrollMessage.setBackground(Constants.BKG_MAIN_COLOR);
	    jScrollMessage.setAutoscrolls(true);
	    jScrollMessage.setOpaque(false);
	    jScrollMessage.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
	    jScrollMessage.setViewportView(messageTextArea);
	    
	    org.jdesktop.layout.GroupLayout emailPanelLayout = new org.jdesktop.layout.GroupLayout(emailPanel);
        emailPanel.setLayout(emailPanelLayout);
        emailPanelLayout.setHorizontalGroup(
        		emailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(emailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(emailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(emailPanelLayout.createSequentialGroup()
                        .add(receiverLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(receiverTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(emailPanelLayout.createSequentialGroup()
                        .add(subjectLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(subjectTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(emailPanelLayout.createSequentialGroup()
                        .add(messageLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(12, 12, 12))
        );
        emailPanelLayout.setVerticalGroup(
        		emailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(emailPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(emailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(receiverLabel)
                    .add(receiverTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(emailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                    .add(subjectLabel)
                    .add(subjectTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        		.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        		.add(emailPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
        			.add(messageLabel)
        			.add(jScrollMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        		.addContainerGap()));

        
		/**********************************
         *          Main Panel 
         **********************************/
                
        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
            	.addContainerGap()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                		.add(emailPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 420, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                	    .add(org.jdesktop.layout.GroupLayout.TRAILING,mainPanelLayout.createSequentialGroup()
                	    	.add(5,5,5)
                	    	.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                	    			.add(org.jdesktop.layout.GroupLayout.TRAILING,mainPanelLayout.createSequentialGroup()
                	    					.add(jButtonOk)
                	    					.add(jButtonCancel)))
                            .add(7,7,7)
                            .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            		.add(org.jdesktop.layout.GroupLayout.TRAILING,mainPanelLayout.createSequentialGroup()
                            				.add(fd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            				.add(5,5,5)
                            				.add(jButtonHelp))))
                        .add(informationLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
            		.addContainerGap()
            		.add(emailPanel)
            		.add(11,11,11)
            		.add(informationLabel)
            		.add(11,11,11)
            		.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            				.add(jButtonOk)
            				.add(jButtonHelp)
            				.add(fd)
            				.add(jButtonCancel))
            		.addContainerGap()));

        
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 440, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 265, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        
        
        pack();

	}
	
	private void jButtonCancelActionPerformed() {
		receiverTextField.setText("");
		subjectTextField.setText("");
		messageTextArea.setText("");
		
		setVisible(false);
		dispose();
	}

	public String[] getValues() {

		String[] result = new String[3];
		result[0] = receiverTextField.getText();
		result[1] = subjectTextField.getText();
		result[2] = messageTextArea.getText();

		return result;
	}

	
	private void keyTypedErrorTextField(java.awt.event.KeyEvent evt){
		((JTextField)evt.getSource()).setBackground(java.awt.Color.white);	
	}
	
    private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_SendInvoice_"+Constants.LANG.getLocale().getLanguage()+".html");
    	if (helpFile == null) {
    		Constants.DIALOG.showErrorHelp();
	    	return;
    	}

    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
    }
	
	/** Declaration of variables **/
	
    private JPanel mainPanel = null;
    private JPanel emailPanel = null;
    
    private JScrollPane jScrollEmail = null;
    private JScrollPane jScrollMessage = null;
	
    private JLabel receiverLabel = null;
    private JLabel subjectLabel = null;
    private JLabel messageLabel = null;
    private JLabel informationLabel = null;
    
    private JTextField receiverTextField = null;
    private JTextField subjectTextField = null;
    private JTextArea messageTextArea = null;
    
    private JButton jButtonOk = null;
    private JButton jButtonCancel = null;
    private JButton jButtonHelp = null;
	
}
