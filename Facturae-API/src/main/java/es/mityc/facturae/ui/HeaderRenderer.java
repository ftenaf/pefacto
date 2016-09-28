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
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * This class extends DefaultTableCellRenderer. It overwrites the default table cell renderer
 * to set the facturae look and feel on the table headers.
 */
public class HeaderRenderer extends DefaultTableCellRenderer {
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
       setText(value.toString());
       setOpaque(true);
       setBackground(new java.awt.Color(230,240,250));
       setForeground(new java.awt.Color(19,91,135));
       setFont(new java.awt.Font("Arial",0,12));
       setVerticalAlignment(CENTER);
       setHorizontalAlignment(CENTER);
       setBorder(new javax.swing.border.LineBorder(new java.awt.Color(182,219,238), 1));
       return this;
   }
}

