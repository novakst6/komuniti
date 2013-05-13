/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author novakst6
 */
public class FileEditForm {
    private Long id;
    private CommonsMultipartFile file;
    private Boolean image;
    private String description;
    private Boolean keepFile;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }
    
    @AssertFalse(message="Soubor musí být vybrán")
    public boolean isEmpty()
    {
        if(keepFile == true){
            return false;
        }
        return file.isEmpty();
    }
    
    @AssertTrue(message="Velikost souboru překročila dovolenou velikost.")
    public boolean getCheckSize(){
            if(file == null){return true;}
            if(file.getSize() > 200000){
                return false;
            } else {
        return true;
            }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getKeepFile() {
        return keepFile;
    }

    public void setKeepFile(Boolean keepFile) {
        this.keepFile = keepFile;
    }
    
    
}
