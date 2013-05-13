/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;

import cz.komuniti.service.manager.MessageManager;
import cz.komuniti.service.manager.UserManager;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */

@Service("MessageCounter")
public class MessageCounter implements Serializable{
    
    private MessageManager messageManager;
    
    public int getCountInbox(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = (User) auth.getPrincipal();
        String email = u.getUsername();
        int count = messageManager.getCountInboxUnreaded(email, Boolean.FALSE);
        return count;
    }
    
    public int getCountOutbox(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User u = (User) auth.getPrincipal();
        String email = u.getUsername();
        int count = messageManager.getCountOutboxUnreaded(email, Boolean.FALSE);
        return count;
    }

    public @Autowired void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }
    
    
    
}
