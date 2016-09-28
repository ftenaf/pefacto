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
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.mityc.facturae.utils.constants.Constants;

/**
 * This class extends JLabel and implements TableCellRenderer. It overwrites the table cell renderer
 * but from a label. It allow to define a table cell with both image and text.
 */
public class LabelImageRenderer extends JLabel implements TableCellRenderer{
    
        ImageIcon selected = null;
        ResourceBundle lang = null;
        
        public LabelImageRenderer(ImageIcon s, Locale l){
            selected = s;
            lang = ResourceBundle.getBundle("language/lang", l);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
           setIcon(selected);
           setFont(Constants.FONT_PLAIN);
           setForeground(Constants.FONT_COLOR);
           setOpaque(false);
           if (isSelected){
        	   setBackground(Constants.SELECTION_COLOR);
        	   setOpaque(true);
           }
           try{
        	   setText(lang.getString(value.toString()));
           }
           catch (MissingResourceException mre) {
        	   setText(value.toString());
           }
           return this;
        }
              
}
