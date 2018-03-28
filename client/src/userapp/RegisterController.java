/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import business.BusinessInterface;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * RegisterController class
 *
 * RegisterController enables us to register to SAAM Chat when they haven't
 * account yet, also it implements Initializable so FXML loader will
 * automatically call any suitably annotated no-arguments initialize() method
 * defined by the controller
 *
 *
 * @author Team 4
 */
public class RegisterController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button closeButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button uploadPhotoButton;
    @FXML
    private TextField name;
    @FXML
    private TextField status;
    @FXML
    private TextField city;
    @FXML
    private TextField phone;
    @FXML
    private DatePicker date;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femalRadioButton;
    @FXML
    private TextField email;
    private double xOffset;
    private double yOffset;
    public Image img;
    BusinessInterface serverRef;
    @FXML
    private PasswordField password;
    @FXML
    private ToggleGroup genderGroup = new ToggleGroup();

    /**
     * initialize method we set toggle group for gender and make scene to be
     * movable by drag,drop and release, also it Called to initialize a
     * controller after its root element has been completely processed.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        maleRadioButton.setToggleGroup(genderGroup);
        maleRadioButton.setUserData("male");

        femalRadioButton.setToggleGroup(genderGroup);
        femalRadioButton.setUserData("female");

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
     * minimizeWindow Method to minimize the window
     *
     * @param event when we clicked on minimize button
     */
    @FXML
    private void minimizeWindow(ActionEvent event) {
        Main.getPrimaryStage().setIconified(true);
    }

    /**
     * closeSystem Method to close the window
     *
     * @param event when we clicked on close button
     */
    @FXML
    private void closeSystem(ActionEvent event) {
        Platform.exit();
        System.exit(0);

    }

    /**
     * registerMethod is used to create a client account with the data entered
     * by the user
     *
     * @param event
     */
    @FXML
    private void registerMethod(ActionEvent event) {
        Client c = new Client();
        c.setCity(city.getText());
        c.setDate(java.sql.Date.valueOf(date.getValue()));
        c.setName(name.getText());
        c.setGender(genderGroup.getSelectedToggle().getUserData().toString());
        c.setPhone(phone.getText());
        c.setEmail(email.getText());
        c.setPassword(password.getText());
        c.setStatus(status.getText());
        c.setImage("yy");
        try {
            int ret = serverRef.createClient(c);
            if (ret == 0) {
                JOptionPane.showMessageDialog(null, "User Acoount Created Succeccfully");
            } else {
                JOptionPane.showMessageDialog(null, "Error in Creating User");
            }
        } catch (RemoteException ex) {
            System.out.println("error in creating clint inside register controller");
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * uploadPhotoMethod is used to enable the client to choose an image during
     * register through application thread
     *
     * @param event
     */
    @FXML
    private void uploadPhotoMethod(ActionEvent event) {
        if (Platform.isFxApplicationThread()) {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File selectedFile = fileChooser.showSaveDialog(null);

            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                img = SwingFXUtils.toFXImage(bufferedImage, null);

            } catch (IOException ex) {
                System.out.println("error in uploading photo");
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            Platform.runLater(new Runnable() {

                @Override
                public void run() {

                    FileChooser fileChooser = new FileChooser();
                    //Set extension filter
                    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                    fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

                    File selectedFile = fileChooser.showSaveDialog(null);

                    try {
                        BufferedImage bufferedImage = ImageIO.read(selectedFile);
                        img = SwingFXUtils.toFXImage(bufferedImage, null);

                    } catch (IOException ex) {
                        System.out.println("error in uploading photo");
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });

        }

    }

    /**
     * viewLoginMethod is used to enable the client who has registered now to
     * log in SAAM Chat after registration
     *
     * @param event
     */
    @FXML
    private void viewLoginMethod(ActionEvent event) {

        if (Platform.isFxApplicationThread()) {
            Stage stage;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = loader.load();
                LoginController controller = loader.getController();
                controller.setsServerRef(serverRef);
                Scene scene = new Scene(root, 350, 420);
                stage.setTitle("Register");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("error in show login fxml from register fxml");
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            Platform.runLater(new Runnable() {

                @Override
                public void run() {

                    Stage stage;
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                        Parent root = loader.load();
                        LoginController controller = loader.getController();
                        controller.setsServerRef(serverRef);
                        Scene scene = new Scene(root, 350, 420);
                        stage.setTitle("Register");
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("error in show login fxml from register fxml");
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });

        }

    }

    /**
     * setsServerRef method enables us to move from the Login stage to Register
     * stage and it's called by RegisterController in the LoginController.
     *
     * @param serverRef will be sent to setsServerRef from LoginController
     */
    public void setsServerRef(BusinessInterface serverRef) {
        this.serverRef = serverRef;
    }

}
