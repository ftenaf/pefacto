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

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import es.mityc.appfacturae.utils.constants.Constants;

public class ImportResultCellRenderer extends DefaultTableCellRenderer{

	private java.awt.Color bkgColor = null;
	private java.awt.Color fgColorError = null;
	private java.awt.Color fgColorWarning = null;
	private java.awt.Color fgColorOk = null;
	private java.awt.Font font = null;
	
	public ImportResultCellRenderer(java.awt.Color bkgColorSelected, java.awt.Color fgColorError, java.awt.Color fgColorWarning, java.awt.Color fgColorOk, java.awt.Font font){
		super();
		this.bkgColor = bkgColorSelected;
		this.fgColorError = fgColorError;
		this.fgColorWarning = fgColorWarning;
		this.fgColorOk = fgColorOk;
		this.font = font;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		if (value != null){
			if (vColIndex != 2){
				setText(value.toString());
				setBorder(new EmptyBorder(5,15,5,0));
			}
		}
		setFont(font);
		setBackground(bkgColor);
		Object typeOb = table.getValueAt(rowIndex, 2);
		if (typeOb != null && !"".equals(typeOb.toString())){
			try{
				int type = Integer.parseInt(typeOb.toString());
				if (type == 0){
					setForeground(fgColorError);
					if (vColIndex == 0){
						setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/error_importation_message.gif")));
						setIconTextGap(10);
					}
					else if (vColIndex == 2){
						setBorder(new EmptyBorder(5,5,5,0));
						setText("x");
					}
					return this;
				}
				else if (type == 1){
					setForeground(fgColorWarning);
					if (vColIndex == 0){
						setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/warning_importation_message.gif")));
					}
					else if (vColIndex == 2){
						setBorder(new EmptyBorder(5,5,5,0));
						setText("");
					}
					return this;
				}
				else if (type == 2){
					setForeground(fgColorOk);
					if (vColIndex == 0){
						setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/correct_importation_message.gif")));
					}
					else if (vColIndex == 2){
						setBorder(new EmptyBorder(5,5,5,0));
						setText("");
					}
					return this;
				}
			}
			catch(NumberFormatException nfe){}
		}
		setForeground(Constants.FONT_COLOR);
		return this;
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

