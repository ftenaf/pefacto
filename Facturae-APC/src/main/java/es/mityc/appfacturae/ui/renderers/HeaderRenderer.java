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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.mityc.appfacturae.ui.renderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HeaderRenderer extends DefaultTableCellRenderer {
   // This method is called each time a cell in a column
   // using this renderer needs to be rendered.
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
       setText(value.toString());
       setToolTipText(value.toString());
       setOpaque(true);
       setBackground(new java.awt.Color(230,240,250));
       setForeground(new java.awt.Color(19,91,135));
       setFont(new java.awt.Font("Arial",0,12));
       setVerticalAlignment(CENTER);
       setHorizontalAlignment(CENTER);
       setBorder(new javax.swing.border.LineBorder(new java.awt.Color(182,219,238), 1));
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

