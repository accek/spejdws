/**
 * TakipiSOAPStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.wroc.pwr.plwordnet.clarin.ws.takipi;

public class TakipiSOAPStub extends org.apache.axis.client.Stub implements pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[8];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Tag");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "text"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "format"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "DocumentFormat"), pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useGuesser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "TaggerResponse"));
        oper.setReturnClass(pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "TagResponse"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Lemmatize");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "text"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "format"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "DocumentFormat"), pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useGuesser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "TaggerResponse"));
        oper.setReturnClass(pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "TagResponse"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Tokenize");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "text"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "format"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "DocumentFormat"), pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useGuesser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "TaggerResponse"));
        oper.setReturnClass(pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "TagResponse"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AnalyzeMorphology");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "text"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "format"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "DocumentFormat"), pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useGuesser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "TaggerResponse"));
        oper.setReturnClass(pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "TagResponse"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("SplitIntoSentences");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "text"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "format"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "DocumentFormat"), pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "useGuesser"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "TaggerResponse"));
        oper.setReturnClass(pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "TagResponse"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        oper.setReturnClass(int.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "status"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetResult");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "TaggerResponse"));
        oper.setReturnClass(pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "TagResponse"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DeleteRequest");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "token"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "status"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg",
                      new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
                      false
                     ));
        _operations[7] = oper;

    }

    public TakipiSOAPStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public TakipiSOAPStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public TakipiSOAPStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
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
            qName = new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "DocumentFormat");
            cachedSerQNames.add(qName);
            cls = pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "TaggerResponse");
            cachedSerQNames.add(qName);
            cls = pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
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
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
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
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse tag(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/Tag");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "Tag"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {text, format, new java.lang.Boolean(useGuesser)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse lemmatize(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/Lemmatize");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "Lemmatize"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {text, format, new java.lang.Boolean(useGuesser)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse tokenize(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/Tokenize");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "Tokenize"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {text, format, new java.lang.Boolean(useGuesser)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse analyzeMorphology(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/AnalyzeMorphology");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "AnalyzeMorphology"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {text, format, new java.lang.Boolean(useGuesser)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse splitIntoSentences(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/SplitIntoSentences");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "SplitIntoSentences"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {text, format, new java.lang.Boolean(useGuesser)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public int getStatus(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/GetStatus");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "GetStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Integer) _resp).intValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Integer) org.apache.axis.utils.JavaUtils.convert(_resp, int.class)).intValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse getResult(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/GetResult");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "GetResult"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse) org.apache.axis.utils.JavaUtils.convert(_resp, pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public boolean deleteRequest(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/DeleteRequest");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "DeleteRequest"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {token});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) {
              throw (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
