/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.form.admin;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author novakst6
 */
public class FileAddForm {
    
    private CommonsMultipartFile file;
    private String description;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
    
    @AssertFalse(message="Soubor musí být vybrán")
    public boolean isEmpty()
    {
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
    
    
}
