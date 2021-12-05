/**
 * IfService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ifService;

public interface IfService extends java.rmi.Remote {
    public java.lang.String getName(java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String getState(java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String getType(java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String login(int id, java.lang.String password) throws java.rmi.RemoteException;
    public boolean selectProduct(java.lang.String idProduct) throws java.rmi.RemoteException;
    public float getNote(java.lang.String id) throws java.rmi.RemoteException;
    public double getPrice(java.lang.String id, java.lang.String currency) throws java.rmi.RemoteException;
    public boolean signUp(int id, java.lang.String password, java.lang.String firstName, java.lang.String lastName) throws java.rmi.RemoteException;
    public java.lang.String[] getAllProduct() throws java.rmi.RemoteException;
    public boolean buyProduct(java.lang.String[] idProducts, double price, int idUser) throws java.rmi.RemoteException;
    public double balanceValue(int id) throws java.rmi.RemoteException;
}
