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

package es.mityc.facturaeface.service;

import es.mityc.facturaeface.ConstantsFACe;

public class SSPPWebServiceProxyServiceLocator extends org.apache.axis.client.Service implements SSPPWebServiceProxyService {
	private static final long serialVersionUID = -4607400700525470741L;
    public SSPPWebServiceProxyServiceLocator() {
    }


    public SSPPWebServiceProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SSPPWebServiceProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SSPPWebServiceProxyPort
    private java.lang.String SSPPWebServiceProxyPort_address = ConstantsFACe.XMLNS_SSPP;

    public java.lang.String getSSPPWebServiceProxyPortAddress() {
        return SSPPWebServiceProxyPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SSPPWebServiceProxyPortWSDDServiceName = "SSPPWebServiceProxyPort";

    public java.lang.String getSSPPWebServiceProxyPortWSDDServiceName() {
        return SSPPWebServiceProxyPortWSDDServiceName;
    }

    public void setSSPPWebServiceProxyPortWSDDServiceName(java.lang.String name) {
        SSPPWebServiceProxyPortWSDDServiceName = name;
    }

    public SSPPWebServiceProxyPort getSSPPWebServiceProxyPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SSPPWebServiceProxyPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSSPPWebServiceProxyPort(endpoint);
    }

    public SSPPWebServiceProxyPort getSSPPWebServiceProxyPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            SSPPWebServiceProxyBindingStub _stub = new SSPPWebServiceProxyBindingStub(portAddress, this);
            _stub.setPortName(getSSPPWebServiceProxyPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSSPPWebServiceProxyPortEndpointAddress(java.lang.String address) {
        SSPPWebServiceProxyPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (SSPPWebServiceProxyPort.class.isAssignableFrom(serviceEndpointInterface)) {
                SSPPWebServiceProxyBindingStub _stub = new SSPPWebServiceProxyBindingStub(new java.net.URL(SSPPWebServiceProxyPort_address), this);
                _stub.setPortName(getSSPPWebServiceProxyPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SSPPWebServiceProxyPort".equals(inputPortName)) {
            return getSSPPWebServiceProxyPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @SuppressWarnings("rawtypes")
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPWebServiceProxyService");
    }

    private java.util.HashSet ports = null;

    @SuppressWarnings("rawtypes")
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPWebServiceProxyPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SSPPWebServiceProxyPort".equals(portName)) {
            setSSPPWebServiceProxyPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
