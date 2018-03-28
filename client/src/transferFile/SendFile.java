    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferFile;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import userapp.Main;

/**
 *
 * @author abdalla
 */
public class SendFile {

    ServerSocket servsock = null;
    File myFile = null;
    Socket sock = null;
    byte[] mybytearray = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    DataOutputStream dos;
           
    /**
     * Send File
     * @param port
     * @param pathOfFile 
     */
    public SendFile( int port , String pathOfFile) {
        try {
            servsock = new ServerSocket(port);
            myFile = new File(pathOfFile);
            
            long size = myFile.length();
            
            System.out.println("File Size = " + size);
            
            sock = servsock.accept();
            int count;

            bis = new BufferedInputStream(new FileInputStream(myFile));
//            bis.read(mybytearray, 0, mybytearray.length);
            os = sock.getOutputStream();
            dos = new DataOutputStream(os);
            dos.writeLong(size);

//            os.write();
            double ss =0;
            byte[] buffer = new byte[4096];
            while ((count = bis.read(buffer)) > 0) {
                os.write(buffer, 0, count);
                ss =(double)(ss + count);
                System.out.println(ss * 100L / 100);
                
            }
//            Label message = new Label("File Send Successfully ..");
//            Main.getPrimaryStage().setScene(new Scene(message));
            System.out.println("File Send Successfully ..");                   
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Catch from SendFile");
        } finally {
            try {
                if(os!=null)
                os.flush();
                os.close();
                if(bis!=null)
                bis.close();//abdalla
                if(dos!=null)
                dos.close();
                if(sock!=null)
                sock.close();
                if(servsock!=null)
               servsock.close();
            } catch (Exception e) {

            }
        }
    }
    
    



}
