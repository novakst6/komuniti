/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.service.manager;

import cz.komuniti.model.entity.HelpEntity;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author novakst6
 */
@Service
public class HelpManager extends GenericManager<HelpEntity> {
    
    @PostConstruct
    public void init(){
        super.setEntityClass(HelpEntity.class);
    }
    
}
