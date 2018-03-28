/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import business.BusinessInterface;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 *
 * @author Ali Alzantot
 */
public class Main extends Application {
    
    private static Stage primaryStageObj;
    private static String ip;
    
    @Override
public void start(Stage primaryStage) {
        primaryStageObj=primaryStage;
        Parent root = null;
        try {
            Registry reg= LocateRegistry.getRegistry("127.0.0.1",5000);
            BusinessInterface serverRef = (BusinessInterface) reg.lookup("Chat");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            root = loader.load();
            LoginController controller = loader.getController(); 
            controller.setsServerRef(serverRef);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Server not Found");
            ex.printStackTrace();
        }

        
        Scene scene = new Scene(root);
        primaryStage.setTitle("User");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);             
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
       
    }
    
    
    public static Stage getPrimaryStage() {
        
        return primaryStageObj;
    }
}
