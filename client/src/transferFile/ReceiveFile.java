/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import userapp.Main;

/**
 *
 * @author abdalla
 */
public class ReceiveFile {

    Socket sock = null;
    InputStream is = null;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    int bytesRead = 0;
    byte[] buffer = null;

    

    /**
     * Receive File
     * @param ip 
     * @param port
     * @param fileName 
     */
    public ReceiveFile(String ip , int port , String fileName) {
        try {
            System.out.println("ip : "+ip+ "port : "+port +"fileName :"+fileName);
            sock = new Socket(ip, port);
            is = sock.getInputStream();
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            buffer = new byte[4096];
            int count;
             DataInputStream b=  new DataInputStream(sock.getInputStream());
//            int size = b.readInt();
           Long size = b.readLong();
            System.out.println(size);
            while ((count = is.read(buffer)) > 0) {
                bos.write(buffer, 0, count);
                //System.out.println("");
            }
//            Label message = new Label("Received");
//            Main.getPrimaryStage().setScene(new Scene(message));
            System.out.println("Received");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Catch from receiveFile");
        } finally {

            try {
                if(bos!=null)
                bos.close();
                if(fos!=null)
                fos.close();
                if(sock!=null)
                sock.close();
            } catch (Exception e) {
                System.out.println("catch from finally");
            }

        }
    }

}
