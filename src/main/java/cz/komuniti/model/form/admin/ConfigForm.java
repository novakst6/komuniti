/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;


import cz.komuniti.service.validator.FieldEmailInDBNoDelete;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author novakst6
 */
@FieldEmailInDBNoDelete(email="contentModerator",message="Zadaný uživatel nebyl nalezen.")
public class ConfigForm {
    private String contentModerator;

    @Email(message="Email musí být ve správném formátu.")
    public String getContentModerator() {
        return contentModerator;
    }

    public void setContentModerator(String contenetModerator) {
        this.contentModerator = contenetModerator;
    }
    
    
}
