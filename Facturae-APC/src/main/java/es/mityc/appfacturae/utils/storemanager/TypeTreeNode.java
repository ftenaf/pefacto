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

import javax.swing.JPopupMenu;

/**
 * Modelo visual de la estructura árbol que muestra los datos de firma
 */
public class TypeTreeNode {	
	
	private EnumTipoNodo tipo = null;
	private String nombre = null;
	private String toolTip = null;
	private JPopupMenu menu = null;
	private Object datosAsociados = null;

	public TypeTreeNode() {}
	
	public TypeTreeNode (String nombre, EnumTipoNodo tipo, String toolTip, 
			JPopupMenu menu, Object datosAsociados) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.toolTip = toolTip;
		this.menu = menu;
		this.datosAsociados = datosAsociados;
	}	
	
	public EnumTipoNodo getTipo() {
		return tipo;
	}
	public void setTipo(EnumTipoNodo tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}		
	public JPopupMenu getMenu() {
		return menu;
	}
	public void setMenu(JPopupMenu menu) {
		this.menu = menu;
	}
	public Object getDatosAsociados() {
		return datosAsociados;
	}
	public void setDatosAsociados(Object datosAsociados) {
		this.datosAsociados = datosAsociados;
	}

	@Override
	public String toString() {
		return nombre;
	}
}