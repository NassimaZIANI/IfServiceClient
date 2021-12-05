package ifService;

public class IfServiceProxy implements ifService.IfService {
  private String _endpoint = null;
  private ifService.IfService ifService = null;
  
  public IfServiceProxy() {
    _initIfServiceProxy();
  }
  
  public IfServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIfServiceProxy();
  }
  
  private void _initIfServiceProxy() {
    try {
      ifService = (new ifService.IfServiceServiceLocator()).getIfService();
      if (ifService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ifService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ifService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ifService != null)
      ((javax.xml.rpc.Stub)ifService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ifService.IfService getIfService() {
    if (ifService == null)
      _initIfServiceProxy();
    return ifService;
  }
  
  public java.lang.String getName(java.lang.String id) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.getName(id);
  }
  
  public java.lang.String getType(java.lang.String id) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.getType(id);
  }
  
  public java.lang.String login(int id, java.lang.String password) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.login(id, password);
  }
  
  public boolean selectProduct(java.lang.String idProduct) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.selectProduct(idProduct);
  }
  
  public double getPrice(java.lang.String id, java.lang.String currency) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.getPrice(id, currency);
  }
  
  public boolean signUp(int id, java.lang.String password, java.lang.String firstName, java.lang.String lastName) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.signUp(id, password, firstName, lastName);
  }
  
  public java.lang.String[] getAllProduct() throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.getAllProduct();
  }
  
  public boolean buyProduct(java.lang.String[] idProducts, double price, int idUser) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.buyProduct(idProducts, price, idUser);
  }
  
  public double balanceValue(int id) throws java.rmi.RemoteException{
    if (ifService == null)
      _initIfServiceProxy();
    return ifService.balanceValue(id);
  }
  
  
}