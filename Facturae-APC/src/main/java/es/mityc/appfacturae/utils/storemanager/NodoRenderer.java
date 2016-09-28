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
package es.mityc.appfacturae.utils.storemanager;

import java.awt.Color;
import java.awt.Component;
import java.awt.MediaTracker;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Modelo visual de la estructura árbol que muestra los datos del certificado.</p>
 */
public class NodoRenderer extends DefaultTreeCellRenderer {
	
	private static Log log = LogFactory.getLog(NodoRenderer.class);
	
	private static final String STR_ICON_CERT =	"/images/CertSmall.png";
	private static final String STR_ICON_CERT_INFO = "/images/CertInfo.png";
	
	private static ImageIcon iconos[];
	static {
		loadIcons();
	}

	public NodoRenderer() {
		super();
	}

	@Override
	public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(
				tree, value, sel,
				expanded, leaf, row,
				hasFocus);
		TypeTreeNode tipoNodo = getTipoTreePath(value);
		if (tipoNodo != null) {
			ImageIcon icono = resuelveIcono(tipoNodo.getTipo());
			if (icono == null)
				log.debug("No se encuentra el icono asociado al tipo de firma: " + tipoNodo.getTipo());
			else
				setIcon(icono);
			setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0), 2)); // Margen transparente de dos pixels
			setToolTipText(tipoNodo.getToolTip());
		} else
			setToolTipText(null);

		return this;
	}

	protected TypeTreeNode getTipoTreePath (Object value) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		if (node.getUserObject() instanceof TypeTreeNode)
			return (TypeTreeNode)node.getUserObject();
		else if (node.getParent() != null) {
			DefaultMutableTreeNode nodeHijo = (DefaultMutableTreeNode)node.getParent();
			if (nodeHijo.getUserObject() instanceof TypeTreeNode)
				return (TypeTreeNode)nodeHijo.getUserObject();
		}
        return null;
    }
	
	private ImageIcon resuelveIcono(EnumTipoNodo tipo) {
		
		ImageIcon icono = null;
		
		switch (tipo) {
			case TIPO_CERT: 
				icono = iconos[0];
				break;
			
			case TIPO_CERT_INFO:
				icono = iconos[1];
				break;

			default:
				log.debug("No se reconoce el tipo de nodo " + tipo.name());
				icono = iconos[0];
		}
		
		return icono;
	}
	
	private static void loadIcons() {
		log.debug("Cargando iconos");
		iconos = new ImageIcon[] {
			getIcon(STR_ICON_CERT),			// 0
			getIcon(STR_ICON_CERT_INFO),
		};
		
		for (int i = 0; i < iconos.length; i++) {
			int tries = 0;
			while ((iconos[i].getImageLoadStatus() == MediaTracker.LOADING) && (tries < 10)) {
				try {
					Thread.sleep(50);
					tries++;
				} catch (InterruptedException e) { /* No hace nada */ }
				if (tries >= 10)
					log.debug("Se superó el tiempo de carga del icono número: " + (i+1));
			}
		}
		log.debug("Iconos cargados");
	}
	
	private static ImageIcon getIcon(String name) {
		Class<?> classLoader = NodoRenderer.class;
		URL url = classLoader.getResource(name);
		if (url != null)
			return new ImageIcon(url);
		else {
			log.debug("No se pudo recuperar el recurso llamado " + name);
			return null;
		}
	}
}