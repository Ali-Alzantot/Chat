/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DAO;



import DataAccessLayer.DTO.Has;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import DataAccessLayer.Interface.HasInterface;
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
public class HasImpl implements HasInterface {

    private Statement s ;
    private PreparedStatement st;
    private ResultSet rs ;
    private DataBaseManager managerObj ;
    
    public HasImpl(){
         managerObj =new DataBaseManager();
    }
    
    /**
     * utilizes a group object and a connection object to create a row in the database.
     * each call takes the id of the object and the email of the user from the object to fill the row.
     * @param obj
     * @param con
     * @return integer 0 if successful operation and -1 if unsuccessful operation
     */
    @Override
    public int create(Has obj,Connection con) {
  
                try {

                    st = con.prepareStatement("INSERT  INTO Has (Email, ID)VALUES (? , ? ) ",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE) ;
                    st.setString(1, obj.getEmail());
                    st.setString(2, obj.getId());
                    st.executeQuery() ;

                    return 0;
                } catch (SQLException ex) {
                   
                    return -1;
                }finally {
            try {
                st.close();
               
            } catch (SQLException ex) {
                Logger.getLogger(HasImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * utilizes 2 strings one of the email , the other of the id and a connection object to retrieve a row.
     * each call takes the id of the group and email and if found it builds with it the has object.
     * @param email
     * @param id
     * @param con
     * @return has object if successful operation.
     */
    @Override
    public Has retreive(String email,String id,Connection con) {
        Has hs=new Has();
        try {

            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE) ;
            rs=s.executeQuery(" SELECT * " + " FROM Has " + " WHERE email  = '" + email + "' and id = '"+id+"'");
            if(rs.next()){
                hs.setEmail(rs.getString(1));
                hs.setId(rs.getString(2));
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
        finally {
            try {
//                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return hs;            
    }
    
    
    /**
     * utilizes a string email and a connection object to retrieve several rows from the has table.
     * each call takes the string email to retrieve all the groups of this specific user.
     * @param email
     * @param con
     * @return array list of has objects if any found.
     */
    @Override
    public ArrayList<Has> retreiveAll(String email,Connection con) {
       ArrayList<Has> c;
       c = new ArrayList<Has>();
       try {
           Statement st = con.createStatement();
           rs = st.executeQuery(" SELECT * " + " FROM Has " + " WHERE email  = '" + email + "'");
           rs = st.getResultSet();
           rs.next();
           while(!rs.isAfterLast()){
               Has temp = new Has();
               temp.setEmail(rs.getString(1));
               temp.setId(rs.getString(2));
               c.add(temp);
               rs.next();
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
        return c;      
    }    
    
    
    
    /**
     * utilizes a string id and a connection object to retrieve several rows from the has table.
     * each call takes the string id to retrieve all the users in this specific group.
     * @param id
     * @param con
     * @return  array list of has objects if any found
     */
    @Override
    public ArrayList<Has> retreiveGroupRows(String id,Connection con) {
       ArrayList<Has> c;
       c = new ArrayList<Has>();
       try {
           Statement st = con.createStatement();
           rs = st.executeQuery(" SELECT * " + " FROM Has " + " WHERE id  = '" + id + "'");
           rs = st.getResultSet();
           rs.next();
           while(!rs.isAfterLast()){
               Has temp = new Has();
               temp.setEmail(rs.getString(1));
               temp.setId(rs.getString(2));
               c.add(temp);
               rs.next();
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
        return c;      
    }
    

    
    @Override
    public int update(Has obj,Connection con) {
                try{

                    st=con.prepareStatement(" update Has set Email  = ? where ID = ? ",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    st.setString(1,obj.getEmail());
                    st.setString(2,obj.getId()); 
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                try {
                    st.executeUpdate();

                    return 0;
                   
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return -1; 
                }
                finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    /**
     * utilizes a has object and a connection object to retrieve delete a row from the has table.
     * each call takes the email variable and the id variable from the object to delete that specific row from the has table.
     * @param obj
     * @param con
     * @return 
     */
    @Override
    public int delete(Has obj,Connection con) {
               try{
                   System.out.println("ssdasda" + obj.getEmail());
                    st=con.prepareStatement(" DELETE FROM Has WHERE email= ? and ID = ? ",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    st.setString(1,obj.getEmail());
                    st.setString(2, obj.getId());
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                try {
                    st.executeUpdate();
                    return 0;
                   
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return -1; 
                } 
                finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AdminImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Has retreive(String email, Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        

}
