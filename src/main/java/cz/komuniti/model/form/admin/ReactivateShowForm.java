/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;

import cz.komuniti.service.validator.FieldEmailNotInDB;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novkast6
 */

@FieldEmailNotInDB(email="email",message="Tento uživatel nebyl nalezen.")
public class ReactivateShowForm {
    
    private String email;

    @Email(message="Email musí být ve správném formátu.")
    @NotEmpty(message="Email musí být vyplněn.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
