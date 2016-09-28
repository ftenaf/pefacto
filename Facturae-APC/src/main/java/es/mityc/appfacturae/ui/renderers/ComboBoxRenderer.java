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

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import es.mityc.appfacturae.utils.constants.Constants;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer {
	
	private int lestart = 0;
	private boolean standard = true;
	
	public ComboBoxRenderer(boolean standard) {
		setOpaque(true);
		this.standard = standard;
	}

	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus){
		if (value == null) value = "";
		if ("".equals(value)) 
			setText(" ");
		else 
			setText(value.toString());
			setToolTipText(value.toString());
		setForeground(Constants.FONT_COLOR);
		if (value.toString().equals(Constants.LANG.getString("ALL")) || value.toString().equals(Constants.LANG.getString("INDIVIDUAL"))){
			setBackground(isSelected ? Constants.SELECTION_COLOR : Color.white);
			setFont(Constants.FONT_ITALIC);
		}
		else if (value.toString().equals(Constants.LANG.getString("LEGALENTITY"))){
			lestart = index;
			setBackground(isSelected ? Constants.SELECTION_COLOR : Constants.BKG_MAIN_COLOR);
			setFont(Constants.FONT_ITALIC);
	    }
		else{
			setBackground(isSelected ? Constants.SELECTION_COLOR : Color.white);
			setFont(Constants.FONT_PLAIN);
			if (!standard){
				if (index > lestart)
					setForeground(Constants.LEGENTITY_COLOR);
				else
					setForeground(Constants.INDIVIDUAL_COLOR);
			}
		}
		
		return this;
	}
	
	public int getLeStart(){
		return lestart;
	}

}


