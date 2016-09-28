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
package es.mityc.appfacturae.ui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import es.mityc.appfacturae.ui.dialogs.importers.SaveInvoiceDialog;
import es.mityc.appfacturae.ui.renderers.ButtonDeleteRenderer;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;

public class ButtonSaveEditor extends AbstractCellEditor implements TableCellEditor, ActionListener, MouseListener {

	private static final long serialVersionUID = 8874524832140704754L;

	JButton button;
	private String value = null;
	private Integer inoviceStatus;
	private String inoviceId;
	private String inoviceVersion;

	private static final String infoSelPath = "/images/save_selected.jpg";
	private static final String infoPath = "/images/save.jpg";
	private static ImageIcon infoSel = null;
	private static ImageIcon info = null;
	static {
		loadIcons();
	}

	public ButtonSaveEditor() {
		super();
		button = new JButton();
		button.addActionListener(this);
		button.addMouseListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (value != null && value.length() > 0) {
			Thread th = new Thread(new Runnable() {
				public void run() {
					MainWindow mw = MainWindow.getInstance();
					mw.getTransition().putTransitionPanel(Constants.LANG.getString("SavingToDisk"));
					try {
						SaveInvoiceDialog eid = new SaveInvoiceDialog(null, true, inoviceStatus, inoviceId, inoviceVersion);
						eid.setVisible(true);
						mw.getTransition().removeTransitionPanel();
					} catch (Exception e) {
						mw.getTransition().removeTransitionPanel();
					} finally {
						cancelCellEditing();
					}
				}
			});
			th.start();
		}
	}

	public void mouseEntered(java.awt.event.MouseEvent evt) {
		((MainWindow) ((JButton) evt.getSource()).getTopLevelAncestor()).setCursor(java.awt.Cursor
			.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
	}

	public void mouseExited(java.awt.event.MouseEvent evt) {
		((MainWindow) ((JButton) evt.getSource()).getTopLevelAncestor()).setCursor(java.awt.Cursor
			.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
	}

	public void mouseReleased(MouseEvent mouseevent) {
	}

	public void mouseClicked(MouseEvent mouseevent) {
	}

	public void mousePressed(MouseEvent mouseevent) {
	}

	/**	Implement the one CellEditor method that AbstractCellEditor doesn't. */
	public Object getCellEditorValue() {
		return button;
	}

	/**	Implement the one method defined by TableCellEditor. */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value != null && value instanceof String && value.toString().length() > 0) {
			this.value = (String) value;
			inoviceId = table.getModel().getValueAt(row, Constants.TABLE_INVOICES_ID_0).toString();
			String status = table.getModel().getValueAt(row, Constants.TABLE_INVOICES_STATUS_10).toString();
			inoviceStatus = Integer.parseInt(status);
			inoviceVersion = table.getModel().getValueAt(row, Constants.TABLE_INVOICES_VERSION_5).toString();
			if (isSelected)
				button.setIcon(infoSel);
			else
				button.setIcon(info);

			button.setToolTipText(Constants.LANG.getString("InfoFACe"));
			button.setBorderPainted(false);
			button.setContentAreaFilled(false);
		} else {
			return null;
		}

		return button;
	}

	private static void loadIcons() {
		infoSel = getIcon(infoSelPath);
		info = getIcon(infoPath);
	}

	private static ImageIcon getIcon(String name) {
		Class<?> classLoader = ButtonDeleteRenderer.class;
		URL url = classLoader.getResource(name);
		if (url != null)
			return new ImageIcon(url);
		else
			return null;
	}
}
