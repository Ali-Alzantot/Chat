/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DTO;

/**
 * it is a class that represents an admin with an admin username and a password and their setters and getters.
 * @author team 4
 */
public class Admin implements java.io.Serializable {
    private String userName;
    private String password;
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
