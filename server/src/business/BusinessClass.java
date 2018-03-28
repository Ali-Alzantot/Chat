/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import DataAccessLayer.DAO.ClientImpl;
import DataAccessLayer.DAO.ContactImpl;
import DataAccessLayer.DAO.GroupsImpl;
import DataAccessLayer.DAO.HasImpl;
import DataAccessLayer.DAO.RequestsImpl;
import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.DTO.Groups;
import DataAccessLayer.DTO.Has;
import DataAccessLayer.DTO.Message;
import DataAccessLayer.DTO.Requests;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import java.awt.AWTException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ali Alzantot
 */
public class BusinessClass extends UnicastRemoteObject implements BusinessInterface {

   ClientImpl ci = new ClientImpl();
    GroupsImpl gi = new GroupsImpl();
    ContactImpl coni = new ContactImpl();
    RequestsImpl ri = new RequestsImpl();
    HasImpl hi = new HasImpl();
    public static HashMap<ClientBusinessInterface,String> onlineUsers=new HashMap<ClientBusinessInterface,String>();
    public BusinessClass() throws RemoteException {

    }
    
    
    @Override
    public int createClient(Client obj) throws RemoteException {
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        int ret = ci.create(obj, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public Client retreiveByEmailAndPass(String email, String pass) {
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        Client c = ci.retreiveByEmailAndPass(email, pass, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     @Override
    public ArrayList<String> retreiveRequests(Client obj) {
        ArrayList<String> sL = new ArrayList<String>();
        ArrayList<Requests> reqL = new ArrayList<Requests>();
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        reqL=ri.retreiveAll(obj.getEmail(), con);
       
        for(Requests x : reqL){
            sL.add(x.getEmailSender());
        }
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sL;
    }

    @Override
    public ArrayList<Client> retreiveFriends(Client obj) {
        ArrayList<Client> cL = new ArrayList<Client>();
        ArrayList<Contact> cont = new ArrayList<Contact>();
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        cont=coni.retreiveall(obj, con);
        for(Contact x : cont){
            if(!x.getContactEmail().contains(obj.getEmail())){
                cL.add(ci.retreive(x.getContactEmail(), con));
                
                
                
            }
            else{
                cL.add(ci.retreive(x.getEmail(), con));
            }
        }
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cL;
    }

    @Override
    public ArrayList<Groups> retreiveGroups(Client obj) {
        ArrayList<Groups> gL = new ArrayList<Groups>();
        ArrayList<Has> hL = new ArrayList<Has>();
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        hL=hi.retreiveAll(obj.getEmail(), con);
        
        for(Has x:hL){
            gL.add(gi.retreive(x.getId(), con));
            
        }
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gL;
    }

    @Override
    public int acceptRequest(String senderMail, String receiverMail) throws RemoteException {
        int ret=-1;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        Client obj1=ci.retreive(senderMail, con);
        Client obj2=ci.retreive(receiverMail, con);
        coni.create(obj1, obj2, con);
        Requests reqObj = ri.retreive(senderMail, receiverMail, con);
        ret=ri.delete(reqObj, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public int declineRequest(String senderMail, String receiverMail) throws RemoteException {
        int ret=-1;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        Requests reqObj = ri.retreive(senderMail, receiverMail, con);
        ret=ri.delete(reqObj, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public int addFriend(String senderMail, String receiverMail) throws RemoteException {
         int ret=-1;
         Requests reqObj = new Requests();
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        Client obj1=ci.retreive(senderMail, con);
        Client obj2=ci.retreive(receiverMail, con);
        reqObj.setEmailSender(obj1.getEmail());
        reqObj.setEmailReciever(obj2.getEmail());
        ret = ri.create(reqObj, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public int addToGroup(String email, String id) throws RemoteException {
        int ret=-1;
         Has hasObj = new Has();
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        email= ci.retreive(email, con).getEmail();
        id= gi.retreive(id, con).getName();
        hasObj.setEmail(email);
        hasObj.setId(id);
        ret = hi.create(hasObj, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    @Override
    public int createGroup(String name,Client c) throws RemoteException {
        Groups g=new Groups();
        Has h = new Has();
        int i =-1;
        String temp="0";
        Integer z = new Integer(0);
         ArrayList<Groups> gL = new ArrayList<Groups>();
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        gL=gi.retreiveAll(con);
        for(Groups x:gL){
            
            if(Integer.parseInt(x.getId().replaceAll(" ", ""))>=Integer.parseInt(temp)){
                temp=x.getId().replaceAll(" ", "");
                
            }
        }
        z=(Integer.parseInt(temp))+1;
        
        g.setId(z.toString());
        g.setName(name);
        i=gi.create(g, con);
        h.setEmail(c.getEmail());
        h.setId(g.getId());
        i=hi.create(h, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    @Override
    public int unfriend(String email1, String email2) throws RemoteException {
        Contact c=new Contact();
        int i =-1;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        Client obj1 = ci.retreive(email1, con);
        Client obj2 = ci.retreive(email2, con);
        i=coni.delete(obj1, obj2, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }

    @Override
    public int leaveGroup(String email, String id) throws RemoteException {
        int i =-1;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        
        Has obj = new Has();
        obj=hi.retreive(email,id, con);
        i=hi.delete(obj, con);
        
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
    
    @Override
    public void tellOthers(Message msg, String senderEmail, ArrayList<String> receiversEmails,int sendFlage,String strFlage) throws RemoteException {
     DataBaseManager managerObj = new DataBaseManager();
     Connection con = managerObj.getCon();
      
        // Get a set of the entries
          Set set = onlineUsers.entrySet();
          // Get an iterator
          Iterator i = set.iterator();

          // Display elements
          while(i.hasNext()) {
             Map.Entry me = (Map.Entry)i.next();
                try{
                   ClientBusinessInterface clientRef=(ClientBusinessInterface) me.getKey();
                   if(!me.getValue().equals(senderEmail)){
                       
                       for (int j=0;j<receiversEmails.size();++j){
                           if(me.getValue().equals(receiversEmails.get(j))){
                               ClientImpl ci=new ClientImpl();
                                 Client c;
                                 c=ci.retreive(receiversEmails.get(j), con);
                                 clientRef.receive(msg,senderEmail.replaceAll("\\s+",""),sendFlage,strFlage);
                                
                                
                           }
                           
                        }       
               
               
               }
            }
            catch(RemoteException ex){
                System.out.println("can't send MSG");
                ex.printStackTrace();
            } 
          }       
        

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void register(ClientBusinessInterface clientRef, String email) throws RemoteException {
        System.out.println(email);
        onlineUsers.put(clientRef,email);
        
                        //////////   For ONLINE Announcement
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        BusinessClass in = new BusinessClass();
        ArrayList<Client> Friends;

        Client ch = ci.retreive(email, con);

       Friends=in.retreiveFriends(ch);

        in.tellOthersClientRegisteration(email, Friends);

        
        System.out.println("client add succefully");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void unRegister(ClientBusinessInterface clientRef,String email) throws RemoteException {
        onlineUsers.remove(clientRef);
        System.out.println("client removed succefully");
      
        //////////   For OFFLINE Announcement
        
            
                        //////////   For OFLINE Announcement
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        BusinessClass in = new BusinessClass();
        ArrayList<Client> Friends;

        Client ch = ci.retreive(email, con);

        Friends=in.retreiveFriends(ch);

        in.tellOthersClientUNREG(email, Friends);    
    }

    @Override
    public ArrayList<Client> reteriveClients(String id) throws RemoteException {
      DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
       ArrayList<Client> clients = gi.reteriveClients(id, con);
                
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients;
    }
    
    @Override
    public Client retreiveClient(String email) throws RemoteException {
      DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        Client c = ci.retreive(email,con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in getting client by email and password inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;  
    }

    @Override
    public ArrayList<Has> retreiveGroupRows(String id) throws RemoteException {
      DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
       ArrayList<Has> clients = hi.retreiveGroupRows(id,con);
                
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clients; 
    }

    
     @Override
        public Groups reteriveDeleteGroups(String id)throws RemoteException{
              DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
       Groups g= gi.retreive(id, con);
                
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
        }

    @Override
    public int DeleteGroups(Groups g) throws RemoteException {
     int i=0;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        i= gi.delete(g,con);
        System.out.println(i);        
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
        
        
    }
    
    @Override
    public int create(Has obj)throws RemoteException {
       int i=0;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        i= hi.create(obj,con);
        System.out.println(i);        
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;   
        
    }

    @Override
    public Has retreive(String email) throws RemoteException {
        Has h;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        h= hi.retreive(email,con);
             
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return h;  
    }

    @Override
    public int delete(Has obj) throws RemoteException {
       int i=0;
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        i= hi.delete(obj,con);
        System.out.println("delet elhas");
        System.out.println(obj.getEmail()+obj.getId());
        //System.out.println(i);        
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;    
    }

    @Override
    public int update(Client obj) throws RemoteException {
        System.out.println("dh el business clASS    ");
         DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        int ret = ci.update(obj, con);
        try {
            con.close();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            System.out.println("error in closing the connection in method create inside business class");
            Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
        
        
        
    }

    @Override
    public void tellOthersAdmin(String msg) throws RemoteException {


        
        // Get a set of the entries
       // Get a set of the entries
        Set set = onlineUsers.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // Display elements
        
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            try {
                ClientBusinessInterface clientRef = (ClientBusinessInterface) me.getKey();
                try {
                    //if (!me.getValue().equals(senderEmail)) {
                    clientRef.displayTray(msg);
                } catch (AWTException ex) {
                    Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            } catch (RemoteException ex) {
                System.out.println("can't send MSG");
                ex.printStackTrace();
            }
        }

    }
    
   @Override
    public void tellOthersClientRegisteration(String mail, ArrayList<Client> clientFriends) throws RemoteException {
 
        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        // System.out.println("Message Reveived " + msg);
        // Get a set of the entries
        for(int ii=0;ii<clientFriends.size();++ii){
            Set set = onlineUsers.entrySet();
            // Get an iterator
            Iterator i = set.iterator();

            // Display elements
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                ClientBusinessInterface clientRef = (ClientBusinessInterface) me.getKey();
                if(clientFriends.get(ii).getEmail().replaceAll(" ", "").equals(me.getValue())&&!clientFriends.get(ii).getEmail().replaceAll(" ", "").equals(mail.replaceAll(" ", "")) ){
                    try {
                        clientRef.displayTrayToClients(mail);
                    } catch (AWTException ex) {
                        Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }

            }
        }

    }

    @Override
    public void tellOthersClientUNREG(String mail, ArrayList<Client> clientFriends) throws RemoteException {

        DataBaseManager managerObj = new DataBaseManager();
        Connection con = managerObj.getCon();
        // System.out.println("Message Reveived " + msg);
        // Get a set of the entries
        for(int ii=0;ii<clientFriends.size();++ii){
            Set set = onlineUsers.entrySet();
            // Get an iterator
            Iterator i = set.iterator();

            // Display elements
            while (i.hasNext()) {
                Map.Entry me = (Map.Entry) i.next();
                ClientBusinessInterface clientRef = (ClientBusinessInterface) me.getKey();
                if(clientFriends.get(ii).getEmail().replaceAll(" ", "").equals(me.getValue())&&!clientFriends.get(ii).getEmail().replaceAll(" ", "").equals(mail.replaceAll(" ", "")) ){
                    try {
                        clientRef.displayTrayToClientsOFFLINE(mail);
                    } catch (AWTException ex) {
                        Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(BusinessClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }

            }
        }
    }
   @Override
    public void sendIP(String ip,String email,String FileName) throws RemoteException {
        Set set = onlineUsers.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            ClientBusinessInterface clientRef=(ClientBusinessInterface) me.getKey();
            if(me.getValue().equals(email.replaceAll(" ",""))){
                clientRef.receiveFile(ip, email,234);
            }
        }
    }

    @Override
    public int shakeOne(String emailSender,String emailReciever) throws RemoteException {
        int s=-1;
emailSender.replaceAll(" ","");
emailReciever.replaceAll(" ","");
          Set set = onlineUsers.entrySet();
          // Get an iterator
          Iterator i = set.iterator();
      System.out.println("shakeOne"+i.hasNext()+"size ??:"+onlineUsers.size());
          // Display elements
          while(i.hasNext()) {
             Map.Entry me = (Map.Entry)i.next();
               System.out.println("AfftershakeOne");
                try{
                   ClientBusinessInterface clientRef=(ClientBusinessInterface) me.getKey();
                    emailSender.replaceAll(" ","");
                        emailReciever.replaceAll(" ","");
                        System.out.println("beforfile("+emailSender.replaceAll(" ","")+emailReciever.replaceAll(" ","")+")");
 
                           if(me.getValue().equals(emailReciever.replaceAll(" ",""))){
                          clientRef.acceptFile(emailSender);
                               System.out.println("file");
                           
                           
                          
               
               
               }
            }
            catch(RemoteException ex){
                System.out.println("can't send file");
                ex.printStackTrace();
            } 
          }  
        

        return s;
    }

    @Override
    public String shakeTwo(String emailSender, String emailReciever,String ip) throws RemoteException {
                String s=null;
            emailSender.replaceAll(" ","");
            emailReciever.replaceAll(" ","");
          Set set = onlineUsers.entrySet();
          // Get an iterator
          Iterator i = set.iterator();
      System.out.println("shakeTwo"+i.hasNext()+"size ??:"+onlineUsers.size());
          // Display elements
          while(i.hasNext()) {
             Map.Entry me = (Map.Entry)i.next();
               System.out.println("AfftershakeTwo");
                try{
                   ClientBusinessInterface clientRef=(ClientBusinessInterface) me.getKey();
                    emailSender.replaceAll(" ","");
                        emailReciever.replaceAll(" ","");
                        System.out.println("beforfile("+emailSender.replaceAll(" ","")+emailReciever.replaceAll(" ","")+")");
 
                           if(me.getValue().equals(emailSender.replaceAll(" ",""))){
                                   clientRef.sendBufferedFile(ip,emailSender);
                               System.out.println("file");              
               }
            }
            catch(RemoteException ex){
                System.out.println("can't send file");
                ex.printStackTrace();
            } 
          }  
        

        return s;
    }

    @Override
    public String shakeThree(String ip, String fileName,String emailSender, String emailReciever,int fileSize) throws RemoteException {
               String s=null;
          Set set = onlineUsers.entrySet();
          // Get an iterator
          Iterator i = set.iterator();
      System.out.println("shakeThree"+i.hasNext()+"size ??:"+onlineUsers.size());
          // Display elements
          while(i.hasNext()) {
             Map.Entry me = (Map.Entry)i.next();
               System.out.println("AfftershakeThree");
                try{
                   ClientBusinessInterface clientRef=(ClientBusinessInterface) me.getKey();
                    emailSender.replaceAll(" ","");
                        emailReciever.replaceAll(" ","");
                        System.out.println("beforfile("+emailSender.replaceAll(" ","")+emailReciever.replaceAll(" ","")+")");
 
                           if(me.getValue().equals(emailReciever.replaceAll(" ",""))){
                          clientRef.receiveFile(ip,fileName,fileSize);
                               System.out.println("file");
                           
                           
                          
               
               
               }
            }
            catch(RemoteException ex){
                System.out.println("can't send file");
                ex.printStackTrace();
            } 
          }  
        

        return s;
    }
    
    
 @Override
    public int checkOnline(String mail) throws RemoteException {
        int check=-1;
        Set set = onlineUsers.entrySet();
        // Get an iterator
        Iterator i = set.iterator();

        // Display elements
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
                if (mail.contains(me.getValue().toString())) {
                    check=0;
                }
        }
        return check;
    }

    @Override
    public void setUserState(String mail, ArrayList<Client> clientFriends, String state) throws RemoteException {
        Set set = onlineUsers.entrySet();
        // Get an iterator
        
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+clientFriends.size());
        // Display elements
        for(int x=0;x<clientFriends.size();x++){   
            Iterator i = set.iterator();
            while (i.hasNext()) {
                System.out.println("222222222222222222222222222222");
                Map.Entry me = (Map.Entry) i.next();
                if (me.getValue().toString().contains(clientFriends.get(x).getEmail().replaceAll(" ", ""))) {
                    System.out.println("ahoh elemail la2ah");
                   ClientBusinessInterface clientref =  (ClientBusinessInterface) me.getKey();
                   clientref.setFriendState(mail,state);
                }
            }
        }
    }
    

    
}
