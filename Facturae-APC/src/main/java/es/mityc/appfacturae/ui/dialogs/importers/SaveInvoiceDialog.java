/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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
package es.mityc.appfacturae.ui.dialogs.importers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.mityc.appfacturae.exceptions.ImportExportException;
import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.importers.ExportInvoiceResult;
import es.mityc.appfacturae.importers.InvoiceExporter;
import es.mityc.appfacturae.ui.components.FadingCanvas;
import es.mityc.appfacturae.ui.components.ImportExportResult;
import es.mityc.appfacturae.ui.components.PicturedPanel;
import es.mityc.appfacturae.ui.transitions.GifAnimatedIcon;
import es.mityc.appfacturae.ui.transitions.GifWaitIndicator;
import es.mityc.appfacturae.ui.transitions.WaitIndicator;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FileUtil;

public class SaveInvoiceDialog extends JDialog {

	private static final long serialVersionUID = -1013765681683262306L;
	private static Log logger = LogFactory.getLog(SaveInvoiceDialog.class);
	private WaitIndicator waiter = null;
	private Thread th = null;
	private Thread waiterTh = null;
	private Integer invoiceStatus;
	private String invoiceId;
	private String invoiceVersion;

	public SaveInvoiceDialog(Frame parent, boolean modal, Integer invoiceStatus, String invoiceId, String invoiceVersion) {
		super(parent, modal);

		this.setResizable(false);
		this.setSize(390, 230);
		this.setTitle(Constants.LANG.getString("SaveToDisk"));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.invoiceStatus = invoiceStatus;
		this.invoiceId = invoiceId;
		this.invoiceVersion = invoiceVersion;

		init();

		String ext = Constants.XML;
		Boolean isDraft = false;
		if(invoiceStatus==1) isDraft = true;
		if (!isDraft)
			ext = Constants.XSIG;
		fcSaveInv.setSelectedFile(new File(invoiceId + ext));
		jTextFieldExportInvoice.setText(invoiceId + ext);
		
		setLocationRelativeTo(parent);
	}

	private void init() {
		BufferedImage imgTopBar2 = null;
		try {
			imgTopBar2 = ImageIO.read(getClass().getResourceAsStream("/images/topbar2.jpg"));
		} catch (IOException ioe) {
			logger.error("Error during image charge: " + ioe.getMessage());
		}

		jPanelInvoicesExport = new JPanel();
		jPanelTopBar = new PicturedPanel(imgTopBar2, 1.00f);
		jLabelTopBar1 = new javax.swing.JLabel();
		jLabelTopBar2 = new javax.swing.JLabel();
		jButtonReturn = new JButton();
		jLabelTransition = new JLabel();

		jTextFieldExportInvoice = new javax.swing.JTextField();
		jTextFieldExportInvoice.setEditable(false);
		jLabelExportInvoice = new javax.swing.JLabel();
		jLabelExportInvoiceDir = new javax.swing.JLabel();
		jButtonExportInvoiceDir = new javax.swing.JButton();
		jButtonExportInvoice = new javax.swing.JButton();

		jLabelTransition.setVerticalAlignment(SwingConstants.CENTER);
		jLabelTransition.setVerticalTextPosition(SwingConstants.TOP);
		jLabelTransition.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTransition.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabelTransition.setFont(Constants.FONT_PLAIN);
		jLabelTransition.setForeground(Constants.FONT_COLOR);

		jButtonReturn.setBorderPainted(false);
		jButtonReturn.setContentAreaFilled(false);
		jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg")));
		jButtonReturn.setToolTipText(Constants.LANG.getString("Return"));
		jButtonReturn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonReturn.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent evt) {
				jButtonReturn.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});

		jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));
		jPanelTopBar.setBackground(Color.white);
		jPanelTopBar.setBorder(javax.swing.BorderFactory.createLineBorder(Constants.BORDER_COLOR));
		jLabelTopBar1.setFont(Constants.TITLE_FONT_ITALIC_LITTLE);
		jLabelTopBar1.setForeground(Constants.FONT_COLOR);
		jLabelTopBar1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabelTopBar1.setText(Constants.LANG.getString("TopBarMessage1"));
		jLabelTopBar2.setFont(Constants.TITLE_FONT_ITALIC_LITTLE);
		jLabelTopBar2.setForeground(Constants.FONT_COLOR);
		jLabelTopBar2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabelTopBar2.setText(Constants.LANG.getString("TopBarMessage2"));

		org.jdesktop.layout.GroupLayout jPanelTopBarLayout = new org.jdesktop.layout.GroupLayout(jPanelTopBar);
		jPanelTopBar.setLayout(jPanelTopBarLayout);
		jPanelTopBarLayout.setHorizontalGroup(jPanelTopBarLayout.createSequentialGroup()
			.addContainerGap()
			.add(jPanelTopBarLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
					.add(jLabelTopBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
					.add(jLabelTopBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 345,
						org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap());
		jPanelTopBarLayout.setVerticalGroup(jPanelTopBarLayout.createSequentialGroup().add(5, 5, 5).add(jLabelTopBar1)
			.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(jLabelTopBar2).add(5, 5, 5));

		fcSaveInv = new JFileChooser();
		fcSaveInv.setFileSelectionMode(JFileChooser.FILES_ONLY);

		fd = new FadingCanvas();
		fd.setFont(Constants.TITLE_FONT);
		fd.setForeground(Constants.FONT_COLOR);

		jButtonExportInvoiceDir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_folder.jpg")));
		jButtonExportInvoiceDir.setToolTipText(Constants.LANG.getString("Examine"));
		jButtonExportInvoiceDir.setBorderPainted(false);
		jButtonExportInvoiceDir.setContentAreaFilled(false);
		jButtonExportInvoiceDir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getDestinyFolderToexportInvoice();
			}
		});
		jButtonExportInvoiceDir.addMouseListener(new ButtonCursor());
		
		jButtonExportInvoice.setIcon(new ImageIcon(getClass().getResource("/images/optionButton.gif")));
		jButtonExportInvoice.setDisabledIcon(new ImageIcon(getClass().getResource("/images/optionButton2.gif")));    	
		jButtonExportInvoice.setEnabled(false);
		jButtonExportInvoice.setFont(Constants.TITLE_FONT);
		jButtonExportInvoice.setForeground(Constants.FONT_COLOR);
		jButtonExportInvoice.setText(Constants.LANG.getString("Save"));
		jButtonExportInvoice.setToolTipText(Constants.LANG.getString("Save"));
		jButtonExportInvoice.setBorderPainted(false);
		jButtonExportInvoice.setContentAreaFilled(false);
		jButtonExportInvoice.setMargin(new Insets(1, 1, 1, 1));
		jButtonExportInvoice.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (th == null || !th.isAlive()) {
					th = new Thread(new Runnable() {
						public void run() {
							MainWindow mw = MainWindow.getInstance();
							try {
								mw.getTransition().putTransitionPanel(Constants.LANG.getString("SavingToDisk"));
								putTransitionPanel(Constants.LANG.getString("SavingToDisk"));
								ExportInvoiceResult result = new ExportInvoiceResult();
								InvoiceExporter invoiceExporter = new InvoiceExporter();
								File destinationFile = new File(jTextFieldExportInvoice.getText());
								File destinationDirectory = destinationFile.getParentFile();
								if (!FileUtil.existsDirectory(destinationDirectory)) {
									jButtonExportInvoice.setEnabled(false);
									logger.error("The directory does not exist or cannot be read");
									throw new FileNotFoundException(
										"The destination directory does not exist or cannot be read");
								}else{
									jButtonExportInvoice.setEnabled(true);
								}
								boolean save = true;
								if (FileUtil.existsFile(destinationFile)){
									logger.error("File already exists");
									if (Constants.DIALOG.showConfirmFileExists(jTextFieldExportInvoice.getText()) != JOptionPane.YES_OPTION) {
										save = false;
									}
								}
								if(save){
									try {
										switch (invoiceStatus) {
										case Constants.DRAFT_STATUS_TREE:
											result = invoiceExporter.exportInvoiceWithName(destinationFile,
												InvoiceClassType.OO, InvoiceStatusType.D, invoiceId);
											break;
										case Constants.ISSUED_STATUS_TREE:
											result = invoiceExporter.exportInvoiceWithName(destinationFile,
												InvoiceClassType.OO, InvoiceStatusType.I, invoiceId);
											break;
										case Constants.SENT_STATUS_TREE:
											result = invoiceExporter.exportInvoiceWithName(destinationFile,
												InvoiceClassType.OO, InvoiceStatusType.S, invoiceId);
											break;
										case Constants.RECEIVED_STATUS_TREE:
											result = invoiceExporter.exportInvoiceWithName(destinationFile,
												InvoiceClassType.OO, InvoiceStatusType.R, invoiceId);
											break;
										case Constants.CORRECTIVE_DRAFT_STATUS_TREE:
											result = invoiceExporter.exportInvoiceWithName(destinationFile,
												InvoiceClassType.OR, InvoiceStatusType.D, invoiceId);
											break;
										case Constants.CORRECTIVE_ISSUED_STATUS_TREE:
											result = invoiceExporter.exportInvoiceWithName(destinationFile,
												InvoiceClassType.OR, InvoiceStatusType.I, invoiceId);
											break;
										case Constants.CORRECTIVE_SENT_STATUS_TREE:
											result = invoiceExporter.exportInvoiceWithName(destinationFile,
												InvoiceClassType.OR, InvoiceStatusType.S, invoiceId);
											break;
										default:
											break;
										}
	
										ImportExportResult error;
										if (result.getResult() == ExportInvoiceResult.ExportInvoiceResultType.CORRECT) {
											error = new ImportExportResult(ImportExportResult.ImportErrorLevel.OK, result
												.getId(), result.getObservations());
											Constants.DIALOG.showOKSave("");
										} else {
											error = new ImportExportResult(ImportExportResult.ImportErrorLevel.ERROR,
												result.getId(), result.getObservations());
											Constants.DIALOG.showErrorSave(error.getError());
										}
	
									} catch (ImportExportException e) {
										logger.error("Error saving invoice: " + e.getMessage());
										Constants.DIALOG.showErrorSave("");
									}
								}

								mw.refreshProgressBar(100);
							} catch (FileNotFoundException e) {
								logger.error("Error saving invoice: " + e.getMessage());
								Constants.DIALOG.showErrorSave("");
							} catch (Throwable e) {
								logger.error("Error saving invoice: " + e.getMessage());
								Constants.DIALOG.showErrorSave("");
							} finally {
								removeTransitionPanel();
								mw.getTransition().removeTransitionPanel();
							}
						}
					});
					th.start();
				}
			}
		});
		jButtonExportInvoice.addMouseListener(new ButtonCursor());

		jLabelExportInvoice.setFont(Constants.FONT_BOLD);
		jLabelExportInvoice.setForeground(Constants.FONT_COLOR);
		jLabelExportInvoice.setText(Constants.LANG.getString("SaveInvoice"));

		jLabelExportInvoiceDir.setFont(Constants.FONT_PLAIN);
		jLabelExportInvoiceDir.setForeground(Constants.FONT_COLOR);
		jLabelExportInvoiceDir.setText(Constants.LANG.getString("FileName") + ":");

		GroupLayout jPanelInvoicesExportLayout = new GroupLayout(jPanelInvoicesExport);
		jPanelInvoicesExportLayout.setVerticalGroup(jPanelInvoicesExportLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(jPanelInvoicesExportLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(jLabelExportInvoice)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jLabelExportInvoiceDir)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(jPanelInvoicesExportLayout.createParallelGroup(Alignment.CENTER)
							.addComponent(jTextFieldExportInvoice, GroupLayout.PREFERRED_SIZE, 20,
								GroupLayout.PREFERRED_SIZE).addComponent(jButtonExportInvoiceDir)
							.addComponent(jButtonExportInvoice))));
		jPanelInvoicesExportLayout.setHorizontalGroup(jPanelInvoicesExportLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(jPanelInvoicesExportLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanelInvoicesExportLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(jLabelExportInvoice)
							.addComponent(jLabelExportInvoiceDir)
							.addGroup(jPanelInvoicesExportLayout.createSequentialGroup()
									.addComponent(jTextFieldExportInvoice, GroupLayout.PREFERRED_SIZE, 222,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jButtonExportInvoiceDir, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jButtonExportInvoice))).addContainerGap()));
		jPanelInvoicesExport.setLayout(jPanelInvoicesExportLayout);

		//Main Panel 
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
							.addComponent(jPanelInvoicesExport, GroupLayout.PREFERRED_SIZE, 370,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jPanelTopBar, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
							.addGroup(layout.createSequentialGroup()
									.addComponent(fd, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jButtonReturn, GroupLayout.PREFERRED_SIZE, 32,
										GroupLayout.PREFERRED_SIZE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(
			layout.createSequentialGroup().addContainerGap()
				.addComponent(jPanelTopBar, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(jPanelInvoicesExport, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jButtonReturn)
						.addComponent(fd, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))));
		getContentPane().setLayout(layout);

		pack();
	}

	private class ButtonCursor extends MouseAdapter {
		public void mouseEntered(java.awt.event.MouseEvent evt) {
			SaveInvoiceDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		}

		public void mouseExited(java.awt.event.MouseEvent evt) {
			SaveInvoiceDialog.this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * Puts a semi-translucent panel in MainWindow showing a parametric message with a animated icon
	 * @param message.- Message to show in the translucent panel
	 */
	public void putTransitionPanel(String message) {
		jLabelTransition.setText(message);
		if (jLabelTransition.getIcon() == null) {
			InputStream is = getClass().getResourceAsStream("/images/FactWaiter.gif");
			if (is != null) {
				GifAnimatedIcon animatedIcon = GifAnimatedIcon.getAnimatedIcon(is);
				if (animatedIcon != null)
					jLabelTransition.setIcon(animatedIcon);
			}
		}
		waiterTh = new Thread(new Runnable() {
			public void run() {
				waiter = new GifWaitIndicator(jPanelInvoicesExport, jLabelTransition);
			}
		});
		waiterTh.start();
	}

	/**
	 * Removes the semi-translucent panel
	 */
	public void removeTransitionPanel() {
		if (waiterTh != null) {
			try {
				waiterTh.join(500);
			} catch (InterruptedException e) {
			}
			waiterTh.interrupt();
			if (waiter != null) {
				waiter.setVisible(false);
				waiter.dispose();
			}
		}
	}

	private void getDestinyFolderToexportInvoice() {
		int returnVal = fcSaveInv.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			jTextFieldExportInvoice.setText(fcSaveInv.getSelectedFile().getAbsolutePath());
			jTextFieldExportInvoice.setForeground(Constants.FONT_COLOR);
			jButtonExportInvoice.setEnabled(true);
		}
	}

	private JFileChooser fcSaveInv = new JFileChooser();
	private javax.swing.JTextField jTextFieldExportInvoice;
	private javax.swing.JLabel jLabelExportInvoice;
	private javax.swing.JLabel jLabelExportInvoiceDir;
	private javax.swing.JButton jButtonExportInvoiceDir;
	private javax.swing.JButton jButtonExportInvoice;
	private JPanel jPanelInvoicesExport;
	private javax.swing.JPanel jPanelTopBar;
	private javax.swing.JLabel jLabelTopBar1;
	private javax.swing.JLabel jLabelTopBar2;
	private javax.swing.JLabel jLabelTransition = null;
	private JButton jButtonReturn = null;
	private FadingCanvas fd = null;
}
