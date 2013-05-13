/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.security;

import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.service.info.InfoMessages;
import cz.komuniti.service.manager.UserManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;


/**
 *
 * @author novakst6
 */
@Service("googleAuthenticationSuccessHandler")
public class GoogleAuthenticationSuccessHandler implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    private @Autowired Assembler assembler;
    
    private @Autowired UserManager userManager;
    
    private @Autowired InfoMessages infoMessages;
    
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        
        String email = null;
        String firstName = null;
        String lastName = null;
        String fullName = null;
        List<OpenIDAttribute> attributes = token.getAttributes();
        for (OpenIDAttribute attribute : attributes) {
            if (attribute.getName().equals("email")) {
                email = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("firstName")) {
                firstName = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("lastName")) {
                lastName = attribute.getValues().get(0);
            }
            if (attribute.getName().equals("fullname")) {
                fullName = attribute.getValues().get(0);
            }
        }
        if (fullName == null) {
            StringBuilder fullNameBldr = new StringBuilder();
            if (firstName != null) {
                fullNameBldr.append(firstName);
            }
            if (lastName != null) {
                fullNameBldr.append(" ").append(lastName);
            }
            
        }
        
        UserEntity user = userManager.findByEmail(email);
        
        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        
        if(!user.getActive()){
            infoMessages.setWarnMessage("Uživatelský účet není ještě aktivovaný, počkejte až bude váš účet aktivovaný.");
            throw new UsernameNotFoundException("Neaktivovany");
        }
        if(user.getDeleted()){
                infoMessages.setWarnMessage("Uživatelský účet byl smazán.");
                throw new UsernameNotFoundException("Smazaný");
        }
        
        return assembler.buildUserFromUserEntity(user);
    }

    public void setAssembler(Assembler assembler) {
        this.assembler = assembler;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setInfoMessages(InfoMessages infoMessages) {
        this.infoMessages = infoMessages;
    }

    
	
}
