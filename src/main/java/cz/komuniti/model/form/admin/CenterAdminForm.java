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
public class CenterAdminForm {
    
    private Long adminId;
    private Long centerId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }
    
    @AssertFalse(message="Uživatel musí být vybrátn.")
    public boolean isUserSelected(){
        return adminId == null;
    }
}
