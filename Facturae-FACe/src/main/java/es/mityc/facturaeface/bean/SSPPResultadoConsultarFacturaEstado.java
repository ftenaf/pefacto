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

public class SSPPResultadoConsultarFacturaEstado  implements java.io.Serializable {
    private java.lang.String codigo_estado;

    private java.lang.String descripcion_estado;

    private java.lang.String motivo_estado;

    public SSPPResultadoConsultarFacturaEstado() {
    }

    public SSPPResultadoConsultarFacturaEstado(
           java.lang.String codigo_estado,
           java.lang.String descripcion_estado,
           java.lang.String motivo_estado) {
           this.codigo_estado = codigo_estado;
           this.descripcion_estado = descripcion_estado;
           this.motivo_estado = motivo_estado;
    }


    /**
     * Gets the codigo_estado value for this SSPPResultadoConsultarFacturaEstado.
     * 
     * @return codigo_estado
     */
    public java.lang.String getCodigo_estado() {
        return codigo_estado;
    }


    /**
     * Sets the codigo_estado value for this SSPPResultadoConsultarFacturaEstado.
     * 
     * @param codigo_estado
     */
    public void setCodigo_estado(java.lang.String codigo_estado) {
        this.codigo_estado = codigo_estado;
    }


    /**
     * Gets the descripcion_estado value for this SSPPResultadoConsultarFacturaEstado.
     * 
     * @return descripcion_estado
     */
    public java.lang.String getDescripcion_estado() {
        return descripcion_estado;
    }


    /**
     * Sets the descripcion_estado value for this SSPPResultadoConsultarFacturaEstado.
     * 
     * @param descripcion_estado
     */
    public void setDescripcion_estado(java.lang.String descripcion_estado) {
        this.descripcion_estado = descripcion_estado;
    }


    /**
     * Gets the motivo_estado value for this SSPPResultadoConsultarFacturaEstado.
     * 
     * @return motivo_estado
     */
    public java.lang.String getMotivo_estado() {
        return motivo_estado;
    }


    /**
     * Sets the motivo_estado value for this SSPPResultadoConsultarFacturaEstado.
     * 
     * @param motivo_estado
     */
    public void setMotivo_estado(java.lang.String motivo_estado) {
        this.motivo_estado = motivo_estado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSPPResultadoConsultarFacturaEstado)) return false;
        SSPPResultadoConsultarFacturaEstado other = (SSPPResultadoConsultarFacturaEstado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigo_estado==null && other.getCodigo_estado()==null) || 
             (this.codigo_estado!=null &&
              this.codigo_estado.equals(other.getCodigo_estado()))) &&
            ((this.descripcion_estado==null && other.getDescripcion_estado()==null) || 
             (this.descripcion_estado!=null &&
              this.descripcion_estado.equals(other.getDescripcion_estado()))) &&
            ((this.motivo_estado==null && other.getMotivo_estado()==null) || 
             (this.motivo_estado!=null &&
              this.motivo_estado.equals(other.getMotivo_estado())));
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
        if (getCodigo_estado() != null) {
            _hashCode += getCodigo_estado().hashCode();
        }
        if (getDescripcion_estado() != null) {
            _hashCode += getDescripcion_estado().hashCode();
        }
        if (getMotivo_estado() != null) {
            _hashCode += getMotivo_estado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSPPResultadoConsultarFacturaEstado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarFacturaEstado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo_estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo_estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion_estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcion_estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("motivo_estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "motivo_estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
