/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 * this is class fro user refrence with the server
 * @author Team4
 */

import DataAccessLayer.DTO.Message;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import userapp.ClientMainController;
public class ClientBusinessClass extends UnicastRemoteObject implements ClientBusinessInterface {
    ClientMainController c;

    
   /**
    * this method for getting user gui contoller to update the gui
    * @param c 
    */
    public void setC(ClientMainController c) {
        this.c = c;
    }

    /**
     * this is the constructor
     * @throws RemoteException 
     */
    public ClientBusinessClass() throws RemoteException {
        
    }
    
    /**
     * this method for receiving method from the server
     * @param msg
     * @param name
     * @param sendFlage
     * @param strFlage
     * @throws RemoteException 
     */
    @Override
    public void receive(Message msg, String name,int sendFlage,String strFlage) throws RemoteException {
        
        c.print(msg, name,sendFlage,strFlage);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * this method for receiving notifiaction from the server
     * @param msg
     * @throws RemoteException
     * @throws AWTException
     * @throws MalformedURLException 
     */
    @Override
    public void displayTray(String msg) throws RemoteException, AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        java.util.Date d = new java.util.Date();
        String lineseparator=System.getProperty("line.separator");
        trayIcon.displayMessage("SAAM CHAT ANNOUNCEMENT",msg+lineseparator+d, MessageType.INFO);
    }
    /**
     * this method for receiving friends online ANNOUNCEMENT
     * @param mail
     * @throws RemoteException
     * @throws AWTException
     * @throws MalformedURLException 
     */
    @Override
    public void displayTrayToClients(String mail) throws RemoteException, AWTException, MalformedURLException {
        System.out.println("From Displaaaaaaaaaaaaaay ICoooooon");
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();
        //If the icon is a file
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        java.util.Date d = new java.util.Date();
        String lineseparator=System.getProperty("line.separator");
        trayIcon.displayMessage("Status ANNOUNCEMENT",mail+" is Online Now"+lineseparator+d, MessageType.INFO);

    }
    /**
     * this method for receiving friends offline ANNOUNCEMENT
     * @param mail
     * @throws RemoteException
     * @throws AWTException
     * @throws MalformedURLException 
     */
    @Override
    public void displayTrayToClientsOFFLINE(String mail) throws RemoteException, AWTException, MalformedURLException {
        System.out.println("From Displaaaaaaaaaaaaaay ICoooooon");
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resizes the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);
        java.util.Date d = new java.util.Date();
        String lineseparator=System.getProperty("line.separator");
        trayIcon.displayMessage("Status ANNOUNCEMENT",mail+" is OFFLINE Now"+lineseparator+d, MessageType.INFO);

    }

    /** 
     * getLocalHost 
     * receive IP 
     * @return return IP as String
     * @throws RemoteException 
     */
    @Override
    public String receiveIP() throws RemoteException {
        String hostname = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
         //   byte[] ipAddr = addr.getAddress();
            hostname = addr.getHostAddress();
        } catch (java.net.UnknownHostException ex) {
            Logger.getLogger(ClientBusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostname;
    }

    /**
     * accept File  from Client Main Controller
     * @param email
     * @return
     * @throws RemoteException 
     */
    
    @Override
    public String acceptFile(String email) throws RemoteException {
        String s = c.acceptFile(email.replaceAll(" ",""));
        return s;
    }
    /**
     * send Buffered File from Client Main Controller
     * @param ip
     * @param fileName
     * @throws RemoteException 
     */
    @Override
    public void sendBufferedFile(String ip ,String fileName) throws RemoteException {
        c.sendBufferedFile();
        
    }
    /**
     * receive File  from Client Main Controller
     * @param ip
     * @param Email
     * @param fileSize
     * @return
     * @throws RemoteException 
     */
    @Override
    public String receiveFile(String ip,String Email,int fileSize) throws RemoteException {
        c.receiveFile(ip, Email,fileSize);
        return null;
    }
    /**
     * set Friend State  from    Client Main Controller
     * @param email
     * @param state
     * @throws RemoteException 
     */
    @Override
    public void setFriendState(String email, String state) throws RemoteException {
        c.setFriendStatus(email.replaceAll(" ", ""),state.replaceAll(" ", ""));
        System.out.println("geena hna");
    }
    
}
