/**
 * Operation_faultMsg.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.wroc.pwr.plwordnet.clarin.ws.takipi;

public class Operation_faultMsg extends org.apache.axis.AxisFault {
    public java.lang.String fault;
    public java.lang.String getFault() {
        return this.fault;
    }

    public Operation_faultMsg() {
    }

    public Operation_faultMsg(java.lang.Exception target) {
        super(target);
    }

    public Operation_faultMsg(java.lang.String message, java.lang.Throwable t) {
        super(message, t);
    }

      public Operation_faultMsg(java.lang.String fault) {
        this.fault = fault;
    }

    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, fault);
    }
}
