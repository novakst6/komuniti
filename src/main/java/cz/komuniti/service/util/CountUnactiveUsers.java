/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.util;

import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.service.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */

@Service("CountUnactiveUsers")
public class CountUnactiveUsers {
    
    private UserManager userManager;

    public int getCountUnactiveUsers()
    {
        try {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        UserEntity user = userManager.findByEmail(auth.getName());
        
        Long idCenter = user.getCentrum().getId();
        int countUnactiveCenter = userManager.getCountUnactiveCenter(idCenter, Boolean.FALSE, Boolean.FALSE);
        return countUnactiveCenter;
        } catch(Exception e){
            System.out.println("ERROR COUNT "+e.getMessage());
            return -1;
        }
        
    }

    public @Autowired void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    
}
