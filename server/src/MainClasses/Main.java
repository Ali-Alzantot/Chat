/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;

import Server.Controller.Server;
import ViewLayer.fxml.AdminloginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Team4
 */
public class Main extends Application  {
      private static Stage primaryStageObj;  

      /**
       * 
       * @return primary Stage Object
       */
      
    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }

        /**
     * start Login.fxml 
     * @param primaryStage
     * @throws Exception 
     */
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageObj=primaryStage;
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ViewLayer/fxml/Login.fxml"));
        root = loader.load();
        AdminloginController controller = loader.getController(); 

        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);             
        primaryStage.show();
        
    }
    public static void main(String[] args) {
                 new ChatServerRMI ();
                launch(args);

    }
}
