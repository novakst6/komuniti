/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form;


import cz.komuniti.service.validator.FieldEmailInDB;
import cz.komuniti.service.validator.FieldMatch;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novakst6
 */
@FieldMatch.List({
    @FieldMatch(first="email",second="emailConfirm",message="{validation.fieldmatch.email}"),
    @FieldMatch(first="password",second="passwordConfirm",message="{validation.fieldmatch.password}")
})
@FieldEmailInDB(email="email",message="{validation.fieldemailindb.message}")
public class UserRegistrationForm {
     
    private String email;
    private String emailConfirm;
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

    public UserRegistrationForm(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    public UserRegistrationForm() {
    }

    @Email(message="{validation.field.email.format}")
    @NotEmpty(message="{validation.field.email.notempty}")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message="{validation.field.password.notempty}")
    @Size(min=5, message="{validation.field.password.size}")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Email(message="{validation.field.email.confirm.format}")
    @NotEmpty(message="{validation.field.email.confirm.notempty}")
    public String getEmailConfirm() {
        return emailConfirm;
    }

    public void setEmailConfirm(String emailConfirm) {
        this.emailConfirm = emailConfirm;
    }
    
    @NotEmpty(message="{validation.field.password.confirm.notempty}")
    @Size(min=5, message="{validation.field.password.confirm.size}")
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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
