//
//import DataAccessLayer.DAO.AdminImpl;
//import DataAccessLayer.DAO.ClientImpl;
//import DataAccessLayer.DAO.ContactImpl;
//import DataAccessLayer.DAO.GroupsImpl;
//import DataAccessLayer.DAO.HasImpl;
//import DataAccessLayer.DAO.RequestsImpl;
//import DataAccessLayer.DTO.Admin;
//import DataAccessLayer.DTO.Client;
//import DataAccessLayer.DTO.Contact;
//import DataAccessLayer.DTO.Groups;
//import DataAccessLayer.DTO.Has;
//import DataAccessLayer.DTO.Requests;
//import DataAccessLayer.DataBaseManager.DataBaseManager;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import sun.security.acl.GroupImpl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abdalla
 */
public class Test {

   // public static void main(String[] args) throws SQLException {



        
        
        
        /** start admin **/
        
        
            /** create 

            AdminImpl adi=new AdminImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Admin ad=new Admin();
            ad.setPassword("ali");
            ad.setUserName("alix");
            adi.create(ad, con);
            con.close();
            
            **/
            
            
            
             /** retreive

            AdminImpl adi=new AdminImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Admin ad;
            ad=adi.retreive("alix", con);
            System.out.println(ad.getPassword());
            con.close();
            
             **/             
            
            
            
            /** update 

            AdminImpl adi=new AdminImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Admin ad;
            ad=adi.retreive("alix", con);
            ad.setPassword("123");
            adi.update(ad, con);
            con.close();
            
             **/  
            

            
            /** delete 
            AdminImpl adi=new AdminImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Admin ad;
            ad=adi.retreive("alix", con);
            adi.delete(ad, con);
            con.close();

             **/              
        
        
        /** end of admin test **/
        
        
        /** start Client**/
        
        
            /** create 
             * 
            Client c=new Client();
            c.setCity("cc");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            c.setDate(java.sql.Date.valueOf("3017-09-09"));
            c.setName("65");
            c.setGender("ms");
            c.setPhone("24");
            c.setEmail("cc");
            c.setPassword("cc");
            c.setStatus("cc");
            c.setImage("cc");
            ClientImpl ci=new ClientImpl();
            DataBaseManager managerObj=new DataBaseManager();
            Connection con =managerObj.getCon();
            ci.create(c, con);
            con.close();
            
             **/
            
            
            
             /** retreive 
        
            ClientImpl ci=new ClientImpl();

            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();

            Client c;
            c=ci.retreive("cc", con);
            System.out.println(c.getDate());

            con.close();

            **/             
            
            
            
            /** update **/
/*
            ClientImpl ci=new ClientImpl();

            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();

            Client c;
            c=ci.retreive("abdalla@saam.com", con);
            c.setName("abdo");
            ci.update(c, con);
            con.commit();
            con.close();
            
            */

            
           

/*       ClientImpl ci=new ClientImpl();

DataBaseManager managerObj = new DataBaseManager();
Connection con = managerObj.getCon();

Client c;
c=ci.retreive("mayada@saam.com", con);
System.out.println(c.getEmail());
//c.setGender("female");
//ci.update(c, con);
ci.delete(c, con);
con.commit();
con.close();*/
                        
        
        
        /** end of client test **/        
        
        
        
       
           /** start contact**/
        
        
            /** create new contact

            ContactImpl coi=new ContactImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Contact co=new Contact();

            ClientImpl cli=new ClientImpl();

            Client cl1;
            cl1=cli.retreive("cc", con);
            
            Client cl2;
            cl2=cli.retreive("abv", con);
            coi.create(cl1, cl2, con);
            con.close();
            **/           
            
            
             /** retreive all contact for a client
            ContactImpl coi=new ContactImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();


            ClientImpl cli=new ClientImpl();
            
            Client cl2;
            cl2=cli.retreive("cc", con);
            ArrayList<Contact> ac=coi.retreiveall(cl2, con);
            System.out.println(ac.get(0).getEmail()+" "+ac.get(0).getContactEmail());
            con.close();

             **/             
            
            
            
            /** update 
            ContactImpl coi=new ContactImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();


            ClientImpl cli=new ClientImpl();
            
            Client cl2;
            cl2=cli.retreive("cc", con);
            cl2.setEmail("ss");
            cli.update(cl2, con);
            coi.update("cc", cl2, con);
            con.close();
            **/  
            

            
            /** delete 
            ContactImpl coi=new ContactImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Contact co=new Contact();

            ClientImpl cli=new ClientImpl();

            Client cl1;
            cl1=cli.retreive("cc", con);
            
            Client cl2;
            cl2=cli.retreive("abvb a", con);
            coi.delete(cl1, cl2, con);
            con.close();
             **/              
        
        
        /** end of contact test **/       
        

            
        /** start Groups**/
        
        
            /** create
            
            GroupsImpl gi=new GroupsImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Groups g=new Groups();
            g.setId("3");
            g.setName("ali");
            gi.create(g, con);
            con.close();
            **/
            
            
            
             /** retreive

            GroupsImpl gi=new GroupsImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Groups g;
            g=gi.retreive("3", con);
            System.out.println(g.getName());
            con.close();
            
            **/             
            
            
            
            /** update 
            GroupsImpl gi=new GroupsImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Groups g;
            g=gi.retreive("3", con);
            g.setName("ali");
            gi.update(g, con);
            con.close();
            
             **/  
            

            
            /** delete 
            GroupsImpl gi=new GroupsImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Groups g;
            g=gi.retreive("3", con);
            gi.delete(g, con);
            con.close();
            **/  
        
        /** end of Groups test **/              
            

            
         /** start Has**/
        
        
            /** create
            HasImpl hi=new HasImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Has h=new Has();
            h.setEmail("cc");
            h.setId("3");
            hi.create(h, con);
            con.close();

             **/
            
            
            
             /** retreive all groups 

            HasImpl hi=new HasImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            
            ArrayList<Has> ac=hi.retreiveAll("cc", con);
            System.out.println(ac.get(0).getId()+" "+ac.get(0).getEmail());
            con.close();
            
             **/             
            
            
            
            /** update **/


            /** update **/  
            

            
            /** delete 
            HasImpl hi=new HasImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            
            ArrayList<Has> ac=hi.retreiveAll("cc", con);
            hi.delete(ac.get(0), con);
            con.close();

            **/              
        
        
        /** end of Has test **/             
            

            
        /** start requests**/
        
        
            /** create 
            RequestsImpl ri=new RequestsImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            Requests r=new Requests();
            r.setEmailSender("abvb a");
            r.setEmailReciever("cc");
            ri.create(r, con);
            con.close();

           **/
            
            
            
             /** retreive all requests 
            RequestsImpl hi=new RequestsImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            
            ArrayList<Requests> ac=hi.retreiveAll("cc", con);
            System.out.println(ac.get(0).getEmailSender()+" "+ac.get(0).getEmailReciever());
            con.close();

            **/             
            
            
            
            /** update 
             error logic

            **/  
            

            
            /** delete

            RequestsImpl hi=new RequestsImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            
            ArrayList<Requests> ac=hi.retreiveAll("cc", con);
            hi.delete(ac.get(0), con);
            con.close();
            **/              
        
        
        /** end of requests test **/              
            
            
            
            
            
            
  //  }
}
