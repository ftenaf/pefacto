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
package es.mityc.appfacturae.ui.components;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTabbedPane;


public class TransparentTabbedPane extends JTabbedPane {

  public TransparentTabbedPane() {
    super();
  }

  @Override
  public void paintComponent(Graphics g) {
      super.paintComponent(this.transp(g));
  }
  
  public Graphics transp(Graphics g){
      Graphics2D g2 = (Graphics2D) g;
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f));
      g2.drawImage(null, 0, 0, null);
      return g2;
  }
}