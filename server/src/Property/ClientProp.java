/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Property;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



/**
 *
 * @author Team4
 */
public class ClientProp {

    private final StringProperty Email = new SimpleStringProperty(this, "Email ");

    public StringProperty EmailProperty() {
        return Email;
    }

    public final String getEmail() {
        return EmailProperty().get();
    }

    public final void setEmail(String Email) {
        EmailProperty().set(Email);
    }
    private final StringProperty Password = new SimpleStringProperty(this, "Password ");

    public StringProperty PasswordProperty() {
        return Password;
    }

    public final String getPassword() {
        return PasswordProperty().get();
    }

    public final void setPassword(String Password) {
        PasswordProperty().set(Password);
    }
    private final StringProperty Name = new SimpleStringProperty(this, "Name ");

    public StringProperty NameProperty() {
        return Name;
    }

    public final String getName() {
        return NameProperty().get();
    }

    public final void setName(String Name) {
        NameProperty().set(Name);
    }
    private final StringProperty Gender = new SimpleStringProperty(this, "Gender ");

    public StringProperty GenderProperty() {
        return Gender;
    }

    public final String getGender() {
        return GenderProperty().get();
    }

    public final void setGender(String Gender) {
        GenderProperty().set(Gender);
    }

    private final StringProperty Status = new SimpleStringProperty(this, "Status ");

    public StringProperty StatusProperty() {
        return Status;
    }

    public final String getStatus() {
        return StatusProperty().get();
    }

    public final void setStatus(String Status) {
        StatusProperty().set(Status);
    }
    private final StringProperty CIty = new SimpleStringProperty(this, "CIty ");

    public StringProperty CItyProperty() {
        return CIty;
    }

    public final String getCIty() {
        return CItyProperty().get();
    }

    public final void setCIty(String CIty) {
        CItyProperty().set(CIty);
    }
    private final StringProperty Phone = new SimpleStringProperty(this, "Phone ");

    public StringProperty PhoneProperty() {
        return Phone;
    }

    public final String getPhone() {
        return PhoneProperty().get();
    }

    public final void setPhone(String Phone) {
        PhoneProperty().set(Phone);
    }

    public ClientProp(String Email, String Password, String Name, String Gender, String Status, String City, String Phone) {
        setEmail(Email);
        setPassword(Password);
        setName(Name);
        setGender(Gender);
        setStatus(Status);
        setCIty(City);
        setPhone(Phone);
    }

}
