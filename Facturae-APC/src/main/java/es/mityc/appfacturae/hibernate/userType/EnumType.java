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
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Generic class to map a JDK 1.5 Enum to a SMALL INT column in the DB.
 * Beware that the enum.ordinal() relates to the ORDERING of the enum values, so, if
 * your change it later on, all your DB values will return an incorrect value!
 * There is no problem due to the facturae format versions are closed and they can't
 * be modified.
 */
public class EnumType<E extends Enum<E>> implements UserType { 
    private Class<E> classE = null;
    private E[] theEnumValues;
    
    protected EnumType(Class<E> c, E[] e) { 
        this.classE = c; 
        this.theEnumValues = e;
    } 
 
    private static final int[] SQL_TYPES = {Types.SMALLINT};
    
    /**
     * Simple mapping to a SMALLINT.
     */
    public int[] sqlTypes() { 
        return SQL_TYPES; 
    } 
 
    public Class<?> returnedClass() { 
        return classE; 
    } 

    /**
     * From the SMALLINT in the DB, get the corresponding Enum.
     */
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException { 
        final int val = resultSet.getShort(names[0]);
        return theEnumValues[val]; 
    } 
 
    /**
     * Set the SMALLINT in the DB based on enum.ordinal() value. BEWARE this
     * could change, though in this case it's known it won't happen, because the 
     * facturae versions are closed.
     */
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException { 
        if (value == null) { 
            preparedStatement.setNull(index, Types.SMALLINT); 
        } else { 
            preparedStatement.setInt(index, ((Enum<?>)value).ordinal()); 
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
