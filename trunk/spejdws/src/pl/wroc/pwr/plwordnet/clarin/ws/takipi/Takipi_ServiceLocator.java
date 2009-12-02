/**
 * Takipi_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.wroc.pwr.plwordnet.clarin.ws.takipi;

public class Takipi_ServiceLocator extends org.apache.axis.client.Service implements pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_Service {

    public Takipi_ServiceLocator() {
    }


    public Takipi_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Takipi_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for takipiSOAP
    private java.lang.String takipiSOAP_address = "http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/";

    public java.lang.String gettakipiSOAPAddress() {
        return takipiSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String takipiSOAPWSDDServiceName = "takipiSOAP";

    public java.lang.String gettakipiSOAPWSDDServiceName() {
        return takipiSOAPWSDDServiceName;
    }

    public void settakipiSOAPWSDDServiceName(java.lang.String name) {
        takipiSOAPWSDDServiceName = name;
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_PortType gettakipiSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(takipiSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return gettakipiSOAP(endpoint);
    }

    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_PortType gettakipiSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pl.wroc.pwr.plwordnet.clarin.ws.takipi.TakipiSOAPStub _stub = new pl.wroc.pwr.plwordnet.clarin.ws.takipi.TakipiSOAPStub(portAddress, this);
            _stub.setPortName(gettakipiSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void settakipiSOAPEndpointAddress(java.lang.String address) {
        takipiSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                pl.wroc.pwr.plwordnet.clarin.ws.takipi.TakipiSOAPStub _stub = new pl.wroc.pwr.plwordnet.clarin.ws.takipi.TakipiSOAPStub(new java.net.URL(takipiSOAP_address), this);
                _stub.setPortName(gettakipiSOAPWSDDServiceName());
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
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("takipiSOAP".equals(inputPortName)) {
            return gettakipiSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "takipi");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://plwordnet.pwr.wroc.pl/clarin/ws/takipi/", "takipiSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("takipiSOAP".equals(portName)) {
            settakipiSOAPEndpointAddress(address);
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
