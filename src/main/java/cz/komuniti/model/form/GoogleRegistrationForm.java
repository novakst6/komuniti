/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;


import cz.komuniti.service.validator.FieldEmailInDB;
import cz.komuniti.service.validator.FieldMatch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novakst6
 */
@FieldMatch.List({
    @FieldMatch(first="password",second="passwordConfirm",message="{validation.fieldmatch.password}")
})
@FieldEmailInDB(email="email", message="{validation.fieldemailindb.message}")
public class GoogleRegistrationForm {
    
    private String email;
    private String password;
    private String passwordConfirm;
    private Long regionId;
    private Long centerId;
    private String firstName = "BEZ JMÉNA";
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
        
    @Email(message="{validation.field.email.format}")
    @NotEmpty(message="{validation.field.email.notempty}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }
    
}
