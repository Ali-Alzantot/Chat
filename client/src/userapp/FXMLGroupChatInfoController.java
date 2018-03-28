/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import business.BusinessInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXMLGroupChatInfoController class (Friend Profile Class)
 *
 * @author Team 4
 *
 * FXMLGroupChatInfoController enables us to view Our Friends profile which
 * contains all data that they have been entered at the registration unless
 * Password ofCourse, also it implements Initializable so FXML loader will
 * automatically call any suitably annotated no-arguments initialize() method
 * defined by the controller
 *
 *
 */
public class FXMLGroupChatInfoController implements Initializable {

    /**
     *
     * My Friend identifier (FrName, FrPhone, FrStatues, FrEmail, FrCity,
     * FrBDate,FrImage, FrGender)
     */
    @FXML
    private Text FrName;
    @FXML
    private Text FrPhone;
    @FXML
    private Text FrStatues;
    @FXML
    private Text FrEmail;
    @FXML
    private Text FrCity;
    @FXML
    private Text FrBDate;
    @FXML
    private Text FrGender;

    @FXML
    private ImageView FrImage;

    public Image img = new Image(getClass().getResourceAsStream("../images/away.png"));

    Client friend;
    BusinessInterface serverRef;

    /**
     * initialize method Called to initialize a controller after its root
     * element has been completely processed.
     * @param url 
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * setsServerRef method enables us to move from the main client scene
     * to my friend profile scene and it's called by FXMLMyProfileController in
     * the main client.
     * @param serverRef will be sent to setsServerRef from client main
     */
    public void setsServerRef(BusinessInterface serverRef) {
        this.serverRef = serverRef;
    }

    /**
     * setClient method which take a client object from the main client
     * scene to show its data in this scene, which enables us to restore the
     * data when we open another scene
     * @param c is a client with the personal data to set and get it to be shown
     */
    public void setClient(Client c) {
        this.friend = c;

        FrName.setText(friend.getName());
        FrStatues.setText(friend.getStatus());
        FrCity.setText("City:          " + friend.getCity());
        FrPhone.setText("Phone:          " + friend.getPhone());
        FrEmail.setText("Email:          " + friend.getEmail());
        FrGender.setText("Gender:          " + friend.getGender());
        FrBDate.setText("BDate:          " + friend.getDate());
        FrImage.setImage(img);
        FrImage.fitHeightProperty();
        FrImage.fitWidthProperty();
    }

}
