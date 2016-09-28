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

public class ButtonSeeHTMLRenderer extends JButton implements TableCellRenderer {

	private static final String ojoSelPath = "/images/ojoSel.jpg";
	private static final String ojoPath = "/images/ojo.jpg";
	private static ImageIcon ojoSel = null;
	private static ImageIcon ojo = null;
	static {
		loadIcons();
	}

	public ButtonSeeHTMLRenderer(){
		super();
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		if (isSelected)
			setIcon(ojoSel);
		else
			setIcon(ojo);

		setToolTipText(Constants.LANG.getString("SeeWithFormat"));

		setBorderPainted(false);
		setContentAreaFilled(false);

		return this;
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