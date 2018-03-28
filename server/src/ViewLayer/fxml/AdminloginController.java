/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewLayer.fxml;

import DataAccessLayer.DAO.AdminImpl;
import DataAccessLayer.DTO.Admin;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import MainClasses.Main;
import Server.Controller.Server;
import businessLayer.admin.Login;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 *
 * @author Team4
 */
public class AdminloginController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private ImageView Defaultview;
    @FXML
    private ImageView Sarahview;
    @FXML
    private ImageView Dominicview;
    @FXML
    private Label selectedPicture;
    @FXML
    private Button closeButton;
     @FXML
    private Button MinimizedBtn;   
    
    private double xOffset;
    private double yOffset;

    /**
     * Initializes the controller class.
     */
    private static AdminloginController instance;

    public AdminloginController() {
        instance = this;
    }
    /**
     * initialize Admin login Controller
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* Drag and Drop */
        borderPane.setOnMousePressed(event -> {
            xOffset = Main.getPrimaryStage().getX() - event.getScreenX();
            yOffset = Main.getPrimaryStage().getY() - event.getScreenY();
            borderPane.setCursor(Cursor.CLOSED_HAND);
        });

        borderPane.setOnMouseDragged(event -> {
            Main.getPrimaryStage().setX(event.getScreenX() + xOffset);
            Main.getPrimaryStage().setY(event.getScreenY() + yOffset);

        });

        borderPane.setOnMouseReleased(event -> {
            borderPane.setCursor(Cursor.DEFAULT);
        });
    }
    /**
     * Client login authorization and loading Chart.fxml
     * @param event 
     */
    @FXML
    private void loginButtonAction(ActionEvent event) {

        if (Platform.isFxApplicationThread()) {
            AdminImpl adminImpl = new AdminImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Admin c = adminImpl.retreive(usernameTextfield.getText(), passwordTextfield.getText(), con);//serverRef.retreiveByEmailAndPass(email.getText(),password.getText());
            if (c != null) {
                JOptionPane.showMessageDialog(null, "Login Success ^_^ ");

                if (Platform.isFxApplicationThread()) {
                    Stage stage;
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chart.fxml"));
                        Parent root = loader.load();
                          ChartController controller = loader.getController();
                            Server.setServerController(controller);
                                if(Server.chartcontroller!=null)
                               new Thread(new Server()).start();
                        Scene scene = new Scene(root);
                        stage.setTitle("Register");
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("error in show register fxml from login fxml");
                        Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            Stage stage;
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Chart.fxml"));
                                Parent root = loader.load();
                                ChartController controller = loader.getController();
                                Server.setServerController(controller);
                                if(Server.chartcontroller!=null)
                                new Thread(new Server()).start();
                                Scene scene = new Scene(root);
                                stage.setTitle("Register");
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println("error in show register fxml from login fxml");
                                Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                }

            } else {
                JOptionPane.showMessageDialog(null, "Error in Email and Password IF you don't have Account Please Register");
            }
        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {

                    AdminImpl adminImpl = new AdminImpl();
                    DataBaseManager managerObj = new DataBaseManager();
                    Connection con = managerObj.getCon();
                    Admin c = adminImpl.retreive(usernameTextfield.getText(), passwordTextfield.getText(), con);//serverRef.retreiveByEmailAndPass(email.getText(),password.getText());
                    if (c != null) {
                        JOptionPane.showMessageDialog(null, "Login Success ^_^ ");

                        if (Platform.isFxApplicationThread()) {
                            Stage stage;
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Chart.fxml"));
                                Parent root = loader.load();
                                //  ChartController controller = loader.getController();
                                //   controller.setsServerRef(serverRef);
                                Scene scene = new Scene(root);
                                stage.setTitle("Register");
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println("error in show register fxml from login fxml");
                                Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    Stage stage;
                                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chart.fxml"));
                                        Parent root = loader.load();
                                        //    ChartController controller = loader.getController();
                                        //     controller.setsServerRef(serverRef);
                                        Scene scene = new Scene(root, 1280, 800);
                                        stage.setTitle("Register");
                                        stage.setScene(scene);
                                        stage.setResizable(false);
                                        stage.initStyle(StageStyle.UNDECORATED);
                                        stage.show();
                                    } catch (IOException ex) {
                                        System.out.println("error in show register fxml from login fxml");
                                        Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Error in Email and Password IF you don't have Account Please Register");
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                }
            });
        }

//        Login login = new Login();
//        Admin admin = new Admin();
//        admin.setUserName(usernameTextfield.getText());
//        admin.setPassword(passwordTextfield.getText());
//        if (login.Cheack(admin) == 0) {
//            System.out.println("Login Sucesse.....");
//            FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("ChatView.fxml"));
//            try {
//                Parent window = (Pane) fmxlLoader.load();
//            } catch (IOException ex) {
//                Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            //  this.scene = new Scene(window);
//        }
    }
  /**
     * Minimize window used by fxml
     * @param event 
     */
    @FXML
    private void minimizeWindow(ActionEvent event) {
        Main.getPrimaryStage().setIconified(true);
    }
      /**
     * close window and end system running   used by fxml
     * @param event 
     */
    @FXML
    private void closeSystem(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

}
