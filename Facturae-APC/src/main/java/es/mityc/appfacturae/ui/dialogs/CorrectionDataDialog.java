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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;

import es.mityc.appfacturae.facturae.CorrectiveType;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.utils.InvoiceUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.facturae.utils.UnmarshalException;

public class CorrectionDataDialog extends JDialog{	    

	private String version = "";
	
	/** Creates new Dialog */
	public CorrectionDataDialog(CorrectiveType ct, String version) {
		
		this.version = version;
		initComponents(ct);
		this.setSize(475, 280);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
	}


	private void initComponents(CorrectiveType ct) {

		mainPanel = new javax.swing.JPanel();
		BufferedImage imgTopBar2 = null;
        try {
        	imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
        } catch (IOException ioe) {
        	Logger.getLogger(CorrectionDataDialog.class.getName()).log(Level.SEVERE, null, ioe);
        }
		jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
		jLabelTopBarMsg1 = new javax.swing.JLabel();
		jLabelTopBarMsg2 = new javax.swing.JLabel();
		jButtonReturn = new javax.swing.JButton();
		jPanelCorrectionData = new javax.swing.JPanel();
		jLabelInvoice = new javax.swing.JLabel();
		jTextFieldInvoiceSeries = new javax.swing.JTextField();
		jTextFieldInvoiceNumber = new javax.swing.JTextField();
		jButtonGoInvoice = new javax.swing.JButton();
		jLabelReason = new javax.swing.JLabel();
		jLabelAddReason = new javax.swing.JLabel();
		jTextFieldReasonCode = new javax.swing.JTextField();
		jTextFieldReasonDesc = new javax.swing.JTextField();
		jTextFieldAddReason = new javax.swing.JTextField();
		jLabelMethod = new javax.swing.JLabel();
		jTextFieldMethodCode = new javax.swing.JTextField();
		jTextFieldMethodDesc = new javax.swing.JTextField();
		jButtonHelp = new javax.swing.JButton();
		
		jLabelInvoice.setFont(Constants.FONT_PLAIN);
		jLabelMethod.setFont(Constants.FONT_PLAIN);
		jLabelReason.setFont(Constants.FONT_PLAIN);
		jLabelAddReason.setFont(Constants.FONT_PLAIN);
		jLabelInvoice.setForeground(Constants.FONT_COLOR);
		jLabelMethod.setForeground(Constants.FONT_COLOR);
		jLabelReason.setForeground(Constants.FONT_COLOR);
		jLabelAddReason.setForeground(Constants.FONT_COLOR);
		
		jTextFieldInvoiceNumber.setEditable(false);
		jTextFieldInvoiceSeries.setEditable(false);
		jTextFieldMethodCode.setEditable(false);
		jTextFieldMethodDesc.setEditable(false);
		jTextFieldReasonCode.setEditable(false);
		jTextFieldReasonDesc.setEditable(false);
		jTextFieldAddReason.setEditable(false);
		jTextFieldInvoiceNumber.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldInvoiceSeries.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldMethodCode.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldMethodDesc.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldReasonCode.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldReasonDesc.setBackground(Constants.BKG_MAIN_COLOR);
		jTextFieldAddReason.setBackground(Constants.BKG_MAIN_COLOR);
		if (ct.getInvoiceNumber() != null)
			jTextFieldInvoiceNumber.setText(ct.getInvoiceNumber());
		if (ct.getInvoiceSeriesCode() != null) 
			jTextFieldInvoiceSeries.setText(ct.getInvoiceSeriesCode());
		if (ct.getCorrectionMethod() != null)
			jTextFieldMethodCode.setText(ct.getCorrectionMethod());
		if (ct.getCorrectionMethodDescription() != null)
			jTextFieldMethodDesc.setText(ct.getCorrectionMethodDescription().value());
		if (ct.getReasonCode() != null)
			jTextFieldReasonCode.setText(ct.getReasonCode());
		if (ct.getReasonDescriptionValue() != null)
			jTextFieldReasonDesc.setText(ct.getReasonDescriptionValue());
		if (version.equals("3.2")){
			if (((es.mityc.appfacturae.facturae32.CorrectiveType)ct).getAdditionalReasonDescription() != null && !"".equals(((es.mityc.appfacturae.facturae32.CorrectiveType)ct).getAdditionalReasonDescription().trim()))
				jTextFieldAddReason.setText(((es.mityc.appfacturae.facturae32.CorrectiveType)ct).getAdditionalReasonDescription());
		}
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
        	public void windowClosing(WindowEvent e) {
        		jButtonReturnActionPerformed();
        	}
			public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
        });
		setTitle(Constants.LANG.getString("CorrectiveInfo"));

		mainPanel.setBackground(Constants.BKG_MAIN_COLOR);
		
		jPanelTopBar.setBackground(java.awt.Color.white);
		jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));

		jLabelTopBarMsg1.setFont(Constants.TITLE_FONT_ITALIC);
		jLabelTopBarMsg1.setForeground(Constants.FONT_COLOR);
		jLabelTopBarMsg1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabelTopBarMsg1.setText(Constants.LANG.getString("TopBarMessage1")); 

		jLabelTopBarMsg2.setFont(Constants.TITLE_FONT_ITALIC);
		jLabelTopBarMsg2.setForeground(Constants.FONT_COLOR);
		jLabelTopBarMsg2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabelTopBarMsg2.setText(Constants.LANG.getString("TopBarMessage2")); 

		org.jdesktop.layout.GroupLayout jPanelTopBarLayout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
		jPanelTopBar.setLayout(jPanelTopBarLayout);
		jPanelTopBarLayout.setHorizontalGroup(
				jPanelTopBarLayout.createSequentialGroup()
					.addContainerGap(140,Short.MAX_VALUE)
					.add(jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
						.add(jLabelTopBarMsg2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(jLabelTopBarMsg1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 270, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
					.addContainerGap()
						
		);
		jPanelTopBarLayout.setVerticalGroup(
				jPanelTopBarLayout.createSequentialGroup()
					.add(jLabelTopBarMsg1)
					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
					.add(jLabelTopBarMsg2)
		);

		jButtonReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_return.jpg"))); 
		jButtonReturn.setToolTipText(Constants.LANG.getString("Return")); 
		jButtonReturn.setBorderPainted(false);
		jButtonReturn.setContentAreaFilled(false);
		jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonReturnActionPerformed();
			}
		});
		jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				CorrectionDataDialog.this.mouseEntered(evt);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				CorrectionDataDialog.this.mouseExited(evt);
			}
		});

		jPanelCorrectionData.setBackground(Constants.BKG_MAIN_COLOR);
		jPanelCorrectionData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR), Constants.LANG.getString("CorrectiveInfo"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, Constants.FONT_BOLD, Constants.FONT_COLOR)); 

		jLabelInvoice.setText(Constants.LANG.getString("CorrectedInvoice"));
		
		jButtonGoInvoice.setText(Constants.LANG.getString("SeeInvoice")+" ("+Constants.LANG.getString("Original").toLowerCase()+")");
		jButtonGoInvoice.setFont(Constants.TITLE_FONT);
		jButtonGoInvoice.setForeground(Constants.FONT_COLOR);
		jButtonGoInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/optionButton.gif"))); 
		jButtonGoInvoice.setToolTipText(Constants.LANG.getString("SeeInvoice")+" ("+Constants.LANG.getString("Original")+")"); 
		jButtonGoInvoice.setBorderPainted(false);
		jButtonGoInvoice.setContentAreaFilled(false);
		jButtonGoInvoice.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonGoInvoiceActionPerformed(evt);
			}
		});
		jButtonGoInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				CorrectionDataDialog.this.mouseEntered(evt);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				CorrectionDataDialog.this.mouseExited(evt);
			}
		});

		jLabelReason.setText(Constants.LANG.getString("Reason"));
		
		jLabelAddReason.setText(Constants.LANG.getString("AdditionalReasonCorrective"));
		
		jLabelMethod.setText(Constants.LANG.getString("Method"));
		
		if (!version.equals("3.2")){
			jLabelAddReason.setVisible(false);
			jTextFieldAddReason.setVisible(false);
		}
		
		org.jdesktop.layout.GroupLayout jPanelCorrectionDataLayout = new org.jdesktop.layout.GroupLayout(jPanelCorrectionData);
		jPanelCorrectionData.setLayout(jPanelCorrectionDataLayout);
		jPanelCorrectionDataLayout.setHorizontalGroup(
				jPanelCorrectionDataLayout.createSequentialGroup()
					.addContainerGap()
					.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
							.add(jLabelInvoice, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.add(jLabelReason)
							.add(jLabelAddReason)
							.add(jLabelMethod))
					.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
					.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
							.add(jPanelCorrectionDataLayout.createSequentialGroup()
									.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
										.add(jTextFieldMethodCode,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,50,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(jTextFieldReasonCode,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,50,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(jTextFieldInvoiceSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
									.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanelCorrectionDataLayout.createSequentialGroup()
												.add(jTextFieldInvoiceNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jButtonGoInvoice)
												.add(170, 170, 170))
									.add(jTextFieldMethodDesc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 280, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
									.add(jTextFieldReasonDesc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 280, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
							.add(jTextFieldAddReason, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 335, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
					.addContainerGap()
		);
		jPanelCorrectionDataLayout.setVerticalGroup(
				jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanelCorrectionDataLayout.createSequentialGroup()
						.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jLabelInvoice)
								.add(jTextFieldInvoiceNumber, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jButtonGoInvoice)
								.add(jTextFieldInvoiceSeries, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jLabelReason)
								.add(jTextFieldReasonCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jTextFieldReasonDesc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jLabelMethod)
								.add(jTextFieldMethodDesc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(jTextFieldMethodCode, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jPanelCorrectionDataLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jLabelAddReason)
								.add(jTextFieldAddReason, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
		);

		jButtonHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_help.jpg"))); 
		jButtonHelp.setToolTipText(Constants.LANG.getString("Help")); 
		jButtonHelp.setBorderPainted(false);
		jButtonHelp.setContentAreaFilled(false);
		jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonHelpActionPerformed();
			}
		});
		jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				CorrectionDataDialog.this.mouseEntered(evt);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				CorrectionDataDialog.this.mouseExited(evt);
			}
		});

		org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(
				mainPanelLayout.createSequentialGroup()
				 	.addContainerGap()
				 	.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				 		.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 455, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(jPanelCorrectionData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 455, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
							.add(jButtonHelp,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							.add(jButtonReturn,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,32,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
					.addContainerGap()
		);
		mainPanelLayout.setVerticalGroup(
				mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(mainPanelLayout.createSequentialGroup()
						.addContainerGap()
						.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jPanelCorrectionData, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
								.add(jButtonReturn)
								.add(jButtonHelp)))
		);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 475, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 255, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		);

		pack();
	}
                             

	private void jButtonReturnActionPerformed() {
		setVisible(false);
	}

	private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_CorrectionData_"+Constants.LANG.getLocale().getLanguage()+".html");
    	if (helpFile == null) {
    		Constants.DIALOG.showErrorHelp();
	    	return;
    	}

    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
	}

	private void jButtonGoInvoiceActionPerformed(java.awt.event.ActionEvent evt) {
		try{
			((InvoiceUtil) Class.forName(Constants.APP_PROP.getProperty("appUtilsPath")+"Invoice"+FacturaeUtil.getVersionConst(version)+"Util").newInstance()).chargeInvoice(true, jTextFieldInvoiceSeries.getText()+jTextFieldInvoiceNumber.getText());
		} catch (InstantiationException e) {
			Constants.DIALOG.showErrorSeeCorrections("InstantiationException:" + e.getMessage());
		} catch (IllegalAccessException e) {
			Constants.DIALOG.showErrorSeeCorrections("IllegalAccessException:" + e.getMessage());
		} catch (ClassNotFoundException e) {
			Constants.DIALOG.showErrorSeeCorrections("ClassNotFoundException:" + e.getMessage());
		} catch (UnmarshalException e) {
			Constants.DIALOG.showErrorSeeCorrections(e.getMessage());
		}
	}
	
	private void mouseEntered(java.awt.event.MouseEvent evt){
		((JButton)evt.getSource()).getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	private void mouseExited(java.awt.event.MouseEvent evt){
		((JButton)evt.getSource()).getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private javax.swing.JButton jButtonGoInvoice;
	private javax.swing.JButton jButtonHelp;
	private javax.swing.JButton jButtonReturn;
	private javax.swing.JLabel jLabelInvoice;
	private javax.swing.JLabel jLabelMethod;
	private javax.swing.JLabel jLabelReason;
	private javax.swing.JLabel jLabelAddReason;
	private javax.swing.JLabel jLabelTopBarMsg1;
	private javax.swing.JLabel jLabelTopBarMsg2;
	private javax.swing.JPanel jPanelCorrectionData;
	private javax.swing.JPanel jPanelTopBar;
	private javax.swing.JTextField jTextFieldInvoiceNumber;
	private javax.swing.JTextField jTextFieldInvoiceSeries;
	private javax.swing.JTextField jTextFieldMethodCode;
	private javax.swing.JTextField jTextFieldMethodDesc;
	private javax.swing.JTextField jTextFieldReasonCode;
	private javax.swing.JTextField jTextFieldReasonDesc;
	private javax.swing.JTextField jTextFieldAddReason;
	private javax.swing.JPanel mainPanel;
}
