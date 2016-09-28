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

public class SSPPResultadoEnviarFactura  implements java.io.Serializable {
    private java.lang.String codigo_registro;

    private java.lang.String organo_gestor;

    private java.lang.String unidad_tramitadora;

    private java.lang.String oficina_contable;

    private java.lang.String identificador_emisor;

    private java.lang.String numero_factura;

    private java.lang.String serie_factura;

    private java.lang.String fecha_recepcion;

    private java.lang.String pdf_justificante;

    public SSPPResultadoEnviarFactura() {
    }

    public SSPPResultadoEnviarFactura(
           java.lang.String codigo_registro,
           java.lang.String organo_gestor,
           java.lang.String unidad_tramitadora,
           java.lang.String oficina_contable,
           java.lang.String identificador_emisor,
           java.lang.String numero_factura,
           java.lang.String serie_factura,
           java.lang.String fecha_recepcion,
           java.lang.String pdf_justificante) {
           this.codigo_registro = codigo_registro;
           this.organo_gestor = organo_gestor;
           this.unidad_tramitadora = unidad_tramitadora;
           this.oficina_contable = oficina_contable;
           this.identificador_emisor = identificador_emisor;
           this.numero_factura = numero_factura;
           this.serie_factura = serie_factura;
           this.fecha_recepcion = fecha_recepcion;
           this.pdf_justificante = pdf_justificante;
    }


    /**
     * Gets the codigo_registro value for this SSPPResultadoEnviarFactura.
     * 
     * @return codigo_registro
     */
    public java.lang.String getCodigo_registro() {
        return codigo_registro;
    }


    /**
     * Sets the codigo_registro value for this SSPPResultadoEnviarFactura.
     * 
     * @param codigo_registro
     */
    public void setCodigo_registro(java.lang.String codigo_registro) {
        this.codigo_registro = codigo_registro;
    }


    /**
     * Gets the organo_gestor value for this SSPPResultadoEnviarFactura.
     * 
     * @return organo_gestor
     */
    public java.lang.String getOrgano_gestor() {
        return organo_gestor;
    }


    /**
     * Sets the organo_gestor value for this SSPPResultadoEnviarFactura.
     * 
     * @param organo_gestor
     */
    public void setOrgano_gestor(java.lang.String organo_gestor) {
        this.organo_gestor = organo_gestor;
    }


    /**
     * Gets the unidad_tramitadora value for this SSPPResultadoEnviarFactura.
     * 
     * @return unidad_tramitadora
     */
    public java.lang.String getUnidad_tramitadora() {
        return unidad_tramitadora;
    }


    /**
     * Sets the unidad_tramitadora value for this SSPPResultadoEnviarFactura.
     * 
     * @param unidad_tramitadora
     */
    public void setUnidad_tramitadora(java.lang.String unidad_tramitadora) {
        this.unidad_tramitadora = unidad_tramitadora;
    }


    /**
     * Gets the oficina_contable value for this SSPPResultadoEnviarFactura.
     * 
     * @return oficina_contable
     */
    public java.lang.String getOficina_contable() {
        return oficina_contable;
    }


    /**
     * Sets the oficina_contable value for this SSPPResultadoEnviarFactura.
     * 
     * @param oficina_contable
     */
    public void setOficina_contable(java.lang.String oficina_contable) {
        this.oficina_contable = oficina_contable;
    }


    /**
     * Gets the identificador_emisor value for this SSPPResultadoEnviarFactura.
     * 
     * @return identificador_emisor
     */
    public java.lang.String getIdentificador_emisor() {
        return identificador_emisor;
    }


    /**
     * Sets the identificador_emisor value for this SSPPResultadoEnviarFactura.
     * 
     * @param identificador_emisor
     */
    public void setIdentificador_emisor(java.lang.String identificador_emisor) {
        this.identificador_emisor = identificador_emisor;
    }


    /**
     * Gets the numero_factura value for this SSPPResultadoEnviarFactura.
     * 
     * @return numero_factura
     */
    public java.lang.String getNumero_factura() {
        return numero_factura;
    }


    /**
     * Sets the numero_factura value for this SSPPResultadoEnviarFactura.
     * 
     * @param numero_factura
     */
    public void setNumero_factura(java.lang.String numero_factura) {
        this.numero_factura = numero_factura;
    }


    /**
     * Gets the serie_factura value for this SSPPResultadoEnviarFactura.
     * 
     * @return serie_factura
     */
    public java.lang.String getSerie_factura() {
        return serie_factura;
    }


    /**
     * Sets the serie_factura value for this SSPPResultadoEnviarFactura.
     * 
     * @param serie_factura
     */
    public void setSerie_factura(java.lang.String serie_factura) {
        this.serie_factura = serie_factura;
    }


    /**
     * Gets the fecha_recepcion value for this SSPPResultadoEnviarFactura.
     * 
     * @return fecha_recepcion
     */
    public java.lang.String getFecha_recepcion() {
        return fecha_recepcion;
    }


    /**
     * Sets the fecha_recepcion value for this SSPPResultadoEnviarFactura.
     * 
     * @param fecha_recepcion
     */
    public void setFecha_recepcion(java.lang.String fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }


    /**
     * Gets the pdf_justificante value for this SSPPResultadoEnviarFactura.
     * 
     * @return pdf_justificante
     */
    public java.lang.String getPdf_justificante() {
        return pdf_justificante;
    }


    /**
     * Sets the pdf_justificante value for this SSPPResultadoEnviarFactura.
     * 
     * @param pdf_justificante
     */
    public void setPdf_justificante(java.lang.String pdf_justificante) {
        this.pdf_justificante = pdf_justificante;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSPPResultadoEnviarFactura)) return false;
        SSPPResultadoEnviarFactura other = (SSPPResultadoEnviarFactura) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codigo_registro==null && other.getCodigo_registro()==null) || 
             (this.codigo_registro!=null &&
              this.codigo_registro.equals(other.getCodigo_registro()))) &&
            ((this.organo_gestor==null && other.getOrgano_gestor()==null) || 
             (this.organo_gestor!=null &&
              this.organo_gestor.equals(other.getOrgano_gestor()))) &&
            ((this.unidad_tramitadora==null && other.getUnidad_tramitadora()==null) || 
             (this.unidad_tramitadora!=null &&
              this.unidad_tramitadora.equals(other.getUnidad_tramitadora()))) &&
            ((this.oficina_contable==null && other.getOficina_contable()==null) || 
             (this.oficina_contable!=null &&
              this.oficina_contable.equals(other.getOficina_contable()))) &&
            ((this.identificador_emisor==null && other.getIdentificador_emisor()==null) || 
             (this.identificador_emisor!=null &&
              this.identificador_emisor.equals(other.getIdentificador_emisor()))) &&
            ((this.numero_factura==null && other.getNumero_factura()==null) || 
             (this.numero_factura!=null &&
              this.numero_factura.equals(other.getNumero_factura()))) &&
            ((this.serie_factura==null && other.getSerie_factura()==null) || 
             (this.serie_factura!=null &&
              this.serie_factura.equals(other.getSerie_factura()))) &&
            ((this.fecha_recepcion==null && other.getFecha_recepcion()==null) || 
             (this.fecha_recepcion!=null &&
              this.fecha_recepcion.equals(other.getFecha_recepcion()))) &&
            ((this.pdf_justificante==null && other.getPdf_justificante()==null) || 
             (this.pdf_justificante!=null &&
              this.pdf_justificante.equals(other.getPdf_justificante())));
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
        if (getCodigo_registro() != null) {
            _hashCode += getCodigo_registro().hashCode();
        }
        if (getOrgano_gestor() != null) {
            _hashCode += getOrgano_gestor().hashCode();
        }
        if (getUnidad_tramitadora() != null) {
            _hashCode += getUnidad_tramitadora().hashCode();
        }
        if (getOficina_contable() != null) {
            _hashCode += getOficina_contable().hashCode();
        }
        if (getIdentificador_emisor() != null) {
            _hashCode += getIdentificador_emisor().hashCode();
        }
        if (getNumero_factura() != null) {
            _hashCode += getNumero_factura().hashCode();
        }
        if (getSerie_factura() != null) {
            _hashCode += getSerie_factura().hashCode();
        }
        if (getFecha_recepcion() != null) {
            _hashCode += getFecha_recepcion().hashCode();
        }
        if (getPdf_justificante() != null) {
            _hashCode += getPdf_justificante().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSPPResultadoEnviarFactura.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoEnviarFactura"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo_registro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo_registro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organo_gestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organo_gestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unidad_tramitadora");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unidad_tramitadora"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oficina_contable");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oficina_contable"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador_emisor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificador_emisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero_factura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numero_factura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serie_factura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serie_factura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fecha_recepcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fecha_recepcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pdf_justificante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pdf_justificante"));
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
