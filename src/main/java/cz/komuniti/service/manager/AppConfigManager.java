/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.AppConfigEntity;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service
public class AppConfigManager extends GenericManager<AppConfigEntity> {
    
    @PostConstruct
    public void init(){
        super.setEntityClass(AppConfigEntity.class);
    }
    
}
