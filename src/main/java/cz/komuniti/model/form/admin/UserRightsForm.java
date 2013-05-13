/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author novakst6
 */
public class UserRightsForm {
    
    private List<Long> roles;
    private Long id;

    public UserRightsForm() {
     roles = new LinkedList<Long>();
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
