/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SaveChat;

/**
 *
 * @author abdalla
 */
public class Massege implements java.io.Serializable{
   private String url;
   private String massege;
   private String Stayle;
   private String type;
   private String fontColor;
   private String lableColor;
   private String fontFamily;
   private String nameSender;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }

    public String getStayle() {
        return Stayle;
    }

    public void setStayle(String Stayle) {
        this.Stayle = Stayle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getLableColor() {
        return lableColor;
    }

    public void setLableColor(String lableColor) {
        this.lableColor = lableColor;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }
    
}
