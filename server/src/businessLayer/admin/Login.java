/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLayer.admin;

import DataAccessLayer.DAO.AdminImpl;
import DataAccessLayer.DTO.Admin;
import DataAccessLayer.DataBaseManager.DataBaseManager;
import java.sql.Connection;

/**
 *
 * @author Team4
 */
public class Login {
       private Admin admin;
       private Admin adCheck;
      private   AdminImpl adminImp;
    public Login ()
    {
       // new Login ();
    }

    /**
     * check  Admin login authorization
     * @param adCheck 
     */
    public Login (Admin adCheck)
    {
           this.adCheck=adCheck;
             adminImp=new AdminImpl();
            DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            

            if(adCheck!=null)
        {
                        admin=adminImp.retreive(adCheck.getUserName(), con);
            if(admin.getPassword().equalsIgnoreCase(adCheck.getPassword()))
            {
                System.out.println("Success Login ....");
            }
            else 
            {
              System.out.println("Wrong Password");  
            }
             System.out.println("Wrong user name ");  
        }
    }
    
    /**
     * check  Admin login authorization
     * @param adCheck
     * @return return 0 if the operation done successfully and -1 if it fail 
     */
    
    public int Cheack(Admin adCheck)
            
    {
         adminImp= new AdminImpl();
                     DataBaseManager managerObj = new DataBaseManager();
            Connection con = managerObj.getCon();
            
            adCheck=adminImp.retreive("Admin", con);
        int x=-1;
        if(adCheck!=null)
        {
            admin=adminImp.retreive(adCheck.getUserName(), con);
            if(admin.getPassword().equalsIgnoreCase(adCheck.getPassword()))
            {
                System.out.println("Success Login ....");
                x=0;
            }
            else 
            {
              System.out.println("Wrong Password");  
            }

        }else
           System.out.println("Wrong user name ");  
        return x;
    }

}
