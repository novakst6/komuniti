/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author novakst6
 */
public class OfferTagAddForm {
    
    private String name;

    @NotEmpty(message="Jméno musí být vyplněno.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
