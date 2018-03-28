/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.GenericInterface;

import java.sql.Connection;

/**
 *
 * @author Team4
 */
public interface GInterface <TT> {
    
    public int create(TT obj ,Connection con );
    
    public TT retreive (String email,Connection con);
    
    public int update (TT obj,Connection con);
    
    public int delete (TT obj,Connection con);
}
