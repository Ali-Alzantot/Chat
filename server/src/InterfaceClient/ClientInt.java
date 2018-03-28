/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Team4
 */
public interface ClientInt  extends Remote {
 void receive(String msg) throws RemoteException; 

}
