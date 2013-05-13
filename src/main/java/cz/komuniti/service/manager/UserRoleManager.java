/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.UserRoleEntity;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service
public class UserRoleManager extends GenericManager<UserRoleEntity> {

    @PostConstruct
    public void init(){
        super.setEntityClass(UserRoleEntity.class);
    }
    
}
