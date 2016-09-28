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

public class SSPPOrganoGestorUnidadTramitadora  implements java.io.Serializable {
    private SSPPUnidadDir organo_gestor;

    private SSPPUnidadDir unidad_tramitadora;

    private SSPPUnidadDir oficina_contable;

    public SSPPOrganoGestorUnidadTramitadora() {
    }

    public SSPPOrganoGestorUnidadTramitadora(
           SSPPUnidadDir organo_gestor,
           SSPPUnidadDir unidad_tramitadora,
           SSPPUnidadDir oficina_contable) {
           this.organo_gestor = organo_gestor;
           this.unidad_tramitadora = unidad_tramitadora;
           this.oficina_contable = oficina_contable;
    }


    /**
     * Gets the organo_gestor value for this SSPPOrganoGestorUnidadTramitadora.
     * 
     * @return organo_gestor
     */
    public SSPPUnidadDir getOrgano_gestor() {
        return organo_gestor;
    }


    /**
     * Sets the organo_gestor value for this SSPPOrganoGestorUnidadTramitadora.
     * 
     * @param organo_gestor
     */
    public void setOrgano_gestor(SSPPUnidadDir organo_gestor) {
        this.organo_gestor = organo_gestor;
    }


    /**
     * Gets the unidad_tramitadora value for this SSPPOrganoGestorUnidadTramitadora.
     * 
     * @return unidad_tramitadora
     */
    public SSPPUnidadDir getUnidad_tramitadora() {
        return unidad_tramitadora;
    }


    /**
     * Sets the unidad_tramitadora value for this SSPPOrganoGestorUnidadTramitadora.
     * 
     * @param unidad_tramitadora
     */
    public void setUnidad_tramitadora(SSPPUnidadDir unidad_tramitadora) {
        this.unidad_tramitadora = unidad_tramitadora;
    }


    /**
     * Gets the oficina_contable value for this SSPPOrganoGestorUnidadTramitadora.
     * 
     * @return oficina_contable
     */
    public SSPPUnidadDir getOficina_contable() {
        return oficina_contable;
    }


    /**
     * Sets the oficina_contable value for this SSPPOrganoGestorUnidadTramitadora.
     * 
     * @param oficina_contable
     */
    public void setOficina_contable(SSPPUnidadDir oficina_contable) {
        this.oficina_contable = oficina_contable;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSPPOrganoGestorUnidadTramitadora)) return false;
        SSPPOrganoGestorUnidadTramitadora other = (SSPPOrganoGestorUnidadTramitadora) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.organo_gestor==null && other.getOrgano_gestor()==null) || 
             (this.organo_gestor!=null &&
              this.organo_gestor.equals(other.getOrgano_gestor()))) &&
            ((this.unidad_tramitadora==null && other.getUnidad_tramitadora()==null) || 
             (this.unidad_tramitadora!=null &&
              this.unidad_tramitadora.equals(other.getUnidad_tramitadora()))) &&
            ((this.oficina_contable==null && other.getOficina_contable()==null) || 
             (this.oficina_contable!=null &&
              this.oficina_contable.equals(other.getOficina_contable())));
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
        if (getOrgano_gestor() != null) {
            _hashCode += getOrgano_gestor().hashCode();
        }
        if (getUnidad_tramitadora() != null) {
            _hashCode += getUnidad_tramitadora().hashCode();
        }
        if (getOficina_contable() != null) {
            _hashCode += getOficina_contable().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSPPOrganoGestorUnidadTramitadora.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPOrganoGestorUnidadTramitadora"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organo_gestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organo_gestor"));
        elemField.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPUnidadDir"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidad_tramitadora");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unidad_tramitadora"));
        elemField.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPUnidadDir"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oficina_contable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oficina_contable"));
        elemField.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPUnidadDir"));
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
