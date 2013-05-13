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

//@FieldNotMatch.List({
//    @FieldNotMatch(first="id",second="parentId",message="Region nemůže být sám sobě nadřazený region.")
//})
public class RegionEditForm {
    
    private Long id;
    private String name;
    private Long parentId;

    @NotEmpty(message="Jméno regionu musí být vyplněno.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
//    @AssertFalse(message="Region nemůže být sám sobě nadřazeným regionem.")
//    public boolean isRightParent(){
//        return id == parentId;
//    }
}
