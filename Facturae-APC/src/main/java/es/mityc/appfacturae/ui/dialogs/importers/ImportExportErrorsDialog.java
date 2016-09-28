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
package es.mityc.appfacturae.ui.dialogs.importers;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import es.mityc.appfacturae.ui.components.ImportExportResult;
import es.mityc.appfacturae.ui.components.NoEdiTableModel;
import es.mityc.appfacturae.ui.renderers.HeaderRenderer;
import es.mityc.appfacturae.ui.renderers.ImportResultCellRenderer;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.StringUtil;

public class ImportExportErrorsDialog extends JDialog{
	
	public ImportExportErrorsDialog(Frame f, boolean modal, ArrayList<ImportExportResult> errors, int correct){
		super(f,modal);
		initComponents();
		this.setTitle(Constants.LANG.getString("NOOKImportExportTitle"));
		this.setSize(525, 185);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		chargeData(errors, correct);
	}

	private void chargeData(ArrayList<ImportExportResult> errors, int correct) {
		int total = errors.size(); 
		jTextAreaError.setText(StringUtil.wordWrap(Constants.LANG.getString("InvoicesImportedResultMessage") + " " + correct + "/" + total, 70));
		for (int i = 0 ; i < total ; i++){
			((DefaultTableModel)jTableErrors.getModel()).addRow(new Object[]{errors.get(i).getId(), errors.get(i).getError(),errors.get(i).getLevel().ordinal()});
		}
	}

	private void initComponents() {
	
		jPanelErrors = new JPanel();
		jScrollPaneErrors = new JScrollPane();
		jTableErrors = new JTable();

		jButtonReturn = new JButton();
		jButtonReturn.setIcon(new ImageIcon(getClass().getResource("/images/button_return.jpg"))); 
		jButtonReturn.setToolTipText(Constants.LANG.getString("Return")); 
		jButtonReturn.setBorderPainted(false);
		jButtonReturn.setContentAreaFilled(false);
		jButtonReturn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		jButtonReturn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				ImportExportErrorsDialog.this.mouseEntered(evt);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				ImportExportErrorsDialog.this.mouseExited(evt);
			}
		});

		jTableErrors.setModel(new NoEdiTableModel(
				new Object [][] {

				},
				new String [] {
						Constants.LANG.getString("InvoiceId"), 				
						Constants.LANG.getString("Error"),
						"!"
				},
				new int[]{}
		));

		for (int i = 0 ; i < jTableErrors.getColumnCount() ; i++){
			jTableErrors.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer());
		}

		jTableErrors.setGridColor(Constants.SELECTION_COLOR);
		jScrollPaneErrors.setViewportView(jTableErrors);
		jTableErrors.getTableHeader().setReorderingAllowed(false);
		jTableErrors.getColumnModel().getColumn(0).setResizable(false);
		jTableErrors.getColumnModel().getColumn(0).setCellRenderer(new ImportResultCellRenderer(java.awt.Color.white,Constants.ERROR_MSG_COLOR,Constants.INDIVIDUAL_COLOR, Constants.OK_MSG_COLOR,Constants.FONT_PLAIN));
		jTableErrors.getColumnModel().getColumn(0).setPreferredWidth(30);
		jTableErrors.getColumnModel().getColumn(1).setResizable(false);
		jTableErrors.getColumnModel().getColumn(1).setCellRenderer(new ImportResultCellRenderer(java.awt.Color.white,Constants.ERROR_MSG_COLOR,Constants.INDIVIDUAL_COLOR, Constants.OK_MSG_COLOR,Constants.FONT_PLAIN));
		jTableErrors.getColumnModel().getColumn(1).setPreferredWidth(300);
		jTableErrors.getColumnModel().getColumn(2).setResizable(false);
		jTableErrors.getColumnModel().getColumn(2).setCellRenderer(new ImportResultCellRenderer(java.awt.Color.white,Constants.ERROR_MSG_COLOR,Constants.INDIVIDUAL_COLOR, Constants.OK_MSG_COLOR,Constants.FONT_PLAIN));
		jTableErrors.getColumnModel().getColumn(2).setPreferredWidth(0);
		jTableErrors.getColumnModel().getColumn(2).setMaxWidth(0);
		jTableErrors.getColumnModel().getColumn(2).setMinWidth(0);

		jScrollPaneErrors.getViewport().setBackground(Constants.BKG_MAIN_COLOR);

		// TextArea Error
		jTextAreaError = new JTextArea();
		jTextAreaError.setFont(Constants.FONT_PLAIN);
		jTextAreaError.setForeground(Constants.FONT_COLOR);
		jTextAreaError.setBackground(Constants.BKG_MAIN_COLOR);
		jTextAreaError.setEditable(false);

		jTextAreaError.setWrapStyleWord(true);

		org.jdesktop.layout.GroupLayout jPanelLayout = new org.jdesktop.layout.GroupLayout(jPanelErrors);
		jPanelErrors.setLayout(jPanelLayout);
		jPanelLayout.setHorizontalGroup(
				jPanelLayout.createSequentialGroup()
				.addContainerGap()
				.add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)	
						.add(jTextAreaError, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(jScrollPaneErrors, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 500, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(jButtonReturn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
						.addContainerGap()
		);
		jPanelLayout.setVerticalGroup(
				jPanelLayout.createSequentialGroup()
				.addContainerGap()
				.add(jTextAreaError,org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
				.add(jScrollPaneErrors, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
				.add(jButtonReturn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
				.addContainerGap()
		);

		add(jPanelErrors);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
	
	/** Action on mouse entered*/
	private void mouseEntered(java.awt.event.MouseEvent evt) {
		jPanelErrors.getTopLevelAncestor().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
	}
	
	/** Action on mouse exited*/
	private void mouseExited(java.awt.event.MouseEvent evt) {
		jPanelErrors.getTopLevelAncestor().setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
	}
	
	private JTextArea jTextAreaError = null;
	private JPanel jPanelErrors = null;
	private JScrollPane jScrollPaneErrors = null;
	private JTable jTableErrors;
	private JButton jButtonReturn = null;
}
