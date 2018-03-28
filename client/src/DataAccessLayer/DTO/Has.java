/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DTO;

/**
 * it is a class that represents of a client as a member of the group with a group id and client's email and their setters and getters.
 * @author team 4
 */
public class Has implements java.io.Serializable {
    private String email;
    private String id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
