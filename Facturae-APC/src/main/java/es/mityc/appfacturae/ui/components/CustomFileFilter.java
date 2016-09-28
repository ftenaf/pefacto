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


import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.filechooser.FileFilter;

public class CustomFileFilter extends FileFilter{

    private Hashtable<String, CustomFileFilter> filters = null;
    private String description = null;
    private String fullDescription = null;
    private boolean useExtensionsInDescription = true;

    public CustomFileFilter() {
    	this.filters = new Hashtable<String, CustomFileFilter>();
    }

    public CustomFileFilter(String extension) {
    	this(extension,null);
    }

    public CustomFileFilter(String extension, String description) {
		this();
		if(extension!=null) addExtension(extension);
	 	if(description!=null) setDescription(description);
    }

    public CustomFileFilter(String[] filters) {
    	this(filters, null);
    }

    public CustomFileFilter(String[] filters, String description) {
		this();
		for (int i = 0; i < filters.length; i++) {
		    addExtension(filters[i]);
		}
	 	if(description!=null) setDescription(description);
    }

    public boolean accept(File f) {
		if(f != null) {
		    if(f.isDirectory()) {
		    	return true;
		    }
		    String extension = getExtension(f);
		    if(extension != null && filters.get(getExtension(f)) != null) {
		    	return true;
		    }
		}
		return false;
    }

    public String getExtension(File f) {
	if(f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
	    	return filename.substring(i+1).toLowerCase();
	    };
	}
	return null;
    }

    public void addExtension(String extension) {
		if(filters == null) {
		    filters = new Hashtable<String, CustomFileFilter>(5);
		}
		filters.put(extension.toLowerCase(), this);
		fullDescription = null;
    }

    public String getDescription() {
	if(fullDescription == null) {
	    if(description == null || isExtensionListInDescription()) {
 		fullDescription = description==null ? "(" : description + " (";
		Enumeration<String> extensions = filters.keys();
		if(extensions != null) {
		    fullDescription += "." + extensions.nextElement();
		    while (extensions.hasMoreElements()) {
			fullDescription += ", ." + extensions.nextElement();
		    }
		}
		fullDescription += ")";
	    } else {
		fullDescription = description;
	    }
	}
	return fullDescription;
    }

    public void setDescription(String description) {
		this.description = description;
		fullDescription = null;
    }

    public void setExtensionListInDescription(boolean b) {
		useExtensionsInDescription = b;
		fullDescription = null;
    }

    public boolean isExtensionListInDescription() {
    	return useExtensionsInDescription;
    }
}
