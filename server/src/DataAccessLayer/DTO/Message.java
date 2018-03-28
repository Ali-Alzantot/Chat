/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer.DTO;

import javafx.scene.paint.Color;

/**
 * it is a class that represents Messages between 2 clients 
 * @author team 4
 */
public class Message implements java.io.Serializable{
    int fSize;
    String color;
    String msg;
    String font;
    
    public String getColor(){
        return this.color;
    }
    public void setColor(String c){
        this.color=c;
    }
    public void setFSize(int s){
        this.fSize=s;
    }
    public int getFSize(){
        return this.fSize;
    }
    
    public String getMsg(){
        return this.msg;
    }
    
    public void setMsg(String s){
        this.msg=s;
    }
    
    public String getFont(){
        return this.font;
    }
    
    public void setFont(String s){
        this.font=s;
    }
    
}
