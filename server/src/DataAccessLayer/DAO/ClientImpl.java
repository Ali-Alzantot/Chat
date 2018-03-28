/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DAO;

import DataAccessLayer.DTO.Client;

import DataAccessLayer.DataBaseManager.DataBaseManager;
import DataAccessLayer.Interface.ClientInterface;
import Property.ClientProp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static jdk.nashorn.tools.ShellFunctions.input;

/**
 * ClientImpl class
 *
 * @author Team 4
 *
 * ClientImpl class enables us to deal with clients throw DataBase by Inserting,
 * Retrieving, Deleting and creating records at Database, also it enables the
 * Administrator to categorize the age, the gender and the number of Online
 * users.
 *
 */
public class ClientImpl implements ClientInterface {

    // private Statement s;
    private PreparedStatement st;
    private ResultSet rs;
    private DataBaseManager managerObj;
    private final Connection con;
    private Statement s;
 
    public ClientImpl() {
        managerObj = new DataBaseManager();
        this.con=managerObj.getCon();
    }

    
     /**
     * parametric constructor
     *
     * @param driverClassName
     * @param dbURL
     * @param user
     * @param password
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    
    
    public ClientImpl(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        con = DriverManager.getConnection(dbURL, user, password);
    }
    
     /**
     * shutdown Method to close the connectivity
     * @throws SQLException
     */
    
    
    public void shutdown() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
    
    /**
     * getPersonList Method is used to initiate the property class 
     * @return
     * @throws SQLException
     */
    
    public List<ClientProp> getPersonList() throws SQLException {

        try  {
                 s = con.createStatement();
                
                rs = s.executeQuery("select * from Client");
                rs = s.getResultSet();
            List<ClientProp> clientList = new ArrayList<>();
            while (rs.next()) {
                String Email = rs.getString("Email");
                String Password = rs.getString("Password");
                String Name = rs.getString("Name");
                String Gender = rs.getString("Gender");
                String Status = rs.getString("Status");
                String City = rs.getString("City");
                String Phone = rs.getString("Phone");
                ClientProp client = new ClientProp(Email, Password, Name, Gender, Status, City, Phone);
                clientList.add(client);
            }
            rs.close();
            s.close();
            return clientList;
        } catch (SQLException ex) {
            Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }
    
    /**
     * create Method to insert a record in the client table at database with the
     * client object parameters
     *
     * @param obj to set a client with this object data in the database
     * @param con to get the connection
     * @return integer(0,-1) we use it as an indicator to check whether the
     * creation has been done or not.
     */
    
    
    @Override
    public int create(Client obj, Connection con) {
       

        try {           

            st = con.prepareStatement("INSERT  INTO Client (Email,Password,Name,Gender,Status,CIty,Phone,Bdate)VALUES (?,?,?,?,?,?,?,?) ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            st.setString(1, obj.getEmail());
            st.setString(2, obj.getPassword());
            st.setString(3, obj.getName());
            st.setString(4, obj.getGender());
            st.setString(5, obj.getStatus());
            st.setString(6, obj.getCity());
            st.setString(7, obj.getPhone());
            st.setDate(8, (Date) obj.getDate());
            //st.setString(9, obj.getImage());

            rs = st.executeQuery();
            rs = st.getResultSet();
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

      /**
     * retreive Method to retrieve a client object from it's Email
     *
     * @param email which enable us to get the whole client data as it's the
     * primary key of Client table
     * @param con to get Connectivity
     * @return Client with the associated data with the sent email
     */  
    
    
    @Override
    public Client retreive(String email, Connection con) {      
        Client obj=null;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE) ;
            rs=s.executeQuery(" SELECT * " + " FROM Client " + " WHERE email  = '" + email + "'");
            if(rs.next()){
                obj = new Client();
                obj.setEmail(email);
                obj.setPassword(rs.getString(2));
                obj.setName(rs.getString(3));
                obj.setGender(rs.getString(4));
                obj.setStatus(rs.getString(6));
                obj.setCity(rs.getString(7));
                obj.setPhone(rs.getString(8));
                obj.setDate(rs.getDate(9));
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
        finally
        {
            try {
                s.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
             
        return obj;
    }
    
    
    
    /**
     *
     * retreiveByEmailAndPass Method to retrieve a client object from it's Email
     * and password
     *
     * @param email which enable us to get the whole client data as it's the
     * primary key of Client table
     * @param con to get Connectivity
     * @param pass to get the Client from password also.
     * @return Client with the associated data with the sent email
     */

     public Client retreiveByEmailAndPass(String email,String pass,Connection con) {      
        Client obj=null;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE) ;
            rs=s.executeQuery(" SELECT * " + " FROM Client " + " WHERE email  = '" + email + "' and password = '" + pass + "'");
            if(rs.next()){
                obj=new Client();
                obj.setEmail(email);
                obj.setPassword(rs.getString(2));
                obj.setName(rs.getString(3));
                obj.setGender(rs.getString(4));
                obj.setStatus(rs.getString(6));
                obj.setCity(rs.getString(7));
                obj.setPhone(rs.getString(8));
                obj.setDate(rs.getDate(9));
            }
        }
        catch(Exception x){
            x.printStackTrace();
        }
        finally
        {
            try {
                s.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
             
        return obj;
    }
     
 
    /**
     * update Method that update the data of the client in the database
     *
     * @param obj the client which needs to be updated
     * @param con to get Connectivity
     * @return integer(0,-1) we use it as an indicator to check whether the
     * Update has been done or not.
     */    
     
     
    @Override
    public int update(Client obj, Connection con) {

        try {
            s = con.createStatement();
            s.executeUpdate("UPDATE Client set Password='" + obj.getPassword() + "',Name='" + obj.getName()
                    + "',Gender='" + obj.getGender() + "',Status='" + obj.getStatus() + "',CIty='" + obj.getCity() + "',Phone='" + obj.getPhone()
                    + "' where Email='" + obj.getEmail() + "'");
            //  rs = st.getResultSet();     
            //  st = con.prepareStatement("UPDATE Client set Password=?,Name=?,Gender=?,Status=?,CIty=?,Phone=?,Bdate=?,Image=? where Email=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            /*            st.setString(1, obj.getPassword());
            st.setString(2, obj.getName());
            st.setString(3, obj.getGender());
            st.setString(4, obj.getStatus());
            st.setString(5, obj.getCity());
            st.setString(6, obj.getPhone());
            st.setDate(7, (Date) obj.getDate());
            st.setString(8, obj.getImage());
            st.setString(9, obj.getEmail());*/
        } catch (Exception ex) {
            ex.printStackTrace();

            return -1;
        }
        //  s.execute(sql);
        //System.out.println(st.g);
        return 0;

    }

     /**
     * delete Method to delete the client from the database
     *
     * @param obj the client which needs to be cleared from the database
     * @param con to get connectivity
     * @return integer(0,-1) we use it as an indicator to check whether the
     * Update has been done or not.
     */   
    
    
    
    @Override
    public int delete(Client obj, Connection con) {
        try {
            s = con.createStatement();
            s.executeUpdate(" DELETE FROM Client where Email = '" + obj.getEmail() + "'");
            //st.setString(1, obj.getEmail());
            // System.out.println("EmailSender: " + obj.getEmailSender() + " " + "EmailReciever: " + obj.getEmailReciever());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            if (obj.getEmail() != null) {
                // st.executeUpdate();
            } else {
                System.out.println(" No Client to delete");
            }
            return 0;

            // } catch (SQLException ex) {
            //     ex.printStackTrace();
            //     return -1;
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
     * getAge Method which calculate the age of the client using the calendar
     * and the birthdate
     *
     * @param dob Calendar object
     * @return age , the age of the client
     */
    
    
    public int getAge(Calendar dob) {
        long currentTime = System.currentTimeMillis();
        TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar today = Calendar.getInstance(tz);
        today.setTimeInMillis(currentTime);

        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);

        int age = curYear - dobYear;

        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }
        System.out.println(age);
        return age;
    }
    /**
     * Gget Method is take arrayList of Birth of date for each client, the start and end age to retrieve the numbers
     * of these clients within this range.
     * @param c arrayList of Birth of date for each client
     * @param start age 
     * @param End age 
     * @deprecated 
     * @return integer number refers to number of clients within this range of age 
     */
    public int Gget(ArrayList<Date> c, int start, int End) {
        int n = 1;
        for (int i = 0; i < c.size(); i++) {
            //  c.get(i).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                 //  TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles"); 
           Calendar dob = Calendar.getInstance();
           if(c.get(i)!=null)
            dob.setTime(c.get(i));
            int date = getAge(dob);
            if (date > start && date < End) {
                n += 1;
            }
        }
        return n;
    }

     /**
     * getA Method that counts number of clients within range age from 5 to 10 years
     * @param con to get Connection 
     * @return count which refers to number of clients 
     */ 
    
    
    public int getA(Connection con) {

        int counter = 0;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery("SELECT months_between (sysdate, Bdate)/12 FROM Client ");
            List<Integer> myCoords = new ArrayList<Integer>();
            while (rs.next()) {
                myCoords.add( rs.getInt(1));
                 System.out.println(" age:"+rs.getInt(1));
            }
         
           Iterator <Integer> myListIterator = myCoords.iterator(); 
 while (myListIterator.hasNext()) {
    Integer coord = myListIterator.next();     
if(coord>=5&&coord<10)counter++;
}
        } catch (SQLException x) {
            
        } finally {
            try {
                if(s!=null)
                s.close();
                 if(rs!=null)
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return counter;
    }

     /**
     * getB Method that counts number of clients within range age from 10 to 20 years
     * @param con to get Connection 
     * @return count which refers to number of clients 
     */
       
    
    public int getB(Connection con) {
           int counter = 0;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery("SELECT months_between (sysdate, Bdate)/12 FROM Client ");
            List<Integer> myCoords = new ArrayList<Integer>();
            while (rs.next()) {
                myCoords.add( rs.getInt(1));
            }
         
           Iterator <Integer> myListIterator = myCoords.iterator(); 
 while (myListIterator.hasNext()) {
    Integer coord = myListIterator.next();     
if(coord>=10&&coord<20)counter++;
}
        } catch (SQLException x) {
            
        } finally {
            try {
                if(s!=null)
                s.close();
                 if(rs!=null)
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return counter;
    }

     /**
     * getC Method that counts number of clients within range age from 20 to 30 years
     * @param con to get Connection 
     * @return count which refers to number of clients 
     */
    
    
    
    public int getC(Connection con) {
           int counter = 0;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery("SELECT months_between (sysdate, Bdate)/12 FROM Client ");
            List<Integer> myCoords = new ArrayList<Integer>();
            while (rs.next()) {
                myCoords.add( rs.getInt(1));
                 System.out.println(" age:"+rs.getInt(1));
            }
         
           Iterator <Integer> myListIterator = myCoords.iterator(); 
 while (myListIterator.hasNext()) {
    Integer coord = myListIterator.next();     
if(coord>=20&&coord<30)counter++;
}
        } catch (SQLException x) {
            
        } finally {
            try {
                if(s!=null)
                s.close();
                 if(rs!=null)
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return counter;
    }

     /**
     * getD Method that counts number of clients within range age from 30 to 40 years
     * @param con to get Connection 
     * @return count which refers to number of clients 
     */    
    
    public int getD(Connection con) {
        int counter = 0;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery("SELECT months_between (sysdate, Bdate)/12 FROM Client ");
            List<Integer> myCoords = new ArrayList<Integer>();
            while (rs.next()) {
                myCoords.add( rs.getInt(1));
            }
         
           Iterator <Integer> myListIterator = myCoords.iterator(); 
 while (myListIterator.hasNext()) {
    Integer coord = myListIterator.next();     
if(coord>=30&&coord<40)counter++;
}
        } catch (SQLException x) {
            
        } finally {
            try {
                if(s!=null)
                s.close();
                 if(rs!=null)
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return counter;
    }

     /**
     * getE Method that counts number of clients within range age from 40 to 550 years
     * @param con to get Connection 
     * @return count which refers to number of clients 
     */
    
    
    public int getE(Connection con) {
      int counter = 0;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery("SELECT months_between (sysdate, Bdate)/12 FROM Client ");
            List<Integer> myCoords = new ArrayList<Integer>();
            while (rs.next()) {
                myCoords.add( rs.getInt(1));
            }
         
           Iterator <Integer> myListIterator = myCoords.iterator(); 
 while (myListIterator.hasNext()) {
    Integer coord = myListIterator.next();     
if(coord>=40&&coord<550)counter++;
}
        } catch (SQLException x) {
            
        } finally {
            try {
                if(s!=null)
                s.close();
                 if(rs!=null)
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return counter;
    }
    
    /**
     * getA2 Method get the number of male clients in the database through
     * Select query with where condition to be a male
     *
     * @param con to get the connectivity
     * @return counter which is the number of female clients
     */

    
    
    
    public int getA2(Connection con) {
        int counter = 1;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(" SELECT count(GENDER) " + " FROM Client  where GENDER='Male'");
            if (rs.next()) {
                counter = Integer.parseInt(rs.getString(1));
                System.out.println(counter);
            }
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            try {
                s.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return counter;
    }

   
    /**
     * getB2 Method get the number of female clients in the database through
     * Select query with where condition to be a female
     *
     * @param con to get the connectivity
     * @return counter which is the number of female clients
     */
    
    
    public int getB2(Connection con) {
        int counter = 1;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(" SELECT count(GENDER) " + " FROM Client  where GENDER='Female'");
            if (rs.next()) {
                counter = Integer.parseInt(rs.getString(1));
                System.out.println(counter);
            }
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            try {
                s.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return counter;
    }
    
   
    /**
     * getContOfClient Method is a function to get the number of clients in the
     * database through Select query
     *
     * @return count which is the number of Clients
     */
    
       public int getContOfClient(){
         int counter = 1;
        try {
            s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(" SELECT count(email) " + " FROM Client ");
            if (rs.next()) {
                counter = Integer.parseInt(rs.getString(1));
                System.out.println("getContOfClient : "+counter);
            }
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            try {
                s.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return counter;
    }


}
