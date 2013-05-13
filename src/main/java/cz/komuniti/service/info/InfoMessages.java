/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.info;

import java.io.Serializable;
import java.util.Stack;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service("infoMessages")
@Scope(value="session",proxyMode= ScopedProxyMode.TARGET_CLASS)
public class InfoMessages implements Serializable{
    private Stack<String> infoMessages;
    private Stack<String> errorMessages;
    private Stack<String> warnMessages;
    
    public InfoMessages() {
        infoMessages = new Stack<String>();
        errorMessages = new Stack<String>();
        warnMessages = new Stack<String>();
    }
    
    
    public String getInfoMessage(){
        String message = "";
                while(!infoMessages.empty()){
                    message += infoMessages.pop();
                    message += "\n";
                }
        return message;
    }
    
    public String getErrorMessage(){
        String message = "";
        while(!errorMessages.empty()){
            message += errorMessages.pop();
            message += "\n";
        }
        return message;
    }
    
    public String getWarnMessage(){
        String message = "";
        while(!warnMessages.empty()){
            message += warnMessages.pop();
            message += "\n";
        }
        return message;
    }
    
    public void setInfoMessage(String message){
        infoMessages.push(message);
    }
    
    public void setErrorMessage(String message){
        errorMessages.push(message);
    }
    
    public void setWarnMessage(String message){
        warnMessages.push(message);
    }
    
    public boolean getIsInfoMessage(){
        return !infoMessages.empty();
    }
    
    public boolean getIsErrorMessage(){
        return !errorMessages.empty();
    }
    
    public boolean getIsWarnMessage(){
        return !warnMessages.empty();
    }
}
