/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DAO;

import DataAccessLayer.DTO.Requests;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import DataAccessLayer.Interface.RequestsInterface;
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
 * @author Team4
 */
public class RequestsImpl implements RequestsInterface {

    private Statement s;
    private PreparedStatement st;
    private ResultSet rs;
    private DataBaseManager managerObj;

    public RequestsImpl() {
        managerObj = new DataBaseManager();
    }
        /**
         * 
         * @param obj take object from Requests 
         * @param con take object from database connection
         * @return  return 0 if the operation done successfully and -1 if it fail 
         */
    @Override
    public  int create(Requests obj, Connection con) {
        try {
            st = con.prepareStatement("INSERT  INTO Requests ( EmailSender , EmailReciever )VALUES (? , ? ) ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setString(1, obj.getEmailSender());
            st.setString(2, obj.getEmailReciever());
            st.executeQuery();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(RequestsImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RequestsImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * 
     * @param EmailSender email sender as String
     * @param EmailReciever email receiver  as String
     * @param con get Connection from caller to handle the database
     * @return object from Requests
     */
    @Override
    public Requests retreive(String EmailSender, String EmailReciever, Connection con) {
        Requests req = new Requests();
        try {
            
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           // st.setString(1, EmailSender);
           // st.setString(2, EmailReciever); //EmailReceiver
           
            rs = s.executeQuery(" SELECT *  FROM Requests  where EmailSender = '"+EmailSender+"' AND EmailReciever = '"+EmailReciever+"'");

            while  (rs.next()) {
                
               // System.out.println("lol");
                req.setEmailSender(rs.getString("EmailSender"));
                req.setEmailReciever(rs.getString("EmailReciever"));

            }
        } catch (SQLException x) {
            x.printStackTrace();
        } finally {
            try {
                if (s != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RequestsImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return req;
    }

    /**
     * 
     * @param email   as String   the client's email what you need to get all friend Requests that have
     * @param con get Connection from caller to handle the database
     * @return  ArrayList from  friend Requests that client have
     */
       @Override
    public ArrayList<Requests> retreiveAll(String email,Connection con) {
       ArrayList<Requests> c;
       c = new ArrayList<Requests>();
       try {
           Statement st = con.createStatement();
           rs = st.executeQuery(" SELECT * " + " FROM Requests " + " WHERE EmailReciever  = '" + email + "'");
           rs = st.getResultSet();
           rs.next();
           while(!rs.isAfterLast()){
               Requests temp = new Requests();
               temp.setEmailSender(rs.getString(1));
               temp.setEmailReciever(rs.getString(2));
               c.add(temp);
               rs.next();
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
        return c;      
    } 
    
    
    /**
     * 
     * @param email as String   the client's email what you need to get all friend Requests that have
     * @param con get Connection from caller to handle the database
     * @return object from Requests
     */
    @Override
    public Requests retreive(String email, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param obj take object from Requests 
     * @param con take object from database connection
     * @return return 0 if the operation done successfully and -1 if it fail 
     */
    @Override
    public int update(Requests obj, Connection con) {
        try {
            st = con.prepareStatement(" update Requests set EmailSender  = ? AND EmailReciever = ? where EmailSender = ? AND EmailReciever = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setString(1, obj.getEmailSender());
            st.setString(2, obj.getEmailReciever());
            st.setString(3, obj.getEmailSender());
            st.setString(4, obj.getEmailReciever());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            st.executeUpdate();

            return 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RequestsImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 
     * @param obj take object from Requests 
     * @param con take object from database connection
     * @return return 0 if the operation done successfully and -1 if it fail 
     */
    @Override
    public int delete(Requests obj, Connection con) {
        try {
            st = con.prepareStatement(" DELETE FROM Requests where EmailSender = ? AND EmailReciever = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setString(1, obj.getEmailSender());
            st.setString(2, obj.getEmailReciever());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            if(obj.getEmailSender()!=null)
            {
            st.executeUpdate();
            }else
             System.out.println(" No Requests to delete");
            return 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RequestsImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
