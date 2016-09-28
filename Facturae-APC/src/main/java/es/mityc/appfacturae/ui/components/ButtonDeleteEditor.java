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
import java.io.File;
import java.net.URL;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import es.mityc.appfacturae.exceptions.DatabaseOperationException;
import es.mityc.appfacturae.hibernate.FacturaeManager;
import es.mityc.appfacturae.hibernate.auxClass.xmlData;
import es.mityc.appfacturae.ui.renderers.ButtonDeleteRenderer;
import es.mityc.appfacturae.ui.windows.MainWindow;
import es.mityc.appfacturae.utils.IntermediaryUtil;
import es.mityc.appfacturae.utils.constants.Constants;
import es.mityc.appfacturae.utils.io.FacturaeUtil;
import es.mityc.facturae.utils.UnmarshalException;
import es.mityc.facturae.utils.UnmarshallerUtil;

public class ButtonDeleteEditor extends AbstractCellEditor implements TableCellEditor,ActionListener,MouseListener {
	
	/**
	 * Número de revisión.
	 */
	private static final long serialVersionUID = 814165491018004532L;
	
	JButton button;
	private String id = "";
	private static Log logger = LogFactory.getLog(ButtonDeleteEditor.class);
	
	private static ImageIcon delSel;
	private static ImageIcon delInaSel;
	private static ImageIcon del;
	private static ImageIcon delIna;
	static {
		loadIcons();
	}
	
	public ButtonDeleteEditor() {
		super();
		button = new JButton();
		button.addActionListener(this);
		button.addMouseListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (((JButton)e.getSource()).isEnabled()) { 

			MainWindow mw = MainWindow.getInstance();

			try {
				mw.getTransition().putTransitionPanel(Constants.LANG.getString("DeletingInvoice"));

				SQLQuery s = FacturaeManager.getInstance().executeQuery(Constants.QUERY3.replace("$1", id));
				List<?> ls = s.list();
				SQLQuery sVersion = FacturaeManager.getInstance().executeQuery("SELECT VERSION FROM INVOICE WHERE NVL(SERIES_CODE+NUMBER,NUMBER) = '" + id + "'");
				List<?> lsVersion = sVersion.list();
				String version = "";
				if (lsVersion != null && lsVersion.size() > 0)
					version = lsVersion.get(0).toString();
				if (ls != null && ls.size() > 0){
					File f = FacturaeManager.getInstance().loadInvoice(id);
					es.mityc.appfacturae.facturae.Facturae fe = null;
					es.mityc.facturae.GenericFacturae gfe = null;
					try {
						UnmarshallerUtil unmarshallerUtil = UnmarshallerUtil.getInstance(FacturaeUtil.getVersionOb(version));
						gfe = unmarshallerUtil.unmarshal(f);
						fe = IntermediaryUtil.getApplicationFacturae(gfe);
					} catch (UnmarshalException ue) {
						logger.error("Unmarshal error during invoice deletion: " + ue.getMessage(), ue);
						Constants.DIALOG.showErrorDelete();
					}
					
					fe.setId(Integer.parseInt(((Object[])ls.get(0))[0].toString()));
					// The ids and xml, which don't belong to facturae, have to be set
					fe.getParties().getSellerParty().setId(Integer.parseInt(((Object[])ls.get(0))[1].toString()));
					fe.getParties().getBuyerParty().setId(Integer.parseInt(((Object[])ls.get(0))[2].toString()));
					xmlData xd = new xmlData();
					xd.setXsig((java.sql.Blob)((Object[])ls.get(0))[3]);
					fe.setXsig(xd);
					
					try {
						FacturaeManager.getInstance().deleteFacturae(fe, true, version);
						int selRow = mw.getJTableInvoices().getSelectedRow();
						mw.getJTableInvoices().clearSelection();
						this.cancelCellEditing();
						((NoEdiTableModel)mw.getJTableInvoices().getModel()).removeRow(selRow);
					} catch (DatabaseOperationException ex) {
						logger.error("Data base operation error deleting invoice: " + ex.getMessage(), ex);
						Constants.DIALOG.showErrorDelete();
					}
				}
			} finally {
				mw.getTransition().removeTransitionPanel();
			}
		}
	}
	
	public void mouseEntered(java.awt.event.MouseEvent evt) {
		if (((JButton)evt.getSource()).isEnabled()){ 
			((MainWindow)((JButton)evt.getSource()).getTopLevelAncestor()).setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
		}
	}
	
	public void mouseExited(java.awt.event.MouseEvent evt) {
		if (((JButton)evt.getSource()).isEnabled()){ 
			((MainWindow)((JButton)evt.getSource()).getTopLevelAncestor()).setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
		}
	}
	
	public void mouseReleased(MouseEvent mouseevent) {
	}
	
	public void mouseClicked(MouseEvent mouseevent) {
	}
	
	public void mousePressed(MouseEvent mouseevent) {
	}
	
	//	Implement the one CellEditor method that AbstractCellEditor doesn't.
	public Object getCellEditorValue() {
		return button;
	}

	//	Implement the one method defined by TableCellEditor.
	public Component getTableCellEditorComponent(JTable table,Object value,	boolean isSelected,int row,int column) {
		id = table.getValueAt(row, 0).toString();
		if (isSelected){
			button.setIcon(delSel);
			button.setDisabledIcon(delInaSel);
		}
		else{
			button.setIcon(del);
			button.setDisabledIcon(delIna);
		}
		int selNode = 0;
		int[] selRows = ((MainWindow)table.getTopLevelAncestor()).getJTreeInvoices().getSelectionRows();
		if (selRows != null && selRows[0] > 0 && selRows[0] < 9)
			selNode=selRows[0];
		if (value != null && (selNode == 1 || selNode == 6)){
        	button.setToolTipText(Constants.LANG.getString("DeleteDraft"));
		} else{
        	button.setToolTipText(Constants.LANG.getString("DeleteInvoice"));
        }
        
		button.setEnabled(true);
        button.setBorderPainted(false);
		button.setContentAreaFilled(false);
        
		return button;
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