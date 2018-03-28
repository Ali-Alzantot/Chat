 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DAO;

import DataAccessLayer.DTO.Admin;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import DataAccessLayer.Interface.AdminInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author team 4
 */
public class AdminImpl implements AdminInterface {

    private Statement s;
    private PreparedStatement st;
    private ResultSet rs;
    private DataBaseManager managerObj;

    /**
     *
     */
    public AdminImpl() {
        managerObj = new DataBaseManager();

    }

    /**
     * utilizes an Admin object and a connection object to create a table in the admin table in the database
     * each call takes the object's username and password to create a new admin.
     * @param obj
     * @param con
     * @return 0if successful operation and -1 for unsuccessful operation.
     */
    @Override
    public int create(Admin obj, Connection con) {

        try {
            st = con.prepareStatement("INSERT  INTO Admin (UserName , Password  )VALUES (? , ? ) ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.setString(1, obj.getUserName());
            st.setString(2, obj.getPassword());

            st.executeQuery();


            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            try {
                st.close();
               // rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * utilizes a string email and a connection object to retrieve a row from the table.
     * each call takes the string email to retrieve the admin object.
     * @param email
     * @param con
     * @return admin object.
     */
    @Override
    public Admin retreive(String email, Connection con) {
        Admin ad = new Admin();
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(" SELECT * " + " FROM admin " + " WHERE UserName  = '" + email + "'");
            if (rs.next()) {
                ad.setPassword(rs.getString(2));
                ad.setUserName(rs.getString(1));
            }
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            try {
                s.close();
                //rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ad;
    }
    
    /**
     * utilizes a string email, string password and a connection object to retrieve an admin row.
     * each call takes the string email and string password to retrieve an admin object if found.
     * @param email
     * @param password
     * @param con
     * @return an admin object if both email and password are met in the database.
     */
    public Admin retreive(String email,String password, Connection con) {
        Admin obj = null;
        try {
               
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(" SELECT * " + " FROM admin " + " WHERE UserName  = '" + email + "' AND Password = '"+password+"'");
            if (rs.next()) {
                 obj=new Admin();
                obj.setPassword(rs.getString(2));
                obj.setUserName(rs.getString(1));
            }
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            try {
                s.close();
                //rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }

    /**
     * utilizes an admin object and a connection object to update row in the admin table.
     * each call takes the admin object to update the password of the given in that object of the email of the object if found.
     * @param obj
     * @param con
     * @return integer 0 if successful operation and -1 if unsuccessful operation.
     */
    @Override
    public int update(Admin obj, Connection con) {
        try {

            st = con.prepareStatement(" update Admin set Password = ? where UserName = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setString(1, obj.getPassword());
            st.setString(2, obj.getUserName());
        } catch (Exception ex) {
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
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * utilizes an admin object and a connection object to delete row in the admin table.
     * each call takes the admin object to delete the row of the object if found.
     * @param obj
     * @param con
     * @return 0 if successful operation and -1 if unsuccessful operation
     */
    @Override
    public int delete(Admin obj, Connection con) {
        try {
            st = con.prepareStatement(" DELETE FROM Admin WHERE UserName= ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setString(1, obj.getUserName());
        } catch (Exception ex) {
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
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
