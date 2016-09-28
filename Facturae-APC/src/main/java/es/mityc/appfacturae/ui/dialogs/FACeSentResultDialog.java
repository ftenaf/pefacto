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
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gobiernodecanarias.ceh.efactura.ws.v2.FacturaExtendidaV2;

import es.mityc.appfacturae.hibernate.auxClass.FACeSentResult;
import es.mityc.appfacturae.ui.transitions.Transition;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.PeFACUtils;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.Base64;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFactura;
import es.mityc.facturaeface.exception.FACeException;

public class FACeSentResultDialog extends JDialog {
	private FACeSentResult faceResult;
	private static Log logger = LogFactory.getLog(FACeSentResultDialog.class);
	private MainWindow mw;
	private boolean isSent = false;

	/**
	 * 
	 */
	private static final long serialVersionUID = 295597664019440592L;

	public static void showFACeReceiverDialog(MainWindow mw, FACeSentResult faceResult, boolean isSent) {
		FACeSentResultDialog dialog = new FACeSentResultDialog(mw, true, faceResult, isSent);
		dialog.setVisible(true);
	}

	private FACeSentResultDialog(MainWindow mw, boolean modal, FACeSentResult faceResult, boolean isSent) {
		super(mw, modal);
		this.mw = mw;
		this.faceResult = faceResult;
		this.isSent = isSent;
		initComponents();
	}

	private void initComponents() {
		panelPrin = new JPanel();
		jButtonYes = new JButton();
		jButtonNo = new JButton();
		jButtonDeliveryJustification = new JButton();

		if (isSent) {
			jLabelSentOk = new JLabel(Constants.LANG.getString("FACeSentOk"));
		} else {
			jLabelSentOk = new JLabel("");
		}
		jLabelSentOk.setForeground(Constants.FONT_COLOR);
		jLabelSentOk.setFont(Constants.FONT_PLAIN);
		jLabelSentOk.setHorizontalAlignment(SwingConstants.LEFT);

		jLabelDeliveryJustification = new JLabel(Constants.LANG.getString("MsgDeliveryJustification"));
		jLabelDeliveryJustification.setForeground(Constants.FONT_COLOR);
		jLabelDeliveryJustification.setFont(Constants.FONT_PLAIN);
		jLabelDeliveryJustification.setHorizontalAlignment(SwingConstants.LEFT);

		jButtonDeliveryJustification.setBorderPainted(false);
		jButtonDeliveryJustification.setContentAreaFilled(false);
		jButtonDeliveryJustification.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonDeliveryJustification.setIcon(new ImageIcon(getClass().getResource("/images/pdf_icon.png")));
		jButtonDeliveryJustification.setToolTipText(Constants.LANG.getString("MsgDeliveryJustification"));
		jButtonDeliveryJustification.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonDeliveryJustification.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent evt) {
				jButtonDeliveryJustification.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonDeliveryJustification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonDeliveryJustificationActionPerformed();
			}
		});

		jLabelStatusQuestion = new JLabel(Constants.LANG.getString("MsgCheckFACeStatus"));
		jLabelStatusQuestion.setForeground(Constants.FONT_COLOR);
		jLabelStatusQuestion.setFont(Constants.FONT_PLAIN);
		jLabelStatusQuestion.setHorizontalAlignment(SwingConstants.LEFT);

		jButtonYes.setBorderPainted(false);
		jButtonYes.setContentAreaFilled(false);
		jButtonYes.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonYes.setIcon(new ImageIcon(getClass().getResource("/images/button_accept.jpg")));
		jButtonYes.setToolTipText(Constants.LANG.getString("Yes"));
		jButtonYes.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonYes.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent evt) {
				jButtonYes.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonYesActionPerformed();
			}
		});

		jButtonNo.setBorderPainted(false);
		jButtonNo.setContentAreaFilled(false);
		jButtonNo.setHorizontalAlignment(SwingConstants.CENTER);
		jButtonNo.setIcon(new ImageIcon(getClass().getResource("/images/button_cancel.jpg")));
		jButtonNo.setToolTipText(Constants.LANG.getString("No"));
		jButtonNo.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				jButtonNo.getTopLevelAncestor().setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			public void mouseExited(MouseEvent evt) {
				jButtonNo.getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
		jButtonNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonNoActionPerformed();
			}
		});

		// The Main Panel is built
		panelPrin.setBackground(Constants.BKG_MAIN_COLOR);
		panelPrin.setLayout(new GridBagLayout());

		GridBagConstraints jLabelSentOkConstranits = new GridBagConstraints();
		jLabelSentOkConstranits.gridx = 0;
		jLabelSentOkConstranits.gridy = 0;
		jLabelSentOkConstranits.gridwidth = 2;
		jLabelSentOkConstranits.gridheight = 1;
		jLabelSentOkConstranits.anchor = GridBagConstraints.WEST;
		jLabelSentOkConstranits.insets = new Insets(3, 20, 6, 10);
		panelPrin.add(jLabelSentOk, jLabelSentOkConstranits);

		GridBagConstraints jLabelDeliveryJustificationConstranits = new GridBagConstraints();
		jLabelDeliveryJustificationConstranits.gridx = 0;
		jLabelDeliveryJustificationConstranits.gridy = 1;
		jLabelDeliveryJustificationConstranits.gridwidth = 1;
		jLabelDeliveryJustificationConstranits.gridheight = 2;
		jLabelDeliveryJustificationConstranits.anchor = GridBagConstraints.WEST;
		jLabelDeliveryJustificationConstranits.insets = new Insets(3, 20, 6, 10);
		panelPrin.add(jLabelDeliveryJustification, jLabelDeliveryJustificationConstranits);

		GridBagConstraints jButtonDeliveryJustificationConstraints = new GridBagConstraints();
		jButtonDeliveryJustificationConstraints.gridx = 2;
		jButtonDeliveryJustificationConstraints.gridy = 1;
		jButtonDeliveryJustificationConstraints.gridwidth = 2;
		jButtonDeliveryJustificationConstraints.gridheight = 2;
		jButtonDeliveryJustificationConstraints.anchor = GridBagConstraints.WEST;
		jButtonDeliveryJustificationConstraints.insets = new Insets(3, 3, 6, 10);
		panelPrin.add(jButtonDeliveryJustification, jButtonDeliveryJustificationConstraints);

		GridBagConstraints jLabelStatusQuestionConstraints = new GridBagConstraints();
		jLabelStatusQuestionConstraints.gridx = 0;
		jLabelStatusQuestionConstraints.gridy = 3;
		jLabelStatusQuestionConstraints.gridwidth = 4;
		jLabelStatusQuestionConstraints.fill = GridBagConstraints.HORIZONTAL;
		jLabelStatusQuestionConstraints.anchor = GridBagConstraints.WEST;
		jLabelStatusQuestionConstraints.insets = new Insets(3, 20, 6, 10);
		panelPrin.add(jLabelStatusQuestion, jLabelStatusQuestionConstraints);

		GridBagConstraints jButtonYesConstraints = new GridBagConstraints();
		jButtonYesConstraints.gridx = 0;
		jButtonYesConstraints.gridy = 4;
		jButtonYesConstraints.anchor = GridBagConstraints.EAST;
		jButtonYesConstraints.insets = new Insets(3, 10, 3, 3);
		panelPrin.add(jButtonYes, jButtonYesConstraints);

		GridBagConstraints jButtonNoConstraints = new GridBagConstraints();
		jButtonNoConstraints.gridx = 1;
		jButtonNoConstraints.gridy = 4;
		jButtonNoConstraints.anchor = GridBagConstraints.CENTER;
		jButtonNoConstraints.insets = new Insets(3, 10, 3, 10);
		panelPrin.add(jButtonNo, jButtonNoConstraints);

		getContentPane().add(panelPrin);

		this.setSize(400, 300);
		this.setTitle(Constants.LANG.getString("FACeSentResult"));
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				jButtonNoActionPerformed();
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}

			public void windowDeiconified(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
			}

			public void windowOpened(WindowEvent e) {
			}
		});

		t = new Transition(panelPrin);
	}

	private void jButtonYesActionPerformed() {
		// consulta FACe
		Thread th = new Thread(new Runnable() {
			public void run() {
				logger.debug("Consultado estado: " + faceResult.getRegisterCode());
				mw.getTransition().putTransitionPanel(Constants.LANG.getString("Visualizing"));
				t.putTransitionPanel(Constants.LANG.getString("Visualizing"));
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				boolean isOk = false;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				String result = "";
				try {
					SSPPResultadoConsultarFactura faceStatus = PeFACUtils.getInstance()
							.getInvoiceStatus(faceResult.getRegisterCode());
					if (faceStatus != null) {
						result = MessageFormat.format(Constants.LANG.getString("MsgFACeStatus"),
								faceStatus.getTramitacion().getCodigo_estado(),
								faceStatus.getTramitacion().getDescripcion_estado(),
								faceStatus.getAnulacion().getCodigo_estado(),
								faceStatus.getAnulacion().getDescripcion_estado());
						isOk = true;
					}
				} catch (FACeException e) {
					logger.error("Error al obtener el estado de la factura " + e.getMessage());
					result = e.getMessage();
				} catch(Exception e) {
					logger.error("Fallo al obtener el estado de la factura " + e.getMessage());
					result = e.getMessage();
				}
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				t.removeTransitionPanel();
				mw.getTransition().removeTransitionPanel();
				if (isOk) {
					Constants.DIALOG.showInfo(result);
				} else {
					if (result != null && result.length() > 0) {
						Constants.DIALOG.showErrorFACe(result);
					}
				}
				setVisible(false);
				dispose();
			}
		});
		th.start();
	}

	private void jButtonNoActionPerformed() {
		this.setVisible(false);
		this.dispose();
	}

	private void jButtonDeliveryJustificationActionPerformed() {
		Thread th = new Thread(new Runnable() { public void run() {
			String fileName = "justificante-" + faceResult.getRegisterCode() + ".pdf";
			File f = new File(fileName);
			f.deleteOnExit();
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				logger.fatal("No se ha podido crear el fichero temporal para el justificante. Se cancela la operación.", e);
			}
			mw.getTransition().putTransitionPanel(Constants.LANG.getString("Visualizing"));
			t.putTransitionPanel(Constants.LANG.getString("Visualizing"));
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
			if(out != null) {
				if (faceResult.getDeliveryJustification() == null) {
					logger.debug("Consultado estado: " + faceResult.getRegisterCode());
					try {
						FacturaExtendidaV2 facturaExtendida = PeFACUtils.getInstance()
								.consultarFacturaPefac((long) Integer.parseInt(faceResult.getRegisterCode()));
						facturaExtendida.getComprobanteRegistro().writeTo(out);
					} catch(NumberFormatException e) {
						logger.error("Error al convertir el identificador de factura a númerico", e);
					} catch (FACeException e) {
						logger.error("Error al consultar la infromación de la factura", e);
					} catch (IOException e) {
						logger.error("Error al obtener el justificante de registro", e);
					}
				} else {
					try {
						out.write(Base64.decode(faceResult.getDeliveryJustification()));
					} catch (IOException e) {
						logger.error("Error saving a PDF file.: " + e.getMessage(), e);
					}
				}
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.fatal("Error al cerrar el canal de salida del justificante. Se aborta la operación.", e);
					f = null;
				}
				if(f != null) {
					try {
						Desktop.getDesktop().open(f);
					} catch (Exception e) {
						logger.error("Error launching PDF viewer.: " + e.getMessage(), e);
					}
				}
			}
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			t.removeTransitionPanel();
			mw.getTransition().removeTransitionPanel();
			setVisible(false);
			dispose();
		}});
		th.start();
	}

	private JLabel jLabelDeliveryJustification = null;
	private JButton jButtonDeliveryJustification = null;
	private JLabel jLabelStatusQuestion = null;
	private JLabel jLabelSentOk = null;
	private JButton jButtonYes = null;
	private JButton jButtonNo = null;
	private JPanel panelPrin = null;

	Transition t = null;

}
