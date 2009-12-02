package pl.wroc.pwr.plwordnet.clarin.ws.takipi;

public class TakipiProxy implements pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_PortType {
  private String _endpoint = null;
  private pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_PortType takipi_PortType = null;
  
  public TakipiProxy() {
    _initTakipiProxy();
  }
  
  public TakipiProxy(String endpoint) {
    _endpoint = endpoint;
    _initTakipiProxy();
  }
  
  private void _initTakipiProxy() {
    try {
      takipi_PortType = (new pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_ServiceLocator()).gettakipiSOAP();
      if (takipi_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)takipi_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)takipi_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (takipi_PortType != null)
      ((javax.xml.rpc.Stub)takipi_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pl.wroc.pwr.plwordnet.clarin.ws.takipi.Takipi_PortType getTakipi_PortType() {
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType;
  }
  
  public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse tag(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.tag(text, format, useGuesser);
  }
  
  public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse lemmatize(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.lemmatize(text, format, useGuesser);
  }
  
  public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse tokenize(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.tokenize(text, format, useGuesser);
  }
  
  public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse analyzeMorphology(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.analyzeMorphology(text, format, useGuesser);
  }
  
  public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse splitIntoSentences(java.lang.String text, pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat format, boolean useGuesser) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.splitIntoSentences(text, format, useGuesser);
  }
  
  public int getStatus(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.getStatus(token);
  }
  
  public pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse getResult(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.getResult(token);
  }
  
  public boolean deleteRequest(java.lang.String token) throws java.rmi.RemoteException, pl.wroc.pwr.plwordnet.clarin.ws.takipi.Operation_faultMsg{
    if (takipi_PortType == null)
      _initTakipiProxy();
    return takipi_PortType.deleteRequest(token);
  }
  
  
}