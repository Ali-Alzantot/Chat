/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.DTO.Groups;
import DataAccessLayer.DTO.Has;
import business.BusinessInterface;
import java.awt.Dimension;
import static java.awt.SystemColor.window;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * GroupInfoController class
 *
 * GroupInfoController enables us to view The information about the group Chat,
 * also it implements Initializable so FXML loader will automatically call any
 * suitably annotated no-arguments initialize() method defined by the controller
 *
 * @author Team 4
 */
public class GroupInfoController implements Initializable {

    /**
     * The Group Chat identifier (gName, gpID) The Group Chat
     * features(addMembergp,deleteGroup)
     */
    @FXML
    private Label gName;

    @FXML
    private TextField addMembergp;

    @FXML
    private Label memberStatus;
    @FXML
    private Label memberName;
    @FXML
    private Button removemember;
    @FXML
    private HBox hboxMember;
    @FXML
    private VBox vbContainer;
    @FXML
    private Label gender;
    @FXML
    private Button deleteGroup;
    @FXML
    private ImageView addMemberImg;
    @FXML
    private Label gpID;
    @FXML
    private Label memberMail;

    BusinessInterface serverRef;

    ArrayList<Client> cs = new ArrayList<Client>();
    ArrayList<Groups> gs = new ArrayList<Groups>();
    ArrayList<Has> hm = new ArrayList<Has>();
    ClientMainController cmc;
    Client c;
    Groups g;
    Stage stage;

    /**
     *
     * setCmc method which take a ClientMainController object from the main
     * client scene to enable us to restore the data when we open group chat
     * information scene
     *
     * @param cmc is the ClientMainController object which be sent from main
     * client scene
     *
     *
     */
    public void setCmc(ClientMainController cmc) {
        this.cmc = cmc;
    }

    /**
     * setStage method which take a Stage object from the main client stage to
     * show group chat information stage
     *
     * @param stage is the Stage object which be sent from main client stage
     *
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * initialize method Called to initialize a controller after its root
     * element has been completely processed.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * setsServerRef method enables us to move from the main client stage to
     * group Chat information and it's called by GroupInfoController in the main
     * client.
     *
     * @param serverRef will be sent to setsServerRef from client main
     */
    public void setsServerRef(BusinessInterface serverRef) {
        this.serverRef = serverRef;
    }

    /**
     * deleteGpMethod method which deleting a group with all its data and we
     * achieved that by retrieving the group object by the group id which we
     * used as a primary key then we delete the group, close the stage and
     * finally we call the refreshAfterRemoveGroup method to from main client
     */
    @FXML
    public void deleteGpMethod() {
        int i = 0;
        Groups g = new Groups();

        try {
            g = serverRef.reteriveDeleteGroups(gpID.getText());
            System.out.println(g.getName());

            hm = serverRef.retreiveGroupRows(gpID.getText());
            int y;
            for (y = 0; y < hm.size(); y++) {
                serverRef.delete(hm.get(y));
            }
            i = serverRef.DeleteGroups(g);

            stage.close();
            cmc.refreshAfterRemoveGroup();

            System.out.println(i);
        } catch (RemoteException ex) {
            Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * onEnter method which enables us to add members to the group through vBox
     * by entering the client mail, each client data like(Name, Email, Status)
     * is shown in H-Box inside the V-Box
     *
     * @param ae which we used to make onEnter event for the addMembergp
     * textField
     */
    @FXML
    public void onEnter(ActionEvent ae) {
        Has h = new Has();
        h.setEmail(addMembergp.getText());   //Enter real mail in database 
        h.setId(gpID.getText());
        try {
            serverRef.create(h);
            vbContainer.getChildren().clear();

            try {
                ArrayList<Client> clients = serverRef.reteriveClients(gpID.getText());
                for (int i = 0; i < clients.size(); i++) {
                    hboxMember = new HBox();
                    HBox hb = new HBox();
                    hb.setSpacing(40);
                    if (clients.get(i).getName() != null) {
                        memberName = new Label(clients.get(i).getName().replaceAll("\\s+", ""));
                        memberName.setFont(new Font("Arial", 30));
                    }
                    if (clients.get(i).getStatus() != null) {
                        memberStatus = new Label(clients.get(i).getStatus().replaceAll("\\s+", ""));
                        memberStatus.setFont(new Font("Arial", 30));
                    }

                    memberMail = new Label(clients.get(i).getEmail().replaceAll("\\s+", ""));
                    memberMail.setFont(new Font("Arial", 30));

                    if (memberStatus == null && memberName != null) {
                        hb.getChildren().addAll(memberName, new Label(""), memberMail);
                    }
                    if (memberName == null && memberStatus != null) {
                        hb.getChildren().addAll(new Label(""), memberStatus, memberMail);
                    }
                    if (memberName == null && memberStatus == null) {
                        hb.getChildren().addAll(new Label(""), new Label(""), memberMail);
                    }
                    if (memberName != null && memberStatus != null) {
                        hb.getChildren().addAll(memberName, memberStatus, memberMail);
                    }
                    vbContainer.getChildren().add(hb);

                }
            } catch (RemoteException ex) {
                Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * setG method to display the group chat information(gName,gpID,member name,
     * member status,member email) which sent from main client
     *
     * @param g is the Groups object which be sent from main client scene
     */
    public void setG(Groups g) {
        this.g = g;

        gName.setText(g.getName().replaceAll(" ", ""));
        gpID.setText(g.getId().replaceAll(" ", ""));

        try {
            ArrayList<Client> clients = serverRef.reteriveClients(g.getId());
            for (int i = 0; i < clients.size(); i++) {
                //  System.out.println(clients.size());
                final String s = clients.get(i).getEmail();

                HBox hb = new HBox();
                hb.setSpacing(40);
                if (clients.get(i).getName() != null) {
                    memberName = new Label(clients.get(i).getName().replaceAll("\\s+", ""));
                    memberName.setFont(new Font("Arial", 30));
                }
                if (clients.get(i).getStatus() != null) {
                    memberStatus = new Label(clients.get(i).getStatus().replaceAll("\\s+", ""));
                    memberStatus.setFont(new Font("Arial", 30));
                }
                memberMail = new Label(clients.get(i).getEmail().replaceAll("\\s+", ""));
                memberMail.setFont(new Font("Arial", 30));

                if (memberStatus == null && memberName != null) {
                    hb.getChildren().addAll(memberName, new Label(""), memberMail);
                }
                if (memberName == null && memberStatus != null) {
                    hb.getChildren().addAll(new Label(""), memberStatus, memberMail);
                }
                if (memberName == null && memberStatus == null) {
                    hb.getChildren().addAll(new Label(""), new Label(""), memberMail);
                }
                if (memberName != null && memberStatus != null) {
                    hb.getChildren().addAll(memberName, memberStatus, memberMail);
                }
                vbContainer.getChildren().add(hb);

            }
        } catch (RemoteException ex) {
            Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
