/**
 * Takipi_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pl.wroc.pwr.plwordnet.clarin.ws.takipi;

public interface Takipi_PortType extends java.rmi.Remote {
    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse tag(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse lemmatize(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse tokenize(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse analyzeMorphology(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse splitIntoSentences(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
    public int getStatus(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
    public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse getResult(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
    public boolean deleteRequest(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg;
}
