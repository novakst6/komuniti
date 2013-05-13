/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;


import javax.validation.constraints.AssertFalse;

/**
 *
 * @author novakst6
 */

public class ItemUnionForm {   
    
    private Long primaryId;

    public Long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }
    
    @AssertFalse(message="Musí být vybrána primární věc.")
    public boolean isSelected(){
        return primaryId == null;
    }
    
}
