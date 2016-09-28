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
import javax.swing.table.DefaultTableCellRenderer;

import es.mityc.appfacturae.facturae.InvoiceClassType;
import es.mityc.appfacturae.hibernate.auxClass.InvoiceStatusType;
import es.mityc.appfacturae.utils.constants.Constants;

public class StateClassCellRenderer extends DefaultTableCellRenderer{

	private java.awt.Color bkgColorSelected = null;
	private java.awt.Color bkgColorNotSelected = null;
	private java.awt.Color fgColorSelected = null;
	private java.awt.Color fgColorNotSelected = null;
	private java.awt.Font font = null;
	private int alignment = -1;
	// type = 0 => Class ; type = 1 => State
	private int type = -1;
	
	public StateClassCellRenderer(java.awt.Color bkgColorSelected, java.awt.Color bkgColorNotSelected, java.awt.Color fgColorSelected, java.awt.Color fgColorNotSelected, java.awt.Font font, int alignment,int type){
		super();
		this.bkgColorSelected = bkgColorSelected;
		this.bkgColorNotSelected = bkgColorNotSelected;
		this.fgColorSelected = fgColorSelected;
		this.fgColorNotSelected = fgColorNotSelected;
		this.font = font;
		this.alignment = alignment;
		this.type = type;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		
		if (type == 0){
			setText(InvoiceClassType.values()[Integer.valueOf(value.toString())].toString());
			if (isSelected){
				setBackground(bkgColorSelected);
				setForeground(fgColorSelected);
			}
			else{
				setForeground(fgColorNotSelected);
				switch (Integer.valueOf(value.toString())){
				case 0: setBackground(new java.awt.Color(200,250,200));
						setToolTipText(Constants.LANG.getString("Original"));
						break;
				case 1: setBackground(new java.awt.Color(250,200,250));
						setToolTipText(Constants.LANG.getString("Corrective"));
						break;
				default: setBackground(bkgColorNotSelected);
				}
			}
		}
		else{
			setText(InvoiceStatusType.values()[Integer.valueOf(value.toString())].toString());
			if (isSelected){
				setBackground(bkgColorSelected);
				setForeground(fgColorSelected);
			}
			else{
				setForeground(fgColorNotSelected);
				switch (Integer.valueOf(value.toString())){
				case 0: setBackground(new java.awt.Color(215,245,250));
						setToolTipText(Constants.LANG.getString("DraftStr"));
						break;
				case 1: setBackground(new java.awt.Color(195,240,250));
						setToolTipText(Constants.LANG.getString("Issued"));
						break;
				case 2: setBackground(new java.awt.Color(175,235,250));
						setToolTipText(Constants.LANG.getString("Sent"));
						break;
				case 3: setBackground(new java.awt.Color(155,230,250));
						setToolTipText(Constants.LANG.getString("Received"));
						break;
				default: setBackground(bkgColorNotSelected);
				}
			}
		}
		
		setHorizontalAlignment(alignment);
		setFont(font);
		
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

