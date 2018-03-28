/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the ceditor.
 */
package DataAccessLayer.DTO;

/**
 * it is a class that represents contact relationship between 2 clients of their emails and their setters and getters.
 * @author team 4
 */
public class Contact implements java.io.Serializable {
    private String email;
    private String contactEmail;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
}
