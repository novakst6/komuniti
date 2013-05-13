/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;


import cz.komuniti.service.validator.FieldEmailInDB;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novakst6
 */
@FieldEmailInDB(email="email",message="Tento email je již použitý. Použijte prosím jiný.")
public class UserAddForm {
    private String email;
    private String googleName;
    private Boolean active;
    private Long regionId;
    private Long centerId;

    @NotEmpty(message="Email musí být vyplněn!")
    @Email(message="Email musí být ve správném formátu!")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @NotEmpty(message="Jméno a příjmení musí být vyplněno")
    public String getGoogleName() {
        return googleName;
    }

    public void setGoogleName(String googleName) {
        this.googleName = googleName;
    }
    
}
