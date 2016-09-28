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

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import es.mityc.appfacturae.utils.constants.Constants;

public class ReceiveImageRenderer extends DefaultTableCellRenderer{
    
        ImageIcon selected = null;
        
        public ReceiveImageRenderer(){  }
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
	        Component renderer = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,rowIndex,vColIndex);
	        
	        if (value != null){
		        if(value.toString().equals(Constants.LANG.getString("OK"))){
		        	
		        	renderer.setForeground(Constants.OK_MSG_COLOR);
		        	renderer.setFont(Constants.FONT_BOLD);
		        	setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		        	selected = new javax.swing.ImageIcon(getClass().getResource("/images/correct_importation_message.gif"));
		        	setIcon(selected);
		        	
		        }else if(value.toString().equals(Constants.LANG.getString("NOOK"))) {
		        	
		        	renderer.setForeground(Constants.ERROR_MSG_COLOR);
		        	renderer.setFont(Constants.FONT_BOLD);
		        	setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		        	selected = new javax.swing.ImageIcon(getClass().getResource("/images/error_importation_message.gif"));
		        	setIcon(selected);
		        	
		        }else{
		        	
		        	renderer.setForeground(Constants.FONT_COLOR);
		        	selected = null;
		        	setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		        	setIcon(selected);
		        	
		        }
		        
	            setText(value.toString());
	            
	        }
	        else
	        	setText("");
	            
            return this;
        }
              
}
