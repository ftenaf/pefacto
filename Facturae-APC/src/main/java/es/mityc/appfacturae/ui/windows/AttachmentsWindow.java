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
package es.mityc.appfacturae.ui.windows;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.AttachedDocument;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.NoEdiTableModel;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.dialogs.ContextualHelpDialog;
import es.mityc.appfacturae.ui.dialogs.InputAttachmentDialog;
import es.mityc.appfacturae.ui.renderers.CustomCellRenderer;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.OSUtil;

public class AttachmentsWindow extends javax.swing.JFrame {
	private static final long serialVersionUID = -712022559251791193L;

	private static Log logger = LogFactory.getLog(AttachmentsWindow.class);
	private FadingCanvas fd = null;
	private String invoiceId = null;
	
	// Singelton pattern
	private static AttachmentsWindow aw = null;
	
	/**
	 * Dialog for manage invoice attachments
	 * @param att.- ArrayList with attached documents
	 * @param id .- Invoice identification
	 * @param allowInclude Indica si se permite añadir anexos. 
	 * @return La nueva ventana de anexos.
	 */
	public static AttachmentsWindow getInstance(ArrayList<Object[]> att, String id) {
		if (aw == null)
			aw = new AttachmentsWindow(att, id);
		
		return aw;
	}
	
	/** Creates new form Attachments */
	private AttachmentsWindow(ArrayList<Object[]> att, String id) {
		super();
		
		initComponents();
		
		invoiceId = id;
		setAttachments(att);
		
		/** Loading images */
		BufferedImage imgLogoApp = null;
		try {
			imgLogoApp = ImageIO.read(this.getClass().getResourceAsStream("/images/logoapp.jpg"));
		} catch(IOException ioe) {
			logger.error(Constants.LANG.getString("ConfigurationWindowException3") + ioe.getMessage());
		}
		
		this.setSize(490, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(imgLogoApp);
	}

	private void initComponents() {

		mainPanel = new javax.swing.JPanel();
		
		BufferedImage imgTopBar2 = null;
        try {
        	imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
        } catch (IOException ioe) {
        	Logger.getLogger(ReceiveInvoiceWindow.class.getName()).log(Level.SEVERE, null, ioe);
        }
        jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
		
		jLabelTopBarMsg1 = new javax.swing.JLabel();
		jLabelTopBarMsg2 = new javax.swing.JLabel();
		
		
		jButtonSee = new javax.swing.JButton();
		jButtonReturn = new javax.swing.JButton();
		jButtonHelp = new javax.swing.JButton();
		jButtonDelete = new javax.swing.JButton();
		jButtonAdd = new javax.swing.JButton();
		jScrollPaneAttachments = new javax.swing.JScrollPane();
		jTableAttachments = new javax.swing.JTable();

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
		setTitle(Constants.LANG.getString("Attachments"));

		fd = new FadingCanvas();
        fd.setFont(Constants.TITLE_FONT);
        fd.setForeground(Constants.FONT_COLOR);
        
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
				jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
					.add(jPanelTopBarLayout.createSequentialGroup()
							.addContainerGap(450, 450))
						.add(jPanelTopBarLayout.createParallelGroup()
							.add(jLabelTopBarMsg1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)								
							.add(jLabelTopBarMsg2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
		);
		jPanelTopBarLayout.setVerticalGroup(
				jPanelTopBarLayout.createParallelGroup()
				.add(jPanelTopBarLayout.createSequentialGroup()
						.add(4, 4, 4)
						.add(jLabelTopBarMsg1)
						.add(3, 3, 3)
						.add(jLabelTopBarMsg2)
						.addContainerGap())
		);

		

		jButtonSee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_see.jpg")));
		jButtonSee.setToolTipText(Constants.LANG.getString("SeeContent"));
		jButtonSee.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
		jButtonSee.setBorderPainted(false);
		jButtonSee.setContentAreaFilled(false);
		jButtonSee.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openFile();
			}
		});
		jButtonSee.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				AttachmentsWindow.this.mouseEntered(evt);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				AttachmentsWindow.this.mouseExited(evt);
			}
		});

		jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg"))); 
        jButtonReturn.setToolTipText(Constants.LANG.getString("Return")); 
        jButtonReturn.setBorderPainted(false);
        jButtonReturn.setContentAreaFilled(false);
        jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AttachmentsWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	AttachmentsWindow.this.mouseExited(evt);
            }
        });
        
        jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButtonReturnActionPerformed();
            }
        });
        
        jButtonHelp.setIcon(new ImageIcon(getClass().getResource("/images/button_help.jpg"))); 
        jButtonHelp.setToolTipText(Constants.LANG.getString("Help")); 
        jButtonHelp.setBorderPainted(false);
        jButtonHelp.setContentAreaFilled(false);
        jButtonHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	AttachmentsWindow.this.mouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	AttachmentsWindow.this.mouseExited(evt);
            }
        });
        jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButtonHelpActionPerformed();
            }
        });
        
        
		jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_minus.jpg")));
		jButtonDelete.setToolTipText(Constants.LANG.getString("Remove"));
		jButtonDelete.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
		jButtonDelete.setBorderPainted(false);
		jButtonDelete.setContentAreaFilled(false);
		jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				delAttachment();
			}
		});
		jButtonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				AttachmentsWindow.this.mouseEntered(evt);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				AttachmentsWindow.this.mouseExited(evt);
			}
		});
		jButtonDelete.setVisible(false);

		jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_mini_plus.jpg")));
		jButtonAdd.setToolTipText(Constants.LANG.getString("Add"));
		jButtonAdd.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.SELECTION_COLOR));
		jButtonAdd.setBorderPainted(false);
		jButtonAdd.setContentAreaFilled(false);
		jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addAttachment();
			}
		});
		jButtonAdd.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				AttachmentsWindow.this.mouseEntered(evt);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				AttachmentsWindow.this.mouseExited(evt);
			}
		});
		jButtonAdd.setVisible(false);

		jTableAttachments.setModel(new NoEdiTableModel(
	            new Object [][] {

	            },
	            new String [] {
	            	Constants.LANG.getString("Format"), 		
	            	Constants.LANG.getString("Path"), 		
	            	Constants.LANG.getString("Description"),	 
	            	Constants.LANG.getString("IncludeInXML")	
	            },
	            new int[]{}
	        ));
		
		for (int i = 0 ; i < jTableAttachments.getColumnCount() ; i++)
			jTableAttachments.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
		
		jTableAttachments.setGridColor(Constants.SELECTION_COLOR);
		jScrollPaneAttachments.setViewportView(jTableAttachments);
		jScrollPaneAttachments.getViewport().setBackground(Constants.BKG_MAIN_COLOR);
		
		jTableAttachments.getColumnModel().getColumn(0).setResizable(false);
		jTableAttachments.getColumnModel().getColumn(0).setMaxWidth(65);
		jTableAttachments.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
		jTableAttachments.getColumnModel().getColumn(1).setResizable(false);
		jTableAttachments.getColumnModel().getColumn(1).setMaxWidth(190);
		jTableAttachments.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
		jTableAttachments.getColumnModel().getColumn(2).setResizable(false);
		jTableAttachments.getColumnModel().getColumn(2).setMaxWidth(190);
		jTableAttachments.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
		jTableAttachments.getColumnModel().getColumn(3).setResizable(false);
		jTableAttachments.getColumnModel().getColumn(3).setMaxWidth(85);
		jTableAttachments.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer(Constants.SELECTION_COLOR,java.awt.Color.white,Constants.FONT_COLOR,Constants.FONT_COLOR,Constants.FONT_PLAIN,javax.swing.SwingConstants.LEFT));
		jTableAttachments.getTableHeader().setReorderingAllowed(false);
		
		org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(
				mainPanelLayout.createSequentialGroup()
					.addContainerGap()
					.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 470, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(mainPanelLayout.createSequentialGroup()
							.add(jScrollPaneAttachments, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 420, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
									.add(jButtonSee, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
									.add(jButtonDelete, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
									.add(jButtonAdd, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
						.add(org.jdesktop.layout.GroupLayout.LEADING,mainPanelLayout.createSequentialGroup()
							.add(fd)
							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							.add(jButtonHelp,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
							.add(jButtonReturn,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
					.addContainerGap()
		);
		mainPanelLayout.setVerticalGroup(
				mainPanelLayout.createSequentialGroup()
						.addContainerGap()
						.add(jPanelTopBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jScrollPaneAttachments, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(mainPanelLayout.createSequentialGroup()
										.add(jButtonAdd)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jButtonDelete)
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jButtonSee)))
						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
						.add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jButtonReturn)
								.add(jButtonHelp)
								.add(fd))
						.addContainerGap()
		);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 490, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
		);

		pack();
	}

	private void jButtonReturnActionPerformed() {
		aw.setVisible(false);
		aw.dispose();
    	aw = null;
	}
	
    private void jButtonHelpActionPerformed() {
    	URL helpFile = this.getClass().getResource("/html/help_AttachmentsWindow_"+Constants.LANG.getLocale().getLanguage()+".html");
    	if (helpFile == null) {
    		Constants.DIALOG.showErrorHelp();
	    	return;
    	}

    	ContextualHelpDialog chd = new ContextualHelpDialog(Constants.LANG.getLocale(), helpFile.toString());
    	chd.setVisible(true);
    	chd.dispose();
    }
	
	private void mouseEntered(java.awt.event.MouseEvent evt) {                                      
		this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
	}                                     

	private void mouseExited(java.awt.event.MouseEvent evt) {                                     
		this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
	}                                    

	private void openFile() {  
		int row = jTableAttachments.getSelectedRow();
		if (row >= 0) {
			String url = null;
			url = ((NoEdiTableModel)jTableAttachments.getModel()).getValueAt(row, 1).toString();
			OSUtil.open(url);
		}
	}                                        

	private void addAttachment() {

		ArrayList<String> prevDesc = new ArrayList<String> ();
		DefaultTableModel dtm = (DefaultTableModel)jTableAttachments.getModel();
		int includedLength = 0;
		for (int i = 0; i < dtm.getRowCount(); ++i) {
			prevDesc.add((String)dtm.getValueAt(i, 2));
			if (dtm.getValueAt(i, 3) != null && Constants.LANG.getString("Yes").equals(dtm.getValueAt(i, 3).toString())) {
				File f = new File(dtm.getValueAt(i, 1).toString());
				includedLength += f.length();
			}
		}

		InputAttachmentDialog iad = InputAttachmentDialog.getInstance(this, prevDesc, true, includedLength);

		iad.setVisible(true);

		String[] result = iad.getValues();

		if (result == null || result.length != 4) {
			Thread th2 = new Thread(new Runnable() { public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				fd.showMessage(Constants.LANG.getString("NOOKIncompleteForm"), Constants.ERROR_MSG_COLOR);
			}});
			th2.start();		
			return;
		}
		String format = result[0];
		String document = result[1];
		String description = result[2];
		String includingXML = result[3];

		if (format != null && !"".equals(format) && document != null && !"".equals(document)) {// If not, it was canceled
			((NoEdiTableModel)jTableAttachments.getModel()).addRow(new Object[] {
					format, 
					document, 
					description, 
					includingXML 
			});    	
			
			AttachedDocument ad = new AttachedDocument();
			ad.setInvoiceId(invoiceId);
			ad.setExtension(format);
			ad.setDescription(description);
			ad.setPath(document);
			ad.setIsIncluded(Constants.LANG.getString("Yes").equals(includingXML)?1:0);
			
			try {
				FacturaeManager.getInstance().saveAttachment(ad);
			} catch (DatabaseOperationException e1) {
				Constants.DIALOG.showErrorSaveAttachment();
			}
			
			Thread th2 = new Thread(new Runnable() { public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				fd.showMessage(Constants.LANG.getString("OKMessageAttachAdded"), Constants.OK_MSG_COLOR);
			}});
			th2.start();
		} else {
			Thread th2 = new Thread(new Runnable() { public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				fd.showMessage(Constants.LANG.getString("OKMessageCanceledByUser"), Constants.OK_MSG_COLOR);
			}});
			th2.start();
		}
	}

	private void delAttachment() {
		int row = jTableAttachments.getSelectedRow();
		if (row >= 0) {
			
			if (Constants.LANG.getString("Yes").equals(((NoEdiTableModel)jTableAttachments.getModel()).getValueAt(row, 3).toString())) {
				fd.showMessage(Constants.LANG.getString("NODeleteMessageAttachInlcuded"), Constants.ERROR_MSG_COLOR);
				return;
			}
				
			
			String format = ((NoEdiTableModel)jTableAttachments.getModel()).getValueAt(row, 0).toString();
			String document = ((NoEdiTableModel)jTableAttachments.getModel()).getValueAt(row, 1).toString();
			String description = ((NoEdiTableModel)jTableAttachments.getModel()).getValueAt(row, 2).toString();
			String includingXML = ((NoEdiTableModel)jTableAttachments.getModel()).getValueAt(row, 3).toString();
			
			((NoEdiTableModel)jTableAttachments.getModel()).removeRow(row);
			
			AttachedDocument ad = new AttachedDocument();
			ad.setPath(document);
			ad.setInvoiceId(invoiceId);
			ad.setDescription(description);
			ad.setExtension(format);
			ad.setIsIncluded(Constants.LANG.getString("Yes").equals(includingXML)?1:0);
			
	    	try {
				FacturaeManager.getInstance().deleteAttachment(ad);
			} catch (DatabaseOperationException e) {
				Constants.DIALOG.showErrorDeleteAttachment();
			}
	    	fd.showMessage(Constants.LANG.getString("OKMesaggeAttachDeleted"), Constants.OK_MSG_COLOR);
		} else
			fd.showMessage(Constants.LANG.getString("NOOKForDeleteMessage"), Constants.ERROR_MSG_COLOR);
	}
	
	private void setAttachments(ArrayList<Object[]> att) {
		Object[] attFile = null;
		for (int i = 0; i < att.size(); i++) {
			attFile = att.get(i);
			// Make legible internal value
			if (attFile[3] != null && "1".equals(attFile[3].toString().trim()))
				attFile[3] = Constants.LANG.getString("Yes");
			else
				attFile[3] = Constants.LANG.getString("No");
			
			// Add row to Jtable
			((NoEdiTableModel)jTableAttachments.getModel()).addRow(attFile);
			
			// Is attachment included? then it may be not erasable
			if (attFile[3] != null && Constants.LANG.getString("Yes").equals(attFile[3].toString())) {
				int row = ((NoEdiTableModel)jTableAttachments.getModel()).getRowCount() - 1;
				for (int j = 0; j < 4; ++j)
					((CustomCellRenderer)jTableAttachments.getCellRenderer(row, j)).setCellBackgroundGray();
			}
		}
	}

	private javax.swing.JButton jButtonReturn = null;
	private javax.swing.JButton jButtonHelp = null;
	private javax.swing.JButton jButtonSee = null;
	private javax.swing.JButton jButtonDelete = null;
	private javax.swing.JButton jButtonAdd = null;
	private javax.swing.JLabel jLabelTopBarMsg2 = null;
	private javax.swing.JLabel jLabelTopBarMsg1 = null;
	private javax.swing.JPanel jPanelTopBar = null;
	private javax.swing.JScrollPane jScrollPaneAttachments = null;
	private javax.swing.JTable jTableAttachments = null;
	private javax.swing.JPanel mainPanel = null;
}