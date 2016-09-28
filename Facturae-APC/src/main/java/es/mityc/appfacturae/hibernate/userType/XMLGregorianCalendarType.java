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
package es.mityc.appfacturae.hibernate.userType;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Generic class to map a JDK 1.5 XMLGregorianCalendar to a java.sql.Timestamp column in the DB.
 */
public class XMLGregorianCalendarType implements UserType { 
    
    private static final int[] SQL_TYPES = {Types.TIMESTAMP};
    
    /**
     * Simple mapping to a TIMESTAMP.
     */
    public int[] sqlTypes() { 
        return SQL_TYPES; 
        
    } 
 
    public Class<?> returnedClass() { 
        return XMLGregorianCalendar.class; 
    } 

    /**
     * From the java.sql.Date in the DB, get the XMLGregorianCalendar.
     */
    public XMLGregorianCalendar nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException { 
        
    	final Timestamp val = resultSet.getTimestamp(names[0]);
        
        // A GregorianCalendar object is created with the Date's value 
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(val);
        
        // A XMLGregorianCalendar object is created with the GregorianCalendar's value through a DatatypeFactory object.
        try{
	        DatatypeFactory dtf = DatatypeFactory.newInstance();
	        XMLGregorianCalendar result = dtf.newXMLGregorianCalendar(gc);
	        return result;
        }catch (DatatypeConfigurationException dce){
        	return null;
        }
    } 
 
    /**
     * From the XMLGregorianCalendar, set the java.sql.Timestamp in the DB.
     */
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException { 
        if (value == null) { 
            preparedStatement.setNull(index, Types.TIMESTAMP); 
        } else { 
        	// XMLGregorianCalendar --> GregorianCalendar --> java.util.Date --> java.sql.Date
        	preparedStatement.setTimestamp(index, new Timestamp((((XMLGregorianCalendar)value).toGregorianCalendar().getTime()).getTime())); 
        } 
    } 
    
    public Object deepCopy(Object value) throws HibernateException{ 
        return value; 
    } 
 
    public boolean isMutable() { 
        return false; 
    } 
 
    public Object assemble(Serializable cached, Object owner)
        throws HibernateException {
         return cached;
    } 

    public Serializable disassemble(Object value) throws HibernateException { 
        return (Serializable)value; 
    } 
 
    public Object replace(Object original, Object target, Object owner) throws HibernateException { 
        return original; 
    } 
    public int hashCode(Object x) throws HibernateException { 
        return x.hashCode(); 
    } 
    public boolean equals(Object x, Object y) throws HibernateException { 
        if (x == y) 
            return true; 
        if (null == x || null == y) 
            return false; 
        return x.equals(y); 
    } 
} 
