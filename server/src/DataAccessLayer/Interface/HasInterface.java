/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Interface;



import DataAccessLayer.DTO.Has;
import DataAccessLayer.GenericInterface.GInterface;
import java.sql.Connection;
import java.util.ArrayList;

/**
 *
 * @author Team4
 */
public interface HasInterface extends GInterface<Has>{
        @Override
    public int create (Has obj,Connection con);
    @Override
    public Has retreive (String email,Connection con);
    @Override
        public int update (Has obj,Connection con);
    @Override
    public int delete (Has obj,Connection con);
    public Has retreive(String email,String id,Connection con);
    public ArrayList<Has> retreiveAll (String email,Connection con);
    
     public ArrayList<Has> retreiveGroupRows(String id,Connection con);
}
