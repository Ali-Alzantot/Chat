/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

import InterfaceClient.ClientInt;
import business.BusinessClass;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Team4
 */
public class ChatServerRMI  {
public static  Registry reg;
public static BusinessClass serverImpl;

    /**
     * 
     * create Registry and binding it with serverImpl BusinessClass object as name "Chat"
     */
    public ChatServerRMI ()
    {
 
        try {
             reg = LocateRegistry.createRegistry(5000);            
             serverImpl = new BusinessClass();
            reg.rebind("Chat", serverImpl );
            System.out.println("binding.......");
        } catch (RemoteException ex) {
            Logger.getLogger(ChatServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * stop server
     */
public static void Stop() 
{
    try{


        /** Unexport  this will also remove us from the RMI runtime **/
        if(serverImpl!=null)
        UnicastRemoteObject.unexportObject(serverImpl, true);

        System.out.println("CalculatorServer exiting.");
    }
    catch(Exception ex){
        Logger.getLogger(ChatServerRMI.class.getName()).log(Level.SEVERE, null, ex);
    }
}
/**
 * start server again
 */
public static void conti()
{
 try {
                  
            reg.rebind("Chat", serverImpl );
            System.out.println("binding.......");
        } catch (RemoteException ex) {
            Logger.getLogger(ChatServerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }

}
}
