/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Interface;



import DataAccessLayer.DTO.Requests;
import DataAccessLayer.GenericInterface.GInterface;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Team4
 */
public interface RequestsInterface extends GInterface<Requests>{
    

            @Override
    public int create(Requests obj ,Connection con );
    
    public Requests retreive(String SenderEmail ,String RecieverEmail,Connection con  );

            @Override
    public Requests retreive (String email,Connection con );

            @Override
        public int update (Requests obj,Connection con );

            @Override
    public int delete (Requests obj,Connection con );
    
    public ArrayList<Requests> retreiveAll (String email,Connection con);

}
