/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-API".
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
package es.mityc.facturae.ui;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import es.mityc.facturae.utils.constants.Constants;

/**
 * This class extends DefaultTableCellRenderer. It overwrites the default table cell renderer
 * to set the facturae look and feel on the table cells.
 */
public class CellRenderer extends DefaultTableCellRenderer{
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
        if (isSelected){
            setBackground(Constants.SELECTION_COLOR);
        }
        else{
            setBackground(java.awt.Color.WHITE);
        }
        setForeground(Constants.FONT_COLOR);
        if (value != null)
            setText(value.toString());
        else
            setText("");
        return this;
    }
    @Override
    public void setFont(Font font) {
    	super.setFont(Constants.FONT_PLAIN);
    }
    
}