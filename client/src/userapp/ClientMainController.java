/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.DTO.Groups;
import DataAccessLayer.DTO.Message;
import DataAccessLayer.DTO.Requests;
import business.BusinessInterface;
import business.ClientBusinessInterface;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;

import java.io.IOException;
import static java.lang.String.format;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import transferFile.ProgressBar;
import transferFile.ReceiveFile;
import transferFile.SendFile;

/**
 * controlls the main view controller
 * @author Team 4
 */
public class ClientMainController implements Initializable {

    ArrayList<Client> cs = new ArrayList<Client>();
    ArrayList<Groups> gs = new ArrayList<Groups>();
    ArrayList<String> rs = new ArrayList<String>();
    ArrayList<Contact> cons = new ArrayList<Contact>();
    Client c;
    ClientMainController cmc;
    Client cChosen;
    Groups gChosen;
    int flagChosen = -1;
    @FXML
    ScrollPane scrollPane;
    BusinessInterface serverRef;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Text friendName;
    @FXML
    private Text userName;
    @FXML
    private Text friendStatus;
    @FXML
    private ImageView friendImg;
    @FXML
    VBox requestList;
    @FXML
    ListView<Text> groupList;
    @FXML
    ListView<Text> friendList;
    @FXML
    TextField textField;
    private double xOffset;
    private double yOffset;
    @FXML
    private ImageView userImg;
    @FXML
    private MenuItem profile;
    @FXML
    private MenuItem logout;

    @FXML
    private ComboBox userstatus;
    @FXML
    private HBox onlineUsersHbox;
    @FXML
    private Label onlineCount;
    @FXML
    private Button addFriend;
    @FXML
    private ImageView addfriend;
    @FXML
    private Label groupsCount;
    @FXML
    private Button addGroup;
    @FXML
    private ImageView creategroup;
    @FXML
    private HBox onlineUsersHbox1;
    @FXML
    private Label resquestsCount;
    @FXML
    private MenuBar friendMenu;
    @FXML
    private MenuItem unfriend;
    @FXML
    private MenuItem friendProfile;
    @FXML
    private MenuItem gInfo;
    @FXML
    private MenuItem leaveG;
    @FXML
    private ImageView menu;
    @FXML
    private ComboBox font;
    @FXML
    private ComboBox fontSize;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Menu infoMenu;
    public Image myImg = new Image(getClass().getResourceAsStream("l.png"));
    public Image friendimg = new Image(getClass().getResourceAsStream("n.jpg"));
    ArrayList<String> receiversEmails = new ArrayList<String>();
    ImageView old;
    ImageView myold;
    int flage = 0;
    String lastTypedName = "543";
    Groups choosenGroup;
    Client choosenClient;
    @FXML
    private VBox vb;
    String choosenvb = "";
    int choosenFlage = -1;
    public HashMap<String, VBox> messges = new HashMap<String, VBox>();
    @FXML
    private Button upload;
    private Desktop desktop = Desktop.getDesktop();
    private String FilePath;
    private Stage watingStage;
    private String fileName;
    private int fileSize;   

    ClientBusinessInterface clientRef;

    /**
     * Initializes the controller class. building the combo boxes controlling
     * the message font (Font Familiesand sizes). setting initial values for the
     * size, color and the font. setting the client main controller parameter to
     * this controller. setting border pane movement
     *
     * xoffset cursor position in x axis.
     *  yoffset cursor position in y axis.
     *  fontSize font size combo box.
     * font font family combo box.
     * colorPicker font color Picker.
     *  font size, font family color boxes and font color picker colorPicker
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fontSize.getItems().addAll("5", "10", "20", "25", "30", "48");
        fontSize.getSelectionModel().select(3);
        font.getItems().addAll(javafx.scene.text.Font.getFamilies());
        font.getSelectionModel().select(0);
        colorPicker.setValue(Color.WHITE);

        cmc = this;
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
     * sets the minimize button action to minimize the window. the button action
     * is set in clientMain.fxml.
     *
     * @param event
     */
    @FXML
    private void minimizeWindow(ActionEvent event) {
        Main.getPrimaryStage().setIconified(true);
    }

    /**
     * sets the minimize button action to close the window. the button action is
     * set in clientMain.fxml.
     *
     * @param event
     */
    @FXML
    private void closeSystem(ActionEvent event) {
        try {
            serverRef.unRegister(clientRef, c.getEmail());
            userstatus.setValue("Offline");
            setMyStatus();
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Platform.exit();
        System.exit(0);
    }

    /**
     * sets the friends list of the client in the friends ListView by calling
     * the server to retrieve them. the retrieved list is placed in the cs
     * ArrayList variable. links the email of the client with a new VBox in
     * messges HashMap.
     *
     * @param c the client object of the owner of this window
     * the friends list on the left of the scene each row represents a
     * friend.
     */
    public void setFriendList(Client c) {
        friendList.getItems().clear();
        try {
            cs = serverRef.retreiveFriends(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Client x : cs) {
            VBox vbTemp = new VBox();
            vbTemp.setSpacing(5);
            messges.put(x.getEmail().replaceAll("\\s+", ""), vbTemp);
            String s = x.getName();
            s = x.getName().replaceAll("  ", "");
            friendList.getItems().add(new Text(s));
        }
        setFriendsStatus();
    }

    /**
     * sets the groups list of the groups in the groups ListView by calling the
     * server to retrieve them. the retrieved list is placed in the gs ArrayList
     * variable. links the email of the client with a new VBox in messges
     * HashMap.
     *
     * @param c the client object of the owner of this window.
     * @see the group list on the left of the scene each row represents a group.
     */
    public void setGroupList(Client c) {
        groupList.getItems().clear();
        try {
            gs = serverRef.retreiveGroups(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Groups x : gs) {
            VBox vbTemp = new VBox();
            vbTemp.setSpacing(5);
            messges.put(x.getName().replaceAll(" ", ""), vbTemp);
            String s = x.getName();
            s = x.getName().replaceAll("  ", "");
            groupList.getItems().add(new Text(s));
        }
    }

    /**
     * sets the friends list of the client in the requests ListView by calling
     * the server to retrieve them. the retrieved list is placed in the rs
     * ArrayList variable. adding accept and decline buttons to the view and
     * linking them to the accept and decline functions, and updates the friends ListView.
     *
     * @param C
     * requests list on the left if the scene each row represents a request
     * each with accept and decline button
     */

    public void setRequestList(Client C) {
        requestList.getChildren().clear();
        try {
            rs = serverRef.retreiveRequests(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String x : rs) {
            final String s = x;
            Button accept = new Button("", new Text("accept"));
            Button decline = new Button("", new Text("Decline"));
            accept.setStyle("-fx-margin: 3");
            decline.setStyle("-fx-margin: 3");
            accept.setPrefSize(0, 0);
            decline.setPrefSize(0, 0);
            HBox reqBox = new HBox();
            x = x.replaceAll("  ", "");
            Text reqLabel = new Text(x);
            reqBox.getChildren().addAll(reqLabel, accept, decline);
            requestList.getChildren().add(reqBox);
            accept.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    acceptRequest(s);
                    setRequestList(c);
                    setFriendList(c);
                }
            });
            decline.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DeclineRequest(s);
                    setRequestList(c);
                    setFriendList(c);
                }
            });
        }
    }

    /**
     * calls the acceptRequest method in the server via the server reference.
     *
     * @param sender represents the sender email.
     */
    private void acceptRequest(String sender) {
        int i = 0;
        try {
            i = serverRef.acceptRequest(sender, c.getEmail());

        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * calls the declineRequest method in the server via the server reference.
     *
     * @param sender
     */
    private void DeclineRequest(String sender) {
        int i = 0;
        try {
            i = serverRef.declineRequest(sender, c.getEmail());

        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * sets the owner client lists by calling setRequestList, setFriendList and
     * setGroupList methods. setting the client variable c which represents the
     * owner variable object. sets the status of the owner client to online.
     *
     * @param cc
     */
    public void setClient(Client cc) {
        if (Platform.isFxApplicationThread()) {
            this.c = cc;
            setRequestList(c);
            setFriendList(c);
            setGroupList(c);
            userName.setText(c.getName().replaceAll(" ", ""));
            userstatus.setValue("Online");
            setMyStatus();
        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    c = cc;
                    setRequestList(c);
                    setFriendList(c);
                    setGroupList(c);
                    userName.setText(c.getName().replaceAll(" ", ""));
                    userstatus.setValue("Online");
                    setMyStatus();
                }
            });
        }
    }

    /**
     * the addFriendMethod opens a JoptionPane to take the variable of a string
     * which represents the email of request receiver calls addfriend method of
     * the server via the reference serverRef. the method is called by the addfriend button.
     * @param e ActionEvent parameter.
     */
    @FXML
    private void addFriendMethod(ActionEvent e) {
        int i = -100;
        String mailPane = JOptionPane.showInputDialog("Add Friend please write down the Email");
        try {
            i = serverRef.addFriend(c.getEmail(), mailPane);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * the unFriendMethod opens a JoptionPane to confirm the user's choice of
     * removing the contact. the method removes the friend and the messages vbox
     * from from the hashmap. calls unfriend method of the server via the
     * reference serverRef. the method is called by the unfriend menuItem.
     */

    @FXML
    private void unfriendMethod() {

        int unfriendPane;
        unfriendPane = JOptionPane.showConfirmDialog(null, "Would you like to unfriend " + friendName.getText(), "Unfriend", YES_NO_OPTION);
        if (unfriendPane == 0) {
            try {
                serverRef.unfriend(c.getEmail(), cChosen.getEmail());
                messges.remove(cChosen.getEmail().replaceAll(" ", ""));
                setFriendList(c);
                cChosen = null;
                friendName.setVisible(false);
                friendStatus.setVisible(false);
                friendImg.setVisible(false);
                infoMenu.setVisible(false);
                friendList.getSelectionModel().select(-1);
            } catch (RemoteException ex) {
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * the unFriendMethod opens a JoptionPane to confirm the user's choice of
     * removing the contact. the method removes the group and the messages vboxfrom the hashmap.
     * calls leave method of the server via the reference
     * serverRef. the method is called by the leaveG menuItem.
     *
     * @param e
     */

    @FXML
    private void leaveGroupMethod(ActionEvent e) {

        int unfriendPane;
        unfriendPane = JOptionPane.showConfirmDialog(null, "Would you like to Leave " + friendName.getText(), "Unfriend", YES_NO_OPTION);
        if (unfriendPane == 0) {
            try {
                serverRef.leaveGroup(c.getEmail(), gChosen.getId());
                messges.remove(gChosen.getName().replaceAll(" ", ""));
                setGroupList(c);
                friendName.setVisible(false);
                friendStatus.setVisible(false);
                friendImg.setVisible(false);
                infoMenu.setVisible(false);
                gChosen = null;
                groupList.getSelectionModel().select(-1);
            } catch (RemoteException ex) {
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    /**
     * the addFriendMethod opens a JoptionPane to take the variable of a string which represents the name of the group.
     * the method uses the server reference object and calls createGroup method giving the parameters of the current user's object and the name of the group.
     * the server via the reference serverRef. the method is called by the createGroup button.
     */

    @FXML
    private void createGroupMethod() {
        int i = -100;

        String namePane = JOptionPane.showInputDialog("Please write down the Group's name");
        try {
            i = serverRef.createGroup(namePane, c);
            VBox vbTemp = new VBox();
            vbTemp.setSpacing(5);
            messges.put(namePane.replaceAll(" ", ""), vbTemp);
            setGroupList(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * defines a new stage stage to build the scene of the group info. 
     * creates the stage scene controller and gives the new controller the object of the
     * chosen group. gives the the next scene the object of the serverref. gives
     * the the next scene the object of the current controller.
     *
     * @param e
     * @see a new stage containing the group info and is given some control.
     */
    @FXML
    private void groupInfoMethod(ActionEvent e) {

        if (Platform.isFxApplicationThread()) {
            Stage stage;
            stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupInfo.fxml"));
                Parent root = loader.load();
                GroupInfoController controller = loader.getController();
                controller.setsServerRef(serverRef);
                controller.setG(choosenGroup);
                controller.setCmc(cmc);
                controller.setStage(stage);
                Scene scene = new Scene(root);
                stage.setTitle("Group Information");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("error in show friend Profile fxml from Main fxml");
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    Stage stage;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupInfo.fxml"));
                        Parent root = loader.load();
                        GroupInfoController controller = loader.getController();
                        controller.setsServerRef(serverRef);
                        controller.setG(choosenGroup);
                        controller.setCmc(cmc);
                        controller.setStage(stage);
                        Scene scene = new Scene(root);
                        stage.setTitle("Group Information");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("error in show friend Profile fxml from Main fxml");
                        Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }

    }

/**
 * this method for selecting  friend to start chat with him when the user click on it
 */
    @FXML
    private void showFriend() {
        if (friendList.getSelectionModel().getSelectedItem() != null) {
            int it = 0;
            System.out.println(friendList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem()));
            it = friendList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem());
            cChosen = cs.get(it);
            friendList.getSelectionModel().getSelectedItem().setFont(Font.font(12));
            friendStatus.setVisible(false);
            friendName.setVisible(false);
            choosenClient = cChosen;
            choosenFlage = 0;
            scrollPane.setContent(messges.get(choosenClient.getEmail().replaceAll("\\s+", "")));
            messges.get(choosenClient.getEmail().replaceAll("\\s+", "")).prefWidthProperty().bind(scrollPane.widthProperty().multiply(.98));
            choosenvb = choosenClient.getEmail().replaceAll("\\s+", "");
            if (cChosen.getName() != null) {
                friendName.setText(cChosen.getName().replace("  ", ""));
                friendName.setVisible(true);
            }
            if (cChosen.getStatus() != null) {
                friendStatus.setText(cChosen.getStatus().replace("  ", ""));
                friendStatus.setVisible(true);
            }
            friendImg.setVisible(true);
            infoMenu.setVisible(true);
            unfriend.setVisible(true);
            friendProfile.setVisible(true);
            gInfo.setVisible(false);
            leaveG.setVisible(false);
            receiversEmails.clear();
            receiversEmails.add(cChosen.getEmail().replaceAll("\\s+", ""));
        }
        friendList.getSelectionModel().select(-1);

    }
/**
 * this method for selecting the group to start chat with it when the user click on it
 */
    @FXML
    private void showGroup() {
        if (groupList.getSelectionModel().getSelectedItem() != null) {
            int it = 1;
            System.out.println(groupList.getItems().indexOf(groupList.getSelectionModel().getSelectedItem()));
            it = groupList.getItems().indexOf(groupList.getSelectionModel().getSelectedItem());
            gChosen = gs.get(it);
            groupList.getSelectionModel().getSelectedItem().setFont(Font.font(12));
            choosenGroup = gChosen;
            choosenFlage = 1;
            vb.getChildren().clear();
            scrollPane.setContent(messges.get(choosenGroup.getName().replaceAll(" ", "")));
            messges.get(choosenGroup.getName().replaceAll("\\s+", "")).prefWidthProperty().bind(scrollPane.widthProperty().multiply(.98));
            choosenvb = choosenGroup.getName().replaceAll("\\s+", "");
            if (gChosen.getName() != null) {
                friendName.setText(gChosen.getName().replace("  ", ""));
                friendName.setVisible(true);
            }
            friendStatus.setText("");
            friendStatus.setVisible(true);
            friendImg.setVisible(false);
            friendProfile.setVisible(false);
            infoMenu.setVisible(true);
            unfriend.setVisible(false);
            gInfo.setVisible(true);
            leaveG.setVisible(true);
            receiversEmails.clear();
            ArrayList<Client> clients = new ArrayList<Client>();
            try {
                clients = serverRef.reteriveClients(gChosen.getId().replaceAll("\\s+", ""));
                for (int i = 0; i < clients.size(); ++i) {
                    receiversEmails.add(clients.get(i).getEmail().replaceAll("\\s+", ""));

                }
            } catch (RemoteException ex) {
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        groupList.getSelectionModel().select(-1);
    }

    public void setsServerRef(BusinessInterface serverRef) {
        this.serverRef = serverRef;
    }

    /**
     *defines a new stage stage to build the scene of the owner client profile. 
     * Gives the the next scene the object of the server reference object the client logged in.
     */

    @FXML
    public void viewProfile() {

        if (Platform.isFxApplicationThread()) {
            Stage stage;
            stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMyProfile.fxml"));
                Parent root = loader.load();
                FXMLMyProfileController controller = loader.getController();
                controller.setsServerRef(serverRef);
                controller.setClient(c);
                Scene scene = new Scene(root);
                stage.setTitle("My Profile");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("error in show Profile fxml from Main fxml");
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    Stage stage;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMyProfile.fxml"));
                        Parent root = loader.load();
                        FXMLMyProfileController controller = loader.getController();
                        controller.setsServerRef(serverRef);
                        controller.setClient(c);
                        Scene scene = new Scene(root);
                        stage.setTitle("My Profile");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("error in show Profile fxml from Main fxml");
                        Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }

    /**
     * this method for printing sending message on the sender side
     * and send it to the receiver
     * @param ae 
     */
    @FXML
    public void onEnter(ActionEvent ae) {
        if (receiversEmails.size() != 0) {
            if (Platform.isFxApplicationThread()) {
                try {
                    Message msg = new Message();
                    msg.setMsg(textField.getText());
                    msg.setFSize(Integer.parseInt((String) fontSize.getValue()));
                    msg.setColor(String.format("#%02X%02X%02X", ((int) colorPicker.getValue().getRed()) * 255, ((int) colorPicker.getValue().getGreen()) * 255, ((int) colorPicker.getValue().getBlue()) * 255));
                    msg.setFont((String) font.getValue());
                    if (myold != null) {
                        myold.setVisible(false);
                    }
                    HBox temp = new HBox();
                    TextFlow textFlow = new TextFlow();
                    textFlow.setStyle("-fx-background-color:#00a099;-fx-background-radius: 15,15,15,15;");
                    textFlow.setMaxWidth(360);
                    Text t = new Text(msg.getMsg());
                    t.setFill(Color.web(msg.getColor()));
                    t.setFont(new Font(msg.getFont(), msg.getFSize()));
                    textFlow.getChildren().add(t);
                    ImageView i = new ImageView();
                    myold = i;
                    i.setImage(myImg);
                    i.setFitHeight(29);
                    i.setFitWidth(31);
                    i.getStyleClass().add("chatimg");
                    final Circle clip = new Circle(15, 15, 15);
                    i.setClip(clip);
                    temp.getChildren().add(textFlow);
                    temp.getChildren().add(i);
                    temp.setAlignment(Pos.BASELINE_RIGHT);
                    if (flage == 0) {
                        TextFlow textFlow2 = new TextFlow();
                        Text sname = new Text(c.getName().replace("  ", ""));
                        textFlow2.getChildren().add(sname);
                        HBox temp2 = new HBox();
                        temp2.getChildren().add(textFlow2);
                        temp2.setAlignment(Pos.BASELINE_RIGHT);
                        //vb.getChildren().add(temp2); 
                        if (choosenFlage != -1) {
                            if (choosenFlage == 0) {
                                messges.get(choosenClient.getEmail().replaceAll("\\s+", "")).getChildren().add(temp2);
                            } else {
                                messges.get(choosenGroup.getName().replaceAll("\\s+", "")).getChildren().add(temp2);
                            }
                        }
                        flage = 1;
                    }
                    if (choosenFlage != -1) {
                        if (choosenFlage == 0) {
                            messges.get(choosenClient.getEmail().replaceAll("\\s+", "")).getChildren().add(temp);
                            serverRef.tellOthers(msg, c.getEmail().replaceAll("\\s+", ""), receiversEmails, 0, c.getEmail().replaceAll("\\s+", ""));

                        } else {
                            messges.get(choosenGroup.getName().replaceAll("\\s+", "")).getChildren().add(temp);
                            serverRef.tellOthers(msg, c.getEmail().replaceAll("\\s+", ""), receiversEmails, 1, choosenGroup.getName().replaceAll(" ", ""));

                        }
                    }

                    //vb.getChildren().add(temp);                
                    textField.setText("");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            if (myold != null) {
                                myold.setVisible(false);
                            }
                            Message msg = new Message();
                            msg.setMsg(textField.getText());
                            msg.setFSize(Integer.parseInt((String) fontSize.getValue()));
                            msg.setColor(String.format("#%02X%02X%02X", ((int) colorPicker.getValue().getRed()) * 255, ((int) colorPicker.getValue().getGreen()) * 255, ((int) colorPicker.getValue().getBlue()) * 255));
                            msg.setFont((String) font.getValue());
                            HBox temp = new HBox();
                            TextFlow textFlow = new TextFlow();
                            textFlow.setStyle("-fx-background-color:#00a099;-fx-background-radius: 15,15,15,15;");
                            textFlow.setMaxWidth(360);
                            Text t = new Text(msg.getMsg());
                            t.setFill(Color.web(msg.getColor()));
                            t.setFont(new Font(msg.getFont(), msg.getFSize()));
                            textFlow.getChildren().add(t);
                            ImageView i = new ImageView();
                            myold = i;
                            i.setImage(myImg);
                            i.setFitHeight(29);
                            i.setFitWidth(31);
                            i.getStyleClass().add("chatimg");
                            final Circle clip = new Circle(15, 15, 15);
                            i.setClip(clip);
                            temp.getChildren().add(textFlow);
                            temp.getChildren().add(i);
                            temp.setAlignment(Pos.BASELINE_RIGHT);
                            if (flage == 0) {
                                TextFlow textFlow3 = new TextFlow();
                                Text sname = new Text(c.getName().replace("  ", ""));
                                textFlow3.getChildren().add(sname);

                                HBox temp2 = new HBox();
                                temp2.getChildren().add(textFlow3);
                                temp2.setAlignment(Pos.BASELINE_RIGHT);
                                //vb.getChildren().add(temp2);   
                                if (choosenFlage != -1) {
                                    if (choosenFlage == 0) {

                                        messges.get(choosenClient.getEmail().replaceAll("\\s+", "")).getChildren().add(temp2);
                                    } else {

                                        messges.get(choosenGroup.getName().replaceAll("\\s+", "")).getChildren().add(temp2);
                                    }
                                }
                                flage = 1;
                            }
                            //vb.getChildren().add(temp);   
                            if (choosenFlage != -1) {
                                if (choosenFlage == 0) {
                                    messges.get(choosenClient.getEmail().replaceAll("\\s+", "")).getChildren().add(temp);
                                    serverRef.tellOthers(msg, c.getEmail().replaceAll("\\s+", ""), receiversEmails, 0, c.getEmail().replaceAll(" ", ""));

                                } else {
                                    messges.get(choosenGroup.getName().replaceAll("\\s+", "")).getChildren().add(temp);
                                    serverRef.tellOthers(msg, c.getEmail().replaceAll("\\s+", ""), receiversEmails, 1, choosenGroup.getName().replaceAll(" ", ""));

                                }
                            }

                            textField.setText("");
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    }

                });

            }
        }

    }

    /**
     * this is method for printing messages the user received
     * @param s
     * @param name
     * @param sendFlage
     * @param strFlage 
     */
    public void print(Message s, String name, int sendFlage, String strFlage) {

        if (Platform.isFxApplicationThread()) {
            if (old != null) {
                old.setVisible(false);
            }

            if (sendFlage == 0) {
                if (choosenvb.equals(strFlage.replaceAll("\\s+", ""))) {

                } else {

                    ObservableList<Text> topics;
                    topics = friendList.getItems();
                    int listCounter = 0;
                    for (Text each : topics) {
                        Client client = null;
                        try {
                            client = serverRef.retreiveClient(strFlage.replaceAll("\\s+", ""));
                        } catch (RemoteException ex) {
                            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String namex = client.getName().replaceAll("\\s+", "");

                        if (each.getText().replaceAll("\\s+", "").toString().equals(namex.replaceAll("\\s+", ""))) {

                            break;
                        } else {
                            listCounter++;
                        }
                    }
                    int it = friendList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem());
                    friendList.getSelectionModel().select(listCounter);
                    friendList.getSelectionModel().getSelectedItem().setFont(Font.font(16));
                    friendList.getSelectionModel().select(it);

                }
            } else {
                if (choosenvb.equals(strFlage.replaceAll("\\s+", ""))) {

                } else {

                    ObservableList<Text> topics;
                    topics = groupList.getItems();
                    int listCounter = 0;
                    for (Text each : topics) {
                        if (each.getText().replaceAll("\\s+", "").toString().equals(strFlage.replaceAll("\\s+", ""))) {
                            break;
                        } else {
                            listCounter++;
                        }
                    }
                    int it = groupList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem());
                    groupList.getSelectionModel().select(listCounter);
                    groupList.getSelectionModel().getSelectedItem().setFont(Font.font(16));
                    groupList.getSelectionModel().select(it);

                }
            }

            HBox temp = new HBox();
            TextFlow textFlow = new TextFlow();
            textFlow.setStyle("-fx-background-color:#015655;-fx-background-radius: 15,15,15,15;");
            textFlow.setMaxWidth(360);
            Text t = new Text(s.getMsg());
            t.setFill(Color.web(s.getColor()));
            t.setFont(new Font(s.getFont(), s.getFSize()));
            textFlow.getChildren().add(t);
            ImageView ii = new ImageView();
            old = ii;
            ii.setFitHeight(29);
            ii.setFitWidth(31);
            ii.getStyleClass().add("chatimg");
            ii.setImage(friendimg);
            final Circle clip = new Circle(15, 15, 15);
            ii.setClip(clip);
            temp.getChildren().add(ii);
            temp.getChildren().add(textFlow);
            if (!lastTypedName.equals(name)) {
                HBox hh = new HBox();
                TextFlow textFlow2 = new TextFlow();
                Text sname = null;
                try {
                    sname = new Text(serverRef.retreiveClient(name.replaceAll(" ", "")).getName().replaceAll(" ", ""));
                } catch (RemoteException ex) {
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
                textFlow2.getChildren().add(sname);
                hh.getChildren().add(textFlow2);

                if (sendFlage == 0) {

                    messges.get(strFlage).getChildren().add(hh);
                } else {
                    messges.get(strFlage).getChildren().add(hh);
                }

                //vb.getChildren().add(sname);
                lastTypedName = name;
            }

            if (sendFlage == 0) {
                messges.get(strFlage).getChildren().add(temp);
            } else {
                messges.get(strFlage).getChildren().add(temp);
            }

            //vb.getChildren().add(temp);
            flage = 0;
        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    if (old != null) {
                        old.setVisible(false);
                    }

                    if (sendFlage == 0) {
                        if (choosenvb.equals(strFlage.replaceAll("\\s+", ""))) {

                        } else {

                            ObservableList<Text> topics;
                            topics = friendList.getItems();
                            int listCounter = 0;
                            for (Text each : topics) {
                                Client client = null;
                                try {
                                    client = serverRef.retreiveClient(strFlage.replaceAll("\\s+", ""));
                                } catch (RemoteException ex) {
                                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                String namex = client.getName().replaceAll("\\s+", "");

                                if (each.getText().replaceAll("\\s+", "").toString().equals(namex.replaceAll("\\s+", ""))) {

                                    break;
                                } else {
                                    listCounter++;
                                }
                            }
                            int it = friendList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem());
                            friendList.getSelectionModel().select(listCounter);
                            friendList.getSelectionModel().getSelectedItem().setFont(Font.font(16));
                            friendList.getSelectionModel().select(it);

                        }
                    } else {
                        if (choosenvb.equals(strFlage.replaceAll("\\s+", ""))) {

                        } else {

                            ObservableList<Text> topics;
                            topics = groupList.getItems();
                            int listCounter = 0;
                            for (Text each : topics) {
                                if (each.getText().replaceAll("\\s+", "").toString().equals(strFlage.replaceAll("\\s+", ""))) {
                                    break;
                                } else {
                                    listCounter++;
                                }
                            }
                            int it = groupList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem());
                            groupList.getSelectionModel().select(listCounter);
                            groupList.getSelectionModel().getSelectedItem().setFont(Font.font(16));
                            groupList.getSelectionModel().select(it);

                        }
                    }

                    HBox temp = new HBox();
                    TextFlow textFlow = new TextFlow();
                    textFlow.setStyle("-fx-background-color:#015655;-fx-background-radius: 15,15,15,15;");
                    textFlow.setMaxWidth(360);
                    Text t = new Text(s.getMsg());
                    t.setFill(Color.web(s.getColor()));
                    t.setFont(new Font(s.getFont(), s.getFSize()));
                    textFlow.getChildren().add(t);
                    ImageView ii = new ImageView();
                    old = ii;
                    ii.setFitHeight(29);
                    ii.setFitWidth(31);
                    ii.getStyleClass().add("chatimg");
                    ii.setImage(friendimg);
                    final Circle clip = new Circle(15, 15, 15);
                    ii.setClip(clip);
                    temp.getChildren().add(ii);
                    temp.getChildren().add(textFlow);
                    if (!lastTypedName.equals(name)) {
                        HBox hh = new HBox();
                        TextFlow textFlow2 = new TextFlow();
                        Text sname = null;
                        try {
                            sname = new Text(serverRef.retreiveClient(name.replaceAll(" ", "")).getName().replaceAll(" ", ""));
                        } catch (RemoteException ex) {
                            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        textFlow2.getChildren().add(sname);
                        hh.getChildren().add(textFlow2);

                        if (sendFlage == 0) {

                            messges.get(strFlage).getChildren().add(hh);
                        } else {
                            messges.get(strFlage).getChildren().add(hh);
                        }
                        //vb.getChildren().add(sname);
                        lastTypedName = name;
                    }

                    if (sendFlage == 0) {
                        messges.get(strFlage.replaceAll("\\s+", "")).getChildren().add(temp);
                    } else {
                        messges.get(strFlage.replaceAll("\\s+", "")).getChildren().add(temp);
                    }
                    flage = 0;
                }

            });
        }

    }

    /**
     * defines a new stage stage to build the scene of the friend info. 
     * creates the stage scene controller and gives the new controller the object of the
     * chosen friend. 
     * Gives the the next scene the object of the server reference object.
     **************************************************************************************
     * a new stage containing the friend info and is given some control.
     */

    @FXML
    public void showFriendProfile() {

        if (Platform.isFxApplicationThread()) {
            Stage stage;
            stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFriendProfile.fxml"));
                Parent root = loader.load();
                FXMLGroupChatInfoController controller = loader.getController();
                controller.setsServerRef(serverRef);
                controller.setClient(choosenClient);
                Scene scene = new Scene(root);
                stage.setTitle(choosenClient.getName().replaceAll(" ", "") + " Profile");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("error in show friend Profile fxml from Main fxml");
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    Stage stage;
                    stage = new Stage();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFriendProfile.fxml"));
                        Parent root = loader.load();
                        FXMLGroupChatInfoController controller = loader.getController();
                        controller.setsServerRef(serverRef);
                        controller.setClient(choosenClient);
                        Scene scene = new Scene(root);
                        stage.setTitle(choosenClient.getName().replaceAll(" ", "") + " Profile");
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException ex) {
                        System.out.println("error in show friend Profile fxml from Main fxml");
                        Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }

    /**
     * resets the groupList listview.
     * sets the chosengroup variable to null.
     * sets the selection of the item in the group list to -.
     * sets the labels containing the group name, image and status to false and the info menu which holds the menu items group info and leave group.
     */
    public void refreshAfterRemoveGroup() {

        setGroupList(c);
        friendName.setVisible(false);
        friendStatus.setVisible(false);
        friendImg.setVisible(false);
        infoMenu.setVisible(false);
        gChosen = null;
        groupList.getSelectionModel().select(-1);

    }
    /**
     * creates an object of the XMLbuilder class giving it the the vbox shown whether it is group or individual messaging
     */
    @FXML
    void saveChat() {
        XMLBuilder builder = new XMLBuilder();

        if (choosenFlage != -1) {
            if (choosenFlage == 0) {
                builder.buildXML(messges.get(choosenClient.getEmail().replaceAll("\\s+", "")));
            } else {
                builder.buildXML(messges.get(choosenGroup.getName().replaceAll("\\s+", "")));

            }
        }
    }
    /**
     * this is method for sending file
     */
    @FXML
    public void sendFile() {
        if (cChosen != null) {
//        try {
//            System.out.println(serverRef.sendIP("abdalla@saam.com"));
//        } catch (RemoteException ex) {
//            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
            final FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);
            Stage stage = new Stage();

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                openFile(file);
            }
            FilePath = file.getPath();
            fileName = file.getName();
            fileSize = (int) file.length() / 5000000;
            final Button openMultipleButton = new Button("Open File ...");
            openMultipleButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    configureFileChooser(fileChooser);
                    List<File> list
                            = fileChooser.showOpenMultipleDialog(stage);
                    if (list != null) {
                        for (File file : list) {
                            openFile(file);
                            FilePath = file.getPath();
                            fileName = file.getName();
                            fileSize = (int) file.length() / 5000000;
                        }
                    }
                }
            });
            System.out.println(FilePath);
            FilePath = file.getPath();
            fileName = file.getName();
            fileSize = (int) file.length() / 5000000;
            Button sendFileButton = new Button("Send");
            sendFileButton.setDefaultButton(true);
            sendFileButton.setOnAction(
                    new EventHandler<ActionEvent>() {

                @Override
                public void handle(final ActionEvent e) {
                    int check = -1;
                    TextField waiting = new TextField("waiting...");
                    final GridPane inputGridPane = new GridPane();
                    // GridPane.setConstraints(upload, 0, 0);
                    GridPane.setConstraints(waiting, 1, 0);
                    inputGridPane.getChildren().clear();
                    inputGridPane.getChildren().addAll(waiting);
                    final Pane rootGroup = new VBox(12);
                    rootGroup.getChildren().addAll(inputGridPane);

                    stage.setScene(new Scene(rootGroup));

                    stage.show();
                    watingStage = stage;
                    //////
                    try {
                        System.out.println("check");

                        serverRef.shakeOne(c.getEmail(), cChosen.getEmail());

                        System.out.println(c.getEmail() + "  + " + cChosen.getEmail());
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //////
//                if(check==0){
//                 new SendFile(6555, file.getPath());
//                try {
//                    serverRef.sendIP(getMyIp(),c.getEmail(),file.getName());
//                } catch (RemoteException ex) {
//                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                }
                    /*EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {

                                if(true){
                           
                            //       stage.close();
                            try {
                                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                            }

                            ProgressBar frame = new ProgressBar();
                            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                            frame.pack();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                            frame.iterate();
                        }}
                    }
                });*/

                }
            });
            final GridPane inputGridPane = new GridPane();

            // GridPane.setConstraints(upload, 0, 0);
            GridPane.setConstraints(openMultipleButton, 1, 0);
            inputGridPane.setHgap(6);
            inputGridPane.setVgap(6);
            inputGridPane.getChildren().addAll(openMultipleButton, sendFileButton);
            final Pane rootGroup = new VBox(12);
            rootGroup.getChildren().addAll(inputGridPane);

            stage.setScene(new Scene(rootGroup));
            stage.show();
        }
    }

    public void sendBufferedFile() {
        //  watingStage.close();
        System.out.println("sendBufferedFile" + FilePath);
        //  watingStage.close();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SendFile(8555, FilePath);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

            }
        });
        try {
            System.out.println(getMyIp() + "sendBufferedFile" + c.getEmail() + cChosen.getEmail());
            serverRef.shakeThree(getMyIp(), fileName, c.getEmail(), cChosen.getEmail(), fileSize);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getMyIp() {
        String hostName = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostName;
    }

    public String acceptFile(String email) {
        int i = JOptionPane.showConfirmDialog(null, "Accept file from " + email, "Recieve file", YES_NO_OPTION);
        if (i == 0) {
            try {
                System.out.println("acceptFile");
                serverRef.shakeTwo(email, c.getEmail(), getMyIp());

            } catch (RemoteException ex) {
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return getMyIp();
    }

    @FXML
    public void receiveFile(String ip, String filename, int fileSize) {
        System.out.println("../../receiveFile" + filename + "IP : " + ip + "fileSize " + fileSize);

        //   watingStage.close();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReceiveFile(ip, 8555, filename);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                new ReceiveFile(ip, 8555, filename);
            }
        });

    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {

            System.out.println(" openFilefail");

        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    /**
     * defines a new stage stage to build the scene of the login. 
     * Gives the the next scene the object of the serverref.
     * set the user status to offline and calls the setMyStatus method.
     **************************************************************************************
     * @see a new stage of the login scene.
     */
    @FXML
    private void logOut() {
        try {
            serverRef.unRegister(clientRef, c.getEmail());

            if (Platform.isFxApplicationThread()) {
                Stage stage;
                stage = Main.getPrimaryStage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                    Parent root = loader.load();
                    LoginController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    Scene scene = new Scene(root);
                    stage.setTitle("Login Page");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show Login fxml from Main fxml");
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                userstatus.setValue("Offline");
                setMyStatus();

            } else {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        Stage stage;
                        stage = Main.getPrimaryStage();
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                            Parent root = loader.load();
                            LoginController controller = loader.getController();
                            controller.setsServerRef(serverRef);
                            Scene scene = new Scene(root);
                            stage.setTitle("Login Page");
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            System.out.println("error in show Login fxml from Main fxml");
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        userstatus.setValue("Offline");
                        setMyStatus();
                    }
                });
            }

        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * **************************************************************************************************
     * @param clientRef 
     */
    public void setClientRef(ClientBusinessInterface clientRef) {
        this.clientRef = clientRef;
    }
/**
 * sets the initial value state of the friend list of the client whether is it green for the online and the black for the offline.
 */
    public void setFriendsStatus() {
        for (Text s : friendList.getItems()) { //5od di copy t7t
            System.out.println("gab text");
            for (Client temp : cs) {
                System.out.println("aheh");
                if (temp.getName().contains(s.getText())) {
                    System.out.println("true");
                    try {
                        int check = serverRef.checkOnline(temp.getEmail());
                        System.out.println(check);
                        if (check == 0) {
                            s.setFill(javafx.scene.paint.Color.GREEN);
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * gets the user status from the userstatus ComboBox and calls the server reference object method setUserState with given parameters the owners email and arrayList of the friends and the new state.
     */
    @FXML
    void setMyStatus() {
        String state = (String) userstatus.getValue();
        System.out.println(state);
        try {
            System.out.println(cs.size());
            serverRef.setUserState(c.getEmail(), cs, state);
            System.out.println(cs.size());
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * the function sets the color of the friend in the friendList according to friend's state.
     * it sets the Online to green, away to yellow, red to busy and black as default.
     * @param email represents the email of the friend to be looped on to define the related item in the listView.
     * @param state string containing the state of the email.
     *  the friend whose email is received his color will change according to the incoming status.
     */
    public void setFriendStatus(String email, String state) {
        System.out.println(cs.size());
        for (Client x : cs) {

            System.out.println(x.getEmail() + " " + email);
            if (x.getEmail().contains(email)) {
                System.out.println("donia saleema");
                for (Text s : friendList.getItems()) {
                    if (x.getName().contains(s.getText())) {
                        System.out.println("text saleem");
                        switch (state) {
                            case "Online":
                                s.setFill(Color.GREEN);
                                break;
                            case "Away":
                                s.setFill(Color.YELLOW);
                                break;
                            case "Busy":
                                s.setFill(Color.RED);
                                break;
                            default:
                                s.setFill(Color.BLACK);
                                break;
                        }
                    }
                }
            }
        }
    }

}
