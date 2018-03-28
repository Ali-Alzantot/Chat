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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * FXMLMyProfileController class
 *
 * @author team 4
 *
 * FXMLMyProfileController enables us to view Our Data which contains all data
 * that we have entered at the registration, also it implements Initializable so
 * FXML loader will automatically call any suitably annotated no-arguments
 * initialize() method defined by the controller
 *
 */
public class FXMLMyProfileController implements Initializable {

    /**
     * My profile identifier (MyName, MyPhone, MyStatus, MyEmail, MyCity,
     * MyBDate, MyPassword, MyImage, genderGroup)
     *
     */
    @FXML
    private TextField MyName;
    @FXML
    private TextField MyPhone;
    @FXML
    private TextArea MyStatus;
    @FXML
    private Label MyEmail;
    @FXML
    private TextField MyCity;
    @FXML
    private TextField MyBDate;
    @FXML
    private TextField MyPassword;
    @FXML
    private ImageView MyImage;
    @FXML
    private RadioButton maleGender;
    @FXML
    private RadioButton femaleGender;
    Client ci;
    BusinessInterface serverRef;
    @FXML
    private ToggleGroup genderGroup = new ToggleGroup();
    @FXML
    private Button saveButton;

    public Image img;
    Client c;

    /**
     * initialize method Called to initialize a controller after its root
     * element has been completely processed, also we have used the ToggleGroup
     * which represents the gender
     * @param url 
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        maleGender.setToggleGroup(genderGroup);
        maleGender.setUserData("male");

        femaleGender.setToggleGroup(genderGroup);
        femaleGender.setUserData("female");
    }

    /**
     * setsServerRef method enables us to move from the main client stage
     * to my profile and it's called by FXMLMyProfileController in
     * the main client.
     * @param serverRef will be sent to setsServerRef from client main
     */
    public void setsServerRef(BusinessInterface serverRef) {
        this.serverRef = serverRef;
    }

    /**
     * uploadPhotoMethod method which we used to enable the user to change his
     * profile picture through file chooser, this method should be run at the application thread
     * therefore we used platform.isFxApplicationThread() check
     */
    @FXML
    private void uploadPhotoMethod() {
        if (Platform.isFxApplicationThread()) {
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File selectedFile = fileChooser.showSaveDialog(null);

            if (selectedFile != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(selectedFile);
                    img = SwingFXUtils.toFXImage(bufferedImage, null);
                    MyImage.setImage(img);

                } catch (IOException ex) {
                    System.out.println("error in uploading photo");
                    //Lo1gger.getLogger(FXMLMyProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                    if (selectedFile != null) {
                        try {
                            BufferedImage bufferedImage = ImageIO.read(selectedFile);
                            img = SwingFXUtils.toFXImage(bufferedImage, null);
                            MyImage.setImage(img);

                        } catch (IOException ex) {
                            System.out.println("error in uploading photo");
                        }
                    }
                }
            });
        }
    }

    /**
     * setClient method which take a client object from the main client
     * scene to show its data in this scene, which enables us to restore the
     * data when we open another scene
     * @param c is a client with the personal data to set and get it to be shown
     */
    public void setClient(Client c) {
        this.ci = c;

        MyCity.setText(ci.getCity());
        MyName.setText(ci.getName());
        MyPhone.setText(ci.getPhone());
        MyStatus.setText(ci.getStatus());
        MyEmail.setText(ci.getEmail());
        MyPassword.setText(ci.getPassword().replaceAll(" ", ""));
        if (ci.getDate() != null) {
            MyBDate.setText(ci.getDate().toString());
        }
        
        if (ci.getGender() != null) {
            if (ci.getGender().replaceAll(" ", "").equalsIgnoreCase("Female")) {
                femaleGender.setSelected(true);
            } else {
                maleGender.setSelected(true);
            }
        }
    }

    
    /**
     * updateMyProfile method which enables the client to change his/her data unless Email which we used
     * as a primary key and we used the Email to retrieve the client and replace his/her data by which the user 
     * has entered now then we updated the client profile in the dataBase with the new entered data.
     * 
     * */
    @FXML
    public void updateMyProfile() {
        try {

            Client ct = serverRef.retreiveClient(MyEmail.getText().replaceAll(" ", ""));
            System.out.println(ct.getEmail());

            ct.setEmail((MyEmail.getText()));
            ct.setName(MyName.getText());
            ct.setCity(MyCity.getText());
            ct.setPassword(MyPassword.getText());
            ct.setPhone(MyPhone.getText());
            ct.setStatus(MyStatus.getText());
            ct.setDate(java.sql.Date.valueOf("3017-09-09"));
            // ct.setImage(MyImage.getImage());
            System.out.println("updateeeee");
            if (genderGroup.getSelectedToggle().getUserData() != null) {
                ct.setGender(genderGroup.getSelectedToggle().getUserData().toString());
            }

            System.out.println(ct.getGender());
            serverRef.update(ct);
            JOptionPane.showMessageDialog(null, "Data Updated successfully");
        } catch (RemoteException ex) {
            Logger.getLogger(FXMLMyProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
