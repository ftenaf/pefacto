/**
 * Copyright 2015 Ministerio de Industria, Energía y Turismo
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



import java.net.Authenticator;
import java.util.Properties;

import org.apache.axis.AxisProperties;
import org.apache.axis.Handler;

import es.mityc.facturaeface.ConstantsFACe;
import es.mityc.facturaeface.FACeConfig;
import es.mityc.facturaeface.ProxyAuthenticator;
import es.mityc.facturaeface.bean.SSPPEstado;
import es.mityc.facturaeface.bean.SSPPEstados;
import es.mityc.facturaeface.bean.SSPPFactura;
import es.mityc.facturaeface.bean.SSPPFicheroAnexo;
import es.mityc.facturaeface.bean.SSPPFicheroFactura;
import es.mityc.facturaeface.bean.SSPPOrganoGestorUnidadTramitadora;
import es.mityc.facturaeface.bean.SSPPResultadoAnularFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFactura;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarFacturaEstado;
import es.mityc.facturaeface.bean.SSPPResultadoConsultarUnidades;
import es.mityc.facturaeface.bean.SSPPResultadoEnviarFactura;
import es.mityc.facturaeface.bean.SSPPUnidadDir;


@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class SSPPWebServiceProxyBindingStub extends org.apache.axis.client.Stub implements SSPPWebServiceProxyPort {
	private java.util.Vector cachedSerClasses = new java.util.Vector();
	private java.util.Vector cachedSerQNames = new java.util.Vector();
	private java.util.Vector cachedSerFactories = new java.util.Vector();
	private java.util.Vector cachedDeserFactories = new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[8];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1() {
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("enviarFactura");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "facturaWS"),
			org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
				ConstantsFACe.XMLNS_SSPP, "SSPPFactura"), SSPPFactura.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoEnviarFactura"));
		oper.setReturnClass(SSPPResultadoEnviarFactura.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("consultarFactura");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "numeroRegistro"),
			org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"SSPPResultadoConsultarFactura"));
		oper.setReturnClass(SSPPResultadoConsultarFactura.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[1] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("anularFactura");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "numeroRegistro"),
			org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
		oper.addParameter(param);
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "motivo"),
			org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoAnularFactura"));
		oper.setReturnClass(SSPPResultadoAnularFactura.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[2] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("consultarEstados");
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPEstados"));
		oper.setReturnClass(SSPPEstados.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[3] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("consultarUnidades");
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"SSPPResultadoConsultarUnidades"));
		oper.setReturnClass(SSPPResultadoConsultarUnidades.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[4] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("consultarAdministraciones");
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "ArrayOfSSPPUnidadDir"));
		oper.setReturnClass(SSPPUnidadDir[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[5] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("consultarUnidadesPorAdministracion");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codigoDir"),
			org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"SSPPResultadoConsultarUnidades"));
		oper.setReturnClass(SSPPResultadoConsultarUnidades.class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[6] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("consultarListadoFacturas");
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "listadoFacturas"),
			org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
				"http://schemas.xmlsoap.org/soap/encoding/", "Array"), java.lang.Object[].class, false, false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"ArrayOfSSPPResultadoConsultarFactura"));
		oper.setReturnClass(SSPPResultadoConsultarFactura[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[7] = oper;

	}

	public SSPPWebServiceProxyBindingStub() throws org.apache.axis.AxisFault {
		this(null);
	}

	public SSPPWebServiceProxyBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service)
		throws org.apache.axis.AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public SSPPWebServiceProxyBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
		java.lang.Class cls;
		javax.xml.namespace.QName qName;
		javax.xml.namespace.QName qName2;
		java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
		java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
		java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
		java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
		java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
		java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
		java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
		java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
		java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
		java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "ArrayOfSSPPEstado");
		cachedSerQNames.add(qName);
		cls = SSPPEstado[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPEstado");
		qName2 = null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "ArrayOfSSPPFicheroAnexo");
		cachedSerQNames.add(qName);
		cls = SSPPFicheroAnexo[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPFicheroAnexo");
		qName2 = null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"ArrayOfSSPPOrganoGestorUnidadTramitadora");
		cachedSerQNames.add(qName);
		cls = SSPPOrganoGestorUnidadTramitadora[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPOrganoGestorUnidadTramitadora");
		qName2 = null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "ArrayOfSSPPResultadoConsultarFactura");
		cachedSerQNames.add(qName);
		cls = SSPPResultadoConsultarFactura[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarFactura");
		qName2 = null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "ArrayOfSSPPUnidadDir");
		cachedSerQNames.add(qName);
		cls = SSPPUnidadDir[].class;
		cachedSerClasses.add(cls);
		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPUnidadDir");
		qName2 = null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPEstado");
		cachedSerQNames.add(qName);
		cls = SSPPEstado.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPEstados");
		cachedSerQNames.add(qName);
		cls = SSPPEstados.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPFactura");
		cachedSerQNames.add(qName);
		cls = SSPPFactura.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPFicheroAnexo");
		cachedSerQNames.add(qName);
		cls = SSPPFicheroAnexo.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPFicheroFactura");
		cachedSerQNames.add(qName);
		cls = SSPPFicheroFactura.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPOrganoGestorUnidadTramitadora");
		cachedSerQNames.add(qName);
		cls = SSPPOrganoGestorUnidadTramitadora.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoAnularFactura");
		cachedSerQNames.add(qName);
		cls = SSPPResultadoAnularFactura.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarFactura");
		cachedSerQNames.add(qName);
		cls = SSPPResultadoConsultarFactura.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarFacturaEstado");
		cachedSerQNames.add(qName);
		cls = SSPPResultadoConsultarFacturaEstado.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoConsultarUnidades");
		cachedSerQNames.add(qName);
		cls = SSPPResultadoConsultarUnidades.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPResultadoEnviarFactura");
		cachedSerQNames.add(qName);
		cls = SSPPResultadoEnviarFactura.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName = new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "SSPPUnidadDir");
		cachedSerQNames.add(qName);
		cls = SSPPUnidadDir.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

	}

	protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
		//Establecemos el CustomSocketFactory para la validacion del certificado
		//Actualmente aceptamos cualquier certificado para FACe
		AxisProperties.setProperty("axis.socketSecureFactory","es.mityc.facturaeface.trust.FACeSSLSocketFactory");
        try {
        	if(FACeConfig.getInstance().isUseProxy()) {
	        	Properties props= new Properties(System.getProperties());
	        	props.put("http.proxySet", "true");
	        	props.put("http.proxyHost", FACeConfig.getInstance().getProxyHost());
	        	props.put("http.proxyPort", FACeConfig.getInstance().getProxyPort());
	
	        	if (FACeConfig.getInstance().isAuthProxy()){
        			Authenticator.setDefault(new ProxyAuthenticator(FACeConfig.getInstance().getProxyUser(),FACeConfig.getInstance().getProxyPassword()));
        		} 
        		else {
        			Authenticator.setDefault(null);
        		}
	        	Properties newprops = new Properties(props);
	        	System.setProperties(newprops);
        	}
        	org.apache.axis.client.Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
/*			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}*/
			_call.setTimeout(FACeConfig.getInstance().getFACETimeoutMilis());
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				java.lang.String key = (java.lang.String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized (this) {
				if (firstCall()) {
					// must set encoding style before registering serializers
					_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
					_call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
					for (int i = 0; i < cachedSerFactories.size(); ++i) {
						java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
						javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames.get(i);
						java.lang.Object x = cachedSerFactories.get(i);
						if (x instanceof Class) {
							java.lang.Class sf = (java.lang.Class) cachedSerFactories.get(i);
							java.lang.Class df = (java.lang.Class) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
							org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
								.get(i);
							org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
								.get(i);
							_call.registerTypeMapping(cls, qName, sf, df, false);
						}
					}
				}
			}
			return _call;
		} catch (java.lang.Throwable _t) {
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public SSPPResultadoEnviarFactura enviarFactura(SSPPFactura facturaWS) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#enviarFactura");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "enviarFactura"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { facturaWS });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPResultadoEnviarFactura) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPResultadoEnviarFactura) org.apache.axis.utils.JavaUtils.convert(_resp,
						SSPPResultadoEnviarFactura.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SSPPResultadoConsultarFactura consultarFactura(java.lang.String numeroRegistro)
		throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#consultarFactura");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "consultarFactura"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { numeroRegistro });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPResultadoConsultarFactura) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPResultadoConsultarFactura) org.apache.axis.utils.JavaUtils.convert(_resp,
						SSPPResultadoConsultarFactura.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SSPPResultadoAnularFactura anularFactura(java.lang.String numeroRegistro, java.lang.String motivo)
		throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#anularFactura");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "anularFactura"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { numeroRegistro, motivo });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPResultadoAnularFactura) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPResultadoAnularFactura) org.apache.axis.utils.JavaUtils.convert(_resp,
						SSPPResultadoAnularFactura.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SSPPEstados consultarEstados() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[3]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#consultarEstados");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "consultarEstados"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPEstados) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPEstados) org.apache.axis.utils.JavaUtils.convert(_resp, SSPPEstados.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SSPPResultadoConsultarUnidades consultarUnidades() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[4]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#consultarUnidades");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP, "consultarUnidades"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPResultadoConsultarUnidades) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPResultadoConsultarUnidades) org.apache.axis.utils.JavaUtils.convert(_resp,
						SSPPResultadoConsultarUnidades.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SSPPUnidadDir[] consultarAdministraciones() throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[5]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#consultarAdministraciones");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"consultarAdministraciones"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPUnidadDir[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPUnidadDir[]) org.apache.axis.utils.JavaUtils.convert(_resp, SSPPUnidadDir[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SSPPResultadoConsultarUnidades consultarUnidadesPorAdministracion(java.lang.String codigoDir)
		throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[6]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#consultarUnidadesPorAdministracion");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"consultarUnidadesPorAdministracion"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { codigoDir });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPResultadoConsultarUnidades) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPResultadoConsultarUnidades) org.apache.axis.utils.JavaUtils.convert(_resp,
						SSPPResultadoConsultarUnidades.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public SSPPResultadoConsultarFactura[] consultarListadoFacturas(java.lang.Object[] listadoFacturas)
		throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
        Handler reqHandler = new FACeClientHandler(true); 
		_call.setClientHandlers(reqHandler, null);
		_call.setOperation(_operations[7]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(super.cachedEndpoint.toString() + "#consultarListadoFacturas");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(ConstantsFACe.XMLNS_SSPP,
			"consultarListadoFacturas"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] { listadoFacturas });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (SSPPResultadoConsultarFactura[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (SSPPResultadoConsultarFactura[]) org.apache.axis.utils.JavaUtils.convert(_resp,
						SSPPResultadoConsultarFactura[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

}
