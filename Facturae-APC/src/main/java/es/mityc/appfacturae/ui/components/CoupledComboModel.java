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

import java.util.EventListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;


public class CoupledComboModel extends DefaultComboBoxModel {
	
	private DefaultComboBoxModel coupled;

	public CoupledComboModel(DefaultComboBoxModel coupled) {
		super();
		this.coupled = coupled;
		setSelectedItem(null);
	}
	
	@Override
	public void addElement(Object obj) {
		coupled.addElement(obj);
	}
	
	@Override
	public void removeAllElements() {
		coupled.removeAllElements();
	}
	
	@Override
	public Object getElementAt(int i) {
		return coupled.getElementAt(i);
	}
	
	@Override
	public int getIndexOf(Object obj) {
		return coupled.getIndexOf(obj);
	}
	
	@Override
	public int getSize() {
		return coupled.getSize();
	}
	
	@Override
	public ListDataListener[] getListDataListeners() {
		return coupled.getListDataListeners();
	}
	
	/*@Override
	public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
		return coupled.getListeners(listenerType);
	}*/
	@Override
	public EventListener[] getListeners(Class listenerType) {
		// TODO Auto-generated method stub
		return coupled.getListeners(listenerType);
	}

	
	@Override
	public void addListDataListener(ListDataListener listdatalistener) {
		coupled.addListDataListener(listdatalistener);
	}
	
	@Override
	public void removeListDataListener(ListDataListener listdatalistener) {
		coupled.removeListDataListener(listdatalistener);
	}
	
	@Override
	public void removeElement(Object obj) {
		coupled.removeElement(obj);
		setSelectedItem(null);
	}
	
	@Override
	public void removeElementAt(int i) {
		coupled.removeElementAt(i);
		setSelectedItem(null);
	}
	
}
