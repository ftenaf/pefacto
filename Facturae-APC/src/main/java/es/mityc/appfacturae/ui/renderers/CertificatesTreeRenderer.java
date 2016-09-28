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
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import es.mityc.appfacturae.utils.constants.Constants;

public class CertificatesTreeRenderer extends JLabel implements TreeCellRenderer {

	public CertificatesTreeRenderer(){
		super();
	}

	public Component getTreeCellRendererComponent( JTree tree,Object value, boolean bSelected, boolean bExpanded, boolean bLeaf, int iRow, boolean bHasFocus )
    {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        String labelText = (String)node.getUserObject();
		this.setOpaque(false);
		this.setFont(Constants.FONT_PLAIN);
		this.setIcon(new ImageIcon(getClass().getResource("/images/certificate.jpg")));
        this.setForeground(Constants.FONT_COLOR);
	    this.setBackground(Constants.BKG_MAIN_COLOR);
	    this.setText(labelText);
	    return this;
    }
}
