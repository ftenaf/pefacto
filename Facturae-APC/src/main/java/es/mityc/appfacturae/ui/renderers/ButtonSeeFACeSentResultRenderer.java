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

import es.mityc.appfacturae.utils.constants.Constants;

public class ButtonSeeFACeSentResultRenderer extends JButton implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3521658744106795249L;
	private static final String infoSelPath = "/images/infoSel.jpg";
	private static final String infoPath = "/images/info.jpg";
	private static ImageIcon infoSel = null;
	private static ImageIcon info = null;
	static {
		loadIcons();
	}

	public ButtonSeeFACeSentResultRenderer(){
		super();
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		if(value!=null  && value instanceof String && value.toString().length()>0) {
			if (isSelected)
				setIcon(infoSel);
			else
				setIcon(info);
	
			setToolTipText(Constants.LANG.getString("InfoFACe"));
		} else {
			return null;
		}
		setBorderPainted(false);
		setContentAreaFilled(false);

		return this;
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
