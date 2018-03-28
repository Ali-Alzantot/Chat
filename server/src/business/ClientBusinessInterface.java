/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import DataAccessLayer.DTO.Message;
import java.awt.AWTException;
import java.rmi.*;

/**
 * this is interface fro user refrence with the server
 * 
 * @author Team4
 */
public interface ClientBusinessInterface extends Remote{
     void receive(Message msg,String name,int sendFlage,String strFlage) throws RemoteException;
     public void displayTray(String msg) throws RemoteException,AWTException, java.net.MalformedURLException;
     public void displayTrayToClients(String mail) throws RemoteException,AWTException, java.net.MalformedURLException;
     public void displayTrayToClientsOFFLINE(String mail) throws RemoteException,AWTException, java.net.MalformedURLException;
     public String receiveIP() throws RemoteException;//abdalla
     public String acceptFile(String Email) throws RemoteException;//abdalla
     public void sendBufferedFile(String ip , String fileName) throws RemoteException;//abdalla
     public String receiveFile(String ip,String Email,int fileSize) throws RemoteException ;//abdalla
     public void setFriendState(String email,String state) throws RemoteException;
}
