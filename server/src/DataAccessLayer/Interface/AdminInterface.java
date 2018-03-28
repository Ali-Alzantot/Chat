/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.Interface;


import DataAccessLayer.DTO.Admin;
import DataAccessLayer.GenericInterface.GInterface;
import java.sql.Connection;
import static javax.swing.text.html.HTML.Tag.TT;

/**
 *
 * @author Team4
 */
public interface AdminInterface extends GInterface<Admin>{
    @Override
    public int create (Admin obj,Connection con);
    @Override
    public Admin retreive (String email,Connection con);
    @Override
    public int update (Admin obj,Connection con);
    @Override
    public int delete (Admin obj,Connection con);

}
