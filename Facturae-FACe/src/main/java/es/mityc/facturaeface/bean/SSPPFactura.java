/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Facturae-FAce".
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

package es.mityc.facturaeface.bean;

import es.mityc.facturaeface.ConstantsFACe;

public class SSPPFactura  implements java.io.Serializable {
    private java.lang.String correo;

    private SSPPFicheroFactura fichero_factura;

    private SSPPFicheroAnexo[] ficheros_anexos;

    public SSPPFactura() {
    }

    public SSPPFactura(
           java.lang.String correo,
           SSPPFicheroFactura fichero_factura,
           SSPPFicheroAnexo[] ficheros_anexos) {
           this.correo = correo;
           this.fichero_factura = fichero_factura;
           this.ficheros_anexos = ficheros_anexos;
    }


    /**
     * Gets the correo value for this SSPPFactura.
     * 
     * @return correo
     */
    public java.lang.String getCorreo() {
        return correo;
    }


    /**
     * Sets the correo value for this SSPPFactura.
     * 
     * @param correo
     */
    public void setCorreo(java.lang.String correo) {
        this.correo = correo;
    }


    /**
     * Gets the fichero_factura value for this SSPPFactura.
     * 
     * @return fichero_factura
     */
    public SSPPFicheroFactura getFichero_factura() {
        return fichero_factura;
    }


    /**
     * Sets the fichero_factura value for this SSPPFactura.
     * 
     * @param fichero_factura
     */
    public void setFichero_factura(SSPPFicheroFactura fichero_factura) {
        this.fichero_factura = fichero_factura;
    }


    /**
     * Gets the ficheros_anexos value for this SSPPFactura.
     * 
     * @return ficheros_anexos
     */
    public SSPPFicheroAnexo[] getFicheros_anexos() {
        return ficheros_anexos;
    }


    /**
     * Sets the ficheros_anexos value for this SSPPFactura.
     * 
     * @param ficheros_anexos
     */
    public void setFicheros_anexos(SSPPFicheroAnexo[] ficheros_anexos) {
        this.ficheros_anexos = ficheros_anexos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSPPFactura)) return false;
        SSPPFactura other = (SSPPFactura) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.correo==null && other.getCorreo()==null) || 
             (this.correo!=null &&
              this.correo.equals(other.getCorreo()))) &&
            ((this.fichero_factura==null && other.getFichero_factura()==null) || 
             (this.fichero_factura!=null &&
              this.fichero_factura.equals(other.getFichero_factura()))) &&
            ((this.ficheros_anexos==null && other.getFicheros_anexos()==null) || 
             (this.ficheros_anexos!=null &&
              java.util.Arrays.equals(this.ficheros_anexos, other.getFicheros_anexos())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCorreo() != null) {
            _hashCode += getCorreo().hashCode();
        }
        if (getFichero_factura() != null) {
            _hashCode += getFichero_factura().hashCode();
        }
        if (getFicheros_anexos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFicheros_anexos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFicheros_anexos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSPPFactura.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPFactura"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "correo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fichero_factura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fichero_factura"));
        elemField.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPFicheroFactura"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ficheros_anexos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ficheros_anexos"));
        elemField.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPFicheroAnexo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
