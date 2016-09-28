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

import es.mityc.appfacturae.ui.renderers.ButtonDeleteRenderer;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.visualize.VisualizeUtil;

public class ButtonSeeHTMLEditor extends AbstractCellEditor implements TableCellEditor,ActionListener,MouseListener {
	
	JButton button;
	private String id = "";
	private String version = "";
	
	private static final String ojoSelPath = "/images/ojoSel.jpg";
	private static final String ojoPath = "/images/ojo.jpg";
	private static ImageIcon ojoSel = null;
	private static ImageIcon ojo = null;
	static {
		loadIcons();
	}
	
	public ButtonSeeHTMLEditor() {
		super();
		button = new JButton();
		button.addActionListener(this);
		button.addMouseListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Thread th = new Thread(new Runnable() { public void run() {
			MainWindow mw = MainWindow.getInstance();
			try {
				mw.getTransition().putTransitionPanel(Constants.LANG.getString("Visualizing"));
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				VisualizeUtil.showAsHTML(id, version);
			} finally {
				mw.getTransition().removeTransitionPanel();
				cancelCellEditing();
			}
		}});
		th.start();
	}
	
	public void mouseEntered(java.awt.event.MouseEvent evt) {
		((MainWindow)((JButton)evt.getSource()).getTopLevelAncestor()).setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
	}
	
	public void mouseExited(java.awt.event.MouseEvent evt) {
		((MainWindow)((JButton)evt.getSource()).getTopLevelAncestor()).setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
	}
	
	public void mouseReleased(MouseEvent mouseevent) {}
	public void mouseClicked(MouseEvent mouseevent) {}
	public void mousePressed(MouseEvent mouseevent) {}
	
	/**	Implement the one CellEditor method that AbstractCellEditor doesn't. */
	public Object getCellEditorValue() {
		return button;
	}

	/**	Implement the one method defined by TableCellEditor. */
	public Component getTableCellEditorComponent(JTable table,Object value,	boolean isSelected,int row,int column) {
		id = table.getValueAt(row, Constants.TABLE_INVOICES_ID_0).toString();
		version = table.getValueAt(row, Constants.TABLE_INVOICES_VERSION_5).toString();
		if (isSelected)
			button.setIcon(ojoSel);
		else
			button.setIcon(ojo);
        
		button.setToolTipText(Constants.LANG.getString("SeeWithFormat"));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
        
		return button;
	}
	
	private static void loadIcons() {
		ojoSel = getIcon(ojoSelPath);
		ojo = getIcon(ojoPath);
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