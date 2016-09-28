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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer{

	private java.awt.Color bkgColorSelected = null;
	private java.awt.Color bkgColorNotSelected = null;
	private java.awt.Color fgColorSelected = null;
	private java.awt.Color fgColorNotSelected = null;
	private java.awt.Font font = null;
	private int alignment = -1;
	
	public CustomCellRenderer(java.awt.Color bkgColorSelected, java.awt.Color bkgColorNotSelected, java.awt.Color fgColorSelected, java.awt.Color fgColorNotSelected, java.awt.Font font, int alignment){
		super();
		this.bkgColorSelected = bkgColorSelected;
		this.bkgColorNotSelected = bkgColorNotSelected;
		this.fgColorSelected = fgColorSelected;
		this.fgColorNotSelected = fgColorNotSelected;
		this.font = font;
		this.alignment = alignment;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {

		if (isSelected){
			setBackground(bkgColorSelected);
			setForeground(fgColorSelected);
		}
		else{
			setBackground(bkgColorNotSelected);
			setForeground(fgColorNotSelected);
		}
		if (value != null){
			setHorizontalAlignment(alignment);
			setToolTipText(value.toString());
			setText(value.toString());
			setFont(font);
		} else{
			setText("");
		}

		return this;
	}
	
	public void setCellBackgroundGray() {
		setBackground(Color.gray);
	}

	// The following methods override the defaults for performance reasons
	@Override
	public void validate() {}
	@Override
	public void revalidate() {}
	@Override
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
	@Override
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}
