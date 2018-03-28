/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DAO;

import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import DataAccessLayer.Interface.ContactInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Team 4.
 */
public class ContactImpl implements ContactInterface {
   private DataBaseManager managerObj;
   private PreparedStatement st;
   private ResultSet rs ;
   
    /**
     *constructor.
     */
    public ContactImpl() {
        managerObj =new DataBaseManager();
    }
    /**
     * utilizes 2 client objects and connection to the database to add create a contact row.
     * each row takes the email of the 2 client objects and build the row.
     * @param obj1
     * @param obj2
     * @param con
     * @return integer 0 if success and -1 if else.
     */
    @Override
    public int create(Client obj1, Client obj2, Connection con) {
       int i=-1;
       try {
           con.setAutoCommit (false);
           st = con.prepareStatement("INSERT  INTO Contact (Email_1 , Email_2  )VALUES (? , ? ) ",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE) ;
           st.setString(1, obj1.getEmail());
           st.setString(2, obj2.getEmail());
           rs = st.executeQuery();
           i=0;
       } catch (SQLException ex) {
           Logger.getLogger(ContactImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
        return i;
    }
    /**
     * utilizes 2 client objects and connection to the database to add retrieve a contact row.
     * each call takes the email of the 2 client objects and retrieve the contact object. 
     * @param obj
     * @param obj2
     * @param con
     * @return a contact object if success.
     */

    @Override
    public Contact retreive(Client obj, Client obj2, Connection con) {
       Contact c=new Contact();
       try {
           Statement st = con.createStatement();
           rs = st.executeQuery("SELECT * FROM Contact WHERE (Email_1 = '"+obj.getEmail()+"' and Email_2 = '"+obj2.getEmail()+"') or (Email_1 = '"+obj2.getEmail()+"' and Email_2 = '"+obj.getEmail()+"')");
           rs = st.getResultSet();
           rs.next();
           c.setEmail(rs.getString(1));
           c.setContactEmail(rs.getString(2));
       } catch (SQLException ex) {
           Logger.getLogger(ContactImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
        return c;
    }

    /**
     * utilizes a client object, email and connection to the database to add update a contact row.
     * it gets the old email client and and the old email searches for the old email and replaces it with the new object's email.
     * @param email
     * @param obj
     * @param con
     * @return integer 0 if success and -1 if else.
     */
    @Override
    public int update (String email,Client obj,Connection con) {
        int i=-1;
       try {
           Statement st = con.createStatement();
           st.executeUpdate("UPDATE Contact SET Email_1 = '"+obj.getEmail()+"' WHERE Email_1 = '"+email+"'");
           st.executeUpdate("UPDATE Contact SET Email_2 = '"+obj.getEmail()+"' WHERE Email_2 = '"+email+"'");
           i=0;
       } catch (SQLException ex) {
           Logger.getLogger(ContactImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
        return i;
    }
    /**
     * utilizes 2 client objects and connection to the database to delete a contact row.
     * it searches for the 2 emails from the 2 objects in either sides and delete ut if found.
     * @param obj1
     * @param obj2
     * @param con
     * @return integer 0 if success and -1 if else.
     */
    @Override
    public int delete(Client obj1, Client obj2, Connection con) {
         int i=-1;
       try {
           Statement st = con.createStatement();
           st.executeUpdate("DELETE FROM CONTACT WHERE ( EMAIL_1 = '"+obj1.getEmail()+"' AND EMAIL_2= '"+obj2.getEmail()+"') OR ( EMAIL_1 = '"+obj2.getEmail()+"' AND EMAIL_2= '"+obj1.getEmail()+"')");
           //st.executeUpdate("UPDATE Contact SET Email_2 = '"+obj.getEmail()+"' WHERE Email_2 = '"+obj2.getEmail()+"'");
           i=0;
       } catch (SQLException ex) {
           Logger.getLogger(ContactImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
        return i;
    }

    /**
     * utilizes a client objects and connection to the database to retrieve a array list contacts.
     * each call takes the email of the client object and searches for that email in either sides. 
     * @param obj
     * @param con
     * @return array list of contacts of certain client object.
     */
    @Override
    public ArrayList<Contact> retreiveall(Client obj, Connection con) {
        ArrayList<Contact> c;
       c = new ArrayList<Contact>();
       try {
           Statement st = con.createStatement();
           rs = st.executeQuery(" SELECT * FROM CONTACT WHERE EMAIL_1 = '"+obj.getEmail()+"' OR EMAIL_2 = '"+obj.getEmail()+"'");
           rs = st.getResultSet();
           rs.next();
           while(!rs.isAfterLast()){
               Contact temp = new Contact();
               temp.setContactEmail(rs.getString(1));
               temp.setEmail(rs.getString(2));
               c.add(temp);
               rs.next();
           }
       } catch (SQLException ex) {
           Logger.getLogger(ContactImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
        return c;
    }

    /**
     *
     * @param obj
     * @param con
     * @return
     */
    @Override
    public int create(Client obj, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param email
     * @param con
     * @return
     */
    @Override
    public Client retreive(String email, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param obj
     * @param con
     * @return
     */
    @Override
    public int update(Client obj, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * utilizes a client object and connection to the database to delete all contact row of certain client object.
     * it searches for the email of the client object in either columns and delete them all.
     * each call takes the email of the 2 client objects and retrieve it. 
     * @param obj
     * @param con
     * @return integer -1 if fails and 0 if success.
     */
    @Override
    public int delete(Client obj, Connection con) {
        int i=-1;
       try {
           Statement st = con.createStatement();
           st.executeUpdate("DELETE FROM CONTACT WHERE EMAIL_1 = '"+obj.getEmail()+"' OR EMAIL_2 = '"+obj.getEmail()+"'");
           //st.executeUpdate("UPDATE Contact SET Email_2 = '"+obj.getEmail()+"' WHERE Email_2 = '"+obj2.getEmail()+"'");
           i=0;
       } catch (SQLException ex) {
           Logger.getLogger(ContactImpl.class.getName()).log(Level.SEVERE, null, ex);
       }
        return i;
    }
}