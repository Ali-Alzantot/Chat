/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Interface;



import DataAccessLayer.DTO.Client;
import DataAccessLayer.GenericInterface.GInterface;
import java.sql.Connection;

/**
 *  ClientInterface class 
 * @author Team 4
 * 
 */

public interface ClientInterface extends GInterface<Client>{

    /**
     * create is a method which take an object from client and save it's data in the database
     * @param obj
     * @param con
     * @return integer (indicator) which refers to the success or fail of creating process
     */
    @Override
       public int create(Client obj ,Connection con );

    /**
     *  retreive is a method which take the email of the client (the primary key) and return the client
     * which email's matches this given email
     * @param email
     * @param con
     * @return Client
     */
    @Override
            public Client retreive (String email,Connection con);

    /**
     * update is a method which take a client object and update in it
     * @param obj
     * @param con
     * @return integer (indicator) which refers to the success or fail of updating process
     */
    @Override
      public int update (Client obj,Connection con);

    /**
     * delete is a method which delete a client record from the database
     * @param obj
     * @param con
     * @return integer (indicator) which refers to the success or fail of deleting process
     */
    @Override
      public int delete (Client obj,Connection con);

}
