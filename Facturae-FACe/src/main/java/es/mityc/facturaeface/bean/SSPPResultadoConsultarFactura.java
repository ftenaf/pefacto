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

public class SSPPResultadoConsultarFactura  implements java.io.Serializable {
    private java.lang.String numero_registro;

    private SSPPResultadoConsultarFacturaEstado tramitacion;

    private SSPPResultadoConsultarFacturaEstado anulacion;

    public SSPPResultadoConsultarFactura() {
    }

    public SSPPResultadoConsultarFactura(
           java.lang.String numero_registro,
           SSPPResultadoConsultarFacturaEstado tramitacion,
           SSPPResultadoConsultarFacturaEstado anulacion) {
           this.numero_registro = numero_registro;
           this.tramitacion = tramitacion;
           this.anulacion = anulacion;
    }


    /**
     * Gets the numero_registro value for this SSPPResultadoConsultarFactura.
     * 
     * @return numero_registro
     */
    public java.lang.String getNumero_registro() {
        return numero_registro;
    }


    /**
     * Sets the numero_registro value for this SSPPResultadoConsultarFactura.
     * 
     * @param numero_registro
     */
    public void setNumero_registro(java.lang.String numero_registro) {
        this.numero_registro = numero_registro;
    }


    /**
     * Gets the tramitacion value for this SSPPResultadoConsultarFactura.
     * 
     * @return tramitacion
     */
    public SSPPResultadoConsultarFacturaEstado getTramitacion() {
        return tramitacion;
    }


    /**
     * Sets the tramitacion value for this SSPPResultadoConsultarFactura.
     * 
     * @param tramitacion
     */
    public void setTramitacion(SSPPResultadoConsultarFacturaEstado tramitacion) {
        this.tramitacion = tramitacion;
    }


    /**
     * Gets the anulacion value for this SSPPResultadoConsultarFactura.
     * 
     * @return anulacion
     */
    public SSPPResultadoConsultarFacturaEstado getAnulacion() {
        return anulacion;
    }


    /**
     * Sets the anulacion value for this SSPPResultadoConsultarFactura.
     * 
     * @param anulacion
     */
    public void setAnulacion(SSPPResultadoConsultarFacturaEstado anulacion) {
        this.anulacion = anulacion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSPPResultadoConsultarFactura)) return false;
        SSPPResultadoConsultarFactura other = (SSPPResultadoConsultarFactura) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.numero_registro==null && other.getNumero_registro()==null) || 
             (this.numero_registro!=null &&
              this.numero_registro.equals(other.getNumero_registro()))) &&
            ((this.tramitacion==null && other.getTramitacion()==null) || 
             (this.tramitacion!=null &&
              this.tramitacion.equals(other.getTramitacion()))) &&
            ((this.anulacion==null && other.getAnulacion()==null) || 
             (this.anulacion!=null &&
              this.anulacion.equals(other.getAnulacion())));
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
        if (getNumero_registro() != null) {
            _hashCode += getNumero_registro().hashCode();
        }
        if (getTramitacion() != null) {
            _hashCode += getTramitacion().hashCode();
        }
        if (getAnulacion() != null) {
            _hashCode += getAnulacion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSPPResultadoConsultarFactura.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarFactura"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero_registro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numero_registro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tramitacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tramitacion"));
        elemField.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarFacturaEstado"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anulacion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anulacion"));
        elemField.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarFacturaEstado"));
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
