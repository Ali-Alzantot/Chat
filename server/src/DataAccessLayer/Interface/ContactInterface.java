/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Interface;

import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.GenericInterface.GInterface;
import java.sql.Connection;
import java.util.ArrayList;


/**
 *
 * @author Team4
 */
public interface ContactInterface extends GInterface <Client>{
    public int create (Client obj1,Client obj2,Connection con);
    public Contact retreive (Client obj,Client obj2,Connection con);
    public int update (String email,Client obj,Connection con);
    public int delete (Client obj1,Client obj2,Connection con);
    public ArrayList<Contact> retreiveall (Client obj,Connection con);
    
}
