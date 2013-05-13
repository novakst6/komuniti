/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.validator;

import cz.komuniti.model.entity.UserEntity;
import cz.komuniti.service.manager.UserManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author novakst6
 */
@Component
public class FieldEmailInDBValidator implements ConstraintValidator<FieldEmailInDB, Object>{

    private String email;
    
    private UserManager userManager;
    
    public void initialize(final FieldEmailInDB constrainAnnotation) {
        email = constrainAnnotation.email();
    }

    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try{
            final Object fieldEmail = BeanUtils.getProperty(value, email);
            UserEntity u = userManager.findByEmail((String)fieldEmail);
            return u == null;
        }catch(Exception e){
            ///ignore
        }
        return true;
    }

    public @Autowired void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    
}
