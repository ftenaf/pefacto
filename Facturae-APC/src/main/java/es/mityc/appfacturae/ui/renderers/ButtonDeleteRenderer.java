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
package es.mityc.appfacturae.ui.renderers;

import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;

public class ButtonDeleteRenderer extends JButton implements TableCellRenderer {

	private static ImageIcon delSel;
	private static ImageIcon delInaSel;
	private static ImageIcon del;
	private static ImageIcon delIna;
	static {
		loadIcons();
	}

	public ButtonDeleteRenderer(){
		super();
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		if (isSelected){
			setIcon(delSel);
			setDisabledIcon(delInaSel);
		}
		else{
			setIcon(del);
			setDisabledIcon(delIna);
		}
		int selNode = 0;
		int[] selRows = ((MainWindow)table.getTopLevelAncestor()).getJTreeInvoices().getSelectionRows();
		if (selRows != null && selRows.length >0 && selRows[0] > 0 && selRows[0] < 9)
			selNode=selRows[0];
		if (value != null && (selNode == 1 || selNode == 6)){
			setToolTipText(Constants.LANG.getString("DeleteDraft"));
		}
		else{
			setToolTipText(Constants.LANG.getString("DeleteInvoice"));
		}

		setEnabled(true);
		setBorderPainted(false);
		setContentAreaFilled(false);

		return this;
	}

	private static void loadIcons() {
		delSel = getIcon("/images/deleteSel.jpg");
		delInaSel = getIcon("/images/deleteInactiveSel.jpg");
		del = getIcon("/images/delete.jpg");
		delIna = getIcon("/images/deleteInactive.jpg");
	}

	private static ImageIcon getIcon(String name) {
		Class<?> classLoader = ButtonDeleteRenderer.class;
		URL url = classLoader.getResource(name);
		if (url != null)
			return new ImageIcon(url);
		else {
			return null;
		}
	}
}